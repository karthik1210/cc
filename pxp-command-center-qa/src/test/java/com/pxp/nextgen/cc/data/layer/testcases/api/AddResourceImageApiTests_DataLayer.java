//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.SharedServiceApiValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AddResourceImageApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	Response getResourceDetailsApiResponse, deleteResourceImageApiResponse, addResourceImageUrlApiResponse;
	private SharedServiceApiValidations sharedServiceApiValidations;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		sharedServiceApiValidations = new SharedServiceApiValidations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify add resource Url functionality")
	public void testAddImageUrlFunctionality() throws Exception {
		logStep("Get resource ImageUrl Details");
		getResourceDetailsApiResponse = sharedInfo
				.getResourceDetails_DL(Integer.parseInt(testData.getProperty("valid.resource.id.two")));
		logStep("Verify imageurl is not present for selected resource");
		Boolean value = sharedServiceApiValidations.validateInageUrlValueIsNull(getResourceDetailsApiResponse);
		if (value.equals(false)) {
			logStep("delete resource imageurl");
			deleteResourceImageApiResponse = sharedInfo
					.deleteResourceImage_DL(Integer.parseInt(testData.getProperty("valid.resource.id.two")));
			logStep("Resource image url is deleted");
			getResourceDetailsApiResponse = sharedInfo
					.getResourceDetails_DL(Integer.parseInt(testData.getProperty("valid.resource.id.two")));
			CCUtils.assertTrue(sharedServiceApiValidations.validateInageUrlValueIsNull(getResourceDetailsApiResponse),
					"imageUrl is present");
		}
		logStep("Add Resouce image url");
		addResourceImageUrlApiResponse = sharedInfo.addResourceImage_DL(
				Integer.parseInt(testData.getProperty("valid.resource.id.two")), testData.getProperty("imageurl"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceImageUrlApiResponse);
		CCUtils.assertEquals(addResourceImageUrlApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid resourceId and verify response body ")
	public void testErrorResponseForInvalidResourceId() throws Exception {
		logStep("Enter invalid resource id");
		addResourceImageUrlApiResponse = sharedInfo.addResourceImage_DL(
				Integer.parseInt(testData.getProperty("non.existing.resource.id")), testData.getProperty("imageurl"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceImageUrlApiResponse);
		CCUtils.assertEquals(addResourceImageUrlApiResponse.getStatusCode(), 400);
		sharedServiceApiValidations.verifyErrorResponseForInvalidResourceId(addResourceImageUrlApiResponse,
				Integer.parseInt(testData.getProperty("non.existing.resource.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value in image url and verify response body ")
	public void testErrorResponseForBlankSpace() throws Exception {
		logStep("Enter blank value in imageUrl field");
		addResourceImageUrlApiResponse = sharedInfo
				.addResourceImage_DL(Integer.parseInt(testData.getProperty("valid.resource.id.two")), "");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceImageUrlApiResponse);
		CCUtils.assertEquals(addResourceImageUrlApiResponse.getStatusCode(), 400);
		sharedServiceApiValidations.verifyErrorResponseForImageUrlField(addResourceImageUrlApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter empty payload and verify response body ")
	public void testErrorResponseForEmptyPayload() throws Exception {
		logStep("Enter empty payload");
		addResourceImageUrlApiResponse = sharedInfo
				.addResourceImageWithEmptyPayload_DL(Integer.parseInt(testData.getProperty("valid.resource.id.two")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceImageUrlApiResponse);
		CCUtils.assertEquals(addResourceImageUrlApiResponse.getStatusCode(), 400);
		sharedServiceApiValidations.verifyErrorResponseForImageUrlField(addResourceImageUrlApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if POST is used instead of PATCH")
	public void testErrorForInvalidMethodCall() throws IOException {
		addResourceImageUrlApiResponse = sharedInfo.addResourceImageUsingInvalidMethodCall_DL(
				Integer.parseInt(testData.getProperty("valid.resource.id.two")), testData.getProperty("imageurl"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceImageUrlApiResponse);
		CCUtils.assertEquals(addResourceImageUrlApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(addResourceImageUrlApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorForInvalidUrl() throws IOException {
		addResourceImageUrlApiResponse = sharedInfo.addResourceImageUsingInvalidUrl_DL(
				Integer.parseInt(testData.getProperty("valid.resource.id.two")), testData.getProperty("imageurl"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceImageUrlApiResponse);
		CCUtils.assertEquals(addResourceImageUrlApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(addResourceImageUrlApiResponse);
	}
}
