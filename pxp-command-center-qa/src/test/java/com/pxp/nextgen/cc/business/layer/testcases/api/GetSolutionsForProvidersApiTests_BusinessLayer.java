//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetSolutionsForProvidersApiTests_BusinessLayer extends BaseRest {
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	private Validations validation;
	private LocationsValidations locationsValidations;
	private CreateClientHierarchy_BL clientHierObj;
	Response getResourceDetailsApiResponse, createProviderApiResponse, updateSolutionsApiResponse,
			updateSolutionForResources, fetchSolutionsForProviders;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, practiceIdOne, providerIdOne;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		locationsValidations = new LocationsValidations();

		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		practiceIdOne = clientHierObj.createPractice_BL(enterpriseId);
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Enable existing solution id for existing resourceId  and practiceId ");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutionsDetails_BL(solutionId, "New", practiceId);
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForProvider_BL(providerId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 200);
		String externalIdOne = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceIdOne, firstName, lastName,
				testData.getProperty("provider.gender"), externalIdOne, testData.getProperty("provider.credentials"));
		providerIdOne = createProviderApiResponse.jsonPath().getInt("providerId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceId, providerId to get solutions and verify response body")
	public void testGetSolutionsForExistingPracticeIdAndProviderId() throws IOException {
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_BL(providerId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 200);
		locationsValidations.verifyNoOfSolutionsFetchedSolutionNameandId(fetchSolutionsForProviders);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceId, providerId to get solutions but with no associated solution and verify response code")
	public void testGetSolutionsForExistingPracticeIdAndProviderIdWithNoAssociatedSolution() throws IOException {
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_BL(providerIdOne, practiceIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing practiceId, non-existing providerId to get solutions and verify response code")
	public void testErrorMessageForExistingPracticeIdAndNonExistingProviderId() throws IOException {
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_BL(practiceId,
				Integer.parseInt(testData.getProperty("non.existing.provider.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing practiceId, existing providerId to get solutions and verify response code")
	public void testErrorMessageForNonExistingPracticeIdAndExistingProviderId() throws IOException {
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_BL(
				Integer.parseInt(testData.getProperty("non.existing.practice.id")), providerId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing practiceId, non-existing providerId to get solutions and verify response code")
	public void testErrorMessageForBothNonExistingPracticeIdAndProviderId() throws IOException {
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_BL(
				Integer.parseInt(testData.getProperty("non.existing.practice.id")),
				Integer.parseInt(testData.getProperty("non.existing.provider.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter practiceId and providerId as null and verify response code")
	public void testErrorMessageForNullValuesInPracticeIdAndProviderId() throws IOException {
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_BL_NullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 400);
		validation.verifyErrorMessageIfClientIdIsNull(fetchSolutionsForProviders);
	}
}
