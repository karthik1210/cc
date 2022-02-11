// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

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

public class GetOrganizationDetailsApiTests_BusinessLayer extends BaseRest {

	private OrgInfo orgInfo;
	private Validations validation;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse, createClientApiResponse,
			getOrgIdApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		orgInfo = new OrgInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter system id in orgId field and validate response body")
	public void testFetchingSystemOrgId() throws IOException {
		String systemName = testData.getProperty("api.system.name").concat(CCUtils.generateRandomString());
		String systemSapNumber = testData.getProperty("api.system.sapnumber").concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createSystem(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), systemName, testData.getProperty("api.system.orgtype"),
				Integer.parseInt(testData.getProperty("api.Parentid")), testData.getProperty("api.parenttype"),
				systemSapNumber, testData.getProperty("api.system.timezoneid"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createSystemApiResponse);
		CCUtils.assertEquals(createSystemApiResponse.getStatusCode(), 200);
		validation.verifyCreateSystemResponseBody(createSystemApiResponse, systemName);
		int orgId = createSystemApiResponse.jsonPath().get("orgId");
		logStep("Fetching System Id Response body");
		getOrgIdApiResponse = orgInfo.getOrgId(orgId);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(getOrgIdApiResponse);
		CCUtils.assertEquals(getOrgIdApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgIdResponseBody(getOrgIdApiResponse, orgId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter enterprise id in orgId field and validate response body")
	public void testFetchingEnterpriseOrgId() throws IOException {
		String enterpriseName = testData.getProperty("api.enterprise.name").concat(CCUtils.generateRandomString());
		String enterpriseSapNumber = testData.getProperty("api.enterprise.sapnumber")
				.concat(CCUtils.generateRandomString());
		createEnterpriseApiResponse = orgInfo.createEnterprise(testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.displayname"), enterpriseName,
				testData.getProperty("api.enterprise.orgtype"),
				Integer.parseInt(testData.getProperty("api.enterprise.parentid")),
				testData.getProperty("api.enterprise.parenttype"), enterpriseSapNumber,
				testData.getProperty("api.enterprise.timezoneid"));

		logStep("Validating response information");
		CCUtils.responseTimeValidation(createEnterpriseApiResponse);
		CCUtils.assertEquals(createEnterpriseApiResponse.getStatusCode(), 200);
		validation.verifyCreateEnterpriseResponseBody(createEnterpriseApiResponse, enterpriseName);
		int orgId = createEnterpriseApiResponse.jsonPath().get("orgId");
		logStep("Fetching Enterprise Id Response body");
		getOrgIdApiResponse = orgInfo.getOrgId(orgId);
		CCUtils.responseTimeValidation(getOrgIdApiResponse);
		CCUtils.assertEquals(getOrgIdApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgIdResponseBody(getOrgIdApiResponse, orgId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter practice id in orgId field and validate response body")
	public void testFetchingPracticeOrgId() throws IOException {
		String practiceName = testData.getProperty("api.practice.name").concat(CCUtils.generateRandomString());
		String practiceSapNumber = testData.getProperty("api.practice.sapnumber")
				.concat(CCUtils.generateRandomString());
		createPracticeApiResponse = orgInfo.createPractice(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), practiceName,
				testData.getProperty("api.practice.orgtype"),
				Integer.parseInt(testData.getProperty("api.practice.parentid")),
				testData.getProperty("api.practice.parenttype"), practiceSapNumber,
				testData.getProperty("api.practice.timezoneid"));

		logStep("Validating response information");
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		validation.verifyCreatePracticeResponseBody(createPracticeApiResponse, practiceName);
		int orgId = createPracticeApiResponse.jsonPath().get("orgId");
		logStep("Fetching Practice Id Response body");
		getOrgIdApiResponse = orgInfo.getOrgId(orgId);
		CCUtils.responseTimeValidation(getOrgIdApiResponse);
		CCUtils.assertEquals(getOrgIdApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgIdResponseBody(getOrgIdApiResponse, orgId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid org id in orgId field and validate response body")
	public void testFetchingErrorResponseForInvalidOrgId() throws IOException {
		getOrgIdApiResponse = orgInfo.getOrgId(Integer.parseInt(testData.getProperty("api.org.invalid.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgIdApiResponse);
		CCUtils.assertEquals(getOrgIdApiResponse.getStatusCode(), 500);
		validation.verifyInvalidServerError(getOrgIdApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter client id in orgId field and validate response body")
	public void testFetchingErrorResponseIfEnteredClientIdInOrgIdFiled() throws IOException {
		getOrgIdApiResponse = orgInfo.getOrgId(Integer.parseInt(testData.getProperty("api.org.client.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgIdApiResponse);
		CCUtils.assertEquals(getOrgIdApiResponse.getStatusCode(), 500);
		validation.verifyInvalidServerError(getOrgIdApiResponse);
	}

}
