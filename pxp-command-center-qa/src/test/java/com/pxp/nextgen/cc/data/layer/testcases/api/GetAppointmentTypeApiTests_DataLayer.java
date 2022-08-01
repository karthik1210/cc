//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.ResourceDetails;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetAppointmentTypeApiTests_DataLayer extends BaseRest {

	private SharedInfo sharedInfo;
	private Validations validation;
	Response getAppointmentTypeApiResponse;
	List<ResourceDetails> resourceList;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get the Appointment type details with the valid AppointmentypeId and verify response body")
	public void testGetAppointmentTypeFunctionalityUsingAppointmentTypeId() throws IOException {
		logStep("Add valid resource id for appointment type");
		getAppointmentTypeApiResponse = sharedInfo
				.getAppointmentType_DL(Integer.parseInt(testData.getProperty("valid.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypeApiResponse);
		CCUtils.assertEquals(getAppointmentTypeApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get the Appointment type details with the Invalid AppointmentypeId and verify response body")
	public void testGetAppointmentTypeFunctionalityUsingInvalidAppointmentTypeId() throws IOException {
		getAppointmentTypeApiResponse = sharedInfo
				.getAppointmentType_DL(Integer.parseInt(testData.getProperty("non.existing.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypeApiResponse);
		CCUtils.assertEquals(getAppointmentTypeApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Make a POST call instead of PUT and verify response body")
	public void testErrorMessageForInvalidMethodCall() throws IOException {
		getAppointmentTypeApiResponse = sharedInfo.invalidMethodCallForGetAppointmentType_DL(
				Integer.parseInt(testData.getProperty("valid.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypeApiResponse);
		CCUtils.assertEquals(getAppointmentTypeApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(getAppointmentTypeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		getAppointmentTypeApiResponse = sharedInfo.getAppointmentTypeUsingInvalidUrl_DL(
				Integer.parseInt(testData.getProperty("valid.appointment.type.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypeApiResponse);
		CCUtils.assertEquals(getAppointmentTypeApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getAppointmentTypeApiResponse);
	}

}
