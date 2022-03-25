// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetUserRolesForUserTypeApiTests_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response getUserRolesApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get user roles for internal user")
	public void testUserRolesForInternalUser() throws IOException {
		getUserRolesApiResponse = userInfo.getUserRoles_DataLayer(testData.getProperty("user.type.internal"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getUserRolesApiResponse);
		CCUtils.assertEquals(getUserRolesApiResponse.getStatusCode(), 200);
		validation.verifyGetUserRolesResponseForInternalUser(getUserRolesApiResponse);
	}

	@DataProvider(name = "userType")
	public Object[][] getUserType() throws Exception {
		return new Object[][] {
				{ testData.getProperty("user.type.internal").concat(CCUtils.generateAlphaNumericString(5)) },
				{ testData.getProperty("api.existing.client.id") } };
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userType", description = "Enter non existing user type and verify response status code")
	public void testResponseCodeForNonExistingUserType(String userType) throws IOException {
		getUserRolesApiResponse = userInfo.getUserRoles_DataLayer(userType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getUserRolesApiResponse);
		CCUtils.assertEquals(getUserRolesApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank space in user type field and verify error message")
	public void testErrorRepsonseForBlankUserType() throws IOException {
		getUserRolesApiResponse = userInfo.getUserRoles_DataLayer(" ");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getUserRolesApiResponse);
		CCUtils.assertEquals(getUserRolesApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForUserTypeField(getUserRolesApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error message for invalid url")
	public void testErrorMessagesForInvalidUrl() throws IOException {
		getUserRolesApiResponse = userInfo
				.getUserRolesUsingInvalidUrl_DataLayer(testData.getProperty("user.type.internal"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getUserRolesApiResponse);
		CCUtils.assertEquals(getUserRolesApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getUserRolesApiResponse);
	}

}
