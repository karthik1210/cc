// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.TransferBusinessUnits;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class MoveSystemAndAgnosticPracticeNode_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	private TransferBusinessUnits transferBusinessUnits;
	Response transferBusinessUnitsApiResponse, createClientApiResponse, createOrgNodeApiResponse,
			fetchOrgNodeApiResponse;
	int clientId, targetClientId, inactivetargetClientId;
	String clientSapNumber, targetclientSapNumber;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupQaServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
		transferBusinessUnits = new TransferBusinessUnits();

		logStep("Create an active client node");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		clientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Create target client");
		String targetclientName = testData.getProperty("api.targetclient.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		targetclientSapNumber = testData.getProperty("api.targetclient.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.targetclient.active"),
				targetclientName, targetclientSapNumber, testData.getProperty("api.targetclient.testclient"),
				testData.getProperty("api.targetclient.type"), testData.getProperty("api.targetclient.timezoneid"));
		targetClientId = createClientApiResponse.jsonPath().getInt("clientId");

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing clientId and verify error message for client id field")
	public void testErrorMessageForInvalidClientId() throws IOException {
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(40043, 4324, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForClientIdField(transferBusinessUnitsApiResponse, String.valueOf(40043));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing orgId and verify error message ")
	public void testErrorMessageForInvalidOrgId() throws IOException {
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(targetClientId, 0, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForOrgId(transferBusinessUnitsApiResponse, String.valueOf(0));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move inactive/active  system node from one client to another client with inherit.")
	public void testMoveActiveAndInactiveSystemNodeToAnotherClientWithInherit() throws Exception {
		logStep("Create an inactive system node below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL("System_name_1", testData.getProperty("api.system.orgtype"),
				"", "", "false", "", clientId, testData.getProperty("api.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int sourceSystemId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Move an system node from one client to another client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(targetClientId, sourceSystemId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		fetchOrgNodeApiResponse = orgInfo.getOrgId(sourceSystemId);
		logStep("Verify system node inherit client type,sap number,timezone and status attributes from target client node");
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, sourceSystemId, "System_name_1",
				testData.getProperty("api.targetclient.active"), testData.getProperty("api.targetclient.type"),
				targetclientSapNumber, testData.getProperty("api.targetclient.timezoneid"));

		logStep("Create an active system node below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL("System_name_2", testData.getProperty("api.system.orgtype"),
				"System1", testData.getProperty("api.system.sapnumber"), testData.getProperty("api.system.active"),
				"US/Central", clientId, testData.getProperty("api.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int sourceSystemIdOne = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Move an system node from one client to another client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(targetClientId, sourceSystemIdOne,
				true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		fetchOrgNodeApiResponse = orgInfo.getOrgId(sourceSystemIdOne);
		logStep("Verify system node inherit client type,sap number,timezone and status attributes from target client node");
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, sourceSystemIdOne, "System_name_2",
				testData.getProperty("api.targetclient.active"), testData.getProperty("api.targetclient.type"),
				targetclientSapNumber, testData.getProperty("api.targetclient.timezoneid"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move active/inactive system node to active/inactive client with do not inherit. Verify error message is displayed when user move active/inactive system node to inactive target client node")
	public void testMoveInactiveAndActiveSystemNodeActiveInactiveTargetClientNodeWithDoNotInherit() throws Exception {
		logStep("Create an inactive system node below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL("System_name_1", testData.getProperty("api.system.orgtype"),
				"", "", "false", "", clientId, testData.getProperty("api.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int sourceSystemId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Move an system node from one client to another client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(targetClientId, sourceSystemId, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		fetchOrgNodeApiResponse = orgInfo.getOrgId(sourceSystemId);
		logStep("Verify system node do not inherit client type,sap number,timezone and status attributes from target client node");
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, sourceSystemId, "System_name_1",
				"false", testData.getProperty("api.targetclient.type"), clientSapNumber,
				testData.getProperty("api.client.timezoneid"));

		logStep("Create an active system node below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL("System_name_2", testData.getProperty("api.system.orgtype"),
				"System1", "", testData.getProperty("api.system.active"), "US/Central", clientId,
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int sourceSystemIdOne = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Move an active system node from one client to another client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(targetClientId, sourceSystemIdOne,
				false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		fetchOrgNodeApiResponse = orgInfo.getOrgId(sourceSystemIdOne);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, sourceSystemIdOne, "System_name_2",
				"true", testData.getProperty("api.targetclient.type"), clientSapNumber, "US/Central");

		logStep("Move an inactive system node an inactive client and verify error message");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(
				Integer.parseInt(testData.getProperty("inactive.target.client.id")), sourceSystemId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForMovingOrgNodeToInactiveClient(transferBusinessUnitsApiResponse);

		logStep("Move an active system node an inactive client and verify error message");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(
				Integer.parseInt(testData.getProperty("inactive.target.client.id")), sourceSystemIdOne, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForMovingOrgNodeToInactiveClient(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move inactive & active agnostic practice node from one client to another client with inherit.Verify error message is displayed when user move active/inactive system node to  target client node")
	public void testMoveMoveInactiveAndActiveAgnosticPracticeNodeToClientWithInherit()
			throws NullPointerException, Exception {
		logStep("Create an inactive agnostic practice node  below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL("Agnostic_Practice_Name_1",
				testData.getProperty("api.practice.orgtype"), "", "", "false", "", clientId, "CLIENT",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int practiceId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Move an agnostic practice node from one client to another client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(targetClientId, practiceId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		fetchOrgNodeApiResponse = orgInfo.getOrgId(practiceId);
		logStep("Verify agnostic practice node inherit client type,sap number,timezone and status attributes from target client node");
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, practiceId, "Agnostic_Practice_Name_1",
				testData.getProperty("api.targetclient.active"), testData.getProperty("api.targetclient.type"),
				targetclientSapNumber, testData.getProperty("api.targetclient.timezoneid"));

		logStep("Create an active agnostic practice below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL("Agnostic_Practice_Name_1",
				testData.getProperty("api.practice.orgtype"), "", "", testData.getProperty("api.practice.active"), "",
				clientId, "CLIENT", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int practiceIdOne = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Move an agnostic practice node from one client to another client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(targetClientId, practiceIdOne, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		fetchOrgNodeApiResponse = orgInfo.getOrgId(practiceIdOne);
		logStep("Verify system node inherit client type,sap number,timezone and status attributes from target client node");
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, practiceIdOne,
				"Agnostic_Practice_Name_1", testData.getProperty("api.targetclient.active"),
				testData.getProperty("api.targetclient.type"), targetclientSapNumber,
				testData.getProperty("api.targetclient.timezoneid"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move inactive & active agnostic practice node from one client to another active/inactive client with do not inherit")
	public void testMoveInactiveAndactiveAgnosticPracticeNodeToClientWithDoNotInherit()
			throws NullPointerException, Exception {
		logStep("Create an inactive agnostic practice node");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL("Agnostic_Practice_Name_1",
				testData.getProperty("api.practice.orgtype"), "", "", "false", "", clientId, "CLIENT",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int practiceId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Move an inactive agnostic practice node from one client to another client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(targetClientId, practiceId, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		fetchOrgNodeApiResponse = orgInfo.getOrgId(practiceId);
		logStep("Verify practice node do not inherit client type,sap number,timezone and status attributes from target client node");
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, practiceId, "Agnostic_Practice_Name_1",
				"false", testData.getProperty("api.targetclient.type"), clientSapNumber,
				testData.getProperty("api.client.timezoneid"));

		logStep("Create an active agnostic practice below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL("Agnostic_Practice_Name_2",
				testData.getProperty("api.practice.orgtype"), " ", "", testData.getProperty("api.practice.active"),
				"US/Alaska", clientId, "CLIENT", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int practiceIdOne = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Move an active agnostic practice node from one client to another client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(targetClientId, practiceIdOne, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		fetchOrgNodeApiResponse = orgInfo.getOrgId(practiceIdOne);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, practiceIdOne,
				"Agnostic_Practice_Name_2", "true", testData.getProperty("api.targetclient.type"), clientSapNumber,
				"US/Alaska");

		logStep("Move an inactive agnostic practice node an inactive client and verify error message");
		transferBusinessUnitsApiResponse = transferBusinessUnits
				.moveOrgNode_BL(Integer.parseInt(testData.getProperty("inactive.target.client.id")), practiceId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForMovingOrgNodeToInactiveClient(transferBusinessUnitsApiResponse);

		logStep("Move an active agnostic practice node an inactive client and verify error message");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(
				Integer.parseInt(testData.getProperty("inactive.target.client.id")), practiceIdOne, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForMovingOrgNodeToInactiveClient(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and verify error response body")
	public void testErrorResponseForInvalidUrl() throws Exception {
		logStep("Move an system node from one client to another client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNodeUsingInvalidUrl(targetClientId,
				Integer.parseInt(testData.getProperty("active.system.id.different.client")), true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(transferBusinessUnitsApiResponse);
	}
}