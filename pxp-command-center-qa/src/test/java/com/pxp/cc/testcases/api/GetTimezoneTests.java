// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeMethod;
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

	@BeforeMethod()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		validation = new Validations();
		timezone = new Timezone();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check timezone id & displayname ")
	public void testTimezoneValues() throws IOException {
		Response res = timezone.getTimezone();
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(res);
		logStep("Validating Response code");
		CCUtils.assertEquals(res.getStatusCode(), 200);
		logStep("Validating Timezone id and displayname");
		validation.verifyGetTimezonApiResponseBody(res);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instaead of get ")
	public void testPracticeParentDropdown() throws IOException {
		Response res = timezone.getInvalidTimezone();
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(res);
		logStep("Validating error Response code");
		CCUtils.assertEquals(res.getStatusCode(), 405);
	}
}
