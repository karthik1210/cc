// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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

public class CreateOrgNodeApiTests_BusinessLayer extends BaseRest {

	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	Response createClientApiResponse, fetchClientDetailsApiResponse, createOrgNodeApiResponse, fetchOrgNodeApiResponse;
	List<Map<String, Object>> solutionList = null;
	List<Map<String, Object>> solutionListTwo = null;
	Map<String, Object> solutionMap = new HashMap<String, Object>();
	Map<String, Object> solutionMapTwo = new HashMap<String, Object>();

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create active org nodes below active client(enter new details in sap id and timezone field)")
	public void testCreateActiveOrgNodesBelowActiveClient() throws Exception {
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
				solutionList);
		int systemId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "System_name_1");
		logStep("Fetched newly created system details using system id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(systemId);
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, systemId, "System_name_1",
				testData.getProperty("api.system.orgtype"), testData.getProperty("api.client.type"), "System1",
				testData.getProperty("api.system.sapnumber"), testData.getProperty("api.system.active"),
				testData.getProperty("api.client.testclient"), "US/Central", "Central Standard Time", 0, false,
				solutionList);

		logStep("Create an active enterprise node below system");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.enterprise.active"), "Enterprise1",
				"Enterprise_name_1", testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.sapnumber"),
				"US/Samoa", solutionList);
		int enterpriseId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "Enterprise_name_1");
		logStep("Fetched newly created enterprise details using enterprise id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(enterpriseId);
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, enterpriseId, "Enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.client.type"), "Enterprise1",
				testData.getProperty("api.enterprise.sapnumber"), testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.client.testclient"), "US/Samoa", "Midway Island, Samoa", 0, false,
				solutionList);

		logStep("Create an active agnostic practice node with solutions below client");
		solutionList = new ArrayList<Map<String, Object>>();
		solutionMap.put("solutionId", testData.getProperty("Portal.id"));
		solutionMap.put("status", testData.getProperty("status.newly.purchased"));
		solutionList.add(solutionMap);
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.practice.active"), "AgnoPractice1",
				"Agnostic_Practice_Name_1", testData.getProperty("api.practice.orgtype"), clientId, "CLIENT",
				testData.getProperty("api.practice.sapnumber"), "US/Hawaii", solutionList);
		int practiceId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "Agnostic_Practice_Name_1");
		logStep("Fetched newly created agnostic practice details using agnostic practice id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(practiceId);
		List<Response> practiceResponse = new ArrayList<Response>();
		practiceResponse.add(fetchOrgNodeApiResponse);
		int practiceCount = validation.validateActiveandSolutionStatusInGetPracticeDetails(practiceResponse);
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, practiceId,
				"Agnostic_Practice_Name_1", testData.getProperty("api.practice.orgtype"),
				testData.getProperty("api.client.type"), "AgnoPractice1",
				testData.getProperty("api.practice.sapnumber"), testData.getProperty("api.practice.active"),
				testData.getProperty("api.client.testclient"), "US/Hawaii", "Hawaii Standard Time", practiceCount, true,
				solutionList);
		logStep("Verify active practice count and newly purchased solution is displayed on client details page");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(Integer.toString(clientId));
		validation.verifySearchClientIdResponseBody(fetchClientDetailsApiResponse, Integer.toString(clientId),
				clientName, testData.getProperty("api.client.type"), clientSapNumber,
				testData.getProperty("api.client.active"), testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("api.client.displayname"),
				practiceCount, solutionList);

		logStep("Create an active NGE practice node with solutions below enterprise");
		List<Map<String, Object>> solutionListOne = new ArrayList<Map<String, Object>>();
		solutionMap.put("solutionId", testData.getProperty("Portal.id"));
		solutionMap.put("status", testData.getProperty("status.newly.purchased"));
		solutionListOne.add(solutionMap);
		List<Map<String, Object>> solutionListTwo = new ArrayList<Map<String, Object>>();
		solutionMapTwo.put("solutionId", testData.getProperty("forms.id"));
		solutionMapTwo.put("status", testData.getProperty("status.newly.purchased"));
		solutionListTwo.add(solutionMapTwo);
		List<Map<String, Object>> finalSolutionLists = new ArrayList<Map<String, Object>>();
		finalSolutionLists.addAll(solutionListOne);
		finalSolutionLists.addAll(solutionListTwo);
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.practice.active"), "NgePractice1",
				"NGE_Practice_Name_1", testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.sapnumber"),
				"US/Pacific", finalSolutionLists);
		int ngePracticeId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "NGE_Practice_Name_1");
		logStep("Fetched newly created nge practice details using nge practice id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(ngePracticeId);
		practiceResponse.add(fetchOrgNodeApiResponse);
		int pracCount = validation.validateActiveandSolutionStatusInGetPracticeDetails(practiceResponse);
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, ngePracticeId, "NGE_Practice_Name_1",
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"), "NgePractice1",
				testData.getProperty("api.practice.sapnumber"), testData.getProperty("api.practice.active"),
				testData.getProperty("api.client.testclient"), "US/Pacific", "Pacific Standard Time", 1, true,
				finalSolutionLists);
		logStep("Verify active practice count and newly purchased solutions are displayed on client details page");
		List<Map<String, Object>> solutionDetailList = new ArrayList<Map<String, Object>>();
		solutionDetailList.addAll(finalSolutionLists);
		solutionDetailList.addAll(solutionList);
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(Integer.toString(clientId));
		validation.verifySearchClientIdResponseBody(fetchClientDetailsApiResponse, Integer.toString(clientId),
				clientName, testData.getProperty("api.client.type"), clientSapNumber,
				testData.getProperty("api.client.active"), testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("api.client.displayname"),
				pracCount, solutionDetailList);
		logStep("Verify active practice count and newly purchased solutions are displayed on system details page");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(systemId);
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, systemId, "System_name_1",
				testData.getProperty("api.system.orgtype"), testData.getProperty("api.client.type"), "System1",
				testData.getProperty("api.system.sapnumber"), testData.getProperty("api.system.active"),
				testData.getProperty("api.client.testclient"), "US/Central", "Central Standard Time", 1, true,
				finalSolutionLists);
		logStep("Verify active practice count and newly purchased solutions are displayed on enterprise details page");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(enterpriseId);
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, enterpriseId, "Enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.client.type"), "Enterprise1",
				testData.getProperty("api.enterprise.sapnumber"), testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.client.testclient"), "US/Samoa", "Midway Island, Samoa", 1, true,
				finalSolutionLists);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create inactive org nodes below active client(inherit sap id and timezone details from parent node)")
	public void testCreateInactiveOrgNodesBelowActiveClient() throws Exception {

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
		int systemId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "System_name_1");

		logStep("Create an inactive enterprise node below system");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.enterprise.active"), "",
				"Enterprise_name_1", testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), "", "", solutionList);
		int enterpriseId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "Enterprise_name_1");

		logStep("Create an inactive practice node with solutions below client");
		solutionList = new ArrayList<Map<String, Object>>();
		solutionMap.put("solutionId", testData.getProperty("Portal.id"));
		solutionMap.put("status", testData.getProperty("status.newly.purchased"));
		solutionList.add(solutionMap);
		createOrgNodeApiResponse = orgInfo.createOrgNode("false", "", "Agnostic_Practice_Name_1",
				testData.getProperty("api.practice.orgtype"), clientId, "CLIENT", "", "", solutionList);
		int practiceId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "Agnostic_Practice_Name_1");

		logStep("Create an inactive NGE practice node with solutions below enterprise");
		List<Map<String, Object>> solutionListOne = new ArrayList<Map<String, Object>>();
		solutionMap.put("solutionId", testData.getProperty("forms.id"));
		solutionMap.put("status", testData.getProperty("status.newly.purchased"));
		solutionListOne.add(solutionMap);
		createOrgNodeApiResponse = orgInfo.createOrgNode("false", "", "NGE_Practice_Name_1",
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), "", "", solutionListOne);
		int ngePracticeId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, "NGE_Practice_Name_1");

		logStep("Fetched newly created system details using system id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(systemId);
		logStep("Verify practice count and solutions activated within inactive nge practices are not displayed on system details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, systemId, "System_name_1",
				testData.getProperty("api.system.orgtype"), testData.getProperty("api.client.type"), "System_name_1",
				clientSapNumber, "false", testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("api.client.displayname"), 0, false,
				null);

		logStep("Fetched newly created enterprise details using enterprise id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(enterpriseId);
		logStep("Verify practice count and solutions activated within inactive nge practices are not displayed on enterprise details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, enterpriseId, "Enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.client.type"),
				"Enterprise_name_1", clientSapNumber, "false", testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("api.client.displayname"), 0, false,
				null);

		logStep("Fetched newly created agnostic practice details using practice id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(practiceId);
		logStep("Verify practice count and solutions activated within inactive agnostic practices are  displayed on agnostic practice details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, practiceId,
				"Agnostic_Practice_Name_1", testData.getProperty("api.practice.orgtype"),
				testData.getProperty("api.client.type"), "Agnostic_Practice_Name_1", clientSapNumber, "false",
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.timezoneid"),
				testData.getProperty("api.client.displayname"), 1, true, solutionList);

		logStep("Fetched newly created nge practice details using nge practice id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(ngePracticeId);
		logStep("Verify practice count and solutions activated within inactive nge practices are displayed on nge practice details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, ngePracticeId, "NGE_Practice_Name_1",
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"),
				"NGE_Practice_Name_1", clientSapNumber, "false", testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("api.client.displayname"), 1, true,
				solutionListOne);

		logStep("Fetched client details using client id");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(Integer.toString(clientId));
		logStep("Verify practice count and solutions activated within inactive practices are not displayed on client details page");
		validation.verifySearchClientIdResponseBody(fetchClientDetailsApiResponse, Integer.toString(clientId),
				clientName, testData.getProperty("api.client.type"), clientSapNumber,
				testData.getProperty("api.client.active"), testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("api.client.displayname"), 0, null);

	}

	@DataProvider(name = "getTypes")
	public Object[] getOrgAndPatentTypes() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("api.system.orgtype"), testData.getProperty("api.parenttype") },
				{ testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.enterprise.parenttype") },
				{ testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.parenttype") },
				{ testData.getProperty("api.practice.orgtype"), testData.getProperty("api.parenttype") } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getTypes", description = "Enter null value in mandatory field and verify response body")
	public void testErrorMessageForMandatoryFields(String[] getOrgAndPatentTypes) throws Exception {
		logStep("Enter null value in mandatory field");
		createOrgNodeApiResponse = orgInfo.createOrgNode(null, "", "", getOrgAndPatentTypes[0], null,
				getOrgAndPatentTypes[1], "", "", solutionList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageResponseBodyForMandatoryFields(createOrgNodeApiResponse);
	}

	@DataProvider(name = "getOrgDetails")
	public Object[] getOrgParentTypesAndParentId() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("api.system.orgtype"), testData.getProperty("api.parenttype"),
						testData.getProperty("api.existing.client.id") },
				{ testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.enterprise.parenttype"),
						testData.getProperty("api.enterprise.parentid") },
				{ testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.parenttype"),
						testData.getProperty("api.practice.parentid") },
				{ testData.getProperty("api.practice.orgtype"), testData.getProperty("api.parenttype"),
						testData.getProperty("api.existing.client.id") } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Enter more than 100 characters in org name, solution display name field and more than 50 characters in sap id field and verify error response body")
	public void testMaximumCharactersErrorMessage(String[] getOrgParentTypesAndParentId) throws Exception {
		logStep("Enter more than 100 characters in org name, solution display name field and more than 50 characters in sap id field");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.client.active"),
				testData.getProperty("invalid.solutionDisplayName"), testData.getProperty("invalid.name"),
				getOrgParentTypesAndParentId[0], Integer.parseInt(getOrgParentTypesAndParentId[2]),
				getOrgParentTypesAndParentId[1], testData.getProperty("invalid.sap"), "", solutionList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyMaximumCharacterErrorMessageInResponseBody(createOrgNodeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Enter invalid timezone id in timezone field and verify error response body")
	public void testErrorResponseForInvalidTimezoneId(String[] getOrgParentTypesAndParentId) throws Exception {
		logStep("Enter invalid timezone id in timezone field");
		String invalidTimezoneId = testData.getProperty("api.system.timezoneid").concat("abcdef@12");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.client.active"),
				testData.getProperty("api.system.displayname"), "node_name", getOrgParentTypesAndParentId[0],
				Integer.parseInt(getOrgParentTypesAndParentId[2]), getOrgParentTypesAndParentId[1],
				testData.getProperty("api.system.sapnumber"), invalidTimezoneId, solutionList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForTimezoneField(createOrgNodeApiResponse, invalidTimezoneId);
	}

	@DataProvider(name = "getInvalidParentTypes")
	public Object[] getParentIdOrgTypeAndInvalidParentTypes() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("api.system.orgtype"), "SYSTEMCLIENT ",
						testData.getProperty("api.existing.client.id") },
				{ testData.getProperty("api.enterprise.orgtype"), "PRACTICE",
						testData.getProperty("api.enterprise.parentid") },
				{ testData.getProperty("api.practice.orgtype"), "PRACTICE",
						testData.getProperty("api.practice.parentid") },
				{ testData.getProperty("api.practice.orgtype"), "CLIENTSS",
						testData.getProperty("api.existing.client.id") } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getInvalidParentTypes", description = "Enter invalid parent type in parent type field and verify error response body")
	public void testErrorResponseForInvalidParentType(String[] getParentIdOrgTypeAndInvalidParentTypes)
			throws Exception {
		logStep("Enter invalid parent type in parent type field");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.client.active"),
				testData.getProperty("api.system.displayname"), "node_name", getParentIdOrgTypeAndInvalidParentTypes[0],
				Integer.parseInt(getParentIdOrgTypeAndInvalidParentTypes[2]),
				getParentIdOrgTypeAndInvalidParentTypes[1], testData.getProperty("api.system.sapnumber"),
				testData.getProperty("api.system.timezoneid"), solutionList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponsForInvalidParentType(createOrgNodeApiResponse);
	}

	@DataProvider(name = "getParentId")
	public Object[][] getParentIdDetails() throws Exception {
		return new Object[][] { { testData.getProperty("api.existing.client.id") },
				{ testData.getProperty("api.enterprise.parentid") }, { testData.getProperty("api.practice.parentid") },
				{ testData.getProperty("api.existing.client.id") } };
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getParentId", description = "Enter blank space in org name ,org type and parent type fields and verify error response body")
	public void testErrorResponseForBlankFields(String getParentIdDetails) throws Exception {
		logStep("Enter blank space in org name ,org type and parent type fields");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.client.active"),
				testData.getProperty("api.system.displayname"), " ", " ", Integer.parseInt(getParentIdDetails), " ",
				testData.getProperty("api.system.sapnumber"), testData.getProperty("api.system.timezoneid"),
				solutionList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForBlankFields(createOrgNodeApiResponse);
	}

	@DataProvider(name = "getInvalidOrgType")
	public Object[] getInvalidOrgTypeDetails() {
		Object[][] obj = new Object[][] {
				{ "SYS TEM", testData.getProperty("api.parenttype"), testData.getProperty("api.existing.client.id") },
				{ "ENTERPRISEE", testData.getProperty("api.enterprise.parenttype"),
						testData.getProperty("api.enterprise.parentid") },
				{ "PRACTICEE", testData.getProperty("api.practice.parenttype"),
						testData.getProperty("api.practice.parentid") },
				{ "PRAC TICE", testData.getProperty("api.parenttype"),
						testData.getProperty("api.existing.client.id") } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getInvalidOrgType", description = "Enter invalid org type in orgType field and verify error response body")
	public void testErrorResponseForInvalidOrgType(String[] getInvalidOrgTypeDetails) throws Exception {
		logStep("Enter invalid org type in orgType field");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.client.active"),
				testData.getProperty("api.system.displayname"), "node_name", getInvalidOrgTypeDetails[0],
				Integer.parseInt(getInvalidOrgTypeDetails[2]), getInvalidOrgTypeDetails[1],
				testData.getProperty("api.system.sapnumber"), testData.getProperty("api.system.timezoneid"),
				solutionList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidOrgType(createOrgNodeApiResponse, getInvalidOrgTypeDetails[0]);
	}

	@DataProvider(name = "getPracticeNodeDetails")
	public Object[] getPracticeNodeParentTypesAndParentId() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.parenttype"),
						testData.getProperty("api.practice.parentid") },
				{ testData.getProperty("api.practice.orgtype"), testData.getProperty("api.parenttype"),
						testData.getProperty("api.existing.client.id") } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getPracticeNodeDetails", description = "Enter invalid solution id and solution status and verify error response body")
	public void testErrorResponseForInvalidSolutionIdAndStatus(String[] getPracticeNodeParentTypesAndParentId)
			throws Exception {
		logStep("Enter invalid solution id and solution status");
		solutionList = new ArrayList<Map<String, Object>>();
		solutionMap.put("solutionId", null);
		solutionMap.put("status", "");
		solutionList.add(solutionMap);
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.client.active"),
				testData.getProperty("api.practice.displayname"), "practice", getPracticeNodeParentTypesAndParentId[0],
				Integer.parseInt(getPracticeNodeParentTypesAndParentId[2]), getPracticeNodeParentTypesAndParentId[1],
				testData.getProperty("api.practice.sapnumber"), testData.getProperty("api.practice.timezoneid"),
				solutionList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForSolutionIdAndStatusField(createOrgNodeApiResponse);
	}

	@DataProvider(name = "orgNodeDetails")
	public Object[] orgNodeDetail() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("api.system.orgtype"), testData.getProperty("api.parenttype"),
						testData.getProperty("api.nonexisting.client.id") },
				{ testData.getProperty("api.practice.orgtype"), testData.getProperty("api.parenttype"),
						testData.getProperty("api.nonexisting.client.id") } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "orgNodeDetails", description = "Enter non existing client id in parent id field and verify error response body")
	public void testErrorResponseForNonExistingClientId(String[] orgNodeDetail) throws Exception {
		logStep("Enter non existing client id in parent id field");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.client.active"),
				testData.getProperty("api.practice.displayname"), "practice", orgNodeDetail[0],
				Integer.parseInt(orgNodeDetail[2]), orgNodeDetail[1], testData.getProperty("api.practice.sapnumber"),
				testData.getProperty("api.practice.timezoneid"), solutionList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForInvalidClientIdInParentIdField(createOrgNodeApiResponse,
				Integer.parseInt(orgNodeDetail[2]));
	}

	@DataProvider(name = "orgNodeData")
	public Object[] orgNodeData() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.enterprise.parenttype"),
						"1234" },
				{ testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.parenttype"),
						"3344" } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "orgNodeData", description = "Enter non existing org id in org id field and verify error response body")
	public void testErrorResponseForNonExistingOrgIdValueInParentIdField(String[] orgNodeData) throws Exception {
		logStep("Enter non existing org id in org id field");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.client.active"),
				testData.getProperty("api.enterprise.displayname"), "node_name_1", orgNodeData[0],
				Integer.parseInt(orgNodeData[2]), orgNodeData[1], testData.getProperty("api.enterprise.sapnumber"),
				testData.getProperty("api.enterprise.timezoneid"), solutionList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForInvalidOrgIdInParentIdField(createOrgNodeApiResponse,
				Integer.parseInt(orgNodeData[2]));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create active org nodes below inactive client(enter blank space in sap id field)")
	public void testCreateActiveOrgNodesBelowInactiveClient() throws Exception {

		logStep("Create an inactive client node");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient("false", clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"), null);
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");

		String systemDisplayName = CCUtils
				.getName(CCUtils.generateStringWithSpace(CCUtils.generateAlphaNumericString(100)));
		String systemName = CCUtils.getName(CCUtils.generateStringWithSpace(CCUtils.generateAlphaNumericString(100)));
		logStep("Create an active system node below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.system.active"), systemDisplayName,
				systemName, testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), " ", "", solutionListTwo);
		int systemId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, systemName);

		logStep("Create an active enterprise node below system");
		String enterpriseDisplayName = CCUtils
				.getName(CCUtils.generateStringWithSpace(CCUtils.generateAlphaNumericString(100)));
		String enterpriseName = CCUtils
				.getName(CCUtils.generateStringWithSpace(CCUtils.generateAlphaNumericString(100)));
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.enterprise.active"),
				enterpriseDisplayName, enterpriseName, testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), " ", "", solutionListTwo);
		int enterpriseId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, enterpriseName);

		logStep("Create an active agnostic practice node without solutions below client");
		String agnoPracticeDisplayName = CCUtils
				.getName(CCUtils.generateStringWithSpace(CCUtils.generateAlphaNumericString(100)));
		String agnoPracticeName = CCUtils
				.getName(CCUtils.generateStringWithSpace(CCUtils.generateAlphaNumericString(100)));
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.practice.active"),
				agnoPracticeDisplayName, agnoPracticeName, testData.getProperty("api.practice.orgtype"), clientId,
				"CLIENT", " ", "", solutionListTwo);
		int practiceId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, agnoPracticeName);

		logStep("Create an active NGE practice node without solutions below enterprise");
		String ngePracticeDisplayName = CCUtils
				.getName(CCUtils.generateStringWithSpace(CCUtils.generateAlphaNumericString(100)));
		String ngePracticeName = CCUtils
				.getName(CCUtils.generateStringWithSpace(CCUtils.generateAlphaNumericString(100)));
		createOrgNodeApiResponse = orgInfo.createOrgNode(testData.getProperty("api.practice.active"),
				ngePracticeDisplayName, ngePracticeName, testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), " ", "", solutionListTwo);
		int ngePracticeId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse, ngePracticeName);

		logStep("Fetched newly created system details using system id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(systemId);
		logStep("Verify practice count and solutions are not displayed on system details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, systemId, systemName,
				testData.getProperty("api.system.orgtype"), testData.getProperty("api.client.type"), systemDisplayName,
				"", "false", testData.getProperty("api.client.testclient"), "", "", 0, false, solutionListTwo);

		logStep("Fetched newly created enterprise details using enterprise id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(enterpriseId);
		logStep("Verify practice count and solutions are not displayed on enterprise details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, enterpriseId, enterpriseName,
				testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.client.type"),
				enterpriseDisplayName, "", "false", testData.getProperty("api.client.testclient"), "", "", 0, false,
				solutionListTwo);

		logStep("Fetched newly created agnostic practice details using practice id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(practiceId);
		solutionListTwo = new ArrayList<Map<String, Object>>();
		logStep("Verify practice count is not displayed and solutions list is displayed with activeCount=0 on agnostic practice details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, practiceId, agnoPracticeName,
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"),
				agnoPracticeDisplayName, "", "false", testData.getProperty("api.client.testclient"), "", "", 0, false,
				solutionListTwo);

		logStep("Fetched newly created nge practice details using nge practice id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(ngePracticeId);
		logStep("Verify practice count is not displayed and solutions list is displayed with activeCount=0 on nge practice details page");
		validation.verifyGetOrgNodeDetailsApiResponseBody(fetchOrgNodeApiResponse, ngePracticeId, ngePracticeName,
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.type"),
				ngePracticeDisplayName, "", "false", testData.getProperty("api.client.testclient"), "", "", 0, false,
				solutionListTwo);

		logStep("Fetched client details using client id");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(Integer.toString(clientId));
		logStep("Verify practice count and solutions are not displayed on client details page");
		validation.verifySearchClientIdResponseBody(fetchClientDetailsApiResponse, Integer.toString(clientId),
				clientName, testData.getProperty("api.client.type"), clientSapNumber, "false",
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.timezoneid"),
				testData.getProperty("api.client.displayname"), 0, null);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Enter invalid url and verify error response body")
	public void testErrorResponseForInvalidUrl(String[] getOrgParentTypesAndParentId) throws Exception {
		logStep("Enter invalid url");
		createOrgNodeApiResponse = orgInfo.createOrgNodeUsingInvalidUrl(testData.getProperty("api.client.active"),
				testData.getProperty("api.system.displayname"), "node_name", getOrgParentTypesAndParentId[0],
				Integer.parseInt(getOrgParentTypesAndParentId[2]), getOrgParentTypesAndParentId[1],
				testData.getProperty("api.system.sapnumber"), testData.getProperty("api.system.timezoneid"),
				solutionList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(createOrgNodeApiResponse);
	}

}
