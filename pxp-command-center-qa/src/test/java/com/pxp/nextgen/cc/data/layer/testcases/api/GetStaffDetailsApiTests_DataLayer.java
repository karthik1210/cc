// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetStaffDetailsApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_DL clientHierObj;
	Response createNewStaffApiResponse, getStaffDetailsApiResponse;
	int clientId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing StaffId to get staff details and verify response body")
	public void testToGetStaffDetailsForExistingStaffId() throws IOException {
		logStep("Create new staff");
		String firstName = testData.getProperty("staff.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("staff.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createNewStaffApiResponse = sharedInfo.createStaff_DL(clientId, firstName, lastName,
				testData.getProperty("staff.gender"), externalId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewStaffApiResponse);
		CCUtils.assertEquals(createNewStaffApiResponse.getStatusCode(), 200);
		int staffId= createNewStaffApiResponse.jsonPath().getInt("id");
		logStep("Enter existing StaffId to get staff details and verify response body");
		getStaffDetailsApiResponse=sharedInfo.getStaff_DL(staffId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStaffDetailsApiResponse);
		CCUtils.assertEquals(getStaffDetailsApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForStaffDetails(getStaffDetailsApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing StaffId and verify response body")
	public void testErrorMessageForNonExistingStaffId() throws IOException {
		logStep("Enter non-existing StaffId and verify response body");
		int staffId= Integer.parseInt(testData.getProperty("api.nonexisting.id"));
		getStaffDetailsApiResponse=sharedInfo.getStaff_DL(staffId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStaffDetailsApiResponse);
		CCUtils.assertEquals(getStaffDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyInvalidStaffId(getStaffDetailsApiResponse,staffId);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value in StaffId field and verify response code")
	public void testErrorMessageForNullStaffId() throws IOException {
		logStep("Enter null value in StaffId field and verify response code");
		getStaffDetailsApiResponse=sharedInfo.getStaff_DL_Null_value();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStaffDetailsApiResponse);
		CCUtils.assertEquals(getStaffDetailsApiResponse.getStatusCode(), 400);
	}
}
