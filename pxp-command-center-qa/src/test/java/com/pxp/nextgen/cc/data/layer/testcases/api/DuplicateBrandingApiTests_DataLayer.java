// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

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
	Response fetchDuplicateBrandApiResponse, createBrandApiResponse;
	private CreateClientHierarchy_DL clientHierObj;
	int clientId, systemId, enterpriseId, ngPracticeId, agnosticPracticeId;
	public int brandId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		brandInfo = new BrandsInfo();
		validation = new Validations();
		duplicateBrandingValidations = new DuplicateBrandingValidations();
		clientHierObj = new CreateClientHierarchy_DL();
		
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		ngPracticeId = clientHierObj.createPractice_DL(enterpriseId);
		ngPracticeId = clientHierObj.createPractice_DL(enterpriseId);
		agnosticPracticeId = clientHierObj.createAgnosticPractice_DL(systemId);
	}

	public int createBrand(int orgId) {
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplateDL(String.valueOf(orgId), name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		return createBrandApiResponse.jsonPath().getInt("brandId");
	}

	@Test(priority = 0, retryAnalyzer = RetryAnalyzer.class, description = "Duplicate existing brand to another newly created client Org")
	public void testDuplicateBrandToClientOrg() throws IOException {
		brandId = createBrand(clientId);
		logStep("Duplicating Brand to newly created client org");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrand(brandId, clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 200);
	}

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Duplicate existing brand to another newly created system Org")
	public void testDuplicateBrandToSystemOrg() throws IOException {
		brandId = createBrand(clientId);
		logStep("Duplicating Brand to newly created system org");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrand(brandId, systemId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 200);
	}

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Duplicate existing brand to another newly created enterprise Org")
	public void testDuplicateBrandToEnterpriseOrg() throws IOException {
		logStep("Duplicating Brand to newly created enterprise org");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrand(brandId, enterpriseId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 200);
	}

	@Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Duplicate existing brand to another newly created ng practice Org")
	public void testDuplicateBrandToNgPracticeOrg() throws IOException {
		logStep("Duplicating Brand to newly created Ng practice org");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrand(brandId, ngPracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 200);
	}

	@Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Duplicate existing brand to another newly created ng practice Org")
	public void testDuplicateBrandToAgnosticPracticeOrg() throws IOException {
		logStep("Duplicating Brand to newly created agnostic practice org");
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrand(brandId, agnosticPracticeId);
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
		brandId = createBrand(clientId);
		fetchDuplicateBrandApiResponse = brandInfo.postDuplicateBrand(brandId,
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
		fetchDuplicateBrandApiResponse = brandInfo
				.postDuplicateBrand(Integer.parseInt(testData.getProperty("brand.id.one")), 0);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchDuplicateBrandApiResponse);
		CCUtils.assertEquals(fetchDuplicateBrandApiResponse.getStatusCode(), 400);
		duplicateBrandingValidations.verifyErrorMessageInResponseForNullOwnerId(fetchDuplicateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when duplicating brand with invalid url")
	public void testErrorMsgForInvalidUrl() throws IOException {
		logStep("Duplicating brand to org with invalid url");
		fetchDuplicateBrandApiResponse = brandInfo
				.postDuplicateBrandInvalidUrl(Integer.parseInt(testData.getProperty("brand.id.one")), 0);
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
