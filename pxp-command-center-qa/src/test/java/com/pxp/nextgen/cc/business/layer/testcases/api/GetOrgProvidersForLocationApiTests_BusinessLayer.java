//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
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
import io.restassured.response.Response;

public class GetOrgProvidersForLocationApiTests_BusinessLayer extends BaseRest {
	private LocationsInfo locationsInfo;
	private LocationsValidations locationsValidations;
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createNewLocationApiResponse, fetchProvidersForLocationResponse,
			associateDeassociatePracticesForLocationApiResponse, associateProvidersForLocationApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, locationId;
	String clientName, clientSapNumber, externalId, firstName, lastName, resourceExternalId;
	private SharedInfo sharedInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		locationsInfo = new LocationsInfo();
		locationsValidations = new LocationsValidations();
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