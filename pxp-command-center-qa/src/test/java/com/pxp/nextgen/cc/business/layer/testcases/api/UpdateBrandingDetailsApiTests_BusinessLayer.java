// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import java.io.IOException;

public class UpdateBrandingDetailsApiTests_BusinessLayer extends BaseRest {
	private BrandsInfo brandInfo;
	private Validations validation;
	Response updateBrandDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupQaServiceRequestSpecificationBuilder();
		brandInfo = new BrandsInfo();
		validation = new Validations();
	}

	@DataProvider(name = "updateBrandingValidDetails")
	public Object[] getDetails() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("client.brand.id").trim(), testData.getProperty("client.org.id").trim(),
						testData.getProperty("client.org.brand.name").trim(),
						testData.getProperty("showlogo.true").trim(), testData.getProperty("client.brand.color") },
				{ testData.getProperty("client.brand.id").trim(), testData.getProperty("client.org.id").trim(),
						testData.getProperty("client.org.brand.name").trim(),
						testData.getProperty("showlogo.false").trim(), testData.getProperty("client.brand.color") },
				{ testData.getProperty("system.brand.id").trim(), testData.getProperty("system.org.id").trim(),
						testData.getProperty("system.org.brand.name").trim(),
						testData.getProperty("showlogo.true").trim(), testData.getProperty("system.brand.color") },
				{ testData.getProperty("system.brand.id").trim(), testData.getProperty("system.org.id").trim(),
						testData.getProperty("system.org.brand.name").trim(),
						testData.getProperty("showlogo.false").trim(), testData.getProperty("system.brand.color") },
				{ testData.getProperty("enterprise.brand.id").trim(), testData.getProperty("enterprise.org.id").trim(),
						testData.getProperty("enterprise.org.brand.name").trim(),
						testData.getProperty("showlogo.true").trim(), testData.getProperty("enterprise.brand.color") },
				{ testData.getProperty("enterprise.brand.id").trim(), testData.getProperty("enterprise.org.id").trim(),
						testData.getProperty("enterprise.org.brand.name").trim(),
						testData.getProperty("showlogo.false").trim(), testData.getProperty("enterprise.brand.color") },
				{ testData.getProperty("practice.brand.id").trim(), testData.getProperty("practice.org.id").trim(),
						testData.getProperty("practice.org.brand.name").trim(),
						testData.getProperty("showlogo.true").trim(), testData.getProperty("practice.brand.color") },
				{ testData.getProperty("practice.brand.id").trim(), testData.getProperty("practice.org.id").trim(),
						testData.getProperty("practice.org.brand.name").trim(),
						testData.getProperty("showlogo.false").trim(),
						testData.getProperty("practice.brand.color") }, };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "updateBrandingValidDetails", description = "Enter existing BrandID, orgId to update brand at CLIENT, SYSTEM, ENTERPRISE, PRACTICE level")
	public void testSuccessResponseCodeForExistingBrandIdOrgIdToUpdateBranding(String[] getDetails)
			throws NullPointerException, IOException {
		logStep("Enter existing brandId, orgId");
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails(getDetails[0], getDetails[1], getDetails[2],
				getDetails[3], getDetails[4]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 200);
		validation.verifyUpdateBrandingDetails(updateBrandDetailsApiResponse, getDetails[0], getDetails[2]);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing BrandID, non-existing orgId and validate error response code")
	public void testErrorResponseCodeForExistingBrandIdNonExistingOrgId() throws NullPointerException, IOException {
		logStep("Enter existing brandId, non-existing orgId");
		String orgId = testData.getProperty("non.existing.org.id");
		String brandName = testData.getProperty("practice.org.brand.name");
		String brandId = testData.getProperty("practice.brand.id");
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails(brandId, orgId, brandName, "true",
				testData.getProperty("client.brand.color"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(updateBrandDetailsApiResponse, orgId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing BrandID, existing orgId and validate error response code")
	public void testErrorResponseCodeForNonExistingBrandIdExistingOrgId() throws NullPointerException, IOException {
		logStep("Enter non-existing BrandID, existing orgId");
		String orgId = testData.getProperty("practice.org.id");
		String brandName = testData.getProperty("practice.org.brand.name");
		String brandId = testData.getProperty("non.existing.brand.id");
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails(brandId, orgId, brandName, "true",
				testData.getProperty("client.brand.color"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidBrandId(updateBrandDetailsApiResponse,
				Integer.parseInt(testData.getProperty("non.existing.brand.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing BrandID, orgId as null and validate error response code")
	public void testErrorResponseCodeForExistingBrandIdAndOrgIdAsNull() throws NullPointerException, IOException {
		logStep("Enter existing brandId, orgId as null ");
		String brandName = testData.getProperty("client.org.brand.name");
		String brandId = testData.getProperty("client.brand.id");
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails(brandId, null, brandName, "true",
				testData.getProperty("client.brand.color"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForOrgIdAsNull(updateBrandDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, BrandID as null and validate error response code")
	public void testErrorResponseCodeForExistingOrgIdAndBrandIdAsNull() throws NullPointerException, IOException {
		logStep("Enter existing orgId, BrandID as null");
		String orgId = testData.getProperty("client.org.id");
		String brandName = testData.getProperty("client.org.brand.name");
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails(null, orgId, brandName, "true",
				testData.getProperty("client.brand.color"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 400);
		validation.verifyInvalidRequestValidation(updateBrandDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing BrandID and request payload as null and validate error response code")
	public void testErrorResponseCodeForRequestPayloadAsNull() throws NullPointerException, IOException {
		logStep("Enter existing BrandID and request payload as null");
		String brandId = testData.getProperty("client.brand.id");
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails(brandId, null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 400);
		validation.verifyInvalidRequestValidation(updateBrandDetailsApiResponse);
	}

	@Test(priority = 9, retryAnalyzer = RetryAnalyzer.class, description = "Check Version Name length validation and validate response code")
	public void versionNameFieldLengthValidation() throws IOException, NullPointerException {
		logStep("Enter existing brandId");
		String orgId = testData.getProperty("client.org.id");
		String brandId = testData.getProperty("client.brand.id");
		logStep("Version Name length validation with 50 charecters");
		String brandName = "AutoBrand".concat(CCUtils.generateAlphaNumericString(41));
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails(brandId, orgId, brandName, "true",
				testData.getProperty("client.brand.color"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 200);
		logStep("Version Name length validation with 51 charecters");
		brandName = "AutoBrand".concat(CCUtils.generateAlphaNumericString(51));
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails(brandId, orgId, brandName, "true",
				testData.getProperty("client.brand.color"));
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 400);
		validation.verifyInvalidRequestValidation(updateBrandDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check error response code for invalid Url")
	public void testErrorResponseCodeForInvalidUrl() throws NullPointerException, IOException {
		logStep("Enter existing brandId");
		String orgId = testData.getProperty("practice.org.id");
		String brandName = testData.getProperty("practice.org.brand.name");
		int brandId = Integer.parseInt(testData.getProperty("practice.brand.id"));
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetailsInvalidUrl(brandId, orgId, brandName, "true",
				testData.getProperty("practice.brand.color"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateBrandDetailsApiResponse);
	}
}
