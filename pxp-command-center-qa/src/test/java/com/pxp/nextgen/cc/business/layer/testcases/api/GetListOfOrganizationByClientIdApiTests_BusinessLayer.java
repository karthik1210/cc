// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.OrgDetails;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetListOfOrganizationByClientIdApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	Response createClientApiResponse, getOrganizationListApiResponse, createOrgNodeApiResponse;
	int clientId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
	}

	@DataProvider(name = "orgType")
	public Object[][] getOrgType() throws Exception {
		return new Object[][] { { testData.getProperty("api.system.orgtype") },
				{ testData.getProperty("api.enterprise.orgtype") }, { testData.getProperty("api.practice.orgtype") } };
	}

	public int createClient(String status) throws Exception {
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(status, clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"));
		return createClientApiResponse.jsonPath().getInt("clientId");
	}

	public int createOrgNode(String status, String name, int parentId, String orgType, String parentType)
			throws Exception {
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL(name, orgType,
				testData.getProperty("api.system.displayname"), "", status,
				testData.getProperty("api.system.timezoneid"), parentId, parentType,
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));

		return createOrgNodeApiResponse.jsonPath().getInt("orgId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "orgType", description = "Verify 204 status code in response when user enter client id which does not contains subsequent child nodes")
	public void testResponseCodeForSingleClientNode(String orgType) throws Exception {
		logStep("Create new inactive client node");
		int clientIdOne = createClient("false");
		logStep("Get list of organization by client id");
		getOrganizationListApiResponse = clientInfo.getOrganizationListByClientId(clientIdOne, orgType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrganizationListApiResponse);
		CCUtils.assertEquals(getOrganizationListApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 0, description = "Create a new client with subsequent child nodes and verify user able get list of organization by client id")
	public void testGetOrganizationListByClientIdFunctionality() throws Exception {
		logStep("Create new active client");
		clientId = createClient(testData.getProperty("api.client.active"));
		logStep("Create active and inactive system below client");
		int systemIdOne = createOrgNode(testData.getProperty("api.system.active"), "System_name_1", clientId,
				testData.getProperty("api.system.orgtype"), testData.getProperty("api.parenttype"));
		int systemIdTwo = createOrgNode("false", "System_name_2", clientId, testData.getProperty("api.system.orgtype"),
				testData.getProperty("api.parenttype"));
		List<OrgDetails> systemList = Arrays.asList(new OrgDetails(systemIdOne, "System_name_1"),
				new OrgDetails(systemIdTwo, "System_name_2"));
		logStep("Get list of organization by client id and org type system");
		getOrganizationListApiResponse = clientInfo.getOrganizationListByClientId(clientId,
				testData.getProperty("api.system.orgtype"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrganizationListApiResponse);
		CCUtils.assertEquals(getOrganizationListApiResponse.getStatusCode(), 200);
		validation.verifyGetOrganizationListApiResponse(getOrganizationListApiResponse, systemList);
		logStep("Create active and inactive enterprise below system");
		int enterpriseIdOne = createOrgNode(testData.getProperty("api.enterprise.active"), "Enterprise_name_1",
				systemIdOne, testData.getProperty("api.enterprise.orgtype"),
				testData.getProperty("api.enterprise.parenttype"));
		int enterpriseIdTwo = createOrgNode("false", "Enterprise_name_2", systemIdOne,
				testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.enterprise.parenttype"));
		List<OrgDetails> enterpriseList = Arrays.asList(new OrgDetails(enterpriseIdOne, "Enterprise_name_1"),
				new OrgDetails(enterpriseIdTwo, "Enterprise_name_2"));
		logStep("Get list of organization by client id and org type enterprise");
		getOrganizationListApiResponse = clientInfo.getOrganizationListByClientId(clientId,
				testData.getProperty("api.enterprise.orgtype"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrganizationListApiResponse);
		CCUtils.assertEquals(getOrganizationListApiResponse.getStatusCode(), 200);
		validation.verifyGetOrganizationListApiResponse(getOrganizationListApiResponse, enterpriseList);
		logStep("Create active and inactive practices below enterprise and client node");
		int ngePracticeOne = createOrgNode(testData.getProperty("api.practice.active"), "Practice_name_1",
				enterpriseIdOne, testData.getProperty("api.practice.orgtype"),
				testData.getProperty("api.practice.parenttype"));
		int ngePracticeTwo = createOrgNode("false", "Practice_name_2", enterpriseIdTwo,
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.parenttype"));
		int agnosticPracticeOne = createOrgNode(testData.getProperty("api.practice.active"), "agnostic_practice_name_1",
				systemIdOne, testData.getProperty("api.practice.orgtype"), testData.getProperty("api.system.orgtype"));
		int agnosticPracticeTwo = createOrgNode("false", "agnostic_practice_name_2", systemIdOne,
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.system.orgtype"));
		logStep("Get list of organization by client id and org type practice");
		List<OrgDetails> practiceList = Arrays.asList(new OrgDetails(ngePracticeOne, "Practice_name_1"),
				new OrgDetails(ngePracticeTwo, "Practice_name_2"),
				new OrgDetails(agnosticPracticeOne, "agnostic_practice_name_1"),
				new OrgDetails(agnosticPracticeTwo, "agnostic_practice_name_2"));
		getOrganizationListApiResponse = clientInfo.getOrganizationListByClientId(clientId,
				testData.getProperty("api.practice.orgtype"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrganizationListApiResponse);
		CCUtils.assertEquals(getOrganizationListApiResponse.getStatusCode(), 200);
		validation.verifyGetOrganizationListApiResponse(getOrganizationListApiResponse, practiceList);
		logStep("Enter blank space in orgType field and valid clientId and Verify actice nge/agnostic practice node list should be displayed in response body");
		List<OrgDetails> activePracticeList = Arrays.asList(new OrgDetails(ngePracticeOne, "Practice_name_1"),
				new OrgDetails(agnosticPracticeOne, "agnostic_practice_name_1"));
		getOrganizationListApiResponse = clientInfo.getOrganizationListByClientId(clientId, " ");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrganizationListApiResponse);
		CCUtils.assertEquals(getOrganizationListApiResponse.getStatusCode(), 200);
		validation.verifyGetOrganizationListApiResponse(getOrganizationListApiResponse, activePracticeList);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "orgType", description = "Enter invalid client id in clientId field and verify 204 status code")
	public void testErrorResponseForInvalidClientId(String orgType) throws Exception {
		logStep("Get list of organization using invalid client id and valid org type");
		getOrganizationListApiResponse = clientInfo.getOrganizationListByClientId(
				Integer.parseInt(testData.getProperty("api.nonexisting.client.id")), orgType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrganizationListApiResponse);
		CCUtils.assertEquals(getOrganizationListApiResponse.getStatusCode(), 204);
	}

	@DataProvider(name = "invalidOrgType")
	public Object[][] getInvalidOrgType() throws Exception {
		return new Object[][] { { " " + testData.getProperty("api.system.orgtype") },
				{ " " + testData.getProperty("api.enterprise.orgtype") },
				{ "   " + testData.getProperty("api.practice.orgtype") },
				{ "abc" + testData.getProperty("api.parenttype") }, { "null" }, { "abcdfefd" } };
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidOrgType", description = "Enter invalid org type in type field and verify error response")
	public void testErrorResponseForInvalidOrgType(String invalidOrgType) throws Exception {
		logStep("Get list of organization using valid client id and invalid org type");
		getOrganizationListApiResponse = clientInfo.getOrganizationListByClientId(clientId, invalidOrgType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrganizationListApiResponse);
		CCUtils.assertEquals(getOrganizationListApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForOrgTypeField(getOrganizationListApiResponse, invalidOrgType);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid client id ,type in clientId and type field and verify error response")
	public void testErrorResponseForInvalidOrgTypeAndClientId() throws Exception {
		getOrganizationListApiResponse = clientInfo.getOrganizationListByClientId(clientId,
				"xyz12" + testData.getProperty("api.parenttype"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrganizationListApiResponse);
		CCUtils.assertEquals(getOrganizationListApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForOrgTypeField(getOrganizationListApiResponse,
				"xyz12" + testData.getProperty("api.parenttype"));

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error response for invalid url")
	public void testErrorResponseForInvalidUrl() throws Exception {
		logStep("Enter invalid url and validate error status code");
		getOrganizationListApiResponse = clientInfo.getOrganizationListByClientIdUsingInvalidUrl(clientId,
				testData.getProperty("api.system.orgtype"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrganizationListApiResponse);
		CCUtils.assertEquals(getOrganizationListApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getOrganizationListApiResponse);
	}

}
