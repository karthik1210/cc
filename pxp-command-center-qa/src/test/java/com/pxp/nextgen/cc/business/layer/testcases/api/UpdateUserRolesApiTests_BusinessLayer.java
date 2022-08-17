// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class UpdateUserRolesApiTests_BusinessLayer extends BaseRest {
	private Validations validation;
	private UserInfo userInfo;
	Response updateUserRolesApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupQaServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing userId and validate error status code")
	public void testErrorResponseForInvalidUserId() throws IOException {
		ArrayList<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("role.id.one")));
		updateUserRolesApiResponse = userInfo
				.updateUserRoles(Integer.parseInt(testData.getProperty("non.existing.user.id")), roleIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserRolesApiResponse);
		CCUtils.assertEquals(updateUserRolesApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForUserIdField(updateUserRolesApiResponse,
				testData.getProperty("non.existing.user.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid internal userId and invalid roleIds and validate response body")
	public void testErrorResponseForValidInternalUserIdAndInvalidRoleIds() throws IOException {
		ArrayList<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.invalid.roleId")));
		roleIds.add(Integer.parseInt(testData.getProperty("var.reseller.role.id.one")));
		roleIds.add(Integer.parseInt(testData.getProperty("client.user.role.id.one")));
		updateUserRolesApiResponse = userInfo
				.updateUserRoles(Integer.parseInt(testData.getProperty("api.internalUser.userId")), roleIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserRolesApiResponse);
		CCUtils.assertEquals(updateUserRolesApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid client userId and invalid roleIds and validate response body")
	public void testErrorResponseForValidClientUserIdAndInvalidRoleIds() throws IOException {
		ArrayList<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.invalid.roleId")));
		roleIds.add(Integer.parseInt(testData.getProperty("var.reseller.role.id.two")));
		roleIds.add(Integer.parseInt(testData.getProperty("role.id.one")));
		updateUserRolesApiResponse = userInfo.updateUserRoles(Integer.parseInt(testData.getProperty("client.user.id")),
				roleIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserRolesApiResponse);
		CCUtils.assertEquals(updateUserRolesApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid VAR/Reseller userId and invalid roleIds and validate response body")
	public void testErrorResponseForValidVarResellerUserIdAndInvalidRoleIds() throws IOException {
		ArrayList<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("api.invalid.roleId")));
		roleIds.add(Integer.parseInt(testData.getProperty("client.user.role.id.two")));
		roleIds.add(Integer.parseInt(testData.getProperty("role.id.two")));
		updateUserRolesApiResponse = userInfo.updateUserRoles(Integer.parseInt(testData.getProperty("var.reseller.id")),
				roleIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserRolesApiResponse);
		CCUtils.assertEquals(updateUserRolesApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid  userId in userId field and enter null value in roleIds field and validate response body")
	public void testErrorResponseForNullRoleIdsField() throws IOException {
		ArrayList<Integer> roleIds = new ArrayList<Integer>();
		updateUserRolesApiResponse = userInfo
				.updateUserRoles(Integer.parseInt(testData.getProperty("api.internalUser.userId")), roleIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserRolesApiResponse);
		CCUtils.assertEquals(updateUserRolesApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidInput(updateUserRolesApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if POST is used instead of PUT")
	public void testErrorForInvalidMethodCall() throws IOException {
		ArrayList<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("role.id.one")));
		updateUserRolesApiResponse = userInfo.updateUserRolesUsingInvalidMethodCall(
				Integer.parseInt(testData.getProperty("api.internalUser.userId")), roleIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserRolesApiResponse);
		CCUtils.assertEquals(updateUserRolesApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(updateUserRolesApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		ArrayList<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("role.id.one")));
		updateUserRolesApiResponse = userInfo.updateUserRolesUsingInvalidUrl(
				Integer.parseInt(testData.getProperty("api.internalUser.userId")), roleIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserRolesApiResponse);
		CCUtils.assertEquals(updateUserRolesApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateUserRolesApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid internal userid in userId field and internal user roles in roleIds field and verify response body")
	public void testUpdateUserRolesFunctionalityForInternalUser() throws IOException {
		ArrayList<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("role.id.one")));
		roleIds.add(Integer.parseInt(testData.getProperty("role.id.two")));
		updateUserRolesApiResponse = userInfo
				.updateUserRoles(Integer.parseInt(testData.getProperty("internal.user.id.one")), roleIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserRolesApiResponse);
		CCUtils.assertEquals(updateUserRolesApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid client userid in userId field and client user role in roleIds field and verify response body")
	public void testUpdateUserRolesFunctionalityForClientUser() throws IOException {
		ArrayList<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("client.user.role.id.one")));
		updateUserRolesApiResponse = userInfo.updateUserRoles(Integer.parseInt(testData.getProperty("client.user.id")),
				roleIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserRolesApiResponse);
		CCUtils.assertEquals(updateUserRolesApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid reseller userid in userId field and reseller user roles in roleIds field and verify response body")
	public void testUpdateUserRolesFunctionalityForResellerUser() throws IOException {
		ArrayList<Integer> roleIds = new ArrayList<Integer>();
		roleIds.add(Integer.parseInt(testData.getProperty("var.reseller.role.id.one")));
		roleIds.add(Integer.parseInt(testData.getProperty("var.reseller.role.id.two")));
		updateUserRolesApiResponse = userInfo.updateUserRoles(Integer.parseInt(testData.getProperty("var.reseller.id")),
				roleIds);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateUserRolesApiResponse);
		CCUtils.assertEquals(updateUserRolesApiResponse.getStatusCode(), 200);
	}
}
