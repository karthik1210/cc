// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

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

public class GetSolutionsByOrgIdApiTest_DataLayer extends BaseRest {

	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	Response createClientApiResponse, createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			fetchClientDetailsApiResponse, fetchPracticeApiResponse, updateSolutionsApiResponse,
			fetchOrgDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing org id in orgId field and verify error message for org id field")
	public void testErrorMessageForNonExistingOrgId() throws IOException {
		logStep("Enter non existing org id and validate error message");
		int orgId = Integer.parseInt(testData.getProperty("api.org.invalid.id"));
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(orgId);
		logStep("Validating response information");
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		validation.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(fetchOrgDetailsApiResponse, orgId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		logStep("Enter invalid url  and validate error message");
		int orgId = Integer.parseInt(testData.getProperty("api.existing.orgid"));
		fetchOrgDetailsApiResponse = orgInfo.getOrgIdUsingInvalidUrl_DataLayer(orgId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchOrgDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing client id in orgId field and verify error message for org id field")
	public void testErrorMessageWhenSearchClientIdInOrgIdField() throws IOException {
		logStep("Enter clientId instead of org id in orgId field and validate error message");
		int clientId = Integer.parseInt(testData.getProperty("api.existing.client.id"));
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(clientId);
		logStep("Validating response information");
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		validation.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(fetchOrgDetailsApiResponse, clientId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "create active agnostic practice without/with solutions and verify response & status code")
	public void verifySolutionsDetailsForActiveAgnosticClient() throws IOException {
		logStep("Create active client with valid client details");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				testData.getProperty("api.client.id"), clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");
		logStep("create an active practice node without solutions");
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNodeDataLayer(practiceName,
				ccUser.getPracticeDisplayNameApi(), practiceSapNumber, "true", ccUser.getPracticeTimezoneIdApi(),
				ccUser.getPracticeOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Fetch solutions details using org id");
		int practiceIdOne = createPracticeApiResponse.jsonPath().getInt("orgId");
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 204);
		logStep("create an active practice node with solution");
		String name = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String sapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		logStep("create an active practice node");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNodeDataLayer(name,
				ccUser.getPracticeDisplayNameApi(), sapNumber, "true", ccUser.getPracticeTimezoneIdApi(),
				ccUser.getPracticeOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int practiceIdTwo = createPracticeApiResponse.jsonPath().getInt("orgId");
		logStep("Purchase solution for practice node");
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutionsDetails_DataLayer(1101, "Newly purchased",
				practiceIdTwo, Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Fetch solutions details using org id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceIdTwo);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchOrgIdSolutionsResponseBody(fetchOrgDetailsApiResponse, practiceIdTwo, name, 1101,
				"Newly purchased");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create inactive Agnostic client without/with purchased solution verify response & status code")
	public void verifySolutionsDetailsForInactiveAgnosticClient() throws IOException {
		logStep("Create inactive client with valid client details");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientApiResponse = clientInfo.createNewClient_DataLayer("false", testData.getProperty("api.client.id"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.type"), testData.getProperty("api.client.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");
		logStep("create an inactive practice node without solutions");
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNodeDataLayer(practiceName,
				ccUser.getPracticeDisplayNameApi(), practiceSapNumber, "false", ccUser.getPracticeTimezoneIdApi(),
				ccUser.getPracticeOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Fetch solutions details using org id");
		int practiceIdOne = createPracticeApiResponse.jsonPath().getInt("orgId");
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 204);
		logStep("create an inactive practice node with solution");
		String name = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String sapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		logStep("create an active practice node");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNodeDataLayer(name,
				ccUser.getPracticeDisplayNameApi(), sapNumber, "false", ccUser.getPracticeTimezoneIdApi(),
				ccUser.getPracticeOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int practiceIdTwo = createPracticeApiResponse.jsonPath().getInt("orgId");
		logStep("Purchase solution for practice node");
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutionsDetails_DataLayer(1101, "Newly purchased",
				practiceIdTwo, Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Fetch solutions details using org id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceIdTwo);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchOrgIdSolutionsResponseBody(fetchOrgDetailsApiResponse, practiceIdTwo, name, 1101,
				"Newly purchased");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create active nge client with purchased solution verify response & status code ")
	public void verifySolutionsDetailsForActiveNGEClient() throws IOException {
		logStep("Create active client with valid client details");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				testData.getProperty("api.client.id"), clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int clientId = createClientApiResponse.jsonPath().get("clientId");
		logStep("Create a new active system below client.");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), ccUser.getSystemNameApi(),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
				ccUser.getSystemSapNumberApi(), testData.getProperty("api.system.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new active enterprise below system.");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				systemId, ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi(), Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new active practice below enterprise.");
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), practiceName, ccUser.getPracticeOrgTypeApi(), enterpriseId,
				ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(), ccUser.getPracticeTimezoneIdApi(),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int practiceId = createPracticeApiResponse.jsonPath().getInt("orgId");
		logStep("Purchase solution for active practice node");
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutionsDetails_DataLayer(1101, "Newly purchased", practiceId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Fetch solutions details using practice id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchOrgIdSolutionsResponseBody(fetchOrgDetailsApiResponse, practiceId, practiceName, 1101,
				"Newly purchased");
		logStep("Fetch solutions details using System id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(systemId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchOrgIdSolutionsResponseBody(fetchOrgDetailsApiResponse, practiceId, practiceName, 1101,
				"Newly purchased");
		logStep("Fetch solutions details using Enteprise org id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(enterpriseId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchOrgIdSolutionsResponseBody(fetchOrgDetailsApiResponse, practiceId, practiceName, 1101,
				"Newly purchased");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create active nge client without purchased solution verify response & status code ")
	public void verifySolutionsDetailsForActiveNGEClientWithoutSolution() throws IOException {
		logStep("Create active client with valid client details");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				testData.getProperty("api.client.id"), clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int clientId = createClientApiResponse.jsonPath().get("clientId");
		logStep("Create a new active system below client.");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), ccUser.getSystemNameApi(),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
				ccUser.getSystemSapNumberApi(), testData.getProperty("api.system.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new active enterprise below system.");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				systemId, ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi(), Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new active practice below active enterprise without solutions.");
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		createPracticeApiResponse = orgInfo.createOrg_DataLayer("true", ccUser.getPracticeDisplayNameApi(),
				practiceName, ccUser.getPracticeOrgTypeApi(), enterpriseId, ccUser.getPracticeParentTypeApi(),
				ccUser.getPracticeSapNumberApi(), ccUser.getPracticeTimezoneIdApi(),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int practiceId = createPracticeApiResponse.jsonPath().getInt("orgId");
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 204);
		logStep("Fetch solutions details using practice id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 204);
		logStep("Fetch solutions details using System id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(systemId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 204);
		logStep("Fetch solutions details using Enteprise org id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(enterpriseId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create inactive nge client with purchased solution verify response & status code")
	public void verifySolutionsDetailsForInactiveNGEClientWithSolution() throws IOException {

		logStep("Create inactive client with valid client details");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientApiResponse = clientInfo.createNewClient_DataLayer("false", testData.getProperty("api.client.id"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.type"), testData.getProperty("api.client.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");
		logStep("Create a new inactive system below inactive client.");
		createSystemApiResponse = orgInfo.createOrg_DataLayer("false", testData.getProperty("api.system.displayname"),
				ccUser.getSystemNameApi(), testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), ccUser.getSystemSapNumberApi(),
				testData.getProperty("api.system.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new  inactive enterprise below inactive system.");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer("false", ccUser.getEnterpriseDisplayNameApi(),
				ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(), systemId,
				ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi(), Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new inactive practice below inactive enterprise.");
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		createPracticeApiResponse = orgInfo.createOrg_DataLayer("false", ccUser.getPracticeDisplayNameApi(),
				practiceName, ccUser.getPracticeOrgTypeApi(), enterpriseId, ccUser.getPracticeParentTypeApi(),
				ccUser.getPracticeSapNumberApi(), ccUser.getPracticeTimezoneIdApi(),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int practiceId = createPracticeApiResponse.jsonPath().getInt("orgId");
		logStep("Purchase solution for inactive practice node");
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutionsDetails_DataLayer(1101, "Newly purchased", practiceId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Fetch solutions details using practice id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchOrgIdSolutionsResponseBody(fetchOrgDetailsApiResponse, practiceId, practiceName, 1101,
				"Newly purchased");
		logStep("Fetch solutions details using System id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(systemId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 204);
		logStep("Fetch solutions details using Enteprise org id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(enterpriseId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create inactive nge client without solution verify response & status code")
	public void verifySolutionsDetailsForInactiveNGEClientWitioutolution() throws IOException {
		logStep("Create inactive client with valid client details");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientApiResponse = clientInfo.createNewClient_DataLayer("false", testData.getProperty("api.client.id"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.type"), testData.getProperty("api.client.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");
		logStep("create an inactive practice node");
		logStep("Create a new  system below inactive client.");
		createSystemApiResponse = orgInfo.createOrg_DataLayer("false", testData.getProperty("api.system.displayname"),
				ccUser.getSystemNameApi(), testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), ccUser.getSystemSapNumberApi(),
				testData.getProperty("api.system.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new  enterprise below inactive system.");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer("false", ccUser.getEnterpriseDisplayNameApi(),
				ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(), systemId,
				ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi(), Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new inactive practice below inactive enterprise without solutions.");
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		createPracticeApiResponse = orgInfo.createOrg_DataLayer("false", ccUser.getPracticeDisplayNameApi(),
				practiceName, ccUser.getPracticeOrgTypeApi(), enterpriseId, ccUser.getPracticeParentTypeApi(),
				ccUser.getPracticeSapNumberApi(), ccUser.getPracticeTimezoneIdApi(),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int practiceId = createPracticeApiResponse.jsonPath().getInt("orgId");
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 204);
		logStep("Fetch solutions details using practice id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 204);
		logStep("Fetch solutions details using System id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(systemId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 204);
		logStep("Fetch solutions details using Enteprise org id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(enterpriseId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 204);
	}

}
