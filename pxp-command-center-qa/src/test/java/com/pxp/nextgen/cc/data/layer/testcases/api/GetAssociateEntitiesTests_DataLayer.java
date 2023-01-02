// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
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

public class GetAssociateEntitiesTests_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	private OrgInfo orgInfo;
	private ClientInfo clientInfo;
	private SharedInfo sharedInfo;
	Response createUserApiResponse, fetchUserDetailsApiResponse, createSystemApiResponse, createEnterpriseApiResponse,
			createClientApiResponse, createPracticeApiResponse, createProviderApiResponse,
			fetchAssociateEntitiesApiResponse, addAssociatedEntitiesResponse;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	int clientId, systemId, enterpriseId, practiceId, providerId, userId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
		clientInfo = new ClientInfo();
		sharedInfo = new SharedInfo();
		clientId = createClient(ccUser.getClientOneType());
		systemId = createSystemNode(clientId);
		enterpriseId = createEnterpriseNode(systemId);
		practiceId = createPracticeNode(enterpriseId);
		providerId = createProvider(practiceId);
		userId = createClientUser();
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

	public int createClientUser() throws Exception {
		logStep("Create client user using valid details ");
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error message")
	public void testErrorMessageInvalidUrl() throws IOException {

		fetchAssociateEntitiesApiResponse = userInfo.getAssociateEntitiesInvalidUrlDL(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchAssociateEntitiesApiResponse);
		CCUtils.assertEquals(fetchAssociateEntitiesApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchAssociateEntitiesApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of GET")
	public void testErrorForInvalidMethodCall() throws IOException {

		fetchAssociateEntitiesApiResponse = userInfo.invalidMethodCallGetAssociateEntitiesDL(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchAssociateEntitiesApiResponse);
		CCUtils.assertEquals(fetchAssociateEntitiesApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(fetchAssociateEntitiesApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error message for non existing userId")
	public void testErrorMessageForNonExistingUserId() throws IOException {
		int userId = Integer.parseInt(testData.getProperty("nonexisting.user.id"));
		fetchAssociateEntitiesApiResponse = userInfo.getAssociateEntitiesDL(userId);
		CCUtils.responseTimeValidation(fetchAssociateEntitiesApiResponse);
		CCUtils.assertEquals(fetchAssociateEntitiesApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForUserIdField(fetchAssociateEntitiesApiResponse,
				testData.getProperty("nonexisting.user.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify status code for existing userId where associate entities are added")
	public void testMessageForExistingUserIdWithAssociatedEntities() throws IOException {
		ArrayList<Object> OrgIDs = new ArrayList<Object>();
		OrgIDs.add(enterpriseId);
		OrgIDs.add(practiceId);
		addAssociatedEntitiesResponse = userInfo.addAssociateEntities_DL(userId, OrgIDs);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
		CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 200);
		fetchAssociateEntitiesApiResponse = userInfo.getAssociateEntitiesDL(userId);
		CCUtils.responseTimeValidation(fetchAssociateEntitiesApiResponse);
		CCUtils.assertEquals(fetchAssociateEntitiesApiResponse.getStatusCode(), 200);
		validation.getAssociatedEntities(fetchAssociateEntitiesApiResponse, OrgIDs);
	}
}
