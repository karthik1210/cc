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

public class GetVarResellerUserLoginDetailsApiTests_BusinessLayer extends BaseRest {
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
	public void testErrorMessageNonExistingUserId() throws IOException {
		logStep("Enter non existing user id");
		int userId = Integer.parseInt(testData.getProperty("nonexisting.user.id"));
		fetchUserLoginDetailsApiResponse = userInfo.getUserlogin(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserLoginDetailsApiResponse);
		CCUtils.assertEquals(fetchUserLoginDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForNonexistingUserIdField(fetchUserLoginDetailsApiResponse,
				testData.getProperty("nonexisting.user.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify status code & responce body for existing VarReseller userId")
	public void testErrorMessageExistingVarResellerUserIdForUserIdField() throws NullPointerException {
		logStep("Enter  existing Internal userId");
		int userId = Integer.parseInt(testData.getProperty("api.VarReseller.userId"));
		fetchUserLoginDetailsApiResponse = userInfo.getUserlogin(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserLoginDetailsApiResponse);
		CCUtils.assertEquals(fetchUserLoginDetailsApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForLoginDetail(fetchUserLoginDetailsApiResponse,
				testData.getProperty("api.login.count"), testData.getProperty("api.login.attempts"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMassageForUserDetailsUsingInvalidUrl() throws IOException {
		logStep("Enter  existing user id");
		int userId = Integer.parseInt(testData.getProperty("api.VarReseller.userId"));
		fetchUserLoginDetailsApiResponse = userInfo.getUserloginInvalidUrl(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserLoginDetailsApiResponse);
		CCUtils.assertEquals(fetchUserLoginDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchUserLoginDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of GET")
	public void testErrorForInvalidMethodCall() throws IOException {
		logStep("Enter  existing user id");
		int userId = Integer.parseInt(testData.getProperty("api.VarReseller.userId"));
		fetchUserLoginDetailsApiResponse = userInfo.invalidMethodCallGetUserlogin(userId);
    	logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserLoginDetailsApiResponse);
		CCUtils.assertEquals(fetchUserLoginDetailsApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(fetchUserLoginDetailsApiResponse);
	}

}