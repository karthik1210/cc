// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class CreateOrg_DataLayer extends BaseRest {

	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse, createClientApiResponse,
			fetchOrgDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create a new system and fetch newly created system details using system id")
	public void testCreateNewSystemOrgNode() throws IOException {
		logStep("Create new system using valid details in system name, sap number, active, testclient, client type and timezone field");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), Integer.parseInt(testData.getProperty("api.parentid")),
				testData.getProperty("api.parenttype"), testData.getProperty("api.system.sapnumber"),
				testData.getProperty("api.system.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating Response Body");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(createSystemApiResponse);
		validation.verifyCreateSystemResponseBody(createSystemApiResponse, testData.getProperty("api.system.name"));

		int orgId = createSystemApiResponse.jsonPath().get("orgId");
		logStep("Fetch newly created system details using system id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(orgId);
		logStep("Validating response information");
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgDetailsApiResponse, orgId,
				testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"),
				testData.getProperty("api.client.type"), testData.getProperty("api.system.displayname"),
				testData.getProperty("api.system.sapnumber"), testData.getProperty("api.system.active"),
				testData.getProperty("api.system.testclient"), testData.getProperty("api.system.timezoneid"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter NULL value in mandatory field and validate error message")
	public void testErrorMessagesOfSystemMandatoryFields() throws IOException {
		logStep("Verify error message for mandatory system fields");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(null, testData.getProperty("api.system.displayname"),
				null, null, Integer.parseInt(testData.getProperty("api.parentid")), null,
				testData.getProperty("api.system.sapnumber"), testData.getProperty("api.system.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(createSystemApiResponse);
		validation.verifyOrgNodeNullErrorMessageResponseBody(createSystemApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create New Enterprise and fetch newly created Enterprise details using Enterprise id")
	public void testCreateNewEnterpriseOrgNode() throws IOException {
		logStep("Create new enterprise using valid details in enterprise name, sap number, active, testclient, client type and timezone field");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.displayname"), testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"),
				Integer.parseInt(testData.getProperty("api.enterprise.parentid")),
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.sapnumber"),
				testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		validation.verifyCreateEnterpriseResponseBody(createEnterpriseApiResponse,
				testData.getProperty("api.enterprise.name"));

		int orgId = createEnterpriseApiResponse.jsonPath().get("orgId");
		logStep("Fetch newly created enterprise details using enterprise id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(orgId);
		logStep("Validating response information");
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgDetailsApiResponse, orgId,
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"),
				testData.getProperty("api.client.type"), testData.getProperty("api.enterprise.displayname"),
				testData.getProperty("api.enterprise.sapnumber"), testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.testclient"), testData.getProperty("api.enterprise.timezoneid"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter NULL value in mandatory field and validate error message")
	public void testErrorMessagesOfEnterpriseMandatoryFields() throws IOException {
		logStep("Verify error message for mandatory enterprise fields");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(null,
				testData.getProperty("api.enterprise.displayname"), null, null,
				Integer.parseInt(testData.getProperty("api.enterprise.parentid")), null,
				testData.getProperty("api.enterprise.sapnumber"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		validation.verifyOrgNodeNullErrorMessageResponseBody(createEnterpriseApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create New agnostic Practice and fetch newly created Practice details using Practice id")
	public void testCreateNewAgnosticPracticeOrgNode() throws IOException {
		logStep("Create agnostic practice below the client with valid details");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.system.testclient"),
				testData.getProperty("api.client.type"), testData.getProperty("api.client.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Create a new practice below enterprise.");
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), clientId, "CLIENT",
				testData.getProperty("api.practice.sapnumber"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		validation.verifyCreatePracticeResponseBody(createPracticeApiResponse,
				testData.getProperty("api.practice.name"));

		int orgId = createPracticeApiResponse.jsonPath().get("orgId");
		logStep("Fetching Practice Id Response body");
		fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(orgId);
		logStep("Validating response information");
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgDetailsApiResponse, orgId,
				testData.getProperty("api.practice.name"), testData.getProperty("api.practice.orgtype"),
				testData.getProperty("api.client.type"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.sapnumber"), testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.testclient"), testData.getProperty("api.practice.timezoneid"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create New NGE Practice and fetch newly created Practice details using Practice id")
	public void testCreateNewNgePracticeOrgNode() throws IOException {
		logStep("Create nge practice below the client with valid details");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.system.testclient"),
				testData.getProperty("api.client.type"), testData.getProperty("api.client.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int clientId = createClientApiResponse.jsonPath().getInt("clientId");

		logStep("Create a new system below client.");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
				testData.getProperty("api.system.sapnumber"), testData.getProperty("api.system.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");

		logStep("Create a new enterprise below system.");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.displayname"), testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.sapnumber"),
				testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");

		logStep("Create a new practice below enterprise.");
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.sapnumber"),
				testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		validation.verifyCreatePracticeResponseBody(createPracticeApiResponse,
				testData.getProperty("api.practice.name"));

		int orgId = createPracticeApiResponse.jsonPath().get("orgId");
		logStep("Fetching Practice Id Response body");
		fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(orgId);
		logStep("Validating response information");
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgDetailsApiResponse, orgId,
				testData.getProperty("api.practice.name"), testData.getProperty("api.practice.orgtype"),
				testData.getProperty("api.client.type"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.sapnumber"), testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.testclient"), testData.getProperty("api.practice.timezoneid"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter NULL value in mandatory field and validate error message")
	public void testErrorMessagesOfPracticeMandatoryFields() throws IOException {
		logStep("Verify error message for mandatory practice fields");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(null,
				testData.getProperty("api.practice.displayname"), null, null,
				Integer.parseInt(testData.getProperty("api.practice.parentid")), null,
				testData.getProperty("api.practice.sapnumber"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating Response Body");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		validation.verifyOrgNodeNullErrorMessageResponseBody(createEnterpriseApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Non existing time zone id and validate error message")
	public void testErrorResponseForOrgTimezoneField() throws IOException {
		logStep("Verify error message for organization timezone field");
		String orgTimezoneId = ccUser.getSystemTimezoneIdApi().concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), Integer.parseInt(testData.getProperty("api.parentid")),
				testData.getProperty("api.parenttype"), testData.getProperty("api.system.sapnumber"), orgTimezoneId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(createSystemApiResponse);
		validation.verifyErrorMessageInResponseForOrgTimezoneIdField(createSystemApiResponse, orgTimezoneId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Non existing Org Type Field and validate error message")
	public void testErrorResponseForOrgTypeField() throws IOException {
		logStep("Verify error message for org type field");
		String orgType = ccUser.getSystemOrgTypeApi().concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"), orgType,
				Integer.parseInt(testData.getProperty("api.parentid")), testData.getProperty("api.parenttype"),
				testData.getProperty("api.system.sapnumber"), testData.getProperty("api.system.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(createSystemApiResponse);
		validation.verifyErrorMessageInResponseForOrgTypeField(createSystemApiResponse, orgType);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Non existing Org Parent Id Field and validate error message")
	public void testErrorResponseForOrgParentIdField() throws IOException {
		logStep("Verify error message for org parent id field");
		Integer orgParentId = 4022553;
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), orgParentId, testData.getProperty("api.parenttype"),
				testData.getProperty("api.system.sapnumber"), testData.getProperty("api.system.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(createSystemApiResponse);
		validation.verifyErrorMessageInResponseForOrgParentIdField(createSystemApiResponse, orgParentId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Non existing Org Parent Type Field and validate error message")
	public void testErrorResponseForOrgParentType() throws IOException {
		logStep("Verify error message for org parent type field");
		String orgParentType = ccUser.getParentTypeApi().concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), Integer.parseInt(testData.getProperty("api.parentid")),
				orgParentType, testData.getProperty("api.system.sapnumber"),
				testData.getProperty("api.system.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(createSystemApiResponse);
		validation.verifyErrorMessageInResponseForParentTypeField(createSystemApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter maximum characters in system field and verify response for free text field as well")
	public void testErrorResponsForMaximumCharacterAndFreeTextSystemField() throws IOException {
		logStep("Verify maximum length validation error message for the system name, system display name & system sap number fields");
		String systemName = "WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28";
		String systemDisplayName = "WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28";
		String systemSapNumber = "WTUWTRXBNVNXVOERUWOHF @$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28";
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				systemDisplayName, systemName, testData.getProperty("api.system.orgtype"),
				Integer.parseInt(testData.getProperty("api.parentid")), testData.getProperty("api.parenttype"),
				systemSapNumber, testData.getProperty("api.system.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(createSystemApiResponse);
		validation.verifyMaximumCharacterErrorMessageInResponseBody(createSystemApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter maximum characters in enterprise field and verify response for free text field as well")
	public void testErrorResponsForMaximumCharacterAndFreeTextEnterpriseField() throws IOException {
		logStep("Verify maximum length validation error message for the enterprise name, enterprise display name & enterprise sap number fields");
		String enterpriseName = "WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28";
		String enterpriseDisplayName = "WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28";
		String enterpriseSapNumber = "WTUWTRXBNVNXVOERUWOHF @$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28";
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"),
				enterpriseDisplayName, enterpriseName, testData.getProperty("api.enterprise.orgtype"),
				Integer.parseInt(testData.getProperty("api.enterprise.parentid")),
				testData.getProperty("api.enterprise.parenttype"), enterpriseSapNumber,
				testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		validation.verifyMaximumCharacterErrorMessageInResponseBody(createEnterpriseApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create New Practice and fetch newly created Practice details using Practice id")
	public void testErrorResponsForMaximumCharacterAndFreeTextPracticeField() throws IOException {
		logStep("Verify maximum length validation error message for the practice name, practice display name & practice sap number fields");
		String practiceName = "WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28";
		String practiceDisplayName = "WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28";
		String practiceSapNumber = "WTUWTRXBNVNXVOERUWOHF @$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28";
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				practiceDisplayName, practiceName, testData.getProperty("api.practice.orgtype"),
				Integer.parseInt(testData.getProperty("api.practice.parentid")),
				testData.getProperty("api.practice.parenttype"), practiceSapNumber,
				testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		validation.verifyMaximumCharacterErrorMessageInResponseBody(createPracticeApiResponse);
	}

}
