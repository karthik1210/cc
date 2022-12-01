//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.Locations;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AddOrRemoveLocationsForTheResourceApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private LocationsValidations locationsValidations;
	Response updateLocationForResource;
	List<Locations> locationId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		locationsValidations = new LocationsValidations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add location id for existing ResourceId and practiceId and verify response body")
	public void testToAddExistingLocationIdForExistingResourceId() throws IOException {
		logStep("Add valid location id for existing providerId and practiceId");
		locationId = Arrays.asList(new Locations(Integer.parseInt(testData.getProperty("valid.location.Id")), true));
		updateLocationForResource = sharedInfo.addOrRemoveLocationForResource_DL(
				Integer.parseInt(testData.getProperty("valid.resourceId")),
				Integer.parseInt(testData.getProperty("valid.practiceId")), locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForResource);
		CCUtils.assertEquals(updateLocationForResource.getStatusCode(), 207);
		int locationId = Integer.parseInt(testData.getProperty("valid.location.Id"));
		locationsValidations.verifyAddOrRemoveLocationsApiResponseBody(updateLocationForResource, locationId);
	 }
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Remove location id for existing resourceId and practiceId and verify response body")
	public void testToRemoveExistingLocationIdForExistingResourceId() throws IOException {
		logStep("Remove valid location id for existing resourceId and practiceId ");
		locationId = Arrays.asList(new Locations(Integer.parseInt(testData.getProperty("valid.location.Id")), false));
		updateLocationForResource = sharedInfo.addOrRemoveLocationForResource_DL(
				Integer.parseInt(testData.getProperty("valid.resourceId")),
				Integer.parseInt(testData.getProperty("valid.practiceId")), locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForResource);
		CCUtils.assertEquals(updateLocationForResource.getStatusCode(), 207);
		int locationId = Integer.parseInt(testData.getProperty("valid.location.Id"));
		locationsValidations.verifyAddOrRemoveLocationsApiResponseBody(updateLocationForResource, locationId);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting location id for existing resourceId and practiceId  and verify error message")
	public void testToAddNonExistingLocationIdForExistingResourceId() throws IOException {
		logStep("Enter nonexisting location id for existing resourceId and practiceId");
		locationId = Arrays
				.asList(new Locations(Integer.parseInt(testData.getProperty("nonexisting.location.id")), true));
		updateLocationForResource = sharedInfo.addOrRemoveLocationForResource_DL(
				Integer.parseInt(testData.getProperty("valid.resourceId")),
				Integer.parseInt(testData.getProperty("valid.practiceId")), locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForResource);
		CCUtils.assertEquals(updateLocationForResource.getStatusCode(), 207);
		ArrayList<Integer> locationId = new ArrayList<Integer>();
		locationId.add(Integer.parseInt(testData.getProperty("nonexisting.location.id")));
		locationsValidations.verifyAddOrRemoveLocationsApiResponseBodyForInvalidInput(updateLocationForResource,
				locationId, "ERROR", "Invalid input");
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid location id for nonexisting resource id and existing practice id and verify error message")
	public void testValidLocationIdForNonExistingResourceId() throws IOException {
		logStep("Enter valid location id for nonexisting resource id and existing practice id");
		locationId = Arrays.asList(new Locations(Integer.parseInt(testData.getProperty("valid.location.Id")), true));
		updateLocationForResource = sharedInfo.addOrRemoveLocationForResource_DL(
				Integer.parseInt(testData.getProperty("nonexisting.resourceId")),
				Integer.parseInt(testData.getProperty("valid.practiceId")), locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForResource);
		CCUtils.assertEquals(updateLocationForResource.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateLocationForResource);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid location id for nonexisting resource and practiceId and verify error message")
	public void testValidLocationIdForNonExistingResourceIdAndPracticeId() throws IOException {
		logStep("Enter valid location id for nonexisting provider and practiceId");
		locationId = Arrays.asList(new Locations(Integer.parseInt(testData.getProperty("valid.location.Id")), true));
		updateLocationForResource = sharedInfo.addOrRemoveLocationForResource_DL(
				Integer.parseInt(testData.getProperty("nonexisting.resourceId")),
				Integer.parseInt(testData.getProperty("nonexisting.practiceId")), locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForResource);
		CCUtils.assertEquals(updateLocationForResource.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateLocationForResource);
	}
    
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank input in request payload and verify error message")
	public void testErrorMessageForBlankRequestPayload() throws IOException {
		logStep("Add valid resource and practiceId");
		locationId = Arrays.asList();
		updateLocationForResource = sharedInfo.addOrRemoveLocationForResource_DL(
				Integer.parseInt(testData.getProperty("valid.resourceId")),
				Integer.parseInt(testData.getProperty("valid.practiceId")), locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForResource);
		CCUtils.assertEquals(updateLocationForResource.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateLocationForResource);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values for resourceId and practiceId to add Locations and verify error message")
	public void testAddToLocationForNullValues() throws IOException {
		updateLocationForResource = sharedInfo.getLocationsForResourceForNullValues_DL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForResource);
		CCUtils.assertEquals(updateLocationForResource.getStatusCode(), 400);
	}
}
