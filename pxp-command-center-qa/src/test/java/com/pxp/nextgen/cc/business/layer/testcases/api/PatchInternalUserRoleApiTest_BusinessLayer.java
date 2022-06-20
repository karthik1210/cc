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

public class PatchInternalUserRoleApiTest_BusinessLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response RoleAssociationApiResponse, getUserRolesByUserApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing userId & orgId ,activate role & validate status code and response body")
	public void testActivateRoleForInternalUser() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("api.internalUser.userId"));
		int orgId = Integer.parseInt(testData.getProperty("api.internalUser.orgId"));
		RoleAssociationApiResponse = userInfo.patchInternalUserRole_BL(userId, orgId,
				testData.getProperty("api.internal.roleId"), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(RoleAssociationApiResponse);
		CCUtils.assertEquals(RoleAssociationApiResponse.getStatusCode(), 200);
		getUserRolesByUserApiResponse = userInfo.getUserRolesByUser(userId);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing userId & orgId ,deactivate role & validate status code and response body")
	public void testDeactivateRoleForInternalUser() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("api.internalUser.userId"));
		int orgId = Integer.parseInt(testData.getProperty("api.internalUser.orgId"));
		RoleAssociationApiResponse = userInfo.patchInternalUserRole_BL(userId, orgId,
				testData.getProperty("api.internal.roleId"), "false");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(RoleAssociationApiResponse);
		CCUtils.assertEquals(RoleAssociationApiResponse.getStatusCode(), 200);
		getUserRolesByUserApiResponse = userInfo.getUserRolesByUser(userId);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value for roleId and validate error message")
	public void testErrorMessagesForNullRoleId() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("api.internalUser.userId"));
		int orgId = Integer.parseInt(testData.getProperty("api.internalUser.orgId"));
		RoleAssociationApiResponse = userInfo.patchInternalUserRole_BL(userId, orgId, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(RoleAssociationApiResponse);
		CCUtils.assertEquals(RoleAssociationApiResponse.getStatusCode(), 400);
		validation.verifyNullRoleIdApiResponse(RoleAssociationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid value for roleId and validate error message")
	public void testErrorMessagesForInvalidRoleId() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("api.internalUser.userId"));
		int orgId = Integer.parseInt(testData.getProperty("api.internalUser.orgId"));
		String roleId = testData.getProperty("api.invalid.roleId");
		RoleAssociationApiResponse = userInfo.patchInternalUserRole_BL(userId, orgId,
				testData.getProperty("api.invalid.roleId"), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(RoleAssociationApiResponse);
		CCUtils.assertEquals(RoleAssociationApiResponse.getStatusCode(), 400);
		validation.verifyInvalidRoleIdApiResponse(RoleAssociationApiResponse, orgId, roleId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessagesForInvalidUrl() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("api.internalUser.userId"));
		int orgId = Integer.parseInt(testData.getProperty("api.internalUser.orgId"));
		RoleAssociationApiResponse = userInfo.patchInternalUserRoleInvalidUrl_BL(userId, orgId,
				testData.getProperty("api.internal.roleId"), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(RoleAssociationApiResponse);
		CCUtils.assertEquals(RoleAssociationApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(RoleAssociationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of PATCH")
	public void testErrorForInvalidMethodCall() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("api.internalUser.userId"));
		int orgId = Integer.parseInt(testData.getProperty("api.internalUser.orgId"));
		RoleAssociationApiResponse = userInfo.patchInternalUserRoleInvalidMethod_BL(userId, orgId,
				testData.getProperty("api.internal.roleId"), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(RoleAssociationApiResponse);
		CCUtils.assertEquals(RoleAssociationApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(RoleAssociationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid userId & valid orgId and validate error message")
	public void testErrorMessagesForInvalidUserIdAndValidOrgId() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("nonexisting.user.id"));
		int orgId = Integer.parseInt(testData.getProperty("api.internalUser.orgId"));
		RoleAssociationApiResponse = userInfo.patchInternalUserRole_BL(userId, orgId,
				testData.getProperty("api.internal.roleId"), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(RoleAssociationApiResponse);
		CCUtils.assertEquals(RoleAssociationApiResponse.getStatusCode(), 400);
		validation.verifyInvalidUserIdApiResponse(RoleAssociationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid userId & invalid orgId and validate error message")
	public void testErrorMessagesForValidUserIdAndInvalidOrgId() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("api.internalUser.userId"));
		int orgId = Integer.parseInt(testData.getProperty("api.nonexisting.orgid"));
		String roleId = testData.getProperty("api.internal.roleId");
		RoleAssociationApiResponse = userInfo.patchInternalUserRole_BL(userId, orgId, roleId, "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(RoleAssociationApiResponse);
		CCUtils.assertEquals(RoleAssociationApiResponse.getStatusCode(), 400);
		validation.verifyInvalidRoleIdApiResponse(RoleAssociationApiResponse, orgId, roleId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid userId & invalid orgId and validate error message")
	public void testErrorMessagesForInvalidUserIdAndInvalidOrgId() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("nonexisting.user.id"));
		int orgId = Integer.parseInt(testData.getProperty("api.nonexisting.orgid"));
		String roleId = testData.getProperty("api.internal.roleId");
		RoleAssociationApiResponse = userInfo.patchInternalUserRole_BL(userId, orgId, roleId, "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(RoleAssociationApiResponse);
		CCUtils.assertEquals(RoleAssociationApiResponse.getStatusCode(), 400);
		validation.verifyInvalidUserIdApiResponse(RoleAssociationApiResponse);
	}
}