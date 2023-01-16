//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class ActivateDeactivateUserApiTests_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	private OrgInfo orgInfo;
	private ClientInfo clientInfo;
	private SharedInfo sharedInfo;
	Response activateDeactivateUserApiResponse, createClientApiResponse, createSystemApiResponse,createProviderApiResponse,
			createEnterpriseApiResponse, createUserApiResponse, createPracticeApiResponse;
	int id, idOne, varUserIdOne, varUserIdTwo,clientId, systemId, enterpriseId, practiceId, providerId,clientIdOne,clientIdTwo;
	String clientName, clientSapNumber;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		sharedInfo = new SharedInfo();
		orgInfo = new OrgInfo();
		id = createIntenalUser();
		idOne = createIntenalUser();
		varUserIdOne = createNewVarResellerUser();
		varUserIdTwo = createNewVarResellerUser();
		clientIdOne=createNewClientUser();
		clientIdTwo=createNewClientUser();
	}

	public int createClient(String clientType) throws Exception {
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
		return createClientApiResponse.jsonPath().get("clientId");
	}

	public int createSystemNode(int clientId) throws Exception {
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
				testData.getProperty("api.system.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createSystemApiResponse.jsonPath().get("orgId");
	}

	public int createEnterpriseNode(int SystemId) throws Exception {
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.displayname"), testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createEnterpriseApiResponse.jsonPath().get("orgId");
	}

	public int createPracticeNode(int enterpriseId) throws Exception {
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse.jsonPath().get("orgId");
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
		clientId = createClient(ccUser.getClientOneType());
		systemId = createSystemNode(clientId);
		enterpriseId = createEnterpriseNode(systemId);
		practiceId = createPracticeNode(enterpriseId);
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

	public int createIntenalUser() throws Exception {
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
		return createUserApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify the status of single internal user from active to inactive and inactive to active and verify response body")
	public void testActivateDeactivateSingleInternalUser() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		userIds.add(id);
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
		userIds.add(id);
		userIds.add(idOne);
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
		userIds.add(id);
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
		userIds.add(id);
		logStep("Do not enter user status in the status field");
		activateDeactivateUserApiResponse = userInfo.activateDeactivateUsers_DL("", userIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(activateDeactivateUserApiResponse);
		CCUtils.assertEquals(activateDeactivateUserApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForBlankStatusField(activateDeactivateUserApiResponse);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify the status of single VarReseller user from active to inactive and inactive to active and verify response body")
	public void testActivateDeactivateSingleVarResellerUser() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		userIds.add(varUserIdOne);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify the status of multiple VarReseller users from active to inactive and inactive to active and verify response body")
	public void testActivateDeactivateMultipleVarResellerUsers() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		userIds.add(varUserIdOne);
		userIds.add(varUserIdTwo);
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
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify the status of single Client user from active to inactive and inactive to active and verify response body")
	public void testActivateDeactivateSingleClientUser() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		userIds.add(clientIdOne);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify the status of multiple Client users from active to inactive and inactive to active and verify response body")
	public void testActivateDeactivateMultipleClientUsers() throws IOException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		userIds.add(clientIdOne);
		userIds.add(clientIdTwo);
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


}
