//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
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

public class AssociateDeassociateBrandApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private DuplicateBrandingValidations brandingValidations;
	private Validations validation;
	Response associateDeassociateBrandApiResponse, getBrandListApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
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
	public void testAssociateDeassociateBrandingTemplateFunctionalityAtOrgLevel(Integer orgId) throws Exception {
		logStep("Get branding list");
		getBrandListApiResponse = sharedInfo.getBrandList_DL(orgId);
		logStep("Fetch active template brandId");
		int brandId = brandingValidations.getActiveBrandId(getBrandListApiResponse);
		logStep("Change branding template status from active to inactive");
		associateDeassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_DL(orgId, brandId, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociateBrandApiResponse);
		CCUtils.assertEquals(associateDeassociateBrandApiResponse.getStatusCode(), 200);
		logStep("Get branding list");
		getBrandListApiResponse = sharedInfo.getBrandList_DL(orgId);
		logStep("Verify branding template status is changed from active to inactive");
		brandingValidations.VerifyActiveFlagStatus(getBrandListApiResponse, false, brandId);
		logStep("Change branding template status from inactive to active");
		associateDeassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_DL(orgId, brandId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociateBrandApiResponse);
		CCUtils.assertEquals(associateDeassociateBrandApiResponse.getStatusCode(), 200);
		logStep("Get branding list");
		getBrandListApiResponse = sharedInfo.getBrandList_DL(orgId);
		logStep("Verify branding template status is changed from inactive to active");
		brandingValidations.VerifyActiveFlagStatus(getBrandListApiResponse, true, brandId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing org id and verify error message")
	public void testErrorMessageForNonExistingOrgId() throws Exception {
		logStep("Enter non existing org id");
		associateDeassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_DL(
				Integer.parseInt(testData.getProperty("non.existing.org.id")),
				Integer.parseInt(testData.getProperty("brand.id")), false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociateBrandApiResponse);
		CCUtils.assertEquals(associateDeassociateBrandApiResponse.getStatusCode(), 400);
		brandingValidations.verifyErrorMessageInResponseForNonExistingOrgIdOfOrg(associateDeassociateBrandApiResponse,
				testData.getProperty("non.existing.org.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing brand id and verify error message")
	public void testErrorMessageForNonExistingBrandId() throws Exception {
		logStep("Enter non existing brand id");
		associateDeassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_DL(
				Integer.parseInt(testData.getProperty("org.client.id")),
				Integer.parseInt(testData.getProperty("non.existing.brand.id")), false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociateBrandApiResponse);
		CCUtils.assertEquals(associateDeassociateBrandApiResponse.getStatusCode(), 400);
		brandingValidations.verifyErrorMessageInResponseForNonExistingBrandId(associateDeassociateBrandApiResponse,
				testData.getProperty("non.existing.brand.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter empty payload and verify error message")
	public void testErrorMessageForBlankPayload() throws Exception {
		logStep("Enter non existing brand id");
		associateDeassociateBrandApiResponse = sharedInfo
				.associateDeassociateBrandUsingEmptyPayload_DL(Integer.parseInt(testData.getProperty("org.client.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociateBrandApiResponse);
		CCUtils.assertEquals(associateDeassociateBrandApiResponse.getStatusCode(), 400);
		brandingValidations.verifyErrorMessageForEmptyPayload(associateDeassociateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if POST is used instead of PUT")
	public void testErrorForInvalidMethodCall() throws IOException {
		logStep("Enter non existing brand id");
		associateDeassociateBrandApiResponse = sharedInfo.associateDeassociateBrandUsingInvalidMethodCall_DL(
				Integer.parseInt(testData.getProperty("org.client.id")),
				Integer.parseInt(testData.getProperty("client.brand.id")), false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociateBrandApiResponse);
		CCUtils.assertEquals(associateDeassociateBrandApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(associateDeassociateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorForInvalidUrl() throws IOException {
		logStep("Enter non existing brand id");
		associateDeassociateBrandApiResponse = sharedInfo.associateDeassociateBrandUsingInvalidUrl_DL(
				Integer.parseInt(testData.getProperty("org.client.id")),
				Integer.parseInt(testData.getProperty("client.brand.id")), false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociateBrandApiResponse);
		CCUtils.assertEquals(associateDeassociateBrandApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(associateDeassociateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "orgIdValue", description = "Verify user able to activate/deactivate in use branding template status from inactive to active")
	public void testAssociateDeassociateInUseBrandFunctionalityAtOrgLevel(Integer orgId) throws Exception {
		logStep("Get branding list");
		getBrandListApiResponse = sharedInfo.getBrandList_DL(orgId);
		logStep("Fetch active template brandId");
		int brandId = brandingValidations.getInactiveInUseBrandId(getBrandListApiResponse);
		logStep("Change branding template status from inactive to active");
		associateDeassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_DL(orgId, brandId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociateBrandApiResponse);
		CCUtils.assertEquals(associateDeassociateBrandApiResponse.getStatusCode(), 200);
		logStep("Get branding list");
		getBrandListApiResponse = sharedInfo.getBrandList_DL(orgId);
		logStep("Verify branding template status is changed from inactive to active");
		brandingValidations.VerifyActiveFlagStatus(getBrandListApiResponse, true, brandId);
	}
}
