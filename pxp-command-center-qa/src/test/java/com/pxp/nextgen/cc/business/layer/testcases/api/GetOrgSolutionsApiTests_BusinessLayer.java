// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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

public class GetOrgSolutionsApiTests_BusinessLayer extends BaseRest {

	private CreateClientHierarchy_BL clientHierObj;
	private OrgInfo orgInfo;
	private Validations validation;
	Response getOrgSolutionsResponse, createAgPracticeApiResponse, updateSolutionsApiResponse;
	public int clientId, systemId, enterpriseId, practiceId, providerId, agnosticPracticeId;
	List<Integer> solutionList = null;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		orgInfo = new OrgInfo();
		validation = new Validations();
		clientHierObj = new CreateClientHierarchy_BL();
		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		agnosticPracticeId = clientHierObj.createAgnosticPractice_BL(systemId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org solution as type PXP for ng Practice")
	public void testToGetOrgSolutionTypePxpForActiveNGPractice() throws Exception {
		log("Update org details by adding up only one solution as newly purchased");
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_BusinessLayer(productList, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		log("Get org solution of org");
		getOrgSolutionsResponse = orgInfo.getOrgsSolutions_BusinessLayer(practiceId,
				testData.getProperty("ecosystem.pxp"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgSolutionsResponse);
		CCUtils.assertEquals(getOrgSolutionsResponse.getStatusCode(), 200);
	}

	// will enable this test case once this JIRA ID issue - PXPP-28907 is resolved
	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Get org solution as type ADD-ON for ng Practice")
	public void testToGetOrgSolutionTypeAddonForActiveNGPractice() throws Exception {
		log("Update org details by adding up only one solution as newly purchased");
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("chat.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_BusinessLayer(productList, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		log("Get org solution of org");
		getOrgSolutionsResponse = orgInfo.getOrgsSolutions_BusinessLayer(practiceId,
				testData.getProperty("add.on.solution.type"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgSolutionsResponse);
		CCUtils.assertEquals(getOrgSolutionsResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org solution as type PXP for active agnostic practice")
	public void testToGetOrgSolutionForTypePxpForActiveAgnosticPractice() throws Exception {
		log("Update org details by adding up only one solution as newly purchased for active agnostic practice");
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_BusinessLayer(productList, agnosticPracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		log("Get org solution of agnostic org");
		getOrgSolutionsResponse = orgInfo.getOrgsSolutions_BusinessLayer(agnosticPracticeId,
				testData.getProperty("ecosystem.pxp"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgSolutionsResponse);
		CCUtils.assertEquals(getOrgSolutionsResponse.getStatusCode(), 200);
		validation.verifyResponseBodyOfOrgSol(getOrgSolutionsResponse,
				Integer.parseInt(testData.getProperty("Portal.id")));
	}

	// will enable this test case once this JIRA ID issue - PXPP-28907 is resolved
	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Get org solution as type ADD-ON for active agnostic practice")
	public void testToGetOrgSolutionForTypeAddonForActiveAgnosticPractice() throws Exception {
		log("Update org details by adding up only one solution as newly purchased for active agnostic practice");
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("chat.id"), testData.getProperty("status.newly.purchased")));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_BusinessLayer(productList, agnosticPracticeId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionsApiResponse);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		log("Get org solution of agnostic org");
		getOrgSolutionsResponse = orgInfo.getOrgsSolutions_BusinessLayer(agnosticPracticeId,
				testData.getProperty("add.on.solution.type"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgSolutionsResponse);
		CCUtils.assertEquals(getOrgSolutionsResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org solution as for non-existing type for ng Practice")
	public void testToGetOrgSolutionForNonExistingTypeForActiveNGPractice() throws Exception {
		log("Get org solution for non existing type");
		getOrgSolutionsResponse = orgInfo.getOrgsSolutions_BusinessLayer(practiceId,
				testData.getProperty("non.existing.ecosystem"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgSolutionsResponse);
		CCUtils.assertEquals(getOrgSolutionsResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org solution for non existing orgId")
	public void testToGetOrSolutionForNonexistingOrgId() throws Exception {
		log("Get org solution for non existing orgId");
		getOrgSolutionsResponse = orgInfo.getOrgsSolutions_BusinessLayer(
				Integer.parseInt(testData.getProperty("non.existing.practice.id")),
				testData.getProperty("ecosystem.pxp"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgSolutionsResponse);
		CCUtils.assertEquals(getOrgSolutionsResponse.getStatusCode(), 400);
		validation.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(getOrgSolutionsResponse,
				Integer.parseInt(testData.getProperty("non.existing.practice.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get org solution for Null values in orgId and type field")
	public void testGetOrgSolutionForNullOrgId() throws Exception {
		log("Get org solution for Null values in orgId field and type field");
		getOrgSolutionsResponse = orgInfo.getOrgsSolutions_Null_BusinessLayer();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getOrgSolutionsResponse);
		CCUtils.assertEquals(getOrgSolutionsResponse.getStatusCode(), 400);
	}
}
