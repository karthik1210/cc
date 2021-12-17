// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class EditOrgNodeApiTest extends BaseRest {

	public CreateClientApiTests createClientTest;
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	Response createClientApiResponse, createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			updateSystemApiResponse, fetchSystemDetailsApiResponse, updateEnterpriseApiResponse,
			fetchClientDetailsApiResponse, fetchEnterpriseDetailsApiResponse, updatePracticeApiResponse,
			fetchPracticeDetailsApiResponse;

	@BeforeMethod()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit system details and fetch updated system details using system id")
	public void testEditOrgSystemNode() throws IOException {
		logStep("Create a active system node below client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(),
				createClientApiResponse.jsonPath().getInt("id"), ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int systemId = createSystemApiResponse.jsonPath().getInt("id");

		logStep("Update system details");
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		String systemDisplayName = ccUser.getSystemDisplayNameApi().concat(CCUtils.generateRandomString());
		updateSystemApiResponse = orgInfo.updateSystem(ccUser.getOrgActive(), systemDisplayName, systemId,
				systemSapNumber, testData.getProperty("new.timezone"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSystemApiResponse);
		CCUtils.assertEquals(updateSystemApiResponse.getStatusCode(), 200);
		validation.verifyCreateSystemResponseBody(updateSystemApiResponse, ccUser.getSystemNameApi());
		String orgId = updateSystemApiResponse.jsonPath().get("id").toString();

		logStep("Fetch updated system details using system id");
		fetchSystemDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		validation.verifySearchUpdatedSystemResponseBody(fetchSystemDetailsApiResponse, orgId, systemDisplayName,
				systemSapNumber, ccUser.getOrgActive(), testData.getProperty("new.timezone"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit enterprise details and fetch updated enterprise details using enterprise id")
	public void testEditOrgEnterpriseNode() throws IOException {
		logStep("Create inactive enterprise node below system");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), testData.getProperty("api.client.five.type"),
				ccUser.getTimezoneId());
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(),
				createClientApiResponse.jsonPath().getInt("id"), ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getOrgActive(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				createSystemApiResponse.jsonPath().getInt("id"), ccUser.getEnterpriseParentTypeApi(),
				ccUser.getEnterpriseSapNumberApi(), ccUser.getEnterpriseTimezoneIdApi());
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("id");

		logStep("Edit enterprise details");
		String enterpriseSapNumber = ccUser.getEnterpriseSapNumberApi().concat(CCUtils.generateRandomString());
		String enterpriseDisplayName = ccUser.getEnterpriseDisplayNameApi().concat(CCUtils.generateRandomString());
		updateEnterpriseApiResponse = orgInfo.updateEnterprise(ccUser.getEnterpriseActiveApi(), enterpriseDisplayName,
				enterpriseId, enterpriseSapNumber, testData.getProperty("new.timezone"));
		String orgId = updateEnterpriseApiResponse.jsonPath().get("id").toString();

		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateEnterpriseApiResponse);
		CCUtils.assertEquals(updateEnterpriseApiResponse.getStatusCode(), 200);
		logStep("fetch updated enterprise details using enterprise id");
		fetchEnterpriseDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		validation.verifySearchUpdatedEnterpriseResponseBody(fetchEnterpriseDetailsApiResponse, orgId,
				enterpriseDisplayName, enterpriseSapNumber, ccUser.getEnterpriseActiveApi(),
				testData.getProperty("new.timezone"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit nge practice details and fetch updated practice details using practice id")
	public void testEditOrgPracticeNode() throws IOException {
		logStep("Create active nge practice node below enterprise");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), testData.getProperty("api.client.five.type"),
				ccUser.getTimezoneId());
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(),
				createClientApiResponse.jsonPath().getInt("id"), ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				createSystemApiResponse.jsonPath().getInt("id"), ccUser.getEnterpriseParentTypeApi(),
				ccUser.getEnterpriseSapNumberApi(), ccUser.getEnterpriseTimezoneIdApi());
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				createEnterpriseApiResponse.jsonPath().getInt("id"), ccUser.getPracticeParentTypeApi(),
				ccUser.getPracticeSapNumberApi(), ccUser.getPracticeTimezoneIdApi());
		int practiceId = createPracticeApiResponse.jsonPath().getInt("id");

		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		String practiceSolutionDisplayName = ccUser.getPracticeDisplayNameApi().concat(CCUtils.generateRandomString());
		updatePracticeApiResponse = orgInfo.updatePractice(ccUser.getOrgActive(), practiceSolutionDisplayName,
				practiceId, practiceSapNumber, testData.getProperty("new.timezone"));
		String orgId = updatePracticeApiResponse.jsonPath().get("id").toString();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updatePracticeApiResponse);
		CCUtils.assertEquals(updatePracticeApiResponse.getStatusCode(), 200);
		fetchPracticeDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		validation.verifySearchUpdatedPracticeResponseBody(fetchPracticeDetailsApiResponse, orgId,
				practiceSolutionDisplayName, practiceSapNumber, ccUser.getOrgActive(),
				testData.getProperty("new.timezone"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit agnostic practice below inactive client & verify response")
	public void testEditActiveAgnosticPracticeBelowInactiveClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getOrgActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Create a new inactive practice below inactive client.");
		createPracticeApiResponse = orgInfo.createAgnosticPractice(ccUser.getOrgActive(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				clientId, ccUser.getParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());
		int orgId = createPracticeApiResponse.jsonPath().getInt("id");

		logStep("Update an existing practice from inactive to active status below inactive client.");
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		updatePracticeApiResponse = orgInfo.updatePractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), orgId, practiceSapNumber, ccUser.getPracticeTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updatePracticeApiResponse);
		CCUtils.assertEquals(updatePracticeApiResponse.getStatusCode(), 400);
		validation.verifyInactiveClientErrorResponsForPractice(updatePracticeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit active enterprise below inactive system & verify response")
	public void testEditActiveEnterpriseBelowInactiveSystem() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Create a new inactive system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getOrgActive(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int systemId = createSystemApiResponse.jsonPath().getInt("id");

		logStep("Create a new active enterprise below active client.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				systemId, ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("id");

		logStep("Update an existing enterprise below inactive system.");
		String enterpriseSapNumber = ccUser.getEnterpriseSapNumberApi().concat(CCUtils.generateRandomString());
		updateEnterpriseApiResponse = orgInfo.updateEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), enterpriseId, enterpriseSapNumber,
				ccUser.getEnterpriseTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateEnterpriseApiResponse);
		CCUtils.assertEquals(updateEnterpriseApiResponse.getStatusCode(), 400);
		validation.verifyInactiveSystemErrorResponForEnterprise(updateEnterpriseApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit NGE practice below inactive enterprise & verify response")
	public void testEditActiveNgePracticeBelowInactieEnterprise() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Create a new active system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int systemId = createSystemApiResponse.jsonPath().getInt("id");

		logStep("Create a new inactive enterprise below active system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getOrgActive(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				systemId, ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("id");

		logStep("Create a new inactive practice below inactive enterprise.");
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				enterpriseId, ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());
		int practiceId = createPracticeApiResponse.jsonPath().getInt("id");

		logStep("Update an existing practice status below inactive enterprise.");
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		updatePracticeApiResponse = orgInfo.updatePractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), practiceId, practiceSapNumber, ccUser.getPracticeTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updatePracticeApiResponse);
		CCUtils.assertEquals(updatePracticeApiResponse.getStatusCode(), 400);
		validation.verifyInactivePracticeErrorResponsWhileUpdataingActivePractice(updatePracticeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit system status below inactive client & verify response")
	public void testChangeStatusSystemActiveBelowInactiveClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getOrgActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Create a new inactive system below inactive client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getOrgActive(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int orgId = createSystemApiResponse.jsonPath().getInt("id");

		logStep("Update an existing system status below inactive client.");
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		updateSystemApiResponse = orgInfo.updateSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				orgId, systemSapNumber, ccUser.getSystemTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSystemApiResponse);
		CCUtils.assertEquals(updateSystemApiResponse.getStatusCode(), 400);
		validation.verifyInactiveClientErrorResponsforSystem(updateSystemApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit status of agnostic practice below active client & verify response")
	public void testChangeStatusInactiveAgnosticPracticeBelowActiveClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		int clientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Create a new active system below active client.");
		createPracticeApiResponse = orgInfo.createAgnosticPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				clientId, ccUser.getParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());
		int orgId = createPracticeApiResponse.jsonPath().getInt("id");

		logStep("Create a new inactive practice below active client.");
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		updatePracticeApiResponse = orgInfo.updatePractice(ccUser.getOrgActive(), ccUser.getPracticeDisplayNameApi(),
				orgId, practiceSapNumber, ccUser.getPracticeTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updatePracticeApiResponse);
		CCUtils.assertEquals(updatePracticeApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit status inactive enterprise below active system & verify response")
	public void testChangeStatusInactiveEnterpriseBelowActiveSystem() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Create a new active system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int systemId = createSystemApiResponse.jsonPath().getInt("id");

		logStep("Create a new active enterprise below active system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				systemId, ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("id");

		logStep("Update an existing enterprise below active system.");
		String enterpriseSapNumber = ccUser.getEnterpriseSapNumberApi().concat(CCUtils.generateRandomString());
		updateEnterpriseApiResponse = orgInfo.updateEnterprise(ccUser.getOrgActive(),
				ccUser.getEnterpriseDisplayNameApi(), enterpriseId, enterpriseSapNumber,
				ccUser.getEnterpriseTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateEnterpriseApiResponse);
		CCUtils.assertEquals(updateEnterpriseApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit status NGE practice below active enterprise & verify response")
	public void testChangeStatusNgePracticeInactiveBelowActieEnterprise() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Create a new active system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int systemId = createSystemApiResponse.jsonPath().getInt("id");

		logStep("Create a new active enterprise below active system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				systemId, ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("id");

		logStep("Create a new active practice below active enterprise.");
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				enterpriseId, ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());
		int practiceId = createPracticeApiResponse.jsonPath().getInt("id");

		logStep("Update an existing practice below active enterprise.");
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		updatePracticeApiResponse = orgInfo.updatePractice(ccUser.getOrgActive(), ccUser.getPracticeDisplayNameApi(),
				practiceId, practiceSapNumber, ccUser.getPracticeTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updatePracticeApiResponse);
		CCUtils.assertEquals(updatePracticeApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit inactive system below active client & verify response")
	public void testChangeStatusInactiveSystemBelowActiveClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Create a new active system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int orgId = createSystemApiResponse.jsonPath().getInt("id");

		logStep("Update an existing system below active client.");
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		updateSystemApiResponse = orgInfo.updateSystem(ccUser.getOrgActive(), ccUser.getSystemDisplayNameApi(), orgId,
				systemSapNumber, ccUser.getSystemTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSystemApiResponse);
		CCUtils.assertEquals(updateSystemApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit inherit functionality for enterprise node & verify response")
	public void testInheritanceFunctionalityEditAtEnterpriseNode() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Create a new active system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int systemId = createSystemApiResponse.jsonPath().getInt("id");

		logStep("Create a new enterprise below active system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(), null,
				ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(), systemId,
				ccUser.getEnterpriseParentTypeApi(), " ", null);
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("id");

		logStep("Update an existing enterprise below active system.");
		updateEnterpriseApiResponse = orgInfo.updateEnterprise(ccUser.getEnterpriseActiveApi(), null, enterpriseId, " ",
				null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateEnterpriseApiResponse);
		CCUtils.assertEquals(updateEnterpriseApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit inherit functionality for practice node & verify response")
	public void testInheritanceFunctionalityEditAtPracticeNode() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Create a new active system below client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int systemId = createSystemApiResponse.jsonPath().getInt("id");

		logStep("Create a new active enterprise below system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				systemId, ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("id");

		logStep("Create a new active practice below enterprise.");
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(), null,
				ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(), enterpriseId,
				ccUser.getPracticeParentTypeApi(), " ", null);
		int practiceId = createPracticeApiResponse.jsonPath().getInt("id");

		logStep("Update an existing practice.");
		updatePracticeApiResponse = orgInfo.updatePractice(ccUser.getPracticeActiveApi(), null, practiceId, " ", null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updatePracticeApiResponse);
		CCUtils.assertEquals(updatePracticeApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit inherit functionality for system node & verify response")
	public void testInheritanceFunctionalityEditAtSystemNode() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Create a new active system below inactive client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), null, ccUser.getSystemNameApi(),
				ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(), " ", null);
		int systemId = createSystemApiResponse.jsonPath().getInt("id");

		logStep("Update an existing system below active client.");
		updateSystemApiResponse = orgInfo.updateSystem(ccUser.getSystemeActiveApi(), null, systemId, " ", null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSystemApiResponse);
		CCUtils.assertEquals(updateSystemApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit system for verifying maximum character & verify response")
	public void testEditForMaximumCharacterAndFreeTextFieldErrorResponsAtSystem() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Create a new active system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int SystemId = createSystemApiResponse.jsonPath().getInt("id");

		logStep("Update an existing system below active client.");
		updateSystemApiResponse = orgInfo.updateSystem(ccUser.getOrgActive(), ccUser.getInvalidSolutionDisplayName(),
				SystemId, ccUser.getInvalidSap(), ccUser.getSystemTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSystemApiResponse);
		CCUtils.assertEquals(updateSystemApiResponse.getStatusCode(), 400);
		validation.verifyMaximumCharacterAndFreeTextFieldErrorResponsForOrgNode(updateSystemApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit enterprise for verifying maximum character & verify response")
	public void testEditForMaximumCharacterAndFreeTextFieldErrorResponsAtEnterprise() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Create a new active system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int systemId = createSystemApiResponse.jsonPath().getInt("id");

		logStep("Create a new active enterprise below active system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				systemId, ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("id");

		logStep("Update an existing enterprise below active system.");
		updateEnterpriseApiResponse = orgInfo.updateEnterprise(ccUser.getOrgActive(),
				ccUser.getInvalidSolutionDisplayName(), enterpriseId, ccUser.getInvalidSap(),
				ccUser.getEnterpriseTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateEnterpriseApiResponse);
		CCUtils.assertEquals(updateEnterpriseApiResponse.getStatusCode(), 400);
		validation.verifyMaximumCharacterAndFreeTextFieldErrorResponsForOrgNode(updateEnterpriseApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit practice for verifying maximum character & verify response")
	public void testEditForMaximumCharacterAndFreeTextFieldErrorResponsAtPractice() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().getInt("id");

		logStep("Create a new active system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		int systemId = createSystemApiResponse.jsonPath().getInt("id");

		logStep("Create a new active enterprise below active system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				systemId, ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("id");

		logStep("Create a new active practice below active enterprise.");
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				enterpriseId, ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());
		int practiceId = createPracticeApiResponse.jsonPath().getInt("id");

		logStep("Update an existing practice below active enterprise.");
		updatePracticeApiResponse = orgInfo.updatePractice(ccUser.getOrgActive(),
				ccUser.getInvalidSolutionDisplayName(), practiceId, ccUser.getInvalidSap(),
				ccUser.getPracticeTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updatePracticeApiResponse);
		CCUtils.assertEquals(updatePracticeApiResponse.getStatusCode(), 400);
		validation.verifyMaximumCharacterAndFreeTextFieldErrorResponsForOrgNode(updatePracticeApiResponse);
	}
}
