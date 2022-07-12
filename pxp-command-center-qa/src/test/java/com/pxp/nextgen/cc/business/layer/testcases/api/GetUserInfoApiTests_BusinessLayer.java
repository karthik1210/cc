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

public class GetUserInfoApiTests_BusinessLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response fetchUserInfoApiResponse;
	int userId;;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing internal user auth id in x-auth-id field and verify response body")
	public void testUserInfoDetailsForInternalUser() throws IOException {
		logStep("Enter internal user auth id in auth id field");
		fetchUserInfoApiResponse = userInfo.getUserInfo(testData.getProperty("internal.auth.id"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserInfoApiResponse);
		CCUtils.assertEquals(fetchUserInfoApiResponse.getStatusCode(), 200);
		validation.verifyUserInfoApiResponseBody(fetchUserInfoApiResponse,
				Integer.parseInt(testData.getProperty("internal.user.id")),
				testData.getProperty("internal.user.first.name"), testData.getProperty("internal.user.last.name"),
				testData.getProperty("user.type.internal"), null);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing client user auth id in x-auth-id field and verify response body")
	public void testUserInfoDetailsForClientUser() throws IOException {
		logStep("Enter client user auth id in auth id field");
		fetchUserInfoApiResponse = userInfo.getUserInfo(testData.getProperty("client.auth.id"));
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
		fetchUserInfoApiResponse = userInfo.getUserInfo(testData.getProperty("reseller.auth.id"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchUserInfoApiResponse);
		CCUtils.assertEquals(fetchUserInfoApiResponse.getStatusCode(), 200);
		validation.verifyUserInfoApiResponseBody(fetchUserInfoApiResponse,
				Integer.parseInt(testData.getProperty("reseller.user.id")),
				testData.getProperty("reseller.user.first.name"), testData.getProperty("reseller.user.last.name"),
				testData.getProperty("user.type.varreseller"), null);
	}

}
