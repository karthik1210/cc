// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.util.Arrays;
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

public class GetOrgSolutionsApiTests_DataLayer extends BaseRest {
	private OrgInfo orgInfo;
	private Validations validation;
	Response updateSolutionsApiResponse, fetchOrgSolutionDetailsApiResponse, getOrgSolutionsResponse,
			createAgnosticPracticeApiResponse, createPracticeApiResponse;
	List<Integer> solutionList = null;
	int clientId, systemId, enterpriseId, practiceId, agnosticPracticeId;
	private CreateClientHierarchy_DL clientHierObj;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		orgInfo = new OrgInfo();
		validation = new Validations();
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
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		agnosticPracticeId = clientHierObj.createAgnosticPractice_DL(systemId);
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
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		agnosticPracticeId = clientHierObj.createAgnosticPractice_DL(systemId);
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
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
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
		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
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