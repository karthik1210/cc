//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
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

public class UpdateProviderApiTests_BusinessLayer extends BaseRest{
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
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		
		logStep("Create nge practice below the client with valid details");
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber, "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId(),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		clientId = createClientApiResponse.jsonPath().getInt("clientId");
		logStep("Create a new system below client.");
		createSystemApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", testData.getProperty("api.system.active"), "", clientId,
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new enterprise below system.");
		createEnterpriseApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemId,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new practice below enterprise.");
		createPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");

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