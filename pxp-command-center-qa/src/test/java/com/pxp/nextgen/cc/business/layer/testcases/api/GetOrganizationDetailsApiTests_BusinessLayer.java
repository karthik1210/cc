// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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
	Response fetchOrgDetailsApiResponse;
	private ClientInfo clientInfo;
	private CreateClientHierarchy_BL clientHierObj;
	int clientId, systemId, enterpriseId, practiceId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		orgInfo = new OrgInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		clientHierObj = new CreateClientHierarchy_BL();
		   
		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter system id in orgId field and validate response body")
	public void testFetchSystemDetailsUsingSystemId() throws IOException {
		logStep("Fetch newly created system details using system id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgId(systemId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgDetailsApiResponse, systemId,
				(testData.getProperty("api.system.name")), testData.getProperty("api.system.orgtype"),
				(testData.getProperty("api.system.name")), "true", "", "");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter enterprise id in orgId field and validate response body")
	public void testFetchEnterpriseDetailsUsingEnterpriseId() throws IOException {
		logStep("Fetch newly created enterprise details using enterprise id");
		fetchOrgDetailsApiResponse = orgInfo.getOrgId(enterpriseId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgDetailsApiResponse, enterpriseId,
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"),
				 testData.getProperty("api.enterprise.name"),"true", testData.getProperty("api.enterprise.timezoneid"), testData.getProperty("api.enterprise.timezonedisplayname"));
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter practice id in orgId field and validate response body")
	public void testFetchingPracticeOrgId() throws IOException {

		logStep("Fetching Practice Id Response body");
		fetchOrgDetailsApiResponse = orgInfo.getOrgId(practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgDeatilsApiResponseBody(fetchOrgDetailsApiResponse, practiceId,
				testData.getProperty("api.practice.name"), testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				"true", testData.getProperty("api.practice.timezoneid"),testData.getProperty("api.practice.timezonedisplayname")  );
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid org id in orgid field and validate response body")
	public void testErrorResponseForInvalidOrgId() throws IOException {
		int orgId = Integer.parseInt(testData.getProperty("api.org.invalid.id"));
		fetchOrgDetailsApiResponse = orgInfo.getOrgId(orgId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(fetchOrgDetailsApiResponse, orgId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid org id in orgid field and validate response body")
	public void testErrorResponseWhenUserEnterClientIdInOrgIdField() throws IOException {
		int orgId = Integer.parseInt(testData.getProperty("api.nonexisting.id"));
		fetchOrgDetailsApiResponse = orgInfo.getOrgId(orgId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgDetailsApiResponse.getStatusCode(), 400);
	}
}
