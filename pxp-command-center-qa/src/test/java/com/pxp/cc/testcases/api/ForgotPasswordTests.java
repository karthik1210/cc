// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.PasswordPage;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class ForgotPasswordTests extends BaseRest {

	private Validations validation;
	private PasswordPage passwordPage;
	Response forgotPasswordApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilderWithoutAuthorization();
		passwordPage = new PasswordPage();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to verify user received password reset link when entered registered username for forgot password")
	public void testForgotPasswordFunctionalityWithRegisteredUser() throws IOException {
		forgotPasswordApiResponse = passwordPage.forgotPassword(testData.getProperty("password.username"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(forgotPasswordApiResponse);
		CCUtils.assertEquals(forgotPasswordApiResponse.getStatusCode(), 200);
		validation.verifySuccessMessageInResponseWhenUserIsRegistered(forgotPasswordApiResponse,
				testData.getProperty("password.username"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to verify forgot Password Functionality Without Entering Username")
	public void testUsernameFieldWithNullData() throws IOException {
		forgotPasswordApiResponse = passwordPage.forgotPassword("");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(forgotPasswordApiResponse);
		CCUtils.assertEquals(forgotPasswordApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseWhenUsernameFieldBlank(forgotPasswordApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to verify forgot Password Functionality when user enter only space in username field")
	public void testUsernameFieldWithSpace() throws IOException {
		forgotPasswordApiResponse = passwordPage.forgotPassword(" ");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(forgotPasswordApiResponse);
		CCUtils.assertEquals(forgotPasswordApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseWhenUsernameFieldIsInvalid(forgotPasswordApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to verify forgot Password Functionality With unregistered Username")
	public void testUsernameFieldWithUnregisteredUser() throws IOException {
		forgotPasswordApiResponse = passwordPage.forgotPassword("Test@123");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(forgotPasswordApiResponse);
		CCUtils.assertEquals(forgotPasswordApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseWhenUsernameIsUnregistered(forgotPasswordApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to verify forgot Password Functionality With external user")
	public void testUsernameFieldWithExternalUser() throws IOException {
		forgotPasswordApiResponse = passwordPage.forgotPassword("testuser");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(forgotPasswordApiResponse);
		CCUtils.assertEquals(forgotPasswordApiResponse.getStatusCode(), 200);
		validation.verifyMessageInResponseWhenUserIsExternal(forgotPasswordApiResponse);
	}

}
