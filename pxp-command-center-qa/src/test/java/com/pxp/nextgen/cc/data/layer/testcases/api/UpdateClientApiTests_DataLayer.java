// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import com.intuit.ifs.csscat.core.RetryAnalyzer;

public class UpdateClientApiTests_DataLayer extends BaseRest {
	private ClientInfo clientInfo;
	private Validations validation;
	Response createClientApiResponse, fetchClientDetailsApiResponse, updateClientApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupQaServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client details(client name, sap number,client type, timezone id, status) and fetch updated client details using client id")
	public void testEditClientDetails() throws IOException {
		logStep("Create a new active client");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.type"), testData.getProperty("api.client.timezoneid"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");
		logStep("Edit Client Details");
		String newClientName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(40))));
		String newClientSapNumber = CCUtils
				.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.client.sapnumber")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(25))));
		updateClientApiResponse = clientInfo.UpdateClient_DataLayer(clientId, false, newClientName,
				newClientSapNumber, testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.forteen.type"), testData.getProperty("new.timezone"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 200);
		validation.verifyCreateAndUpdateClientResponseBody(updateClientApiResponse, newClientName);
		logStep("Fetch updated client details using clientid");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails_DataLayer(clientId);
		logStep("Verify updated client details");
		validation.verifyGetClientDetailsApiResponseBody(fetchClientDetailsApiResponse, Integer.toString(clientId),
				newClientName, testData.getProperty("api.client.forteen.type"), newClientSapNumber, "false",
				testData.getProperty("api.client.testclient"), testData.getProperty("new.timezone"),
				testData.getProperty("new.timezone.display.name"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit client status from inactive to active and update timezone value from not null to null value and fetch updated client details using client id")
	public void testEditClientStatusFromInactiveToActiveAndUpdateTimezoneToNullValue() throws IOException {
		logStep("Create a new inactive client");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		createClientApiResponse = clientInfo.createNewClient_DataLayer("false", clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");
		logStep("Update client status from inactive to active and update timezone from not null value to null value");
		updateClientApiResponse = clientInfo.UpdateClient_DataLayer(clientId, Boolean.parseBoolean(testData.getProperty("api.client.active")),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.forteen.type"), null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 200);
		validation.verifyCreateAndUpdateClientResponseBody(updateClientApiResponse, clientName);
		logStep("Fetch updated client details using clientid");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails_DataLayer(clientId);
		logStep("Verify client status is changed from inactive to active and timezone value is changed from not null value to null value");
		validation.verifyGetClientDetailsApiResponseBody(fetchClientDetailsApiResponse, Integer.toString(clientId),
				clientName, testData.getProperty("api.client.forteen.type"), clientSapNumber, "true",
				testData.getProperty("api.client.testclient"), null, null);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing client id in clientId field and verify error message")
	public void testErrorMessageForNonExistingClientId() throws IOException {
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		logStep("Enter non existing client id in clientId field and verify error message");
		int nonExistingClientId = Integer.parseInt(testData.getProperty("api.nonexisting.client.id"));
		updateClientApiResponse = clientInfo.UpdateClient_DataLayer(nonExistingClientId,
				Boolean.parseBoolean(testData.getProperty("api.client.active")), clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.forteen.type"),
				testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		validation.verifyResponseForNonExistingClientId(updateClientApiResponse, nonExistingClientId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value in mandatory fields(active,client name,sap nummber and type) and validate error message")
	public void testErrorMessagesForMandatoryFields() throws IOException {
		logStep("Enter null value in mandatory fields(active,client name,sap nummber and type) and validate error message");
		int existingClientId = Integer.parseInt(testData.getProperty("api.existing.client.id"));
		updateClientApiResponse = clientInfo.UpdateClient_DataLayer(existingClientId, Boolean.parseBoolean(null), null, null,
				testData.getProperty("api.client.testclient"), null, testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		validation.verifyNullErrorMessageResponseBody(updateClientApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter already existing value in client name field and validate error message")
	public void testErrorResponseForClientNameField() throws IOException {
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String clientSapNumberNew = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		createClientApiResponse = clientInfo.createNewClient_DataLayer("false", clientName, clientSapNumberNew,
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		logStep("Enter already existing value in client name field and validate error message");
		fetchClientDetailsApiResponse = clientInfo
				.getClientDetails_DataLayer(Integer.parseInt(testData.getProperty("api.existing.client.id")));
		String type = fetchClientDetailsApiResponse.jsonPath().get("type");
		Boolean activeStatus = fetchClientDetailsApiResponse.jsonPath().getBoolean("active");
		String sapNum = fetchClientDetailsApiResponse.jsonPath().get("sapNumber");
		String testClient = fetchClientDetailsApiResponse.jsonPath().get("testClient");
		int existingClientId = Integer.parseInt(testData.getProperty("api.existing.client.id"));
		String timezoneId = fetchClientDetailsApiResponse.jsonPath().get("timezone.id");
		updateClientApiResponse = clientInfo.UpdateClient_DataLayer(existingClientId, activeStatus, clientName, sapNum,
				testClient, type, timezoneId);

		/*
		 * updateClientApiResponse = clientInfo.UpdateClient_DataLayer(existingClientId,
		 * testData.getProperty("api.client.active"),
		 * testData.getProperty("api.existing.client.name"), clientSapNumber,
		 * testData.getProperty("api.client.testclient"),
		 * testData.getProperty("api.client.forteen.type"),
		 * testData.getProperty("api.client.timezoneid"));
		 */
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientName(updateClientApiResponse,
				clientName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter already existing value in sap number field and validate error message")
	public void testErrorResponseForSapNumberField() throws IOException {
		logStep("Enter already existing value in sap number field and validate error message");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		int existingClientId = Integer.parseInt(testData.getProperty("api.existing.client.id"));
		updateClientApiResponse = clientInfo.UpdateClient_DataLayer(existingClientId,
				Boolean.parseBoolean(testData.getProperty("api.client.active")), clientName,
				testData.getProperty("api.existing.client.sapnumber"), testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.forteen.type"), testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientSapNumber(updateClientApiResponse,
				testData.getProperty("api.existing.client.sapnumber"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing client type and validate error message")
	public void testErrorResponseForTypeField() throws IOException {
		logStep("Enter non existing client type and validate error message");
		String nonExistingClientType = testData.getProperty("api.client.type").concat(CCUtils.generateRandomString());
		String clientName = testData.getProperty("api.client.name").concat(CCUtils.generateRandomString());
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		int existingClientId = Integer.parseInt(testData.getProperty("api.existing.client.id"));
		updateClientApiResponse = clientInfo.UpdateClient_DataLayer(existingClientId,
				Boolean.parseBoolean(testData.getProperty("api.client.active")), clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"), nonExistingClientType,
				testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientTypeField(updateClientApiResponse, nonExistingClientType);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing time zone id and validate error message")
	public void testErrorResponseForTimezoneField() throws IOException {
		logStep("Enter non existing time zone id in timezone field and validate error message");
		String nonExistingTimezoneId = testData.getProperty("api.client.timezoneid")
				.concat(CCUtils.generateRandomString());
		String clientName = testData.getProperty("api.client.name").concat(CCUtils.generateRandomString());
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		int existingClientId = Integer.parseInt(testData.getProperty("api.existing.client.id"));
		updateClientApiResponse = clientInfo.UpdateClient_DataLayer(existingClientId,
				Boolean.parseBoolean(testData.getProperty("api.client.active")), clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				nonExistingTimezoneId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientTimezoneIdField(updateClientApiResponse, nonExistingTimezoneId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 50 characters in sap id field and validate error message")
	public void testMaximumCharacterErrorResponseForSapNumberField() throws IOException {
		logStep("Enter more than 50 characters in sap id field and validate error message");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(4));
		int existingClientId = Integer.parseInt(testData.getProperty("api.existing.client.id"));
		updateClientApiResponse = clientInfo.UpdateClient_DataLayer(existingClientId,
				Boolean.parseBoolean(testData.getProperty("api.client.active")), clientName,
				testData.getProperty("api.invalid.client.sapnumber"), testData.getProperty("api.client.testclient"),
				testData.getProperty("api.client.type"), testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientSapNumberField(updateClientApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in client name field and validate error message")
	public void testMaximumCharacterErrorResponseForClientNameField() throws IOException {
		logStep("Enter more than 100 characters in client name field and validate error message");
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(4));
		int existingClientId = Integer.parseInt(testData.getProperty("api.existing.client.id"));
		updateClientApiResponse = clientInfo.UpdateClient_DataLayer(existingClientId,
				Boolean.parseBoolean(testData.getProperty("api.client.active")), testData.getProperty("api.invalid.client.name"),
				clientSapNumber, testData.getProperty("api.client.testclient"), testData.getProperty("api.client.type"),
				testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		validation.verifyMaximumCharacterErrorMessageInResponseForClientNameField(updateClientApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank space in active, client name, sap nummber, timezone id and type fields and validate error message")
	public void testErrorMessagesForBlankFields() throws IOException {
		logStep("Enter blank space in active, client name, sap nummber, timezone id and type fields and validate error message");
		int existingClientId = Integer.parseInt(testData.getProperty("api.existing.client.id"));
		updateClientApiResponse = clientInfo.UpdateClient_DataLayer(existingClientId, Boolean.parseBoolean(" "), " ", " ",
				testData.getProperty("api.client.testclient"), " ", testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 400);
		validation.verifyNullErrorMessageResponseBody(updateClientApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessagesForInvalidUrl() throws IOException {
		logStep("Enter invalid url and validate error status code");
		int existingClientId = Integer.parseInt(testData.getProperty("api.existing.client.id"));
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		updateClientApiResponse = clientInfo.UpdateClientIsingInvalidUrl_DataLayer(existingClientId,
				Boolean.parseBoolean(testData.getProperty("api.client.active")), clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.forteen.type"),
				testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateClientApiResponse);
		CCUtils.assertEquals(updateClientApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateClientApiResponse);
	}

}
