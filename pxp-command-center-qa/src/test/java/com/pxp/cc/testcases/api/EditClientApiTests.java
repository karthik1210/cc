// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import java.util.List;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class EditClientApiTests extends BaseRest {

	private ClientInfo clientInfo;
	private Validations validation;
	Response createClientApiResponse, fetchClientDetailsApiResponse, updateClientApiResponse;
	List<Integer> solutionList = null;

	@BeforeTest()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client sapnumber and fetch updated client details using client id")
	public void testEditClientSapNumber() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		String newSapNumber = clientSapNumber.concat(CCUtils.generateRandomString());
		logStep("Edit client sapnumber");
		updateClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), clientId, clientName,
				newSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(updateClientApiResponse, clientName);

		logStep("Verify updated client details");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), newSapNumber, ccUser.getActive(), ccUser.getTestClient(),
				ccUser.getTimezoneId(), ccUser.getDisplayName(), ccUser.getPracticeCount(), false, solutionList);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client type and fetch updated client details using client id")
	public void testEditClientType() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Edit client type");
		updateClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), clientId, clientName,
				clientSapNumber, ccUser.getTestClient(), "TSI", ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(updateClientApiResponse, clientName);

		logStep("Verify updated client details using client id");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName, "TSI",
				clientSapNumber, ccUser.getActive(), ccUser.getTestClient(), ccUser.getTimezoneId(),
				ccUser.getDisplayName(), ccUser.getPracticeCount(), false, solutionList);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client timezone and fetch updated client details using client id")
	public void testEditClientTimezone() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Edit client timezone");
		updateClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), clientId, clientName,
				clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), "Asia/Kuwait");
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(updateClientApiResponse, clientName);

		logStep("Verify updated client details using client id");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, ccUser.getActive(), ccUser.getTestClient(), "Asia/Kuwait",
				"Kuwait, Riyadh", ccUser.getPracticeCount(), false, solutionList);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client status from active to inactive and fetch updated client details using client id")
	public void testEditClientStatusFromActiveToInactive() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new active client");
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Update client status from active to inactive");
		updateClientApiResponse = clientInfo.createNewClientUpdateClient("false", clientId, clientName, clientSapNumber,
				ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(updateClientApiResponse, clientName);

		logStep("Verify Client Status is changed from active to inactive");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, "false", ccUser.getTestClient(), ccUser.getTimezoneId(),
				ccUser.getDisplayName(), ccUser.getPracticeCount(), false, solutionList);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client status from inactive to active and fetch updated client details using client id")
	public void testEditClientStatusFromInactiveToActive() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new inactive client");
		createClientApiResponse = clientInfo.createNewClientUpdateClient("false", ccUser.getClientId(), clientName,
				clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Update client status from inactive to active");
		updateClientApiResponse = clientInfo.createNewClientUpdateClient("true", clientId, clientName, clientSapNumber,
				ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(updateClientApiResponse, clientName);

		logStep("Verify Client Status is changed from inactive to active");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, "true", ccUser.getTestClient(), ccUser.getTimezoneId(),
				ccUser.getDisplayName(), ccUser.getPracticeCount(), false, solutionList);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client and enter null value in timezone field and fetch updated client details using client id")
	public void testUpdateNullDataInTimezoneField() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClientUpdateClient("false", ccUser.getClientId(), clientName,
				clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Edit client and enter null value in timezone field");
		updateClientApiResponse = clientInfo.createNewClientUpdateClient("true", clientId, clientName, clientSapNumber,
				ccUser.getTestClient(), ccUser.getClientType(), null);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(updateClientApiResponse, clientName);

		logStep("Verify updated client details using client id");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				ccUser.getClientType(), clientSapNumber, "true", ccUser.getTestClient(), null, null,
				ccUser.getPracticeCount(), false, solutionList);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client and enter more than 50 characters in sap number field and verify error message for sap number field")
	public void testMaximumCharacterErrorResponseForClientSapNumberField() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Edit client and enter more than 50 characters in sap id field");
		updateClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), clientId, clientName,
				ccUser.getInvalidClientSapNumber(), ccUser.getTestClient(), ccUser.getClientType(),
				ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyErrorMessageInResponseForClientSapNumberField(updateClientApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client and enter more than 100 characters in client name field and verify error message for client name field")
	public void testMaximumCharacterErrorResponseForClientNameField() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Edit client and enter more than 100 characters in client name field");
		updateClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), clientId,
				ccUser.getInvalidClientName(), clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(),
				ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyMaximumCharacterErrorMessageInResponseForClientNameField(updateClientApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client and enter invalid client type and verify error message for client type field.")
	public void testUpdateInvalidClientTypeInClientTypeField() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Edit client and enter invalid client type");
		String invalidClientType = ccUser.getClientType().concat(CCUtils.generateRandomString());
		updateClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), clientId, clientName,
				clientSapNumber, ccUser.getTestClient(), invalidClientType, ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyErrorMessageInResponseForClientTypeField(updateClientApiResponse, invalidClientType);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client and enter invalid client timezone id and verify error message for client timezone field.")
	public void testUpdateInvalidTimezoneIdInTimezoneField() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Edit client and enter invalid client timezone id");
		String invalidClientTimezoneId = ccUser.getTimezoneId().concat(CCUtils.generateRandomString());
		updateClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), clientId, clientName,
				clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), invalidClientTimezoneId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyErrorMessageInResponseForClientTimezoneIdField(updateClientApiResponse,
				invalidClientTimezoneId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client and enter existing client name and verify error message for client name field")
	public void testUpdateExistingClientNameInClientNameField() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Edit client and enter existing client name and verify error message for client name field");
		updateClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), clientId,
				ccUser.getClientName(), clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(),
				ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyErrorMessageInResponseForClientNameField(updateClientApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client and enter existing client sap number and verify error message for sap number field")
	public void testUpdateExistingSapNumberInClientSapNumberField() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Edit client and enter existing client sap number and verify error message for sap number field");
		updateClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), clientId, clientName,
				ccUser.getClientSapNumber(), ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyErrorMessageInResponseForSapNumberField(updateClientApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client and enter null values in mandatory field and verify error message")
	public void testUpdateNullDataInMandatoryFields() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("id").toString();

		logStep("Edit client and enter null values in mandatory field");
		updateClientApiResponse = clientInfo.createNewClientUpdateClient(null, clientId, null, null,
				ccUser.getTestClient(), null, ccUser.getTimezoneId());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyNullErrorMessageResponseBody(updateClientApiResponse);
	}
}
