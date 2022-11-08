//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.LocationsInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.OrgSolutions;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetPracticeLocationApiTests_DataLayer extends BaseRest {
	private LocationsInfo locationInfo;
	private SharedInfo sharedInfo;
	private LocationsInfo locationsInfo;
	private LocationsValidations locationsValidations;
	JsonPath jsonPath;
	private ClientInfo clientInfo;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response createClientApiResponse, createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			getPracticeLocationApiResponse, enableDisableSolutionForLocation,
			associateDeassociatePracticesForLocationApiResponse, createLocationApiResponse,
			updateLocationForPracticesResponse, updateSolutionsApiResponse;
	private Validations validation;
	private OrgInfo orgInfo;
	String locationName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		locationsInfo = new LocationsInfo();
		orgInfo = new OrgInfo();
		locationsValidations = new LocationsValidations();
		locationInfo = new LocationsInfo();
		clientId = createClient(ccUser.getClientOneType());
		systemId = createSystemNode(clientId);
		enterpriseId = createEnterpriseNode(systemId);
		practiceId = createPracticeNode(enterpriseId);
		locationId = createLocation(clientId);
	}

	public int createClient(String clientType) throws Exception {
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient_DataLayer(ccUser.getActive(), clientName, clientSapNumber,
				"true", Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	public int createSystemNode(int clientId) throws Exception {
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "",
				testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), "", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createSystemApiResponse.jsonPath().get("orgId");
	}

	public int createEnterpriseNode(int SystemId) throws Exception {
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"), "",
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"), SystemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createEnterpriseApiResponse.jsonPath().get("orgId");
	}

	public int createPracticeNode(int enterpriseId) throws Exception {
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse.jsonPath().get("orgId");
	}

	public int createLocation(int clientId) throws Exception {
		locationName = testData.getProperty("api.location.name");
		String addressLine1 = testData.getProperty("api.location.name");
		String addressLine2 = testData.getProperty("api.location.name");
		String city = testData.getProperty("api.location.name");
		String state = testData.getProperty("api.state.name");
		int zipcode = Integer.parseInt(testData.getProperty("api.zipcode"));
		int lat = Integer.parseInt(testData.getProperty("api.lat"));
		int lng = Integer.parseInt(testData.getProperty("api.lng"));
		int externalId = Integer.parseInt(testData.getProperty("api.external.id"));
		String timezoneCode = testData.getProperty("timezone.code");
		createLocationApiResponse = locationInfo.createLocation_DL(String.valueOf(clientId), locationName, addressLine1,
				addressLine2, city, state, zipcode, lat, lng, externalId, timezoneCode, "");
		return createLocationApiResponse.jsonPath().get("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId and practiceId to get Practice Location and verify response body")
	public void testGetLocationsForExistingLocationIdAndPracticeId() throws IOException {
		logStep("Add product for practice");
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id").trim(),
						testData.getProperty("status.newly.purchased").trim()),
				new OrgSolutions(testData.getProperty("engage.id").trim(),
						testData.getProperty("status.newly.purchased").trim()));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, practiceId);
		logStep("Associate location for Practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, practiceId, true);
		logStep("Associate solution for location");
		int solutionId = Integer.parseInt(testData.getProperty("engage.id"));
		enableDisableSolutionForLocation = locationsInfo.toggleSolutionStatusApiResponse_DL(locationId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(enableDisableSolutionForLocation.getStatusCode(), 200);
		logStep("Get Practice Location");
		getPracticeLocationApiResponse = sharedInfo.getPracticeLocation_DL(practiceId, locationId,
				testData.getProperty("solution.engage"), "en");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeLocationApiResponse);
		CCUtils.assertEquals(getPracticeLocationApiResponse.getStatusCode(), 200);
		locationsValidations.verifyGetPracticeLocationDetailsApiResponseBody(getPracticeLocationApiResponse,
				locationName);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing locationId and existing practiceId to get Practice Location and verify response body")
	public void testGetLocationsForNonExistingLocationIdAndExistingPracticeId() throws IOException {
		logStep("Add product for practice");
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id").trim(),
						testData.getProperty("status.newly.purchased").trim()),
				new OrgSolutions(testData.getProperty("engage.id").trim(),
						testData.getProperty("status.newly.purchased").trim()));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, practiceId);
		logStep("Associate location for Practice");
		int locationId = Integer.parseInt(testData.getProperty("non.existing.location.id"));
		logStep("Get Practice Location");
		getPracticeLocationApiResponse = sharedInfo.getPracticeLocation_DL(practiceId, locationId,
				testData.getProperty("solution.engage"), "en");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeLocationApiResponse);
		CCUtils.assertEquals(getPracticeLocationApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(getPracticeLocationApiResponse);
	}


	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId and non-existing practiceId to get Practice Location and verify response body")
	public void testGetLocationsForExistingLocationIdAndNonExistingPracticeId() throws IOException {
		logStep("Get Practice Location");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		getPracticeLocationApiResponse = sharedInfo.getPracticeLocation_DL(practiceId, locationId,
				testData.getProperty("solution.engage"), "en");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeLocationApiResponse);
		CCUtils.assertEquals(getPracticeLocationApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(getPracticeLocationApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing locationId and existing practiceId to get Practice Location and verify response body")
	public void testGetLocationsForNonExistingLocationIdAndNonExistingPracticeId() throws IOException {
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		int locationId = Integer.parseInt(testData.getProperty("non.existing.location.id"));
		logStep("Get Practice Location");
		getPracticeLocationApiResponse = sharedInfo.getPracticeLocation_DL(practiceId, locationId,
				testData.getProperty("solution.engage"), "en");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeLocationApiResponse);
		CCUtils.assertEquals(getPracticeLocationApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(getPracticeLocationApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter locationId and practiceId as null to get Practice Location and verify response body")
	public void testGetLocationsForLocationIdAndPracticeIdAsNull() throws IOException {
		logStep("Get Practice Location");
		getPracticeLocationApiResponse = sharedInfo.getPracticeLocation_NullValues_DL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeLocationApiResponse);
		CCUtils.assertEquals(getPracticeLocationApiResponse.getStatusCode(), 400);
	}
}