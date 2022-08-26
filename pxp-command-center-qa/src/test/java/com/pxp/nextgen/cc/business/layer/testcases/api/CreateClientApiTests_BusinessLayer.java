// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class CreateClientApiTests_BusinessLayer extends BaseRest {

	private ClientInfo clientInfo;
	private Validations validation;
	Response createClientApiResponse, fetchClientDetailsApiResponse;
	List<Integer> solutionList = null;
	String clientName, clientSapNumber;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 0, description = "Create a new active test client and fetch newly created client details using client id")
	public void testCreateNewActiveTestClient() throws IOException {
		logStep("Create client using valid details in client name, sap number, active, testclient, client type and timezone field");
		clientName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(78))));
		clientSapNumber = CCUtils
				.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.client.sapnumber")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(45))));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("clientId").toString();
		logStep("Fetch newly created client details using client id");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				testData.getProperty("api.client.type"), clientSapNumber, testData.getProperty("api.client.active"),
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.timezoneid"),
				testData.getProperty("api.client.displayname"),
				Integer.parseInt(testData.getProperty("api.client.practice.count")), false, solutionList);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value in mandatory fields(active,client name,sap nummber,timezone id and type) and validate error message")
	public void testErrorMessagesForMandatoryFields() throws IOException {
		logStep("Enter null value in mandatory fields(active,client name,sap nummber and type) and validate error message");
		createClientApiResponse = clientInfo.createNewClient(null, null, null,
				testData.getProperty("api.client.testclient"), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 400);
		validation.verifyNullErrorMessageResponseBody(createClientApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter already existing value in client name field and validate error message")
	public void testErrorResponseForClientNameField() throws IOException {
		logStep("Enter already existing value in client name field and validate error message");
		String clientSapNumberOne = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumberOne, testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.type"), testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientName(createClientApiResponse, clientName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter already existing value in sap number field and validate error message")
	public void testErrorResponseForSapNumberField() throws IOException {
		logStep("Enter already existing value in sap number field and validate error message");
		String clientNameOne = testData.getProperty("api.client.name").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientNameOne,
				clientSapNumber, testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientSapNumber(createClientApiResponse, clientSapNumber);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing client type and validate error message")
	public void testErrorResponseForTypeField() throws IOException {
		logStep("Enter non existing client type and validate error message");
		String clientType = testData.getProperty("api.client.type").concat(CCUtils.generateRandomString());
		String clientName = testData.getProperty("api.client.name").concat(CCUtils.generateRandomString());
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"), clientType,
				testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientTypeField(createClientApiResponse, clientType);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing time zone id and validate error message")
	public void testErrorResponseForTimezoneField() throws IOException {
		logStep("Enter non existing time zone id in timezone field and validate error message");
		String clientTimezoneId = testData.getProperty("api.client.timezoneid").concat(CCUtils.generateRandomString());
		String clientName = testData.getProperty("api.client.name").concat(CCUtils.generateRandomString());
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				clientTimezoneId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientTimezoneIdField(createClientApiResponse, clientTimezoneId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 50 characters in sap id field and validate error message")
	public void testMaximumCharacterErrorResponseForSapNumberField() throws IOException {
		logStep("Enter more than 50 characters in sap id field and validate error message");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(4));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				testData.getProperty("api.invalid.client.sapnumber"), testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.type"), testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientSapNumberField(createClientApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in client name field and validate error message")
	public void testMaximumCharacterErrorResponseForClientNameField() throws IOException {
		logStep("Enter more than 100 characters in client name field and validate error message");
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(4));
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"),
				testData.getProperty("api.invalid.client.name"), clientSapNumber,
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 400);
		validation.verifyMaximumCharacterErrorMessageInResponseForClientNameField(createClientApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank space in active, client name, sap nummber, timezone id and type fields and validate error message")
	public void testErrorMessagesForBlankFields() throws IOException {
		logStep("Enter blank space in active, client name, sap nummber, timezone id and type fields and validate error message");
		createClientApiResponse = clientInfo.createNewClient(" ", " ", " ",
				testData.getProperty("api.client.testclient"), " ", " ");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 400);
		validation.verifyNullErrorMessageResponseBody(createClientApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessagesForInvalidUrl() throws IOException {
		logStep("Enter invalid url and validate error status code");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClientUsingInvalidUrl(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.type"), testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(createClientApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new inactive client and fetch newly created client details using client id")
	public void testCreateNewInactiveClient() throws IOException {
		logStep("Create client using valid details in client name, sap number, active, testclient, client type and timezone field");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		createClientApiResponse = clientInfo.createNewClient("false", clientName, clientSapNumber, "false",
				testData.getProperty("api.client.type"), testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		String clientId = createClientApiResponse.jsonPath().get("clientId").toString();
		logStep("Fetch newly created client details using client id");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		validation.verifySearchClientResponseBody(fetchClientDetailsApiResponse, clientId, clientName,
				testData.getProperty("api.client.type"), clientSapNumber, "false", "false",
				testData.getProperty("api.client.timezoneid"), testData.getProperty("api.client.displayname"),
				Integer.parseInt(testData.getProperty("api.client.practice.count")), false, solutionList);
	}

}
