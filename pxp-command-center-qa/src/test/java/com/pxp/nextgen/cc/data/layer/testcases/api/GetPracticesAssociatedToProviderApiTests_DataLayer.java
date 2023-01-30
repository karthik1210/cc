// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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

public class GetPracticesAssociatedToProviderApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	Response fetchProviderDetailsApiResponse;
	int clientId, systemId, enterpriseId, practiceId, locationId, providerId;
	String externalId, firstName, lastName;
	Response updatePracticeForProvider, createProviderApiResponse, createResourceApiResponse,
			getResourceDetailsApiResponse;
	private CreateClientHierarchy_DL clientHierObj;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		logStep("Create new provider");
		firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check response for one provider associated with one practice")
	public void testResponseCodeForOneProviderAssociatedWithOnePractice() throws IOException {
		logStep("Enter existing providerId");
		fetchProviderDetailsApiResponse = sharedInfo.getPracticesAssociatedToProvider_DL(providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 200);
		ArrayList<Integer> practiceIds = new ArrayList<Integer>();
		practiceIds.add(practiceId);
		validation.verifyResponseBodyForProviderAssociatedToPractices(fetchProviderDetailsApiResponse, practiceIds);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check response for one provider associated with multiple practices")
	public void testResponseCodeForOneProviderAssociatedWithMultiplePractices() throws IOException {
		logStep("Enter existing providerId");
		fetchProviderDetailsApiResponse = sharedInfo.getPracticesAssociatedToProvider_DL(providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 200);
		ArrayList<Integer> practiceIds = new ArrayList<Integer>();
		practiceIds.add(practiceId);
		validation.verifyResponseBodyForProviderAssociatedToPractices(fetchProviderDetailsApiResponse, practiceIds);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing providerId in providerId field and validate status code")
	public void testErrorResponseCodeForNonExistingProviderId() throws IOException {
		logStep("Enter non existing providerId");
		fetchProviderDetailsApiResponse = sharedInfo.getPracticesAssociatedToProvider_DL(
				Integer.parseInt(testData.getProperty("non.existing.provider.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		logStep("Enter existing providerId");
		fetchProviderDetailsApiResponse = sharedInfo.getPracticesAssociatedToProviderInvalidUrl_DL(
				Integer.parseInt(testData.getProperty("existing.provider.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchProviderDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if POST is used instead of GET")
	public void testErrorMessageForInvalidMethodCall() throws IOException {
		logStep("Enter existing providerId");
		fetchProviderDetailsApiResponse = sharedInfo.getPracticesAssociatedToProviderInvalidMethodCall_DL(
				Integer.parseInt(testData.getProperty("existing.provider.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProviderDetailsApiResponse);
		CCUtils.assertEquals(fetchProviderDetailsApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(fetchProviderDetailsApiResponse);
	}
}
