//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.Locations;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class SearchLocationsForAssoWithResource_DataLayer extends BaseRest {
	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_DL clientHierObj;
	Response createProviderApiResponse, createResourceApiResponse, createNewLocationApiResponse,
			associateDeassociatePracticesForLocationApiResponse, addResourceForLocationApiResponse,
			updateLocationForResource, searchlocationApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, resourceIdOne, locationId;
	String externalId, firstName, lastName, resourceExternalId, resourceName, locationName;
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
		logStep("Create new location");
		locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_DL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 200);
		locationId = createNewLocationApiResponse.jsonPath().getInt("id");
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		resourceName = CCUtils.generateAlphaNumericString(8);
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search Locations For existing resourceId, practiceId and location name and verify response body")
	public void testToSearchLoctionForExistingResourceIdPracticeIdAndLocationName() throws IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Search Locations For existing resourceId,practiceId and location name and verify response body");
		searchlocationApiResponse = sharedInfo.searchLocationForResource_DL(resourceId, practiceId, locationName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchlocationApiResponse);
		CCUtils.assertEquals(searchlocationApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForGetLocationResource(searchlocationApiResponse);
		logStep("Deassociate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, practiceId, false);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Search Locations For existing resourceId,practiceId and location name and verify response body");
		searchlocationApiResponse = sharedInfo.searchLocationForResource_DL(resourceId, practiceId, locationName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchlocationApiResponse);
		CCUtils.assertEquals(searchlocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search Locations For non-existing resourceId, existing practiceId and existing location name and verify response code")
	public void testToSearchLoctionForNonExistingResourceIdExistingPracticeIdAndExistingLocationName()
			throws IOException {
		logStep("Search Locations For non-existing resourceId, existing practiceId and existing location name and verify response code");
		int resourceId = Integer.parseInt(testData.getProperty("nonexisting.resourceId"));
		searchlocationApiResponse = sharedInfo.searchLocationForResource_DL(resourceId, practiceId, locationName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchlocationApiResponse);
		CCUtils.assertEquals(searchlocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search Locations For existing resourceId, non-existing practiceId and existing location name and verify response code")
	public void testToSearchLoctionForExistingResourceIdNonExistingPracticeIdAndExistingLocationName()
			throws IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Search Locations For existing resourceId, non-existing practiceId and existing location name and verify response code");
		int practiceId = Integer.parseInt(testData.getProperty("nonexisting.practiceId"));
		searchlocationApiResponse = sharedInfo.searchLocationForResource_DL(resourceId, practiceId, locationName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchlocationApiResponse);
		CCUtils.assertEquals(searchlocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search Locations For existing resourceId, existing practiceId and non-existing location name and verify response code")
	public void testToSearchLoctionForExistingResourceIdExistingPracticeIdAndNonExistingLocationName()
			throws IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Search Locations For existing resourceId, existing practiceId and non-existing location name and verify response code");
		String locationName = CCUtils.generateRandomString();
		searchlocationApiResponse = sharedInfo.searchLocationForResource_DL(resourceId, practiceId, locationName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchlocationApiResponse);
		CCUtils.assertEquals(searchlocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search Locations For  non-existing resourceId, practiceId and location name and verify response code")
	public void testToSearchLoctionForNonExistingResourceIdPracticeIdAndLocationName() throws IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Search Locations For  non-existing resourceId, practiceId and location name and verify response code");
		int resourceId = Integer.parseInt(testData.getProperty("nonexisting.resourceId"));
		int practiceId = Integer.parseInt(testData.getProperty("nonexisting.practiceId"));
		String locationName = CCUtils.generateRandomString();
		searchlocationApiResponse = sharedInfo.searchLocationForResource_DL(resourceId, practiceId, locationName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchlocationApiResponse);
		CCUtils.assertEquals(searchlocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search Locations For null values and verify response code")
	public void testToSearchLoctionForNullValue() throws IOException {
		;
		logStep("Search Locations For null values and verify response code");
		searchlocationApiResponse = sharedInfo.searchLocationForResource_NullValue_DL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchlocationApiResponse);
		CCUtils.assertEquals(searchlocationApiResponse.getStatusCode(), 400);
	}
}