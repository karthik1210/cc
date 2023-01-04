// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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
	String clientName, clientSapNumber;
	int clientId, systemId, enterpriseId, ngePracticeId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
	}

	public int createClient(String status, String testClient) throws Exception {
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient(status, clientName, clientSapNumber, testClient,
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 0, description = "Create active org nodes below active client")
	public void testCreateActiveOrgNodesBelowActiveClient() throws Exception {
		logStep("Create an active client");
		clientId = createClient("true", "false");
		logStep("Create an active system node below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", testData.getProperty("api.system.active"), "", clientId,
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		systemId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse,
				testData.getProperty("api.system.name"));
		logStep("Fetched newly created system details using system id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(systemId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeApiResponse, systemId,
				testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"),
				testData.getProperty("api.system.name"), testData.getProperty("api.system.active"),
				ccUser.getTimezoneId(), "");
		logStep("Create an active enterprise node below system");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemId,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		enterpriseId = createOrgNodeApiResponse.jsonPath().get("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse,
				testData.getProperty("api.enterprise.name"));
		logStep("Fetched newly created enterprise details using enterprise id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(enterpriseId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeApiResponse, enterpriseId,
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"),
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"),
				testData.getProperty("api.system.timezonedisplayname"));
		logStep("Create an active NGE practice node without solutions below enterprise");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		ngePracticeId = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse,
				testData.getProperty("api.practice.name"));
		logStep("Fetched newly created nge practice details using nge practice id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(ngePracticeId);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeApiResponse, ngePracticeId,
				testData.getProperty("api.practice.name"), testData.getProperty("api.practice.orgtype"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.timezoneid"),
				testData.getProperty("api.practice.timezonedisplayname"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create inactive org nodes below active client")
	public void testCreateInactiveOrgNodesBelowActiveClient() throws Exception {
		logStep("Create an active client");
		int clientIdOne = createClient("true", "true");
		logStep("Create an inactive system node below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", "false", "", clientIdOne,
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemIdOne = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse,
				testData.getProperty("api.system.name"));
		logStep("Fetched newly created system details using system id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(systemIdOne);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeApiResponse, systemIdOne,
				testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"),
				testData.getProperty("api.system.name"), "false", ccUser.getTimezoneId(), "");
		logStep("Create an inactive enterprise node below system");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", "false",
				testData.getProperty("api.enterprise.timezoneid"), systemIdOne,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseIdOne = createOrgNodeApiResponse.jsonPath().get("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse,
				testData.getProperty("api.enterprise.name"));
		logStep("Fetched newly created enterprise details using enterprise id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(enterpriseIdOne);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeApiResponse, enterpriseIdOne,
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"),
				testData.getProperty("api.enterprise.name"), "false", testData.getProperty("api.enterprise.timezoneid"),
				testData.getProperty("api.system.timezonedisplayname"));
		logStep("Create an inactive NGE practice node without solutions below enterprise");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"), "false",
				testData.getProperty("api.practice.timezoneid"), enterpriseIdOne,
				testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int ngePracticeIdOne = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse,
				testData.getProperty("api.practice.name"));
		logStep("Fetched newly created nge practice details using nge practice id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(ngePracticeIdOne);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeApiResponse, ngePracticeIdOne,
				testData.getProperty("api.practice.name"), testData.getProperty("api.practice.orgtype"),
				testData.getProperty("api.practice.displayname"), "false",
				testData.getProperty("api.practice.timezoneid"),
				testData.getProperty("api.practice.timezonedisplayname"));
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
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL("", getOrgAndPatentTypes[0], "", null, "", 0,
				getOrgAndPatentTypes[1], 0, 0);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageResponseBodyForMandatoryFields(createOrgNodeApiResponse);
	}

	@DataProvider(name = "getOrgDetails")
	public Object[] getOrgParentTypesAndParentId() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("api.system.orgtype"), testData.getProperty("api.parenttype"),
						String.valueOf(clientId) },
				{ testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.enterprise.parenttype"),
						String.valueOf(systemId) },
				{ testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.parenttype"),
						String.valueOf(enterpriseId) } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Enter more than 100 characters in org name, solution display name field and verify error response body")
	public void testMaximumCharactersErrorMessage(String[] getOrgParentTypesAndParentId) throws Exception {
		logStep("Enter more than 100 characters in org name, solution display name field");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("invalid.name"),
				getOrgParentTypesAndParentId[0], testData.getProperty("invalid.solutionDisplayName"),
				testData.getProperty("api.client.active"), "", Integer.parseInt(getOrgParentTypesAndParentId[2]),
				getOrgParentTypesAndParentId[1], Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyMaximumCharacterErrorMessageInResponseBodyForNameAndSolutionsDisplayField(
				createOrgNodeApiResponse);
	}

	@DataProvider(name = "getOrgDetailsInfo")
	public Object[] getOrgParentTypesAndParentIdInfo() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.enterprise.parenttype"),
						String.valueOf(systemId) },
				{ testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.parenttype"),
						String.valueOf(enterpriseId) } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetailsInfo", description = "Enter invalid timezone id in timezone field and verify error response body")
	public void testErrorResponseForInvalidTimezoneId(String[] getOrgParentTypesAndParentId) throws Exception {
		logStep("Enter invalid timezone id in timezone field");
		String invalidTimezoneId = testData.getProperty("api.system.timezoneid").concat("abcdef@12");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL("node_name", getOrgParentTypesAndParentId[0],
				testData.getProperty("api.system.displayname"), testData.getProperty("api.client.active"),
				invalidTimezoneId, Integer.parseInt(getOrgParentTypesAndParentId[2]), getOrgParentTypesAndParentId[1],
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForOrgTimezoneIdField(createOrgNodeApiResponse, invalidTimezoneId);
	}

	@DataProvider(name = "getParentId")
	public Object[][] getParentIdDetails() throws Exception {
		return new Object[][] { { String.valueOf(clientId) }, { String.valueOf(systemId) },
				{ String.valueOf(enterpriseId) } };
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getParentId", description = "Enter blank space in org name ,org type and parent type fields and verify error response body")
	public void testErrorResponseForBlankFields(String getParentIdDetails) throws Exception {
		logStep("Enter blank space in org name ,org type and parent type fields");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL(" ", " ", testData.getProperty("api.system.displayname"),
				testData.getProperty("api.client.active"), testData.getProperty("api.system.timezoneid"),
				Integer.parseInt(getParentIdDetails), " ", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForBlankFields(createOrgNodeApiResponse);
	}

	@DataProvider(name = "getInvalidOrgType")
	public Object[] getInvalidOrgTypeDetails() {
		Object[][] obj = new Object[][] {
				{ "SYS TEM", testData.getProperty("api.parenttype"), String.valueOf(clientId) },
				{ "ENTERPRISEE", testData.getProperty("api.enterprise.parenttype"), String.valueOf(systemId) },
				{ "PRACTICEE", testData.getProperty("api.practice.parenttype"), String.valueOf(enterpriseId) } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getInvalidOrgType", description = "Enter invalid org type in orgType field and verify error response body")
	public void testErrorResponseForInvalidOrgType(String[] getInvalidOrgTypeDetails) throws Exception {
		logStep("Enter invalid org type in orgType field");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL("node_name", getInvalidOrgTypeDetails[0],
				testData.getProperty("api.system.displayname"), testData.getProperty("api.client.active"),
				testData.getProperty("api.system.timezoneid"), Integer.parseInt(getInvalidOrgTypeDetails[2]),
				getInvalidOrgTypeDetails[1], Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForOrgTypeField(createOrgNodeApiResponse, getInvalidOrgTypeDetails[0]);
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
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL("node_name", orgNodeDetail[0],
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.client.active"),
				testData.getProperty("api.practice.timezoneid"), Integer.parseInt(orgNodeDetail[2]), orgNodeDetail[1],
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(createOrgNodeApiResponse,
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
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL("node_name", orgNodeData[0],
				testData.getProperty("api.enterprise.displayname"), testData.getProperty("api.client.active"),
				testData.getProperty("api.enterprise.timezoneid"), Integer.parseInt(orgNodeData[2]), orgNodeData[1],
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(createOrgNodeApiResponse,
				Integer.parseInt(orgNodeData[2]));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create active org nodes below inactive client")
	public void testCreateActiveOrgNodesBelowInactiveClient() throws Exception {
		logStep("Create an inactive client");
		int clientIdTwo = createClient("false", "false");
		logStep("Create an active system node below client");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", testData.getProperty("api.system.active"), "",
				clientIdTwo, testData.getProperty("api.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemIdTwo = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse,
				testData.getProperty("api.system.name"));
		logStep("Fetched newly created system details using system id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(systemIdTwo);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeApiResponse, systemIdTwo,
				testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"),
				testData.getProperty("api.system.name"), "false", ccUser.getTimezoneId(), "");
		logStep("Create an active enterprise node below system");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemIdTwo,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseIdTwo = createOrgNodeApiResponse.jsonPath().get("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse,
				testData.getProperty("api.enterprise.name"));
		logStep("Fetched newly created enterprise details using enterprise id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(enterpriseIdTwo);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeApiResponse, enterpriseIdTwo,
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"),
				testData.getProperty("api.enterprise.name"), "false", testData.getProperty("api.enterprise.timezoneid"),
				testData.getProperty("api.system.timezonedisplayname"));
		logStep("Create an active NGE practice node without solutions below enterprise");
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), "", testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.timezoneid"), enterpriseIdTwo,
				testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int ngePracticeIdTwo = createOrgNodeApiResponse.jsonPath().getInt("orgId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyCreateOrgNodeApiResponseBody(createOrgNodeApiResponse,
				testData.getProperty("api.practice.name"));
		logStep("Fetched newly created nge practice details using nge practice id");
		fetchOrgNodeApiResponse = orgInfo.getOrgId(ngePracticeIdTwo);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgNodeApiResponse, ngePracticeIdTwo,
				testData.getProperty("api.practice.name"), testData.getProperty("api.practice.orgtype"),
				testData.getProperty("api.practice.name"), "false", testData.getProperty("api.practice.timezoneid"),
				testData.getProperty("api.practice.timezonedisplayname"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Enter invalid url and verify error response body")
	public void testErrorResponseForInvalidUrl(String[] getOrgParentTypesAndParentId) throws Exception {
		logStep("Enter invalid url");
		createOrgNodeApiResponse = orgInfo.createOrgNodeUsingInvalidUrl_BL("node_name", getOrgParentTypesAndParentId[0],
				testData.getProperty("api.system.displayname"), testData.getProperty("api.client.active"),
				testData.getProperty("api.system.timezoneid"), Integer.parseInt(getOrgParentTypesAndParentId[2]),
				getOrgParentTypesAndParentId[1], Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(createOrgNodeApiResponse);
	}

}
