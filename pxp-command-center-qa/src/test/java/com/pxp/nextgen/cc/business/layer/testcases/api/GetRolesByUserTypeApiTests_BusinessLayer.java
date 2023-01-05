// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;

import com.pxp.cc.helpers.UserInfo;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetRolesByUserTypeApiTests_BusinessLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response getUserRolesApiResponse, createClientApiResponse;
	String clientName, clientSapNumber, newClientId;
	private ClientInfo clientInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		clientInfo = new ClientInfo();
		validation = new Validations();
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(testData.getProperty("api.client.type")),
				ccUser.getTimezoneId(),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		newClientId = createClientApiResponse.jsonPath().get("clientId").toString();
	}

	@DataProvider(name = "userTypeValue")
	public Object[][] getUserTypes() throws Exception {
		return new Object[][] { { testData.getProperty("user.type.internal").trim() },
				{ testData.getProperty("user.type.client").trim() },
				{ testData.getProperty("user.type.varreseller").trim() } };
	}

	@DataProvider(name = "userType")
	public Object[][] getUserType() throws Exception {
		return new Object[][] {
				{ testData.getProperty("user.type.internal").concat(CCUtils.generateAlphaNumericString(5)) },
				{ newClientId } };
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userTypeValue", description = "Get user roles by userType")
	public void testUserRolesForUserType(String userType) throws IOException {
		getUserRolesApiResponse = userInfo.getUserRolesByUserType(userType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getUserRolesApiResponse);
		CCUtils.assertEquals(getUserRolesApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userType", description = "Enter non existing user type and verify response status code")
	public void testResponseCodeForNonExistingUserType(String userType) throws IOException {
		getUserRolesApiResponse = userInfo.getUserRolesByUserType(userType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getUserRolesApiResponse);
		CCUtils.assertEquals(getUserRolesApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank space in user type field and verify error message")
	public void testErrorRepsonseForBlankUserType() throws IOException {
		getUserRolesApiResponse = userInfo.getUserRolesByUserType(" ");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getUserRolesApiResponse);
		CCUtils.assertEquals(getUserRolesApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForUserTypeField(getUserRolesApiResponse);
	}
}
