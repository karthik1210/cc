//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.AppointmentTypeInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.AppointmentTypeDetails;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;

public class GetAppointmentTypesApiTests_BusinessLayer extends BaseRest {
	List<AppointmentTypeDetails> appointmentTypeList;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, appointmentTypeId;
	String name, externalId, duration;
	Response createProviderApiResponse, createResourceApiResponse, createAppointmentTypeApiResponse,
			getAppointmentTypesApiResponse;
	private SharedInfo sharedInfo;
	private AppointmentTypeInfo appointmentTypeInfo;
	private CreateClientHierarchy_BL clientHierObj;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing active appointmentTypeID and verify status code and response body")
	public void testGetAppointmentTypeFunctionalityWithExistingActiveAppointmentTypeId() throws IOException {
		logStep("Enter existing active appointmentTypeID");
		getAppointmentTypesApiResponse = sharedInfo.getAppointmentTypes_BL(appointmentTypeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypesApiResponse);
		CCUtils.assertEquals(getAppointmentTypesApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing appointmentTypeID and verify status code and response body")
	public void testGetAppointmentTypeFunctionalityWithNonExistingAppointmentTypeId() throws IOException {
		logStep("Enter non-existing appointmentTypeID");
		int appointmentTypeId = Integer.parseInt(testData.getProperty("non.existing.appointment.type.id"));
		getAppointmentTypesApiResponse = sharedInfo.getAppointmentTypes_BL(appointmentTypeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypesApiResponse);
		CCUtils.assertEquals(getAppointmentTypesApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check response for null values in appointmentTypeID field")
	public void testErrorResponseCodeForNullValues() throws IOException {
		logStep("Enter existing appointmentTypeID");
		getAppointmentTypesApiResponse = sharedInfo.getAppointmentTypes_NullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getAppointmentTypesApiResponse);
		CCUtils.assertEquals(getAppointmentTypesApiResponse.getStatusCode(), 400);
	}
}
