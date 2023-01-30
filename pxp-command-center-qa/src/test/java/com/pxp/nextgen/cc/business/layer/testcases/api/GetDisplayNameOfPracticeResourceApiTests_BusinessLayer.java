//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.ResourceValidations;
import io.restassured.response.Response;

public class GetDisplayNameOfPracticeResourceApiTests_BusinessLayer extends BaseRest {
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId, resourceId;
	Response createProviderApiResponse, createResourceApiResponse, getResourceDetailsApiResponse;
	private ResourceValidations resourceValidation;	
	private SharedInfo sharedInfo;
	private ResourceInfo resourceInfo;
	private CreateClientHierarchy_BL clientHierObj;
	
	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		resourceInfo = new ResourceInfo();
		sharedInfo = new SharedInfo();
		resourceValidation = new ResourceValidations();
		clientHierObj = new CreateClientHierarchy_BL();
		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		resourceId = createNewResource();
	}

	public int createNewResource() {
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		return createResourceApiResponse.jsonPath().getInt("resourceId");
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get resource display details by entering correct resource id")
	public void testGetResourceDisplayName() throws IOException {
		logStep("fetch resource  display details via correct resource id and Practice id");
		getResourceDetailsApiResponse = resourceInfo
				. getDisplayNameOfPracticeResource_BL(resourceId,practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 200);
		resourceValidation.verifyGetDisplayNameOfPracticeResourcesApiResponseBody(getResourceDetailsApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get resource display details error message by entering non-existing resource id")
	public void testGetResourceDisplayNameForNonExistingResourceId() throws IOException {
		logStep("fetch resource details error message via non-existing resource id");
		getResourceDetailsApiResponse = resourceInfo
				. getDisplayNameOfPracticeResource_BL(Integer.parseInt(testData.getProperty("non.existing.resource.id")),practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 400);
		resourceValidation.verifyErrorMessageForBlankField(getResourceDetailsApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get resource display details error message by entering non-existing practice id")
	public void testGetResourceDisplayNameForNonExistingPracticeId() throws IOException {
		logStep("fetch resource details error message via non-existing practice id");
		getResourceDetailsApiResponse = resourceInfo
				. getDisplayNameOfPracticeResource_BL(resourceId,Integer.parseInt(testData.getProperty("non.existing.practice.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 400);
		resourceValidation.verifyErrorMessageForBlankField(getResourceDetailsApiResponse); 
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get resource details error message by entering null resource id and practice id")
	public void testGetResourceDisplayNameForNullResourceId() throws IOException {
		logStep("fetch resource details error message via Null values");
		getResourceDetailsApiResponse = resourceInfo.getDisplayNameOfPracticeResource_BL_NULL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 400);
	}
}