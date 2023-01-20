//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
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

public class EnableDisableSolutionsForAppTypesApiTests_DataLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private AppointmentTypeInfo appointmentTypeInfo;
	private Validations validation;
	Response createClientApiResponse, createSystemApiResponse, createPracticeApiResponse, createEnterpriseApiResponse,
			createAppointmentTypeApiResponse, updateSolutionsApiResponse;
	int clientId, systemId, enterpriseId, practiceId, appointmentTypeId, solutionId;
	String clientName, clientSapNumber, externalId, firstName, lastName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
		appointmentTypeInfo = new AppointmentTypeInfo();
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
		validation.verifyErrorMessageForNonExistingField(updateSolutionsApiResponse);
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
		validation.verifyErrorMessageForNonExistingField(updateSolutionsApiResponse);
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
		validation.verifyErrorMessageForNonExistingField(updateSolutionsApiResponse);
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
