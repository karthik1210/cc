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

public class SearchLocationsApiTests_DataLayer extends BaseRest {

	private LocationsInfo locationInfo;
	private LocationsValidations locationsValidations;
	Response searchLocationsResult;
	private Validations validations;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		locationInfo = new LocationsInfo();
		locationsValidations = new LocationsValidations();
		validations = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "search full name location without applying location filters and keeping page 0 and page size 10")
	public void testSearchFullNameLocationWithoutFilterWithoutSorting() throws IOException {
		searchLocationsResult = locationInfo.getLocationsWithFilter_DL(
				testData.getProperty("search.location.full.name").trim(), 0, 0, 0, 0, "null", "null",
				Integer.parseInt(testData.getProperty("api.page").trim()),
				Integer.parseInt(testData.getProperty("api.pageSize1").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchLocationsResult);
		CCUtils.assertEquals(searchLocationsResult.getStatusCode(), 200);
		locationsValidations.verifySearchLocationOutputWithoutFilter(searchLocationsResult,
				testData.getProperty("search.location.full.name").trim());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "search partial name location without applying location filters and keeping page 0 and page size 10")
	public void testSearchPartialNameLocationWithoutFilterWithoutSorting() throws IOException {
		searchLocationsResult = locationInfo.getLocationsWithFilter_DL(
				testData.getProperty("search.location.partial.name").trim(), 0, 0, 0, 0, "null", "null",
				Integer.parseInt(testData.getProperty("api.page").trim()),
				Integer.parseInt(testData.getProperty("api.pageSize1").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchLocationsResult);
		CCUtils.assertEquals(searchLocationsResult.getStatusCode(), 200);
		locationsValidations.verifySearchLocationOutputWithoutFilter(searchLocationsResult,
				testData.getProperty("search.location.partial.name").trim());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "search non-existing location without applying location filters and keeping page 0 and page size 10")
	public void testSearchNonExistingLocationWithoutFilterWithoutSorting() throws IOException {
		searchLocationsResult = locationInfo.getLocationsWithFilter_DL(
				testData.getProperty("search.location.non.existing.name").trim(), 0, 0, 0, 0, "null", "null",
				Integer.parseInt(testData.getProperty("api.page").trim()),
				Integer.parseInt(testData.getProperty("api.pageSize1").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchLocationsResult);
		CCUtils.assertEquals(searchLocationsResult.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "search partial location name with system Id filter and keeping page 0 and page size 10")
	public void testSearchLocationWithSystemIdFilterWithoutSorting() throws IOException {
		searchLocationsResult = locationInfo.getLocationsWithFilterSystemId_DL(
				testData.getProperty("search.location").trim(),
				Integer.parseInt(testData.getProperty("search.location.system.filter").trim()), 0, 0, 0, "null", "null",
				Integer.parseInt(testData.getProperty("api.page").trim()),
				Integer.parseInt(testData.getProperty("api.pageSize1").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchLocationsResult);
		CCUtils.assertEquals(searchLocationsResult.getStatusCode(), 200);
		locationsValidations.verifySearchLocationOutputWithoutFilter(searchLocationsResult,
				testData.getProperty("search.location").trim());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "search partial location name with enterprise Id filter and keeping page 0 and page size 10")
	public void testSearchLocationWithEnterpriseIdFilterWithoutSorting() throws IOException {
		searchLocationsResult = locationInfo.getLocationsWithFilterEnterpriseId_DL(
				testData.getProperty("search.location").trim(), 0,
				Integer.parseInt(testData.getProperty("search.location.enterprise.filter").trim()), 0, 0, "null",
				"null", Integer.parseInt(testData.getProperty("api.page").trim()),
				Integer.parseInt(testData.getProperty("api.pageSize1").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchLocationsResult);
		CCUtils.assertEquals(searchLocationsResult.getStatusCode(), 200);
		locationsValidations.verifySearchLocationOutputWithoutFilter(searchLocationsResult,
				testData.getProperty("search.location").trim());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "search partial location name with practice Id filter and keeping page 0 and page size 10")
	public void testSearchLocationWithPracticeIdFilterWithoutSorting() throws IOException {
		searchLocationsResult = locationInfo.getLocationsWithFilterPracticeId_DL(
				testData.getProperty("search.location").trim(), 0, 0,
				Integer.parseInt(testData.getProperty("search.location.practice.filter").trim()), 0, "null", "null",
				Integer.parseInt(testData.getProperty("api.page").trim()),
				Integer.parseInt(testData.getProperty("api.pageSize1").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchLocationsResult);
		CCUtils.assertEquals(searchLocationsResult.getStatusCode(), 200);
		locationsValidations.verifySearchLocationOutputWithoutFilter(searchLocationsResult,
				testData.getProperty("search.location").trim());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "search partial location name with solutions Id filter and keeping page 0 and page size 10")
	public void testSearchLocationWithSolutionIdFilterWithoutSorting() throws IOException {
		searchLocationsResult = locationInfo.getLocationsWithFilterSolutionId_DL(
				testData.getProperty("search.location").trim(), 0, 0, 0,
				Integer.parseInt(testData.getProperty("search.location.solutions.filter").trim()), "null", "null",
				Integer.parseInt(testData.getProperty("api.page").trim()),
				Integer.parseInt(testData.getProperty("api.pageSize1").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchLocationsResult);
		CCUtils.assertEquals(searchLocationsResult.getStatusCode(), 200);
		locationsValidations.verifySearchLocationOutputWithoutFilter(searchLocationsResult,
				testData.getProperty("search.location").trim());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "search partial location name with sort column location in descending order by keeping page 0 and page size 10")
	public void testSearchLocationWithSortColumnNameIdFilterWithoutSorting() throws IOException {
		searchLocationsResult = locationInfo.getLocationsWithFilterSortColumnSortDir_DL(
				testData.getProperty("search.location").trim(), 0, 0, 0, 0,
				testData.getProperty("search.sortcolumn.location.name").trim(),
				testData.getProperty("search.sortdir.descending").trim(),
				Integer.parseInt(testData.getProperty("api.page").trim()),
				Integer.parseInt(testData.getProperty("api.pageSize1").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchLocationsResult);
		CCUtils.assertEquals(searchLocationsResult.getStatusCode(), 200);
		locationsValidations.verifySearchLocationOutputWithoutFilter(searchLocationsResult,
				testData.getProperty("search.location").trim());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "search location by invalid url")
	public void testSearchLocationInvalidUrl() throws IOException {
		searchLocationsResult = locationInfo.getLocationsInvalidUrl_DL(
				testData.getProperty("search.location.full.name").trim(), 0, 0, 0, 0, "null", "null",
				Integer.parseInt(testData.getProperty("api.page").trim()),
				Integer.parseInt(testData.getProperty("api.pageSize1").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchLocationsResult);
		CCUtils.assertEquals(searchLocationsResult.getStatusCode(), 404);
		validations.verifyErrorInResponseForInvalidUrl(searchLocationsResult);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "search location with invalid method call POST")
	public void testSearchLocationInvalidMethodCall() throws IOException {
		searchLocationsResult = locationInfo.getLocationsInvalidMethodCall_DL(
				testData.getProperty("search.location.full.name").trim(), 0, 0, 0, 0, "null", "null",
				Integer.parseInt(testData.getProperty("api.page").trim()),
				Integer.parseInt(testData.getProperty("api.pageSize1").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchLocationsResult);
		CCUtils.assertEquals(searchLocationsResult.getStatusCode(), 405);
		validations.verifyErrorMessageInResponseForInvalidMethodCall(searchLocationsResult);
	}
}
