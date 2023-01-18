//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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

public class EnableDisableSolutionsForAppTypesApiTests_BusinessLayer extends BaseRest {
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

		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber, "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId(),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		clientId = createClientApiResponse.jsonPath().getInt("clientId");
		createSystemApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", testData.getProperty("api.system.active"), "", clientId,
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		createEnterpriseApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemId,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		createPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		logStep("Create new AppointmentType");
		String name = CCUtils.generateAlphaNumericString(10);
		String externalId = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_BL(practiceId, testData.getProperty("default.solution.id"), name, externalId,
				duration, categoryId, name, externalId);
		CCUtils.assertEquals(createAppointmentTypeApiResponse.getStatusCode(), 200);
		validation.verifyCreateLocationApiResponseBody(createAppointmentTypeApiResponse, name);
		appointmentTypeId = createAppointmentTypeApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable existing solution id for existing practiceid and appointmenTypeId and verify response body")
	public void testToEnableExistingSolutionIdForExistingAppTypeId() throws IOException {
		logStep("Enable existing solution id for existing practiceid and AppointmenTypeId");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = appointmentTypeInfo.enableOrDisableSolutionsForAppTypes_BL(appointmentTypeId,
				practiceId, solutionId, true);
		logStep("Validating response information");
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 207);
		validation.verifyResponseBodyOfAppTypesSolutions(updateSolutionsApiResponse, solutionId, "SUCCESS");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Disable existing solution id for existing practiceid and appointmenTypeId and verify response body")
	public void testToDisableExistingSolutionIdForExistingAppTypeId() throws IOException {
		logStep("Disable existing solution id for existing practiceid and appointmenTypeId");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = appointmentTypeInfo.enableOrDisableSolutionsForAppTypes_BL(appointmentTypeId,
				practiceId, solutionId, false);
		logStep("Validating response information");
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 207);
		validation.verifyResponseBodyOfAppTypesSolutions(updateSolutionsApiResponse, solutionId, "SUCCESS");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting solution id for existing appointmenTypeId and practiceId  and verify error message")
	public void testErrorMessageForNonExistingSolutionIdForExistingAppTypeId() throws IOException {
		logStep("Enter nonexisting solution id for existing appointmenTypeId and practiceId");
		int solutionId = Integer.parseInt(testData.getProperty("api.nonexisting.id"));
		updateSolutionsApiResponse = appointmentTypeInfo.enableOrDisableSolutionsForAppTypes_BL(appointmentTypeId,
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
		updateSolutionsApiResponse = appointmentTypeInfo.enableOrDisableSolutionsForAppTypes_BL(appointmentTypeId,
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
		updateSolutionsApiResponse = appointmentTypeInfo.enableOrDisableSolutionsForAppTypes_BL(appointmentTypeId,
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
		updateSolutionsApiResponse = appointmentTypeInfo.enableOrDisableSolutionsForAppTypes_BL(appointmentTypeId,
				practiceId, solutionId, true);
		logStep("Validating response information");
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForNonExistingField(updateSolutionsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter practiceid, appointmentTypeId as null and verify response code")
	public void testResponseCodeForNullInputInAllFields() throws NullPointerException, IOException {
		logStep("Enter practiceid, appointmentTypeId as null");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = appointmentTypeInfo.enableOrDisableSolutionsForAppTypes_NullValues_BL(solutionId, true);
		logStep("Validating response information");
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 400);
	}
	
}
