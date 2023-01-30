//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import io.restassured.response.Response;

public class GetLocationByPracticeApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private LocationsValidations locationsvalidations;
	private CreateClientHierarchy_DL clientHierObj;
	int clientId, systemId, enterpriseId, practiceId, locationId;
	String locationName;
	Response createNewLocationApiResponse, associatePracticeToLocationResponse, getLocationByPracticeResponse;

	@BeforeClass
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		locationsvalidations = new LocationsValidations();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		locationId = addNewLocation();
	}

	public int addNewLocation() throws IOException {
		locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		String externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_DL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		return createNewLocationApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify fetching location by practice using valid locationId and practiceId")
	public void testGetLocationByPracticeFunctionality() throws NullPointerException, IOException {
		logStep("Enter locationId and practiceId to associate Practice to Location");
		associatePracticeToLocationResponse = sharedInfo.associateDeassociatePractice_DL(practiceId, locationId, true);
		logStep("Enter valid locationId and practiceId to get location by practice");
		getLocationByPracticeResponse = sharedInfo.getLocationByPractice_DL(locationId, practiceId);
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 200);
		locationsvalidations.verifyGetLocationByPracticeApiResponseBody(getLocationByPracticeResponse, locationName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify fetching location by practice using non-existing locationID and existing practiceId")
	public void testGetLocationByPracticeFunctionalityUsingNonExistingLocationId() throws IOException {
		logStep("Enter non-existing locationId and existing practiceId to get location by practice");
		getLocationByPracticeResponse = sharedInfo.getLocationByPractice_DL(
				Integer.parseInt(testData.getProperty("non.existing.location.id")), practiceId);
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 400);
		locationsvalidations.verifyErrorResponseForNonExistingId(getLocationByPracticeResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify fetching location by practice using existing locationID and non-existing practiceId")
	public void testGetLocationByPracticeFunctionalityUsingNonExistingPracticeId() throws IOException {
		logStep("Enter existing locationID and non-existing practiceId to get location by practice");
		getLocationByPracticeResponse = sharedInfo.getLocationByPractice_DL(locationId,
				Integer.parseInt(testData.getProperty("non.existing.practice.id")));
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 400);
		locationsvalidations.verifyErrorResponseForNonExistingId(getLocationByPracticeResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify fetching location by practice using non-existing locationID and non-existing practiceId")
	public void testGetLocationByPracticeFunctionalityUsingNonExistingPracticeIdLocationId() throws IOException {
		logStep("Enter existing locationID and non-existing practiceId to get location by practice");
		getLocationByPracticeResponse = sharedInfo.getLocationByPractice_DL(
				Integer.parseInt(testData.getProperty("non.existing.location.id")),
				Integer.parseInt(testData.getProperty("non.existing.practice.id")));
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 400);
		locationsvalidations.verifyErrorResponseForNonExistingId(getLocationByPracticeResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify fetching location by practice using locationID and practiceId as null values")
	public void testGetLocationByPracticeFunctionalityUsingNullValues() {
		logStep("Enter location ID and practiceId as null to get location by practice");
		getLocationByPracticeResponse = sharedInfo.getLocationByPracticeUsingNullValues_DL();
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 400);
	}
}
