// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;

import org.testng.annotations.BeforeTest;
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

	@BeforeTest()
	public void setBaseUri() throws Exception {

		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit an existing system details and fetch updated system details using system id")
	public void testEditOrgSystemNode() throws IOException {
		String systemName = ccUser.getSystemNameApi();
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		updateSystemApiResponse = orgInfo.updateSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemOrgId(), systemSapNumber, ccUser.getSystemTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateSystemApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateSystemResponseBody(updateSystemApiResponse, systemName);
		String orgId = updateSystemApiResponse.jsonPath().get("id").toString();
		fetchSystemDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(fetchSystemDetailsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(fetchSystemDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");

		validation.verifySearchUpdatedSystemResponseBody(fetchSystemDetailsApiResponse, orgId,
				ccUser.getSystemDisplayNameApi(), systemSapNumber, ccUser.getSystemeActiveApi(),
				ccUser.getSystemTimezoneIdApi());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit an existing enterprise details and fetch updated enterprise details using enterprise id")
	public void testEditOrgEnterpriseNode() throws IOException {
		String enterpriseName = ccUser.getEnterpriseNameApi();
		String enterpriseSapNumber = ccUser.getEnterpriseSapNumberApi().concat(CCUtils.generateRandomString());
		updateEnterpriseApiResponse = orgInfo.updateEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseOrgId(), enterpriseSapNumber,
				ccUser.getEnterpriseTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateEnterpriseApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateEnterpriseApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateEnterpriseResponseBody(updateEnterpriseApiResponse, enterpriseName);
		String orgId = updateEnterpriseApiResponse.jsonPath().get("id").toString();
		fetchEnterpriseDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(fetchEnterpriseDetailsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(fetchEnterpriseDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");

		validation.verifySearchUpdatedEnterpriseResponseBody(fetchEnterpriseDetailsApiResponse, orgId,
				ccUser.getEnterpriseDisplayNameApi(), enterpriseSapNumber, ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseTimezoneIdApi());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit an existing practice details and fetch updated practice details using practice id")
	public void testEditOrgPracticeNode() throws IOException {
		String practiceName = ccUser.getPracticeNameApi();
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		updatePracticeApiResponse = orgInfo.updatePractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeOrgId(), practiceSapNumber,
				ccUser.getPracticeTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updatePracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updatePracticeApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreatePracticeResponseBody(updatePracticeApiResponse, practiceName);
		String orgId = updatePracticeApiResponse.jsonPath().get("id").toString();
		fetchPracticeDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(fetchPracticeDetailsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(fetchPracticeDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");

		validation.verifySearchUpdatedPracticeResponseBody(fetchPracticeDetailsApiResponse, orgId,
				ccUser.getPracticeDisplayNameApi(), practiceSapNumber, ccUser.getPracticeActiveApi(),
				ccUser.getPracticeTimezoneIdApi());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit agnostic practice below inactive client & verify response")
	public void testEditActiveAgnosticPracticeBelowInactiveClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getOrgActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new inactive practice below inactive client.");
		createPracticeApiResponse = orgInfo.createAgnosticPractice(ccUser.getOrgActive(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				Integer.parseInt(clientId), ccUser.getParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		String orgId = createPracticeApiResponse.jsonPath().get("id").toString();

		logStep("Update an existing practice from inactive to active status below inactive client.");
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		updatePracticeApiResponse = orgInfo.updatePractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), Integer.parseInt(orgId), practiceSapNumber,
				ccUser.getPracticeTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updatePracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updatePracticeApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyInactiveClientErrorResponsForPractice(updatePracticeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit active enterprise below inactive system & verify response")
	public void testEditActiveEnterpriseBelowInactiveSystem() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new inactive system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getOrgActive(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
				ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();
		logStep("Create a new active enterprise below active client.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				Integer.parseInt(orgId), ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 200);
		orgId = createEnterpriseApiResponse.jsonPath().get("id").toString();

		logStep("Update an existing enterprise below inactive system.");
		String enterpriseSapNumber = ccUser.getEnterpriseSapNumberApi().concat(CCUtils.generateRandomString());
		updateEnterpriseApiResponse = orgInfo.updateEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), Integer.parseInt(orgId), enterpriseSapNumber,
				ccUser.getEnterpriseTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateEnterpriseApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateEnterpriseApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyInactiveSystemErrorResponForEnterprise(updateEnterpriseApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit NGE practice below inactive enterprise & verify response")
	public void testEditActiveNgePracticeBelowInactieEnterprise() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new active system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
				ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();
		logStep("Create a new inactive enterprise below active system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getOrgActive(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				Integer.parseInt(orgId), ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 200);
		orgId = createEnterpriseApiResponse.jsonPath().get("id").toString();
		logStep("Create a new inactive practice below inactive enterprise.");
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				Integer.parseInt(orgId), ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		orgId = createPracticeApiResponse.jsonPath().get("id").toString();

		logStep("Update an existing practice status below inactive enterprise.");
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		updatePracticeApiResponse = orgInfo.updatePractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), Integer.parseInt(orgId), practiceSapNumber,
				ccUser.getPracticeTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updatePracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updatePracticeApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyInactivePracticeErrorResponsWhileUpdataingActivePractice(updatePracticeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit system status below inactive client & verify response")
	public void testChangeStatusSystemActiveBelowInactiveClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getOrgActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new inactive system below inactive client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getOrgActive(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
				ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();

		logStep("Update an existing system status below inactive client.");
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		updateSystemApiResponse = orgInfo.updateSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				Integer.parseInt(orgId), systemSapNumber, ccUser.getSystemTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateSystemApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyInactiveClientErrorResponsforSystem(updateSystemApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit status of agnostic practice below active client & verify response")
	public void testChangeStatusInactiveAgnosticPracticeBelowActiveClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new active system below active client.");
		createPracticeApiResponse = orgInfo.createAgnosticPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				Integer.parseInt(clientId), ccUser.getParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		String orgId = createPracticeApiResponse.jsonPath().get("id").toString();

		logStep("Create a new inactive practice below active client.");
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		updatePracticeApiResponse = orgInfo.updatePractice(ccUser.getOrgActive(), ccUser.getPracticeDisplayNameApi(),
				Integer.parseInt(orgId), practiceSapNumber, ccUser.getPracticeTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updatePracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updatePracticeApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit status inactive enterprise below active system & verify response")
	public void testChangeStatusInactiveEnterpriseBelowActiveSystem() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new active system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
				ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();
		logStep("Create a new active enterprise below active system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				Integer.parseInt(orgId), ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 200);
		orgId = createEnterpriseApiResponse.jsonPath().get("id").toString();

		logStep("Update an existing enterprise below active system.");
		String enterpriseSapNumber = ccUser.getEnterpriseSapNumberApi().concat(CCUtils.generateRandomString());
		updateEnterpriseApiResponse = orgInfo.updateEnterprise(ccUser.getOrgActive(),
				ccUser.getEnterpriseDisplayNameApi(), Integer.parseInt(orgId), enterpriseSapNumber,
				ccUser.getEnterpriseTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateEnterpriseApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateEnterpriseApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit status NGE practice below active enterprise & verify response")
	public void testChangeStatusNgePracticeInactiveBelowActieEnterprise() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new active system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
				ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();
		logStep("Create a new active enterprise below active system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				Integer.parseInt(orgId), ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 200);
		orgId = createEnterpriseApiResponse.jsonPath().get("id").toString();
		logStep("Create a new active practice below active enterprise.");
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				Integer.parseInt(orgId), ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		orgId = createPracticeApiResponse.jsonPath().get("id").toString();

		logStep("Update an existing practice below active enterprise.");
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		updatePracticeApiResponse = orgInfo.updatePractice(ccUser.getOrgActive(), ccUser.getPracticeDisplayNameApi(),
				Integer.parseInt(orgId), practiceSapNumber, ccUser.getPracticeTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updatePracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updatePracticeApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit inactive system below active client & verify response")
	public void testChangeStatusInactiveSystemBelowActiveClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new active system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
				ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();

		logStep("Update an existing system below active client.");
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		updateSystemApiResponse = orgInfo.updateSystem(ccUser.getOrgActive(), ccUser.getSystemDisplayNameApi(),
				Integer.parseInt(orgId), systemSapNumber, ccUser.getSystemTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateSystemApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit inherit functionality for enterprise node & verify response")
	public void testInheritanceFunctionalityEditAtEnterpriseNode() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new active system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
				ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();
		logStep("Create a new enterprise below active system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(), null,
				ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(), Integer.parseInt(orgId),
				ccUser.getEnterpriseParentTypeApi(), " ", null);

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 200);
		orgId = createEnterpriseApiResponse.jsonPath().get("id").toString();

		logStep("Update an existing enterprise below active system.");
		updateEnterpriseApiResponse = orgInfo.updateEnterprise(ccUser.getEnterpriseActiveApi(), null,
				Integer.parseInt(orgId), " ", null);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateEnterpriseApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateEnterpriseApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit inherit functionality for practice node & verify response")
	public void testInheritanceFunctionalityEditAtPracticeNode() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new active system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
				ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();
		logStep("Create a new active enterprise below active system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				Integer.parseInt(orgId), ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 200);
		orgId = createEnterpriseApiResponse.jsonPath().get("id").toString();
		logStep("Create a new active practice below active enterprise.");
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(), null,
				ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(), Integer.parseInt(orgId),
				ccUser.getPracticeParentTypeApi(), " ", null);

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		orgId = createPracticeApiResponse.jsonPath().get("id").toString();

		logStep("Update an existing practice below active system.");
		updatePracticeApiResponse = orgInfo.updatePractice(ccUser.getPracticeActiveApi(), null, Integer.parseInt(orgId),
				" ", null);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updatePracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updatePracticeApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit inherit functionality for system node & verify response")
	public void testInheritanceFunctionalityEditAtSystemNode() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new active system below inactive client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), null, ccUser.getSystemNameApi(),
				ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId), ccUser.getParentTypeApi(), " ", null);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();

		logStep("Update an existing system below active client.");
		updateSystemApiResponse = orgInfo.updateSystem(ccUser.getSystemeActiveApi(), null, Integer.parseInt(orgId), " ",
				null);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateSystemApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit system for verifying maximum character & verify response")
	public void testEditForMaximumCharacterAndFreeTextFieldErrorResponsAtSystem() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new active system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
				ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();

		logStep("Update an existing system below active client.");
		updateSystemApiResponse = orgInfo.updateSystem(ccUser.getOrgActive(),
				"WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS1",
				Integer.parseInt(orgId), "WTUWTXBN242937645@$$q$& (**&*^bcgd64SVFSFVVNWUVJ8QH",
				ccUser.getSystemTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateSystemApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyMaximumCharacterAndFreeTextFieldErrorResponsForOrgNode(updateSystemApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit enterprise for verifying maximum character & verify response")
	public void testEditForMaximumCharacterAndFreeTextFieldErrorResponsAtEnterprise() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new active system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
				ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();
		logStep("Create a new active enterprise below active system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				Integer.parseInt(orgId), ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 200);
		orgId = createEnterpriseApiResponse.jsonPath().get("id").toString();

		logStep("Update an existing enterprise below active system.");
		updateEnterpriseApiResponse = orgInfo.updateEnterprise(ccUser.getOrgActive(),
				"WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS1",
				Integer.parseInt(orgId), "WTUWTXBN242937645@$$q$& (**&*^bcgd64SVFSFVVNWUVJ8QH",
				ccUser.getEnterpriseTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateEnterpriseApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateEnterpriseApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyMaximumCharacterAndFreeTextFieldErrorResponsForOrgNode(updateEnterpriseApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create & edit practice for verifying maximum character & verify response")
	public void testEditForMaximumCharacterAndFreeTextFieldErrorResponsAtPractice() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new active system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
				ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();
		logStep("Create a new active enterprise below active system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				Integer.parseInt(orgId), ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 200);
		orgId = createEnterpriseApiResponse.jsonPath().get("id").toString();
		logStep("Create a new active practice below active enterprise.");
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				Integer.parseInt(orgId), ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		orgId = createPracticeApiResponse.jsonPath().get("id").toString();

		logStep("Update an existing practice below active enterprise.");
		updatePracticeApiResponse = orgInfo.updatePractice(ccUser.getOrgActive(),
				"WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS1",
				Integer.parseInt(orgId), "WTUWTXBN242937645@$$q$& (**&*^bcgd64SVFSFVVNWUVJ8QH",
				ccUser.getPracticeTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updatePracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updatePracticeApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyMaximumCharacterAndFreeTextFieldErrorResponsForOrgNode(updatePracticeApiResponse);
	}

}
