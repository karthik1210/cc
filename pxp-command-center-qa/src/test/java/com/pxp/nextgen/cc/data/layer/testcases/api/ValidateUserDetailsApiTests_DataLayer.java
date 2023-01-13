// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

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
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class ValidateUserDetailsApiTests_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	private OrgInfo orgInfo;
	private ClientInfo clientInfo;
	private SharedInfo sharedInfo;
	Response createUserApiResponse, fetchUserDetailsApiResponse, createSystemApiResponse, createEnterpriseApiResponse,
			getValidateUserApiResponse, createClientApiResponse, createPracticeApiResponse, createProviderApiResponse;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	int clientId, systemId, enterpriseId, practiceId, providerId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
		clientInfo = new ClientInfo();
		sharedInfo = new SharedInfo();
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validate internal user and verify status code and responce body ")
	public void testToValidateInternalUser() throws IOException {
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
		logStep("Enter already Existing Email, userName, mobileNumber and verify error message in response body");
		getValidateUserApiResponse = userInfo.validateUserDetails_DL(email, userName, mobileNumber);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateUsername(getValidateUserApiResponse, userName);
		logStep("Enter already Existing userName and verify error message in response body");
		getValidateUserApiResponse = userInfo.validateUserDetails_DL("aa@tt.as", userName, 1000000011);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateUsername(getValidateUserApiResponse, userName);
		logStep("Enter already Existing mobileNumber and verify error message in response body");
		getValidateUserApiResponse = userInfo.validateUserDetails_DL("aa@tt.as", "qq12", mobileNumber);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateMobile(getValidateUserApiResponse, mobileNumber);
		logStep("Enter already Existing Email and verify error message in response body");
		getValidateUserApiResponse = userInfo.validateUserDetails_DL(email, "qq12", 1001001001);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateEmail(getValidateUserApiResponse, email);
		logStep("Enter alreadynon existing Email, userName, mobileNumber and verify error message in response body");
		getValidateUserApiResponse = userInfo.validateUserDetails_DL("aa@tt.as", "qq12", 1001001001);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 200);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validate client user and verify status code and responce body")
	public void testToValidateClientUser() throws Exception {
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
		logStep("Enter already Existing Email, userName, mobileNumber and verify error message in response body");
		getValidateUserApiResponse = userInfo.validateUserDetails_DL(email, userName, mobileNumber);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateUsername(getValidateUserApiResponse, userName);
		logStep("Enter already Existing userName and verify error message in response body");
		getValidateUserApiResponse = userInfo.validateUserDetails_DL("aa@tt.as", userName, 1000000011);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateUsername(getValidateUserApiResponse, userName);
		logStep("Enter already Existing mobileNumber and verify error message in response body");
		getValidateUserApiResponse = userInfo.validateUserDetails_DL("aa@tt.as", "qq12", mobileNumber);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateMobile(getValidateUserApiResponse, mobileNumber);
		logStep("Enter already Existing Email and verify error message in response body");
		getValidateUserApiResponse = userInfo.validateUserDetails_DL(email, "qq12", 1001001001);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateEmail(getValidateUserApiResponse, email);
		logStep("Enter already non existing Email, userName, mobileNumber and verify error message in response body");
		getValidateUserApiResponse = userInfo.validateUserDetails_DL("aa@tt.as", "qq12", 1001001001);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 200);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, enabled = true, description = "Validate Var/Reseller user and verify status code and responce body")
	public void testToValidateVarResellerUser() throws IOException {
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
		logStep("Enter already Existing Email, userName, mobileNumber and verify error message in response body");
		getValidateUserApiResponse = userInfo.validateUserDetails_DL(email, userName, mobileNumber);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateUsername(getValidateUserApiResponse, userName);
		logStep("Enter already Existing  userName and verify error message in response body");
		getValidateUserApiResponse = userInfo.validateUserDetails_DL("aa@tt.as", userName, 1000000011);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateUsername(getValidateUserApiResponse, userName);
		logStep("Enter already Existing  mobileNumber and verify error message in response body");
		getValidateUserApiResponse = userInfo.validateUserDetails_DL("aa@tt.as", "qq12", mobileNumber);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateMobile(getValidateUserApiResponse, mobileNumber);
		logStep("Enter already Existing Email and verify error message in response body");
		getValidateUserApiResponse = userInfo.validateUserDetails_DL(email, "qq12", 1001001001);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 409);
		validation.verifyErrorResponseForDuplicateEmail(getValidateUserApiResponse, email);
		logStep("Enter non existing Email, userName, mobileNumber and verify error message in response body");
		getValidateUserApiResponse = userInfo.validateUserDetails_DL("aa@tt.as", "qq12", 1001001001);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getValidateUserApiResponse);
		CCUtils.assertEquals(getValidateUserApiResponse.getStatusCode(), 200);
	}
}
