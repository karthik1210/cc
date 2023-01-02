//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

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
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AssociateDeassociatePracticeForResource_DataLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private Validations validation;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createResourceApiResponse, associateDeassociatePracticeForResourceApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, resourceIdOne;
	String clientName, clientSapNumber, externalId, firstName, lastName, resourceExternalId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		resourceInfo = new ResourceInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();

		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
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
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Create provider for resourceId");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new resource");
		resourceId = createResource();
	}

	public int createResource() {
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		String resourceName = CCUtils.generateAlphaNumericString(8);
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		return createResourceApiResponse.jsonPath().getInt("resourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add practiceid for existing resourceId and verify response body")
	public void testResponseCodeToAddPracticeForResource() throws NullPointerException, IOException {
		logStep("Add practiceid for existing resourceId");
		associateDeassociatePracticeForResourceApiResponse = sharedInfo
				.associateDeassociatePracticeForResource_DL(resourceId, practiceId, "true");
		CCUtils.assertEquals(associateDeassociatePracticeForResourceApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Remove practiceid for existing resourceId and verify response body")
	public void testResponseCodeToRemovePracticeForResource() throws NullPointerException, IOException {
		logStep("Remove practiceid for existing resourceId");
		associateDeassociatePracticeForResourceApiResponse = sharedInfo
				.associateDeassociatePracticeForResource_DL(resourceId, practiceId, "false");
		CCUtils.assertEquals(associateDeassociatePracticeForResourceApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add existing practiceid for non-existing resourceId and verify response body")
	public void testResponseCodeToAddPracticeForNonExistingResource() throws NullPointerException, IOException {
		logStep("Add existing practiceid for non-existing resourceId");
		associateDeassociatePracticeForResourceApiResponse = sharedInfo.associateDeassociatePracticeForResource_DL(
				Integer.parseInt(testData.getProperty("non.existing.resource.id")), practiceId, "true");
		CCUtils.assertEquals(associateDeassociatePracticeForResourceApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(associateDeassociatePracticeForResourceApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add non-existing practiceid for existing resourceId and verify response body")
	public void testResponseCodeToAddNonExistingPracticeForResource() throws NullPointerException, IOException {
		logStep("Add non-existing practiceid for existing resourceId");
		associateDeassociatePracticeForResourceApiResponse = sharedInfo.associateDeassociatePracticeForResource_DL(
				resourceId, Integer.parseInt(testData.getProperty("nonexisting.practiceId")), "true");
		CCUtils.assertEquals(associateDeassociatePracticeForResourceApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(associateDeassociatePracticeForResourceApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank input in request payload and verify error message")
	public void testErrorMessageForBlankRequestPayload() throws NullPointerException, IOException {
		logStep("Enter blank input in request payload");
		associateDeassociatePracticeForResourceApiResponse = sharedInfo
				.associateDeassociatePracticeForResourceUsingEmptyPayload_DL(resourceId);
		CCUtils.assertEquals(associateDeassociatePracticeForResourceApiResponse.getStatusCode(), 400);
		validation.verifyInvalidRequestValidation(associateDeassociatePracticeForResourceApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add practiceid for existing resourceId With Active Field As Null and verify response body")
	public void testResponseCodeToAddPracticeForResourceWithActiveFieldAsNull()
			throws NullPointerException, IOException {
		logStep("Add practiceid for existing resourceId With Active Field As Null");
		associateDeassociatePracticeForResourceApiResponse = sharedInfo
				.associateDeassociatePracticeForResource_DL(resourceId, practiceId, " ");
		CCUtils.assertEquals(associateDeassociatePracticeForResourceApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForActiveStatusAsNull(associateDeassociatePracticeForResourceApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter practiceid, resourceId as null and verify response code")
	public void testResponseCodeToAddPracticeWithResourceIdAsNull() throws NullPointerException, IOException {
		logStep("Enter practiceid, resourceId as null");
		associateDeassociatePracticeForResourceApiResponse = sharedInfo
				.associateDeassociatePracticeForResourceUsingNullValue_DL();
		CCUtils.assertEquals(associateDeassociatePracticeForResourceApiResponse.getStatusCode(), 400);
	}
}