// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import java.util.List;
import org.testng.annotations.BeforeClass;
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

public class MoveOrgNodeWithDoNotInheritTests extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	private TransferBusinessUnits transferBusinessUnits;
	Response transferBusinessUnitsApiResponse, createClientApiResponse, createSystemApiResponse,
			createPracticeApiResponse, createEnterpriseApiResponse;
	List<Integer> solutionList = null;

	@BeforeClass()
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
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, 4001, sourceType, 4003, targetType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidCombinationOfSourceAndTargetType(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move active system node from one client to another client with do not inherit")
	public void testMoveSystemNodeToAnotherClient() throws IOException {
		logStep("Create a new client node");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a system node below client");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int sourceSystemId = createSystemApiResponse.jsonPath().get("id");

		logStep("Create a new target client node");
		String targetClientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String targetClientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("create a client node");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(),
				targetClientName, targetClientSapNumber, ccUser.getTestClient(), ccUser.getClientType(),
				ccUser.getTimezoneId());
		int targetClientId = createClientApiResponse.jsonPath().get("id");

		logStep("Move system node from one client to another client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, sourceSystemId, "SYSTEM",
				targetClientId, "CLIENT");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move an active system node to inactive client and verify error message")
	public void testErrorMessageForInactiveTargetClientNode() throws IOException {
		logStep("Create a new active client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a active system node below client");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int sourceSystemId = createSystemApiResponse.jsonPath().get("id");

		logStep("Create a new target client node");
		String targetClientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String targetClientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient("false", 
				targetClientName, targetClientSapNumber, ccUser.getTestClient(), ccUser.getClientType(),
				ccUser.getTimezoneId());
		int targetClientId = createClientApiResponse.jsonPath().get("id");

		logStep("Move an active system node to inactive client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, sourceSystemId, "SYSTEM",
				targetClientId, "CLIENT");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForMovingOrgNodeToInactiveClient(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move a system node to same client and verify errror message")
	public void testAlreadyExistsErrorMessageForSystemNode() throws IOException {
		logStep("Create a new active client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a new system node below client");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int sourceSystemId = createSystemApiResponse.jsonPath().get("id");

		logStep("Move a system node to same client ");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, sourceSystemId, "SYSTEM", clientId,
				"CLIENT");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForAlreadyExistSource(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move an agnostic practice node to same client and verify errror message")
	public void testAlreadyExistsErrorMessageForPracticeNode() throws IOException {
		logStep("Create a new active client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a source agnostic practice below client");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				clientId, ccUser.getParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi(), solutionList);
		int sourcePracticeId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Move an agnostic practice node to same client ");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, sourcePracticeId, "PRACTICE",
				clientId, "CLIENT");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForAlreadyExistSource(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move an active agnostic practice node to inactive client and verify error message")
	public void testErrorMessageForInactiveTargetClient() throws IOException {
		logStep("Create a new active client");
		String sourceClientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String sourceClientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(),
				sourceClientName, sourceClientSapNumber, ccUser.getTestClient(), ccUser.getClientType(),
				ccUser.getTimezoneId());
		int sourceClientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a new agnostic practice node below client node");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				sourceClientId, ccUser.getParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi(), solutionList);
		int sourcePracticeId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Create a new inactive client");
		String targetClientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String targetClientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient("false", 
				targetClientName, targetClientSapNumber, ccUser.getTestClient(), ccUser.getClientType(),
				ccUser.getTimezoneId());
		int targetClientId = createClientApiResponse.jsonPath().get("id");

		logStep("Move an active agnostic practice node to inactive client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, sourcePracticeId, "PRACTICE",
				targetClientId, "CLIENT");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForMovingOrgNodeToInactiveClient(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move active agnostic practice node from one client to another client with do not inherit")
	public void testMoveAgnosticPracticeNodeToAnotherClient() throws IOException {
		logStep("Create a new active client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(),
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
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(),
				targetClientName, targetClientSapNumber, ccUser.getTestClient(), ccUser.getClientType(),
				ccUser.getTimezoneId());
		int targetClientId = createClientApiResponse.jsonPath().get("id");

		logStep("Move an agnostic practice node from one client to another client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, sourcePracticeId, "PRACTICE",
				targetClientId, "CLIENT");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move nge practice node to another enterprise of different client with do not inherit")
	public void testMoveNgePracticeNodeToEnterprise() throws IOException {
		logStep("Create a new nge practice node");
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				ccUser.getPracticeParentIdApi(), ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());
		int sourcePracticeId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Create a new enterprise node");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				ccUser.getEnterpriseParentIdApi(), ccUser.getEnterpriseParentTypeApi(),
				ccUser.getEnterpriseSapNumberApi(), ccUser.getEnterpriseTimezoneIdApi());
		int targetEnterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		logStep("Move an nge practice node to another enterprise with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, sourcePracticeId, "PRACTICE",
				targetEnterpriseId, "ENTERPRISE");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move an nge practice node to same enterprise with do not inherit and verify error message")
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

		logStep("Move an nge practice node to same enterprise with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, sourcePracticeId, "PRACTICE",
				enterpriseId, "ENTERPRISE");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForAlreadyExistSource(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move an nge practice node to inactive enterprise with do not inherit and verify error message")
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

		logStep("Move an nge practice node to inactive enterprise with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, sourcePracticeId, "PRACTICE",
				targetEnterpriseId, "ENTERPRISE");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForMovingOrgNodeToInactiveTarget(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move enterprise node to another system of different client with do not inherit")
	public void testMoveEnterpriseNodeToSystemOfDifferentClient() throws IOException {
		logStep("Create a new client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a new system node below client");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int targetSystemId = createSystemApiResponse.jsonPath().get("id");

		logStep("Create a new enterprise node");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				ccUser.getEnterpriseParentIdApi(), ccUser.getEnterpriseParentTypeApi(),
				ccUser.getEnterpriseSapNumberApi(), ccUser.getEnterpriseTimezoneIdApi());
		int SourceEnterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, SourceEnterpriseId, "ENTERPRISE",
				targetSystemId, "SYSTEM");
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move an enterprise node to an same system and verify errror message")
	public void testAlreadyExistsErrorMessageForEnterpriseNode() throws IOException {
		logStep("Create a new client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(),
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

		logStep("Move an agnostic practice node to an same client ");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, SourceEnterpriseId, "ENTERPRISE",
				systemId, "SYSTEM");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForAlreadyExistSource(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move an active enterprise node to an inactive system and verify error message")
	public void testErrorMessageForInactiveTargetSystem() throws IOException {
		logStep("Create a new client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(),
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
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, SourceEnterpriseId, "ENTERPRISE",
				targetSystemId, "SYSTEM");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForMovingOrgNodeToInactiveTarget(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move agnostic practice node to enterprise node with do not inherit")
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
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a new agnostic practice node below client node");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				clientId, ccUser.getParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi(), solutionList);
		int sourcePracticeId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Move an nge practice node to enterprise with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, sourcePracticeId, "PRACTICE",
				targetEnterpriseId, "ENTERPRISE");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move nge practice node to client with do not inherit")
	public void testMoveNgePracticeNodeToClient() throws IOException {
		logStep("Create a new active client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(),
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
		int enterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		logStep("Create a new nge practice node");
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				enterpriseId, ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());
		int sourcePracticeId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Move an nge practice node to client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, sourcePracticeId, "PRACTICE",
				clientId, "CLIENT");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move enterprise node from one system to another system of same client with do not inherit")
	public void testMoveEnterpriseNodeToSystemOfSameClient() throws IOException {
		logStep("Create a new client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		logStep("Create a new system node below client");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int systemId = createSystemApiResponse.jsonPath().get("id");

		logStep("Create a new enterprise node below system");
		createEnterpriseApiResponse = orgInfo.createEnterprise("false", ccUser.getEnterpriseDisplayNameApi(),
				ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(), systemId,
				ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());
		int SourceEnterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		logStep("Create a new system node below client");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int targetSystemId = createSystemApiResponse.jsonPath().get("id");

		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, SourceEnterpriseId, "ENTERPRISE",
				targetSystemId, "SYSTEM");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move nge practice node from one enterprise to another enterprise of same client with do not inherit")
	public void testMoveNgePracticeNodeToEnterpriseOfSameClient() throws IOException {
		logStep("Create a new client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(),
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
		createPracticeApiResponse = orgInfo.createPractice("false", ccUser.getPracticeDisplayNameApi(),
				ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(), enterpriseId,
				ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(), ccUser.getPracticeTimezoneIdApi());
		int sourcePracticeId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Create a new enterprise node below system");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				systemId, ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());
		int targetEnterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, sourcePracticeId, "PRACTICE",
				targetEnterpriseId, "ENTERPRISE");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
	}
}
