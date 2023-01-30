//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

public class AddOrRemoveResourcesForTheProviderApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	List<ResourceDetails> resourceList;
	private ResourceInfo resourceInfo;
	private CreateClientHierarchy_BL clientHierObj;
	Response createProviderApiResponse, createResourceApiResponse, updateResourcesForProvider;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId;
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
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Create provider for resourceId");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		String resourceName = CCUtils.generateAlphaNumericString(8);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		resourceId = createResourceApiResponse.jsonPath().get("resourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add resource for existing providerId and practiceId and verify response body")
	public void testToAddExistingResourceIdForExistingProviderId() throws IOException {
		logStep("Add existing resourceId for existing providerId and practiceId");
		resourceList = Arrays.asList(new ResourceDetails(resourceId, true));
		updateResourcesForProvider = sharedInfo.addOrRemoveResourcesForProvider_BL(providerId, practiceId,
				resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourcesForProvider);
		CCUtils.assertEquals(updateResourcesForProvider.getStatusCode(), 207);
		ArrayList<Integer> resourceIds = new ArrayList<Integer>();
		resourceIds.add(resourceId);
		validation.verifyAddOrRemoveResourcesApiResponseBody(updateResourcesForProvider, resourceId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Remove resource for existing providerId and practiceId and verify response body")
	public void testToRemoveExistingResourceIdForExistingProviderId() throws IOException {
		logStep("Remove existing resourceId for existing providerId and practiceId");
		resourceList = Arrays.asList(new ResourceDetails(resourceId, false));
		updateResourcesForProvider = sharedInfo.addOrRemoveResourcesForProvider_BL(providerId, practiceId,
				resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourcesForProvider);
		CCUtils.assertEquals(updateResourcesForProvider.getStatusCode(), 207);
		ArrayList<Integer> resourceIds = new ArrayList<Integer>();
		resourceIds.add(resourceId);
		validation.verifyAddOrRemoveResourcesApiResponseBody(updateResourcesForProvider, resourceId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting resource for existing providerId and practiceId  and verify error message")
	public void testToAddNonExistingResourceIdForExistingProviderId() throws IOException {
		logStep("Enter nonexisting resourceId for existing providerId and practiceId");
		resourceList = Arrays
				.asList(new ResourceDetails(Integer.parseInt(testData.getProperty("non.existing.resource.id")), true));
		updateResourcesForProvider = sharedInfo.addOrRemoveResourcesForProvider_BL(providerId, practiceId,
				resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourcesForProvider);
		CCUtils.assertEquals(updateResourcesForProvider.getStatusCode(), 207);
		validation.verifyAddOrRemoveResourcesApiResponseBody(updateResourcesForProvider,
				Integer.parseInt(testData.getProperty("non.existing.resource.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing resource for nonexisting provideId and existing practiceId and verify error message")
	public void testExistingResourceIdForNonExistingProviderId() throws IOException {
		logStep("Remove existing resourceId for existing providerId and practiceId");
		resourceList = Arrays.asList(new ResourceDetails(resourceId, true));
		updateResourcesForProvider = sharedInfo.addOrRemoveResourcesForProvider_BL(
				Integer.parseInt(testData.getProperty("nonexisting.providerId")), practiceId, resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourcesForProvider);
		CCUtils.assertEquals(updateResourcesForProvider.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateResourcesForProvider);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing resource for existing providerId and nonexisting practiceId and verify error message")
	public void testExistingResourceIdForNonExistingProviderIdAndPracticeId() throws IOException {
		logStep("Enter existing resourceId for nonexisting provider and practiceId");
		resourceList = Arrays.asList(new ResourceDetails(resourceId, true));
		updateResourcesForProvider = sharedInfo.addOrRemoveResourcesForProvider_BL(providerId,
				Integer.parseInt(testData.getProperty("nonexisting.practiceId")), resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourcesForProvider);
		CCUtils.assertEquals(updateResourcesForProvider.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateResourcesForProvider);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank input in request payload and verify error message")
	public void testErrorMessageForBlankRequestPayload() throws IOException {
		logStep("Add valid provider and practiceId");
		resourceList = Arrays.asList();
		updateResourcesForProvider = sharedInfo.addOrRemoveResourcesForProvider_BL(providerId, practiceId,
				resourceList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourcesForProvider);
		CCUtils.assertEquals(updateResourcesForProvider.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateResourcesForProvider);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values for providerId and practiceId to add Resources and verify error message")
	public void testAddToResourcesForNullValues() throws IOException {
		updateResourcesForProvider = sharedInfo.getResourcesForProviderForNullValues_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourcesForProvider);
		CCUtils.assertEquals(updateResourcesForProvider.getStatusCode(), 400);
	}
}
