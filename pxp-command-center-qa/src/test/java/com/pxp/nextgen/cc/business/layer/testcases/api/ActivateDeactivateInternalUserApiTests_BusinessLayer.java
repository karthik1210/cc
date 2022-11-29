//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class ActivateDeactivateInternalUserApiTests_BusinessLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response activateDeactivateUserApiResponse, createUserApiResponse;
	int id, idOne;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		validation = new Validations();
		id = createIntenalUser();
		idOne = createIntenalUser();
	}

	public int createIntenalUser() throws Exception {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("existing.internal.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer("Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), "NOT_SENT", null, null, null, roleIds,
				false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		return createUserApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify the status of single internal user from active to inactive and inactive to active and verify response body")
	public void testActivateDeactivateSingleInternalUser() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		userIds.add(id);
		logStep("Modify the status of internal user from active to inactive");
		activateDeactivateUserApiResponse = userInfo.activateDeactivateUsers_BL(testData.getProperty("status.inactive"),
				userIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(activateDeactivateUserApiResponse);
		CCUtils.assertEquals(activateDeactivateUserApiResponse.getStatusCode(), 207);
		validation.verifyActivateDeactivateUserApiResponseBody(activateDeactivateUserApiResponse, userIds, "SUCCESS",
				"DEACTIVATED");
		logStep("Modify the status of internal user from inactive to active");
		activateDeactivateUserApiResponse = userInfo.activateDeactivateUsers_BL(testData.getProperty("status.active"),
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
		userIds.add(id);
		userIds.add(idOne);
		logStep("Modify the status of multiple internal users from active to inactive");
		activateDeactivateUserApiResponse = userInfo.activateDeactivateUsers_BL(testData.getProperty("status.inactive"),
				userIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(activateDeactivateUserApiResponse);
		CCUtils.assertEquals(activateDeactivateUserApiResponse.getStatusCode(), 207);
		validation.verifyActivateDeactivateUserApiResponseBody(activateDeactivateUserApiResponse, userIds, "SUCCESS",
				"DEACTIVATED");
		logStep("Modify the status of multiple internal users from inactive to active");
		activateDeactivateUserApiResponse = userInfo.activateDeactivateUsers_BL(testData.getProperty("status.active"),
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
		userIds.add(id);
		logStep("Enter non existing status in status field");
		activateDeactivateUserApiResponse = userInfo
				.activateDeactivateUsers_BL(testData.getProperty("non.existing.status"), userIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(activateDeactivateUserApiResponse);
		CCUtils.assertEquals(activateDeactivateUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidUserStatus(activateDeactivateUserApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Do not enter user ids in the userIds field and verify response body")
	public void testErrorMessageForBlankUserIdField() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		logStep("Do not enter user ids in the userIds field");
		activateDeactivateUserApiResponse = userInfo.activateDeactivateUsers_BL(testData.getProperty("status.active"),
				userIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(activateDeactivateUserApiResponse);
		CCUtils.assertEquals(activateDeactivateUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForBlankUserIdField(activateDeactivateUserApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Do not enter status in the status field and verify response body")
	public void testErrorMessageForEmptyStatusField() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		userIds.add(id);
		logStep("Do not enter user status in the status field");
		activateDeactivateUserApiResponse = userInfo.activateDeactivateUsers_BL("", userIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(activateDeactivateUserApiResponse);
		CCUtils.assertEquals(activateDeactivateUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForBlankStatusField_BL(activateDeactivateUserApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing user id in userIds field and verify response body")
	public void testErrorMessageForNonExistingUserId() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		userIds.add(Integer.parseInt(testData.getProperty("non.existing.user.id")));
		logStep("Enter non existing user id in the userIds field");
		activateDeactivateUserApiResponse = userInfo.activateDeactivateUsers_BL(testData.getProperty("status.inactive"),
				userIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(activateDeactivateUserApiResponse);
		CCUtils.assertEquals(activateDeactivateUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForNonExistingUserId(activateDeactivateUserApiResponse);
	}

}
