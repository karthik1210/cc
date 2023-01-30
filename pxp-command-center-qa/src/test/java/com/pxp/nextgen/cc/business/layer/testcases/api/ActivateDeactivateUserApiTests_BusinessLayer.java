//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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

public class ActivateDeactivateUserApiTests_BusinessLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	private SharedInfo sharedInfo;
	private CreateClientHierarchy_BL clientHierObj;
	Response activateDeactivateUserApiResponse, createUserApiResponse, createProviderApiResponse;
	int id, idOne, varUserIdOne, varUserIdTwo, clientId, systemId, enterpriseId, practiceId, providerId, clientIdOne,
			clientIdTwo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_BL();
		id = createIntenalUser();
		idOne = createIntenalUser();
		varUserIdOne = createNewVarResellerUser();
		varUserIdTwo = createNewVarResellerUser();
		clientIdOne = createNewClientUser();
		clientIdTwo = createNewClientUser();
	}

	public int createProvider(int practiceId) throws Exception {
		logStep("Create provider for providerId");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		return createProviderApiResponse.jsonPath().get("providerId");
	}

	public int createNewClientUser() throws Exception {
		logStep("Create client user using valid details ");
		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		providerId = createProvider(practiceId);
		providerId = createProviderApiResponse.jsonPath().get("providerId");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer("Client",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), "NOT_SENT", clientId, null, providerId, null,
				false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		return createUserApiResponse.jsonPath().getInt("id");	
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

	public int createNewVarResellerUser() throws IOException {
		logStep("Create Var/Reseller user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("existing.varReseller.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer("VARReseller",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), "NOT_SENT", null,
				testData.getProperty("api.resellerId"), null, roleIds, false);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify the status of single VarReseller user from active to inactive and inactive to active and verify response body")
	public void testActivateDeactivateSingleVarResellerUser() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		userIds.add(varUserIdOne);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify the status of multiple VarReseller users from active to inactive and inactive to active and verify response body")
	public void testActivateDeactivateMultipleVarResellerUsers() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		userIds.add(varUserIdOne);
		userIds.add(varUserIdTwo);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify the status of single Client user from active to inactive and inactive to active and verify response body")
	public void testActivateDeactivateSingleClientUser() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		userIds.add(clientIdOne);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify the status of multiple Client users from active to inactive and inactive to active and verify response body")
	public void testActivateDeactivateMultipleClientUsers() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		userIds.add(clientIdOne);
		userIds.add(clientIdTwo);
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
}
