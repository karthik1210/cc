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
		setupQaServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing id in user id field and verify error message for user id field")
	public void testErrorMessageNonExistingUserIdForUserIdField() throws IOException {
		logStep("Enter non existing client id");
		int userId= Integer. parseInt(testData.getProperty("nonexisting.user.id"));
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
	public void fetchInternalUserDetails() throws NullPointerException {
		logStep("Enter  existing internal userid");
		int userId= Integer. parseInt(testData.getProperty("existing.internal.userid"));
		fetchUserDetailsApiResponse = userInfo.getUserDetails(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
		CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 200);
		  validation.verifyUserDetailsResponseBodyForBusinessLayer(
		  fetchUserDetailsApiResponse, testData.getProperty("api.internal.authId"),
		  "Internal", testData.getProperty("api.internal.firstName"),
		  testData.getProperty("api.internal.lastName"),
		  testData.getProperty("api.internal.userName"),
		  testData.getProperty("api.internal.email"),
		  testData.getProperty("api.internal.mobile"));  
	}

	/*//Enable test  once below issue is resolved. 
	 * Jira # PXPP-20773
	 * @Test(retryAnalyzer = RetryAnalyzer.class, description =
	 * "Fetch client user detail using existing client user id in userId field")
	 * public void fetchClientUserDetails() throws NullPointerException {
	 * logStep("Enter  existing client userid"); int userId= Integer.
	 * parseInt(testData.getProperty("existing.client.userid.qa"));
	 * fetchUserDetailsApiResponse = userInfo.getUserDetails(userId);
	 * logStep("Validating response information");
	 * CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
	 * CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 200);
	 * validation.verifyUserDetailsResponseBodyForBusinessLayer(
	 * fetchUserDetailsApiResponse, testData.getProperty("api.client.authId"),
	 * "Client", testData.getProperty("api.client.firstName"),
	 * testData.getProperty("api.client.lastName"),
	 * testData.getProperty("api.client.userName"),
	 * testData.getProperty("api.client.email"),
	 * testData.getProperty("api.client.mobile")); }
	 */

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch var/reseller user detail using existing internal user id in userId field ")
	public void fetchvarResellerUserDetails() throws NullPointerException {
		logStep("Enter  existing var/reseller userid");
		int userId= Integer. parseInt(testData.getProperty("existing.varReseller.userid"));
		fetchUserDetailsApiResponse = userInfo.getUserDetails(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserDetailsApiResponse);
		CCUtils.assertEquals(fetchUserDetailsApiResponse.getStatusCode(), 200);
		validation.verifyUserDetailsResponseBodyForBusinessLayer(fetchUserDetailsApiResponse,
				testData.getProperty("api.varReseller.authId"), "VAReseller",
				testData.getProperty("api.varReseller.firstName"), testData.getProperty("api.varReseller.lastName"),
				testData.getProperty("api.varReseller.userName"), testData.getProperty("api.varReseller.email"),
				testData.getProperty("api.varReseller.mobile"));
	}

}