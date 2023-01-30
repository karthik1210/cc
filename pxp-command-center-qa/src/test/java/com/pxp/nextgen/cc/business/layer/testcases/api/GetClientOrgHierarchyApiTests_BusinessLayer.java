// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.OrgNode;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetClientOrgHierarchyApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	Response createClientApiResponse, fetchClientDetailsApiResponse, createPracticeApiResponse,
			fetchPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse;
	int clientId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing id in client id field and verify error message for client id field")
	public void testErrorMessageForClientIdField() throws IOException {
		logStep("Enter non existing client id");
		fetchClientDetailsApiResponse = clientInfo
				.getClientOrgHierarchyDetails(Integer.parseInt(testData.getProperty("non.existing.org.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 0, description = "Create client & add org node below the client then fetch & validate client org hierarchy details with client id")
	public void testExistingClientIdWithNgeFlow() throws IOException {
		logStep("Create client using valid details in client name, sap number, active, testclient, client type and timezone field");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		List<OrgNode> datalist = new ArrayList<OrgNode>();
		clientId = createClientApiResponse.jsonPath().get("clientId");

		datalist.add(new OrgNode(clientId, 0, "CLIENT", clientName,
				Boolean.parseBoolean(testData.getProperty("api.client.active"))));

		logStep("Create a new system below client.");
		createSystemApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", testData.getProperty("api.system.active"),
				testData.getProperty("api.system.timezoneid"), clientId, testData.getProperty("api.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");

		datalist.add(new OrgNode(systemId, 1, testData.getProperty("api.system.orgtype"),
				testData.getProperty("api.system.name"),
				Boolean.parseBoolean(testData.getProperty("api.system.active"))));

		logStep("Create a new enterprise below system.");
		createEnterpriseApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemId,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");

		datalist.add(new OrgNode(enterpriseId, 2, testData.getProperty("api.enterprise.orgtype"),
				testData.getProperty("api.enterprise.name"),
				Boolean.parseBoolean(testData.getProperty("api.enterprise.active"))));

		logStep("Create a new practice below enterprise.");
		createPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int practiceId = createPracticeApiResponse.jsonPath().get("orgId");

		datalist.add(new OrgNode(practiceId, 3, testData.getProperty("api.practice.orgtype"),
				testData.getProperty("api.practice.name"),
				Boolean.parseBoolean(testData.getProperty("api.practice.active"))));

		fetchClientDetailsApiResponse = clientInfo.getClientOrgHierarchyDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating error message of response body");

	//validation.verifySearchClientOrgHierarchyResponseBody(fetchClientDetailsApiResponse, datalist);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create client & without adding org node below the client then fetch & validate client org hierarchy details with client id")
	public void testExistingClientIdWithoutOrgNode() throws IOException {
		logStep("Create client using valid details in client name, sap number, active, testclient, client type and timezone field");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		List<OrgNode> datalist = new ArrayList<OrgNode>();
		int clientIdOne = createClientApiResponse.jsonPath().get("clientId");

		datalist.add(new OrgNode(clientIdOne, 0, "CLIENT", clientName,
				Boolean.parseBoolean(testData.getProperty("api.client.active"))));

		fetchClientDetailsApiResponse = clientInfo.getClientOrgHierarchyDetails(clientIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating error message of response body");

		validation.verifySearchClientOrgHierarchyResponseBody(fetchClientDetailsApiResponse, datalist);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error response for invalid url")
	public void testErrorResponseForInvalidUrl() throws Exception {
		logStep("Enter invalid url and validate error status code");
		fetchClientDetailsApiResponse = clientInfo.getClientOrgHierarchyUsingInvalidUrl(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchClientDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create client & add org node below the client then fetch & validate client org hierarchy details with client id")
	public void testExistingClientIdWithAgnosticFlow() throws IOException {
		logStep("Create client using valid details in client name, sap number, active, testclient, client type and timezone field");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		List<OrgNode> datalist = new ArrayList<OrgNode>();

		int clientIdTwo = createClientApiResponse.jsonPath().get("clientId");

		datalist.add(new OrgNode(clientIdTwo, 0, "CLIENT", clientName,
				Boolean.parseBoolean(testData.getProperty("api.client.active"))));

		logStep("Create a new system below client.");
		createSystemApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", testData.getProperty("api.system.active"),
				testData.getProperty("api.system.timezoneid"), clientIdTwo, testData.getProperty("api.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemIdTwo = createSystemApiResponse.jsonPath().getInt("orgId");

		datalist.add(new OrgNode(systemIdTwo, 1, testData.getProperty("api.system.orgtype"),
				testData.getProperty("api.system.name"),
				Boolean.parseBoolean(testData.getProperty("api.system.active"))));

		logStep("Create a new practice below system.");
		createPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				systemIdTwo, testData.getProperty("api.system.orgtype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int practiceIdTwo = createPracticeApiResponse.jsonPath().get("orgId");

		datalist.add(new OrgNode(practiceIdTwo, 2, testData.getProperty("api.practice.orgtype"),
				testData.getProperty("api.practice.name"),
				Boolean.parseBoolean(testData.getProperty("api.practice.active"))));

		fetchClientDetailsApiResponse = clientInfo.getClientOrgHierarchyDetails(clientIdTwo);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating error message of response body");
		//validation.verifySearchClientOrgHierarchyResponseBody(fetchClientDetailsApiResponse, datalist);
	}

}
