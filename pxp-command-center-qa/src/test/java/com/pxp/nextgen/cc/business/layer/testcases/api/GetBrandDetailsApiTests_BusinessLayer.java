// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetBrandDetailsApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	Response getBrandingDetailsApiResponse, createBrandApiResponse, createAgnosticPracticeApiResponse;
	private BrandsInfo brandInfo;
	int clientId, systemId, enterpriseId, practiceId, agnosticPracticeId, brandId;
	private CreateClientHierarchy_BL clientHierObj;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		brandInfo = new BrandsInfo();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		agnosticPracticeId = clientHierObj.createAgnosticPractice_BL(systemId);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 0, description = "Enter existing client brandId in brand id field and verify response body")
	public void testGetBrandDetailsApi() throws IOException {
		logStep("Enter valid values in request payload to create branding template at client level");
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate(String.valueOf(clientId), name, "true", color);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		brandId = createBrandApiResponse.jsonPath().getInt("brandId");
		getBrandingDetailsApiResponse = sharedInfo.getBrandDetails(brandId);
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 200);
		validation.verifyBrandingDetails_BL(getBrandingDetailsApiResponse, clientId,
				testData.getProperty("brand.id.one.org.type"), name, testData.getProperty("brand.id.one.showLogo"),
				color, testData.getProperty("brand.id.one.logo"), testData.getProperty("brand.id.one.favicon"),
				testData.getProperty("brand.id.one.emailLogo"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing system brandId in brand id field and verify response body")
	public void testGetBrandDetailsApiForSystemNode() throws IOException {
		logStep("Enter valid values in request payload to create branding template at system level");
		String name = testData.getProperty("system.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("system.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate(String.valueOf(systemId), name, "true", color);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		int systemBrandId = createBrandApiResponse.jsonPath().getInt("brandId");
		getBrandingDetailsApiResponse = sharedInfo.getBrandDetails(systemBrandId);
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 200);
		validation.verifyBrandingDetails_BL(getBrandingDetailsApiResponse, systemId, "SYSTEM", name,
				testData.getProperty("brand.id.one.showLogo"), color, testData.getProperty("brand.id.one.logo"),
				testData.getProperty("brand.id.one.favicon"), testData.getProperty("brand.id.one.emailLogo"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing enterprise brandId in brand id field and verify response body")
	public void testGetBrandDetailsApiForEnterpriseNode() throws IOException {
		logStep("Enter valid values in request payload to create branding template at enterprise level");
		String name = testData.getProperty("enterprise.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("enterprise.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate(String.valueOf(enterpriseId), name, "true", color);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		int enterpriseBrandId = createBrandApiResponse.jsonPath().getInt("brandId");
		getBrandingDetailsApiResponse = sharedInfo.getBrandDetails(enterpriseBrandId);
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 200);
		validation.verifyBrandingDetails_BL(getBrandingDetailsApiResponse, enterpriseId, "ENTERPRISE", name,
				testData.getProperty("brand.id.one.showLogo"), color, testData.getProperty("brand.id.one.logo"),
				testData.getProperty("brand.id.one.favicon"), testData.getProperty("brand.id.one.emailLogo"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practice brandId in brand id field and verify response body")
	public void testGetBrandDetailsApiForPracticeNode() throws IOException {
		logStep("Enter valid values in request payload to create branding template at practice level");
		String name = testData.getProperty("practice.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("practice.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate(String.valueOf(practiceId), name, "true", color);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		int practiceBrandId = createBrandApiResponse.jsonPath().getInt("brandId");
		getBrandingDetailsApiResponse = sharedInfo.getBrandDetails(practiceBrandId);
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 200);
		validation.verifyBrandingDetails_BL(getBrandingDetailsApiResponse, practiceId, "PRACTICE", name,
				testData.getProperty("brand.id.one.showLogo"), color, testData.getProperty("brand.id.one.logo"),
				testData.getProperty("brand.id.one.favicon"), testData.getProperty("brand.id.one.emailLogo"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing agnostic practiceId brandId in brand id field and verify response body")
	public void testGetBrandDetailsApiForAgnosticPracticeNode() throws IOException {
		logStep("Enter valid values in request payload to create branding template at agnostic practice level");
		String name = "AGNOSTIC" + testData.getProperty("practice.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("practice.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate(String.valueOf(agnosticPracticeId), name, "true",
				color);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		int agnosticPracticeBrandId = createBrandApiResponse.jsonPath().getInt("brandId");
		getBrandingDetailsApiResponse = sharedInfo.getBrandDetails(agnosticPracticeBrandId);
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 200);
		validation.verifyBrandingDetails_BL(getBrandingDetailsApiResponse, agnosticPracticeId, "PRACTICE", name,
				testData.getProperty("brand.id.one.showLogo"), color, testData.getProperty("brand.id.one.logo"),
				testData.getProperty("brand.id.one.favicon"), testData.getProperty("brand.id.one.emailLogo"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error message")
	public void testErrorResponseForInvalidUrl() throws IOException {
		getBrandingDetailsApiResponse = sharedInfo.getBrandDetailsUsingInvalidUrl(brandId);
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getBrandingDetailsApiResponse);
	}
}
