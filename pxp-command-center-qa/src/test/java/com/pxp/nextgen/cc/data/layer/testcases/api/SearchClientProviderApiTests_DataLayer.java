//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;

public class SearchClientProviderApiTests_DataLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, searchProvidersResult, updateProviderForPracticesResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName, firstName, lastName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();		
		sharedInfo = new SharedInfo();		
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
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