// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class FetchClientDetailsTests_DataLayer extends BaseRest {

	private ClientInfo clientInfo;
	private Validations validation;
	Response fetchClientDetailsApiResponse, createClientApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing id in client id field and verify error message for client id field")
	public void testErrorMessageForClientIdField() throws IOException {
		logStep("Enter non existing client id");
		fetchClientDetailsApiResponse = clientInfo
				.getClientDetails_DataLayer(Integer.parseInt(testData.getProperty("api.nonexisting.client.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 400);
		logStep("Validating error message of response body");
		validation.verifyErrorResponseBodyForClientIdField(fetchClientDetailsApiResponse,
				testData.getProperty("api.nonexisting.client.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing id in client id field and verify response body.")
	public void testGetExistingClientIdDetails() throws IOException {
		logStep("Creat a new client and search that client");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");
		logStep("Enter existing client id");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails_DataLayer(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetClientDetailsApiResponseBody(fetchClientDetailsApiResponse, String.valueOf(clientId),
				clientName, Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.one.type"), clientSapNumber, testData.getProperty("api.client.active"),
				testData.getProperty("api.client.testclient"), testData.getProperty("api.client.timezoneid"),
				testData.getProperty("api.client.displayname"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessagesForInvalidUrl() throws IOException {
		logStep("Enter invalid url and validate error status code");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");
		logStep("Enter existing client id");
		fetchClientDetailsApiResponse = clientInfo.getClientDetails_DataLayerInvalidUrl(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchClientDetailsApiResponse);
		CCUtils.assertEquals(fetchClientDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchClientDetailsApiResponse);
	}
}
