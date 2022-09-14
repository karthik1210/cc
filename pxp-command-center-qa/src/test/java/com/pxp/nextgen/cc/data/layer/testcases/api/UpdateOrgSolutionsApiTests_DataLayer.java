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

public class UpdateOrgSolutionsApiTests_DataLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	Response updateSolutionsApiResponse, fetchOrgSolutionDetailsApiResponse, createClientApiResponse;
	List<Integer> solutionList = null;
	String practiceName;
	int clientId;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		orgInfo = new OrgInfo();
		clientInfo = new ClientInfo();
		validation = new Validations();
		clientId = createClient();
	}

	public int createClient() throws IOException {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		logStep("Create a client of name : " + clientName);
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(ccUser.getActive(), clientName, clientSapNumber,
				ccUser.getTestClient(), Integer.parseInt(testData.getProperty("api.client.type")),
				ccUser.getTimezoneId());
		return createClientApiResponse.jsonPath().getInt("clientId");
	}

	public int createPracticeNode(int parentId, String active, String parentType) throws IOException {
		practiceName = ccUser.getPracticeNameApi().concat(CCUtils.generateRandomString());
		Response createPracticeApiResponse = orgInfo.createOrgNode_DataLayer(practiceName,
				ccUser.getPracticeOrgTypeApi(), ccUser.getPracticeDisplayNameApi(), active,
				ccUser.getPracticeTimezoneIdApi(), parentId, parentType,
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse.jsonPath().getInt("orgId");
	}

	public int createNgePracticeNode(String active) throws IOException {
		Response createSystemApiResponse = orgInfo.createOrgNode_DataLayer(testData.getProperty("api.system.name"),
				ccUser.getSystemOrgTypeApi(), "", testData.getProperty("api.system.active"),
				ccUser.getSystemTimezoneIdApi(), clientId, testData.getProperty("api.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");

		Response createEnterpriseApiResponse = orgInfo.createOrgNode_DataLayer(
				testData.getProperty("api.enterprise.name"), ccUser.getEnterpriseOrgTypeApi(), "",
				testData.getProperty("api.enterprise.active"), ccUser.getEnterpriseTimezoneIdApi(), systemId,
				ccUser.getSystemOrgTypeApi(), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		return createPracticeNode(enterpriseId, active, testData.getProperty("api.enterprise.orgtype"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update active agnostic practice node solutions and verify response & status code ")
	public void verifySolutionsDetailsForActiveAgnosticPractice() throws IOException {
		int practiceId = createPracticeNode(clientId, "true", testData.getProperty("api.parenttype"));
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId);
		List<OrgSolutions> defaultSolutionList = validation.getSolutionsDetails(fetchOrgSolutionDetailsApiResponse);
		logStep("Purchase solution for active practice node");
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, practiceId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId);
		List<OrgSolutions> finalList = new ArrayList<OrgSolutions>();
		finalList.addAll(productList);
		finalList.addAll(defaultSolutionList);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, practiceId, practiceName,
				finalList);

		logStep("Terminate newly purchased solution for active practice node");
		List<OrgSolutions> productList1 = Arrays
				.asList(new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.terminated")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList1, practiceId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId);
		List<OrgSolutions> finalList1 = new ArrayList<OrgSolutions>();
		finalList1.addAll(productList1);
		finalList1.addAll(defaultSolutionList);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, practiceId, practiceName,
				finalList1);

		logStep("Change solution status from terminated to Active implementation for active practice node");
		List<OrgSolutions> productList2 = Arrays.asList(new OrgSolutions(testData.getProperty("Portal.id"),
				testData.getProperty("status.active.implementation")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList2, practiceId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId);
		List<OrgSolutions> finalList2 = new ArrayList<OrgSolutions>();
		finalList2.addAll(productList2);
		finalList2.addAll(defaultSolutionList);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, practiceId, practiceName,
				finalList2);

		logStep("Change solution status from Active implementation to Terminated for active practice node");
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList1, practiceId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, practiceId, practiceName,
				finalList1);

		logStep("Change the same solution status multiple times in the same payload and verify that the last added status is updated");
		List<OrgSolutions> finalList3 = new ArrayList<OrgSolutions>();
		finalList3.addAll(productList);
		finalList3.addAll(productList2);
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(finalList3, practiceId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, practiceId, practiceName,
				finalList2);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing practice id and  verify response body")
	public void verifyErrorResponseForNonExistingPracticeId() throws IOException {
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList,
				Integer.parseInt(testData.getProperty("non.existing.practice.id")),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(updateSolutionsApiResponse,
				Integer.parseInt(testData.getProperty("non.existing.practice.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank list and  verify response body")
	public void testErrorMessageForBlankPayload() throws IOException {
		List<OrgSolutions> productList = Arrays.asList();
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList,
				Integer.parseInt(testData.getProperty("valid.practice.id")),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 400);
		validation.verifyErrorResponseBodyForEmptyList(updateSolutionsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and  verify response body")
	public void testErrorResponseForInvalidUrl() throws IOException {
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutionsUsingInvalidUrl_DataLayer(productList,
				Integer.parseInt(testData.getProperty("valid.practice.id")),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateSolutionsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Make a POST call instead of PUT and verify response body")
	public void testErrorMessageForInvalidMethodCall() throws IOException {
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutionsUsingInvalidMethod_DataLayer(productList,
				Integer.parseInt(testData.getProperty("valid.practice.id")),
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(updateSolutionsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update solution for inactive agnostic practice verify response & status code ")
	public void verifySolutionsDetailsForInactiveAgnosticPractice() throws IOException {
		int practiceId = createPracticeNode(clientId, "false", testData.getProperty("api.parenttype"));
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId);
		List<OrgSolutions> defaultSolutionList = validation.getSolutionsDetails(fetchOrgSolutionDetailsApiResponse);
		logStep("Purchase solution for inactive practice node");
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, practiceId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId);
		List<OrgSolutions> finalList = new ArrayList<OrgSolutions>();
		finalList.addAll(productList);
		finalList.addAll(defaultSolutionList);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, practiceId, practiceName,
				finalList);

		logStep("Terminate newly purchased solution for inactive practice node");
		List<OrgSolutions> productList1 = Arrays
				.asList(new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.terminated")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList1, practiceId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId);
		List<OrgSolutions> finalList1 = new ArrayList<OrgSolutions>();
		finalList1.addAll(productList1);
		finalList1.addAll(defaultSolutionList);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, practiceId, practiceName,
				finalList1);

		logStep("Change solution status from terminated to Active implementation for inactive practice node");
		List<OrgSolutions> productList2 = Arrays.asList(new OrgSolutions(testData.getProperty("Portal.id"),
				testData.getProperty("status.active.implementation")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList2, practiceId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId);
		List<OrgSolutions> finalList2 = new ArrayList<OrgSolutions>();
		finalList2.addAll(productList2);
		finalList2.addAll(defaultSolutionList);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, practiceId, practiceName,
				finalList2);

		logStep("Change solution status from Active implementation to Terminated for inactive practice node");
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList1, practiceId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, practiceId, practiceName,
				finalList1);

		logStep("Change the same solution status multiple times in the same payload and verify that the last added status is updated");
		List<OrgSolutions> finalList3 = new ArrayList<OrgSolutions>();
		finalList3.addAll(productList);
		finalList3.addAll(productList2);
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(finalList3, practiceId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, practiceId, practiceName,
				finalList2);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Update solutions details for inactive nge practice node verify response & status code ")
	public void verifySolutionsDetailsForInactiveNgePractice() throws IOException {
		int inactiveNgePracticeId = createNgePracticeNode("false");
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(inactiveNgePracticeId);
		List<OrgSolutions> defaultSolutionList = validation.getSolutionsDetails(fetchOrgSolutionDetailsApiResponse);
		logStep("Purchase solution for inactive practice node");
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("forms.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, inactiveNgePracticeId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(inactiveNgePracticeId);
		List<OrgSolutions> finalList = new ArrayList<OrgSolutions>();
		finalList.addAll(productList);
		finalList.addAll(defaultSolutionList);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, inactiveNgePracticeId,
				practiceName, finalList);

		logStep("Terminate newly purchased solution for inactive practice node");
		List<OrgSolutions> productList1 = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.terminated")),
				new OrgSolutions(testData.getProperty("forms.id"), testData.getProperty("status.terminated")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList1, inactiveNgePracticeId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(inactiveNgePracticeId);
		List<OrgSolutions> finalList1 = new ArrayList<OrgSolutions>();
		finalList1.addAll(productList1);
		finalList1.addAll(defaultSolutionList);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, inactiveNgePracticeId,
				practiceName, finalList1);

		logStep("Change solution status from terminated to Active implementation for inactive practice node");
		List<OrgSolutions> productList2 = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"),
						testData.getProperty("status.active.implementation")),
				new OrgSolutions(testData.getProperty("forms.id"),
						testData.getProperty("status.active.implementation")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList2, inactiveNgePracticeId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(inactiveNgePracticeId);
		List<OrgSolutions> finalList2 = new ArrayList<OrgSolutions>();
		finalList2.addAll(productList2);
		finalList2.addAll(defaultSolutionList);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, inactiveNgePracticeId,
				practiceName, finalList2);

		logStep("Change the same solution status multiple times in the same payload and verify that the last added status is updated");
		List<OrgSolutions> productList3 = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.go.live")));

		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList3, inactiveNgePracticeId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		List<OrgSolutions> finalList3 = new ArrayList<OrgSolutions>();
		finalList3.add(new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.go.live")));
		finalList3.add(new OrgSolutions(testData.getProperty("forms.id"),
				testData.getProperty("status.active.implementation")));
		finalList3.addAll(defaultSolutionList);
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(inactiveNgePracticeId);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, inactiveNgePracticeId,
				practiceName, finalList3);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Active nge practice with solution verify response & status code ")
	public void verifySolutionsDetailsForActiveNgePractice() throws IOException {
		int activeNgePracticeId = createNgePracticeNode("true");
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(activeNgePracticeId);
		List<OrgSolutions> defaultSolutionList = validation.getSolutionsDetails(fetchOrgSolutionDetailsApiResponse);
		logStep("Purchase solution for active practice node");
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("forms.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, activeNgePracticeId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(activeNgePracticeId);
		List<OrgSolutions> finalList = new ArrayList<OrgSolutions>();
		finalList.addAll(productList);
		finalList.addAll(defaultSolutionList);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, activeNgePracticeId,
				practiceName, finalList);

		logStep("Terminate newly purchased solution for active practice node");
		List<OrgSolutions> productList1 = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.terminated")),
				new OrgSolutions(testData.getProperty("forms.id"), testData.getProperty("status.terminated")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList1, activeNgePracticeId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(activeNgePracticeId);
		List<OrgSolutions> finalList1 = new ArrayList<OrgSolutions>();
		finalList1.addAll(productList1);
		finalList1.addAll(defaultSolutionList);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, activeNgePracticeId,
				practiceName, finalList1);

		logStep("Change solution status from terminated to Active implementation for active practice node");
		List<OrgSolutions> productList2 = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"),
						testData.getProperty("status.active.implementation")),
				new OrgSolutions(testData.getProperty("forms.id"),
						testData.getProperty("status.active.implementation")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList2, activeNgePracticeId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(activeNgePracticeId);
		List<OrgSolutions> finalList2 = new ArrayList<OrgSolutions>();
		finalList2.addAll(productList2);
		finalList2.addAll(defaultSolutionList);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, activeNgePracticeId,
				practiceName, finalList2);

		logStep("Change the same solution status multiple times in the same payload and verify that the last added status is updated");
		List<OrgSolutions> productList3 = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.on.hold")));

		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList3, activeNgePracticeId,
				Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		List<OrgSolutions> finalList3 = new ArrayList<OrgSolutions>();
		finalList3.add(new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.on.hold")));
		finalList3.add(new OrgSolutions(testData.getProperty("forms.id"),
				testData.getProperty("status.active.implementation")));
		finalList3.addAll(defaultSolutionList);
		logStep("Fetch solutions details using practice id");
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(activeNgePracticeId);
		validation.verifyGetOrgIdSolutionsResponseBody(fetchOrgSolutionDetailsApiResponse, activeNgePracticeId,
				practiceName, finalList3);
	}
}
