// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

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
				.getClientOrgHierarchyDetails(Integer.parseInt(testData.getProperty("api.nonexisting.client.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 400);
		logStep("Validating error message of response body");
		validation.verifyErrorResponseBodyForClientIdField(fetchClientDetailsApiResponse,
				(testData.getProperty("api.nonexisting.client.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create client & add org node below the client then fetch & validate client org hierarchy details with client id")
	public void testExistingClientIdWithNgeFlow() throws IOException {
		logStep("Create client using valid details in client name, sap number, active, testclient, client type and timezone field");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"),
				testData.getProperty("api.client.id"), clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		List<OrgNode> datalist = new ArrayList();
		int clientId = createClientApiResponse.jsonPath().get("clientId");

		datalist.add(new OrgNode(clientId, 0, "CLIENT", clientName,
				Boolean.parseBoolean(testData.getProperty("api.client.active")), 1));

		logStep("Create a new system below client.");
		createSystemApiResponse = orgInfo.createOrg(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
				testData.getProperty("api.system.sapnumber"), testData.getProperty("api.system.timezoneid"));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");

		datalist.add(new OrgNode(systemId, 1, testData.getProperty("api.system.orgtype"),
				testData.getProperty("api.system.name"),
				Boolean.parseBoolean(testData.getProperty("api.system.active")), 2));

		logStep("Create a new enterprise below system.");
		createEnterpriseApiResponse = orgInfo.createOrg(testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.displayname"), testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.sapnumber"),
				testData.getProperty("api.enterprise.timezoneid"));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");

		datalist.add(new OrgNode(enterpriseId, 2, testData.getProperty("api.enterprise.orgtype"),
				testData.getProperty("api.enterprise.name"),
				Boolean.parseBoolean(testData.getProperty("api.enterprise.active")), 3));

		logStep("Create a new practice below enterprise.");
		createPracticeApiResponse = orgInfo.createOrg(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.sapnumber"),
				testData.getProperty("api.practice.timezoneid"));

		int practiceId = createPracticeApiResponse.jsonPath().get("orgId");

		datalist.add(new OrgNode(practiceId, 3, testData.getProperty("api.practice.orgtype"),
				testData.getProperty("api.practice.name"),
				Boolean.parseBoolean(testData.getProperty("api.practice.active")), 4));

		fetchClientDetailsApiResponse = clientInfo.getClientOrgHierarchyDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating error message of response body");

		validation.verifySearchClientOrgHierarchyResponseBody(fetchClientDetailsApiResponse, datalist);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create client & without adding org node below the client then fetch & validate client org hierarchy details with client id")
	public void testExistingClientIdWithoutOrgNode() throws IOException {
		logStep("Create client using valid details in client name, sap number, active, testclient, client type and timezone field");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"),
				testData.getProperty("api.client.id"), clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		List<OrgNode> datalist = new ArrayList();
		int clientId = createClientApiResponse.jsonPath().get("clientId");

		datalist.add(new OrgNode(clientId, 0, "CLIENT", clientName,
				Boolean.parseBoolean(testData.getProperty("api.client.active")), 1));

		fetchClientDetailsApiResponse = clientInfo.getClientOrgHierarchyDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating error message of response body");

		validation.verifySearchClientOrgHierarchyResponseBody(fetchClientDetailsApiResponse, datalist);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error response for invalid url")
	public void testErrorResponseForInvalidUrl() throws Exception {
		logStep("Enter invalid url and validate error status code");
		fetchClientDetailsApiResponse = clientInfo
				.getClientOrgHierarchyUsingInvalidUrl(Integer.parseInt(testData.getProperty("api.existing.client.id")));
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
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"),
				testData.getProperty("api.client.id"), clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		List<OrgNode> datalist = new ArrayList();
		int clientId = createClientApiResponse.jsonPath().get("clientId");

		datalist.add(new OrgNode(clientId, 0, "CLIENT", clientName,
				Boolean.parseBoolean(testData.getProperty("api.client.active")), 1));

		logStep("Create a new practice below client.");
		createPracticeApiResponse = orgInfo.createOrg(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), clientId, "CLIENT",
				testData.getProperty("api.practice.sapnumber"), testData.getProperty("api.practice.timezoneid"));

		int practiceId = createPracticeApiResponse.jsonPath().get("orgId");

		datalist.add(new OrgNode(practiceId, 1, testData.getProperty("api.practice.orgtype"),
				testData.getProperty("api.practice.name"),
				Boolean.parseBoolean(testData.getProperty("api.practice.active")), 2));

		logStep("Create a new system below client.");
		createSystemApiResponse = orgInfo.createOrg(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
				testData.getProperty("api.system.sapnumber"), testData.getProperty("api.system.timezoneid"));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");

		datalist.add(new OrgNode(systemId, 1, testData.getProperty("api.system.orgtype"),
				testData.getProperty("api.system.name"),
				Boolean.parseBoolean(testData.getProperty("api.system.active")), 3));

		logStep("Create a new enterprise below system.");
		createEnterpriseApiResponse = orgInfo.createOrg(testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.displayname"), testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.sapnumber"),
				testData.getProperty("api.enterprise.timezoneid"));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");

		datalist.add(new OrgNode(enterpriseId, 3, testData.getProperty("api.enterprise.orgtype"),
				testData.getProperty("api.enterprise.name"),
				Boolean.parseBoolean(testData.getProperty("api.enterprise.active")), 4));

		logStep("Create a new practice below enterprise.");
		createPracticeApiResponse = orgInfo.createOrg(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.sapnumber"),
				testData.getProperty("api.practice.timezoneid"));

		int ngePracticeId = createPracticeApiResponse.jsonPath().get("orgId");

		datalist.add(new OrgNode(ngePracticeId, 4, testData.getProperty("api.practice.orgtype"),
				testData.getProperty("api.practice.name"),
				Boolean.parseBoolean(testData.getProperty("api.practice.active")), 5));

		fetchClientDetailsApiResponse = clientInfo.getClientOrgHierarchyDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating error message of response body");

		validation.verifySearchClientOrgHierarchyResponseBody(fetchClientDetailsApiResponse, datalist);
	}

}
