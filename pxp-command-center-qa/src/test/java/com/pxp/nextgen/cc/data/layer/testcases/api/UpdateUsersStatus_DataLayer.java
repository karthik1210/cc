// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;

public class UpdateUsersStatus_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private OrgInfo orgInfo;
	private ClientInfo clientInfo;
	private SharedInfo sharedInfo;
	Response createUserApiResponse, createSystemApiResponse, createEnterpriseApiResponse, createClientApiResponse,
			updateUserApiResponse, createPracticeApiResponse, createProviderApiResponse;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName, clientUserName, InternalUserUserName,
			varResellerUserName;
	int clientId, systemId, enterpriseId, practiceId, providerId, internalUserId, clientUserId, varResellerId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		orgInfo = new OrgInfo();
		clientInfo = new ClientInfo();
		sharedInfo = new SharedInfo();
		internalUserId = createNewInternalUser();
		clientUserId = createNewClientUser();
		varResellerId = createNewVarResellerUser();
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

	public int createNewInternalUser() throws IOException {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("existing.internal.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		InternalUserUserName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer("Internal", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), InternalUserUserName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), "NOT_SENT", null, null, null, roleIds, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		return createUserApiResponse.jsonPath().getInt("id");
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
		clientUserName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer("Client", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), clientUserName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), "NOT_SENT", clientId, null, providerId, null, false);
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
		varResellerUserName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer("VARReseller",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"),
				varResellerUserName, email, mobileNumber, testData.getProperty("api.user.userStatus"), "NOT_SENT", null,
				testData.getProperty("api.resellerId"), null, roleIds, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		return createUserApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify the status of  internal users and verify response code")
	public void testUpdateUserStatusForInternalUsers() throws IOException {
		logStep("Modify the status of  internal users from active to inactive");
		updateUserApiResponse = userInfo.updateUsersStatus_DL(internalUserId, "Inactive", "Modified",
				testData.getProperty("api.user.userStatus"), InternalUserUserName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserApiResponse);
		CCUtils.assertEquals(updateUserApiResponse.getStatusCode(), 200);
		logStep("Modify the status of  internal users from inactive to active");
		updateUserApiResponse = userInfo.updateUsersStatus_DL(internalUserId,
				testData.getProperty("api.user.userStatus"), "Modified", "Inactive", InternalUserUserName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserApiResponse);
		CCUtils.assertEquals(updateUserApiResponse.getStatusCode(), 200);
		logStep("Modify the status of  internal users from active to No Role Assigned");
		updateUserApiResponse = userInfo.updateUsersStatus_DL(internalUserId, "No Role Assigned", "Modified",
				testData.getProperty("api.user.userStatus"), InternalUserUserName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserApiResponse);
		CCUtils.assertEquals(updateUserApiResponse.getStatusCode(), 200);
		logStep("Modify the status of  internal users from active to Locked");
		updateUserApiResponse = userInfo.updateUsersStatus_DL(internalUserId, "Locked", "Modified",
				testData.getProperty("api.user.userStatus"), InternalUserUserName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserApiResponse);
		CCUtils.assertEquals(updateUserApiResponse.getStatusCode(), 200);
		logStep("Modify the status of  internal users from Locked to Locked Out");
		updateUserApiResponse = userInfo.updateUsersStatus_DL(internalUserId, "Locked Out", "Modified", "Locked",
				InternalUserUserName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserApiResponse);
		CCUtils.assertEquals(updateUserApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify the status of  Client users and verify response code")
	public void testUpdateUserStatusForClientUsers() throws IOException {
		logStep("Modify the status of  Client users from active to inactive");
		updateUserApiResponse = userInfo.updateUsersStatus_DL(clientUserId, "Inactive", "Modified",
				testData.getProperty("api.user.userStatus"), clientUserName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserApiResponse);
		CCUtils.assertEquals(updateUserApiResponse.getStatusCode(), 200);
		logStep("Modify the status of Client users from inactive to active");
		updateUserApiResponse = userInfo.updateUsersStatus_DL(clientUserId, testData.getProperty("api.user.userStatus"),
				"Modified", "Inactive", clientUserName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserApiResponse);
		CCUtils.assertEquals(updateUserApiResponse.getStatusCode(), 200);
		logStep("Modify the status of  Client users from active to No Role Assigned");
		updateUserApiResponse = userInfo.updateUsersStatus_DL(clientUserId, "No Role Assigned", "Modified",
				testData.getProperty("api.user.userStatus"), clientUserName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserApiResponse);
		CCUtils.assertEquals(updateUserApiResponse.getStatusCode(), 200);
		logStep("Modify the status of  Client users from active to Locked");
		updateUserApiResponse = userInfo.updateUsersStatus_DL(clientUserId, "Locked", "Modified",
				testData.getProperty("api.user.userStatus"), clientUserName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserApiResponse);
		CCUtils.assertEquals(updateUserApiResponse.getStatusCode(), 200);
		logStep("Modify the status of  Client users from Locked to Locked Out");
		updateUserApiResponse = userInfo.updateUsersStatus_DL(clientUserId, "Locked Out", "Modified", "Locked",
				clientUserName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserApiResponse);
		CCUtils.assertEquals(updateUserApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify the status of VarReseller users  and verify response code")
	public void testUpdateUserStatusForVarResellerUsers() throws IOException {
		logStep("Modify the status of  VarReseller users from active to inactive");
		updateUserApiResponse = userInfo.updateUsersStatus_DL(varResellerId, "Inactive", "Modified",
				testData.getProperty("api.user.userStatus"), varResellerUserName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserApiResponse);
		CCUtils.assertEquals(updateUserApiResponse.getStatusCode(), 200);

		logStep("Modify the status of  VarReseller users from inactive to active");
		updateUserApiResponse = userInfo.updateUsersStatus_DL(varResellerId,
				testData.getProperty("api.user.userStatus"), "Modified", "Inactive", varResellerUserName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserApiResponse);
		CCUtils.assertEquals(updateUserApiResponse.getStatusCode(), 200);
		logStep("Modify the status of  VarReseller users from active to No Role Assigned");
		updateUserApiResponse = userInfo.updateUsersStatus_DL(varResellerId, "No Role Assigned", "Modified",
				testData.getProperty("api.user.userStatus"), varResellerUserName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserApiResponse);
		CCUtils.assertEquals(updateUserApiResponse.getStatusCode(), 200);
		logStep("Modify the status of VarReseller users from active to Locked");
		updateUserApiResponse = userInfo.updateUsersStatus_DL(varResellerId, "Locked", "Modified",
				testData.getProperty("api.user.userStatus"), varResellerUserName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserApiResponse);
		CCUtils.assertEquals(updateUserApiResponse.getStatusCode(), 200);
		logStep("Modify the status of VarReseller users from Locked to Locked Out");
		updateUserApiResponse = userInfo.updateUsersStatus_DL(varResellerId, "Locked Out", "Modified", "Locked",
				varResellerUserName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserApiResponse);
		CCUtils.assertEquals(updateUserApiResponse.getStatusCode(), 200);
	}
}
