//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetAppointmentTypesApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	Response getAppointmentTypesApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupQaServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing active appointmentTypeID and verify status code and response body")
	public void testGetAppointmentTypeFunctionalityWithExistingActiveAppointmentTypeId() throws IOException {
		logStep("Enter existing active appointmentTypeID");
		int appointmentTypeId = Integer.parseInt(testData.getProperty("existing.active.appointment.type.id"));
		getAppointmentTypesApiResponse = sharedInfo.getAppointmentTypes_BL(appointmentTypeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypesApiResponse);
		CCUtils.assertEquals(getAppointmentTypesApiResponse.getStatusCode(), 200);
		validation.verifySuccessResponseBodyForExistingAppointmentTypeId(getAppointmentTypesApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing inactive appointmentTypeID and verify status code and response body")
	public void testGetAppointmentTypeFunctionalityWithExistingInactiveAppointmentTypeId() throws IOException {
		logStep("Enter existing inactive appointmentTypeID");
		int appointmentTypeId = Integer.parseInt(testData.getProperty("existing.inactive.appointment.type.id"));
		getAppointmentTypesApiResponse = sharedInfo.getAppointmentTypes_BL(appointmentTypeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypesApiResponse);
		CCUtils.assertEquals(getAppointmentTypesApiResponse.getStatusCode(), 200);
		validation.verifySuccessResponseBodyForExistingAppointmentTypeId(getAppointmentTypesApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing appointmentTypeID and verify status code and response body")
	public void testGetAppointmentTypeFunctionalityWithNonExistingAppointmentTypeId() throws IOException {
		logStep("Enter non-existing appointmentTypeID");
		int appointmentTypeId = Integer.parseInt(testData.getProperty("non.existing.appointment.type.id"));
		getAppointmentTypesApiResponse = sharedInfo.getAppointmentTypes_BL(appointmentTypeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypesApiResponse);
		CCUtils.assertEquals(getAppointmentTypesApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseMessageForNonExistingAppointmentTypeId(getAppointmentTypesApiResponse,
				testData.getProperty("non.existing.appointment.type.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Make POST call instead of GET and verify response body")
	public void testErrorMessageForInvalidMethodCall() throws IOException {
		logStep("Enter existing appointmentTypeID");
		int appointmentTypeId = Integer.parseInt(testData.getProperty("valid.appointment.type.id"));
		getAppointmentTypesApiResponse = sharedInfo.getAppointmentTypesInvalidMethodCall_BL(appointmentTypeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypesApiResponse);
		CCUtils.assertEquals(getAppointmentTypesApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(getAppointmentTypesApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		logStep("Enter existing appointmentTypeID");
		int appointmentTypeId = Integer.parseInt(testData.getProperty("valid.appointment.type.id"));
		getAppointmentTypesApiResponse = sharedInfo.getAppointmentTypesInvalidUrl_BL(appointmentTypeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypesApiResponse);
		CCUtils.assertEquals(getAppointmentTypesApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getAppointmentTypesApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check response for null values in appointmentTypeID field")
	public void testErrorResponseCodeForNullValues() throws IOException {
		logStep("Enter existing appointmentTypeID");
		getAppointmentTypesApiResponse = sharedInfo.getAppointmentTypes_NullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypesApiResponse);
		CCUtils.assertEquals(getAppointmentTypesApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageIfClientIdIsNull(getAppointmentTypesApiResponse);
	}
}
