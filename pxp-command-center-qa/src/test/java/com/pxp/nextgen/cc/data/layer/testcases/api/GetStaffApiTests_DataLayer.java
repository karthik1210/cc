// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import com.pxp.cc.model.BaseRest;
import io.restassured.response.Response;

public class GetStaffApiTests_DataLayer extends BaseRest {

	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_DL clientHierObj;
	Response createNewstaffApiResponse, getStaffDetailsApiResponse;
	int clientId, staffId;
	public String firstName, lastName, externalId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_DL();
		clientId = clientHierObj.createClient_DL();
		staffId = createStaff(clientId);
	}

	public int createStaff(int clientId) {
		logStep("Create new staff");
		firstName = testData.getProperty("staff.firstname").concat(CCUtils.generateAlphaNumericString(5));
		lastName = testData.getProperty("staff.lastname").concat(CCUtils.generateAlphaNumericString(5));
		externalId = CCUtils.generateAlphaNumericString(10);
		createNewstaffApiResponse = sharedInfo.createStaff_DL(clientId, firstName, lastName,
				testData.getProperty("staff.gender"), externalId);
		CCUtils.assertEquals(createNewstaffApiResponse.getStatusCode(), 200);
		return createNewstaffApiResponse.jsonPath().get("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get Staff details with corect client id and staff id")
	public void testGetStaffDetailsWithExistingClientIdAndStaffId() throws IOException {
		logStep("Enter existing client Id and Staff Id in get staff details");
		getStaffDetailsApiResponse = sharedInfo.getStaffDetailsOfClient_DL(clientId, staffId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStaffDetailsApiResponse);
		CCUtils.assertEquals(getStaffDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetStaffResponseBody(getStaffDetailsApiResponse, firstName, lastName, externalId,
				testData.getProperty("staff.gender"), true);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get Staff details with non-existing client id and existing staff id")
	public void testGetStaffDetailsWithNonExistingClientIdAndExistingStaffId() throws IOException {
		logStep("Enter non-existing client Id and existing Staff Id in get staff details");
		getStaffDetailsApiResponse = sharedInfo
				.getStaffDetailsOfClient_DL(Integer.parseInt(testData.getProperty("api.nonexisting.id")), staffId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStaffDetailsApiResponse);
		CCUtils.assertEquals(getStaffDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(getStaffDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get Staff details with existing client id and non-existing staff id")
	public void testGetStaffDetailsWithExistingClientIdAndNonExistingStaffId() throws IOException {
		logStep("Enter existing client Id and non-existing Staff Id in get staff details");
		getStaffDetailsApiResponse = sharedInfo.getStaffDetailsOfClient_DL(clientId,
				Integer.parseInt(testData.getProperty("api.nonexisting.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStaffDetailsApiResponse);
		CCUtils.assertEquals(getStaffDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(getStaffDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get Staff details with both non-existing client id and staff id")
	public void testGetStaffDetailsWithBothNonExistingClientIdAndStaffId() throws IOException {
		logStep("Enter both non-existing client Id and Staff Id in get staff details");
		getStaffDetailsApiResponse = sharedInfo.getStaffDetailsOfClient_DL(
				Integer.parseInt(testData.getProperty("api.nonexisting.id")),
				Integer.parseInt(testData.getProperty("api.nonexisting.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStaffDetailsApiResponse);
		CCUtils.assertEquals(getStaffDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(getStaffDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get Staff details with both null values in client id and staff id")
	public void testGetStaffDetailsWithBothNullValuesClientIdAndStaffId() throws IOException {
		logStep("Enter both null values in client Id and Staff Id in get staff details");
		getStaffDetailsApiResponse = sharedInfo.getStaffDetailsOfClient_NULL_DL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStaffDetailsApiResponse);
		CCUtils.assertEquals(getStaffDetailsApiResponse.getStatusCode(), 400);
	}
}
