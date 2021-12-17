// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetOrgIdSystemEnterprisePracticeApiTest extends BaseRest {

	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse, createClientApiResponse,
			getOrgIdApiResponse;

	@BeforeMethod()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter system id in orgId field and validate response body")
	public void testFetchingSystemOrgId() throws IOException {
		String systemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				systemName, ccUser.getSystemOrgTypeApi(), ccUser.getParentIdApi(), ccUser.getParentTypeApi(),
				systemSapNumber, ccUser.getSystemTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		validation.verifyCreateSystemResponseBody(createSystemApiResponse, systemName);
		int orgId = createSystemApiResponse.jsonPath().get("id");
		logStep("Fetching System Id Response body");
		getOrgIdApiResponse = orgInfo.getOrgId(orgId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(getOrgIdApiResponse);
		CCUtils.assertEquals(getOrgIdApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgIdResponseBody(getOrgIdApiResponse, orgId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter enterprise id in orgId field and validate response body")
	public void testFetchingEnterpriseOrgId() throws IOException {
		String enterpriseName = ccUser.getEnterpriseNameApi().concat(CCUtils.generateRandomString());
		String enterpriseSapNumber = ccUser.getEnterpriseSapNumberApi().concat(CCUtils.generateRandomString());
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), enterpriseName, ccUser.getEnterpriseOrgTypeApi(),
				ccUser.getEnterpriseParentIdApi(), ccUser.getEnterpriseParentTypeApi(), enterpriseSapNumber,
				ccUser.getEnterpriseTimezoneIdApi());

		logStep("Validating response information");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 200);
		validation.verifyCreateEnterpriseResponseBody(createEnterpriseApiResponse, enterpriseName);
		int orgId = createEnterpriseApiResponse.jsonPath().get("id");
		logStep("Fetching Enterprise Id Response body");
		getOrgIdApiResponse = orgInfo.getOrgId(orgId);
		CCUtils.responseTimeValidation(getOrgIdApiResponse);
		CCUtils.assertEquals(getOrgIdApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgIdResponseBody(getOrgIdApiResponse, orgId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter practice id in orgId field and validate response body")
	public void testFetchingPracticeOrgId() throws IOException {
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), practiceName, ccUser.getPracticeOrgTypeApi(),
				ccUser.getPracticeParentIdApi(), ccUser.getPracticeParentTypeApi(), practiceSapNumber,
				ccUser.getPracticeTimezoneIdApi());

		logStep("Validating response information");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		validation.verifyCreatePracticeResponseBody(createPracticeApiResponse, practiceName);
		int orgId = createPracticeApiResponse.jsonPath().get("id");
		logStep("Fetching Practice Id Response body");
		getOrgIdApiResponse = orgInfo.getOrgId(orgId);
		CCUtils.responseTimeValidation(getOrgIdApiResponse);
		CCUtils.assertEquals(getOrgIdApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgIdResponseBody(getOrgIdApiResponse, orgId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter client id in orgId field and validate response body")
	public void testFetchingErrorResponseForClientId() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getOrgClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		int clientId = createClientApiResponse.jsonPath().get("id");
		logStep("Fetching Client Id Response");
		getOrgIdApiResponse = orgInfo.getOrgId(clientId);
		CCUtils.responseTimeValidation(getOrgIdApiResponse);
		CCUtils.assertEquals(getOrgIdApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientIdInOrgIdField(getOrgIdApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid org id in orgId field and validate response body")
	public void testFetchingErrorResponseForInvalidOrgId() throws IOException {
		getOrgIdApiResponse = orgInfo.getOrgId(ccUser.getOrgInvalidId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgIdApiResponse);
		CCUtils.assertEquals(getOrgIdApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidInOrgId(getOrgIdApiResponse);
	}

}
