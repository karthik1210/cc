// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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

public class GetUserInfoApiTests_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response fetchUserInfoApiResponse, fetchUserDetailsApiResponse, createUserApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create internal user fetch user & usingauth id in x-auth-id field and verify response body")
	public void testUserInfoDetailsForInternalUser() throws IOException {
		logStep("Create  user using valid details ");
		String authIdOne = CCUtils.generateAuthId();
		List<Integer> roleIds = new ArrayList<Integer>();
		String firstName = testData.getProperty("api.user.firstName");
		String lastName = testData.getProperty("api.user.lastName");
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(authIdOne, "Internal", firstName, lastName, userName,
				email, mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Enter  new internal userid");
		fetchUserDetailsApiResponse = UserInfo.getUserDetails_DL(String.valueOf(id));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
		CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 200);

		String authId = fetchUserDetailsApiResponse.jsonPath().getString("authId");

		logStep("Enter internal user auth id in auth id field");
		fetchUserInfoApiResponse = userInfo.getUserInfo_DataLayer(authId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserInfoApiResponse);
		CCUtils.assertEquals(fetchUserInfoApiResponse.getStatusCode(), 200);
		validation.verifyUserInfoApiResponseBody(fetchUserInfoApiResponse, id, firstName, lastName,
				testData.getProperty("user.type.internal"), null);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing client user auth id in x-auth-id field and verify response body")
	public void testUserInfoDetailsForClientUser() throws IOException {
		logStep("Enter client user auth id in auth id field");
		fetchUserInfoApiResponse = userInfo.getUserInfo_DataLayer(testData.getProperty("client.auth.id"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserInfoApiResponse);
		CCUtils.assertEquals(fetchUserInfoApiResponse.getStatusCode(), 200);
		validation.verifyUserInfoApiResponseBody(fetchUserInfoApiResponse,
				Integer.parseInt(testData.getProperty("client.user.id")),
				testData.getProperty("client.user.first.name"), testData.getProperty("client.user.last.name"),
				testData.getProperty("user.type.client"), testData.getProperty("client.user.client.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing var/reseller user auth id in x-auth-id field and verify response body")
	public void testUserInfoDetailsForVarResellerUser() throws IOException {
		logStep("Enter var/reseller user auth id in auth id field");
		fetchUserInfoApiResponse = userInfo.getUserInfo_DataLayer(testData.getProperty("reseller.auth.id"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserInfoApiResponse);
		CCUtils.assertEquals(fetchUserInfoApiResponse.getStatusCode(), 200);
		validation.verifyUserInfoApiResponseBody(fetchUserInfoApiResponse,
				Integer.parseInt(testData.getProperty("reseller.user.id")),
				testData.getProperty("reseller.user.first.name"), testData.getProperty("reseller.user.last.name"),
				testData.getProperty("user.type.varreseller"), null);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value in x-auth-id field and verify response body")
	public void testErrorMessageForNullAuthId() throws IOException {
		logStep("Enter null value in auth id field");
		fetchUserInfoApiResponse = userInfo.getUserInfo_DataLayer("null");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserInfoApiResponse);
		CCUtils.assertEquals(fetchUserInfoApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForInvalidAuthId(fetchUserInfoApiResponse, null);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing auth id in x-auth-id field and verify response body")
	public void testErrorMessageForNonExistingAuthId() throws IOException {
		logStep("Enter non-existing auth id value in auth id field");
		fetchUserInfoApiResponse = userInfo.getUserInfo_DataLayer(testData.getProperty("non.existing.auth.id"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserInfoApiResponse);
		CCUtils.assertEquals(fetchUserInfoApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForInvalidAuthId(fetchUserInfoApiResponse,
				testData.getProperty("non.existing.auth.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Do not enter any value in x-auth-id field and verify response body")
	public void testResponseBodyForBlankAuthId() throws IOException {
		logStep("Do not enter any value in auth id field");
		fetchUserInfoApiResponse = userInfo.getUserInfo_DataLayer("");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserInfoApiResponse);
		CCUtils.assertEquals(fetchUserInfoApiResponse.getStatusCode(), 400);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForGetUserInfoUsingInvalidUrl() throws IOException {
		fetchUserInfoApiResponse = userInfo
				.getUserInfoUsingInvalidUrl_DataLayer(testData.getProperty("internal.auth.id"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserInfoApiResponse);
		CCUtils.assertEquals(fetchUserInfoApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchUserInfoApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of GET")
	public void testErrorForInvalidMethod() throws IOException {
		fetchUserInfoApiResponse = userInfo
				.getUserInfoUsingInvalidMethodCall_DataLayer(testData.getProperty("internal.auth.id"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserInfoApiResponse);
		CCUtils.assertEquals(fetchUserInfoApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(fetchUserInfoApiResponse);
	}

}
