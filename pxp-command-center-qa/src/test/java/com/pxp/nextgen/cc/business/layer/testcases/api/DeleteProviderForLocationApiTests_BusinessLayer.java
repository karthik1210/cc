// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.LocationsInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class DeleteProviderForLocationApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	private LocationsValidations locationsValidations;
	private Validations validation;
	private LocationsInfo locationInfo;
	Response getProviderDetailsApiResponse, createClientApiResponse, createProviderApiResponse, createSystemApiResponse,
			createPracticeApiResponse, createEnterpriseApiResponse, createNewLocationApiResponse,
			addProviderForLocationApiResponse, deleteProviderForLocationsApiResponse,
			getProvidersForLocationApiResponse, associateDeassociatePracticesForLocationApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, locationId, providerIdOne;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		locationInfo = new LocationsInfo();
		locationsValidations = new LocationsValidations();
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
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
		locationId = createLocation(clientId);
		logStep("Associate NGE practice with loacation");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		providerId = createProvider(practiceId);
		providerIdOne = createProvider(practiceId);
	}

	public int createProvider(int practiceId) {
		String firstName = "ProviderOne".concat(CCUtils.generateAlphaNumericString(6));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		logStep("Validating response information");
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		return createProviderApiResponse.jsonPath().getInt("providerId");
	}

	public int createLocation(int clientId) throws Exception {
		logStep("Enter valid data in mandatory field");
		String locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		String externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_BL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		logStep("Validating response information");
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 200);
		validation.verifyCreateLocationApiResponseBody(createNewLocationApiResponse, locationName);
		return createNewLocationApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 2, description = "Delete provider for location using valid providerId,locationId,practiceId and verify response body")
	public void testDeteteProviderForLocationFunctionality() throws IOException {
		logStep("Add Provider for Location");
		List<Integer> providerIdList = new ArrayList<Integer>();
		providerIdList.add(providerId);
		addProviderForLocationApiResponse = locationInfo.associateProvidersToLocation_BL(locationId, practiceId,
				providerIdList);
		CCUtils.assertEquals(addProviderForLocationApiResponse.getStatusCode(), 207);
		logStep("Fetch provider name using providerId");
		getProviderDetailsApiResponse = sharedInfo.getProviderDetails(clientId, providerId);
		logStep("Verify newly added provider is displayed for location");
		getProvidersForLocationApiResponse = locationInfo.getProvidersForLocation_BL(locationId, practiceId, 0, 10," ");
		CCUtils.assertEquals(getProvidersForLocationApiResponse.getStatusCode(), 200);
		logStep("Delete provider for location");
		deleteProviderForLocationsApiResponse = sharedInfo.deleteProviderForLocation_BL(locationId, practiceId,
				providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteProviderForLocationsApiResponse);
		CCUtils.assertEquals(deleteProviderForLocationsApiResponse.getStatusCode(), 200);
		logStep("Verify deleted provider is not displayed for location");
		getProvidersForLocationApiResponse = locationInfo.getProvidersForLocation_BL(locationId, practiceId, 0, 10,
				"");
		CCUtils.assertEquals(getProvidersForLocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 0, description = "Verify response body for invalid location id")
	public void testErrorMessageForInvalidLocationId() throws IOException {
		logStep("Delete provider for location using invalid locationId and valid providerId and practiceId");
		deleteProviderForLocationsApiResponse = sharedInfo.deleteProviderForLocation_BL(
				Integer.parseInt(testData.getProperty("non.existing.location.id")), practiceId, providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteProviderForLocationsApiResponse);
		CCUtils.assertEquals(deleteProviderForLocationsApiResponse.getStatusCode(), 400);
		locationsValidations.verifyErrorResponseForNonExistingId(deleteProviderForLocationsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 1, description = "Verify response body for invalid practiceId")
	public void testErrorMessageForInvalidPracticeId() throws IOException {
		logStep("Delete provider for location using invalid practiceId and valid providerId and locationId");
		deleteProviderForLocationsApiResponse = sharedInfo.deleteProviderForLocation_BL(locationId,
				Integer.parseInt(testData.getProperty("non.existing.org.id")), providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteProviderForLocationsApiResponse);
		CCUtils.assertEquals(deleteProviderForLocationsApiResponse.getStatusCode(), 400);
		locationsValidations.verifyErrorResponseForNonExistingId(deleteProviderForLocationsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify response body for invalid provider id")
	public void testErrorMessageForInvalidProviderId() throws IOException {
		logStep("Delete provider for location using invalid providerId and valid locationId and practiceId");
		deleteProviderForLocationsApiResponse = sharedInfo.deleteProviderForLocation_BL(locationId, practiceId,
				Integer.parseInt(testData.getProperty("non.existing.provider.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteProviderForLocationsApiResponse);
		CCUtils.assertEquals(deleteProviderForLocationsApiResponse.getStatusCode(), 400);
		locationsValidations.verifyErrorResponseForNonExistingId(deleteProviderForLocationsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 3, description = "Do not add provider with location and try to delete same provider and verify response body")
	public void testErrorMessageForNonAssociatedProviderId() throws IOException {
		logStep("Do not add newly created provider with location and Delete same provider for location using valid providerId, locationId and practiceId");
		deleteProviderForLocationsApiResponse = sharedInfo.deleteProviderForLocation_BL(locationId, practiceId,
				providerIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteProviderForLocationsApiResponse);
		CCUtils.assertEquals(deleteProviderForLocationsApiResponse.getStatusCode(), 400);
		locationsValidations.verifyErrorResponseForNonExistingId(deleteProviderForLocationsApiResponse);
	}

}
