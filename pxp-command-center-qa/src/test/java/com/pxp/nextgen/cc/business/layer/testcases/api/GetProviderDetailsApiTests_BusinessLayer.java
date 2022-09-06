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
import io.restassured.response.Response;

public class GetProviderDetailsApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	Response fetchProviderDetailsApiResponse, createClientApiResponse, createOrgNodeApiResponse,
			createProviderApiResponse;
	int clientId, systemId, enterpriseId, practiceId;
	String clientName, clientSapNumber;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
		logStep("Create active client node");
		clientId = createClient(testData.getProperty("api.client.active"));
		logStep("Create active system, enterprise ,nge practice and inactive agnostic practice nodes below active client");
		systemId = createOrgNode(testData.getProperty("api.system.active"), "", testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"), "", "",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		enterpriseId = createOrgNode(testData.getProperty("api.enterprise.active"), "",
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), "", "US/Eastern",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		practiceId = createOrgNode(testData.getProperty("api.practice.active"), "",
				testData.getProperty("api.practice.name"), testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), "", "US/Central",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
	}

	public int createClient(String status) throws Exception {
		clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		clientSapNumber = testData.getProperty("api.client.sapnumber")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		createClientApiResponse = clientInfo.createNewClient(status, clientName, clientSapNumber,
				testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"));
		return createClientApiResponse.jsonPath().getInt("clientId");
	}

	public int createOrgNode(String status, String displayName, String name, String orgType, int parentId,
			String parentType, String sapNumber, String timezoneId, int ehridentifier, int pmIdentifier)
			throws Exception {
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL(name, orgType, displayName, sapNumber, status, timezoneId,
				parentId, parentType, ehridentifier, pmIdentifier);
		return createOrgNodeApiResponse.jsonPath().getInt("orgId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing providerId in providerId field and validate status code")
	public void testSuccessResponseCodeForExistingProviderId() throws NullPointerException, IOException {
		logStep("Enter existing providerId");
		int providerId = Integer.parseInt(testData.getProperty("provider.id"));
		fetchProviderDetailsApiResponse = sharedInfo.getProviderDetails(providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForProviderDetails(fetchProviderDetailsApiResponse,
				testData.getProperty("provider.firstname"), testData.getProperty("provider.lastname"),
				testData.getProperty("provider.gender"), testData.getProperty("provider.status"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing providerId in providerId field and validate status code")
	public void testErrorResponseCodeForNonExistingProviderId() throws IOException {
		logStep("Enter non existing providerId");
		int providerId = Integer.parseInt(testData.getProperty("non.existing.provider.id"));
		fetchProviderDetailsApiResponse = sharedInfo.getProviderDetails(providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForNonexistingproviderId(fetchProviderDetailsApiResponse, providerId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		logStep("Enter existing providerId");
		int providerId = Integer.parseInt(testData.getProperty("existing.provider.id"));
		fetchProviderDetailsApiResponse = sharedInfo.getProviderDetailsInvalidUrl(providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchProviderDetailsApiResponse);
	}
}
