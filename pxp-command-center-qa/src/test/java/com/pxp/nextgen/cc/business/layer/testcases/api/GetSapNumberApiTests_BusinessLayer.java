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
	Response getSapNumberApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		validation = new Validations();
	}

	@DataProvider(name = "clientSapNumber")
	public Object[][] getClientSapNumber() throws Exception {
		return new Object[][] { { testData.getProperty("api.existing.client.sapnumber").trim() },
				{ testData.getProperty("api.existing.client.sapnumber").trim().toLowerCase() },
				{ testData.getProperty("api.existing.client.sapnumber").trim().toUpperCase() },
				{ CCUtils.reverseCase(testData.getProperty("api.existing.client.sapnumber").trim()) } };
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
		String clientSapNumber = testData.getProperty("api.existing.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericString(5));
		getSapNumberApiResponse = clientInfo.getSapNumber(clientSapNumber);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getSapNumberApiResponse);
		CCUtils.assertEquals(getSapNumberApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForSapNumberField(getSapNumberApiResponse, clientSapNumber);
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
		getSapNumberApiResponse = clientInfo
				.getClientSapNumbrUsingInvalidUrl(testData.getProperty("api.existing.client.sapnumber"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getSapNumberApiResponse);
		CCUtils.assertEquals(getSapNumberApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getSapNumberApiResponse);
	}
}
