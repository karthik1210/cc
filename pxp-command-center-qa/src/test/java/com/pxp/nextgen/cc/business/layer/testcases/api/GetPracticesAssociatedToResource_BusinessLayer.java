//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;

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

public class GetPracticesAssociatedToResource_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private Validations validation;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createResourceApiResponse, associateDeassociatePracticeForResourceApiResponse,
			getPracticesAssociatedToResourceApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, resourceIdOne;
	String clientName, clientSapNumber, externalId, firstName, lastName, resourceExternalId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		resourceInfo = new ResourceInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber, "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
		clientId = createClientApiResponse.jsonPath().getInt("clientId");
		createSystemApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", testData.getProperty("api.system.active"), "", clientId,
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		createEnterpriseApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemId,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		createPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		logStep("Create provider for resourceId");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new resource");
		resourceId = createResource();
	}

	public int createResource() {
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		String resourceName = CCUtils.generateAlphaNumericString(8);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		return createResourceApiResponse.jsonPath().getInt("resourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceid for existing resourceId and verify response body")
	public void testResponseCodeForExistingPracticeIdAndResourceId() throws NullPointerException, IOException {
		logStep("Add practiceid for existing resourceId");
		associateDeassociatePracticeForResourceApiResponse = sharedInfo
				.associateDeassociatePracticeForResource_BL(resourceId, practiceId, "true");
		CCUtils.assertEquals(associateDeassociatePracticeForResourceApiResponse.getStatusCode(), 200);
		logStep("Enter existind practiceid for existing resourceId and verify response body");
		getPracticesAssociatedToResourceApiResponse = sharedInfo.getPracticeForResource_BL(resourceId, practiceId);
		CCUtils.assertEquals(getPracticesAssociatedToResourceApiResponse.getStatusCode(), 200);
		ArrayList<Object> practiceIds = new ArrayList<Object>();
		practiceIds.add(practiceId);
		validation.verifygetPracticesAssociatedToResourceApiResponse(getPracticesAssociatedToResourceApiResponse,practiceIds);
		logStep("Remove practiceid for existing resourceId");
		associateDeassociatePracticeForResourceApiResponse = sharedInfo
				.associateDeassociatePracticeForResource_BL(resourceId, practiceId, "false");
		CCUtils.assertEquals(associateDeassociatePracticeForResourceApiResponse.getStatusCode(), 200);
		logStep("Enter existind practiceid for existing resourceId and verify response body");
		getPracticesAssociatedToResourceApiResponse = sharedInfo.getPracticeForResource_BL(resourceId, practiceId);
		CCUtils.assertEquals(getPracticesAssociatedToResourceApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceid for non-existing resourceId and verify response body")
	public void testResponseCodeForExistingPracticeIdAndNonExistingResourceId() throws NullPointerException, IOException {
		logStep("Enter existind practiceid for non-existing resourceId and verify response body");
		int resourceId= Integer.parseInt(testData.getProperty("non.existing.resource.id"));
		getPracticesAssociatedToResourceApiResponse = sharedInfo.getPracticeForResource_BL(resourceId, practiceId);
		CCUtils.assertEquals(getPracticesAssociatedToResourceApiResponse.getStatusCode(), 204);
	
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing practiceid for existing resourceId and verify response body")
	public void testResponseCodeForNonExistingPracticeIdAndExistingResourceId() throws NullPointerException, IOException {
		logStep("Enter non-existind practiceid for existing resourceId and verify response body");
		int practiceId= Integer.parseInt(testData.getProperty("nonexisting.practiceId"));
		getPracticesAssociatedToResourceApiResponse = sharedInfo.getPracticeForResource_BL(resourceId, practiceId);
		CCUtils.assertEquals(getPracticesAssociatedToResourceApiResponse.getStatusCode(), 204);
	
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing practiceid for non-existing resourceId and verify response body")
	public void testResponseCodeForNonExistingPracticeIdAndNonExistingResourceId() throws NullPointerException, IOException {
		logStep("Enter non-existind practiceid for existing resourceId and verify response body");
		int resourceId= Integer.parseInt(testData.getProperty("non.existing.resource.id"));
		int practiceId= Integer.parseInt(testData.getProperty("nonexisting.practiceId"));
		getPracticesAssociatedToResourceApiResponse = sharedInfo.getPracticeForResource_BL(resourceId, practiceId);
		CCUtils.assertEquals(getPracticesAssociatedToResourceApiResponse.getStatusCode(), 204);
	
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter practiceid, resourceId as null and verify response code")
	public void testResponseCodeToAddPracticeWithResourceIdAsNull() throws NullPointerException, IOException {
		logStep("Enter practiceid, resourceId as null");
		getPracticesAssociatedToResourceApiResponse = sharedInfo
				.associateDeassociatePracticeForResourceUsingNullValue_BL();
		CCUtils.assertEquals(getPracticesAssociatedToResourceApiResponse.getStatusCode(), 400);
	}
}
