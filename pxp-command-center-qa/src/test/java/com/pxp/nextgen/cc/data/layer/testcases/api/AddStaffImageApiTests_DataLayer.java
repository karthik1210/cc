// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AddStaffImageApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private ClientInfo clientInfo;
	Response createNewstaffApiResponse,createClientApiResponse,addStaffImageUrlApiResponse,getStaffDetailsApiResponse,deleteStaffImageApiResponse ;
	int clientId,staffId;
	
	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		logStep("Create client using valid details in client name, sap number, active, testclient, client type and timezone field");
		String clientName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5))));
		String clientSapNumber = CCUtils
				.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.client.sapnumber")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5))));
		String testFlag = testData.getProperty("api.client.testclient");
		String clientTimezoneId = testData.getProperty("api.client.timezoneid");
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testFlag, Integer.parseInt(testData.getProperty("api.client.type")),
				clientTimezoneId, testData.getProperty("ehr.identifier"), testData.getProperty("pm.identifier"));
		clientId = createClientApiResponse.jsonPath().get("clientId");
	
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
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify add Staff Url functionality")
	public void testAddImageUrlFunctionality() throws Exception {
		logStep("Add Staff image url");
		addStaffImageUrlApiResponse = sharedInfo.addStaffImage_DL(
				staffId, testData.getProperty("imageurl"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addStaffImageUrlApiResponse);
		CCUtils.assertEquals(addStaffImageUrlApiResponse.getStatusCode(), 200);
		logStep("Get staff ImageUrl Details");
		getStaffDetailsApiResponse = sharedInfo
				.getStaffDetails_DL(staffId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(	getStaffDetailsApiResponse);
		CCUtils.assertEquals(getStaffDetailsApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForExistingStaffd(getStaffDetailsApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing staffid with valid image format to add staff image and verify response code")
	public void testResponseCodeToAddStaffImageWithNonExistingStaffId() throws IOException {
		log("Enter non-existing staffid with valid image format");
		int nonExistingStaffId = Integer.parseInt(testData.getProperty("api.nonexisting.id"));
		addStaffImageUrlApiResponse = sharedInfo.addStaffImage_DL(
				nonExistingStaffId, testData.getProperty("imageurl"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addStaffImageUrlApiResponse);
		CCUtils.assertEquals(addStaffImageUrlApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseBodyForNonExistingStaffId(addStaffImageUrlApiResponse, nonExistingStaffId);
	}
	 
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value in image url and verify response body ")
	public void testErrorResponseForBlankSpace() throws Exception {
		logStep("Enter blank value in imageUrl field");
		addStaffImageUrlApiResponse = sharedInfo.addStaffImage_DL(
				staffId,"");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addStaffImageUrlApiResponse);
		CCUtils.assertEquals(addStaffImageUrlApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForImageUrlField(addStaffImageUrlApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter empty payload and verify response body ")
	public void testErrorResponseForEmptyPayload() throws Exception {
		logStep("Enter empty payload");
		addStaffImageUrlApiResponse = sharedInfo
				.addStaffImageWithEmptyPayload_DL(staffId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addStaffImageUrlApiResponse);
		CCUtils.assertEquals(addStaffImageUrlApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForImageUrlField(addStaffImageUrlApiResponse);
	}	
}