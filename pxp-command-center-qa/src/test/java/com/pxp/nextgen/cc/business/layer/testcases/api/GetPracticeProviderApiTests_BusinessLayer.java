//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

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
import io.restassured.response.Response;

public class GetPracticeProviderApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private ClientInfo clientInfo;
	int clientId, systemId, enterpriseId, practiceId, resourceId, solutionId, providerId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse, createClientApiResponse,
			createProviderApiResponse, updateProviderDetailsApiResponse, getPracticeProviderApiResponse,
			putPracticeProviderResponse;
	private OrgInfo orgInfo;
	String providerFirstName, providerLastName;

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
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId(),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
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
		String firstName = "Provi"
				+ testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Create provider for resourceId");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test get provider for a practice and a provider")
	public void testGetPracticeProvider() throws IOException {
		logStep("Enter existing practiceId, providerId");
		getPracticeProviderApiResponse = sharedInfo.getPracticeProvider_BL(practiceId, providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeProviderApiResponse);
		CCUtils.assertEquals(getPracticeProviderApiResponse.getStatusCode(), 200);
		String providerName = getPracticeProviderApiResponse.jsonPath().get("displayName.en");
		validation.validateGetPracticeProviderResponseBody(getPracticeProviderApiResponse, providerName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test to update provider with email and mobile and provider for a practice and a provider")
	public void testGetPracticeProviderDetailsWithEmailId() throws IOException {
		logStep("Update provider details and get provider details");
		putPracticeProviderResponse = sharedInfo.putPracticeProvider_BL(practiceId, providerId, providerFirstName,
				providerLastName, " ", testData.getProperty("api.full.email.id"),
				testData.getProperty("api.client.mobile"));
		CCUtils.assertEquals(putPracticeProviderResponse.getStatusCode(), 200);
		getPracticeProviderApiResponse = sharedInfo.getPracticeProvider_BL(practiceId, providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeProviderApiResponse);
		CCUtils.assertEquals(getPracticeProviderApiResponse.getStatusCode(), 200);
		String providerName = getPracticeProviderApiResponse.jsonPath().get("displayName.en");
		validation.validateGetPracticeProviderResponseBody(getPracticeProviderApiResponse, providerName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify 'Invalid input' message when enter non existing practice Id and existing provider id")
	public void testErrorMessageForNonExistingPracticeIdAndExistingProviderId() throws IOException {
		logStep("Enter Non Existing PracticeId And Existing ProviderId");
		getPracticeProviderApiResponse = sharedInfo
				.getPracticeProvider_BL(Integer.parseInt(testData.getProperty("non.existing.org.id")), providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeProviderApiResponse);
		CCUtils.assertEquals(getPracticeProviderApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(getPracticeProviderApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify 'Invalid input' message when enter existing practice Id and non-existing provider id")
	public void testErrorMessageForExistingPracticeIdAndNonExistingProviderId() throws IOException {
		logStep("Enter Existing PracticeId And Non Existing ProviderId");
		getPracticeProviderApiResponse = sharedInfo.getPracticeProvider_BL(practiceId,
				Integer.parseInt(testData.getProperty("non.existing.provider.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeProviderApiResponse);
		CCUtils.assertEquals(getPracticeProviderApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(getPracticeProviderApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify 'Invalid input' message when enter both non existing practice Id and provider id")
	public void testErrorMessageForBothNonExistingPracticeIdAndProviderId() throws IOException {
		logStep("Enter Both Non Existing PracticeId And ProviderId");
		getPracticeProviderApiResponse = sharedInfo.getPracticeProvider_BL(
				Integer.parseInt(testData.getProperty("non.existing.org.id")),
				Integer.parseInt(testData.getProperty("non.existing.provider.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeProviderApiResponse);
		CCUtils.assertEquals(getPracticeProviderApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(getPracticeProviderApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter providerId and practiceId as null and verify response body")
	public void testGetProviderForPracticeIdAndProviderIdAsNull() throws IOException {
		logStep("Enter providerId and practiceId as null");
		getPracticeProviderApiResponse = sharedInfo.getPracticeProvider_NullValues_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticeProviderApiResponse);
		CCUtils.assertEquals(getPracticeProviderApiResponse.getStatusCode(), 400);
	}
}
