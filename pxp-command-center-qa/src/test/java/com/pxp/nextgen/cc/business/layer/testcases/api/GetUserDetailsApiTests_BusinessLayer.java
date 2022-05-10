// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetUserDetailsApiTests_BusinessLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response fetchUserDetailsApiResponse;
	int userId;;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing id in user id field and verify error message for user id field")
	public void testErrorMessageNonExistingUserIdForUserIdField() throws IOException {
		logStep("Enter non existing client id");
		fetchUserDetailsApiResponse = userInfo.getUserDetails(testData.getProperty("nonexisting.user.id"));
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
	public void fetchInternalUserDetails() throws NullPointerException {

		logStep("Enter  existing internal userid");
		fetchUserDetailsApiResponse = userInfo.getUserDetails(testData.getProperty("existing.internal.userid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
		CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 200);
		validation.verifyUserDetailsResponseBody(fetchUserDetailsApiResponse,
				testData.getProperty("api.internal.authId"), "Internal", testData.getProperty("api.internal.firstName"),
				testData.getProperty("api.internal.lastName"), testData.getProperty("api.internal.userName"),
				testData.getProperty("api.internal.email"), testData.getProperty("api.internal.mobile"),
				testData.getProperty("api.internal.userStatus"), "User created on 05/09/2022");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch client user detail using existing client user id in userId field")
	public void fetchClientUserDetails() throws NullPointerException {
		logStep("Enter  existing client userid");
		fetchUserDetailsApiResponse = userInfo.getUserDetails(testData.getProperty("existing.client.userid"));
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch var/reseller user detail using existing internal user id in userId field ")
	public void fetchvarResellerUserDetails() throws NullPointerException {
		logStep("Enter  existing var/reseller userid");
		fetchUserDetailsApiResponse = userInfo.getUserDetails(testData.getProperty("existing.varReseller.userid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
		CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 200);
		validation.verifyUserDetailsResponseBody(fetchUserDetailsApiResponse,
				testData.getProperty("api.varReseller.authId"), "VAReseller",
				testData.getProperty("api.varReseller.firstName"), testData.getProperty("api.varReseller.lastName"),
				testData.getProperty("api.varReseller.userName"), testData.getProperty("api.varReseller.email"),
				testData.getProperty("api.varReseller.mobile"), testData.getProperty("api.varReseller.userStatus"),
				"Role must be assigned for user to enroll");
	}

}