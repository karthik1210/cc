// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class UpdateClientStatusApiTests extends BaseRest {

	private ClientInfo clientInfo;
	private Validations validation;
	private OrgInfo orgInfo;
	Response createClientApiResponse, fetchClientDetailsApiResponse, createSystemApiResponse,
			updateClientStatusApiResponse, fetchSystemDetailsApiResponse;
	List<Integer> solutionList = null;

	@BeforeMethod()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to change client status from active to inactive")
	public void testUpdateClientNodeStatusFromActiveToInactive() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());

		logStep("Create a new active Client");
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Fetch newly created client details using client id");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, ccUser.getActive(), ccUser.getTestClient(),
				ccUser.getTimezoneId(), ccUser.getDisplayName(), ccUser.getPracticeCount(), false, solutionList);

		logStep("Update client status from active to inactive");
		updateClientStatusApiResponse = clientInfo.patchClientStatus(clientId, "false");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientStatusApiResponse);
		CCUtils.assertEquals(updateClientStatusApiResponse.getStatusCode(), 200);

		logStep("Verify Client Status is changed from active to inactive");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, "false", ccUser.getTestClient(), ccUser.getTimezoneId(),
				ccUser.getDisplayName(), ccUser.getPracticeCount(), false, solutionList);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to change client status from inactive to active")
	public void testUpdateClientNodeStatusFromInativeToActive() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());

		logStep("Create a new inactive Client");
		createClientApiResponse = clientInfo.createNewClientUpdateClient("false", ccUser.getClientId(), clientName,
				clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Fetch newly created client details using client id");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, "false", ccUser.getTestClient(), ccUser.getTimezoneId(),
				ccUser.getDisplayName(), ccUser.getPracticeCount(), false, solutionList);

		logStep("Update client status from inactive to active");
		updateClientStatusApiResponse = clientInfo.patchClientStatus(clientId, "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientStatusApiResponse);
		CCUtils.assertEquals(updateClientStatusApiResponse.getStatusCode(), 200);

		logStep("Verify Client Status is changed from active to inactive");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, "true", ccUser.getTestClient(), ccUser.getTimezoneId(),
				ccUser.getDisplayName(), ccUser.getPracticeCount(), false, solutionList);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify after changing client node status from active to inactive, subsequent child nodes also become inactive")
	public void testChildNodeStatusWhenClientNodeStatusChangedToInactive() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new active Client");
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new active system below active client.");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
				ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();

		logStep("Update client status from active to inactive");
		updateClientStatusApiResponse = clientInfo.patchClientStatus(clientId, "false");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientStatusApiResponse);
		CCUtils.assertEquals(updateClientStatusApiResponse.getStatusCode(), 200);

		logStep("Verify Client Status is changed from active to inactive");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, "false", ccUser.getTestClient(), ccUser.getTimezoneId(),
				ccUser.getDisplayName(), ccUser.getPracticeCount(), false, solutionList);

		logStep("Verify after updating client node status from active to inactive, subsequent child node also become inactive");
		fetchSystemDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSystemDetailsApiResponse);
		CCUtils.assertEquals(fetchSystemDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchSystemResponseBody(fetchSystemDetailsApiResponse, orgId, ccUser.getSystemNameApi(),
				ccUser.getSystemOrgTypeApi(), ccUser.getClientType(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemSapNumberApi(), "false", ccUser.getSystemTestClientApi(),
				ccUser.getSystemTimezoneIdApi(), ccUser.getSystemTimezoneDisplayNameApi(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify after updating client node status from inactive to active, only client node status changed to active and subsequent child nodes remains inactive")
	public void testChildNodeStatusWhenClientNodeStatusChangedToActive() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new inactive Client");
		createClientApiResponse = clientInfo.createNewClientUpdateClient("false", ccUser.getClientId(), clientName,
				clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Create a new inactive system below inactive client.");
		createSystemApiResponse = orgInfo.createSystem("false", ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), Integer.parseInt(clientId),
				ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();

		logStep("Update client status from inactive to active");
		updateClientStatusApiResponse = clientInfo.patchClientStatus(clientId, "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientStatusApiResponse);
		CCUtils.assertEquals(updateClientStatusApiResponse.getStatusCode(), 200);

		logStep("Verify Client Status is changed from inactive to active");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, "true", ccUser.getTestClient(), ccUser.getTimezoneId(),
				ccUser.getDisplayName(), ccUser.getPracticeCount(), false, solutionList);

		logStep("Verify after updating client node status from inactive to active, only client node status changed to active and subsequent child nodes remains inactive");
		fetchSystemDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSystemDetailsApiResponse);
		CCUtils.assertEquals(fetchSystemDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchSystemResponseBody(fetchSystemDetailsApiResponse, orgId, ccUser.getSystemNameApi(),
				ccUser.getSystemOrgTypeApi(), ccUser.getClientType(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemSapNumberApi(), "false", ccUser.getSystemTestClientApi(),
				ccUser.getSystemTimezoneIdApi(), ccUser.getSystemTimezoneDisplayNameApi(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing client id and verify error message is displayed for client id field")
	public void testErrorMessageForInvalidClientId() throws IOException {
		logStep("Enter non existing client id");
		updateClientStatusApiResponse = clientInfo.patchClientStatus(ccUser.getNonExistingClientId(), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientStatusApiResponse);
		CCUtils.assertEquals(updateClientStatusApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForClientIdField(updateClientStatusApiResponse);

	}
}
