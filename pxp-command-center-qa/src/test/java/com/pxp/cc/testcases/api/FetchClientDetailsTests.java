// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class FetchClientDetailsTests extends BaseRest {

	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	Response createClientApiResponse, fetchClientDetailsApiResponse, createPracticeApiResponse;
	List<Integer> solutionList = null;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing id in client id field and verify error message for client id field")
	public void testErrorMessageForClientIdField() throws IOException {
		logStep("Enter non existing client id");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(ccUser.getNonExistingClientId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForClientIdField(fetchClientDetailsApiResponse,
				ccUser.getNonExistingClientId());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new client and fetch newly created client details using client id")
	public void testFetchDetailsOfNewlyCreatedClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Fetch newly created client details using client id");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, ccUser.getActive(), ccUser.getTestClient(),
				ccUser.getTimezoneId(), ccUser.getDisplayName(), ccUser.getPracticeCount(), false, solutionList);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "create a new practice node with solutions and verify active solution details are displayed in the client details")
	public void testClientDetailsRepresentActiveSolutions() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("create a client node");
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		solutionList = new ArrayList<Integer>();
		solutionList.add(1101);
		solutionList.add(1102);
		logStep("create a practice node with solutions");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), practiceName, ccUser.getPracticeOrgTypeApi(),
				Integer.parseInt(clientId), ccUser.getParentTypeApi(), practiceSapNumber,
				ccUser.getPracticeTimezoneIdApi(), solutionList);

		logStep("Verify client details represent active solutions");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, ccUser.getActive(), ccUser.getTestClient(),
				ccUser.getTimezoneId(), ccUser.getDisplayName(), 1, true, solutionList);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "create a new practice node without solutions and verify the solutions available for purchase are not displayed in the client details")
	public void testClientDetailsNotRepresentSolutionAvailableForPurchase() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("create a client node");
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		logStep("create a practice node without solutions");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), practiceName, ccUser.getPracticeOrgTypeApi(),
				Integer.parseInt(clientId), ccUser.getParentTypeApi(), practiceSapNumber,
				ccUser.getPracticeTimezoneIdApi(), solutionList);

		logStep("Verify client details not represent solutions available for purchase");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, ccUser.getActive(), ccUser.getTestClient(),
				ccUser.getTimezoneId(), ccUser.getDisplayName(), 1, false, solutionList);
	}
}
