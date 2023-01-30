// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class ManageAssociateEntitiesForClientUserTests_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	private CreateClientHierarchy_DL clientHierObj;
	private SharedInfo sharedInfo;
	Response createUserApiResponse, fetchUserDetailsApiResponse, createSystemApiResponse, createEnterpriseApiResponse,
			createClientApiResponse, createPracticeApiResponse, createProviderApiResponse,
			addAssociatedEntitiesResponse;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	int clientId, systemId, enterpriseId, practiceId, providerId, userId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_DL();
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		providerId = createProvider(practiceId);
		userId = createClientUser();
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageInvalidUrl() throws IOException {
		ArrayList<Object> OrgIDs = new ArrayList<Object>();
		OrgIDs.add(practiceId);

		addAssociatedEntitiesResponse = userInfo.addAssociateEntitiesInvalidUrl_DL(userId, OrgIDs);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
		CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(addAssociatedEntitiesResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error message for existing userId in userId field & null value in orgId")
	public void testErrorMessageForNullValueForOrgIdField() throws IOException {
		ArrayList<Object> OrgIDs = new ArrayList<Object>();

		addAssociatedEntitiesResponse = userInfo.addAssociateEntities_DL(userId, OrgIDs);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
		CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 400);
		validation.verifyErrorInResponseForNullValuesForOrgIdField(addAssociatedEntitiesResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error message for existing userId in userId field & invalid value in orgId")
	public void testErrorMessageForInvalidValueForOrgIdField() throws IOException {
		ArrayList<Object> OrgIDs = new ArrayList<Object>();
		OrgIDs.add("abc#$%");
		addAssociatedEntitiesResponse = userInfo.addAssociateEntities_DL(userId, OrgIDs);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
		CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error message for non existing userId in userId field")
	public void testErrorMessageForNonExistingUserId() throws IOException {
		ArrayList<Object> OrgIDs = new ArrayList<Object>();
		OrgIDs.add(testData.getProperty("org.id.one"));
		int userId = Integer.parseInt(testData.getProperty("nonexisting.user.id"));
		addAssociatedEntitiesResponse = userInfo.addAssociateEntities_DL(userId, OrgIDs);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
		CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 400);
		validation.verifyErrorInResponseForNullValueForNonExistingUserId(addAssociatedEntitiesResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing userID & nonexisting org id  and fetch user by using useId verify status code")
	public void testErrorMessageForExistingUserIdAndNonExistingOrgId() throws IOException {
		ArrayList<Object> OrgIDs = new ArrayList<Object>();
		OrgIDs.add(testData.getProperty("non.existing.org.id"));
		addAssociatedEntitiesResponse = userInfo.addAssociateEntities_DL(userId, OrgIDs);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
		CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing userID & existing OrgIDs  and fetch user by using useId verify status code")
	public void testStatusCodeForExistingUserIdAndExistingOrgIDs() throws IOException {
		ArrayList<Object> OrgIDs = new ArrayList<Object>();
		OrgIDs.add(enterpriseId);
		OrgIDs.add(practiceId);
		addAssociatedEntitiesResponse = userInfo.addAssociateEntities_DL(userId, OrgIDs);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
		CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 200);
	}
}
