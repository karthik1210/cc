// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetClientUserLoginDetailsApiTests_BusinessLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response fetchUserLoginDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error message for non existing userId")
	public void testErrorMessageForNonExistingUserId() throws IOException {
		logStep("Enter non existing user id");
		int userId = Integer.parseInt(testData.getProperty("nonexisting.user.id"));
		fetchUserLoginDetailsApiResponse = userInfo.getUserLogin(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserLoginDetailsApiResponse);
		CCUtils.assertEquals(fetchUserLoginDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForNonexistingUserIdField(fetchUserLoginDetailsApiResponse,
				testData.getProperty("nonexisting.user.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify status code & response body for existing Client userId")
	public void testSuccessMessageForExistingClientUserId() throws NullPointerException {
		logStep("Enter  existing Internal userId");
		int userId = Integer.parseInt(testData.getProperty("api.client.user.id"));
		fetchUserLoginDetailsApiResponse = userInfo.getUserLogin(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserLoginDetailsApiResponse);
		CCUtils.assertEquals(fetchUserLoginDetailsApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForLoginDetail(fetchUserLoginDetailsApiResponse,
				testData.getProperty("client.user.login.count"), testData.getProperty("client.user.login.attempts"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMassageForInvalidUrl() throws IOException {
		logStep("Enter  existing user id");
		int userId = Integer.parseInt(testData.getProperty("api.client.user.id"));
		fetchUserLoginDetailsApiResponse = userInfo.getUserLoginInvalidUrl(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserLoginDetailsApiResponse);
		CCUtils.assertEquals(fetchUserLoginDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchUserLoginDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of GET")
	public void testErrorMassageForInvalidMethodCall() throws IOException {
		logStep("Enter  existing user id");
		int userId = Integer.parseInt(testData.getProperty("api.client.user.id"));
		fetchUserLoginDetailsApiResponse = userInfo.invalidMethodCallGetUserLogin(userId);
    	logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserLoginDetailsApiResponse);
		CCUtils.assertEquals(fetchUserLoginDetailsApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(fetchUserLoginDetailsApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check response for null values in providerId field")
	public void testErrorResponseCodeForNullValues() throws IOException {
		fetchUserLoginDetailsApiResponse = userInfo.getUserLogin_NullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserLoginDetailsApiResponse);
		CCUtils.assertEquals(fetchUserLoginDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageIfClientIdIsNull(fetchUserLoginDetailsApiResponse);
	}
}
