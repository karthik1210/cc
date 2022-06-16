// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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

public class PatchInternalUsersStatusApiTests_BusinessLayer extends BaseRest {

	private UserInfo userInfo;
	private Validations validation;
	Response createUserApiResponse, fetchUserDetailsApiResponse, updateInternalUserDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update user status of newly created internal user from active to inactive using user id")
	public void testCreateNewInternalUserAndUpdateUserStatusInactive() throws IOException {
		logStep("Create new internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer(testData.getProperty("api.user.clientId"),
				testData.getProperty("api.user.poolId"), null, "Internal", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_BusinessLayer(testData.getProperty("user.status.inactive"), id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update user status of newly created internal user from active to locked using user id")
	public void testCreateNewInternalUserAndUpdateUserStatusLocked() throws IOException {
		logStep("Create new internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer(testData.getProperty("api.user.clientId"),
				testData.getProperty("api.user.poolId"), null, "Internal", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_BusinessLayer(testData.getProperty("user.status.lockedout"), id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update user status of newly created internal user from active to no role assigned using user id")
	public void testCreateNewInternalUserAndUpdateUserStatusNoRoleAssigned() throws IOException {
		logStep("Create new internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer(testData.getProperty("api.user.clientId"),
				testData.getProperty("api.user.poolId"), null, "Internal", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_BusinessLayer(testData.getProperty("user.status.no.roles.assigned"), id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update user status of newly created internal user from inactive to active using user id")
	public void testCreateNewInternalUserAndUpdateUserStatusActive() throws IOException {
		logStep("Create new internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer(testData.getProperty("api.user.clientId"),
				testData.getProperty("api.user.poolId"), null, "Internal", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("user.status.inactive"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_BusinessLayer(testData.getProperty("user.status.active"), id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update user status of newly created internal user from active to null using user id")
	public void testCreateNewInternalUserAndUpdateUserStatusNull() throws IOException {
		logStep("Create new internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer(testData.getProperty("api.user.clientId"),
				testData.getProperty("api.user.poolId"), null, "Internal", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo.patchInternalUser_BusinessLayer(null, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInternalUserStatusAsNull(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update existing user status using invalid user id")
	public void testModifyExistingUserStatusWithInvalidUserid() throws IOException {
		logStep("Update existing user status with non existing user id");
		int id = Integer.parseInt(testData.getProperty("non.existing.user.id"));
		logStep("Modify status of the existing Internal user");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_BusinessLayer(testData.getProperty("user.status.active"), id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseUpdateInternalUserStatusWithInvaliduserId(
				updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update user status of newly created internal user with invalid url")
	public void testErrorMessagesForUpdateInternalUserStatusWithInvalidUrl() throws IOException {
		logStep("Create new internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer(testData.getProperty("api.user.clientId"),
				testData.getProperty("api.user.poolId"), null, "Internal", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("user.status.inactive"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_BusinessLayerUsingInvalidUrl(testData.getProperty("user.status.active"), id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update user status with invalid method call")
	public void testErrorForInternalUserStatusInvalidMethodCall() throws IOException {
		logStep("Create new internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer(testData.getProperty("api.user.clientId"),
				testData.getProperty("api.user.poolId"), null, "Internal", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("user.status.inactive"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_BusinessLayerUsingInvalidMethodCall(testData.getProperty("user.status.active"), id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update invalid user status of newly created internal user using user id")
	public void testErrorForInternalUserStatusWithInvalidStatus() throws IOException {
		logStep("Create new internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer(testData.getProperty("api.user.clientId"),
				testData.getProperty("api.user.poolId"), null, "Internal", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("user.status.inactive"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Modify invalid status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_BusinessLayer(testData.getProperty("invalid.user.status"), id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidUserStatus(updateInternalUserDetailsApiResponse);
	}

}
