// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

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

public class GetClientNameApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private Validations validation;
	Response getClientNameApiResponse, createClientApiResponse;
	int clientId;
	String clientNameData, clientSapNumber;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		validation = new Validations();
		clientNameData = "New_" + ccUser.getClientName().trim().concat(CCUtils.generateAlphaNumericString(5)).trim();
		clientSapNumber = ccUser.getClientSapNumber().trim().concat(CCUtils.generateRandomString()).trim();
		clientId = createClient(clientNameData, clientSapNumber);
	}

	public int createClient(String name, String sapNumber) throws Exception {
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), name, sapNumber, "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId(),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, name);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	@DataProvider(name = "clientName")
	public Object[][] getClientName() throws Exception {
		return new Object[][] { { clientNameData.trim() }, { clientNameData.toLowerCase().trim() },
				{ clientNameData.toUpperCase().trim() }, { CCUtils.reverseCase(clientNameData).trim() } };
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "clientName", description = "Enter already existing client name and verify client name and client id details displayed in response body")
	public void testAlreadyExistingClientName(String clientName) throws IOException {
		getClientNameApiResponse = clientInfo.getClientName(clientName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getClientNameApiResponse);
		CCUtils.assertEquals(getClientNameApiResponse.getStatusCode(), 200);
		validation.verifyResponseForExistingClientName(getClientNameApiResponse, clientNameData);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing client name in client name field and validate error message")
	public void testErrorMessageForNonExistingClientName() throws IOException {
		String newClientName = testData.getProperty("api.existing.client.name")
				.concat(CCUtils.generateAlphaNumericString(5));
		getClientNameApiResponse = clientInfo.getClientName(newClientName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getClientNameApiResponse);
		CCUtils.assertEquals(getClientNameApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientNameField(getClientNameApiResponse, newClientName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank space in client name field and validate error message")
	public void testErrorMessageForBlankClientNameField() throws IOException {
		getClientNameApiResponse = clientInfo.getClientName(" ");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getClientNameApiResponse);
		CCUtils.assertEquals(getClientNameApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageIfClientNameIsNull(getClientNameApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		getClientNameApiResponse = clientInfo.getClientNameUsingInvalidUrl(clientNameData);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getClientNameApiResponse);
		CCUtils.assertEquals(getClientNameApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getClientNameApiResponse);
	}
}
