//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.AppointmentTypeInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetSolutionsForAppTypesApiTests_BusinessLayer extends BaseRest {
	private AppointmentTypeInfo appointmentTypeInfo;
	private Validations validation;
	private CreateClientHierarchy_BL clientHierObj;
	Response updateSolutionsForAppTypeApiResponse, getSolutionsForAppTypeApiResponse, createAppointmentTypeApiResponse;
	int clientId, systemId, enterpriseId, practiceId, appointmentTypeId;
	String externalId, firstName, lastName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		validation = new Validations();
		appointmentTypeInfo = new AppointmentTypeInfo();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		logStep("Create new AppointmentType");
		String name = CCUtils.generateAlphaNumericString(10);
		String externalId = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_BL(practiceId,
				testData.getProperty("default.solution.id"), name, externalId, duration, categoryId, name, externalId);
		CCUtils.assertEquals(createAppointmentTypeApiResponse.getStatusCode(), 200);
		validation.verifyCreateLocationApiResponseBody(createAppointmentTypeApiResponse, name);
		appointmentTypeId = createAppointmentTypeApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceid, existing appointmenTypeId to get Solutions and verify response code")
	public void testResponseCodeToGetSolutionForAppointmentType() throws NullPointerException, IOException {
		logStep("Enter existing practiceid, existing appointmenTypeId");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsForAppTypeApiResponse = appointmentTypeInfo
				.enableOrDisableSolutionsForAppTypes_BL(appointmentTypeId, practiceId, solutionId, true);
		logStep("Get solution for AppointmentType");
		getSolutionsForAppTypeApiResponse = appointmentTypeInfo.getSolutionsForAppTypes_BL(appointmentTypeId,
				practiceId);
		logStep("Validating response information");
		CCUtils.assertEquals(getSolutionsForAppTypeApiResponse.getStatusCode(), 200);
		validation.verifyNoOfSolutionsFetchedSolutionNameandId(getSolutionsForAppTypeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceid, nonexisting appointmentTypeId to get Solutions and verify response code")
	public void testResponseCodeToGetSolutionForAppointmentTypeForExistingPracticeIdAndNonExistingAppointTypeId()
			throws NullPointerException, IOException {
		logStep("Enter existing practiceid, nonexisting appointmentTypeId to get Solutions");
		int appointmentTypeId = Integer.parseInt(testData.getProperty("non.existing.appointment.type.id"));
		getSolutionsForAppTypeApiResponse = appointmentTypeInfo.getSolutionsForAppTypes_BL(appointmentTypeId,
				practiceId);
		logStep("Validating response information");
		CCUtils.assertEquals(getSolutionsForAppTypeApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting practiceid, existing appointmentTypeId to get Solutions and verify response code")
	public void testResponseCodeToToggleValidSolutionStatusForExistingAppointmentTypeIdIdAndNonExistingPracticeId()
			throws NullPointerException, IOException {
		logStep("Enter nonexisting practiceid, existing appointmentTypeId");
		int practiceId = Integer.parseInt(testData.getProperty("api.nonexisting.practiceId"));
		getSolutionsForAppTypeApiResponse = appointmentTypeInfo.getSolutionsForAppTypes_BL(appointmentTypeId,
				practiceId);
		logStep("Validating response information");
		CCUtils.assertEquals(getSolutionsForAppTypeApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting practiceid, nonexisting appointmentTypeId to get Solutions and verify response code")
	public void testResponseCodeToToggleValidSolutionStatusForNonExistingAppointmentTypeIdIdAndExistingPracticeId()
			throws NullPointerException, IOException {
		logStep("Enter nonexisting practiceid, nonexisting appointmentTypeId");
		int practiceId = Integer.parseInt(testData.getProperty("api.nonexisting.practiceId"));
		int appointmentTypeId = Integer.parseInt(testData.getProperty("non.existing.appointment.type.id"));
		getSolutionsForAppTypeApiResponse = appointmentTypeInfo.getSolutionsForAppTypes_BL(appointmentTypeId,
				practiceId);
		logStep("Validating response information");
		CCUtils.assertEquals(getSolutionsForAppTypeApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter practiceid, appointmentTypeId as null and verify response code")
	public void testResponseCodeForNullInputInAllFields() throws NullPointerException, IOException {
		logStep("Enter practiceid, appointmentTypeId as null");
		getSolutionsForAppTypeApiResponse = appointmentTypeInfo.getSolutionsForAppTypesNullValues_BL();
		logStep("Validating response information");
		CCUtils.assertEquals(getSolutionsForAppTypeApiResponse.getStatusCode(), 400);
	}

}
