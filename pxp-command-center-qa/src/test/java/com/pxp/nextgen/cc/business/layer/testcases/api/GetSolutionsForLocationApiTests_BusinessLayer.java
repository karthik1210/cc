// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.LocationsInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import io.restassured.response.Response;

public class GetSolutionsForLocationApiTests_BusinessLayer extends BaseRest {
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	private LocationsValidations locationsValidations;
	private LocationsInfo locationsInfo;
	private CreateClientHierarchy_BL clientHierObj;
	Response updateSolutionsApiResponse, getSolutionsForLocation, createNewLocationApiResponse,
			associateDeassociatePracticesForLocationApiResponse, toggleSolutionStatusApiResponse;
	int clientId, systemId, enterpriseId, practiceId, locationId;
	String externalId, firstName, lastName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		orgInfo = new OrgInfo();
		locationsInfo = new LocationsInfo();
		sharedInfo = new SharedInfo();
		locationsValidations = new LocationsValidations();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid practiceid and locationId  and verify response code")
	public void testResponseCodeToToggleSolutionStatusForLocationAndPractice()
			throws NullPointerException, IOException {
		logStep("Associate solution to practice");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = orgInfo.updateOrgSolutionsDetails_BL(solutionId, "New", practiceId);
		logStep("Associate location to practice");
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_BL(locationId, practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticesForLocationApiResponse.getStatusCode(), 200);
		logStep("Toggle solution for Location and Practice");
		toggleSolutionStatusApiResponse = locationsInfo.toggleSolutionStatusApiResponse_BL(locationId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(toggleSolutionStatusApiResponse.getStatusCode(), 200);
		logStep("Verify associate solution for Location and Practice");
		getSolutionsForLocation = locationsInfo.getSolutionStatusApiResponse_BL(locationId, practiceId);
		CCUtils.assertEquals(getSolutionsForLocation.getStatusCode(), 200);
		locationsValidations.verifyNoOfSolutionsFetchedSolutionNameandId(getSolutionsForLocation);
		toggleSolutionStatusApiResponse = locationsInfo.toggleSolutionStatusApiResponse_BL(locationId, practiceId,
				solutionId, false);
		CCUtils.assertEquals(toggleSolutionStatusApiResponse.getStatusCode(), 200);
		getSolutionsForLocation = locationsInfo.getSolutionStatusApiResponse_BL(locationId, practiceId);
		CCUtils.assertEquals(getSolutionsForLocation.getStatusCode(), 204);
		logStep("Verify deassociate solution for Location and Practice");
		getSolutionsForLocation = locationsInfo.getSolutionStatusApiResponse_BL(locationId, practiceId);
		CCUtils.assertEquals(getSolutionsForLocation.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceid and nonexisting locationId and verify response code")
	public void testResponseCodeForNonExistingLocationIdAndExistingPracticeId()
			throws NullPointerException, IOException {
		logStep("Associate solution to practice");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = orgInfo.updateOrgSolutionsDetails_BL(solutionId, "New", practiceId);
		logStep("Enter existing practiceid and nonexisting locationId and verify response code");
		int locationId = Integer.parseInt(testData.getProperty("api.nonexisting.locationId"));
		getSolutionsForLocation = locationsInfo.getSolutionStatusApiResponse_BL(locationId, practiceId);
		CCUtils.assertEquals(getSolutionsForLocation.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting practiceid and existing locationId and verify response code")
	public void testResponseCodeForExistingLocationIdAndNonExistingPracticeId()
			throws NullPointerException, IOException {
		logStep("Enter nonexisting practiceid and existing locationId and verify response code");
		int practiceId = Integer.parseInt(testData.getProperty("api.nonexisting.practiceId"));
		getSolutionsForLocation = locationsInfo.getSolutionStatusApiResponse_BL(locationId, practiceId);
		CCUtils.assertEquals(getSolutionsForLocation.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting practiceid and nonexisting locationId and verify response code")
	public void testResponseCodeForNonExistingLocationIdAndNonExistingPracticeId()
			throws NullPointerException, IOException {
		logStep("Enter nonexisting practiceid and nonexisting locationId and verify response code");
		int locationId = Integer.parseInt(testData.getProperty("api.nonexisting.locationId"));
		int practiceId = Integer.parseInt(testData.getProperty("api.nonexisting.practiceId"));
		getSolutionsForLocation = locationsInfo.getSolutionStatusApiResponse_BL(locationId, practiceId);
		CCUtils.assertEquals(getSolutionsForLocation.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values in LocationId & PracticeId field and verify response code")
	public void testResponseCodeForNullValuesInLocationIdAndPracticeIdField() throws NullPointerException, IOException {
		logStep("Enter null values in LocationId & PracticeId field");
		getSolutionsForLocation = locationsInfo.getSolutionStatusApiResponseNullValue_BL();
		CCUtils.assertEquals(getSolutionsForLocation.getStatusCode(), 400);
	}
}