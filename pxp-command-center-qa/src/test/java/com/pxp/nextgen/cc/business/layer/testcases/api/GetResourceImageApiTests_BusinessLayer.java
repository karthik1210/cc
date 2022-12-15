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
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.ResourceValidations;
import io.restassured.response.Response;

public class GetResourceImageApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private ResourceValidations resourceValidation;
	Response addResourceImageApiResponse, getResourceDetailsApiResponse, createClientApiResponse,
			createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse, createEnterpriseApiResponse,
			createResourceApiResponse, getResourceImageApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId;
	String clientName, clientSapNumber,imageUrl;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		resourceInfo = new ResourceInfo();
		resourceValidation = new ResourceValidations();
		sharedInfo = new SharedInfo();
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
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response code of get resource image details")
	public void testResponseCodeToGetResourceImage() throws NullPointerException, IOException {
		log("Enter valid resourceid with valid image format");
		URL url = ClassLoader.getSystemResource("data-driven/images/logo.png");
		File file = new File(url.getFile());
		String fileType = "image/png";
		addResourceImageApiResponse = resourceInfo.addResourceImage_BL(resourceId, file, fileType);
		CCUtils.responseTimeValidation(addResourceImageApiResponse);
		CCUtils.assertEquals(addResourceImageApiResponse.getStatusCode(), 200);
		getResourceDetailsApiResponse = resourceInfo.getResourceDetails_BL(resourceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 200);
		imageUrl=getResourceDetailsApiResponse.jsonPath().getString("imageUrl");
		getResourceImageApiResponse=resourceInfo.getResourceImage_BL(imageUrl);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceImageApiResponse);
		CCUtils.assertEquals(getResourceImageApiResponse.getStatusCode(), 200);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response code of get resource image details with null values")
	public void testResponseCodeToGetResourceImageWithNull() throws NullPointerException, IOException {
		logStep("Enter the null values in Image Url");
		getResourceImageApiResponse=resourceInfo.getResourceImage_Null_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceImageApiResponse);
		CCUtils.assertEquals(getResourceImageApiResponse.getStatusCode(), 400);
		resourceValidation.verifyErrorMessageForResourceWithNonExistingUrl(getResourceImageApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response code of entering non-existing image url details")
	public void testResponseCodeToGetResourceImageWithInvalidImageUrl() throws NullPointerException, IOException {
		logStep("Enter the non Existing Image Url");
		getResourceImageApiResponse=resourceInfo.getResourceImage_BL(testData.getProperty("non.existing.image.id"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceImageApiResponse);
		CCUtils.assertEquals(getResourceImageApiResponse.getStatusCode(), 400);
		resourceValidation.verifyErrorMessageForResourceWithNonExistingUrl(getResourceImageApiResponse);
	}	
}
