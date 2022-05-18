// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.RoleInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetRolesByUserTypeApiTests_BusinessLayer extends BaseRest {
	private RoleInfo roleInfo;
	private Validations validation;
	Response getUserRolesApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		roleInfo = new RoleInfo();
		validation = new Validations();
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
				{ testData.getProperty("api.existing.client.id") } };
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userTypeValue", description = "Get user roles by userType")
	public void testUserRolesForUserType(String userType) throws IOException {
		getUserRolesApiResponse = roleInfo.getUserRolesByUserType(userType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getUserRolesApiResponse);
		CCUtils.assertEquals(getUserRolesApiResponse.getStatusCode(), 200);
		validation.verifyGetUserRolesResponseBody(getUserRolesApiResponse, userType);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "userType", description = "Enter non existing user type and verify response status code")
	public void testResponseCodeForNonExistingUserType(String userType) throws IOException {
		getUserRolesApiResponse = roleInfo.getUserRolesByUserType(userType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getUserRolesApiResponse);
		CCUtils.assertEquals(getUserRolesApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank space in user type field and verify error message")
	public void testErrorRepsonseForBlankUserType() throws IOException {
		getUserRolesApiResponse = roleInfo.getUserRolesByUserType(" ");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getUserRolesApiResponse);
		CCUtils.assertEquals(getUserRolesApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForUserTypeField(getUserRolesApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error message for invalid url")
	public void testErrorMessagesForInvalidUrl() throws IOException {
		getUserRolesApiResponse = roleInfo
				.getUserRolesByUserTypeUsingInvalidUrl(testData.getProperty("user.type.internal"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getUserRolesApiResponse);
		CCUtils.assertEquals(getUserRolesApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getUserRolesApiResponse);
	}

}
