//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;

public class GetDisplayNameOfPracticeResourceApiTests_DataLayer extends BaseRest {
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId, resourceId;
	Response createProviderApiResponse, createResourceApiResponse, getResourceDetailsApiResponse,
			savePracticeResourceApiResponse;
	private SharedInfo sharedInfo;
	private ResourceInfo resourceInfo;
	private CreateClientHierarchy_DL clientHierObj;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		resourceInfo = new ResourceInfo();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		resourceId = createNewResource();
	}

	public int createNewResource() {
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		return createResourceApiResponse.jsonPath().getInt("resourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get resource display details by entering existing resourceId")
	public void testGetGetDisplayNameOfPracticeResource() throws IOException {
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Enter valid details in save practice provider payload");
		savePracticeResourceApiResponse = sharedInfo.savePracticeResource_DL(practiceId, resourceId, enDisplayName,
				esDisplayName);
		logStep("fetch resource  display details via correct resource id and Practice id");
		getResourceDetailsApiResponse = resourceInfo.getDisplayNameOfPracticeResource_DL(resourceId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get resource display details error message by entering non-existing resourceId")
	public void testGetResourceDisplayNameForNonExistingResourceId() throws IOException {
		logStep("fetch resource details error message via non-existing resource id");
		getResourceDetailsApiResponse = resourceInfo.getDisplayNameOfPracticeResource_DL(
				Integer.parseInt(testData.getProperty("non.existing.resource.id")), practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get resource display details error message by entering non-existing practiceId")
	public void testGetResourceDisplayNameForNonExistingPracticeId() throws IOException {
		logStep("fetch resource details error message via non-existing practice id");
		getResourceDetailsApiResponse = resourceInfo.getDisplayNameOfPracticeResource_DL(resourceId,
				Integer.parseInt(testData.getProperty("non.existing.practice.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get resource display details error message by entering non-existing resourceId, non-existing practiceId")
	public void testGetResourceDisplayNameForNonExistingPracticeIdResourceId() throws IOException {
		logStep("fetch resource details error message via non-existing practiceId, non-existing practiceId");
		getResourceDetailsApiResponse = resourceInfo.getDisplayNameOfPracticeResource_DL(
				Integer.parseInt(testData.getProperty("non.existing.resource.id")),
				Integer.parseInt(testData.getProperty("non.existing.practice.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get resource details error message by entering null resource id and practice id")
	public void testGetResourceDisplayNameForNullResourceId() throws IOException {
		logStep("fetch resource details error message via Null values");
		getResourceDetailsApiResponse = resourceInfo.getDisplayNameOfPracticeResource_DL_NULL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 400);
	}
}