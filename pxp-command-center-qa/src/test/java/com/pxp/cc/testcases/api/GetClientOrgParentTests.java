// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;

public class GetClientOrgParentTests extends BaseRest {

	private ClientInfo clientInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check parents for practice in client ")
	public void testPracticeParentDropdown() throws IOException {
		Response res = clientInfo.getParentDropdown(ccUser.getClientCreatedByApi(), "PRACTICE");
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(res);
		logStep("Validating Response code");
		CCUtils.assertEquals(res.getStatusCode(), 200);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check parents for enterprise in client ")
	public void testEnterpriseParentDropdown() throws IOException {
		Response res = clientInfo.getParentDropdown(ccUser.getClientCreatedByApi(), "ENTERPRISE");
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(res);
		logStep("Validating Response code");
		CCUtils.assertEquals(res.getStatusCode(), 200);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check parents for system in client")
	public void testSystemParentDropdown() throws IOException {
		Response res = clientInfo.getParentDropdown(ccUser.getClientCreatedByApi(), "SYSTEM");
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(res);
		logStep("Validating Response code");
		CCUtils.assertEquals(res.getStatusCode(), 200);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check parents for client in client")
	public void testClientInput() throws IOException {
		Response res = clientInfo.getParentDropdown(ccUser.getClientCreatedByApi(), "CLIENT");
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(res);
		logStep("Validating Response code");
		CCUtils.assertEquals(res.getStatusCode(), 400);

	}
}
