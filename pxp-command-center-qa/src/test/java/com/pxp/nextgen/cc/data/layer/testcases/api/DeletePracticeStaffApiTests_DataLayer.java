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

public class DeletePracticeStaffApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private CreateClientHierarchy_DL clientHierObj;
	Response createNewstaffApiResponse, DeletePracticeStaffTypeApiResponse, associateDeassociatePracticeForStaffApiResponse ;
	int clientId, systemId, enterpriseId, practiceId, staffId;
	String externalId;
	
	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_DL();
       
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);	
		logStep("Create new staff");
		String firstName = testData.getProperty("staff.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("staff.lastname").concat(CCUtils.generateAlphaNumericString(5));
		externalId = CCUtils.generateAlphaNumericString(10);
		createNewstaffApiResponse = sharedInfo.createStaff_DL(clientId, firstName, lastName,
				testData.getProperty("staff.gender"), externalId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewstaffApiResponse);
		CCUtils.assertEquals(createNewstaffApiResponse.getStatusCode(), 200);
	    staffId=createNewstaffApiResponse.jsonPath().get("id");
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 2, description = "Delete staff for practice using valid externalId,practiceId and verify response body")
	public void testDeteteStaffForPracticeFunctionality() throws IOException {
		logStep("Add practiceid for existing staffId");
		associateDeassociatePracticeForStaffApiResponse = sharedInfo
				.associateDeassociatePracticeForStaff_DL(staffId, practiceId, "true");
		CCUtils.assertEquals(associateDeassociatePracticeForStaffApiResponse.getStatusCode(), 200);
		logStep("Delete Practice Staff");
		DeletePracticeStaffTypeApiResponse = sharedInfo.deleteStaffPracticeType_DL(practiceId,
				testData.getProperty("default.solution.id"),externalId);
		CCUtils.assertEquals(DeletePracticeStaffTypeApiResponse.getStatusCode(), 200);	
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 2, description = "Verify response body for invalid practice id")
	public void testErrorMessageForInvalidPracticeId() throws IOException {
		logStep("Add practiceid for existing staffId");
		associateDeassociatePracticeForStaffApiResponse = sharedInfo
				.associateDeassociatePracticeForStaff_DL(staffId, practiceId, "true");
		CCUtils.assertEquals(associateDeassociatePracticeForStaffApiResponse.getStatusCode(), 200);
		logStep("Delete Practice Staff Invalid PracticeId");
		int practiceids=Integer.parseInt(testData.getProperty("api.Non.existing.practice.id"));
		DeletePracticeStaffTypeApiResponse = sharedInfo.deleteStaffPracticeType_DL(practiceids,
				testData.getProperty("default.solution.id"),externalId);
		CCUtils.assertEquals(DeletePracticeStaffTypeApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForStaffPracticeField(DeletePracticeStaffTypeApiResponse, practiceids);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 2, description = "Verify response body for invalid extrenal id")
	public void testErrorMessageForInvalidExternalId() throws IOException {
		logStep("Add practiceid for existing staffId");
		associateDeassociatePracticeForStaffApiResponse = sharedInfo
				.associateDeassociatePracticeForStaff_DL(staffId, practiceId, "true");
		CCUtils.assertEquals(associateDeassociatePracticeForStaffApiResponse.getStatusCode(), 200);
		logStep("Delete Practice Staff with Invalid ExternalId");
		DeletePracticeStaffTypeApiResponse = sharedInfo.deleteStaffPracticeType_DL(practiceId,
				testData.getProperty("default.solution.id"),testData.getProperty("template.product.name"));
		CCUtils.assertEquals(DeletePracticeStaffTypeApiResponse.getStatusCode(), 400);	
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Delete staff for practice for blank responseBody and verify response body")
	public void testToDeleteStaffTypesForBlankResponseBody() throws IOException {
		logStep("Add practiceid for existing resourceId");
		associateDeassociatePracticeForStaffApiResponse = sharedInfo
				.associateDeassociatePracticeForStaff_DL(staffId, practiceId, "true");
		CCUtils.assertEquals(associateDeassociatePracticeForStaffApiResponse.getStatusCode(), 200);
		logStep("Delete staff for practice null responseBody and verify response body");
		DeletePracticeStaffTypeApiResponse = sharedInfo.deleteStaffPracticeType_NullValues_DL();
		CCUtils.assertEquals(DeletePracticeStaffTypeApiResponse.getStatusCode(), 400);
	}	
}
