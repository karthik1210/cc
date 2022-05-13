// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;

import io.restassured.response.Response;

public class ValidateUserDetailsApiTests_DataLayer extends BaseRest {
	private Validations validation;
	private UserInfo userInfo;
	Response getValidateUserApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@DataProvider(name = "getDuplicateEmailDetails")
	public Object[] getDetails() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("existing.internal.user.email").trim(), "xyz.xyz.abc@1234", "1000000001" },
				{ testData.getProperty("existing.internal.user.email").trim().toLowerCase(), "xyz.xyz.abc@1234",
						"1000000001" },
				{ testData.getProperty("existing.internal.user.email").trim().toUpperCase(), "xyz.xyz.abc@1234",
						"1000000001" },
				{ CCUtils.reverseCase(testData.getProperty("existing.internal.user.email").trim()), "xyz.xyz.abc@1234",
						"1000000001" },
				{ testData.getProperty("existing.client.user.email").trim(), "xyz.xyz.abc@1234", "1000000001" },
				{ testData.getProperty("existing.client.user.email").trim().toLowerCase(), "xyz.xyz.abc@1234",
						"1000000001" },
				{ testData.getProperty("existing.client.user.email").trim().toUpperCase(), "xyz.xyz.abc@1234",
						"1000000001" },
				{ CCUtils.reverseCase(testData.getProperty("existing.client.user.email").trim()), "xyz.xyz.abc@1234",
						"1000000001" },
				{ testData.getProperty("existing.reseller.user.email").trim(), "xyz.xyz.abc@1234", "1000000001" },
				{ testData.getProperty("existing.reseller.user.email").trim().toLowerCase(), "xyz.xyz.abc@1234",
						"1000000001" },
				{ testData.getProperty("existing.reseller.user.email").trim().toUpperCase(), "xyz.xyz.abc@1234",
						"1000000001" },
				{ CCUtils.reverseCase(testData.getProperty("existing.reseller.user.email").trim()), "xyz.xyz.abc@1234",
						"1000000001" } };
		return obj;
	}

	@DataProvider(name = "getDuplicateUserNameDetails")
	public Object[] getUserDetails() {
		Object[][] obj = new Object[][] {
				{ "xyz.xyz.abc@gmail.com", testData.getProperty("existing.internal.user.username").trim(),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com", testData.getProperty("existing.internal.user.username").trim().toLowerCase(),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com", testData.getProperty("existing.internal.user.username").trim().toUpperCase(),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com",
						CCUtils.reverseCase(testData.getProperty("existing.internal.user.username").trim()),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com", testData.getProperty("existing.client.user.username").trim(), "1000000001" },
				{ "xyz.xyz.abc@gmail.com", testData.getProperty("existing.client.user.username").trim().toLowerCase(),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com", testData.getProperty("existing.client.user.username").trim().toUpperCase(),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com",
						CCUtils.reverseCase(testData.getProperty("existing.client.user.username").trim()),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com", testData.getProperty("existing.reseller.user.username").trim(),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com", testData.getProperty("existing.reseller.user.username").trim().toLowerCase(),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com", testData.getProperty("existing.reseller.user.username").trim().toUpperCase(),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com",
						CCUtils.reverseCase(testData.getProperty("existing.reseller.user.username").trim()),
						"1000000001" } };
		return obj;
	}
	

	@DataProvider(name = "getDuplicateMobileNumberDetails")
	public Object[] getCommandCenterUserDetails() {
		Object[][] obj = new Object[][] {
				{ "xyz.xyz.abc@gmail.com", "xyz.xyz.abc@1234",
						testData.getProperty("existing.internal.user.mobile.number").trim() },
				{ "xyz.xyz.abc@gmail.com", "xyz.xyz.abc@1234",
						testData.getProperty("existing.client.user.mobile.number").trim() },
				{ "xyz.xyz.abc@gmail.com", "xyz.xyz.abc@1234",
						testData.getProperty("existing.reseller.user.mobile.number").trim() } };
		return obj;
	}
	
	@DataProvider(name = "getInvalidFormatEmail")
	public Object[] getInvalidEmailDetails() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("invalid.internal.user.emailid"), "xyz.xyz.abc@1234", "1000000001" },
				{ testData.getProperty("invalid.client.user.emailid").trim(), "xyz.xyz.abc@1234", "1000000001" },
				{ testData.getProperty("invalid.reseller.user.emailid").trim(), "xyz.xyz.abc@1234", "1000000001" } };

		return obj;
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getDuplicateEmailDetails", description = "Enter already Existing Email and verify error message in response body")
	public void testAlreadyExistingEmail(String[] getDetails) throws IOException {
		getValidateUserApiResponse = userInfo.validateUserDetails_DL(getDetails[0], getDetails[1], getDetails[2]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForDuplicateEmail(getValidateUserApiResponse, getDetails[0]);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getDuplicateUserNameDetails", description = "Enter already existing username in username field and verify error message in response body")
	public void testAlreadyExistingUsername(String[] getUserDetails) throws IOException {
		getValidateUserApiResponse = userInfo.validateUserDetails_DL(getUserDetails[0], getUserDetails[1],
				getUserDetails[2]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForDuplicateUsername(getValidateUserApiResponse, getUserDetails[1]);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getDuplicateMobileNumberDetails", description = "Enter already existing mobile number in mobile field and verify error message in response body")
	public void testAlreadyExistingMobileNumber(String[] getCommandCenterUserDetails) throws IOException {
		getValidateUserApiResponse = userInfo.validateUserDetails_DL(getCommandCenterUserDetails[0],
				getCommandCenterUserDetails[1], getCommandCenterUserDetails[2]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForDuplicateMobile(getValidateUserApiResponse, getCommandCenterUserDetails[2]);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing email, username and mobile number and verify success response code in response body")
	public void testSuccessResponseCodeForNonExistingEmailUsernameAndMobile() throws IOException {
		getValidateUserApiResponse = userInfo.validateUserDetails_DL("xyz.xyz.abc.1234.1234@gmail.com",
				"xyz.xyz.abc@1234.1234567", "1000000001");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getInvalidFormatEmail", description = "Enter Existing Email in email field with invalid format and verify error response code")
	public void testInvalidFormatEmail(String[] getInvalidEmailDetails) throws IOException {
		getValidateUserApiResponse = userInfo.validateUserDetails_DL(getInvalidEmailDetails[0],
				getInvalidEmailDetails[1], getInvalidEmailDetails[2]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForInvalidFormatEmail(getValidateUserApiResponse, getInvalidEmailDetails[0]);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testDuplicateUserDetailsUsingInvalidUrl() throws IOException {
		getValidateUserApiResponse = userInfo.validateUserDetailsUsingInvalidUrl_DL(
				testData.getProperty("existing.internal.user.email").trim(),
				testData.getProperty("existing.internal.user.username").trim(),
				testData.getProperty("existing.internal.user.mobile.number").trim());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getValidateUserApiResponse);
	}
}
