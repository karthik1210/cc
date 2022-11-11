//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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

public class GetProvidersByClientIdApiTest_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	Response createProviderApiResponse, createClientApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, fetchProviderDetailsApiResponse;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId;
	String providerName, firstName,lastName;
	
	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
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
		logStep("Create new provider");
		 firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		 lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		providerName = createProviderApiResponse.jsonPath().getString("firstName");	
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing Providername, ClientId to get one Provider and verify status code and response body")
	public void testGetProviderForClientIdForExistingProvidernameAndClientId() throws IOException {
		logStep("fetch newly created provider details using provider name and ClientId");
		fetchProviderDetailsApiResponse = sharedInfo.getProviderByClientId_DL(clientId,providerName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 200);
		ArrayList<Integer> providerIds = new ArrayList<Integer>();
        providerIds.add(providerId);
		validation.verifyGetProvidersByClientIdResponseBody(fetchProviderDetailsApiResponse,providerIds,firstName,lastName);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing ClientId, existing ProviderName and validate status code")
	public void testErrorResponseCodeForNonExistingProviderId() throws IOException {
		logStep("Enter non existing ClientId");
		int clientId = Integer.parseInt(testData.getProperty("api.nonexisting.client.id"));
		fetchProviderDetailsApiResponse = sharedInfo.getProviderByClientId_DL(clientId,providerName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 204);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing ClientId, non-existing ProviderName and validate status code")
	public void testErrorResponseCodeForNonExistingProviderName() throws IOException {
		logStep("Enter non existing ProviderName");
		String firstName = testData.getProperty("api.non.existing.provider.firstname");
		fetchProviderDetailsApiResponse = sharedInfo.getProviderByClientId_DL(clientId,firstName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 204);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing ClientId, non-existing ProviderName and validate status code")
	public void testErrorResponseCodeForNonExistingProviderNameAndNonExistingClientId() throws IOException {
		logStep("Enter non existing ProviderName and non-existing ProviderName");
		int clientId = Integer.parseInt(testData.getProperty("api.nonexisting.client.id"));
		String firstName = testData.getProperty("api.non.existing.provider.firstname");
		fetchProviderDetailsApiResponse = sharedInfo.getProviderByClientId_DL(clientId,firstName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 204);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter ClientId & ProviderName as null and verify status code and response body")
	public void testErrorMessageForNullValuesInPracticeIdAndLocationId() throws IOException {
		logStep("Enter ClientId & ProviderName as null");
		fetchProviderDetailsApiResponse = sharedInfo.getProviderByClientId_NullValues_DL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 400);
	}
}