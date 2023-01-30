//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;

public class SearchLocationForProvider_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private CreateClientHierarchy_DL clientHierObj;
	Response createProviderApiResponse, createResourceApiResponse, createNewLocationApiResponse,
			associateProvidersForLocationApiResponse, associateDeassociatePracticesForLocationApiResponse,
			addResourceForLocationApiResponse, searchLocationForProviderApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, locationId;
	String clientName, clientSapNumber, externalId, firstName, lastName, locationName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_DL();
	}

	public void createLocationAndProvider() throws Exception {
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);	
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Create provider");
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
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter providerId and practiceId to get locations associated with provider")
	public void testGetLocationsUsingExistingProviderIdAndPracticeId() throws Exception {
		createLocationAndProvider();
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Enter providerId and practiceId to get locations associated with provider");
		searchLocationForProviderApiResponse = sharedInfo.searchLocationForProvider_DL(providerId, practiceId,
				locationName);
		CCUtils.responseTimeValidation(searchLocationForProviderApiResponse);
		CCUtils.assertEquals(searchLocationForProviderApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter providerId and practiceId and invalid  name  to get locations associated with provider")
	public void testGetLocationsUsingExistingProviderIdAndPracticeIdAndNullValueForNameField() throws Exception {
		createLocationAndProvider();
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Enter providerId and practiceId to get locations associated with provider");
		String locationName = CCUtils.generateRandomString();
		searchLocationForProviderApiResponse = sharedInfo.searchLocationForProvider_DL(providerId, practiceId,
				locationName);
		CCUtils.responseTimeValidation(searchLocationForProviderApiResponse);
		CCUtils.assertEquals(searchLocationForProviderApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Non Existing providerId and Existing practiceId to get locations associated with provider")
	public void testGetLocationsUsingNonExistingProviderIdAndExistingPracticeId() throws Exception {
		logStep("Enter Non Existingre sourceId and Existing practiceId to get locations associated with resource");
		int providerId = Integer.parseInt(testData.getProperty("nonexisting.providerId"));
		searchLocationForProviderApiResponse = sharedInfo.searchLocationForProvider_DL(providerId, practiceId,
				locationName);
		CCUtils.responseTimeValidation(searchLocationForProviderApiResponse);
		CCUtils.assertEquals(searchLocationForProviderApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Existing providerId and Non Existing practiceId to get locations associated with provider")
	public void testGetLocationsUsingExistingProviderIdAndNonExistingPracticeId() throws Exception {
		logStep("Enter Non Existingre sourceId and Existing practiceId to get locations associated with resource");
		int practiceId = Integer.parseInt(testData.getProperty("nonexisting.practiceId"));
		searchLocationForProviderApiResponse = sharedInfo.searchLocationForProvider_DL(providerId, practiceId,
				locationName);
		CCUtils.responseTimeValidation(searchLocationForProviderApiResponse);
		CCUtils.assertEquals(searchLocationForProviderApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Non Existing providerId and Non Existing practiceId to get locations associated with provider")
	public void testGetLocationsUsingNonExistingProviderIdAndNonExistingPracticeId() throws Exception {
		logStep("Enter Non Existingre sourceId and Existing practiceId to get locations associated with resource");
		int practiceId = Integer.parseInt(testData.getProperty("nonexisting.practiceId"));
		int providerId = Integer.parseInt(testData.getProperty("nonexisting.providerId"));
		searchLocationForProviderApiResponse = sharedInfo.searchLocationForProvider_DL(providerId, practiceId,
				locationName);
		CCUtils.responseTimeValidation(searchLocationForProviderApiResponse);
		CCUtils.assertEquals(searchLocationForProviderApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Null values in providerId, practiceId and name field to get locations associated with provider")
	public void testGetLocationsUsingNullInput() throws Exception {
		logStep("Enter Non Existingre sourceId and Existing practiceId to get locations associated with resource");
		searchLocationForProviderApiResponse = sharedInfo.searchLocationForProviderForNullValue_DL();
		CCUtils.responseTimeValidation(searchLocationForProviderApiResponse);
		CCUtils.assertEquals(searchLocationForProviderApiResponse.getStatusCode(), 400);
	}
}