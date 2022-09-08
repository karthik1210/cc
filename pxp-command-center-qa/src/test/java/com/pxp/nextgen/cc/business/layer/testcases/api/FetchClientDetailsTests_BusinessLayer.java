// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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
			fetchPracticeApiResponse, fetchOrgSolutionDetailsApiResponse, addOrgSolutionsApiResponse,
			createSystemApiResponse;
	List<Integer> solutionList = null;
	Map<String, Object> solutionMap = new HashMap<String, Object>();

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
	}

	public Response createAgnosticPractice(String clientId, String activeStatus)
			throws NumberFormatException, IOException {
		String systemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		logStep("create an active practice node with solutions");
		createSystemApiResponse = orgInfo.createOrgNode_BL(systemName, testData.getProperty("api.system.orgtype"), "",
				activeStatus, "", Integer.parseInt(clientId), "CLIENT",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		logStep("create an active practice node with solutions");
		createPracticeApiResponse = orgInfo.createOrgNode_BL(practiceName, testData.getProperty("api.practice.orgtype"),
				testData.getProperty("api.practice.displayname"), activeStatus,
				testData.getProperty("api.practice.timezoneid"), systemId, "SYSTEM",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse;
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
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(testData.getProperty("api.client.type")),
				ccUser.getTimezoneId());
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
				testData.getProperty("api.client.one.type"), clientSapNumber, ccUser.getActive(),
				ccUser.getTestClient(), ccUser.getTimezoneId(), ccUser.getDisplayName(), 0, solutionLists);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "create new active practice nodes with solutions and verify solution details are displayed in the client details")
	public void testClientDetailsRepresentActivePracticesWithActiveSolutions() throws IOException {

		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("create a client node");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(testData.getProperty("api.client.type")),
				ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("clientId").toString();

		Map<String, Object> solutionMapOne = new HashMap<String, Object>();
		List<Map<String, Object>> solutionListsOne = new ArrayList<Map<String, Object>>();
		solutionMapOne.put("solutionId", testData.getProperty("Portal.id"));
		solutionMapOne.put("status", testData.getProperty("status.newly.purchased"));
		solutionListsOne.add(solutionMapOne);

		logStep("create an active practice node with solutions");
		createPracticeApiResponse = createAgnosticPractice(clientId, "true");
		int practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		addOrgSolutionsApiResponse = orgInfo.updateOrgSolutions(solutionListsOne, practiceId);
		CCUtils.assertEquals(addOrgSolutionsApiResponse.getStatusCode(), 200);
		fetchPracticeApiResponse = orgInfo.getOrgId(practiceId);

		Map<String, Object> solutionMapTwo = new HashMap<String, Object>();
		List<Map<String, Object>> solutionListsPracTwo = new ArrayList<Map<String, Object>>();
		solutionMapTwo.put("solutionId", testData.getProperty("Portal.id"));
		solutionMapTwo.put("status", testData.getProperty("status.newly.purchased"));
		solutionListsPracTwo.add(solutionMapTwo);

		logStep("create another active practice node with solutions");
		createPracticeApiResponse = createAgnosticPractice(clientId, "true");
		int practiceId_two = createPracticeApiResponse.jsonPath().get("orgId");
		addOrgSolutionsApiResponse = orgInfo.updateOrgSolutions(solutionListsPracTwo, practiceId_two);
		CCUtils.assertEquals(addOrgSolutionsApiResponse.getStatusCode(), 200);

		logStep("Default Solution list");
		List<Map<String, Object>> defaultSolutionOneList = new ArrayList<Map<String, Object>>();
		solutionMap.put("solutionId", testData.getProperty("ehr.identifier"));
		solutionMap.put("status", testData.getProperty("status.newly.purchased"));
		defaultSolutionOneList.add(solutionMap);
		Map<String, Object> solutionMapThree = new HashMap<String, Object>();
		List<Map<String, Object>> defaultSolutionTwoList = new ArrayList<Map<String, Object>>();
		solutionMapThree.put("solutionId", testData.getProperty("pm.identifier"));
		solutionMapThree.put("status", testData.getProperty("status.newly.purchased"));
		defaultSolutionTwoList.add(solutionMapThree);

		logStep("validate both practice response body that they are created as active status with solutions");
		List<Map<String, Object>> finalSolutionLists = new ArrayList<Map<String, Object>>();
		finalSolutionLists.addAll(defaultSolutionOneList);
		finalSolutionLists.addAll(defaultSolutionTwoList);
		finalSolutionLists.addAll(solutionListsOne);
		finalSolutionLists.addAll(defaultSolutionOneList);
		finalSolutionLists.addAll(defaultSolutionTwoList);
		finalSolutionLists.addAll(solutionListsPracTwo);

		logStep("Verify client details represent active solutions");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchClientIdResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				testData.getProperty("api.client.one.type"), clientSapNumber, ccUser.getActive(),
				ccUser.getTestClient(), ccUser.getTimezoneId(), ccUser.getDisplayName(), 2, finalSolutionLists);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "create new practice node without solutions and verify the practice count is one and default solutions details are displayed in client details page")
	public void testClientDetailsDonotRepresentActivePracticesWithoutSolutions() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("create a client node");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(testData.getProperty("api.client.type")),
				ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("clientId").toString();
		logStep("create another active practice node without solutions");
		createPracticeApiResponse = createAgnosticPractice(clientId, "true");

		int practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		fetchPracticeApiResponse = orgInfo.getOrgId(practiceId);
		List<Response> practiceResponse = new ArrayList<Response>();
		practiceResponse.add(fetchPracticeApiResponse);
		List<Map<String, Object>> defaultSolutionOne = new ArrayList<Map<String, Object>>();
		solutionMap.put("solutionId", testData.getProperty("ehr.identifier"));
		solutionMap.put("status", testData.getProperty("status.newly.purchased"));
		defaultSolutionOne.add(solutionMap);
		List<Map<String, Object>> defaultSolutionTwo = new ArrayList<Map<String, Object>>();
		solutionMap.put("solutionId", testData.getProperty("pm.identifier"));
		solutionMap.put("status", testData.getProperty("status.newly.purchased"));
		defaultSolutionTwo.add(solutionMap);
		List<Map<String, Object>> finalSolutionLists = new ArrayList<Map<String, Object>>();
		finalSolutionLists.addAll(defaultSolutionOne);
		finalSolutionLists.addAll(defaultSolutionTwo);

		logStep("Verify client details represent default solutions and practice count as one");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchClientIdResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				testData.getProperty("api.client.one.type"), clientSapNumber, ccUser.getActive(),
				ccUser.getTestClient(), ccUser.getTimezoneId(), ccUser.getDisplayName(), 1, finalSolutionLists);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "create new Inactive practice node and verify the practice count is zero and solutions details is null")
	public void testClientDetailsDonotRepresentInactiveSolutions() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("create a client node");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(testData.getProperty("api.client.type")),
				ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("clientId").toString();
		List<Map<String, Object>> solutionLists = new ArrayList<Map<String, Object>>();

		logStep("create an inactive practice node");
		createPracticeApiResponse = createAgnosticPractice(clientId, "false");
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
				testData.getProperty("api.client.one.type"), clientSapNumber, ccUser.getActive(),
				ccUser.getTestClient(), ccUser.getTimezoneId(), ccUser.getDisplayName(), pracCount, finalSolutionLists);
	}
}
