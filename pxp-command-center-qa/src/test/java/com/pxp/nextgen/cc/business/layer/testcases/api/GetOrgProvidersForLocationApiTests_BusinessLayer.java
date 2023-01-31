//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.LocationsInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import io.restassured.response.Response;

public class GetOrgProvidersForLocationApiTests_BusinessLayer extends BaseRest {
	private LocationsInfo locationsInfo;
	private LocationsValidations locationsValidations;
	private CreateClientHierarchy_BL clientHierObj;
	Response createProviderApiResponse, createNewLocationApiResponse, fetchProvidersForLocationResponse,
			associateDeassociatePracticesForLocationApiResponse, associateProvidersForLocationApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, locationId;
	String externalId, firstName, lastName, resourceExternalId;
	private SharedInfo sharedInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		locationsInfo = new LocationsInfo();
		locationsValidations = new LocationsValidations();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Create provider to fetch resources");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new location");
		String locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_BL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 200);
		locationId = createNewLocationApiResponse.jsonPath().getInt("id");
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing  practiceId and existing Provider name and verify status code and response body")
	public void testGetProviderForLocationForExistingPracticeIdAndName() throws IOException {
		logStep("Enter existing  practiceId and Provider name");
		fetchProvidersForLocationResponse = locationsInfo.getOrgProvidersForLocation_BL(practiceId, firstName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 200);
		locationsValidations.verifyGetProviderApiResponseBody(fetchProvidersForLocationResponse, providerId, firstName,
				lastName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing  practiceId and existing Provider name and verify status code")
	public void testGetProviderForLocationForNonExistingPracticeIdAndExistingName() throws IOException {
		logStep("Enter non existing  practiceId and existing Provider name ");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		fetchProvidersForLocationResponse = locationsInfo.getOrgProvidersForLocation_BL(practiceId, firstName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing  practiceId and non existing Provider name and verify status code")
	public void testGetProviderForLocationForExistingPracticeIdAndNonExistingName() throws IOException {
		logStep("Enter existing  practiceId and non existing Provider name");
		String firstName = CCUtils.generateAlphaNumericString(10);
		fetchProvidersForLocationResponse = locationsInfo.getOrgProvidersForLocation_BL(practiceId, firstName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing  practiceId and non existing Provider name and verify status code ")
	public void testGetProviderForLocationForNonExistingPracticeIdAndNonExistingName() throws IOException {
		logStep("Enter non existing  practiceId and non existing Provider name ");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		String firstName = CCUtils.generateAlphaNumericString(10);
		fetchProvidersForLocationResponse = locationsInfo.getOrgProvidersForLocation_BL(practiceId, firstName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter practiceId and Provider name as null and verify status code ")
	public void testGetProviderForLocationForNullValuesPracticeIdAndName() throws IOException {
		logStep("Enter practiceId and Provider name as null");
		fetchProvidersForLocationResponse = locationsInfo.getOrgProvidersForLocationNullValues_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 400);
	}
}