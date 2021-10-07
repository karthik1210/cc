// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.ClientOrg;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;

import io.restassured.response.Response;

public class GetClientOrgControllerAgnosticPracticeTests extends BaseRest {

	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	Response createClientApiResponse, fetchClientOrgPracticeDetailsApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse;
	List<Integer> solutionList = null;

	@BeforeTest()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch newly created agnostic practice below client level.")
	public void testFetchDetailsOfNewlyCreatedAgnosticPractice() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		logStep("create a practice node");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), practiceName, ccUser.getPracticeOrgTypeApi(), clientId,
				ccUser.getParentTypeApi(), practiceSapNumber, ccUser.getPracticeTimezoneIdApi(), solutionList);

		logStep("Fetching client-org-practice details");
		fetchClientOrgPracticeDetailsApiResponse = clientInfo.getClientOrgId(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientOrgPracticeDetailsApiResponse);
		CCUtils.assertEquals(fetchClientOrgPracticeDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetClientOrgIdResponseBody(fetchClientOrgPracticeDetailsApiResponse,
				createPracticeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing client id & verify response code.")
	public void testErrorMessageForInvalidClientId() throws IOException {
		logStep("Enter non existing client id");
		fetchClientOrgPracticeDetailsApiResponse = clientInfo.getClientOrgId(ccUser.getNonExistClientId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientOrgPracticeDetailsApiResponse);
		CCUtils.assertEquals(fetchClientOrgPracticeDetailsApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter org id in client search fields & verify response code")
	public void testErrorMessageForClientFieldWhenEnteredOrgId() throws IOException {
		fetchClientOrgPracticeDetailsApiResponse = clientInfo.getClientOrgId(ccUser.getSystemOrgId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientOrgPracticeDetailsApiResponse);
		CCUtils.assertEquals(fetchClientOrgPracticeDetailsApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch multiple agnostic practices below client level.")
	public void testFetchDetailsOfNewlyCreatedMultiplePractices() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());

		logStep("Create multiple agnostic practices below client level");
		int clientId = createClientApiResponse.jsonPath().get("id");
		List<ClientOrg> actualClientOrg = new ArrayList<ClientOrg>();
		List<ClientOrg> actualClientOrgList = new ArrayList<ClientOrg>();
		for (int i = 0; i <= 1; i++) {
			String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
			String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
			createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
					ccUser.getPracticeDisplayNameApi(), practiceName, ccUser.getPracticeOrgTypeApi(), clientId,
					ccUser.getParentTypeApi(), practiceSapNumber, ccUser.getPracticeTimezoneIdApi(), solutionList);
			actualClientOrgList = validation.validateAddMultiplePractice(createPracticeApiResponse, actualClientOrg);

		}
		logStep("Fetching client-org-practice details");
		fetchClientOrgPracticeDetailsApiResponse = clientInfo.getClientOrgId(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientOrgPracticeDetailsApiResponse);
		CCUtils.assertEquals(fetchClientOrgPracticeDetailsApiResponse.getStatusCode(), 200);
		validation.verifyMultipleGetClientOrgIdResponseBody(fetchClientOrgPracticeDetailsApiResponse,
				actualClientOrgList);
	}

}
