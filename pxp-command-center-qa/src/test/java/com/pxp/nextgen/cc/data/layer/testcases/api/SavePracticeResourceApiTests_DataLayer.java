package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class SavePracticeResourceApiTests_DataLayer extends BaseRest {

	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_DL clientHierObj;
	Response createResourceApiResponse, createProviderApiResponse, updateResourceNameResponse;
	public int clientId, systemId, enterpriseId, practiceId, providerId, resourceId;
	String externalId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		resourceInfo = new ResourceInfo();
		clientHierObj = new CreateClientHierarchy_DL();
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		providerId = createProviderForPractice(practiceId);
		resourceId = createResource(providerId);
	}

	public int createProviderForPractice(int practiceId) throws IOException {
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createProviderApiResponse);
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		validation.verifyCreateProviderApiResponseBody(createProviderApiResponse, firstName, lastName);
		int providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		return providerId;
	}

	public int createResource(int providerId) {
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		int resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
		return resourceId;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify update resource name with correct practice id and resource id")
	public void testToUpdateResourceName() throws IOException {
		logStep("To update the resource name");
		updateResourceNameResponse = resourceInfo.putDisplayNameOfPracticeResource_DL(resourceId, practiceId,
				testData.getProperty("resource.name"), testData.getProperty("resource.name"));
		logStep("Validate response code is 200");
		CCUtils.assertEquals(updateResourceNameResponse.getStatusCode(), 200);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourceNameResponse);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify update resource name with non-existing practice id and existing resource id")
	public void testToUpdateResourceNameWithNonExistingPracticeIdAndExistingResourceId() throws IOException {
		logStep("To update the resource name");
		updateResourceNameResponse = resourceInfo.putDisplayNameOfPracticeResource_DL(resourceId,
				Integer.parseInt(testData.getProperty("nonexisting.practiceId")), testData.getProperty("resource.name"),
				testData.getProperty("resource.name"));
		logStep("Validate response code is 400");
		CCUtils.assertEquals(updateResourceNameResponse.getStatusCode(), 400);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourceNameResponse);
		validation.verifyErrorMessageForBlankField(updateResourceNameResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify update resource name with non-existing practice id and existing resource id")
	public void testToUpdateResourceNameWithExistingPracticeIdAndNonExistingResourceId() throws IOException {
		logStep("To update the resource name");
		updateResourceNameResponse = resourceInfo.putDisplayNameOfPracticeResource_DL(
				Integer.parseInt(testData.getProperty("nonexisting.resourceId")), practiceId,
				testData.getProperty("resource.name"), testData.getProperty("resource.name"));
		logStep("Validate response code is 400");
		CCUtils.assertEquals(updateResourceNameResponse.getStatusCode(), 400);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourceNameResponse);
		validation.verifyErrorMessageForBlankField(updateResourceNameResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify update resource name with non-existing practice id and existing resource id")
	public void testToUpdateResourceNameWithNonExistingPracticeIdAndNonExistingResourceId() throws IOException {
		logStep("To update the resource name");
		updateResourceNameResponse = resourceInfo.putDisplayNameOfPracticeResource_DL(
				Integer.parseInt(testData.getProperty("nonexisting.resourceId")),
				Integer.parseInt(testData.getProperty("nonexisting.practiceId")), testData.getProperty("resource.name"),
				testData.getProperty("resource.name"));
		logStep("Validate response code is 400");
		CCUtils.assertEquals(updateResourceNameResponse.getStatusCode(), 400);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourceNameResponse);
		validation.verifyErrorMessageForBlankField(updateResourceNameResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify update resource name with null practice id and resource id and null response body")
	public void testToUpdateResourceNameWithNullPracticeIdAndResourceIdAndNullResponseBody() throws IOException {
		logStep("To update the resource name");
		updateResourceNameResponse = resourceInfo.putDisplayNameOfPracticeResource__NULL_DL();
		logStep("Validate response code is 400");
		CCUtils.assertEquals(updateResourceNameResponse.getStatusCode(), 400);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourceNameResponse);
	}
}
