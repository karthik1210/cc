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

public class UploadBrandLogoApiTests_DataLayer extends BaseRest {

	private BrandsInfo brandsInfo;
	Response createClientApiResponse, createBrandApiResponse, updateBrandImageApiResponse;
	public int orgId;
	public int brandId;
	public String namePayLoad;
	private Validations validation;
	private BrandsValidations brandsValidations;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		brandsInfo = new BrandsInfo();
		validation = new Validations();
		brandsValidations = new BrandsValidations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response code when creating a new brand logo")
	public void testResponseCodeForCreateBrandLogo() throws IOException {
		log("Create new brandId & update brand image");
		int brandId = Integer.parseInt(testData.getProperty("created.brand.id"));
		String logoType = testData.getProperty("api.logoType");
		String altText = testData.getProperty("api.altText");
		String href = testData.getProperty("api.href");
		String name = testData.getProperty("api.name");
		String mimeType = testData.getProperty("api.mimeType");
		updateBrandImageApiResponse = brandsInfo.updateBrandImage_DL(brandId, logoType, altText, href, name, mimeType);
		CCUtils.responseTimeValidation(updateBrandImageApiResponse);
		CCUtils.assertEquals(updateBrandImageApiResponse.getStatusCode(), 200);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter new brandId, update brand image and delete updated imageId and verify response code")
	public void testErrorResponseCodeForCreateBrandLogoForNonExistingBrandId() throws IOException {
		int brandId = Integer.parseInt(testData.getProperty("non.existing.brand.id"));
		String logoType = testData.getProperty("api.logoType");
		String altText = testData.getProperty("api.altText");
		String href = testData.getProperty("api.href");
		String name = testData.getProperty("api.name");
		String mimeType = testData.getProperty("api.mimeType");
		updateBrandImageApiResponse = brandsInfo.updateBrandImage_DL(brandId, logoType, altText, href, name, mimeType);
		CCUtils.responseTimeValidation(updateBrandImageApiResponse);
		CCUtils.assertEquals(updateBrandImageApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidBrandId(updateBrandImageApiResponse, brandId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if POST is used instead of PATCH")
	public void testErrorForInvalidMethodCall() throws IOException {
		int brandId = Integer.parseInt(testData.getProperty("created.brand.id"));
		String logoType = testData.getProperty("api.logoType");
		String altText = testData.getProperty("api.altText");
		String href = testData.getProperty("api.href");
		String name = testData.getProperty("api.name");
		String mimeType = testData.getProperty("api.mimeType");
		updateBrandImageApiResponse = brandsInfo.updateBrandImage_DL_Invalid_Method_Call(brandId, logoType, altText,
				href, name, mimeType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandImageApiResponse);
		CCUtils.assertEquals(updateBrandImageApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPatchMethodCall(updateBrandImageApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorForInvalidUrl() throws IOException {
		int brandId = Integer.parseInt(testData.getProperty("created.brand.id"));
		String logoType = testData.getProperty("api.logoType");
		String altText = testData.getProperty("api.altText");
		String href = testData.getProperty("api.href");
		String name = testData.getProperty("api.name");
		String mimeType = testData.getProperty("api.mimeType");
		updateBrandImageApiResponse = brandsInfo.updateBrandImage_DL_Invalid_Url(brandId, logoType, altText, href, name,
				mimeType);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandImageApiResponse);
		CCUtils.assertEquals(updateBrandImageApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateBrandImageApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template with null values in orgId field and verify error response code")
	public void testErrorMessageCreateBrandLogoWithAllFieldAsNull() throws IOException {
		int brandId = Integer.parseInt(testData.getProperty("created.brand.id"));
		logStep("Enter null values in orgId field in request payload");
		updateBrandImageApiResponse = brandsInfo.updateBrandImage_DL(brandId,null, null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandImageApiResponse);
		CCUtils.assertEquals(updateBrandImageApiResponse.getStatusCode(), 400);
		brandsValidations.verifyErrorMessageIfCreateBrandLogoResponseBodyNull(updateBrandImageApiResponse);
	}
}
