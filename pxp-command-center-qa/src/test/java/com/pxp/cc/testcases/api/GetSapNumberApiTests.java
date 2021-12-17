// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetSapNumberApiTests extends BaseRest {

	private ClientInfo clientInfo;
	private Validations validation;
	Response getSapNumberApiResponse;

	@BeforeMethod()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		validation = new Validations();
	}

	@DataProvider(name = "clientSapNumber")
	public Object[][] getClientSapNumber() throws Exception {
		return new Object[][] { { ccUser.getExistingClientSapNumber().trim() },
				{ ccUser.getExistingClientSapNumber().trim().toLowerCase() },
				{ ccUser.getExistingClientSapNumber().trim().toUpperCase() } };
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "clientSapNumber", description = "Enter already existing client sap number and verify error message for sap number field")
	public void testErrorMessageForAlreadyExistingClienSapNumber(String sapNumber) throws IOException {
		getSapNumberApiResponse = clientInfo.getSapNumber(sapNumber);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(getSapNumberApiResponse);
		logStep("Validating Response code");
		CCUtils.assertEquals(getSapNumberApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyErrorMessageInResponseForSapNumberField(getSapNumberApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing sap number in client sap number field and validate response body")
	public void testNonExistingSapNumber() throws IOException {
		String sapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		getSapNumberApiResponse = clientInfo.getSapNumber(sapNumber);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(getSapNumberApiResponse);
		logStep("Validating Response code");
		CCUtils.assertEquals(getSapNumberApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyResponseBodyForSapNumberField(getSapNumberApiResponse);
	}

}
