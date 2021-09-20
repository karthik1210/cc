// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import java.util.List;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.TransferBusinessUnits;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class MoveOrgNodeWithInheritTests extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	private TransferBusinessUnits transferBusinessUnits;
	Response transferBusinessUnitsApiResponse, createClientApiResponse, createSystemApiResponse,
			createPracticeApiResponse, createEnterpriseApiResponse, fetchOrgDetailsApiResponse;
	List<Integer> solutionList = null;

	@BeforeTest()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
		transferBusinessUnits = new TransferBusinessUnits();
	}

	@DataProvider(name = "invalidCombinationOfOrgTypes")
	public Object[] invalidCombinationOfSourceAndTarget() {
		Object[][] obj = new Object[][] { { "SYSTEM", "ENTERPRISE" }, { "SYSTEM", "PRACTICE" },
				{ "ENTERPRISE", "CLIENT" }, { "ENTERPRISE", "PRACTICE" }, { "PRACTICE", "SYSTEM" } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidCombinationOfOrgTypes", description = "Enter invalid combination of source and target type and verify error message")
	public void testErrorMessageForInvalidSourceAndTargetType(String sourceType, String targetType) throws IOException {
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, 4001, sourceType, 4003, targetType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidCombinationOfSourceAndTargetType(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move inactive system node from one client to another client with inherit")
	public void testMoveInactiveSystemNodeToAnotherClient() throws IOException {
		logStep("Create a new client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), "GBS", ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a inactive system node below client");
		createSystemApiResponse = orgInfo.createSystem("false", ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int sourceSystemId = createSystemApiResponse.jsonPath().get("id");

		logStep("Create a new target client node");
		String targetClientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String targetClientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("create a client node");
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				targetClientName, targetClientSapNumber, ccUser.getTestClient(), ccUser.getClientType(),
				ccUser.getTimezoneId());
		int targetClientId = createClientApiResponse.jsonPath().get("id");

		logStep("Move an system node from one client to another client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, sourceSystemId, "SYSTEM",
				targetClientId, "CLIENT");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);

		logStep("Verify system node inherit status, sapnumber,timezone and client type values from target client node");
		fetchOrgDetailsApiResponse = orgInfo.getOrgNodesDetails(String.valueOf(sourceSystemId));
		validation.verifySearchSystemResponseBody(fetchOrgDetailsApiResponse, String.valueOf(sourceSystemId),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), ccUser.getClientType(),
				ccUser.getSystemDisplayNameApi(), targetClientSapNumber, ccUser.getActive(),
				ccUser.getSystemTestClientApi(), ccUser.getTimezoneId(), ccUser.getDisplayName(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move active system node from one client to another client with inherit")
	public void testMoveActiveSystemNodeToAnotherClient() throws IOException {
		logStep("Create a new client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), "TSI", null);
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a system node below client");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(), " ",
				null);
		int sourceSystemId = createSystemApiResponse.jsonPath().get("id");

		logStep("Create a new target client node");
		String targetClientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String targetClientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("create a client node");
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				targetClientName, targetClientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), null);
		int targetClientId = createClientApiResponse.jsonPath().get("id");

		logStep("Move an system node from one client to another client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, sourceSystemId, "SYSTEM",
				targetClientId, "CLIENT");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);

		logStep("Verify system node inherit status, sapnumber,timezone and client type values from target client node");
		fetchOrgDetailsApiResponse = orgInfo.getOrgNodesDetails(String.valueOf(sourceSystemId));
		validation.verifySearchSystemResponseBody(fetchOrgDetailsApiResponse, String.valueOf(sourceSystemId),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), ccUser.getClientType(),
				ccUser.getSystemDisplayNameApi(), targetClientSapNumber, ccUser.getActive(),
				ccUser.getSystemTestClientApi(), null, null, false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move inactive system node to an inactive client and verify error message")
	public void testErrorMessageForInactiveTargetClientNode() throws IOException {
		logStep("Create a new active client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a inactive system node below client");
		createSystemApiResponse = orgInfo.createSystem("false", ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int sourceSystemId = createSystemApiResponse.jsonPath().get("id");

		logStep("Create a new target client node");
		String targetClientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String targetClientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient("false", ccUser.getClientId(),
				targetClientName, targetClientSapNumber, ccUser.getTestClient(), ccUser.getClientType(),
				ccUser.getTimezoneId());
		int targetClientId = createClientApiResponse.jsonPath().get("id");

		logStep("Move an inactive system node to an inactive client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, sourceSystemId, "SYSTEM",
				targetClientId, "CLIENT");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForMovingOrgNodeToInactiveClient(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move an system node to same client and verify errror message")
	public void testAlreadyExistsErrorMessageForSystemNode() throws IOException {
		logStep("Create a new active client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a new system node below client");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int sourceSystemId = createSystemApiResponse.jsonPath().get("id");

		logStep("Move an system node to an same client ");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, sourceSystemId, "SYSTEM", clientId,
				"CLIENT");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForAlreadyExistSource(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move inactive agnostic practice node from one client to another client with inherit")
	public void testMoveInactiveAgnosticPracticeNodeToClient() throws IOException {
		logStep("Create a new inactive client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient("false", ccUser.getClientId(), clientName,
				clientSapNumber, ccUser.getTestClient(), "TSI", ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a new inactive agnostic practice node below client node");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode("false", ccUser.getPracticeDisplayNameApi(),
				ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getPracticeSapNumberApi(), ccUser.getPracticeTimezoneIdApi(), solutionList);
		int sourcePracticeId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Create a new target client");
		String targetClientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String targetClientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				targetClientName, targetClientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), null);
		int targetClientId = createClientApiResponse.jsonPath().get("id");

		logStep("Move an agnostic practice node from one client to another client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, sourcePracticeId, "PRACTICE",
				targetClientId, "CLIENT");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);

		logStep("Verify agnostic practice node inherit status, sapnumber,timezone and client type values from target client node");
		fetchOrgDetailsApiResponse = orgInfo.getOrgNodesDetails(String.valueOf(sourcePracticeId));
		validation.verifySearchPracticeResponseBody(fetchOrgDetailsApiResponse, String.valueOf(sourcePracticeId),
				ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(), ccUser.getClientType(),
				ccUser.getPracticeDisplayNameApi(), targetClientSapNumber, ccUser.getActive(),
				ccUser.getPracticeTestClientApi(), null, null, false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move active agnostic practice node from one client to another client with inherit")
	public void testMoveActiveAgnosticPracticeNodeToClient() throws IOException {
		logStep("Create a new active client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a new agnostic practice node below client node");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				clientId, ccUser.getParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi(), solutionList);
		int sourcePracticeId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Create a new target client");
		String targetClientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String targetClientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				targetClientName, targetClientSapNumber, ccUser.getTestClient(), "TSI", ccUser.getTimezoneId());
		int targetClientId = createClientApiResponse.jsonPath().get("id");

		logStep("Move an agnostic practice node from one client to another client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, sourcePracticeId, "PRACTICE",
				targetClientId, "CLIENT");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);

		logStep("Verify agnostic practice node inherit status, sapnumber,timezone and client type values from target client node");
		fetchOrgDetailsApiResponse = orgInfo.getOrgNodesDetails(String.valueOf(sourcePracticeId));
		validation.verifySearchPracticeResponseBody(fetchOrgDetailsApiResponse, String.valueOf(sourcePracticeId),
				ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(), "TSI", ccUser.getPracticeDisplayNameApi(),
				targetClientSapNumber, ccUser.getActive(), ccUser.getPracticeTestClientApi(), ccUser.getTimezoneId(),
				ccUser.getDisplayName(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move an agnostic practice node to an same client and verify errror message")
	public void testAlreadyExistsErrorMessageForPracticeNode() throws IOException {
		logStep("Create a new active client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a source agnostic practice below client");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				clientId, ccUser.getParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi(), solutionList);
		int sourcePracticeId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Move an agnostic practice node to an same client ");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, sourcePracticeId, "PRACTICE",
				clientId, "CLIENT");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForAlreadyExistSource(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move enterprise node from one system to another system of same client with inherit")
	public void testMoveInactiveEnterpriseNodeToSystemOfSameClient() throws IOException {
		logStep("Create a new client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), null);
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a new system node below client");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), null);
		int systemId = createSystemApiResponse.jsonPath().get("id");

		logStep("Create a new inactive enterprise node below system");
		createEnterpriseApiResponse = orgInfo.createEnterprise("false", ccUser.getEnterpriseDisplayNameApi(),
				ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(), systemId,
				ccUser.getEnterpriseParentTypeApi(), " ", null);
		int SourceEnterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		logStep("Create a new system node below client");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int targetSystemId = createSystemApiResponse.jsonPath().get("id");

		logStep("Move enterprise node from one system to another system of same client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, SourceEnterpriseId, "ENTERPRISE",
				targetSystemId, "SYSTEM");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);

		logStep("Verify enterprise node inherit status, sapnumber,timezone and client type values from target system node");
		fetchOrgDetailsApiResponse = orgInfo.getOrgNodesDetails(String.valueOf(SourceEnterpriseId));
		validation.verifySearchEnterpriseResponseBody(fetchOrgDetailsApiResponse, String.valueOf(SourceEnterpriseId),
				ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(), ccUser.getClientType(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemeActiveApi(),
				ccUser.getEnterpriseTestClientApi(), ccUser.getSystemTimezoneIdApi(),
				ccUser.getSystemTimezoneDisplayNameApi(), ccUser.getEnterprisePracticeCountApi(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move enterprise node to another system of different client with inherit")
	public void testMoveEnterpriseNodeToSystemOfDifferentClient() throws IOException {
		logStep("Create a new client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), null);
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a new system node below client");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(), " ",
				null);
		int targetSystemId = createSystemApiResponse.jsonPath().get("id");

		logStep("Create a new enterprise node");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				ccUser.getEnterpriseParentIdApi(), ccUser.getEnterpriseParentTypeApi(),
				ccUser.getEnterpriseSapNumberApi(), ccUser.getEnterpriseTimezoneIdApi());
		int SourceEnterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		logStep("Move enterprise node from one system to another system of different client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, SourceEnterpriseId, "ENTERPRISE",
				targetSystemId, "SYSTEM");
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);

		logStep("Verify enterprise node inherit status, sapnumber,timezone and client type values from target system node of different client");
		fetchOrgDetailsApiResponse = orgInfo.getOrgNodesDetails(String.valueOf(SourceEnterpriseId));
		validation.verifySearchEnterpriseResponseBody(fetchOrgDetailsApiResponse, String.valueOf(SourceEnterpriseId),
				ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(), ccUser.getClientType(),
				ccUser.getEnterpriseDisplayNameApi(), "", ccUser.getSystemeActiveApi(),
				ccUser.getEnterpriseTestClientApi(), null, null, ccUser.getEnterprisePracticeCountApi(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move an active enterprise node to an inactive system and verify error message")
	public void testErrorMessageForInactiveTargetSystem() throws IOException {
		logStep("Create a new client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a inactive system node below client");
		createSystemApiResponse = orgInfo.createSystem("false", ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int targetSystemId = createSystemApiResponse.jsonPath().get("id");

		logStep("Create a new enterprise node");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				ccUser.getEnterpriseParentIdApi(), ccUser.getEnterpriseParentTypeApi(),
				ccUser.getEnterpriseSapNumberApi(), ccUser.getEnterpriseTimezoneIdApi());
		int SourceEnterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		logStep("Move an active enterprise node to an inactive system and verify error message");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, SourceEnterpriseId, "ENTERPRISE",
				targetSystemId, "SYSTEM");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForMovingOrgNodeToInactiveTarget(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move an enterprise node to an same system and verify errror message")
	public void testAlreadyExistsErrorMessageForEnterpriseNode() throws IOException {
		logStep("Create a new client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a system node below client");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int systemId = createSystemApiResponse.jsonPath().get("id");

		logStep("Create a new enterprise node");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				systemId, ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());
		int SourceEnterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		logStep("Move an enterprise node to an same system");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, SourceEnterpriseId, "ENTERPRISE",
				systemId, "SYSTEM");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForAlreadyExistSource(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move nge practice node to another enterprise of different client with inherit")
	public void testMoveInactiveNgePracticeNodeToEnterprise() throws IOException {
		logStep("Create a new inactive nge practice node");
		createPracticeApiResponse = orgInfo.createPractice("false", ccUser.getPracticeDisplayNameApi(),
				ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(), ccUser.getPracticeParentIdApi(),
				ccUser.getPracticeParentTypeApi(), " ", null);
		int sourcePracticeId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Create a new enterprise node");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				ccUser.getEnterpriseParentIdApi(), ccUser.getEnterpriseParentTypeApi(),
				ccUser.getEnterpriseSapNumberApi(), ccUser.getEnterpriseTimezoneIdApi());
		int targetEnterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		logStep("Move an nge practice node to another enterprise with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, sourcePracticeId, "PRACTICE",
				targetEnterpriseId, "ENTERPRISE");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);

		logStep("Verify nge practice node inherit status, sapnumber,timezone and client type values from target enterprise node");
		fetchOrgDetailsApiResponse = orgInfo.getOrgNodesDetails(String.valueOf(sourcePracticeId));
		validation.verifySearchPracticeResponseBody(fetchOrgDetailsApiResponse, String.valueOf(sourcePracticeId),
				ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(), "Direct Client",
				ccUser.getPracticeDisplayNameApi(), ccUser.getEnterpriseSapNumberApi(), ccUser.getEnterpriseActiveApi(),
				ccUser.getPracticeTestClientApi(), ccUser.getEnterpriseTimezoneIdApi(),
				ccUser.getEnterpriseTimezoneDisplayNameApi(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move nge practice node from one enterprise to another enterprise of same client with inherit")
	public void testMoveNgePracticeNodeToEnterpriseOfSameClient() throws IOException {
		logStep("Create a new client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a new system node below client");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int systemId = createSystemApiResponse.jsonPath().get("id");

		logStep("Create a new enterprise node below system");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				systemId, ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());
		int enterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		logStep("Create a new nge practice node below enterprise");
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				enterpriseId, ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());
		int sourcePracticeId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Create a new enterprise node below system");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				systemId, ccUser.getEnterpriseParentTypeApi(), " ", null);
		int targetEnterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		logStep("move nge practice node from one enterprise to another enterprise of same client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, sourcePracticeId, "PRACTICE",
				targetEnterpriseId, "ENTERPRISE");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);

		logStep("Verify nge practice node inherit status, sapnumber,timezone and client type values from target enterprise node");
		fetchOrgDetailsApiResponse = orgInfo.getOrgNodesDetails(String.valueOf(sourcePracticeId));
		validation.verifySearchPracticeResponseBody(fetchOrgDetailsApiResponse, String.valueOf(sourcePracticeId),
				ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(), ccUser.getClientType(),
				ccUser.getPracticeDisplayNameApi(), "", ccUser.getEnterpriseActiveApi(), ccUser.getTestClient(),
				ccUser.getSystemTimezoneIdApi(), ccUser.getSystemTimezoneDisplayNameApi(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move nge practice node to client with inherit")
	public void testMoveInactiveNgePracticeNodeToSameClient() throws IOException {
		logStep("Create a new active client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), null);
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a system node below client");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int systemId = createSystemApiResponse.jsonPath().get("id");

		logStep("Create a new enterprise node");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				systemId, ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());
		int enterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		logStep("Create a new inactive nge practice node");
		createPracticeApiResponse = orgInfo.createPractice("false", ccUser.getPracticeDisplayNameApi(),
				ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(), enterpriseId,
				ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(), ccUser.getPracticeTimezoneIdApi());
		int sourcePracticeId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Move an nge practice node to client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, sourcePracticeId, "PRACTICE",
				clientId, "CLIENT");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);

		logStep("Verify nge practice node inherit status, sapnumber,timezone and client type values from target client node");
		fetchOrgDetailsApiResponse = orgInfo.getOrgNodesDetails(String.valueOf(sourcePracticeId));
		validation.verifySearchPracticeResponseBody(fetchOrgDetailsApiResponse, String.valueOf(sourcePracticeId),
				ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(), ccUser.getClientType(),
				ccUser.getPracticeDisplayNameApi(), clientSapNumber, ccUser.getActive(), ccUser.getTestClient(), null,
				null, false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move an nge practice node to same enterprise with inherit and verify error message")
	public void testAlreadyExistsErrorForNgePracticeNode() throws IOException {
		logStep("Create a new enterprise node");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				ccUser.getEnterpriseParentIdApi(), ccUser.getEnterpriseParentTypeApi(),
				ccUser.getEnterpriseSapNumberApi(), ccUser.getEnterpriseTimezoneIdApi());
		int enterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		logStep("Create a new nge practice node");
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				enterpriseId, ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());
		int sourcePracticeId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Move an nge practice node to same enterprise with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, sourcePracticeId, "PRACTICE",
				enterpriseId, "ENTERPRISE");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForAlreadyExistSource(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move an nge practice node to inactive enterprise with inherit and verify error message")
	public void testErrorMessageForInactiveTargetEnterprise() throws IOException {
		logStep("Create a new nge practice node");
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				ccUser.getPracticeParentIdApi(), ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());
		int sourcePracticeId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Create a new enterprise node");
		createEnterpriseApiResponse = orgInfo.createEnterprise("false", ccUser.getEnterpriseDisplayNameApi(),
				ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(), ccUser.getEnterpriseParentIdApi(),
				ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());
		int targetEnterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		logStep("Move an nge practice node to inactive enterprise with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, sourcePracticeId, "PRACTICE",
				targetEnterpriseId, "ENTERPRISE");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForMovingOrgNodeToInactiveTarget(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move agnostic practice node to enterprise node with inherit")
	public void testMoveAgnosticPracticeNodeToEnterprise() throws IOException {
		logStep("Create a new enterprise node");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				ccUser.getEnterpriseParentIdApi(), ccUser.getEnterpriseParentTypeApi(),
				ccUser.getEnterpriseSapNumberApi(), ccUser.getEnterpriseTimezoneIdApi());
		int targetEnterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		logStep("Create a new active client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a new agnostic practice node below client node");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				clientId, ccUser.getParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi(), solutionList);
		int sourcePracticeId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Move an agnostic practice node to another enterprise with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, sourcePracticeId, "PRACTICE",
				targetEnterpriseId, "ENTERPRISE");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);

		logStep("Verify agnostic practice node inherit status, sapnumber,timezone and client type values from target enterprise node");
		fetchOrgDetailsApiResponse = orgInfo.getOrgNodesDetails(String.valueOf(sourcePracticeId));
		validation.verifySearchPracticeResponseBody(fetchOrgDetailsApiResponse, String.valueOf(sourcePracticeId),
				ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(), ccUser.getClientTypeApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getEnterpriseSapNumberApi(), ccUser.getEnterpriseActiveApi(),
				ccUser.getPracticeTestClientApi(), ccUser.getEnterpriseTimezoneIdApi(),
				ccUser.getEnterpriseTimezoneDisplayNameApi(), false);
	}

}
