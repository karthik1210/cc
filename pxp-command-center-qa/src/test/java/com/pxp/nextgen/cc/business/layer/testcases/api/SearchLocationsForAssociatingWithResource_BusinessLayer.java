//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.Locations;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class SearchLocationsForAssociatingWithResource_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private Validations validation;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createResourceApiResponse, createNewLocationApiResponse,
			associateDeassociatePracticesForLocationApiResponse, addResourceForLocationApiResponse,
			updateLocationForResource, searchlocationApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, resourceIdOne, locationId;
	String clientName, clientSapNumber, externalId, firstName, lastName, resourceExternalId, resourceName, locationName;
	List<Locations> locationIds;

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
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Create provider for resourceId");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new location");
		locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_BL(clientId,
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
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search Locations For existing resourceId,practiceId and location name and verify response body")
	public void testToSearchLoctionForExistingResourceIdPracticeIdAndLocationName() throws IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Search Locations For existing resourceId,practiceId and location name and verify response body");
		searchlocationApiResponse = sharedInfo.searchLocationForResource_BL(resourceId, practiceId, locationName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchlocationApiResponse);
		CCUtils.assertEquals(searchlocationApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForGetLocationResource(searchlocationApiResponse);
		logStep("Deassociate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, false);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Search Locations For existing resourceId,practiceId and location name and verify response body");
		searchlocationApiResponse = sharedInfo.searchLocationForResource_BL(resourceId, practiceId, locationName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchlocationApiResponse);
		CCUtils.assertEquals(searchlocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search Locations For non-existing resourceId, existing practiceId and existing location name and verify response code")
	public void testToSearchLoctionForNonExistingResourceIdExistingPracticeIdAndExistingLocationName()
			throws IOException {
		logStep("Search Locations For non-existing resourceId, existing practiceId and existing location name and verify response code");
		int resourceId = Integer.parseInt(testData.getProperty("nonexisting.resourceId"));
		searchlocationApiResponse = sharedInfo.searchLocationForResource_BL(resourceId, practiceId, locationName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchlocationApiResponse);
		CCUtils.assertEquals(searchlocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search Locations For existing resourceId, non-existing practiceId and existing location name and verify response code")
	public void testToSearchLoctionForExistingResourceIdNonExistingPracticeIdAndExistingLocationName()
			throws IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Search Locations For existing resourceId, non-existing practiceId and existing location name and verify response code");
		int practiceId = Integer.parseInt(testData.getProperty("nonexisting.practiceId"));
		searchlocationApiResponse = sharedInfo.searchLocationForResource_BL(resourceId, practiceId, locationName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchlocationApiResponse);
		CCUtils.assertEquals(searchlocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search Locations For existing resourceId, existing practiceId and non-existing location name and verify response code")
	public void testToSearchLoctionForExistingResourceIdExistingPracticeIdAndNonExistingLocationName()
			throws IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Search Locations For existing resourceId, existing practiceId and non-existing location name and verify response code");
		String locationName = CCUtils.generateRandomString();
		searchlocationApiResponse = sharedInfo.searchLocationForResource_BL(resourceId, practiceId, locationName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchlocationApiResponse);
		CCUtils.assertEquals(searchlocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search Locations For  non-existing resourceId, practiceId and location name and verify response code")
	public void testToSearchLoctionForNonExistingResourceIdPracticeIdAndLocationName() throws IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Search Locations For  non-existing resourceId, practiceId and location name and verify response code");
		int resourceId = Integer.parseInt(testData.getProperty("nonexisting.resourceId"));
		int practiceId = Integer.parseInt(testData.getProperty("nonexisting.practiceId"));
		String locationName = CCUtils.generateRandomString();
		searchlocationApiResponse = sharedInfo.searchLocationForResource_BL(resourceId, practiceId, locationName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchlocationApiResponse);
		CCUtils.assertEquals(searchlocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search Locations For null values and verify response code")
	public void testToSearchLoctionForNullValue() throws IOException {
		;
		logStep("Search Locations For null values and verify response code");
		searchlocationApiResponse = sharedInfo.searchLocationForResource_NullValue_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchlocationApiResponse);
		CCUtils.assertEquals(searchlocationApiResponse.getStatusCode(), 400);
	}
}