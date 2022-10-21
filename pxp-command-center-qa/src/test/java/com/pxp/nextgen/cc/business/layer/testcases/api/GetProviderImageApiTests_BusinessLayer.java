//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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
import io.restassured.response.Response;

public class GetProviderImageApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	private SharedServiceApiValidations sharedServiceApiValidations;
	Response addProviderImageApiResponse, getProviderDetailsApiResponse, createClientApiResponse,
			createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse, createEnterpriseApiResponse,
			getProviderImageApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId;
	String clientName, clientSapNumber;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		sharedServiceApiValidations = new SharedServiceApiValidations();

		logStep("Create nge practice below the client with valid details");
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber, "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get provider image using valid image id and verify response code")
	public void testGetProviderImageFunctionalityUsingValidImageId() throws NullPointerException, IOException {
		logStep("Enter valid providerid with valid image format");
		URL url = ClassLoader.getSystemResource("data-driven/images/TestImage_JPG_Format.jpg");
		File file = new File(url.getFile());
		String fileType = "image/png";
		addProviderImageApiResponse = sharedInfo.addProviderImage_BL(providerId, file, fileType);
		CCUtils.responseTimeValidation(addProviderImageApiResponse);
		CCUtils.assertEquals(addProviderImageApiResponse.getStatusCode(), 200);
		logStep("fetch provider details using correct provider id");
		getProviderDetailsApiResponse = sharedInfo.getProviderDetails(providerId);
		CCUtils.assertEquals(getProviderDetailsApiResponse.getStatusCode(), 200);
		logStep("Get provider image using valid image id");
		String imageId = sharedServiceApiValidations.getImageUrl(getProviderDetailsApiResponse);
		getProviderImageApiResponse = sharedInfo.getProviderImage_BL(imageId);
		CCUtils.assertEquals(getProviderImageApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get provider image using invalid image id and verify response code")
	public void testGetProviderImageFunctionalityUsingInvalidImageId() throws NullPointerException, IOException {
		logStep("Get provider image using invalid image id");
		String imageId = testData.getProperty("non.existing.image.id");
		getProviderImageApiResponse = sharedInfo.getProviderImage_BL(imageId);
		CCUtils.assertEquals(getProviderImageApiResponse.getStatusCode(), 400);
		sharedServiceApiValidations.verifyErrorResponseForInvalidImageId(getProviderImageApiResponse);
	}

}
