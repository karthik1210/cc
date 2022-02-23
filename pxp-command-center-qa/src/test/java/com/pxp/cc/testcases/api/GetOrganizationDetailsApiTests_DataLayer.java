// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetOrganizationDetailsApiTests_DataLayer extends BaseRest {

	private OrgInfo orgInfo;
	private Validations validation;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse, createClientApiResponse,
			fetchOrgDetailsApiResponse;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		orgInfo = new OrgInfo();
		validation = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter system id in orgId field and validate response body")
	public void testFetchSystemDetailsUsingSystemId() throws IOException {
		String systemName = testData.getProperty("api.system.name").concat(CCUtils.generateRandomString());
		String systemSapNumber = testData.getProperty("api.system.sapnumber").concat(CCUtils.generateRandomString());
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), systemName, testData.getProperty("api.system.orgtype"),
				Integer.parseInt(testData.getProperty("api.parentid")), testData.getProperty("api.parenttype"),
				systemSapNumber, testData.getProperty("api.system.timezoneid"));

		int orgId = createSystemApiResponse.jsonPath().get("orgId");
		logStep("Fetch newly created system details using system id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(orgId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgDetailsApiResponse, orgId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter enterprise id in orgId field and validate response body")
	public void testFetchEnterpriseDetailsUsingEnterpriseId() throws IOException {
		String enterpriseName = testData.getProperty("api.enterprise.name").concat(CCUtils.generateRandomString());
		String enterpriseSapNumber = testData.getProperty("api.enterprise.sapnumber")
				.concat(CCUtils.generateRandomString());
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.displayname"), enterpriseName,
				testData.getProperty("api.enterprise.orgtype"),
				Integer.parseInt(testData.getProperty("api.enterprise.parentid")),
				testData.getProperty("api.enterprise.parenttype"), enterpriseSapNumber,
				testData.getProperty("api.enterprise.timezoneid"));

		int orgId = createEnterpriseApiResponse.jsonPath().get("orgId");
		logStep("Fetch newly created enterprise details using enterprise id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(orgId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgDetailsApiResponse, orgId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter practice id in orgId field and validate response body")
	public void testFetchingPracticeOrgId() throws IOException {
		String practiceName = testData.getProperty("api.practice.name").concat(CCUtils.generateRandomString());
		String practiceSapNumber = testData.getProperty("api.practice.sapnumber")
				.concat(CCUtils.generateRandomString());
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), practiceName,
				testData.getProperty("api.practice.orgtype"),
				Integer.parseInt(testData.getProperty("api.practice.parentid")),
				testData.getProperty("api.practice.parenttype"), practiceSapNumber,
				testData.getProperty("api.practice.timezoneid"));

		int orgId = createPracticeApiResponse.jsonPath().get("orgId");
		logStep("Fetching Practice Id Response body");
		fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(orgId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgDetailsApiResponse, orgId);
	}

	@DataProvider(name = "invalidOrgId")
	public Object[][] getOrgId() throws Exception {
		return new Object[][] { { testData.getProperty("api.org.invalid.id") },
				{ testData.getProperty("api.org.client.id") } };
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidOrgId", description = "Enter invalid org id in orgid field and validate response body")
	public void testErrorResponseForInvalidOrgId(String invalidOrgId) throws IOException {
		int orgId = Integer.parseInt(invalidOrgId);
		fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(orgId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForClientIdInOrgIdField(fetchOrgDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		fetchOrgDetailsApiResponse = orgInfo
				.getOrgIdUsingInvalidUrl_DataLayer(Integer.parseInt(testData.getProperty("api.existing.orgid")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchOrgDetailsApiResponse);
	}

}
