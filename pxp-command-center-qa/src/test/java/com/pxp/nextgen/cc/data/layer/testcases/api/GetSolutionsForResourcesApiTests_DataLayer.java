//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.OrgResourceInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.PracticeAppointmentTypeValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetSolutionsForResourcesApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	private ResourceInfo resourceInfo;
	private OrgResourceInfo orgResourceInfo;
	private Validations validations;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId, resourceId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			updateSolutionsApiResponse, createClientApiResponse, updateSolutionForResources, getSolutionForResources,
			createProviderApiResponse, createResourceApiResponse, fetchSolutionsForResources;
	private PracticeAppointmentTypeValidations practiceAppointmentTypeValidations;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		validations = new Validations();
		orgResourceInfo = new OrgResourceInfo();
		practiceAppointmentTypeValidations = new PracticeAppointmentTypeValidations();
		sharedInfo = new SharedInfo();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
		resourceInfo = new ResourceInfo();
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
		logStep("Create new provider");
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
		logStep("Enable existing solution id for existing resourceId  and practiceId ");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutionsDetails_DataLayer(solutionId, "New", practiceId);
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResource_DL(resourceId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Fetch all the solutions of a resource of a practice")
	public void testGetSolutionsForResources() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_DL(resourceId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 200);
		practiceAppointmentTypeValidations.verifyNoOfSolutionsFetchedSolutionNameandId(fetchSolutionsForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching solutions of existing a resource Id and practice Id but no solution is associated")
	public void testResponseCodeForExistingResourceIdandPracticeIdOfNoAssociatedSolution() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_DL(
				Integer.parseInt(testData.getProperty("no.solu.resources.resource.id").trim()),
				Integer.parseInt(testData.getProperty("no.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching solutions of existing resource Id and non-existing practice Id")
	public void testErrorMessageForExistingResourceIdandNonexistingPracticeId() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_DL(
				Integer.parseInt(testData.getProperty("get.solu.resources.resource.id").trim()),
				Integer.parseInt(testData.getProperty("non.existing.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching resource of non-existing practice Id and existing appointment Type Id")
	public void testErrorMessageForNonExistingResourceIdandExistingPracticeId() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_DL(
				Integer.parseInt(testData.getProperty("non.existing.solu.resources.resource.id").trim()),
				Integer.parseInt(testData.getProperty("get.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validating status code when fetching solutions of both non-existing resource Id and practice Id")
	public void testErrorMessageForBothNonExistingResourceIdandPracticeId() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_DL(
				Integer.parseInt(testData.getProperty("non.existing.solu.resources.resource.id").trim()),
				Integer.parseInt(testData.getProperty("non.existing.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when fetching solutions with incorrect HTTP method")
	public void testErrorMessageForInvalidMethodCallWhenFetchingSolutionWithHTTPMethodPOST() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_DL_InvalidMethodCall(
				Integer.parseInt(testData.getProperty("get.solu.resources.resource.id").trim()),
				Integer.parseInt(testData.getProperty("get.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 405);
		validations.verifyErrorMessageInResponseForInvalidMethodCall(fetchSolutionsForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when fetching solutions with invalid url")
	public void testErrorMessageForInvalidUrlWhenFetchingSolutions() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_DL_InvalidUrl(
				Integer.parseInt(testData.getProperty("get.solu.resources.resource.id").trim()),
				Integer.parseInt(testData.getProperty("get.solu.resources.prac.id").trim()));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 404);
		validations.verifyErrorInResponseForInvalidUrl(fetchSolutionsForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating error message when fetching solutions with resouce id and null and practice Id as null")
	public void testErrorMessageForNullValuesInPracticeIdAndAppointmentTypeId() throws IOException {
		fetchSolutionsForResources = orgResourceInfo.getSolutionsForResources_DL_NullValues();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(fetchSolutionsForResources);
		CCUtils.assertEquals(fetchSolutionsForResources.getStatusCode(), 400);
		validations.verifyErrorMessageIfClientIdIsNull(fetchSolutionsForResources);
	}
}
