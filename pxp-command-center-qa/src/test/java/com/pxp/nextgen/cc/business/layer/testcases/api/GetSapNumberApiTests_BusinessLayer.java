// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetSapNumberApiTests_BusinessLayer extends BaseRest {

	private ClientInfo clientInfo;
	private Validations validation;
	Response getSapNumberApiResponse, createClientApiResponse;
	int clientId;
	String clientName, clientSapNumber;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		validation = new Validations();
		clientName = ccUser.getClientName().trim().concat(CCUtils.generateRandomString()).trim();
		clientSapNumber = "SAP_01_number_".trim().concat(CCUtils.generateAlphaNumericString(5)).trim();
		clientId = createClient(clientName, clientSapNumber);
	}

	public int createClient(String name, String sapNumber) throws Exception {
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), name, sapNumber, "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, name);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	@DataProvider(name = "clientSapNumber")
	public Object[][] getClientSapNumber() throws Exception {
		return new Object[][] { { clientSapNumber.trim() }, { clientSapNumber.toLowerCase().trim() },
				{ clientSapNumber.toUpperCase().trim() }, { CCUtils.reverseCase(clientSapNumber).trim() } };
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "clientSapNumber", description = "Enter already existing client SapNumber and verify client name and client id details displayed in response body")
	public void testAlreadyExistingSapNumber(String clientSapNumber) throws IOException {
		getSapNumberApiResponse = clientInfo.getSapNumber(clientSapNumber);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getSapNumberApiResponse);
		CCUtils.assertEquals(getSapNumberApiResponse.getStatusCode(), 200);
		validation.verifyResponseForExistingClientSapNumber(getSapNumberApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing client sap number in client sap number field and validate error message")
	public void testErrorMessageForNonExistingSapNumber() throws IOException {
		String newSapNumber = testData.getProperty("api.existing.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericString(5));
		getSapNumberApiResponse = clientInfo.getSapNumber(newSapNumber);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getSapNumberApiResponse);
		CCUtils.assertEquals(getSapNumberApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForSapNumberField(getSapNumberApiResponse, newSapNumber);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank space in SapNumber field and validate error message")
	public void testErrorMessageForBlankSapNumberField() throws IOException {
		getSapNumberApiResponse = clientInfo.getSapNumber(" ");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getSapNumberApiResponse);
		CCUtils.assertEquals(getSapNumberApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageIfClientSapNumberIsNull(getSapNumberApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		getSapNumberApiResponse = clientInfo.getClientSapNumbrUsingInvalidUrl(clientSapNumber);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getSapNumberApiResponse);
		CCUtils.assertEquals(getSapNumberApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getSapNumberApiResponse);
	}
}
