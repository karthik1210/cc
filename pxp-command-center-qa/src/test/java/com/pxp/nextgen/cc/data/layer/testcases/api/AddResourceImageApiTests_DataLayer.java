//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.SharedServiceApiValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AddResourceImageApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private SharedServiceApiValidations sharedServiceApiValidations;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId, resourceId;
	Response createProviderApiResponse, createResourceApiResponse, getResourceDetailsApiResponse,
			deleteResourceImageApiResponse, addResourceImageUrlApiResponse;;
	private ResourceInfo resourceInfo;
	private CreateClientHierarchy_DL clientHierObj;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		sharedServiceApiValidations = new SharedServiceApiValidations();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		resourceInfo = new ResourceInfo();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		logStep("Create new provider");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify add resource Url functionality")
	public void testAddImageUrlFunctionality() throws Exception {
		logStep("Get resource ImageUrl Details");
		getResourceDetailsApiResponse = sharedInfo.getResourceDetails_DL(resourceId);
		logStep("Verify imageurl is not present for selected resource");
		Boolean value = sharedServiceApiValidations.validateImageUrlValueIsNull(getResourceDetailsApiResponse);
		if (value.equals(false)) {
			logStep("delete resource imageurl");
			deleteResourceImageApiResponse = sharedInfo.deleteResourceImage_DL(resourceId);
			logStep("Resource image url is deleted");
			getResourceDetailsApiResponse = sharedInfo.getResourceDetails_DL(resourceId);
			CCUtils.assertTrue(sharedServiceApiValidations.validateImageUrlValueIsNull(getResourceDetailsApiResponse),
					"imageUrl is present");
		}
		logStep("Add Resouce image url");
		addResourceImageUrlApiResponse = sharedInfo.addResourceImage_DL(resourceId, testData.getProperty("imageurl"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceImageUrlApiResponse);
		CCUtils.assertEquals(addResourceImageUrlApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid resourceId and verify response body ")
	public void testErrorResponseForInvalidResourceId() throws Exception {
		logStep("Enter invalid resource id");
		addResourceImageUrlApiResponse = sharedInfo.addResourceImage_DL(
				Integer.parseInt(testData.getProperty("non.existing.resource.id")), testData.getProperty("imageurl"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceImageUrlApiResponse);
		CCUtils.assertEquals(addResourceImageUrlApiResponse.getStatusCode(), 400);
		sharedServiceApiValidations.verifyErrorResponseForInvalidResourceId(addResourceImageUrlApiResponse,
				Integer.parseInt(testData.getProperty("non.existing.resource.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value in image url and verify response body ")
	public void testErrorResponseForBlankSpace() throws Exception {
		logStep("Enter blank value in imageUrl field");
		addResourceImageUrlApiResponse = sharedInfo.addResourceImage_DL(resourceId, "");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceImageUrlApiResponse);
		CCUtils.assertEquals(addResourceImageUrlApiResponse.getStatusCode(), 400);
		sharedServiceApiValidations.verifyErrorResponseForImageUrlField(addResourceImageUrlApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter empty payload and verify response body ")
	public void testErrorResponseForEmptyPayload() throws Exception {
		logStep("Enter empty payload");
		addResourceImageUrlApiResponse = sharedInfo.addResourceImageWithEmptyPayload_DL(resourceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceImageUrlApiResponse);
		CCUtils.assertEquals(addResourceImageUrlApiResponse.getStatusCode(), 400);
		sharedServiceApiValidations.verifyErrorResponseForImageUrlField(addResourceImageUrlApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if POST is used instead of PATCH")
	public void testErrorForInvalidMethodCall() throws IOException {
		addResourceImageUrlApiResponse = sharedInfo.addResourceImageUsingInvalidMethodCall_DL(resourceId,
				testData.getProperty("imageurl"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceImageUrlApiResponse);
		CCUtils.assertEquals(addResourceImageUrlApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(addResourceImageUrlApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorForInvalidUrl() throws IOException {
		addResourceImageUrlApiResponse = sharedInfo.addResourceImageUsingInvalidUrl_DL(resourceId,
				testData.getProperty("imageurl"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceImageUrlApiResponse);
		CCUtils.assertEquals(addResourceImageUrlApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(addResourceImageUrlApiResponse);
	}
}
