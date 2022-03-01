// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

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

public class GetOrgHierarchyControllerApiTests extends BaseRest {

	private ClientInfo clientInfo;
	private Validations validation;
	Response createClientApiResponse, getOrgHierarchyControllerApiResponse, createSystemApiResponse,
			createEnterpriseApiResponse, createPracticeApiResponse;
	private OrgInfo orgInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org hierarchy details including client data")
	public void getOrgHierarchyWithClient() throws IOException {
		logStep("Create a new client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("id").toString();
		logStep("Create a new active system.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
				ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();
		logStep("Create a new active enterprise below active system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				Integer.parseInt(orgId), ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());
		orgId = createEnterpriseApiResponse.jsonPath().get("id").toString();
		logStep("Create a new active practice below active enterprise.");
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				Integer.parseInt(orgId), ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());

		logStep("Verify response when client data exclude option is selected 'false' from fetch org hierarchy controller.");
		getOrgHierarchyControllerApiResponse = clientInfo.getOrgHierarchyWithClientNode(clientId, false);
		CCUtils.responseTimeValidation(getOrgHierarchyControllerApiResponse);
		CCUtils.assertEquals(getOrgHierarchyControllerApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org hierarchy details excluding client data")
	public void getOrgHierarchyWithoutClient() throws IOException {
		logStep("Create a new client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("id").toString();
		logStep("Create a new active system.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
				ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();
		logStep("Create a new active enterprise below active system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				Integer.parseInt(orgId), ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());
		orgId = createEnterpriseApiResponse.jsonPath().get("id").toString();
		logStep("Create a new active practice below active enterprise.");
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				Integer.parseInt(orgId), ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());

		logStep("Verify response when client data exclude option is selected 'true' from fetch org hierarchy controller.");
		getOrgHierarchyControllerApiResponse = clientInfo.getOrgHierarchyWithClientNode(clientId, true);
		CCUtils.responseTimeValidation(getOrgHierarchyControllerApiResponse);
		CCUtils.assertEquals(getOrgHierarchyControllerApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org hierarchy details when client id is null")
	public void testErrorMessageIfClientIdIsNull() throws IOException {
		logStep("Create a new client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());

		logStep("Test to Verify response when client id is null.");
		getOrgHierarchyControllerApiResponse = clientInfo.getOrgHierarchyWithClientNode(null, true);
		CCUtils.responseTimeValidation(getOrgHierarchyControllerApiResponse);
		CCUtils.assertEquals(getOrgHierarchyControllerApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageIfClientIdIsNull(getOrgHierarchyControllerApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify response when client data exclude option is not selected")
	public void getOrgHierarchyExcludeOptionSelectedNullForClient() throws IOException {
		logStep("Create a new client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Test to Verify response when exclude option is not selected.");
		getOrgHierarchyControllerApiResponse = clientInfo.getOrgHierarchyWithClientNode(clientId, null);
		CCUtils.responseTimeValidation(getOrgHierarchyControllerApiResponse);
		CCUtils.assertEquals(getOrgHierarchyControllerApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org hierarchy details with invalid client id")
	public void getOrgHierarchyWithInvalidClientId() throws IOException {
		logStep("Verify response when user entered org id instead of client id");
		getOrgHierarchyControllerApiResponse = clientInfo.getOrgHierarchyWithClientNode(ccUser.getNonExistingClientId(),
				false);
		CCUtils.responseTimeValidation(getOrgHierarchyControllerApiResponse);
		CCUtils.assertEquals(getOrgHierarchyControllerApiResponse.getStatusCode(), 204);
	}

}
