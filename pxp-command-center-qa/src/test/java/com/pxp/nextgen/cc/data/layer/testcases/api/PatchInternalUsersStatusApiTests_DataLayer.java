// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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

public class PatchInternalUsersStatusApiTests_DataLayer extends BaseRest {

	private UserInfo userInfo;
	private Validations validation;
	Response createUserApiResponse, updateInternalUserDetailsApiResponse;
	public int userId;
	List<Integer> roleIds;
	String email, userName;
	Long mobileNumber;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	public int createUser() throws NullPointerException, IOException {
		logStep("Create new internal user using valid details ");
		roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		return id;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update user status of newly created internal user from active to inactive using user id")
	public void testUpdateInternalUserStatusInactive() throws IOException {
		logStep("Modify status of the newly created Internal user from active to inactive");
		userId = createUser();
		int id = createUserApiResponse.jsonPath().getInt("id");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.inactive"), id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update user status of newly created internal user from active to locked using user id")
	public void testUpdateUserStatusLocked() throws IOException {
		logStep("Modify status of the newly created Internal user from active to locked");
		userId = createUser();
		int id = createUserApiResponse.jsonPath().getInt("id");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.lockedout"), id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update user status of newly created internal user from active to no role assigned using user id")
	public void testUpdateUserStatusNoRoleAssigned() throws IOException {
		logStep("Modify status of the newly created Internal user from active to no roles assigned");
		userId = createUser();
		int id = createUserApiResponse.jsonPath().getInt("id");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.no.roles.assigned"), id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update user status of newly created internal user from inactive to active using user id")
	public void testUpdateUserStatusActiveFromInactive() throws IOException {
		logStep("Create new internal user & Modify status of the newly created Internal user from inactive to active");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("user.status.inactive"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.active"), id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update user status of newly created internal user from locked to active using user id")
	public void testUpdateUserStatusActiveFromLocked() throws IOException {
		logStep("Create new internal user & Modify status of the newly created Internal user from locked to active");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("user.status.lockedout"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.active"), id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update user status of newly created internal user from no roles assigned to active using user id")
	public void testUpdateUserStatusActiveFromNoRolesAssigned() throws IOException {
		logStep("Create new internal user & Modify status of the newly created Internal user from no roles assigned to active");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(null, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("user.status.no.roles.assigned"), null, null, null, null, roleIds);
		int id = createUserApiResponse.jsonPath().getInt("id");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.active"), id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update user status of newly created internal user from active to null using user id")
	public void testUpdateUserStatusNull() throws IOException {
		logStep("Modify status of the newly created Internal user as null");
		userId = createUser();
		int id = createUserApiResponse.jsonPath().getInt("id");
		updateInternalUserDetailsApiResponse = userInfo.patchInternalUser_DataLayer(null, id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 500);
		validation.verifyErrorMessageInResponseForNullInput(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update existing user status using invalid user id")
	public void testModifyExistingUserStatusWithInvalidUserid() throws IOException {
		logStep("Update existing user status with non existing user id");
		int id = Integer.parseInt(testData.getProperty("non.existing.user.id"));
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.active"), id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseUpdateInternalUserStatusWithInvaliduserId(
				updateInternalUserDetailsApiResponse, testData.getProperty("non.existing.user.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update user status of newly created internal user with invalid url")
	public void testErrorMessagesForUpdateInternalUserStatusWithInvalidUrl() throws IOException {
		logStep("Modify status of the newly created Internal user with invalid url");
		userId = createUser();
		int id = createUserApiResponse.jsonPath().getInt("id");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayerUsingInvalidUrl(testData.getProperty("user.status.inactive"), id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update user status with invalid method call")
	public void testErrorForInternalUserStatusInvalidMethodCall() throws IOException {
		logStep("Modify status of the newly created Internal user with invalid method call");
		userId = createUser();
		int id = createUserApiResponse.jsonPath().getInt("id");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayerUsingInvalidMethodCall(testData.getProperty("user.status.lockedout"), id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update invalid user status of newly created internal user using user id")
	public void testErrorForInternalUserStatusWithInvalidStatus() throws IOException {
		logStep("Modify status of the newly created Internal user with invalid status");
		userId = createUser();
		int id = createUserApiResponse.jsonPath().getInt("id");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("invalid.user.status"), id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidUserStatus(updateInternalUserDetailsApiResponse);
	}

}
