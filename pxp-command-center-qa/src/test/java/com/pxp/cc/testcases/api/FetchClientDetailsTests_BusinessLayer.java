// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;

import io.restassured.response.Response;

public class FetchClientDetailsTests_BusinessLayer extends BaseRest {

	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	Response createClientApiResponse, fetchClientDetailsApiResponse, createPracticeApiResponse,
			fetchPracticeApiResponse;
	List<Integer> solutionList = null;
	Map<String, Object> solutionMap = new HashMap<String, Object>();

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
	}

	public Response createAgnosticPractice(String clientId, List<Map<String, Object>> solutionLists,
			String activeStatus) throws NumberFormatException, IOException {
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		logStep("create an active practice node with solutions");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(practiceName, ccUser.getPracticeDisplayNameApi(),
				practiceSapNumber, activeStatus, ccUser.getPracticeTimezoneIdApi(), solutionLists,
				ccUser.getPracticeOrgTypeApi(), Integer.parseInt(clientId), ccUser.getParentTypeApi());
		return createPracticeApiResponse;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing id in client id field and verify error message for client id field")
	public void testErrorMessageForClientIdField() throws IOException {
		logStep("Enter non existing client id");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(ccUser.getNonExistingClientId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 500);
		String message = validation.getMessage(fetchClientDetailsApiResponse);
		log("print message : " + message);
		logStep("Validating response message");
		CCUtils.assertEquals(message, "client not found by clientId=" + ccUser.getNonExistingClientId());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new client and fetch newly created client details using client id")
	public void testFetchDetailsOfNewlyCreatedClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), ccUser.getClientId(), clientName,
				clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("clientId").toString();

		List<Map<String, Object>> solutionLists = new ArrayList<Map<String, Object>>();
		logStep("Fetch newly created client details using client id");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchClientIdResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, ccUser.getActive(), ccUser.getTestClient(),
				ccUser.getTimezoneId(), ccUser.getDisplayName(), 0, solutionLists);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "create new active practice nodes with solutions and verify solution details are displayed in the client details")
	public void testClientDetailsRepresentActivePracticesWithActiveSolutions() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("create a client node");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), ccUser.getClientId(), clientName,
				clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("clientId").toString();

		List<Map<String, Object>> solutionLists = new ArrayList<Map<String, Object>>();
		solutionMap.put("solutionId", testData.getProperty("Portal.id"));
		solutionMap.put("status", testData.getProperty("status.newly.purchased"));
		solutionLists.add(solutionMap);
		logStep("create an active practice node with solutions");
		createPracticeApiResponse = createAgnosticPractice(clientId, solutionLists, "true");

		int practiceId = createPracticeApiResponse.jsonPath().get("orgId");

		fetchPracticeApiResponse = orgInfo.getOrgId(practiceId);
		List<Response> practiceResponse = new ArrayList<Response>();
		practiceResponse.add(fetchPracticeApiResponse);

		List<Map<String, Object>> solutionListsPracTwo = new ArrayList<Map<String, Object>>();
		solutionMap.put("solutionId", testData.getProperty("Portal.id"));
		solutionMap.put("status", testData.getProperty("status.newly.purchased"));
		solutionListsPracTwo.add(solutionMap);

		logStep("create another active practice node with solutions");
		createPracticeApiResponse = createAgnosticPractice(clientId, solutionListsPracTwo, "true");
		int practiceId_two = createPracticeApiResponse.jsonPath().get("orgId");
		fetchPracticeApiResponse = orgInfo.getOrgId(practiceId_two);
		practiceResponse.add(fetchPracticeApiResponse);
		logStep("validate both practice response body that they are created as active status with solutions");
		int pracCount = validation.validateActiveandSolutionStatusInGetPracticeDetails(practiceResponse);
		List<Map<String, Object>> finalSolutionLists = new ArrayList<Map<String, Object>>();
		finalSolutionLists.addAll(solutionLists);
		finalSolutionLists.addAll(solutionListsPracTwo);

		logStep("Verify client details represent active solutions");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchClientIdResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, ccUser.getActive(), ccUser.getTestClient(),
				ccUser.getTimezoneId(), ccUser.getDisplayName(), pracCount, finalSolutionLists);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "create new practice node without solutions and verify the practice count is zero and solutions details is null")
	public void testClientDetailsDonotRepresentActivePracticesWithoutSolutions() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("create a client node");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), ccUser.getClientId(), clientName,
				clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("clientId").toString();
		List<Map<String, Object>> solutionLists = new ArrayList<Map<String, Object>>();
		logStep("create another active practice node without solutions");
		createPracticeApiResponse = createAgnosticPractice(clientId, solutionLists, "true");

		int practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		fetchPracticeApiResponse = orgInfo.getOrgId(practiceId);
		List<Response> practiceResponse = new ArrayList<Response>();
		practiceResponse.add(fetchPracticeApiResponse);
		logStep("validate practice response body that it is created as active status without solutions");
		int pracCount = validation.validateActiveandSolutionStatusInGetPracticeDetails(practiceResponse);
		List<Map<String, Object>> finalSolutionLists = new ArrayList<Map<String, Object>>();
		finalSolutionLists.addAll(solutionLists);

		logStep("Verify client details not represent any solutions and practice count as zero");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchClientIdResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, ccUser.getActive(), ccUser.getTestClient(),
				ccUser.getTimezoneId(), ccUser.getDisplayName(), pracCount, finalSolutionLists);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "create new Inactive practice node and verify the practice count is zero and solutions details is null")
	public void testClientDetailsDonotRepresentInactiveSolutions() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("create a client node");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), ccUser.getClientId(), clientName,
				clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("clientId").toString();
		List<Map<String, Object>> solutionLists = new ArrayList<Map<String, Object>>();

		logStep("create an inactive practice node");
		createPracticeApiResponse = createAgnosticPractice(clientId, solutionLists, "false");
		int practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		fetchPracticeApiResponse = orgInfo.getOrgId(practiceId);
		List<Response> practiceResponse = new ArrayList<Response>();
		practiceResponse.add(fetchPracticeApiResponse);
		logStep("validate practice response body that it is created as inactive status");
		int pracCount = validation.validateActiveandSolutionStatusInGetPracticeDetails(practiceResponse);

		List<Map<String, Object>> finalSolutionLists = new ArrayList<Map<String, Object>>();
		finalSolutionLists.addAll(solutionLists);

		logStep("Verify client details not represent any solutions and display practice count as zero");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);

		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchClientIdResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, ccUser.getActive(), ccUser.getTestClient(),
				ccUser.getTimezoneId(), ccUser.getDisplayName(), pracCount, finalSolutionLists);
	}
}
