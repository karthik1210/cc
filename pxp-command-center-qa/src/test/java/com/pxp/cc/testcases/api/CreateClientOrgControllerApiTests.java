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

public class CreateClientOrgControllerApiTests extends BaseRest {

	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	Response createClientApiResponse, fetchClientDetailsApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse;
	List<Integer> solutionList = null;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create new agnostic practice below client level.")
	public void testCreateAgnosticPractice() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(ccUser.getClientType()), ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		logStep("create a practice node");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), practiceName, ccUser.getPracticeOrgTypeApi(),
				Integer.parseInt(clientId), ccUser.getParentTypeApi(), practiceSapNumber,
				ccUser.getPracticeTimezoneIdApi(), solutionList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create new enterprise below agnostic client node")
	public void testCreateEnterpriseBelowClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(ccUser.getClientType()), ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new active enterprise below active client.");
		createEnterpriseApiResponse = orgInfo.createEnterpriseNodeForAgnostic(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				Integer.parseInt(clientId), ccUser.getParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 400);
		validation.verifyInvalidOrgTypeEnterpriseErrorResponsBelowClient(createEnterpriseApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create new system below agnostic client node.")
	public void testCreateSystemBelowClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(ccUser.getClientType()), ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new inactive system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
				ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new agnostic practice with solutions and verify active solution details are displayed in the client details")
	public void testClientDetailsRepresentActiveSolutions() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("create a client node");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(ccUser.getClientType()), ccUser.getTimezoneId());
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing parent id & verify response message in response body.")
	public void testErrorMessageForInvalidParentId() throws IOException {
		logStep("Enter non existing parent id");
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		logStep("create a practice node");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), practiceName, ccUser.getPracticeOrgTypeApi(),
				ccUser.getOrgInvalidId(), ccUser.getParentTypeApi(), practiceSapNumber,
				ccUser.getPracticeTimezoneIdApi(), solutionList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidParentId(createPracticeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create the agnostic practice status active below inactive client.")
	public void testActiveAgnosticPracticeBelowInactiveClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getOrgActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(ccUser.getClientType()), ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		logStep("create a practice node");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), practiceName, ccUser.getPracticeOrgTypeApi(),
				Integer.parseInt(clientId), ccUser.getParentTypeApi(), practiceSapNumber,
				ccUser.getPracticeTimezoneIdApi(), solutionList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create agnostic practice status inactive below active client.")
	public void testInactiveAgnosticPracticeBelowActiveClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(ccUser.getClientType()), ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		logStep("create a practice node");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getOrgActive(),
				ccUser.getPracticeDisplayNameApi(), practiceName, ccUser.getPracticeOrgTypeApi(),
				Integer.parseInt(clientId), ccUser.getParentTypeApi(), practiceSapNumber,
				ccUser.getPracticeTimezoneIdApi(), solutionList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create system inactive below active agnostic client.")
	public void testCreateInactiveSystemBelowActiveClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(ccUser.getClientType()), ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new inactive system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getOrgActive(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
				ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create system inactive below active agnostic client.")
	public void testCreateActiveSystemBelowInactiveClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getOrgActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(ccUser.getClientType()), ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new inactive system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
				ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values in mandatory fields & verify response message in response body.")
	public void testErrorMessageForNullValuesInMandatorFields() throws IOException {
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		logStep("create a practice node");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), null, null, ccUser.getOrgInvalidId(), null, practiceSapNumber,
				ccUser.getPracticeTimezoneIdApi(), solutionList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForNullValuesAgnosticPractice(createPracticeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = " Verify response of sap id, timezone and status value fetch from parent (client) in add new practice.")
	public void testInheritanceFunctionalityAtAgnosticPractice() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(ccUser.getClientType()), ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		logStep("create a practice node");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), practiceName, ccUser.getPracticeOrgTypeApi(),
				Integer.parseInt(clientId), ccUser.getParentTypeApi(), null, null, solutionList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
	}
}
