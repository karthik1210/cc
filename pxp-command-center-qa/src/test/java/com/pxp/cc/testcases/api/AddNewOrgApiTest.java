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

public class AddNewOrgApiTest extends BaseRest {

	private OrgInfo orgInfo;
	private Validations validation;
	Response createSystemApiResponse, fetchSystemDetailsApiResponse, createEnterpriseApiResponse,
			fetchEnterpriseDetailsApiResponse, createPracticeApiResponse, fetchPracticeDetailsApiResponse;
	List<Integer> solutionList = null;

	@BeforeTest()
	public void setBaseUri() throws Exception {

		setupServiceRequestSpecBuilder();
		orgInfo = new OrgInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new system and fetch newly created system details using system id")
	public void testAddNewSystemNode() throws IOException {
		String systemName = ccUser.getSystemNameApi().concat(CCUtils.generateRandomString());
		String systemSapNumber = ccUser.getSystemSapNumberApi().concat(CCUtils.generateRandomString());

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
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter NULL value in mandatory field and validate error message")
	public void testErrorMessagesForSystemMandatoryFields() throws IOException {
		createSystemApiResponse = orgInfo.createSystem(null, ccUser.getSystemDisplayNameApi(), null, null,
				ccUser.getParentIdApi(), null, ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);

		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 400);

		logStep("Validating Response Body");
		validation.verifyOrgNullErrorMessageResponseBody(createSystemApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add New Enterprise and fetch newly created Enterprise details using Enterprise id")
	public void testAddNewEnterpriseNode() throws IOException {
		String enterpriseName = ccUser.getEnterpriseNameApi().concat(CCUtils.generateRandomString());
		String enterpriseSapNumber = ccUser.getEnterpriseSapNumberApi().concat(CCUtils.generateRandomString());
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), enterpriseName, ccUser.getEnterpriseOrgTypeApi(),
				ccUser.getEnterpriseParentIdApi(), ccUser.getEnterpriseParentTypeApi(), enterpriseSapNumber,
				ccUser.getEnterpriseTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreateEnterpriseResponseBody(createEnterpriseApiResponse, enterpriseName);
		String orgId = createEnterpriseApiResponse.jsonPath().get("id").toString();
		fetchEnterpriseDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(fetchEnterpriseDetailsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(fetchEnterpriseDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");

		validation.verifySearchEnterpriseResponseBody(fetchEnterpriseDetailsApiResponse, orgId, enterpriseName,
				ccUser.getEnterpriseOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getEnterpriseDisplayNameApi(),
				enterpriseSapNumber, ccUser.getEnterpriseActiveApi(), ccUser.getEnterpriseTestClientApi(),
				ccUser.getEnterpriseTimezoneIdApi(), ccUser.getEnterpriseTimezoneDisplayNameApi(),
				ccUser.getEnterprisePracticeCountApi(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter NULL value in mandatory field and validate error message")
	public void testErrorMessagesForEnterpriseMandatoryFields() throws IOException {
		createEnterpriseApiResponse = orgInfo.createEnterprise(null, ccUser.getEnterpriseDisplayNameApi(), null, null,
				ccUser.getParentIdApi(), null, ccUser.getEnterpriseSapNumberApi(), ccUser.getEnterpriseTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);

		logStep("Validating Response Code");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 400);

		logStep("Validating Response Body");
		validation.verifyOrgNullErrorMessageResponseBody(createEnterpriseApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Add New Practice and fetch newly created Practice details using Practice id")
	public void testAddNewPracticeNode() throws IOException {
		String practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), practiceName, ccUser.getPracticeOrgTypeApi(),
				ccUser.getPracticeParentIdApi(), ccUser.getPracticeParentTypeApi(), practiceSapNumber,
				ccUser.getPracticeTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");
		validation.verifyCreatePracticeResponseBody(createPracticeApiResponse, practiceName);
		String orgId = createPracticeApiResponse.jsonPath().get("id").toString();
		fetchPracticeDetailsApiResponse = orgInfo.getOrgNodesDetails(orgId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(fetchPracticeDetailsApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(fetchPracticeDetailsApiResponse.getStatusCode(), 200);
		logStep("Validating Response Body");

		validation.verifySearchPracticeResponseBody(fetchPracticeDetailsApiResponse, orgId, practiceName,
				ccUser.getPracticeOrgTypeApi(), ccUser.getClientTypeApi(), ccUser.getPracticeDisplayNameApi(),
				practiceSapNumber, ccUser.getPracticeActiveApi(), ccUser.getPracticeTestClientApi(),
				ccUser.getPracticeTimezoneIdApi(), ccUser.getPracticeTimezoneDisplayNameApi(), false);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter NULL value in mandatory field and validate error message")
	public void testErrorMessagesForPracticeMandatoryFields() throws IOException {
		createPracticeApiResponse = orgInfo.createPractice(null, ccUser.getPracticeDisplayNameApi(), null, null,
				ccUser.getParentIdApi(), null, ccUser.getPracticeSapNumberApi(), ccUser.getPracticeTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createPracticeApiResponse);

		logStep("Validating Response Code");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 400);

		logStep("Validating Response Body");
		validation.verifyOrgNullErrorMessageResponseBody(createPracticeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Non existing time zone id and validate error message")
	public void testErrorResponseForOrgTimezoneField() throws IOException {
		String systemTimezoneId = ccUser.getSystemTimezoneIdApi().concat(CCUtils.generateRandomString());
		String enterpriseTimezoneId = ccUser.getEnterpriseTimezoneIdApi().concat(CCUtils.generateRandomString());
		String practiceTimezoneId = ccUser.getPracticeTimezoneIdApi().concat(CCUtils.generateRandomString());

		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), ccUser.getParentIdApi(),
				ccUser.getParentTypeApi(), ccUser.getSystemSapNumberApi(), systemTimezoneId);

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyErrorMessageInResponseForSystemTimezoneIdField(createSystemApiResponse, systemTimezoneId);

		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				ccUser.getEnterpriseParentIdApi(), ccUser.getEnterpriseParentTypeApi(),
				ccUser.getEnterpriseSapNumberApi(), enterpriseTimezoneId);

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyErrorMessageInResponseForEnterpriseTimezoneIdField(createEnterpriseApiResponse,
				enterpriseTimezoneId);

		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				ccUser.getPracticeParentIdApi(), ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				practiceTimezoneId);

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyErrorMessageInResponseForPracticeTimezoneIdField(createPracticeApiResponse,
				practiceTimezoneId);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Non existing Org Type Field and validate error message")
	public void testErrorResponseForOrgTypeField() throws IOException {
		String systemOrgType = ccUser.getSystemOrgTypeApi().concat(CCUtils.generateRandomString());
		String enterpriseOrgType = ccUser.getEnterpriseOrgTypeApi().concat(CCUtils.generateRandomString());
		String practiceOrgType = ccUser.getPracticeOrgTypeApi().concat(CCUtils.generateRandomString());

		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), systemOrgType, ccUser.getParentIdApi(), ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyErrorMessageInResponseForSystemOrgTypeField(createSystemApiResponse, systemOrgType);

		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), enterpriseOrgType,
				ccUser.getEnterpriseParentIdApi(), ccUser.getEnterpriseParentTypeApi(),
				ccUser.getEnterpriseSapNumberApi(), ccUser.getEnterpriseTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyErrorMessageInResponseForEnterpriseOrgTypeField(createEnterpriseApiResponse,
				enterpriseOrgType);

		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), practiceOrgType,
				ccUser.getPracticeParentIdApi(), ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyErrorMessageInResponseForPracticeOrgTypeField(createPracticeApiResponse, practiceOrgType);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Non existing Org Parent Id Field and validate error message")
	public void testErrorResponseForOrgParentIdField() throws IOException {
		Integer systemOrgParentId = 4022553;
		Integer enterpriseOrgParentId = 56464674;
		Integer practiceOrgParentId = 876876786;
		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), systemOrgParentId, ccUser.getParentTypeApi(),
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyErrorMessageInResponseForSystemParentIdField(createSystemApiResponse, systemOrgParentId);

		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				enterpriseOrgParentId, ccUser.getEnterpriseParentTypeApi(), ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyErrorMessageInResponseForEnterpriseParentIdField(createEnterpriseApiResponse,
				enterpriseOrgParentId);

		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				practiceOrgParentId, ccUser.getPracticeParentTypeApi(), ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyErrorMessageInResponseForPracticeParentIdField(createPracticeApiResponse, practiceOrgParentId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Non existing Org Parent Type Field and validate error message")
	public void testErrorResponseForOrgParentType() throws IOException {
		String systemOrgParentType = ccUser.getParentTypeApi().concat(CCUtils.generateRandomString());
		String enterpriseOrgParentType = ccUser.getEnterpriseParentTypeApi().concat(CCUtils.generateRandomString());
		String practiceOrgParentType = ccUser.getPracticeParentTypeApi().concat(CCUtils.generateRandomString());

		createSystemApiResponse = orgInfo.createSystem(ccUser.getSystemeActiveApi(), ccUser.getSystemDisplayNameApi(),
				ccUser.getSystemNameApi(), ccUser.getSystemOrgTypeApi(), ccUser.getParentIdApi(), systemOrgParentType,
				ccUser.getSystemSapNumberApi(), ccUser.getSystemTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyErrorMessageInResponseForParentTypeFieldSystem(createSystemApiResponse, systemOrgParentType);

		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				ccUser.getEnterpriseDisplayNameApi(), ccUser.getEnterpriseNameApi(), ccUser.getEnterpriseOrgTypeApi(),
				ccUser.getEnterpriseParentIdApi(), enterpriseOrgParentType, ccUser.getEnterpriseSapNumberApi(),
				ccUser.getEnterpriseTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyErrorMessageInResponseForParentTypeFieldEnterprise(createEnterpriseApiResponse,
				enterpriseOrgParentType);

		createPracticeApiResponse = orgInfo.createPractice(ccUser.getPracticeActiveApi(),
				ccUser.getPracticeDisplayNameApi(), ccUser.getPracticeNameApi(), ccUser.getPracticeOrgTypeApi(),
				ccUser.getPracticeParentIdApi(), practiceOrgParentType, ccUser.getPracticeSapNumberApi(),
				ccUser.getPracticeTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		logStep("Validating Response Code");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 400);
		logStep("Validating Response Body");
		validation.verifyErrorMessageInResponseForParentTypeFieldPractice(createPracticeApiResponse,
				practiceOrgParentType);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter maximum characters in system field and verify response for free text field as well")
	public void testMaximumCharacterAndFreeTextFieldErrorResponsForSystem() throws IOException {
		createSystemApiResponse = orgInfo.createSystem(ccUser.getActiveApi(),
				"WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28",
				"WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28",
				ccUser.getSystemOrgTypeApi(), ccUser.getParentIdApi(), ccUser.getParentTypeApi(),
				"WTUWTRXBNVNXVOERUWOHF @$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28",
				ccUser.getSystemTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createSystemApiResponse);

		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 400);

		logStep("Validating Response Body");
		validation.verifyMaximumCharacterErrorMessageInResponseForSystemField(createSystemApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter maximum characters in enterprise field and verify response for free text field as well")
	public void testMaximumCharacterAndFreeTextFieldErrorResponsForEnterprise() throws IOException {
		createEnterpriseApiResponse = orgInfo.createEnterprise(ccUser.getEnterpriseActiveApi(),
				"WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28",
				"WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28",
				ccUser.getEnterpriseOrgTypeApi(), ccUser.getEnterpriseParentIdApi(),
				ccUser.getEnterpriseParentTypeApi(),
				"WTUWTRXBNVNXVOERUWOHF @$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28",
				ccUser.getEnterpriseTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);

		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 400);

		logStep("Validating Response Body");
		validation.verifyMaximumCharacterAndFreeTextFieldErrorResponsForEnterprise(createEnterpriseApiResponse);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter maximum characters in practice field and verify response for free text field as well")
	public void testMaximumCharacterAndFreeTextFieldErrorResponsForPractice() throws IOException {
		createPracticeApiResponse = orgInfo.createEnterprise(ccUser.getPracticeActiveApi(),
				"WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28",
				"WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28",
				ccUser.getPracticeOrgTypeApi(), ccUser.getPracticeParentIdApi(), ccUser.getPracticeParentTypeApi(),
				"WTUWTRXBNVNXVOERUWOHF @$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28",
				ccUser.getPracticeTimezoneIdApi());

		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(createPracticeApiResponse);

		logStep("Validating Response Code");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 400);

		logStep("Validating Response Body");
		validation.verifyMaximumCharacterAndFreeTextFieldErrorResponsForPractice(createPracticeApiResponse);
	}

}
