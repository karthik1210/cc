// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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

public class GetOrgSolutionsApiTests_DataLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private Validations validation;
	Response updateSolutionsApiResponse, fetchOrgSolutionDetailsApiResponse, createClientApiResponse,
			createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse, getOrgSolutionsResponse,
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
		createClientApiResponse = clientInfo.createNewClient_DataLayer(ccUser.getActive(), clientName, clientSapNumber,
				"false", Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
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
				testData.getProperty("api.practice.orgtype"), systemId, testData.getProperty("api.practice.parenttype"),
				testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse.jsonPath().get("orgId");
	}

	public int createAgnosticInactivePracticeNode(int systemId) throws Exception {
		createPracticeApiResponse = orgInfo.createOrg_DataLayer("false",
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), systemId, testData.getProperty("api.practice.parenttype"),
				testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse.jsonPath().get("orgId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org solution as Active Implementation")
	public void testToGetOrgSolution() throws Exception {
		log("Update org details by adding up only one solution as Active Implementation");
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
		log("Get org solution as Active Implementation");
		getOrgSolutionsResponse = orgInfo.getOrgsSolutions_DataLayer(practiceId, testData.getProperty("ecosystem.pxp"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgSolutionsResponse);
		CCUtils.assertEquals(getOrgSolutionsResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org solution as Active Implementation for active agnostic practice")
	public void testToGetOrgSolutionForActiveAgnosticPractice() throws Exception {
		log("Update org details by adding up only one solution as Active Implementation for active agnostic practice");
		clientId = createClient();
		systemId = createSystemNode(clientId);
		agnosticPracticeId = createAgnosticActivePracticeNode(systemId);
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, agnosticPracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		log("Get solution as Active Implementation for active agnostic practice");
		getOrgSolutionsResponse = orgInfo.getOrgsSolutions_DataLayer(practiceId, testData.getProperty("ecosystem.pxp"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgSolutionsResponse);
		CCUtils.assertEquals(getOrgSolutionsResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org solution as Active Implementation for inactive agnostic practice")
	public void testToGetOrgSolutionForInactiveAgnosticPractice() throws Exception {
		log("Update org details by adding up only one solution as Active Implementation for inactive agnostic practice");
		clientId = createClient();
		systemId = createSystemNode(clientId);
		agnosticPracticeId = createAgnosticInactivePracticeNode(systemId);
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, agnosticPracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		log("Get org solution as Active Implementation for inactive agnostic practice");
		getOrgSolutionsResponse = orgInfo.getOrgsSolutions_DataLayer(practiceId, testData.getProperty("ecosystem.pxp"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgSolutionsResponse);
		CCUtils.assertEquals(getOrgSolutionsResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org solution as type is NGE")
	public void testToGetOrgSolutionForTypeNGE() throws Exception {
		clientId = createClient();
		systemId = createSystemNode(clientId);
		enterpriseId = createEnterpriseNode(systemId);
		practiceId = createPracticeNode(enterpriseId);
		log("Get org solution as type is NGE");
		getOrgSolutionsResponse = orgInfo.getOrgsSolutions_DataLayer(practiceId, testData.getProperty("ecosystem.nge"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgSolutionsResponse);
		CCUtils.assertEquals(getOrgSolutionsResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org solution for nonexisting orgId")
	public void testToGetOrSolutionForNonexistingOrgId() throws Exception {
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		log("Get org solution for nonexisting orgId");
		getOrgSolutionsResponse = orgInfo.getOrgsSolutions_DataLayer(practiceId, testData.getProperty("ecosystem.pxp"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgSolutionsResponse);
		CCUtils.assertEquals(getOrgSolutionsResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(getOrgSolutionsResponse, practiceId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org solution for Null values in orgId field")
	public void testGetOrgSolutionForNullOrgId() throws Exception {
		log("Get org solution for Null values in orgId field");
		getOrgSolutionsResponse = orgInfo.getOrgsSolutionsNullValues_DataLayer();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgSolutionsResponse);
		CCUtils.assertEquals(getOrgSolutionsResponse.getStatusCode(), 400);
	}
}