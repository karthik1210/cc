// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.PasswordPage;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class ResetPasswordTests extends BaseRest {
	private Validations validation;
	private PasswordPage passwordPage;
	Response resetPasswordApiResponse, forgotPasswordApiResponse;	

	@BeforeTest()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilderWithoutAuthorization();
		passwordPage = new PasswordPage();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to verify registered user is able to reset password with valid token")
	public void testSuccessfullyPasswordResetFunctionalityWithRegisteredUser()
			throws Exception {
		forgotPasswordApiResponse = passwordPage.forgotPassword(testData.getProperty("reset.password.email.mailinator"));
		logStep("Verify user click on forgot password and entered registred username to get the password reset link");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(forgotPasswordApiResponse);
		CCUtils.assertEquals(forgotPasswordApiResponse.getStatusCode(), 200);
		validation.verifySuccessMessageInResponseWhenUserIsRegistered(forgotPasswordApiResponse, testData.getProperty("reset.password.email.mailinator"));
		String url = passwordPage.urlInEmail(testData.getProperty("reset.password.email.mailinator"),
				testData.getProperty("reset.password.email.mailinator").concat(CCUtils.generateRandomString()));
		resetPasswordApiResponse = passwordPage.resetPasswordFunctionality(
				testData.getProperty("reset.password.set").concat(CCUtils.generateRandomString()), passwordPage.getTokenValueFromEmailLink(url));
		logStep("Validating email received for reset password and verifying success message");
		validation.verifySuccessMessageInResponseForValidToken(resetPasswordApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to verify Password reset Functionality with invalid token")
	public void testPasswordResetFunctionalityWithInvalidToken() throws IOException {
		resetPasswordApiResponse = passwordPage.resetPasswordFunctionality(
				testData.getProperty("reset.password.set").concat(CCUtils.generateRandomString()),
				testData.getProperty("reset.password.token"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(resetPasswordApiResponse);
		CCUtils.assertEquals(resetPasswordApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidToken(resetPasswordApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to verify Password reset Functionality with space in password")
	public void testPasswordResetFunctionalityWithSpaceInPassword()
			throws Exception {
		resetPasswordApiResponse = passwordPage.resetPasswordFunctionality(testData.getProperty("reset.password.space.in.password").concat(CCUtils.generateRandomString()),
				testData.getProperty("reset.password.token"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(resetPasswordApiResponse);
		CCUtils.assertEquals(resetPasswordApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseWhenSpaceInPassword(resetPasswordApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to verify Password reset Functionality with incorrect password")
	public void testInvalidPasswordForPasswordResetFunctionality()
			throws Exception {
		resetPasswordApiResponse = passwordPage.resetPasswordFunctionality(testData.getProperty("reset.password.with.invalid.password"),
				testData.getProperty("reset.password.token"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(resetPasswordApiResponse);
		CCUtils.assertEquals(resetPasswordApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseWhenEnteredInvalidPassword(resetPasswordApiResponse);
	}
	
}

