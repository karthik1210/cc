// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetStaffApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private ClientInfo clientInfo;
	Response createNewstaffApiResponse, createClientApiResponse, getStaffApiResponse;
	int clientId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		clientId = createClient();
	}

	public int createClient() throws NumberFormatException, NullPointerException, IOException {
		logStep("Create client using valid details in client name, sap number, active, testclient, client type and timezone field");
		String clientName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5))));
		String clientSapNumber = CCUtils
				.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.client.sapnumber")
						.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5))));
		String testFlag = testData.getProperty("api.client.testclient");
		String clientTimezoneId = testData.getProperty("api.client.timezoneid");
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testFlag, Integer.parseInt(testData.getProperty("api.client.type")),
				clientTimezoneId, testData.getProperty("ehr.identifier"), testData.getProperty("pm.identifier"));
		return createClientApiResponse.jsonPath().getInt("clientId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing StaffId and verify response body")
	public void testToGetStaffForExistingStaffId() throws IOException {
		logStep("Create new staff");
		String firstName = testData.getProperty("staff.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("staff.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createNewstaffApiResponse = sharedInfo.createStaff_DL(clientId, firstName, lastName,
				testData.getProperty("staff.gender"), externalId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewstaffApiResponse);
		CCUtils.assertEquals(createNewstaffApiResponse.getStatusCode(), 200);
		int staffId= createNewstaffApiResponse.jsonPath().getInt("id");
		logStep("Enter existing StaffId and verify response body");
		getStaffApiResponse=sharedInfo.getStaff_DL(staffId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStaffApiResponse);
		CCUtils.assertEquals(getStaffApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForStaffDetail(getStaffApiResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing StaffId and verify response body")
	public void testErrorMessageForNonExistingStaffId() throws IOException {
		logStep("Enter non-existing StaffId and verify response body");
		int staffId= Integer.parseInt(testData.getProperty("api.nonexisting.id"));
		getStaffApiResponse=sharedInfo.getStaff_DL(staffId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStaffApiResponse);
		CCUtils.assertEquals(getStaffApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyInvalidStaffId(getStaffApiResponse,staffId);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value in StaffId field and verify response code")
	public void testErrorMessageForNullStaffId() throws IOException {
		logStep("Enter null value in StaffId field and verify response code");
		getStaffApiResponse=sharedInfo.getStaff_DL_Null_value();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getStaffApiResponse);
		CCUtils.assertEquals(getStaffApiResponse.getStatusCode(), 400);
	}
}
