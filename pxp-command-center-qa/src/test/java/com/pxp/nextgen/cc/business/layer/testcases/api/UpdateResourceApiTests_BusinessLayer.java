//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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
import io.restassured.response.Response;

public class UpdateResourceApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private ClientInfo clientInfo;
	private ResourceInfo resourceInfo;
	int clientId, systemId, enterpriseId, practiceId, resourceId, solutionId, providerId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse, createClientApiResponse,
			createProviderApiResponse, updateResourceDetailsApiResponse, createResourceApiResponse;
	private OrgInfo orgInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		resourceInfo = new ResourceInfo();
		sharedInfo = new SharedInfo();

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

		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enters existing resourceId and valid input in request Payload and validate response code")
	public void testSuccessResponseCodeForExistingResourceIdForValidPayload() throws NullPointerException, IOException {
		logStep("Enters existing resourceId and valid input in request Payload");
		String resourceName = "Res "
				+ testData.getProperty("resource.name").concat(CCUtils.generateAlphaNumericString(5));
		String resourceType = testData.getProperty("resource.type");
		String resourceExternalId = "EX_ID_001" + CCUtils.generateAlphaNumericString(10);
		String resourceLinkType = testData.getProperty("resource.link.type");
		updateResourceDetailsApiResponse = sharedInfo.updateResourceDetails_BL(resourceId, resourceName, resourceType,
				resourceExternalId, resourceLinkType, providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourceDetailsApiResponse);
		CCUtils.assertEquals(updateResourceDetailsApiResponse.getStatusCode(), 200);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enters existing resourceId, Invalid values in payload and validate status code")
	public void testErrorResponseCodeForExistingResourceIdForInvalidPayload() throws NullPointerException, IOException {
		logStep("Enters existing resourceId, Invalid values in payload");
		String resourceName = testData.getProperty("api.invalid.firstName");
		String resourceType = CCUtils.generateAlphaNumericString(10);
		String resourceExternalId = "EX_ID_001" + CCUtils.generateAlphaNumericString(10);
		String resourceLinkType = CCUtils.generateAlphaNumericString(10);
		updateResourceDetailsApiResponse = sharedInfo.updateResourceDetails_BL(resourceId, resourceName, resourceType,
				resourceExternalId, resourceLinkType, providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourceDetailsApiResponse);
		CCUtils.assertEquals(updateResourceDetailsApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing resourceId, and valid input in Payload and validate status code")
	public void testErrorResponseCodeForNonExistingResourceIdForValidPayload() throws IOException {
		logStep("Enter non existing resourceId, and valid input in Payload");
		int nonexistingResourceId = Integer.parseInt(testData.getProperty("non.existing.resource.id"));
		String resourceName = "Res"
				+ testData.getProperty("resource.name").concat(CCUtils.generateAlphaNumericString(5));
		String resourceType = testData.getProperty("resource.type");
		String resourceExternalId = "EX_ID_001" + CCUtils.generateAlphaNumericString(10);
		String resourceLinkType = testData.getProperty("resource.link.type");
		updateResourceDetailsApiResponse = sharedInfo.updateResourceDetails_BL(nonexistingResourceId, resourceName, resourceType,
				resourceExternalId, resourceLinkType, providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourceDetailsApiResponse);
		CCUtils.assertEquals(updateResourceDetailsApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enters existing resourceId, Null payload and validate status code")
	public void testErrorResponseCodeForExistingResourceIdForNullValuesInPayload()
			throws NullPointerException, IOException {
		logStep("Enters existing resourceId, Null payload");
		updateResourceDetailsApiResponse = sharedInfo.updateResourceDetails_NullPayload_BL(resourceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourceDetailsApiResponse);
		CCUtils.assertEquals(updateResourceDetailsApiResponse.getStatusCode(), 400);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enters Null resourceId And Null payload and validate status code")
	public void testErrorResponseCodeForNullValuesInResourceIdForNullValuesInPayload()
			throws NullPointerException, IOException {
		logStep("Enters Null resourceId And Null payload");
		updateResourceDetailsApiResponse = sharedInfo.updateResourceDetails_NullValues_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateResourceDetailsApiResponse);
		CCUtils.assertEquals(updateResourceDetailsApiResponse.getStatusCode(), 400);
	}

}