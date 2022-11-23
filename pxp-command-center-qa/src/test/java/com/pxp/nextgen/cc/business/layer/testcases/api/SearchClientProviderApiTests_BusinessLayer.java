//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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

public class SearchClientProviderApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, searchProvidersResult, updateProviderForPracticesResponse,
			updateSolutionsApiResponse, fetchOrgSolutionDetailsApiResponse, updateSolutionsForProviderResponse;
	int clientId, systemId, enterpriseId, practiceId, solutionId, providerId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName, firstName, lastName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		logStep("Create nge practice below the client with valid details");
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber, "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
		clientId = createClientApiResponse.jsonPath().getInt("clientId");
		logStep("Create a new system below client.");
		createSystemApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", testData.getProperty("api.system.active"), "", clientId,
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new enterprise below system.");
		createEnterpriseApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemId,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new practice below enterprise.");
		createPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
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