//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
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

public class GetPracticesForStaffApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private ClientInfo clientInfo;
	private Validations validation;
	private OrgInfo orgInfo;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,updateStaffForPracticesResponse,
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
		logStep("Create staff to get staffId");
		staffNameOne = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		lastNameOne = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalIdOne = CCUtils.generateAlphaNumericString(10);
		createNewstaffApiResponse = sharedInfo.createStaff_DL(clientId, staffNameOne, lastNameOne,
				testData.getProperty("staff.gender"), externalIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewstaffApiResponse);
		CCUtils.assertEquals(createNewstaffApiResponse.getStatusCode(), 200);
		StaffIdOne = createNewstaffApiResponse.jsonPath().get("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify 200 status code and response body by entering existing staffId to get Practices For Staff")
	public void testGetPracticesForExistingStaffAssociatedWithPractices() throws IOException {
		logStep("associate practices for staff");
		updateStaffForPracticesResponse = sharedInfo.practicesAssociateToStaff_DL(staffId, practiceId, true);
		CCUtils.assertEquals(updateStaffForPracticesResponse.getStatusCode(), 200);
		logStep("Enter existing staffId with associated Practices");
		getPracticesAssociatedToStaffApiResponse = sharedInfo.getPracticesAssociatedToStaff_DL(staffId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticesAssociatedToStaffApiResponse);
		CCUtils.assertEquals(getPracticesAssociatedToStaffApiResponse.getStatusCode(), 200);
		ArrayList<Integer> practiceIds = new ArrayList<Integer>();
		practiceIds.add(practiceId);
		validation.verifyResponseBodyForStaffAssociatedToPractices(getPracticesAssociatedToStaffApiResponse, practiceIds);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify 204 status code by entering existing staffId with no associated Practices")
	public void testGetPracticesForExistingStaffNotAssociatedWithPractices() throws IOException {
		logStep("Enter existing staffId with no associated Practices");
		getPracticesAssociatedToStaffApiResponse = sharedInfo.getPracticesAssociatedToStaff_DL(StaffIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticesAssociatedToStaffApiResponse);
		CCUtils.assertEquals(getPracticesAssociatedToStaffApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify 204 status code by entering non-existing staffId to get Practices For Staff")
	public void testResponseForNonExistingStaffId() throws IOException {
		logStep("Enter non-existing staffId in staffId field");
		getPracticesAssociatedToStaffApiResponse = sharedInfo
				.getPracticesAssociatedToStaff_DL(Integer.parseInt(testData.getProperty("non.existing.provider.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticesAssociatedToStaffApiResponse);
		CCUtils.assertEquals(getPracticesAssociatedToStaffApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify 400 status code and response body by entering staffId as null")
	public void testResponseForStaffIdAsNull() throws IOException {
		logStep("Enter null value in staffId field");
		getPracticesAssociatedToStaffApiResponse = sharedInfo.getPracticesAssociatedToStaff_NullValues_DL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticesAssociatedToStaffApiResponse);
		CCUtils.assertEquals(getPracticesAssociatedToStaffApiResponse.getStatusCode(), 400);
	}
}
