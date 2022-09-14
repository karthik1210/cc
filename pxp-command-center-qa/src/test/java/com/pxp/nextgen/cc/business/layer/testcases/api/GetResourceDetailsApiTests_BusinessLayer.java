// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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

public class GetResourceDetailsApiTests_BusinessLayer extends BaseRest {
	private ResourceInfo resourceInfo;
	private ResourceValidations resourceValidation;
	private Validations validations;
	Response getResourceDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		resourceInfo = new ResourceInfo();
		resourceValidation = new ResourceValidations();
		validations = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get resource details by entering correct resource id")
	public void testGetResourceDetails() throws IOException {
		logStep("fetch resource details via correct resource id");
		getResourceDetailsApiResponse = resourceInfo
				.getResourceDetails_BL(Integer.parseInt(testData.getProperty("resource.id.one")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 200);
		resourceValidation.verifyResponseBodyForExistingResourceId(getResourceDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get resource details error message by entering non-existing resource id")
	public void testGetResourceDetailsForNonExistingResourceId() throws IOException {
		logStep("fetch resource details error message via non-existing resource id");
		getResourceDetailsApiResponse = resourceInfo
				.getResourceDetails_BL(Integer.parseInt(testData.getProperty("non.existing.resource.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 400);
		resourceValidation.verifyErrorMessageInResponseBodyForNonExistingResourceId(getResourceDetailsApiResponse,
				Integer.parseInt(testData.getProperty("non.existing.resource.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get resource details error message by entering null resource id")
	public void testGetResourceDetailsForNullResourceId() throws IOException {
		logStep("fetch resource details error message via non-existing resource id");
		getResourceDetailsApiResponse = resourceInfo.getResourceDetails_BL_NULL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 400);
		validations.verifyResponseForEmptyOrNULLOrMisFormatData_DL(getResourceDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get resource details error message by entering correct resource id with invalid url")
	public void testGetResourceDetailsForesourceIdForInvalidUrl() throws IOException {
		logStep("fetch resource details error message via non-existing resource id");
		getResourceDetailsApiResponse = resourceInfo
				.getResourceDetails_BL_INVALID_URL(Integer.parseInt(testData.getProperty("resource.id.one")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 404);
		validations.verifyErrorInResponseForInvalidUrl(getResourceDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get resource details error message by entering correct resource id with invalid url")
	public void testGetResourceDetailsForesourceIdForInvalidMethodCall() throws IOException {
		logStep("fetch resource details error message via non-existing resource id");
		getResourceDetailsApiResponse = resourceInfo
				.getResourceDetails_BL_INVALID_METHOD_CALL(Integer.parseInt(testData.getProperty("resource.id.one")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 405);
		validations.verifyErrorMessageInResponseForInvalidMethodCall(getResourceDetailsApiResponse);
	}
}
