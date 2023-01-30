//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.LocationsInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.Locations;
import com.pxp.cc.pojos.OrgSolutions;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetLocationsByPracticeApiTests_Businesslayer extends BaseRest {
	private LocationsInfo locationInfo;
	private SharedInfo sharedInfo;
	private LocationsInfo locationsInfo;
	private LocationsValidations locationsValidations;
	JsonPath jsonPath;
	private CreateClientHierarchy_BL clientHierObj;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId;
	Response getLocationByPracticeResponse, enableDisableSolutionForLocation, updateProviderDetailsApiResponse,
			updateLocationForProvider, associateDeassociatePracticesForLocationApiResponse, createLocationApiResponse,
			updateSolutionForProviders, updateLocationForPracticesResponse, createProviderApiResponse,
			updateSolutionsApiResponse;
	private OrgInfo orgInfo;
	List<Locations> locationIdone;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		locationsInfo = new LocationsInfo();
		orgInfo = new OrgInfo();
		locationsValidations = new LocationsValidations();
		locationInfo = new LocationsInfo();
		clientHierObj = new CreateClientHierarchy_BL();
		   
		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		locationId = createLocation(clientId);
		providerId = createProvider(practiceId);
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
				addressLine2, city, state, zipcode, lat, lng, externalId, timezoneCode, "");
		return createLocationApiResponse.jsonPath().get("id");
	}

	public int createProvider(int practiceId) throws Exception {
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		return createProviderApiResponse.jsonPath().get("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid providerId and practiceId and verify response body")
	public void testToGetLocationsForExistingProviderIdAndPracticeId() throws IOException {
		logStep("Add product for practice");
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id").trim(),
						testData.getProperty("status.newly.purchased").trim()),
				new OrgSolutions(testData.getProperty("engage.id").trim(),
						testData.getProperty("status.newly.purchased").trim()));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_BusinessLayer(productList, practiceId);
		logStep("Associate location for Practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		logStep("Associate solution for location");
		int solutionId = Integer.parseInt(testData.getProperty("engage.id"));
		enableDisableSolutionForLocation = locationsInfo.toggleSolutionStatusApiResponse_BL(locationId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(enableDisableSolutionForLocation.getStatusCode(), 200);
		logStep("Associate provider for Practice");
		updateProviderDetailsApiResponse = sharedInfo.updateProviderDetails_BL(providerId, String.valueOf(practiceId),
				"true");
		CCUtils.assertEquals(updateProviderDetailsApiResponse.getStatusCode(), 200);
		logStep("Associate solution for provider");
		updateSolutionForProviders = sharedInfo.enableOrDisableSolutionsForProvider_BL(providerId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(updateSolutionForProviders.getStatusCode(), 200);
		logStep("Associate location for provider");
		locationIdone = Arrays.asList(new Locations(locationId, true));
		updateLocationForProvider = sharedInfo.addOrRemoveLocationForProvider_BL(providerId, practiceId, locationIdone);
		CCUtils.assertEquals(updateLocationForProvider.getStatusCode(), 207);
		logStep("Get location for Practice");
		getLocationByPracticeResponse = sharedInfo.getLocationForPractice_BL(practiceId, providerId,
				testData.getProperty("solution.engage"), "en");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 200);
		locationsValidations.verifyAddOrRemoveLocationsApiResponseBody(getLocationByPracticeResponse, locationId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing providerId and non-existing practiceId and verify response code")
	public void testToGetLocationsForExistingProviderIdAndNonExistingPracticeId() throws IOException {
		logStep("Get location for Non Existing PracticeId");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		getLocationByPracticeResponse = sharedInfo.getLocationForPractice_BL(practiceId, providerId,
				testData.getProperty("solution.portal"), "en");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing providerId and existing practiceId and verify response code")
	public void testToGetLocationsForNonExistingProviderIdAndExistingPracticeId() throws IOException {
		logStep("Add product for practice");
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id").trim(),
						testData.getProperty("status.newly.purchased").trim()),
				new OrgSolutions(testData.getProperty("engage.id").trim(),
						testData.getProperty("status.newly.purchased").trim()));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_BusinessLayer(productList, practiceId);
		logStep("Associate location for Practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		logStep("Associate solution for location");
		int solutionId = Integer.parseInt(testData.getProperty("Portal.id"));
		enableDisableSolutionForLocation = locationsInfo.toggleSolutionStatusApiResponse_BL(locationId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(enableDisableSolutionForLocation.getStatusCode(), 200);
		logStep("Get location for Non Existing ProviderId");
		int providerId = Integer.parseInt(testData.getProperty("non.existing.provider.id"));
		getLocationByPracticeResponse = sharedInfo.getLocationForPractice_BL(practiceId, providerId,
				testData.getProperty("solution.portal"), "en");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing providerId and non-existing practiceId and verify response code")
	public void testToGetLocationIdForNonExistingProviderIdAndNonExistingPracticeId() throws IOException {
		logStep("Get location for Non Existing PracticeId And ProviderId");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		int providerId = Integer.parseInt(testData.getProperty("non.existing.provider.id"));
		getLocationByPracticeResponse = sharedInfo.getLocationForPractice_BL(practiceId, providerId,
				testData.getProperty("solution.portal"), "en");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values for providerId and practiceId fields and verify response code")
	public void testToGetLocationIdForNullValuesForProviderIdAndPracticeId() throws IOException {
		logStep("Get location for null PracticeId And ProviderId");
		getLocationByPracticeResponse = sharedInfo.getLocationForPractice_NullValues_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 400);
	}
}
