//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import org.testng.annotations.Test;
import java.io.IOException;
import org.testng.annotations.BeforeClass;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class UpdateProviderDetailsApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId;
	Response createProviderApiResponse, updateProviderDetailsApiResponse;
	private CreateClientHierarchy_DL clientHierObj;
	
	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		validation = new Validations();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		logStep("Create new provider");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enters existing providerId, PracticeID to associate practice for a provider and validate status code")
	public void testSuccessResponseCodeForExistingProviderIdToAssociatePractice()
			throws NullPointerException, IOException {
		logStep("Enter  providerId");
		updateProviderDetailsApiResponse = sharedInfo.updateProviderDetails(providerId,
				String.valueOf(practiceId), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateProviderDetailsApiResponse);
		CCUtils.assertEquals(updateProviderDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enters existing providerId, PracticeID to deassociate practice for a provider and validate status code")
	public void testSuccessResponseCodeForExistingProviderIdToDeAssociatePractice()
			throws NullPointerException, IOException {
		logStep("Enter existing providerId");
		updateProviderDetailsApiResponse = sharedInfo.updateProviderDetails(providerId,
				String.valueOf(practiceId), "false");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateProviderDetailsApiResponse);
		CCUtils.assertEquals(updateProviderDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing providerId, existing PracticeID to associate practice for a provider and validate status code")
	public void testErrorResponseCodeForNonExistingProviderId() throws IOException {
		logStep("Enter non existing providerId");
		int providerId = Integer.parseInt(testData.getProperty("non.existing.provider.id"));
		updateProviderDetailsApiResponse = sharedInfo.updateProviderDetails(providerId,
				String.valueOf(practiceId), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateProviderDetailsApiResponse);
		CCUtils.assertEquals(updateProviderDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateProviderDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing providerId, non existing PracticeID to associate practice for a provider and validate status code")
	public void testErrorResponseCodeForNonExistingPracticeId() throws IOException {
		logStep("Enter non existing practiceId");
		updateProviderDetailsApiResponse = sharedInfo.updateProviderDetails(providerId,
				testData.getProperty("non.existing.practice.id"), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateProviderDetailsApiResponse);
		CCUtils.assertEquals(updateProviderDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateProviderDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing providerId, PracticeID, active status as null and validate status code")
	public void testErrorResponseCodeForActiveStatusAsNull() throws IOException {
		logStep("Enter non existing providerId");
		updateProviderDetailsApiResponse = sharedInfo.updateProviderDetails(providerId,
				String.valueOf(practiceId), " ");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateProviderDetailsApiResponse);
		CCUtils.assertEquals(updateProviderDetailsApiResponse.getStatusCode(), 400); 
		validation.verifyErrorMessageForActiveStatusAsNull(updateProviderDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorResponseCodeForInvalidUrl() throws IOException {
		logStep("Enter existing providerId");
		updateProviderDetailsApiResponse = sharedInfo.updateProviderDetailsInvalidUrl(providerId,
				String.valueOf(practiceId), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateProviderDetailsApiResponse);
		CCUtils.assertEquals(updateProviderDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateProviderDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if POST is used instead of PUT")
	public void testErrorResponseCodeForInvalidMethodCall() throws IOException {
		logStep("Enter existing providerId");
		updateProviderDetailsApiResponse = sharedInfo.updateProviderDetailsInvalidMethod(providerId,
				String.valueOf(practiceId), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateProviderDetailsApiResponse);
		CCUtils.assertEquals(updateProviderDetailsApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(updateProviderDetailsApiResponse);
	}
}
