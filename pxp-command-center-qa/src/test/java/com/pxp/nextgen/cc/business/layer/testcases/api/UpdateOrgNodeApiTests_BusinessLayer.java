// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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

public class UpdateOrgNodeApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	String clientName, clientSapNumber;
	Response fetchClientDetailsApiResponse, updateOrgNodeApiResponse, createClientApiResponse, createOrgNodeApiResponse,
			fetchOrgNodeDetails;
	int clientId, systemId, enterpriseId, practiceId, agnosticPracticeId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
		logStep("Create active client node");
		clientId = createClient(testData.getProperty("api.client.active"));
		logStep("Create active system, enterprise ,nge practice and inactive agnostic practice nodes below active client");
		systemId = createOrgNode(testData.getProperty("api.system.active"), "", testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"), "", "",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		enterpriseId = createOrgNode(testData.getProperty("api.enterprise.active"), "",
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), "", "US/Eastern",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		practiceId = createOrgNode(testData.getProperty("api.practice.active"), "",
				testData.getProperty("api.practice.name"), testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), "", "US/Central",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		agnosticPracticeId = createOrgNode("false", "", "ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), systemId, testData.getProperty("api.system.orgtype"), "",
				"US/Hawaii", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
	}

	public int createClient(String status) throws Exception {
		clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(status, clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"));
		return createClientApiResponse.jsonPath().getInt("clientId");
	}

	public int createOrgNode(String status, String displayName, String name, String orgType, int parentId,
			String parentType, String sapNumber, String timezoneId, int ehridentifier, int pmIdentifier)
			throws Exception {
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL(name, orgType, displayName, sapNumber, status, timezoneId,
				parentId, parentType, ehridentifier, pmIdentifier);
		return createOrgNodeApiResponse.jsonPath().getInt("orgId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify active org node status from active to inactive and verify subsequent child nodes also becomes inactive and validate response body. Activate org node from inactive to active when parent node is active and validate response body")
	public void testOrgNodeUpdateStatusFunctionality() throws NullPointerException, Exception {
		logStep("Modify system node status from active to inactive");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", "", "false",
				testData.getProperty("api.client.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")), systemId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, systemId,
				testData.getProperty("api.system.name"));
		logStep("verify after updating system node status from active to inactive, subsequent child nodes(Enterprise/NGE practice/Agnostic practice) also become inactive");
		fetchOrgNodeDetails = orgInfo.getOrgId(systemId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, systemId, testData.getProperty("api.system.name"), "false");
		fetchOrgNodeDetails = orgInfo.getOrgId(enterpriseId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, enterpriseId, testData.getProperty("api.enterprise.name"),
				"false");
		fetchOrgNodeDetails = orgInfo.getOrgId(practiceId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, practiceId, testData.getProperty("api.practice.name"),
				"false");
		fetchOrgNodeDetails = orgInfo.getOrgId(agnosticPracticeId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, agnosticPracticeId, "ag_practice_name_1", "false");

		logStep("Modify system node status from inactive to active when parent node client is active");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", "", "true",
				testData.getProperty("api.client.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")), systemId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, systemId,
				testData.getProperty("api.system.name"));
		logStep("verify only system node status is changed from inactive to active and subsequent child nodes(Enterprise/NGE practice/Agnostic Practice) remains inactive");
		fetchOrgNodeDetails = orgInfo.getOrgId(systemId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, systemId, testData.getProperty("api.system.name"), "true");
		fetchOrgNodeDetails = orgInfo.getOrgId(enterpriseId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, enterpriseId, testData.getProperty("api.enterprise.name"),
				"false");
		fetchOrgNodeDetails = orgInfo.getOrgId(practiceId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, practiceId, testData.getProperty("api.practice.name"),
				"false");
		fetchOrgNodeDetails = orgInfo.getOrgId(agnosticPracticeId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, agnosticPracticeId, "ag_practice_name_1", "false");

		logStep("Modify enterprise node status from inactive to active when parent node system is active");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", "", "true", "US/Eastern",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")), enterpriseId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, enterpriseId,
				testData.getProperty("api.enterprise.name"));
		logStep("verify only enterprise node status is changed from inactive to active and subsequent child nodes(NGE practice) remains inactive");
		fetchOrgNodeDetails = orgInfo.getOrgId(enterpriseId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, enterpriseId, testData.getProperty("api.enterprise.name"),
				"true");
		fetchOrgNodeDetails = orgInfo.getOrgId(practiceId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, practiceId, testData.getProperty("api.practice.name"),
				"false");

		logStep("Modify nge practice node status from inactive to active when parent node enterprise is active");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), "", "", "true", "US/Central",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")), practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, practiceId,
				testData.getProperty("api.practice.name"));
		logStep("verify NGE practice node status is changed from inactive to active");
		fetchOrgNodeDetails = orgInfo.getOrgId(practiceId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, practiceId, testData.getProperty("api.practice.name"),
				"true");

		logStep("Modify enterprise node status from active to inactive");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", "", "false", "US/Eastern",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")), enterpriseId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, enterpriseId,
				testData.getProperty("api.enterprise.name"));
		logStep("verify after updating enterprise node status from active inactive, subsequent child node(NGE practice) also become inactive");
		fetchOrgNodeDetails = orgInfo.getOrgId(enterpriseId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, enterpriseId, testData.getProperty("api.enterprise.name"),
				"false");
		fetchOrgNodeDetails = orgInfo.getOrgId(practiceId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, practiceId, testData.getProperty("api.practice.name"),
				"false");

		logStep("Modify agnostic practice node status from inactive to active when parent node system is active");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_BL("ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), "", "", "true", "US/Hawaii",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")), agnosticPracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 200);
		validation.verifyUpdateOrgApiResponseBody(updateOrgNodeApiResponse, agnosticPracticeId, "ag_practice_name_1");
		logStep("verify agnostic practice node status is changed from inactive to active");
		fetchOrgNodeDetails = orgInfo.getOrgId(agnosticPracticeId);
		validation.verifyOrgNodeStatus(fetchOrgNodeDetails, agnosticPracticeId, "ag_practice_name_1", "true");
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
		System.out.println("dataprovider" + getOrgTypeAndOrgId);
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_BL("", getOrgTypeAndOrgId[0], "", "", null, "",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")), Integer.parseInt(getOrgTypeAndOrgId[1]));

		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageResponseBodyForNullValueInMandatoryField(updateOrgNodeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Enter maximum characters in sap id,splution display name and name field and validate error response body")
	public void testMaximumCharactersErrorMessage(String[] getOrgTypeAndOrgId) throws IOException {
		logStep("Enter maximum characters in sap id,splution display name and name field and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_BL(testData.getProperty("invalid.name"), getOrgTypeAndOrgId[0],
				testData.getProperty("invalid.solutionDisplayName"), "", "true", "US/Central",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")), Integer.parseInt(getOrgTypeAndOrgId[1]));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyMaximumCharacterErrorMessageInResponseBody(updateOrgNodeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Enter invalid timezone id in timezone field and validate error response body")
	public void testErrorMessageForInvalidTimezoneId(String[] getOrgTypeAndOrgId) throws IOException {
		logStep("Enter invalid timezone id in timezone field and validate error response body");
		String invalidTimezoneId = testData.getProperty("api.system.timezoneid").concat("abcdef@12");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_BL("Org_Name", getOrgTypeAndOrgId[0], "Dispaly_Name_1", "",
				"true", invalidTimezoneId, Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")), Integer.parseInt(getOrgTypeAndOrgId[1]));
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
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_BL("Org_Name", getOrgTypeAndInvalidOrgId[0], "Dispaly_Name_1",
				"", "true", "US/Eastern", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")),
				Integer.parseInt(getOrgTypeAndInvalidOrgId[1]));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(updateOrgNodeApiResponse,
				Integer.parseInt(getOrgTypeAndInvalidOrgId[1]));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", description = "Verify error message for invalid url")
	public void testErrorMessageForInvalidUrl(String[] getOrgTypeAndOrgId) throws IOException {
		updateOrgNodeApiResponse = orgInfo.updateOrgNodeUsingInvalidUrl_BL("org_name", getOrgTypeAndOrgId[0], "org@001",
				"", "true", "US/Central", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")), Integer.parseInt(getOrgTypeAndOrgId[1]));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateOrgNodeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Modify org node status from inactive to active when parent node is inactive and validate error response body")
	public void testActivateOrgNodeWhenParentIsInactive() throws Exception, NullPointerException {
		logStep("Create inactive client node");
		int clientId = createClient("false");
		logStep("Create inactive system, enterprise ,nge practice and agnostic practice nodes below inactive client");
		int systemIdOne = createOrgNode("false", "", "system_name_1", testData.getProperty("api.system.orgtype"),
				clientId, testData.getProperty("api.parenttype"), "", "",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseIdOne = createOrgNode("false", "", "enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), systemIdOne,
				testData.getProperty("api.enterprise.parenttype"), "",
				testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int practiceIdOne = createOrgNode("false", "", "practice_name_1", testData.getProperty("api.practice.orgtype"),
				enterpriseIdOne, testData.getProperty("api.practice.parenttype"), "",
				testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int agnosticPracticeIdOne = createOrgNode("false", "", "ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), systemIdOne, testData.getProperty("api.system.orgtype"),
				"", testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));

		logStep("Modify system node status from inactive to active when client node is inactive and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_BL("system_name_1", testData.getProperty("api.system.orgtype"),
				"", "", "true", "US/Samoa", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")), systemIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyInactiveClientErrorResponsforSystem(updateOrgNodeApiResponse);

		logStep("Modify enterprise node status from inactive to active when system node is inactive and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_BL("enterprise_name_1",
				testData.getProperty("api.enterprise.orgtype"), "", "", "true", "US/Eastern",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")), enterpriseIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyInactiveSystemErrorResponForEnterprise(updateOrgNodeApiResponse);

		logStep("Modify agnostic practice node status from inactive to active when system node is inactive and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_BL("ag_practice_name_1",
				testData.getProperty("api.practice.orgtype"), "", "", "true", "US/Hawaii",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")), agnosticPracticeIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyInactiveSystemErrorResponsForPractice(updateOrgNodeApiResponse);

		logStep("Modify nge practice node status from inactive to active when enterprise node is inactive and validate error response body");
		updateOrgNodeApiResponse = orgInfo.updateOrgNode_BL("practice_name_1",
				testData.getProperty("api.practice.orgtype"), "", "", "true", "US/Central",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")), practiceIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateOrgNodeApiResponse);
		CCUtils.assertEquals(updateOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyInactivePracticeErrorResponsWhileUpdataingActivePractice(updateOrgNodeApiResponse);
	}

}
