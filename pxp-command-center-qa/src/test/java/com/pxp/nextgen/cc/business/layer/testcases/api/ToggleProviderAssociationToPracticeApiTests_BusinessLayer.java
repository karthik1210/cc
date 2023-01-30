//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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

public class ToggleProviderAssociationToPracticeApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_BL clientHierObj;
	Response createProviderApiResponse, fetchProviderDetailsApiResponse,associateDeassociatePracticesForProviderApiResponse,
	        getPracticesAssociateToProviderApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId;
	String externalId, firstName, lastName;	
	ArrayList<Integer> associatePracticeIds; 
	
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