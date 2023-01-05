// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetLocationDetailsApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private ClientInfo clientInfo;
	private LocationsValidations locationValidation;
	Response createNewLocationApiResponse, createClientApiResponse, fetchLocationDetailsApiResponse;
	String externalId, locationName;
	int clientId, locationId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		clientId = createClient();
		locationValidation = new LocationsValidations();
		locationId = createLocation(clientId);
	}

	public int createClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient("true", clientName, clientSapNumber, "true",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId(),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		return createClientApiResponse.jsonPath().get("clientId");
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid location id in location id field and verify response body")
	public void testGetLocationDetailsFunctionalityUsingValidLocationId() throws IOException {
		logStep("Fetch location details using location id");
		fetchLocationDetailsApiResponse = sharedInfo.getLocationDetails(clientId,locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchLocationDetailsApiResponse);
		CCUtils.assertEquals(fetchLocationDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetLocationDetailsApiResponseBody(fetchLocationDetailsApiResponse, locationName,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), testData.getProperty("location.timezonecode"),
				testData.getProperty("location.timezone.displayname"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing location id in location id field and verify response body")
	public void testErrorMessageForNonExistingLocationId() throws IOException {
		logStep("Enter non existing location id in location id field");
		fetchLocationDetailsApiResponse = sharedInfo
				.getLocationDetails(clientId,Integer.parseInt(testData.getProperty("non.existing.location.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchLocationDetailsApiResponse);
		CCUtils.assertEquals(fetchLocationDetailsApiResponse.getStatusCode(), 400);
		locationValidation.verifyErrorResponseForInvalidLocationId(fetchLocationDetailsApiResponse,
				Integer.parseInt(testData.getProperty("non.existing.location.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error response body for invalid url")
	public void testErrorResponseForInvalidUrl() throws IOException {
		logStep("Verify error response body for invalid url");
		fetchLocationDetailsApiResponse = sharedInfo.getLocationDetailsUsingInvalidUrl_BL(locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchLocationDetailsApiResponse);
		CCUtils.assertEquals(fetchLocationDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchLocationDetailsApiResponse);
	}
}
