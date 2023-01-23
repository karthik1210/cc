//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.AppointmentTypeInfo;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class SaveAppTypesDisplayInfoApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private ClientInfo clientInfo;
	private Validations validation;
	private OrgInfo orgInfo;
	private AppointmentTypeInfo appointmentTypeInfo;
	int clientId, systemId, enterpriseId, practiceId, appointmentTypeId;
	Response createClientApiResponse, createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			createAppointmentTypeApiResponse, saveAppTypesDisplayInfoApiResponse;
	String clientName, clientSapNumber;

	@BeforeClass
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid request payload details with existing appointmentTypeId, existing practiceId and verify response body")
	public void testSaveAppTypesInfoWithValidDetails() throws IOException {
		logStep("Enter valid request payload details with existing appointmentTypeId, existing practiceId");
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		saveAppTypesDisplayInfoApiResponse = sharedInfo.saveAppTypesInfoForPractice_DL(appointmentTypeId, practiceId,
				enDisplayName, esDisplayName);
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(saveAppTypesDisplayInfoApiResponse);
		CCUtils.assertEquals(saveAppTypesDisplayInfoApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing appointmentTypeId, existing practiceId, do not Enter details in non-mandatory fields and verify response body")
	public void testSaveAppTypesInfoFunctionalityq() throws IOException {
		logStep("Enter existing appointmentTypeId, existing practiceId, do not Enter details in non-mandatory fields");
		saveAppTypesDisplayInfoApiResponse = sharedInfo.saveAppTypesInfoForPractice_DL(appointmentTypeId, practiceId,
				"", "");
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(saveAppTypesDisplayInfoApiResponse);
		CCUtils.assertEquals(saveAppTypesDisplayInfoApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid request payload details with non-existing appointmentTypeId, existing practiceId and verify response body")
	public void testSaveAppTypesInfoWithNonExistingAppTypeId() throws IOException {
		logStep("Enter valid request payload details with non-existing appointmentTypeId, existing practiceId");
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		saveAppTypesDisplayInfoApiResponse = sharedInfo.saveAppTypesInfoForPractice_DL(
				Integer.parseInt(testData.getProperty("non.existing.appointment.type.id")), practiceId, enDisplayName,
				esDisplayName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(saveAppTypesDisplayInfoApiResponse);
		CCUtils.assertEquals(saveAppTypesDisplayInfoApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForInvalidInput(saveAppTypesDisplayInfoApiResponse,
				Integer.parseInt(testData.getProperty("non.existing.appointment.type.id")), practiceId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid request payload details with existing appointmentTypeId, non-existing practice id and verify response body")
	public void testSaveAppTypesInfoWithNonExistingPracticeId() throws IOException {
		logStep("Enter valid request payload details with existing appointmentTypeId, non-existing practice id");
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		saveAppTypesDisplayInfoApiResponse = sharedInfo.saveAppTypesInfoForPractice_DL(appointmentTypeId,
				Integer.parseInt(testData.getProperty("non.existing.practice.id")), enDisplayName, esDisplayName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(saveAppTypesDisplayInfoApiResponse);
		CCUtils.assertEquals(saveAppTypesDisplayInfoApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForInvalidInput(saveAppTypesDisplayInfoApiResponse, appointmentTypeId,
				Integer.parseInt(testData.getProperty("non.existing.practice.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid request payload details with non-existing appointmentTypeId, non-existing practice id and verify response body")
	public void testSaveAppTypesInfoWithNonExistingPracticeIdAppTypeId() throws IOException {
		logStep("Enter valid request payload details with non-existing appointmentTypeId, non-existing practice id");
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		saveAppTypesDisplayInfoApiResponse = sharedInfo.saveAppTypesInfoForPractice_DL(
				Integer.parseInt(testData.getProperty("non.existing.appointment.type.id")),
				Integer.parseInt(testData.getProperty("non.existing.practice.id")), enDisplayName, esDisplayName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(saveAppTypesDisplayInfoApiResponse);
		CCUtils.assertEquals(saveAppTypesDisplayInfoApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForInvalidInput(saveAppTypesDisplayInfoApiResponse,
				Integer.parseInt(testData.getProperty("non.existing.appointment.type.id")),
				Integer.parseInt(testData.getProperty("non.existing.practice.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid request payload with appointmentTypeId and practice id as null to verify response error")
	public void testSaveAppTypesInfoUsingNullValues() throws IOException {
		logStep("Enter valid request payload with appointmentTypeId and practice id as null");
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		saveAppTypesDisplayInfoApiResponse = sharedInfo.saveAppTypesInfoForPracticeNullValues_DL(enDisplayName,
				esDisplayName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(saveAppTypesDisplayInfoApiResponse);
		CCUtils.assertEquals(saveAppTypesDisplayInfoApiResponse.getStatusCode(), 400);
	}

}
