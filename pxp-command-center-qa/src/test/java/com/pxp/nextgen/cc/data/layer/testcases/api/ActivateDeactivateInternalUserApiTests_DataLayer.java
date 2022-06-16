//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class ActivateDeactivateInternalUserApiTests_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response activateDeactivateUserApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify the status of single internal user from active to inactive and inactive to active and verify response body")
	public void testActivateDeactivateSingleInternalUser() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		userIds.add(Integer.parseInt(testData.getProperty("internal.user.id.one")));
		logStep("Modify the status of internal user from active to inactive");
		activateDeactivateUserApiResponse = userInfo.activateDeactivateUsers_DL(testData.getProperty("status.inactive"),
				userIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(activateDeactivateUserApiResponse);
		CCUtils.assertEquals(activateDeactivateUserApiResponse.getStatusCode(), 207);
		validation.verifyActivateDeactivateUserApiResponseBody(activateDeactivateUserApiResponse, userIds, "SUCCESS",
				"DEACTIVATED");
		logStep("Modify the status of internal user from inactive to active");
		activateDeactivateUserApiResponse = userInfo.activateDeactivateUsers_DL(testData.getProperty("status.active"),
				userIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(activateDeactivateUserApiResponse);
		CCUtils.assertEquals(activateDeactivateUserApiResponse.getStatusCode(), 207);
		validation.verifyActivateDeactivateUserApiResponseBody(activateDeactivateUserApiResponse, userIds, "SUCCESS",
				"ACTIVATED");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify the status of multiple internal users from active to inactive and inactive to active and verify response body")
	public void testActivateDeactivateMultipleInternalUsers() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		userIds.add(Integer.parseInt(testData.getProperty("internal.user.id.two")));
		userIds.add(Integer.parseInt(testData.getProperty("internal.user.id.three")));
		userIds.add(Integer.parseInt(testData.getProperty("internal.user.id.four")));
		logStep("Modify the status of multiple internal users from active to inactive");
		activateDeactivateUserApiResponse = userInfo.activateDeactivateUsers_DL(testData.getProperty("status.inactive"),
				userIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(activateDeactivateUserApiResponse);
		CCUtils.assertEquals(activateDeactivateUserApiResponse.getStatusCode(), 207);
		validation.verifyActivateDeactivateUserApiResponseBody(activateDeactivateUserApiResponse, userIds, "SUCCESS",
				"DEACTIVATED");
		logStep("Modify the status of multiple internal users from inactive to active");
		activateDeactivateUserApiResponse = userInfo.activateDeactivateUsers_DL(testData.getProperty("status.active"),
				userIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(activateDeactivateUserApiResponse);
		CCUtils.assertEquals(activateDeactivateUserApiResponse.getStatusCode(), 207);
		validation.verifyActivateDeactivateUserApiResponseBody(activateDeactivateUserApiResponse, userIds, "SUCCESS",
				"ACTIVATED");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing status in status field and verify response body")
	public void testErrorMessageForInvalidStatus() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		userIds.add(Integer.parseInt(testData.getProperty("internal.user.id.one")));
		logStep("Enter non existing status in status field");
		activateDeactivateUserApiResponse = userInfo
				.activateDeactivateUsers_DL(testData.getProperty("non.existing.status"), userIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(activateDeactivateUserApiResponse);
		CCUtils.assertEquals(activateDeactivateUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidUserStatus(activateDeactivateUserApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Do not enter user ids in the userIds field and verify response body")
	public void testErrorMessageForBlankUserIdField() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		logStep("Do not enter user ids in the userIds field");
		activateDeactivateUserApiResponse = userInfo.activateDeactivateUsers_DL(testData.getProperty("status.active"),
				userIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(activateDeactivateUserApiResponse);
		CCUtils.assertEquals(activateDeactivateUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForBlankUserIdField(activateDeactivateUserApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Do not enter status in the status field and verify response body")
	public void testErrorMessageForEmptyStatusField() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		userIds.add(Integer.parseInt(testData.getProperty("internal.user.id.one")));
		logStep("Do not enter user status in the status field");
		activateDeactivateUserApiResponse = userInfo.activateDeactivateUsers_DL("", userIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(activateDeactivateUserApiResponse);
		CCUtils.assertEquals(activateDeactivateUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForBlankStatusField(activateDeactivateUserApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if POST is used instead of PUT")
	public void testErrorForInvalidMethodCall() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		userIds.add(Integer.parseInt(testData.getProperty("internal.user.id.one")));
		activateDeactivateUserApiResponse = userInfo
				.activateDeactivateUsersUsingInvalidMethod_DL(testData.getProperty("status.active"), userIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(activateDeactivateUserApiResponse);
		CCUtils.assertEquals(activateDeactivateUserApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(activateDeactivateUserApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		userIds.add(Integer.parseInt(testData.getProperty("internal.user.id.one")));
		activateDeactivateUserApiResponse = userInfo
				.activateDeactivateUsersUsingInvalidUrl_DL(testData.getProperty("status.active"), userIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(activateDeactivateUserApiResponse);
		CCUtils.assertEquals(activateDeactivateUserApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(activateDeactivateUserApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing user id in userIds field and verify response body")
	public void testErrorMessageForNonExistingUserId() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		userIds.add(Integer.parseInt(testData.getProperty("non.existing.user.id")));
		logStep("Enter non existing user id in the userIds field");
		activateDeactivateUserApiResponse = userInfo.activateDeactivateUsers_DL(testData.getProperty("status.inactive"),
				userIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(activateDeactivateUserApiResponse);
		CCUtils.assertEquals(activateDeactivateUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForNonExistingUserId(activateDeactivateUserApiResponse);
	}

}
