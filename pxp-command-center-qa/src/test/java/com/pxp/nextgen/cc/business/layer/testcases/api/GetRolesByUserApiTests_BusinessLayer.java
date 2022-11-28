// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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

public class GetRolesByUserApiTests_BusinessLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response getUserRolesByUserApiResponse,createUserApiResponse;
	int id;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		validation = new Validations();
		id = createIntenalUser();	
	}

	public int createIntenalUser() throws Exception {
		logStep("Create internal user using valid details ");
		List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("existing.internal.roleId")));
		String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
		String userName = testData.getProperty("api.user.userName")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		Long mobileNumber = CCUtils.generateMobileNumber();
		createUserApiResponse = userInfo.createNewUser_BusinessLayer("Internal",
				testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
				mobileNumber, testData.getProperty("api.user.userStatus"), "NOT_SENT", null, null, null, roleIds,
				false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createUserApiResponse);
		CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
		return createUserApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get user roles of existing user using user id and verify response body")
	public void testGetUserRolesByUserFunctionality() throws IOException {
		getUserRolesByUserApiResponse = userInfo
				.getUserRolesByUser(id);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getUserRolesByUserApiResponse);
		CCUtils.assertEquals(getUserRolesByUserApiResponse.getStatusCode(), 200);	
	List<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("existing.internal.roleId")));
	validation.verifyGetUserRolesByUserApiResponse(getUserRolesByUserApiResponse, roleIds);
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
