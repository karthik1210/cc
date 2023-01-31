//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class EnableOrDisableSolutionsForProviderApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private OrgInfo orgInfo;
	private CreateClientHierarchy_BL clientHierObj;
	int clientId, systemId, enterpriseId, practiceId, providerId;
	Response associateDisassociateBrandApiResponse, createProviderApiResponse,
			updateSolutionsApiResponse, updateSolutionForProvider;

	@BeforeClass
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		providerId = createProviderFunctionality();
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable existing solution for existing providerId and practiceId and verify response body")
	public void testToEnableExistingSolutionIdForExistingProviderId() throws Exception {
		logStep("Enable existing solution id for existing providerId  and practiceId ");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = orgInfo.updateOrgSolutionsDetails_BL(solutionId, "New", practiceId);
		updateSolutionForProvider = sharedInfo.enableOrDisableSolutionsForProvider_BL(providerId, practiceId,
				solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForProvider);
		CCUtils.assertEquals(updateSolutionForProvider.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Disable existing solution for existing providerId and practiceId and verify response body")
	public void testToDisableExistingSolutionIdForExistingProviderId() throws IOException {
		logStep("Disable existing solution id for existing providerId  and practiceId ");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = orgInfo.updateOrgSolutionsDetails_BL(solutionId, "New", practiceId);
		updateSolutionForProvider = sharedInfo.enableOrDisableSolutionsForProvider_BL(providerId, practiceId,
				solutionId, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForProvider);
		CCUtils.assertEquals(updateSolutionForProvider.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing solution id for existing providerId and practiceId  and verify error message")
	public void testErrorMessageForNonExistingSolutionIdForExistingProviderId() throws IOException {
		logStep("Enter non-existing solution id for existing providerId and practiceId ");
		int solutionId = Integer.parseInt(testData.getProperty("nonexisting.solutionId"));
		updateSolutionForProvider = sharedInfo.enableOrDisableSolutionsForProvider_BL(providerId, practiceId,
				solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForProvider);
		CCUtils.assertEquals(updateSolutionForProvider.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(updateSolutionForProvider);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing solution id for non-existing providerId and existing practice id and verify error message")
	public void testErrorMessageForExistingSolutionIdAndNonExistingProviderId() throws IOException {
		logStep("Enter existing solution id for non-existing providerId and existing practiceId ");
		int providerId = Integer.parseInt(testData.getProperty("non.existing.provider.id"));
		int solutionId = Integer.parseInt(testData.getProperty("valid.solutionId"));
		updateSolutionForProvider = sharedInfo.enableOrDisableSolutionsForProvider_BL(providerId, practiceId,
				solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForProvider);
		CCUtils.assertEquals(updateSolutionForProvider.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateSolutionForProvider);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid solution id for existing providerId and non-existing practiceId and verify error message")
	public void testValidSolutionIdForNonExistingPracticeId() throws IOException {
		logStep("Enter existing solution id for existing providerId and non-existing practiceId ");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		int solutionId = Integer.parseInt(testData.getProperty("valid.solutionId"));
		updateSolutionForProvider = sharedInfo.enableOrDisableSolutionsForProvider_BL(providerId, practiceId,
				solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForProvider);
		CCUtils.assertEquals(updateSolutionForProvider.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateSolutionForProvider);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank input in request payload and verify error message")
	public void testErrorMessageForBlankRequestPayload() throws IOException {
		logStep("Enable existing providerId  and practiceId and blank inputs in request body");
		updateSolutionForProvider = sharedInfo.enableOrDisableSolutionsForProviderForNullPayload_BL(providerId,
				practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForProvider);
		CCUtils.assertEquals(updateSolutionForProvider.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values for providerId and practiceId and verify error message")
	public void testErrorResponseWithProviderIdPracticeIdAsNullValues() throws IOException {
		updateSolutionForProvider = sharedInfo.enableOrDisableSolutionsForProvidersUsingNullValue_BL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForProvider);
		CCUtils.assertEquals(updateSolutionForProvider.getStatusCode(), 400);
	}
}
