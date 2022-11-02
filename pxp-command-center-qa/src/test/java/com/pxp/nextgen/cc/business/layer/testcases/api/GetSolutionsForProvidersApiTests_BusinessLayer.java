//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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
import com.pxp.cc.util.PracticeAppointmentTypeValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetSolutionsForProvidersApiTests_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	private Validations validation;
	private LocationsValidations locationsValidations;
	Response getResourceDetailsApiResponse, createClientApiResponse, createProviderApiResponse, createSystemApiResponse,
			createPracticeApiResponse, createEnterpriseApiResponse, updateSolutionsApiResponse,
			updateSolutionForResources, fetchSolutionsForProviders;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, practiceIdOne, providerIdOne;
	String clientName, clientSapNumber;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		locationsValidations = new LocationsValidations();

		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber, "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
		clientId = createClientApiResponse.jsonPath().getInt("clientId");
		createSystemApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", testData.getProperty("api.system.active"), "", clientId,
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		createEnterpriseApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemId,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		createPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		practiceId = createPracticeApiResponse.jsonPath().get("orgId");
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
		createPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		practiceIdOne = createPracticeApiResponse.jsonPath().get("orgId");
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceIdOne, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerIdOne = createProviderApiResponse.jsonPath().getInt("providerId");

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing providerId, practiceId with no associating solutions")
	public void testGetSolutionsForProviders1() throws IOException {
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_BL(providerIdOne, practiceIdOne);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch all the solutions of a provider of a practice")
	public void testGetSolutionsForProviders() throws IOException {
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_BL(providerId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 200);
		locationsValidations.verifyNoOfSolutionsFetchedSolutionNameandId(fetchSolutionsForProviders);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating status code when fetching solutions of existing a provider Id and practice Id but no solution is associated")
	public void testResponseCodeForExistingProviderIdAndPracticeIdOfNoAssociatedSolution() throws IOException {
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_BL(
				Integer.parseInt(testData.getProperty("no.solu.resources.resource.id").trim()),
				Integer.parseInt(testData.getProperty("no.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating status code when fetching solutions of existing provider Id and non-existing practice Id")
	public void testErrorMessageForExistingResourceIdandNonexistingPracticeId() throws IOException {
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_BL(
				Integer.parseInt(testData.getProperty("get.solu.resources.resource.id").trim()),
				Integer.parseInt(testData.getProperty("non.existing.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating status code when fetching provider of non-existing practice Id and existing solution Id")
	public void testErrorMessageForNonExistingResourceIdandExistingPracticeId() throws IOException {
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_BL(
				Integer.parseInt(testData.getProperty("non.existing.solu.resources.resource.id").trim()),
				Integer.parseInt(testData.getProperty("get.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating status code when fetching solutions of both non-existing provider Id and practice Id")
	public void testErrorMessageForBothNonExistingResourceIdandPracticeId() throws IOException {
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_BL(
				Integer.parseInt(testData.getProperty("non.existing.solu.resources.resource.id").trim()),
				Integer.parseInt(testData.getProperty("non.existing.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when fetching solutions with provider id and null and practice Id as null")
	public void testErrorMessageForNullValuesInPracticeIdAndAppointmentTypeId() throws IOException {
		fetchSolutionsForProviders = sharedInfo.getSolutionsForProviders_BL_NullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForProviders);
		CCUtils.assertEquals(fetchSolutionsForProviders.getStatusCode(), 400);
		validation.verifyErrorMessageIfClientIdIsNull(fetchSolutionsForProviders);
	}

}
