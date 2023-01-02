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
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetExternalInternalMapApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	private LocationsValidations locationsValidations;
	int clientId, systemId, enterpriseId, practiceId, solutionId, providerId, practiceIdOne, providerIdOne;
	String locationId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse, createClientApiResponse,
			createNewLocationApiResponse, getExternalInternalMapApiResponse;
	String externalId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		locationsValidations = new LocationsValidations();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
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
		locationId = createNewLocationApiResponse.jsonPath().getString("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceId, locationId to get ExternalId and verify response body")
	public void testGetExternalIdForExistingPracticeIdAndLocationId() throws IOException {
		getExternalInternalMapApiResponse = sharedInfo.getExternalInternalMap_DL(practiceId,
				testData.getProperty("default.solution.id"), locationId, testData.getProperty("map.type"),
				testData.getProperty("map.maptype.internal"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalInternalMapApiResponse);
		CCUtils.assertEquals(getExternalInternalMapApiResponse.getStatusCode(), 200);
		locationsValidations.verifyGetExternalMapApiResponseBody(getExternalInternalMapApiResponse, externalId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceId, externalId to get locationId and verify response body")
	public void testGetLocationIdForExistingPracticeIdAndExternalId() throws IOException {
		getExternalInternalMapApiResponse = sharedInfo.getExternalInternalMap_DL(practiceId,
				testData.getProperty("default.solution.id"), externalId, testData.getProperty("map.type"),
				testData.getProperty("map.maptype.external"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalInternalMapApiResponse);
		CCUtils.assertEquals(getExternalInternalMapApiResponse.getStatusCode(), 200);
		locationsValidations.verifyGetInternalMapApiResponseBody(getExternalInternalMapApiResponse, locationId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing practiceId, existing locationId to get ExternalId and verify response body")
	public void testGetExternalIdForNonExistingPracticeIdAndExistingLocationId() throws IOException {
		getExternalInternalMapApiResponse = sharedInfo.getExternalInternalMap_DL(
				Integer.parseInt(testData.getProperty("non.existing.practice.id")),
				testData.getProperty("default.solution.id"), locationId, testData.getProperty("map.type"),
				testData.getProperty("map.maptype.internal"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalInternalMapApiResponse);
		CCUtils.assertEquals(getExternalInternalMapApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidOrgId(getExternalInternalMapApiResponse,
				testData.getProperty("non.existing.practice.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceId, non-existing locationId to get ExternalId and verify response body")
	public void testGetExternalIdForExistingPracticeIdAndNonExistingLocationId() throws IOException {
		getExternalInternalMapApiResponse = sharedInfo.getExternalInternalMap_DL(practiceId,
				testData.getProperty("default.solution.id"), testData.getProperty("non.existing.location.id"),
				testData.getProperty("map.type"), testData.getProperty("map.maptype.internal"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalInternalMapApiResponse);
		CCUtils.assertEquals(getExternalInternalMapApiResponse.getStatusCode(), 400);
		locationsValidations.verifyErrorResponseForInvalidExternalId(getExternalInternalMapApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceId, locationId with invalid type to get ExternalId and verify response body")
	public void testGetExternalIdForExistingPracticeIdAndLocationIdWithInvalidType() throws IOException {
		getExternalInternalMapApiResponse = sharedInfo.getExternalInternalMap_DL(practiceId,
				testData.getProperty("default.solution.id"), locationId, testData.getProperty("user.type.invalid"),
				testData.getProperty("map.maptype.internal"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalInternalMapApiResponse);
		CCUtils.assertEquals(getExternalInternalMapApiResponse.getStatusCode(), 400);
		locationsValidations.verifyErrorResponseForInvalidType(getExternalInternalMapApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceId, locationId with invalid maptype to get ExternalId and verify response body")
	public void testGetExternalIdForExistingPracticeIdAndLocationIdWithInvalidMapType() throws IOException {
		getExternalInternalMapApiResponse = sharedInfo.getExternalInternalMap_DL(practiceId,
				testData.getProperty("default.solution.id"), locationId, testData.getProperty("map.type"),
				testData.getProperty("user.type.invalid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalInternalMapApiResponse);
		CCUtils.assertEquals(getExternalInternalMapApiResponse.getStatusCode(), 400);
		locationsValidations.verifyErrorResponseForInvalidMapType(getExternalInternalMapApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter practiceId as null to get ExternalId and verify response body")
	public void testGetExternalIdForPracticeIdAsNull() throws IOException {
		getExternalInternalMapApiResponse = sharedInfo.getExternalInternalMap_NullValues_DL(
				testData.getProperty("default.solution.id"), locationId, testData.getProperty("map.type"),
				testData.getProperty("map.maptype.internal"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getExternalInternalMapApiResponse);
		CCUtils.assertEquals(getExternalInternalMapApiResponse.getStatusCode(), 400);
	}
}
