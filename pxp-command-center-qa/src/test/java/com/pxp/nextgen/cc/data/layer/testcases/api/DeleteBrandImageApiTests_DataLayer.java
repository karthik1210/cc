// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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
	private BrandsInfo brandsInfo;
	private Validations validation;
	private BrandsValidations brandsValidations;
	Response updateBrandImageApiResponse, getBrandImageApiResponse, deleteBrandImageApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupQaServiceRequestSpecificationBuilderForDataLayer();
		brandsValidations = new BrandsValidations();
		brandsInfo = new BrandsInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing brandId, update brand image and delete updated imageId and verify response code")
	public void testResponseCodeForDeleteBrandImage() throws IOException {
		log("Enter existing brandId & update brand image");
		int brandId = Integer.parseInt(testData.getProperty("api.brand.id.qa"));
		String logoType = testData.getProperty("api.logoType.qa");
		String altText = testData.getProperty("api.altText.qa");
		String href = testData.getProperty("api.href.qa");
		String name = testData.getProperty("api.name.qa");
		String mimeType = testData.getProperty("api.mimeType.qa");
		updateBrandImageApiResponse = brandsInfo.updateBrandImage_DL(brandId, logoType, altText, href, name, mimeType);
		CCUtils.responseTimeValidation(updateBrandImageApiResponse);
		CCUtils.assertEquals(updateBrandImageApiResponse.getStatusCode(), 200);
		log("Fetch updated brandId");
		getBrandImageApiResponse = brandsInfo.getBrandImage_DL(brandId);
		CCUtils.responseTimeValidation(getBrandImageApiResponse);
		CCUtils.assertEquals(getBrandImageApiResponse.getStatusCode(), 200);
		log("Delete updated brandImage");
		deleteBrandImageApiResponse = brandsInfo.deteteBrandImage_DL(brandId, href);
		CCUtils.responseTimeValidation(deleteBrandImageApiResponse);
		CCUtils.assertEquals(deleteBrandImageApiResponse.getStatusCode(), 200);
		log("Verify image get deleted");
		getBrandImageApiResponse = brandsInfo.getBrandImage_DL(brandId);
		CCUtils.responseTimeValidation(getBrandImageApiResponse);
		CCUtils.assertEquals(getBrandImageApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing brandId & non-existing imageId  and verify error message")
	public void testErrorMessageForNonExistingImageId() throws IOException {
		log("Enter existing brand Id non-existing imageid");
		int brandId = Integer.parseInt(testData.getProperty("api.brand.id.qa"));
		String href = testData.getProperty("api.invalid.href.qa");
		deleteBrandImageApiResponse = brandsInfo.deteteBrandImage_DL(brandId, href);
		CCUtils.responseTimeValidation(deleteBrandImageApiResponse);
		CCUtils.assertEquals(deleteBrandImageApiResponse.getStatusCode(), 400);
		brandsValidations.verifyBrandNotFoundErrorMessage(deleteBrandImageApiResponse, brandId, href);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing brandId & existing imageId  and verify error message")
	public void testErrorMessageForNonExistingBrandId() throws IOException {
		log("Enter non-existing brand Id existing imageid");
		int brandId = Integer.parseInt(testData.getProperty("api.nonexisting.brand.id.qa"));
		String href = testData.getProperty("api.href.qa");
		deleteBrandImageApiResponse = brandsInfo.deteteBrandImage_DL(brandId, href);
		CCUtils.responseTimeValidation(deleteBrandImageApiResponse);
		CCUtils.assertEquals(deleteBrandImageApiResponse.getStatusCode(), 400);
		brandsValidations.verifyBrandNotFoundErrorMessage(deleteBrandImageApiResponse, brandId, href);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing brandId & non-existing imageId  and verify error message")
	public void testErrorMessageForNonExistingBrandIdAndImageId() throws IOException {
		log("Enter non-existing brand Id non-existing imageid");
		int brandId = Integer.parseInt(testData.getProperty("api.nonexisting.brand.id.qa"));
		String href = testData.getProperty("api.invalid.href.qa");
		deleteBrandImageApiResponse = brandsInfo.deteteBrandImage_DL(brandId, href);
		CCUtils.responseTimeValidation(deleteBrandImageApiResponse);
		CCUtils.assertEquals(deleteBrandImageApiResponse.getStatusCode(), 400);
		brandsValidations.verifyBrandNotFoundErrorMessage(deleteBrandImageApiResponse, brandId, href);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Invalid url and verify error message")
	public void testErrorMessageForInvalidUrl() throws IOException {
		log("Enter invalid url");
		int brandId = Integer.parseInt(testData.getProperty("api.brand.id.qa"));
		String href = testData.getProperty("api.invalid.href.qa");
		deleteBrandImageApiResponse = brandsInfo.deteteBrandImageInvalidUrl_DL(brandId, href);
		CCUtils.responseTimeValidation(deleteBrandImageApiResponse);
		CCUtils.assertEquals(deleteBrandImageApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(deleteBrandImageApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value and verify error message")
	public void testErrorMessageForNullValue() throws IOException {
		log("Enter invalid url");
		deleteBrandImageApiResponse = brandsInfo.deteteBrandImage_NullValue_DL();
		CCUtils.responseTimeValidation(deleteBrandImageApiResponse);
		CCUtils.assertEquals(deleteBrandImageApiResponse.getStatusCode(), 400);
		brandsValidations.verifyErrorMessageIfBrandIdIsNull(deleteBrandImageApiResponse);
	}
}
