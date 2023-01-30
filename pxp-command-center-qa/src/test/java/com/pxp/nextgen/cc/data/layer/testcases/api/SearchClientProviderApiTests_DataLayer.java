//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;

public class SearchClientProviderApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	Response createProviderApiResponse, searchProvidersResult, updateProviderForPracticesResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId;
	String firstName, lastName;
	private CreateClientHierarchy_DL clientHierObj;
	
	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();	
		sharedInfo = new SharedInfo();		
		clientHierObj = new CreateClientHierarchy_DL();
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Create provider for resourceId");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search provider for client using clientId and full provider name, partial provider name and verify response body")
	public void testSearchProviderUsingValidInput() throws IOException {
		logStep("Search provider for client using clientId and provider name");
		searchProvidersResult = sharedInfo.getProvidersForClient_DL(String.valueOf(clientId), null, null, null, null,
				null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 200);
		logStep("Search provider for client using clientId and full provider name");
		searchProvidersResult = sharedInfo.getProvidersForClient_DL(String.valueOf(clientId), firstName, null, null,
				null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 200);
		logStep("Search provider for client using clientId and partial provider name");
		searchProvidersResult = sharedInfo.getProvidersForClient_DL(String.valueOf(clientId),
				testData.getProperty("provider.firstname"), null, null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search provider for client using orgId and verify response body")
	public void testSearchProviderWithOrgId() throws IOException, NullPointerException {
		logStep("Search provider for client using clientId and systemId");
		searchProvidersResult = sharedInfo.getProvidersForClient_DL(String.valueOf(clientId), null,
				String.valueOf(systemId), null, String.valueOf(practiceId), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 200);
		logStep("Search provider for client using clientId and enterpriseId");
		searchProvidersResult = sharedInfo.getProvidersForClient_DL(String.valueOf(clientId), null, null,
				String.valueOf(enterpriseId), String.valueOf(practiceId), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 200);
		logStep("Search  provider for client using clientId and practiceId");
		searchProvidersResult = sharedInfo.getProvidersForClient_DL(String.valueOf(clientId), null, null, null,
				String.valueOf(practiceId), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 200);
		logStep("Deassociate practices for provider");
		updateProviderForPracticesResponse = sharedInfo.practicesAssociateToProvider_DL(providerId, practiceId, false);
		CCUtils.assertEquals(updateProviderForPracticesResponse.getStatusCode(), 200);
		logStep("Search providerId for client using valid input");
		searchProvidersResult = sharedInfo.getProvidersForClient_DL(String.valueOf(clientId), null, null, null,
				String.valueOf(practiceId), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search provider for non-existing clientId and verify response code")
	public void testSearchProviderWithNonExistingClientId() throws IOException {
		logStep("Search provider for client with non-existing clientId");
		searchProvidersResult = sharedInfo.getProvidersForClient_DL(testData.getProperty("org.client.id"), null, null,
				null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search provider for non-existing practiceId and verify response code")
	public void testSearchProviderWithNonExistingPracticeId() throws IOException {
		logStep("Search provider for client with non-existing practiceId");
		searchProvidersResult = sharedInfo.getProvidersForClient_DL(String.valueOf(clientId), null, null, null,
				testData.getProperty("nonexiting.practice.id"), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search provider for non-existing clientId, non-existing practiceId and verify response code")
	public void testSearchProviderWithNonExistingClientIdPracticeId() throws IOException {
		logStep("Search provider for client with non-existing clientId, non-existing practiceId");
		searchProvidersResult = sharedInfo.getProvidersForClient_DL(testData.getProperty("org.client.id"), null, null,
				null, testData.getProperty("nonexiting.practice.id"), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search provider with clientId, practiceId as null and verify response code")
	public void testSearchProviderWithClientIdPracticeIdAsNull() throws IOException {
		logStep("Search provider with clientId, practiceId as null");
		searchProvidersResult = sharedInfo.getProvidersForClient_DL(null, null, null, null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchProvidersResult);
		CCUtils.assertEquals(searchProvidersResult.getStatusCode(), 400);
	}
}