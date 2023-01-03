// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

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
	String clientName, clientSapNumber, testFlag, clientTimezoneId;
	int clientId, systemId, enterpriseId, practiceId, agnosticPracticeId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		orgInfo = new OrgInfo();
		clientInfo = new ClientInfo();
		validation = new Validations();
		logStep("Create active client node");
		clientId = createClient(testData.getProperty("api.client.active"));
		logStep("Create active system, enterprise ,nge practice and inactive agnostic practice nodes below active client");
		systemId = createOrgNode(testData.getProperty("api.system.active"), "", "system_name_1",
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"), "");
		enterpriseId = createOrgNode(testData.getProperty("api.enterprise.active"), "", "enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), "US/Eastern");
		practiceId = createOrgNode(testData.getProperty("api.practice.active"), "", "practice_name_1",
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), "US/Central");
		agnosticPracticeId = createOrgNode("false", "", "ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), systemId, testData.getProperty("api.parenttype"),
				"US/Hawaii");
	}

	@DataProvider(name = "getOrgDetails")
	public Object[] getOrgTypeAndOrgId() {
		Object[][] obj = new Object[][] { { testData.getProperty("api.system.orgtype"), String.valueOf(systemId) },
				{ testData.getProperty("api.enterprise.orgtype"), String.valueOf(enterpriseId) },
				{ testData.getProperty("api.practice.orgtype"), String.valueOf(practiceId) },
				{ testData.getProperty("api.practice.orgtype"), String.valueOf(agnosticPracticeId) } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Enter null value in mandatory fields and validate error response body")
	public void testErrorMessagesForMandatoryFields(String[] getOrgTypeAndOrgId) throws IOException {
		logStep("Enter null value in mandatory fields and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("", getOrgTypeAndOrgId[0], "", null, null,
				Integer.parseInt(getOrgTypeAndOrgId[1]), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyOrgNodeNullErrorMessageResponseBody(updateOrgNodeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Enter invalid value in ehr identifier field and validate error response body")
	public void testErrorMessagesForEhrIdentifierField(String[] getOrgTypeAndOrgId) throws IOException {
		logStep("Enter null value in eht identifier field and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("Org_name", getOrgTypeAndOrgId[0],
				"org_display_name", "true", "US/Eastern", Integer.parseInt(getOrgTypeAndOrgId[1]), 0,
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForInvalidProductId(updateOrgNodeApiResponse, 0);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Enter invalid value in pmidentifier, ehr identifier field and validate error response body")
	public void testErrorMessagesForPmIdentifierField(String[] getOrgTypeAndOrgId) throws IOException {
		logStep("Enter null value in pm identifier field and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("Org_name", getOrgTypeAndOrgId[0],
				"org_display_name", "true", "US/Eastern", Integer.parseInt(getOrgTypeAndOrgId[1]),
				Integer.parseInt(testData.getProperty("ehr.identifier")), 0);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForInvalidProductId(updateOrgNodeApiResponse, 0);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Enter maximum characters in solution display name and name field and validate error response body")
	public void testMaximumCharactersErrorMessage(String[] getOrgTypeAndOrgId) throws IOException {
		logStep("Enter maximum characters in sap id,splution display name and name field and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer(testData.getProperty("invalid.name"),
				getOrgTypeAndOrgId[0], testData.getProperty("invalid.solutionDisplayName"), "true", "US/Central",
				Integer.parseInt(getOrgTypeAndOrgId[1]), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
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
				"true", invalidTimezoneId, Integer.parseInt(getOrgTypeAndOrgId[1]),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForTimezoneField(updateOrgNodeApiResponse, invalidTimezoneId);
	}

	@DataProvider(name = "getOrgDetail")
	public Object[] getOrgTypeAndInvalidOrgId() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("api.system.orgtype"), String.valueOf(systemId).concat("12") },
				{ testData.getProperty("api.enterprise.orgtype"), String.valueOf(enterpriseId).concat("00") },
				{ testData.getProperty("api.practice.orgtype"), String.valueOf(practiceId).concat("022") },
				{ testData.getProperty("api.practice.orgtype"), String.valueOf(agnosticPracticeId).concat("01") } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetail", description = "Enter invalid org id in orgId field and validate error response body")
	public void testErrorMessageForInvalidOrgId(String[] getOrgTypeAndInvalidOrgId) throws IOException {
		logStep("Enter invalid org id in orgId field and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("Org_Name", getOrgTypeAndInvalidOrgId[0],
				"Dispaly_Name_1", "true", "US/Eastern", Integer.parseInt(getOrgTypeAndInvalidOrgId[1]),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
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
		testFlag = testData.getProperty("api.client.testclient");
		clientTimezoneId = testData.getProperty("api.client.timezoneid");
		createClientApiResponse = clientInfo.createNewClient_DataLayer(status, clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
		return createClientApiResponse.jsonPath().getInt("clientId");
	}

	public int createOrgNode(String status, String displayName, String name, String orgType, int parentId,
			String parentType, String timezoneId) throws Exception {
		createOrgNodeApiResponse = orgInfo.createOrg_DataLayer(status, displayName, name, orgType, parentId, parentType,
				timezoneId, Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createOrgNodeApiResponse.jsonPath().getInt("orgId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify active org node status from active to inactive and verify subsequent child nodes also becomes inactive and validate response body. Activate org node from inactive to active when parent node is active and validate response body")
	public void testOrgNodeUpdateStatusFunctionality() throws NullPointerException, Exception {
		logStep("Create active client node");
		int clientId = createClient(testData.getProperty("api.client.active"));
		logStep("Create active system, enterprise ,nge practice and inactive agnostic practice nodes below active client");
		int systemId = createOrgNode(testData.getProperty("api.system.active"), "", "system_name_1",
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"), "");
		int enterpriseId = createOrgNode(testData.getProperty("api.enterprise.active"), "", "enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), "US/Eastern");
		int practiceId = createOrgNode(testData.getProperty("api.practice.active"), "", "practice_name_1",
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), "US/Central");
		int agnosticPracticeId = createOrgNode("false", "", "ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), systemId, testData.getProperty("api.parenttype"),
				"US/Hawaii");
		logStep("Modify system node status from active to inactive");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("system_name_1",
				testData.getProperty("api.system.orgtype"), "", "false", "", systemId,
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, systemId, "system_name_1");
		logStep("verify after updating system node status from active to inactive, subsequent child nodes(Enterprise/NGE practice/Agnostic Practice) also become inactive");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(systemId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, systemId, "system_name_1", "false");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(enterpriseId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, enterpriseId, "enterprise_name_1", "false");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(practiceId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, practiceId, "practice_name_1", "false");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(agnosticPracticeId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, agnosticPracticeId, "ag_practice_name_1", "false");
		logStep("Modify system node status from inactive to active when parent node client is active");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("system_name_1",
				testData.getProperty("api.system.orgtype"), "", "true", "", systemId,
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, systemId, "system_name_1");
		logStep("verify only system node status is changed from inactive to active and subsequent child nodes(Enterprise/NGE practice/Agnostic Practice) remains inactive");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(systemId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, systemId, "system_name_1", "true");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(enterpriseId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, enterpriseId, "enterprise_name_1", "false");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(practiceId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, practiceId, "practice_name_1", "false");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(agnosticPracticeId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, agnosticPracticeId, "ag_practice_name_1", "false");

		logStep("Modify enterprise node status from inactive to active when parent node system is active");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), "", "true", "US/Eastern", enterpriseId,
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
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
				testData.getProperty("api.practice.orgtype"), "", "true", "US/Central", practiceId,
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, practiceId, "practice_name_1");
		logStep("verify NGE practice node status is changed from inactive to active");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(practiceId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, practiceId, "practice_name_1", "true");

		logStep("Modify enterprise node status from active to inactive");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), "", "false", "US/Eastern", enterpriseId,
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, enterpriseId, "enterprise_name_1");
		logStep("verify after updating enterprise node status from active inactive, subsequent child node(NGE practice) also become inactive");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(enterpriseId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, enterpriseId, "enterprise_name_1", "false");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(practiceId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, practiceId, "practice_name_1", "false");

		logStep("Modify agnostic practice node status from inactive to active when parent node system is active");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), "", "true", "US/Hawaii", agnosticPracticeId,
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, agnosticPracticeId, "ag_practice_name_1");
		logStep("verify agnostic practice node status is changed from inactive to active");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(agnosticPracticeId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, agnosticPracticeId, "ag_practice_name_1", "true");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify org node status from inactive to active when parent node is inactive and validate error response body. Modify node name,display name,timezone field values and verify response body")
	public void testActivateOrgNodeWhenParentIsInactive() throws Exception, NullPointerException {
		logStep("Create inactive client node");
		int clientId = createClient("false");
		logStep("Create inactive system, enterprise ,nge practice and agnostic practice nodes below inactive client");
		int systemId = createOrgNode("false", "", "system_name_1", testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), "");
		int enterpriseId = createOrgNode("false", "", "enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), "US/Eastern");
		int practiceId = createOrgNode("false", "", "practice_name_1", testData.getProperty("api.practice.orgtype"),
				enterpriseId, testData.getProperty("api.practice.parenttype"), "US/Central");
		int agnosticPracticeId = createOrgNode("false", "", "ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), systemId, testData.getProperty("api.parenttype"),
				"US/Hawaii");

		logStep("Modify system node status from inactive to active when client node is inactive and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("system_name_1",
				testData.getProperty("api.system.orgtype"), "", "true", "US/Samoa", systemId,
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyInactiveClientErrorResponsforSystem(updateOrgNodeApiResponse);

		logStep("Modify enterprise node status from inactive to active when system node is inactive and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), "", "true", "US/Eastern", enterpriseId,
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyInactiveSystemErrorResponForEnterprise(updateOrgNodeApiResponse);

		logStep("Modify nge practice node status from inactive to active when enterprise node is inactive and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("practice_name_1",
				testData.getProperty("api.practice.orgtype"), "", "true", "US/Central", practiceId,
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyInactivePracticeErrorResponsWhileUpdataingActivePractice(updateOrgNodeApiResponse);
		logStep("Modify agnostic practice node status from inactive to active when system node is inactive and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), "", "true", "US/Hawaii", agnosticPracticeId,
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyInactiveSystemErrorResponsForPractice(updateOrgNodeApiResponse);
		logStep("Update system name value");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer(
				"System_name_2 System_name_2 System_name_2 System_name_2 System_name_2 System_name_2 System_name_2@12",
				testData.getProperty("api.system.orgtype"), "", "false", "", systemId,
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, systemId,
				"System_name_2 System_name_2 System_name_2 System_name_2 System_name_2 System_name_2 System_name_2@12");
		logStep("Fetch updated system details using system id and verify sap number is updated as null value and timezone fetch from its parent node");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(systemId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, systemId,
				"System_name_2 System_name_2 System_name_2 System_name_2 System_name_2 System_name_2 System_name_2@12",
				testData.getProperty("api.system.orgtype"),
				"System_name_2 System_name_2 System_name_2 System_name_2 System_name_2 System_name_2 System_name_2@12",
				"false", testData.getProperty("api.client.timezoneid"), "");
		logStep("Update enterprise name, timezone");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer(
				"enterprise_name_2 enterprise_name_2 enterprise_name_2 enterprise_name_2 enterprise_name_2 enter@1234",
				testData.getProperty("api.enterprise.orgtype"), "", "false", "US/Alaska", enterpriseId,
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, enterpriseId,
				"enterprise_name_2 enterprise_name_2 enterprise_name_2 enterprise_name_2 enterprise_name_2 enter@1234");
		logStep("Fetch updated enterprise details using enterprise id and verify sap number is updated as null value and timezone fetch from its parent node");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(enterpriseId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, enterpriseId,
				"enterprise_name_2 enterprise_name_2 enterprise_name_2 enterprise_name_2 enterprise_name_2 enter@1234",
				testData.getProperty("api.enterprise.orgtype"),
				"enterprise_name_2 enterprise_name_2 enterprise_name_2 enterprise_name_2 enterprise_name_2 enter@1234",
				"false", "US/Alaska", "Alaska Standard Time");

		logStep("Update nge practice name, practice display name,timezone");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer(
				"practice_name_2 practice_name_2 practice_name_2 practice_name_2 practice_name_2 practice_name_2@1234",
				testData.getProperty("api.practice.orgtype"),
				"practice_display_name@123 practice_display_name@123 practice_display_name@123 practice_display_name1",
				"false", "US/Arizona", practiceId, Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, practiceId,
				"practice_name_2 practice_name_2 practice_name_2 practice_name_2 practice_name_2 practice_name_2@1234");
		logStep("Fetch updated nge practice details using practice id and verify sap number updated as null value and timezone fetch from its parent node");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(practiceId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, practiceId,
				"practice_name_2 practice_name_2 practice_name_2 practice_name_2 practice_name_2 practice_name_2@1234",
				testData.getProperty("api.practice.orgtype"),
				"practice_display_name@123 practice_display_name@123 practice_display_name@123 practice_display_name1",
				"false", "US/Arizona", "Arizona Standard Time");

		logStep("Update agnostic practice name, practice display name, timezone");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_DataLayer("ag_Prac_2",
				testData.getProperty("api.practice.orgtype"), "agnostic_practice_display_name", "false",
				"Canada/Atlantic", agnosticPracticeId, Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, agnosticPracticeId, "ag_Prac_2");
		logStep("Fetch updated agnostic practice details using agnostic practice id and verify sap number updated as null value and timezone fetch from its parent node");
		fetchOrgNodeDetails = orgInfo.getOrgId_DataLayer(agnosticPracticeId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeDetails, agnosticPracticeId, "ag_Prac_2",
				testData.getProperty("api.practice.orgtype"), "agnostic_practice_display_name", "false",
				"Canada/Atlantic", "Atlantic Standard Time");

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Verify error message for invalid url")
	public void testErrorMessageForInvalidUrl(String[] getOrgTypeAndOrgId) throws IOException {
		updateOrgNodeApiResponse = orgInfo.updateOrgNodeUsingInvalidUrl_DataLayer("org_name", getOrgTypeAndOrgId[0],
				"org@001", "true", "US/Central", Integer.parseInt(getOrgTypeAndOrgId[1]),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateOrgNodeApiResponse);
	}

}
