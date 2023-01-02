//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AssociateProvidersToLocationApiTests_DataLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	private Validations validation;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createNewLocationApiResponse,
			associateDeassociatePracticesForLocationApiResponse, associateProvidersForLocationApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, locationId;
	String clientName, clientSapNumber, externalId, firstName, lastName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();

		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		clientId = createClientApiResponse.jsonPath().get("clientId");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
				testData.getProperty("api.system.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		systemId = createSystemApiResponse.jsonPath().get("orgId");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.displayname"), testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		enterpriseId = createEnterpriseApiResponse.jsonPath().get("orgId");
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid practiceid and locationId with valid details in request body and verify response code")
	public void testResponseCodeToAssociateProviderToLocation() throws NullPointerException, IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Associate provider to Location");
		ArrayList<Integer> providerIds = new ArrayList<Integer>();
		providerIds.add(providerId);
		associateProvidersForLocationApiResponse = sharedInfo.associateProvidersToLocation_DL(locationId, practiceId,
				providerIds);
		CCUtils.assertEquals(associateProvidersForLocationApiResponse.getStatusCode(), 207);
		validation.verifyAssociateProvidersForLocationApiResponseBody(associateProvidersForLocationApiResponse,
				providerIds, "SUCCESS", null);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceid and existing locationId with invalid details in request body  and verify response code")
	public void testResponseCodeForNonExistingProviderIdForLocation() throws NullPointerException, IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Associate nonexisting providerId to location");
		ArrayList<Integer> providerIds = new ArrayList<Integer>();
		providerIds.add(Integer.parseInt(testData.getProperty("non.existing.provider.id")));
		associateProvidersForLocationApiResponse = sharedInfo.associateProvidersToLocation_DL(locationId, practiceId,
				providerIds);
		CCUtils.assertEquals(associateProvidersForLocationApiResponse.getStatusCode(), 207);
		validation.verifyAssociateProvidersForLocationApiResponseBody(associateProvidersForLocationApiResponse,
				providerIds, "ERROR", "Invalid input");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing practiceid and existing locationId with valid details in request body  and verify response code")
	public void testErrorMessageForNonExistingPracticeIdAndExistingProviderIdForLocation()
			throws NullPointerException, IOException {
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		logStep("Associate valid providerId to Location");
		ArrayList<Integer> providerIds = new ArrayList<Integer>();
		providerIds.add(providerId);
		associateProvidersForLocationApiResponse = sharedInfo.associateProvidersToLocation_DL(locationId, practiceId,
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
		associateProvidersForLocationApiResponse = sharedInfo.associateProvidersToLocation_DL(locationId, practiceId,
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
		associateProvidersForLocationApiResponse = sharedInfo.associateProvidersToLocation_DL(locationId, practiceId,
				providerIds);
		CCUtils.assertEquals(associateProvidersForLocationApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(associateProvidersForLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values in all input fields and verify response code")
	public void testResponseCodeForNullValuesInAllInputFields() throws NullPointerException, IOException {
		associateProvidersForLocationApiResponse = sharedInfo.associateProvidersToLocation_DL();
		CCUtils.assertEquals(associateProvidersForLocationApiResponse.getStatusCode(), 400);
	}
}