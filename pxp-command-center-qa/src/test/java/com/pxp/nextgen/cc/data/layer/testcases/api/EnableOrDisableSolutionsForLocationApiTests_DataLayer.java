//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.OrgSolutions;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class EnableOrDisableSolutionsForLocationApiTests_DataLayer extends BaseRest {
	private Validations validation;
	private SharedInfo sharedInfo;
	private LocationsValidations locationValidation;
	private OrgInfo orgInfo;
	private CreateClientHierarchy_DL clientHierObj;
	public int clientId, systemId, agPracticeId, locationId, enterpriseId;
	public String externalId;
	Response updateSolutionsApiResponse, enableDisableSolutionForLocation, getSolutionForLocation,
			createNewLocationApiResponse, associateDeassociatePracticesForLocationApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
		locationValidation = new LocationsValidations();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		agPracticeId = clientHierObj.createPractice_DL(enterpriseId);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable existing solution id for existing locationId and practiceId and verify response body")
	public void testToEnableExistingSolutionIdForExistingLocationId() throws Exception {
		logStep("Enable existing solution id for existing resourceId  and practiceId ");
		locationId = createLocationWithinClient(clientId);
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, agPracticeId, true);
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id").trim(),
						testData.getProperty("status.newly.purchased").trim()),
				new OrgSolutions(testData.getProperty("engage.id").trim(),
						testData.getProperty("status.newly.purchased").trim()));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, agPracticeId);
		enableDisableSolutionForLocation = sharedInfo.enableDisableSolutionForLocation_DL(agPracticeId, locationId,
				Integer.parseInt(testData.getProperty("Portal.id").trim()), true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(enableDisableSolutionForLocation);
		CCUtils.assertEquals(enableDisableSolutionForLocation.getStatusCode(), 200);
		getSolutionForLocation = sharedInfo.getSolutionForLocation_DL(agPracticeId, locationId);
		locationValidation.getSolutionsForLocations(getSolutionForLocation,
				Integer.parseInt(testData.getProperty("Portal.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Disable existing solution id for existing locationId and practiceId and verify response body")
	public void testToDisableExistingSolutionIdForExistingLocationId() throws Exception {
		logStep("Disable existing solution id for existing locationId and practiceId");
		locationId = createLocationWithinClient(clientId);
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, agPracticeId, true);
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id").trim(),
						testData.getProperty("status.newly.purchased").trim()),
				new OrgSolutions(testData.getProperty("engage.id").trim(),
						testData.getProperty("status.newly.purchased").trim()));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, agPracticeId);
		enableDisableSolutionForLocation = sharedInfo.enableDisableSolutionForLocation_DL(agPracticeId, locationId,
				Integer.parseInt(testData.getProperty("Portal.id").trim()), true);
		enableDisableSolutionForLocation = sharedInfo.enableDisableSolutionForLocation_DL(agPracticeId, locationId,
				Integer.parseInt(testData.getProperty("Portal.id").trim()), false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(enableDisableSolutionForLocation);
		CCUtils.assertEquals(enableDisableSolutionForLocation.getStatusCode(), 200);
		getSolutionForLocation = sharedInfo.getSolutionForLocation_DL(agPracticeId, locationId);
		CCUtils.assertEquals(getSolutionForLocation.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable Disable solution for non existing locationId and existing practiceId and verify response body")
	public void testErrorEnableDisableSolutionForExistingPracticeIdNonExistingLocationId() throws Exception {
		logStep("Disable existing solution id for existing locationId and practiceId");
		locationId = createLocationWithinClient(clientId);
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, agPracticeId, true);
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("engage.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, agPracticeId);
		enableDisableSolutionForLocation = sharedInfo.enableDisableSolutionForLocation_DL(agPracticeId, 899,
				Integer.parseInt(testData.getProperty("Portal.id")), true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(enableDisableSolutionForLocation);
		CCUtils.assertEquals(enableDisableSolutionForLocation.getStatusCode(), 400);
		locationValidation.verifyErrorResponseForNonExistingId(enableDisableSolutionForLocation);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable Disable solution for non existing locationId and existing practiceId and verify response body")
	public void testErrorEnableDisableSolutionForNonExistingPracticeIdExistingLocationId() throws Exception {
		logStep("Disable existing solution id for existing locationId and practiceId");
		locationId = createLocationWithinClient(clientId);
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, agPracticeId, true);
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("engage.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, agPracticeId);
		enableDisableSolutionForLocation = sharedInfo.enableDisableSolutionForLocation_DL(100, locationId,
				Integer.parseInt(testData.getProperty("Portal.id")), true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(enableDisableSolutionForLocation);
		CCUtils.assertEquals(enableDisableSolutionForLocation.getStatusCode(), 400);
		locationValidation.verifyErrorResponseForNonExistingId(enableDisableSolutionForLocation);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable Disable solution for both non existing locationId and practiceId and verify response body")
	public void testErrorEnableDisableSolutionForBothNonExistingPracticeIdAndLocationId() throws Exception {
		logStep("Disable existing solution id for existing locationId and practiceId");
		locationId = createLocationWithinClient(clientId);
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, agPracticeId, true);
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("engage.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, agPracticeId);
		enableDisableSolutionForLocation = sharedInfo.enableDisableSolutionForLocation_DL(100, 100,
				Integer.parseInt(testData.getProperty("Portal.id")), true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(enableDisableSolutionForLocation);
		CCUtils.assertEquals(enableDisableSolutionForLocation.getStatusCode(), 400);
		locationValidation.verifyErrorResponseForNonExistingId(enableDisableSolutionForLocation);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable Disable solution for both existing locationId and practiceId and by giving solution Id as null and verify response body")
	public void testErrorEnableDisableSolutionForBothExistingPracticeIdAndLocationIdWthSolutionIDNULL()
			throws Exception {
		logStep("Disable existing solution id for existing locationId and practiceId");
		locationId = createLocationWithinClient(clientId);
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, agPracticeId, true);
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("engage.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, agPracticeId);
		enableDisableSolutionForLocation = sharedInfo.enableDisableSolutionForLocation_DL_NULL_SOLUTIONID(agPracticeId,
				locationId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(enableDisableSolutionForLocation);
		CCUtils.assertEquals(enableDisableSolutionForLocation.getStatusCode(), 400);
		locationValidation.verifyErrorResponseForInvalidSolutionId(enableDisableSolutionForLocation);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable Disable solution for both existing locationId and practiceId and by giving active as null and verify response body")
	public void testErrorEnableDisableSolutionForBothExistingPracticeIdAndLocationIdWthActiveNULL() throws Exception {
		logStep("Disable existing solution id for existing locationId and practiceId");
		locationId = createLocationWithinClient(clientId);
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, agPracticeId, true);
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("engage.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, agPracticeId);
		enableDisableSolutionForLocation = sharedInfo.enableDisableSolutionForLocation_DL_NULL_Active(agPracticeId,
				locationId, Integer.parseInt(testData.getProperty("Portal.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(enableDisableSolutionForLocation);
		CCUtils.assertEquals(enableDisableSolutionForLocation.getStatusCode(), 400);
		locationValidation.verifyErrorResponseForInvalidActiveId(enableDisableSolutionForLocation);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable Disable solution for both existing locationId and practiceId and by giving both solution Id and active as null and verify response body")
	public void testErrorEnableDisableSolutionForBothExistingPracticeIdAndLocationIdWthBothActiveAndSolutionIdNULL()
			throws Exception {
		logStep("Disable existing solution id for existing locationId and practiceId");
		locationId = createLocationWithinClient(clientId);
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, agPracticeId, true);
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("engage.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, agPracticeId);
		enableDisableSolutionForLocation = sharedInfo
				.enableDisableSolutionForLocation_DL_NULL_SOLUTIONID_ACTIVE(agPracticeId, locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(enableDisableSolutionForLocation);
		CCUtils.assertEquals(enableDisableSolutionForLocation.getStatusCode(), 400);
		locationValidation.verifyErrorResponseForInvalidSolutionIdActive(enableDisableSolutionForLocation);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable disable solutions with correct practiceId and location id with invalid method call : patch")
	public void testEnableDisableSolutionForLoactionIdAndPracticeIdInvalidMethodCall() throws Exception {
		locationId = createLocationWithinClient(clientId);
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, agPracticeId, true);
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("engage.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, agPracticeId);
		enableDisableSolutionForLocation = sharedInfo.enableDisableSolutionForLocation_DL_INVALID_Method_Call(
				agPracticeId, locationId, Integer.parseInt(testData.getProperty("Portal.id")), true);
		CCUtils.assertEquals(enableDisableSolutionForLocation.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPatchMethodCall(enableDisableSolutionForLocation);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable disable solutions with correct practiceId and location id with invalid url")
	public void testEnableDisableSolutionForLoactionIdAndPracticeIdInvalidUrl() throws Exception {
		locationId = createLocationWithinClient(clientId);
		associateDeassociatePracticesForLocationApiResponse = sharedInfo
				.associateDeassociatePracticeForLocation_DL(locationId, agPracticeId, true);
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("engage.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, agPracticeId);
		enableDisableSolutionForLocation = sharedInfo.enableDisableSolutionForLocation_DL_INVALID_URL(agPracticeId,
				locationId, Integer.parseInt(testData.getProperty("Portal.id")), true);
		CCUtils.assertEquals(enableDisableSolutionForLocation.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(enableDisableSolutionForLocation);
	}
}
