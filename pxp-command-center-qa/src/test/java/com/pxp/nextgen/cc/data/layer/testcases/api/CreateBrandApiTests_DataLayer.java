// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.*;
import org.testng.annotations.*;
import io.restassured.response.Response;
import java.io.*;

public class CreateBrandApiTests_DataLayer extends BaseRest {
	private BrandsInfo brandInfo;
	private Validations validation;
	Response createBrandApiResponse;
	int clientId, systemId, enterpriseId, practiceId, agnosticPracticeId;
	private CreateClientHierarchy_DL clientHierObj;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		brandInfo = new BrandsInfo();
		new SharedInfo();
		validation = new Validations();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);	
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template at Client Level and fetch newly created brand details using brandId")
	public void testCreateBrandAtClientLevel() throws IOException {
		logStep("Enter valid values in request payload to create branding template at client level");
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(clientId), name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		validation.verifyUpdateBrandingDetails(createBrandApiResponse, brandId, name);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template at System Level and fetch newly created brand details using brandId")
	public void testCreateBrandAtSystemLevel() throws IOException {
		logStep("Enter valid values in request payload to create branding template at system level");
		String name = testData.getProperty("system.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("system.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(systemId), name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		validation.verifyUpdateBrandingDetails(createBrandApiResponse, brandId, name);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template at Enterprise Level and fetch newly created brand details using brandId")
	public void testCreateBrandAtEnterpriseLevel() throws IOException {
		logStep("Enter valid values in request payload to create branding template at enterprise level");
		String name = testData.getProperty("enterprise.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("enterprise.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(enterpriseId), name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		validation.verifyUpdateBrandingDetails(createBrandApiResponse, brandId, name);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template at Practice Level and fetch newly created brand details using brandId")
	public void testCreateBrandAtPracticeLevel() throws IOException {
		logStep("Enter valid values in request payload to create branding template at Practice level");
		String name = testData.getProperty("practice.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("practice.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(practiceId), name, "true", color);
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
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(clientId), name, "true", "    ");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		validation.verifyUpdateBrandingDetails(createBrandApiResponse, brandId, name);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template with null values in payload and verify error response code")
	public void testCreateBrandWithNullValuesInPayload() throws IOException {
		logStep("Enter null values in request payload");
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template with null values in name field and verify error response code")
	public void testCreateBrandWithNameFieldAsNull() throws IOException {
		logStep("Enter null values in name field in request payload");
		String orgId = testData.getProperty("client.org.id");
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(orgId, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template with null values in orgId field and verify error response code")
	public void testCreateBrandWithOrgIdFieldAsNull() throws IOException {
		logStep("Enter null values in orgId field in request payload");
		String name = testData.getProperty("client.org.brand.name");
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(null, name, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check error response code for invalid Url")
	public void testErrorResponseCodeForInvalidUrl() throws NullPointerException, IOException {
		logStep("Enter valid values in request payload to create branding template");
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplateInvalidUrl_DL(String.valueOf(clientId), name, "true",
				color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(createBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check error response code for invalid method call")
	public void testErrorResponseCodeForInvalidMethodCall() throws NullPointerException, IOException {
		logStep("Enter valid values in request payload to create branding template");
		String name = testData.getProperty("client.org.brand.name");
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplateInvalidMethodCall_DL(String.valueOf(clientId), name,
				"true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(createBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check Version Name length validation and validate response code")
	public void testNameFieldLengthValidation() throws IOException, NullPointerException {
		logStep("Enter valid values in request payload to create branding template");
		String color = testData.getProperty("client.brand.color");
		logStep("Version Name length validation with 50 characters");
		String name = "AutoBrand".concat(CCUtils.generateAlphaNumericString(41));
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(clientId), name, "true", color);
		logStep("Validating response Code");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		logStep("Version Name length validation with 51 characters");
		name = "AutoBrand".concat(CCUtils.generateAlphaNumericString(51));
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(clientId), name, "true", color);
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 400);
		validation.verifyInvalidRequestValidation(createBrandApiResponse);
	}
}
