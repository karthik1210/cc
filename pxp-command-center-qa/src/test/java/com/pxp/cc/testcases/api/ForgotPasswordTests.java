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

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ForgotPasswordTests extends BaseRest{
	
	private Validations validation;
	private PasswordPage passwordPage;
	Response forgotPasswordApiResponse, resetPasswordApiResponse;
	
	@BeforeTest()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilderPassword();
		passwordPage = new PasswordPage();
		validation = new Validations();
	}
	
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to verify user received password reset link when entered registered username for forgot password")
	public void testForgotPasswordFunctionalityWithRegisteredUser() throws IOException {
		RestAssured.baseURI = testData.getProperty("password.baseurl");		
		forgotPasswordApiResponse = passwordPage.password("qausernew");	
		logStep("Validating response information");
		CCUtils.responseTimeValidation(forgotPasswordApiResponse);
		CCUtils.assertEquals(forgotPasswordApiResponse.getStatusCode(), 200);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to verify forgot Password Functionality Without Entering Username")
	public void testUsernameFieldWithNullData() throws IOException {
		RestAssured.baseURI = testData.getProperty("password.baseurl");	
		forgotPasswordApiResponse = passwordPage.password("");	
		logStep("Validating response information");
		CCUtils.responseTimeValidation(forgotPasswordApiResponse);
		CCUtils.assertEquals(forgotPasswordApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseWhenUsernameFieldBlank(forgotPasswordApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to verify forgot Password Functionality when user enter only space in username field")
	public void testUsernameFieldWithSpace() throws IOException {
		RestAssured.baseURI = testData.getProperty("password.baseurl");	
		forgotPasswordApiResponse = passwordPage.password(" ");	
		logStep("Validating response information");
		CCUtils.responseTimeValidation(forgotPasswordApiResponse);
		CCUtils.assertEquals(forgotPasswordApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseWhenUsernameFieldIsInvalid(forgotPasswordApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to verify forgot Password Functionality Without Entering Username")
	public void testUsernameFieldWithUnregisteredUser() throws IOException {
		RestAssured.baseURI = testData.getProperty("password.baseurl");	
		forgotPasswordApiResponse = passwordPage.password("Test@123");	
		logStep("Validating response information");
		CCUtils.responseTimeValidation(forgotPasswordApiResponse);
		CCUtils.assertEquals(forgotPasswordApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseWhenUsernameIsUnregistered(forgotPasswordApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to verify forgot Password Functionality With external user")
	public void testUsernameFieldWithExternalUser() throws IOException {
		RestAssured.baseURI = testData.getProperty("password.baseurl");	
		forgotPasswordApiResponse = passwordPage.password("testuser");	
		logStep("Validating response information");
		CCUtils.responseTimeValidation(forgotPasswordApiResponse);
		CCUtils.assertEquals(forgotPasswordApiResponse.getStatusCode(), 200);
		validation.verifyMessageInResponseWhenUserIsExternal(forgotPasswordApiResponse);
	}	
		
}
