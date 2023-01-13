//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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

public class SaveAppTypesDisplayInfoApiTests_BusinessLayer extends BaseRest {
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
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
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
		appointmentTypeId = createNewAppointmentType();
	}

	public int createNewAppointmentType() {
		logStep("Create new AppointmentType");
		String name = CCUtils.generateAlphaNumericString(10);
		String externalId = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_BL(practiceId, name, externalId,
				duration, categoryId, name, externalId);
		CCUtils.assertEquals(createAppointmentTypeApiResponse.getStatusCode(), 200);
		return createAppointmentTypeApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid request payload details with existing appointmentTypeId, existing practiceId and verify response body")
	public void testSaveAppTypesInfoWithValidDetails() throws IOException {
		logStep("Enter valid request payload details with existing appointmentTypeId, existing practiceId");
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		saveAppTypesDisplayInfoApiResponse = sharedInfo.saveAppTypesInfoForPractice_BL(appointmentTypeId, practiceId,
				enDisplayName, esDisplayName);
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(saveAppTypesDisplayInfoApiResponse);
		CCUtils.assertEquals(saveAppTypesDisplayInfoApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing appointmentTypeId, existing practiceId, do not Enter details in non-mandatory fields and verify response body")
	public void testSaveAppTypesInfoFunctionalityq() throws IOException {
		logStep("Enter existing appointmentTypeId, existing practiceId, do not Enter details in non-mandatory fields");
		saveAppTypesDisplayInfoApiResponse = sharedInfo.saveAppTypesInfoForPractice_BL(appointmentTypeId, practiceId,
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
		saveAppTypesDisplayInfoApiResponse = sharedInfo.saveAppTypesInfoForPractice_BL(
				Integer.parseInt(testData.getProperty("non.existing.appointment.type.id")), practiceId, enDisplayName,
				esDisplayName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(saveAppTypesDisplayInfoApiResponse);
		CCUtils.assertEquals(saveAppTypesDisplayInfoApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForInvalidInput(
				saveAppTypesDisplayInfoApiResponse, Integer.parseInt(testData.getProperty("non.existing.appointment.type.id")), practiceId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid request payload details with existing appointmentTypeId, non-existing practice id and verify response body")
	public void  testSaveAppTypesInfoWithNonExistingPracticeId() throws IOException {
		logStep("Enter valid request payload details with existing appointmentTypeId, non-existing practice id");
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		saveAppTypesDisplayInfoApiResponse = sharedInfo.saveAppTypesInfoForPractice_BL(appointmentTypeId,
				Integer.parseInt(testData.getProperty("non.existing.practice.id")), enDisplayName, esDisplayName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(saveAppTypesDisplayInfoApiResponse);
		CCUtils.assertEquals(saveAppTypesDisplayInfoApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForInvalidInput(saveAppTypesDisplayInfoApiResponse, 
				appointmentTypeId, Integer.parseInt(testData.getProperty("non.existing.practice.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid request payload details with non-existing appointmentTypeId, non-existing practice id and verify response body")
	public void testSaveAppTypesInfoWithNonExistingPracticeIdAppTypeId() throws IOException {
		logStep("Enter valid request payload details with non-existing appointmentTypeId, non-existing practice id");
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		saveAppTypesDisplayInfoApiResponse = sharedInfo.saveAppTypesInfoForPractice_BL(
				Integer.parseInt(testData.getProperty("non.existing.appointment.type.id")),
				Integer.parseInt(testData.getProperty("non.existing.practice.id")), enDisplayName, esDisplayName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(saveAppTypesDisplayInfoApiResponse);
		CCUtils.assertEquals(saveAppTypesDisplayInfoApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForInvalidInput(saveAppTypesDisplayInfoApiResponse, 
				Integer.parseInt(testData.getProperty("non.existing.appointment.type.id")), Integer.parseInt(testData.getProperty("non.existing.practice.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid request payload with appointmentTypeId and practice id as null to verify response error")
	public void testSaveAppTypesInfoUsingNullValues() throws IOException {
		logStep("Enter valid request payload with appointmentTypeId and practice id as null");
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		saveAppTypesDisplayInfoApiResponse = sharedInfo.saveAppTypesInfoForPracticeNullValues_BL(enDisplayName,
				esDisplayName);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(saveAppTypesDisplayInfoApiResponse);
		CCUtils.assertEquals(saveAppTypesDisplayInfoApiResponse.getStatusCode(), 400);
	}

}
