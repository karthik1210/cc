// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetBrandDetailsApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	Response getBrandingDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing brandId in brand id field and verify error response")
	public void testErrorResponseForInvalidBrandId() throws IOException {
		getBrandingDetailsApiResponse = sharedInfo
				.getBrandDetails(Integer.parseInt(testData.getProperty("non.existing.brand.id")));
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidBrandId(getBrandingDetailsApiResponse,
				Integer.parseInt(testData.getProperty("non.existing.brand.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing brandId in brand id field and verify response body")
	public void testGetBrandDetailsApi() throws IOException {
		getBrandingDetailsApiResponse = sharedInfo
				.getBrandDetails(Integer.parseInt(testData.getProperty("brand.id.one")));
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 200);
		validation.verifyBrandingDetails(getBrandingDetailsApiResponse, testData.getProperty("brand.id.one.orgId"),
				testData.getProperty("brand.id.one.orgType"), testData.getProperty("brand.id.one.name"),
				testData.getProperty("brand.id.one.showLogo"), testData.getProperty("brand.id.one.color"),
				Integer.parseInt(testData.getProperty("brand.id.one")), testData.getProperty("brand.id.one.logo"),
				testData.getProperty("brand.id.one.favicon"), testData.getProperty("brand.id.one.emailLogo"),
				testData.getProperty("brand.id.one.active"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error message")
	public void testErrorResponseForInvalidUrl() throws IOException {
		getBrandingDetailsApiResponse = sharedInfo
				.getBrandDetailsUsingInvalidUrl(Integer.parseInt(testData.getProperty("brand.id.one")));
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getBrandingDetailsApiResponse);
	}
}
