// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetPracticeProviderApiTests_DataLayer extends BaseRest {
	JsonPath jsonPath;
	int clientId, systemId, enterpriseId, practiceId, providerId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response getPracticeProviderResponse, createProviderApiResponse, putPracticeProviderResponse;
	private Validations validation;
	private SharedInfo sharedInfo;
	private CreateClientHierarchy_DL clientHierObj;
	String providerFirstName, providerLastName;

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
		providerId = createProvider(practiceId);
	}

	public int createProvider(int practiceId) {
		providerFirstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		providerLastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(5);
		createProviderApiResponse = sharedInfo.createProvider_DataLayer(practiceId, providerFirstName, providerLastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		int providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		return providerId;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test get provider for a practice and a provider")
	public void testGetPracticeProvider() throws IOException {
		getPracticeProviderResponse = sharedInfo.getPracticeProvder_DL(practiceId, providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeProviderResponse);
		CCUtils.assertEquals(getPracticeProviderResponse.getStatusCode(), 200);
		String providerName = providerFirstName + " " + providerLastName;
		validation.validateGetPracticeProviderResponseBody(getPracticeProviderResponse, providerName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to update provider with email and mobile and provider for a practice and a provider")
	public void testGetPracticeProviderDetailsWithEmailId() throws IOException {
		putPracticeProviderResponse = sharedInfo.putPracticeProvder_DL(practiceId, providerId, providerFirstName,
				providerLastName, " ", testData.getProperty("api.full.email.id"),
				testData.getProperty("api.client.mobile"));
		CCUtils.assertEquals(putPracticeProviderResponse.getStatusCode(), 200);
		getPracticeProviderResponse = sharedInfo.getPracticeProvder_DL(practiceId, providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeProviderResponse);
		CCUtils.assertEquals(getPracticeProviderResponse.getStatusCode(), 200);
		String providerName = providerFirstName + " " + providerLastName;
		validation.validateGetPracticeProviderResponseBodyWithEmailAndMobile(getPracticeProviderResponse, providerName,
				testData.getProperty("api.full.email.id"), testData.getProperty("api.client.mobile"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify 'Invalid input' message when enter non existing practice Id and existing provider id")
	public void testErrorMessageForNonExistingPracticeIdAndExistingProviderId() throws IOException {
		getPracticeProviderResponse = sharedInfo
				.getPracticeProvder_DL(Integer.parseInt(testData.getProperty("non.existing.org.id")), providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeProviderResponse);
		CCUtils.assertEquals(getPracticeProviderResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(getPracticeProviderResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify 'Invalid input' message when enter existing practice Id and non-existing provider id")
	public void testErrorMessageForExistingPracticeIdAndNonExistingProviderId() throws IOException {
		getPracticeProviderResponse = sharedInfo.getPracticeProvder_DL(practiceId,
				Integer.parseInt(testData.getProperty("non.existing.provider.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeProviderResponse);
		CCUtils.assertEquals(getPracticeProviderResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(getPracticeProviderResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify 'Invalid input' message when enter both non existing practice Id and provider id")
	public void testErrorMessageForBothNonExistingPracticeIdAndProviderId() throws IOException {
		getPracticeProviderResponse = sharedInfo.getPracticeProvder_DL(
				Integer.parseInt(testData.getProperty("non.existing.org.id")),
				Integer.parseInt(testData.getProperty("non.existing.provider.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeProviderResponse);
		CCUtils.assertEquals(getPracticeProviderResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(getPracticeProviderResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter locationId and practiceId as null to get Practice Location and verify response body")
	public void testGetProviderForPracticeIdAndProviderIdAsNull() throws IOException {
		logStep("Get Practice Location");
		getPracticeProviderResponse = sharedInfo.getPracticeProvder_NullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeProviderResponse);
		CCUtils.assertEquals(getPracticeProviderResponse.getStatusCode(), 400);
	}
}
