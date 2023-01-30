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
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class RemoveProviderImageApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private SharedServiceApiValidations sharedServiceApiValidations;
	private CreateClientHierarchy_BL clientHierObj;
	Response addProviderImageApiResponse, getProviderDetailsApiResponse, createProviderApiResponse,
			deleteProviderImageApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, providerIdTwo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		sharedServiceApiValidations = new SharedServiceApiValidations();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		providerId = createProvider();
		providerIdTwo = createProvider();
	}

	public int createProvider() {
		logStep("Create new provider");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		return createProviderApiResponse.jsonPath().getInt("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Delete provider image using providerId  and verify response code")
	public void testResponseCodeToDeleteProviderImageWithValidProviderId() throws NullPointerException, IOException {
		logStep("Enter valid providerid with valid image format");
		URL url = ClassLoader.getSystemResource("data-driven/images/TestImage_JPG_Format.jpg");
		File file = new File(url.getFile());
		String fileType = "image/png";
		addProviderImageApiResponse = sharedInfo.addProviderImage_BL(providerId, file, fileType);
		CCUtils.assertEquals(addProviderImageApiResponse.getStatusCode(), 200);
		logStep("Delete provider image");
		deleteProviderImageApiResponse = sharedInfo.deleteProviderImage_BL(providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteProviderImageApiResponse);
		CCUtils.assertEquals(deleteProviderImageApiResponse.getStatusCode(), 200);
		logStep("Fetch provider details using provider id");
		getProviderDetailsApiResponse = sharedInfo.getProviderDetails(clientId, providerId);
		CCUtils.assertEquals(getProviderDetailsApiResponse.getStatusCode(), 200);
		logStep("Verify imageurl is not present for selected provider");
		Boolean value = sharedServiceApiValidations.validateImageUrlValueIsNull(getProviderDetailsApiResponse);
		CCUtils.assertEquals(value, true);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify image is not present for provider.Delete provider image using providerId and verify response code")
	public void testErrorResponseCodeForInvalidImage() throws NullPointerException, IOException {
		logStep("Fetch provider details using provider id");
		getProviderDetailsApiResponse = sharedInfo.getProviderDetails(clientId, providerIdTwo);
		CCUtils.assertEquals(getProviderDetailsApiResponse.getStatusCode(), 200);
		logStep("Verify imageurl is not present for selected provider");
		Boolean value = sharedServiceApiValidations.validateImageUrlValueIsNull(getProviderDetailsApiResponse);
		CCUtils.assertEquals(value, true);
		logStep("Delete provider image");
		deleteProviderImageApiResponse = sharedInfo.deleteProviderImage_BL(providerIdTwo);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteProviderImageApiResponse);
		CCUtils.assertEquals(deleteProviderImageApiResponse.getStatusCode(), 400);
		sharedServiceApiValidations.verifyErrorResponseForImageField(deleteProviderImageApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid provider id in providerId field and verify response code")
	public void testErrorResponseCodeForInvalidProviderId() throws NullPointerException, IOException {
		logStep("Delete provider image");
		int nonExistingProviderId = Integer.parseInt(testData.getProperty("non.existing.provider.id"));
		deleteProviderImageApiResponse = sharedInfo.deleteProviderImage_BL(nonExistingProviderId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteProviderImageApiResponse);
		CCUtils.assertEquals(deleteProviderImageApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForNonexistingproviderId(deleteProviderImageApiResponse,
				nonExistingProviderId);
	}
}
