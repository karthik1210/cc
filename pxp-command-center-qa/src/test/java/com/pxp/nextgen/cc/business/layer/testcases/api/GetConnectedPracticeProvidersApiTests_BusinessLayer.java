// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

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
import com.pxp.cc.util.Validations;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetConnectedPracticeProvidersApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	JsonPath jsonPath;
	int clientId, systemId, enterpriseId, practiceId, providerId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response getConnectedPracticeProviderResponse, createProviderApiResponse;
	private Validations validation;
	private CreateClientHierarchy_BL clientHierObj;
	String providerFirstName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		providerId = createProvider(practiceId);
	}

	public int createProvider(int practiceId) {
		providerFirstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, providerFirstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		return createProviderApiResponse.jsonPath().getInt("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify fetching provider for existing practice Id and existing provider name")
	public void testGetConnectedPracticeProviders() throws IOException {
		getConnectedPracticeProviderResponse = sharedInfo.getConnectedPracticeProvider_BL(practiceId,
				providerFirstName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getConnectedPracticeProviderResponse);
		CCUtils.assertEquals(getConnectedPracticeProviderResponse.getStatusCode(), 200);
		validation.validateGetConnectedPracticeProvidersResponseBody(getConnectedPracticeProviderResponse, providerId,
				providerFirstName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validate get connected practice providers for non-existing practice Id and existing provider name")
	public void testNotFoundGetConnectedPracticeProvidersWithNonExistingPracticeId() throws IOException {
		getConnectedPracticeProviderResponse = sharedInfo.getConnectedPracticeProvider_BL(
				Integer.parseInt(testData.getProperty("non.existing.org.id")), providerFirstName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getConnectedPracticeProviderResponse);
		CCUtils.assertEquals(getConnectedPracticeProviderResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validate get connected practice providers for existing practice Id and non-existing provider name")
	public void testNotFoundGetConnectedPracticeProvidersWithNonExistingProviderName() throws IOException {
		getConnectedPracticeProviderResponse = sharedInfo.getConnectedPracticeProvider_BL(practiceId,
				testData.getProperty("api.non.existing.provider.firstname"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getConnectedPracticeProviderResponse);
		CCUtils.assertEquals(getConnectedPracticeProviderResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validate get connected practice providers for both non-existing practice Id and provider name")
	public void testNotFoundGetConnectedPracticeProvidersWithBothNonExistingProviderNameAndPracticeId()
			throws IOException {
		getConnectedPracticeProviderResponse = sharedInfo.getConnectedPracticeProvider_BL(
				Integer.parseInt(testData.getProperty("non.existing.org.id")),
				testData.getProperty("api.non.existing.provider.firstname"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getConnectedPracticeProviderResponse);
		CCUtils.assertEquals(getConnectedPracticeProviderResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validate get connected practice providers for null values of practice Id and null provider name")
	public void testNotFoundGetConnectedPracticeProvidersAsNull() throws IOException {
		getConnectedPracticeProviderResponse = sharedInfo.getConnectedPracticeProvider_BL_Null_Values();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getConnectedPracticeProviderResponse);
		CCUtils.assertEquals(getConnectedPracticeProviderResponse.getStatusCode(), 400);
	}
}
