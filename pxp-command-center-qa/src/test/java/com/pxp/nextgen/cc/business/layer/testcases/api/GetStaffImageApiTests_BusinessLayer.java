// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetStaffImageApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_BL clientHierObj;
	Response createNewstaffApiResponse, addStaffImageApiResponse, getStaffDetailsApiResponse,
	deleteStaffImageApiResponse ;
	int clientId,staffId;
  
	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientHierObj = new CreateClientHierarchy_BL();
		clientId = clientHierObj.createClient_BL();
		staffId=createStaff();	
	}
	
	public int createStaff() {
		logStep("Create new staff");
		String firstName = testData.getProperty("staff.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("staff.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createNewstaffApiResponse = sharedInfo.createStaff_BL(clientId, firstName, lastName,
				testData.getProperty("staff.gender"), externalId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewstaffApiResponse);
		CCUtils.assertEquals(createNewstaffApiResponse.getStatusCode(), 200);
	    return createNewstaffApiResponse.jsonPath().get("id");
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify Get Staff Image functionality")
	public void testGetStaffImageFunctionality() throws NullPointerException, IOException {
		log("Enter staffId with valid image format");
		URL url = ClassLoader.getSystemResource("data-driven/images/logo.png");
		File file = new File(url.getFile());
		String fileType = "image/png";
		addStaffImageApiResponse = sharedInfo.addStaffImage_BL(staffId, file, fileType);
		CCUtils.assertEquals(addStaffImageApiResponse.getStatusCode(), 200);
		logStep("fetch staff details via correct staff id");
		getStaffDetailsApiResponse = sharedInfo.getStaffDetails_BL(clientId,staffId);
	    logStep("Validating response information");
	    CCUtils.responseTimeValidation(getStaffDetailsApiResponse);
		CCUtils.assertEquals(getStaffDetailsApiResponse.getStatusCode(), 200);
        validation.verifyResponseBodyForExistingStaffId_BL(getStaffDetailsApiResponse);
        Object imageId = getStaffDetailsApiResponse.jsonPath().get("imageUrl");
        getStaffDetailsApiResponse = sharedInfo.getStaffImageDetails_BL(imageId);
	    logStep("Validating response information");
	    CCUtils.responseTimeValidation(getStaffDetailsApiResponse);
		CCUtils.assertEquals(getStaffDetailsApiResponse.getStatusCode(), 200);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid imageId and verify response body")
	public void testResponseCodeToGetStaffImageUrlWithInvalidImageId() throws NullPointerException, Exception {
		log("Get Staff Image Url by entering invalid imageId");
		 getStaffDetailsApiResponse = sharedInfo.getStaffImageDetails_BL("api.href");
		 logStep("Validating response information");
		 CCUtils.responseTimeValidation(getStaffDetailsApiResponse);
		 CCUtils.assertEquals(getStaffDetailsApiResponse.getStatusCode(), 400);
		 validation.verifyErrorMessageForInvalidStaffUrl(getStaffDetailsApiResponse);		
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values and verify error message")
	public void testErrorResponseCodeToGetStaffImageUrlWithNullValue() throws NullPointerException, Exception {
		log("Get Staff Image Url by entering imageId as null");
		 getStaffDetailsApiResponse = sharedInfo.getStaffImageDetails_Null_Values_BL();
		 logStep("Validating response information");
		 CCUtils.responseTimeValidation(getStaffDetailsApiResponse);
		 CCUtils.assertEquals(getStaffDetailsApiResponse.getStatusCode(), 400);
	}
}
