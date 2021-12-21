// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.Timezone;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetTimezoneTests extends BaseRest {
	private Validations validation;
	Timezone timezone;

	@BeforeTest()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		validation = new Validations();
		timezone = new Timezone();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check timezone id & displayname ")
	public void testTimezoneValues() throws IOException {
		Response res = timezone.getTimezone();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(res);
		CCUtils.assertEquals(res.getStatusCode(), 200);
		logStep("Validating Timezone id and displayname");
		validation.verifyGetTimezonApiResponseBody(res);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of GET")
	public void testErrorForInvalidMethod() throws IOException {
		Response res = timezone.getInvalidTimezone();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(res);
		CCUtils.assertEquals(res.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(res);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error status code if user enter invalid url")
	public void testErrorForInvalidTimezoneUrl() throws IOException {
		Response res = timezone.getInvalidTimezoneUrl();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(res);
		CCUtils.assertEquals(res.getStatusCode(), 404);
	}
}
