// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
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

public class MoveEnterpriseAndPracticeNodesApiTests_BusinessLayer extends BaseRest {
	private OrgInfo orgInfo;
	private ClientInfo clientInfo;
	private Validations validation;
	private TransferBusinessUnits transferBusinessUnits;
	Response transferBusinessUnitsApiResponse, fetchClientDetailsApiReponse, fetchOrgNodeApiResponseOne,
			createOrgNodeApiResponse, fetchOrgNodeApiResponse;
	int clientId, targetClientId, inactivetargetClientId;
	String clientType, sapNumber, timezone, status, targetNodeClientType, targetNodeSapNumber, targetNodeTimezone,
			targetNodeStatus;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		orgInfo = new OrgInfo();
		clientInfo = new ClientInfo();
		validation = new Validations();
		transferBusinessUnits = new TransferBusinessUnits();
	}

	@DataProvider(name = "getOrgNodeDetails")
	public Object[] getDetails() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("parent.system.id"), testData.getProperty("parent.enterprise.id"),
						testData.getProperty("inherit") },
				{ testData.getProperty("parent.system.id"), testData.getProperty("parent.enterprise.id"),
						testData.getProperty("do.not.inherit") },
				{ testData.getProperty("parent.enterprise.id"), testData.getProperty("source.nge.practice.id"),
						testData.getProperty("inherit") },
				{ testData.getProperty("parent.enterprise.id"), testData.getProperty("source.nge.practice.id"),
						testData.getProperty("do.not.inherit") } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgNodeDetails", description = "Move an org node to an already existing target node and verify error message")
	public void testAlreadyExistsErrorMessageForOrgNode(String[] getDetails) throws IOException {
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(getDetails[0]), Integer.parseInt(getDetails[1]), Boolean.parseBoolean(getDetails[2]));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForAlreadyExistSource(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing target id and verify error message")
	public void testErrorMessageForNonExistingTargetOrgId() throws IOException {
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("non.existing.org.id")),
				Integer.parseInt(testData.getProperty("parent.enterprise.id")),
				Boolean.parseBoolean(testData.getProperty("inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForOrgId(transferBusinessUnitsApiResponse,
				testData.getProperty("non.existing.org.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing source id and verify error message")
	public void testErrorMessageForNonExistingSourceOrgId() throws IOException {
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("parent.system.id")),
				Integer.parseInt(testData.getProperty("non.existing.org.id")),
				Boolean.parseBoolean(testData.getProperty("inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForOrgId(transferBusinessUnitsApiResponse,
				testData.getProperty("non.existing.org.id"));
	}

	@DataProvider(name = "getOrgNode")
	public Object[] getSysEntNodeDetails() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("inactive.system.same.client"), testData.getProperty("parent.enterprise.id"),
						testData.getProperty("inherit") },
				{ testData.getProperty("inactive.system.same.client"), testData.getProperty("parent.enterprise.id"),
						testData.getProperty("do.not.inherit") },
				{ testData.getProperty("inactive.system.different.client"),
						testData.getProperty("parent.enterprise.id"), testData.getProperty("inherit") },
				{ testData.getProperty("inactive.system.different.client"),
						testData.getProperty("parent.enterprise.id"), testData.getProperty("do.not.inherit") },
				{ testData.getProperty("inactive.system.same.client"),
						testData.getProperty("source.inactive.enterprise.id"), testData.getProperty("inherit") },
				{ testData.getProperty("inactive.system.same.client"),
						testData.getProperty("source.inactive.enterprise.id"), testData.getProperty("do.not.inherit") },
				{ testData.getProperty("inactive.system.different.client"),
						testData.getProperty("source.inactive.enterprise.id"), testData.getProperty("inherit") },
				{ testData.getProperty("inactive.system.different.client"),
						testData.getProperty("source.inactive.enterprise.id"),
						testData.getProperty("do.not.inherit") } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgNode", description = "Move an active/inactive enterprise node to an inactive system of same/different client and verify error message")
	public void testErrorMessageForInactiveTargetSystem(String[] getSysEntNodeDetails) throws IOException {
		logStep("Move an active/inactive enterprise node to an inactive system of same/different client and verify error message");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(getSysEntNodeDetails[0]), Integer.parseInt(getSysEntNodeDetails[1]),
				Boolean.parseBoolean(getSysEntNodeDetails[2]));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInactiveTargetOrgNode(transferBusinessUnitsApiResponse);
	}

	@DataProvider(name = "getOrgNodeData")
	public Object[] getEntPracNodeDetails() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("inactive.enterprise.same.client"),
						testData.getProperty("source.nge.practice.id"), testData.getProperty("inherit") },
				{ testData.getProperty("inactive.enterprise.same.client"),
						testData.getProperty("source.nge.practice.id"), testData.getProperty("do.not.inherit") },
				{ testData.getProperty("inactive.enterprise.different.client"),
						testData.getProperty("source.nge.practice.id"), testData.getProperty("inherit") },
				{ testData.getProperty("inactive.enterprise.different.client"),
						testData.getProperty("source.nge.practice.id"), testData.getProperty("do.not.inherit") },
				{ testData.getProperty("inactive.enterprise.same.client"),
						testData.getProperty("source.agno.practice.id"), testData.getProperty("inherit") },
				{ testData.getProperty("inactive.enterprise.same.client"),
						testData.getProperty("source.agno.practice.id"), testData.getProperty("do.not.inherit") },
				{ testData.getProperty("inactive.enterprise.different.client"),
						testData.getProperty("source.agno.practice.id"), testData.getProperty("inherit") },
				{ testData.getProperty("inactive.enterprise.different.client"),
						testData.getProperty("source.agno.practice.id"), testData.getProperty("do.not.inherit") },
				{ testData.getProperty("inactive.enterprise.same.client"),
						testData.getProperty("source.inactive.nge.practice.id"), testData.getProperty("inherit") },
				{ testData.getProperty("inactive.enterprise.same.client"),
						testData.getProperty("source.inactive.nge.practice.id"),
						testData.getProperty("do.not.inherit") },
				{ testData.getProperty("inactive.enterprise.different.client"),
						testData.getProperty("source.inactive.nge.practice.id"), testData.getProperty("inherit") },
				{ testData.getProperty("inactive.enterprise.different.client"),
						testData.getProperty("source.inactive.nge.practice.id"),
						testData.getProperty("do.not.inherit") },
				{ testData.getProperty("inactive.enterprise.same.client"),
						testData.getProperty("source.inactive.agno.practice.id"), testData.getProperty("inherit") },
				{ testData.getProperty("inactive.enterprise.same.client"),
						testData.getProperty("source.inactive.agno.practice.id"),
						testData.getProperty("do.not.inherit") },
				{ testData.getProperty("inactive.enterprise.different.client"),
						testData.getProperty("source.inactive.agno.practice.id"), testData.getProperty("inherit") },
				{ testData.getProperty("inactive.enterprise.different.client"),
						testData.getProperty("source.inactive.agno.practice.id"),
						testData.getProperty("do.not.inherit") } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgNodeData", description = "Move an active/inactive nge/agnostic practice node to an inactive enterprise of same/different client and verify error message")
	public void testErrorMessageForInactiveTargetEnterprise(String[] getEntPracNodeDetails) throws IOException {
		logStep("Move an active/inactive nge/agnostic practice node to an inactive enterprise of same/different client and verify error message");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(getEntPracNodeDetails[0]), Integer.parseInt(getEntPracNodeDetails[1]),
				Boolean.parseBoolean(getEntPracNodeDetails[2]));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInactiveTargetOrgNode(transferBusinessUnitsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move an active/inactive NGE practice node to an enterprise node of same/different client with do not inherit and verify response body.")
	public void testMoveNgePracticeNodeToEnterpriseNodeOfSameOrDifferentClientWithDoNotInherit()
			throws NullPointerException, Exception {
		logStep("Create active nge practice node below active enterprise node");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.practice.active"), "NgePractice1",
				"NGE_Practice_Name_1", testData.getProperty("api.practice.orgtype"),
				Integer.parseInt(testData.getProperty("parent.enterprise.id")),
				testData.getProperty("api.practice.parenttype"), "NG_Prac@001", "US/Pacific", null);
		int ngePracticeId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(ngePracticeId);
		clientType = validation.getClientType(fetchOrgNodeApiResponse);
		logStep("Move an active nge practice node to an active enterprise node of same client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.enterprise.id.same.client")), ngePracticeId,
				Boolean.parseBoolean(testData.getProperty("do.not.inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify sap id,timezone,status and client type attributes remains as it is in NGE practice node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(ngePracticeId);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, ngePracticeId, "NGE_Practice_Name_1",
				testData.getProperty("api.practice.active"), clientType, "NG_Prac@001", "US/Pacific");

		fetchOrgNodeApiResponse = orgInfo
				.getOrgId(Integer.parseInt(testData.getProperty("active.enterprise.id.different.client")));
		targetNodeClientType = validation.getClientType(fetchOrgNodeApiResponse);
		logStep("Move an active nge practice node to an active enterprise node of different client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.enterprise.id.different.client")), ngePracticeId,
				Boolean.parseBoolean(testData.getProperty("do.not.inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify sap id,timezone and status attributes remains as it is in NGE practice node and client type attribute inherited from target node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(ngePracticeId);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, ngePracticeId, "NGE_Practice_Name_1",
				testData.getProperty("api.practice.active"), targetNodeClientType, "NG_Prac@001", "US/Pacific");

		logStep("Create inactive nge practice node below inactive enterprise node");
		createOrgNodeApiResponse = orgInfo.createOrgNode("false", "Np2", "NGE_Practice_Name_2",
				testData.getProperty("api.practice.orgtype"),
				Integer.parseInt(testData.getProperty("inactive.enterprise.same.client")),
				testData.getProperty("api.practice.parenttype"), "", "", null);
		int ngePracticeIdOne = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("fetch nge practice details");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(ngePracticeIdOne);
		clientType = validation.getClientType(fetchOrgNodeApiResponse);
		sapNumber = validation.getSapId(fetchOrgNodeApiResponse);
		timezone = validation.getTimezone(fetchOrgNodeApiResponse);
		logStep("Move an inactive nge practice node to an active enterprise node of same client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.enterprise.id.same.client")), ngePracticeIdOne,
				Boolean.parseBoolean(testData.getProperty("do.not.inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify sap id,timezone,status and client type attributes remains as it is in NGE practice node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(ngePracticeIdOne);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, ngePracticeIdOne, "NGE_Practice_Name_2",
				"false", clientType, sapNumber, timezone);

		logStep("Move an inactive nge practice node to an active enterprise node of different client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.enterprise.id.different.client")), ngePracticeIdOne,
				Boolean.parseBoolean(testData.getProperty("do.not.inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify sap id,timezone and status attributes remains as it is in NGE practice node and client type attribute inherited from target node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(ngePracticeIdOne);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, ngePracticeIdOne, "NGE_Practice_Name_2",
				"false", targetNodeClientType, sapNumber, timezone);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move an active/inactive NGE practice node to an enterprise node of same/different client with inherit and verify response body.")
	public void testMoveNgePracticeNodeToEnterpriseNodeOfSameOrDifferentClientWithInherit()
			throws NullPointerException, Exception {
		logStep("Create active nge practice node below active enterprise node");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.practice.active"), "NgePractice1",
				"NGE_Practice_1", testData.getProperty("api.practice.orgtype"),
				Integer.parseInt(testData.getProperty("parent.enterprise.id")),
				testData.getProperty("api.practice.parenttype"), "NG_Prac@001", "US/Pacific", null);
		int ngePracticeId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Fetch sap id,timezone,status and client type attributes of target node");
		fetchOrgNodeApiResponse = orgInfo
				.getOrgId(Integer.parseInt(testData.getProperty("active.enterprise.id.same.client")));
		clientType = validation.getClientType(fetchOrgNodeApiResponse);
		sapNumber = validation.getSapId(fetchOrgNodeApiResponse);
		timezone = validation.getTimezone(fetchOrgNodeApiResponse);
		status = validation.getStatus(fetchOrgNodeApiResponse);
		logStep("Move an active nge practice node to an active enterprise node of same client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.enterprise.id.same.client")), ngePracticeId,
				Boolean.parseBoolean(testData.getProperty("inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify NGE practice node inherits sap id,timezone,status and client type attributes from target enterprise node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(ngePracticeId);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, ngePracticeId, "NGE_Practice_1", status,
				clientType, sapNumber, timezone);

		fetchOrgNodeApiResponse = orgInfo
				.getOrgId(Integer.parseInt(testData.getProperty("active.enterprise.id.different.client")));
		targetNodeClientType = validation.getClientType(fetchOrgNodeApiResponse);
		targetNodeSapNumber = validation.getSapId(fetchOrgNodeApiResponse);
		targetNodeTimezone = validation.getTimezone(fetchOrgNodeApiResponse);
		targetNodeStatus = validation.getStatus(fetchOrgNodeApiResponse);
		logStep("Move an active nge practice node to an active enterprise node of different client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.enterprise.id.different.client")), ngePracticeId,
				Boolean.parseBoolean(testData.getProperty("inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify sap id,timezone, client type and status attributes inherited from target node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(ngePracticeId);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, ngePracticeId, "NGE_Practice_1",
				targetNodeStatus, targetNodeClientType, targetNodeSapNumber, targetNodeTimezone);

		logStep("Create inactive nge practice node below inactive enterprise node");
		createOrgNodeApiResponse = orgInfo.createOrgNode("false", "Np2", "NGE_Practice_2",
				testData.getProperty("api.practice.orgtype"),
				Integer.parseInt(testData.getProperty("inactive.enterprise.same.client")),
				testData.getProperty("api.practice.parenttype"), "", "", null);
		int ngePracticeIdOne = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Move an inactive nge practice node to an active enterprise node of same client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.enterprise.id.same.client")), ngePracticeIdOne,
				Boolean.parseBoolean(testData.getProperty("inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify sap id,timezone,status and client type attributes inherited from target enterprise node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(ngePracticeIdOne);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, ngePracticeIdOne, "NGE_Practice_2",
				status, clientType, sapNumber, timezone);

		logStep("Move an inactive nge practice node to an active enterprise node of different client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.enterprise.id.different.client")), ngePracticeIdOne,
				Boolean.parseBoolean(testData.getProperty("inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify sap id,timezone and status attributes inherited from target node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(ngePracticeIdOne);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, ngePracticeIdOne, "NGE_Practice_2",
				targetNodeStatus, targetNodeClientType, targetNodeSapNumber, targetNodeTimezone);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move an active/inactive Agnostic practice node to an enterprise node of same/different client with do not inherit and verify response body.")
	public void testMoveAgnosticPracticeNodeToEnterpriseNodeOfSameOrDifferentClientWithDoNotInherit()
			throws NullPointerException, Exception {
		logStep("Create active agnostic practice node below active client node");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.practice.active"), "AgnoPractice1",
				"AG_Practice_Name_1", testData.getProperty("api.practice.orgtype"),
				Integer.parseInt(testData.getProperty("active.client.id")), testData.getProperty("api.parenttype"),
				"AG_Prac@001", "US/Alaska", null);
		int sourceAgnosticPracticeId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		fetchClientDetailsApiReponse = clientInfo.getClientDetails(testData.getProperty("active.client.id"));
		clientType = validation.getClientNodeClientType(fetchClientDetailsApiReponse);
		logStep("Move an active agnostic practice node to an active enterprise node of same client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.enterprise.id.same.client")), sourceAgnosticPracticeId,
				Boolean.parseBoolean(testData.getProperty("do.not.inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify sap id,timezone,status and client type attributes remains as it is in agnostic practice node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(sourceAgnosticPracticeId);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, sourceAgnosticPracticeId,
				"AG_Practice_Name_1", testData.getProperty("api.practice.active"), clientType, "AG_Prac@001",
				"US/Alaska");

		fetchOrgNodeApiResponse = orgInfo
				.getOrgId(Integer.parseInt(testData.getProperty("active.enterprise.id.different.client")));
		targetNodeClientType = validation.getClientType(fetchOrgNodeApiResponse);
		logStep("Move an active agnostic practice node to an active enterprise node of different client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.enterprise.id.different.client")),
				sourceAgnosticPracticeId, Boolean.parseBoolean(testData.getProperty("do.not.inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify sap id,timezone,status attributes remains as it is in agnostic practice node and client type attribute inherited from target enterprise node");

		fetchOrgNodeApiResponse = orgInfo.getOrgId(sourceAgnosticPracticeId);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, sourceAgnosticPracticeId,
				"AG_Practice_Name_1", testData.getProperty("api.practice.active"), targetNodeClientType, "AG_Prac@001",
				"US/Alaska");

		logStep("Move an active agnostic practice node back to previous enterprise node with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.enterprise.id.same.client")), sourceAgnosticPracticeId,
				Boolean.parseBoolean(testData.getProperty("do.not.inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify sap id,timezone,status and client type attributes remains as it is in agnostic practice node and client type attribute inherited from target enterprise node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(sourceAgnosticPracticeId);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, sourceAgnosticPracticeId,
				"AG_Practice_Name_1", testData.getProperty("api.practice.active"), clientType, "AG_Prac@001",
				"US/Alaska");

		logStep("Create inactive agnostic practice node below active client node");
		createOrgNodeApiResponse = orgInfo.createOrgNode("false", "Ap2", "Ag_Practice_Name_2",
				testData.getProperty("api.practice.orgtype"),
				Integer.parseInt(testData.getProperty("active.client.id")), testData.getProperty("api.parenttype"), "",
				"", null);
		int sourceAgnosticPracticeIdOne = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("fetch agnostic practice details");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(sourceAgnosticPracticeIdOne);
		sapNumber = validation.getSapId(fetchOrgNodeApiResponse);
		timezone = validation.getTimezone(fetchOrgNodeApiResponse);
		logStep("Move an inactive agnostic practice node to an active enterprise node of different client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.enterprise.id.different.client")),
				sourceAgnosticPracticeIdOne, Boolean.parseBoolean(testData.getProperty("do.not.inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify sap id,timezone and status attributes remains as it is in agnostic practice node and client type attribute inherited from target node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(sourceAgnosticPracticeIdOne);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, sourceAgnosticPracticeIdOne,
				"Ag_Practice_Name_2", "false", targetNodeClientType, sapNumber, timezone);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move an active/inactive Agnostic practice node to an enterprise node of same/different client with inherit and verify response body.")
	public void testMoveAgnosticPracticeNodeToEnterpriseNodeOfSameOrDifferentClientWithInherit()
			throws NullPointerException, Exception {
		logStep("Create inactive agnostic practice node below active client node");
		createOrgNodeApiResponse = orgInfo.createOrgNode("false", "AgnoPractice1", "AG_Practice_1",
				testData.getProperty("api.practice.orgtype"),
				Integer.parseInt(testData.getProperty("active.client.id")), testData.getProperty("api.parenttype"),
				"AG_Prac@001", "US/Alaska", null);
		int sourceAgnosticPracticeId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Fetch sap id,timezone,status and client type attributes of target node");
		fetchOrgNodeApiResponse = orgInfo
				.getOrgId(Integer.parseInt(testData.getProperty("active.enterprise.id.same.client")));
		clientType = validation.getClientType(fetchOrgNodeApiResponse);
		sapNumber = validation.getSapId(fetchOrgNodeApiResponse);
		timezone = validation.getTimezone(fetchOrgNodeApiResponse);
		status = validation.getStatus(fetchOrgNodeApiResponse);
		logStep("Move an inactive agnostic practice node to an active enterprise node of same client inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.enterprise.id.same.client")), sourceAgnosticPracticeId,
				Boolean.parseBoolean(testData.getProperty("inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify agnostic practice node inherits sap id,timezone,status and client type attributes from enterprise node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(sourceAgnosticPracticeId);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, sourceAgnosticPracticeId,
				"AG_Practice_1", status, clientType, sapNumber, timezone);

		logStep("Create active agnostic practice node below active client node");
		createOrgNodeApiResponse = orgInfo.createOrgNode("true", "Ap2", "Ag_Practice_2",
				testData.getProperty("api.practice.orgtype"),
				Integer.parseInt(testData.getProperty("active.client.id")), testData.getProperty("api.parenttype"), "",
				"", null);
		int sourceAgnosticPracticeIdOne = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("fetch enterprise node details");
		fetchOrgNodeApiResponse = orgInfo
				.getOrgId(Integer.parseInt(testData.getProperty("active.enterprise.id.different.client")));
		targetNodeClientType = validation.getClientType(fetchOrgNodeApiResponse);
		targetNodeSapNumber = validation.getSapId(fetchOrgNodeApiResponse);
		targetNodeTimezone = validation.getTimezone(fetchOrgNodeApiResponse);
		targetNodeStatus = validation.getStatus(fetchOrgNodeApiResponse);
		logStep("Move an active agnostic practice node to an active enterprise node of different client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.enterprise.id.different.client")),
				sourceAgnosticPracticeIdOne, Boolean.parseBoolean(testData.getProperty("inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify sap id,timezone , client type and status attributes are inherited from target node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(sourceAgnosticPracticeIdOne);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, sourceAgnosticPracticeIdOne,
				"Ag_Practice_2", targetNodeStatus, targetNodeClientType, targetNodeSapNumber, targetNodeTimezone);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move an active/inactive enterprise node to an system node of same/different client with do not inherit and verify response body.")
	public void testMoveEnterpriseNodeToSystemNodeOfSameOrDifferentClientWithDoNotInherit()
			throws NullPointerException, Exception {
		logStep("Create active nge practice node below active enterprise node");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.enterprise.active"), "Ent1",
				"Ent_name_1", testData.getProperty("api.enterprise.orgtype"),
				Integer.parseInt(testData.getProperty("parent.system.id")),
				testData.getProperty("api.enterprise.parenttype"), "ENT@001_01", "US/Samoa", null);
		int enterpriseId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Fetch enterprise node details");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(enterpriseId);
		clientType = validation.getClientType(fetchOrgNodeApiResponse);
		logStep("Create active nge practice node below active enterprise node");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.practice.active"), "NgePractice1",
				"Prac_Name_1", testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), "Prac@001", "US/Pacific", null);
		int ngePracticeId = createOrgNodeApiResponse.jsonPath().getInt("orgId");

		logStep("Move an active enterprise node to an active system node of same client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.system.id.same.client")), enterpriseId,
				Boolean.parseBoolean(testData.getProperty("do.not.inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify client type, status, sap id and timezone attributes remain as it is in enteprise and all its child nodes");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(enterpriseId);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, enterpriseId, "Ent_name_1",
				testData.getProperty("api.enterprise.active"), clientType, "ENT@001_01", "US/Samoa");
		fetchOrgNodeApiResponseOne = orgInfo.getOrgId(ngePracticeId);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponseOne, ngePracticeId, "Prac_Name_1",
				testData.getProperty("api.enterprise.active"), clientType, "Prac@001", "US/Pacific");

		fetchOrgNodeApiResponse = orgInfo
				.getOrgId(Integer.parseInt(testData.getProperty("active.system.id.different.client")));
		targetNodeClientType = validation.getClientType(fetchOrgNodeApiResponse);
		logStep("Move an active enterprise node to an active system node of different client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.system.id.different.client")), enterpriseId,
				Boolean.parseBoolean(testData.getProperty("do.not.inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify status, sap id and timezone attributes remain as it is in enteprise and all its child nodes and client type attribute inherited from target system node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(enterpriseId);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, enterpriseId, "Ent_name_1",
				testData.getProperty("api.enterprise.active"), targetNodeClientType, "ENT@001_01", "US/Samoa");
		fetchOrgNodeApiResponseOne = orgInfo.getOrgId(ngePracticeId);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponseOne, ngePracticeId, "Prac_Name_1",
				testData.getProperty("api.enterprise.active"), targetNodeClientType, "Prac@001", "US/Pacific");

		logStep("Create inactive nge practice node below active enterprise node");
		createOrgNodeApiResponse = orgInfo.createOrgNode("false", "Ent1", "Ent_name_2",
				testData.getProperty("api.enterprise.orgtype"),
				Integer.parseInt(testData.getProperty("parent.system.id")),
				testData.getProperty("api.enterprise.parenttype"), "e1", "", null);
		int enterpriseIdOne = createOrgNodeApiResponse.jsonPath().getInt("orgId");

		logStep("Fetch client type and timezone details of parent node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(Integer.parseInt(testData.getProperty("parent.system.id")));
		clientType = validation.getClientType(fetchOrgNodeApiResponse);
		timezone = validation.getTimezone(fetchOrgNodeApiResponse);
		logStep("Move an inactive enterprise node to an active system node of same client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.system.id.same.client")), enterpriseIdOne,
				Boolean.parseBoolean(testData.getProperty("do.not.inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify client type, status, sap id and timezone attributes remain as it is in enteprise node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(enterpriseIdOne);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, enterpriseIdOne, "Ent_name_2", "false",
				clientType, "e1", timezone);

		logStep("Move an inactive enterprise node to an active system node of different client with do not inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.system.id.different.client")), enterpriseIdOne,
				Boolean.parseBoolean(testData.getProperty("do.not.inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify status, sap id and timezone attributes remain as it is in enteprise node and client type attribute inherited from target system node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(enterpriseIdOne);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, enterpriseIdOne, "Ent_name_2", "false",
				targetNodeClientType, "e1", timezone);

		logStep("Move the inactive enterprise node back to the previous system node");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.system.id.same.client")), enterpriseIdOne,
				Boolean.parseBoolean(testData.getProperty("do.not.inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify status, sap id and timezone attributes remain as it is in enteprise node and client type attribute inherited from target system node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(enterpriseIdOne);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, enterpriseIdOne, "Ent_name_2", "false",
				clientType, "e1", timezone);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Move an active/inactive enterprise node to an system node of same/different client with inherit and verify response body.")
	public void testMoveEnterpriseNodeToSystemNodeOfSameOrDifferentClientWithInherit()
			throws NullPointerException, Exception {
		logStep("Create active nge practice node below active enterprise node");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.enterprise.active"), "Ent1", "Ent_1",
				testData.getProperty("api.enterprise.orgtype"),
				Integer.parseInt(testData.getProperty("parent.system.id")),
				testData.getProperty("api.enterprise.parenttype"), " ", "US/Samoa", null);
		int enterpriseId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Create active nge practice node below active enterprise node");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.practice.active"), "NgePractice1",
				"Prac_1", testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), "Prac@001", "US/Pacific", null);
		int ngePracticeId = createOrgNodeApiResponse.jsonPath().getInt("orgId");

		logStep("Move an active enterprise node to an active system node of same client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.system.id.same.client")), enterpriseId,
				Boolean.parseBoolean(testData.getProperty("inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		fetchOrgNodeApiResponse = orgInfo
				.getOrgId(Integer.parseInt(testData.getProperty("active.system.id.same.client")));
		clientType = validation.getClientType(fetchOrgNodeApiResponse);
		status = validation.getStatus(fetchOrgNodeApiResponse);
		sapNumber = validation.getSapId(fetchOrgNodeApiResponse);
		timezone = validation.getTimezone(fetchOrgNodeApiResponse);
		logStep("Verify enterprise node inherit client type, status, sap id and timezone attributes from target system node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(enterpriseId);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, enterpriseId, "Ent_1", status,
				clientType, sapNumber, timezone);
		fetchOrgNodeApiResponseOne = orgInfo.getOrgId(ngePracticeId);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponseOne, ngePracticeId, "Prac_1", status,
				clientType, sapNumber, timezone);

		logStep("Fetch system node details");
		fetchOrgNodeApiResponse = orgInfo
				.getOrgId(Integer.parseInt(testData.getProperty("active.system.id.different.client")));
		targetNodeClientType = validation.getClientType(fetchOrgNodeApiResponse);
		targetNodeStatus = validation.getStatus(fetchOrgNodeApiResponse);
		targetNodeSapNumber = validation.getSapId(fetchOrgNodeApiResponse);
		targetNodeTimezone = validation.getTimezone(fetchOrgNodeApiResponse);

		logStep("Move an active enterprise node to an active system node of different client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.system.id.different.client")), enterpriseId,
				Boolean.parseBoolean(testData.getProperty("inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify enterprise node inherit client type, status, sap id and timezone attributes from target system node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(enterpriseId);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, enterpriseId, "Ent_1", targetNodeStatus,
				targetNodeClientType, targetNodeSapNumber, targetNodeTimezone);
		fetchOrgNodeApiResponseOne = orgInfo.getOrgId(ngePracticeId);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponseOne, ngePracticeId, "Prac_1",
				targetNodeStatus, targetNodeClientType, targetNodeSapNumber, targetNodeTimezone);

		logStep("Create inactive nge practice node below active enterprise node");
		createOrgNodeApiResponse = orgInfo.createOrgNode("false", "Ent2", "Ent_name_02",
				testData.getProperty("api.enterprise.orgtype"),
				Integer.parseInt(testData.getProperty("parent.system.id")),
				testData.getProperty("api.enterprise.parenttype"), "", "", null);
		int enterpriseIdOne = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Create inactive nge practice node below inactive enterprise node");
		createOrgNodeApiResponse = orgInfo.createOrgNode("false", "NgePractice2", "Prac_02",
				testData.getProperty("api.practice.orgtype"), enterpriseIdOne,
				testData.getProperty("api.practice.parenttype"), "Prac@002", "US/Pacific", null);
		int ngePracticeIdOne = createOrgNodeApiResponse.jsonPath().getInt("orgId");

		logStep("Move an inactive enterprise node to an active system node of same client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.system.id.same.client")), enterpriseIdOne,
				Boolean.parseBoolean(testData.getProperty("inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify enteprise node and all its child node inherits client type, status, sap id and timezone attributes from target system node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(enterpriseIdOne);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, enterpriseIdOne, "Ent_name_02", status,
				clientType, sapNumber, timezone);
		fetchOrgNodeApiResponseOne = orgInfo.getOrgId(ngePracticeIdOne);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponseOne, ngePracticeIdOne, "Prac_02", status,
				clientType, sapNumber, timezone);

		logStep("Move an inactive enterprise node to an active system node of different client with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(testData.getProperty("active.system.id.different.client")), enterpriseIdOne,
				Boolean.parseBoolean(testData.getProperty("inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 200);
		logStep("Verify enteprise node and all its child node inherits client type, status, sap id and timezone attributes from target system node");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(enterpriseIdOne);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, enterpriseIdOne, "Ent_name_02",
				targetNodeStatus, targetNodeClientType, targetNodeSapNumber, targetNodeTimezone);
		fetchOrgNodeApiResponseOne = orgInfo.getOrgId(ngePracticeIdOne);
		validation.verifyOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponseOne, ngePracticeIdOne, "Prac_02",
				targetNodeStatus, targetNodeClientType, targetNodeSapNumber, targetNodeTimezone);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and verify error response body")
	public void testErrorResponseForInvalidUrl() throws Exception {
		logStep("Move an active enterprise node to an active system with inherit");
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNodeUsingInvalidUrl(
				Integer.parseInt(testData.getProperty("active.system.id.same.client")),
				Integer.parseInt(testData.getProperty("parent.enterprise.id")),
				Boolean.parseBoolean(testData.getProperty("inherit")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(transferBusinessUnitsApiResponse);
	}

	@DataProvider(name = "getNodeDetails")
	public Object[] getNodeDetails() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("source.nge.practice.id"), testData.getProperty("parent.enterprise.id"),
						testData.getProperty("inherit") },
				{ testData.getProperty("source.nge.practice.id"), testData.getProperty("parent.enterprise.id"),
						testData.getProperty("do.not.inherit") },
				{ testData.getProperty("parent.enterprise.id"), testData.getProperty("parent.enterprise.id"),
						testData.getProperty("inherit") },
				{ testData.getProperty("parent.system.id"), testData.getProperty("source.nge.practice.id"),
						testData.getProperty("inherit") },
				{ testData.getProperty("parent.system.id"), testData.getProperty("source.nge.practice.id"),
						testData.getProperty("do.not.inherit") },
				{ testData.getProperty("parent.system.id"), testData.getProperty("source.agno.practice.id"),
						testData.getProperty("inherit") },
				{ testData.getProperty("parent.system.id"), testData.getProperty("source.agno.practice.id"),
						testData.getProperty("do.not.inherit") },
				{ testData.getProperty("source.agno.practice.id"), testData.getProperty("source.agno.practice.id"),
						testData.getProperty("do.not.inherit") } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getNodeDetails", description = "Enter invalid combination of source and target id and verify error message")
	public void testErrorMessageForInvalidSourceOrTargetId(String[] getNodeDetails) throws IOException {
		transferBusinessUnitsApiResponse = transferBusinessUnits.moveOrganizationNode_BL(
				Integer.parseInt(getNodeDetails[0]), Integer.parseInt(getNodeDetails[1]),
				Boolean.parseBoolean(getNodeDetails[2]));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(transferBusinessUnitsApiResponse);
		CCUtils.assertEquals(transferBusinessUnitsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidCombinationOfSourceAndTargetNode(transferBusinessUnitsApiResponse);
	}
}
