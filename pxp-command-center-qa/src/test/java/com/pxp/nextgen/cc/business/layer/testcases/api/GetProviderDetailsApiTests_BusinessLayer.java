// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

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

public class GetProviderDetailsApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_BL clientHierObj;
	Response fetchProviderDetailsApiResponse, createClientApiResponse, createOrgNodeApiResponse,
			createProviderApiResponse;
	int clientId, systemId, enterpriseId, practiceId,providerId;
	String providerFirstName, lastName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		validation = new Validations();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		providerFirstName = "Pro"
				+ testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Create provider for resourceId");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, providerFirstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing providerId in providerId field and validate status code")
	public void testSuccessResponseCodeForExistingProviderId() throws NullPointerException, IOException {
		logStep("Enter existing providerId");
		fetchProviderDetailsApiResponse = sharedInfo.getProviderDetails(clientId, providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing providerId in providerId field and validate status code")
	public void testErrorResponseCodeForNonExistingProviderId() throws IOException {
		logStep("Enter non existing providerId");
		int providerId = Integer.parseInt(testData.getProperty("non.existing.provider.id"));
		fetchProviderDetailsApiResponse = sharedInfo.getProviderDetails(clientId, providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(fetchProviderDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		logStep("Enter existing providerId");
		fetchProviderDetailsApiResponse = sharedInfo.getProviderDetailsInvalidUrl(providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchProviderDetailsApiResponse);
	}
}
