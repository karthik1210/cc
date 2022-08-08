// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.*;
import org.testng.annotations.*;
import io.restassured.response.Response;
import java.io.*;

public class CreateBrandingTemplateApiTests_BusinessLayer extends BaseRest {
	private BrandsInfo brandInfo;
	private Validations validation;
	Response createBrandApiResponse, fetchBrandingDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupQaServiceRequestSpecificationBuilder();
		brandInfo = new BrandsInfo();
		new SharedInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template at Client Level and fetch newly created brand details using brandId")
	public void testCreateBrandAtClientLevel() throws IOException {
		logStep("Enter valid values in request payload to create branding template at client level");
		String orgId = testData.getProperty("client.org.id");
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate(orgId, name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		validation.verifyUpdateBrandingDetails(createBrandApiResponse, brandId, name);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template at System Level and fetch newly created brand details using brandId")
	public void testCreateBrandAtSystemLevel() throws IOException {
		logStep("Enter valid values in request payload to create branding template at system level");
		String orgId = testData.getProperty("system.org.id");
		String name = testData.getProperty("system.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("system.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate(orgId, name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		validation.verifyUpdateBrandingDetails(createBrandApiResponse, brandId, name);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template at Enterprise Level and fetch newly created brand details using brandId")
	public void testCreateBrandAtEnterpriseLevel() throws IOException {
		logStep("Enter valid values in request payload to create branding template at enterprise level");
		String orgId = testData.getProperty("enterprise.org.id");
		String name = testData.getProperty("enterprise.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("enterprise.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate(orgId, name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		validation.verifyUpdateBrandingDetails(createBrandApiResponse, brandId, name);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template at Practice Level and fetch newly created brand details using brandId")
	public void testCreateBrandAtPracticeLevel() throws IOException {
		logStep("Enter valid values in request payload to create branding template at Practice level");
		String orgId = testData.getProperty("practice.org.id");
		String name = testData.getProperty("practice.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("practice.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate(orgId, name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		validation.verifyUpdateBrandingDetails(createBrandApiResponse, brandId, name);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template with null values in color field")
	public void testCreateBrandWithColorFieldAsNull() throws IOException {
		logStep("Enter valid values in request payload with null values in color field to create branding template");
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String orgId = testData.getProperty("practice.org.id");
		createBrandApiResponse = brandInfo.createBrandingTemplate(orgId, name, "true", "    ");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		validation.verifyUpdateBrandingDetails(createBrandApiResponse, brandId, name);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template with null values in payload and verify error response code")
	public void testCreateBrandWithNullValuesInPayload() throws IOException {
		logStep("Enter null values in request payload");
		createBrandApiResponse = brandInfo.createBrandingTemplate(null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForNullValuesInPayload(createBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template with null values in name field and verify error response code")
	public void testCreateBrandWithNameFieldAsNull() throws IOException {
		logStep("Enter null values in name field in request payload");
		String orgId = testData.getProperty("client.org.id");
		createBrandApiResponse = brandInfo.createBrandingTemplate(orgId, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForNameFieldAsNull(createBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template with null values in orgId field and verify error response code")
	public void testCreateBrandWithOrgIdFieldAsNull() throws IOException {
		logStep("Enter null values in orgId field in request payload");
		String name = testData.getProperty("client.org.brand.name");
		createBrandApiResponse = brandInfo.createBrandingTemplate(null, name, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForOrgIdAsNull(createBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check error response code for invalid Url")
	public void testErrorResponseCodeForInvalidUrl() throws NullPointerException, IOException {
		logStep("Enter valid values in request payload to create branding template");
		String orgId = testData.getProperty("client.org.id");
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplateInvalidUrl_BL(orgId, name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(createBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check error response code for invalid method call")
	public void testErrorResponseCodeForInvalidMethodCall() throws NullPointerException, IOException {
		logStep("Enter valid values in request payload to create branding template");
		String orgId = testData.getProperty("client.org.id");
		String name = testData.getProperty("client.org.brand.name");
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplateInvalidMethodCall_BL(orgId, name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(createBrandApiResponse);
	}
}
