// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class DeleteBrandingTemplateApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private BrandsInfo brandInfo;
	private Validations validation;
	Response getBrandingDetailsApiResponse, createBrandApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupQaServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		brandInfo = new BrandsInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Delete inactive branding template at client level and verify response code")
	public void testResponseCodeForDeleteInactiveBrandingTemplateAtClientLevel() throws IOException {
		String orgId = testData.getProperty("client.org.id");
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate(orgId, name, "true", color);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		getBrandingDetailsApiResponse = sharedInfo.deleteBrandDetails(Integer.parseInt(brandId));
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Delete inactive branding template at system level and verify response code")
	public void testResponseCodeForDeleteInactiveBrandingTemplateAtSystemLevel() throws IOException {
		String orgId = testData.getProperty("system.org.id");
		String name = testData.getProperty("system.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("system.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate(orgId, name, "true", color);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		getBrandingDetailsApiResponse = sharedInfo.deleteBrandDetails(Integer.parseInt(brandId));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Delete inactive branding template at enterprise level and verify response code")
	public void testResponseCodeForDeleteInactiveBrandingTemplateAtEnterpriseLevel() throws IOException {
		String orgId = testData.getProperty("enterprise.org.id");
		String name = testData.getProperty("enterprise.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("enterprise.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate(orgId, name, "true", color);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		getBrandingDetailsApiResponse = sharedInfo.deleteBrandDetails(Integer.parseInt(brandId));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Delete inactive branding template at practice level and verify response code")
	public void testResponseCodeForDeleteInactiveBrandingTemplateAtPracticeLevel() throws IOException {
		logStep("Enter valid values in request payload to create branding template at Practice level");
		String orgId = testData.getProperty("practice.org.id");
		String name = testData.getProperty("practice.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("practice.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate(orgId, name, "true", color);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		getBrandingDetailsApiResponse = sharedInfo.deleteBrandDetails(Integer.parseInt(brandId));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing branId in brandId field and verify error message")
	public void testErrorResponseForNonExistingBrandId() throws IOException {
		getBrandingDetailsApiResponse = sharedInfo
				.deleteBrandDetails(Integer.parseInt(testData.getProperty("non.existing.brand.id")));
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidBrandId(getBrandingDetailsApiResponse,
				Integer.parseInt(testData.getProperty("non.existing.brand.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error message")
	public void testErrorResponseForInvalidUrl() throws IOException {
		getBrandingDetailsApiResponse = sharedInfo
				.deleteBrandDetailsUsingInvalidUrl(Integer.parseInt(testData.getProperty("brand.id.one")));
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getBrandingDetailsApiResponse);
	}
}
