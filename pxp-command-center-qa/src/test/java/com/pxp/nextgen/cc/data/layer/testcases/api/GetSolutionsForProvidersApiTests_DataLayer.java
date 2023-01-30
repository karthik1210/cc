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
import com.pxp.cc.util.PracticeAppointmentTypeValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetSolutionsForProvidersApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private OrgInfo orgInfo;
	private Validations validations;
	private CreateClientHierarchy_DL clientHierObj;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId, practiceIdOne, providerIdOne;
	Response updateSolutionsApiResponse, updateSolutionForProvider, getSolutionForProvider,
			createProviderApiResponse, fetchSolutionsForProviders, createPracticeOneApiResponse, createProviderOneApiResponse;
	private PracticeAppointmentTypeValidations practiceAppointmentTypeValidations;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		validations = new Validations();
		practiceAppointmentTypeValidations = new PracticeAppointmentTypeValidations();
		sharedInfo = new SharedInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		practiceIdOne = clientHierObj.createPractice_DL(enterpriseId);
		logStep("Create new provider");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Enable existing solution id for existing providerId  and practiceId ");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutionsDetails_DataLayer(solutionId, "New", practiceId);
		updateSolutionForProvider = sharedInfo.enableOrDisableSolutionsForProvider_DL(providerId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(updateSolutionForProvider.getStatusCode(), 200);
		String externalIdOne = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceIdOne, firstName, lastName,
				testData.getProperty("provider.gender"), externalIdOne, testData.getProperty("provider.credentials"));
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		providerIdOne = createProviderApiResponse.jsonPath().getInt("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceId, providerId to get solutions and verify response body")
	public void testGetSolutionsForExistingPracticeIdAndProviderId() throws IOException {
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_DL(providerId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 200);
		practiceAppointmentTypeValidations.verifyNoOfSolutionsFetchedSolutionNameandId(fetchSolutionsForProviders);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceId, providerId to get solutions but with no associated solution and verify response code")
	public void testGetSolutionsForExistingPracticeIdAndProviderIdWithNoAssociatedSolution() throws IOException {
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_DL(providerIdOne, practiceIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceId, non-existing providerId to get solutions and verify response code")
	public void testErrorMessageForExistingPracticeIdAndNonExistingProviderId() throws IOException {
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_DL(practiceId,
				Integer.parseInt(testData.getProperty("non.existing.provider.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing practiceId, existing providerId to get solutions and verify response code")
	public void testErrorMessageForNonExistingPracticeIdAndExistingProviderId() throws IOException {
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_DL(
				Integer.parseInt(testData.getProperty("non.existing.practice.id")), providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing practiceId, non-existing providerId to get solutions and verify response code")
	public void testErrorMessageForBothNonExistingPracticeIdAndProviderId() throws IOException {
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_DL(
				Integer.parseInt(testData.getProperty("non.existing.practice.id")),
				Integer.parseInt(testData.getProperty("non.existing.provider.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter practiceId and providerId as null and verify response code")
	public void testErrorMessageForNullValuesInPracticeIdAndProviderId() throws IOException {
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_DL_NullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 400);
		validations.verifyErrorMessageIfClientIdIsNull(fetchSolutionsForProviders);
	}
}

