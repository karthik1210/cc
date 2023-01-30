//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetExternalInternalMapApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_DL clientHierObj;
	private LocationsValidations locationsValidations;
	int clientId, systemId, enterpriseId, practiceId, solutionId, providerId, practiceIdOne, providerIdOne;
	String locationId;
	Response createNewLocationApiResponse, getExternalInternalMapApiResponse;
	String externalId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		locationsValidations = new LocationsValidations();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
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
