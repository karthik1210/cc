// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.OrgSolutions;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetSolutionsByOrgIdApiTest_DataLayer extends BaseRest {
	private CreateClientHierarchy_DL clientHierObj;
	private OrgInfo orgInfo;
	private Validations validation;
	Response updateSolutionsApiResponse, fetchOrgSolutionDetailsApiResponse;
	int clientId, systemId, enterpriseId, practiceId, agnosticPracticeId;
	String clientName, clientSapNumber;
	List<OrgSolutions> finalList;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		orgInfo = new OrgInfo();
		validation = new Validations();
		clientHierObj = new CreateClientHierarchy_DL();
	}

	public void updateOrgSolutions(int orgId) throws IOException {
		finalList = new ArrayList<OrgSolutions>();
		finalList.add(new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.on.hold")));
		finalList.add(new OrgSolutions(testData.getProperty("forms.id"),
				testData.getProperty("status.active.implementation")));
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(finalList, orgId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing org id in orgId field and verify error message for org id field")
	public void testErrorMessageForNonExistingOrgId() throws Exception {
		logStep("Enter non existing org id and validate error message");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(
				Integer.parseInt(testData.getProperty("api.org.invalid.id")), testData.getProperty("get.org.type"));
		logStep("Validating response information");
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(fetchOrgSolutionDetailsApiResponse);
		validation.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(fetchOrgSolutionDetailsApiResponse,
				Integer.parseInt(testData.getProperty("api.org.invalid.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter incorrect type and existing org id in orgId field and verify status code")
	public void testStatusCodeWhenEnterIncorrectTypeAndExistingOrgId() throws Exception {
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		updateOrgSolutions(practiceId);
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId, "incorrect");
		logStep("Validating response information");
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 204);
		CCUtils.responseTimeValidation(fetchOrgSolutionDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws Exception {
		logStep("Enter invalid url  and validate error message");
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		updateOrgSolutions(practiceId);
		fetchOrgSolutionDetailsApiResponse = orgInfo.getSolutionOrgIdUsingInvalidUrl_DataLayer(practiceId,
				testData.getProperty("get.org.type"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgSolutionDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchOrgSolutionDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid http method and validate status code")
	public void testErrorMessageForInvalidHTTPMethod() throws IOException {
		logStep("Enter clientId instead of org id in orgId field and validate error message");
		int clientId = Integer.parseInt(testData.getProperty("api.existing.client.id"));
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer_InvalidHTTPMethod(clientId,
				testData.getProperty("get.org.type"));
		logStep("Validating response information");
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(fetchOrgSolutionDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Test for fetching solutions for correct org id")
	public void testForFetchingSolutionsForOrgId() throws Exception {
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		updateOrgSolutions(practiceId);
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId,
				testData.getProperty("get.org.type"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgSolutionDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, finalList);
	}

}
