//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AssociateDeassociateStaffFromPracticeApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private ClientInfo clientInfo;
	private Validations validation;
	private OrgInfo orgInfo;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createNewstaffApiResponse, getPracticesAssociatedToStaffApiResponse,
			associateDeassociatePracticeForStaffApiResponse;
	int clientId, systemId, enterpriseId, practiceId, staffId, StaffIdOne;
	String clientName, clientSapNumber, externalId, staffName, lastName, staffNameOne, lastNameOne;

	@BeforeClass
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();

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
		logStep("Create staff to get staffId");
		staffName = testData.getProperty("provider.firstname");
		lastName = testData.getProperty("provider.lastname");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createNewstaffApiResponse = sharedInfo.createStaff_BL(clientId, staffName, lastName,
				testData.getProperty("staff.gender"), externalId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewstaffApiResponse);
		CCUtils.assertEquals(createNewstaffApiResponse.getStatusCode(), 200);
		staffId = createNewstaffApiResponse.jsonPath().get("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Associate practiceid for existing staffId and verify response body")
	public void testResponseCodeToAssociatePracticeForStaff() throws NullPointerException, IOException {
		logStep("Associate practiceid for existing staffId");
		associateDeassociatePracticeForStaffApiResponse = sharedInfo.practicesAssociateToStaff_BL(staffId, practiceId,
				true);
		CCUtils.assertEquals(associateDeassociatePracticeForStaffApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Deassociate practiceid for existing staffId and verify response body")
	public void testResponseCodeToDeassociatePracticeForStaff() throws NullPointerException, IOException {
		logStep("Deassociate practiceid for existing staffId");
		associateDeassociatePracticeForStaffApiResponse = sharedInfo.practicesAssociateToStaff_BL(staffId, practiceId,
				false);
		CCUtils.assertEquals(associateDeassociatePracticeForStaffApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Associate existing practiceid for non-existing StaffId and verify response body")
	public void testResponseCodeToAddPracticeForNonExistingStaff() throws NullPointerException, IOException {
		logStep("Associate existing practiceid for non-existing StaffId");
		associateDeassociatePracticeForStaffApiResponse = sharedInfo.practicesAssociateToStaff_BL(
				Integer.parseInt(testData.getProperty("non.existing.provider.id")), practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticeForStaffApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(associateDeassociatePracticeForStaffApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Associate non-existing practiceid for existing staffId and verify response body")
	public void testResponseCodeToAddNonExistingPracticeForResource() throws NullPointerException, IOException {
		logStep("Associate non-existing practiceid for existing staffId");
		associateDeassociatePracticeForStaffApiResponse = sharedInfo.practicesAssociateToStaff_BL(staffId,
				Integer.parseInt(testData.getProperty("nonexisting.practiceId")), true);
		CCUtils.assertEquals(associateDeassociatePracticeForStaffApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(associateDeassociatePracticeForStaffApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Associate non-existing practiceid for non-existing staffId and verify response body")
	public void testResponseCodeToAddNonExistingPracticeNonExistingStaffIdForResource()
			throws NullPointerException, IOException {
		logStep("Associate non-existing practiceid for existing staffId");
		associateDeassociatePracticeForStaffApiResponse = sharedInfo.practicesAssociateToStaff_BL(
				Integer.parseInt(testData.getProperty("non.existing.provider.id")),
				Integer.parseInt(testData.getProperty("nonexisting.practiceId")), true);
		CCUtils.assertEquals(associateDeassociatePracticeForStaffApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(associateDeassociatePracticeForStaffApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank input in request payload and verify error message")
	public void testErrorMessageForBlankRequestPayload() throws NullPointerException, IOException {
		logStep("Enter blank input in request payload");
		associateDeassociatePracticeForStaffApiResponse = sharedInfo
				.associateDeassociatePracticeForStaffUsingEmptyPayload_BL(staffId);
		CCUtils.assertEquals(associateDeassociatePracticeForStaffApiResponse.getStatusCode(), 400);
		validation.verifyInvalidRequestValidation(associateDeassociatePracticeForStaffApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter practiceid, staffId as null and verify response code")
	public void testResponseCodeToAddPracticeWithStaffIdAsNull() throws NullPointerException, IOException {
		logStep("Enter practiceid, staffid as null");
		associateDeassociatePracticeForStaffApiResponse = sharedInfo
				.associateDeassociatePracticeForStaffUsingNullValue_BL();
		CCUtils.assertEquals(associateDeassociatePracticeForStaffApiResponse.getStatusCode(), 400);
	}
}