// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.SharedServiceApiValidations;
import io.restassured.response.Response;

public class RemoveResourceImageApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private SharedServiceApiValidations sharedServiceApiValidations;
	private CreateClientHierarchy_DL clientHierObj;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId, resourceId;
	Response createProviderApiResponse, createResourceApiResponse, getResourceDetailsApiResponse,
			deleteResourceImageApiResponse, addResourceImageUrlApiResponse;;
	private ResourceInfo resourceInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		sharedServiceApiValidations = new SharedServiceApiValidations();
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify remove resource Url functionality")
	public void testRemoveImageUrlFunctionality() throws Exception {
		logStep("Add Resouce image url");
		addResourceImageUrlApiResponse = sharedInfo.addResourceImage_DL(resourceId, testData.getProperty("imageurl"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceImageUrlApiResponse);
		CCUtils.assertEquals(addResourceImageUrlApiResponse.getStatusCode(), 200);
		logStep("Delete resource image url");
		deleteResourceImageApiResponse = sharedInfo.deleteResourceImage_DL(resourceId);
		CCUtils.responseTimeValidation(addResourceImageUrlApiResponse);
		CCUtils.assertEquals(deleteResourceImageApiResponse.getStatusCode(), 200);
		getResourceDetailsApiResponse = sharedInfo.getResourceDetails_DL(resourceId);
		CCUtils.assertTrue(sharedServiceApiValidations.validateImageUrlValueIsNull(getResourceDetailsApiResponse),
				"imageUrl is present");
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid resourceId and verify response body ")
	public void testErrorResponseForInvalidResourceId() throws Exception {
		logStep("Enter invalid resource id");
		deleteResourceImageApiResponse = sharedInfo.deleteResourceImage_DL(
				Integer.parseInt(testData.getProperty("non.existing.resource.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteResourceImageApiResponse);
		CCUtils.assertEquals(deleteResourceImageApiResponse.getStatusCode(), 400);
		sharedServiceApiValidations.verifyErrorResponseForInvalidResourceId(deleteResourceImageApiResponse,
				Integer.parseInt(testData.getProperty("non.existing.resource.id")));
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values and verify response body ")
	public void testErrorResponseForNullValues() throws Exception {
		deleteResourceImageApiResponse = sharedInfo.deleteResourceImageNullValues_DL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteResourceImageApiResponse);
		CCUtils.assertEquals(deleteResourceImageApiResponse.getStatusCode(), 400);
	}
}