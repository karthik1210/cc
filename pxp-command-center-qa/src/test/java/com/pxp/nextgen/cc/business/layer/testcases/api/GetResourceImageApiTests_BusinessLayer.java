//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.ResourceValidations;
import io.restassured.response.Response;

public class GetResourceImageApiTests_BusinessLayer extends BaseRest {
	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private ResourceValidations resourceValidation;
	private CreateClientHierarchy_BL clientHierObj;
	Response addResourceImageApiResponse, getResourceDetailsApiResponse, createProviderApiResponse, createResourceApiResponse, getResourceImageApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId;
	String imageUrl;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		resourceInfo = new ResourceInfo();
		resourceValidation = new ResourceValidations();
		sharedInfo = new SharedInfo();
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
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response code of get resource image details")
	public void testResponseCodeToGetResourceImage() throws NullPointerException, IOException {
		log("Enter valid resourceid with valid image format");
		URL url = ClassLoader.getSystemResource("data-driven/images/logo.png");
		File file = new File(url.getFile());
		String fileType = "image/png";
		addResourceImageApiResponse = resourceInfo.addResourceImage_BL(resourceId, file, fileType);
		CCUtils.responseTimeValidation(addResourceImageApiResponse);
		CCUtils.assertEquals(addResourceImageApiResponse.getStatusCode(), 200);
		getResourceDetailsApiResponse = resourceInfo.getResourceDetails_BL(resourceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 200);
		imageUrl=getResourceDetailsApiResponse.jsonPath().getString("imageUrl");
		getResourceImageApiResponse=resourceInfo.getResourceImage_BL(imageUrl);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceImageApiResponse);
		CCUtils.assertEquals(getResourceImageApiResponse.getStatusCode(), 200);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response code of get resource image details with null values")
	public void testResponseCodeToGetResourceImageWithNull() throws NullPointerException, IOException {
		logStep("Enter the null values in Image Url");
		getResourceImageApiResponse=resourceInfo.getResourceImage_Null_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceImageApiResponse);
		CCUtils.assertEquals(getResourceImageApiResponse.getStatusCode(), 400);
		resourceValidation.verifyErrorMessageForResourceWithNonExistingUrl(getResourceImageApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response code of entering non-existing image url details")
	public void testResponseCodeToGetResourceImageWithInvalidImageUrl() throws NullPointerException, IOException {
		logStep("Enter the non Existing Image Url");
		getResourceImageApiResponse=resourceInfo.getResourceImage_BL(testData.getProperty("non.existing.image.id"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceImageApiResponse);
		CCUtils.assertEquals(getResourceImageApiResponse.getStatusCode(), 400);
		resourceValidation.verifyErrorMessageForResourceWithNonExistingUrl(getResourceImageApiResponse);
	}	
}
