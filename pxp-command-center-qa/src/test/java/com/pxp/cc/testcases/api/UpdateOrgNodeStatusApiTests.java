// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import java.util.List;

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
	Response createSystemApiResponse, fetchSystemDetailsApiResponse, updateSystemStatusApiResponse,
			createEnterpriseApiResponse, updateEnterpriseStatusApiResponse, fetchEnterpriseDetailsApiResponse,
			createPracticeApiResponse, fetchPracticeDetailsApiResponse, updatePracticeStatusApiResponse,
			createClientApiResponse, fetchClientDetailsApiResponse, updateClientStatusApiResponse;
	List<Integer> solutionList = null;

	@BeforeTest()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
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
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		validation.verifyCreateSystemResponseBody(createSystemApiResponse, systemName);
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();

		logStep("Fetch newly created system details using system id");
		fetchSystemDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		CCUtils.responseTimeValidation(fetchSystemDetailsApiResponse);
		CCUtils.assertEquals(fetchSystemDetailsApiResponse.getStatusCode(), 200);

		validation.verifySearchSystemResponseBody(fetchSystemDetailsApiResponse, orgId, systemName,
				ccUser.getSystemOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getSystemDisplayNameApi(),
				systemSapNumber, ccUser.getSystemeActiveApi(), ccUser.getSystemTestClientApi(),
				ccUser.getSystemTimezoneIdApi(), ccUser.getSystemTimezoneDisplayNameApi(), false);

		logStep("Update system status from active to inactive");
		updateSystemStatusApiResponse = orgInfo.patchOrgStatus(orgId, "false");
		CCUtils.responseTimeValidation(updateSystemStatusApiResponse);
		CCUtils.assertEquals(updateSystemStatusApiResponse.getStatusCode(), 200);

		logStep("Verify system Status is changed from active to inactive");
		fetchSystemDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		CCUtils.assertEquals(fetchSystemDetailsApiResponse.getStatusCode(), 200);

		validation.verifySearchSystemResponseBody(fetchSystemDetailsApiResponse, orgId, systemName,
				ccUser.getSystemOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getSystemDisplayNameApi(),
				systemSapNumber, "false", ccUser.getSystemTestClientApi(), ccUser.getSystemTimezoneIdApi(),
				ccUser.getSystemTimezoneDisplayNameApi(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to change system status from Inactive to Active")
	public void testUpdateSystemNodeStatusFromInactiveToActive() throws IOException {
		String systemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());

		logStep("Create a new inactive system");
		createSystemApiResponse = orgInfo.createSystem("false", ccUser.getSystemDisplayNameApi(), systemName,
				ccUser.getSystemOrgTypeApi(), ccUser.getParentIdApi(), ccUser.getParentTypeApi(), systemSapNumber,
				ccUser.getSystemTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		validation.verifyCreateSystemResponseBody(createSystemApiResponse, systemName);
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();

		logStep("Fetch newly created system details using system id");
		fetchSystemDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		CCUtils.responseTimeValidation(fetchSystemDetailsApiResponse);
		CCUtils.assertEquals(fetchSystemDetailsApiResponse.getStatusCode(), 200);

		validation.verifySearchSystemResponseBody(fetchSystemDetailsApiResponse, orgId, systemName,
				ccUser.getSystemOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getSystemDisplayNameApi(),
				systemSapNumber, "false", ccUser.getSystemTestClientApi(), ccUser.getSystemTimezoneIdApi(),
				ccUser.getSystemTimezoneDisplayNameApi(), false);

		logStep("Update system status from active to inactive");
		updateSystemStatusApiResponse = orgInfo.patchOrgStatus(orgId, "true");
		CCUtils.responseTimeValidation(updateSystemStatusApiResponse);
		CCUtils.assertEquals(updateSystemStatusApiResponse.getStatusCode(), 200);

		logStep("Verify system Status is changed from active to inactive");
		fetchSystemDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		CCUtils.responseTimeValidation(fetchSystemDetailsApiResponse);
		CCUtils.assertEquals(fetchSystemDetailsApiResponse.getStatusCode(), 200);

		validation.verifySearchSystemResponseBody(fetchSystemDetailsApiResponse, orgId, systemName,
				ccUser.getSystemOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getSystemDisplayNameApi(),
				systemSapNumber, "true", ccUser.getSystemTestClientApi(), ccUser.getSystemTimezoneIdApi(),
				ccUser.getSystemTimezoneDisplayNameApi(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify after changing system node status from active to inactive, subsequent child nodes also become inactive")
	public void testChildNodeStatusWhenSystemNodeStatusChangedToInactive() throws IOException {
		String systemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());

		logStep("Create a new active system");
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				systemName, ccUser.getSystemOrgTypeApi(), ccUser.getParentIdApi(), ccUser.getParentTypeApi(),
				systemSapNumber, ccUser.getSystemTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		validation.verifyCreateSystemResponseBody(createSystemApiResponse, systemName);
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();

		logStep("Update system status from active to inactive");
		updateSystemStatusApiResponse = orgInfo.patchOrgStatus(orgId, "false");
		CCUtils.responseTimeValidation(updateSystemStatusApiResponse);
		CCUtils.assertEquals(updateSystemStatusApiResponse.getStatusCode(), 200);

		logStep("Verify system Status is changed from active to inactive");
		fetchSystemDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		CCUtils.responseTimeValidation(fetchSystemDetailsApiResponse);
		CCUtils.assertEquals(fetchSystemDetailsApiResponse.getStatusCode(), 200);

		validation.verifySearchSystemResponseBody(fetchSystemDetailsApiResponse, orgId, systemName,
				ccUser.getSystemOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getSystemDisplayNameApi(),
				systemSapNumber, "false", ccUser.getSystemTestClientApi(), ccUser.getSystemTimezoneIdApi(),
				ccUser.getSystemTimezoneDisplayNameApi(), false);

		logStep("Create a new active enterprise below active system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				Integer.parseInt(orgId), ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());

		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 200);
		orgId = createEnterpriseApiResponse.jsonPath().get("id").toString();

		logStep("Verify after updating system node status from active to inactive, subsequent child node also become inactive");

		fetchEnterpriseDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		CCUtils.responseTimeValidation(fetchEnterpriseDetailsApiResponse);
		CCUtils.assertEquals(fetchEnterpriseDetailsApiResponse.getStatusCode(), 200);

		validation.verifySearchEnterpriseResponseBody(fetchEnterpriseDetailsApiResponse, orgId,
				ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(), ccUser.getClientTypeApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseSapNumberApi(), "false",
				ccUser.getEnterpriseTestClientApi(), ccUser.getEnterpriseTimezoneIdApi(),
				ccUser.getEnterpriseTimezoneDisplayNameApi(), ccUser.getEnterprisePracticeCountApi(), false);

		logStep("Create a new active practice below active enterprise.");
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				Integer.parseInt(orgId), ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());

		CCUtils.responseTimeValidation(createPracticeApiResponse);
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		orgId = createPracticeApiResponse.jsonPath().get("id").toString();

		fetchPracticeDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		CCUtils.responseTimeValidation(fetchPracticeDetailsApiResponse);
		CCUtils.assertEquals(fetchPracticeDetailsApiResponse.getStatusCode(), 200);

		validation.verifySearchUpdatedPracticeResponseBody(fetchPracticeDetailsApiResponse, orgId,
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeSapNumberApi(), "false",
				ccUser.getPracticeTimezoneIdApi());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify after updating system node status from inactive to active, only system node status changed to active and subsequent child nodes remains inactive")
	public void testChildNodeStatusWhenSystemNodeStatusChangedToActive() throws IOException {
		String systemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());

		logStep("Create a new active system");
		createSystemApiResponse = orgInfo.createSystem("false", ccUser.getSystemDisplayNameApi(), systemName,
				ccUser.getSystemOrgTypeApi(), ccUser.getParentIdApi(), ccUser.getParentTypeApi(), systemSapNumber,
				ccUser.getSystemTimezoneIdApi());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		validation.verifyCreateSystemResponseBody(createSystemApiResponse, systemName);
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();

		logStep("Update system status from active to inactive");
		updateSystemStatusApiResponse = orgInfo.patchOrgStatus(orgId, "true");
		CCUtils.responseTimeValidation(updateSystemStatusApiResponse);
		CCUtils.assertEquals(updateSystemStatusApiResponse.getStatusCode(), 200);

		logStep("Verify system Status is changed from inactive to active");
		fetchSystemDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		CCUtils.responseTimeValidation(fetchSystemDetailsApiResponse);
		CCUtils.assertEquals(fetchSystemDetailsApiResponse.getStatusCode(), 200);

		validation.verifySearchSystemResponseBody(fetchSystemDetailsApiResponse, orgId, systemName,
				ccUser.getSystemOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getSystemDisplayNameApi(),
				systemSapNumber, "true", ccUser.getSystemTestClientApi(), ccUser.getSystemTimezoneIdApi(),
				ccUser.getSystemTimezoneDisplayNameApi(), false);

		logStep("Create a new active enterprise below active system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise("false", ccUser.getEnterpriseDisplayNameApi(),
				ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(), Integer.parseInt(orgId),
				ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());

		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 200);
		orgId = createEnterpriseApiResponse.jsonPath().get("id").toString();

		logStep("Verify after updating system node status from active to inactive, subsequent child node also become inactive");

		fetchEnterpriseDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		CCUtils.responseTimeValidation(fetchEnterpriseDetailsApiResponse);
		CCUtils.assertEquals(fetchEnterpriseDetailsApiResponse.getStatusCode(), 200);

		validation.verifySearchEnterpriseResponseBody(fetchEnterpriseDetailsApiResponse, orgId,
				ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(), ccUser.getClientTypeApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseSapNumberApi(), "false",
				ccUser.getEnterpriseTestClientApi(), ccUser.getEnterpriseTimezoneIdApi(),
				ccUser.getEnterpriseTimezoneDisplayNameApi(), ccUser.getEnterprisePracticeCountApi(), false);

		logStep("Create a new active practice below active enterprise.");
		createPracticeApiResponse = orgInfo.createPractice("false", ccUser.getPracticeDisplayNameApi(),
				ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(), Integer.parseInt(orgId),
				ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(), ccUser.getPracticeTimezoneIdApi());

		CCUtils.responseTimeValidation(createPracticeApiResponse);
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		orgId = createPracticeApiResponse.jsonPath().get("id").toString();

		fetchPracticeDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		CCUtils.responseTimeValidation(fetchPracticeDetailsApiResponse);
		CCUtils.assertEquals(fetchPracticeDetailsApiResponse.getStatusCode(), 200);

		validation.verifySearchUpdatedPracticeResponseBody(fetchPracticeDetailsApiResponse, orgId,
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeSapNumberApi(), "false",
				ccUser.getPracticeTimezoneIdApi());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to change enterprise status from active to inactive")
	public void testUpdateEnterpriseNodeStatusFromActiveToInactive() throws IOException {
		String enterpriseName = ccUser.getEnterpriseNameApi().concat(CCUtils.generateRandomString());
		String enterpriseSapNumber = ccUser.getEnterpriseSapNumberApi().concat(CCUtils.generateRandomString());
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), enterpriseName, ccUser.getEnterpriseOrgTypeApi(),
				ccUser.getEnterpriseParentIdApi(), ccUser.getEnterpriseParentTypeApi(), enterpriseSapNumber,
				ccUser.getEnterpriseTimezoneIdApi());

		logStep("Validating response information");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 200);
		validation.verifyCreateEnterpriseResponseBody(createEnterpriseApiResponse, enterpriseName);
		String orgId = createEnterpriseApiResponse.jsonPath().get("id").toString();

		logStep("Fetch newly created enterprise details using enterprise id");
		fetchEnterpriseDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		CCUtils.responseTimeValidation(fetchEnterpriseDetailsApiResponse);
		CCUtils.assertEquals(fetchEnterpriseDetailsApiResponse.getStatusCode(), 200);

		validation.verifySearchEnterpriseResponseBody(fetchEnterpriseDetailsApiResponse, orgId, enterpriseName,
				ccUser.getEnterpriseOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getEnterpriseDisplayNameApi(),
				enterpriseSapNumber, ccUser.getEnterpriseActiveApi(), ccUser.getEnterpriseTestClientApi(),
				ccUser.getEnterpriseTimezoneIdApi(), ccUser.getEnterpriseTimezoneDisplayNameApi(),
				ccUser.getEnterprisePracticeCountApi(), false);

		logStep("Update enterprise status from active to inactive");
		updateEnterpriseStatusApiResponse = orgInfo.patchOrgStatus(orgId, "false");
		CCUtils.responseTimeValidation(updateEnterpriseStatusApiResponse);
		CCUtils.assertEquals(updateEnterpriseStatusApiResponse.getStatusCode(), 200);

		logStep("Verify enterprise Status is changed from active to inactive");
		fetchEnterpriseDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		CCUtils.responseTimeValidation(fetchEnterpriseDetailsApiResponse);
		CCUtils.assertEquals(fetchEnterpriseDetailsApiResponse.getStatusCode(), 200);

		validation.verifySearchEnterpriseResponseBody(fetchEnterpriseDetailsApiResponse, orgId, enterpriseName,
				ccUser.getEnterpriseOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getEnterpriseDisplayNameApi(),
				enterpriseSapNumber, "false", ccUser.getEnterpriseTestClientApi(), ccUser.getEnterpriseTimezoneIdApi(),
				ccUser.getEnterpriseTimezoneDisplayNameApi(), ccUser.getEnterprisePracticeCountApi(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to change practice status from active to inactive")
	public void testUpdatePracticeNodeStatusFromActiveToInactive() throws IOException {
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), practiceName, ccUser.getPracticeOrgTypeApi(),
				ccUser.getPracticeParentIdApi(), ccUser.getPracticeParentTypeApi(), practiceSapNumber,
				ccUser.getPracticeTimezoneIdApi());

		logStep("Validating response information");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		validation.verifyCreatePracticeResponseBody(createPracticeApiResponse, practiceName);
		String orgId = createPracticeApiResponse.jsonPath().get("id").toString();

		logStep("Fetch newly created practice details using practice id");
		fetchPracticeDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		CCUtils.responseTimeValidation(fetchPracticeDetailsApiResponse);
		CCUtils.assertEquals(fetchPracticeDetailsApiResponse.getStatusCode(), 200);

		validation.verifySearchPracticeResponseBody(fetchPracticeDetailsApiResponse, orgId, practiceName,
				ccUser.getPracticeOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getPracticeDisplayNameApi(),
				practiceSapNumber, ccUser.getPracticeActiveApi(), ccUser.getPracticeTestClientApi(),
				ccUser.getPracticeTimezoneIdApi(), ccUser.getPracticeTimezoneDisplayNameApi(), false);

		logStep("Update practice status from active to inactive");
		updatePracticeStatusApiResponse = orgInfo.patchOrgStatus(orgId, "false");
		CCUtils.responseTimeValidation(updatePracticeStatusApiResponse);
		CCUtils.assertEquals(updatePracticeStatusApiResponse.getStatusCode(), 200);

		logStep("Verify practice Status is changed from active to inactive");
		fetchPracticeDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		CCUtils.responseTimeValidation(fetchPracticeDetailsApiResponse);
		CCUtils.assertEquals(fetchPracticeDetailsApiResponse.getStatusCode(), 200);

		validation.verifySearchPracticeResponseBody(fetchPracticeDetailsApiResponse, orgId, practiceName,
				ccUser.getPracticeOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getPracticeDisplayNameApi(),
				practiceSapNumber, "false", ccUser.getPracticeTestClientApi(), ccUser.getPracticeTimezoneIdApi(),
				ccUser.getPracticeTimezoneDisplayNameApi(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to change enterprise status from Inactive to Active")
	public void testUpdateEnterpriseNodeStatusFromInactiveToActive() throws IOException {
		String enterpriseName = ccUser.getEnterpriseNameApi().concat(CCUtils.generateRandomString());
		String enterpriseSapNumber = ccUser.getEnterpriseSapNumberApi().concat(CCUtils.generateRandomString());

		logStep("Create a new inactive enterprise");
		createEnterpriseApiResponse = orgInfo.createEnterprise("false", ccUser.getEnterpriseDisplayNameApi(),
				enterpriseName, ccUser.getEnterpriseOrgTypeApi(), ccUser.getEnterpriseParentIdApi(),
				ccUser.getEnterpriseParentTypeApi(), enterpriseSapNumber, ccUser.getEnterpriseTimezoneIdApi());

		logStep("Validating response information");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 200);
		validation.verifyCreateEnterpriseResponseBody(createEnterpriseApiResponse, enterpriseName);
		String orgId = createEnterpriseApiResponse.jsonPath().get("id").toString();

		logStep("Fetch newly created enterprise details using enterprise id");
		fetchEnterpriseDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		CCUtils.responseTimeValidation(fetchEnterpriseDetailsApiResponse);
		CCUtils.assertEquals(fetchEnterpriseDetailsApiResponse.getStatusCode(), 200);

		validation.verifySearchEnterpriseResponseBody(fetchEnterpriseDetailsApiResponse, orgId, enterpriseName,
				ccUser.getEnterpriseOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getEnterpriseDisplayNameApi(),
				enterpriseSapNumber, "false", ccUser.getEnterpriseTestClientApi(), ccUser.getEnterpriseTimezoneIdApi(),
				ccUser.getEnterpriseTimezoneDisplayNameApi(), ccUser.getEnterprisePracticeCountApi(), false);

		logStep("Update enterprise status from Inactive to Active");
		updateEnterpriseStatusApiResponse = orgInfo.patchOrgStatus(orgId, "true");
		CCUtils.responseTimeValidation(updateEnterpriseStatusApiResponse);
		CCUtils.assertEquals(updateEnterpriseStatusApiResponse.getStatusCode(), 200);

		logStep("Verify enterprise Status is changed from active to inactive");
		fetchEnterpriseDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		CCUtils.responseTimeValidation(fetchEnterpriseDetailsApiResponse);
		CCUtils.assertEquals(fetchEnterpriseDetailsApiResponse.getStatusCode(), 200);

		validation.verifySearchEnterpriseResponseBody(fetchEnterpriseDetailsApiResponse, orgId, enterpriseName,
				ccUser.getEnterpriseOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getEnterpriseDisplayNameApi(),
				enterpriseSapNumber, "true", ccUser.getEnterpriseTestClientApi(), ccUser.getEnterpriseTimezoneIdApi(),
				ccUser.getEnterpriseTimezoneDisplayNameApi(), ccUser.getEnterprisePracticeCountApi(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to change practice status from inactive to active")
	public void testUpdatePracticeNodeStatusFromInactiveToActive() throws IOException {
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());

		logStep("Create a new inactive practice");
		createPracticeApiResponse = orgInfo.createPractice("false", ccUser.getPracticeDisplayNameApi(), practiceName,
				ccUser.getPracticeOrgTypeApi(), ccUser.getPracticeParentIdApi(), ccUser.getPracticeParentTypeApi(),
				practiceSapNumber, ccUser.getPracticeTimezoneIdApi());

		logStep("Validating response information");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		validation.verifyCreatePracticeResponseBody(createPracticeApiResponse, practiceName);
		String orgId = createPracticeApiResponse.jsonPath().get("id").toString();

		logStep("Fetch newly created practice details using practice id");
		fetchPracticeDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		CCUtils.responseTimeValidation(fetchPracticeDetailsApiResponse);
		CCUtils.assertEquals(fetchPracticeDetailsApiResponse.getStatusCode(), 200);

		validation.verifySearchPracticeResponseBody(fetchPracticeDetailsApiResponse, orgId, practiceName,
				ccUser.getPracticeOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getPracticeDisplayNameApi(),
				practiceSapNumber, "false", ccUser.getPracticeTestClientApi(), ccUser.getPracticeTimezoneIdApi(),
				ccUser.getPracticeTimezoneDisplayNameApi(), false);

		logStep("Update practice status from active to inactive");
		updatePracticeStatusApiResponse = orgInfo.patchOrgStatus(orgId, "true");
		CCUtils.responseTimeValidation(updatePracticeStatusApiResponse);
		CCUtils.assertEquals(updatePracticeStatusApiResponse.getStatusCode(), 200);

		logStep("Verify practice Status is changed from active to inactive");
		fetchPracticeDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		CCUtils.responseTimeValidation(fetchPracticeDetailsApiResponse);
		CCUtils.assertEquals(fetchPracticeDetailsApiResponse.getStatusCode(), 200);

		validation.verifySearchPracticeResponseBody(fetchPracticeDetailsApiResponse, orgId, practiceName,
				ccUser.getPracticeOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getPracticeDisplayNameApi(),
				practiceSapNumber, "true", ccUser.getPracticeTestClientApi(), ccUser.getPracticeTimezoneIdApi(),
				ccUser.getPracticeTimezoneDisplayNameApi(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing org id and verify error message is displayed for org id field")
	public void testErrorMessageForInvalidOrgId() throws IOException {
		logStep("Enter non existing client id");
		updateSystemStatusApiResponse = orgInfo.patchOrgStatus(ccUser.getNonExistingOrgId(), "true");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSystemStatusApiResponse);
		CCUtils.assertEquals(updateSystemStatusApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForOrgIdField(updateSystemStatusApiResponse);
	}
}
