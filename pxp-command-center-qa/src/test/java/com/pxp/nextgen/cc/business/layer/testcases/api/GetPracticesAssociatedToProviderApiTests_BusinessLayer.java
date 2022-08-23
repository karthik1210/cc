// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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

public class GetPracticesAssociatedToProviderApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	Response fetchProviderDetailsApiResponse;
	int providerId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupQaServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check response for one provider associated with one practice")
	public void testResponseCodeForOneProviderAssociatedWithOnePractice() throws IOException {
		logStep("Enter existing providerId");
		fetchProviderDetailsApiResponse = sharedInfo
				.getPracticesAssociatedToProvider(Integer.parseInt(testData.getProperty("existing.provider.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 200);
		ArrayList<Integer> practiceIds = new ArrayList<Integer>();
		practiceIds.add(Integer.parseInt(testData.getProperty("practice.id.one")));
		validation.verifyResponseBodyForProviderAssociatedToPractices(fetchProviderDetailsApiResponse, practiceIds);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check response for one provider associated with multiple practices")
	public void testResponseCodeForOneProviderAssociatedWithMultiplePractices() throws IOException {
		logStep("Enter existing providerId");
		fetchProviderDetailsApiResponse = sharedInfo
				.getPracticesAssociatedToProvider(Integer.parseInt(testData.getProperty("existing.provider.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 200);
		ArrayList<Integer> practiceIds = new ArrayList<Integer>();
		practiceIds.add(Integer.parseInt(testData.getProperty("practice.id.one")));
        practiceIds.add(Integer.parseInt(testData.getProperty("practice.id.two")));
		validation.verifyResponseBodyForProviderAssociatedToPractices(fetchProviderDetailsApiResponse, practiceIds);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing providerId in providerId field and validate status code")
	public void testErrorResponseCodeForNonExistingProviderId() throws IOException {
		logStep("Enter non existing providerId");
		fetchProviderDetailsApiResponse = sharedInfo
				.getPracticesAssociatedToProvider(Integer.parseInt(testData.getProperty("non.existing.provider.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		logStep("Enter existing providerId");
		fetchProviderDetailsApiResponse = sharedInfo.getPracticesAssociatedToProviderInvalidUrl(
				Integer.parseInt(testData.getProperty("existing.provider.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchProviderDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if POST is used instead of GET")
	public void testErrorForInvalidMethodCall() throws IOException {
		logStep("Enter existing providerId");
		fetchProviderDetailsApiResponse = sharedInfo.getPracticesAssociatedToProviderInvalidMethodCall(
				Integer.parseInt(testData.getProperty("existing.provider.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(fetchProviderDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check response for null values in providerId field")
	public void testErrorResponseCodeForNullValues() throws IOException {
		fetchProviderDetailsApiResponse = sharedInfo.getPracticesAssociatedToProvider_NullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageIfClientIdIsNull(fetchProviderDetailsApiResponse);
	}
}
