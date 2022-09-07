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

public class AddOrRemoveLocationsForTheProvider extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private LocationsValidations locationsValidations;
	Response createProviderApiResponse, createClientApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, updatePracticeassociateToProvider, updateLocationassociateToPractice,
			updateLocationForProvider;
	List<Locations> locationId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		locationsValidations = new LocationsValidations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add location id for existing providerId and practiceId and verify response body")
	public void testAddlocationForProvider() throws IOException {
		logStep("Add valid location id for existing providerId and practiceId");
		locationId = Arrays.asList(new Locations(Integer.parseInt(testData.getProperty("valid.location.Id")), true));
		updateLocationForProvider = sharedInfo.addOrRemoveLocationForProvider(
				Integer.parseInt(testData.getProperty("valid.providerId")),
				Integer.parseInt(testData.getProperty("valid.practiceId")), locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForProvider);
		CCUtils.assertEquals(updateLocationForProvider.getStatusCode(), 207);
		int locationId = Integer.parseInt(testData.getProperty("valid.location.Id"));
		locationsValidations.verifyAddOrRemoveLocationsApiResponseBody(updateLocationForProvider, locationId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Remove location id for existing providerId and practiceId and verify response body")
	public void testRemovelocationForProvider() throws IOException {
		logStep("Remove valid location id for existing providerId and practiceId ");
		locationId = Arrays.asList(new Locations(Integer.parseInt(testData.getProperty("valid.location.Id")), false));
		updateLocationForProvider = sharedInfo.addOrRemoveLocationForProvider(
				Integer.parseInt(testData.getProperty("valid.providerId")),
				Integer.parseInt(testData.getProperty("valid.practiceId")), locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForProvider);
		CCUtils.assertEquals(updateLocationForProvider.getStatusCode(), 207);
		int locationId = Integer.parseInt(testData.getProperty("valid.location.Id"));
		locationsValidations.verifyAddOrRemoveLocationsApiResponseBody(updateLocationForProvider, locationId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting location id for existing providerId and practiceId  and verify error message")
	public void testNonExistingLocationForProvider() throws IOException {
		logStep("Enter nonexisting location id for existing providerId and practiceId");
		locationId = Arrays
				.asList(new Locations(Integer.parseInt(testData.getProperty("nonexisting.location.id")), true));
		updateLocationForProvider = sharedInfo.addOrRemoveLocationForProvider(
				Integer.parseInt(testData.getProperty("valid.providerId")),
				Integer.parseInt(testData.getProperty("valid.practiceId")), locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForProvider);
		CCUtils.assertEquals(updateLocationForProvider.getStatusCode(), 207);
		ArrayList<Integer> locationId = new ArrayList<Integer>();
		locationId.add(Integer.parseInt(testData.getProperty("nonexisting.location.id")));
		locationsValidations.verifyAddOrRemoveLocationsApiResponseBodyForInvalidInput(updateLocationForProvider, locationId, "ERROR",
				"Invalid input");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid location id for nonexisting provider id and existing practice id and verify error message")
	public void testValidLocationForNonExistingProvider() throws IOException {
		logStep("Enter valid location id for nonexisting provider id and existing practice id");
		locationId = Arrays.asList(new Locations(Integer.parseInt(testData.getProperty("valid.location.Id")), true));
		updateLocationForProvider = sharedInfo.addOrRemoveLocationForProvider(
				Integer.parseInt(testData.getProperty("nonexisting.providerId")),
				Integer.parseInt(testData.getProperty("valid.practiceId")), locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForProvider);
		CCUtils.assertEquals(updateLocationForProvider.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateLocationForProvider);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid location id for nonexisting provider and practiceId and verify error message")
	public void testValidLocationForNonExistingProviderIdAndPracticeId() throws IOException {
		logStep("Enter valid location id for nonexisting provider and practiceId");
		locationId = Arrays.asList(new Locations(Integer.parseInt(testData.getProperty("valid.location.Id")), true));
		updateLocationForProvider = sharedInfo.addOrRemoveLocationForProvider(
				Integer.parseInt(testData.getProperty("nonexisting.providerId")),
				Integer.parseInt(testData.getProperty("nonexisting.practiceId")), locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForProvider);
		CCUtils.assertEquals(updateLocationForProvider.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateLocationForProvider);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank input in request payload and verify error message")
	public void testErrorMessageForBlankRequestPayload() throws IOException {
		logStep("Add valid provider and practiceId");
		locationId = Arrays.asList();
		updateLocationForProvider = sharedInfo.addOrRemoveLocationForProvider(
				Integer.parseInt(testData.getProperty("valid.providerId")),
				Integer.parseInt(testData.getProperty("valid.practiceId")), locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForProvider);
		CCUtils.assertEquals(updateLocationForProvider.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateLocationForProvider);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Make a POST method call instead of PUT method call and verify error message")
	public void testErrorMessageForPostMethodCall() throws IOException {
		logStep("Add valid location id for provider");
		locationId = Arrays.asList(new Locations(Integer.parseInt(testData.getProperty("valid.location.Id")), true));
		updateLocationForProvider = sharedInfo.invalidMethodCallForaddOrRemoveLocationForProvider(
				Integer.parseInt(testData.getProperty("valid.providerId")),
				Integer.parseInt(testData.getProperty("valid.practiceId")), locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForProvider);
		CCUtils.assertEquals(updateLocationForProvider.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(updateLocationForProvider);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url to Add or Remove locations for the provider and verify error message")
	public void testErrorMessageForInvalidUrl() throws IOException {
		logStep("Add valid location id for provider");
		locationId = Arrays.asList(new Locations(Integer.parseInt(testData.getProperty("valid.location.Id")), true));
		updateLocationForProvider = sharedInfo.addOrRemoveLocationForProvider_InvalidUrl(
				Integer.parseInt(testData.getProperty("valid.providerId")),
				Integer.parseInt(testData.getProperty("valid.practiceId")), locationId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForProvider);
		CCUtils.assertEquals(updateLocationForProvider.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateLocationForProvider);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values for providerId and practiceId to get Locations  and verify error message")
	public void testGetLocationForNullValues() throws IOException {
		updateLocationForProvider = sharedInfo.getLocationsForProviderForNullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateLocationForProvider);
		CCUtils.assertEquals(updateLocationForProvider.getStatusCode(), 400);
	}
}