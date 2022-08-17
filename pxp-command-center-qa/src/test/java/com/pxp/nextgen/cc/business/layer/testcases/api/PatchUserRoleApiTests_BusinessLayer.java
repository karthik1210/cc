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

public class PatchUserRoleApiTests_BusinessLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response updateRoleAssociationApiResponse, getUserRolesByUserApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupQaServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing userId & orgId ,activate role & validate status code and response body")
	public void testActivateRoleForInternalUser() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("pxpp.17575.cc.userid.qa"));
		int orgId = Integer.parseInt(testData.getProperty("pxpp.17575.cc.orgid.qa"));
		updateRoleAssociationApiResponse = userInfo.patchInternalUserRole_BL(userId, orgId,
				testData.getProperty("pxpp.17575.cc.roleid.qa"), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateRoleAssociationApiResponse);
		CCUtils.assertEquals(updateRoleAssociationApiResponse.getStatusCode(), 200);
		
		  getUserRolesByUserApiResponse = userInfo.getUserRolesByUser(userId);
		  CCUtils.responseTimeValidation(getUserRolesByUserApiResponse);
		  CCUtils.assertEquals(getUserRolesByUserApiResponse.getStatusCode(), 200);
			
			  List<UserRoles> actualUserRolesListByUser = Arrays.asList( new
			  UserRoles(Integer.parseInt(testData.getProperty("pxpp.17575.cc.roleid.qa")),
			  testData.getProperty("role.name.one.qa")));
			  validation.verifyGetUserRolesByUserApiResponse(getUserRolesByUserApiResponse,
			  actualUserRolesListByUser);
			 
		 
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing userId & orgId ,deactivate role & validate status code and response body")
	public void testDeactivateRoleForInternalUser() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("pxpp.17575.cc.userid.qa"));
		int orgId = Integer.parseInt(testData.getProperty("pxpp.17575.cc.orgid.qa"));
		updateRoleAssociationApiResponse = userInfo.patchInternalUserRole_BL(userId, orgId,
				testData.getProperty("pxpp.17575.cc.roleid.qa"), "false");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateRoleAssociationApiResponse);
		CCUtils.assertEquals(updateRoleAssociationApiResponse.getStatusCode(), 200);
		  getUserRolesByUserApiResponse = userInfo.getUserRolesByUser(userId);
		  CCUtils.responseTimeValidation(getUserRolesByUserApiResponse);
		  CCUtils.assertEquals(getUserRolesByUserApiResponse.getStatusCode(), 200);
			  List<UserRoles> actualUserRolesListByUser = Arrays.asList( new
			  UserRoles(Integer.parseInt(testData.getProperty("pxpp.17575.cc.roleid.qa")),
			  testData.getProperty("role.name.one.qa")));
			  validation.verifyGetUserRolesByUserApiResponse(getUserRolesByUserApiResponse,
			  actualUserRolesListByUser);
			 
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value for roleId and validate error message")
	public void testErrorMessagesForNullRoleId() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("api.internalUser.userId"));
		int orgId = Integer.parseInt(testData.getProperty("api.internalUser.orgId"));
		updateRoleAssociationApiResponse = userInfo.patchInternalUserRole_BL(userId, orgId, null, null);
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
			updateRoleAssociationApiResponse = userInfo.patchInternalUserRole_BL(userId, orgId,
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
		updateRoleAssociationApiResponse = userInfo.patchInternalUserRoleInvalidUrl_BL(userId, orgId,
				testData.getProperty("api.internal.roleId"), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateRoleAssociationApiResponse);
		CCUtils.assertEquals(updateRoleAssociationApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateRoleAssociationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of PATCH")
	public void testErrorForInvalidMethodCall() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("api.internalUser.userId"));
		int orgId = Integer.parseInt(testData.getProperty("api.internalUser.orgId"));
		updateRoleAssociationApiResponse = userInfo.patchInternalUserRoleInvalidMethod_BL(userId, orgId,
				testData.getProperty("api.internal.roleId"), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateRoleAssociationApiResponse);
		CCUtils.assertEquals(updateRoleAssociationApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(updateRoleAssociationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid userId & valid orgId and validate error message")
	public void testErrorMessagesForInvalidUserIdAndValidOrgId() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("nonexisting.user.id"));
		int orgId = Integer.parseInt(testData.getProperty("api.internalUser.orgId"));
		updateRoleAssociationApiResponse = userInfo.patchInternalUserRole_BL(userId, orgId,
				testData.getProperty("api.internal.roleId"), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateRoleAssociationApiResponse);
		CCUtils.assertEquals(updateRoleAssociationApiResponse.getStatusCode(), 400);
		validation.verifyInvalidUserIdApiResponse(updateRoleAssociationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid userId & invalid orgId and validate error message")
	public void testErrorMessagesForValidUserIdAndInvalidOrgId() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("api.internalUser.userId"));
		int orgId = Integer.parseInt(testData.getProperty("api.nonexisting.orgid"));
		String roleId = testData.getProperty("api.internal.roleId");
		updateRoleAssociationApiResponse = userInfo.patchInternalUserRole_BL(userId, orgId, roleId, "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateRoleAssociationApiResponse);
		CCUtils.assertEquals(updateRoleAssociationApiResponse.getStatusCode(), 400);
		validation.verifyInvalidRoleIdApiResponse(updateRoleAssociationApiResponse, userId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid userId & invalid orgId and validate error message")
	public void testErrorMessagesForInvalidUserIdAndInvalidOrgId() throws IOException {
		logStep("Enter existing userId & orgId");
		int userId = Integer.parseInt(testData.getProperty("nonexisting.user.id"));
		int orgId = Integer.parseInt(testData.getProperty("api.nonexisting.orgid"));
		String roleId = testData.getProperty("api.internal.roleId");
		updateRoleAssociationApiResponse = userInfo.patchInternalUserRole_BL(userId, orgId, roleId, "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateRoleAssociationApiResponse);
		CCUtils.assertEquals(updateRoleAssociationApiResponse.getStatusCode(), 400);
		validation.verifyInvalidUserIdApiResponse(updateRoleAssociationApiResponse);
	}
}