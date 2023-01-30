// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.ResourceValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class SrchConResourcesFrLocWithPracticeApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private ResourceInfo resourceInfo;
	private CreateClientHierarchy_BL clientHierObj;
	Response createProviderApiResponse, createResourceApiResponse, createNewLocationApiResponse,
			associateDeassociatePracticesForLocationApiResponse, searchResourceApiResponse;
	public static int clientId, practiceId, locationId, resourceId, systemId, enterpriseId;
	public int providerId;
	private ResourceValidations resourceValidations;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		resourceInfo = new ResourceInfo();
		resourceValidations = new ResourceValidations();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
	}

	public int createProviderForPractice() throws IOException {
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createProviderApiResponse);
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		validation.verifyCreateProviderApiResponseBody(createProviderApiResponse, firstName, lastName);
		int providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		return providerId;
	}

	public int createResource(int providerId) {
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		int resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
		return resourceId;
	}

	public int createLocation() {
		String locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		String externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_BL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		int locationId = createNewLocationApiResponse.jsonPath().getInt("id");
		return locationId;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get list of resource for a practice associated with a location and verify response body")
	public void testGetConnectedResource() throws IOException {
		locationId = createLocation();
		providerId = createProviderForPractice();
		resourceId = createResource(providerId);
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		searchResourceApiResponse = sharedInfo.getConnectedResource_BL(practiceId,
				testData.getProperty("resource.name"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourceApiResponse);
		CCUtils.assertEquals(searchResourceApiResponse.getStatusCode(), 200);
		resourceValidations.verifyGetResourcesApiResponseBody(searchResourceApiResponse, resourceId,
				testData.getProperty("resource.name"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify get 204 status code when providing non-existing practice id associated with a location")
	public void testNotFoundResourceWhenEnterNonExistingPracticeId() throws IOException {
		searchResourceApiResponse = sharedInfo.getConnectedResource_BL(
				Integer.parseInt(testData.getProperty("api.nonexisting.id")), testData.getProperty("resource.name"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourceApiResponse);
		CCUtils.assertEquals(searchResourceApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify get 204 status code when providing non-existing resource name associated with a location")
	public void testNotFoundResourceWhenEnterNonExistingResourceName() throws IOException {
		searchResourceApiResponse = sharedInfo.getConnectedResource_BL(practiceId,
				testData.getProperty("non.existing.user.first.name"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourceApiResponse);
		CCUtils.assertEquals(searchResourceApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify get 204 status code when providing non-existing practice id and resource name associated with a location")
	public void testNotFoundResourceWhenEnterNonExistingResourceNameAndPracticeId() throws IOException {
		searchResourceApiResponse = sharedInfo.getConnectedResource_BL(
				Integer.parseInt(testData.getProperty("api.nonexisting.id")),
				testData.getProperty("non.existing.user.first.name"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourceApiResponse);
		CCUtils.assertEquals(searchResourceApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify get 400 status code when providing null practice id associated with a location")
	public void testNotFoundResourceWhenEnterNullPracticeId() throws IOException {
		searchResourceApiResponse = sharedInfo
				.getConnectedResource_Null_BL(testData.getProperty("non.existing.user.first.name"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchResourceApiResponse);
		CCUtils.assertEquals(searchResourceApiResponse.getStatusCode(), 400);
	}
}
