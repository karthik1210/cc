//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
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

public class GetProvidersForLocationApiTests_BusinessLayer extends BaseRest {
	private LocationsInfo locationsInfo;
	private LocationsValidations locationsValidations;
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createNewLocationApiResponse, fetchProvidersForLocationResponse,
			associateDeassociatePracticesForLocationApiResponse, associateProvidersForLocationApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, locationId, locationIdOne, practiceIdOne;
	String clientName, clientSapNumber, externalId, firstName, lastName, resourceExternalId;
	private SharedInfo sharedInfo;
	private Validations validation;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		locationsInfo = new LocationsInfo();
		locationsValidations = new LocationsValidations();
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
		practiceIdOne = createPracticeApiResponse.jsonPath().get("orgId");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
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
		locationIdOne = createNewLocationApiResponse.jsonPath().getInt("id");
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Associate provider to Location");
		ArrayList<Integer> providerIds = new ArrayList<Integer>();
		providerIds.add(providerId);
		associateProvidersForLocationApiResponse = locationsInfo.associateProvidersToLocation_BL(locationId, practiceId,
				providerIds);
		CCUtils.assertEquals(associateProvidersForLocationApiResponse.getStatusCode(), 207);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId, practiceId to get one Provider and verify status code and response body")
	public void testGetProviderForLocationForExistingLocationIdPracticeId() throws IOException {
		logStep("Enter existing locationId, practiceId");
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = null;
		fetchProvidersForLocationResponse = locationsInfo.getProvidersForLocation_BL(locationId, practiceId, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 200);
		locationsValidations.verifyGetProvidersForLocationValidInput(fetchProvidersForLocationResponse,
				String.valueOf(pageSize));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId, practiceId & name to get Provider and verify status code and response body")
	public void testGetProviderForLocationForExistingLocationIdPracticeIdAndName() throws IOException {
		logStep("Enter existing locationId, practiceId & name");
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		fetchProvidersForLocationResponse = locationsInfo.getProvidersForLocation_BL(locationId, practiceId, page,
				pageSize, firstName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 200);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId, practiceId to get multiple Provider and verify status code and response body")
	public void testGetProviderForLocationForExistingLocationIdPracticeIdToGetMultipleProvider() throws IOException {
		logStep("Enter existing locationId, practiceId ");
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = null;
		fetchProvidersForLocationResponse = locationsInfo.getProvidersForLocation_BL(locationId, practiceId, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 200);
		locationsValidations.verifyGetProvidersForLocationValidInput(fetchProvidersForLocationResponse,
				String.valueOf(pageSize));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId, practiceId with no associate Provider and verify status code ")
	public void testGetProviderForLocationForExistingLocationIdPracticeIdwithNoAssociateProvider() throws IOException {
		logStep("Enter existing locationId, practiceId ");
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = null;
		fetchProvidersForLocationResponse = locationsInfo.getProvidersForLocation_BL(locationIdOne, practiceIdOne, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId, non-existing practiceId  verify verify status code")
	public void testGetProviderForLocationForExistingLocationIdNonExistingPracticeId() throws IOException {
		logStep("Enter existing locationId, non-existing practiceId ");
		int practiceId = Integer.parseInt(testData.getProperty("api.nonexisting.practiceId"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = null;
		fetchProvidersForLocationResponse = locationsInfo.getProvidersForLocation_BL(locationId, practiceId, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing locationId, existing practiceId  verify verify status code")
	public void testGetProviderForLocationForNonexistingLocationIdNonExistingPracticeId() throws IOException {
		logStep("Enter existing locationId, non-existing practiceId ");
		int locationId = Integer.parseInt(testData.getProperty("api.nonexisting.locationId"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = null;
		fetchProvidersForLocationResponse = locationsInfo.getProvidersForLocation_BL(locationId, practiceId, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing locationId, non-existing practiceId  verify verify status code")
	public void testGetProviderForLocationForexistingLocationIdNonExistingPracticeId() throws IOException {
		logStep("Enter existing locationId, non-existing practiceId ");
		int locationId = Integer.parseInt(testData.getProperty("api.nonexisting.locationId"));
		int practiceId = Integer.parseInt(testData.getProperty("api.nonexisting.practiceId"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = null;
		fetchProvidersForLocationResponse = locationsInfo.getProvidersForLocation_BL(locationId, practiceId, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error massage for null  locationId & practiceId")
	public void testErrorMessageForNullValuesInPracticeIdAndLocationId() throws IOException {
		fetchProvidersForLocationResponse = locationsInfo.getProvidersForLocation_BL_NullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 400);
		validation.verifyErrorMessageIfClientIdIsNull(fetchProvidersForLocationResponse);
	}
}