package com.pxp.cc.testcases.api;

import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;

import io.restassured.response.Response;

public class UpdateOrgNodeStatusApiTests extends BaseRest {
	
	private Validations validation;
	private OrgInfo orgInfo;
	Response createSystemApiResponse, fetchSystemDetailsApiResponse, updateSystemStatusApiResponse, createEnterpriseApiResponse,
	fetchEnterpriseDetailsApiResponse, createPracticeApiResponse, fetchPracticeDetailsApiResponse;
		
	@BeforeTest()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
//		clientInfo = new ClientInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to change system status from active to inactive")
	public void testUpdateSystemNodeStatusFromActiveToInactive() throws IOException {
		String systemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		
		logStep("Create a new active system");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				systemName, ccUser.getSystemOrgTypeApi(), ccUser.getParentIdApi(), ccUser.getParentTypeApi(),
				systemSapNumber, ccUser.getSystemTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateSystemResponseBody(createSystemApiResponse, systemName);
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();
		logStep("Fetch newly created system details using system id");
		
		logStep("Fetch newly created system details using system id");
		fetchSystemDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(fetchSystemDetailsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(fetchSystemDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");

		validation.verifySearchSystemResponseBody(fetchSystemDetailsApiResponse, orgId, systemName,
				ccUser.getSystemOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getSystemDisplayNameApi(),
				systemSapNumber, ccUser.getSystemeActiveApi(), ccUser.getSystemTestClientApi(),
				ccUser.getSystemTimezoneIdApi(), ccUser.getSystemTimezoneDisplayNameApi(), false);
		
		logStep("Update system status from active to inactive");
		updateSystemStatusApiResponse = orgInfo.patchOrgStatus(orgId, "false");
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(updateSystemStatusApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(updateSystemStatusApiResponse.getStatusCode(), 200);	
		
		logStep("Verify system Status is changed from active to inactive");
		fetchSystemDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(fetchSystemDetailsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(fetchSystemDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");

		validation.verifySearchSystemResponseBody(fetchSystemDetailsApiResponse, orgId, systemName,
				ccUser.getSystemOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getSystemDisplayNameApi(),
				systemSapNumber, "false", ccUser.getSystemTestClientApi(),
				ccUser.getSystemTimezoneIdApi(), ccUser.getSystemTimezoneDisplayNameApi(), false);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to change system status from Inactive to Active")
	public void testUpdateSystemNodeStatusFromInactiveToActive() throws IOException {
		String systemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		
		logStep("Create a new inactive system");
		createSystemApiResponse = orgInfo.createSystem("false", ccUser.getSystemDisplayNameApi(),
				systemName, ccUser.getSystemOrgTypeApi(), ccUser.getParentIdApi(), ccUser.getParentTypeApi(),
				systemSapNumber, ccUser.getSystemTimezoneIdApi());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateSystemResponseBody(createSystemApiResponse, systemName);
	String orgId = createSystemApiResponse.jsonPath().get("id").toString();
	logStep("Fetch newly created system details using system id");
	
	logStep("Fetch newly created system details using system id");
	fetchSystemDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
	logStep("Validating Response Time");
	CCUtils.responseTimeValidation(fetchSystemDetailsApiResponse);
	logStep("Validating Response Code");
	CCUtils.assertEquals(fetchSystemDetailsApiResponse.getStatusCode(), 200);
	logStep("Validating Response Body");

	validation.verifySearchSystemResponseBody(fetchSystemDetailsApiResponse, orgId, systemName,
			ccUser.getSystemOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getSystemDisplayNameApi(),
			systemSapNumber, "false", ccUser.getSystemTestClientApi(),
			ccUser.getSystemTimezoneIdApi(), ccUser.getSystemTimezoneDisplayNameApi(), false);
	
	logStep("Update system status from active to inactive");
	updateSystemStatusApiResponse = orgInfo.patchOrgStatus(orgId, "true");
	logStep("Validating Response Time");
	CCUtils.responseTimeValidation(updateSystemStatusApiResponse);
	logStep("Validating Response Code");
	CCUtils.assertEquals(updateSystemStatusApiResponse.getStatusCode(), 200);	
	
	logStep("Verify system Status is changed from active to inactive");
	fetchSystemDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
	logStep("Validating Response Time");
	CCUtils.responseTimeValidation(fetchSystemDetailsApiResponse);
	logStep("Validating Response Code");
	CCUtils.assertEquals(fetchSystemDetailsApiResponse.getStatusCode(), 200);
	logStep("Validating Response Body");

	validation.verifySearchSystemResponseBody(fetchSystemDetailsApiResponse, orgId, systemName,
			ccUser.getSystemOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getSystemDisplayNameApi(),
			systemSapNumber, "true", ccUser.getSystemTestClientApi(),
			ccUser.getSystemTimezoneIdApi(), ccUser.getSystemTimezoneDisplayNameApi(), false);	
	}
	
	

}
