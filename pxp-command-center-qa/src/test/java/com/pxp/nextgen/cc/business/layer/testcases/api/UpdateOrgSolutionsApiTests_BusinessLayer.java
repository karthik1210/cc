// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.OrgSolutions;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;

import io.restassured.response.Response;

public class UpdateOrgSolutionsApiTests_BusinessLayer extends BaseRest {
	private OrgInfo orgInfo;
	private Validations validation;
	private CreateClientHierarchy_BL clientHierObj;
	Response updateSolutionsApiResponse, fetchOrgSolutionDetailsApiResponse;
	List<Integer> solutionList = null;
	int clientId, systemId, enterpriseId, practiceId, agnosticPracticeId;
	String clientName, clientSapNumber;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		orgInfo = new OrgInfo();
		validation = new Validations();
		clientHierObj = new CreateClientHierarchy_BL();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update org details by adding up only one solution as Active Implementation")
	public void testUpdateOrgDetailsForOneSolution() throws Exception {
		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		agnosticPracticeId = clientHierObj.createAgnosticPractice_BL(systemId);
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_BusinessLayer(productList, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
	}

	@Test(dependsOnMethods = {
			"testUpdateOrgDetailsForOneSolution" }, retryAnalyzer = RetryAnalyzer.class, description = "Update org details by adding up only more than one solutions as Active Implementation,Go-live,Newly purchased")
	public void testUpdateOrgDetailsForMultipleSolution() throws IOException {
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("engage.id"), testData.getProperty("status.go.live")),
				new OrgSolutions(testData.getProperty("forms.id"),
						testData.getProperty("status.active.implementation")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_BusinessLayer(productList, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validate error message when enter non-exitsing org id")
	public void testErrorMsgWithNonExistingOrgId() throws IOException {
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("engage.id"), testData.getProperty("status.go.live")),
				new OrgSolutions(testData.getProperty("forms.id"),
						testData.getProperty("status.active.implementation")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_BusinessLayer(productList, 20);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(updateSolutionsApiResponse, 20);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validate error messag when we enter null as a value in org id")
	public void testErrorMsgWithNullOrgId() throws IOException {
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("engage.id"), testData.getProperty("status.go.live")),
				new OrgSolutions(testData.getProperty("forms.id"),
						testData.getProperty("status.active.implementation")));
		updateSolutionsApiResponse = orgInfo.updateOrgSolutions_OrgNULLValue(productList);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 400);
		validation.verifyResponseForEmptyOrNULLOrMisFormatData(updateSolutionsApiResponse);
	}

	@Test(dependsOnMethods = {
			"testUpdateOrgDetailsForOneSolution" }, retryAnalyzer = RetryAnalyzer.class, description = "Validate error message when we enter null as a value in solutions id")
	public void testErrorMsgWithNullSolutionsId() throws Exception {
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(null, testData.getProperty("status.newly.purchased")),
				new OrgSolutions(null, testData.getProperty("status.go.live")),
				new OrgSolutions(null, testData.getProperty("status.active.implementation")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_BusinessLayer(productList, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForSolutionId(updateSolutionsApiResponse);
	}

	@Test(dependsOnMethods = {
			"testUpdateOrgDetailsForOneSolution" }, retryAnalyzer = RetryAnalyzer.class, description = "Validate error message when we enter null as a value in status")
	public void testErrorMsgWithNullStatus() throws Exception {
		List<OrgSolutions> productList = Arrays.asList(new OrgSolutions(testData.getProperty("Portal.id"), null),
				new OrgSolutions(testData.getProperty("engage.id"), null),
				new OrgSolutions(testData.getProperty("forms.id"), null));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_BusinessLayer(productList, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForStatus(updateSolutionsApiResponse);
	}

	@Test(dependsOnMethods = {
			"testUpdateOrgDetailsForOneSolution" }, retryAnalyzer = RetryAnalyzer.class, description = "Validate error message when we enter null as a value in both solution id and status")
	public void testErrorMsgWithBothNullStatusAndSolutionId() throws Exception {
		List<OrgSolutions> productList = Arrays.asList(new OrgSolutions(null, null), new OrgSolutions(null, null),
				new OrgSolutions(null, null));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_BusinessLayer(productList, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseForSolutionId(updateSolutionsApiResponse);
		validation.verifyErrorResponseForStatus(updateSolutionsApiResponse);
	}

	@Test(dependsOnMethods = {
			"testUpdateOrgDetailsForOneSolution" }, retryAnalyzer = RetryAnalyzer.class, description = "Update org details with invalid url")
	public void testUpdateOrgDetailsForOneSolutionInvalidUrl() throws Exception {
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_BusinessLayer_InvalidUrl(productList, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateSolutionsApiResponse);
	}

	@Test(dependsOnMethods = {
			"testUpdateOrgDetailsForOneSolution" }, retryAnalyzer = RetryAnalyzer.class, description = "Update org details with invalid method call : post")
	public void testUpdateOrgDetailsForOneSolutionInvalidMethodCall() throws Exception {
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_BusinessLayer_InvalidHTTPMethod(productList,
				practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(updateSolutionsApiResponse);
	}
}
