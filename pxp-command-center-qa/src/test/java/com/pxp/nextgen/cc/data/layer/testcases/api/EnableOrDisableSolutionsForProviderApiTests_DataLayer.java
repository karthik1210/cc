//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class EnableOrDisableSolutionsForProviderApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId;
	Response updateSolutionsApiResponse, createProviderApiResponse, updateSolutionForProvider,
			fetchSolutionsForProviders;
	private OrgInfo orgInfo;
	private CreateClientHierarchy_DL clientHierObj;
	
	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutionsDetails_DataLayer(solutionId, "New", practiceId);
		logStep("Create new provider");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable/Disable  existing solution id for existing ProviderId  and practiceId and verify status code")
	public void testToEnableExistingSolutionIdForExistingProviderIdAndExistingPracticeId() throws IOException {
		logStep("Enable existing solution id for existing providerId  and practiceId ");
		updateSolutionForProvider = sharedInfo.enableOrDisableSolutionsForProvider_DL(providerId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(updateSolutionForProvider.getStatusCode(), 200);
		logStep("fetch Solutions For Providers");
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_DL(providerId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 200);
		logStep("Disable existing solution id for existing providerId  and practiceId ");
		updateSolutionForProvider = sharedInfo.enableOrDisableSolutionsForProvider_DL(providerId, practiceId,
				solutionId, false);
		CCUtils.assertEquals(updateSolutionForProvider.getStatusCode(), 200);
		logStep("fetch Solutions For Providers");
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_DL(providerId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable/Disable  existing solution id for existing ProviderId  and nonexisting practiceId and verify status code")
	public void testErrorMessageToEnableExistingSolutionIdForExistingProviderIdAndNonExistingPracticeId()
			throws IOException {
		logStep("Enable  existing solution id for existing ProviderId  and nonexisting practiceId");
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		updateSolutionForProvider = sharedInfo.enableOrDisableSolutionsForProvider_DL(providerId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(updateSolutionForProvider.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(updateSolutionForProvider);
		logStep("Disable  existing solution id for existing ProviderId  and nonexisting practiceId");
		updateSolutionForProvider = sharedInfo.enableOrDisableSolutionsForProvider_DL(providerId, practiceId,
				solutionId, false);
		CCUtils.assertEquals(updateSolutionForProvider.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(updateSolutionForProvider);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable/Disable  existing solution id for nonexisting ProviderId  and existing practiceId and verify status code")
	public void testErrorMessageToEnableExistingSolutionIdForNonExistingProviderIdAndExistingPracticeId()
			throws IOException {
		logStep("Enable existing solution id for nonexisting ProviderId  and existing practiceId");
		int providerId = Integer.parseInt(testData.getProperty("non.existing.provider.id"));
		updateSolutionForProvider = sharedInfo.enableOrDisableSolutionsForProvider_DL(providerId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(updateSolutionForProvider.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(updateSolutionForProvider);
		logStep("Disable  existing solution id for nonexisting ProviderId  and existing practiceId");
		updateSolutionForProvider = sharedInfo.enableOrDisableSolutionsForProvider_DL(providerId, practiceId,
				solutionId, false);
		CCUtils.assertEquals(updateSolutionForProvider.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(updateSolutionForProvider);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable/Disable  existing solution id for nonexisting ProviderId  and nonexisting practiceId and verify status code")
	public void testErrorMessageToEnableExistingSolutionIdForNonExistingProviderIdAndNonExistingPracticeId()
			throws IOException {
		logStep("Enable existing solution id for nonexisting ProviderId  and nonexisting practiceId");
		int providerId = Integer.parseInt(testData.getProperty("non.existing.provider.id"));
		int practiceId = Integer.parseInt(testData.getProperty("non.existing.practice.id"));
		updateSolutionForProvider = sharedInfo.enableOrDisableSolutionsForProvider_DL(providerId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(updateSolutionForProvider.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(updateSolutionForProvider);
		logStep("Disable  existing solution id for nonexisting ProviderId  and nonexisting practiceId");
		updateSolutionForProvider = sharedInfo.enableOrDisableSolutionsForProvider_DL(providerId, practiceId,
				solutionId, false);
		CCUtils.assertEquals(updateSolutionForProvider.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(updateSolutionForProvider);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting solution id for existing ProviderId  and practiceId and verify status code")
	public void testErrorMessageToEnableNonExistingSolutionIdForExistingProviderIdAndExistingPracticeId()
			throws IOException {
		logStep("Enable existing solution id for existing providerId  and practiceId ");
		int solutionId = Integer.parseInt(testData.getProperty("nonexisting.solutionId"));
		updateSolutionForProvider = sharedInfo.enableOrDisableSolutionsForProvider_DL(providerId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(updateSolutionForProvider.getStatusCode(), 400);
		validation.verifyErrorMessageForBlankField(updateSolutionForProvider);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null responsebody for existing ProviderId  and practiceId and verify status code")
	public void testErrorMessageForNullRequestBodyForExistingProviderIdAndExistingPracticeId() throws IOException {
		logStep("Enable existing solution id for existing providerId  and practiceId ");
		updateSolutionForProvider = sharedInfo.enableOrDisableSolutionsForProviderNullRequestBody_DL(providerId,
				practiceId);
		CCUtils.assertEquals(updateSolutionForProvider.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null  ProviderId  and practiceId and verify status code")
	public void testErrorMessageForNullProviderIdAndPracticeId() throws IOException {
		logStep("Enable existing solution id for existing providerId  and practiceId ");
		updateSolutionForProvider = sharedInfo.enableOrDisableSolutionsForNullProviderIdAndPracticeId_DL();
		CCUtils.assertEquals(updateSolutionForProvider.getStatusCode(), 400);
	}
}