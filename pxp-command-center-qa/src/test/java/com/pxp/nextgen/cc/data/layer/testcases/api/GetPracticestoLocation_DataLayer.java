// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.IOException;
import com.pxp.cc.helpers.*;

public class GetPracticestoLocation_DataLayer extends BaseRest {
	private LocationsInfo locationInfo;
	private ClientInfo clientInfo;
	private SharedInfo sharedInfo;
	int clientId, systemId, enterpriseId, practiceId, locationId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse, createClientApiResponse,
			createLocationApiResponse, updateLocationForPracticesResponse, getPracticeForLocationApi;
	private Validations validation;
	private OrgInfo orgInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		orgInfo = new OrgInfo();
		locationInfo = new LocationsInfo();
		clientId = createClient(ccUser.getClientOneType());
		systemId = createSystemNode(clientId);
		enterpriseId = createEnterpriseNode(systemId);
		practiceId = createPracticeNode(enterpriseId);
		locationId = createLocation(clientId);
	}

	public int createClient(String clientType) throws Exception {
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
		return createClientApiResponse.jsonPath().get("clientId");
	}

	public int createSystemNode(int clientId) throws Exception {
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
				testData.getProperty("api.system.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createSystemApiResponse.jsonPath().get("orgId");
	}

	public int createEnterpriseNode(int SystemId) throws Exception {
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.displayname"), testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), SystemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createEnterpriseApiResponse.jsonPath().get("orgId");
	}

	public int createPracticeNode(int enterpriseId) throws Exception {
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse.jsonPath().get("orgId");
	}

	public int createLocation(int clientId) throws Exception {
		String name = testData.getProperty("api.location.name");
		String addressLine1 = testData.getProperty("api.location.name");
		String addressLine2 = testData.getProperty("api.location.name");
		String city = testData.getProperty("api.location.name");
		String state = testData.getProperty("api.state.name");
		int zipcode = Integer.parseInt(testData.getProperty("api.zipcode"));
		int lat = Integer.parseInt(testData.getProperty("api.lat"));
		int lng = Integer.parseInt(testData.getProperty("api.lng"));
		int externalId = Integer.parseInt(testData.getProperty("api.external.id"));
		String timezoneCode = testData.getProperty("timezone.code");
		createLocationApiResponse = locationInfo.createLocation_DL(String.valueOf(clientId), name, addressLine1,
				addressLine2, city, state, zipcode, lat, lng, externalId, timezoneCode, "");
		return createLocationApiResponse.jsonPath().get("id");
	}

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17718_Get a practice by providing valid location id for practices")
	public void getPracticeForLocationWithValidURLAndValidInput() throws IOException {
		logStep("Associate practices for locations");
		updateLocationForPracticesResponse = locationInfo.locationAssociateToPractice_DL(locationId, practiceId, true);
		CCUtils.assertEquals(updateLocationForPracticesResponse.getStatusCode(), 200);
		logStep(" initiating....Verify the Response code as 200, when user provided valid locationid");
		getPracticeForLocationApi = sharedInfo.getPracticeForLocationWithExistingOrNonExistingInput(locationId);
		CCUtils.assertEquals(getPracticeForLocationApi.getStatusCode(), 200);
	}

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17756_Get a practice bu providing Invalid URL")
	public void getPracticeForLocationWithValidURLAndInvalidInput() throws IOException {
		logStep(" initiating...Verify the Response code as 404, when user provides Invalid URL");
		getPracticeForLocationApi = sharedInfo.getPracticeForLocationWithInvalidInput(locationId);
		CCUtils.assertEquals(getPracticeForLocationApi.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getPracticeForLocationApi);
	}

	@Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17722_Get a practice bu providing Invalid locationId")
	public void getPracticeForLocationWithIncorrectInput() throws IOException {
		logStep(" initiating...Verify the Response code as 204, when user provides Invalid locationid");
		getPracticeForLocationApi = sharedInfo.getPracticeForLocationWithExistingOrNonExistingInput(
				Integer.parseInt(testData.getProperty("api.invalid.location.id")));
		CCUtils.assertEquals(getPracticeForLocationApi.getStatusCode(), 204);
	}

	@Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17723_Make a POST call Instead of GET by providing valid locationId")
	public void getPracticeForLocationWithInvalidPost() throws IOException {
		logStep(" initiating...Verify the Response code as 405, when user makes Incorrect CALL");
		getPracticeForLocationApi = sharedInfo.getPracticeForLocationWithValidInputForPost(locationId);
		CCUtils.assertEquals(getPracticeForLocationApi.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(getPracticeForLocationApi);
	}
}
