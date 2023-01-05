// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

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
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class UpdateBrandImageApiTests_BusinessLayer extends BaseRest {
	private BrandsInfo brandsInfo;
	private Validations validation;
	Response updateBrandingLogoApiResponse, getBrandingDetailsApiResponse, createBrandApiResponse,
			createClientApiResponse, getBrandImageApiResponse;
	private ClientInfo clientInfo;
	int clientId, brandId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		brandsInfo = new BrandsInfo();
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
				testData.getProperty("api.client.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	public int createBrand(int clientId) throws Exception {
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandsInfo.createBrandingTemplate(String.valueOf(clientId), name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		return createBrandApiResponse.jsonPath().get("brandId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update  brand image in  logo type and verify response code")
	public void testResponseCodeForUpdateBrandImageInLogoType() throws NullPointerException, Exception {
		log("Update brand image for brandId in  logo type");
		URL url = ClassLoader.getSystemResource("data-driven/images/leaf.svg");
		File file = new File(url.getFile());
		String fileType = "image/png";
		updateBrandingLogoApiResponse = brandsInfo.updateBrandLogo_BL(brandId, testData.getProperty("api.logo.type"),
				testData.getProperty("api.alt.text"), file, fileType);
		CCUtils.responseTimeValidation(updateBrandingLogoApiResponse);
		CCUtils.assertEquals(updateBrandingLogoApiResponse.getStatusCode(), 200);
		log("Fetch updated brandId");
		getBrandImageApiResponse = brandsInfo.getBrandImage_BL(brandId);
		CCUtils.responseTimeValidation(getBrandImageApiResponse);
		CCUtils.assertEquals(getBrandImageApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update  brand image in  favicon type and verify response code")
	public void testResponseCodeForUpdateBrandImageInFaviconType() throws NullPointerException, Exception {
		log("Update brand image for brandId in  favicon type");
		URL url = ClassLoader.getSystemResource("data-driven/images/leaf.svg");
		File file = new File(url.getFile());
		String fileType = "image/png";
		updateBrandingLogoApiResponse = brandsInfo.updateBrandLogo_BL(brandId, testData.getProperty("api.favicon.type"),
				testData.getProperty("api.alt.text"), file, fileType);
		CCUtils.responseTimeValidation(updateBrandingLogoApiResponse);
		CCUtils.assertEquals(updateBrandingLogoApiResponse.getStatusCode(), 200);
		log("Fetch updated brandId");
		getBrandImageApiResponse = brandsInfo.getBrandImage_BL(brandId);
		CCUtils.responseTimeValidation(getBrandImageApiResponse);
		CCUtils.assertEquals(getBrandImageApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update  brand image in  emailLogo type and verify response code")
	public void testResponseCodeForUpdateBrandImageInPatientCommunicationsLogoType()
			throws NullPointerException, Exception {
		log("Update brand image for brandId in  favicon type");
		URL url = ClassLoader.getSystemResource("data-driven/images/leaf.svg");
		File file = new File(url.getFile());
		String fileType = "image/png";
		updateBrandingLogoApiResponse = brandsInfo.updateBrandLogo_BL(brandId,
				testData.getProperty("api.emailLogo.type"), testData.getProperty("api.alt.text"), file, fileType);
		CCUtils.responseTimeValidation(updateBrandingLogoApiResponse);
		CCUtils.assertEquals(updateBrandingLogoApiResponse.getStatusCode(), 200);
		log("Fetch updated brandId");
		getBrandImageApiResponse = brandsInfo.getBrandImage_BL(brandId);
		CCUtils.responseTimeValidation(getBrandImageApiResponse);
		CCUtils.assertEquals(getBrandImageApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update  brand image  for invalid url and verify error message")
	public void testResponseCodeForUpdateBrandImageForInvalidUrl() throws IOException {
		log("Update  brand image  for invalid url");
		URL url = ClassLoader.getSystemResource("data-driven/images/leaf.svg");
		File file = new File(url.getFile());
		String fileType = "image/png";
		updateBrandingLogoApiResponse = brandsInfo.updateBrandLogo_InvalidUrl_BL(brandId,
				testData.getProperty("api.logo.type"), testData.getProperty("api.alt.text"), file, fileType);
		CCUtils.responseTimeValidation(updateBrandingLogoApiResponse);
		CCUtils.assertEquals(updateBrandingLogoApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateBrandingLogoApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update  brand image  for null brandId and verify error message")
	public void testResponseCodeForUpdateBrandImageForNullBrandId() throws IOException {
		log("Update brand image for null brandId");
		URL url = ClassLoader.getSystemResource("data-driven/images/logo.png");
		File file = new File(url.getFile());
		String fileType = "image/png";
		updateBrandingLogoApiResponse = brandsInfo.updateBrandLogo_Null_BrandId_BL(
				testData.getProperty("api.logo.type"), testData.getProperty("api.alt.text"), file, fileType);
		CCUtils.assertEquals(updateBrandingLogoApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageIfClientIdIsNull(updateBrandingLogoApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update  brand image Non-existing BrandId and verify error message")
	public void testResponseCodeForUpdateBrandImageForNonExistingBrandId() throws IOException {
		log("Update  brand image  for Non-existing BrandId");
		URL url = ClassLoader.getSystemResource("data-driven/images/logo.png");
		File file = new File(url.getFile());
		String fileType = "image/png";
		updateBrandingLogoApiResponse = brandsInfo.updateBrandLogo_BL(
				Integer.parseInt(testData.getProperty("non.existing.brand.id")), testData.getProperty("api.logo.type"),
				testData.getProperty("api.alt.text"), file, fileType);
		CCUtils.responseTimeValidation(updateBrandingLogoApiResponse);
		CCUtils.assertEquals(updateBrandingLogoApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidBrandId(updateBrandingLogoApiResponse,
				Integer.parseInt(testData.getProperty("non.existing.brand.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update  invalid brand image in  logo type and verify response code")
	public void testResponseCodeForUpdateInValidBrandImage() throws NullPointerException, Exception {
		log("Update brand image for brandId in  logo type");
		URL url = ClassLoader.getSystemResource("data-driven/images/Upload.pdf");
		File file = new File(url.getFile());
		String fileType = "application/pdf";
		updateBrandingLogoApiResponse = brandsInfo.updateBrandLogo_BL(brandId, testData.getProperty("api.logo.type"),
				testData.getProperty("api.alt.text"), file, fileType);
		CCUtils.responseTimeValidation(updateBrandingLogoApiResponse);
		CCUtils.assertEquals(updateBrandingLogoApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidImage(updateBrandingLogoApiResponse);
	}
}
