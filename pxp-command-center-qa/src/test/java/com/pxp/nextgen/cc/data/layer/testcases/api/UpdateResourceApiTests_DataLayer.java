//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
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
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class UpdateResourceApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private ClientInfo clientInfo;
	private ResourceInfo resourceInfo;
	int clientId, systemId, enterpriseId, practiceId, resourceId, solutionId, providerId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse, createClientApiResponse,
			createProviderApiResponse, updateResourceDetailsApiResponse, createResourceApiResponse;
	private OrgInfo orgInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		resourceInfo = new ResourceInfo();
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
		String firstName = "Provi"
				+ testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Create provider for resourceId");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		String resourceName = "Resour" + CCUtils.generateAlphaNumericString(8);
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		resourceId = createResourceApiResponse.jsonPath().get("resourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enters existing resourceId and valid input in request Payload and validate response code")
	public void testSuccessResponseCodeForExistingResourceIdForValidPayload() throws NullPointerException, IOException {
		logStep("Enters existing resourceId and valid input in request Payload");
		String resourceName = "Res "
				+ testData.getProperty("resource.name").concat(CCUtils.generateAlphaNumericString(5));
		String resourceType = testData.getProperty("resource.type");
		String resourceExternalId = "EX_ID_001" + CCUtils.generateAlphaNumericString(10);
		String resourceLinkType = testData.getProperty("resource.link.type");
		updateResourceDetailsApiResponse = sharedInfo.updateResourceDetails_DL(resourceId, resourceName, resourceType,
				resourceExternalId, resourceLinkType, providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourceDetailsApiResponse);
		CCUtils.assertEquals(updateResourceDetailsApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForUpdateResource(updateResourceDetailsApiResponse, resourceId, resourceName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enters existing resourceId, Invalid values in payload and validate status code")
	public void testErrorResponseCodeForExistingResourceIdForInvalidPayload() throws NullPointerException, IOException {
		logStep("Enters existing resourceId, Invalid values in payload");
		String resourceName = testData.getProperty("api.invalid.firstName");
		String resourceType = CCUtils.generateAlphaNumericString(10);
		String resourceExternalId = "EX_ID_001" + CCUtils.generateAlphaNumericString(10);
		String resourceLinkType = CCUtils.generateAlphaNumericString(10);
		updateResourceDetailsApiResponse = sharedInfo.updateResourceDetails_DL(resourceId, resourceName, resourceType,
				resourceExternalId, resourceLinkType, providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourceDetailsApiResponse);
		CCUtils.assertEquals(updateResourceDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateResourceDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing resourceId, and valid input in Payload and validate status code")
	public void testErrorResponseCodeForNonExistingResourceIdForValidPayload() throws IOException {
		logStep("Enter non existing resourceId, and valid input in Payload");
		int resourceId = Integer.parseInt(testData.getProperty("non.existing.resource.id"));
		String resourceName = "Res "
				+ testData.getProperty("resource.name").concat(CCUtils.generateAlphaNumericString(5));
		String resourceType = testData.getProperty("resource.type");
		String resourceExternalId = "EX_ID_001" + CCUtils.generateAlphaNumericString(10);
		String resourceLinkType = testData.getProperty("resource.link.type");
		updateResourceDetailsApiResponse = sharedInfo.updateResourceDetails_DL(resourceId, resourceName, resourceType,
				resourceExternalId, resourceLinkType, providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourceDetailsApiResponse);
		CCUtils.assertEquals(updateResourceDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForUpdateResource(updateResourceDetailsApiResponse, resourceId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enters existing resourceId, Null payload and validate status code")
	public void testErrorResponseCodeForExistingResourceIdForNullValuesInPayload()
			throws NullPointerException, IOException {
		logStep("Enters existing resourceId, Null payload");
		updateResourceDetailsApiResponse = sharedInfo.updateResourceDetails_NullPayload_DL(resourceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourceDetailsApiResponse);
		CCUtils.assertEquals(updateResourceDetailsApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enters Null resourceId And Null payload and validate status code")
	public void testErrorResponseCodeForNullValuesInResourceIdForNullValuesInPayload()
			throws NullPointerException, IOException {
		logStep("Enters Null resourceId And Null payload");
		updateResourceDetailsApiResponse = sharedInfo.updateResourceDetails_NullValues_DL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourceDetailsApiResponse);
		CCUtils.assertEquals(updateResourceDetailsApiResponse.getStatusCode(), 400);
	}

}