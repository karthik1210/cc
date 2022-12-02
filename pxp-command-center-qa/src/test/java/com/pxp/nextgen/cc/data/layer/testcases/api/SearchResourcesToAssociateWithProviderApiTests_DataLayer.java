//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
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

public class SearchResourcesToAssociateWithProviderApiTests_DataLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private Validations validation;
	List<ResourceDetails> resourceList;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createResourceApiResponse, searchResourcesToAssociateWithProviderApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId;
	String clientName, clientSapNumber, externalId, firstName, lastName, resourceExternalId, resourceName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		resourceInfo = new ResourceInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();

		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"));
		clientId = createClientApiResponse.jsonPath().get("clientId");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
				testData.getProperty("api.system.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		systemId = createSystemApiResponse.jsonPath().get("orgId");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.displayname"), testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		enterpriseId = createEnterpriseApiResponse.jsonPath().get("orgId");
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Create provider for resourceId");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new resource");
		resourceId = createResource();
		deassociateProviderToResource();
	}

	public int createResource() {
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		resourceName = "Res" + CCUtils.generateAlphaNumericString(8);
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		return createResourceApiResponse.jsonPath().getInt("resourceId");
	}

	public void deassociateProviderToResource() {
		resourceList = Arrays.asList(new ResourceDetails(resourceId, false));
		createResourceApiResponse = sharedInfo.addOrRemoveResourcesForProvider_DL(providerId, practiceId, resourceList);
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 207);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing providerId, practiceid, resource Name to search resources and verify response body")
	public void testToSearchResourcesWithExistingProviderIdPracticeIdResourceName()
			throws NullPointerException, IOException {
		logStep("Enter existing providerId, practiceid, resource Name");
		searchResourcesToAssociateWithProviderApiResponse = sharedInfo
				.searchResourcesToAssociateWithProvider_DL(providerId, practiceId, resourceName);
		CCUtils.assertEquals(searchResourcesToAssociateWithProviderApiResponse.getStatusCode(), 200);
		validation.verifySearchResourcesToAssociateWithProviderApiResponseBody(
				searchResourcesToAssociateWithProviderApiResponse, resourceId, resourceName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing providerId, existing practiceid, existing resource Name to search resources and verify response body")
	public void testToSearchResourcesWithNonExistingProviderIdExistingPracticeIdResourceName()
			throws NullPointerException, IOException {
		logStep("Enter non-existing providerId, existing practiceid, existing resource Name");
		searchResourcesToAssociateWithProviderApiResponse = sharedInfo.searchResourcesToAssociateWithProvider_DL(
				Integer.parseInt(testData.getProperty("non.existing.provider.id")), practiceId, resourceName);
		CCUtils.assertEquals(searchResourcesToAssociateWithProviderApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing providerId, non-existing practiceid, existing resource Name to search resources and verify response body")
	public void testToSearchResourcesWithExistingProviderIdResourceNameNonExistingPracticeId()
			throws NullPointerException, IOException {
		logStep("Enter existing providerId, non-existing practiceid, existing resource Name");
		searchResourcesToAssociateWithProviderApiResponse = sharedInfo.searchResourcesToAssociateWithProvider_DL(
				providerId, Integer.parseInt(testData.getProperty("non.existing.practice.id")), resourceName);
		CCUtils.assertEquals(searchResourcesToAssociateWithProviderApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing providerId, practiceid, non-existing resource Name to search resources and verify response body")
	public void testToSearchResourcesWithExistingProviderIdPracticeIdNonExistingResourceName()
			throws NullPointerException, IOException {
		logStep("Enter existing providerId, practiceid, non-existing resource Name");
		searchResourcesToAssociateWithProviderApiResponse = sharedInfo.searchResourcesToAssociateWithProvider_DL(
				providerId, practiceId, testData.getProperty("api.name"));
		CCUtils.assertEquals(searchResourcesToAssociateWithProviderApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing providerId, practiceid, resource Name to search resources and verify response body")
	public void testToSearchResourcesWithNonExistingProviderIdPracticeIdResourceName()
			throws NullPointerException, IOException {
		logStep("Enter non-existing providerId, practiceid, resource Name");
		searchResourcesToAssociateWithProviderApiResponse = sharedInfo.searchResourcesToAssociateWithProvider_DL(
				Integer.parseInt(testData.getProperty("non.existing.provider.id")),
				Integer.parseInt(testData.getProperty("non.existing.practice.id")),
				testData.getProperty("api.name"));
		CCUtils.assertEquals(searchResourcesToAssociateWithProviderApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter providerId, practiceid, resource Name as null to search resources and verify response body")
	public void testToSearchResourcesWithProviderIdPracticeIdResourceNameAsNull()
			throws NullPointerException, IOException {
		logStep("Enter providerId, practiceid, resource Name as null");
		searchResourcesToAssociateWithProviderApiResponse = sharedInfo
				.searchResourcesToAssociateWithProvider_Null_Values_DL();
		CCUtils.assertEquals(searchResourcesToAssociateWithProviderApiResponse.getStatusCode(), 400);
	}
}