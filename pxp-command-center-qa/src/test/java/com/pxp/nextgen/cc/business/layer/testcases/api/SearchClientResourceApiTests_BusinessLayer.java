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
import io.restassured.response.Response;

public class SearchClientResourceApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private ResourceInfo resourceInfo;
	private CreateClientHierarchy_BL clientHierObj;
	Response createProviderApiResponse, searchResourcesResult, updateResourceForPracticesResponse,
			createResourceApiResponse;
	int clientId, systemId, enterpriseId, practiceId, solutionId, providerId, resourceId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName, firstName, lastName, resourceName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		resourceInfo = new ResourceInfo();
		sharedInfo = new SharedInfo();
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
		String resourceName = testData.getProperty("api.name").concat(CCUtils.generateAlphaNumericString(5));
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		resourceId = createResourceApiResponse.jsonPath().get("resourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search resource for client using clientId and full resource name, partial resource name and verify response body")
	public void testSearchResourceUsingValidInput() throws IOException {
		logStep("Search resource for client using clientId and resource name");
		searchResourcesResult = sharedInfo.getResouresForClient_BL(String.valueOf(clientId), null, null, null, null,
				null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourcesResult);
		CCUtils.assertEquals(searchResourcesResult.getStatusCode(), 200);
		logStep("Search resource for client using clientId and full resource name");
		searchResourcesResult = sharedInfo.getResouresForClient_BL(String.valueOf(clientId), resourceName, null, null,
				null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourcesResult);
		CCUtils.assertEquals(searchResourcesResult.getStatusCode(), 200);
		logStep("Search resource for client using clientId and partial resource name");
		searchResourcesResult = sharedInfo.getResouresForClient_BL(String.valueOf(clientId),
				testData.getProperty("api.name"), null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourcesResult);
		CCUtils.assertEquals(searchResourcesResult.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search resource for client using orgId and verify response body")
	public void testSearchResourceWithOrgId() throws IOException, NullPointerException {
		logStep("Search resource for client using clientId and systemId");
		searchResourcesResult = sharedInfo.getResouresForClient_BL(String.valueOf(clientId), null,
				String.valueOf(systemId), null, String.valueOf(practiceId), null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourcesResult);
		CCUtils.assertEquals(searchResourcesResult.getStatusCode(), 200);
		logStep("Search resource for client using clientId and enterpriseId");
		searchResourcesResult = sharedInfo.getResouresForClient_BL(String.valueOf(clientId), null, null,
				String.valueOf(enterpriseId), String.valueOf(practiceId), null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourcesResult);
		CCUtils.assertEquals(searchResourcesResult.getStatusCode(), 200);
		logStep("Search  resource for client using clientId and practiceId");
		searchResourcesResult = sharedInfo.getResouresForClient_BL(String.valueOf(clientId), null, null, null,
				String.valueOf(practiceId), null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourcesResult);
		CCUtils.assertEquals(searchResourcesResult.getStatusCode(), 200);
		logStep("Deassociate practices for Resource");
		updateResourceForPracticesResponse = sharedInfo.associateDeassociatePracticeForResource_BL(resourceId,
				practiceId, "false");
		CCUtils.assertEquals(updateResourceForPracticesResponse.getStatusCode(), 200);
		logStep("Search ResourceId for client using valid input");
		searchResourcesResult = sharedInfo.getResouresForClient_BL(String.valueOf(clientId), null, null, null,
				String.valueOf(practiceId), null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourcesResult);
		CCUtils.assertEquals(searchResourcesResult.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search resource for non-existing clientId and verify response code")
	public void testSearchResourceWithNonExistingClientId() throws IOException {
		logStep("Search provider for client with non-existing clientId");
		searchResourcesResult = sharedInfo.getResouresForClient_BL(testData.getProperty("org.client.id"), null, null,
				null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourcesResult);
		CCUtils.assertEquals(searchResourcesResult.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search resource for non-existing resource name and verify response code")
	public void testSearchResourceWithNonExistingPracticeId() throws IOException {
		logStep("Search provider for client with non-existing practiceId");
		searchResourcesResult = sharedInfo.getResouresForClient_BL(String.valueOf(clientId), CCUtils.generateAlphaNumericString(5), null, null,
				testData.getProperty("nonexiting.practice.id"), null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourcesResult);
		CCUtils.assertEquals(searchResourcesResult.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search resource for non-existing clientId, non-existing practiceId and verify response code")
	public void testSearchResourceWithNonExistingClientIdPracticeId() throws IOException {
		logStep("Search provider for client with non-existing clientId, non-existing practiceId");
		searchResourcesResult = sharedInfo.getResouresForClient_BL(testData.getProperty("org.client.id"), null, null,
				null, testData.getProperty("nonexiting.practice.id"), null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourcesResult);
		CCUtils.assertEquals(searchResourcesResult.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search resource for non-existing Resource Name and verify response code")
	public void testSearchResourceWithNonExistingResourceName() throws IOException {
		logStep("Search provider for client with non-existing Resource Name");
		searchResourcesResult = sharedInfo.getResouresForClient_BL(testData.getProperty("org.client.id"), null, null,
				null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourcesResult);
		CCUtils.assertEquals(searchResourcesResult.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search resource with clientId, practiceId as null and verify response code")
	public void testSearchResourceWithClientIdPracticeIdAsNull() throws IOException {
		logStep("Search provider with clientId, practiceId as null");
		searchResourcesResult = sharedInfo.getResouresForClient_BL(null, null, null, null, null,
				null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourcesResult);
		CCUtils.assertEquals(searchResourcesResult.getStatusCode(), 400);
	}
}