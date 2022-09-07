// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.Product;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetProductListApiTests_DataLayer extends BaseRest {
	private Validations validation;
	private Product product;
	Response getProductListApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		validation = new Validations();
		product = new Product();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter ecosystem=PXP and verify response body")
	public void testProductListForEcosystemPxp() throws IOException {
		getProductListApiResponse = product.getProducts_DataLayer(testData.getProperty("ecosystem.pxp"), "");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getProductListApiResponse);
		CCUtils.assertEquals(getProductListApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter ecosystem=NGE and type=PM_EHR and verify response body")
	public void testProductListForEcosystemNgeAndTypePmEhr() throws IOException {
		getProductListApiResponse = product.getProducts_DataLayer(testData.getProperty("ecosystem.nge"),
				testData.getProperty("type.pmehr"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getProductListApiResponse);
		CCUtils.assertEquals(getProductListApiResponse.getStatusCode(), 200);
		validation.verifyProductListForEcosystemNgeAndTypePmehr(getProductListApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter ecosystem=OTTO and verify response body")
	public void testProductListForEcosystemOtto() throws IOException {
		getProductListApiResponse = product.getProducts_DataLayer(testData.getProperty("ecosystem.otto"), "");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getProductListApiResponse);
		CCUtils.assertEquals(getProductListApiResponse.getStatusCode(), 200);
		validation.verifyProductListForEcosystemOtto(getProductListApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter type=PM_EHR and verify response body")
	public void testProductListForTypePmEhr() throws IOException {
		getProductListApiResponse = product.getProducts_DataLayer("", testData.getProperty("type.pmehr"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getProductListApiResponse);
		CCUtils.assertEquals(getProductListApiResponse.getStatusCode(), 200);
		validation.verifyProductListForEcosystemNgeAndTypePmehr(getProductListApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter type=PXP and verify response body")
	public void testProductListForTypePxp() throws IOException {
		getProductListApiResponse = product.getProducts_DataLayer("", testData.getProperty("type.pxp"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getProductListApiResponse);
		CCUtils.assertEquals(getProductListApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter type=non existing type and ecosystem= non existing ecosystem and verify response body")
	public void testResponseCodeForNonExistingEcosystemAndType() throws IOException {
		getProductListApiResponse = product.getProducts_DataLayer(testData.getProperty("non.existing.ecosystem"),
				testData.getProperty("non.existing.type"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getProductListApiResponse);
		CCUtils.assertEquals(getProductListApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank space in ecosystem and type field and verify response body")
	public void testErrorResponseBlankEcosystemAndTypeField() throws IOException {
		getProductListApiResponse = product.getProducts_DataLayer(" ", " ");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getProductListApiResponse);
		CCUtils.assertEquals(getProductListApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(getProductListApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of GET")
	public void testErrorForInvalidMethodCall() throws IOException {
		getProductListApiResponse = product.invalidMethodCall_DataLayer(testData.getProperty("ecosystem.nge"),
				testData.getProperty("type.pmehr"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getProductListApiResponse);
		CCUtils.assertEquals(getProductListApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethodCall(getProductListApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		getProductListApiResponse = product.invalidMethodForFetchProductList_DataLayer(
				testData.getProperty("ecosystem.nge"), testData.getProperty("type.pmehr"));
		logStep("Validating Response Information");
		CCUtils.responseTimeValidation(getProductListApiResponse);
		CCUtils.assertEquals(getProductListApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getProductListApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid ecosystem=NGO and verify 204 response code")
	public void testResponseCodeForEcosystemNgo() throws IOException {
		getProductListApiResponse = product.getProducts_DataLayer(testData.getProperty("ecosystem.ngo").trim(), "");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getProductListApiResponse);
		CCUtils.assertEquals(getProductListApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid ecosystem=INT and verify 204 response code")
	public void testResponseCodeForEcosystemInt() throws IOException {
		getProductListApiResponse = product.getProducts_DataLayer(testData.getProperty("ecosystem.int").trim(), "");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getProductListApiResponse);
		CCUtils.assertEquals(getProductListApiResponse.getStatusCode(), 200);
	}
}
