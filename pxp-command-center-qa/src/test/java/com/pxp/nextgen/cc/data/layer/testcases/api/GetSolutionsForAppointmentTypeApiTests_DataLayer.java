//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.AppointmentTypeInfo;
import com.pxp.cc.helpers.PracticeAppointmentTypeInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.PracticeAppointmentTypeValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetSolutionsForAppointmentTypeApiTests_DataLayer extends BaseRest {
	private PracticeAppointmentTypeInfo practiceAppointmentTypeInfo;
	private Validations validations;
	private AppointmentTypeInfo appointmentTypeInfo;
	private PracticeAppointmentTypeValidations practiceAppointmentTypeValidations;
	Response createAppointmentTypeApiResponse;
	Response fetchSolutionsForAppointmentTypeResponse;
	int clientId, systemId, enterpriseId, practiceId, solutionId, appointmentTypeId, practiceIdOne;
	Response updateSolutionsApiResponse, updateSolutionForResources, getSolutionForResources, createProviderApiResponse,
			createResourceApiResponse, fetchSolutionsForResources;
	private CreateClientHierarchy_DL clientHierObj;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		practiceAppointmentTypeInfo = new PracticeAppointmentTypeInfo();
		validations = new Validations();
		appointmentTypeInfo = new AppointmentTypeInfo();
		practiceAppointmentTypeValidations = new PracticeAppointmentTypeValidations();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		practiceIdOne = clientHierObj.createPractice_DL(enterpriseId);
		logStep("Create new AppointmentType");
		String name = CCUtils.generateAlphaNumericString(10);
		String externalIdApp = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_DL(practiceId,
				testData.getProperty("default.solution.id"), name, externalIdApp, duration, categoryId, name,
				externalIdApp);
		CCUtils.assertEquals(createAppointmentTypeApiResponse.getStatusCode(), 200);
		appointmentTypeId = createAppointmentTypeApiResponse.jsonPath().getInt("id");
		logStep("Enable existing solution id for existing practiceid and AppointmenTypeId");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = appointmentTypeInfo.enableOrDisableSolutionsForAppTypes_DL(appointmentTypeId,
				practiceId, solutionId, true);
		logStep("Validating response information");
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 207);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch all the solutions of appointment type of a practice")
	public void testGetSolutionsForAppointmentType() throws IOException {
		fetchSolutionsForAppointmentTypeResponse = practiceAppointmentTypeInfo
				.getSolutionsForAppointmentType_DL(practiceId, appointmentTypeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchSolutionsForAppointmentTypeResponse.getStatusCode(), 200);
		practiceAppointmentTypeValidations
				.verifyNoOfSolutionsFetchedSolutionNameandId(fetchSolutionsForAppointmentTypeResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching solutions of existing practice Id and appointment Type Id but no solutions is associated")
	public void testResponseCodeForExistingPracticeIdandAppointmentTypeOfNoAssociatedSolutions() throws IOException {
		fetchSolutionsForAppointmentTypeResponse = practiceAppointmentTypeInfo
				.getSolutionsForAppointmentType_DL(practiceIdOne, appointmentTypeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchSolutionsForAppointmentTypeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching solution of existing practice Id and non-existing appointment Type Id")
	public void testErrorMessageForExistingPracticeIdandNonexistingAppointmentTypeId() throws IOException {
		fetchSolutionsForAppointmentTypeResponse = practiceAppointmentTypeInfo.getSolutionsForAppointmentType_DL(
				practiceId,
				Integer.parseInt(testData.getProperty("non.existing.solutions.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForAppointmentTypeResponse);
		CCUtils.assertEquals(fetchSolutionsForAppointmentTypeResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching solutions of non-existing practice Id and existing appointment Type Id")
	public void testErrorMessageForNonExistingPracticeIdandExistingAppointmentTypeId() throws IOException {
		fetchSolutionsForAppointmentTypeResponse = practiceAppointmentTypeInfo.getSolutionsForAppointmentType_DL(
				Integer.parseInt(testData.getProperty("non.existing.solutions.practice.id")),
				appointmentTypeId);
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
