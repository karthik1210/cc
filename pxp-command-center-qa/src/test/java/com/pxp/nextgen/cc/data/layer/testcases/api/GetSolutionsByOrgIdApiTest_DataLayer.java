// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
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

public class GetSolutionsByOrgIdApiTest_DataLayer extends BaseRest {

	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	Response createClientApiResponse, createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			updateSolutionsApiResponse, fetchOrgSolutionDetailsApiResponse;
	int clientId, systemId, enterpriseId;
	String practiceName;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		validation = new Validations();
		clientId = createClient();
	}

	public int createClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		logStep("Create a client of name : " + clientName);
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(ccUser.getActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), ccUser.getClientType(), ccUser.getTimezoneId());
		return createClientApiResponse.jsonPath().getInt("clientId");
	}

	public int createPracticeNode(int parentId, String active, String parentType) throws IOException {
		practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		String practiceSapNumber = ccUser.getPracticeSapNumberApi().concat(CCUtils.generateRandomString());
		Response createPracticeApiResponse = orgInfo.createOrgNode_DataLayer(practiceName,
				ccUser.getPracticeOrgTypeApi(), ccUser.getPracticeDisplayNameApi(), practiceSapNumber, active,
				ccUser.getPracticeTimezoneIdApi(), parentId, parentType,
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		return createPracticeApiResponse.jsonPath().getInt("orgId");
	}

	public int createNgePracticeNode(String active) throws IOException {
		Response createSystemApiResponse = orgInfo.createOrgNode_DataLayer(testData.getProperty("api.system.name"),
				ccUser.getSystemOrgTypeApi(), ccUser.getSystemDisplayNameApi(),
				testData.getProperty("api.system.sapnumber"), testData.getProperty("api.system.active"),
				ccUser.getSystemTimezoneIdApi(), clientId, testData.getProperty("api.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		systemId = createSystemApiResponse.jsonPath().getInt("orgId");

		Response createEnterpriseApiResponse = orgInfo.createOrgNode_DataLayer(
				testData.getProperty("api.enterprise.name"), ccUser.getEnterpriseOrgTypeApi(),
				ccUser.getEnterpriseDisplayNameApi(), testData.getProperty("api.enterprise.sapnumber"),
				testData.getProperty("api.enterprise.active"), ccUser.getEnterpriseTimezoneIdApi(), systemId,
				ccUser.getSystemOrgTypeApi(), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		return createPracticeNode(enterpriseId, active, testData.getProperty("api.enterprise.orgtype"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing org id in orgId field and verify error message for org id field")
	public void testErrorMessageForNonExistingOrgId() throws IOException {
		logStep("Enter non existing org id and validate error message");
		int orgId = Integer.parseInt(testData.getProperty("api.org.invalid.id"));
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(orgId);
		logStep("Validating response information");
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(fetchOrgSolutionDetailsApiResponse);
		validation.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(fetchOrgSolutionDetailsApiResponse, orgId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		logStep("Enter invalid url  and validate error message");
		int orgId = Integer.parseInt(testData.getProperty("api.existing.orgid"));
		fetchOrgSolutionDetailsApiResponse = orgInfo.getSolutionOrgIdUsingInvalidUrl_DataLayer(orgId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgSolutionDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(fetchOrgSolutionDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing client id in orgId field and verify error message for org id field")
	public void testErrorMessageWhenSearchClientIdInOrgIdField() throws IOException {
		logStep("Enter clientId instead of org id in orgId field and validate error message");
		int clientId = Integer.parseInt(testData.getProperty("api.existing.client.id"));
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(clientId);
		logStep("Validating response information");
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 400);
		CCUtils.responseTimeValidation(fetchOrgSolutionDetailsApiResponse);
		validation.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(fetchOrgSolutionDetailsApiResponse,
				clientId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create active nge practice with/without solutions verify response & status code ")
	public void verifySolutionsDetailsForActiveNgePracticeWithoutOrWithSolutions() throws IOException {
		int activeNgePracticeId = createNgePracticeNode("true");
		List<OrgSolutions> defaultSolutionList = Arrays.asList(
				new OrgSolutions(testData.getProperty("default.solution.id"),
						testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("forms.id"), testData.getProperty("status.newly.purchased")));
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(activeNgePracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgSolutionDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, activeNgePracticeId,
				practiceName, defaultSolutionList);
		logStep("Fetch solutions details using enterprise id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(enterpriseId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgSolutionDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, activeNgePracticeId,
				practiceName, defaultSolutionList);
		logStep("Fetch solutions details using system id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(systemId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgSolutionDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, activeNgePracticeId,
				practiceName, defaultSolutionList);

		logStep("Purchase solutions for active practice node");
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("forms.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, activeNgePracticeId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(activeNgePracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgSolutionDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 200);
		List<OrgSolutions> finalList = new ArrayList<OrgSolutions>();
		finalList.addAll(productList);
		finalList.addAll(defaultSolutionList);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, activeNgePracticeId,
				practiceName, finalList);
		logStep("Fetch solutions details using enterprise id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(enterpriseId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgSolutionDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, activeNgePracticeId,
				practiceName, finalList);
		logStep("Fetch solutions details using system id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(systemId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgSolutionDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, activeNgePracticeId,
				practiceName, finalList);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create inactive nge practice with/without solution and verify response & status code ")
	public void verifySolutionsDetailsForInactiveNgePracticeWithoutSolution() throws IOException {
		int inactiveNgePracticeId = createNgePracticeNode("false");
		List<OrgSolutions> defaultSolutionList = Arrays.asList(
				new OrgSolutions(testData.getProperty("default.solution.id"),
						testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("forms.id"), testData.getProperty("status.newly.purchased")));
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(inactiveNgePracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgSolutionDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 200);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, inactiveNgePracticeId,
				practiceName, defaultSolutionList);
		logStep("Fetch solutions details using enterprise id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(enterpriseId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgSolutionDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 204);
		logStep("Fetch solutions details using system id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(systemId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgSolutionDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 204);

		logStep("Purchase solutions for inactive practice node");
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("forms.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, inactiveNgePracticeId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(inactiveNgePracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgSolutionDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 200);
		List<OrgSolutions> finalList = new ArrayList<OrgSolutions>();
		finalList.addAll(productList);
		finalList.addAll(defaultSolutionList);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, inactiveNgePracticeId,
				practiceName, finalList);
		logStep("Fetch solutions details using enterprise id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(enterpriseId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgSolutionDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 204);
		logStep("Fetch solutions details using system id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(systemId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchOrgSolutionDetailsApiResponse);
		CCUtils.assertEquals(fetchOrgSolutionDetailsApiResponse.getStatusCode(), 204);
	}
}
