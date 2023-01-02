//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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
	private ClientInfo clientInfo;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			updateSolutionsApiResponse, createClientApiResponse, createProviderApiResponse, updateSolutionForProvider,
			fetchSolutionsForProviders;
	private OrgInfo orgInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		String clientName = testData.getProperty("api.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
				clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
				Integer.parseInt(testData.getProperty("api.client.type")),
				testData.getProperty("api.client.timezoneid"), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
		clientId = createClientApiResponse.jsonPath().get("clientId");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
				testData.getProperty("api.system.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		systemId = createSystemApiResponse.jsonPath().get("orgId");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.displayname"), testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), systemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		enterpriseId = createEnterpriseApiResponse.jsonPath().get("orgId");
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
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