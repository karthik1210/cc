//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;

public class SearchClientProviderApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private CreateClientHierarchy_BL clientHierObj;
	Response createProviderApiResponse, searchProvidersResult, updateProviderForPracticesResponse,
			updateSolutionsApiResponse, fetchOrgSolutionDetailsApiResponse, updateSolutionsForProviderResponse;
	int clientId, systemId, enterpriseId, practiceId, solutionId, providerId;
	String firstName, lastName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		logStep("Create new provider");
		firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		providerId = createProviderApiResponse.jsonPath().get("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search provider for client using clientId and full provider name, partial provider name and verify response body")
	public void testSearchProviderUsingValidInput() throws IOException {
		logStep("Search provider for client using clientId and provider name");
		searchProvidersResult = sharedInfo.getProvidersForClient_BL(String.valueOf(clientId), null, null, null, null,
				null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 200);
		logStep("Search provider for client using clientId and full provider name");
		searchProvidersResult = sharedInfo.getProvidersForClient_BL(String.valueOf(clientId), firstName, null, null,
				null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 200);
		logStep("Search provider for client using clientId and partial provider name");
		searchProvidersResult = sharedInfo.getProvidersForClient_BL(String.valueOf(clientId),
				testData.getProperty("provider.firstname"), null, null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search provider for client using orgId and verify response body")
	public void testSearchProviderWithOrgId() throws IOException, NullPointerException {
		logStep("Search provider for client using clientId and systemId");
		searchProvidersResult = sharedInfo.getProvidersForClient_BL(String.valueOf(clientId), null,
				String.valueOf(systemId), null, String.valueOf(practiceId), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 200);
		logStep("Search provider for client using clientId and enterpriseId");
		searchProvidersResult = sharedInfo.getProvidersForClient_BL(String.valueOf(clientId), null, null,
				String.valueOf(enterpriseId), String.valueOf(practiceId), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 200);
		logStep("Search  provider for client using clientId and practiceId");
		searchProvidersResult = sharedInfo.getProvidersForClient_BL(String.valueOf(clientId), null, null, null,
				String.valueOf(practiceId), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 200);
		logStep("Deassociate practices for provider");
		updateProviderForPracticesResponse = sharedInfo.practicesAssociateToPractice_BL(providerId, practiceId, false);
		CCUtils.assertEquals(updateProviderForPracticesResponse.getStatusCode(), 200);
		logStep("Search providerId for client using valid input");
		searchProvidersResult = sharedInfo.getProvidersForClient_BL(String.valueOf(clientId), null, null, null,
				String.valueOf(practiceId), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search provider for non-existing clientId and verify response code")
	public void testSearchProviderWithNonExistingClientId() throws IOException {
		logStep("Search provider for client with non-existing clientId");
		searchProvidersResult = sharedInfo.getProvidersForClient_BL(testData.getProperty("org.client.id"), null, null,
				null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search provider for non-existing practiceId and verify response code")
	public void testSearchProviderWithNonExistingPracticeId() throws IOException {
		logStep("Search provider for client with non-existing practiceId");
		searchProvidersResult = sharedInfo.getProvidersForClient_BL(String.valueOf(clientId), null, null, null,
				testData.getProperty("nonexiting.practice.id"), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search provider for non-existing clientId, non-existing practiceId and verify response code")
	public void testSearchProviderWithNonExistingClientIdPracticeId() throws IOException {
		logStep("Search provider for client with non-existing clientId, non-existing practiceId");
		searchProvidersResult = sharedInfo.getProvidersForClient_BL(testData.getProperty("org.client.id"), null, null,
				null, testData.getProperty("nonexiting.practice.id"), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search provider with clientId, practiceId as null and verify response code")
	public void testSearchProviderWithClientIdPracticeIdAsNull() throws IOException {
		logStep("Search provider with clientId, practiceId as null");
		searchProvidersResult = sharedInfo.getProvidersForClient_BL(null, null, null, null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 400);
	}
}