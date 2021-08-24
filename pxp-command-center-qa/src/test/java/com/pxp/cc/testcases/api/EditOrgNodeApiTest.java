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
	Response createClientApiResponse, createSystemApiResponse, updateSystemApiResponse, fetchSystemDetailsApiResponse,
			updateEnterpriseApiResponse, fetchClientDetailsApiResponse, fetchEnterpriseDetailsApiResponse, updatePracticeApiResponse,
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
		validation.verifySystemResponseBody(updateSystemApiResponse, systemName);
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
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseOrgId(), enterpriseSapNumber,ccUser.getEnterpriseTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateEnterpriseApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateEnterpriseApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyEnterpriseResponseBody(updateEnterpriseApiResponse, enterpriseName);
		String orgId = updateEnterpriseApiResponse.jsonPath().get("id").toString();
		fetchEnterpriseDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(fetchEnterpriseDetailsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(fetchEnterpriseDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");

		validation.verifySearchUpdatedEnterpriseResponseBody(fetchEnterpriseDetailsApiResponse, orgId,
				ccUser.getEnterpriseDisplayNameApi(), enterpriseSapNumber, ccUser.getEnterpriseActiveApi(), ccUser.getEnterpriseTimezoneIdApi());
	}	
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit an existing practice details and fetch updated practice details using practice id")
	public void testEditOrgPracticeNode() throws IOException {
		String practiceName = ccUser.getPracticeNameApi();
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		updatePracticeApiResponse = orgInfo.updatePractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(),ccUser.getPracticeOrgId(), practiceSapNumber,ccUser.getPracticeTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updatePracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updatePracticeApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyPracticeResponseBody(updatePracticeApiResponse, practiceName);
		String orgId = updatePracticeApiResponse.jsonPath().get("id").toString();
		fetchPracticeDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(fetchPracticeDetailsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(fetchPracticeDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");

		validation.verifySearchUpdatedPracticeResponseBody(fetchPracticeDetailsApiResponse, orgId,
				ccUser.getPracticeDisplayNameApi(), practiceSapNumber, ccUser.getPracticeActiveApi(), ccUser.getPracticeTimezoneIdApi());
	}	
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit an existing inactive system status to active under inactive client & verify response")
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
		validation.verifyCreateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();
		
		 logStep("Create a new inactive system below inactive client.");
	        createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
	                ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
	                ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
	        logStep("Validating Response Time");
	        CCUtils.responseTimeValidation(createSystemApiResponse);
	        logStep("Validating Response Code");
	        CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
	        String orgId = createSystemApiResponse.jsonPath().get("id").toString();
	        
	        logStep("Create a new active practice below inactive client.");
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		updatePracticeApiResponse = orgInfo.updatePractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(),Integer.parseInt(orgId), practiceSapNumber,ccUser.getPracticeTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updatePracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updatePracticeApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyInactiveClientErrorResponsWhileAddingActivePractice(updatePracticeApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit an existing inactive system status to active under inactive client & verify response")
	public void testEditActiveEnterpriseBelowInactiveSystem() throws IOException {		
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getOrgActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();
		
		 logStep("Create a new inactive system below inactive client.");
	        createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
	                ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
	                ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
	        logStep("Validating Response Time");
	        CCUtils.responseTimeValidation(createSystemApiResponse);
	        logStep("Validating Response Code");
	        CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
	        String orgId = createSystemApiResponse.jsonPath().get("id").toString();

}
