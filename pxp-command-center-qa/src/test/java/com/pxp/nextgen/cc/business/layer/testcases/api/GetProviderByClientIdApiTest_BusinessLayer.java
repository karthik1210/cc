//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetProviderByClientIdApiTest_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_BL clientHierObj;
	Response createProviderApiResponse, getProviderDetailsByClientIdApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId;
	String externalId, providerFirstName, lastName, resourceExternalId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		providerFirstName = "Pro"
				+ testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(2));
		lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(2));
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
