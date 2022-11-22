//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.AppointmentTypeInfo;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.AppointmentTypeDetails;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;

public class GetAppointmentTypesApiTests_BusinessLayer extends BaseRest {
	List<AppointmentTypeDetails> appointmentTypeList;
	private ClientInfo clientInfo;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, appointmentTypeId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName, name, externalId, duration;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse, createClientApiResponse,
			createProviderApiResponse, createResourceApiResponse, createAppointmentTypeApiResponse,
			getAppointmentTypesApiResponse;
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	private AppointmentTypeInfo appointmentTypeInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		appointmentTypeInfo = new AppointmentTypeInfo();
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber, "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
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
		name = CCUtils.generateAlphaNumericString(10);
		externalId = CCUtils.generateAlphaNumericString(4);
		duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_BL(practiceId, name, externalId,
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
