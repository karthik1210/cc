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

public class SavePracticeProviderApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_BL clientHierObj;
	Response savePracticeProviderApiResponse, fetchPracticeProviderApiResponse, createClientApiResponse,
			createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse, createEnterpriseApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, practiceIdOne;
	String clientName, clientSapNumber;

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
		practiceIdOne = clientHierObj.createPractice_BL(enterpriseId);
		logStep("Create new provider");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in all field, valid practice id, valid provider id and verify response body")
	public void testSavePracticeProviderFunctionality() throws IOException {
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		String email = CCUtils.generateNewEmail(10);
		String mobile = String.valueOf(CCUtils.generateMobileNumber());
		logStep("Enter valid details in save practice provider payload");
		savePracticeProviderApiResponse = sharedInfo.savePracticeProvider_BL(practiceId, providerId, enDisplayName,
				esDisplayName, email, mobile);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 200);
		logStep("Fetch practice provider details");
		fetchPracticeProviderApiResponse = sharedInfo.getPracticeProviderDetails_BL(practiceId, providerId);
		CCUtils.assertEquals(fetchPracticeProviderApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForGetPracticeProvider(fetchPracticeProviderApiResponse, enDisplayName,
				esDisplayName, email, mobile);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in all field, valid practice id and invalid provider id and verify response body")
	public void testInvalidProviderId() throws IOException {
		String email = CCUtils.generateNewEmail(10);
		String mobile = String.valueOf(CCUtils.generateMobileNumber());
		logStep("Enter invalid provider id");
		savePracticeProviderApiResponse = sharedInfo.savePracticeProvider_BL(practiceId,
				Integer.parseInt(testData.getProperty("non.existing.provider.id")), testData.getProperty("api.en"),
				testData.getProperty("api.es"), email, mobile);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(savePracticeProviderApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in all field, invalid practice id and valid provider id and verify response body")
	public void testInvalidPracticeId() throws IOException {
		String email = CCUtils.generateNewEmail(10);
		String mobile = String.valueOf(CCUtils.generateMobileNumber());
		logStep("Enter invalid practice id");
		savePracticeProviderApiResponse = sharedInfo.savePracticeProvider_BL(
				Integer.parseInt(testData.getProperty("non.existing.practice.id")), providerId,
				testData.getProperty("api.en"), testData.getProperty("api.es"), email, mobile);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(savePracticeProviderApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		String email = CCUtils.generateNewEmail(10);
		String mobile = String.valueOf(CCUtils.generateMobileNumber());
		savePracticeProviderApiResponse = sharedInfo.savePracticeProviderUsingInvalidUrl_BL(practiceId, providerId,
				testData.getProperty("api.en"), testData.getProperty("api.es"), email, mobile);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(savePracticeProviderApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Make Post Call instead of GET and verify error message")
	public void testErrorMessageForInvalidMethodCall() throws IOException {
		String email = CCUtils.generateNewEmail(10);
		String mobile = String.valueOf(CCUtils.generateMobileNumber());
		savePracticeProviderApiResponse = sharedInfo.savePracticeProviderUsingInvalidMethod_BL(practiceId, providerId,
				testData.getProperty("api.en"), testData.getProperty("api.es"), email, mobile);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(savePracticeProviderApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter practice id which is not associated with provider id and Verify response id")
	public void testErrorForNonAssociatedProviderId() throws IOException {
		String email = CCUtils.generateNewEmail(10);
		String mobile = String.valueOf(CCUtils.generateMobileNumber());
		savePracticeProviderApiResponse = sharedInfo.savePracticeProvider_BL(practiceIdOne, providerId,
				testData.getProperty("api.en"), testData.getProperty("api.es"), email, mobile);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(savePracticeProviderApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid email id and verify response body")
	public void testErrorResponseForEmailId() throws IOException {
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		String mobile = String.valueOf(CCUtils.generateMobileNumber());
		logStep("Enter valid details in save practice provider payload");
		savePracticeProviderApiResponse = sharedInfo.savePracticeProvider_BL(practiceId, providerId, enDisplayName,
				esDisplayName, testData.getProperty("invalid.user.email.one"), mobile);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForInvalidEmailFormat(savePracticeProviderApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid mobile number and verify response body")
	public void testErrorResponseForInvalidMobileNumber() throws IOException {
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		String mobile = CCUtils.generateAlphaNumericString(20);
		String email = CCUtils.generateNewEmail(10);
		logStep("Enter valid details in save practice provider payload");
		savePracticeProviderApiResponse = sharedInfo.savePracticeProvider_BL(practiceId, providerId, enDisplayName,
				esDisplayName, email, mobile);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForInvalidMobileNumber(savePracticeProviderApiResponse);
	}
}
