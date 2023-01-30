// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.File;
import java.net.URL;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.BrandsValidations;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;

public class GetBrandImageUrlApiTests_BusinessLayer extends BaseRest {
	private BrandsInfo brandsInfo;
	private BrandsValidations brandsValidations;
	Response updateBrandingLogoApiResponse, getBrandingDetailsApiResponse, createBrandApiResponse, getBrandImageApiResponse, getBrandImageUrlApiResponse;
	int clientId, brandId;
	private CreateClientHierarchy_BL clientHierObj;
	URL url;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		brandsInfo = new BrandsInfo();
		brandsValidations = new BrandsValidations();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		brandId = createBrand(clientId);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify 200 status code and response body by entering valid imageId for LogoType")
	public void testSuccessResponseCodeToGetBrandImageUrlLogoType() throws NullPointerException, Exception {
		log("Get Brand Image Url by entering valid imageId for LogoType");
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
		Object imageId = getBrandImageApiResponse.jsonPath().get("logo.href");
		getBrandImageUrlApiResponse = brandsInfo.getBrandImageUrl_BL(imageId);
		CCUtils.responseTimeValidation(getBrandImageUrlApiResponse);
		CCUtils.assertEquals(getBrandImageUrlApiResponse.getStatusCode(), 200);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify 200 status code and response body by entering valid imageId for FaviconType")
	public void testSuccessResponseCodeToGetBrandImageUrlFaviconType() throws NullPointerException, Exception {
		log("Get Brand Image Url by entering valid imageId for FaviconType");
		URL url = ClassLoader.getSystemResource("data-driven/images/logo.png");
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
		Object imageId = getBrandImageApiResponse.jsonPath().get("favicon.href");
		getBrandImageUrlApiResponse = brandsInfo.getBrandImageUrl_BL(imageId);
		CCUtils.responseTimeValidation(getBrandImageUrlApiResponse);
		CCUtils.assertEquals(getBrandImageUrlApiResponse.getStatusCode(), 200);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify 200 status code and response body by entering valid imageId for EmailLogoType")
	public void testSuccessResponseCodeToGetBrandImageUrlEmailLogoType() throws NullPointerException, Exception {
		log("Get Brand Image Url by entering valid imageId for EmailLogoType");
		URL url = ClassLoader.getSystemResource("data-driven/images/leaf.svg");
		File file = new File(url.getFile());
		String fileType = "image/png";
		updateBrandingLogoApiResponse = brandsInfo.updateBrandLogo_BL(brandId, testData.getProperty("api.emailLogo.type"),
				testData.getProperty("api.alt.text"), file, fileType);
		CCUtils.responseTimeValidation(updateBrandingLogoApiResponse);
		CCUtils.assertEquals(updateBrandingLogoApiResponse.getStatusCode(), 200);
		log("Fetch updated brandId");
		getBrandImageApiResponse = brandsInfo.getBrandImage_BL(brandId);
		CCUtils.responseTimeValidation(getBrandImageApiResponse);
		CCUtils.assertEquals(getBrandImageApiResponse.getStatusCode(), 200);
		Object imageId = getBrandImageApiResponse.jsonPath().get("emailLogo.href");
		getBrandImageUrlApiResponse = brandsInfo.getBrandImageUrl_BL(imageId);
		CCUtils.responseTimeValidation(getBrandImageUrlApiResponse);
		CCUtils.assertEquals(getBrandImageUrlApiResponse.getStatusCode(), 200);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify 400 status code and response body by entering invalid imageId")
	public void testResponseCodeToGetBrandImageUrlWithInvalidImageId() throws NullPointerException, Exception {
		log("Get Brand Image Url by entering invalid imageId");
		getBrandImageUrlApiResponse = brandsInfo.getBrandImageUrl_BL("api.invalid.href");
		CCUtils.responseTimeValidation(getBrandImageUrlApiResponse);
		CCUtils.assertEquals(getBrandImageUrlApiResponse.getStatusCode(), 400);
		brandsValidations.verifyErrorMessageForInvaliBrandUrl(getBrandImageUrlApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify 400 status code and response body by entering imageId as null")
	public void testErrorResponseCodeToGetBrandImageUrlWithNullValue() throws NullPointerException, Exception {
		log("Get Brand Image Url by entering imageId as null");
		getBrandImageUrlApiResponse = brandsInfo.getBrandImageUrl_Null_Value_BL();
		CCUtils.responseTimeValidation(getBrandImageUrlApiResponse);
		CCUtils.assertEquals(getBrandImageUrlApiResponse.getStatusCode(), 400);
	}
	
}
