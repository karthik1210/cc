//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import org.testng.annotations.Test;
import java.io.IOException;
import org.testng.annotations.BeforeClass;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class UpdateProviderDetailsApiTests_DataLayer extends BaseRest {

	private SharedInfo sharedInfo;
	private Validations validation;
	private ClientInfo clientInfo;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse, createClientApiResponse,
			createProviderApiResponse, updateProviderDetailsApiResponse;
	private OrgInfo orgInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();

		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		clientId = createClientApiResponse.jsonPath().get("clientId");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
				testData.getProperty("api.system.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		systemId = createSystemApiResponse.jsonPath().get("orgId");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.displayname"), testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		enterpriseId = createEnterpriseApiResponse.jsonPath().get("orgId");
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
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
