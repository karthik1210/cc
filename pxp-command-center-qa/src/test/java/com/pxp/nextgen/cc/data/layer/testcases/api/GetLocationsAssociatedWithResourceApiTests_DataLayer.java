//Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.LocationsInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetLocationsAssociatedWithResourceApiTests_DataLayer extends BaseRest {

	private LocationsInfo locationInfo;
	private LocationsValidations locationsValidations;
	Response getLocationsForResourseResponse;
	private Validations validation;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		locationInfo = new LocationsInfo();
		locationsValidations = new LocationsValidations();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing resourceId, practiceId to get Locations For Resource  and verify status code and response body")
	public void testGetLocationForExistingResourceIdAndPracticeId() throws IOException {
		int resourceId = Integer.parseInt(testData.getProperty("api.resource.id"));
		int practiceId = Integer.parseInt(testData.getProperty("exiting.practice.id"));
		getLocationsForResourseResponse = locationInfo.getLocationsForResource_DL(resourceId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationsForResourseResponse);
		CCUtils.assertEquals(getLocationsForResourseResponse.getStatusCode(), 200);
		String locationId = getLocationsForResourseResponse.jsonPath().getString("locationId");
		locationsValidations.verifyResponseBodyForLocationAssociatedWithResourses(getLocationsForResourseResponse,
				locationId);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing resourceId, existing practiceId to get Locations For Resource  and verify status code")
	public void testGetLocationForNonExistingResourceIdAndExistingPracticeId() throws IOException {
		int resourceId = Integer.parseInt(testData.getProperty("non.exting.resource.id"));
		int practiceId = Integer.parseInt(testData.getProperty("exiting.practice.id"));
		getLocationsForResourseResponse = locationInfo.getLocationsForResource_DL(resourceId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationsForResourseResponse);
		CCUtils.assertEquals(getLocationsForResourseResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing resourceId, non-existing practiceId to get Locations For Resource  and verify status code")
	public void testGetLocationForExistingResourceIdAndNonExistingPracticeId() throws IOException {
		int resourceId = Integer.parseInt(testData.getProperty("api.resource.id"));
		int practiceId = Integer.parseInt(testData.getProperty("nonexiting.practice.id"));
		getLocationsForResourseResponse = locationInfo.getLocationsForResource_DL(resourceId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationsForResourseResponse);
		CCUtils.assertEquals(getLocationsForResourseResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing resourceId, non-existing practiceId to get Locations For Resource  and verify status code")
	public void testGetLocationForNonExistingResourceIdAndNonExistingPracticeId() throws IOException {
		int resourceId = Integer.parseInt(testData.getProperty("non.exting.resource.id"));
		int practiceId = Integer.parseInt(testData.getProperty("nonexiting.practice.id"));
		getLocationsForResourseResponse = locationInfo.getLocationsForResource_DL(resourceId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationsForResourseResponse);
		CCUtils.assertEquals(getLocationsForResourseResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing resourceId, practiceId to get Locations For Resource for Invalid url and verify error message")
	public void testGetLocationForInvalidUrl() throws IOException {
		int resourceId = Integer.parseInt(testData.getProperty("api.resource.id"));
		int practiceId = Integer.parseInt(testData.getProperty("exiting.practice.id"));
		getLocationsForResourseResponse = locationInfo.getLocationsForResourceForInvalidUrl_DL(resourceId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationsForResourseResponse);
		CCUtils.assertEquals(getLocationsForResourseResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getLocationsForResourseResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value resourceId & practiceId to get Locations  and verify error message")
	public void testGetLocationForNullValues() throws IOException {
		getLocationsForResourseResponse = locationInfo.getLocationsForResourceForNullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getLocationsForResourseResponse);
		CCUtils.assertEquals(getLocationsForResourseResponse.getStatusCode(), 400);
		validation.verifyErrorMessageIfClientIdIsNull(getLocationsForResourseResponse);
	}
}