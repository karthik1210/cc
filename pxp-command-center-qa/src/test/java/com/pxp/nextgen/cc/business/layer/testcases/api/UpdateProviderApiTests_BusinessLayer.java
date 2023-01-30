//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class UpdateProviderApiTests_BusinessLayer extends BaseRest{
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_BL clientHierObj;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId;
	Response createProviderApiResponse, updateProviderDetailsApiResponse;

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
		logStep("Create new provider");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enters existing providerId,and valid input in Payload and validate status code")
	public void testSuccessResponseCodeForExistingProviderIdForValidPayload()
			throws NullPointerException, IOException {
		logStep("Enter  providerId");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		String gender=testData.getProperty("provider.gender");
		String credentials=testData.getProperty("provider.credentials");
		updateProviderDetailsApiResponse = sharedInfo.updateProviderDetails_BL(providerId,
			firstName,lastName,gender, externalId, credentials);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateProviderDetailsApiResponse);
		CCUtils.assertEquals(updateProviderDetailsApiResponse.getStatusCode(), 200);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enters existing providerId, Invalid values in payload and validate status code")
	public void testErrorResponseCodeForExistingProviderIdForInvalidPayload()
			throws NullPointerException, IOException {
		logStep("Enter existing providerId");
		String firstName = testData.getProperty("api.invalid.firstName");
		String lastName = testData.getProperty("provider.lastname");
		String externalId = CCUtils.generateAlphaNumericString(10);
		String gender=testData.getProperty("api.invalid.gender");
		String credentials=testData.getProperty("provider.credentials");
		updateProviderDetailsApiResponse = sharedInfo.updateProviderDetails_BL(providerId,
			firstName,lastName,gender, externalId, credentials);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateProviderDetailsApiResponse);
		CCUtils.assertEquals(updateProviderDetailsApiResponse.getStatusCode(), 400);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing providerId, and valid input in Payload and validate status code")
	public void testErrorResponseCodeForNonExistingProviderIdForValidPayload() throws IOException {
		logStep("Enter non existing providerId");
		int providerId = Integer.parseInt(testData.getProperty("non.existing.provider.id"));
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		String gender=testData.getProperty("provider.gender");
		String credentials=testData.getProperty("provider.credentials");
		updateProviderDetailsApiResponse = sharedInfo.updateProviderDetails_BL(providerId,
			firstName,lastName,gender, externalId, credentials);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateProviderDetailsApiResponse);
		CCUtils.assertEquals(updateProviderDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateProviderDetailsApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enters existing providerId,Null payload and validate status code")
	public void testErrorResponseCodeForExistingProviderIdForNullValuesInPayload()
			throws NullPointerException, IOException {
		logStep("Enter existing providerId");
		updateProviderDetailsApiResponse = sharedInfo.updateProviderDetails_NullPayload_BL(providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateProviderDetailsApiResponse);
		CCUtils.assertEquals(updateProviderDetailsApiResponse.getStatusCode(), 400);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enters Null providerId And Null payload and validate status code")
	public void testErrorResponseCodeForNullValuesInProviderIdForNullValuesInPayload()
			throws NullPointerException, IOException {
		logStep("Enter Null providerId");
		updateProviderDetailsApiResponse = sharedInfo.updateProviderDetails_NullValues_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateProviderDetailsApiResponse);
		CCUtils.assertEquals(updateProviderDetailsApiResponse.getStatusCode(), 400);
	}
	
}