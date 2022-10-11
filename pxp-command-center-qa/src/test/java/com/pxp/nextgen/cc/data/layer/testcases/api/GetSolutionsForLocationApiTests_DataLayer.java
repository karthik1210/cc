//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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
	private ClientInfo clientInfo;
	private SharedInfo sharedInfo;
	private LocationsValidations locationValidation;
	private OrgInfo orgInfo;
	public int clientId, systemId, practiceId, locationId;
	public String externalId;
	Response createSystemApiResponse, createPracticeApiResponse, updateSolutionsApiResponse, createClientApiResponse,
			enableDisableSolutionForLocation, getSolutionForLocation, createNewLocationApiResponse,
			associateDeassociatePracticesForLocationApiResponse;

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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId and practiceId and verify response body")
	public void testToGetSolutionsForExistingLocationIdAndPracticeId() throws Exception {
		logStep("Enable existing existing locationId  for practiceId ");
		clientId = createClient();
		systemId = createSystemNode(clientId);
		practiceId = createAgnosticPracticeNode(systemId);
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
		clientId = createClient();
		systemId = createSystemNode(clientId);
		practiceId = createAgnosticPracticeNode(systemId);
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
		clientId = createClient();
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