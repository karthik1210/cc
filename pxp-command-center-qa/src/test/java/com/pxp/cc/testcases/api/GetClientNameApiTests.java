// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetClientNameApiTests extends BaseRest {
	private ClientInfo clientInfo;
	private Validations validation;
	Response getClientNameApiResponse;

	@BeforeTest()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		validation = new Validations();
	}

	@DataProvider(name = "clientName")
	public Object[][] getClientName() throws Exception {
		return new Object[][] { { ccUser.getClientName().trim() }, { ccUser.getClientName().trim().toLowerCase() },
				{ ccUser.getClientName().trim().toUpperCase() } };
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "clientName", description = "Enter already existing client name and verify error message for client name field")
	public void testErrorMessageForAlreadyExistingClientName(String clientName) throws IOException {
		getClientNameApiResponse = clientInfo.getClientName(clientName);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(getClientNameApiResponse);
		logStep("Validating Response code");
		CCUtils.assertEquals(getClientNameApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyErrorMessageInResponseForClientNameField(getClientNameApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing client name in client name field and validate response body")
	public void testNonExistingClientName() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		getClientNameApiResponse = clientInfo.getClientName(clientName);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(getClientNameApiResponse);
		logStep("Validating Response code");
		CCUtils.assertEquals(getClientNameApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyResponseBodyForClientNameField(getClientNameApiResponse);
	}
}
