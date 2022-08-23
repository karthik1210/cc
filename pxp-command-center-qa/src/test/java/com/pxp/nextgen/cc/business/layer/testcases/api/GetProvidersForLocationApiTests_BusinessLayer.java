//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.LocationsInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetProvidersForLocationApiTests_BusinessLayer extends BaseRest {

	private LocationsInfo locationsInfo;
	private LocationsValidations locationsValidations;
	private Validations validation;
	Response fetchProvidersForLocationResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		locationsInfo = new LocationsInfo();
		locationsValidations = new LocationsValidations();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId, practiceId to get one Provider and verify status code and response body")
	public void testGetProviderForLocationForExistingLocationIdPracticeId() throws IOException {
		logStep("Enter existing locationId, practiceId");
		int locationId = Integer.parseInt(testData.getProperty("api.locationId.one"));
		int practiceId = Integer.parseInt(testData.getProperty("api.practiceId.one"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = null;
		fetchProvidersForLocationResponse = locationsInfo.getProvidersForLocation_BL(locationId, practiceId, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 200);
		locationsValidations.verifyGetProvidersForLocationValidInput(fetchProvidersForLocationResponse,
				String.valueOf(pageSize));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId, practiceId & name to get Provider and verify status code and response body")
	public void testGetProviderForLocationForExistingLocationIdPracticeIdAndName() throws IOException {
		logStep("Enter existing locationId, practiceId & name");
		int locationId = Integer.parseInt(testData.getProperty("api.locationId.one"));
		int practiceId = Integer.parseInt(testData.getProperty("api.practiceId.one"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = (testData.getProperty("api.provider.name.one"));
		fetchProvidersForLocationResponse = locationsInfo.getProvidersForLocation_BL(locationId, practiceId, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 200);
		locationsValidations.verifyGetProvidersForLocationValidInput(fetchProvidersForLocationResponse, name);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId, practiceId to get multiple Provider and verify status code and response body")
	public void testGetProviderForLocationForExistingLocationIdPracticeIdToGetMultipleProvider() throws IOException {
		logStep("Enter existing locationId, practiceId ");
		int locationId = Integer.parseInt(testData.getProperty("api.locationId.two"));
		int practiceId = Integer.parseInt(testData.getProperty("api.practiceId.two"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = null;
		fetchProvidersForLocationResponse = locationsInfo.getProvidersForLocation_BL(locationId, practiceId, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 200);
		locationsValidations.verifyGetProvidersForLocationValidInput(fetchProvidersForLocationResponse,
				String.valueOf(pageSize));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId, practiceId with no associate  Provider and verify status code ")
	public void testGetProviderForLocationForExistingLocationIdPracticeIdwithNoAssociateProvider() throws IOException {
		logStep("Enter existing locationId, practiceId ");
		int locationId = Integer.parseInt(testData.getProperty("api.locationId.three"));
		int practiceId = Integer.parseInt(testData.getProperty("api.practiceId.three"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = null;
		fetchProvidersForLocationResponse = locationsInfo.getProvidersForLocation_BL(locationId, practiceId, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId, non-existing practiceId  verify verify status code")
	public void testGetProviderForLocationForExistingLocationIdNonExistingPracticeId() throws IOException {
		logStep("Enter existing locationId, non-existing practiceId ");
		int locationId = Integer.parseInt(testData.getProperty("api.locationId.one"));
		int practiceId = Integer.parseInt(testData.getProperty("api.nonexisting.practiceId"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = null;
		fetchProvidersForLocationResponse = locationsInfo.getProvidersForLocation_BL(locationId, practiceId, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing locationId, existing practiceId  verify verify status code")
	public void testGetProviderForLocationForNonexistingLocationIdNonExistingPracticeId() throws IOException {
		logStep("Enter existing locationId, non-existing practiceId ");
		int locationId = Integer.parseInt(testData.getProperty("api.nonexisting.locationId"));
		int practiceId = Integer.parseInt(testData.getProperty("api.practiceId.one"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = null;
		fetchProvidersForLocationResponse = locationsInfo.getProvidersForLocation_BL(locationId, practiceId, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing locationId, non-existing practiceId  verify verify status code")
	public void testGetProviderForLocationForexistingLocationIdNonExistingPracticeId() throws IOException {
		logStep("Enter existing locationId, non-existing practiceId ");
		int locationId = Integer.parseInt(testData.getProperty("api.nonexisting.locationId"));
		int practiceId = Integer.parseInt(testData.getProperty("api.nonexisting.practiceId"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = null;
		fetchProvidersForLocationResponse = locationsInfo.getProvidersForLocation_BL(locationId, practiceId, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMassageForGetProviderForLocationUsingInvalidUrl() throws IOException {
		logStep("Enter existing locationId, practiceId & name");
		int locationId = Integer.parseInt(testData.getProperty("api.locationId.one"));
		int practiceId = Integer.parseInt(testData.getProperty("api.practiceId.one"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = (testData.getProperty("api.provider.name.one"));
		fetchProvidersForLocationResponse = locationsInfo.getProvidersForLocationInvalidUrl_BL(locationId, practiceId,
				page, pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchProvidersForLocationResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of GET")
	public void testErrorForInvalidMethodCall() throws IOException {
		logStep("Enter existing locationId, practiceId & name");
		int locationId = Integer.parseInt(testData.getProperty("api.locationId.one"));
		int practiceId = Integer.parseInt(testData.getProperty("api.practiceId.one"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = (testData.getProperty("api.provider.name.one"));
		fetchProvidersForLocationResponse = locationsInfo.getProvidersForLocationInvalidMethodCall_BL(locationId,
				practiceId, page, pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(fetchProvidersForLocationResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error massage for null  locationId & practiceId")
	public void testErrorMessageForNullValuesInPracticeIdAndLocationId() throws IOException {
		fetchProvidersForLocationResponse = locationsInfo.getProvidersForLocation_BL_NullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationResponse);
		CCUtils.assertEquals(fetchProvidersForLocationResponse.getStatusCode(), 400);
		validation.verifyErrorMessageIfClientIdIsNull(fetchProvidersForLocationResponse);
	}
}