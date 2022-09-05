//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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
	Response savePracticeProviderApiResponse, fetchPracticeProviderApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in all field, valid practice id, valid provider id and verify response body")
	public void testSavePracticeProviderFunctionality() throws IOException {
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		String email = CCUtils.generateNewEmail(10);
		String mobile = String.valueOf(CCUtils.generateMobileNumber());
		logStep("Enter valid details in payload");
		savePracticeProviderApiResponse = sharedInfo.savePracticeProvider_BL(
				Integer.parseInt(testData.getProperty("new.practice.id.one")),
				Integer.parseInt(testData.getProperty("new.existing.provider.id")), enDisplayName, esDisplayName, email,
				mobile);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 200);
		logStep("Fetch practice provider details");
		fetchPracticeProviderApiResponse = sharedInfo.getPracticeProviderDetails_BL(
				Integer.parseInt(testData.getProperty("new.practice.id.one")),
				Integer.parseInt(testData.getProperty("new.existing.provider.id")));
		CCUtils.assertEquals(fetchPracticeProviderApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForGetPracticeProvider(fetchPracticeProviderApiResponse, enDisplayName,
				esDisplayName, email, mobile);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in all field, valid practice id and invalid provider id and verify response body")
	public void testInvalidProviderId() throws IOException {
		String email = CCUtils.generateNewEmail(10);
		String mobile = String.valueOf(CCUtils.generateMobileNumber());
		logStep("Enter invalid provider id");
		savePracticeProviderApiResponse = sharedInfo.savePracticeProvider_BL(
				Integer.parseInt(testData.getProperty("new.practice.id.one")),
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
				Integer.parseInt(testData.getProperty("non.existing.practice.id")),
				Integer.parseInt(testData.getProperty("new.existing.provider.id")), testData.getProperty("api.en"),
				testData.getProperty("api.es"), email, mobile);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(savePracticeProviderApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		String email = CCUtils.generateNewEmail(10);
		String mobile = String.valueOf(CCUtils.generateMobileNumber());
		savePracticeProviderApiResponse = sharedInfo.savePracticeProviderUsingInvalidUrl_BL(
				Integer.parseInt(testData.getProperty("non.existing.practice.id")),
				Integer.parseInt(testData.getProperty("new.existing.provider.id")), testData.getProperty("api.en"),
				testData.getProperty("api.es"), email, mobile);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(savePracticeProviderApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Make Post Call instead of GET and verify error message")
	public void testErrorMessageForInvalidMethodCall() throws IOException {
		String email = CCUtils.generateNewEmail(10);
		String mobile = String.valueOf(CCUtils.generateMobileNumber());
		savePracticeProviderApiResponse = sharedInfo.savePracticeProviderUsingInvalidMethod_BL(
				Integer.parseInt(testData.getProperty("non.existing.practice.id")),
				Integer.parseInt(testData.getProperty("new.existing.provider.id")), testData.getProperty("api.en"),
				testData.getProperty("api.es"), email, mobile);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(savePracticeProviderApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter provider id which is not associated with practice id and Verify response id")
	public void testErrorForNonAssociatedProviderId() throws IOException {
		String email = CCUtils.generateNewEmail(10);
		String mobile = String.valueOf(CCUtils.generateMobileNumber());
		savePracticeProviderApiResponse = sharedInfo.savePracticeProvider_BL(
				Integer.parseInt(testData.getProperty("non.existing.practice.id")),
				Integer.parseInt(testData.getProperty("provider.id")), testData.getProperty("api.en"),
				testData.getProperty("api.es"), email, mobile);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(savePracticeProviderApiResponse);
	}
}
