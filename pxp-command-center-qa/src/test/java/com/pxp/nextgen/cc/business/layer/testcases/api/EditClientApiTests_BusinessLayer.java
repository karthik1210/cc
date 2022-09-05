// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class EditClientApiTests_BusinessLayer extends BaseRest {

	private ClientInfo clientInfo;
	private Validations validation;
	Response createClientApiResponse, fetchClientDetailsApiResponse, updateClientApiResponse;
	List<Map<String, Object>> solutionLists = new ArrayList<Map<String, Object>>();
	String clientName, clientSapNumber, newClientId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		validation = new Validations();
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(testData.getProperty("api.client.type")),
				ccUser.getTimezoneId());
		newClientId = createClientApiResponse.jsonPath().get("clientId").toString();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client name,sapnumber,clien type,timezone and fetch updated client details using client id")
	public void testEditClientName() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(testData.getProperty("api.client.type")),
				ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("clientId").toString();
		String newClientName = clientName.concat(CCUtils.generateRandomString());
		String newSapNumber = clientSapNumber.concat(CCUtils.generateRandomString());
		logStep("Edit non-existing client name of created client");
		updateClientApiResponse = clientInfo.updateClient(Boolean.parseBoolean(ccUser.getActive()), clientId,
				newClientName, newSapNumber, ccUser.getTestClient(),
				Integer.parseInt(testData.getProperty("client.type.tsi.id")), "Asia/Kuwait");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 200);
		validation.verifyCreateAndUpdateClientResponseBody(updateClientApiResponse, newClientName);
		logStep("Fetch updated client details using clientid");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Verify updated client details");
		validation.verifySearchClientIdResponseBody(fetchClientDetailsApiResponse, clientId, newClientName, "TSI",
				newSapNumber, ccUser.getActive(), ccUser.getTestClient(), "Asia/Kuwait", "Kuwait, Riyadh", 0,
				solutionLists);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client status from active to inactive anf inactive to active and fetch updated client details using client id")
	public void testEditClientStatusFromActiveToInactive() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new active client");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(testData.getProperty("api.client.type")),
				ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("clientId").toString();
		logStep("Update client status from active to inactive");
		updateClientApiResponse = clientInfo.updateClient(Boolean.parseBoolean("false"), clientId, clientName,
				clientSapNumber, ccUser.getTestClient(), Integer.parseInt(testData.getProperty("api.client.type")),
				ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 200);
		validation.verifyCreateAndUpdateClientResponseBody(updateClientApiResponse, clientName);
		logStep("Fetch updated client details using clientid");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Verify Client Status is changed from active to inactive");
		validation.verifySearchClientIdResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, "false", ccUser.getTestClient(), ccUser.getTimezoneId(),
				"Eastern Standard Time", 0, solutionLists);
		logStep("Update client status from inactive to active");
		updateClientApiResponse = clientInfo.updateClient(Boolean.parseBoolean("true"), clientId, clientName,
				clientSapNumber, ccUser.getTestClient(), Integer.parseInt(testData.getProperty("api.client.type")),
				ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 200);
		validation.verifyCreateAndUpdateClientResponseBody(updateClientApiResponse, clientName);
		logStep("Fetch updated client details using clientid");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Verify Client Status is changed from inactive to active");
		validation.verifySearchClientIdResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				testData.getProperty("api.client.one.type"), clientSapNumber, "true", ccUser.getTestClient(),
				ccUser.getTimezoneId(), "Eastern Standard Time", 0, solutionLists);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify status code when editing client details with invalid url")
	public void testEditClientDetailsWithInvalidUrl() throws IOException {
		logStep("Update client status from active to inactive");
		updateClientApiResponse = clientInfo.updateClientWithInvalidUrl(Boolean.parseBoolean("false"), newClientId,
				clientName, clientSapNumber, ccUser.getTestClient(),
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		logStep("Validating status code should be 404");
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 404);
		logStep("Validating response body error message should be 'Not found'");
		validation.verifyErrorInResponseForInvalidUrl(updateClientApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify status code and message when hitting update client details without client id")
	public void testErrorWhileEditingClientWithoutClientId() throws IOException {
		logStep("Update all key values of client details to null");
		updateClientApiResponse = clientInfo.updateClient(Boolean.parseBoolean(null), "", "", "", "", 0, "");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		logStep("Validating status code should be 405");
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 405);
		validation.verifyResponseBodyUpdatingClientWithOutClientIdAndAllValuesNull(updateClientApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify status code and message when all key values are null while updating client details")
	public void testEditClientDetailsWhenAllKeyValueAreNull() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new inactive client");
		createClientApiResponse = clientInfo.createNewClient("false", clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(testData.getProperty("api.client.type")),
				ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("clientId").toString();
		logStep("Update client status from inactive to active");
		updateClientApiResponse = clientInfo.updateClient(Boolean.parseBoolean(null), clientId, "", "", "", 0, "");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		logStep("Validating status code should be 400");
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		validation.verifyNullErrorMessageResponseBody(updateClientApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client and enter more than 50 characters in sap number field ,enter more than 100 characters in client name field, invalid client type,invalid client timezone id  and verify error message for sap number field")
	public void testMaximumCharacterErrorResponseForClientSapNumberField() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(testData.getProperty("api.client.type")),
				ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("clientId").toString();
		logStep("Edit client and enter more than 50 characters in sap id field");
		updateClientApiResponse = clientInfo.updateClient(Boolean.parseBoolean(ccUser.getActive()), clientId,
				clientName, ccUser.getInvalidClientSapNumber(), ccUser.getTestClient(),
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientSapNumberField(updateClientApiResponse);
		logStep("Edit client and enter more than 100 characters in client name field");
		updateClientApiResponse = clientInfo.updateClient(Boolean.parseBoolean(ccUser.getActive()), clientId,
				ccUser.getInvalidClientName(), clientSapNumber, ccUser.getTestClient(),
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		validation.verifyMaximumCharacterErrorMessageInResponseForClientNameField(updateClientApiResponse);
		logStep("Edit client and enter invalid client type");
		int invalidClientType = Integer.parseInt(testData.getProperty("invalid.client.type"));
		updateClientApiResponse = clientInfo.updateClient(Boolean.parseBoolean(ccUser.getActive()), clientId,
				clientName, clientSapNumber, ccUser.getTestClient(), invalidClientType, ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientTypeField(updateClientApiResponse, invalidClientType);
		String invalidClientTimezoneId = ccUser.getTimezoneId().concat(CCUtils.generateRandomString());
		updateClientApiResponse = clientInfo.updateClient(Boolean.parseBoolean(ccUser.getActive()), clientId,
				clientName, clientSapNumber, ccUser.getTestClient(),
				Integer.parseInt(testData.getProperty("api.client.type")), invalidClientTimezoneId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientTimezoneIdField(updateClientApiResponse,
				invalidClientTimezoneId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client and enter existing client name and verify error message for client name field")
	public void testUpdateExistingClientNameInClientNameField() throws IOException {
		String newClientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), newClientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(testData.getProperty("api.client.type")),
				ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("clientId").toString();
		logStep("Edit client and enter existing client name and verify error message for client name field");
		updateClientApiResponse = clientInfo.updateClient(Boolean.parseBoolean(ccUser.getActive()), clientId,
				clientName, clientSapNumber, ccUser.getTestClient(),
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientName(updateClientApiResponse, clientName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client and enter existing client sap number and verify error message for sap number field")
	public void testUpdateExistingSapNumberInClientSapNumberField() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String newSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, newSapNumber,
				ccUser.getTestClient(), Integer.parseInt(testData.getProperty("api.client.type")),
				ccUser.getTimezoneId());
		String clientId = createClientApiResponse.jsonPath().get("clientId").toString();
		logStep("Edit client and enter existing client sap number and verify error message for sap number field");
		updateClientApiResponse = clientInfo.updateClient(Boolean.parseBoolean(ccUser.getActive()), clientId,
				clientName, clientSapNumber, ccUser.getTestClient(),
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientSapNumber(updateClientApiResponse, clientSapNumber);
	}

	/*
	 * Enable test case once this issue is addressed for Jira#PXPP-20903
	 * 
	 * @Test(retryAnalyzer = RetryAnalyzer.class, description =
	 * "Edit client and enter null values in mandatory field and verify error message"
	 * ) public void testUpdateNullDataInMandatoryFields() throws IOException {
	 * logStep("Edit client and enter null values in mandatory field");
	 * updateClientApiResponse = clientInfo.updateClient(null, newClientId, null,
	 * null, ccUser.getTestClient(), null, null);
	 * logStep("Validating response information");
	 * CCUtils.responseTimeValidation(updateClientApiResponse);
	 * CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
	 * validation.verifyNullErrorMessageResponseBody(updateClientApiResponse); }
	 */

}