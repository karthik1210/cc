//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class ToggleLocationAssociationToPracticeApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private LocationsValidations locationValidation;
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	Response createNewLocationApiResponse, createClientApiResponse, fetchLocationDetailsApiResponse,
			createOrgNodeApiResponse, associateDeassociatePracticesForLocationApiResponse,
			getPracticesAssociateToLocationApiResponse;
	String externalId, locationName;
	int clientId, systemId, enterpriseId, ngePracticeId, agnosticPracticeId, locationId;
	ArrayList<Integer> associatePracticeIds;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		locationValidation = new LocationsValidations();

		clientId = createClient();
		logStep("Create active system, enterprise ,nge practice and inactive agnostic practice nodes below active client");
		systemId = createOrgNode(testData.getProperty("api.system.active"), "", testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"), "",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		enterpriseId = createOrgNode(testData.getProperty("api.enterprise.active"), "",
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), "US/Eastern",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		ngePracticeId = createOrgNode(testData.getProperty("api.practice.active"), "",
				testData.getProperty("api.practice.name"), testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), "US/Central",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		agnosticPracticeId = createOrgNode(testData.getProperty("api.practice.active"), "",
				testData.getProperty("api.practice.name"), testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.system.orgtype"), "US/Central",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		locationId = createLocation(clientId);

	}

	public int createClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient("true", clientName, clientSapNumber, "true",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	public int createOrgNode(String status, String displayName, String name, String orgType, int parentId,
			String parentType, String timezoneId, int ehridentifier, int pmIdentifier) throws Exception {
		createOrgNodeApiResponse = orgInfo.createOrgNode_BL(name, orgType, displayName, status, timezoneId, parentId,
				parentType, ehridentifier, pmIdentifier);
		return createOrgNodeApiResponse.jsonPath().getInt("orgId");
	}

	public int createLocation(int clientId) throws Exception {
		logStep("Enter valid data in mandatory field");
		locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_BL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewLocationApiResponse);
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 200);
		validation.verifyCreateLocationApiResponseBody(createNewLocationApiResponse, locationName);
		return createNewLocationApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 0, description = "Verify user able to associate and deassociate practice for location")
	public void testAssociateDeassociateLocationForPracticeFunctionality() throws IOException {
		logStep("Associate NGE practice with loacation");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, ngePracticeId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociatePracticesForLocationApiResponse);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Fetch practice associated to location");
		getPracticesAssociateToLocationApiResponse = sharedInfo.getPracticesAssociatedToLocation_BL(locationId);
		logStep("Validating response information");
		CCUtils.assertEquals(getPracticesAssociateToLocationApiResponse.getStatusCode(), 200);
		associatePracticeIds = new ArrayList<Integer>();
		associatePracticeIds.add(ngePracticeId);
		locationValidation.verifyGetPracticesAssociatedToLocationApiResponseBody(
				getPracticesAssociateToLocationApiResponse, associatePracticeIds);
		logStep("Associate Agnostic practice with loacation");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, agnosticPracticeId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociatePracticesForLocationApiResponse);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Fetch practice associated to location");
		getPracticesAssociateToLocationApiResponse = sharedInfo.getPracticesAssociatedToLocation_BL(locationId);
		logStep("Validating response information");
		CCUtils.assertEquals(getPracticesAssociateToLocationApiResponse.getStatusCode(), 200);
		associatePracticeIds.add(agnosticPracticeId);
		locationValidation.verifyGetPracticesAssociatedToLocationApiResponseBody(
				getPracticesAssociateToLocationApiResponse, associatePracticeIds);
		logStep("Deassociate NGE practice with loacation");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, ngePracticeId, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociatePracticesForLocationApiResponse);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Fetch deassociated practice ids from location");
		getPracticesAssociateToLocationApiResponse = sharedInfo.getPracticesAssociatedToLocation_BL(locationId);
		logStep("Validating response information");
		CCUtils.assertEquals(getPracticesAssociateToLocationApiResponse.getStatusCode(), 200);
		associatePracticeIds.remove(0);
		locationValidation.verifyGetPracticesAssociatedToLocationApiResponseBody(
				getPracticesAssociateToLocationApiResponse, associatePracticeIds);
		logStep("Deassociate all practices(Agnostic) with loacation");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, agnosticPracticeId, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociatePracticesForLocationApiResponse);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Fetch deassociated practice ids from location and verify 204 status code is displayed in response if  practices are not associated with location");
		getPracticesAssociateToLocationApiResponse = sharedInfo.getPracticesAssociatedToLocation_BL(locationId);
		logStep("Validating response information");
		CCUtils.assertEquals(getPracticesAssociateToLocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid location in location id field and verify response body")
	public void testErrorResponseForNonExistingLocationId() throws IOException {
		logStep("Enter non existing location id in location id field");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo.associateDeassociatePracticeForLocation_BL(
				Integer.parseInt(testData.getProperty("non.existing.location.id")), ngePracticeId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociatePracticesForLocationApiResponse);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 400);
		locationValidation
				.verifyErrorResponseForNonExistingLocationId(associateDeassociatePracticesForLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid location in location id field and non existing practice id in practice id field and verify response body")
	public void testErrorResponseForNonExistingPracticeId() throws IOException {
		logStep("Enter non existing practice id in practice id field");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo.associateDeassociatePracticeForLocation_BL(
				locationId, Integer.parseInt(testData.getProperty("non.existing.org.id")), true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociatePracticesForLocationApiResponse);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 400);
		locationValidation
				.verifyErrorResponseForNonExistingPracticeId(associateDeassociatePracticesForLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank payload and verify response body")
	public void testErrorResponseForEmptyPayload() throws IOException {
		logStep("Enter blank payload and verify error response");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocationWithEmptyPayload_BL(locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociatePracticesForLocationApiResponse);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 400);
		locationValidation.verifyErrorResponseForEmptyPayload(associateDeassociatePracticesForLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocationUsingInvalidUrl_BL(locationId, ngePracticeId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociatePracticesForLocationApiResponse);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(associateDeassociatePracticesForLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if POST is used instead of PUT")
	public void testErrorForInvalidMethodCall() throws IOException {
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocationUsingInvalidMethod_BL(locationId, ngePracticeId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociatePracticesForLocationApiResponse);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(
				associateDeassociatePracticesForLocationApiResponse);
	}

}
