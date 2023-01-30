//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetProviderByClientIdApiTest_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_DL clientHierObj;
	Response createProviderApiResponse, fetchProviderDetailsApiResponse;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId;
	String providerName, firstName, lastName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
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
	public void testGetProviderForClientIdForExistingProviderNameAndClientId() throws IOException {
		logStep("fetch newly created provider details using provider name and ClientId");
		fetchProviderDetailsApiResponse = sharedInfo.getProviderByClientId_DL(clientId, providerName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 200);
		ArrayList<Integer> providerIds = new ArrayList<Integer>();
		providerIds.add(providerId);
		validation.verifyGetProvidersByClientIdResponseBody(fetchProviderDetailsApiResponse, providerIds, firstName,
				lastName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing ClientId, existing ProviderName and validate status code")
	public void testErrorResponseCodeForNonExistingClientId() throws IOException {
		logStep("Enter non existing ClientId");
		int clientId = Integer.parseInt(testData.getProperty("api.nonexisting.client.id"));
		fetchProviderDetailsApiResponse = sharedInfo.getProviderByClientId_DL(clientId, providerName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing ClientId, non-existing ProviderName and validate status code")
	public void testErrorResponseCodeForNonExistingProviderName() throws IOException {
		logStep("Enter non existing ProviderName");
		String firstName = testData.getProperty("api.non.existing.provider.firstname");
		fetchProviderDetailsApiResponse = sharedInfo.getProviderByClientId_DL(clientId, firstName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing ClientId, non-existing ProviderName and validate status code")
	public void testErrorResponseCodeForNonExistingProviderNameAndNonExistingClientId() throws IOException {
		logStep("Enter non existing ProviderName and non-existing ProviderName");
		int clientId = Integer.parseInt(testData.getProperty("api.nonexisting.client.id"));
		String firstName = testData.getProperty("api.non.existing.provider.firstname");
		fetchProviderDetailsApiResponse = sharedInfo.getProviderByClientId_DL(clientId, firstName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter ClientId & ProviderName as null and verify status code and response body")
	public void testErrorMessageForNullValuesInClientIdAndProviderName() throws IOException {
		logStep("Enter ClientId & ProviderName as null");
		fetchProviderDetailsApiResponse = sharedInfo.getProviderByClientId_NullValues_DL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 400);
	}
}