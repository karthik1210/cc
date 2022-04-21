// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public class UpdateOrgNodeApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	String clientName, clientSapNumber;
	Response fetchClientDetailsApiResponse, updateOrgNodeApiResponse, createClientApiResponse, createOrgNodeApiResponse,
			fetchOrgNodeDetails;;
	List<Map<String, Object>> solutionList = null;
	List<Map<String, Object>> solutionListOne = null;
	Map<String, Object> solutionMap = new HashMap<String, Object>();

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
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
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("", getOrgTypeAndOrgId[0], "", "", null, "", solutionList,
				Integer.parseInt(getOrgTypeAndOrgId[1]));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageResponseBodyForNullValueInMandatoryField(updateOrgNodeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Enter maximum characters in sap id,splution display name and name field and validate error response body")
	public void testMaximumCharactersErrorMessage(String[] getOrgTypeAndOrgId) throws IOException {
		logStep("Enter maximum characters in sap id,splution display name and name field and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode(testData.getProperty("invalid.name"), getOrgTypeAndOrgId[0],
				testData.getProperty("invalid.solutionDisplayName"), testData.getProperty("invalid.sap"), "true",
				"US/Central", solutionList, Integer.parseInt(getOrgTypeAndOrgId[1]));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyMaximumCharacterErrorMessageInResponseBody(updateOrgNodeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Enter invalid timezone id in timezone field and validate error response body")
	public void testErrorMessageForInvalidTimezoneId(String[] getOrgTypeAndOrgId) throws IOException {
		logStep("Enter invalid timezone id in timezone field and validate error response body");
		String invalidTimezoneId = testData.getProperty("api.system.timezoneid").concat("abcdef@12");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("Org_Name", getOrgTypeAndOrgId[0], "Dispaly_Name_1", "SAP@001",
				"true", invalidTimezoneId, solutionList, Integer.parseInt(getOrgTypeAndOrgId[1]));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForOrgTimezoneIdField(updateOrgNodeApiResponse, invalidTimezoneId);
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
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("Org_Name", getOrgTypeAndInvalidOrgId[0], "Dispaly_Name_1",
				"SAP@001", "true", "US/Eastern", solutionList, Integer.parseInt(getOrgTypeAndInvalidOrgId[1]));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(updateOrgNodeApiResponse,
				Integer.parseInt(getOrgTypeAndInvalidOrgId[1]));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Verify error message for invalid url")
	public void testErrorMessageForInvalidUrl(String[] getOrgTypeAndOrgId) throws IOException {
		updateOrgNodeApiResponse = orgInfo.updateOrgNodeUsingInvalidUrl("org_name", getOrgTypeAndOrgId[0], "org@001",
				"org_sap@001", "true", "US/Central", solutionList, Integer.parseInt(getOrgTypeAndOrgId[1]));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateOrgNodeApiResponse);
	}

	public int createClient(String status) throws Exception {
		clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(status, clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		return createClientApiResponse.jsonPath().getInt("clientId");
	}

	public int createOrgNode(String status, String displayName, String name, String orgType, int parentId,
			String parentType, String sapNumber, String timezoneId, List<Map<String, Object>> productList)
			throws Exception {
		createOrgNodeApiResponse = orgInfo.createOrgNode(status, displayName, name, orgType, parentId, parentType,
				sapNumber, timezoneId, productList);
		return createOrgNodeApiResponse.jsonPath().getInt("orgId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify active org node status from active to inactive and verify subsequent child nodes also becomes inactive and validate response body. Activate org node from inactive to active when parent node is active and validate response body")
	public void testOrgNodeUpdateStatusFunctionality() throws NullPointerException, Exception {
		logStep("Create active client node");
		int clientId = createClient(testData.getProperty("api.client.active"));
		logStep("Create active system, enterprise ,nge practice and inactive agnostic practice nodes below active client");
		int systemId = createOrgNode(testData.getProperty("api.system.active"), "", "system_name_1",
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"), "Sys@001",
				"US/Samoa", solutionList);
		int enterpriseId = createOrgNode(testData.getProperty("api.enterprise.active"), "", "enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), "Ent@001", "US/Eastern", solutionList);
		int practiceId = createOrgNode(testData.getProperty("api.practice.active"), "", "practice_name_1",
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), "Prac@001", "US/Central", solutionList);
		int agnosticPracticeId = createOrgNode("false", "", "ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), clientId, testData.getProperty("api.parenttype"),
				"AGPrac@001", "US/Hawaii", solutionList);

		logStep("Modify system node status from active to inactive");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("system_name_1", testData.getProperty("api.system.orgtype"),
				"", "Sys@001", "false", "US/Samoa", solutionList, systemId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, systemId, "system_name_1");
		logStep("verify after updating system node status from active to inactive, subsequent child nodes(Enterprise/NGE practice) also become inactive");
		fetchOrgNodeDetails = orgInfo.getOrgId(systemId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, systemId, "system_name_1", "false");
		fetchOrgNodeDetails = orgInfo.getOrgId(enterpriseId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, enterpriseId, "enterprise_name_1", "false");
		fetchOrgNodeDetails = orgInfo.getOrgId(practiceId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, practiceId, "practice_name_1", "false");

		logStep("Modify system node status from inactive to active when parent node client is active");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("system_name_1", testData.getProperty("api.system.orgtype"),
				"", "Sys@001", "true", "US/Samoa", solutionList, systemId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, systemId, "system_name_1");
		logStep("verify only system node status is changed from inactive to active and subsequent child nodes(Enterprise/NGE practice) remains inactive");
		fetchOrgNodeDetails = orgInfo.getOrgId(systemId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, systemId, "system_name_1", "true");
		fetchOrgNodeDetails = orgInfo.getOrgId(enterpriseId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, enterpriseId, "enterprise_name_1", "false");
		fetchOrgNodeDetails = orgInfo.getOrgId(practiceId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, practiceId, "practice_name_1", "false");

		logStep("Modify enterprise node status from inactive to active when parent node system is active");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), "", "Ent@001", "true", "US/Eastern", solutionList,
				enterpriseId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, enterpriseId, "enterprise_name_1");
		logStep("verify only enterprise node status is changed from inactive to active and subsequent child nodes(NGE practice) remains inactive");
		fetchOrgNodeDetails = orgInfo.getOrgId(enterpriseId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, enterpriseId, "enterprise_name_1", "true");
		fetchOrgNodeDetails = orgInfo.getOrgId(practiceId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, practiceId, "practice_name_1", "false");

		logStep("Modify nge practice node status from inactive to active when parent node enterprise is active");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("practice_name_1",
				testData.getProperty("api.practice.orgtype"), "", "Prac@001", "true", "US/Central", solutionList,
				practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, practiceId, "practice_name_1");
		logStep("verify NGE practice node status is changed from inactive to active");
		fetchOrgNodeDetails = orgInfo.getOrgId(practiceId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, practiceId, "practice_name_1", "true");

		logStep("Modify enterprise node status from active to inactive");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), "", "Ent@001", "false", "US/Eastern", solutionList,
				enterpriseId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, enterpriseId, "enterprise_name_1");
		logStep("verify after updating enterprise node status from active inactive, subsequent child node(NGE practice) also become inactive");
		fetchOrgNodeDetails = orgInfo.getOrgId(enterpriseId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, enterpriseId, "enterprise_name_1", "false");
		fetchOrgNodeDetails = orgInfo.getOrgId(practiceId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, practiceId, "practice_name_1", "false");

		logStep("Modify agnostic practice node status from inactive to active when parent node client is active");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), "", "AGPrac@001", "true", "US/Hawaii", solutionList,
				agnosticPracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, agnosticPracticeId, "ag_practice_name_1");
		logStep("verify agnostic practice node status is changed from inactive to active");
		fetchOrgNodeDetails = orgInfo.getOrgId(agnosticPracticeId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, agnosticPracticeId, "ag_practice_name_1", "true");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify org node status from inactive to active when parent node is inactive and validate error response body")
	public void testActivateOrgNodeWhenParentIsInactive() throws Exception, NullPointerException {
		logStep("Create inactive client node");
		int clientId = createClient("false");
		logStep("Create inactive system, enterprise ,nge practice and agnostic practice nodes below inactive client");
		int systemId = createOrgNode("false", "", "system_name_1", testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), "Sys@001", "US/Samoa", solutionList);
		int enterpriseId = createOrgNode("false", "", "enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), "Ent@001", "US/Eastern", solutionList);
		int practiceId = createOrgNode("false", "", "practice_name_1", testData.getProperty("api.practice.orgtype"),
				enterpriseId, testData.getProperty("api.practice.parenttype"), "Prac@001", "US/Central", solutionList);
		int agnosticPracticeId = createOrgNode("false", "", "ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), clientId, testData.getProperty("api.parenttype"),
				"AGPrac@001", "US/Hawaii", solutionList);

		logStep("Modify system node status from inactive to active when client node is inactive and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("system_name_1", testData.getProperty("api.system.orgtype"),
				"", "Sys@001", "true", "US/Samoa", solutionList, systemId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyInactiveClientErrorResponsforSystem(updateOrgNodeApiResponse);

		logStep("Modify enterprise node status from inactive to active when system node is inactive and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), "", "Ent@001", "true", "US/Eastern", solutionList,
				enterpriseId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyInactiveSystemErrorResponForEnterprise(updateOrgNodeApiResponse);

		logStep("Modify nge practice node status from inactive to active when enterprise node is inactive and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("practice_name_1",
				testData.getProperty("api.practice.orgtype"), "", "Prac@001", "true", "US/Central", solutionList,
				practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyInactivePracticeErrorResponsWhileUpdataingActivePractice(updateOrgNodeApiResponse);

		logStep("Modify agnostic practice node status from inactive to active when client node is inactive and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), "", "AGPrac@001", "true", "US/Hawaii", solutionList,
				agnosticPracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyInactiveClientErrorResponsForPractice(updateOrgNodeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create org nodes(Enter new value in sap id and timezone field) with solution and Update org node using valid testdata(Sap id and timezone=not null value and modify solution status) and validate response body")
	public void testUpdateNgePracticeSolutionStatus() throws Exception, NullPointerException {
		logStep("Create active client node");
		int clientId = createClient(testData.getProperty("api.client.active"));
		logStep("Create active system, enterprise ,nge practice nodes(enter new value in sap id and timezone field) below active client");
		int systemId = createOrgNode("true", "s1", "system_name_1", testData.getProperty("api.system.orgtype"),
				clientId, testData.getProperty("api.parenttype"), "systemSap@001", "US/Alaska", solutionList);
		int enterpriseId = createOrgNode("true", "e1", "enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), "entSap@001", "US/Central", solutionList);
		solutionList = new ArrayList<Map<String, Object>>();
		solutionMap.put("solutionId", testData.getProperty("Portal.id"));
		solutionMap.put("status", testData.getProperty("status.newly.purchased"));
		solutionList.add(solutionMap);
		int practiceId = createOrgNode("true", "p1", "practice_name_1", testData.getProperty("api.practice.orgtype"),
				enterpriseId, testData.getProperty("api.practice.parenttype"), "prac@001", "US/Pacific", solutionList);

		logStep("Update system name, system display name, sap id, timezone (enter not null value in sap id and timezone field)");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("sys1", testData.getProperty("api.system.orgtype"), "",
				"s_sap@001", "true", "US/Samoa", solutionList, systemId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, systemId, "sys1");
		logStep("Fetch updated system details using system id and verify updated system details");
		fetchOrgNodeDetails = orgInfo.getOrgId(systemId);
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeDetails, systemId, "sys1",
				testData.getProperty("api.system.orgtype"), testData.getProperty("api.client.type"), "sys1",
				"s_sap@001", "true", testData.getProperty("api.client.testclient"), "US/Samoa", "Midway Island, Samoa",
				1, true, solutionList);

		logStep("Update enterprise name, enterprise display name, sap id, timezone (enter not null value in sap id and timezone field)");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("ent2", testData.getProperty("api.enterprise.orgtype"), "",
				"e_Sap@01", "true", "US/Arizona", solutionList, enterpriseId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, enterpriseId, "ent2");
		logStep("Fetch updated enterprise details using enterprise id and  and verify updated enterprise details");
		fetchOrgNodeDetails = orgInfo.getOrgId(enterpriseId);
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeDetails, enterpriseId, "ent2",
				testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.client.type"), "ent2",
				"e_Sap@01", "true", testData.getProperty("api.client.testclient"), "US/Arizona",
				"Arizona Standard Time", 1, true, solutionList);

		logStep("Update nge practice name, practice display name, sap id, timezone (enter not null value in sap id and timezone field) and terminate activate solution");
		solutionList = new ArrayList<Map<String, Object>>();
		solutionMap.put("solutionId", testData.getProperty("Portal.id"));
		solutionMap.put("status", testData.getProperty("status.terminated"));
		solutionList.add(solutionMap);
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("prac2", testData.getProperty("api.practice.orgtype"), "p2",
				"pracSap@002", "true", "US/Mountain", solutionList, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, practiceId, "prac2");
		logStep("Fetch updated nge practice details using practice id and verify practice details");
		fetchOrgNodeDetails = orgInfo.getOrgId(practiceId);
		logStep("Verify practice count and solutions list is displayed on nge practice details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeDetails, practiceId, "prac2",
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"), "p2",
				"pracSap@002", "true", testData.getProperty("api.client.testclient"), "US/Mountain",
				"Mountain Standard Time", 1, true, solutionList);
		fetchOrgNodeDetails = orgInfo.getOrgId(enterpriseId);
		logStep("Verify practice count and null solutions list is displayed on enterprise details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeDetails, enterpriseId, "ent2",
				testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.client.type"), "ent2",
				"e_Sap@01", "true", testData.getProperty("api.client.testclient"), "US/Arizona",
				"Arizona Standard Time", 1, false, null);
		fetchOrgNodeDetails = orgInfo.getOrgId(systemId);
		logStep("Verify practice count and null solutions list is displayed on system details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeDetails, systemId, "sys1",
				testData.getProperty("api.system.orgtype"), testData.getProperty("api.client.type"), "sys1",
				"s_sap@001", "true", testData.getProperty("api.client.testclient"), "US/Samoa", "Midway Island, Samoa",
				1, false, null);

		logStep("Update nge practice node details(Activate terminated solution)");
		solutionList = new ArrayList<Map<String, Object>>();
		solutionMap.put("solutionId", testData.getProperty("Portal.id"));
		solutionMap.put("status", testData.getProperty("status.active.implementation"));
		solutionList.add(solutionMap);
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("prac2", testData.getProperty("api.practice.orgtype"), "p2",
				"pracSap@002", "true", "US/Mountain", solutionList, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, practiceId, "prac2");
		logStep("Fetch updated nge practice details using practice id and verify practice details");
		fetchOrgNodeDetails = orgInfo.getOrgId(practiceId);
		logStep("Verify practice count and active solutions details is displayed on nge practice details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeDetails, practiceId, "prac2",
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"), "p2",
				"pracSap@002", "true", testData.getProperty("api.client.testclient"), "US/Mountain",
				"Mountain Standard Time", 1, true, solutionList);
		fetchOrgNodeDetails = orgInfo.getOrgId(enterpriseId);
		logStep("Verify practice count and active solutions details is displayed on enterprise details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeDetails, enterpriseId, "ent2",
				testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.client.type"), "ent2",
				"e_Sap@01", "true", testData.getProperty("api.client.testclient"), "US/Arizona",
				"Arizona Standard Time", 1, true, solutionList);
		fetchOrgNodeDetails = orgInfo.getOrgId(systemId);
		logStep("Verify practice count and active solutions details is displayed on system details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeDetails, systemId, "sys1",
				testData.getProperty("api.system.orgtype"), testData.getProperty("api.client.type"), "sys1",
				"s_sap@001", "true", testData.getProperty("api.client.testclient"), "US/Samoa", "Midway Island, Samoa",
				1, true, solutionList);

		logStep("Update nge practice status from active to inactive");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("prac2", testData.getProperty("api.practice.orgtype"), "p2",
				"pracSap@002", "false", "US/Mountain", solutionList, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, practiceId, "prac2");
		logStep("Fetch updated nge practice details using practice id and verify practice details");
		fetchOrgNodeDetails = orgInfo.getOrgId(practiceId);
		logStep("Verify practice count and active solutions details is displayed on nge practice details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeDetails, practiceId, "prac2",
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"), "p2",
				"pracSap@002", "false", testData.getProperty("api.client.testclient"), "US/Mountain",
				"Mountain Standard Time", 1, true, solutionList);
		fetchOrgNodeDetails = orgInfo.getOrgId(enterpriseId);
		logStep("Verify practice count=0 and null solutions list is displayed on enterprise details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeDetails, enterpriseId, "ent2",
				testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.client.type"), "ent2",
				"e_Sap@01", "true", testData.getProperty("api.client.testclient"), "US/Arizona",
				"Arizona Standard Time", 0, false, null);
		fetchOrgNodeDetails = orgInfo.getOrgId(systemId);
		logStep("Verify practice count=0 and null solutions list is displayed on system details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeDetails, systemId, "sys1",
				testData.getProperty("api.system.orgtype"), testData.getProperty("api.client.type"), "sys1",
				"s_sap@001", "true", testData.getProperty("api.client.testclient"), "US/Samoa", "Midway Island, Samoa",
				0, false, null);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create agnostic practice node(Enter new value in sap id and timezone field) with solution and Update org node using valid testdata(Sap id and timezone=not null value and modify solution status) and validate response body")
	public void testUpdateAgnosticPracticeSolutionStatus() throws Exception, NullPointerException {
		logStep("Create active client node");
		int clientId = createClient(testData.getProperty("api.client.active"));
		logStep("Create active system, enterprise ,nge practice nodes(enter new value in sap id and timezone field) below active client");
		solutionListOne = new ArrayList<Map<String, Object>>();
		solutionMap.put("solutionId", testData.getProperty("forms.id"));
		solutionMap.put("status", testData.getProperty("status.newly.purchased"));
		solutionListOne.add(solutionMap);
		int agnosticPracticeId = createOrgNode("true", "ap1", "ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), clientId, testData.getProperty("api.parenttype"),
				"apPrac@001", "US/Hawaii", solutionListOne);

		logStep("Update agnostic practice name, practice display name, sap id, timezone (enter not null value in sap id and timezone field) and terminate active solution");
		solutionListOne = new ArrayList<Map<String, Object>>();
		solutionMap.put("solutionId", testData.getProperty("forms.id"));
		solutionMap.put("status", testData.getProperty("status.terminated"));
		solutionListOne.add(solutionMap);
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("ag_prac2", testData.getProperty("api.practice.orgtype"),
				"agp2", "ag_Sap@002", "true", "Canada/Atlantic", solutionListOne, agnosticPracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, agnosticPracticeId, "ag_prac2");
		logStep("Fetch updated agnostic practice details using agnostic practice id and verify practice details");
		fetchOrgNodeDetails = orgInfo.getOrgId(agnosticPracticeId);
		logStep("Verify practice count and solution list displayed on practice details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeDetails, agnosticPracticeId, "ag_prac2",
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"), "agp2",
				"ag_Sap@002", "true", testData.getProperty("api.client.testclient"), "Canada/Atlantic",
				"Atlantic Standard Time", 1, true, solutionListOne);
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(Integer.toString(clientId));
		logStep("Verify practice count and null solution list displayed on client details page");
		validation.verifySearchClientIdResponseBody(fetchClientDetailsApiResponse, Integer.toString(clientId),
				clientName, testData.getProperty("api.client.type"), clientSapNumber,
				testData.getProperty("api.client.active"), testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("api.client.displayname"), 0, null);

		solutionListOne = new ArrayList<Map<String, Object>>();
		solutionMap.put("solutionId", testData.getProperty("forms.id"));
		solutionMap.put("status", testData.getProperty("status.active.implementation"));
		solutionListOne.add(solutionMap);
		logStep("Activate terminated solution");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("ag_prac2", testData.getProperty("api.practice.orgtype"),
				"agp2", "ag_Sap@002", "true", "Canada/Atlantic", solutionListOne, agnosticPracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, agnosticPracticeId, "ag_prac2");
		logStep("Fetch updated agnostic practice details using agnostic practice id and verify practice details");
		fetchOrgNodeDetails = orgInfo.getOrgId(agnosticPracticeId);
		logStep("Verify practice count and solution list displayed on practice details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeDetails, agnosticPracticeId, "ag_prac2",
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"), "agp2",
				"ag_Sap@002", "true", testData.getProperty("api.client.testclient"), "Canada/Atlantic",
				"Atlantic Standard Time", 1, true, solutionListOne);
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(Integer.toString(clientId));
		logStep("Verify practice count and solution list displayed on client details page");
		validation.verifySearchClientIdResponseBody(fetchClientDetailsApiResponse, Integer.toString(clientId),
				clientName, testData.getProperty("api.client.type"), clientSapNumber,
				testData.getProperty("api.client.active"), testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("api.client.displayname"), 1,
				solutionListOne);

		logStep("Update practice status from active to inactive");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode("ag_prac2", testData.getProperty("api.practice.orgtype"),
				"agp2", "ag_Sap@002", "false", "Canada/Atlantic", solutionListOne, agnosticPracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, agnosticPracticeId, "ag_prac2");
		logStep("Fetch updated agnostic practice details using agnostic practice id and verify practice details");
		fetchOrgNodeDetails = orgInfo.getOrgId(agnosticPracticeId);
		logStep("Verify practice count and solution list displayed on practice details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeDetails, agnosticPracticeId, "ag_prac2",
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"), "agp2",
				"ag_Sap@002", "false", testData.getProperty("api.client.testclient"), "Canada/Atlantic",
				"Atlantic Standard Time", 1, true, solutionListOne);
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(Integer.toString(clientId));
		logStep("Verify practice count=0 and null solution list displayed on client details page");
		validation.verifySearchClientIdResponseBody(fetchClientDetailsApiResponse, Integer.toString(clientId),
				clientName, testData.getProperty("api.client.type"), clientSapNumber,
				testData.getProperty("api.client.active"), testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("api.client.displayname"), 0, null);
	}

}
