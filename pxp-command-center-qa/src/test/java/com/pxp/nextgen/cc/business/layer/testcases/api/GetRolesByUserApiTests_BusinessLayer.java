// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.UserRoles;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetRolesByUserApiTests_BusinessLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response getUserRolesByUserApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get user roles of existing user using user id and verify response body")
	public void testGetUserRolesByUserFunctionality() throws IOException {
		getUserRolesByUserApiResponse = userInfo
				.getUserRolesByUser(Integer.parseInt(testData.getProperty("existing.user.id.with.user.roles")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getUserRolesByUserApiResponse);
		CCUtils.assertEquals(getUserRolesByUserApiResponse.getStatusCode(), 200);
		List<UserRoles> actualUserRolesListByUser = Arrays.asList(
				new UserRoles(Integer.parseInt(testData.getProperty("role.id.one")),
						testData.getProperty("role.name.one")),
				new UserRoles(Integer.parseInt(testData.getProperty("role.id.two")),
						testData.getProperty("role.name.two")),
				new UserRoles(Integer.parseInt(testData.getProperty("role.id.three")),
						testData.getProperty("role.name.three")));
		validation.verifyGetUserRolesByUserApiResponse(getUserRolesByUserApiResponse, actualUserRolesListByUser);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing user id in user id field and verify response status code")
	public void testResponseStatuscodeForNonExistingUserId() throws IOException {
		getUserRolesByUserApiResponse = userInfo
				.getUserRolesByUser(Integer.parseInt(testData.getProperty("non.existing.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getUserRolesByUserApiResponse);
		CCUtils.assertEquals(getUserRolesByUserApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing user id that does not contains user roles and verify response status code")
	public void testResponseForUserWithoutUserRoles() throws IOException {
		getUserRolesByUserApiResponse = userInfo
				.getUserRolesByUser(Integer.parseInt(testData.getProperty("existing.user.id.without.user.roles")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getUserRolesByUserApiResponse);
		CCUtils.assertEquals(getUserRolesByUserApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error message for invalid url")
	public void testErrorMessagesForInvalidUrl() throws IOException {
		getUserRolesByUserApiResponse = userInfo.getUserRolesByUserUsingInvalidUrl(
				Integer.parseInt(testData.getProperty("existing.user.id.with.user.roles")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getUserRolesByUserApiResponse);
		CCUtils.assertEquals(getUserRolesByUserApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getUserRolesByUserApiResponse);
	}

}
