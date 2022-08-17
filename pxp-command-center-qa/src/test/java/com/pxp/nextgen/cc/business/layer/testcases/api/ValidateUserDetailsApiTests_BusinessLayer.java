// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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

public class ValidateUserDetailsApiTests_BusinessLayer extends BaseRest {
	private Validations validation;
	private UserInfo userInfo;
	Response getValidateUserApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupQaServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@DataProvider(name = "getDuplicateEmailDetails")
	public Object[] getDetails() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("existing.internal.user.email.qa").trim(), "xyz.xyz.abc@1234", "1000000001" },
				{ testData.getProperty("existing.internal.user.email.qa").trim().toLowerCase(), "xyz.xyz.abc@1234",
						"1000000001" },
				{ testData.getProperty("existing.internal.user.email.qa").trim().toUpperCase(), "xyz.xyz.abc@1234",
						"1000000001" },
				{ CCUtils.reverseCase(testData.getProperty("existing.internal.user.email.qa").trim()), "xyz.xyz.abc@1234",
						"1000000001" },
				{ testData.getProperty("existing.client.user.email.qa").trim(), "xyz.xyz.abc@1234", "1000000001" },
				{ testData.getProperty("existing.client.user.email.qa").trim().toLowerCase(), "xyz.xyz.abc@1234",
						"1000000001" },
				{ testData.getProperty("existing.client.user.email.qa").trim().toUpperCase(), "xyz.xyz.abc@1234",
						"1000000001" },
				{ CCUtils.reverseCase(testData.getProperty("existing.client.user.email.qa").trim()), "xyz.xyz.abc@1234",
						"1000000001" },
				{ testData.getProperty("existing.reseller.user.email.qa").trim(), "xyz.xyz.abc@1234", "1000000001" },
				{ testData.getProperty("existing.reseller.user.email.qa").trim().toLowerCase(), "xyz.xyz.abc@1234",
						"1000000001" },
				{ testData.getProperty("existing.reseller.user.email.qa").trim().toUpperCase(), "xyz.xyz.abc@1234",
						"1000000001" },
				{ CCUtils.reverseCase(testData.getProperty("existing.reseller.user.email.qa").trim()), "xyz.xyz.abc@1234",
						"1000000001" } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getDuplicateEmailDetails", description = "Enter already existing Email in email field and verify error message in response body")
	public void testAlreadyExistingEmail(String[] getDetails) throws IOException {
		getValidateUserApiResponse = userInfo.validateUserDetails(getDetails[0], getDetails[1], getDetails[2]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateEmail(getValidateUserApiResponse, getDetails[0]);
	}

	@DataProvider(name = "getDuplicateUserNameDetails")
	public Object[] getUserDetails() {
		Object[][] obj = new Object[][] {
				{ "xyz.xyz.abc@gmail.com", testData.getProperty("existing.internal.user.username.qa").trim(),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com", testData.getProperty("existing.internal.user.username.qa").trim().toLowerCase(),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com", testData.getProperty("existing.internal.user.username.qa").trim().toUpperCase(),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com",
						CCUtils.reverseCase(testData.getProperty("existing.internal.user.username.qa").trim()),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com", testData.getProperty("existing.client.user.username.qa").trim(), "1000000001" },
				{ "xyz.xyz.abc@gmail.com", testData.getProperty("existing.client.user.username.qa").trim().toLowerCase(),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com", testData.getProperty("existing.client.user.username.qa").trim().toUpperCase(),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com",
						CCUtils.reverseCase(testData.getProperty("existing.client.user.username.qa").trim()),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com", testData.getProperty("existing.reseller.user.username.qa").trim(),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com", testData.getProperty("existing.reseller.user.username.qa").trim().toLowerCase(),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com", testData.getProperty("existing.reseller.user.username.qa").trim().toUpperCase(),
						"1000000001" },
				{ "xyz.xyz.abc@gmail.com",
						CCUtils.reverseCase(testData.getProperty("existing.reseller.user.username.qa").trim()),
						"1000000001" } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getDuplicateUserNameDetails", description = "Enter already existing username in username field and verify error message in response body")
	public void testAlreadyExistingUsername(String[] getUserDetails) throws IOException {
		getValidateUserApiResponse = userInfo.validateUserDetails(getUserDetails[0], getUserDetails[1],
				getUserDetails[2]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateUsername(getValidateUserApiResponse, getUserDetails[1]);
	}

	@DataProvider(name = "getDuplicateMobileNumberDetails")
	public Object[] getCommandCenterUserDetails() {
		Object[][] obj = new Object[][] {
				{ "xyz.xyz.abc@gmail.com", "xyz.xyz.abc@1234",
						testData.getProperty("existing.internal.user.mobile.number.qa").trim() },
				{ "xyz.xyz.abc@gmail.com", "xyz.xyz.abc@1234",
						testData.getProperty("existing.client.user.mobile.number.qa").trim() },
				{ "xyz.xyz.abc@gmail.com", "xyz.xyz.abc@1234",
						testData.getProperty("existing.reseller.user.mobile.number.qa").trim() } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getDuplicateMobileNumberDetails", description = "Enter already existing mobile number in mobile field and verify error message in response body")
	public void testAlreadyExistingMobileNumber(String[] getCommandCenterUserDetails) throws IOException {
		getValidateUserApiResponse = userInfo.validateUserDetails(getCommandCenterUserDetails[0],
				getCommandCenterUserDetails[1], getCommandCenterUserDetails[2]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateMobile(getValidateUserApiResponse, getCommandCenterUserDetails[2]);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing email, username and mobile number and verify success response code in response body")
	public void testSuccessResponseCodeForNonExistingEmailUsernameAndMobile() throws IOException {
		getValidateUserApiResponse = userInfo.validateUserDetails("xyz.xyz.abc.1234.1234@gmail.com",
				"xyz.xyz.abc@1234.1234567", "1000000001");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testDuplicateUserDetailsUsingInvalidUrl() throws IOException {
		getValidateUserApiResponse = userInfo.validateUserDetailsUsingInvalidUrl(
				testData.getProperty("existing.internal.user.email.qa").trim(),
				testData.getProperty("existing.internal.user.username.qa").trim(),
				testData.getProperty("existing.internal.user.mobile.number.qa").trim());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getValidateUserApiResponse);
	}

}
