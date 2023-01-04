//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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

public class GetPracticesForStaffApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private ClientInfo clientInfo;
	private Validations validation;
	private OrgInfo orgInfo;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createNewstaffApiResponse, getPracticesAssociatedToStaffApiResponse,
			updateStaffForPracticesResponse;
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
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
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
		logStep("Create staff to get staffId");
		staffNameOne = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		lastNameOne = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalIdOne = CCUtils.generateAlphaNumericString(10);
		createNewstaffApiResponse = sharedInfo.createStaff_BL(clientId, staffNameOne, lastNameOne,
				testData.getProperty("staff.gender"), externalIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewstaffApiResponse);
		CCUtils.assertEquals(createNewstaffApiResponse.getStatusCode(), 200);
		StaffIdOne = createNewstaffApiResponse.jsonPath().get("id");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify 200 status code and response body by entering existing staffId to get Practices For Staff")
	public void testGetPracticesForExistingStaffAssociatedWithPractices() throws IOException {
		logStep("associate practices for staff");
		updateStaffForPracticesResponse = sharedInfo.practicesAssociateToStaff_BL(staffId, practiceId, true);
		CCUtils.assertEquals(updateStaffForPracticesResponse.getStatusCode(), 200);
		logStep("Enter existing staffId with associated Practices");
		getPracticesAssociatedToStaffApiResponse = sharedInfo.getPracticesAssociatedToStaff_BL(staffId);
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
		getPracticesAssociatedToStaffApiResponse = sharedInfo.getPracticesAssociatedToStaff_BL(StaffIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticesAssociatedToStaffApiResponse);
		CCUtils.assertEquals(getPracticesAssociatedToStaffApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify 204 status code by entering non-existing staffId to get Practices For Staff")
	public void testResponseForNonExistingStaffId() throws IOException {
		logStep("Enter non-existing staffId in staffId field");
		getPracticesAssociatedToStaffApiResponse = sharedInfo
				.getPracticesAssociatedToStaff_BL(Integer.parseInt(testData.getProperty("non.existing.provider.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticesAssociatedToStaffApiResponse);
		CCUtils.assertEquals(getPracticesAssociatedToStaffApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify 400 status code and response body by entering staffId as null")
	public void testResponseForStaffIdAsNull() throws IOException {
		logStep("Enter null value in staffId field");
		getPracticesAssociatedToStaffApiResponse = sharedInfo.getPracticesAssociatedToStaff_NullValues_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getPracticesAssociatedToStaffApiResponse);
		CCUtils.assertEquals(getPracticesAssociatedToStaffApiResponse.getStatusCode(), 400);
	}

}
