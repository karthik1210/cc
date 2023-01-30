// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class EnableOrDisableSolutionsForResourceApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private OrgInfo orgInfo;
	private ResourceInfo resourceInfo;
	private CreateClientHierarchy_BL clientHierObj;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId;
	Response associateDisassociateBrandApiResponse, createProviderApiResponse,
			createResourceApiResponse, updateSolutionsApiResponse, updateSolutionForResources;

	@BeforeClass
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
		resourceInfo = new ResourceInfo();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		providerId = createProviderFunctionality();
		resourceId = createResourceFunctionality();
	}

	public int createProviderFunctionality() {
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		return createProviderApiResponse.jsonPath().getInt("providerId");
	}

	public int createResourceFunctionality() {
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		return createResourceApiResponse.jsonPath().getInt("resourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable existing solution for existing resourceId  and practiceId and verify response body")
	public void testToEnableExistingSolutionIdForExistingResourceId() throws Exception {
		logStep("Enable existing solution id for existing resourceId  and practiceId ");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = orgInfo.updateOrgSolutionsDetails_BL(solutionId, "New", practiceId);
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResource_BL(resourceId, practiceId,
				solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Disable existing solution for existing resourceId  and practiceId and verify response body")
	public void testToDisableExistingSolutionIdForExistingResourceId() throws IOException {
		logStep("Disable existing solution id for existing resourceId  and practiceId ");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = orgInfo.updateOrgSolutionsDetails_BL(solutionId, "New", practiceId);
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResource_BL(resourceId, practiceId,
				solutionId, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing solution id for existing resourceId and practiceId  and verify error message")
	public void testErrorMessageForNonExistingSolutionIdForExistingResourceId() throws IOException {
		logStep("Enter non-existing solution id for existing resourceId and practiceId ");
		int solutionId = Integer.parseInt(testData.getProperty("nonexisting.solutionId"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResource_BL(resourceId, practiceId,
				solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 400);
		validation.verifyErrorMessageForNonExistingInput(updateSolutionForResources, practiceId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing solution id for non-existing resourceId and existing practice id and verify error message")
	public void testErrorMessageForExistingSolutionIdAndNonExistingResourceId() throws IOException {
		logStep("Enter existing solution id for non-existing resourceId and existing practiceId ");
		int resourceId = Integer.parseInt(testData.getProperty("nonexisting.resourceId"));
		int solutionId = Integer.parseInt(testData.getProperty("valid.solutionId"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResource_BL(resourceId, practiceId,
				solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateSolutionForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid solution id for existing resourceId and non-existing practiceId and verify error message")
	public void testValidSolutionIdForNonExistingPracticeId() throws IOException {
		logStep("Enter existing solution id for existing resourceId and non-existing practiceId ");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		int solutionId = Integer.parseInt(testData.getProperty("valid.solutionId"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResource_BL(resourceId, practiceId,
				solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateSolutionForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank input in request payload and verify error message")
	public void testErrorMessageForBlankRequestPayload() throws IOException {
		logStep("Enable existing resourceId  and practiceId and blank inputs in request body");
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResourceForNullPayload_BL(resourceId,
				practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values for resourceId and practiceId to add Locations and verify error message")
	public void testAddToLocationForNullValues() throws IOException {
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResourceUsingNullValue_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Make a POST method call instead of PUT method call and verify error message")
	public void testErrorMessageForPostMethodCall() throws IOException {
		logStep("Enable existing solution id for existing resourceId  and practiceId ");
		int solutionId = Integer.parseInt(testData.getProperty("valid.solutionId"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResourceUsingInvalidMethod_BL(resourceId,
				practiceId, solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(updateSolutionForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Hit invalid url to Enable or disable solutions for resources and verify error message")
	public void testErrorMessageEnableOrDisableSolutionsForResourceWithInvalidUrl() throws Exception {
		logStep("Enable existing solution id for existing resourceId  and practiceId ");
		int solutionId = Integer.parseInt(testData.getProperty("valid.solutionId"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResourceUsingInvalidUrl_BL(resourceId,
				practiceId, solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateSolutionForResources);
	}
}
