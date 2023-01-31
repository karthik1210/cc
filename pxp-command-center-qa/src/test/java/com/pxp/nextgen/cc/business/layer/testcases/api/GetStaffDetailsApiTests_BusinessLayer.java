//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetStaffDetailsApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_BL clientHierObj;
	Response createNewStaffApiResponse, createClientApiResponse, getStaffDetailsApiResponse;
	int clientId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing StaffId to get staff details and verify response body")
	public void testToGetStaffDetailsForExistingStaffId() throws IOException {
		logStep("Create new staff");
		String firstName = testData.getProperty("staff.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("staff.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createNewStaffApiResponse = sharedInfo.createStaff_BL(clientId, firstName, lastName,
				testData.getProperty("staff.gender"), externalId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewStaffApiResponse);
		CCUtils.assertEquals(createNewStaffApiResponse.getStatusCode(), 200);
		int staffId= createNewStaffApiResponse.jsonPath().getInt("id");
		logStep("Enter existing StaffId to get staff details and verify response body");
		getStaffDetailsApiResponse=sharedInfo.getStaffDetails_BL(clientId, staffId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStaffDetailsApiResponse);
		CCUtils.assertEquals(getStaffDetailsApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForStaffDetails(getStaffDetailsApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing StaffId and verify response body")
	public void testErrorMessageForNonExistingStaffId() throws IOException {
		logStep("Enter non-existing StaffId and verify response body");
		int staffId= Integer.parseInt(testData.getProperty("api.nonexisting.id"));
		getStaffDetailsApiResponse=sharedInfo.getStaffDetails_BL(clientId,staffId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStaffDetailsApiResponse);
		CCUtils.assertEquals(getStaffDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(getStaffDetailsApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value in StaffId field and verify response code")
	public void testErrorMessageForNullStaffId() throws IOException {
		logStep("Enter null value in StaffId field and verify response code");
		getStaffDetailsApiResponse=sharedInfo.getStaffDetails_BL_Null_value();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStaffDetailsApiResponse);
		CCUtils.assertEquals(getStaffDetailsApiResponse.getStatusCode(), 400);
	}
}
