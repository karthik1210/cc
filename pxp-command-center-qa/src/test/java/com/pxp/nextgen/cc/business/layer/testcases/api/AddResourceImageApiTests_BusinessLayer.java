//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.File;
import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.ResourceValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AddResourceImageApiTests_BusinessLayer extends BaseRest {
	private ResourceInfo resourceInfo;
	private ResourceValidations resourceValidation;
	private Validations validation;
	Response addResourceImageApiResponse, getResourceDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		resourceInfo = new ResourceInfo();
		resourceValidation = new ResourceValidations();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid resourceid with valid image format to add resource image and verify response code")
	public void testResponseCodeToAddResourceImageWithValidResourceId() throws NullPointerException, IOException {
		log("Enter valid resourceid with valid image format");
		String projectpath = System.getProperty("user.dir");
		File file = new File(projectpath + "\\src\\test\\resources\\data-driven\\images\\logo.png");
		int resourceId = Integer.parseInt(testData.getProperty("resource.id.one"));
		addResourceImageApiResponse = resourceInfo.addResourceImage_BL(resourceId, file);
		CCUtils.responseTimeValidation(addResourceImageApiResponse);
		CCUtils.assertEquals(addResourceImageApiResponse.getStatusCode(), 200);
		logStep("fetch resource details via correct resource id");
		getResourceDetailsApiResponse = resourceInfo.getResourceDetails_BL(resourceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 200);
		resourceValidation.verifyResponseBodyForExistingResourceId(getResourceDetailsApiResponse);
	}

	/*
	 * Enable test case once issue is addressed for shared service Jira # PXPP-20911
	 */ 
	/*
	 * @Test(retryAnalyzer = RetryAnalyzer.class, description =
	 * "Enter valid resourceid with invalid image format to add resource image and verify response code"
	 * ) public void testResponseCodeToAddResourceImageWithInvalidImageFormat()
	 * throws IOException { log("Enter valid resourceid with invalid image format");
	 * String projectpath = System.getProperty("user.dir"); File file = new
	 * File(projectpath +
	 * "\\src\\test\\resources\\data-driven\\images\\Upload.pdf"); int resourceId =
	 * Integer.parseInt(testData.getProperty("resource.id.one"));
	 * addResourceImageApiResponse = resourceInfo.addResourceImage_BL(resourceId,
	 * file); CCUtils.responseTimeValidation(addResourceImageApiResponse);
	 * CCUtils.assertEquals(addResourceImageApiResponse.getStatusCode(), 400); }
	 */

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing resourceid with valid image format to add resource image and verify response code")
	public void testResponseCodeToAddResourceImageWithNonExistingResourceId() throws IOException {
		log("Enter non-existing resourceid with valid image format");
		String projectpath = System.getProperty("user.dir");
		File file = new File(projectpath + "\\src\\test\\resources\\data-driven\\images\\logo.png");
		int nonExistingResourceId = Integer.parseInt(testData.getProperty("non.existing.resource.id"));
		addResourceImageApiResponse = resourceInfo.addResourceImage_BL(nonExistingResourceId, file);
		CCUtils.responseTimeValidation(addResourceImageApiResponse);
		CCUtils.assertEquals(addResourceImageApiResponse.getStatusCode(), 400);
		resourceValidation.verifyErrorMessageInResponseBodyForNonExistingResourceId(addResourceImageApiResponse,
				nonExistingResourceId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error message")
	public void testErrorResponseForInvalidUrl() throws IOException {
		log("Enter valid resourceid with valid image format");
		String projectpath = System.getProperty("user.dir");
		File file = new File(projectpath + "\\src\\test\\resources\\data-driven\\images\\logo.png");
		int resourceId = Integer.parseInt(testData.getProperty("resource.id.one"));
		addResourceImageApiResponse = resourceInfo.addResourceImageInvalidUrl_BL(resourceId, file);
		CCUtils.responseTimeValidation(addResourceImageApiResponse);
		CCUtils.assertEquals(addResourceImageApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(addResourceImageApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter PUT instead of POST and verify response code")
	public void testErrorMessageForInvalidMethodCall() throws IOException {
		log("Enter valid resourceid with valid image format");
		String projectpath = System.getProperty("user.dir");
		File file = new File(projectpath + "\\src\\test\\resources\\data-driven\\images\\logo.png");
		int resourceId = Integer.parseInt(testData.getProperty("resource.id.one"));
		addResourceImageApiResponse = resourceInfo.addResourceImageInvalidMethodCall_BL(resourceId, file);
		CCUtils.responseTimeValidation(addResourceImageApiResponse);
		CCUtils.assertEquals(addResourceImageApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(addResourceImageApiResponse);
	}
}
