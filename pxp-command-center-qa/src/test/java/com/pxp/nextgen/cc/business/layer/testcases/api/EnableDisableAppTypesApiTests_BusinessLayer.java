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

public class EnableDisableAppTypesApiTests_BusinessLayer extends BaseRest {
	private Validations validation;
	private AppointmentTypeInfo appointmentTypeInfo;
	private CreateClientHierarchy_BL clientHierObj;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, appointmentTypeId, ehrIdentifier,
			pmIdentifier;
	String name, externalId, duration;
	Response createAppointmentTypeApiResponse, enableDisableAppointmentApiResponse;

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
		name = CCUtils.generateAlphaNumericString(10);
		externalId = CCUtils.generateAlphaNumericString(4);
		duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_BL(practiceId, testData.getProperty("default.solution.id"), name, externalId,
				duration, categoryId, name, externalId);
		CCUtils.assertEquals(createAppointmentTypeApiResponse.getStatusCode(), 200);
		appointmentTypeId = createAppointmentTypeApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable and disable Practiceid for existing appointmentTypeId and verify response code")
	public void testResponseCodeToAddAndRemoveExistingPracticeIdForAppointmentId() throws IOException {
		logStep("Disable Practiceid for existing appointmentTypeId");
		enableDisableAppointmentApiResponse = appointmentTypeInfo.enableDisableAppType_BL(practiceId, appointmentTypeId,
				"false");
		CCUtils.assertEquals(enableDisableAppointmentApiResponse.getStatusCode(), 200);
		logStep("Enable Practiceid for existing appointmentTypeId");
		enableDisableAppointmentApiResponse = appointmentTypeInfo.enableDisableAppType_BL(practiceId, appointmentTypeId,
				"true");
		CCUtils.assertEquals(enableDisableAppointmentApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing PracticeId and non-existing appointmentTypeId and verify response body")
	public void testErrorMessagesForNonexistingAppointmentTypeId() throws IOException {
		logStep("Enter existing PracticeId and non-existing appointmentTypeId");
		int appointmentTypeId = Integer.parseInt(testData.getProperty("non.existing.appointment.type.id"));
		enableDisableAppointmentApiResponse = appointmentTypeInfo.enableDisableAppType_BL(practiceId, appointmentTypeId,
				"true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(enableDisableAppointmentApiResponse);
		CCUtils.assertEquals(enableDisableAppointmentApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(enableDisableAppointmentApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing PracticeId and existing AppointmentTypeId and verify response body")
	public void testErrorMessagesForNonExistingPracticeIdAndExistingAppointmentTypeId() throws IOException {
		logStep("Enter non-existing PracticeId and existing AppointmentTypeId");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		enableDisableAppointmentApiResponse = appointmentTypeInfo.enableDisableAppType_BL(practiceId, appointmentTypeId,
				"true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(enableDisableAppointmentApiResponse);
		CCUtils.assertEquals(enableDisableAppointmentApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(enableDisableAppointmentApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing PracticeId and non-existing AppointmentTypeId and verify response body")
	public void testErrorMessagesForNonExistingpracticeIdAndNonExistingAppointmentTypeId() throws IOException {
		logStep("Enter non-existing PracticeId and non-existing AppointmentTypeId");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		int appointmentTypeId = Integer.parseInt(testData.getProperty("non.existing.appointment.type.id"));
		enableDisableAppointmentApiResponse = appointmentTypeInfo.enableDisableAppType_BL(practiceId, appointmentTypeId,
				"true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(enableDisableAppointmentApiResponse);
		CCUtils.assertEquals(enableDisableAppointmentApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(enableDisableAppointmentApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable existing Practiceid for existing appointmentTypeId With Active Status As Null and verify response body")
	public void testToAddPracticeIdForAppointmentIdWithActiveStatusAsNull() throws IOException {
		logStep("Disable Practiceid for existing appointmentTypeId");
		enableDisableAppointmentApiResponse = appointmentTypeInfo.enableDisableAppType_BL(practiceId, appointmentTypeId,
				" ");
		logStep("Validating response information");
		CCUtils.assertEquals(enableDisableAppointmentApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Practiceid, AppointmentTypeId as Null and verify response code")
	public void testResponseCodeToAddPracticeWithAppointmentIdAsNull() throws IOException {
		logStep("Enter Practiceid, AppointmentTypeId as null");
		enableDisableAppointmentApiResponse = appointmentTypeInfo.enableDisableAppTypes_NullValues_BL();
		logStep("Validating response information");
		CCUtils.assertEquals(enableDisableAppointmentApiResponse.getStatusCode(), 400);
	}
}
