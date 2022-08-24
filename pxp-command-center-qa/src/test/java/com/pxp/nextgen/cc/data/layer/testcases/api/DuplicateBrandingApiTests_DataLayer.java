// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.DuplicateBrandingValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class DuplicateBrandingApiTests_DataLayer extends BaseRest {

	private BrandsInfo brandInfo;
	private Validations validation;
	private DuplicateBrandingValidations duplicateBrandingValidations;
	Response fetchDuplicateBrandApiResponse, createClientApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupQaServiceRequestSpecificationBuilderForDataLayer();
		brandInfo = new BrandsInfo();
		validation = new Validations();
		duplicateBrandingValidations = new DuplicateBrandingValidations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Duplicate existing brand to another existing Org")
	public void testDuplicateBrandToOrg() throws IOException {
		logStep("Duplicating Brand to existing Org");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrand(
				Integer.parseInt(testData.getProperty("brand.id")),
				Integer.parseInt(testData.getProperty("owner.id.org")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when we enter non-existing Brand Id when duplicating brand")
	public void testErrorMsgWhenEnterNonExistingBrandId() throws IOException {
		logStep("Duplicating non-existing brand to org");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrand(
				Integer.parseInt(testData.getProperty("non.existing.brand.id")),
				Integer.parseInt(testData.getProperty("owner.id.org")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 400);
		duplicateBrandingValidations.verifyErrorMessageInResponseForNonExistingBrandId(fetchDuplicateBrandApiResponse,
				testData.getProperty("non.existing.brand.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when we enter non-existing owner id when duplicating brand to Org")
	public void testErrorMsgWhenEnterNonExistingOwnerIdOfOrg() throws IOException {
		logStep("Duplicating brand to non-existing org id");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrand(
				Integer.parseInt(testData.getProperty("brand.id")),
				Integer.parseInt(testData.getProperty("non.existing.owner.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 400);
		duplicateBrandingValidations.verifyErrorMessageInResponseForNonExistingOrgIdOfOrg(
				fetchDuplicateBrandApiResponse, testData.getProperty("non.existing.owner.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when we enter null owner Id while duplicating brand")
	public void testErrorMsgWhenEnterNullownerId() throws IOException {
		logStep("Duplicating brand to owner type value as either client or org but owner id as null");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrand(
				Integer.parseInt(testData.getProperty("brand.id.one")), 0);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 400);
		duplicateBrandingValidations.verifyErrorMessageInResponseForNullOwnerId(fetchDuplicateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when duplicating brand with invalid url")
	public void testErrorMsgForInvalidUrl() throws IOException {
		logStep("Duplicating brand to org with invalid url");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrandInvalidUrl(
				Integer.parseInt(testData.getProperty("brand.id.one")), 0);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchDuplicateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when duplicating brand with incorrect HTTP method")
	public void testErrorMsgForInvalidMethodCall() throws IOException {
		logStep("Duplicating brand to org with invalid HTTP method call as PATCH");
		fetchDuplicateBrandApiResponse = brandInfo.duplicateBrandInvalidMethodCall(
				Integer.parseInt(testData.getProperty("brand.id.one")),
				Integer.parseInt(testData.getProperty("owner.id.org")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethodCall(fetchDuplicateBrandApiResponse);
	}
}
