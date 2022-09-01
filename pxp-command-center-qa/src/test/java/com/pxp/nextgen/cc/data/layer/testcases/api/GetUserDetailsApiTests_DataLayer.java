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

public class GetUserDetailsApiTests_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response fetchUserDetailsApiResponse, createUserApiResponse;
	int userId;;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Non Existing id in userId field and verify error message for userId field")
	public void testErrorMessageNonExistingUserIdForUserIdField() throws IOException {
		logStep("Enter non existing client id");
		fetchUserDetailsApiResponse = UserInfo.getUserDetails_DL(testData.getProperty("nonexisting.user.id"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
		CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForUserIdField(fetchUserDetailsApiResponse,
				testData.getProperty("nonexisting.user.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch Internal user detail using Existing Internal user id in userId field")
	public void fetchInternalUserDetails() throws NullPointerException, IOException {

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
				email, mobileNumber, testData.getProperty("user.userStatus"), null, null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int id = createUserApiResponse.jsonPath().getInt("id");
		logStep("Enter  new internal userid");
		fetchUserDetailsApiResponse = UserInfo.getUserDetails_DL(String.valueOf(id));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
		CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 200);
		validation.verifyUserDetailsResponseBody(fetchUserDetailsApiResponse, authIdOne, "Internal", firstName,
				lastName, userName, email, Long.toString(mobileNumber), testData.getProperty("user.userStatus"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch Client user detail using Existing Client user id in userId field")
	public void fetchClientUserDetails() throws NullPointerException {
		logStep("Enter  existing client userid");
		fetchUserDetailsApiResponse = UserInfo.getUserDetails_DL(testData.getProperty("existing.client.userid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
		CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 200);
		validation.verifyClientUserDetailsResponseBody(fetchUserDetailsApiResponse,
				testData.getProperty("api.client.authId"), "Client", testData.getProperty("api.client.firstName"),
				testData.getProperty("api.client.lastName"), testData.getProperty("api.client.userName"),
				testData.getProperty("api.client.email"), testData.getProperty("api.client.mobile"),
				testData.getProperty("api.client.userStatus"), testData.getProperty("api.client.id"),
				testData.getProperty("api.client.name"), testData.getProperty("api.provider.id"),
				testData.getProperty("api.provider.name"), "Role must be assigned for user to enroll");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch VAR/Reseller user detail using existing VAR/Reseller user id in userId field")
	public void fetchVarResellerUserDetails() throws NullPointerException {
		logStep("Enter  existing var/reseller userid");
		fetchUserDetailsApiResponse = UserInfo.getUserDetails_DL(testData.getProperty("existing.varReseller.userid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
		CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 200);
		validation.verifyUserDetailsResponseBody(fetchUserDetailsApiResponse,
				testData.getProperty("api.varReseller.authId"), "VAReseller",
				testData.getProperty("api.varReseller.firstName"), testData.getProperty("api.varReseller.lastName"),
				testData.getProperty("api.varReseller.userName"), testData.getProperty("api.varReseller.email"),
				testData.getProperty("api.varReseller.mobile"), testData.getProperty("api.varReseller.userStatus"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMassageForUserDetailsUsingInvalidUrl() throws IOException {
		fetchUserDetailsApiResponse = UserInfo
				.getUserDetailsInvalidUrl_DL(testData.getProperty("existing.internal.userid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
		CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchUserDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter PUT method instead of GET method and validate error status code")
	public void testErrorMassageForUserDetailsUsingInvalidMethod() throws IOException {
		fetchUserDetailsApiResponse = UserInfo.invalidMethodCall_DL(testData.getProperty("existing.internal.userid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
		CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 405);
		validation.verifyErrorInResponseForInvalidMethod(fetchUserDetailsApiResponse);
	}
}
