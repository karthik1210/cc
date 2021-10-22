// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.AuditLog;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class OrgNodeAuditLogTests extends BaseRest {
	private OrgInfo orgInfo;
	private Validations validation;
	private ClientInfo clientInfo;
	private AuditLog auditLog;
	Response createClientApiResponse, createSystemApiResponse, createPracticeApiResponse, createEnterpriseApiResponse,
			auditLogApiResponse;
	List<String> orgTypes = null;
	List<Integer> solutionList = null;

	private static final String TIMEZONE = "Timezone";
	private static final String SAP_ID = "SAP ID";
	private static final String STATUS = "Status";
	private static final String FIELD = "field";
	private static final String RESOURCE_ID = "resourceId";
	private static final String TARGETDATA = "targetData";
	private static final String ORG_TYPE_SYSTEM = "SYSTEM";
	private static final String ORG_TYPE_ENTERPRISE = "ENTERPRISE";
	private static final String ORG_TYPE_PRACTICE = "ORG";

	@BeforeTest()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
		auditLog = new AuditLog();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create and update system node and verify audit log for system node")
	public void testAuditLogForSystemNode() throws IOException {
		logStep("Create active system node below client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		String systemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createSystem(ccUser.getActive(), ccUser.getSystemDisplayNameApi(), systemName,
				ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(), systemSapNumber,
				ccUser.getSystemTimezoneIdApi());
		int orgId = createSystemApiResponse.jsonPath().get("id");

		logStep("Verify audit log for newly created system node");
		orgTypes = new ArrayList<String>();
		orgTypes.add(ORG_TYPE_SYSTEM);
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 10);

		logStep("Validating response information");
		verifyAuditLogForNewlyCreatedOrgNode(auditLogApiResponse, orgId, ORG_TYPE_SYSTEM);

		logStep("Modify sap id, timezone and status fields of newly created ststem node");
		String newSystemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		orgInfo.updateSystem("false", ccUser.getSystemDisplayNameApi(), orgId, newSystemSapNumber, null);

		logStep("Verify audit log of updated system node");
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 10);
		verifyAuditLogForUpdatedOrgNodeFields(auditLogApiResponse, orgId, ORG_TYPE_SYSTEM,
				ccUser.getSystemTimezoneIdApi(), ccUser.getTimezoneId(), systemSapNumber, newSystemSapNumber, "true",
				"false");
	}

	public void verifyAuditLogForNewlyCreatedOrgNode(Response auditLogApiResponse, int orgId, String context)
			throws IOException {

		CCUtils.responseTimeValidation(auditLogApiResponse);
		CCUtils.assertEquals(auditLogApiResponse.getStatusCode(), 200);
		validation.verifyAuditLogResponseBodyForNewlyCreatedOrgNode(auditLogApiResponse, orgId, context);
	}

	public void verifyAuditLogForUpdatedOrgNodeFields(Response auditLogApiResponse, int orgId, String context,
			String oldTimezone, String newTimezone, String oldSapId, String newSapId, String oldStatus,
			String newStatus) throws IOException {
		JsonPath jsonPath = new JsonPath(auditLogApiResponse.getBody().asString());
		LocalDate date = LocalDate.now();
		JSONArray jsonArray = new JSONArray(jsonPath.getList(date.toString()));
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			JSONObject targetData = jsonObject.getJSONObject(TARGETDATA);
			if (jsonObject.get(RESOURCE_ID).equals(orgId)) {
				if (targetData.getString(FIELD).equals(TIMEZONE)) {
					validation.verifyAuditLogResponseBodyForUpdatedOrgNode(jsonObject, orgId, context, TIMEZONE,
							oldTimezone, newTimezone);
				} else if (targetData.getString(FIELD).equals(SAP_ID)) {
					validation.verifyAuditLogResponseBodyForUpdatedOrgNode(jsonObject, orgId, context, SAP_ID, oldSapId,
							newSapId);
				} else if (targetData.getString(FIELD).equals(STATUS)) {
					validation.verifyAuditLogResponseBodyForUpdatedOrgNode(jsonObject, orgId, context, STATUS,
							oldStatus, newStatus);
				}
			}
		}
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create and update agnostic practice node and verify audit log for agnostic practice node")
	public void testAuditLogForAgnosticPracticeNode() throws IOException {
		logStep("Create active agnostic practice node below client");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		logStep("create a practice node with solutions");
		createPracticeApiResponse = orgInfo.createAgnosticPracticeNode(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), practiceName, ccUser.getPracticeOrgTypeApi(), clientId,
				ccUser.getParentTypeApi(), practiceSapNumber, ccUser.getPracticeTimezoneIdApi(), solutionList);
		int orgId = createPracticeApiResponse.jsonPath().get("id");

		logStep("Verify audit log for newly created agnostic practice node");
		orgTypes = new ArrayList<String>();
		orgTypes.add(ORG_TYPE_PRACTICE);
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 10);

		logStep("Validating response information");
		verifyAuditLogForNewlyCreatedOrgNode(auditLogApiResponse, orgId, ORG_TYPE_PRACTICE);

		logStep("Modify sap id, timezone and status fields of newly created agnostic practice node");
		orgInfo.updatePractice("false", ccUser.getPracticeDisplayNameApi(), orgId, " ",
				testData.getProperty("new.timezone"));

		logStep("Verify audit log for updated agnostic practice node");
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 10);
		verifyAuditLogForUpdatedOrgNodeFields(auditLogApiResponse, orgId, ORG_TYPE_PRACTICE,
				ccUser.getPracticeTimezoneIdApi(), testData.getProperty("new.timezone"),
				practiceSapNumber, " ", "true", "false");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create and update enterprise node and verify audit log for enterprise node")
	public void testAuditLogForEnterpriseNode() throws IOException {
		logStep("Create inactive enterprise node below system node");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		String systemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createSystem(ccUser.getActive(), ccUser.getSystemDisplayNameApi(), systemName,
				ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(), systemSapNumber, null);
		int systemId = createSystemApiResponse.jsonPath().get("id");

		String enterpriseName = ccUser.getEnterpriseNameApi().concat(CCUtils.generateRandomString());
		String enterpriseSapNumber = ccUser.getEnterpriseSapNumberApi().concat(CCUtils.generateRandomString());
		createEnterpriseApiResponse = orgInfo.createEnterprise("false", ccUser.getEnterpriseDisplayNameApi(),
				enterpriseName, ccUser.getEnterpriseOrgTypeApi(), systemId, ccUser.getEnterpriseParentTypeApi(),
				enterpriseSapNumber, ccUser.getEnterpriseTimezoneIdApi());
		int enterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		logStep("Verify audit log for newly created enterprise node");
		orgTypes = new ArrayList<String>();
		orgTypes.add(ORG_TYPE_ENTERPRISE);
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 10);

		logStep("Validating response information");
		verifyAuditLogForNewlyCreatedOrgNode(auditLogApiResponse, enterpriseId, ORG_TYPE_ENTERPRISE);

		logStep("Modify sap id, timezone and status fields of newly created enterprise node");
		orgInfo.updateEnterprise("true", ccUser.getEnterpriseDisplayNameApi(), enterpriseId, null, null);

		logStep("Verify audit log for updated enterprise node");
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 10);
		verifyAuditLogForUpdatedOrgNodeFields(auditLogApiResponse, enterpriseId, ORG_TYPE_ENTERPRISE,
				ccUser.getEnterpriseTimezoneIdApi(), ccUser.getTimezoneId(), enterpriseSapNumber, systemSapNumber,
				"false", "true");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create and update nge practice node and verify audit log for nge practice node")
	public void testAuditLogForNgePracticeNode() throws IOException {
		logStep("Create inactive nge practice node below enterprise node");
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClientUpdateClient(ccUser.getActive(), ccUser.getClientId(),
				clientName, clientSapNumber, ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		int clientId = createClientApiResponse.jsonPath().get("id");

		String systemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createSystem(ccUser.getActive(), ccUser.getSystemDisplayNameApi(), systemName,
				ccUser.getSystemOrgTypeApi(), clientId, ccUser.getParentTypeApi(), systemSapNumber,
				ccUser.getSystemTimezoneIdApi());
		int systemId = createSystemApiResponse.jsonPath().get("id");

		String enterpriseName = ccUser.getEnterpriseNameApi().concat(CCUtils.generateRandomString());
		String enterpriseSapNumber = ccUser.getEnterpriseSapNumberApi().concat(CCUtils.generateRandomString());
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), enterpriseName, ccUser.getEnterpriseOrgTypeApi(), systemId,
				ccUser.getEnterpriseParentTypeApi(), enterpriseSapNumber, ccUser.getEnterpriseTimezoneIdApi());
		int enterpriseId = createEnterpriseApiResponse.jsonPath().get("id");

		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		createPracticeApiResponse = orgInfo.createPractice("false", ccUser.getPracticeDisplayNameApi(), practiceName,
				ccUser.getPracticeOrgTypeApi(), enterpriseId, ccUser.getPracticeParentTypeApi(), practiceSapNumber,
				ccUser.getPracticeTimezoneIdApi());
		int orgId = createPracticeApiResponse.jsonPath().get("id");
		
		logStep("Verify audit log for newly created nge practice node");
		orgTypes = new ArrayList<String>();
		orgTypes.add(ORG_TYPE_PRACTICE);
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 10);
		
		logStep("Validating response information");
		verifyAuditLogForNewlyCreatedOrgNode(auditLogApiResponse, orgId, ORG_TYPE_PRACTICE);
		
		logStep("Modify sap id, timezone and status fields of newly created nge practice node");
		orgInfo.updatePractice("true", ccUser.getPracticeDisplayNameApi(), orgId, "", "");
		
		logStep("Verify audit log for updated nge practice node");
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 10);
		verifyAuditLogForUpdatedOrgNodeFields(auditLogApiResponse, orgId, ORG_TYPE_PRACTICE,
				ccUser.getPracticeTimezoneIdApi(), ccUser.getEnterpriseTimezoneIdApi(), practiceSapNumber, "", "false",
				"true");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid eventcds and verify statuscode")
	public void testErrorForInvalidEventcds() throws IOException {
		orgTypes = new ArrayList<String>();
		orgTypes.add(testData.getProperty("invalid.org.type"));
		auditLogApiResponse = auditLog.viewAuditLog(orgTypes, 10);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(auditLogApiResponse);
		CCUtils.assertEquals(auditLogApiResponse.getStatusCode(), 204);
	}

}
