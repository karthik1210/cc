// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.util.ArrayList;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.LocationsInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetLocationForProviderApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private LocationsValidations locationsValidations;
	private LocationsInfo locationsInfo;
	private Validations validation;
	private CreateClientHierarchy_DL clientHierObj;
	Response createProviderApiResponse, createResourceApiResponse, createNewLocationApiResponse,
			associateDeassociatePracticesForLocationApiResponse, addProviderForLocationApiResponse,
			getLocationsForProviderResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, locationId;
	String externalId, firstName, lastName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		locationsInfo = new LocationsInfo();
		sharedInfo = new SharedInfo();
		locationsValidations = new LocationsValidations();
		validation = new Validations();

	    clientHierObj = new CreateClientHierarchy_DL();
		
	    clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		logStep("Create new provider");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Provider and practiceId to get locations associated with Provider")
	public void testGetLocationsUsingExistingProviderIdIdAndPracticeId() throws Exception {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Add one Provider for Location");
		ArrayList<Integer> providerIds = new ArrayList<Integer>();
		providerIds.add(providerId);
		addProviderForLocationApiResponse = locationsInfo.addProviderForLocation_DL(locationId, practiceId,
				providerIds);
		logStep("Enter providerId and practiceId");
		getLocationsForProviderResponse = locationsInfo.getLocationsForProvider_DL(providerId, practiceId);
		CCUtils.responseTimeValidation(getLocationsForProviderResponse);
		CCUtils.assertEquals(getLocationsForProviderResponse.getStatusCode(), 200);
		String locationID = getLocationsForProviderResponse.jsonPath().getString("locationId");
		locationsValidations.verifyResponseBodyForLocationAssociatedWithProviders(getLocationsForProviderResponse,
				locationID);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing providerId and existing practiceId and verify response code")
	public void testGetLocationUsingNonExistingProviderIdAndExistingPracticeId() throws Exception {
		logStep("Enter non-existing providerId and existing practiceId");
		int providerId = Integer.parseInt(testData.getProperty("non.existing.provider.id"));
		getLocationsForProviderResponse = locationsInfo.getLocationsForProvider_DL(providerId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationsForProviderResponse);
		CCUtils.assertEquals(getLocationsForProviderResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing ProviderId and non-existing practiceId and verify response code")
	public void testGetLocationUsingExistingProviderIdAndNonExistingPracticeId() throws Exception {
		logStep("Enter existing ProviderId and non-existing practiceId");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		getLocationsForProviderResponse = locationsInfo.getLocationsForProvider_DL(providerId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationsForProviderResponse);
		CCUtils.assertEquals(getLocationsForProviderResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing providerId and practiceId and verify response code")
	public void testGetLocationUsingNonExistingProviderIdAndPracticeId() throws Exception {
		logStep("Enter non-existing providerId and practiceId");
		int providerId = Integer.parseInt(testData.getProperty("non.existing.provider.id"));
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		getLocationsForProviderResponse = locationsInfo.getLocationsForProvider_DL(providerId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationsForProviderResponse);
		CCUtils.assertEquals(getLocationsForProviderResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter providerId and practiceId as null value and verify response code")
	public void testGetLocationUsingNullValues() throws Exception {
		logStep("Enter providerId and practice as null value");
		getLocationsForProviderResponse = locationsInfo.getLocationsForProviderUsingNullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationsForProviderResponse);
		CCUtils.assertEquals(getLocationsForProviderResponse.getStatusCode(), 400);
		validation.verifyErrorMessageIfClientIdIsNull(getLocationsForProviderResponse);
	}
}