//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class CreateProviderApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_DL clientHierObj;
	Response createProviderApiResponse, fetchProviderDetailsApiResponse;
	int clientId, systemId, enterpriseId, practiceId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in firstname,lastname,gender and external id field and verify response body")
	public void testCreateProviderUsingValidDetails() throws IOException {
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DataLayer(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createProviderApiResponse);
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		validation.verifyCreateProviderApiResponseBody(createProviderApiResponse, firstName, lastName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter 100 characters in firstname,lastname field and 50 characters in external id and crendentials  field and verify response body")
	public void testFieldValidationForFirstNameLastNameAndExternalIdField() throws IOException {
		String firstName = CCUtils.generateAlphaNumericString(100);
		String lastName = CCUtils.generateAlphaNumericString(100);
		String externalId = CCUtils.generateAlphaNumericString(50);
		String credentials = CCUtils.generateAlphaNumericString(50);
		createProviderApiResponse = sharedInfo.createProvider_DataLayer(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, credentials);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createProviderApiResponse);
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in firstname,lastname,and more than 50 characters in external id,credentials field and verify response body")
	public void testCreateProviderUsingInvalidDetails() throws IOException {
		String firstName = CCUtils.generateAlphaNumericString(101);
		String lastName = CCUtils.generateAlphaNumericString(101);
		String externalId = CCUtils.generateAlphaNumericString(60);
		String credentials = CCUtils.generateAlphaNumericString(60);
		createProviderApiResponse = sharedInfo.createProvider_DataLayer(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, credentials);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createProviderApiResponse);
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid value in gender field and verify response body")
	public void testErrorMessageForInvalidGenderField() throws IOException {
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DataLayer(practiceId,
				testData.getProperty("provider.firstname"), testData.getProperty("provider.lastname"),
				testData.getProperty("invalid.gender"), externalId, testData.getProperty("provider.credentials"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createProviderApiResponse);
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing practice id in practice id field and verify response body")
	public void testErrorResponseForInvalidPracticeId() throws IOException {
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DataLayer(
				Integer.parseInt(testData.getProperty("non.existing.practice.id")),
				testData.getProperty("provider.firstname"), testData.getProperty("provider.lastname"),
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createProviderApiResponse);
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForNonExistingPracticeId(createProviderApiResponse,
				Integer.parseInt(testData.getProperty("non.existing.practice.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorResponseForInvalidUrl() throws IOException {
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProviderUsingInvalidUrl_DataLayer(practiceId,
				testData.getProperty("provider.firstname"), testData.getProperty("provider.lastname"),
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createProviderApiResponse);
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(createProviderApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of POST")
	public void testErrorForInvalidMethodCall() throws IOException {
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProviderUsingInvalidMethod_DataLayer(practiceId,
				testData.getProperty("provider.firstname"), testData.getProperty("provider.lastname"),
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createProviderApiResponse);
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(createProviderApiResponse);
	}
}
