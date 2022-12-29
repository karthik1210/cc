//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;

public class SearchClientStaffApiTests_DataLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	Response createClientApiResponse, createSystemApiResponse, createPracticeApiResponse, createEnterpriseApiResponse,
			createNewstaffApiResponse, searchStaffResult, updateStaffForPracticesResponse;
	int clientId, systemId, enterpriseId, practiceId, staffId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName, firstName, lastName, externalId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"));
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
		logStep("Create new staff");
		firstName = testData.getProperty("staff.firstname").concat(CCUtils.generateAlphaNumericString(5));
		lastName = testData.getProperty("staff.lastname").concat(CCUtils.generateAlphaNumericString(5));
		externalId = CCUtils.generateAlphaNumericString(10);
		createNewstaffApiResponse = sharedInfo.createStaff_DL(clientId, firstName, lastName,
				testData.getProperty("staff.gender"), externalId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewstaffApiResponse);
		CCUtils.assertEquals(createNewstaffApiResponse.getStatusCode(), 200);
		staffId = createNewstaffApiResponse.jsonPath().getInt("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search staff for client using clientId and staff name and verify response body")
	public void testSearchStaffUsingValidInput() {
		logStep("Search staff for client using clientId and staff name");
		searchStaffResult = sharedInfo.getStaffForClient_DL(String.valueOf(clientId), null, null, null, null, null,
				null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchStaffResult);
		CCUtils.assertEquals(searchStaffResult.getStatusCode(), 200);
		logStep("Search staff for client using clientId and staff name");
		searchStaffResult = sharedInfo.getStaffForClient_DL(String.valueOf(clientId), firstName, null, null, null, null,
				null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchStaffResult);
		CCUtils.assertEquals(searchStaffResult.getStatusCode(), 200);
		logStep("Search staff for client using clientId and partial staff name");
		searchStaffResult = sharedInfo.getStaffForClient_DL(String.valueOf(clientId),
				testData.getProperty("staff.firstname"), null, null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchStaffResult);
		CCUtils.assertEquals(searchStaffResult.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search staff for client using orgId and verify response body")
	public void testSearchStaffWithOrgId() throws IOException, NullPointerException {
		logStep("associate practices for staff");
		updateStaffForPracticesResponse = sharedInfo.practicesAssociateToStaff_DL(staffId, practiceId, true);
		CCUtils.assertEquals(updateStaffForPracticesResponse.getStatusCode(), 200);
		logStep("Search staff for client using clientId and systemId");
		searchStaffResult = sharedInfo.getStaffForClient_DL(String.valueOf(clientId), null, String.valueOf(systemId),
				null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchStaffResult);
		CCUtils.assertEquals(searchStaffResult.getStatusCode(), 200);
		logStep("Search staff for client using clientId and enterpriseId");
		searchStaffResult = sharedInfo.getStaffForClient_DL(String.valueOf(clientId), null, null,
				String.valueOf(enterpriseId), String.valueOf(practiceId), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchStaffResult);
		CCUtils.assertEquals(searchStaffResult.getStatusCode(), 200);
		logStep("Search staff for client using clientId and practiceId");
		searchStaffResult = sharedInfo.getStaffForClient_DL(String.valueOf(clientId), null, null, null,
				String.valueOf(practiceId), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchStaffResult);
		CCUtils.assertEquals(searchStaffResult.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search  Staff for non-existing clientId and verify response code")
	public void testSearchStaffWithNonExistingClientId() throws IOException {
		logStep("Search staff for client with non-existing clientId");
		searchStaffResult = sharedInfo.getStaffForClient_DL(testData.getProperty("org.client.id"), null, null,
				null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchStaffResult);
		CCUtils.assertEquals(searchStaffResult.getStatusCode(), 204);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search staff for non-existing practiceId and verify response code")
	public void testSearchStaffWithNonExistingPracticeId() throws IOException {
		logStep("Search staff for client with non-existing practiceId");
		searchStaffResult = sharedInfo.getStaffForClient_DL(String.valueOf(clientId), null, null, null,
				testData.getProperty("nonexiting.practice.id"), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchStaffResult);
		CCUtils.assertEquals(searchStaffResult.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search staff for non-existing clientId, non-existing practiceId and verify response code")
	public void testSearchStaffWithNonExistingClientIdPracticeId() throws IOException {
		logStep("Search provider for client with non-existing clientId, non-existing practiceId");
		searchStaffResult = sharedInfo.getStaffForClient_DL(testData.getProperty("org.client.id"), null, null,
				null, testData.getProperty("nonexiting.practice.id"), null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchStaffResult);
		CCUtils.assertEquals(searchStaffResult.getStatusCode(), 204);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search staff with null and verify response code")
	public void testSearchStaffWithNull() throws IOException {
		logStep("Search staff with clientId, practiceId as null");
		searchStaffResult  = sharedInfo.getStaffForClient_DL(null, null, null, null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(searchStaffResult);
		CCUtils.assertEquals(searchStaffResult.getStatusCode(), 400);
	}
}
