// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetVarResellerUserLoginDetailsApiTests_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response fetchUserLoginDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing VarReseller userID and validate status code")
	public void testSuccessResponseCodeForExistingVarResellerUserId() throws NullPointerException {
		logStep("Enter existing VarReseller userId");
		int userId = Integer.parseInt(testData.getProperty("existing.var.reseller.userid"));
		fetchUserLoginDetailsApiResponse = userInfo.getUserlogin_DL(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserLoginDetailsApiResponse);
		CCUtils.assertEquals(fetchUserLoginDetailsApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForLoginDetail(fetchUserLoginDetailsApiResponse,
				testData.getProperty("login.count"), testData.getProperty("login.attempts"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing VarReseller userID and validate status code")
	public void testErrorResponseCodeForNonExistingVarResellerUserId() throws IOException {
		logStep("Enter non existing VarReseller userId");
		int userId = Integer.parseInt(testData.getProperty("nonexisting.user.id"));
		fetchUserLoginDetailsApiResponse = userInfo.getUserlogin_DL(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserLoginDetailsApiResponse);
		CCUtils.assertEquals(fetchUserLoginDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForNonexistingUserIdField(fetchUserLoginDetailsApiResponse,
				testData.getProperty("nonexisting.user.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		logStep("Enter existing VarReseller userId");
		int userId = Integer.parseInt(testData.getProperty("existing.var.reseller.userid"));
		fetchUserLoginDetailsApiResponse = userInfo.getUserloginInvalidUrl_DL(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserLoginDetailsApiResponse);
		CCUtils.assertEquals(fetchUserLoginDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchUserLoginDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of GET")
	public void testErrorForInvalidMethodCall() throws IOException {
		logStep("Enter existing VarReseller userId");
		int userId = Integer.parseInt(testData.getProperty("existing.var.reseller.userid"));
		fetchUserLoginDetailsApiResponse = userInfo.invalidMethodCallGetUserlogin_DL(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserLoginDetailsApiResponse);
		CCUtils.assertEquals(fetchUserLoginDetailsApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(fetchUserLoginDetailsApiResponse);
	}
}
