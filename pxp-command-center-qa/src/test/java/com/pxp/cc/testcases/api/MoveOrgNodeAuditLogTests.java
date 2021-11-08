// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
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
import io.restassured.path.json.JsonPath;
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

	private static final String TIMEZONE = "TimeZone";
	private static final String CLIENT_TYPE = "ClientType";
	private static final String SAP_ID = "Sap-Number";
	private static final String STATUS = "Status";
	private static final String FIELD = "field";
	private static final String RESOURCE_ID = "resourceId";
	private static final String TARGETDATA = "targetData";
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

	public void validateAuditLogForMoveOrgNodeWithDoNotInherit(Response auditLogApiResponse, int orgId, String context,
			String orgNodeName, String oldParentName, String newParentName, String oldClientType, String newClientType)
			throws IOException {
		JsonPath jsonPath = new JsonPath(auditLogApiResponse.getBody().asString());
		LocalDate date = LocalDate.now();
		JSONArray jsonArray = new JSONArray(jsonPath.getList(date.toString()));
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			JSONObject targetData = jsonObject.getJSONObject(TARGETDATA);
			if (jsonObject.get(RESOURCE_ID).equals(orgId)) {
				if (targetData.getString(FIELD).equals(orgNodeName)) {
					validation.verifyAuditLogResponseBodyForMoveOrgNode(jsonObject, orgId, context, orgNodeName,
							oldParentName, newParentName);
				} else if (targetData.getString(FIELD).equals(CLIENT_TYPE)) {
					validation.verifyAuditLogResponseBodyForMoveOrgNode(jsonObject, orgId, context, CLIENT_TYPE,
							oldClientType, newClientType);
				}
			}
		}
	}

	public void verifyAuditLogResponseForMoveOrgNodeWithDoNotInherit(Response auditLogApiResponse, int orgId,
			String context, String orgNodeName, String oldParentName, String newParentName, String oldClientType,
			String newClientType) throws IOException {
		CCUtils.responseTimeValidation(auditLogApiResponse);
		CCUtils.assertEquals(auditLogApiResponse.getStatusCode(), 200);
		validateAuditLogForMoveOrgNodeWithDoNotInherit(auditLogApiResponse, orgId, context, orgNodeName, oldParentName,
				newParentName, oldClientType, newClientType);
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
				targeClientName, targeClientSapNumber, ccUser.getTestClient(), testData.getProperty("new.client.type"),
				testData.getProperty("new.timezone"));
		int targetClientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Move system node from one client to another client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(false, sourceSystemId, "SYSTEM",
				targetClientId, "CLIENT");

		logStep("Verify audit log");
		orgTypes = new ArrayList<String>();
		orgTypes.add(ORG_TYPE_SYSTEM);
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 10);

		logStep("Validating response information");
		verifyAuditLogResponseForMoveOrgNodeWithDoNotInherit(auditLogApiResponse, sourceSystemId, ORG_TYPE_SYSTEM,
				ccUser.getSystemNameApi(), clientName, targeClientName, ccUser.getClientType(),
				testData.getProperty("new.client.type"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move active agnostic practice node from one client to another client with do not inherit and verify audit log for agnostic practice node")
	public void testAuditLogForMoveAgnosticPracticeNodeWithDoNotInherit() throws IOException {
		logStep("Create active agnostic practice node below client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), testData.getProperty("new.client.type"),
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
		verifyAuditLogResponseForMoveOrgNodeWithDoNotInherit(auditLogApiResponse, sourcePracticeId, ORG_TYPE_PRACTICE,
				ccUser.getPracticeNameApi(), clientName, targeClientName, testData.getProperty("new.client.type"),
				ccUser.getClientType());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move inactive enterprise node from one system to another system of same client with do not inherit and verify audit log for enterprise node")
	public void testAuditLogForMoveEnterpriseNodeToSystemWithDoNotInherit() throws IOException {
		logStep("Create inactive enterprise node below system");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), testData.getProperty("new.client.type"),
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
		verifyAuditLogResponseForMoveOrgNodeWithDoNotInherit(auditLogApiResponse, sourceEnterpriseId,
				ORG_TYPE_ENTERPRISE, ccUser.getEnterpriseNameApi(), ccUser.getSystemNameApi(), targetSystemName,
				testData.getProperty("new.client.type"), testData.getProperty("new.client.type"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move active nge practice node from one enterprise to another enterprise of same client with do not inherit and verify audit log for nge practice node")
	public void testAuditLogForMovePracticeToEnterpriseWithDoNotInherit() throws IOException {
		logStep("Create active nge practice node below enterprise");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), testData.getProperty("new.client.type"),
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
		verifyAuditLogResponseForMoveOrgNodeWithDoNotInherit(auditLogApiResponse, sourcePracticeId, ORG_TYPE_PRACTICE,
				ccUser.getPracticeNameApi(), ccUser.getEnterpriseNameApi(), targetEnterpriseName,
				testData.getProperty("new.client.type"), testData.getProperty("new.client.type"));
	}

	public void validateAuditLogForMoveOrgNodeWithInherit(Response auditLogApiResponse, int orgId, String context,
			String orgNodeName, String oldParentName, String newParentName, String oldTimezone, String newTimezone,
			String oldSapId, String newSapId, String oldStatus, String newStatus, String oldClientType,
			String newClientType) throws IOException {
		JsonPath jsonPath = new JsonPath(auditLogApiResponse.getBody().asString());
		LocalDate date = LocalDate.now();
		JSONArray jsonArray = new JSONArray(jsonPath.getList(date.toString()));
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			JSONObject targetData = jsonObject.getJSONObject(TARGETDATA);
			if (jsonObject.get(RESOURCE_ID).equals(orgId)) {
				if (targetData.getString(FIELD).equals(orgNodeName)) {
					validation.verifyAuditLogResponseBodyForMoveOrgNode(jsonObject, orgId, context, orgNodeName,
							oldParentName, newParentName);
				} else if (targetData.getString(FIELD).equals(TIMEZONE)) {
					validation.verifyAuditLogResponseBodyForMoveOrgNode(jsonObject, orgId, context, TIMEZONE,
							oldTimezone, newTimezone);
				} else if (targetData.getString(FIELD).equals(SAP_ID)) {
					validation.verifyAuditLogResponseBodyForMoveOrgNode(jsonObject, orgId, context, SAP_ID, oldSapId,
							newSapId);
				} else if (targetData.getString(FIELD).equals(STATUS)) {
					validation.verifyAuditLogResponseBodyForMoveOrgNode(jsonObject, orgId, context, STATUS, oldStatus,
							newStatus);
				} else if (targetData.getString(FIELD).equals(CLIENT_TYPE)) {
					validation.verifyAuditLogResponseBodyForMoveOrgNode(jsonObject, orgId, context, CLIENT_TYPE,
							oldClientType, newClientType);
				}
			}
		}
	}

	public void verifyAuditLogResponseForMoveOrgNodeWithInherit(Response auditLogApiResponse, int orgId, String context,
			String orgNodeName, String oldParentName, String newParentName, String oldTimezone, String newTimezone,
			String oldSapId, String newSapId, String oldStatus, String newStatus, String oldClientType,
			String newClientType) throws IOException {
		CCUtils.responseTimeValidation(auditLogApiResponse);
		CCUtils.assertEquals(auditLogApiResponse.getStatusCode(), 200);
		validateAuditLogForMoveOrgNodeWithInherit(auditLogApiResponse, orgId, context, orgNodeName, oldParentName,
				newParentName, oldTimezone, newTimezone, oldSapId, newSapId, oldStatus, newStatus, oldClientType,
				newClientType);
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
				targeClientName, targeClientSapNumber, ccUser.getTestClient(), testData.getProperty("new.client.type"),
				testData.getProperty("new.timezone"));
		int targetClientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Move system node from one client to another client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, sourceSystemId, "SYSTEM",
				targetClientId, "CLIENT");

		logStep("Verify audit log");
		orgTypes = new ArrayList<String>();
		orgTypes.add(ORG_TYPE_SYSTEM);
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 10);

		logStep("Validating response information");
		verifyAuditLogResponseForMoveOrgNodeWithInherit(auditLogApiResponse, sourceSystemId, ORG_TYPE_SYSTEM,
				ccUser.getSystemNameApi(), clientName, targeClientName, ccUser.getSystemTimezoneIdApi(),
				testData.getProperty("new.timezone"), ccUser.getSystemSapNumberApi(), targeClientSapNumber,
				ccUser.getSystemeActiveApi(), ccUser.getActive(), ccUser.getClientType(),
				testData.getProperty("new.client.type"));
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
				targeClientName, targeClientSapNumber, ccUser.getTestClient(), testData.getProperty("new.client.type"),
				testData.getProperty("new.timezone"));
		int targetClientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Move inactive agnostic practice node from one client to another client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode(true, sourcePracticeId, "PRACTICE",
				targetClientId, "CLIENT");

		logStep("Verify audit log");
		orgTypes = new ArrayList<String>();
		orgTypes.add(ORG_TYPE_PRACTICE);
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 10);

		logStep("Validating response information");
		verifyAuditLogResponseForMoveOrgNodeWithInherit(auditLogApiResponse, sourcePracticeId, ORG_TYPE_PRACTICE,
				ccUser.getPracticeNameApi(), clientName, targeClientName, ccUser.getPracticeTimezoneIdApi(),
				testData.getProperty("new.timezone"), ccUser.getPracticeSapNumberApi(), targeClientSapNumber, "false",
				ccUser.getActive(), ccUser.getClientType(), testData.getProperty("new.client.type"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move inactive practice node from one enterprise to another enterprise of same client with inherit and verify audit log for practice node")
	public void testAuditLogForMovePracticeToEnterpriseWithInherit() throws IOException {
		logStep("Create inactive practice node below enterprise");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), testData.getProperty("new.client.type"),
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
		verifyAuditLogResponseForMoveOrgNodeWithInherit(auditLogApiResponse, sourcePracticeId, ORG_TYPE_PRACTICE,
				ccUser.getPracticeNameApi(), ccUser.getEnterpriseNameApi(), targetEnterpriseName,
				ccUser.getPracticeTimezoneIdApi(), testData.getProperty("new.timezone"), "", targetEnterpriseSapNumber,
				"false", ccUser.getEnterpriseActiveApi(), testData.getProperty("new.client.type"),
				testData.getProperty("new.client.type"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move inactive enterprise node from one system to another system of same client with inherit and verify audit log for enterprise node")
	public void testAuditLogForMoveEnterpriseNodeToSystemWithInherit() throws IOException {
		logStep("Create inactive enterprise node below system");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), testData.getProperty("new.client.type"),
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
		verifyAuditLogResponseForMoveOrgNodeWithInherit(auditLogApiResponse, sourceEnterpriseId, ORG_TYPE_ENTERPRISE,
				ccUser.getEnterpriseNameApi(), ccUser.getSystemNameApi(), targetSystemName,
				ccUser.getEnterpriseTimezoneIdApi(), testData.getProperty("new.timezone"),
				ccUser.getEnterpriseSapNumberApi(), targetSystemSapNumber, "false", ccUser.getSystemeActiveApi(),
				testData.getProperty("new.client.type"), testData.getProperty("new.client.type"));
	}
}
