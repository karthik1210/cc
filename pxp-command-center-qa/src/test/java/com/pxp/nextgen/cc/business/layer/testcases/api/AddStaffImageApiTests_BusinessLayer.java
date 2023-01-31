//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AddStaffImageApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_BL clientHierObj;
	Response createNewstaffApiResponse, addStaffImageApiResponse,getStaffDetailsApiResponse;
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
	
	@DataProvider(name = "getImage")
	public Object[] getImageFile() {
		Object[][] obj = new Object[][] { { "logo.png" }, { "TestImage_GIF_Format.gif" },
				{ "TestImage_JPEG_Format.jpeg" }, { "TestImage_JPG_Format.jpg" }, { "TestImage_RAW_Format.raw" },
				{ "TestImage_TIF_Format.tif" }, { "TestImage_TIFF_Format.tiff" } };
		return obj;
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getImage", description = "Add Staff image with valid staffId and verify response code")
	public void testResponseCodeToAddStaffImageWithValidStaffId(String getImageValue) throws NullPointerException, IOException {
		logStep("Enter valid staffId with valid image format");
		URL url = ClassLoader.getSystemResource("data-driven/images/" + getImageValue);
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
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid staffid with invalid image format to add staff image and verify response code")
	public void testResponseCodeToAddStaffImageWithInvalidImageFormat() throws IOException {
		log("Enter valid resourceid with invalid image format");
		URL url = ClassLoader.getSystemResource("data-driven/images/Upload.pdf");
		File file = new File(url.getFile());
		String fileType = "application/pdf";
		addStaffImageApiResponse = sharedInfo.addStaffImage_BL(staffId, file, fileType);
		CCUtils.responseTimeValidation(addStaffImageApiResponse);
		CCUtils.assertEquals(addStaffImageApiResponse.getStatusCode(), 400);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing staffid with valid image format to add staff image and verify response code")
	public void testResponseCodeToAddStaffImageWithNonExistingStaffId() throws IOException {
		log("Enter non-existing staffid with valid image format");
		int nonExistingStaffId = Integer.parseInt(testData.getProperty("api.nonexisting.id"));
		URL url = ClassLoader.getSystemResource("data-driven/images/logo.png");
		File file = new File(url.getFile());
		String fileType = "image/png";
		addStaffImageApiResponse = sharedInfo.addStaffImage_BL(nonExistingStaffId, file, fileType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addStaffImageApiResponse);
		CCUtils.assertEquals(addStaffImageApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseBodyForNonExistingStaffId(addStaffImageApiResponse, nonExistingStaffId);	
	}
}