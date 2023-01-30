// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.ResourceDetails;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AddRemoveResourcesForProviderApiTests_DataLayer extends BaseRest {

	private SharedInfo sharedInfo;
	private Validations validation;
	private ResourceInfo resourceInfo;
	Response createProviderApiResponse, createResourceApiResponse, searchResourceApiResponse, addResourceResponse,
			removeResourceResponse, getResourceResponse;
	public static int clientId, practiceId, resourceId, systemId, enterpriseId;
	public int providerId;
	private Validations validations;
	private CreateClientHierarchy_DL clientHierObj;

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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body for add resources for a provider and a practice")
	public void testAddResourcesForProvider() throws IOException {
		List<ResourceDetails> addResourcePayload = new ArrayList<ResourceDetails>();
		addResourcePayload.add(new ResourceDetails(resourceId, true));
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(providerId, practiceId, addResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceResponse);
		CCUtils.assertEquals(addResourceResponse.getStatusCode(), 207);
		validations.verifyResponseBodyOfAddRemoveResource(addResourceResponse, resourceId,
				testData.getProperty("status.success"));
		getResourceResponse = sharedInfo.getResourcesForProvider(providerId, practiceId);
		validations.verifyResponseBodyOfGetResource(getResourceResponse, resourceId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body for remove resources for a provider and a practice")
	public void testRemoveResourcesForProvider() throws IOException {
		List<ResourceDetails> addResourcePayload = new ArrayList<ResourceDetails>();
		addResourcePayload.add(new ResourceDetails(resourceId, true));
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(providerId, practiceId, addResourcePayload);
		List<ResourceDetails> removeResourcePayload = new ArrayList<ResourceDetails>();
		removeResourcePayload.add(new ResourceDetails(resourceId, false));
		removeResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(providerId, practiceId,
				removeResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(removeResourceResponse);
		CCUtils.assertEquals(removeResourceResponse.getStatusCode(), 207);
		getResourceResponse = sharedInfo.getResourcesForProvider(providerId, practiceId);
		CCUtils.assertEquals(getResourceResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body for add/remove resources for a provider and a practice when enter resourceId as zero")
	public void testErrorResourcesForProviderWhenResourceIdIsZero() throws IOException {
		List<ResourceDetails> addResourcePayload = new ArrayList<ResourceDetails>();
		addResourcePayload.add(new ResourceDetails(0, true));
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(providerId, practiceId, addResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceResponse);
		CCUtils.assertEquals(addResourceResponse.getStatusCode(), 207);
		validations.verifyResponseBodyOfAddRemoveResource(addResourceResponse, 0, testData.getProperty("status.error"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body for add/remove resources for a provider and a practice when enter non-existing practice Id")
	public void testErrorResourcesForProviderWhenProvideNonExistingPracticeId() throws IOException {
		List<ResourceDetails> addResourcePayload = new ArrayList<ResourceDetails>();
		addResourcePayload.add(new ResourceDetails(0, true));
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(providerId,
				Integer.parseInt(testData.getProperty("nonexisting.practiceId")), addResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceResponse);
		CCUtils.assertEquals(addResourceResponse.getStatusCode(), 400);
		validations.verifyErrorInResponseForNullValuesForOrgIdField(addResourceResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body for add/remove resources for a provider and a practice when enter non-existing provider Id")
	public void testErrorResourcesForProviderWhenProvideNonExistingProviderId() throws IOException {
		List<ResourceDetails> addResourcePayload = new ArrayList<ResourceDetails>();
		addResourcePayload.add(new ResourceDetails(0, true));
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(
				Integer.parseInt(testData.getProperty("nonexisting.providerId")), practiceId, addResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceResponse);
		CCUtils.assertEquals(addResourceResponse.getStatusCode(), 400);
		validations.verifyErrorInResponseForNullValuesForOrgIdField(addResourceResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body for add resources for a provider and a practice when enter resource Id as null")
	public void testErrorAddResourcesForProviderWhenEnterResourceIdNULL() throws IOException {
		List<ResourceDetails> addResourcePayload = new ArrayList<ResourceDetails>();
		addResourcePayload.add(new ResourceDetails(null, true));
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(providerId, practiceId, addResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceResponse);
		CCUtils.assertEquals(addResourceResponse.getStatusCode(), 400);
		validations.verifyAddRemoveResourceForNullResourceIdAndActive(addResourceResponse, "ResourceId cannot be null");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body for add resources for a provider and a practice when enter active as null")
	public void testAddResourcesForProviderWhenEnterActiveNULL() throws IOException {
		List<ResourceDetails> addResourcePayload = new ArrayList<ResourceDetails>();
		addResourcePayload.add(new ResourceDetails(resourceId, null));
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(providerId, practiceId, addResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceResponse);
		CCUtils.assertEquals(addResourceResponse.getStatusCode(), 400);
		validations.verifyAddRemoveResourceForNullResourceIdAndActive(addResourceResponse, "Active cannot be null");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body for add resources for a provider and a practice when enter both active and resource Id as null")
	public void testAddResourcesForProviderWhenEnterBothResourceIdAndActiveAsNULL() throws IOException {
		List<ResourceDetails> addResourcePayload = new ArrayList<ResourceDetails>();
		addResourcePayload.add(new ResourceDetails(null, null));
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(providerId, practiceId, addResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceResponse);
		CCUtils.assertEquals(addResourceResponse.getStatusCode(), 400);
		validations.verifyAddRemoveResourceForNullResourceIdAndActive(addResourceResponse,
				"ResourceId cannot be null Active cannot be null");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body for add resources for a provider and a practice when enter both provider id and practice id as null")
	public void testAddResourcesForProviderWhenEnterBothProviderIdAndPracticeIdAsNULL() throws IOException {
		List<ResourceDetails> addResourcePayload = new ArrayList<ResourceDetails>();
		addResourcePayload.add(new ResourceDetails(resourceId, true));
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider_Null(addResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceResponse);
		CCUtils.assertEquals(addResourceResponse.getStatusCode(), 400);
	}
}
