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
import com.pxp.cc.util.LocationsValidations;
import io.restassured.response.Response;

public class SaveLocationDisplayInfoForPracticeApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private LocationsValidations locationValidation;
	int clientId, systemId, enterpriseId, practiceId, locationId;
	String externalId;
	Response createClientApiResponse, createSystemApiResponse, createPracticeApiResponse, createNewLocationApiResponse,
			associatePracticeToLocationResponse, getLocationInfoByPracticeApiResponse,
			saveLocationByPracticeApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		locationValidation = new LocationsValidations();
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
				clientTimezoneId);
		return createClientApiResponse.jsonPath().getInt("clientId");
	}

	public int createSystemNode(int clientId) throws Exception {
		log("create a system within a client");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "",
				testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), "", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createSystemApiResponse.jsonPath().get("orgId");
	}

	public int createAgnosticPracticeNode(int systemId) throws Exception {
		log("create a agnostic practice within a system");
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse.jsonPath().get("orgId");
	}

	public int createLocationWithinClient(int clientId) throws Exception {
		log("create a location within a client");
		String locationName = testData.getProperty("location.name").concat(CCUtils.generateAlphaNumericString(3));
		externalId = testData.getProperty("location.external.id").concat(CCUtils.generateAlphaNumericString(3));
		createNewLocationApiResponse = sharedInfo.createNewLocation_DL(clientId,
				testData.getProperty("location.addressline.one"), testData.getProperty("location.addressline.two"),
				testData.getProperty("location.city"), testData.getProperty("location.state"),
				testData.getProperty("location.zipcode"), Float.parseFloat(testData.getProperty("location.lat")),
				Float.parseFloat(testData.getProperty("location.lng")), locationName, externalId,
				testData.getProperty("location.timezonecode"), locationName);
		return createNewLocationApiResponse.jsonPath().get("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid request payload details with existing location id, existing practice id and verify response body")
	public void testSaveLocationInfoByPracticeFunctionality() throws Exception {
		clientId = createClient();
		systemId = createSystemNode(clientId);
		practiceId = createAgnosticPracticeNode(systemId);
		locationId = createLocationWithinClient(clientId);
		logStep("Enter location Id and prcatice Id to associate Practice to Location");
		associatePracticeToLocationResponse = sharedInfo.associateDeassociatePractice_DL(practiceId, locationId, true);
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
		saveLocationByPracticeApiResponse = sharedInfo.saveLocationInfoForPractice_DL(locationId, practiceId,
				enDisplayName, esDisplayName, addressExtension, phoneNumber, startTime, endTime, enDirection,
				esDirection);
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(saveLocationByPracticeApiResponse);
		CCUtils.assertEquals(saveLocationByPracticeApiResponse.getStatusCode(), 200);
		logStep("Get the newly entered location Info");
		getLocationInfoByPracticeApiResponse = sharedInfo.getLocationByPractice_DL(locationId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationInfoByPracticeApiResponse);
		CCUtils.assertEquals(getLocationInfoByPracticeApiResponse.getStatusCode(), 200);
		locationValidation.verifyGetLocationByPracticeApiResponseBody(getLocationInfoByPracticeApiResponse,
				enDisplayName);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid request payload details, non-existing location id, existing practice id and verify response body")
	public void testSaveLocationInfoByPracticeUsingNonExistingLocationId() throws Exception {
		clientId = createClient();
		systemId = createSystemNode(clientId);
		practiceId = createAgnosticPracticeNode(systemId);
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
		saveLocationByPracticeApiResponse = sharedInfo.saveLocationInfoForPractice_DL(
				Integer.parseInt(testData.getProperty("non.existing.location.id")), practiceId, enDisplayName,
				esDisplayName, addressExtension, phoneNumber, startTime, endTime, enDirection, esDirection);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(saveLocationByPracticeApiResponse);
		CCUtils.assertEquals(saveLocationByPracticeApiResponse.getStatusCode(), 400);
		locationValidation.verifyErrorResponseForNonExistingId(saveLocationByPracticeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid request payload details, existing location id, non-existing practice id and verify response body")
	public void testSaveLocationInfoByPracticeUsingNonExistingPracticeId() throws Exception {
		clientId = createClient();
		locationId = createLocationWithinClient(clientId);
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		String addressExtension = testData.getProperty("api.addressExtension")
				.concat(CCUtils.generateAlphaNumericString(5));
		String phoneNumber = testData.getProperty("api.phone.no");
		String startTime = testData.getProperty("api.office.hour.startTime");
		String endTime = testData.getProperty("api.office.hour.endTime");
		String enDirection = testData.getProperty("api.en.direction").concat(CCUtils.generateAlphaNumericString(5));
		String esDirection = testData.getProperty("api.es.direction").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Enter valid details in payload and enter non-existing practice Id");
		saveLocationByPracticeApiResponse = sharedInfo.saveLocationInfoForPractice_DL(locationId,
				Integer.parseInt(testData.getProperty("non.existing.practice.id")), enDisplayName, esDisplayName,
				addressExtension, phoneNumber, startTime, endTime, enDirection, esDirection);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(saveLocationByPracticeApiResponse);
		CCUtils.assertEquals(saveLocationByPracticeApiResponse.getStatusCode(), 400);
		locationValidation.verifyErrorResponseForNonExistingId(saveLocationByPracticeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing location id and practice id, without mandatory parameters in request payload and verify response body")
	public void testSaveLocationInfoForPracticeWithoutMandatoryParametersInRequestPayload() throws Exception {
		logStep("Request Payload excluding Office Hours and Phone number parameters");
		clientId = createClient();
		systemId = createSystemNode(clientId);
		practiceId = createAgnosticPracticeNode(systemId);
		locationId = createLocationWithinClient(clientId);
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		String addressExtension = testData.getProperty("api.addressExtension")
				.concat(CCUtils.generateAlphaNumericString(5));
		String enDirection = testData.getProperty("api.en.direction").concat(CCUtils.generateAlphaNumericString(5));
		String esDirection = testData.getProperty("api.es.direction").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Enter existing location id and practice id, without mandatory parameters in request payload");
		saveLocationByPracticeApiResponse = sharedInfo
				.saveLocationInfoForPracticeWithoutMandatoryParametersInPayload_DL(locationId, practiceId,
						enDisplayName, esDisplayName, addressExtension, enDirection, esDirection);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(saveLocationByPracticeApiResponse);
		CCUtils.assertEquals(saveLocationByPracticeApiResponse.getStatusCode(), 400);
		locationValidation.verifyErrorResponseForNonExistingId(saveLocationByPracticeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid request payload, location id and practice id as null and verify response code")
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
		saveLocationByPracticeApiResponse = sharedInfo.saveLocationInfoForPracticeUsingNullValues_DL(enDisplayName,
				esDisplayName, addressExtension, phoneNumber, startTime, endTime, enDirection, esDirection);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(saveLocationByPracticeApiResponse);
		CCUtils.assertEquals(saveLocationByPracticeApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing location id, existing practice id with null payload and verify response code")
	public void testSaveLocationInfoByPracticeWithNullPayload() throws Exception {
		clientId = createClient();
		systemId = createSystemNode(clientId);
		practiceId = createAgnosticPracticeNode(systemId);
		locationId = createLocationWithinClient(clientId);
		logStep("Enter location Id and prcatice Id to associate Practice to Location");
		associatePracticeToLocationResponse = sharedInfo.associateDeassociatePractice_DL(practiceId, locationId, true);
		logStep("Enter existing location id, existing practice id with null payload");
		saveLocationByPracticeApiResponse = sharedInfo.saveLocationInfoForPracticeWithNullPayload_DL(locationId,
				practiceId);
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(saveLocationByPracticeApiResponse);
		CCUtils.assertEquals(saveLocationByPracticeApiResponse.getStatusCode(), 400);
	}
}
