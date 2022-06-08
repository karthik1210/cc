
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

public class  ManageAssociateEntitiesTests_BusinessLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response addAssociatedEntitiesResponse, fetchAssociateEntitiesApiResponse;
	int userId;;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageInvalidUrl() throws IOException {
		ArrayList<Object> OrgIDs = new ArrayList<Object>();
		OrgIDs.add(4140);
		int userId = Integer.parseInt(testData.getProperty("existing.user.id"));
		addAssociatedEntitiesResponse = userInfo.addAssociateEntitiesInvalidUrl(userId, OrgIDs);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
		CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(addAssociatedEntitiesResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error message for existing userId in userId field & null value in orgId")
	public void testErrorMessageForNullValueForOrgIdField() throws IOException {
		ArrayList<Object> OrgIDs = new ArrayList<Object>();
		int userId = Integer.parseInt(testData.getProperty("existing.user.id"));
		addAssociatedEntitiesResponse = userInfo.addAssociateEntities(userId, OrgIDs);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
		CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 400);
		validation.verifyErrorInResponseForNullValueForOrgIdField(addAssociatedEntitiesResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error message for existing userId in userId field & invalid value in orgId")
	public void testErrorMessageForInvalidValueForOrgIdField() throws IOException {
		ArrayList<Object> OrgIDs = new ArrayList<Object>();
		OrgIDs.add("abc#$%");
		int userId = Integer.parseInt(testData.getProperty("existing.user.id"));
		addAssociatedEntitiesResponse = userInfo.addAssociateEntities(userId, OrgIDs);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
		CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 400);
		validation.verifyErrorInResponseForInvalidValueForOrgIdField(addAssociatedEntitiesResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error message for non existing userId in userId field")
	public void testErrorMessageForNonExistingUserId() throws IOException {
		ArrayList<Object> OrgIDs = new ArrayList<Object>();
		OrgIDs.add(4140);
		int userId = Integer.parseInt(testData.getProperty("nonexisting.user.id"));
		addAssociatedEntitiesResponse = userInfo.addAssociateEntities(userId, OrgIDs);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
		CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 400);
		validation.verifyErrorInResponseForNullValueForNonExistingUserId(addAssociatedEntitiesResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of POST")
	public void testErrorForInvalidMethodCall() throws IOException {
		ArrayList<Object> OrgIDs = new ArrayList<Object>();
		OrgIDs.add(4140);
		int userId = Integer.parseInt(testData.getProperty("existing.user.id"));
		addAssociatedEntitiesResponse = userInfo.invalidMethodCall(userId, OrgIDs);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
		CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(addAssociatedEntitiesResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing userID & nonexisting org id  and fetch user by using useId verify status code")
	public void testErrorMessageForExistingUserIdAndNonExistingOrgId() throws IOException {
		ArrayList<Object> OrgIDs = new ArrayList<Object>();
		OrgIDs.add(41);
		int userId = Integer.parseInt(testData.getProperty("existing.user.id"));
		addAssociatedEntitiesResponse = userInfo.addAssociateEntities(userId, OrgIDs);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
		CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 200);
		fetchAssociateEntitiesApiResponse = userInfo.getAssociateEntities(userId);
		CCUtils.responseTimeValidation(fetchAssociateEntitiesApiResponse);
		CCUtils.assertEquals(fetchAssociateEntitiesApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing userID & existing OrgIDs  and fetch user by using useId verify status code")
	public void testErrorMessageForExistingUserIdAndExistingOrgIDs() throws IOException {
		ArrayList<Object> OrgIDs = new ArrayList<Object>();
		OrgIDs.add(4141);
		OrgIDs.add(4142);
		int userId = Integer.parseInt(testData.getProperty("existing.user.id"));
		addAssociatedEntitiesResponse = userInfo.addAssociateEntities(userId, OrgIDs);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
		CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 200);
		fetchAssociateEntitiesApiResponse = userInfo.getAssociateEntities(userId);
		CCUtils.responseTimeValidation(fetchAssociateEntitiesApiResponse);
		CCUtils.assertEquals(fetchAssociateEntitiesApiResponse.getStatusCode(), 200);
		validation.getAssociatedEntities(fetchAssociateEntitiesApiResponse, OrgIDs);

	}
}

