// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

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

public class GetProviderDetailsApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_DL clientHierObj;
	Response fetchProviderDetailsApiResponse, createProviderApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId;

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

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 0, description = "Enter existing providerId in providerId field and validate status code")
	public void testSuccessResponseCodeForExistingProviderId() throws NullPointerException, IOException {
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DataLayer(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createProviderApiResponse);
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		validation.verifyCreateProviderApiResponseBody(createProviderApiResponse, firstName, lastName);
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("fetch newly created provider details using provider id");
		fetchProviderDetailsApiResponse = sharedInfo.getProviderDetails_DL(clientId, providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForProviderDetails(fetchProviderDetailsApiResponse, firstName, lastName,
				testData.getProperty("provider.gender"), testData.getProperty("provider.status"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing providerId in providerId field and validate status code")
	public void testErrorResponseCodeForNonExistingProviderId() throws IOException {
		logStep("Enter non existing providerId");
		int providerId = Integer.parseInt(testData.getProperty("non.existing.provider.id"));
		fetchProviderDetailsApiResponse = sharedInfo.getProviderDetails_DL(clientId,providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(fetchProviderDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		logStep("Enter existing providerId");
		fetchProviderDetailsApiResponse = sharedInfo.getProviderDetailsInvalidUrl_DL(providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchProviderDetailsApiResponse);
	}
}
