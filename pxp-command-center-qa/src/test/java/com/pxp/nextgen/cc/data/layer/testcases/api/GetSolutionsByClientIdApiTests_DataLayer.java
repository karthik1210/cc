// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetSolutionsByClientIdApiTests_DataLayer extends BaseRest {

	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	Response createClientApiResponse, createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			fetchClientDetailsApiResponse, fetchPracticeApiResponse, updateSolutionsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing client id in clientId solutions field and verify error message for client id field")
	public void testErrorMessageForNonExistingClientId() throws IOException {
		logStep("Enter non existing client id and validate error message");
		int clientId = Integer.parseInt(testData.getProperty("api.nonexisting.client.id"));
		fetchClientDetailsApiResponse = clientInfo.getClientSolutionsDetails_DataLayer(clientId);
		logStep("Validating response information");
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		validation.verifyErrorResponseBodyForClientIdSolutionsField(fetchClientDetailsApiResponse, clientId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing org id in clientId solutions field and verify error message for client id field")
	public void testErrorMessageWhenSearchOrgIdInClientIdField() throws IOException {
		logStep("Enter orgId instead of client id in clientId field and validate error message");
		int orgId = Integer.parseInt(testData.getProperty("api.existing.orgid"));
		fetchClientDetailsApiResponse = clientInfo.getClientSolutionsDetails_DataLayer(orgId);
		logStep("Validating response information");
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		validation.verifyErrorResponseBodyForClientIdSolutionsField(fetchClientDetailsApiResponse, orgId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new client without practice node & verify status code and response error message for clientId solutions field")
	public void testCreateClientWithoutPracticeNode() throws IOException {
		logStep("Create active client with valid client details");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.type"), testData.getProperty("api.client.timezoneid"));

		int clientId = createClientApiResponse.jsonPath().get("clientId");
		logStep("Fetch newly created client details using client id");
		fetchClientDetailsApiResponse = clientInfo.getClientSolutionsDetails_DataLayer(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "create agnostic practice without solutions and verify response & status code in clientId solutions field")
	public void testCreateAgnosticPracticeWithoutSolutions() throws IOException {
		logStep("Create active client with valid client details");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.type"), testData.getProperty("api.client.timezoneid"));

		int clientId = createClientApiResponse.jsonPath().get("clientId");
		logStep("create an active practice node");
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		logStep("create an active practice node with solutions");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNodeDataLayer(practiceName,
				ccUser.getPracticeDisplayNameApi(), practiceSapNumber, "true", ccUser.getPracticeTimezoneIdApi(),
				ccUser.getPracticeOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				testData.getProperty("ehr.identifier"), testData.getProperty("pm.identifier"));
		logStep("Fetch newly created client details using client id");
		fetchClientDetailsApiResponse = clientInfo.getClientSolutionsDetails_DataLayer(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "create agnostic practice with solutions and verify response & status code in clientId solutions field")
	public void testCreateAgnosticPracticeWithPurchasedSolution() throws IOException {
		logStep("Create active client with valid client details");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.type"), testData.getProperty("api.client.timezoneid"));

		int clientId = createClientApiResponse.jsonPath().get("clientId");

		logStep("create an active system node");
		String systemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createAgnosticPracticeNodeDataLayer(systemName, systemName, systemSapNumber,
				"true", ccUser.getPracticeTimezoneIdApi(), ccUser.getSystemOrgTypeApi(), clientId,
				ccUser.getParentTypeApi(), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));

		int systemId = createSystemApiResponse.jsonPath().get("orgId");

		logStep("create an active practice node");
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		logStep("create an active practice node");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNodeDataLayer(practiceName,
				ccUser.getPracticeDisplayNameApi(), practiceSapNumber, "true", ccUser.getPracticeTimezoneIdApi(),
				ccUser.getPracticeOrgTypeApi(), systemId, ccUser.getParentTypeApi(),
				testData.getProperty("ehr.identifier"), testData.getProperty("pm.identifier"));

		int practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		logStep("Purchase solution for practice node");
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutionsDetails_DataLayer(1101, "Newly purchased", practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);

		logStep("Fetch newly created client details using client id");
		fetchClientDetailsApiResponse = clientInfo.getClientSolutionsDetails_DataLayer(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create inactive client with inactive practice having solution verify response & status code in clientId solutions field")
	public void testCreateInactiveClientWithInactivePracticeNodeWithPurchasedSolution() throws IOException {
		logStep("Create inactive client with valid client details");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientApiResponse = clientInfo.createNewClient_DataLayer("false", clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));

		int clientId = createClientApiResponse.jsonPath().get("clientId");

		logStep("create an active system node");
		String systemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createAgnosticPracticeNodeDataLayer(systemName, systemName, systemSapNumber,
				"true", ccUser.getPracticeTimezoneIdApi(), ccUser.getSystemOrgTypeApi(), clientId,
				ccUser.getParentTypeApi(), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));

		int systemId = createSystemApiResponse.jsonPath().get("orgId");

		logStep("create an inactive practice node");
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		logStep("create an active practice node with solutions");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNodeDataLayer(practiceName,
				ccUser.getPracticeDisplayNameApi(), practiceSapNumber, "false", ccUser.getPracticeTimezoneIdApi(),
				ccUser.getPracticeOrgTypeApi(), systemId, ccUser.getParentTypeApi(),
				testData.getProperty("ehr.identifier"), testData.getProperty("pm.identifier"));

		int practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		logStep("Purchase solution for inactive practice node");
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutionsDetails_DataLayer(1101, "Newly purchased", practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);

		logStep("Fetch newly created client details using client id");
		fetchClientDetailsApiResponse = clientInfo.getClientSolutionsDetails_DataLayer(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create active client with inactive practice having solution verify response & status code in clientId solutions field")
	public void testCreateActiveClientWithInactivePracticeNodeWithPurchasedSolution() throws IOException {
		logStep("Create active client with valid client details");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.type"), testData.getProperty("api.client.timezoneid"));

		int clientId = createClientApiResponse.jsonPath().get("clientId");

		logStep("create an active system node");
		String systemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createAgnosticPracticeNodeDataLayer(systemName, systemName, systemSapNumber,
				"true", ccUser.getPracticeTimezoneIdApi(), ccUser.getSystemOrgTypeApi(), clientId,
				ccUser.getParentTypeApi(), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));

		int systemId = createSystemApiResponse.jsonPath().get("orgId");

		logStep("create an inactive practice node");
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		logStep("create an active practice node with solutions");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNodeDataLayer(practiceName,
				ccUser.getPracticeDisplayNameApi(), practiceSapNumber, "false", ccUser.getPracticeTimezoneIdApi(),
				ccUser.getPracticeOrgTypeApi(), systemId, ccUser.getParentTypeApi(),
				testData.getProperty("ehr.identifier"), testData.getProperty("pm.identifier"));

		int practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		logStep("Purchase solution for inactive practice node");
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutionsDetails_DataLayer(1101, "Newly purchased", practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);

		logStep("Fetch newly created client details using client id");
		fetchClientDetailsApiResponse = clientInfo.getClientSolutionsDetails_DataLayer(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create nge practice with purchased solution verify response & status code in clientId solutions field")
	public void testCreateNgePracticeWithPurchasedSolution() throws IOException {
		logStep("Create active client with valid client details");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.type"), testData.getProperty("api.client.timezoneid"));

		int clientId = createClientApiResponse.jsonPath().get("clientId");
		logStep("Create a new active system below client.");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), ccUser.getSystemNameApi(),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
				testData.getProperty("api.system.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new active enterprise below system.");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				systemId, ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseTimezoneIdApi(),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new active practice below enterprise.");
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), practiceName, ccUser.getPracticeOrgTypeApi(), enterpriseId,
				ccUser.getPracticeParentTypeApi(), ccUser.getPracticeTimezoneIdApi(),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));

		int practiceId = createPracticeApiResponse.jsonPath().getInt("orgId");
		logStep("Purchase solution for inactive practice node");
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutionsDetails_DataLayer(1101, "Newly purchased", practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);

		logStep("Fetch newly created client details using client id");
		fetchClientDetailsApiResponse = clientInfo.getClientSolutionsDetails_DataLayer(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
	}

}
