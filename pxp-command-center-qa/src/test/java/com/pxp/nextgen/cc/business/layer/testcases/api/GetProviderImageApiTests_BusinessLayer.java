//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.SharedServiceApiValidations;
import io.restassured.response.Response;

public class GetProviderImageApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private CreateClientHierarchy_BL clientHierObj;
	private SharedServiceApiValidations sharedServiceApiValidations;
	Response addProviderImageApiResponse, getProviderDetailsApiResponse, getProviderImageApiResponse, createProviderApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId;
	
	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		sharedServiceApiValidations = new SharedServiceApiValidations();
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

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get provider image using valid image id and verify response code")
	public void testGetProviderImageFunctionalityUsingValidImageId() throws NullPointerException, IOException {
		logStep("Enter valid providerid with valid image format");
		URL url = ClassLoader.getSystemResource("data-driven/images/TestImage_JPG_Format.jpg");
		File file = new File(url.getFile());
		String fileType = "image/png";
		addProviderImageApiResponse = sharedInfo.addProviderImage_BL(providerId, file, fileType);
		CCUtils.responseTimeValidation(addProviderImageApiResponse);
		CCUtils.assertEquals(addProviderImageApiResponse.getStatusCode(), 200);
		logStep("fetch provider details using correct provider id");
		getProviderDetailsApiResponse = sharedInfo.getProviderDetails(clientId, providerId);
		CCUtils.assertEquals(getProviderDetailsApiResponse.getStatusCode(), 200);
		logStep("Get provider image using valid image id");
		String imageId = sharedServiceApiValidations.getImageUrl(getProviderDetailsApiResponse);
		getProviderImageApiResponse = sharedInfo.getProviderImage_BL(imageId);
		CCUtils.assertEquals(getProviderImageApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get provider image using invalid image id and verify response code")
	public void testGetProviderImageFunctionalityUsingInvalidImageId() throws NullPointerException, IOException {
		logStep("Get provider image using invalid image id");
		String imageId = testData.getProperty("non.existing.image.id");
		getProviderImageApiResponse = sharedInfo.getProviderImage_BL(imageId);
		CCUtils.assertEquals(getProviderImageApiResponse.getStatusCode(), 400);
		sharedServiceApiValidations.verifyErrorResponseForInvalidImageId(getProviderImageApiResponse);
	}

}
