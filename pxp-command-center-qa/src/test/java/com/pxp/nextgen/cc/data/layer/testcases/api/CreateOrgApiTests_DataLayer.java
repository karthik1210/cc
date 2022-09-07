// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class CreateOrgApiTests_DataLayer extends BaseRest {

	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse, createClientApiResponse,
			fetchOrgDetailsApiResponse, createOrgNodeApiResponse;
	int clientId, systemId, enterpriseId, practiceId, agnosticPracticeId;
	String clientName, clientSapNumber, testFlag, clientTimezoneId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
		clientId = createClient();
	}

	public int createClient() throws NumberFormatException, NullPointerException, IOException {
		logStep("Create client using valid details in client name, sap number, active, testclient, client type and timezone field");
		clientName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5))));
		clientSapNumber = CCUtils
				.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.client.sapnumber")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5))));
		testFlag = testData.getProperty("api.client.testclient");
		clientTimezoneId = testData.getProperty("api.client.timezoneid");
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testFlag, Integer.parseInt(testData.getProperty("api.client.type")),
				clientTimezoneId);
		return createClientApiResponse.jsonPath().getInt("clientId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 0, description = "Create a new system and fetch newly created system details using system id")
	public void testCreateNewSystemOrgNode() throws IOException {
		logStep("Create new system using valid details in system name, sap number, active, testclient, client type and timezone field");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "",
				testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), "", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Body");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(createSystemApiResponse);
		validation.verifyCreateSystemResponseBody(createSystemApiResponse, testData.getProperty("api.system.name"));

		systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		logStep("Fetch newly created system details using system id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(systemId);
		logStep("Validating response information");
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgDetailsApiResponse, systemId,
				testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"),
				testData.getProperty("api.client.one.type"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.active"), testFlag, clientTimezoneId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter NULL value in mandatory field and validate error message")
	public void testErrorMessagesOfSystemMandatoryFields() throws IOException {
		logStep("Verify error message for mandatory system fields");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(null, testData.getProperty("api.system.displayname"),
				null, null, clientId, null, "", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(createSystemApiResponse);
		validation.verifyOrgNodeNullErrorMessageResponseBody(createSystemApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 1, description = "Create New Enterprise and fetch newly created Enterprise details using Enterprise id")
	public void testCreateNewEnterpriseOrgNode() throws IOException {
		logStep("Create new enterprise using valid details in enterprise name, sap number, active, testclient, client type and timezone field");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"), "",
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		validation.verifyCreateEnterpriseResponseBody(createEnterpriseApiResponse,
				testData.getProperty("api.enterprise.name"));

		enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		logStep("Fetch newly created enterprise details using enterprise id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(enterpriseId);
		logStep("Validating response information");
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgDetailsApiResponse, enterpriseId,
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"),
				testData.getProperty("api.client.one.type"), testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.active"), testFlag,
				testData.getProperty("api.enterprise.timezoneid"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 2, description = "Enter NULL value in mandatory field and validate error message")
	public void testErrorMessagesOfEnterpriseMandatoryFields() throws IOException {
		logStep("Verify error message for mandatory enterprise fields");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(null,
				testData.getProperty("api.enterprise.displayname"), null, null, systemId, null,
				testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		validation.verifyOrgNodeNullErrorMessageResponseBody(createEnterpriseApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 3, description = "Create New agnostic Practice and fetch newly created Practice details using Practice id")
	public void testCreateNewAgnosticPracticeOrgNode() throws IOException {
		logStep("Create a new practice below system.");
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), systemId, testData.getProperty("api.system.orgtype"),
				testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		validation.verifyCreatePracticeResponseBody(createPracticeApiResponse,
				testData.getProperty("api.practice.name"));

		agnosticPracticeId = createPracticeApiResponse.jsonPath().getInt("orgId");
		logStep("Fetching Practice Id Response body");
		fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(agnosticPracticeId);
		logStep("Validating response information");
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgDetailsApiResponse, agnosticPracticeId,
				testData.getProperty("api.practice.name"), testData.getProperty("api.practice.orgtype"),
				testData.getProperty("api.client.one.type"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testFlag, testData.getProperty("api.practice.timezoneid"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 4, description = "Create New NGE Practice and fetch newly created Practice details using Practice id")
	public void testCreateNewNgePracticeOrgNode() throws IOException {
		logStep("Create a new practice below enterprise.");
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		validation.verifyCreatePracticeResponseBody(createPracticeApiResponse,
				testData.getProperty("api.practice.name"));

		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		logStep("Fetching Practice Id Response body");
		fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(practiceId);
		logStep("Validating response information");
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgDetailsApiResponse, practiceId,
				testData.getProperty("api.practice.name"), testData.getProperty("api.practice.orgtype"),
				testData.getProperty("api.client.one.type"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testFlag, testData.getProperty("api.practice.timezoneid"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 5, description = "Enter Non existing time zone id and validate error message")
	public void testErrorResponseForOrgTimezoneField() throws IOException {
		logStep("Verify error message for organization timezone field");
		String orgTimezoneId = ccUser.getSystemTimezoneIdApi().concat(CCUtils.generateRandomString());
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), orgTimezoneId,
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		validation.verifyErrorMessageInResponseForOrgTimezoneIdField(createPracticeApiResponse, orgTimezoneId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Non existing Org Type Field and validate error message")
	public void testErrorResponseForOrgTypeField() throws IOException {
		logStep("Verify error message for org type field");
		String orgType = ccUser.getSystemOrgTypeApi().concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "",
				testData.getProperty("api.system.name"), orgType, clientId, testData.getProperty("api.parenttype"), "",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(createSystemApiResponse);
		validation.verifyErrorMessageInResponseForOrgTypeField(createSystemApiResponse, orgType);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Non existing Org Parent Id Field and validate error message")
	public void testErrorResponseForOrgParentIdField() throws IOException {
		logStep("Verify error message for org parent id field");
		Integer orgParentId = 4022553;
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "",
				testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"), orgParentId,
				testData.getProperty("api.parenttype"), "", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(createSystemApiResponse);
		validation.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(createSystemApiResponse, orgParentId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter maximum characters in system field and verify response for free text field as well")
	public void testErrorResponsForMaximumCharacterAndFreeTextSystemField() throws IOException {
		logStep("Verify maximum length validation error message for the system name field");
		String systemName = "WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28";
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "", systemName,
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"), "",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(createSystemApiResponse);
		validation.verifyErrorResponseBodyForNameField(createSystemApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 6, description = "Enter maximum characters in enterprise field and verify response for free text field as well")
	public void testErrorResponsForMaximumCharacterAndFreeTextEnterpriseField() throws IOException {
		logStep("Verify maximum length validation error message for the enterprise name field");
		String enterpriseName = "WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28";
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"), "",
				enterpriseName, testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		validation.verifyErrorResponseBodyForNameField(createEnterpriseApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 7, description = "Create New Practice and fetch newly created Practice details using Practice id")
	public void testErrorResponsForMaximumCharacterAndFreeTextPracticeField() throws IOException {
		logStep("Verify maximum length validation error message for the practice name, practice display name fields");
		String practiceName = "WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28";
		String practiceDisplayName = "WTUWTRXBNVNXVOERUWOHF VC848684425643610242937645@$$q$&(**&*^bcgd64SVFSFVVNWORIU78YGBVJ8QHJSFBN77BJGS28";
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				practiceDisplayName, practiceName, testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		validation.verifyMaximumCharacterErrorMessageInResponseBody(createPracticeApiResponse);
	}

	@DataProvider(name = "getOrgDetails")
	public Object[] getOrgTypeAndOrgId() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("api.system.orgtype"), String.valueOf(clientId),
						testData.getProperty("api.parenttype") },
				{ testData.getProperty("api.enterprise.orgtype"), String.valueOf(systemId),
						testData.getProperty("api.enterprise.parenttype") },
				{ testData.getProperty("api.practice.orgtype"), String.valueOf(enterpriseId),
						testData.getProperty("api.practice.parenttype") },
				{ testData.getProperty("api.practice.orgtype"), String.valueOf(systemId),
						testData.getProperty("api.system.orgtype") } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", priority = 8, description = "Enter invalid value in ehr identifier field and validate error response body")
	public void testErrorMessagesForEhrIdentifierField(String[] getOrgTypeAndOrgId) throws IOException {
		logStep("Enter null value in eht identifier field and validate error response body");
		createOrgNodeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "",
				"org_name", getOrgTypeAndOrgId[0], Integer.parseInt(getOrgTypeAndOrgId[1]), getOrgTypeAndOrgId[2],
				"US/Alaska", 0, Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForInvalidProductId(createOrgNodeApiResponse,0);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgDetails", priority = 9, description = "Enter invalid value in pmidentifier, ehr identifier field and validate error response body")
	public void testErrorMessagesForPmIdentifierField(String[] getOrgTypeAndOrgId) throws IOException {
		logStep("Enter null value in pm identifier field and validate error response body");
		createOrgNodeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "",
				"org_name", getOrgTypeAndOrgId[0], Integer.parseInt(getOrgTypeAndOrgId[1]), getOrgTypeAndOrgId[2],
				"US/Alaska", Integer.parseInt(testData.getProperty("ehr.identifier")), 0);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForInvalidProductId(createOrgNodeApiResponse,0);
	}

	@DataProvider(name = "getOrgNodeDetails")
	public Object[] getOrgTypeParentTypeAndOrgId() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("api.enterprise.orgtype"), String.valueOf(systemId),
						testData.getProperty("api.enterprise.parenttype") },
				{ testData.getProperty("api.practice.orgtype"), String.valueOf(enterpriseId),
						testData.getProperty("api.practice.parenttype") },
				{ testData.getProperty("api.practice.orgtype"), String.valueOf(systemId),
						testData.getProperty("api.system.orgtype") } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "getOrgNodeDetails", priority = 10, description = "Enter null value in timezone field and validate error response body")
	public void testErrorMessagesForNullTimezoneField(String[] getOrgTypeAndOrgIdValue) throws IOException {
		logStep("Enter null value in pm identifier field and validate error response body");
		createOrgNodeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "",
				"org_name", getOrgTypeAndOrgIdValue[0], Integer.parseInt(getOrgTypeAndOrgIdValue[1]),
				getOrgTypeAndOrgIdValue[2], "", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createOrgNodeApiResponse);
		CCUtils.assertEquals(createOrgNodeApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForNullTimezone(createOrgNodeApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 11, description = "Create New agnostic Practice and fetch newly created Practice details using Practice id")
	public void testCreateNewAgnosticPracticeOrgNodeFunctionality() throws IOException {
		logStep("Create a new practice below system.");
		String practiceName = "practice_name_2 practice_name_2 practice_name_2 practice_name_2 practice_name_2 practice_name_2@1234";
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"), "",
				practiceName, testData.getProperty("api.practice.orgtype"), systemId,
				testData.getProperty("api.system.orgtype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		validation.verifyCreatePracticeResponseBody(createPracticeApiResponse, practiceName);

		agnosticPracticeId = createPracticeApiResponse.jsonPath().getInt("orgId");
		logStep("Fetching Practice Id Response body");
		fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(agnosticPracticeId);
		logStep("Validating response information");
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgDetailsApiResponse, agnosticPracticeId, practiceName,
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.client.one.type"), practiceName,
				testData.getProperty("api.practice.active"), testFlag, testData.getProperty("api.practice.timezoneid"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 12, description = "Enter NULL value in mandatory field and validate error message")
	public void testErrorMessagesOfPracticeMandatoryFields() throws IOException {
		logStep("Verify error message for mandatory practice fields");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(null,
				testData.getProperty("api.practice.displayname"), null, null, practiceId, null,
				testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Body");
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		validation.verifyOrgNodeNullErrorMessageResponseBody(createEnterpriseApiResponse);
	}

}
