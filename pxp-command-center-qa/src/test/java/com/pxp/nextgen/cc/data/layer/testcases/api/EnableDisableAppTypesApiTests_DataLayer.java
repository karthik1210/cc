//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.AppointmentTypeInfo;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class EnableDisableAppTypesApiTests_DataLayer extends BaseRest {
	private Validations validation;
	private ClientInfo clientInfo;
	private AppointmentTypeInfo appointmentTypeInfo;
	private OrgInfo orgInfo;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createResourceApiResponse, enableDisableAppointmentApiResponse,
			createAppointmentTypeApiResponse;
	int clientId, systemId, enterpriseId, practiceId, appointmentTypeId;
	String clientName, clientSapNumber, externalId, firstName, lastName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
		appointmentTypeInfo = new AppointmentTypeInfo();
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
		clientId = createClientApiResponse.jsonPath().get("clientId");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
				testData.getProperty("api.system.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		systemId = createSystemApiResponse.jsonPath().get("orgId");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.displayname"), testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		enterpriseId = createEnterpriseApiResponse.jsonPath().get("orgId");
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
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
	

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable and disable Practiceid for existing appointmentTypeId and verify response code")
	public void testResponseCodeToAddAndRemoveExistingPracticeIdForAppointmentId() throws IOException {
		logStep("Disable Practiceid for existing appointmentTypeId");
		enableDisableAppointmentApiResponse = appointmentTypeInfo.enableDisableAppType_DL(practiceId, appointmentTypeId,
				"false");
		CCUtils.assertEquals(enableDisableAppointmentApiResponse.getStatusCode(), 200);
		logStep("Enable Practiceid for existing appointmentTypeId");
		enableDisableAppointmentApiResponse = appointmentTypeInfo.enableDisableAppType_DL(practiceId, appointmentTypeId,
				"true");
		CCUtils.assertEquals(enableDisableAppointmentApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing PracticeId and non-existing appointmentTypeId and verify response body")
	public void testErrorMessagesForNonexistingAppointmentTypeIdAndExistingPracticeId() throws IOException {
		logStep("Enter existing PracticeId and non-existing appointmentTypeId");
		int appointmentTypeId = Integer.parseInt(testData.getProperty("non.existing.appointment.type.id"));
		enableDisableAppointmentApiResponse = appointmentTypeInfo.enableDisableAppType_DL(practiceId, appointmentTypeId,
				"true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(enableDisableAppointmentApiResponse);
		CCUtils.assertEquals(enableDisableAppointmentApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForNonExistingField(enableDisableAppointmentApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing PracticeId and existing AppointmentTypeId and verify response body")
	public void testErrorMessagesForNonExistingPracticeIdAndExistingAppointmentTypeId() throws IOException {
		logStep("Enter non-existing PracticeId and existing AppointmentTypeId");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		enableDisableAppointmentApiResponse = appointmentTypeInfo.enableDisableAppType_DL(practiceId, appointmentTypeId,
				"true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(enableDisableAppointmentApiResponse);
		CCUtils.assertEquals(enableDisableAppointmentApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForNonExistingField(enableDisableAppointmentApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing PracticeId and non-existing AppointmentTypeId and verify response body")
	public void testErrorMessagesForNonExistingpracticeIdAndNonExistingAppointmentTypeId() throws IOException {
		logStep("Enter non-existing PracticeId and non-existing AppointmentTypeId");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		int appointmentTypeId = Integer.parseInt(testData.getProperty("non.existing.appointment.type.id"));
		enableDisableAppointmentApiResponse = appointmentTypeInfo.enableDisableAppType_DL(practiceId, appointmentTypeId,
				"true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(enableDisableAppointmentApiResponse);
		CCUtils.assertEquals(enableDisableAppointmentApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForNonExistingField(enableDisableAppointmentApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable existing Practiceid for existing appointmentTypeId With Active Status As Null and verify response body")
	public void testToAddPracticeIdForAppointmentIdWithActiveStatusAsNull() throws IOException {
		logStep("Disable Practiceid for existing appointmentTypeId");
		enableDisableAppointmentApiResponse = appointmentTypeInfo.enableDisableAppType_DL(practiceId, appointmentTypeId,
				" ");
		logStep("Validating response information");
		CCUtils.assertEquals(enableDisableAppointmentApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Practiceid, AppointmentTypeId as Null and verify response code")
	public void testResponseCodeToAddPracticeWithAppointmentIdAsNull() throws IOException {
		logStep("Enter Practiceid, AppointmentTypeId as null");
		enableDisableAppointmentApiResponse = appointmentTypeInfo.enableDisableAppTypes_NullValues_DL();
		logStep("Validating response information");
		CCUtils.assertEquals(enableDisableAppointmentApiResponse.getStatusCode(), 400);
	}
	
}
