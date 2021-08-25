// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetClientTypesApiTests extends BaseRest {

	private ClientInfo clientInfo;
	private Validations validation;
	Response getClientTypesApiResponse;

	@BeforeTest()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify client types")
	public void testClientTypes() throws IOException {
		getClientTypesApiResponse = clientInfo.getClientTypes();
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(getClientTypesApiResponse);
		logStep("Validating Response code");
		CCUtils.assertEquals(getClientTypesApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyGetClientTypesApiResponseBody(getClientTypesApiResponse);
	}
}
