//Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.GeneralSettingsClientDropdown;
import com.pxp.cc.helpers.LocationsInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.ClientTopNavDropdownValidations;
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class SearchClientLocationApiTests_BusinessLayer extends BaseRest {
	private LocationsInfo locationInfo;
	private LocationsValidations locationsValidations;
	JsonPath jsonPath;
	List<String> list = null;
	private ClientInfo clientInfo;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse, createClientApiResponse,
			searchLocationsResult, createLocationApiResponse, updateLocationForPracticesResponse,
			updateSolutionsApiResponse, fetchOrgSolutionDetailsApiResponse, updateSolutionsForLocationResponse;
	private Validations validation;
	private OrgInfo orgInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
		locationsValidations = new LocationsValidations();
		new GeneralSettingsClientDropdown();
		new ClientTopNavDropdownValidations();
		locationInfo = new LocationsInfo();
		clientId = createClient(ccUser.getClientOneType());
		systemId = createSystemNode(clientId);
		enterpriseId = createEnterpriseNode(systemId);
		practiceId = createPracticeNode(enterpriseId);
		locationId = createLocation(clientId);
	}

	public int createClient(String clientType) throws Exception {
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	public int createSystemNode(int clientId) throws Exception {
		createSystemApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", testData.getProperty("api.system.active"), "", clientId,
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createSystemApiResponse.jsonPath().get("orgId");
	}

	public int createEnterpriseNode(int SystemId) throws Exception {
		createEnterpriseApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemId,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createEnterpriseApiResponse.jsonPath().get("orgId");
	}

	public int createPracticeNode(int enterpriseId) throws Exception {
		createPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse.jsonPath().get("orgId");
	}

	public int createLocation(int clientId) throws Exception {
		String name = testData.getProperty("api.location.name");
		String addressLine1 = testData.getProperty("api.location.name");
		String addressLine2 = testData.getProperty("api.location.name");
		String city = testData.getProperty("api.location.name");
		String state = testData.getProperty("api.state.name");
		int zipcode = Integer.parseInt(testData.getProperty("api.zipcode"));
		int lat = Integer.parseInt(testData.getProperty("api.lat"));
		int lng = Integer.parseInt(testData.getProperty("api.lng"));
		int externalId = Integer.parseInt(testData.getProperty("api.external.id"));
		String timezoneCode = testData.getProperty("timezone.code");
		createLocationApiResponse = locationInfo.createLocation_BL(String.valueOf(clientId), name, addressLine1,
				addressLine2, city, state, zipcode, lat, lng, externalId, timezoneCode);
		return createLocationApiResponse.jsonPath().get("id");
	}

	@DataProvider(name = "searchLocationUsingValidInput")
	public Object[][] getDetails() throws Exception {
		return new Object[][] { { String.valueOf(clientId).trim(), null, null, null, null, null, null },
				{ String.valueOf(clientId).trim(), testData.getProperty("api.location.name").trim(), null, null, null,
						null, testData.getProperty("api.pageSize1") },
				{ String.valueOf(clientId).trim(), testData.getProperty("api.partial.location.name").trim(), null, null,
						null, null, testData.getProperty("api.pageSize1") } };
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "searchLocationUsingValidInput", description = "Search  location for client using clientId and lacation name")
	public void testSearchLocationUsingValidInput(String[] getDetails) throws IOException {
		logStep("Search  location for client using clientId and lacation name");
		searchLocationsResult = locationInfo.getLocationsForClient_BL(getDetails[0], getDetails[1], getDetails[2],
				getDetails[3], getDetails[4], getDetails[5], getDetails[6]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchLocationsResult);
		CCUtils.assertEquals(searchLocationsResult.getStatusCode(), 200);
		locationsValidations.verifySearchLocationOutputWithoutFilter(searchLocationsResult,
				testData.getProperty("search.location.full.name").trim());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search  location for client using PracticeId")
	public void testSearchLocationUsingPracticeId() throws IOException {
		logStep("Associate practices for locations");
		updateLocationForPracticesResponse = locationInfo.locationAssociateToPractice_BL(locationId, practiceId, true);
		CCUtils.assertEquals(updateLocationForPracticesResponse.getStatusCode(), 200);
		logStep("Search  location for client using valid input");
		searchLocationsResult = locationInfo.getLocationsForClient_BL(String.valueOf(clientId), null, null, null,
				String.valueOf(practiceId), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchLocationsResult);
		CCUtils.assertEquals(searchLocationsResult.getStatusCode(), 200);
		locationsValidations.verifySearchLocationOutputWithoutFilter(searchLocationsResult,
				testData.getProperty("search.location.full.name").trim());
		logStep("Deassociate practices for locations");
		updateLocationForPracticesResponse = locationInfo.locationAssociateToPractice_BL(locationId, practiceId, false);
		CCUtils.assertEquals(updateLocationForPracticesResponse.getStatusCode(), 200);
		logStep("Search  location for client using valid input");
		searchLocationsResult = locationInfo.getLocationsForClient_BL(String.valueOf(clientId), null, null, null,
				String.valueOf(practiceId), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchLocationsResult);
		CCUtils.assertEquals(searchLocationsResult.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search  location for client using SolutionId",enabled = false)
	public void testSearchLocationUsingSolutionId() throws IOException {
		logStep("Purchase solution for practice node");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutionsDetails_BL(solutionId, "New", practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		logStep("Associate practices for locations");
		updateLocationForPracticesResponse = locationInfo.locationAssociateToPractice_BL(locationId, practiceId, true);
		CCUtils.assertEquals(updateLocationForPracticesResponse.getStatusCode(), 200);
		logStep("Associate Solution for locations");
		updateSolutionsForLocationResponse = locationInfo.solutionAssociateToLocation_BL(locationId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(updateSolutionsForLocationResponse.getStatusCode(), 200);
		searchLocationsResult = locationInfo.getLocationsForClient_BL(String.valueOf(clientId), null, null, null, null,
				testData.getProperty("api.solution.id"), null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchLocationsResult);
		CCUtils.assertEquals(searchLocationsResult.getStatusCode(), 200);
		locationsValidations.verifySearchLocationOutputWithoutFilter(searchLocationsResult,
				testData.getProperty("search.location.full.name").trim());
		logStep("Deassociate Solution for locations");
		updateSolutionsForLocationResponse = locationInfo.solutionAssociateToLocation_BL(locationId, practiceId,
				solutionId, false);
		CCUtils.assertEquals(updateSolutionsForLocationResponse.getStatusCode(), 200);
		searchLocationsResult = locationInfo.getLocationsForClient_BL(String.valueOf(clientId), null, null, null, null,
				testData.getProperty("api.solution.id"), null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchLocationsResult);
		CCUtils.assertEquals(searchLocationsResult.getStatusCode(), 200);
		locationsValidations.verifySearchLocationOutputWithoutFilter(searchLocationsResult,
				testData.getProperty("search.location.full.name").trim());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search  location for client using invalid url")
	public void testSearchLocationUsingInvalidUrl() throws IOException {
		logStep("Search  location for client using invalid url");
		searchLocationsResult = locationInfo.getLocationsForClientInvalidUrl_BL(String.valueOf(clientId), null, null,
				null, null, null, null);
		CCUtils.assertEquals(searchLocationsResult.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(searchLocationsResult);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search  location for client using invalid method call PUT instead of GET")
	public void testSearchLocationUsingInvalidMethodPutInstedOfGet() throws IOException {
		logStep("Search  location for client using method call PUT instead of GET");
		searchLocationsResult = locationInfo.getLocationsForClientInvalidMethodCall_BL(String.valueOf(clientId), null,
				null, null, null, null, null);
		CCUtils.assertEquals(searchLocationsResult.getStatusCode(), 405);
		validation.verifyResponseBodyUpdatingClientWithOutClientIdAndAllValuesNull(searchLocationsResult);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search  location for null value")
	public void testSearchLocationForNullValue() throws IOException {
		logStep("Search  location for null value in clientId input field");
		searchLocationsResult = locationInfo.getLocationsForClientForNullValue_BL();
		CCUtils.assertEquals(searchLocationsResult.getStatusCode(), 400);
	}
}