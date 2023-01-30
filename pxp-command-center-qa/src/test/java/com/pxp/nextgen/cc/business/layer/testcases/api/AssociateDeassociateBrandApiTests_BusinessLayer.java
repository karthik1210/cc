// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.DuplicateBrandingValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AssociateDeassociateBrandApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private DuplicateBrandingValidations brandingValidations;
	private Validations validation;
	private BrandsInfo brandsInfo;
	private CreateClientHierarchy_BL clientHierObj;
	int clientId, systemId, enterpriseId, practiceId, brandId = 0;
	Response associateDisassociateBrandApiResponse, createBrandApiResponse;

	@BeforeClass
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		brandingValidations = new DuplicateBrandingValidations();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		brandsInfo = new BrandsInfo();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
	}

	public int createBrandTemplate(int orgId) {
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.org.brand.color");
		createBrandApiResponse = brandsInfo.createBrandingTemplate(String.valueOf(orgId), name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		return createBrandApiResponse.jsonPath().get("brandId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to associate and disaasociate branding template at client level")
	public void associateAndDisassociateBrandToClient() throws IOException {
		logStep("Create new branding template at Client level");
		brandId = createBrandTemplate(clientId);
		logStep("associate branding template to Client");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_BL(clientId, brandId, true);
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 200);
		logStep("disassociate Branding template to Client");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_BL(clientId, brandId, false);
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to associate and disaasociate branding template at system level")
	public void associateAndDisassociateBrandToSystem() throws IOException {
		logStep("Create new branding template at System level");
		brandId = createBrandTemplate(systemId);
		logStep("associate branding template to System");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_BL(systemId, brandId, true);
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 200);
		logStep("disasscoiate branding template to system");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_BL(systemId, brandId, false);
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to associate and disaasociate branding template at Enterprise level")
	public void associateAndDisassociateBrandToEnterprise() throws IOException {
		logStep("Create new branding template at Enterprise level");
		brandId = createBrandTemplate(enterpriseId);
		logStep("associate branding template to enterprise");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_BL(enterpriseId, brandId, true);
		logStep("Validating respone information");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 200);
		logStep("disassociate branding template to enterprise");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_BL(enterpriseId, brandId, false);
		logStep("validating response information");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to associate and disaasociate branding template at Practice level")
	public void associateAndDisassociateBrandToPractice() throws IOException {
		logStep("create new branding template at Practice level");
		brandId = createBrandTemplate(practiceId);
		logStep("associate branding template to practice");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_BL(practiceId, brandId, true);
		logStep("validating response information");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 200);
		logStep("disassociate branding template to practice");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_BL(practiceId, brandId, false);
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing Org Id and existing brand id to verify error message")
	public void testErrorMessageForNonExistingOrgId() throws Exception {
		logStep("Enter non-existing Org Id and existing brand id");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_BL(
				Integer.parseInt(testData.getProperty("non.existing.org.id")), brandId, false);
		logStep("Validating Response Body");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 400);
		brandingValidations.verifyErrorMessageInResponseForNonExistingOrgIdOfOrg(associateDisassociateBrandApiResponse,
				testData.getProperty("non.existing.org.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing org id and non-existing Brand Id to verify error message")
	public void testErrorMessageForNonExistingBrandId() throws Exception {
		logStep("Enter existing org id and non-existing Brand Id");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_BL(clientId,
				Integer.parseInt(testData.getProperty("non.existing.brand.id")), true);
		logStep("Validating Response body");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 400);
		brandingValidations.verifyErrorMessageInResponseForNonExistingBrandId(associateDisassociateBrandApiResponse,
				testData.getProperty("non.existing.brand.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing org id and Brand Id to verify error message")
	public void testErrorMessageForNonExistingOrgIdAndBrandId() throws Exception {
		logStep("Enter non-existing org id and Brand Id");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_BL(
				Integer.parseInt(testData.getProperty("non.existing.org.id")),
				Integer.parseInt(testData.getProperty("non.existing.brand.id")), true);
		logStep("Validating Response body");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 400);
		brandingValidations.verifyErrorMessageInResponseForNonExistingBrandId(associateDisassociateBrandApiResponse,
				testData.getProperty("non.existing.brand.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Org Id as null to verify error message")
	public void testErrorMessageForOrgIdAsNull() throws Exception {
		logStep("Enter Org Id as null");
		associateDisassociateBrandApiResponse = sharedInfo.associateDisassociateBrandUsingNullValue_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter empty payload and verify error message")
	public void testErrorMessageForEmptyPayload() throws Exception {
		logStep("Enter existing Client id");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrandUsingEmptyPayload_BL(clientId);
		logStep("validating Response body");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 400);
		brandingValidations.verifyErrorMessageForEmptyPayload(associateDisassociateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Hit invalid URL and verify error message")
	public void testErrorMessageForInvalidURL() throws Exception {
		logStep("Enter existing Org Id and brand id");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrandUsingInvalidUrl_BL(clientId,
				brandId, true);
		logStep("Validate Response body");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(associateDisassociateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Instead of PUT method call make POST call and verify error message")
	public void testErrorMessageForInvalidMethodCall() throws Exception {
		logStep("Enter existing Org id and brand id");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrandUsingInvalidMethodCall_BL(clientId,
				brandId, false);
		logStep("Validate response body");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(associateDisassociateBrandApiResponse);
	}
}