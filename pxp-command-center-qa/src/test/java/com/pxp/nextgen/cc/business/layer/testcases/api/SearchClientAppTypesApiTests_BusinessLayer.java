//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.AppointmentTypeInfo;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;

public class SearchClientAppTypesApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	private AppointmentTypeInfo appointmentTypeInfo;
	Response createClientApiResponse, createSystemApiResponse, createPracticeApiResponse, createEnterpriseApiResponse,
	createAppointmentTypeApiResponse, searchAppTypesApiResponse, toggleSolutionStatusApiResponse;
	int clientId, systemId, enterpriseId, practiceId, appointmentTypeId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName, name;

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
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId(),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		clientId = createClientApiResponse.jsonPath().getInt("clientId");
		createSystemApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", testData.getProperty("api.system.active"), "", clientId,
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		 systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		createEnterpriseApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemId,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		 enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		createPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		logStep("Create new AppointmentType");
		name = testData.getProperty("api.appointment.name").concat(CCUtils.generateAlphaNumericString(5));
		String externalIdApp = CCUtils.generateAlphaNumericString(4);
		String duration = CCUtils.generateNumericString(2);
		String categoryId = CCUtils.generateNumericString(5);
		createAppointmentTypeApiResponse = appointmentTypeInfo.createAppointmentType_BL(practiceId, testData.getProperty("default.solution.id"), name, externalIdApp,
				duration, categoryId, name, externalIdApp);
		CCUtils.assertEquals(createAppointmentTypeApiResponse.getStatusCode(), 200);
		appointmentTypeId = createAppointmentTypeApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search Appointment Types for client using clientId and Appointment Types name and verify response body")
	public void testSearchAppointmentTypesUsingValidInput() {
		logStep("Search Appointment Types for client using clientId and Appointment Types name");
		searchAppTypesApiResponse = sharedInfo.getAppTypesForClient_BL(String.valueOf(clientId), null, null, null, null, null,
				null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchAppTypesApiResponse);
		CCUtils.assertEquals(searchAppTypesApiResponse.getStatusCode(), 200);
		logStep("Search Appointment Types for client using clientId and full Appointment Types name");
		searchAppTypesApiResponse = sharedInfo.getAppTypesForClient_BL(String.valueOf(clientId), name, null, null, null, null,
				null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchAppTypesApiResponse);
		CCUtils.assertEquals(searchAppTypesApiResponse.getStatusCode(), 200);
		logStep("Search Appointment Types for client using clientId and partial Appointment Types name");
		searchAppTypesApiResponse = sharedInfo.getAppTypesForClient_BL(String.valueOf(clientId),
				testData.getProperty("api.appointment.name"), null, null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchAppTypesApiResponse);
		CCUtils.assertEquals(searchAppTypesApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search Appointment Types for client using orgId and verify response body")
	public void testSearchAppointmentTypesWithOrgId() throws IOException, NullPointerException {
		logStep("Search Appointment Types for client using clientId and systemId");
		searchAppTypesApiResponse = sharedInfo.getAppTypesForClient_BL(String.valueOf(clientId), null, String.valueOf(systemId),
				null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchAppTypesApiResponse);
		CCUtils.assertEquals(searchAppTypesApiResponse.getStatusCode(), 200);
		logStep("Search Appointment Types for client using clientId and enterpriseId");
		searchAppTypesApiResponse = sharedInfo.getAppTypesForClient_BL(String.valueOf(clientId), null, null,
				String.valueOf(enterpriseId), String.valueOf(practiceId), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchAppTypesApiResponse);
		CCUtils.assertEquals(searchAppTypesApiResponse.getStatusCode(), 200);
		logStep("Search Appointment Types for client using clientId and practiceId");
		searchAppTypesApiResponse = sharedInfo.getAppTypesForClient_BL(String.valueOf(clientId), null, null, null,
				String.valueOf(practiceId), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchAppTypesApiResponse);
		CCUtils.assertEquals(searchAppTypesApiResponse.getStatusCode(), 200);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search Appointment Types for client using clientId and nonexisting app type name and verify response body")
	public void testSearchAppointmentTypesWithNonExistingAppTypeName() throws IOException, NullPointerException {
		logStep("Toggle solution for Location and Practice");
		String nonexistingAppName = CCUtils.generateAlphaNumericString(4);;
		logStep("Search Appointment Types for client using clientId and systemId");
		searchAppTypesApiResponse = sharedInfo.getAppTypesForClient_BL(String.valueOf(clientId), nonexistingAppName, String.valueOf(systemId),
				null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchAppTypesApiResponse);
		CCUtils.assertEquals(searchAppTypesApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search Appointment Types for non-existing clientId and verify response code")
	public void testSearchAppointmentTypesWithNonExistingClientId() throws IOException {
		logStep("Search Appointment Types for client with non-existing clientId");
		searchAppTypesApiResponse = sharedInfo.getAppTypesForClient_BL(testData.getProperty("org.client.id"), null, null,
				null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchAppTypesApiResponse);
		CCUtils.assertEquals(searchAppTypesApiResponse.getStatusCode(), 204);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search Appointment Types for non-existing practiceId and verify response code")
	public void testSearchAppointmentTypesWithNonExistingPracticeId() throws IOException {
		logStep("Search Appointment Types for client with non-existing practiceId");
		searchAppTypesApiResponse = sharedInfo.getAppTypesForClient_BL(String.valueOf(clientId), null, null, null,
				testData.getProperty("nonexiting.practice.id"), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchAppTypesApiResponse);
		CCUtils.assertEquals(searchAppTypesApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search Appointment Types for non-existing clientId, non-existing practiceId and verify response code")
	public void testSearchAppointmentTypesWithNonExistingClientIdPracticeId() throws IOException {
		logStep("Search provider for client with non-existing clientId, non-existing practiceId");
		searchAppTypesApiResponse = sharedInfo.getAppTypesForClient_BL(testData.getProperty("org.client.id"), null, null,
				null, testData.getProperty("nonexiting.practice.id"), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchAppTypesApiResponse);
		CCUtils.assertEquals(searchAppTypesApiResponse.getStatusCode(), 204);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search Appointment Types with null and verify response code")
	public void testSearchAppointmentTypesWithNull() throws IOException {
		logStep("Search Appointment Types with clientId, practiceId as null");
		searchAppTypesApiResponse  = sharedInfo.getAppTypesForClient_BL(null, null, null, null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchAppTypesApiResponse);
		CCUtils.assertEquals(searchAppTypesApiResponse.getStatusCode(), 400);
	}
}