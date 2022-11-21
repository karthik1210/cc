// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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
	private ClientInfo clientInfo;
	int clientId, systemId, enterpriseId, practiceId, providerId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response createClientApiResponse, createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			getConnectedPracticeProviderResponse, createProviderApiResponse;
	private Validations validation;
	private OrgInfo orgInfo;
	String providerFirstName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		orgInfo = new OrgInfo();
		clientId = createClient(ccUser.getClientOneType());
		systemId = createSystemNode(clientId);
		enterpriseId = createEnterpriseNode(systemId);
		practiceId = createPracticeNode(enterpriseId);
		providerId = createProvider(practiceId);
	}

	public int createClient(String clientType) throws Exception {
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber, "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
		clientId = createClientApiResponse.jsonPath().getInt("clientId");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	public int createSystemNode(int clientId) throws Exception {
		createSystemApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", testData.getProperty("api.system.active"), "", clientId,
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createSystemApiResponse.jsonPath().get("orgId");
	}

	public int createEnterpriseNode(int SystemId) throws Exception {
		createEnterpriseApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemId,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createEnterpriseApiResponse.jsonPath().get("orgId");
	}

	public int createPracticeNode(int enterpriseId) throws Exception {
		createPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse.jsonPath().get("orgId");
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
