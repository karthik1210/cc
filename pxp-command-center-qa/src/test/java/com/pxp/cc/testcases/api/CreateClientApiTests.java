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

public class CreateClientApiTests extends BaseRest {

	private ClientInfo clientInfo;
	private Validations validation;
	Response createClientApiResponse, fetchClientDetailsApiResponse;
	List<Integer> solutionList = null;

	@BeforeTest()
	public void setBaseUri() throws Exception {

		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new client and fetch newly created client details using client id")
	public void testCreateNewClient() throws IOException {
		try {
			String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
			String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());

			createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
					clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(),
					ccUser.getTimezoneId());
			logStep("Validating Response Time");
			CCUtils.responseTimeValidation(createClientApiResponse);
			logStep("Validating Response Code");
			CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 200);
			logStep("Validating Response Body");
			validation.verifyCreateClientResponseBody(createClientApiResponse, clientName);
			String clientId = createClientApiResponse.jsonPath().get("id").toString();
			fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
			logStep("Validating Response Time");
			CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
			logStep("Validating Response Code");
			CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
			logStep("Validating Response Body");
			validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
					ccUser.getClientType(), clientSapNumber, ccUser.getActive(), ccUser.getTestClient(),
					ccUser.getTimezoneId(), ccUser.getDisplayName(), ccUser.getPracticeCount(), false, solutionList);
		} catch (Exception e) {
			log("Error : {}", e.getMessage());
		}

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value in mandatory fields(active,client name,sap nummber, and type) and validate error message.")
	public void testErrorMessagesForMandatoryFields() throws IOException {
		try {
			createClientApiResponse = clientInfo.createNewClientUpdateClient(null, ccUser.getClientId(), null, null,
					ccUser.getTestClient(), null, ccUser.getTimezoneId());
			logStep("Validating Response Time");
			CCUtils.responseTimeValidation(createClientApiResponse);
			logStep("Validating Response Code");
			CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 400);
			logStep("Validating Response Body");
			validation.verifyNullErrorMessageResponseBody(createClientApiResponse);
		} catch (Exception e) {
			log("Error : {}", e.getMessage());
		}
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter already existing value in client name field and sap number field and validate error message.")
	public void testErrorResponseForClientNameAndSapNumberField() throws IOException {
		try {
			createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
					ccUser.getClientName(), ccUser.getClientSapNumber(), ccUser.getTestClient(), ccUser.getClientType(),
					ccUser.getTimezoneId());
			logStep("Validating Response Time");
			CCUtils.responseTimeValidation(createClientApiResponse);
			logStep("Validating Response Code");
			CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 400);
			logStep("Validating Response Body");
			validation.verifyErrorMessageInResponseForClientNameField(createClientApiResponse);
			String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
			createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
					clientName, ccUser.getClientSapNumber(), ccUser.getTestClient(), ccUser.getClientType(),
					ccUser.getTimezoneId());
			logStep("Validating Response Time");
			CCUtils.responseTimeValidation(createClientApiResponse);
			logStep("Validating Response Code");
			CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 400);
			logStep("Validating Response Body");
			validation.verifyErrorMessageInResponseForSapNumberField(createClientApiResponse);
		} catch (Exception e) {
			log("Error : {}", e.getMessage());
		}

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing client type and validate error message.")
	public void testErrorResponseForTypeField() throws IOException {
		try {
			String clientType = ccUser.getClientType().concat(CCUtils.generateRandomString());
			String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
			String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
			createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
					clientName, clientSapNumber, ccUser.getTestClient(), clientType, ccUser.getTimezoneId());
			logStep("Validating Response Time");
			CCUtils.responseTimeValidation(createClientApiResponse);
			logStep("Validating Response Code");
			CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 400);
			logStep("Validating Response Body");
			validation.verifyErrorMessageInResponseForClientTypeField(createClientApiResponse, clientType);
		} catch (Exception e) {
			log("Error : {}", e.getMessage());
		}

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing time zone id and validate error message.")
	public void testErrorResponseForTimezoneField() throws IOException {
		try {
			String clientTimezoneId = ccUser.getTimezoneId().concat(CCUtils.generateRandomString());
			String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
			String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
			createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
					clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), clientTimezoneId);
			logStep("Validating Response Time");
			CCUtils.responseTimeValidation(createClientApiResponse);
			logStep("Validating Response Code");
			CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 400);
			logStep("Validating Response Body");
			validation.verifyErrorMessageInResponseForClientTimezoneIdField(createClientApiResponse, clientTimezoneId);
		} catch (Exception e) {
			log("Error : {}", e.getMessage());
		}

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 50 characters in sap id field and validate error message")
	public void testMaximumCharacterErrorResponseForSapNumberField() throws IOException {
		try {
			String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
			createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
					clientName, ccUser.getInvalidClientSapNumber(), ccUser.getTestClient(), ccUser.getClientType(),
					ccUser.getTimezoneId());
			logStep("Validating Response Time");
			CCUtils.responseTimeValidation(createClientApiResponse);
			logStep("Validating Response Code");
			CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 400);
			logStep("Validating Response Body");
			validation.verifyErrorMessageInResponseForClientSapNumberField(createClientApiResponse);
		} catch (Exception e) {
			log("Error : {}", e.getMessage());
		}

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in client name field and validate error message.")
	public void testMaximumCharacterErrorResponseForClientNameField() throws IOException {
		try {
			String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
			createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
					ccUser.getInvalidClientName(), clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(),
					ccUser.getTimezoneId());
			logStep("Validating Response Time");
			CCUtils.responseTimeValidation(createClientApiResponse);
			logStep("Validating Response Code");
			CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 400);
			logStep("Validating Response Body");
			validation.verifyMaximumCharacterErrorMessageInResponseForClientNameField(createClientApiResponse);
		} catch (Exception e) {
			log("Error : {}", e.getMessage());
		}
	}

}
