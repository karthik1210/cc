//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.SharedServiceApiValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class RemoveProviderImageApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	private Validations validation;
	private SharedServiceApiValidations sharedServiceApiValidations;
	Response addProviderImageApiResponse, getProviderDetailsApiResponse, createClientApiResponse,
			createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse, createEnterpriseApiResponse,
			deleteProviderImageApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, providerIdTwo;
	String clientName, clientSapNumber;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		sharedServiceApiValidations = new SharedServiceApiValidations();

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
		providerId = createProvider();
		providerIdTwo = createProvider();
	}

	public int createProvider() {
		logStep("Create new provider");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		return createProviderApiResponse.jsonPath().getInt("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Delete provider image using providerId  and verify response code")
	public void testResponseCodeToDeleteProviderImageWithValidProviderId() throws NullPointerException, IOException {
		logStep("Enter valid providerid with valid image format");
		URL url = ClassLoader.getSystemResource("data-driven/images/TestImage_JPG_Format.jpg");
		File file = new File(url.getFile());
		String fileType = "image/png";
		addProviderImageApiResponse = sharedInfo.addProviderImage_BL(providerId, file, fileType);
		CCUtils.assertEquals(addProviderImageApiResponse.getStatusCode(), 200);
		logStep("Delete provider image");
		deleteProviderImageApiResponse = sharedInfo.deleteProviderImage_BL(providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteProviderImageApiResponse);
		CCUtils.assertEquals(deleteProviderImageApiResponse.getStatusCode(), 200);
		logStep("Fetch provider details using provider id");
		getProviderDetailsApiResponse = sharedInfo.getProviderDetails(clientId, providerId);
		CCUtils.assertEquals(getProviderDetailsApiResponse.getStatusCode(), 200);
		logStep("Verify imageurl is not present for selected provider");
		Boolean value = sharedServiceApiValidations.validateImageUrlValueIsNull(getProviderDetailsApiResponse);
		CCUtils.assertEquals(value, true);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify image is not present for provider.Delete provider image using providerId and verify response code")
	public void testErrorResponseCodeForInvalidImage() throws NullPointerException, IOException {
		logStep("Fetch provider details using provider id");
		getProviderDetailsApiResponse = sharedInfo.getProviderDetails(clientId, providerIdTwo);
		CCUtils.assertEquals(getProviderDetailsApiResponse.getStatusCode(), 200);
		logStep("Verify imageurl is not present for selected provider");
		Boolean value = sharedServiceApiValidations.validateImageUrlValueIsNull(getProviderDetailsApiResponse);
		CCUtils.assertEquals(value, true);
		logStep("Delete provider image");
		deleteProviderImageApiResponse = sharedInfo.deleteProviderImage_BL(providerIdTwo);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteProviderImageApiResponse);
		CCUtils.assertEquals(deleteProviderImageApiResponse.getStatusCode(), 400);
		sharedServiceApiValidations.verifyErrorResponseForImageField(deleteProviderImageApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid provider id in providerId field and verify response code")
	public void testErrorResponseCodeForInvalidProviderId() throws NullPointerException, IOException {
		logStep("Delete provider image");
		int nonExistingProviderId = Integer.parseInt(testData.getProperty("non.existing.provider.id"));
		deleteProviderImageApiResponse = sharedInfo.deleteProviderImage_BL(nonExistingProviderId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(deleteProviderImageApiResponse);
		CCUtils.assertEquals(deleteProviderImageApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForNonexistingproviderId(deleteProviderImageApiResponse,
				nonExistingProviderId);
	}
}
