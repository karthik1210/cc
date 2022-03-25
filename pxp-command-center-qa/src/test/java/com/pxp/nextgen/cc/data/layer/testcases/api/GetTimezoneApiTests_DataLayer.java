// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.CommonsInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetTimezoneApiTests_DataLayer extends BaseRest {
	private CommonsInfo commonsInfo;
	private Validations validation;
	Response getTimezoneApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		commonsInfo = new CommonsInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check timezone id & displayname ")
	public void testTimezoneValues() throws IOException {
		getTimezoneApiResponse = commonsInfo.getTimezones_DataLayer();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getTimezoneApiResponse);
		CCUtils.assertEquals(getTimezoneApiResponse.getStatusCode(), 200);
		logStep("Validating Timezone id and displayname");
		validation.verifyGetTimezonesApiResponseBody(getTimezoneApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of GET")
	public void testErrorForInvalidMethod() throws IOException {
		getTimezoneApiResponse = commonsInfo.invalidMethodCall_DataLayer();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getTimezoneApiResponse);
		CCUtils.assertEquals(getTimezoneApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(getTimezoneApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		getTimezoneApiResponse = commonsInfo.getTimezonesUsingInvalidUrl_DataLayer();
		logStep("Validating Response Information");
		CCUtils.responseTimeValidation(getTimezoneApiResponse);
		CCUtils.assertEquals(getTimezoneApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getTimezoneApiResponse);
	}

}
