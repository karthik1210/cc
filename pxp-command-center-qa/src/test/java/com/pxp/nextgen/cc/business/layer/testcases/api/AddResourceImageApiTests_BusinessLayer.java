//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.ResourceValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AddResourceImageApiTests_BusinessLayer extends BaseRest {
	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private ResourceValidations resourceValidation;
	private Validations validation;
	private CreateClientHierarchy_BL clientHierObj;
	Response addResourceImageApiResponse, getResourceDetailsApiResponse, createProviderApiResponse, createResourceApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		resourceInfo = new ResourceInfo();
		resourceValidation = new ResourceValidations();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		logStep("Create new provider");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");

		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
	}

	@DataProvider(name = "getImage")
	public Object[] getImageFile() {
		Object[][] obj = new Object[][] { { "logo.png" }, { "TestImage_GIF_Format.gif" },
				{ "TestImage_JPEG_Format.jpeg" }, { "TestImage_JPG_Format.jpg" }, { "TestImage_RAW_Format.raw" },
				{ "TestImage_TIF_Format.tif" }, { "TestImage_TIFF_Format.tiff" } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getImage", description = "Enter valid resourceid with valid image format to add resource image and verify response code")
	public void testResponseCodeToAddResourceImageWithValidResourceId(String getImageValue)
			throws NullPointerException, IOException {
		log("Enter valid resourceid with valid image format");
		URL url = ClassLoader.getSystemResource("data-driven/images/" + getImageValue);
		File file = new File(url.getFile());
		String fileType = "image/png";
		addResourceImageApiResponse = resourceInfo.addResourceImage_BL(resourceId, file, fileType);
		CCUtils.responseTimeValidation(addResourceImageApiResponse);
		CCUtils.assertEquals(addResourceImageApiResponse.getStatusCode(), 200);
		logStep("fetch resource details via correct resource id");
		getResourceDetailsApiResponse = resourceInfo.getResourceDetails_BL(resourceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 200);
		resourceValidation.verifyResponseBodyForExistingResourceId(getResourceDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid resourceid with invalid image format to add resource image and verify response code")
	public void testResponseCodeToAddResourceImageWithInvalidImageFormat() throws IOException {
		log("Enter valid resourceid with invalid image format");
		URL url = ClassLoader.getSystemResource("data-driven/images/Upload.pdf");
		File file = new File(url.getFile());
		String fileType = "application/pdf";
		addResourceImageApiResponse = resourceInfo.addResourceImage_BL(resourceId, file, fileType);
		CCUtils.responseTimeValidation(addResourceImageApiResponse);
		CCUtils.assertEquals(addResourceImageApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing resourceid with valid image format to add resource image and verify response code")
	public void testResponseCodeToAddResourceImageWithNonExistingResourceId() throws IOException {
		log("Enter non-existing resourceid with valid image format");
		URL url = ClassLoader.getSystemResource("data-driven/images/logo.png");
		File file = new File(url.getFile());
		int nonExistingResourceId = Integer.parseInt(testData.getProperty("non.existing.resource.id"));
		String fileType = "image/png";
		addResourceImageApiResponse = resourceInfo.addResourceImage_BL(nonExistingResourceId, file, fileType);
		CCUtils.responseTimeValidation(addResourceImageApiResponse);
		CCUtils.assertEquals(addResourceImageApiResponse.getStatusCode(), 400);
		resourceValidation.verifyErrorMessageInResponseBodyForNonExistingResourceId(addResourceImageApiResponse,
				nonExistingResourceId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error message")
	public void testErrorResponseForInvalidUrl() throws IOException {
		log("Enter valid resourceid with valid image format");
		URL url = ClassLoader.getSystemResource("data-driven/images/logo.png");
		File file = new File(url.getFile());
		addResourceImageApiResponse = resourceInfo.addResourceImageInvalidUrl_BL(resourceId, file);
		CCUtils.responseTimeValidation(addResourceImageApiResponse);
		CCUtils.assertEquals(addResourceImageApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(addResourceImageApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter PUT instead of POST and verify response code")
	public void testErrorMessageForInvalidMethodCall() throws IOException {
		log("Enter valid resourceid with valid image format");
		URL url = ClassLoader.getSystemResource("data-driven/images/logo.png");
		File file = new File(url.getFile());
		addResourceImageApiResponse = resourceInfo.addResourceImageInvalidMethodCall_BL(resourceId, file);
		CCUtils.responseTimeValidation(addResourceImageApiResponse);
		CCUtils.assertEquals(addResourceImageApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(addResourceImageApiResponse);
	}
}
