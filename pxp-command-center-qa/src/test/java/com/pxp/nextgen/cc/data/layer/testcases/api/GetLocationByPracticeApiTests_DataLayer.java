//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import com.pxp.cc.util.LocationsValidations;
import io.restassured.response.Response;

public class GetLocationByPracticeApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private ClientInfo clientInfo;
	private Validations validation;
	private LocationsValidations locationsvalidations;
	private OrgInfo orgInfo;
	int clientId, systemId, enterpriseId, practiceId, locationId;
	String locationName;
	Response createClientApiResponse, createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			createNewLocationApiResponse, associatePracticeToLocationResponse, getLocationByPracticeResponse;

	@BeforeClass
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		locationsvalidations = new LocationsValidations();
		clientId = createClient(ccUser.getClientOneType());
		systemId = createSystemNode(clientId);
		enterpriseId = createEnterpriseNode(systemId);
		practiceId = createPracticeNode(enterpriseId);
		locationId = addNewLocation();
	}

	public int createClient(String clientType) throws Exception {
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	public int createSystemNode(int clientId) throws Exception {
		createSystemApiResponse = orgInfo.createOrgNode_DataLayer(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", testData.getProperty("api.system.active"), "", clientId,
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createSystemApiResponse.jsonPath().get("orgId");
	}

	public int createEnterpriseNode(int SystemId) throws Exception {
		createEnterpriseApiResponse = orgInfo.createOrgNode_DataLayer(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemId,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createEnterpriseApiResponse.jsonPath().get("orgId");
	}

	public int createPracticeNode(int enterpriseId) throws Exception {
		createPracticeApiResponse = orgInfo.createOrgNode_DataLayer(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse.jsonPath().get("orgId");
	}

	public int addNewLocation() throws IOException {
		locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		String externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_DL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		return createNewLocationApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify fetching location by practice using valid locationId and practiceId")
	public void testGetLocationByPracticeFunctionality() throws NullPointerException, IOException {
		logStep("Enter locationId and practiceId to associate Practice to Location");
		associatePracticeToLocationResponse = sharedInfo.associateDeassociatePractice_DL(practiceId, locationId, true);
		logStep("Enter valid locationId and practiceId to get location by practice");
		getLocationByPracticeResponse = sharedInfo.getLocationByPractice_DL(locationId, practiceId);
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 200);
		locationsvalidations.verifyGetLocationByPracticeApiResponseBody(getLocationByPracticeResponse, locationName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify fetching location by practice using non-existing locationID and existing practiceId")
	public void testGetLocationByPracticeFunctionalityUsingNonExistingLocationId() throws IOException {
		logStep("Enter non-existing locationId and existing practiceId to get location by practice");
		getLocationByPracticeResponse = sharedInfo.getLocationByPractice_DL(
				Integer.parseInt(testData.getProperty("non.existing.location.id")), practiceId);
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 400);
		locationsvalidations.verifyErrorResponseForNonExistingId(getLocationByPracticeResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify fetching location by practice using existing locationID and non-existing practiceId")
	public void testGetLocationByPracticeFunctionalityUsingNonExistingPracticeId() throws IOException {
		logStep("Enter existing locationID and non-existing practiceId to get location by practice");
		getLocationByPracticeResponse = sharedInfo.getLocationByPractice_DL(locationId,
				Integer.parseInt(testData.getProperty("non.existing.practice.id")));
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 400);
		locationsvalidations.verifyErrorResponseForNonExistingId(getLocationByPracticeResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify fetching location by practice using non-existing locationID and non-existing practiceId")
	public void testGetLocationByPracticeFunctionalityUsingNonExistingPracticeIdLocationId() throws IOException {
		logStep("Enter existing locationID and non-existing practiceId to get location by practice");
		getLocationByPracticeResponse = sharedInfo.getLocationByPractice_DL(
				Integer.parseInt(testData.getProperty("non.existing.location.id")),
				Integer.parseInt(testData.getProperty("non.existing.practice.id")));
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 400);
		locationsvalidations.verifyErrorResponseForNonExistingId(getLocationByPracticeResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify fetching location by practice using locationID and practiceId as null values")
	public void testGetLocationByPracticeFunctionalityUsingNullValues() {
		logStep("Enter location ID and practiceId as null to get location by practice");
		getLocationByPracticeResponse = sharedInfo.getLocationByPracticeUsingNullValues_DL();
		CCUtils.responseTimeValidation(getLocationByPracticeResponse);
		CCUtils.assertEquals(getLocationByPracticeResponse.getStatusCode(), 400);
	}
}
