// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class PatchUsersStatusApiTests_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	private CreateClientHierarchy_DL clientHierObj;
	private SharedInfo sharedInfo;
	Response activateDeactivateUserApiResponse, createProviderApiResponse, updateInternalUserDetailsApiResponse,
			createUserApiResponse;
	int userId, varResellerId, clientUserId, clientId, systemId, enterpriseId, practiceId, providerId;
	List<Integer> roleIds;
	String email, userName;
	Long mobileNumber;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_DL();
		userId = createUser();
		varResellerId = createNewVarResellerUser();
		clientUserId = createNewClientUser();
	}
	
	public int createProvider(int practiceId) throws Exception {
		logStep("Create provider for providerId");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		return createProviderApiResponse.jsonPath().get("providerId");
	}

	public int createNewClientUser() throws Exception {
		logStep("Create client user using valid details ");
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		providerId = createProvider(practiceId);
		providerId = createProviderApiResponse.jsonPath().get("providerId");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer("Client", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), "NOT_SENT", clientId, null, providerId, null, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		return createUserApiResponse.jsonPath().getInt("id");
	}

	public int createUser() throws NullPointerException, IOException {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("existing.internal.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer("Internal", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), "NOT_SENT", null, null, null, roleIds, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		return createUserApiResponse.jsonPath().getInt("id");
	}

	public int createNewVarResellerUser() throws IOException {
		logStep("Create Var/Reseller user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("existing.varReseller.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer("VARReseller",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), "NOT_SENT", null,
				testData.getProperty("api.resellerId"), null, roleIds, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		return varResellerId = createUserApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update user status of newly created internal user from active to inactive using user id")
	public void testUpdateInternalUserStatusInactive() throws IOException {
		logStep("Modify status of the newly created Internal user from active to inactive");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.inactive"), userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update user status of newly created internal user from active to locked using user id")
	public void testUpdateUserStatusLocked() throws IOException {
		logStep("Modify status of the newly created Internal user from active to locked");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.lockedout"), userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update user status of newly created internal user from inactive to active using user id")
	public void testUpdateUserStatusActiveFromInactive() throws IOException {
		logStep("Create new internal user & Modify status of the newly created Internal user from inactive to active");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.inactive"), userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.active"), userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update user status of newly created internal user from locked to active using user id")
	public void testUpdateUserStatusActiveFromLocked() throws IOException {
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.lockedout"), userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.active"), userId);
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
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 400);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update invalid user status of newly created internal user using user id")
	public void testErrorForInternalUserStatusWithInvalidStatus() throws IOException {
		logStep("Modify status of the newly created Internal user with invalid status");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("invalid.user.status"), userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidUserStatus(updateInternalUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new VarReseller user and update user status of newly created VarReseller user from active to inactive using user id")
	public void testCreateNewVarResellerlUserAndUpdateUserStatusInactive() throws IOException {
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.inactive"), varResellerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new VarReseller user and update user status of newly created internal user from active to locked using user id")
	public void testCreateNewVarResellerUserAndUpdateUserStatusLocked() throws IOException {
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.lockedout"), varResellerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new VarReseller user and update user status of newly created VarReseller user from inactive to active using user id")
	public void testCreateNewVarResellerUserAndUpdateUserStatusActive() throws IOException {
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.inactive"), varResellerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.active"), varResellerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new Client user and update user status of newly created client user from active to inactive using user id")
	public void testCreateNewClientUserAndUpdateUserStatusInactive() throws IOException {
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.inactive"), clientUserId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new Client user and update user status of newly created client user from active to locked using user id")
	public void testCreateNewClientUserAndUpdateUserStatusLocked() throws IOException {
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.lockedout"), clientUserId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new Client user and update user status of newly created client user from inactive to active using user id")
	public void testCreateNewClientUserAndUpdateUserStatusActive() throws IOException {
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.inactive"), clientUserId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
		logStep("Modify status of the newly created Internal user");
		updateInternalUserDetailsApiResponse = userInfo
				.patchInternalUser_DataLayer(testData.getProperty("user.status.active"), clientUserId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateInternalUserDetailsApiResponse);
		CCUtils.assertEquals(updateInternalUserDetailsApiResponse.getStatusCode(), 200);
	}
}
