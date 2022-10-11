// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
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

public class ToggleSolutionStatusForLocationAndPracticeApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	private LocationsValidations locationsValidations;
	private LocationsInfo locationsInfo;
	private Validations validation;
	Response createClientApiResponse, createSystemApiResponse, createPracticeApiResponse, updateSolutionsApiResponse,
			getSolutionsForLocation, createEnterpriseApiResponse, createNewLocationApiResponse,
			associateDeassociatePracticesForLocationApiResponse, toggleSolutionStatusApiResponse;
	int clientId, systemId, enterpriseId, practiceId, locationId;
	String clientName, clientSapNumber, externalId, firstName, lastName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		locationsInfo = new LocationsInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		locationsValidations = new LocationsValidations();

		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber, "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
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
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid practiceid and locationId with valid details in request body and verify response code")
	public void testResponseCodeToToggleSolutionStatusForLocationAndPractice()
			throws NullPointerException, IOException {
		logStep("Associate solution to practice");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = orgInfo.updateOrgSolutionsDetails_BL(solutionId, "New", practiceId);
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Toggle solution for Location and Practice");
		toggleSolutionStatusApiResponse = locationsInfo.toggleSolutionStatusApiResponse_BL(locationId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(toggleSolutionStatusApiResponse.getStatusCode(), 200);
		logStep("Verify associate solution for Location and Practice");
		getSolutionsForLocation = locationsInfo.getSolutionStatusApiResponse_BL(locationId, practiceId);
		CCUtils.assertEquals(getSolutionsForLocation.getStatusCode(), 200);
		locationsValidations.verifyNoOfSolutionsFetchedSolutionNameandId(getSolutionsForLocation);
		toggleSolutionStatusApiResponse = locationsInfo.toggleSolutionStatusApiResponse_BL(locationId, practiceId,
				solutionId, false);
		CCUtils.assertEquals(toggleSolutionStatusApiResponse.getStatusCode(), 200);
		getSolutionsForLocation = locationsInfo.getSolutionStatusApiResponse_BL(locationId, practiceId);
		CCUtils.assertEquals(getSolutionsForLocation.getStatusCode(), 204);
		logStep("Verify deassociate solution for Location and Practice");
		getSolutionsForLocation = locationsInfo.getSolutionStatusApiResponse_BL(locationId, practiceId);
		CCUtils.assertEquals(getSolutionsForLocation.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid practiceid and locationId with invalid details in request body and verify response code")
	public void testResponseCodeToToggleInvalidSolutionIdForLocationIdAndPracticeId()
			throws NullPointerException, IOException {
		int solutionId = Integer.parseInt(testData.getProperty("nonexisting.solutionId"));
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Toggle solution for Location and Practice");
		toggleSolutionStatusApiResponse = locationsInfo.toggleSolutionStatusApiResponse_BL(locationId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(toggleSolutionStatusApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForNonExistingInput(toggleSolutionStatusApiResponse, practiceId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting practiceid and existing locationId with valid details in request body and verify response code")
	public void testResponseCodeToToggleValidSolutionStatusForLocationIdAndNonExistingPracticeId()
			throws NullPointerException, IOException {
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		int practiceId = Integer.parseInt(testData.getProperty("api.nonexisting.practiceId"));
		logStep("Toggle solution for Location and Practice");
		toggleSolutionStatusApiResponse = locationsInfo.toggleSolutionStatusApiResponse_BL(locationId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(toggleSolutionStatusApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(toggleSolutionStatusApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceid and nonexisting locationId with valid details in request body and verify response code")
	public void testResponseCodeToToggleValidSolutionStatusForNonExistingLocationIdAndExistingPracticeId()
			throws NullPointerException, IOException {
		logStep("Associate solution to practice");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		int practiceId = Integer.parseInt(testData.getProperty("api.nonexisting.practiceId"));
		logStep("Toggle solution for Location and Practice");
		toggleSolutionStatusApiResponse = locationsInfo.toggleSolutionStatusApiResponse_BL(locationId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(toggleSolutionStatusApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(toggleSolutionStatusApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting practiceid and nonexisting locationId with invalid details in request body and verify response code")
	public void testResponseCodeToToggleInvalidSolutionStatusForNonExistingLocationIdAndNonExistingPracticeId()
			throws NullPointerException, IOException {

		int solutionId = Integer.parseInt(testData.getProperty("nonexisting.solutionId"));
		int practiceId = Integer.parseInt(testData.getProperty("api.nonexisting.practiceId"));
		int locationId = Integer.parseInt(testData.getProperty("api.nonexisting.locationId"));
		logStep("Toggle solution for Location and Practice");
		toggleSolutionStatusApiResponse = locationsInfo.toggleSolutionStatusApiResponse_BL(locationId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(toggleSolutionStatusApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(toggleSolutionStatusApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid practiceid and locationId with valid details in request body  for invalid url and verify response code")
	public void testResponseCodeToToggleSolutionStatusForLocationIdAndPracticeIdForInvalidUrl()
			throws NullPointerException, IOException {
		logStep("Associate solution to practice");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = orgInfo.updateOrgSolutionsDetails_BL(solutionId, "New", practiceId);
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Toggle solution for Location and Practice");
		toggleSolutionStatusApiResponse = locationsInfo.toggleSolutionStatusApiResponseInvalidUrl_BL(locationId,
				practiceId, solutionId, true);
		CCUtils.assertEquals(toggleSolutionStatusApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(toggleSolutionStatusApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid practiceid and locationId with valid details in request body for post method call instead of put method call and verify response code")
	public void testResponseCodeToToggleSolutionStatusForLocationIdAndPracticeIdForPostMethodCallInsteadOfPutMethodCall()
			throws NullPointerException, IOException {
		logStep("Associate solution to practice");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = orgInfo.updateOrgSolutionsDetails_BL(solutionId, "New", practiceId);
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Toggle solution for Location and Practice");
		toggleSolutionStatusApiResponse = locationsInfo.toggleSolutionStatusApiResponseInvalidMethodCall_BL(locationId,
				practiceId, solutionId, true);
		CCUtils.assertEquals(toggleSolutionStatusApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(toggleSolutionStatusApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter  null values in all fields and verify response code")
	public void testResponseCodeForNullInputInAllFields() throws NullPointerException, IOException {
		toggleSolutionStatusApiResponse = locationsInfo.toggleSolutionStatusApiResponseNullValue_BL();
		CCUtils.assertEquals(toggleSolutionStatusApiResponse.getStatusCode(), 400);
	}
}