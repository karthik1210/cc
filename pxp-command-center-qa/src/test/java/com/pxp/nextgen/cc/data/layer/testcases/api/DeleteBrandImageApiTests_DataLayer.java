// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.BrandsValidations;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class DeleteBrandImageApiTests_DataLayer extends BaseRest {
	private BrandsInfo brandInfo;
	private Validations validation;
	private CreateClientHierarchy_DL clientHierObj;
	private BrandsValidations brandsValidations;
	Response updateBrandImageApiResponse, getBrandImageApiResponse, deleteBrandImageApiResponse, createBrandApiResponse;
	int orgId, clientId;
	String namePayLoad;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		brandsValidations = new BrandsValidations();
		brandInfo = new BrandsInfo();
		validation = new Validations();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
        logStep("New client created with client id : " + clientId);
        logStep("Create a brand for a client");
        namePayLoad = "AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        createBrandApiResponse = brandInfo.postCreateBrand(clientId, namePayLoad);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(createBrandApiResponse);
        CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200); 
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter new brandId, update brand image and delete updated imageId and verify response code")
	public void testResponseCodeForDeleteBrandImage() throws IOException {
		log("Enter new brandId & update brand image");
		int brandId = createBrandApiResponse.jsonPath().getInt("brandId");
		String logoType = testData.getProperty("api.logoType");
		String altText = testData.getProperty("api.altText");
		String href = testData.getProperty("api.href");
		String name = testData.getProperty("api.name");
		String mimeType = testData.getProperty("api.mimeType");
		updateBrandImageApiResponse = brandInfo.updateBrandImage_DL(brandId, logoType, altText, href, name, mimeType);
		CCUtils.responseTimeValidation(updateBrandImageApiResponse);
		CCUtils.assertEquals(updateBrandImageApiResponse.getStatusCode(), 200);
		log("Fetch updated brandId");
		getBrandImageApiResponse = brandInfo.getBrandImage_DL(brandId);
		CCUtils.responseTimeValidation(getBrandImageApiResponse);
		CCUtils.assertEquals(getBrandImageApiResponse.getStatusCode(), 200);
		log("Delete updated brandImage");
		deleteBrandImageApiResponse = brandInfo.deteteBrandImage_DL(brandId, href);
		CCUtils.responseTimeValidation(deleteBrandImageApiResponse);
		CCUtils.assertEquals(deleteBrandImageApiResponse.getStatusCode(), 200);
		log("Verify image get deleted");
		getBrandImageApiResponse = brandInfo.getBrandImage_DL(brandId);
		CCUtils.responseTimeValidation(getBrandImageApiResponse);
		CCUtils.assertEquals(getBrandImageApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing brandId & non-existing imageId  and verify error message")
	public void testErrorMessageForNonExistingImageId() throws IOException {
		log("Enter existing brand & Id non-existing imageid");
		int brandId = createBrandApiResponse.jsonPath().getInt("brandId");
		String href = testData.getProperty("api.invalid.href");
		deleteBrandImageApiResponse = brandInfo.deteteBrandImage_DL(brandId, href);
		CCUtils.responseTimeValidation(deleteBrandImageApiResponse);
		CCUtils.assertEquals(deleteBrandImageApiResponse.getStatusCode(), 400);
		brandsValidations.verifyBrandNotFoundErrorMessage(deleteBrandImageApiResponse, brandId, href);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing brandId & existing imageId  and verify error message")
	public void testErrorMessageForNonExistingBrandId() throws IOException {
		log("Enter non-existing brand & Id existing imageid");
		int brandId = Integer.parseInt(testData.getProperty("api.nonexisting.brand.id"));
		String href = testData.getProperty("api.href");
		deleteBrandImageApiResponse = brandInfo.deteteBrandImage_DL(brandId, href);
		CCUtils.responseTimeValidation(deleteBrandImageApiResponse);
		CCUtils.assertEquals(deleteBrandImageApiResponse.getStatusCode(), 400);
		brandsValidations.verifyBrandNotFoundErrorMessage(deleteBrandImageApiResponse, brandId, href);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing brandId & non-existing imageId  and verify error message")
	public void testErrorMessageForNonExistingBrandIdAndImageId() throws IOException {
		log("Enter non-existing brand & Id non-existing imageid");
		int brandId = Integer.parseInt(testData.getProperty("api.nonexisting.brand.id"));
		String href = testData.getProperty("api.invalid.href");
		deleteBrandImageApiResponse = brandInfo.deteteBrandImage_DL(brandId, href);
		CCUtils.responseTimeValidation(deleteBrandImageApiResponse);
		CCUtils.assertEquals(deleteBrandImageApiResponse.getStatusCode(), 400);
		brandsValidations.verifyBrandNotFoundErrorMessage(deleteBrandImageApiResponse, brandId, href);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Invalid url and verify error message")
	public void testErrorMessageForInvalidUrl() throws IOException {
		log("Enter invalid url");
		int brandId = createBrandApiResponse.jsonPath().getInt("brandId");
		String href = testData.getProperty("api.invalid.href");
		deleteBrandImageApiResponse = brandInfo.deteteBrandImageInvalidUrl_DL(brandId, href);
		CCUtils.responseTimeValidation(deleteBrandImageApiResponse);
		CCUtils.assertEquals(deleteBrandImageApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(deleteBrandImageApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value and verify error message")
	public void testErrorMessageForNullValue() throws IOException {
		log("Enter invalid url");
		deleteBrandImageApiResponse = brandInfo.deteteBrandImage_NullValue_DL();
		CCUtils.responseTimeValidation(deleteBrandImageApiResponse);
		CCUtils.assertEquals(deleteBrandImageApiResponse.getStatusCode(), 400);
		brandsValidations.verifyErrorMessageIfBrandIdIsNull(deleteBrandImageApiResponse);
	}
}
