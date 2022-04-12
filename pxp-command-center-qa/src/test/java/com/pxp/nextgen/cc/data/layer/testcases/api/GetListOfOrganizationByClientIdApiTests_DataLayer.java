// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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

public class GetListOfOrganizationByClientIdApiTests_DataLayer extends BaseRest {

	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	Response createClientApiResponse, getOrganizationListApiResponse, createOrgNodeApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
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
		createClientApiResponse = clientInfo.createNewClient_DataLayer(status, clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		return createClientApiResponse.jsonPath().getInt("clientId");
	}

	public int createOrgNode(String status, String name, int parentId, String orgType, String parentType)
			throws Exception {
		createOrgNodeApiResponse = orgInfo.createOrg_DataLayer(status, testData.getProperty("api.system.displayname"),
				name, orgType, parentId, parentType, testData.getProperty("api.system.sapnumber"),
				testData.getProperty("api.system.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		return createOrgNodeApiResponse.jsonPath().getInt("orgId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "orgType", description = "Verify 204 status code in response when user enter client id which does not contains subsequent child nodes")
	public void testResponseCodeForSingleClientNode(String orgType) throws Exception {
		logStep("Create new inactive client node");
		int clientId = createClient("false");
		logStep("Get list of organization by client id");
		getOrganizationListApiResponse = orgInfo.getOrganizationListByClientId_DataLayer(clientId, orgType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrganizationListApiResponse);
		CCUtils.assertEquals(getOrganizationListApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new client with subsequent child nodes and verify user able get list of organization by client id")
	public void testGetOrganizationListByClientIdFunctionality() throws Exception {
		logStep("Create new active client");
		int clientId = createClient(testData.getProperty("api.client.active"));
		logStep("Create active and inactive system below client");
		int systemIdOne = createOrgNode(testData.getProperty("api.system.active"), "System_name_1", clientId,
				testData.getProperty("api.system.orgtype"), testData.getProperty("api.parenttype"));
		int systemIdTwo = createOrgNode("false", "System_name_2", clientId, testData.getProperty("api.system.orgtype"),
				testData.getProperty("api.parenttype"));
		List<OrgDetails> systemList = Arrays.asList(new OrgDetails(systemIdOne, "System_name_1"),
				new OrgDetails(systemIdTwo, "System_name_2"));
		logStep("Get list of organization by client id and org type system");
		getOrganizationListApiResponse = orgInfo.getOrganizationListByClientId_DataLayer(clientId,
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
		getOrganizationListApiResponse = orgInfo.getOrganizationListByClientId_DataLayer(clientId,
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
				clientId, testData.getProperty("api.practice.orgtype"), testData.getProperty("api.parenttype"));
		int agnosticPracticeTwo = createOrgNode("false", "agnostic_practice_name_2", clientId,
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.parenttype"));
		logStep("Get list of organization by client id and org type practice");
		List<OrgDetails> practiceList = Arrays.asList(new OrgDetails(ngePracticeOne, "Practice_name_1"),
				new OrgDetails(ngePracticeTwo, "Practice_name_2"),
				new OrgDetails(agnosticPracticeOne, "agnostic_practice_name_1"),
				new OrgDetails(agnosticPracticeTwo, "agnostic_practice_name_2"));
		getOrganizationListApiResponse = orgInfo.getOrganizationListByClientId_DataLayer(clientId,
				testData.getProperty("api.practice.orgtype"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrganizationListApiResponse);
		CCUtils.assertEquals(getOrganizationListApiResponse.getStatusCode(), 200);
		validation.verifyGetOrganizationListApiResponse(getOrganizationListApiResponse, practiceList);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "orgType", description = "Enter invalid client id in clientId field and verify 204 status code")
	public void testErrorResponseForInvalidClientId(String orgType) throws Exception {
		logStep("Get list of organization using invalid client id and valid org type");
		getOrganizationListApiResponse = orgInfo.getOrganizationListByClientId_DataLayer(
				Integer.parseInt(testData.getProperty("api.nonexisting.client.id")), orgType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrganizationListApiResponse);
		CCUtils.assertEquals(getOrganizationListApiResponse.getStatusCode(), 204);
	}

	@DataProvider(name = "invalidOrgType")
	public Object[][] getInvalidOrgType() throws Exception {
		return new Object[][] { { " " + testData.getProperty("api.system.orgtype") },
				{ " " + testData.getProperty("api.enterprise.orgtype") },
				{ "   " + testData.getProperty("api.practice.orgtype") }, { testData.getProperty("api.parenttype") },
				{ "null" }, { "abcdfefd" } };
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidOrgType", description = "Enter invalid org type in type field and verify error response")
	public void testErrorResponseForInvalidOrgType(String invalidOrgType) throws Exception {
		logStep("Get list of organization using valid client id and invalid org type");
		getOrganizationListApiResponse = orgInfo.getOrganizationListByClientId_DataLayer(
				Integer.parseInt(testData.getProperty("api.existing.client.id")), invalidOrgType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrganizationListApiResponse);
		CCUtils.assertEquals(getOrganizationListApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForOrgTypeField(getOrganizationListApiResponse, invalidOrgType);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid client id ,type in clientId and type field and verify error response")
	public void testErrorResponseForInvalidOrgTypeAndClientId() throws Exception {
		getOrganizationListApiResponse = orgInfo.getOrganizationListByClientId_DataLayer(
				Integer.parseInt(testData.getProperty("api.nonexisting.client.id")),
				testData.getProperty("api.parenttype"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrganizationListApiResponse);
		CCUtils.assertEquals(getOrganizationListApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForOrgTypeField(getOrganizationListApiResponse,
				testData.getProperty("api.parenttype"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error response for invalid url")
	public void testErrorResponseForInvalidUrl() throws Exception {
		logStep("Enter invalid url and validate error status code");
		getOrganizationListApiResponse = orgInfo.getOrganizationListByClientIdUsingInvalidUrl_DataLayer(
				Integer.parseInt(testData.getProperty("api.existing.client.id")),
				testData.getProperty("api.system.orgtype"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrganizationListApiResponse);
		CCUtils.assertEquals(getOrganizationListApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getOrganizationListApiResponse);
	}
}
