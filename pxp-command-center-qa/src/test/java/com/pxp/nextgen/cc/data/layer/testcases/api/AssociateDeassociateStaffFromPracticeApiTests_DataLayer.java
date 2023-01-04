//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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

public class AssociateDeassociateStaffFromPracticeApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private ClientInfo clientInfo;
	private Validations validation;
	private OrgInfo orgInfo;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createNewstaffApiResponse, getPracticesAssociatedToStaffApiResponse,
			associateDeassociatePracticeForStaffApiResponse;
	int clientId, practiceId, staffId, StaffIdOne;
	String clientName, clientSapNumber, externalId, staffName, lastName, staffNameOne, lastNameOne;

	@BeforeClass
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		logStep("Create nge practice below the client with valid details");
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.system.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
		clientId = createClientApiResponse.jsonPath().getInt("clientId");
		logStep("Create a new system below client.");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "",
				testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), "", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new enterprise below system.");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"), "",
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new practice below enterprise.");
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		logStep("Create staff to get staffId");
		staffName = testData.getProperty("provider.firstname");
		lastName = testData.getProperty("provider.lastname");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createNewstaffApiResponse = sharedInfo.createStaff_DL(clientId, staffName, lastName,
				testData.getProperty("staff.gender"), externalId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewstaffApiResponse);
		CCUtils.assertEquals(createNewstaffApiResponse.getStatusCode(), 200);
		staffId = createNewstaffApiResponse.jsonPath().get("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Associate practiceid for existing staffId and verify response body")
	public void testResponseCodeToAssociatePracticeForStaff() throws NullPointerException, IOException {
		logStep("Associate practiceid for existing staffId");
		associateDeassociatePracticeForStaffApiResponse = sharedInfo.practicesAssociateToStaff_DL(staffId, practiceId,
				true);
		CCUtils.assertEquals(associateDeassociatePracticeForStaffApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Deassociate practiceid for existing staffId and verify response body")
	public void testResponseCodeToDeassociatePracticeForStaff() throws NullPointerException, IOException {
		logStep("Deassociate practiceid for existing staffId");
		associateDeassociatePracticeForStaffApiResponse = sharedInfo.practicesAssociateToStaff_DL(staffId, practiceId,
				false);
		CCUtils.assertEquals(associateDeassociatePracticeForStaffApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Associate existing practiceid for non-existing StaffId and verify response body")
	public void testResponseCodeToAddPracticeForNonExistingStaff() throws NullPointerException, IOException {
		logStep("Associate existing practiceid for non-existing StaffId");
		associateDeassociatePracticeForStaffApiResponse = sharedInfo.practicesAssociateToStaff_DL(
				Integer.parseInt(testData.getProperty("non.existing.provider.id")), practiceId, true);
		CCUtils.assertEquals(associateDeassociatePracticeForStaffApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(associateDeassociatePracticeForStaffApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Associate non-existing practiceid for existing staffId and verify response body")
	public void testResponseCodeToAddNonExistingPracticeForResource() throws NullPointerException, IOException {
		logStep("Associate non-existing practiceid for existing staffId");
		associateDeassociatePracticeForStaffApiResponse = sharedInfo.practicesAssociateToStaff_DL(staffId,
				Integer.parseInt(testData.getProperty("nonexisting.practiceId")), true);
		CCUtils.assertEquals(associateDeassociatePracticeForStaffApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(associateDeassociatePracticeForStaffApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Associate non-existing practiceid for non-existing staffId and verify response body")
	public void testResponseCodeToAddNonExistingPracticeNonExistingStaffIdForResource()
			throws NullPointerException, IOException {
		logStep("Associate non-existing practiceid for existing staffId");
		associateDeassociatePracticeForStaffApiResponse = sharedInfo.practicesAssociateToStaff_DL(
				Integer.parseInt(testData.getProperty("non.existing.provider.id")),
				Integer.parseInt(testData.getProperty("nonexisting.practiceId")), true);
		CCUtils.assertEquals(associateDeassociatePracticeForStaffApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(associateDeassociatePracticeForStaffApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank input in request payload and verify error message")
	public void testErrorMessageForBlankRequestPayload() throws NullPointerException, IOException {
		logStep("Enter blank input in request payload");
		associateDeassociatePracticeForStaffApiResponse = sharedInfo
				.associateDeassociatePracticeForStaffUsingEmptyPayload_DL(staffId);
		CCUtils.assertEquals(associateDeassociatePracticeForStaffApiResponse.getStatusCode(), 400);
		validation.verifyInvalidRequestValidation(associateDeassociatePracticeForStaffApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter practiceid, staffId as null and verify response code")
	public void testResponseCodeToAddPracticeWithStaffIdAsNull() throws NullPointerException, IOException {
		logStep("Enter practiceid, staffid as null");
		associateDeassociatePracticeForStaffApiResponse = sharedInfo
				.associateDeassociatePracticeForStaffUsingNullValue_DL();
		CCUtils.assertEquals(associateDeassociatePracticeForStaffApiResponse.getStatusCode(), 400);
	}
}