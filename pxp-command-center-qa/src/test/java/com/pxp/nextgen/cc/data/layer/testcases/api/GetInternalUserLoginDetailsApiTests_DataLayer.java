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

public class GetInternalUserLoginDetailsApiTests_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response fetchInternalUserLoginDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing Internal userID and validate status code")
	public void testSuccessResponseCodeForExistingInternalUserId() throws NullPointerException {
		logStep("Enter existing Internal userId");
		int userId = Integer.parseInt(testData.getProperty("existing.internal.userId"));
		fetchInternalUserLoginDetailsApiResponse = userInfo.getInternalUserlogin(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchInternalUserLoginDetailsApiResponse);
		CCUtils.assertEquals(fetchInternalUserLoginDetailsApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForLoginDetail(fetchInternalUserLoginDetailsApiResponse,
				testData.getProperty("api.login.count"), testData.getProperty("api.login.attempts"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing Internal userID and validate status code")
	public void testErrorResponseCodeForNonExistingInternalUserId() throws IOException {
		logStep("Enter non existing user id");
		int userId = Integer.parseInt(testData.getProperty("non.existing.user.id"));
		fetchInternalUserLoginDetailsApiResponse = userInfo.getInternalUserlogin(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchInternalUserLoginDetailsApiResponse);
		CCUtils.assertEquals(fetchInternalUserLoginDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForNonexistingUserIdField(fetchInternalUserLoginDetailsApiResponse,
				testData.getProperty("non.existing.user.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		logStep("Enter existing user id");
		int userId = Integer.parseInt(testData.getProperty("existing.internal.userId"));
		fetchInternalUserLoginDetailsApiResponse = userInfo.getInternalUserloginInvalidUrl(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchInternalUserLoginDetailsApiResponse);
		CCUtils.assertEquals(fetchInternalUserLoginDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchInternalUserLoginDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of GET")
	public void testErrorForInvalidMethodCall() throws IOException {
		logStep("Enter existing user id");
		int userId = Integer.parseInt(testData.getProperty("existing.internal.userId"));
		fetchInternalUserLoginDetailsApiResponse = userInfo.invalidMethodCallGetInternalUserlogin(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchInternalUserLoginDetailsApiResponse);
		CCUtils.assertEquals(fetchInternalUserLoginDetailsApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(fetchInternalUserLoginDetailsApiResponse);
	}
}