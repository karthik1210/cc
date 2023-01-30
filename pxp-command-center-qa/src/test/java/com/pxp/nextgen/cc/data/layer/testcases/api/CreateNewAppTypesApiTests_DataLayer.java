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

public class CreateNewAppTypesApiTests_DataLayer extends BaseRest {
	private Validations validation;	
	private AppointmentTypeInfo appointmentTypeInfo;
	private CreateClientHierarchy_DL clientHierObj;
	Response createClientApiResponse, createSystemApiResponse, createPracticeApiResponse, createEnterpriseApiResponse,
			createAppointmentTypeApiResponse;
	int clientId, systemId, enterpriseId, practiceId, appointmentTypeId;
	String clientName, clientSapNumber, externalId, firstName, lastName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		validation = new Validations();
		appointmentTypeInfo = new AppointmentTypeInfo();
		clientHierObj = new CreateClientHierarchy_DL();
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create new AppointmentType and verify response body")
	public void testToCreateAppointmentTypes() throws IOException {
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
		validation.verifyCreateAppTypeResponseBody(createAppointmentTypeApiResponse, appointmentTypeId, name);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create new AppointmentType for invalid practiceId and verify response body")
	public void testToCreateAppointmentTypesForNonExistingPracticeId() throws IOException {
		logStep("Create new AppointmentType");
		String name = CCUtils.generateAlphaNumericString(10);
		String externalIdApp = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_DL(practiceId,
				testData.getProperty("default.solution.id"), name, externalIdApp, duration, categoryId, name,
				externalIdApp);
		CCUtils.assertEquals(createAppointmentTypeApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidOrgId(createAppointmentTypeApiResponse, testData.getProperty("non.existing.practice.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create new AppointmentType for blank responseBody and verify response body")
	public void testToCreateAppointmentTypesForBlankResponseBody() throws IOException {
		logStep("Create new AppointmentType for null responseBody and verify response body");
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_DL(practiceId, "", "", "", "", "",
				"", "");
		CCUtils.assertEquals(createAppointmentTypeApiResponse.getStatusCode(), 400);
	}
}
