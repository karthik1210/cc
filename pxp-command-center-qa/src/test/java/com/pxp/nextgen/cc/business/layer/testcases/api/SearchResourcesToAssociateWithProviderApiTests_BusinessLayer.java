//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.Arrays;
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

public class SearchResourcesToAssociateWithProviderApiTests_BusinessLayer extends BaseRest {
	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_BL clientHierObj;
	List<ResourceDetails> resourceList;
	Response createProviderApiResponse, createResourceApiResponse, searchResourcesToAssociateWithProviderApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId;
	String clientName, clientSapNumber, externalId, firstName, lastName, resourceExternalId, resourceName;

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
		resourceId = createResource();
		deassociateProviderToResource();
	}

	public int createResource() {
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		resourceName = "Res" + CCUtils.generateAlphaNumericString(8);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		return createResourceApiResponse.jsonPath().getInt("resourceId");
	}

	public void deassociateProviderToResource() {
		resourceList = Arrays.asList(new ResourceDetails(resourceId, false));
		createResourceApiResponse = sharedInfo.addOrRemoveResourcesForProvider_BL(providerId, practiceId, resourceList);
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 207);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing providerId, practiceid, resource Name to search resources and verify response body")
	public void testToSearchResourcesWithExistingProviderIdPracticeIdResourceName()
			throws NullPointerException, IOException {
		logStep("Enter existing providerId, practiceid, resource Name");
		searchResourcesToAssociateWithProviderApiResponse = sharedInfo
				.searchResourcesToAssociateWithProvider_BL(providerId, practiceId, resourceName);
		CCUtils.assertEquals(searchResourcesToAssociateWithProviderApiResponse.getStatusCode(), 200);
		validation.verifySearchResourcesToAssociateWithProviderApiResponseBody(
				searchResourcesToAssociateWithProviderApiResponse, resourceId, resourceName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing providerId, existing practiceid, existing resource Name to search resources and verify response body")
	public void testToSearchResourcesWithNonExistingProviderIdExistingPracticeIdResourceName()
			throws NullPointerException, IOException {
		logStep("Enter non-existing providerId, existing practiceid, existing resource Name");
		searchResourcesToAssociateWithProviderApiResponse = sharedInfo.searchResourcesToAssociateWithProvider_BL(
				Integer.parseInt(testData.getProperty("non.existing.provider.id")), practiceId, resourceName);
		CCUtils.assertEquals(searchResourcesToAssociateWithProviderApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing providerId, non-existing practiceid, existing resource Name to search resources and verify response body")
	public void testToSearchResourcesWithExistingProviderIdResourceNameNonExistingPracticeId()
			throws NullPointerException, IOException {
		logStep("Enter existing providerId, non-existing practiceid, existing resource Name");
		searchResourcesToAssociateWithProviderApiResponse = sharedInfo.searchResourcesToAssociateWithProvider_BL(
				providerId, Integer.parseInt(testData.getProperty("non.existing.practice.id")), resourceName);
		CCUtils.assertEquals(searchResourcesToAssociateWithProviderApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing providerId, practiceid, non-existing resource Name to search resources and verify response body")
	public void testToSearchResourcesWithExistingProviderIdPracticeIdNonExistingResourceName()
			throws NullPointerException, IOException {
		logStep("Enter existing providerId, practiceid, non-existing resource Name");
		searchResourcesToAssociateWithProviderApiResponse = sharedInfo
				.searchResourcesToAssociateWithProvider_BL(providerId, practiceId, testData.getProperty("api.name"));
		CCUtils.assertEquals(searchResourcesToAssociateWithProviderApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing providerId, practiceid, resource Name to search resources and verify response body")
	public void testToSearchResourcesWithNonExistingProviderIdPracticeIdResourceName()
			throws NullPointerException, IOException {
		logStep("Enter non-existing providerId, practiceid, resource Name");
		searchResourcesToAssociateWithProviderApiResponse = sharedInfo.searchResourcesToAssociateWithProvider_BL(
				Integer.parseInt(testData.getProperty("non.existing.provider.id")),
				Integer.parseInt(testData.getProperty("non.existing.practice.id")), testData.getProperty("api.name"));
		CCUtils.assertEquals(searchResourcesToAssociateWithProviderApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter providerId, practiceid, resource Name as null to search resources and verify response body")
	public void testToSearchResourcesWithProviderIdPracticeIdResourceNameAsNull()
			throws NullPointerException, IOException {
		logStep("Enter providerId, practiceid, resource Name as null");
		searchResourcesToAssociateWithProviderApiResponse = sharedInfo
				.searchResourcesToAssociateWithProvider_Null_Values_BL();
		CCUtils.assertEquals(searchResourcesToAssociateWithProviderApiResponse.getStatusCode(), 400);
	}
}