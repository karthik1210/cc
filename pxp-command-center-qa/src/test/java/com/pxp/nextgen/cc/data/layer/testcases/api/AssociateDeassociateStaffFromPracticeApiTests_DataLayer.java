//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AssociateDeassociateStaffFromPracticeApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_DL clientHierObj;
	Response createProviderApiResponse, createNewstaffApiResponse, getPracticesAssociatedToStaffApiResponse,
			associateDeassociatePracticeForStaffApiResponse;
	int clientId, practiceId, staffId, StaffIdOne, systemId, enterpriseId;
	String externalId, staffName, lastName, staffNameOne, lastNameOne;

	@BeforeClass
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);	
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