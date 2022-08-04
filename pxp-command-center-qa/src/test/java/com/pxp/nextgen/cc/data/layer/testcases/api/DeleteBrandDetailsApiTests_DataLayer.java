// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class DeleteBrandDetailsApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	Response getBrandingDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Delete inactive branding template at client level and verify response code")
	public void testResponseCodeForDeleteInactiveBrandingTemplateAtClientLevel() throws IOException {
		getBrandingDetailsApiResponse = sharedInfo
				.deleteBrandDetails_DL(Integer.parseInt(testData.getProperty("inactive.brand.id.client.level")));
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Delete inactive branding template at system level and verify response code")
	public void testResponseCodeForDeleteInactiveBrandingTemplateAtSystemLevel() throws IOException {
		getBrandingDetailsApiResponse = sharedInfo
				.deleteBrandDetails_DL(Integer.parseInt(testData.getProperty("inactive.brand.id.system.level")));
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Delete inactive branding template at enterprise level and verify response code")
	public void testResponseCodeForDeleteInactiveBrandingTemplateAtEnterpriseLevel() throws IOException {
		getBrandingDetailsApiResponse = sharedInfo
				.deleteBrandDetails_DL(Integer.parseInt(testData.getProperty("inactive.brand.id.enterprise.level")));
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Delete inactive branding template at practice level and verify response code")
	public void testResponseCodeForDeleteInactiveBrandingTemplateAtPracticeLevel() throws IOException {
		getBrandingDetailsApiResponse = sharedInfo
				.deleteBrandDetails_DL(Integer.parseInt(testData.getProperty("inactive.brand.id.practice.level")));
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing branId in brandId field and verify error message")
	public void testErrorResponseForNonExistingBrandId() throws IOException {
		getBrandingDetailsApiResponse = sharedInfo
				.deleteBrandDetails_DL(Integer.parseInt(testData.getProperty("non.existing.brand.id")));
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidBrandId(getBrandingDetailsApiResponse,
				Integer.parseInt(testData.getProperty("non.existing.brand.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error message")
	public void testErrorResponseForInvalidUrl() throws IOException {
		getBrandingDetailsApiResponse = sharedInfo
				.deleteBrandDetailsUsingInvalidUrl_DL(Integer.parseInt(testData.getProperty("brand.id.one")));
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getBrandingDetailsApiResponse);
	}
}