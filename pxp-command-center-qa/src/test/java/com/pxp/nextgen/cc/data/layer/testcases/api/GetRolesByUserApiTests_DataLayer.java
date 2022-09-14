// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
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

public class GetRolesByUserApiTests_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response getUserRolesByUserApiResponse, createUserApiResponse, putUserRolesApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get user roles of new user using user id and verify response body")
	public void testGetUserRolesByUserFunctionality() throws IOException {
		logStep("Create  user using valid details ");
		String authId = CCUtils.generateAuthId();
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(authId, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		logStep("Update  user roles for new user ");
		int id = createUserApiResponse.jsonPath().getInt("id");
		int pxp_cc_user = Integer.parseInt(testData.getProperty("pxpp.cc.user.id"));
		ArrayList<Object> Roles = new ArrayList<Object>();
		Roles.add(testData.getProperty("role.id.one"));
		Roles.add(testData.getProperty("role.id.two"));
		Roles.add(testData.getProperty("role.id.three"));
		putUserRolesApiResponse = userInfo.putUserRole_DataLayer(id, pxp_cc_user, Roles);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(putUserRolesApiResponse);
		CCUtils.assertEquals(putUserRolesApiResponse.getStatusCode(), 200);
		logStep("Get user roles of new user ");
		getUserRolesByUserApiResponse = userInfo.getUserRolesByUser_DataLayer(id);
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
				.getUserRolesByUser_DataLayer(Integer.parseInt(testData.getProperty("non.existing.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getUserRolesByUserApiResponse);
		CCUtils.assertEquals(getUserRolesByUserApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create user  that does not contains user roles and verify response status code")
	public void testResponseForUserWithoutUserRoles() throws IOException {
		logStep("Create  user using valid details ");
		String authId = CCUtils.generateAuthId();
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.internalUser.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_DataLayer(authId, "Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		int id = createUserApiResponse.jsonPath().getInt("id");
		getUserRolesByUserApiResponse = userInfo.getUserRolesByUser_DataLayer(id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getUserRolesByUserApiResponse);
		CCUtils.assertEquals(getUserRolesByUserApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error message for invalid url")
	public void testErrorMessagesForInvalidUrl() throws IOException {
		getUserRolesByUserApiResponse = userInfo.getUserRolesByUserUsingInvalidUrl_DataLayer(
				Integer.parseInt(testData.getProperty("existing.user.id.with.user.roles")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getUserRolesByUserApiResponse);
		CCUtils.assertEquals(getUserRolesByUserApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getUserRolesByUserApiResponse);
	}
}
