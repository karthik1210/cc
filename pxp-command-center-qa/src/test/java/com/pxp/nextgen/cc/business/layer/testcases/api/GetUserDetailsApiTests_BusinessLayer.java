// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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

public class GetUserDetailsApiTests_BusinessLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	private SharedInfo sharedInfo;
	private CreateClientHierarchy_BL clientHierObj;
	Response fetchUserDetailsApiResponse, createUserApiResponse, createSystemApiResponse,
			createEnterpriseApiResponse, createClientApiResponse, createPracticeApiResponse, createProviderApiResponse;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName,firstName,lastName;
	int clientId, systemId, enterpriseId, practiceId, providerId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_BL();
	}

	public int createProvider(int practiceId) throws Exception {
		logStep("Create provider for providerId");
		 firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		 lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		return createProviderApiResponse.jsonPath().get("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing id in user id field and verify error message for user id field")
	public void testErrorMessageNonExistingUserIdForUserIdField() throws IOException {
		logStep("Enter non existing client id");
		int userId = Integer.parseInt(testData.getProperty("nonexisting.user.id"));
		fetchUserDetailsApiResponse = userInfo.getUserDetails(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
		CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForUserIdField(fetchUserDetailsApiResponse,
				testData.getProperty("nonexisting.user.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMassageForUserDetailsUsingInvalidUrl() throws IOException {
		fetchUserDetailsApiResponse = userInfo
				.getUserDetailsInvalidUrl(testData.getProperty("existing.internal.userid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
		CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch internal user detail using existing internal user id in userId field ")
	public void fetchInternalUserDetails() throws Exception {
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
		int userId = createUserApiResponse.jsonPath().getInt("id");
		logStep("Enter  existing internal userid");
		fetchUserDetailsApiResponse = userInfo.getUserDetails(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
		CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 200);
		validation.verifyUserDetailsResponseBody(fetchUserDetailsApiResponse, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch client user detail using existing client user id in userId field")
	public void fetchClientUserDetails() throws Exception {
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
		int userId = createUserApiResponse.jsonPath().getInt("id");
		logStep("Enter  existing client userid");
		fetchUserDetailsApiResponse = userInfo.getUserDetails(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
		CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 200);
		validation.verifyUserDetailsResponseBody(fetchUserDetailsApiResponse, "Client",
				firstName,lastName, userName, email);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch var/reseller user detail using existing internal user id in userId field ")
	public void fetchvarResellerUserDetails() throws NullPointerException, IOException {
		logStep("Create Var/Reseller user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("existing.varReseller.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer("VARReseller",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), "NOT_SENT", null, testData.getProperty("api.resellerId"), null, roleIds,
				false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);	
		int userId =  createUserApiResponse.jsonPath().getInt("id");
		fetchUserDetailsApiResponse = userInfo.getUserDetails(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
		CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 200);
		validation.verifyUserDetailsResponseBody(fetchUserDetailsApiResponse, "VARReseller",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email);
	}

}