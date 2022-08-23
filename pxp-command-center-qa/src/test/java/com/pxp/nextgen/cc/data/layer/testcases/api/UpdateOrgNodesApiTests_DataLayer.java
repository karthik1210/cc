// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class UpdateOrgNodesApiTests_DataLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	Response updateOrgNodeApiResponse, createClientApiResponse, createOrgNodeApiResponse, fetchOrgNodeDetails;
	String clientName, clientSapNumber;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		orgInfo = new OrgInfo();
		clientInfo = new ClientInfo();
		validation = new Validations();
	}

	@DataProvider(name = "getOrgDetails")
	public Object[] getOrgTypeAndOrgId() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("api.system.orgtype"), testData.getProperty("api.system.org.id") },
				{ testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.enterprise.org.id") },
				{ testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.org.id") },
				{ testData.getProperty("api.practice.orgtype"),
						testData.getProperty("api.agnostic.practice.org.id") } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Enter null value in mandatory fields and validate error response body")
	public void testErrorMessagesForMandatoryFields(String[] getOrgTypeAndOrgId) throws IOException {
		logStep("Enter null value in mandatory fields and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("", getOrgTypeAndOrgId[0], "", "", null, "",
				Integer.parseInt(getOrgTypeAndOrgId[1]), Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyOrgNodeNullErrorMessageResponseBody(updateOrgNodeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Enter maximum characters in sap id,splution display name and name field and validate error response body")
	public void testMaximumCharactersErrorMessage(String[] getOrgTypeAndOrgId) throws IOException {
		logStep("Enter maximum characters in sap id,splution display name and name field and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer(testData.getProperty("invalid.name"),
				getOrgTypeAndOrgId[0], testData.getProperty("invalid.solutionDisplayName"),
				testData.getProperty("invalid.sap"), "true", "US/Central", Integer.parseInt(getOrgTypeAndOrgId[1]),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyMaximumCharacterErrorMessageInResponseBody(updateOrgNodeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Enter invalid timezone id in timezone field and validate error response body")
	public void testErrorMessageForInvalidTimezoneId(String[] getOrgTypeAndOrgId) throws IOException {
		logStep("Enter invalid timezone id in timezone field and validate error response body");
		String invalidTimezoneId = testData.getProperty("api.system.timezoneid").concat("abcdef@12");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("Org_Name", getOrgTypeAndOrgId[0], "Dispaly_Name_1",
				"SAP@001", "true", invalidTimezoneId, Integer.parseInt(getOrgTypeAndOrgId[1]),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForTimezoneField(updateOrgNodeApiResponse, invalidTimezoneId);
	}

	@DataProvider(name = "getOrgDetail")
	public Object[] getOrgTypeAndInvalidOrgId() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("api.system.orgtype"), testData.getProperty("api.system.org.id").concat("12") },
				{ testData.getProperty("api.enterprise.orgtype"),
						testData.getProperty("api.enterprise.org.id").concat("00") },
				{ testData.getProperty("api.practice.orgtype"),
						testData.getProperty("api.practice.org.id").concat("022") },
				{ testData.getProperty("api.practice.orgtype"),
						testData.getProperty("api.agnostic.practice.org.id").concat("01") } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetail", description = "Enter invalid org id in orgId field and validate error response body")
	public void testErrorMessageForInvalidOrgId(String[] getOrgTypeAndInvalidOrgId) throws IOException {
		logStep("Enter invalid org id in orgId field and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("Org_Name", getOrgTypeAndInvalidOrgId[0],
				"Dispaly_Name_1", "SAP@001", "true", "US/Eastern", Integer.parseInt(getOrgTypeAndInvalidOrgId[1]),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(updateOrgNodeApiResponse,
				Integer.parseInt(getOrgTypeAndInvalidOrgId[1]));
	}

	public int createClient(String status) throws Exception {
		clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient_DataLayer(status, clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		return createClientApiResponse.jsonPath().getInt("clientId");
	}

	public int createOrgNode(String status, String displayName, String name, String orgType, int parentId,
			String parentType, String sapNumber, String timezoneId) throws Exception {
		createOrgNodeApiResponse = orgInfo.createOrg_DataLayer(status, displayName, name, orgType, parentId, parentType,
				sapNumber, timezoneId, Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		return createOrgNodeApiResponse.jsonPath().getInt("orgId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify active org node status from active to inactive and verify subsequent child nodes also becomes inactive and validate response body. Activate org node from inactive to active when parent node is active and validate response body")
	public void testOrgNodeUpdateStatusFunctionality() throws NullPointerException, Exception {
		logStep("Create active client node");
		int clientId = createClient(testData.getProperty("api.client.active"));
		logStep("Create active system, enterprise ,nge practice and inactive agnostic practice nodes below active client");
		int systemId = createOrgNode(testData.getProperty("api.system.active"), "", "system_name_1",
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"), "Sys@001",
				"US/Samoa");
		int enterpriseId = createOrgNode(testData.getProperty("api.enterprise.active"), "", "enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), "Ent@001", "US/Eastern");
		int practiceId = createOrgNode(testData.getProperty("api.practice.active"), "", "practice_name_1",
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), "Prac@001", "US/Central");
		int agnosticPracticeId = createOrgNode("false", "", "ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), clientId, testData.getProperty("api.parenttype"),
				"AGPrac@001", "US/Hawaii");

		logStep("Modify system node status from active to inactive");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("system_name_1",
				testData.getProperty("api.system.orgtype"), "", "Sys@001", "false", "US/Samoa", systemId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, systemId, "system_name_1");
		logStep("verify after updating system node status from active to inactive, subsequent child nodes(Enterprise/NGE practice) also become inactive");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(systemId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, systemId, "system_name_1", "false");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(enterpriseId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, enterpriseId, "enterprise_name_1", "false");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(practiceId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, practiceId, "practice_name_1", "false");

		logStep("Modify system node status from inactive to active when parent node client is active");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("system_name_1",
				testData.getProperty("api.system.orgtype"), "", "Sys@001", "true", "US/Samoa", systemId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, systemId, "system_name_1");
		logStep("verify only system node status is changed from inactive to active and subsequent child nodes(Enterprise/NGE practice) remains inactive");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(systemId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, systemId, "system_name_1", "true");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(enterpriseId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, enterpriseId, "enterprise_name_1", "false");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(practiceId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, practiceId, "practice_name_1", "false");

		logStep("Modify enterprise node status from inactive to active when parent node system is active");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), "", "Ent@001", "true", "US/Eastern", enterpriseId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, enterpriseId, "enterprise_name_1");
		logStep("verify only enterprise node status is changed from inactive to active and subsequent child nodes(NGE practice) remains inactive");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(enterpriseId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, enterpriseId, "enterprise_name_1", "true");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(practiceId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, practiceId, "practice_name_1", "false");

		logStep("Modify nge practice node status from inactive to active when parent node enterprise is active");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("practice_name_1",
				testData.getProperty("api.practice.orgtype"), "", "Prac@001", "true", "US/Central", practiceId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, practiceId, "practice_name_1");
		logStep("verify NGE practice node status is changed from inactive to active");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(practiceId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, practiceId, "practice_name_1", "true");

		logStep("Modify enterprise node status from active to inactive");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), "", "Ent@001", "false", "US/Eastern", enterpriseId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, enterpriseId, "enterprise_name_1");
		logStep("verify after updating enterprise node status from active inactive, subsequent child node(NGE practice) also become inactive");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(enterpriseId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, enterpriseId, "enterprise_name_1", "false");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(practiceId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, practiceId, "practice_name_1", "false");

		logStep("Modify agnostic practice node status from inactive to active when parent node client is active");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), "", "AGPrac@001", "true", "US/Hawaii", agnosticPracticeId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, agnosticPracticeId, "ag_practice_name_1");
		logStep("verify agnostic practice node status is changed from inactive to active");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(agnosticPracticeId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, agnosticPracticeId, "ag_practice_name_1", "true");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify org node status from inactive to active when parent node is inactive and validate error response body")
	public void testActivateOrgNodeWhenParentIsInactive() throws Exception, NullPointerException {
		logStep("Create inactive client node");
		int clientId = createClient("false");
		logStep("Create inactive system, enterprise ,nge practice and agnostic practice nodes below inactive client");
		int systemId = createOrgNode("false", "", "system_name_1", testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), "Sys@001", "US/Samoa");
		int enterpriseId = createOrgNode("false", "", "enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), "Ent@001", "US/Eastern");
		int practiceId = createOrgNode("false", "", "practice_name_1", testData.getProperty("api.practice.orgtype"),
				enterpriseId, testData.getProperty("api.practice.parenttype"), "Prac@001", "US/Central");
		int agnosticPracticeId = createOrgNode("false", "", "ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), clientId, testData.getProperty("api.parenttype"),
				"AGPrac@001", "US/Hawaii");

		logStep("Modify system node status from inactive to active when client node is inactive and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("system_name_1",
				testData.getProperty("api.system.orgtype"), "", "Sys@001", "true", "US/Samoa", systemId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyInactiveClientErrorResponsforSystem(updateOrgNodeApiResponse);

		logStep("Modify enterprise node status from inactive to active when system node is inactive and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), "", "Ent@001", "true", "US/Eastern", enterpriseId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyInactiveSystemErrorResponForEnterprise(updateOrgNodeApiResponse);

		logStep("Modify nge practice node status from inactive to active when enterprise node is inactive and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("practice_name_1",
				testData.getProperty("api.practice.orgtype"), "", "Prac@001", "true", "US/Central", practiceId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyInactivePracticeErrorResponsWhileUpdataingActivePractice(updateOrgNodeApiResponse);

		logStep("Modify agnostic practice node status from inactive to active when client node is inactive and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), "", "AGPrac@001", "true", "US/Hawaii", agnosticPracticeId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyInactiveClientErrorResponsForPractice(updateOrgNodeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create og nodes(fetch sap id and timezone from parent nodes) and update org node using valid testdata(enter Sap id and timezone=null/blank space) and validate response body")
	public void testUpdateOrgNodeFunctinalityUsingValidData() throws Exception, NullPointerException {
		logStep("Create active client node");
		int clientId = createClient(testData.getProperty("api.client.active"));
		logStep("Create active system, enterprise ,nge practice and agnostic practice nodes(fetch sap id and timezone values from parent node) below active client");
		int systemId = createOrgNode("true", "", "system_name_1", testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), "", "");
		int enterpriseId = createOrgNode("true", "", "enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), "", "");
		int practiceId = createOrgNode("true", "", "practice_name_1", testData.getProperty("api.practice.orgtype"),
				enterpriseId, testData.getProperty("api.practice.parenttype"), "", "");
		int agnosticPracticeId = createOrgNode("true", "", "ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), clientId, testData.getProperty("api.parenttype"), "", "");

		logStep("Update system name, system display name, sap id, timezone (enter null in sap id and timezone field)");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("System_name_2",
				testData.getProperty("api.system.orgtype"), "", "", "true", "", systemId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, systemId, "System_name_2");
		logStep("Fetch updated system details using system id and verify sap number is updated as null value and timezone fetch from its parent node");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(systemId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, systemId, "System_name_2",
				testData.getProperty("api.system.orgtype"), testData.getProperty("api.client.type"), "System_name_2",
				clientSapNumber, "true", testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"));

		logStep("Update enterprise name, enterprise display name, sap id, timezone (enter null value in sap id and timezone field)");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("enterprise_name_2",
				testData.getProperty("api.enterprise.orgtype"), "", "", "true", "", enterpriseId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, enterpriseId, "enterprise_name_2");
		logStep("Fetch updated enterprise details using enterprise id and verify sap number is updated as null value and timezone fetch from its parent node");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(enterpriseId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, enterpriseId, "enterprise_name_2",
				testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.client.type"),
				"enterprise_name_2", clientSapNumber, "true", testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"));

		logStep("Update nge practice name, practice display name, sap id, timezone (enter null value in sap id and timezone field)");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("practice_name_2",
				testData.getProperty("api.practice.orgtype"), "", "", "true", "", practiceId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, practiceId, "practice_name_2");
		logStep("Fetch updated nge practice details using practice id and verify sap number updated as null value and timezone fetch from its parent node");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(practiceId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, practiceId, "practice_name_2",
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"),
				"practice_name_2", clientSapNumber, "true", testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"));

		logStep("Update agnostic practice name, practice display name, sap id, timezone (enter null value in sap id and timezone field)");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("ag_Prac_2",
				testData.getProperty("api.practice.orgtype"), "", "", "true", "", agnosticPracticeId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, agnosticPracticeId, "ag_Prac_2");
		logStep("Fetch updated agnostic practice details using agnostic practice id and verify sap number updated as null value and timezone fetch from its parent node");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(agnosticPracticeId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, agnosticPracticeId, "ag_Prac_2",
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"), "ag_Prac_2",
				clientSapNumber, "true", testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"));

		logStep("Update system name, system display name, sap id, timezone (enter blank space in sap id and timezone field)");
		String systemDisplayName = CCUtils
				.getName(CCUtils.generateStringWithSpace(CCUtils.generateAlphaNumericString(100)));
		String systemName = CCUtils.getName(CCUtils.generateStringWithSpace(CCUtils.generateAlphaNumericString(100)));
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer(systemName,
				testData.getProperty("api.system.orgtype"), systemDisplayName, " ", "true", " ", systemId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, systemId, systemName);
		logStep("Fetch updated system details using system id and verify sap number is updated as null value and timezone fetch from its parent node");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(systemId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, systemId, systemName,
				testData.getProperty("api.system.orgtype"), testData.getProperty("api.client.type"), systemDisplayName,
				"", "true", testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"));

		logStep("Update enterprise name, enterprise display name, sap id, timezone (enter blank space in sap id and timezone field)");
		String enterpriseDisplayName = CCUtils
				.getName(CCUtils.generateStringWithSpace(CCUtils.generateAlphaNumericString(100)));
		String enterpriseName = CCUtils
				.getName(CCUtils.generateStringWithSpace(CCUtils.generateAlphaNumericString(100)));
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer(enterpriseName,
				testData.getProperty("api.enterprise.orgtype"), enterpriseDisplayName, " ", "true", " ", enterpriseId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, enterpriseId, enterpriseName);
		logStep("Fetch updated enterprise details using enterprise id and verify sap number is updated as null value and timezone fetch from its parent node");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(enterpriseId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, enterpriseId, enterpriseName,
				testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.client.type"),
				enterpriseDisplayName, "", "true", testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"));

		logStep("Update nge practice name, practice display name, sap id, timezone (enter blank space in sap id and timezone field)");
		String practiceDisplayName = CCUtils
				.getName(CCUtils.generateStringWithSpace(CCUtils.generateAlphaNumericString(100)));
		String practiceName = CCUtils.getName(CCUtils.generateStringWithSpace(CCUtils.generateAlphaNumericString(100)));
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer(practiceName,
				testData.getProperty("api.practice.orgtype"), practiceDisplayName, " ", "true", " ", practiceId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, practiceId, practiceName);
		logStep("Fetch updated nge practice details using practice id and verify sap number updated as null value and timezone fetch from its parent node");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(practiceId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, practiceId, practiceName,
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"),
				practiceDisplayName, "", "true", testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"));

		logStep("Update agnostic practice name, practice display name, sap id, timezone (enter blank space in sap id and timezone field)");
		String agPracticeDisplayName = CCUtils
				.getName(CCUtils.generateStringWithSpace(CCUtils.generateAlphaNumericString(100)));
		String agPracticeName = CCUtils
				.getName(CCUtils.generateStringWithSpace(CCUtils.generateAlphaNumericString(100)));
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer(agPracticeName,
				testData.getProperty("api.practice.orgtype"), agPracticeDisplayName, " ", "true", " ",
				agnosticPracticeId, Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, agnosticPracticeId, agPracticeName);
		logStep("Fetch updated agnostic practice details using agnostic practice id and verify sap number updated as null value and timezone fetch from its parent node");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(agnosticPracticeId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, agnosticPracticeId, agPracticeName,
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"),
				agPracticeDisplayName, "", "true", testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"));

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create org nodes(Enter new value in sap id and timezone field) and Update org node using valid testdata(Sap id and timezone=null value/ not null value) and validate response body")
	public void testUpdateOrgNodeFunctinalityUsingNullAndNotNullValue() throws Exception, NullPointerException {
		logStep("Create active client node");
		int clientId = createClient(testData.getProperty("api.client.active"));
		logStep("Create inactive system, enterprise ,nge practice and agnostic practice nodes(enter new value in sap id and timezone field) below active client");
		int systemId = createOrgNode("false", "s1", "system_name_1", testData.getProperty("api.system.orgtype"),
				clientId, testData.getProperty("api.parenttype"), "systemSap@001", "US/Alaska");
		int enterpriseId = createOrgNode("false", "e1", "enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), "entSap@001", "US/Central");
		int practiceId = createOrgNode("false", "p1", "practice_name_1", testData.getProperty("api.practice.orgtype"),
				enterpriseId, testData.getProperty("api.practice.parenttype"), "prac@001", "US/Pacific");
		int agnosticPracticeId = createOrgNode("false", "ap1", "ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), clientId, testData.getProperty("api.parenttype"),
				"apPrac@001", "US/Hawaii");

		logStep("Update system name, system display name, sap id, timezone (enter null in sap id and timezone field)");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("System_name_2",
				testData.getProperty("api.system.orgtype"), "", "", "false", "", systemId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, systemId, "System_name_2");
		logStep("Fetch updated system details using system id and verify sap number is updated as null value and timezone fetch from its parent node");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(systemId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, systemId, "System_name_2",
				testData.getProperty("api.system.orgtype"), testData.getProperty("api.client.type"), "System_name_2",
				"", "false", testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"));

		logStep("Update enterprise name, enterprise display name, sap id, timezone (enter null value in sap id and timezone field)");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("enterprise_name_2",
				testData.getProperty("api.enterprise.orgtype"), "", "", "false", "", enterpriseId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, enterpriseId, "enterprise_name_2");
		logStep("Fetch updated enterprise details using enterprise id and verify sap number is updated as null value and timezone fetch from its parent node");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(enterpriseId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, enterpriseId, "enterprise_name_2",
				testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.client.type"),
				"enterprise_name_2", "", "false", testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"));

		logStep("Update nge practice name, practice display name, sap id, timezone (enter null value in sap id and timezone field)");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("practice_name_2",
				testData.getProperty("api.practice.orgtype"), "", "", "false", "", practiceId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, practiceId, "practice_name_2");
		logStep("Fetch updated nge practice details using practice id and verify sap number updated as null value and timezone fetch from its parent node");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(practiceId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, practiceId, "practice_name_2",
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"),
				"practice_name_2", "", "false", testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"));

		logStep("Update agnostic practice name, practice display name, sap id, timezone (enter null value in sap id and timezone field)");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("ag_Prac_2",
				testData.getProperty("api.practice.orgtype"), "", "", "false", "", agnosticPracticeId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, agnosticPracticeId, "ag_Prac_2");
		logStep("Fetch updated agnostic practice details using agnostic practice id and verify sap number updated as null value and timezone fetch from its parent node");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(agnosticPracticeId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, agnosticPracticeId, "ag_Prac_2",
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"), "ag_Prac_2", "",
				"false", testData.getProperty("api.client.testclient"), testData.getProperty("api.client.timezoneid"));

		logStep("Update system name, system display name, sap id, timezone (enter not null value in sap id and timezone field)");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("sys1", testData.getProperty("api.system.orgtype"),
				"s1", "s_sap@001", "true", "US/Samoa", systemId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, systemId, "sys1");
		logStep("Fetch updated system details using system id and verify updated system details");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(systemId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, systemId, "sys1",
				testData.getProperty("api.system.orgtype"), testData.getProperty("api.client.type"), "s1", "s_sap@001",
				"true", testData.getProperty("api.client.testclient"), "US/Samoa");

		logStep("Update enterprise name, enterprise display name, sap id, timezone (enter not null value in sap id and timezone field)");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("ent2",
				testData.getProperty("api.enterprise.orgtype"), "e1", "entSap@01", "true", "US/Arizona", enterpriseId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, enterpriseId, "ent2");
		logStep("Fetch updated enterprise details using enterprise id and  and verify updated enterprise details");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(enterpriseId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, enterpriseId, "ent2",
				testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.client.type"), "e1",
				"entSap@01", "true", testData.getProperty("api.client.testclient"), "US/Arizona");

		logStep("Update nge practice name, practice display name, sap id, timezone (enter not null value in sap id and timezone field)");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("prac2",
				testData.getProperty("api.practice.orgtype"), "p2", "pracSap@002", "true", "US/Mountain", practiceId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, practiceId, "prac2");
		logStep("Fetch updated nge practice details using practice id and verify practice details");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(practiceId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, practiceId, "prac2",
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"), "p2",
				"pracSap@002", "true", testData.getProperty("api.client.testclient"), "US/Mountain");

		logStep("Update agnostic practice name, practice display name, sap id, timezone (enter not null value in sap id and timezone field)");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("ag_prac2",
				testData.getProperty("api.practice.orgtype"), "agp2", "agSap@002", "true", "Canada/Atlantic",
				agnosticPracticeId, Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, agnosticPracticeId, "ag_prac2");
		logStep("Fetch updated agnostic practice details using agnostic practice id and verify practice details");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(agnosticPracticeId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, agnosticPracticeId, "ag_prac2",
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"), "agp2",
				"agSap@002", "true", testData.getProperty("api.client.testclient"), "Canada/Atlantic");

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create org nodes(Enter new value in sap id and timezone field) and Update org node using valid testdata(Sap id and timezone=blank space) and validate response body")
	public void testUpdateOrgNodeFunctinalityUsingBlankSpace() throws Exception, NullPointerException {
		logStep("Create active client node");
		int clientId = createClient(testData.getProperty("api.client.active"));
		logStep("Create inactive system, enterprise ,nge practice and agnostic practice nodes(enter new value in sap id and timezone field) below active client");
		int systemId = createOrgNode("false", "s1", "system_name_1", testData.getProperty("api.system.orgtype"),
				clientId, testData.getProperty("api.parenttype"), "systemSap@001", "US/Alaska");
		int enterpriseId = createOrgNode("false", "e1", "enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), "entSap@001", "US/Central");
		int practiceId = createOrgNode("false", "p1", "practice_name_1", testData.getProperty("api.practice.orgtype"),
				enterpriseId, testData.getProperty("api.practice.parenttype"), "prac@001", "US/Pacific");
		int agnosticPracticeId = createOrgNode("false", "ap1", "ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), clientId, testData.getProperty("api.parenttype"),
				"apPrac@001", "US/Hawaii");

		logStep("Update system name, system display name, sap id, timezone (enter blank space in sap id and timezone field)");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("System_name_2",
				testData.getProperty("api.system.orgtype"), null, " ", "false", " ", systemId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, systemId, "System_name_2");
		logStep("Fetch updated system details using system id and verify updated system details");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(systemId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, systemId, "System_name_2",
				testData.getProperty("api.system.orgtype"), testData.getProperty("api.client.type"), "System_name_2",
				" ", "false", testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"));

		logStep("Update enterprise name, enterprise display name, sap id, timezone (enter blank space in sap id and timezone field)");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("enterprise_name_2",
				testData.getProperty("api.enterprise.orgtype"), null, " ", "false", " ", enterpriseId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, enterpriseId, "enterprise_name_2");
		logStep("Fetch updated enterprise details using enterprise id and verify updated enterprise details");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(enterpriseId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, enterpriseId, "enterprise_name_2",
				testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.client.type"),
				"enterprise_name_2", " ", "false", testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"));

		logStep("Update nge practice name, practice display name, sap id, timezone (enter blank space in sap id and timezone field)");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("practice_name_2",
				testData.getProperty("api.practice.orgtype"), null, " ", "false", " ", practiceId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, practiceId, "practice_name_2");
		logStep("Fetch updated nge practice details using practice id and verify practice details");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(practiceId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, practiceId, "practice_name_2",
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"),
				"practice_name_2", " ", "false", testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"));

		logStep("Update agnostic practice name, practice display name, sap id, timezone (enter blank space in sap id and timezone field)");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("ag_Prac_2",
				testData.getProperty("api.practice.orgtype"), null, " ", "false", " ", agnosticPracticeId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, agnosticPracticeId, "ag_Prac_2");
		logStep("Fetch updated agnostic practice details using agnostic practice id and verify practice details");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(agnosticPracticeId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, agnosticPracticeId, "ag_Prac_2",
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"), "ag_Prac_2", " ",
				"false", testData.getProperty("api.client.testclient"), testData.getProperty("api.client.timezoneid"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Verify error message for invalid url")
	public void testErrorMessageForInvalidUrl(String[] getOrgTypeAndOrgId) throws IOException {
		updateOrgNodeApiResponse = orgInfo.updateOrgNodeUsingInvalidUrl_DataLayer("org_name", getOrgTypeAndOrgId[0],
				"org@001", "org_sap@001", "true", "US/Central", Integer.parseInt(getOrgTypeAndOrgId[1]),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateOrgNodeApiResponse);
	}

}
