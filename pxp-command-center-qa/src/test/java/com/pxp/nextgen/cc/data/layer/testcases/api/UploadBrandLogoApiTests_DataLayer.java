// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
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

public class UploadBrandLogoApiTests_DataLayer extends BaseRest {
	private BrandsInfo brandsInfo;
	private ClientInfo clientInfo;
	Response updateBrandImageApiResponse, createClientApiResponse, createBrandApiResponse;
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
		clientInfo = new ClientInfo();
	}

	public int createClient() throws NullPointerException, IOException {
		String clientName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(30))));
		String clientSapNumber = CCUtils
				.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.client.sapnumber")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(25))));
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"));
		return createClientApiResponse.jsonPath().getInt("clientId");
	}

	public int createBrand(int orgId) {
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandsInfo.createBrandingTemplateDL(String.valueOf(orgId), name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		return createBrandApiResponse.jsonPath().getInt("brandId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response code when creating a new brand logo")
	public void testResponseCodeForCreateBrandLogo() throws IOException {
		log("Create new brandId & update brand image");
		orgId = createClient();
		brandId = createBrand(orgId);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PATCH is used instead of POST")
	public void testErrorForInvalidMethodCall() throws IOException {
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url to check upload brand logo functionality and validate error status code")
	public void testErrorForInvalidUrl() throws IOException {
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
		logStep("Enter null values in orgId field in request payload");
		updateBrandImageApiResponse = brandsInfo.updateBrandImage_DL(brandId, null, null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandImageApiResponse);
		CCUtils.assertEquals(updateBrandImageApiResponse.getStatusCode(), 400);
		brandsValidations.verifyErrorMessageIfCreateBrandLogoResponseBodyNull(updateBrandImageApiResponse);
	}
}
