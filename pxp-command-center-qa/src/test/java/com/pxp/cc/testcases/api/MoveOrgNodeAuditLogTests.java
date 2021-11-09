// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.AuditLog;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.TransferBusinessUnits;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class MoveOrgNodeAuditLogTests extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	private AuditLog auditLog;
	private TransferBusinessUnits transferBusinessUnits;
	Response transferBusinessUnitsApiResponse, createClientApiResponse, createSystemApiResponse,
			createPracticeApiResponse, createEnterpriseApiResponse, auditLogApiResponse;
	List<String> orgTypes = null;
	List<Integer> solutionList = null;

	private static final String ORG_TYPE_SYSTEM = "SYSTEM";
	private static final String ORG_TYPE_ENTERPRISE = "ENTERPRISE";
	private static final String ORG_TYPE_PRACTICE = "ORG";

	@BeforeTest()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
		transferBusinessUnits = new TransferBusinessUnits();
		auditLog = new AuditLog();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move active system node from one client to another client with do not inherit and verify audit log for system node")
	public void testAuditLogForMoveSystemNodeWithDoNotInherit() throws IOException {
		logStep("Create a active system node below client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(),
				createClientApiResponse.jsonPath().getInt("id"), ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int sourceSystemId = createSystemApiResponse.jsonPath().getInt("id");

		logStep("Create a new target client node");
		String targeClientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String targeClientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				targeClientName, targeClientSapNumber, ccUser.getTestClient(),
				testData.getProperty("api.client.five.type"), testData.getProperty("new.timezone"));
		int targetClientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Move system node from one client to another client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, sourceSystemId, "SYSTEM",
				targetClientId, "CLIENT");

		logStep("Verify audit log");
		orgTypes = new ArrayList<String>();
		orgTypes.add(ORG_TYPE_SYSTEM);
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 10);

		logStep("Validating response information");
		validation.verifyAuditLogResponseForMoveOrgNodeWithDoNotInherit(auditLogApiResponse, sourceSystemId,
				ORG_TYPE_SYSTEM, ccUser.getSystemNameApi(), clientName, targeClientName, ccUser.getClientType(),
				testData.getProperty("api.client.five.type"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move active agnostic practice node from one client to another client with do not inherit and verify audit log for agnostic practice node")
	public void testAuditLogForMoveAgnosticPracticeNodeWithDoNotInherit() throws IOException {
		logStep("Create active agnostic practice node below client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), testData.getProperty("api.client.five.type"),
				ccUser.getTimezoneId());
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				createClientApiResponse.jsonPath().getInt("id"), ccUser.getParentTypeApi(),
				ccUser.getPracticeSapNumberApi(), ccUser.getPracticeTimezoneIdApi(), solutionList);
		int sourcePracticeId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Create a new target client node");
		String targeClientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String targeClientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				targeClientName, targeClientSapNumber, ccUser.getTestClient(), ccUser.getClientType(),
				testData.getProperty("new.timezone"));
		int targetClientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Move practice node from one client to another client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, sourcePracticeId, "PRACTICE",
				targetClientId, "CLIENT");

		logStep("Verify audit log");
		orgTypes = new ArrayList<String>();
		orgTypes.add(ORG_TYPE_PRACTICE);
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 10);

		logStep("Validating response information");
		validation.verifyAuditLogResponseForMoveOrgNodeWithDoNotInherit(auditLogApiResponse, sourcePracticeId,
				ORG_TYPE_PRACTICE, ccUser.getPracticeNameApi(), clientName, targeClientName,
				testData.getProperty("api.client.five.type"), ccUser.getClientType());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move inactive enterprise node from one system to another system of same client with do not inherit and verify audit log for enterprise node")
	public void testAuditLogForMoveEnterpriseNodeToSystemWithDoNotInherit() throws IOException {
		logStep("Create inactive enterprise node below system");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), testData.getProperty("api.client.five.type"),
				ccUser.getTimezoneId());
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(),
				createClientApiResponse.jsonPath().getInt("id"), ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		createEnterpriseApiResponse = orgInfo.createEnterprise("false", ccUser.getEnterpriseDisplayNameApi(),
				ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				createSystemApiResponse.jsonPath().getInt("id"), ccUser.getEnterpriseParentTypeApi(),
				ccUser.getEnterpriseSapNumberApi(), ccUser.getEnterpriseTimezoneIdApi());
		int sourceEnterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		logStep("Create a new target system node");
		String targetSystemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		String targetSystemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				targetSystemName, ccUser.getSystemOrgTypeApi(), createClientApiResponse.jsonPath().getInt("id"),
				ccUser.getParentTypeApi(), targetSystemSapNumber, ccUser.getSystemTimezoneIdApi());
		int targetSystemId = createSystemApiResponse.jsonPath().getInt("id");

		logStep("Move inactive enterprise node from one system to another system of same client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, sourceEnterpriseId, "ENTERPRISE",
				targetSystemId, "SYSTEM");

		logStep("Verify audit log");
		orgTypes = new ArrayList<String>();
		orgTypes.add(ORG_TYPE_ENTERPRISE);
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 10);

		logStep("Validating response information");
		validation.verifyAuditLogResponseForMoveOrgNodeWithDoNotInherit(auditLogApiResponse, sourceEnterpriseId,
				ORG_TYPE_ENTERPRISE, ccUser.getEnterpriseNameApi(), ccUser.getSystemNameApi(), targetSystemName,
				testData.getProperty("api.client.five.type"), testData.getProperty("api.client.five.type"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move active nge practice node from one enterprise to another enterprise of same client with do not inherit and verify audit log for nge practice node")
	public void testAuditLogForMovePracticeToEnterpriseWithDoNotInherit() throws IOException {
		logStep("Create active nge practice node below enterprise");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), testData.getProperty("api.client.five.type"),
				ccUser.getTimezoneId());
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(),
				createClientApiResponse.jsonPath().getInt("id"), ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				createSystemApiResponse.jsonPath().getInt("id"), ccUser.getEnterpriseParentTypeApi(),
				ccUser.getEnterpriseSapNumberApi(), ccUser.getEnterpriseTimezoneIdApi());
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				createEnterpriseApiResponse.jsonPath().getInt("id"), ccUser.getPracticeParentTypeApi(),
				ccUser.getPracticeSapNumberApi(), ccUser.getPracticeTimezoneIdApi());
		int sourcePracticeId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Create a new target enterprise node");
		String targetEnterpriseName = ccUser.getEnterpriseNameApi().concat(CCUtils.generateRandomString());
		String targetEnterpriseSapNumber = ccUser.getEnterpriseSapNumberApi().concat(CCUtils.generateRandomString());
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), targetEnterpriseName, ccUser.getEnterpriseOrgTypeApi(),
				createSystemApiResponse.jsonPath().getInt("id"), ccUser.getEnterpriseParentTypeApi(),
				targetEnterpriseSapNumber, ccUser.getEnterpriseTimezoneIdApi());
		int targetEnterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		logStep("Move practice node from one client to another client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, sourcePracticeId, "PRACTICE",
				targetEnterpriseId, "ENTERPRISE");

		logStep("Verify audit log");
		orgTypes = new ArrayList<String>();
		orgTypes.add(ORG_TYPE_PRACTICE);
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 10);

		logStep("Validating response information");
		validation.verifyAuditLogResponseForMoveOrgNodeWithDoNotInherit(auditLogApiResponse, sourcePracticeId,
				ORG_TYPE_PRACTICE, ccUser.getPracticeNameApi(), ccUser.getEnterpriseNameApi(), targetEnterpriseName,
				testData.getProperty("api.client.five.type"), testData.getProperty("api.client.five.type"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move active system node from one client to another client with inherit and verify audit log for system node")
	public void testAuditLogForMoveSystemNodeToAnotherClientWithInherit() throws IOException {
		logStep("Create a active system node below client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(),
				createClientApiResponse.jsonPath().getInt("id"), ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int sourceSystemId = createSystemApiResponse.jsonPath().getInt("id");

		logStep("Create a new target client node");
		String targeClientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String targeClientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				targeClientName, targeClientSapNumber, ccUser.getTestClient(),
				testData.getProperty("api.client.five.type"), testData.getProperty("new.timezone"));
		int targetClientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Move system node from one client to another client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, sourceSystemId, "SYSTEM",
				targetClientId, "CLIENT");

		logStep("Verify audit log");
		orgTypes = new ArrayList<String>();
		orgTypes.add(ORG_TYPE_SYSTEM);
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 10);

		logStep("Validating response information");
		validation.verifyAuditLogResponseForMoveOrgNodeWithInherit(auditLogApiResponse, sourceSystemId, ORG_TYPE_SYSTEM,
				ccUser.getSystemNameApi(), clientName, targeClientName, ccUser.getSystemTimezoneIdApi(),
				testData.getProperty("new.timezone"), ccUser.getSystemSapNumberApi(), targeClientSapNumber,
				ccUser.getSystemeActiveApi(), ccUser.getActive(), ccUser.getClientType(),
				testData.getProperty("api.client.five.type"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move inactive agnostic practice node from one client to another client with inherit and verify audit log for agnostic practice node")
	public void testAuditLogForMoveAgnosticPracticeToClientWithInherit() throws IOException {
		logStep("Create inactive agnostic practice node below client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode("false", ccUser.getPracticeDisplayNameApi(),
				ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				createClientApiResponse.jsonPath().getInt("id"), ccUser.getParentTypeApi(),
				ccUser.getPracticeSapNumberApi(), ccUser.getPracticeTimezoneIdApi(), solutionList);
		int sourcePracticeId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Create a new target client node");
		String targeClientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String targeClientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				targeClientName, targeClientSapNumber, ccUser.getTestClient(),
				testData.getProperty("api.client.five.type"), testData.getProperty("new.timezone"));
		int targetClientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Move inactive agnostic practice node from one client to another client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, sourcePracticeId, "PRACTICE",
				targetClientId, "CLIENT");

		logStep("Verify audit log");
		orgTypes = new ArrayList<String>();
		orgTypes.add(ORG_TYPE_PRACTICE);
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 10);

		logStep("Validating response information");
		validation.verifyAuditLogResponseForMoveOrgNodeWithInherit(auditLogApiResponse, sourcePracticeId,
				ORG_TYPE_PRACTICE, ccUser.getPracticeNameApi(), clientName, targeClientName,
				ccUser.getPracticeTimezoneIdApi(), testData.getProperty("new.timezone"),
				ccUser.getPracticeSapNumberApi(), targeClientSapNumber, "false", ccUser.getActive(),
				ccUser.getClientType(), testData.getProperty("api.client.five.type"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move inactive practice node from one enterprise to another enterprise of same client with inherit and verify audit log for practice node")
	public void testAuditLogForMovePracticeToEnterpriseWithInherit() throws IOException {
		logStep("Create inactive practice node below enterprise");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), testData.getProperty("api.client.five.type"),
				ccUser.getTimezoneId());
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(),
				createClientApiResponse.jsonPath().getInt("id"), ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		createEnterpriseApiResponse = orgInfo.createEnterprise("false", ccUser.getEnterpriseDisplayNameApi(),
				ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				createSystemApiResponse.jsonPath().getInt("id"), ccUser.getEnterpriseParentTypeApi(),
				ccUser.getEnterpriseSapNumberApi(), ccUser.getEnterpriseTimezoneIdApi());
		createPracticeApiResponse = orgInfo.createPractice("false", ccUser.getPracticeDisplayNameApi(),
				ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				createEnterpriseApiResponse.jsonPath().getInt("id"), ccUser.getPracticeParentTypeApi(), " ",
				ccUser.getPracticeTimezoneIdApi());
		int sourcePracticeId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Create a new target system node");
		String targetEnterpriseName = ccUser.getEnterpriseNameApi().concat(CCUtils.generateRandomString());
		String targetEnterpriseSapNumber = ccUser.getEnterpriseSapNumberApi().concat(CCUtils.generateRandomString());
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), targetEnterpriseName, ccUser.getEnterpriseOrgTypeApi(),
				createSystemApiResponse.jsonPath().getInt("id"), ccUser.getEnterpriseParentTypeApi(),
				targetEnterpriseSapNumber, testData.getProperty("new.timezone"));
		int targetEnterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		logStep("Move inactive practice node from one enterprise to another enterprise of same client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, sourcePracticeId, "PRACTICE",
				targetEnterpriseId, "ENTERPRISE");

		logStep("Verify audit log");
		orgTypes = new ArrayList<String>();
		orgTypes.add(ORG_TYPE_PRACTICE);
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 10);

		logStep("Validating response information");
		validation.verifyAuditLogResponseForMoveOrgNodeWithInherit(auditLogApiResponse, sourcePracticeId,
				ORG_TYPE_PRACTICE, ccUser.getPracticeNameApi(), ccUser.getEnterpriseNameApi(), targetEnterpriseName,
				ccUser.getPracticeTimezoneIdApi(), testData.getProperty("new.timezone"), "", targetEnterpriseSapNumber,
				"false", ccUser.getEnterpriseActiveApi(), testData.getProperty("api.client.five.type"),
				testData.getProperty("api.client.five.type"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move inactive enterprise node from one system to another system of same client with inherit and verify audit log for enterprise node")
	public void testAuditLogForMoveEnterpriseNodeToSystemWithInherit() throws IOException {
		logStep("Create inactive enterprise node below system");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), testData.getProperty("api.client.five.type"),
				ccUser.getTimezoneId());
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(),
				createClientApiResponse.jsonPath().getInt("id"), ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		createEnterpriseApiResponse = orgInfo.createEnterprise("false", ccUser.getEnterpriseDisplayNameApi(),
				ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				createSystemApiResponse.jsonPath().getInt("id"), ccUser.getEnterpriseParentTypeApi(),
				ccUser.getEnterpriseSapNumberApi(), ccUser.getEnterpriseTimezoneIdApi());
		int sourceEnterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		logStep("Create a new target system node");
		String targetSystemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		String targetSystemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				targetSystemName, ccUser.getSystemOrgTypeApi(), createClientApiResponse.jsonPath().getInt("id"),
				ccUser.getParentTypeApi(), targetSystemSapNumber, testData.getProperty("new.timezone"));
		int targetSystemId = createSystemApiResponse.jsonPath().getInt("id");

		logStep("Move inactive enterprise node from one system to another system of same client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, sourceEnterpriseId, "ENTERPRISE",
				targetSystemId, "SYSTEM");

		logStep("Verify audit log");
		orgTypes = new ArrayList<String>();
		orgTypes.add(ORG_TYPE_ENTERPRISE);
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 20);

		logStep("Validating response information");
		validation.verifyAuditLogResponseForMoveOrgNodeWithInherit(auditLogApiResponse, sourceEnterpriseId,
				ORG_TYPE_ENTERPRISE, ccUser.getEnterpriseNameApi(), ccUser.getSystemNameApi(), targetSystemName,
				ccUser.getEnterpriseTimezoneIdApi(), testData.getProperty("new.timezone"),
				ccUser.getEnterpriseSapNumberApi(), targetSystemSapNumber, "false", ccUser.getSystemeActiveApi(),
				testData.getProperty("api.client.five.type"), testData.getProperty("api.client.five.type"));
	}
}
