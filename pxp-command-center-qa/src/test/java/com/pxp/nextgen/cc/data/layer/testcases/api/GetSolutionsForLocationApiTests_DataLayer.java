//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.OrgSolutions;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import io.restassured.response.Response;

public class GetSolutionsForLocationApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private LocationsValidations locationValidation;
	private OrgInfo orgInfo;
	private CreateClientHierarchy_DL clientHierObj;
	public int clientId, systemId, practiceId, locationId, enterpriseId;
	public String externalId;
	Response updateSolutionsApiResponse, enableDisableSolutionForLocation, getSolutionForLocation,
			createNewLocationApiResponse, associateDeassociatePracticesForLocationApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		orgInfo = new OrgInfo();
		locationValidation = new LocationsValidations();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createAgnosticPractice_DL(systemId);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId and practiceId and verify response body")
	public void testToGetSolutionsForExistingLocationIdAndPracticeId() throws Exception {
		logStep("Enable existing existing locationId  for practiceId ");
		locationId = createLocationWithinClient(clientId);
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, practiceId, true);
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id").trim(),
						testData.getProperty("status.newly.purchased").trim()),
				new OrgSolutions(testData.getProperty("engage.id").trim(),
						testData.getProperty("status.newly.purchased").trim()));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, practiceId);
		enableDisableSolutionForLocation = sharedInfo.enableDisableSolutionForLocation_DL(practiceId, locationId,
				Integer.parseInt(testData.getProperty("Portal.id").trim()), true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(enableDisableSolutionForLocation);
		CCUtils.assertEquals(enableDisableSolutionForLocation.getStatusCode(), 200);
		logStep("Get solutions for existing locationId and practiceId and verify response body");
		getSolutionForLocation = sharedInfo.getSolutionForLocation_DL(practiceId, locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(enableDisableSolutionForLocation);
		CCUtils.assertEquals(getSolutionForLocation.getStatusCode(), 200);
		locationValidation.getSolutionsForLocations(getSolutionForLocation,
				Integer.parseInt(testData.getProperty("Portal.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceid and nonexisting locationId and verify response code")
	public void testResponseCodeForNonExistingLocationIdAndExistingPracticeId() throws Exception {
		logStep("Enter existing locationId and nonexisting practiceId and verify response body");
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id").trim(),
						testData.getProperty("status.newly.purchased").trim()),
				new OrgSolutions(testData.getProperty("engage.id").trim(),
						testData.getProperty("status.newly.purchased").trim()));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, practiceId);
		logStep("Enter existing practiceid and nonexisting locationId and verify response code");
		int locationId = Integer.parseInt(testData.getProperty("api.nonexisting.locationId"));
		getSolutionForLocation = sharedInfo.getSolutionForLocation_DL(practiceId, locationId);
		CCUtils.assertEquals(getSolutionForLocation.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting practiceid and existing locationId and verify response code")
	public void testResponseCodeForExistingLocationIdAndNonExistingPracticeId() throws Exception {
		locationId = createLocationWithinClient(clientId);
		logStep("Enter nonexisting practiceid and existing locationId and verify response code");
		int practiceId = Integer.parseInt(testData.getProperty("api.nonexisting.practiceId"));
		getSolutionForLocation = sharedInfo.getSolutionForLocation_DL(practiceId, locationId);
		CCUtils.assertEquals(getSolutionForLocation.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting practiceid and nonexisting locationId and verify response code")
	public void testResponseCodeForNonExistingLocationIdAndNonExistingPracticeId()
			throws NullPointerException, IOException {
		logStep("Enter nonexisting practiceid and nonexisting locationId and verify response code");
		int locationId = Integer.parseInt(testData.getProperty("api.nonexisting.locationId"));
		int practiceId = Integer.parseInt(testData.getProperty("api.nonexisting.practiceId"));
		getSolutionForLocation = sharedInfo.getSolutionForLocation_DL(practiceId, locationId);
		CCUtils.assertEquals(getSolutionForLocation.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values in LocationId & PracticeId field and verify response code")
	public void testResponseCodeForNullValuesInLocationIdAndPracticeIdField() throws NullPointerException, IOException {
		logStep("Enter null values in LocationId & PracticeId field");
		getSolutionForLocation = sharedInfo.getSolutionForLocationForNullValue_DL();
		CCUtils.assertEquals(getSolutionForLocation.getStatusCode(), 400);
	}
}