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

public class GetOrganizationDetailsApiTests_DataLayer extends BaseRest {

	private OrgInfo orgInfo;
	private ClientInfo clientInfo;
	private Validations validation;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse, createClientApiResponse,
			fetchOrgDetailsApiResponse;
	String clientName, clientSapNumber, testFlag, clientTimezoneId, systemName, enterpriseName, practiceName;
	int clientId, systemId, enterpriseId, practiceId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		orgInfo = new OrgInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();

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
				clientTimezoneId, testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
		clientId = createClientApiResponse.jsonPath().getInt("clientId");
		systemName = testData.getProperty("api.system.name").concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "", systemName,
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"), "",
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		enterpriseName = testData.getProperty("api.enterprise.name").concat(CCUtils.generateRandomString());
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"), "",
				enterpriseName, testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		practiceName = testData.getProperty("api.practice.name").concat(CCUtils.generateRandomString());
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), practiceName,
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));

		practiceId = createPracticeApiResponse.jsonPath().getInt("orgId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 0, description = "Enter system id in orgId field and validate response body")
	public void testFetchSystemDetailsUsingSystemId() throws IOException {

		logStep("Fetch newly created system details using system id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(systemId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgDetailsApiResponse, systemId, systemName,
				testData.getProperty("api.system.orgtype"), systemName, "true", "", "");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 1, description = "Enter enterprise id in orgId field and validate response body")
	public void testFetchEnterpriseDetailsUsingEnterpriseId() throws IOException {
		logStep("Fetch newly created enterprise details using enterprise id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(enterpriseId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgDetailsApiResponse, enterpriseId, enterpriseName,
				testData.getProperty("api.enterprise.orgtype"), enterpriseName, "true",
				testData.getProperty("api.enterprise.timezoneid"),
				testData.getProperty("api.enterprise.timezonedisplayname"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 2, description = "Enter practice id in orgId field and validate response body")
	public void testFetchingPracticeOrgId() throws IOException {
		logStep("Fetching Practice Id Response body");
		fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgDetailsApiResponse, practiceId, practiceName,
				testData.getProperty("api.practice.orgtype"),  testData.getProperty("api.practice.displayname"), "true",
				testData.getProperty("api.practice.timezoneid"),
				testData.getProperty("api.practice.timezonedisplayname"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid org id in orgid field and validate response body")
	public void testErrorResponseForInvalidOrgId() throws IOException {
		int orgId = Integer.parseInt(testData.getProperty("api.org.invalid.id"));
		fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(orgId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(fetchOrgDetailsApiResponse, orgId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 3, description = "Enter client id in orgid field and validate response body")
	public void testErrorResponseForClientIdInOrgIdField() throws IOException {
		fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(clientId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 4, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		fetchOrgDetailsApiResponse = orgInfo.getOrgIdUsingInvalidUrl_DataLayer(systemId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchOrgDetailsApiResponse);
	}

}
