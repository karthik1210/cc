//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;

public class SearchLocationForProvider_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createNewLocationApiResponse, associateProvidersForLocationApiResponse,
			associateDeassociatePracticesForLocationApiResponse, searchLocationForProviderApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, locationId;
	String clientName, clientSapNumber, externalId, firstName, lastName, locationName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
	}

	public void createLocationAndProvider() throws Exception {
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber, "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId(),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
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
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter providerId and practiceId to get locations associated with provider")
	public void testGetLocationsUsingExistingProviderIdAndPracticeId() throws Exception {
		createLocationAndProvider();
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Enter providerId and practiceId to get locations associated with provider");
		searchLocationForProviderApiResponse = sharedInfo.searchLocationForProvider_BL(providerId, practiceId,
				locationName);
		CCUtils.responseTimeValidation(searchLocationForProviderApiResponse);
		CCUtils.assertEquals(searchLocationForProviderApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter providerId and practiceId and invalid  name  to get locations associated with provider")
	public void testGetLocationsUsingExistingProviderIdAndPracticeIdAndNullValueForNameField() throws Exception {
		createLocationAndProvider();
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Enter providerId and practiceId to get locations associated with provider");
		String locationName = CCUtils.generateRandomString();
		searchLocationForProviderApiResponse = sharedInfo.searchLocationForProvider_BL(providerId, practiceId,
				locationName);
		CCUtils.responseTimeValidation(searchLocationForProviderApiResponse);
		CCUtils.assertEquals(searchLocationForProviderApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Non Existing providerId and Existing practiceId to get locations associated with provider")
	public void testGetLocationsUsingNonExistingProviderIdAndExistingPracticeId() throws Exception {
		logStep("Enter Non Existing providerId and Existing practiceId to get locations associated with provider");
		int providerId = Integer.parseInt(testData.getProperty("nonexisting.providerId"));
		searchLocationForProviderApiResponse = sharedInfo.searchLocationForProvider_BL(providerId, practiceId,
				locationName);
		CCUtils.responseTimeValidation(searchLocationForProviderApiResponse);
		CCUtils.assertEquals(searchLocationForProviderApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Existing providerId and Non Existing practiceId to get locations associated with provider")
	public void testGetLocationsUsingExistingProviderIdAndNonExistingPracticeId() throws Exception {
		logStep("Enter Existing providerId and Non Existing practiceId to get locations associated with provider");
		int practiceId = Integer.parseInt(testData.getProperty("nonexisting.practiceId"));
		searchLocationForProviderApiResponse = sharedInfo.searchLocationForProvider_BL(providerId, practiceId,
				locationName);
		CCUtils.responseTimeValidation(searchLocationForProviderApiResponse);
		CCUtils.assertEquals(searchLocationForProviderApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Non Existing providerId and Non Existing practiceId to get locations associated with provider")
	public void testGetLocationsUsingNonExistingProviderIdAndNonExistingPracticeId() throws Exception {
		logStep("Enter Non Existing providerId and Non Existing practiceId to get locations associated with provider");
		int practiceId = Integer.parseInt(testData.getProperty("nonexisting.practiceId"));
		int providerId = Integer.parseInt(testData.getProperty("nonexisting.providerId"));
		searchLocationForProviderApiResponse = sharedInfo.searchLocationForProvider_BL(providerId, practiceId,
				locationName);
		CCUtils.responseTimeValidation(searchLocationForProviderApiResponse);
		CCUtils.assertEquals(searchLocationForProviderApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Null values in providerId, practiceId and name field to get locations associated with provider")
	public void testGetLocationsUsingNullInput() throws Exception {
		logStep("\"Enter Null values in providerId, practiceId and name field to get locations associated with provider");
		searchLocationForProviderApiResponse = sharedInfo.searchLocationForProviderForNullValue_BL();
		CCUtils.responseTimeValidation(searchLocationForProviderApiResponse);
		CCUtils.assertEquals(searchLocationForProviderApiResponse.getStatusCode(), 400);
	}
}