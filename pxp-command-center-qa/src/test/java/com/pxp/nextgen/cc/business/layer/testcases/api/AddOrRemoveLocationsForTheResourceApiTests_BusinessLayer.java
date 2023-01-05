//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.LocationsInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.Locations;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AddOrRemoveLocationsForTheResourceApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private LocationsValidations locationsValidations;
	private LocationsInfo locationsInfo;
	private Validations validation;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createResourceApiResponse, createNewLocationApiResponse,
			associateDeassociatePracticesForLocationApiResponse, addResourceForLocationApiResponse,
			updateLocationForResource;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, resourceIdOne, locationId;
	String clientName, clientSapNumber, externalId, firstName, lastName, resourceExternalId;
	List<Locations> locationIds;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		resourceInfo = new ResourceInfo();
		locationsInfo = new LocationsInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		locationsValidations = new LocationsValidations();
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber, "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId(),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		clientId = createClientApiResponse.jsonPath().getInt("clientId");
		createSystemApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", testData.getProperty("api.system.active"), "", clientId,
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		createEnterpriseApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemId,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		createPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Create provider for resourceId");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new location");
		String locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_BL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 200);
		locationId = createNewLocationApiResponse.jsonPath().getInt("id");
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		String resourceName = CCUtils.generateAlphaNumericString(8);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add location id for existing ResourceId and practiceId and verify response body")
	public void testToAddExistingLocationIdForExistingResourceId() throws IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Add valid location id for existing resourceId and practiceId");
		locationIds = Arrays.asList(new Locations(locationId, true));
		updateLocationForResource = sharedInfo.addOrRemoveLocationForResource_BL(resourceId, practiceId, locationIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForResource);
		CCUtils.assertEquals(updateLocationForResource.getStatusCode(), 207);
		locationsValidations.verifyAddOrRemoveLocationsApiResponseBody(updateLocationForResource, locationId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Remove location id for existing resourceId and practiceId and verify response body")
	public void testToRemoveExistingLocationIdForExistingResourceId() throws IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Add valid location id for existing providerId and practiceId");
		locationIds = Arrays.asList(new Locations(locationId, false));
		updateLocationForResource = sharedInfo.addOrRemoveLocationForResource_BL(resourceId, practiceId, locationIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForResource);
		CCUtils.assertEquals(updateLocationForResource.getStatusCode(), 207);
		locationsValidations.verifyAddOrRemoveLocationsApiResponseBody(updateLocationForResource, locationId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting location id for existing resourceId and practiceId  and verify error message")
	public void testToAddNonExistingLocationIdForExistingResourceId() throws IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Enter nonexisting location id for existing resourceId and practiceId");
		locationIds = Arrays
				.asList(new Locations(Integer.parseInt(testData.getProperty("nonexisting.location.id")), true));
		updateLocationForResource = sharedInfo.addOrRemoveLocationForResource_BL(resourceId, practiceId, locationIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForResource);
		CCUtils.assertEquals(updateLocationForResource.getStatusCode(), 207);
		ArrayList<Integer> locationId = new ArrayList<Integer>();
		locationId.add(Integer.parseInt(testData.getProperty("nonexisting.location.id")));
		locationsValidations.verifyAddOrRemoveLocationsApiResponseBodyForInvalidInput(updateLocationForResource,
				locationId, "ERROR", "Invalid input");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid location id for nonexisting resource id and existing practice id and verify error message")
	public void testValidLocationIdForNonExistingResourceId() throws IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		logStep("Enter valid location id for nonexisting resource id and existing practice id");
		locationIds = Arrays.asList(new Locations(locationId, true));
		updateLocationForResource = sharedInfo.addOrRemoveLocationForResource_BL(
				Integer.parseInt(testData.getProperty("nonexisting.resourceId")), practiceId, locationIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForResource);
		CCUtils.assertEquals(updateLocationForResource.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateLocationForResource);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid location id for nonexisting resource and practiceId and verify error message")
	public void testValidLocationIdForNonExistingResourceIdAndPracticeId() throws IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		logStep("Enter valid location id for nonexisting provider and practiceId");
		locationIds = Arrays.asList(new Locations(locationId, true));
		updateLocationForResource = sharedInfo.addOrRemoveLocationForResource_BL(
				Integer.parseInt(testData.getProperty("nonexisting.resourceId")),
				Integer.parseInt(testData.getProperty("nonexisting.practiceId")), locationIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForResource);
		CCUtils.assertEquals(updateLocationForResource.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateLocationForResource);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank input in request payload and verify error message")
	public void testErrorMessageForBlankRequestPayload() throws IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		logStep("Add valid resource and practiceId");
		locationIds = Arrays.asList();
		updateLocationForResource = sharedInfo.addOrRemoveLocationForResource_BL(resourceId, practiceId, locationIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForResource);
		CCUtils.assertEquals(updateLocationForResource.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateLocationForResource);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values for resourceId and practiceId to add Locations and verify error message")
	public void testAddToLocationForNullValues() throws IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, practiceId, true);
		updateLocationForResource = sharedInfo.getLocationsForResourceForNullValues_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForResource);
		CCUtils.assertEquals(updateLocationForResource.getStatusCode(), 400);
	}
}
