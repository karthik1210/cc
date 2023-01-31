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

public class DeleteStaffImageApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_DL clientHierObj;
	Response createNewstaffApiResponse, addStaffImageUrlApiResponse, getStaffDetailsApiResponse,
	deleteStaffImageApiResponse ;
	int clientId,staffId;
	
	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		logStep("Create new staff");
		String firstName = testData.getProperty("staff.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("staff.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createNewstaffApiResponse = sharedInfo.createStaff_DL(clientId, firstName, lastName,
				testData.getProperty("staff.gender"), externalId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewstaffApiResponse);
		CCUtils.assertEquals(createNewstaffApiResponse.getStatusCode(), 200);
	    staffId=createNewstaffApiResponse.jsonPath().get("id");
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify delete Staff Url functionality")
	public void testDeleteImageUrlFunctionality() throws Exception {
		logStep("Add Staff image url");
		addStaffImageUrlApiResponse = sharedInfo.addStaffImage_DL(
				staffId, testData.getProperty("imageurl"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addStaffImageUrlApiResponse);
		CCUtils.assertEquals(addStaffImageUrlApiResponse.getStatusCode(), 200);
		logStep("Delete Staff image url");
		deleteStaffImageApiResponse = sharedInfo.deleteStaffImage_DL(staffId);
		CCUtils.responseTimeValidation(deleteStaffImageApiResponse);
		CCUtils.assertEquals(deleteStaffImageApiResponse.getStatusCode(), 200);
		logStep("Get staff ImageUrl Details");
		getStaffDetailsApiResponse = sharedInfo
				.getStaffDetails_DL(staffId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(	getStaffDetailsApiResponse);
		CCUtils.assertEquals(getStaffDetailsApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForExistingStaffd(getStaffDetailsApiResponse);	
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid StaffId and verify response body ")
	public void testErrorResponseForInvalidStaffId() throws Exception {
		logStep("Enter invalid staff id");
		int nonExistingStaffId = Integer.parseInt(testData.getProperty("api.nonexisting.id"));
		deleteStaffImageApiResponse = sharedInfo.deleteStaffImage_DL(nonExistingStaffId);
		CCUtils.responseTimeValidation(deleteStaffImageApiResponse);
		CCUtils.assertEquals(deleteStaffImageApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseBodyForNonExistingStaffId(deleteStaffImageApiResponse, nonExistingStaffId);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values and verify error message")
	public void testErrorMessageForNullValues() throws IOException {
		log("Enter null values");
		deleteStaffImageApiResponse = sharedInfo.deleteStaffImage_NullValues_DL();
		CCUtils.responseTimeValidation(deleteStaffImageApiResponse);
		CCUtils.assertEquals(deleteStaffImageApiResponse.getStatusCode(), 400);
	}
}