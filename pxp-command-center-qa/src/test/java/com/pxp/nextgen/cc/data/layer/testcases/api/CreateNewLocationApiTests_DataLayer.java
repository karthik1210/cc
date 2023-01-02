// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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

public class CreateNewLocationApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private ClientInfo clientInfo;
	Response createNewLocationApiResponse, createClientApiResponse, fetchLocationDetailsApiResponse;
	String externalId;
	int clientId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		clientId = createClient();
	}

	public int createClient() throws NumberFormatException, NullPointerException, IOException {
		logStep("Create client using valid details in client name, sap number, active, testclient, client type and timezone field");
		String clientName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5))));
		String clientSapNumber = CCUtils
				.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.client.sapnumber")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5))));
		String testFlag = testData.getProperty("api.client.testclient");
		String clientTimezoneId = testData.getProperty("api.client.timezoneid");
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testFlag, Integer.parseInt(testData.getProperty("api.client.type")),
				clientTimezoneId,testData.getProperty("ehr.identifier"),testData.getProperty("pm.identifier"));
		return createClientApiResponse.jsonPath().getInt("clientId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in mandatory field and verify response body")
	public void testAddNewLocationFunctionality() throws IOException {
		logStep("Enter valid data in mandatory field");
		String locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_DL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewLocationApiResponse);
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 200);
		validation.verifyCreateLocationApiResponseBody(createNewLocationApiResponse, locationName);
		int locationId = createNewLocationApiResponse.jsonPath().getInt("id");
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test maximum character functionality for mandatory field")
	public void testAddNewLocationFunctionalityUsingMaximumFieldSize() throws IOException {
		logStep("Enter 100 characters in loacation name, addressline1, addressline2 and 50 charcters in city, zipcode, external id fields");
		String locationName = CCUtils.getName(CCUtils.generateStringWithSpace(
				testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(95))));
		String addressLine1 = CCUtils.getName(CCUtils.generateStringWithSpace(
				testData.getProperty("location.addressline.one").concat(CCUtils.generateAlphaNumericString(95))));
		String addressLine2 = CCUtils.getName(CCUtils.generateStringWithSpace(
				testData.getProperty("location.addressline.two").concat(CCUtils.generateAlphaNumericString(95))));
		String locationDisplayName = CCUtils.getName(CCUtils.generateStringWithSpace(
				testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(95))));
		String city = CCUtils.generateAlphaNumericString(50);
		String zipcode = CCUtils.generateAlphaNumericString(50);
		externalId = CCUtils.generateAlphaNumericString(50);
		createNewLocationApiResponse = sharedInfo.createNewLocation_DL(clientId, addressLine1, addressLine2, city,
				testData.getProperty("location.state"), zipcode, Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), locationDisplayName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewLocationApiResponse);
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 200);
		validation.verifyCreateLocationApiResponseBody(createNewLocationApiResponse, locationName);
		int locationId = createNewLocationApiResponse.jsonPath().getInt("id");
		logStep("Fetch Newly Created location details using location id");
		fetchLocationDetailsApiResponse = sharedInfo.getLocationDetails_DL(clientId,locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchLocationDetailsApiResponse);
		CCUtils.assertEquals(fetchLocationDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetLocationDetailsApiResponseBody(fetchLocationDetailsApiResponse, locationName, addressLine1,
				addressLine2, city, testData.getProperty("location.state"), zipcode,
				Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), testData.getProperty("location.timezonecode"),
				testData.getProperty("location.timezone.displayname"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, enabled = false, description = "Enter existing external id and verify response body")
	public void testErrorResponseForExistingExternalId() throws IOException {
		logStep("Enter valid data in mandatory field");
		String locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_DL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewLocationApiResponse);
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid client id in clientId field and verify error response body")
	public void testErrorResponseForNonExistingClientId() throws IOException {
		logStep("Enter valid data in mandatory field and non existing client id");
		createNewLocationApiResponse = sharedInfo.createNewLocation_DL(
				Integer.parseInt(testData.getProperty("api.nonexisting.client.id")),
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), testData.getProperty("location.name"),
				testData.getProperty("location.external.id"), testData.getProperty("location.timezonecode"), "");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewLocationApiResponse);
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidOrgId(createNewLocationApiResponse,
				testData.getProperty("api.nonexisting.client.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value in mandatoty field and verify error response body")
	public void testRequiredErrorMessageForMandatoryFields() throws IOException {
		logStep("Enter null value in mandatoty field and verify error response body");
		createNewLocationApiResponse = sharedInfo.createNewLocation_DL(clientId, "", "", "", "", "", 0, 0, "", "", "",
				"");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewLocationApiResponse);
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForMandatoryFieldsForLocation(createNewLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid timezone in timezoneCode field and verify error response body")
	public void testErrorResponseForInvalidTimezoneID() throws IOException {
		logStep("Enter valid data in mandatory field and non existing timezone in timezone field");
		String locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		String externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		String invalidTimezoneId = testData.getProperty("location.timezonecode")
				.concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_DL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId, invalidTimezoneId,
				"");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewLocationApiResponse);
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForTimezoneCodeField(createNewLocationApiResponse, invalidTimezoneId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in addressline1, addressline2, location name and more than 50 characters in city, zipcode, external id, state field and verify error response body")
	public void testMaximumCharacterErrorFunctionality() throws IOException {
		logStep("Enter more than 100 characters in addressline1, addressline2, location name and more than 50 characters in city, zipcode, external id, state field and verify response body");
		String invalidValue = CCUtils.generateAlphaNumericString(101);
		createNewLocationApiResponse = sharedInfo.createNewLocation_DL(clientId, invalidValue, invalidValue,
				invalidValue, invalidValue, invalidValue, Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), invalidValue, invalidValue, invalidValue,
				invalidValue);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewLocationApiResponse);
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error response body for invalid url")
	public void testErrorResponseForInvalidUrl() throws IOException {
		logStep("Verify error response body for invalid url");
		String locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		String externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocationUsingInvalidUrl_DL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewLocationApiResponse);
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(createNewLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Make a PUT call instead of POST and verify response body")
	public void testErrorMessageForInvalidMethod() throws IOException {
		logStep("Verify error response for invalid method call");
		String locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		String externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocationUsingInvalidMethod_DL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewLocationApiResponse);
		CCUtils.assertEquals(createNewLocationApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(createNewLocationApiResponse);
	}

}
