// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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

public class UpdateOrgSolutionsApiTests_DataLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	Response updateSolutionsApiResponse, fetchOrgSolutionDetailsApiResponse, createClientApiResponse,
			createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse,
			createAgnosticPracticeApiResponse;
	List<Integer> solutionList = null;
	int clientId, systemId, enterpriseId, practiceId, agnosticPracticeId;
	String clientName, clientSapNumber;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		orgInfo = new OrgInfo();
		clientInfo = new ClientInfo();
		validation = new Validations();
	}

	public int createClient() throws Exception {
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient_DataLayer(ccUser.getActive(), clientName, clientSapNumber, "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	public int createSystemNode(int clientId) throws Exception {
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "",
				testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), "", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createSystemApiResponse.jsonPath().get("orgId");
	}

	public int createEnterpriseNode(int SystemId) throws Exception {
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"), "",
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"), SystemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createEnterpriseApiResponse.jsonPath().get("orgId");
	}

	public int createPracticeNode(int enterpriseId) throws Exception {
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse.jsonPath().get("orgId");
	}
	
	public int createAgnosticActivePracticeNode(int systemId) throws Exception {
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), systemId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse.jsonPath().get("orgId");
	}

	public int createAgnosticInActivePracticeNode(int systemId) throws Exception {
		createPracticeApiResponse = orgInfo.createOrg_DataLayer("false",
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), systemId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse.jsonPath().get("orgId");
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update org details by adding up only one solution as Active Implementation")
	public void testUpdateOrgDetailsForOneSolution() throws Exception {
		clientId = createClient();
		systemId = createSystemNode(clientId);
		enterpriseId = createEnterpriseNode(systemId);
		practiceId = createPracticeNode(enterpriseId);
		agnosticPracticeId = createAgnosticActivePracticeNode(systemId);
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update org details by adding up only one solution as Active Implementation for active agnostic practice")
	public void testUpdateOrgDetailsForOneSolutionForActiveAgnosticPractice() throws Exception {
		clientId = createClient();
		systemId = createSystemNode(clientId);
		agnosticPracticeId = createAgnosticActivePracticeNode(systemId);
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, agnosticPracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update org details by adding up only one solution as Active Implementation for active agnostic practice")
	public void testUpdateOrgDetailsForOneSolutionForInactiveAgnosticPractice() throws Exception {
		clientId = createClient();
		systemId = createSystemNode(clientId);
		agnosticPracticeId = createAgnosticInActivePracticeNode(systemId);
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, agnosticPracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
	}
	
	@Test(dependsOnMethods = {
			"testUpdateOrgDetailsForOneSolution" }, retryAnalyzer = RetryAnalyzer.class, description = "Update org details by adding up only more than one solutions as Active Implementation,Go-live,Newly purchased")
	public void testUpdateOrgDetailsForMultipleSolutions() throws IOException {
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("engage.id"), testData.getProperty("status.go.live")),
				new OrgSolutions(testData.getProperty("forms.id"),
						testData.getProperty("status.active.implementation")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, practiceId);
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
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, 20);
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
		updateSolutionsApiResponse = orgInfo.updateOrgSolutions_OrgNULLValue_DL(productList);
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
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, practiceId);
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
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, practiceId);
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
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, practiceId);
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
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer_InvalidUrl(productList, practiceId);
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
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer_InvalidHTTPMethod(productList,
				practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(updateSolutionsApiResponse);
	}
}
