// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.LocationsInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AddResourcesForLocationApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private ResourceInfo resourceInfo;
	private SharedInfo sharedInfo;
	private LocationsValidations locationsValidations;
	private LocationsInfo locationsInfo;
	private Validations validation;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createResourceApiResponse, createNewLocationApiResponse,
			associateDeassociatePracticesForLocationApiResponse, addResourceForLocationApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, resourceIdOne, locationId;
	String clientName, clientSapNumber, externalId, firstName, lastName, resourceExternalId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		resourceInfo = new ResourceInfo();
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
		logStep("Create provider for resourceId");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		String resourceName = CCUtils.generateAlphaNumericString(8);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
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
	public void testResponseCodeToAddResorceForLocation() throws NullPointerException, IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Add one Resorce for Location");
		ArrayList<Integer> resourceIds = new ArrayList<Integer>();
		resourceIds.add(resourceId);
		addResourceForLocationApiResponse = locationsInfo.addResourceForLocation_BL(locationId, practiceId,
				resourceIds);
		CCUtils.assertEquals(addResourceForLocationApiResponse.getStatusCode(), 207);
		locationsValidations.verifyAddResourcesForLocationApiResponseBody(addResourceForLocationApiResponse,
				resourceIds, "SUCCESS");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceid and existing locationId with invalid details in request body  and verify response code")
	public void testResponseCodeForNonExistingResorceIdForLocation() throws NullPointerException, IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Add nonexisting ResorceId for Location");
		ArrayList<Integer> resourceIds = new ArrayList<Integer>();
		resourceIds.add(Integer.parseInt(testData.getProperty("non.exting.resource.id")));
		addResourceForLocationApiResponse = locationsInfo.addResourceForLocation_BL(locationId, practiceId,
				resourceIds);
		CCUtils.assertEquals(addResourceForLocationApiResponse.getStatusCode(), 207);
		locationsValidations.verifyAddInvalidResourcesForLocationApiResponseBody(addResourceForLocationApiResponse,
				resourceIds, "ERROR", testData.getProperty("invalid.message"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing practiceid and existing locationId with valid details in request body  and verify response code")
	public void testErrorMessageForNonExistingPracticeIdAndExistingResorceIdForLocation()
			throws NullPointerException, IOException {
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		logStep("Add valid ResorceId for Location");
		ArrayList<Integer> resourceIds = new ArrayList<Integer>();
		resourceIds.add(resourceId);
		addResourceForLocationApiResponse = locationsInfo.addResourceForLocation_BL(locationId, practiceId,
				resourceIds);
		CCUtils.assertEquals(addResourceForLocationApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(addResourceForLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceid and non-existing locationId with valid details in request body  and verify response code")
	public void testErrorMessageForExistingPracticeIdResorceIdAndNonExistingLocationId()
			throws NullPointerException, IOException {
		int locationId = Integer.parseInt(testData.getProperty("api.nonexisting.locationId"));
		logStep("Add valid ResorceId for Location");
		ArrayList<Integer> resourceIds = new ArrayList<Integer>();
		resourceIds.add(resourceId);
		addResourceForLocationApiResponse = locationsInfo.addResourceForLocation_BL(locationId, practiceId,
				resourceIds);
		CCUtils.assertEquals(addResourceForLocationApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(addResourceForLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing practiceid and non-existing locationId with invalid details in request body  and verify response code")
	public void testErrorMessageForNonExistingInputInAllFields() throws NullPointerException, IOException {
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		int locationId = Integer.parseInt(testData.getProperty("api.nonexisting.locationId"));
		logStep("Add nonexisting ResorceId for Location");
		ArrayList<Integer> resourceIds = new ArrayList<Integer>();
		resourceIds.add(Integer.parseInt(testData.getProperty("non.exting.resource.id")));
		addResourceForLocationApiResponse = locationsInfo.addResourceForLocation_BL(locationId, practiceId,
				resourceIds);
		CCUtils.assertEquals(addResourceForLocationApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(addResourceForLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify response code for invalid PUT method call instead of POST method call")
	public void testResponseCodeForPutMethodCallInsteadOfPostMethodCall() throws NullPointerException, IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Add one Resorce for Location");
		ArrayList<Integer> resourceIds = new ArrayList<Integer>();
		resourceIds.add(resourceId);
		addResourceForLocationApiResponse = locationsInfo.addResourceForLocationInvalidMethod_BL(locationId, practiceId,
				resourceIds);
		CCUtils.assertEquals(addResourceForLocationApiResponse.getStatusCode(), 405);
		validation.verifyErrorInResponseForInvalidMethod(addResourceForLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid practiceid and locationId with valid details in request body for invalid url and verify response code")
	public void testResponseCodeToAddOneResorceForLocationForInvalidUrl() throws NullPointerException, IOException {
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Add one Resorce for Location");
		ArrayList<Integer> resourceIds = new ArrayList<Integer>();
		resourceIds.add(resourceId);
		addResourceForLocationApiResponse = locationsInfo.addResourceForLocationInvalidUrl_BL(locationId, practiceId,
				resourceIds);
		CCUtils.assertEquals(addResourceForLocationApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(addResourceForLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter  null values in all fields and verify response code")
	public void testResponseCodeForNullInputInAllFields() throws NullPointerException, IOException {
		addResourceForLocationApiResponse = locationsInfo.addResourceForLocation_BL();
		CCUtils.assertEquals(addResourceForLocationApiResponse.getStatusCode(), 400);
	}
}