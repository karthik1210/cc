// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetClientTypesApiTests_BusinessLayer extends BaseRest {

	private ClientInfo clientInfo;
	private Validations validation;
	Response getClientTypesApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify client types")
	public void testClientTypes() throws IOException {
		getClientTypesApiResponse = clientInfo.getClientTypes();
		logStep("Validating Response Information");
		CCUtils.responseTimeValidation(getClientTypesApiResponse);
		CCUtils.assertEquals(getClientTypesApiResponse.getStatusCode(), 200);
		validation.verifyGetClientTypesApiResponseBody(getClientTypesApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		getClientTypesApiResponse = clientInfo.getClientTypeUsingInvalidUrl();
		logStep("Validating Response Information");
		CCUtils.responseTimeValidation(getClientTypesApiResponse);
		CCUtils.assertEquals(getClientTypesApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getClientTypesApiResponse);
	}
}
