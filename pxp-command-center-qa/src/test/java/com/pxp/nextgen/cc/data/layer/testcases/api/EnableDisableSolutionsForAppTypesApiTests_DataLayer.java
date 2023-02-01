//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.AppointmentTypeInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class EnableDisableSolutionsForAppTypesApiTests_DataLayer extends BaseRest {
	private AppointmentTypeInfo appointmentTypeInfo;
	private Validations validation;
	private CreateClientHierarchy_DL clientHierObj;
	Response createAppointmentTypeApiResponse, updateSolutionsApiResponse;
	int clientId, systemId, enterpriseId, practiceId, appointmentTypeId, solutionId;
	String externalId, firstName, lastName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		validation = new Validations();
		appointmentTypeInfo = new AppointmentTypeInfo();
		setupServiceRequestSpecificationBuilderForDataLayer();
		appointmentTypeInfo = new AppointmentTypeInfo();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		logStep("Create new AppointmentType");
		String name = CCUtils.generateAlphaNumericString(10);
		String externalIdApp = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_DL(practiceId, testData.getProperty("default.solution.id"), name, externalIdApp,
				duration, categoryId, name, externalIdApp);
		CCUtils.assertEquals(createAppointmentTypeApiResponse.getStatusCode(), 200);
		appointmentTypeId = createAppointmentTypeApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable existing solution id for existing practiceid and appointmenTypeId and verify response body")
	public void testToEnableExistingSolutionIdForExistingAppTypeId() throws IOException {
		logStep("Enable existing solution id for existing practiceid and AppointmenTypeId");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = appointmentTypeInfo.enableOrDisableSolutionsForAppTypes_DL(appointmentTypeId,
				practiceId, solutionId, true);
		logStep("Validating response information");
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 207);
		validation.verifyResponseBodyOfAppTypesSolutions(updateSolutionsApiResponse, solutionId, "SUCCESS");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Disable existing solution id for existing practiceid and appointmenTypeId and verify response body")
	public void testToDisableExistingSolutionIdForExistingAppTypeId() throws IOException {
		logStep("Disable existing solution id for existing practiceid and appointmenTypeId");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = appointmentTypeInfo.enableOrDisableSolutionsForAppTypes_DL(appointmentTypeId,
				practiceId, solutionId, false);
		logStep("Validating response information");
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 207);
		validation.verifyResponseBodyOfAppTypesSolutions(updateSolutionsApiResponse, solutionId, "SUCCESS");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting solution id for existing appointmenTypeId and practiceId  and verify error message")
	public void testErrorMessageForNonExistingSolutionIdForExistingAppTypeId() throws IOException {
		logStep("Enter nonexisting solution id for existing appointmenTypeId and practiceId");
		int solutionId = Integer.parseInt(testData.getProperty("api.nonexisting.id"));
		updateSolutionsApiResponse = appointmentTypeInfo.enableOrDisableSolutionsForAppTypes_DL(appointmentTypeId,
				practiceId, solutionId, true);
		logStep("Validating response information");
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 207);
		validation.verifyResponseBodyOfAppTypesSolutions(updateSolutionsApiResponse, solutionId, "ERROR");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting practiceid and existing appointmentTypeId with valid details in request body and verify response code")
	public void testErrorMessageForNonExistingPracticeAndExistingAppTypeId() throws IOException {
		logStep("Enter nonexisting practiceid and existing appointmentTypeId");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = appointmentTypeInfo.enableOrDisableSolutionsForAppTypes_DL(appointmentTypeId,
				practiceId, solutionId, true);
		logStep("Validating response information");
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateSolutionsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceid and nonexisting appointmentTypeId with valid details in request body and verify response code")
	public void testErrorMessageForExistingPracticeAndNonExistingAppTypeId() throws IOException {
		logStep("Enter existing practiceid and nonexisting appointmentTypeId");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.appointment.type.id"));
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = appointmentTypeInfo.enableOrDisableSolutionsForAppTypes_DL(appointmentTypeId,
				practiceId, solutionId, true);
		logStep("Validating response information");
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateSolutionsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting practiceid and nonexisting appointmentTypeId with valid details in request body and verify response code")
	public void testErrorMessageForNonExistingPracticeAndNonExistingAppTypeId() throws IOException {
		logStep("Enter nonexisting practiceid and nonexisting appointmentTypeId");
		int practiceId = Integer.parseInt(testData.getProperty("api.nonexisting.practiceId"));
		int appointmentTypeId = Integer.parseInt(testData.getProperty("non.existing.appointment.type.id"));
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = appointmentTypeInfo.enableOrDisableSolutionsForAppTypes_DL(appointmentTypeId,
				practiceId, solutionId, true);
		logStep("Validating response information");
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateSolutionsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter practiceid, appointmentTypeId as null and verify response code")
	public void testResponseCodeForNullInputInAllFields() throws NullPointerException, IOException {
		logStep("Enter practiceid, appointmentTypeId as null");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = appointmentTypeInfo.enableOrDisableSolutionsForAppTypes_NullValues_DL(solutionId, true);
		logStep("Validating response information");
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 400);
	}
	
}
