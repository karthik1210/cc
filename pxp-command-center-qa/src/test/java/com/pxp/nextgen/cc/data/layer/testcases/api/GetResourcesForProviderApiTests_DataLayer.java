// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.ResourceDetails;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetResourcesForProviderApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private ResourceInfo resourceInfo;
	Response createProviderApiResponse, createResourceApiResponse, addResourceResponse, removeResourceResponse,
			getResourceResponse;
	public static int clientId, practiceId, resourceId, systemId, enterpriseId;
	private CreateClientHierarchy_DL clientHierObj;
	public int providerId;
	private Validations validations;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		validations = new Validations();
		resourceInfo = new ResourceInfo();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		providerId = createProviderForPractice();
		resourceId = createResource(providerId);
	}

	public int createProviderForPractice() throws IOException {
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createProviderApiResponse);
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		validation.verifyCreateProviderApiResponseBody(createProviderApiResponse, firstName, lastName);
		int providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		return providerId;
	}

	public int createResource(int providerId) {
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		int resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
		return resourceId;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceId and ProviderId and verify response body")
	public void testGetResourcesForExistingPracticeIdAndProviderId() throws IOException {
		logStep("verify response body for add resources for a provider and a practice");
		List<ResourceDetails> addResourcePayload = new ArrayList<ResourceDetails>();
		addResourcePayload.add(new ResourceDetails(resourceId, true));
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(providerId, practiceId, addResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceResponse);
		CCUtils.assertEquals(addResourceResponse.getStatusCode(), 207);
		logStep("Enter existing practiceId and ProviderId and verify response body");
		getResourceResponse = sharedInfo.getResourcesForProvider(providerId, practiceId);
		validations.verifyResponseBodyOfGetResource(getResourceResponse, resourceId);
		logStep("verify response body for Remove resources for a provider and a practice");
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(providerId, practiceId, addResourcePayload);
		List<ResourceDetails> removeResourcePayload = new ArrayList<ResourceDetails>();
		removeResourcePayload.add(new ResourceDetails(resourceId, false));
		removeResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(providerId, practiceId,
				removeResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(removeResourceResponse);
		CCUtils.assertEquals(removeResourceResponse.getStatusCode(), 207);
		logStep("Enter existing practiceId and ProviderId and verify response body");
		getResourceResponse = sharedInfo.getResourcesForProvider(providerId, practiceId);
		CCUtils.assertEquals(getResourceResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceId and non existing ProviderId and verify response body")
	public void testGetResourcesForExistingPracticeIdAndNonExistingProviderId() throws IOException {
		logStep("Enter existing practiceId and non existing  ProviderId and verify response body");
		int providerId = Integer.parseInt(testData.getProperty("nonexisting.providerId"));
		getResourceResponse = sharedInfo.getResourcesForProvider(providerId, practiceId);
		CCUtils.assertEquals(getResourceResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing practiceId and existing ProviderId and verify response body")
	public void testGetResourcesForNonExistingPracticeIdAndExistingProviderId() throws IOException {
		logStep("Enter non existing practiceId and existing  ProviderId and verify response body");
		int practiceId = Integer.parseInt(testData.getProperty("nonexisting.practiceId"));
		getResourceResponse = sharedInfo.getResourcesForProvider(providerId, practiceId);
		CCUtils.assertEquals(getResourceResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing practiceId and non existing ProviderId and verify response body")
	public void testGetResourcesForNonExistingPracticeIdAndNonExistingProviderId() throws IOException {
		logStep("Enter non existing practiceId and non existing  ProviderId and verify response body");
		int practiceId = Integer.parseInt(testData.getProperty("nonexisting.practiceId"));
		int providerId = Integer.parseInt(testData.getProperty("nonexisting.providerId"));
		getResourceResponse = sharedInfo.getResourcesForProvider(providerId, practiceId);
		CCUtils.assertEquals(getResourceResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values in practiceId and  ProviderId field and verify response body")
	public void testGetResourcesForNullValues() throws IOException {
		logStep("Enter null values in practiceId and  ProviderId field and verify response body");
		getResourceResponse = sharedInfo.getResourcesForProvider_NullValue();
		CCUtils.assertEquals(getResourceResponse.getStatusCode(), 400);
	}
}