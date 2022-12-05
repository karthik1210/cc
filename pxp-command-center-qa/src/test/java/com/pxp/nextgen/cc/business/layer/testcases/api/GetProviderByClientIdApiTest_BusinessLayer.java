//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetProviderByClientIdApiTest_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, getProviderDetailsByClientIdApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId;
	String clientName, clientSapNumber, externalId, providerFirstName, lastName, resourceExternalId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber, "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
		clientId = createClientApiResponse.jsonPath().getInt("clientId");
		createSystemApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", testData.getProperty("api.system.active"), "", clientId,
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		createEnterpriseApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemId,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		createPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		providerFirstName = "Pro"
				+ testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Create provider for resourceId");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, providerFirstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing Providername, ClientId to get Provider details and verify response code and response body")
	public void testGetProviderForClientIdForExistingProviderNameAndClientId() throws IOException {
		logStep("Enter existing Providername, ClientId to get Provider details");
		getProviderDetailsByClientIdApiResponse = sharedInfo.getProviderByClientId_BL(clientId, providerFirstName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getProviderDetailsByClientIdApiResponse);
		CCUtils.assertEquals(getProviderDetailsByClientIdApiResponse.getStatusCode(), 200);
		List<Integer> providerIds = new ArrayList<Integer>();
		providerIds.add(providerId);
		validation.verifyGetProviderByClientIdApiResponseBody(getProviderDetailsByClientIdApiResponse, providerIds,
				providerFirstName, lastName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing ClientId, existing ProviderName and validate response code")
	public void testErrorResponseCodeForNonExistingClientId() throws IOException {
		logStep("Enter non-existing ClientId, existing ProviderName");
		int clientId = Integer.parseInt(testData.getProperty("api.nonexisting.client.id"));
		getProviderDetailsByClientIdApiResponse = sharedInfo.getProviderByClientId_BL(clientId, providerFirstName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getProviderDetailsByClientIdApiResponse);
		CCUtils.assertEquals(getProviderDetailsByClientIdApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing ClientId, non-existing ProviderName and validate response code")
	public void testErrorResponseCodeForNonExistingProviderName() throws IOException {
		logStep("Enter existing ClientId, non-existing ProviderName");
		String firstName = testData.getProperty("api.non.existing.provider.firstname");
		getProviderDetailsByClientIdApiResponse = sharedInfo.getProviderByClientId_BL(clientId, firstName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getProviderDetailsByClientIdApiResponse);
		CCUtils.assertEquals(getProviderDetailsByClientIdApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing ClientId, non-existing ProviderName and validate response code")
	public void testErrorResponseCodeForNonExistingProviderNameAndNonExistingClientId() throws IOException {
		logStep("Enter non-existing ClientId, non-existing ProviderName");
		int clientId = Integer.parseInt(testData.getProperty("api.nonexisting.client.id"));
		String firstName = testData.getProperty("api.non.existing.provider.firstname");
		getProviderDetailsByClientIdApiResponse = sharedInfo.getProviderByClientId_BL(clientId, firstName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getProviderDetailsByClientIdApiResponse);
		CCUtils.assertEquals(getProviderDetailsByClientIdApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter ClientId and ProviderName as null and verify response code and response body")
	public void testErrorMessageForNullValuesInClientIdAndProviderName() throws IOException {
		logStep("Enter ClientId and ProviderName as null");
		getProviderDetailsByClientIdApiResponse = sharedInfo.getProviderByClientId_NullValues_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getProviderDetailsByClientIdApiResponse);
		CCUtils.assertEquals(getProviderDetailsByClientIdApiResponse.getStatusCode(), 400);
	}
}
