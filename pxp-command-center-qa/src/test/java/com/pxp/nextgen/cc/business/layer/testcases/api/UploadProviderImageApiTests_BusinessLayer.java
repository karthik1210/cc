//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
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

public class UploadProviderImageApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	private Validations validation;
	private SharedServiceApiValidations sharedServiceApiValidations;
	Response addProviderImageApiResponse, getProviderDetailsApiResponse, createClientApiResponse,
			createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse, createEnterpriseApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId;
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

		logStep("Create new provider");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
	}

	@DataProvider(name = "getImage")
	public Object[] getImageFile() {
		Object[][] obj = new Object[][] { { "logo.png" }, { "TestImage_GIF_Format.gif" },
				{ "TestImage_JPEG_Format.jpeg" }, { "TestImage_JPG_Format.jpg" }, { "TestImage_RAW_Format.raw" },
				{ "TestImage_TIF_Format.tif" }, { "TestImage_TIFF_Format.tiff" }, { "leaf.svg" } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getImage", description = "Enter valid providerId with valid image format to add provider image and verify response code")
	public void testResponseCodeToAddProviderImageWithValidProviderId(String getImageValue)
			throws NullPointerException, IOException {
		log("Enter valid providerid with valid image format");
		URL url = ClassLoader.getSystemResource("data-driven/images/" + getImageValue);
		File file = new File(url.getFile());
		String fileType = "image/png";
		addProviderImageApiResponse = sharedInfo.addProviderImage_BL(providerId, file, fileType);
		CCUtils.responseTimeValidation(addProviderImageApiResponse);
		CCUtils.assertEquals(addProviderImageApiResponse.getStatusCode(), 200);
		logStep("fetch provider details via correct provider id");
		getProviderDetailsApiResponse = sharedInfo.getProviderDetails(clientId, providerId);
		CCUtils.assertEquals(getProviderDetailsApiResponse.getStatusCode(), 200);
		logStep("Verify imageurl is present for selected provider");
		Boolean value = sharedServiceApiValidations.validateImageUrlValueIsNull(getProviderDetailsApiResponse);
		CCUtils.assertEquals(value, false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid providerId with invalid image format to add provider image and verify response code")
	public void testResponseCodeToAddProviderImageWithInvalidImageFormat() throws IOException {
		log("Enter valid providerId with invalid image format");
		URL url = ClassLoader.getSystemResource("data-driven/images/Upload.pdf");
		File file = new File(url.getFile());
		String fileType = "application/pdf";
		addProviderImageApiResponse = sharedInfo.addProviderImage_BL(providerId, file, fileType);
		CCUtils.responseTimeValidation(addProviderImageApiResponse);
		CCUtils.assertEquals(addProviderImageApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidImage(addProviderImageApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing providerid with valid image format to add provider image and verify response code")
	public void testResponseCodeToAddProviderImageWithNonExistingProviderId() throws IOException {
		log("Enter non-existing providerid with valid image format");
		URL url = ClassLoader.getSystemResource("data-driven/images/logo.png");
		File file = new File(url.getFile());
		int nonExistingProviderId = Integer.parseInt(testData.getProperty("non.existing.provider.id"));
		String fileType = "image/png";
		addProviderImageApiResponse = sharedInfo.addProviderImage_BL(nonExistingProviderId, file, fileType);
		CCUtils.responseTimeValidation(addProviderImageApiResponse);
		CCUtils.assertEquals(addProviderImageApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForNonexistingproviderId(addProviderImageApiResponse, nonExistingProviderId);
	}
}
