// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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

public class GetAssociateEntitiesTests_BusinessLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	private SharedInfo sharedInfo;
	private CreateClientHierarchy_BL clientHierObj;
	Response createUserApiResponse, fetchUserDetailsApiResponse, createProviderApiResponse,
			fetchAssociateEntitiesApiResponse, addAssociatedEntitiesResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, userId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		providerId = createProvider(practiceId);
		userId = createClientUser();
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

	public int createClientUser() throws Exception {
		logStep("Create client user using valid details ");
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer("Client", testData.getProperty("api.user.firstName"),
				testData.getProperty("api.user.lastName"), userName, email, mobileNumber,
				testData.getProperty("api.user.userStatus"), "NOT_SENT", clientId, null, providerId, null, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		return createUserApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error message")
	public void testErrorMessageInvalidUrl() throws IOException {
		
		fetchAssociateEntitiesApiResponse = userInfo.getAssociateEntitiesInvalidUrl(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchAssociateEntitiesApiResponse);
		CCUtils.assertEquals(fetchAssociateEntitiesApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchAssociateEntitiesApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of GET")
	public void testErrorForInvalidMethodCall() throws IOException {
	
		fetchAssociateEntitiesApiResponse = userInfo.invalidMethodCallGetAssociateEntities(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchAssociateEntitiesApiResponse);
		CCUtils.assertEquals(fetchAssociateEntitiesApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(fetchAssociateEntitiesApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error message for non existing userId")
	public void testErrorMessageForNonExistingUserId() throws IOException {
		int userId = Integer.parseInt(testData.getProperty("nonexisting.user.id"));
		fetchAssociateEntitiesApiResponse = userInfo.getAssociateEntities(userId);
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
		addAssociatedEntitiesResponse = userInfo.addAssociateEntities(userId, OrgIDs);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
		CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 200);
		fetchAssociateEntitiesApiResponse = userInfo.getAssociateEntities(userId);
		CCUtils.responseTimeValidation(fetchAssociateEntitiesApiResponse);
		CCUtils.assertEquals(fetchAssociateEntitiesApiResponse.getStatusCode(), 200);
		validation.getAssociatedEntities(fetchAssociateEntitiesApiResponse, OrgIDs);
	}
}
