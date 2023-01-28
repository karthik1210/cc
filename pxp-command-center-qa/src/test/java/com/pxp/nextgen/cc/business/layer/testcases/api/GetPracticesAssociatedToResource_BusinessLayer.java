//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetPracticesAssociatedToResource_BusinessLayer extends BaseRest {
	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_BL clientHierObj;
	Response createProviderApiResponse, createResourceApiResponse, associateDeassociatePracticeForResourceApiResponse,
			getPracticesAssociatedToResourceApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, resourceIdOne;
	String externalId, firstName, lastName, resourceExternalId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		resourceInfo = new ResourceInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing resourceId and verify response body")
	public void testResponseCodeForExistingResourceId() throws NullPointerException, IOException {
		logStep("Add practiceid for existing resourceId");
		associateDeassociatePracticeForResourceApiResponse = sharedInfo
				.associateDeassociatePracticeForResource_BL(resourceId, practiceId, "true");
		CCUtils.assertEquals(associateDeassociatePracticeForResourceApiResponse.getStatusCode(), 200);
		logStep("Enter existing resourceId and verify response body");
		getPracticesAssociatedToResourceApiResponse = sharedInfo.getPracticeForResource_BL(resourceId);
		CCUtils.assertEquals(getPracticesAssociatedToResourceApiResponse.getStatusCode(), 200);
		ArrayList<Object> practiceIds = new ArrayList<Object>();
		practiceIds.add(practiceId);
		validation.verifyGetPracticesAssociatedToResourceApiResponse(getPracticesAssociatedToResourceApiResponse,
				practiceIds);
		logStep("Remove practiceid for existing resourceId");
		associateDeassociatePracticeForResourceApiResponse = sharedInfo
				.associateDeassociatePracticeForResource_BL(resourceId, practiceId, "false");
		CCUtils.assertEquals(associateDeassociatePracticeForResourceApiResponse.getStatusCode(), 200);
		logStep("Enter existing resourceId and verify response body");
		getPracticesAssociatedToResourceApiResponse = sharedInfo.getPracticeForResource_BL(resourceId);
		CCUtils.assertEquals(getPracticesAssociatedToResourceApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing resourceId and verify response body")
	public void testResponseCodeForNonExistingResourceId() throws NullPointerException, IOException {
		logStep("Enter existind practiceid and non-existing resourceId and verify response body");
		int resourceId = Integer.parseInt(testData.getProperty("non.existing.resource.id"));
		getPracticesAssociatedToResourceApiResponse = sharedInfo.getPracticeForResource_BL(resourceId);
		CCUtils.assertEquals(getPracticesAssociatedToResourceApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter  resourceId as null and verify response code")
	public void testResponseCodeToGetPracticeWithResourceIdAsNull() throws NullPointerException, IOException {
		logStep("Enter practiceid, resourceId as null");
		getPracticesAssociatedToResourceApiResponse = sharedInfo
				.associateDeassociatePracticeForResourceUsingNullValue_BL();
		CCUtils.assertEquals(getPracticesAssociatedToResourceApiResponse.getStatusCode(), 400);
	}
}
