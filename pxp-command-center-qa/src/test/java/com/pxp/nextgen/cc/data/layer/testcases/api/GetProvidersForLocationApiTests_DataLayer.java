//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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

public class GetProvidersForLocationApiTests_DataLayer extends BaseRest {
	private LocationsInfo locationsInfo;
	private LocationsValidations locationsValidations;
	private Validations validation;
	Response fetchProvidersForLocationApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		locationsInfo = new LocationsInfo();
		locationsValidations = new LocationsValidations();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId, practiceId to get one associated Provider and verify status code and response body")
	public void testGetProviderForLocationForExistingLocationIdPracticeId() throws IOException {
		logStep("Enter existing locationId, practiceId");
		int locationId = Integer.parseInt(testData.getProperty("api.locationId.one"));
		int practiceId = Integer.parseInt(testData.getProperty("api.practiceId.one"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = null;
		fetchProvidersForLocationApiResponse = locationsInfo.getProvidersForLocation_DL(locationId, practiceId, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationApiResponse);
		CCUtils.assertEquals(fetchProvidersForLocationApiResponse.getStatusCode(), 200);
		locationsValidations.verifyGetProvidersForLocationValidInput(fetchProvidersForLocationApiResponse,
				String.valueOf(pageSize));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId, practiceId, name to get Provider and verify status code and response body")
	public void testGetProviderForLocationForExistingLocationIdPracticeIdAndName() throws IOException {
		logStep("Enter existing locationId, practiceId & name");
		int locationId = Integer.parseInt(testData.getProperty("api.locationId.one"));
		int practiceId = Integer.parseInt(testData.getProperty("api.practiceId.one"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = (testData.getProperty("api.provider.name.one"));
		fetchProvidersForLocationApiResponse = locationsInfo.getProvidersForLocation_DL(locationId, practiceId, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationApiResponse);
		CCUtils.assertEquals(fetchProvidersForLocationApiResponse.getStatusCode(), 200);
		locationsValidations.verifyGetProvidersForLocationValidInput(fetchProvidersForLocationApiResponse, name);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId, practiceId to get multiple associated Providers and verify status code and response body")
	public void testGetProviderForLocationForExistingLocationIdPracticeIdToGetMultipleProvider() throws IOException {
		logStep("Enter existing locationId, practiceId ");
		int locationId = Integer.parseInt(testData.getProperty("api.locationId.two"));
		int practiceId = Integer.parseInt(testData.getProperty("api.practiceId.two"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = null;
		fetchProvidersForLocationApiResponse = locationsInfo.getProvidersForLocation_DL(locationId, practiceId, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationApiResponse);
		CCUtils.assertEquals(fetchProvidersForLocationApiResponse.getStatusCode(), 200);
		locationsValidations.verifyGetProvidersForLocationValidInput(fetchProvidersForLocationApiResponse,
				String.valueOf(pageSize));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId, practiceId with no associated Provider and verify status code and response body")
	public void testGetProviderForLocationForExistingLocationIdPracticeIdwithNoAssociateProvider() throws IOException {
		logStep("Enter existing locationId, practiceId ");
		int locationId = Integer.parseInt(testData.getProperty("api.locationId.three"));
		int practiceId = Integer.parseInt(testData.getProperty("api.practiceId.three"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = null;
		fetchProvidersForLocationApiResponse = locationsInfo.getProvidersForLocation_DL(locationId, practiceId, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationApiResponse);
		CCUtils.assertEquals(fetchProvidersForLocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing locationId, non-existing practiceId and verify status code and response body")
	public void testGetProviderForLocationForExistingLocationIdNonExistingPracticeId() throws IOException {
		logStep("Enter existing locationId, non-existing practiceId ");
		int locationId = Integer.parseInt(testData.getProperty("api.locationId.one"));
		int practiceId = Integer.parseInt(testData.getProperty("api.nonexisting.practiceId"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = null;
		fetchProvidersForLocationApiResponse = locationsInfo.getProvidersForLocation_DL(locationId, practiceId, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationApiResponse);
		CCUtils.assertEquals(fetchProvidersForLocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing locationId, existing practiceId and verify status code and response body")
	public void testGetProviderForLocationForNonexistingLocationIdNonExistingPracticeId() throws IOException {
		logStep("Enter existing locationId, non-existing practiceId ");
		int locationId = Integer.parseInt(testData.getProperty("api.nonexisting.locationId"));
		int practiceId = Integer.parseInt(testData.getProperty("api.practiceId.one"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = null;
		fetchProvidersForLocationApiResponse = locationsInfo.getProvidersForLocation_DL(locationId, practiceId, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationApiResponse);
		CCUtils.assertEquals(fetchProvidersForLocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing locationId, non-existing practiceId and verify status code and response body")
	public void testGetProviderForLocationForexistingLocationIdNonExistingPracticeId() throws IOException {
		logStep("Enter existing locationId, non-existing practiceId ");
		int locationId = Integer.parseInt(testData.getProperty("api.nonexisting.locationId"));
		int practiceId = Integer.parseInt(testData.getProperty("api.nonexisting.practiceId"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = null;
		fetchProvidersForLocationApiResponse = locationsInfo.getProvidersForLocation_DL(locationId, practiceId, page,
				pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationApiResponse);
		CCUtils.assertEquals(fetchProvidersForLocationApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter locationId & practiceId as null and verify status code and response body")
	public void testErrorMessageForNullValuesInPracticeIdAndLocationId() throws IOException {
		fetchProvidersForLocationApiResponse = locationsInfo.getProvidersForLocation_DL_NullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationApiResponse);
		CCUtils.assertEquals(fetchProvidersForLocationApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageIfClientIdIsNull(fetchProvidersForLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMassageForGetProviderForLocationUsingInvalidUrl() throws IOException {
		logStep("Enter existing locationId, practiceId & name");
		int locationId = Integer.parseInt(testData.getProperty("api.locationId.one"));
		int practiceId = Integer.parseInt(testData.getProperty("api.practiceId.one"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = (testData.getProperty("api.provider.name.one"));
		fetchProvidersForLocationApiResponse = locationsInfo.getProvidersForLocationInvalidUrl_DL(locationId,
				practiceId, page, pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationApiResponse);
		CCUtils.assertEquals(fetchProvidersForLocationApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchProvidersForLocationApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter PUT method instead of GET method and validate error status code")
	public void testErrorForInvalidMethodCall() throws IOException {
		logStep("Enter existing locationId, practiceId & name");
		int locationId = Integer.parseInt(testData.getProperty("api.locationId.one"));
		int practiceId = Integer.parseInt(testData.getProperty("api.practiceId.one"));
		int page = Integer.parseInt(testData.getProperty("api.page.number"));
		int pageSize = Integer.parseInt(testData.getProperty("api.page.size"));
		String name = (testData.getProperty("api.provider.name.one"));
		fetchProvidersForLocationApiResponse = locationsInfo.getProvidersForLocationInvalidMethodCall_DL(locationId,
				practiceId, page, pageSize, name);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchProvidersForLocationApiResponse);
		CCUtils.assertEquals(fetchProvidersForLocationApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(fetchProvidersForLocationApiResponse);
	}
}