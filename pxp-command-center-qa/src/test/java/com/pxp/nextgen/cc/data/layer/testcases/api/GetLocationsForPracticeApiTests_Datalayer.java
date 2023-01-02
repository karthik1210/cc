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
import com.pxp.cc.pojos.Locations;
import com.pxp.cc.pojos.OrgSolutions;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetLocationsForPracticeApiTests_Datalayer extends BaseRest {
	private LocationsInfo locationInfo;
	private SharedInfo sharedInfo;
	private LocationsValidations locationsValidations;
	JsonPath jsonPath;
	List<String> list = null;
	private ClientInfo clientInfo;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			getLocationByPracticeResponse, enableDisableSolutionForLocation, createClientApiResponse,
			updateProviderDetailsApiResponse, updateLocationForProvider,
			associateDeassociatePracticesForLocationApiResponse, searchLocationsResult, createLocationApiResponse,
			updateSolutionForProviders, updateLocationForPracticesResponse, createProviderApiResponse,
			updateSolutionsApiResponse, fetchOrgSolutionDetailsApiResponse, updateSolutionsForLocationResponse;
	private Validations validation;
	private OrgInfo orgInfo;
	List<Locations> locationIdone;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		orgInfo = new OrgInfo();
		locationsValidations = new LocationsValidations();
		locationInfo = new LocationsInfo();
		clientId = createClient(ccUser.getClientOneType());
		systemId = createSystemNode(clientId);
		enterpriseId = createEnterpriseNode(systemId);
		practiceId = createPracticeNode(enterpriseId);
		locationId = createLocation(clientId);
		providerId = createProvider(practiceId);
	}

	public int createClient(String clientType) throws Exception {
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	public int createSystemNode(int clientId) throws Exception {
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
				testData.getProperty("api.system.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createSystemApiResponse.jsonPath().get("orgId");
	}

	public int createEnterpriseNode(int SystemId) throws Exception {
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.displayname"), testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), SystemId,
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
		createLocationApiResponse = locationInfo.createLocation_DL(String.valueOf(clientId), name, addressLine1,
				addressLine2, city, state, zipcode, lat, lng, externalId, timezoneCode, "");
		return createLocationApiResponse.jsonPath().get("id");
	}

	public int createProvider(int practiceId) throws Exception {
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DataLayer(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		return createProviderApiResponse.jsonPath().get("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid providerId and practiceId and verify response body")
	public void testToGetLocationIdForExistingProviderIdAndPracticeId() throws IOException {
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
		enableDisableSolutionForLocation = sharedInfo.enableDisableSolutionForLocation_DL(practiceId, locationId,
				Integer.parseInt(testData.getProperty("engage.id").trim()), true);
		CCUtils.assertEquals(enableDisableSolutionForLocation.getStatusCode(), 200);
		logStep("Associate provider for Practice");
		updateProviderDetailsApiResponse = sharedInfo.updateProviderDetails(providerId, String.valueOf(practiceId),
				"true");
		CCUtils.assertEquals(updateProviderDetailsApiResponse.getStatusCode(), 200);
		logStep("Associate solution for provider");
		int solutionId = Integer.parseInt(testData.getProperty("engage.id"));
		updateSolutionForProviders = sharedInfo.enableOrDisableSolutionsForProvider_DL(providerId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(updateSolutionForProviders.getStatusCode(), 200);
		logStep("Associate location for provider");
		logStep("Add valid location id for existing providerId and practiceId");
		locationIdone = Arrays.asList(new Locations(locationId, true));
		updateLocationForProvider = sharedInfo.addOrRemoveLocationForProvider(providerId, practiceId, locationIdone);
		CCUtils.assertEquals(updateLocationForProvider.getStatusCode(), 207);
		logStep("Get location for Practice");
		getLocationByPracticeResponse = sharedInfo.getLocationForPractice(practiceId, providerId,
				testData.getProperty("solution.engage"), "en");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 200);
		locationsValidations.verifyAddOrRemoveLocationsApiResponseBody(getLocationByPracticeResponse, locationId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid providerId and invalid practiceId and verify response code")
	public void testToGetLocationIdForExistingProviderIdAndNonExistingPracticeId() throws IOException {
		logStep("Get location for Non Existing PracticeId");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		getLocationByPracticeResponse = sharedInfo.getLocationForPractice(practiceId, providerId,
				testData.getProperty("solution.portal"), "en");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid providerId and valid practiceId and verify response code")
	public void testToGetLocationIdForNonExistingProviderIdAndExistingPracticeId() throws IOException {
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
		enableDisableSolutionForLocation = sharedInfo.enableDisableSolutionForLocation_DL(practiceId, locationId,
				Integer.parseInt(testData.getProperty("Portal.id").trim()), true);
		CCUtils.assertEquals(enableDisableSolutionForLocation.getStatusCode(), 200);
		logStep("Get location for Non Existing ProviderId");
		int providerId = Integer.parseInt(testData.getProperty("non.existing.provider.id"));
		getLocationByPracticeResponse = sharedInfo.getLocationForPractice(practiceId, providerId,
				testData.getProperty("solution.portal"), "en");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid providerId and invalid practiceId and verify response code")
	public void testToGetLocationIdForNonExistingProviderIdAndNonExistingPracticeId() throws IOException {
		logStep("Get location for Non Existing PracticeId And ProviderId");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		int providerId = Integer.parseInt(testData.getProperty("non.existing.provider.id"));
		getLocationByPracticeResponse = sharedInfo.getLocationForPractice(practiceId, providerId,
				testData.getProperty("solution.portal"), "en");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values for providerId and  practiceId and verify response code")
	public void testToGetLocationIdForNullValuesForProviderIdAndPracticeId() throws IOException {
		logStep("Get location for null PracticeId And ProviderId");
		getLocationByPracticeResponse = sharedInfo.getLocationForPractice_NullValue();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 400);
	}

}