//Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.PracticeAppointmentTypeInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.PracticeAppointmentTypeValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetSolutionsForAppointmentTypeApiTests_DataLayer extends BaseRest {

	private PracticeAppointmentTypeInfo practiceAppointmentTypeInfo;
	private Validations validations;
	private PracticeAppointmentTypeValidations practiceAppointmentTypeValidations;
	Response fetchSolutionsForAppointmentTypeResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		practiceAppointmentTypeInfo = new PracticeAppointmentTypeInfo();
		validations = new Validations();
		practiceAppointmentTypeValidations = new PracticeAppointmentTypeValidations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch all the solutions of appointment type of a practice")
	public void testGetSolutionsForAppointmentType() throws IOException {
		fetchSolutionsForAppointmentTypeResponse = practiceAppointmentTypeInfo.getSolutionsForAppointmentType_DL(
				Integer.parseInt(testData.getProperty("get.solutions.practice.id")),
				Integer.parseInt(testData.getProperty("get.solutions.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchSolutionsForAppointmentTypeResponse.getStatusCode(), 200);
		practiceAppointmentTypeValidations
				.verifyNoOfSolutionsFetchedSolutionNameandId(fetchSolutionsForAppointmentTypeResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching solutions of existing practice Id and appointment Type Id but no resource is associated")
	public void testResponseCodeForExistingPracticeIdandAppointmentTypeOfNoAssociatedSolutions() throws IOException {
		fetchSolutionsForAppointmentTypeResponse = practiceAppointmentTypeInfo.getSolutionsForAppointmentType_DL(
				Integer.parseInt(testData.getProperty("no.solutions.practice.id")),
				Integer.parseInt(testData.getProperty("no.solutions.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchSolutionsForAppointmentTypeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching solution of existing practice Id and non-existing appointment Type Id")
	public void testErrorMessageForExistingPracticeIdandNonexistingAppointmentTypeId() throws IOException {
		fetchSolutionsForAppointmentTypeResponse = practiceAppointmentTypeInfo.getSolutionsForAppointmentType_DL(
				Integer.parseInt(testData.getProperty("get.solutions.practice.id")),
				Integer.parseInt(testData.getProperty("non.existing.solutions.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchSolutionsForAppointmentTypeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching solutions of non-existing practice Id and existing appointment Type Id")
	public void testErrorMessageForNonExistingPracticeIdandExistingAppointmentTypeId() throws IOException {
		fetchSolutionsForAppointmentTypeResponse = practiceAppointmentTypeInfo.getSolutionsForAppointmentType_DL(
				Integer.parseInt(testData.getProperty("non.existing.solutions.practice.id")),
				Integer.parseInt(testData.getProperty("get.solutions.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchSolutionsForAppointmentTypeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching solutions of both non-existing practice Id and appointment Type Id")
	public void testErrorMessageForBothNonExistingPracticeIdandAppointmentTypeId() throws IOException {
		fetchSolutionsForAppointmentTypeResponse = practiceAppointmentTypeInfo.getSolutionsForAppointmentType_DL(
				Integer.parseInt(testData.getProperty("non.existing.solutions.practice.id")),
				Integer.parseInt(testData.getProperty("non.existing.solutions.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchSolutionsForAppointmentTypeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when fetching solutions with incorrect HTTP method")
	public void testErrorMessageForInvalidMethodCallWhenFetchingSolutionsWithHTTPMethodPOST() throws IOException {
		fetchSolutionsForAppointmentTypeResponse = practiceAppointmentTypeInfo
				.getSolutionsForAppointmentType_DL_InvalidMethodCall(
						Integer.parseInt(testData.getProperty("get.solutions.practice.id")),
						Integer.parseInt(testData.getProperty("get.solutions.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchSolutionsForAppointmentTypeResponse.getStatusCode(), 405);
		validations.verifyErrorMessageInResponseForInvalidMethodCall(fetchSolutionsForAppointmentTypeResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when fetching solutions with invalid url")
	public void testErrorMessageForInvalidUrlWhenFetchingSolutions() throws IOException {
		fetchSolutionsForAppointmentTypeResponse = practiceAppointmentTypeInfo
				.getSolutionsForAppointmentType_DL_InvalidUrl(
						Integer.parseInt(testData.getProperty("get.solutions.practice.id")),
						Integer.parseInt(testData.getProperty("get.solutions.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchSolutionsForAppointmentTypeResponse.getStatusCode(), 404);
		validations.verifyErrorInResponseForInvalidUrl(fetchSolutionsForAppointmentTypeResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when fetching solutions with practice id and null and appointment Type Id as null")
	public void testErrorMessageForNullValuesInPracticeIdAndAppointmentTypeId() throws IOException {
		fetchSolutionsForAppointmentTypeResponse = practiceAppointmentTypeInfo
				.getSolutionsForAppointmentTypeDL_NullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchSolutionsForAppointmentTypeResponse.getStatusCode(), 400);
		validations.verifyErrorMessageIfClientIdIsNull(fetchSolutionsForAppointmentTypeResponse);
	}
}
