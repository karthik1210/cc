//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
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

public class ToggleProviderAssociationToPracticeApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	Response createProviderApiResponse, createClientApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, fetchProviderDetailsApiResponse,associateDeassociatePracticesForProviderApiResponse,
	        getPracticesAssociateToProviderApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId;
	String clientName, clientSapNumber, externalId, firstName, lastName;	
	ArrayList<Integer> associatePracticeIds; 
	
	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
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
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		providerId=createProvider(clientId);
	}
	
	public int createProvider(int clientId) throws Exception  {
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createProviderApiResponse);
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		validation.verifyCreateProviderApiResponseBody(createProviderApiResponse, firstName, lastName);
		return createProviderApiResponse.jsonPath().getInt("providerId");
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class,description = "Verify user able to associate and deassociate practice for providers")
	public void testAssociateDeassociateProviderForPracticeFunctionality() throws IOException {
		logStep("Associate practice with Provider");
		associateDeassociatePracticesForProviderApiResponse= sharedInfo
				.associateDeassociatePracticeForProvider_BL(providerId, practiceId,true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociatePracticesForProviderApiResponse);
		CCUtils.assertEquals(associateDeassociatePracticesForProviderApiResponse.getStatusCode(), 200);
		logStep("Fetch practice associated to Provider");
		getPracticesAssociateToProviderApiResponse=sharedInfo.getPracticesAssociatedToProvider_BL(providerId);
		logStep("Validating response information");
		CCUtils.assertEquals(getPracticesAssociateToProviderApiResponse.getStatusCode(), 200);
		associatePracticeIds = new ArrayList<Integer>();
		associatePracticeIds.add(practiceId);
		validation.verifyGetPracticesAssociatedToProviderApiResponseBody
		(getPracticesAssociateToProviderApiResponse,associatePracticeIds);	
		logStep("Deassociate practice with provider");
        associateDeassociatePracticesForProviderApiResponse= sharedInfo
                .associateDeassociatePracticeForProvider_BL(providerId, practiceId, false);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(associateDeassociatePracticesForProviderApiResponse);
        CCUtils.assertEquals(associateDeassociatePracticesForProviderApiResponse.getStatusCode(), 200);
        logStep("Fetch deassociated practice ids from provider and verify 204 status code is displayed in response if  practices are not associated with provider");
        getPracticesAssociateToProviderApiResponse = sharedInfo.getPracticesAssociatedToProvider_BL(providerId);
        logStep("Validating response information");
		CCUtils.assertEquals( getPracticesAssociateToProviderApiResponse.getStatusCode(), 204);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid provider in provider id field and verify response body")
	public void testErrorResponseForNonExistingProviderId() throws IOException {
		logStep("Enter non existing provider id in provider id field");
		associateDeassociatePracticesForProviderApiResponse = sharedInfo.associateDeassociatePracticeForProvider_BL(
				Integer.parseInt(testData.getProperty("non.existing.provider.id")), practiceId,true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociatePracticesForProviderApiResponse);
		CCUtils.assertEquals(associateDeassociatePracticesForProviderApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(associateDeassociatePracticesForProviderApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid provider in provider id field and non existing practice id in practice id field and verify response body")
	public void testErrorResponseForNonExistingPracticeId() throws IOException {
		logStep("Enter non existing provider id in provider id field");
		associateDeassociatePracticesForProviderApiResponse = sharedInfo.associateDeassociatePracticeForProvider_BL(
				providerId, Integer.parseInt(testData.getProperty("non.existing.org.id")), true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociatePracticesForProviderApiResponse);
		CCUtils.assertEquals(associateDeassociatePracticesForProviderApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(associateDeassociatePracticesForProviderApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank payload and verify response body")
	public void testErrorResponseForEmptyPayload() throws IOException {
		logStep("Enter blank payload and verify error response");
		associateDeassociatePracticesForProviderApiResponse = sharedInfo
				. associateDeassociatePracticeForProviderWithEmptyPayload_BL(providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociatePracticesForProviderApiResponse);
		CCUtils.assertEquals(associateDeassociatePracticesForProviderApiResponse.getStatusCode(), 400);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values in ProviderId and verify response body")
	public void testErrorResponseForProviderIdAsNull() throws IOException {
		logStep("Enter null values in ProviderId and verify error response");
		associateDeassociatePracticesForProviderApiResponse = sharedInfo.getPracticesAssociatedToProvider_NullValues();
		CCUtils.responseTimeValidation(associateDeassociatePracticesForProviderApiResponse);
		CCUtils.assertEquals(associateDeassociatePracticesForProviderApiResponse.getStatusCode(), 400);
	}
}