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
	int id;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		validation = new Validations();
		id = createIntenalUser();
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update user status of newly created internal user from active to inactive using user id")
	public void testCreateNewInternalUserAndUpdateUserStatusInactive() throws IOException {

		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo.patchInternalUser_BusinessLayer(id,
				testData.getProperty("api.user.poolId"), testData.getProperty("user.status.inactive"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update user status of newly created internal user from active to locked using user id")
	public void testCreateNewInternalUserAndUpdateUserStatusLocked() throws IOException {
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo.patchInternalUser_BusinessLayer(id,
				testData.getProperty("api.user.poolId"), testData.getProperty("user.status.lockedout"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update user status of newly created internal user from active to no role assigned using user id")
	public void testCreateNewInternalUserAndUpdateUserStatusNoRoleAssigned() throws IOException {
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo.patchInternalUser_BusinessLayer(id,
				testData.getProperty("api.user.poolId"), testData.getProperty("user.status.no.roles.assigned"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidUserStatus(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update user status of newly created internal user from inactive to active using user id")
	public void testCreateNewInternalUserAndUpdateUserStatusActive() throws IOException {
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo.patchInternalUser_BusinessLayer(id,
				testData.getProperty("api.user.poolId"), testData.getProperty("user.status.inactive"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo.patchInternalUser_BusinessLayer(id,
				testData.getProperty("api.user.poolId"), testData.getProperty("user.status.active"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update user status of newly created internal user from active to null using user id")
	public void testCreateNewInternalUserAndUpdateUserStatusNull() throws IOException {
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo.patchInternalUser_BusinessLayer(id,
				testData.getProperty("api.user.poolId"), null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update existing user status using invalid user id")
	public void testModifyExistingUserStatusWithInvalidUserid() throws IOException {
		logStep("Update existing user status with non existing user id");
		int id = Integer.parseInt(testData.getProperty("non.existing.user.id"));
		updateInternalUserDetailsApiResponse = userInfo.patchInternalUser_BusinessLayer(id,
				testData.getProperty("api.user.poolId"), testData.getProperty("user.status.inactive"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseUpdateInternalUserStatusWithInvaliduserId(
				updateInternalUserDetailsApiResponse, testData.getProperty("non.existing.user.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new internal user and update invalid user status of newly created internal user using user id")
	public void testErrorForInternalUserStatusWithInvalidStatus() throws IOException {
		logStep("Modify invalid status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo.patchInternalUser_BusinessLayer(id,
				testData.getProperty("api.user.poolId"), testData.getProperty("invalid.user.status"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidUserStatus(updateInternalUserDetailsApiResponse);
	}

}
