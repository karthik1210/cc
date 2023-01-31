//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.OrgResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.PracticeAppointmentTypeValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetSolutionsForResourcesApiTests_BusinessLayer extends BaseRest {
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	private ResourceInfo resourceInfo;
	private OrgResourceInfo orgResourceInfo;
	private Validations validations;
	private PracticeAppointmentTypeValidations practiceAppointmentTypeValidations;
	private CreateClientHierarchy_BL clientHierObj;
	Response getResourceDetailsApiResponse, createProviderApiResponse, updateSolutionsApiResponse,
			updateSolutionForResources, fetchSolutionsForProviders, createResourceApiResponse,
			fetchSolutionsForResources;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId, practiceIdOne, providerIdOne;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		validations = new Validations();
		orgResourceInfo = new OrgResourceInfo();
		practiceAppointmentTypeValidations = new PracticeAppointmentTypeValidations();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		resourceInfo = new ResourceInfo();
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
		String externalIdOne = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceIdOne, firstName, lastName,
				testData.getProperty("provider.gender"), externalIdOne, testData.getProperty("provider.credentials"));
		providerIdOne = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		String resourceName = CCUtils.generateAlphaNumericString(8);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		resourceId = createResourceApiResponse.jsonPath().get("resourceId");
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResource_BL(resourceId, practiceId,
				solutionId, true);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch all the solutions of a resource of a practice")
	public void testGetSolutionsForResources() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_BL(resourceId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 200);
		practiceAppointmentTypeValidations.verifyNoOfSolutionsFetchedSolutionNameandId(fetchSolutionsForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching solutions of existing a resource Id and practice Id but no solution is associated")
	public void testResponseCodeForExistingResourceIdAndPracticeIdOfNoAssociatedSolution() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_BL(
				Integer.parseInt(testData.getProperty("no.solu.resources.resource.id").trim()),
				Integer.parseInt(testData.getProperty("no.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching solutions of existing resource Id and non-existing practice Id")
	public void testErrorMessageForExistingResourceIdAndNonexistingPracticeId() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_BL(resourceId,
				Integer.parseInt(testData.getProperty("non.existing.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching resource of existing practice Id and non-existing resource Id")
	public void testErrorMessageForNonExistingResourceIdAndExistingPracticeId() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_BL(
				Integer.parseInt(testData.getProperty("non.existing.solu.resources.resource.id").trim()), practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching solutions of both non-existing resource Id and practice Id")
	public void testErrorMessageForBothNonExistingResourceIdAndPracticeId() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_BL(
				Integer.parseInt(testData.getProperty("non.existing.solu.resources.resource.id").trim()),
				Integer.parseInt(testData.getProperty("non.existing.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when fetching solutions with resourceId as null and practice Id as null")
	public void testErrorMessageForNullValuesInPracticeIdAndAppointmentTypeId() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_BL_NullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 400);
		validations.verifyErrorMessageIfClientIdIsNull(fetchSolutionsForResources);
	}
}
