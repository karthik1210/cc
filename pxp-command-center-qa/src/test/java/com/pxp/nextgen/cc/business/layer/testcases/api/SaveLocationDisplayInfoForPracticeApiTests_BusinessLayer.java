// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
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

public class SaveLocationDisplayInfoForPracticeApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private ClientInfo clientInfo;
	private Validations validation;
	private OrgInfo orgInfo;
	private LocationsValidations locationValidation;
	int clientId, systemId, enterpriseId, practiceId, locationId;
	Response createClientApiResponse, createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			createNewLocationApiResponse, associatePracticeToLocationResponse, getLocationInfoByPracticeResponse,
			saveLocationByPracticeApiResponse;

	@BeforeClass
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		locationValidation = new LocationsValidations();
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
		createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
				clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	public int createSystemNode(int clientId) throws Exception {
		createSystemApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", testData.getProperty("api.system.active"), "", clientId,
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createSystemApiResponse.jsonPath().get("orgId");
	}

	public int createEnterpriseNode(int SystemId) throws Exception {
		createEnterpriseApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemId,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createEnterpriseApiResponse.jsonPath().get("orgId");
	}

	public int createPracticeNode(int enterpriseId) throws Exception {
		createPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse.jsonPath().get("orgId");
	}

	public int addNewLocation() throws IOException {
		String locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		String externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_BL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), "");
		return createNewLocationApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid request payload details, existing location id, existing practice id and verify response body")
	public void testSaveLocationInfoByPracticeFunctionality() throws IOException {
		logStep("Enter location Id and prcatice Id to associate Practice to Location");
		associatePracticeToLocationResponse = sharedInfo.associateDeassociatePractice_BL(practiceId, locationId, true);
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		String addressExtension = testData.getProperty("api.addressExtension")
				.concat(CCUtils.generateAlphaNumericString(5));
		String phoneNumber = testData.getProperty("api.phone.no");
		String startTime = testData.getProperty("api.office.hour.startTime");
		String endTime = testData.getProperty("api.office.hour.endTime");
		String enDirection = testData.getProperty("api.en.direction").concat(CCUtils.generateAlphaNumericString(5));
		String esDirection = testData.getProperty("api.es.direction").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Enter valid details to save location info for practice");
		saveLocationByPracticeApiResponse = sharedInfo.saveLocationInfoForPractice_BL(locationId, practiceId,
				enDisplayName, esDisplayName, addressExtension, phoneNumber, startTime, endTime, enDirection,
				esDirection);
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(saveLocationByPracticeApiResponse);
		CCUtils.assertEquals(saveLocationByPracticeApiResponse.getStatusCode(), 200);
		logStep("Get the newly entered location Info");
		getLocationInfoByPracticeResponse = sharedInfo.getLocationByPracticeUsingValidInputs(locationId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationInfoByPracticeResponse);
		CCUtils.assertEquals(getLocationInfoByPracticeResponse.getStatusCode(), 200);
		validation.verifySaveDisplayInfoForPracticeApiResponseBody(getLocationInfoByPracticeResponse, enDisplayName,
				esDisplayName, addressExtension, phoneNumber, startTime, endTime, enDirection, esDirection);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid request payload details, non-existing location id, existing practice id and verify response body")
	public void testSaveLocationInfoByPracticeUsingNonExistingLocationId() throws IOException {
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		String addressExtension = testData.getProperty("api.addressExtension")
				.concat(CCUtils.generateAlphaNumericString(5));
		String phoneNumber = testData.getProperty("api.phone.no");
		String startTime = testData.getProperty("api.office.hour.startTime");
		String endTime = testData.getProperty("api.office.hour.endTime");
		String enDirection = testData.getProperty("api.en.direction").concat(CCUtils.generateAlphaNumericString(5));
		String esDirection = testData.getProperty("api.es.direction").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Enter valid details in payload and enter non-existing location Id");
		saveLocationByPracticeApiResponse = sharedInfo.saveLocationInfoForPractice_BL(
				Integer.parseInt(testData.getProperty("non.existing.location.id")), practiceId, enDisplayName,
				esDisplayName, addressExtension, phoneNumber, startTime, endTime, enDirection, esDirection);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(saveLocationByPracticeApiResponse);
		CCUtils.assertEquals(saveLocationByPracticeApiResponse.getStatusCode(), 400);
		locationValidation.verifyErrorResponseForNonExistingId(saveLocationByPracticeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid request payload details, existing location id, non-existing practice id and verify response body")
	public void testSaveLocationInfoByPracticeUsingNonExistingPracticeId() throws IOException {
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		String addressExtension = testData.getProperty("api.addressExtension")
				.concat(CCUtils.generateAlphaNumericString(5));
		String phoneNumber = testData.getProperty("api.phone.no");
		String startTime = testData.getProperty("api.office.hour.startTime");
		String endTime = testData.getProperty("api.office.hour.endTime");
		String enDirection = testData.getProperty("api.en.direction").concat(CCUtils.generateAlphaNumericString(5));
		String esDirection = testData.getProperty("api.es.direction").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Enter valid details in payload and enter non-existing location Id");
		saveLocationByPracticeApiResponse = sharedInfo.saveLocationInfoForPractice_BL(locationId,
				Integer.parseInt(testData.getProperty("non.existing.practice.id")), enDisplayName, esDisplayName,
				addressExtension, phoneNumber, startTime, endTime, enDirection, esDirection);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(saveLocationByPracticeApiResponse);
		CCUtils.assertEquals(saveLocationByPracticeApiResponse.getStatusCode(), 400);
		locationValidation.verifyErrorResponseForNonExistingId(saveLocationByPracticeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid request payload, existing location id and practice id and verify response error for invalid method call")
	public void testSaveLocationInfoByPracticeUsingInvalidMethodCall() throws IOException {
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		String addressExtension = testData.getProperty("api.addressExtension")
				.concat(CCUtils.generateAlphaNumericString(5));
		String phoneNumber = testData.getProperty("api.phone.no");
		String startTime = testData.getProperty("api.office.hour.startTime");
		String endTime = testData.getProperty("api.office.hour.endTime");
		String enDirection = testData.getProperty("api.en.direction").concat(CCUtils.generateAlphaNumericString(5));
		String esDirection = testData.getProperty("api.es.direction").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Enter valid details and make a POST call");
		saveLocationByPracticeApiResponse = sharedInfo.saveLocationInfoForPracticeUsingInvalidMethodCall_BL(locationId,
				Integer.parseInt(testData.getProperty("non.existing.practice.id")), enDisplayName, esDisplayName,
				addressExtension, phoneNumber, startTime, endTime, enDirection, esDirection);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(saveLocationByPracticeApiResponse);
		CCUtils.assertEquals(saveLocationByPracticeApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(saveLocationByPracticeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid request payload, existing location id and practice id and verify response error for invalid URL")
	public void testSaveLocationInfoByPracticeUsingInvalidUrl() throws IOException {
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		String addressExtension = testData.getProperty("api.addressExtension")
				.concat(CCUtils.generateAlphaNumericString(5));
		String phoneNumber = testData.getProperty("api.phone.no");
		String startTime = testData.getProperty("api.office.hour.startTime");
		String endTime = testData.getProperty("api.office.hour.endTime");
		String enDirection = testData.getProperty("api.en.direction").concat(CCUtils.generateAlphaNumericString(5));
		String esDirection = testData.getProperty("api.es.direction").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Enter valid details and Hit a wrong URL");
		saveLocationByPracticeApiResponse = sharedInfo.saveLocationInfoForPracticeUsingInvalidUrl_BL(locationId,
				Integer.parseInt(testData.getProperty("non.existing.practice.id")), enDisplayName, esDisplayName,
				addressExtension, phoneNumber, startTime, endTime, enDirection, esDirection);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(saveLocationByPracticeApiResponse);
		CCUtils.assertEquals(saveLocationByPracticeApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(saveLocationByPracticeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid request payload, location id and practice id as null to verify response error")
	public void testSaveLocationInfoByPracticeUsingNullValues() throws IOException {
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		String addressExtension = testData.getProperty("api.addressExtension")
				.concat(CCUtils.generateAlphaNumericString(5));
		String phoneNumber = testData.getProperty("api.phone.no");
		String startTime = testData.getProperty("api.office.hour.startTime");
		String endTime = testData.getProperty("api.office.hour.endTime");
		String enDirection = testData.getProperty("api.en.direction").concat(CCUtils.generateAlphaNumericString(5));
		String esDirection = testData.getProperty("api.es.direction").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Enter valid details in request payload and Enter location ID and practiceId as null");
		saveLocationByPracticeApiResponse = sharedInfo.saveLocationInfoForPracticeUsingNullValues_BL(enDisplayName,
				esDisplayName, addressExtension, phoneNumber, startTime, endTime, enDirection, esDirection);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(saveLocationByPracticeApiResponse);
		CCUtils.assertEquals(saveLocationByPracticeApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter selective parameters in request payload, existing location id and practice id to verify response error")
	public void testSaveLocationInfoForPracticeForSelectiveParametersInRequestPayload() throws IOException {
		logStep("Request Payload excluding Office Hours parameter");
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		String addressExtension = testData.getProperty("api.addressExtension")
				.concat(CCUtils.generateAlphaNumericString(5));
		String phoneNumber = testData.getProperty("api.phone.no");
		String enDirection = testData.getProperty("api.en.direction").concat(CCUtils.generateAlphaNumericString(5));
		String esDirection = testData.getProperty("api.es.direction").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Enter valid location ID, practiceId and selective request payload");
		saveLocationByPracticeApiResponse = sharedInfo.saveLocationInfoForPracticeForSelectivePayload_BL(locationId,
				practiceId, enDisplayName, esDisplayName, addressExtension, phoneNumber, enDirection, esDirection);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(saveLocationByPracticeApiResponse);
		CCUtils.assertEquals(saveLocationByPracticeApiResponse.getStatusCode(), 200);
		logStep("Get the newly entered location Info");
		getLocationInfoByPracticeResponse = sharedInfo.getLocationByPracticeUsingValidInputs(locationId, practiceId);
		validation.verifySaveDisplayInfoForSelectivePayloadApiResponseBody(getLocationInfoByPracticeResponse,
				enDisplayName, esDisplayName, addressExtension, phoneNumber, enDirection, esDirection);
	}
}
