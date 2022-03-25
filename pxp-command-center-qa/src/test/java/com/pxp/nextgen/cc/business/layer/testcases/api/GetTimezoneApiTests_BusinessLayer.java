// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.CommonsInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetTimezoneApiTests_BusinessLayer extends BaseRest {
	private Validations validation;
	CommonsInfo timezone;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		validation = new Validations();
		timezone = new CommonsInfo();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check timezone id & displayname ")
	public void testTimezoneValues() throws IOException {
		Response res = timezone.getTimezones_BusinessLayer();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(res);
		CCUtils.assertEquals(res.getStatusCode(), 200);
		logStep("Validating Timezone id and displayname");
		validation.verifyGetTimezonesApiResponseBody(res);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of GET")
	public void testErrorForInvalidMethod() throws IOException {
		Response res = timezone.invalidMethodCall_BusinessLayer();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(res);
		CCUtils.assertEquals(res.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(res);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error status code if user enter invalid url")
	public void testErrorForInvalidTimezoneUrl() throws IOException {
		Response res = timezone.getTimezonesUsingInvalidUrl_BusinessLayerLayer();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(res);
		CCUtils.assertEquals(res.getStatusCode(), 404);
	}
}
