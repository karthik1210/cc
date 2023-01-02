// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class DeleteResourceForLocationApiTests_DataLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private LocationsValidations locationsValidations;
	private Validations validation;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createResourceApiResponse, createNewLocationApiResponse,
			addResourceForLocationApiResponse, deleteResourceForLocationsApiResponse,
			getResourcesForLocationApiResponse, associateDeassociatePracticesForLocationApiResponse,
			getResourceDetailsApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, locationId, resourceIdOne;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		resourceInfo = new ResourceInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		locationsValidations = new LocationsValidations();
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(ccUser.getActive(), clientName, clientSapNumber,
				"false", Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId(),
				testData.getProperty("ehr.identifier"), testData.getProperty("pm.identifier"));
		clientId = createClientApiResponse.jsonPath().getInt("clientId");

		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "",
				testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), "", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"), "",
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		locationId = createLocation(clientId);
		logStep("Associate NGE practice with loacation");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		providerId = createProvider(practiceId);
		resourceId = createResource(practiceId);
		resourceIdOne = createResource(practiceId);
	}

	public int createProvider(int practiceId) {
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		return createProviderApiResponse.jsonPath().getInt("providerId");
	}

	public int createResource(int practiceId) {
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		String resourceName = testData.getProperty("resource.name").concat(CCUtils.generateAlphaNumericString(5));
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		return createResourceApiResponse.jsonPath().getInt("resourceId");
	}

	public int createLocation(int clientId) throws Exception {
		logStep("Enter valid data in mandatory field");
		String locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		String externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_DL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewLocationApiResponse);
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 200);
		validation.verifyCreateLocationApiResponseBody(createNewLocationApiResponse, locationName);
		return createNewLocationApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Delete resources for location using valid resourceId,locationId,practiceId and verify response body")
	public void testDeteteResourceForLocationFunctionality() throws IOException {
		logStep("Add Resource for Location");
		List<Integer> resourceIdList = new ArrayList<Integer>();
		resourceIdList.add(resourceId);
		addResourceForLocationApiResponse = sharedInfo.addResourceForLocation_DL(locationId, practiceId,
				resourceIdList);
		CCUtils.assertEquals(addResourceForLocationApiResponse.getStatusCode(), 207);
		logStep("Fetch resource name using resourceId");
		getResourceDetailsApiResponse = sharedInfo.getResourceDetails_DL(resourceId);
		String name = locationsValidations.getResourceName(getResourceDetailsApiResponse);
		logStep("Verify newly added resource is displayed for location");
		getResourcesForLocationApiResponse = sharedInfo.getResourcesForLocation_DL(locationId, practiceId, 0, 10,
				name.trim());
		CCUtils.assertEquals(getResourcesForLocationApiResponse.getStatusCode(), 200);
		logStep("Delete resource for location");
		deleteResourceForLocationsApiResponse = sharedInfo.deleteResourceForLocation_DL(locationId, practiceId,
				resourceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteResourceForLocationsApiResponse);
		CCUtils.assertEquals(deleteResourceForLocationsApiResponse.getStatusCode(), 200);
		logStep("Verify deleted resource is not displayed for location");
		getResourcesForLocationApiResponse = sharedInfo.getResourcesForLocation_DL(locationId, practiceId, 0, 10,
				name.trim());
		CCUtils.assertEquals(getResourcesForLocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify response body for invalid location id")
	public void testErrorMessageForInvalidLocationId() throws IOException {
		logStep("Delete resource for location using invalid locationId and valid resourceId and practiceId");
		deleteResourceForLocationsApiResponse = sharedInfo.deleteResourceForLocation_DL(
				Integer.parseInt(testData.getProperty("non.existing.location.id")), practiceId, resourceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteResourceForLocationsApiResponse);
		CCUtils.assertEquals(deleteResourceForLocationsApiResponse.getStatusCode(), 400);
		locationsValidations.verifyErrorResponseForNonExistingId(deleteResourceForLocationsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify response body for invalid practiceId")
	public void testErrorMessageForInvalidPracticeId() throws IOException {
		logStep("Delete resource for location using invalid practiceId and valid resourceId and locationId");
		deleteResourceForLocationsApiResponse = sharedInfo.deleteResourceForLocation_DL(locationId,
				Integer.parseInt(testData.getProperty("non.existing.org.id")), resourceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteResourceForLocationsApiResponse);
		CCUtils.assertEquals(deleteResourceForLocationsApiResponse.getStatusCode(), 400);
		locationsValidations.verifyErrorResponseForNonExistingId(deleteResourceForLocationsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify response body for invalid resource id")
	public void testErrorMessageForInvalidResourceId() throws IOException {
		logStep("Delete resource for location using invalid resourceId and valid locationId and practiceId");
		deleteResourceForLocationsApiResponse = sharedInfo.deleteResourceForLocation_DL(locationId, practiceId,
				Integer.parseInt(testData.getProperty("non.existing.resource.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteResourceForLocationsApiResponse);
		CCUtils.assertEquals(deleteResourceForLocationsApiResponse.getStatusCode(), 400);
		locationsValidations.verifyErrorResponseForNonExistingId(deleteResourceForLocationsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Do not add resource with location and try to delete same resouce and verify response body")
	public void testErrorMessageForNonAssociatedResourceId() throws IOException {
		logStep("Do not add newly created resource with location and Delete same resource for location using valid resourceId, locationId and practiceId");
		deleteResourceForLocationsApiResponse = sharedInfo.deleteResourceForLocation_DL(locationId, practiceId,
				resourceIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteResourceForLocationsApiResponse);
		CCUtils.assertEquals(deleteResourceForLocationsApiResponse.getStatusCode(), 400);
		locationsValidations.verifyErrorResponseForNonExistingId(deleteResourceForLocationsApiResponse);
	}
}
