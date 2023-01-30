// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.LocationsInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import io.restassured.response.Response;

public class GetResourcesForLocationApiTests_DataLayer extends BaseRest {
	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private LocationsValidations locationsValidations;
	private LocationsInfo locationsInfo;
	private CreateClientHierarchy_DL clientHierObj;
	Response createProviderApiResponse, createResourceApiResponse, createNewLocationApiResponse,
			getResourceForLocationApiResponse, associateDeassociatePracticesForLocationApiResponse,
			addResourceForLocationApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, resourceIdOne, locationId;
	String externalId, firstName, lastName, resourceExternalId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		resourceInfo = new ResourceInfo();
		locationsInfo = new LocationsInfo();
		sharedInfo = new SharedInfo();
		locationsValidations = new LocationsValidations();
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
		resourceId = createResource();
		resourceIdOne = createResource();
		logStep("Create new location");
		String locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_DL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 200);
		locationId = createNewLocationApiResponse.jsonPath().getInt("id");
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid practiceid and locationId with valid details and verify response code")
	public void testResponseCodeToGetResourceForLocation() throws NullPointerException, IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Add multiple Resource for Location");
		ArrayList<Integer> resourceIds = new ArrayList<Integer>();
		resourceIds.add(resourceId);
		resourceIds.add(resourceIdOne);
		addResourceForLocationApiResponse = locationsInfo.addResourceForLocation_DL(locationId, practiceId,
				resourceIds);
		CCUtils.assertEquals(addResourceForLocationApiResponse.getStatusCode(), 207);
		logStep("Get resource for location");
		getResourceForLocationApiResponse = locationsInfo.getResourceForLocation_DL(locationId, practiceId,"");
		CCUtils.assertEquals(getResourceForLocationApiResponse.getStatusCode(), 200);
		locationsValidations.verifyGetResourcesForLocationApiResponseBody(getResourceForLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid practiceid and valid locationId and verify response code")
	public void testResponseCodeToGetResourceForInvalidPracticeIdAndValidLocationId()
			throws NullPointerException, IOException {
		logStep("Enter invalid practiceid and valid locationId and verify response code");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		getResourceForLocationApiResponse = locationsInfo.getResourceForLocation_DL(locationId, practiceId,"");
		CCUtils.assertEquals(getResourceForLocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid practiceid and invalid locationId and verify response code")
	public void testResponseCodeToGetResourceForValidPracticeIdAndInvalidLocationId()
			throws NullPointerException, IOException {
		logStep("Enter valid practiceid and invalid locationId and verify response code");
		int locationId = Integer.parseInt(testData.getProperty("api.nonexisting.locationId"));
		getResourceForLocationApiResponse = locationsInfo.getResourceForLocation_DL(locationId, practiceId,"");
		CCUtils.assertEquals(getResourceForLocationApiResponse.getStatusCode(), 204);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid practiceid and invalid locationId and verify response code")
	public void testResponseCodeToGetResourceForInvalidPracticeIdAndInvalidLocationId()
			throws NullPointerException, IOException {
		logStep("Enter invalid practiceid and invalid locationId and verify response code");
		int locationId = Integer.parseInt(testData.getProperty("api.nonexisting.locationId"));
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		getResourceForLocationApiResponse = locationsInfo.getResourceForLocation_DL(locationId, practiceId,"");
		CCUtils.assertEquals(getResourceForLocationApiResponse.getStatusCode(), 204);
	}
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values in practiceid and locationId and verify response code")
	public void testResponseCodeToGetResourceForNullValuesInPracticeIdAndLocationIdFields()
			throws NullPointerException, IOException {
		logStep("Enter null values in practiceid and locationId and verify response code");	
		getResourceForLocationApiResponse = locationsInfo.getResourceForLocationNullValue_DL();
		CCUtils.assertEquals(getResourceForLocationApiResponse.getStatusCode(), 400);
	}
}