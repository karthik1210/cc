// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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
import com.pxp.cc.util.Validations;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetPracticeProviderApiTests_DataLayer extends BaseRest {

	JsonPath jsonPath;
	private ClientInfo clientInfo;
	int clientId, systemId, enterpriseId, practiceId, providerId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response createClientApiResponse, createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			getPracticeProviderResponse, createProviderApiResponse, putPracticeProviderResponse;
	private Validations validation;
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	String providerFirstName, providerLastName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		clientId = createClient(ccUser.getClientOneType());
		systemId = createSystemNode(clientId);
		enterpriseId = createEnterpriseNode(systemId);
		practiceId = createPracticeNode(enterpriseId);
		providerId = createProvider(practiceId);
	}

	public int createClient(String clientType) throws Exception {
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient_DataLayer(ccUser.getActive(), clientName, clientSapNumber,
				"true", Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	public int createSystemNode(int clientId) throws Exception {
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "",
				testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), "", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createSystemApiResponse.jsonPath().get("orgId");
	}

	public int createEnterpriseNode(int SystemId) throws Exception {
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"), "",
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"), SystemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createEnterpriseApiResponse.jsonPath().get("orgId");
	}

	public int createPracticeNode(int enterpriseId) throws Exception {
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse.jsonPath().get("orgId");
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
