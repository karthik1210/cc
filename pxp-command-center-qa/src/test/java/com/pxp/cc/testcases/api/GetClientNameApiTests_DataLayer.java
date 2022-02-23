// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

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

public class GetClientNameApiTests_DataLayer extends BaseRest {
	private ClientInfo clientInfo;
	private Validations validation;
	Response getClientNameApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		validation = new Validations();
	}

	@DataProvider(name = "clientName")
	public Object[][] getClientName() throws Exception {
		return new Object[][] { { testData.getProperty("api.existing.client.name").trim() },
				{ testData.getProperty("api.existing.client.name").trim().toLowerCase() },
				{ testData.getProperty("api.existing.client.name").trim().toUpperCase() },
				{ CCUtils.reverseCase(testData.getProperty("api.existing.client.name").trim()) } };
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "clientName", description = "Enter already existing client name and verify client name and client id details displayed in response body")
	public void testAlreadyExistingClientName(String clientName) throws IOException {
		getClientNameApiResponse = clientInfo.getClientName_DataLayer(clientName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getClientNameApiResponse);
		CCUtils.assertEquals(getClientNameApiResponse.getStatusCode(), 200);
		validation.verifyResponseForExistingClientName(getClientNameApiResponse,
				testData.getProperty("api.existing.client.name"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing client name in client name field and validate error message")
	public void testErrorMessageForNonExistingClientName() throws IOException {
		String clientName = testData.getProperty("api.existing.client.name")
				.concat(CCUtils.generateAlphaNumericString(5));
		getClientNameApiResponse = clientInfo.getClientName_DataLayer(clientName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getClientNameApiResponse);
		CCUtils.assertEquals(getClientNameApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientNameField(getClientNameApiResponse, clientName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank space in client name field and validate error message")
	public void testErrorMessageForBlankClientNameField() throws IOException {
		getClientNameApiResponse = clientInfo.getClientName_DataLayer(" ");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getClientNameApiResponse);
		CCUtils.assertEquals(getClientNameApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientNameField(getClientNameApiResponse, " ");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		getClientNameApiResponse = clientInfo
				.getClientNameUsingInvalidUrl_DataLayer(testData.getProperty("api.existing.client.name"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getClientNameApiResponse);
		CCUtils.assertEquals(getClientNameApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getClientNameApiResponse);
	}

}
