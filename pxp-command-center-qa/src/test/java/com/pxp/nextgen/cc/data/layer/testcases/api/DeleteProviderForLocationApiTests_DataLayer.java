// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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

public class DeleteProviderForLocationApiTests_DataLayer extends BaseRest {
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
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		locationInfo = new LocationsInfo();
		locationsValidations = new LocationsValidations();
		logStep("Create nge practice below the client with valid details");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.system.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
	    clientId = createClientApiResponse.jsonPath().getInt("clientId");
		logStep("Create a new system below client.");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "",
				testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), "", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new enterprise below system.");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"), "",
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new practice below enterprise.");
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		locationId = createLocation(clientId);
		logStep("Associate NGE practice with loacation");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		providerId = createProvider(practiceId);
		providerIdOne = createProvider(practiceId);
	}

	public int createProvider(int practiceId) {
		String firstName = "ProviderOne".concat(CCUtils.generateAlphaNumericString(6));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		logStep("Validating response information");
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		return createProviderApiResponse.jsonPath().getInt("providerId");
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
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 200);
		validation.verifyCreateLocationApiResponseBody(createNewLocationApiResponse, locationName);
		return createNewLocationApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 2, description = "Delete provider for location using valid providerId,locationId,practiceId and verify response body")
	public void testDeteteProviderForLocationFunctionality() throws IOException {
		logStep("Add Provider for Location");
		List<Integer> providerIdList = new ArrayList<Integer>();
		providerIdList.add(providerId);
		addProviderForLocationApiResponse = locationInfo.associateProvidersToLocation_DL(locationId, practiceId,
				providerIdList);
		CCUtils.assertEquals(addProviderForLocationApiResponse.getStatusCode(), 207);
		logStep("Fetch provider name using providerId");
		getProviderDetailsApiResponse = sharedInfo.getProviderDetails_DL(clientId,providerId);
		String name = locationsValidations.getFirstName(getProviderDetailsApiResponse);
		logStep("Verify newly added provider is displayed for location");
		getProvidersForLocationApiResponse = locationInfo.getProvidersForLocation_DL(locationId, practiceId, 0, 10,
				name);
		CCUtils.assertEquals(getProvidersForLocationApiResponse.getStatusCode(), 200);
		logStep("Delete provider for location");
		deleteProviderForLocationsApiResponse = sharedInfo.deleteProviderForLocation_DL(locationId, practiceId,
				providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteProviderForLocationsApiResponse);
		CCUtils.assertEquals(deleteProviderForLocationsApiResponse.getStatusCode(), 200);
		logStep("Verify deleted provider is not displayed for location");
		getProvidersForLocationApiResponse = locationInfo.getProvidersForLocation_DL(locationId, practiceId, 0, 10,
				name);
		CCUtils.assertEquals(getProvidersForLocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 0, description = "Verify response body for invalid location id")
	public void testErrorMessageForInvalidLocationId() throws IOException {
		logStep("Delete provider for location using invalid locationId and valid providerId and practiceId");
		deleteProviderForLocationsApiResponse = sharedInfo.deleteProviderForLocation_DL(
				Integer.parseInt(testData.getProperty("non.existing.location.id")), practiceId, providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteProviderForLocationsApiResponse);
		CCUtils.assertEquals(deleteProviderForLocationsApiResponse.getStatusCode(), 400);
		locationsValidations.verifyErrorResponseForNonExistingId(deleteProviderForLocationsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 1, description = "Verify response body for invalid practiceId")
	public void testErrorMessageForInvalidPracticeId() throws IOException {
		logStep("Delete provider for location using invalid practiceId and valid providerId and locationId");
		deleteProviderForLocationsApiResponse = sharedInfo.deleteProviderForLocation_DL(locationId,
				Integer.parseInt(testData.getProperty("non.existing.org.id")), providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteProviderForLocationsApiResponse);
		CCUtils.assertEquals(deleteProviderForLocationsApiResponse.getStatusCode(), 400);
		locationsValidations.verifyErrorResponseForNonExistingId(deleteProviderForLocationsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify response body for invalid provider id")
	public void testErrorMessageForInvalidProviderId() throws IOException {
		logStep("Delete provider for location using invalid providerId and valid locationId and practiceId");
		deleteProviderForLocationsApiResponse = sharedInfo.deleteProviderForLocation_DL(locationId, practiceId,
				Integer.parseInt(testData.getProperty("non.existing.provider.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteProviderForLocationsApiResponse);
		CCUtils.assertEquals(deleteProviderForLocationsApiResponse.getStatusCode(), 400);
		locationsValidations.verifyErrorResponseForNonExistingId(deleteProviderForLocationsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 3, description = "Do not add provider with location and try to delete same provider and verify response body")
	public void testErrorMessageForNonAssociatedProviderId() throws IOException {
		logStep("Do not add newly created provider with location and Delete same provider for location using valid providerId, locationId and practiceId");
		deleteProviderForLocationsApiResponse = sharedInfo.deleteProviderForLocation_DL(locationId, practiceId,
				providerIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteProviderForLocationsApiResponse);
		CCUtils.assertEquals(deleteProviderForLocationsApiResponse.getStatusCode(), 400);
		locationsValidations.verifyErrorResponseForNonExistingId(deleteProviderForLocationsApiResponse);
	}
}
