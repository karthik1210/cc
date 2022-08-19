// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
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
	Response getBrandListApiResponse, associateDisassociateBrandApiResponse;

	@BeforeClass
	public void setBaseUri() throws Exception {
		setupQaServiceRequestSpecificationBuilder();
		brandingValidations = new DuplicateBrandingValidations();
		sharedInfo = new SharedInfo();
		validation = new Validations();
	}

	@DataProvider(name = "orgId")
	public Object[] getOrgId() {
		Object[] obj = new Object[] { Integer.parseInt(testData.getProperty("org.client.id")),
				Integer.parseInt(testData.getProperty("org.system.id")),
				Integer.parseInt(testData.getProperty("org.enterprise.id")),
				Integer.parseInt(testData.getProperty("org.practice.id")) };
		return obj;
	}

	@DataProvider(name = "orgIdValue")
	public Object[] getOrgIdDetails() {
		Object[] obj = new Object[] { Integer.parseInt(testData.getProperty("org.client.one.id")),
				Integer.parseInt(testData.getProperty("org.system.one.id")),
				Integer.parseInt(testData.getProperty("org.enterprise.one.id")),
				Integer.parseInt(testData.getProperty("org.practice.one.id")) };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "orgId", description = "Verify user able to activate/deactivate branding template")
	public void testDeassociateBrandingTemplateFunctionalityAtOrgLevel(Integer orgId) throws Exception {
		logStep("Get Branding List");
		getBrandListApiResponse = sharedInfo.getBrandList_BL(orgId);
		logStep("Get Id for Active Brand template");
		int brandId = brandingValidations.getActiveBrandId(getBrandListApiResponse);
		logStep("Disassociate active brand and change it to Inactive ");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_BL(orgId, brandId, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 200);

		logStep("Get Branding List");
		getBrandListApiResponse = sharedInfo.getBrandList_BL(orgId);
		logStep("Verify Branding template status changed from Active to Inactive");
		brandingValidations.VerifyActiveFlagStatus(getBrandListApiResponse, false, brandId);
		logStep("Change branding template status from inactive to active");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_BL(orgId, brandId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 200);
		logStep("Get Branding List");
		getBrandListApiResponse = sharedInfo.getBrandList_BL(orgId);
		logStep("Verify branding template status changed from Inactive to Active");
		brandingValidations.VerifyActiveFlagStatus(getBrandListApiResponse, true, brandId);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing Org Id and verify error message")
	public void testErrorMessageForNonExistingOrgId() throws Exception {
		logStep("Enter non-existing Org Id");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_BL(
				Integer.parseInt(testData.getProperty("non.existing.org.id")),
				Integer.parseInt(testData.getProperty("brand.id")), false);
		logStep("Validating Response Body");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 400);
		brandingValidations.verifyErrorMessageInResponseForNonExistingOrgIdOfOrg(associateDisassociateBrandApiResponse,
				testData.getProperty("non.existing.org.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing Brand Id and verify error message")
	public void testErrorMessageForNonExistingBrandId() throws Exception {
		logStep("Enter non-existing Brand Id");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_BL(
				Integer.parseInt(testData.getProperty("org.client.id")),
				Integer.parseInt(testData.getProperty("non.existing.brand.id")), false);
		logStep("Validating Response body");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 400);
		brandingValidations.verifyErrorMessageInResponseForNonExistingBrandId(associateDisassociateBrandApiResponse,
				testData.getProperty("non.existing.brand.id"));

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter empty payload and verify error message")
	public void testErrorMessageForEmptyPayload() throws Exception {
		logStep("Enter existing Client id");
		associateDisassociateBrandApiResponse = sharedInfo
				.associateDeassociateBrandUsingEmptyPayload_BL(Integer.parseInt(testData.getProperty("org.client.id")));
		logStep("validating Response body");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 400);
		brandingValidations.verifyErrorMessageForEmptyPayload(associateDisassociateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Hit invalid URL and verify error message")
	public void testErrorMessageForInvalidURL() throws Exception {
		logStep("Enter existing Org Id");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrandUsingInvalidUrl_BL(
				Integer.parseInt(testData.getProperty("org.client.id")),
				Integer.parseInt(testData.getProperty("client.brand.id")), true);
		logStep("Validate Response body");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(associateDisassociateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Use call POST instead of PUT and verify error message")
	public void testErrorMessageForInvalidMethodCall() throws Exception {
		logStep("Enter existing Org id");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrandUsingInvalidMethodCall_BL(
				Integer.parseInt(testData.getProperty("org.client.id")),
				Integer.parseInt(testData.getProperty("client.brand.id")), false);
		logStep("Validate response body");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(associateDisassociateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "orgIdValue", description = "Verify user able to activate/deactivate in use branding template status from inactive to active")
	public void testAssociateDeassociateInUseBrandFunctionalityAtOrgLevel(Integer orgId) throws Exception {
		logStep("Get List of Brands");
		getBrandListApiResponse = sharedInfo.getBrandList_BL(orgId);
		logStep("Get Id for Active Brand template");
		int brandId = brandingValidations.getInactiveInUseBrandId(getBrandListApiResponse);
		logStep("Change branding template status from inactive to active");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_BL(orgId, brandId, true);
		logStep("validate response body");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 200);
		logStep("Get List of Brands");
		getBrandListApiResponse = sharedInfo.getBrandList_BL(orgId);
		logStep("Verify branding template status is changed from inactive to active");
		brandingValidations.VerifyActiveFlagStatus(getBrandListApiResponse, true, brandId);
	}

}