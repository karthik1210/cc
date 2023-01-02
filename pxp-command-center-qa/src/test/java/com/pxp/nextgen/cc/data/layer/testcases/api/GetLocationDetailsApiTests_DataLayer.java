// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetLocationDetailsApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private ClientInfo clientInfo;
	Response createNewLocationApiResponse, createClientApiResponse, fetchLocationDetailsApiResponse;
	String externalId, locationName;
	int clientId, locationId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		clientId = createClient();
		locationId = createLocation();
	}

	public int createClient() throws NumberFormatException, NullPointerException, IOException {
		logStep("Create client using valid details in client name, sap number, active, testclient, client type and timezone field");
		String clientName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5))));
		String clientSapNumber = CCUtils
				.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.client.sapnumber")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5))));
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		return createClientApiResponse.jsonPath().getInt("clientId");
	}

	public int createLocation() throws Exception {
		logStep("Enter valid data in mandatory field");
		locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_DL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 200);
		validation.verifyCreateLocationApiResponseBody(createNewLocationApiResponse, locationName);
		return createNewLocationApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid location id in location field and verify response body")
	public void testAddNewLocationFunctionality() throws IOException {
		logStep("Fetch Newly Created location details using location id");
		fetchLocationDetailsApiResponse = sharedInfo.getLocationDetails_DL(clientId,locationId);
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
				.getLocationDetails_DL(clientId,Integer.parseInt(testData.getProperty("non.existing.location.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchLocationDetailsApiResponse);
		CCUtils.assertEquals(fetchLocationDetailsApiResponse.getStatusCode(), 400);
	}

}
