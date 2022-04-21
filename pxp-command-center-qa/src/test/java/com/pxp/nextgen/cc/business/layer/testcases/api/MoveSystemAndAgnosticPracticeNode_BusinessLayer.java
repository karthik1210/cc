// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;
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

public class MoveSystemAndAgnosticPracticeNode_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	private TransferBusinessUnits transferBusinessUnits;
	Response transferBusinessUnitsApiResponse, createClientApiResponse, createOrgNodeApiResponse,
			createPracticeApiResponse, createEnterpriseApiResponse, fetchOrgDetailsApiResponse;
	List<Map<String, Object>> solutionList = null;
	List<Map<String, Object>> solutionListtwo = null;
	Map<String, Object> solutionMap;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
		transferBusinessUnits = new TransferBusinessUnits();
	}

	@DataProvider(name = "invalidCombinationOfSourceAndTargetId")
	public Object[] invalidCombinationOfSourceAndTarget() {
		Object[][] obj = new Object[][] { { "SYSTEM", "ENTERPRISE" }, { "SYSTEM", "PRACTICE" },
				{ "ENTERPRISE", "CLIENT" }, { "ENTERPRISE", "PRACTICE" }, { "PRACTICE", "SYSTEM" } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid combination of source and target type and verify error message")
	public void testErrorMessageForInvalidSourceAndTargetType() throws IOException {
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(40043, 40061, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidCombinationOfSourceAndTargetType(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move inactive system node from one client to another client with inherit")
	public void testMoveInactiveSystemNodeToAnotherClientWithInherite() throws Exception {
		logStep("Create an active client node");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Create an inactive system node below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode("false", "", "System_name_1",
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"), "", "",
				solutionList);
		int sourcesystemId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "System_name_1");

		logStep("Create target client");
		String targetclientName = testData.getProperty("api.targetclient.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String targetclientSapNumber = testData.getProperty("api.targetclient.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.targetclient.active"),
				targetclientName, targetclientSapNumber, testData.getProperty("api.targetclient.testclient"),
				testData.getProperty("api.targetclient.type"), testData.getProperty("api.targetclient.timezoneid"));
		int targetClientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Move an system node from one client to another client with inherit");
		Boolean inheritFlag = true;
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(targetClientId, sourcesystemId,
				inheritFlag);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move active system node from one client to another client with inherit")
	public void testMoveActiveSystemNodeToAnotherClientWithInherite() throws Exception {
		logStep("Create an active client node");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Create an active system node below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.system.active"), "System1",
				"System_name_1", testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), testData.getProperty("api.system.sapnumber"), "US/Central",
				solutionListtwo);
		int sourcesystemId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "System_name_1");

		logStep("Create target client ");
		String targetclientName = CCUtils
				.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.targetclient.name")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10))));
		String targetclientSapNumber = CCUtils
				.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.targetclient.sapnumber")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(15))));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.targetclient.active"),
				targetclientName, targetclientSapNumber, testData.getProperty("api.targetclient.testclient"),
				testData.getProperty("api.targetclient.type"), testData.getProperty("api.targetclient.timezoneid"));
		int targetClientId = createClientApiResponse.jsonPath().get("clientId");

		logStep("Move an system node from one client to another client with inherit");
		Boolean inheritFlag = true;
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(targetClientId, sourcesystemId,
				inheritFlag);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move inactive system node to an inactive client with inherite and verify error message")
	public void testErrorMessageForInactiveTargetClientNodeWithInherite() throws Exception {
		logStep("Create an active client node");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Create an inactive system node below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode("false", "", "System_name_1",
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"), "", "",
				solutionListtwo);
		int sourcesystemId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "System_name_1");

		logStep("Create target client ");
		String targetclientName = CCUtils
				.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.targetclient.name")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10))));
		String targetclientSapNumber = CCUtils
				.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.targetclient.sapnumber")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(15))));
		createClientApiResponse = clientInfo.createNewClient("false", targetclientName, targetclientSapNumber,
				testData.getProperty("api.targetclient.testclient"), testData.getProperty("api.targetclient.type"),
				testData.getProperty("api.targetclient.timezoneid"));
		int targetClientId = createClientApiResponse.jsonPath().get("clientId");

		logStep("Move an system node from one client to another client with inherit");
		Boolean inheritFlag = true;
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(targetClientId, sourcesystemId,
				inheritFlag);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move an system node to same client with inherite")
	public void testMoveSystemNodToSameClientWithISnherite() throws Exception {
		logStep("Create an active client node");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Create an active system node below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.system.active"), "System1",
				"System_name_1", testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), testData.getProperty("api.system.sapnumber"), "US/Central",
				solutionListtwo);
		int sourceSystemId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "System_name_1");

		logStep("Move an system node to an same client ");
		Boolean inheritFlag = true;
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(clientId, sourceSystemId, inheritFlag);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move inactive agnostic practice node from one client to another client with inherit")
	public void testMoveInactiveAgnosticPracticeNodeToClientWithInherite() throws NullPointerException, Exception {
		logStep("Create an active client node");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Create an inactive practice node with solutions below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode("false", " ", "Agnostic_Practice_Name_1",
				testData.getProperty("api.practice.orgtype"), clientId, "CLIENT", "", "", solutionList);
		int practiceId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "Agnostic_Practice_Name_1");

		logStep("Create target client ");
		String targetclientName = CCUtils
				.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.targetclient.name")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10))));
		String targetclientSapNumber = CCUtils
				.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.targetclient.sapnumber")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(15))));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.targetclient.active"),
				targetclientName, targetclientSapNumber, testData.getProperty("api.targetclient.testclient"),
				testData.getProperty("api.targetclient.type"), testData.getProperty("api.targetclient.timezoneid"));
		int targetClientId = createClientApiResponse.jsonPath().get("clientId");

		logStep("Move an agnostic practice node from one client to another client with inherit");
		Boolean inheritFlag = true;
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(targetClientId, practiceId,
				inheritFlag);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move active agnostic practice node from one client to another client with inherit")
	public void testMoveActiveAgnosticPracticeNodeToClientWithInherite() throws NullPointerException, Exception {
		logStep("Create an active client node");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Create an inactive practice node with solutions below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.practice.active"), " ",
				"Agnostic_Practice_Name_1", testData.getProperty("api.practice.orgtype"), clientId, "CLIENT", "", "",
				solutionList);
		int practiceId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "Agnostic_Practice_Name_1");

		logStep("Create target client ");
		String targetclientName = CCUtils
				.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.targetclient.name")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10))));
		String targetclientSapNumber = CCUtils
				.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.targetclient.sapnumber")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(15))));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.targetclient.active"),
				targetclientName, targetclientSapNumber, testData.getProperty("api.targetclient.testclient"),
				testData.getProperty("api.targetclient.type"), testData.getProperty("api.targetclient.timezoneid"));
		int targetClientId = createClientApiResponse.jsonPath().get("clientId");

		logStep("Move an agnostic practice node from one client to another client with inherit");
		Boolean inheritFlag = true;
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(targetClientId, practiceId,
				inheritFlag);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move an agnostic practice node to an same client with inherite")
	public void testMoveAnAgnosticPracticeNodeToSameClientWithInherite() throws NullPointerException, Exception {
		logStep("Create an active client node");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Create an inactive practice node with solutions below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.practice.active"), " ",
				"Agnostic_Practice_Name_1", testData.getProperty("api.practice.orgtype"), clientId, "CLIENT", "", "",
				solutionList);
		int practiceId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "Agnostic_Practice_Name_1");

		logStep("Move an agnostic practice node to an same client ");
		Boolean inheritFlag = true;
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(clientId, practiceId, inheritFlag);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move inactive system node from one client to another client with do not inherit")
	public void testMoveInactiveSystemNodeToAnotherClientWithDoNotInherite() throws Exception {

		logStep("Create an active client node");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Create an inactive system node below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode("false", "", "System_name_1",
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"), "", "",
				solutionList);
		int sourcesystemId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);

		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "System_name_1");
		logStep("Create target client");
		String targetclientName = testData.getProperty("api.targetclient.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String targetclientSapNumber = testData.getProperty("api.targetclient.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.targetclient.active"),
				targetclientName, targetclientSapNumber, testData.getProperty("api.targetclient.testclient"),
				testData.getProperty("api.targetclient.type"), testData.getProperty("api.targetclient.timezoneid"));
		int targetClientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Move an system node from one client to another client with inherit");
		Boolean inheritFlag = false;
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(targetClientId, sourcesystemId,
				inheritFlag);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move active system node from one client to another client with do not inherit")
	public void testMoveActiveSystemNodeToAnotherClientWithDoNotInherite() throws Exception {
		logStep("Create an active client node");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Create an active system node below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.system.active"), "System1",
				"System_name_1", testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), testData.getProperty("api.system.sapnumber"), "US/Central",
				solutionListtwo);
		int sourcesystemId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "System_name_1");

		logStep("Create target client ");
		String targetclientName = CCUtils
				.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.targetclient.name")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10))));
		String targetclientSapNumber = CCUtils
				.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.targetclient.sapnumber")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(15))));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.targetclient.active"),
				targetclientName, targetclientSapNumber, testData.getProperty("api.targetclient.testclient"),
				testData.getProperty("api.targetclient.type"), testData.getProperty("api.targetclient.timezoneid"));
		int targetClientId = createClientApiResponse.jsonPath().get("clientId");

		logStep("Move an system node from one client to another client with inherit");
		Boolean inheritFlag = false;
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(targetClientId, sourcesystemId,
				inheritFlag);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move inactive system node to an inactive client with do not inherite and verify error message")
	public void testErrorMessageForInactiveTargetClientNodeWithDoNotInherite() throws Exception {

		logStep("Create an active client node");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Create an inactive system node below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode("false", "", "System_name_1",
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"), "", "",
				solutionListtwo);
		int sourcesystemId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "System_name_1");

		logStep("Create target client ");
		String targetclientName = CCUtils
				.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.targetclient.name")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10))));
		String targetclientSapNumber = CCUtils
				.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.targetclient.sapnumber")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(15))));
		createClientApiResponse = clientInfo.createNewClient("false", targetclientName, targetclientSapNumber,
				testData.getProperty("api.targetclient.testclient"), testData.getProperty("api.targetclient.type"),
				testData.getProperty("api.targetclient.timezoneid"));
		int targetClientId = createClientApiResponse.jsonPath().get("clientId");

		logStep("Move an system node from one client to another client with inherit");
		Boolean inheritFlag = false;
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(targetClientId, sourcesystemId,
				inheritFlag);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move system node to same client with do not inherite")
	public void testMoveSystemNodeToSameClientWithDoNotInherite() throws Exception {
		logStep("Create an active client node");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Create an active system node below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.system.active"), "System1",
				"System_name_1", testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), testData.getProperty("api.system.sapnumber"), "US/Central",
				solutionListtwo);
		int sourceSystemId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "System_name_1");

		logStep("Move an system node to an same client ");
		Boolean inheritFlag = false;
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(clientId, sourceSystemId, inheritFlag);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move inactive agnostic practice node from one client to another client with do not inherit")
	public void testMoveInactiveAgnosticPracticeNodeToClientWithDoNotInherite() throws NullPointerException, Exception {
		logStep("Create an active client node");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Create an inactive practice node with solutions below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode("false", " ", "Agnostic_Practice_Name_1",
				testData.getProperty("api.practice.orgtype"), clientId, "CLIENT", "", "", solutionList);
		int practiceId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "Agnostic_Practice_Name_1");

		logStep("Create target client ");
		String targetclientName = CCUtils
				.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.targetclient.name")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10))));
		String targetclientSapNumber = CCUtils
				.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.targetclient.sapnumber")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(15))));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.targetclient.active"),
				targetclientName, targetclientSapNumber, testData.getProperty("api.targetclient.testclient"),
				testData.getProperty("api.targetclient.type"), testData.getProperty("api.targetclient.timezoneid"));
		int targetClientId = createClientApiResponse.jsonPath().get("clientId");

		logStep("Move an agnostic practice node from one client to another client with inherit");
		Boolean inheritFlag = true;
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(targetClientId, practiceId,
				inheritFlag);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move active agnostic practice node from one client to another client with do not inherit")
	public void testMoveActiveAgnosticPracticeNodeToClientWithDoNotInherite() throws NullPointerException, Exception {
		logStep("Create an active client node");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Create an inactive practice node with solutions below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.practice.active"), " ",
				"Agnostic_Practice_Name_1", testData.getProperty("api.practice.orgtype"), clientId, "CLIENT", "", "",
				solutionList);
		int practiceId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "Agnostic_Practice_Name_1");

		logStep("Create target client ");
		String targetclientName = CCUtils
				.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.targetclient.name")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10))));
		String targetclientSapNumber = CCUtils
				.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.targetclient.sapnumber")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(15))));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.targetclient.active"),
				targetclientName, targetclientSapNumber, testData.getProperty("api.targetclient.testclient"),
				testData.getProperty("api.targetclient.type"), testData.getProperty("api.targetclient.timezoneid"));
		int targetClientId = createClientApiResponse.jsonPath().get("clientId");

		logStep("Move an agnostic practice node from one client to another client with inherit");
		Boolean inheritFlag = true;
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(targetClientId, practiceId,
				inheritFlag);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to move an agnostic practice node to an same client with do not inherite")
	public void testMoveAnAgnosticPracticeNodeToSameClientWithinhDoNotInherite()
			throws NullPointerException, Exception {
		logStep("Create an active client node");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Create an inactive practice node with solutions below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.practice.active"), " ",
				"Agnostic_Practice_Name_1", testData.getProperty("api.practice.orgtype"), clientId, "CLIENT", "", "",
				solutionList);
		int practiceId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "Agnostic_Practice_Name_1");

		logStep("Move an agnostic practice node to an same client ");
		Boolean inheritFlag = false;
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrgNode_BL(clientId, practiceId, inheritFlag);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
	}
}
