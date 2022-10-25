//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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

public class SavePracticeProviderApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	Response savePracticeProviderApiResponse, fetchPracticeProviderApiResponse, createClientApiResponse,
			createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse, createEnterpriseApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, practiceIdOne;
	String clientName, clientSapNumber;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		logStep("Create nge practice below the client with valid details");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.system.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");
		logStep("Create a new system below client.");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "",
				testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), "", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new enterprise below system.");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"), "",
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new practice below enterprise.");
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DataLayer(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createProviderApiResponse);
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in all field, valid practice id, valid provider id and verify response body")
	public void testSavePracticeProviderFunctionality() throws IOException {
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		String email = CCUtils.generateNewEmail(10);
		String mobile = String.valueOf(CCUtils.generateMobileNumber());
		logStep("Enter valid details in save practice provider payload");
		savePracticeProviderApiResponse = sharedInfo.savePracticeProvider_DL(practiceId, providerId, enDisplayName,
				esDisplayName, email, mobile);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 200);
		logStep("Fetch practice provider details");
		fetchPracticeProviderApiResponse = sharedInfo.getPracticeProviderDetails_DL(practiceId, providerId);
		CCUtils.assertEquals(fetchPracticeProviderApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForGetPracticeProvider(fetchPracticeProviderApiResponse, enDisplayName,
				esDisplayName, email, mobile);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in all field, valid practice id and invalid provider id and verify response body")
	public void testSavePracticeProviderFunctionalityWithInvalidProviderId() throws IOException {
		String email = CCUtils.generateNewEmail(10);
		String mobile = String.valueOf(CCUtils.generateMobileNumber());
		logStep("Enter invalid provider id");
		savePracticeProviderApiResponse = sharedInfo.savePracticeProvider_DL(practiceId,
				Integer.parseInt(testData.getProperty("non.existing.provider.id")), testData.getProperty("api.en"),
				testData.getProperty("api.es"), email, mobile);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(savePracticeProviderApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in all field, invalid practice id and valid provider id and verify response body")
	public void testSavePracticeProviderFunctionalityWithInvalidPracticeId() throws IOException {
		String email = CCUtils.generateNewEmail(10);
		String mobile = String.valueOf(CCUtils.generateMobileNumber());
		logStep("Enter invalid practice id");
		savePracticeProviderApiResponse = sharedInfo.savePracticeProvider_DL(
				Integer.parseInt(testData.getProperty("non.existing.practice.id")), providerId,
				testData.getProperty("api.en"), testData.getProperty("api.es"), email, mobile);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(savePracticeProviderApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter practice id which is not associated with provider id and Verify response code")
	public void testErrorResponseForNonAssociatedProviderIdWithPracticeId() throws IOException {
		String email = CCUtils.generateNewEmail(10);
		String mobile = String.valueOf(CCUtils.generateMobileNumber());
		savePracticeProviderApiResponse = sharedInfo.savePracticeProvider_DL(practiceIdOne, providerId,
				testData.getProperty("api.en"), testData.getProperty("api.es"), email, mobile);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(savePracticeProviderApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid email id and verify response body")
	public void testErrorResponseForInvalidEmailId() throws IOException {
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		String mobile = String.valueOf(CCUtils.generateMobileNumber());
		logStep("Enter valid details in save practice provider payload");
		savePracticeProviderApiResponse = sharedInfo.savePracticeProvider_DL(practiceId, providerId, enDisplayName,
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
		String mobile = CCUtils.generateAlphaNumericString(31);
		String email = CCUtils.generateNewEmail(10);
		logStep("Enter valid details in save practice provider payload");
		savePracticeProviderApiResponse = sharedInfo.savePracticeProvider_DL(practiceId, providerId, enDisplayName,
				esDisplayName, email, mobile);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForInvalidMobileNumber(savePracticeProviderApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid practice id, valid provider id with null payload and verify response body")
	public void testErrorResponseForNullPayload() throws IOException {
		logStep("Enter null values in save practice provider payload");
		savePracticeProviderApiResponse = sharedInfo.savePracticeProviderNullPayload_DL(practiceId, providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values for practice id, provider id  and verify response body")
	public void testErrorResponseForNullValuesForPracticeIdAndProviderId() throws IOException {
		logStep("Enter null values for practice id, provider id  and verify response body");
		savePracticeProviderApiResponse = sharedInfo.savePracticeProviderNullValues_DL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(savePracticeProviderApiResponse);
		CCUtils.assertEquals(savePracticeProviderApiResponse.getStatusCode(), 400);
	}
}
