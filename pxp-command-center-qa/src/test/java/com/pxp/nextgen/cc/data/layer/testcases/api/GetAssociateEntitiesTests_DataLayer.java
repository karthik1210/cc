// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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

public class GetAssociateEntitiesTests_DataLayer extends BaseRest {
	private UserInfo userInfo;
	private Validations validation;
	Response fetchAssociateEntitiesApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		userInfo = new UserInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error message")
	public void testErrorMessageInvalidUrl() throws IOException {
		int userId = Integer.parseInt(testData.getProperty("existing.user.id"));
		fetchAssociateEntitiesApiResponse = userInfo.getAssociateEntitiesInvalidUrlDL(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchAssociateEntitiesApiResponse);
		CCUtils.assertEquals(fetchAssociateEntitiesApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchAssociateEntitiesApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of GET")
	public void testErrorForInvalidMethodCall() throws IOException {
		int userId = Integer.parseInt(testData.getProperty("existing.user.id"));
		fetchAssociateEntitiesApiResponse = userInfo.invalidMethodCallGetAssociateEntitiesDL(userId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchAssociateEntitiesApiResponse);
		CCUtils.assertEquals(fetchAssociateEntitiesApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(fetchAssociateEntitiesApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error message for non existing userId")
	public void testErrorMessageForNonExistingUserId() throws IOException {
		int userId = Integer.parseInt(testData.getProperty("nonexisting.user.id"));
		fetchAssociateEntitiesApiResponse = userInfo.getAssociateEntitiesDL(userId);
		CCUtils.responseTimeValidation(fetchAssociateEntitiesApiResponse);
		CCUtils.assertEquals(fetchAssociateEntitiesApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForUserIdField(fetchAssociateEntitiesApiResponse,
				testData.getProperty("nonexisting.user.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify status code for existing userId where associate entities are not added")
	public void testMessageForExistingUserIdAWithoutAssociatedEntities() throws IOException {
		int userId = Integer.parseInt(testData.getProperty("api.user.id"));
		fetchAssociateEntitiesApiResponse = userInfo.getAssociateEntitiesDL(userId);
		CCUtils.responseTimeValidation(fetchAssociateEntitiesApiResponse);
		CCUtils.assertEquals(fetchAssociateEntitiesApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify status code for existing userId where associate entities are added")
	public void testMessageForExistingUserIdWithAssociatedEntities() throws IOException {
		ArrayList<Object> OrgIDs = new ArrayList<Object>();
		int userId = Integer.parseInt(testData.getProperty("existing.user.id"));
		fetchAssociateEntitiesApiResponse = userInfo.getAssociateEntitiesDL(userId);
		CCUtils.responseTimeValidation(fetchAssociateEntitiesApiResponse);
		CCUtils.assertEquals(fetchAssociateEntitiesApiResponse.getStatusCode(), 200);
		validation.getAssociatedEntities(fetchAssociateEntitiesApiResponse, OrgIDs);
	}
}
