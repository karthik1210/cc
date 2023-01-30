// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class SavePracticeResourceApiTests_BusinessLayer extends BaseRest {
	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_BL clientHierObj;
	Response createProviderApiResponse, createResourceApiResponse, savePracticeResourceApiResponse,
			fetchPracticeResourceApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, resourceIdOne;
	String externalId, firstName, lastName, resourceExternalId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		resourceInfo = new ResourceInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		logStep("Create provider for resourceId");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new resource");
		resourceId = createResource();
	}

	public int createResource() {
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		String resourceName = CCUtils.generateAlphaNumericString(8);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		return createResourceApiResponse.jsonPath().getInt("resourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in all field, valid practice id, valid resource id and verify response body")
	public void testSavePracticeResourceFunctionality() throws IOException {
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Enter valid details in save practice provider payload");
		savePracticeResourceApiResponse = sharedInfo.savePracticeResource_BL(practiceId, resourceId, enDisplayName,
				esDisplayName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeResourceApiResponse);
		CCUtils.assertEquals(savePracticeResourceApiResponse.getStatusCode(), 200);
		logStep("Fetch practice provider details");
		fetchPracticeResourceApiResponse = sharedInfo.getPracticeResourceDetails_BL(practiceId, resourceId);
		CCUtils.assertEquals(fetchPracticeResourceApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForGetPracticeResource(fetchPracticeResourceApiResponse, enDisplayName,
				esDisplayName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in all field, valid practice id and invalid resource id and verify response body")
	public void testInvalidResourceId() throws IOException {
		logStep("Enter invalid resourceid");
		savePracticeResourceApiResponse = sharedInfo.savePracticeResource_BL(practiceId,
				Integer.parseInt(testData.getProperty("non.exting.resource.id")), testData.getProperty("api.en"),
				testData.getProperty("api.es"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeResourceApiResponse);
		CCUtils.assertEquals(savePracticeResourceApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(savePracticeResourceApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in all field, invalid practice id and valid resource id and verify response body")
	public void testInvalidPracticeId() throws IOException {
		logStep("Enter invalid practice id");
		savePracticeResourceApiResponse = sharedInfo.savePracticeResource_BL(
				Integer.parseInt(testData.getProperty("non.existing.practice.id")), resourceId,
				testData.getProperty("api.en"), testData.getProperty("api.es"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeResourceApiResponse);
		CCUtils.assertEquals(savePracticeResourceApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(savePracticeResourceApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null payload and valid resource id and verify response body")
	public void testSavePracticeResourceFunctionality_NullPayload() throws IOException {
		logStep("Validating response information");
		savePracticeResourceApiResponse = sharedInfo.savePracticeResourceUsingNullValues_BL();
		CCUtils.assertEquals(savePracticeResourceApiResponse.getStatusCode(), 400);
	}
}
