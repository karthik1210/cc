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

public class CreateStaffApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private ClientInfo clientInfo;
	Response createNewstaffApiResponse, createClientApiResponse;
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in mandatory fields and verify response body")
	public void testAddNewStaffFunctionality() throws IOException {
		logStep("Create new staff");
		String firstName = testData.getProperty("staff.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("staff.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createNewstaffApiResponse = sharedInfo.createStaff_DL(clientId, firstName, lastName,
				testData.getProperty("staff.gender"), externalId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewstaffApiResponse);
		CCUtils.assertEquals(createNewstaffApiResponse.getStatusCode(), 200);
		validation.verifyResponseBodyForStaff(createNewstaffApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test maximum character functionality for external id field")
	public void testAddNewStaffFunctionalityUsingMaximumExternalIdFieldSize() throws IOException {
		logStep("Enter 50 characters in external id field");
		String firstName = testData.getProperty("staff.firstname").concat(CCUtils.generateAlphaNumericString(10));
		String lastName = testData.getProperty("staff.lastname").concat(CCUtils.generateAlphaNumericString(10));
		String externalId = CCUtils.generateAlphaNumericString(51);
		createNewstaffApiResponse = sharedInfo.createStaff_DL(clientId, firstName, lastName,
				testData.getProperty("staff.gender"), externalId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewstaffApiResponse);
		CCUtils.assertEquals(createNewstaffApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForExternalIdField(createNewstaffApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, enabled = false, description = "Test maximum character functionality for firstName, lastName fields")
	public void testAddNewStaffFunctionalityUsingMaximumFirstNameLastnameFieldSize() throws IOException {
		logStep("Enter 100 characters in firstName, lastName fields");
		String firstNameOne = testData.getProperty("staff.firstname").concat(CCUtils.generateAlphaNumericString(101));
		String lastNameOne = testData.getProperty("staff.lastname").concat(CCUtils.generateAlphaNumericString(101));
		String externalIdOne = CCUtils.generateAlphaNumericString(5);
		createNewstaffApiResponse = sharedInfo.createStaff_DL(clientId, firstNameOne, lastNameOne,
				testData.getProperty("staff.gender"), externalIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewstaffApiResponse);
		CCUtils.assertEquals(createNewstaffApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid client id in clientId field and verify error response body")
	public void testErrorResponseForNonExistingClientId() throws IOException {
		logStep("Enter valid data in mandatory fields and non existing client id in clientId field");
		String firstNameOne = testData.getProperty("staff.firstname").concat(CCUtils.generateAlphaNumericString(10));
		String lastNameOne = testData.getProperty("staff.lastname").concat(CCUtils.generateAlphaNumericString(11));
		String externalIdOne = CCUtils.generateAlphaNumericString(5);
		createNewstaffApiResponse = sharedInfo.createStaff_DL(
				Integer.parseInt(testData.getProperty("api.nonexisting.client.id")), firstNameOne, lastNameOne,
				testData.getProperty("staff.gender"), externalIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewstaffApiResponse);
		CCUtils.assertEquals(createNewstaffApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidOrgId(createNewstaffApiResponse,
				testData.getProperty("api.nonexisting.client.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null value in mandatory fields and verify error response body")
	public void testRequiredErrorMessageForNullValueMandatoryFields() throws IOException {
		logStep("Enter null value in mandatory fields and verify error response body");
		createNewstaffApiResponse = sharedInfo.createStaff_DL(clientId, null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createNewstaffApiResponse);
		CCUtils.assertEquals(createNewstaffApiResponse.getStatusCode(), 400);
	}

}
