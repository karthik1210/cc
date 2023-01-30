//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import org.testng.annotations.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.Locations;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetPracticesAssociatedToResourcesApiTests_DataLayer extends BaseRest {
	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_DL clientHierObj;
	Response createProviderApiResponse, createResourceApiResponse, createNewLocationApiResponse,
			associateDeassociatePracticesForLocationApiResponse, addResourceForLocationApiResponse,
			updateLocationForResource, fetchResourceDetailsApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, resourceIdOne, locationId;
	String clientName, clientSapNumber, externalId, firstName, lastName, resourceExternalId;
	List<Locations> locationIds;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		resourceInfo = new ResourceInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Create provider for resourceId");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		String resourceName = CCUtils.generateAlphaNumericString(8);
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check response for one resource associated with one practice")
	public void testResponseCodeForOneResourceAssociatedWithOnePractice() throws IOException {
		logStep("Enter existing resourceId");
		fetchResourceDetailsApiResponse = sharedInfo.getPracticesAssociatedToResource_DL(resourceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchResourceDetailsApiResponse);
		CCUtils.assertEquals(fetchResourceDetailsApiResponse.getStatusCode(), 200);
		ArrayList<Integer> practiceIds = new ArrayList<Integer>();
		practiceIds.add(practiceId);
		validation.verifyResponseBodyForResourceAssociatedToPractices(fetchResourceDetailsApiResponse, practiceIds);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing resourceId in resourceId field and validate status code")
	public void testErrorResponseCodeForNonExistingResourceId() throws IOException {
		logStep("Enter non existing resourceId");
		fetchResourceDetailsApiResponse = sharedInfo.getPracticesAssociatedToResource_DL(
				Integer.parseInt(testData.getProperty("non.existing.resource.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchResourceDetailsApiResponse);
		CCUtils.assertEquals(fetchResourceDetailsApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null  resourceId in resourceId field and validate status code")
	public void testErrorResponseCodeForNullValues() throws IOException {
		logStep("Enter null values in resourceId");
		fetchResourceDetailsApiResponse = sharedInfo.getPracticesAssociatedToResourceNullValues_DL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchResourceDetailsApiResponse);
		CCUtils.assertEquals(fetchResourceDetailsApiResponse.getStatusCode(), 400);
	}
}
