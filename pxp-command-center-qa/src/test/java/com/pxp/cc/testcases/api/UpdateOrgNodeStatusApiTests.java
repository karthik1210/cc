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
		logStep("Create a new active system");
		String systemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				systemName, ccUser.getSystemOrgTypeApi(), ccUser.getParentIdApi(), ccUser.getParentTypeApi(),
				systemSapNumber, ccUser.getSystemTimezoneIdApi());
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();

		logStep("Update system status from active to inactive");
		updateSystemStatusApiResponse = orgInfo.patchOrgStatus(orgId, "false");
		CCUtils.responseTimeValidation(updateSystemStatusApiResponse);
		CCUtils.assertEquals(updateSystemStatusApiResponse.getStatusCode(), 200);

		logStep("Verify system Status is changed from active to inactive");
		fetchSystemDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		validation.verifySearchSystemResponseBody(fetchSystemDetailsApiResponse, orgId, systemName,
				ccUser.getSystemOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getSystemDisplayNameApi(),
				systemSapNumber, "false", ccUser.getSystemTestClientApi(), ccUser.getSystemTimezoneIdApi(),
				ccUser.getSystemTimezoneDisplayNameApi(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to change system status from Inactive to Active")
	public void testUpdateSystemNodeStatusFromInactiveToActive() throws IOException {
		logStep("Create a new inactive system");
		String systemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createSystem("false", ccUser.getSystemDisplayNameApi(), systemName,
				ccUser.getSystemOrgTypeApi(), ccUser.getParentIdApi(), ccUser.getParentTypeApi(), systemSapNumber,
				ccUser.getSystemTimezoneIdApi());
		String orgId = createSystemApiResponse.jsonPath().get("id").toString();

		logStep("Update system status from active to inactive");
		updateSystemStatusApiResponse = orgInfo.patchOrgStatus(orgId, "true");
		CCUtils.responseTimeValidation(updateSystemStatusApiResponse);
		CCUtils.assertEquals(updateSystemStatusApiResponse.getStatusCode(), 200);

		logStep("Verify system Status is changed from active to inactive");
		fetchSystemDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		validation.verifySearchSystemResponseBody(fetchSystemDetailsApiResponse, orgId, systemName,
				ccUser.getSystemOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getSystemDisplayNameApi(),
				systemSapNumber, "true", ccUser.getSystemTestClientApi(), ccUser.getSystemTimezoneIdApi(),
				ccUser.getSystemTimezoneDisplayNameApi(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify after changing system node status from active to inactive, subsequent child nodes also become inactive")
	public void testChildNodeStatusWhenSystemNodeStatusChangedToInactive() throws IOException {
		logStep("Create a new active system");
		String systemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				systemName, ccUser.getSystemOrgTypeApi(), ccUser.getParentIdApi(), ccUser.getParentTypeApi(),
				systemSapNumber, ccUser.getSystemTimezoneIdApi());
		String systemId = createSystemApiResponse.jsonPath().get("id").toString();

		logStep("Create a new active enterprise below active system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				Integer.parseInt(systemId), ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());
		String enterpriseId = createEnterpriseApiResponse.jsonPath().get("id").toString();

		logStep("Create a new active practice below active enterprise.");
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				Integer.parseInt(enterpriseId), ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());
		String practiceId = createPracticeApiResponse.jsonPath().get("id").toString();

		logStep("Update system status from active to inactive");
		updateSystemStatusApiResponse = orgInfo.patchOrgStatus(systemId, "false");
		CCUtils.responseTimeValidation(updateSystemStatusApiResponse);
		CCUtils.assertEquals(updateSystemStatusApiResponse.getStatusCode(), 200);

		logStep("Verify system Status is changed from active to inactive");
		fetchSystemDetailsApiResponse = orgInfo.getOrgNodesDetails(systemId);
		validation.verifySearchSystemResponseBody(fetchSystemDetailsApiResponse, systemId, systemName,
				ccUser.getSystemOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getSystemDisplayNameApi(),
				systemSapNumber, "false", ccUser.getSystemTestClientApi(), ccUser.getSystemTimezoneIdApi(),
				ccUser.getSystemTimezoneDisplayNameApi(), false);

		logStep("Verify after updating system node status from active to inactive, subsequent child node also become inactive");
		fetchEnterpriseDetailsApiResponse = orgInfo.getOrgNodesDetails(enterpriseId);
		validation.verifySearchEnterpriseResponseBody(fetchEnterpriseDetailsApiResponse, enterpriseId,
				ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(), ccUser.getClientTypeApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseSapNumberApi(), "false",
				ccUser.getEnterpriseTestClientApi(), ccUser.getEnterpriseTimezoneIdApi(),
				ccUser.getEnterpriseTimezoneDisplayNameApi(), ccUser.getEnterprisePracticeCountApi(), false);
		fetchPracticeDetailsApiResponse = orgInfo.getOrgNodesDetails(practiceId);
		validation.verifySearchUpdatedPracticeResponseBody(fetchPracticeDetailsApiResponse, practiceId,
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeSapNumberApi(), "false",
				ccUser.getPracticeTimezoneIdApi());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify after updating system node status from inactive to active, only system node status changed to active and subsequent child nodes remains inactive")
	public void testChildNodeStatusWhenSystemNodeStatusChangedToActive() throws IOException {
		logStep("Create a new active system");
		String systemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createSystem("false", ccUser.getSystemDisplayNameApi(), systemName,
				ccUser.getSystemOrgTypeApi(), ccUser.getParentIdApi(), ccUser.getParentTypeApi(), systemSapNumber,
				ccUser.getSystemTimezoneIdApi());
		String systemId = createSystemApiResponse.jsonPath().get("id").toString();

		logStep("Create a new active enterprise below active system.");
		createEnterpriseApiResponse = orgInfo.createEnterprise("false", ccUser.getEnterpriseDisplayNameApi(),
				ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(), Integer.parseInt(systemId),
				ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());
		String enterpriseId = createEnterpriseApiResponse.jsonPath().get("id").toString();

		logStep("Create a new active practice below active enterprise.");
		createPracticeApiResponse = orgInfo.createPractice("false", ccUser.getPracticeDisplayNameApi(),
				ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(), Integer.parseInt(enterpriseId),
				ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(), ccUser.getPracticeTimezoneIdApi());
		String practiceId = createPracticeApiResponse.jsonPath().get("id").toString();

		logStep("Update system status from active to inactive");
		updateSystemStatusApiResponse = orgInfo.patchOrgStatus(systemId, "true");
		CCUtils.responseTimeValidation(updateSystemStatusApiResponse);
		CCUtils.assertEquals(updateSystemStatusApiResponse.getStatusCode(), 200);

		logStep("Verify system Status is changed from inactive to active");
		fetchSystemDetailsApiResponse = orgInfo.getOrgNodesDetails(systemId);
		validation.verifySearchSystemResponseBody(fetchSystemDetailsApiResponse, systemId, systemName,
				ccUser.getSystemOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getSystemDisplayNameApi(),
				systemSapNumber, "true", ccUser.getSystemTestClientApi(), ccUser.getSystemTimezoneIdApi(),
				ccUser.getSystemTimezoneDisplayNameApi(), false);

		logStep("Verify after updating system node status from active to inactive, subsequent child node also become inactive");
		fetchEnterpriseDetailsApiResponse = orgInfo.getOrgNodesDetails(enterpriseId);
		validation.verifySearchEnterpriseResponseBody(fetchEnterpriseDetailsApiResponse, enterpriseId,
				ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(), ccUser.getClientTypeApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseSapNumberApi(), "false",
				ccUser.getEnterpriseTestClientApi(), ccUser.getEnterpriseTimezoneIdApi(),
				ccUser.getEnterpriseTimezoneDisplayNameApi(), ccUser.getEnterprisePracticeCountApi(), false);
		fetchPracticeDetailsApiResponse = orgInfo.getOrgNodesDetails(practiceId);
		validation.verifySearchUpdatedPracticeResponseBody(fetchPracticeDetailsApiResponse, practiceId,
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeSapNumberApi(), "false",
				ccUser.getPracticeTimezoneIdApi());
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to change enterprise status from active to inactive")
	public void testUpdateEnterpriseNodeStatusFromActiveToInactive() throws IOException {
		logStep("Create a new active enterprise");
		String enterpriseName = ccUser.getEnterpriseNameApi().concat(CCUtils.generateRandomString());
		String enterpriseSapNumber = ccUser.getEnterpriseSapNumberApi().concat(CCUtils.generateRandomString());
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), enterpriseName, ccUser.getEnterpriseOrgTypeApi(),
				ccUser.getEnterpriseParentIdApi(), ccUser.getEnterpriseParentTypeApi(), enterpriseSapNumber,
				ccUser.getEnterpriseTimezoneIdApi());
		String enterpriseId = createEnterpriseApiResponse.jsonPath().get("id").toString();

		logStep("Update enterprise status from active to inactive");
		updateEnterpriseStatusApiResponse = orgInfo.patchOrgStatus(enterpriseId, "false");
		CCUtils.responseTimeValidation(updateEnterpriseStatusApiResponse);
		CCUtils.assertEquals(updateEnterpriseStatusApiResponse.getStatusCode(), 200);

		logStep("Verify enterprise Status is changed from active to inactive");
		fetchEnterpriseDetailsApiResponse = orgInfo.getOrgNodesDetails(enterpriseId);
		validation.verifySearchEnterpriseResponseBody(fetchEnterpriseDetailsApiResponse, enterpriseId, enterpriseName,
				ccUser.getEnterpriseOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getEnterpriseDisplayNameApi(),
				enterpriseSapNumber, "false", ccUser.getEnterpriseTestClientApi(), ccUser.getEnterpriseTimezoneIdApi(),
				ccUser.getEnterpriseTimezoneDisplayNameApi(), ccUser.getEnterprisePracticeCountApi(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to change practice status from active to inactive")
	public void testUpdatePracticeNodeStatusFromActiveToInactive() throws IOException {
		logStep("Create a new active practice");
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), practiceName, ccUser.getPracticeOrgTypeApi(),
				ccUser.getPracticeParentIdApi(), ccUser.getPracticeParentTypeApi(), practiceSapNumber,
				ccUser.getPracticeTimezoneIdApi());
		String practiceId = createPracticeApiResponse.jsonPath().get("id").toString();

		logStep("Update practice status from active to inactive");
		updatePracticeStatusApiResponse = orgInfo.patchOrgStatus(practiceId, "false");
		CCUtils.responseTimeValidation(updatePracticeStatusApiResponse);
		CCUtils.assertEquals(updatePracticeStatusApiResponse.getStatusCode(), 200);

		logStep("Verify practice Status is changed from active to inactive");
		fetchPracticeDetailsApiResponse = orgInfo.getOrgNodesDetails(practiceId);
		validation.verifySearchPracticeResponseBody(fetchPracticeDetailsApiResponse, practiceId, practiceName,
				ccUser.getPracticeOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getPracticeDisplayNameApi(),
				practiceSapNumber, "false", ccUser.getPracticeTestClientApi(), ccUser.getPracticeTimezoneIdApi(),
				ccUser.getPracticeTimezoneDisplayNameApi(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to change enterprise status from Inactive to Active")
	public void testUpdateEnterpriseNodeStatusFromInactiveToActive() throws IOException {
		logStep("Create a new inactive enterprise");
		String enterpriseName = ccUser.getEnterpriseNameApi().concat(CCUtils.generateRandomString());
		String enterpriseSapNumber = ccUser.getEnterpriseSapNumberApi().concat(CCUtils.generateRandomString());
		createEnterpriseApiResponse = orgInfo.createEnterprise("false", ccUser.getEnterpriseDisplayNameApi(),
				enterpriseName, ccUser.getEnterpriseOrgTypeApi(), ccUser.getEnterpriseParentIdApi(),
				ccUser.getEnterpriseParentTypeApi(), enterpriseSapNumber, ccUser.getEnterpriseTimezoneIdApi());
		String orgId = createEnterpriseApiResponse.jsonPath().get("id").toString();

		logStep("Update enterprise status from Inactive to Active");
		updateEnterpriseStatusApiResponse = orgInfo.patchOrgStatus(orgId, "true");
		CCUtils.responseTimeValidation(updateEnterpriseStatusApiResponse);
		CCUtils.assertEquals(updateEnterpriseStatusApiResponse.getStatusCode(), 200);

		logStep("Verify enterprise Status is changed from active to inactive");
		fetchEnterpriseDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		validation.verifySearchEnterpriseResponseBody(fetchEnterpriseDetailsApiResponse, orgId, enterpriseName,
				ccUser.getEnterpriseOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getEnterpriseDisplayNameApi(),
				enterpriseSapNumber, "true", ccUser.getEnterpriseTestClientApi(), ccUser.getEnterpriseTimezoneIdApi(),
				ccUser.getEnterpriseTimezoneDisplayNameApi(), ccUser.getEnterprisePracticeCountApi(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to change practice status from inactive to active")
	public void testUpdatePracticeNodeStatusFromInactiveToActive() throws IOException {
		logStep("Create a new inactive practice");
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		createPracticeApiResponse = orgInfo.createPractice("false", ccUser.getPracticeDisplayNameApi(), practiceName,
				ccUser.getPracticeOrgTypeApi(), ccUser.getPracticeParentIdApi(), ccUser.getPracticeParentTypeApi(),
				practiceSapNumber, ccUser.getPracticeTimezoneIdApi());
		String orgId = createPracticeApiResponse.jsonPath().get("id").toString();

		logStep("Update practice status from active to inactive");
		updatePracticeStatusApiResponse = orgInfo.patchOrgStatus(orgId, "true");
		CCUtils.responseTimeValidation(updatePracticeStatusApiResponse);
		CCUtils.assertEquals(updatePracticeStatusApiResponse.getStatusCode(), 200);

		logStep("Verify practice Status is changed from active to inactive");
		fetchPracticeDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
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
