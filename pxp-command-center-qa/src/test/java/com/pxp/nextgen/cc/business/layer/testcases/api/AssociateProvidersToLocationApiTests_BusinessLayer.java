//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

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

public class AssociateProvidersToLocationApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	private LocationsValidations locationsValidations;
	private LocationsInfo locationsInfo;
	private Validations validation;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createNewLocationApiResponse,
			associateDeassociatePracticesForLocationApiResponse, associateProvidersForLocationApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, locationId;
	String clientName, clientSapNumber, externalId, firstName, lastName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		locationsInfo = new LocationsInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
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
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Create provider for providerId");
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
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid practiceid and locationId with valid details in request body and verify response code")
	public void testResponseCodeToAssociateProviderToLocation() throws NullPointerException, IOException {
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
		locationsValidations.verifyAssociateProvidersForLocationApiResponseBody(
				associateProvidersForLocationApiResponse, providerIds, "SUCCESS", null);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceid and existing locationId with invalid details in request body  and verify response code")
	public void testResponseCodeForNonExistingProviderIdForLocation() throws NullPointerException, IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Associate nonexisting providerId to location");
		ArrayList<Integer> providerIds = new ArrayList<Integer>();
		providerIds.add(Integer.parseInt(testData.getProperty("non.existing.provider.id")));
		associateProvidersForLocationApiResponse = locationsInfo.associateProvidersToLocation_BL(locationId, practiceId,
				providerIds);
		CCUtils.assertEquals(associateProvidersForLocationApiResponse.getStatusCode(), 207);
		locationsValidations.verifyAssociateProvidersForLocationApiResponseBody(
				associateProvidersForLocationApiResponse, providerIds, "ERROR", "Invalid input");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing practiceid and existing locationId with valid details in request body  and verify response code")
	public void testErrorMessageForNonExistingPracticeIdAndExistingProviderIdForLocation()
			throws NullPointerException, IOException {
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		logStep("Associate valid providerId to Location");
		ArrayList<Integer> providerIds = new ArrayList<Integer>();
		providerIds.add(providerId);
		associateProvidersForLocationApiResponse = locationsInfo.associateProvidersToLocation_BL(locationId, practiceId,
				providerIds);
		CCUtils.assertEquals(associateProvidersForLocationApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(associateProvidersForLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceid and non-existing locationId with valid details in request body  and verify response code")
	public void testErrorMessageForExistingPracticeIdProviderIdAndNonExistingLocationId()
			throws NullPointerException, IOException {
		int locationId = Integer.parseInt(testData.getProperty("api.nonexisting.locationId"));
		logStep("Associate valid providerId to Location");
		ArrayList<Integer> providerIds = new ArrayList<Integer>();
		providerIds.add(providerId);
		associateProvidersForLocationApiResponse = locationsInfo.associateProvidersToLocation_BL(locationId, practiceId,
				providerIds);
		CCUtils.assertEquals(associateProvidersForLocationApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(associateProvidersForLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing practiceid and non-existing locationId with invalid details in request body  and verify response code")
	public void testErrorMessageForNonExistingInputInAllFields() throws NullPointerException, IOException {
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		int locationId = Integer.parseInt(testData.getProperty("api.nonexisting.locationId"));
		logStep("Associate nonexisting providerId to Location");
		ArrayList<Integer> providerIds = new ArrayList<Integer>();
		providerIds.add(Integer.parseInt(testData.getProperty("non.existing.provider.id")));
		associateProvidersForLocationApiResponse = locationsInfo.associateProvidersToLocation_BL(locationId, practiceId,
				providerIds);
		CCUtils.assertEquals(associateProvidersForLocationApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(associateProvidersForLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify response code for invalid PUT method call instead of POST method call")
	public void testResponseCodeForPutMethodCallInsteadOfPostMethodCall() throws NullPointerException, IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Associate valid providerId to Location");
		ArrayList<Integer> providerIds = new ArrayList<Integer>();
		providerIds.add(providerId);
		associateProvidersForLocationApiResponse = locationsInfo
				.associateProvidersToLocationInvalidMethodCall_BL(locationId, practiceId, providerIds);
		CCUtils.assertEquals(associateProvidersForLocationApiResponse.getStatusCode(), 405);
		validation.verifyErrorInResponseForInvalidMethod(associateProvidersForLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid practiceid and locationId with valid details in request body for invalid url and verify response code")
	public void testResponseCodeToAssociateProviderToLocationForInvalidUrl() throws NullPointerException, IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Associate valid providerId to Location");
		ArrayList<Integer> providerIds = new ArrayList<Integer>();
		providerIds.add(providerId);
		associateProvidersForLocationApiResponse = locationsInfo.associateProvidersToLocationInvalidUrl_BL(locationId,
				practiceId, providerIds);
		CCUtils.assertEquals(associateProvidersForLocationApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(associateProvidersForLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values in all input fields and verify response code")
	public void testResponseCodeForNullValuesInAllInputFields() throws NullPointerException, IOException {
		associateProvidersForLocationApiResponse = locationsInfo.associateProvidersToLocation_BL();
		CCUtils.assertEquals(associateProvidersForLocationApiResponse.getStatusCode(), 400);
	}
}