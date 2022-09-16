// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.BrandsValidations;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class DeleteBrandImageApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private BrandsInfo brandInfo;
	private Validations validation;
	private BrandsValidations brandsValidations;
	Response getBrandImageApiResponse, updateBrandingLogoApiResponse, deleteBrandImageApiResponse,
			createBrandApiResponse, createClientApiResponse;
	int orgId, clientId, brandId;
	String namePayLoad;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		brandsValidations = new BrandsValidations();
		clientInfo = new ClientInfo();
		brandInfo = new BrandsInfo();
		validation = new Validations();
		clientId = createClient(ccUser.getClientOneType());
		brandId = createBrand(clientId);
	}

	public int createClient(String clientType) throws Exception {
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	public int createBrand(int clientId) throws Exception {
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate(String.valueOf(clientId), name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		return createBrandApiResponse.jsonPath().get("brandId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter new brandId, update brand image and delete updated imageId and verify response code")
	public void testResponseCodeForDeleteBrandImage() throws IOException {
		log("Enter new brandId & update brand image");
		URL url = ClassLoader.getSystemResource("data-driven/images/logo.png");
		File file = new File(url.getFile());
		String fileType = "image/png";
		updateBrandingLogoApiResponse = brandInfo.updateBrandLogo_BL(brandId, testData.getProperty("api.logo.type"),
				testData.getProperty("api.alt.text"), file, fileType);
		CCUtils.responseTimeValidation(updateBrandingLogoApiResponse);
		CCUtils.assertEquals(updateBrandingLogoApiResponse.getStatusCode(), 200);
		log("Fetch updated brandId");
		getBrandImageApiResponse = brandInfo.getBrandImage_BL(brandId);
		CCUtils.responseTimeValidation(getBrandImageApiResponse);
		CCUtils.assertEquals(getBrandImageApiResponse.getStatusCode(), 200);
		log("Delete updated brandImage");
		String href = getBrandImageApiResponse.jsonPath().getString("logo.href");
		deleteBrandImageApiResponse = brandInfo.deteteBrandImage_BL(brandId, href);
		CCUtils.responseTimeValidation(deleteBrandImageApiResponse);
		CCUtils.assertEquals(deleteBrandImageApiResponse.getStatusCode(), 200);
		log("Verify image get deleted");
		getBrandImageApiResponse = brandInfo.getBrandImage_BL(brandId);
		CCUtils.responseTimeValidation(getBrandImageApiResponse);
		CCUtils.assertEquals(getBrandImageApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing brandId & non-existing imageId  and verify error message")
	public void testErrorMessageForNonExistingImageId() throws IOException {
		log("Enter existing brand & Id non-existing imageid");
		String href = testData.getProperty("api.invalid.href");
		deleteBrandImageApiResponse = brandInfo.deteteBrandImage_BL(brandId, href);
		CCUtils.responseTimeValidation(deleteBrandImageApiResponse);
		CCUtils.assertEquals(deleteBrandImageApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing brandId & existing imageId  and verify error message")
	public void testErrorMessageForNonExistingBrandId() throws IOException {
		log("Enter non-existing brand & Id existing imageid");
		URL url = ClassLoader.getSystemResource("data-driven/images/logo.png");
		File file = new File(url.getFile());
		String fileType = "image/png";
		updateBrandingLogoApiResponse = brandInfo.updateBrandLogo_BL(brandId, testData.getProperty("api.logo.type"),
				testData.getProperty("api.alt.text"), file, fileType);
		CCUtils.responseTimeValidation(updateBrandingLogoApiResponse);
		CCUtils.assertEquals(updateBrandingLogoApiResponse.getStatusCode(), 200);
		log("Fetch updated brandId");
		getBrandImageApiResponse = brandInfo.getBrandImage_BL(brandId);
		CCUtils.responseTimeValidation(getBrandImageApiResponse);
		CCUtils.assertEquals(getBrandImageApiResponse.getStatusCode(), 200);
		log("Delete updated brandImage for non-existing brandId");
		String href = getBrandImageApiResponse.jsonPath().getString("logo.href");
		deleteBrandImageApiResponse = brandInfo
				.deteteBrandImage_BL(Integer.parseInt(testData.getProperty("api.nonexisting.brand.id")), href);
		CCUtils.responseTimeValidation(deleteBrandImageApiResponse);
		CCUtils.assertEquals(deleteBrandImageApiResponse.getStatusCode(), 400);
		brandsValidations.verifyBrandNotFoundErrorMessageResponse(deleteBrandImageApiResponse,
				Integer.parseInt(testData.getProperty("api.nonexisting.brand.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing brandId & non-existing imageId  and verify error message")
	public void testErrorMessageForNonExistingBrandIdAndImageId() throws IOException {
		log("Enter non-existing brand & Id non-existing imageid");
		int brandId = Integer.parseInt(testData.getProperty("api.nonexisting.brand.id"));
		String href = testData.getProperty("api.invalid.href");

		URL url = ClassLoader.getSystemResource("data-driven/images/logo.png");
		File file = new File(url.getFile());
		String fileType = "image/png";
		updateBrandingLogoApiResponse = brandInfo.updateBrandLogo_BL(brandId, testData.getProperty("api.logo.type"),
				testData.getProperty("api.alt.text"), file, fileType);
		CCUtils.responseTimeValidation(updateBrandingLogoApiResponse);

		deleteBrandImageApiResponse = brandInfo.deteteBrandImage_BL(brandId, href);
		CCUtils.responseTimeValidation(deleteBrandImageApiResponse);
		CCUtils.assertEquals(deleteBrandImageApiResponse.getStatusCode(), 400);
		brandsValidations.verifyBrandNotFoundErrorMessageResponse(deleteBrandImageApiResponse, brandId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Invalid url and verify error message")
	public void testErrorMessageForInvalidUrl() throws IOException {
		log("Enter new brandId & update brand image for invalid url");
		URL url = ClassLoader.getSystemResource("data-driven/images/logo.png");
		File file = new File(url.getFile());
		String fileType = "image/png";
		updateBrandingLogoApiResponse = brandInfo.updateBrandLogo_BL(brandId, testData.getProperty("api.logo.type"),
				testData.getProperty("api.alt.text"), file, fileType);
		CCUtils.responseTimeValidation(updateBrandingLogoApiResponse);
		CCUtils.assertEquals(updateBrandingLogoApiResponse.getStatusCode(), 200);
		log("Fetch updated brandId");
		getBrandImageApiResponse = brandInfo.getBrandImage_BL(brandId);
		CCUtils.responseTimeValidation(getBrandImageApiResponse);
		CCUtils.assertEquals(getBrandImageApiResponse.getStatusCode(), 200);
		log("Delete updated brandImage");
		String href = getBrandImageApiResponse.jsonPath().getString("logo.href");
		deleteBrandImageApiResponse = brandInfo.deteteBrandImageInvalidUrl_BL(brandId, href);
		CCUtils.responseTimeValidation(deleteBrandImageApiResponse);
		CCUtils.assertEquals(deleteBrandImageApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(deleteBrandImageApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value and verify error message")
	public void testErrorMessageForNullValue() throws IOException {
		log("Enter invalid url");
		deleteBrandImageApiResponse = brandInfo.deteteBrandImage_NullValue_BL();
		CCUtils.responseTimeValidation(deleteBrandImageApiResponse);
		CCUtils.assertEquals(deleteBrandImageApiResponse.getStatusCode(), 400);
		brandsValidations.verifyErrorMessageIfBrandIdIsNull(deleteBrandImageApiResponse);
	}
}
