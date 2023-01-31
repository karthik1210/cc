// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;

public class GetLocationByPracticeApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private CreateClientHierarchy_BL clientHierObj;
	int clientId, systemId, enterpriseId, practiceId, locationId;
	Response createNewLocationApiResponse, associatePracticeToLocationResponse, getLocationByPracticeResponse;

	@BeforeClass
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_BL();
		   
		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		locationId = addNewLocation();
	}

	public int addNewLocation() throws IOException {
		String locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		String externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_BL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		return createNewLocationApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify fetching location by practice using valid locationId and practiceId")
	public void testGetLocationByPracticeFunctionality() {
		logStep("Enter location Id and prcatice Id to associate Practice to Location");
		associatePracticeToLocationResponse = sharedInfo.associateDeassociatePractice_BL(practiceId, locationId, true);
		logStep("Enter valid location Id and practice Id to get location by practice");
		getLocationByPracticeResponse = sharedInfo.getLocationByPracticeUsingValidInputs(locationId, practiceId);
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify fetching location by practice using non-existing location ID and existing practiceId")
	public void testGetLocationByPracticeFunctionalityUsingNonExistingLocationId() {
		logStep("Enter non-existing location Id and existing practice Id to get location by practice");
		getLocationByPracticeResponse = sharedInfo.getLocationByPracticeUsingValidInputs(
				Integer.parseInt(testData.getProperty("non.existing.location.id")), practiceId);
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify fetching location by practice using existing location ID and non-existing practiceId")
	public void testGetLocationByPracticeFunctionalityUsingNonExistingPracticeId() {
		logStep("Enter existing location ID and non-existing practiceId to get location by practice");
		getLocationByPracticeResponse = sharedInfo.getLocationByPracticeUsingValidInputs(locationId,
				Integer.parseInt(testData.getProperty("non.existing.practice.id")));
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify fetching location by practice using location ID and practiceId as null values")
	public void testGetLocationByPracticeFunctionalityUsingNullValues() {
		logStep("Enter location ID and practiceId as null to get location by practice");
		getLocationByPracticeResponse = sharedInfo.getLocationByPracticeUsingNullValue_BL();
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify fetching location by practice using invalid URL")
	public void testGetLocationByPracticeFunctionalityUsingInvalidUrl() {
		logStep("Enter location Id and prcatice Id to associate Practice to Location");
		associatePracticeToLocationResponse = sharedInfo.associateDeassociatePractice_BL(practiceId, locationId, true);
		logStep("Enter valid location ID and practiceId to get location by practice using invalid URL");
		getLocationByPracticeResponse = sharedInfo.getLocationByPracticeUsingInvalidUrlPath(locationId, practiceId);
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 404);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify fetching location by practice using invalid HTTP method call")
	public void testGetLocationByPracticeFunctionalityUsingInvalidMethodCall() {
		logStep("Enter location Id and prcatice Id to associate Practice to Location");
		associatePracticeToLocationResponse = sharedInfo.associateDeassociatePractice_BL(practiceId, locationId, true);
		logStep("Enter valid location ID and practiceId to get location by practice using invalid HTTP method call");
		getLocationByPracticeResponse = sharedInfo.getLocationByPracticeUsingInvalidMethodCall(locationId, practiceId);
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 405);
	}
}
