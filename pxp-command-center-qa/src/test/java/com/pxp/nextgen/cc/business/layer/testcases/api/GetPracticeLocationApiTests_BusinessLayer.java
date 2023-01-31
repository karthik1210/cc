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
import com.pxp.cc.pojos.OrgSolutions;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetPracticeLocationApiTests_BusinessLayer extends BaseRest {
	private LocationsInfo locationInfo;
	private SharedInfo sharedInfo;
	private LocationsInfo locationsInfo;
	private LocationsValidations locationsValidations;
	JsonPath jsonPath;
	private CreateClientHierarchy_BL clientHierObj;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId;
	Response getPracticeLocationApiResponse, enableDisableSolutionForLocation,
			associateDeassociatePracticesForLocationApiResponse, createLocationApiResponse,
			updateLocationForPracticesResponse, updateSolutionsApiResponse;
	private Validations validation;
	private OrgInfo orgInfo;
	String locationName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		validation = new Validations();
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
		createLocationApiResponse = locationInfo.createLocation_BL(String.valueOf(clientId), locationName, addressLine1,
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
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_BusinessLayer(productList, practiceId);
		logStep("Associate location for Practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		logStep("Associate solution for location");
		int solutionId = Integer.parseInt(testData.getProperty("engage.id"));
		enableDisableSolutionForLocation = locationsInfo.toggleSolutionStatusApiResponse_BL(locationId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(enableDisableSolutionForLocation.getStatusCode(), 200);
		logStep("Get Practice Location");
		getPracticeLocationApiResponse = sharedInfo.getPracticeLocation_BL(practiceId, locationId,
				testData.getProperty("solution.engage"), "en");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeLocationApiResponse);
		CCUtils.assertEquals(getPracticeLocationApiResponse.getStatusCode(), 200);
		locationsValidations.verifyGetPracticeLocationDetailsApiResponseBody(getPracticeLocationApiResponse,
				locationName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId and non-existing practiceId to get Practice Location and verify response body")
	public void testGetLocationsForExistingLocationIdAndNonExistingPracticeId() throws IOException {
		logStep("Get Practice Location");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		getPracticeLocationApiResponse = sharedInfo.getPracticeLocation_BL(practiceId, locationId,
				testData.getProperty("solution.engage"), "en");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeLocationApiResponse);
		CCUtils.assertEquals(getPracticeLocationApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(getPracticeLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing locationId and existing practiceId to get Practice Location and verify response body")
	public void testGetLocationsForNonExistingLocationIdAndExistingPracticeId() throws IOException {
		logStep("Add product for practice");
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id").trim(),
						testData.getProperty("status.newly.purchased").trim()),
				new OrgSolutions(testData.getProperty("engage.id").trim(),
						testData.getProperty("status.newly.purchased").trim()));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_BusinessLayer(productList, practiceId);
		logStep("Associate location for Practice");
		int locationId = Integer.parseInt(testData.getProperty("non.existing.location.id"));
		logStep("Get Practice Location");
		getPracticeLocationApiResponse = sharedInfo.getPracticeLocation_BL(practiceId, locationId,
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
		getPracticeLocationApiResponse = sharedInfo.getPracticeLocation_BL(practiceId, locationId,
				testData.getProperty("solution.engage"), "en");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeLocationApiResponse);
		CCUtils.assertEquals(getPracticeLocationApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(getPracticeLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter locationId and practiceId as null to get Practice Location and verify response body")
	public void testGetLocationsForLocationIdAndPracticeIdAsNull() throws IOException {
		logStep("Get Practice Location");
		getPracticeLocationApiResponse = sharedInfo.getPracticeLocation_NullValues_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeLocationApiResponse);
		CCUtils.assertEquals(getPracticeLocationApiResponse.getStatusCode(), 400);
	}
}