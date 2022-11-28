// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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

public class PatchInternalUserRoleApiTests_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response updateRoleAssociationApiResponse, getUserRolesByUserApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing userId & orgId,activate role & validate status code and response body")
	public void testActivateRoleForInternalUser() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("api.internalUser.userId"));
		int orgId = Integer.parseInt(testData.getProperty("api.internalUser.orgId"));
		updateRoleAssociationApiResponse = userInfo.patchInternalUserRole_DL(userId, orgId,
				testData.getProperty("api.internal.roleId"), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateRoleAssociationApiResponse);
		CCUtils.assertEquals(updateRoleAssociationApiResponse.getStatusCode(), 200);
		getUserRolesByUserApiResponse = userInfo.getUserRolesByUser_DataLayer(userId);
		CCUtils.responseTimeValidation(getUserRolesByUserApiResponse);
		CCUtils.assertEquals(getUserRolesByUserApiResponse.getStatusCode(), 200);
				List<UserRoles> actualUserRolesListByUser = Arrays.asList(
						new UserRoles(Integer.parseInt(testData.getProperty("role.id.three")),
								testData.getProperty("role.name.three")),
						new UserRoles(Integer.parseInt(testData.getProperty("var.reseller.role.id.one")),
								testData.getProperty("api.role.name.one")),
						new UserRoles(Integer.parseInt(testData.getProperty("client.user.role.id.one")),
								testData.getProperty("api.role.name.two")));
		
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing userId & orgId,deactivate role & validate status code and response body")
	public void testDeactivateRoleForInternalUser() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("api.internalUser.userId"));
		int orgId = Integer.parseInt(testData.getProperty("api.internalUser.orgId"));
		updateRoleAssociationApiResponse = userInfo.patchInternalUserRole_DL(userId, orgId,
				testData.getProperty("api.internal.roleId"), "false");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateRoleAssociationApiResponse);
		CCUtils.assertEquals(updateRoleAssociationApiResponse.getStatusCode(), 200);
		getUserRolesByUserApiResponse = userInfo.getUserRolesByUser_DataLayer(userId);
		CCUtils.responseTimeValidation(getUserRolesByUserApiResponse);
		CCUtils.assertEquals(getUserRolesByUserApiResponse.getStatusCode(), 200);
		List<UserRoles> actualUserRolesListByUser = Arrays.asList(
				new UserRoles(Integer.parseInt(testData.getProperty("var.reseller.role.id.one")),
						testData.getProperty("api.role.name.one")),
				new UserRoles(Integer.parseInt(testData.getProperty("client.user.role.id.one")),
						testData.getProperty("api.role.name.two")));
		
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value for roleId and validate error message")
	public void testErrorMessagesForNullRoleId() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("api.internalUser.userId"));
		int orgId = Integer.parseInt(testData.getProperty("api.internalUser.orgId"));
		updateRoleAssociationApiResponse = userInfo.patchInternalUserRole_DL(userId, orgId, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateRoleAssociationApiResponse);
		CCUtils.assertEquals(updateRoleAssociationApiResponse.getStatusCode(), 400);
		validation.verifyNullRoleIdApiResponse(updateRoleAssociationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid value for roleId and validate error message")
	public void testErrorMessagesForInvalidRoleId() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("api.internalUser.userId"));
		int orgId = Integer.parseInt(testData.getProperty("api.internalUser.orgId"));
		String roleId = testData.getProperty("api.invalid.roleId");
		updateRoleAssociationApiResponse = userInfo.patchInternalUserRole_DL(userId, orgId,
				testData.getProperty("api.invalid.roleId"), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateRoleAssociationApiResponse);
		CCUtils.assertEquals(updateRoleAssociationApiResponse.getStatusCode(), 400);
		validation.verifyInvalidRoleIdApiResponse(updateRoleAssociationApiResponse, userId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessagesForInvalidUrl() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("api.internalUser.userId"));
		int orgId = Integer.parseInt(testData.getProperty("api.internalUser.orgId"));
		updateRoleAssociationApiResponse = userInfo.patchInternalUserRoleInvalidUrl_DL(userId, orgId,
				testData.getProperty("api.internal.roleId"), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateRoleAssociationApiResponse);
		CCUtils.assertEquals(updateRoleAssociationApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateRoleAssociationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of PATCH")
	public void testErrorMessageForInvalidMethodCall() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("api.internalUser.userId"));
		int orgId = Integer.parseInt(testData.getProperty("api.internalUser.orgId"));
		updateRoleAssociationApiResponse = userInfo.patchInternalUserRoleInvalidMethod_DL(userId, orgId,
				testData.getProperty("api.internal.roleId"), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateRoleAssociationApiResponse);
		CCUtils.assertEquals(updateRoleAssociationApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(updateRoleAssociationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid userId & valid orgId and validate error message")
	public void testErrorMessagesForInvalidUserIdAndValidOrgId() throws IOException {
		logStep("Enter invalid userId & valid orgId");
		int userId = Integer.parseInt(testData.getProperty("nonexisting.user.id"));
		int orgId = Integer.parseInt(testData.getProperty("api.internalUser.orgId"));
		updateRoleAssociationApiResponse = userInfo.patchInternalUserRole_DL(userId, orgId,
				testData.getProperty("api.internal.roleId"), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateRoleAssociationApiResponse);
		CCUtils.assertEquals(updateRoleAssociationApiResponse.getStatusCode(), 400);
		validation.verifyInvalidUserIdApiResponse(updateRoleAssociationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid userId & invalid orgId and validate error message")
	public void testErrorMessagesForValidUserIdAndInvalidOrgId() throws IOException {
		logStep("Enter valid userId & invalid orgId");
		int userId = Integer.parseInt(testData.getProperty("api.internalUser.userId"));
		int orgId = Integer.parseInt(testData.getProperty("api.nonexisting.orgid"));
		String roleId = testData.getProperty("api.internal.roleId");
		updateRoleAssociationApiResponse = userInfo.patchInternalUserRole_DL(userId, orgId, roleId, "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateRoleAssociationApiResponse);
		CCUtils.assertEquals(updateRoleAssociationApiResponse.getStatusCode(), 400);
		validation.verifyInvalidRoleIdApiResponse(updateRoleAssociationApiResponse, userId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid userId & invalid orgId and validate error message")
	public void testErrorMessagesForInvalidUserIdAndInvalidOrgId() throws IOException {
		logStep("Enter invalid userId & invalid orgId");
		int userId = Integer.parseInt(testData.getProperty("nonexisting.user.id"));
		int orgId = Integer.parseInt(testData.getProperty("api.nonexisting.orgid"));
		String roleId = testData.getProperty("api.internal.roleId");
		updateRoleAssociationApiResponse = userInfo.patchInternalUserRole_DL(userId, orgId, roleId, "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateRoleAssociationApiResponse);
		CCUtils.assertEquals(updateRoleAssociationApiResponse.getStatusCode(), 400);
		validation.verifyInvalidUserIdApiResponse(updateRoleAssociationApiResponse);
	}
}