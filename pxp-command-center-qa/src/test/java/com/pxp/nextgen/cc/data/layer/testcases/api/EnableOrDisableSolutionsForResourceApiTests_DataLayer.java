//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.SharedServiceApiValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class EnableOrDisableSolutionsForResourceApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;	
	private ClientInfo clientInfo;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId,resourceId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,updateSolutionsApiResponse, createClientApiResponse, updateSolutionForResources, getSolutionForResources,
			createProviderApiResponse, createResourceApiResponse;
	private OrgInfo orgInfo;
	private ResourceInfo resourceInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		
			clientInfo = new ClientInfo();
			validation = new Validations();
			orgInfo = new OrgInfo();
			sharedInfo = new SharedInfo();
			resourceInfo = new ResourceInfo();
			String clientName = testData.getProperty("api.client.name")
					.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
			String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
			createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
					clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
					Integer.parseInt(testData.getProperty("api.client.type")),
					testData.getProperty("api.client.timezoneid"));
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
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enable existing solution id for existing resourceId  and practiceId and verify response body")
	public void testToEnableExistingSolutionIdForExistingResourceId() throws IOException {
		logStep("Enable existing solution id for existing resourceId  and practiceId ");
		int solutionId = Integer.parseInt(testData.getProperty("api.solution.id"));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutionsDetails_DataLayer(solutionId, "New", practiceId);
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResource_DL(resourceId, practiceId,
				solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Disable existing solution id for existing resourceId  and practiceId and verify response body")
	public void testToDisableExistingSolutionIdForExistingResourceId() throws IOException {
		logStep("Enable existing solution id for existing resourceId  and practiceId ");
		int resourceId = Integer.parseInt(testData.getProperty("valid.resourceId"));
		int practiceId = Integer.parseInt(testData.getProperty("valid.practiceId.one"));
		int solutionId = Integer.parseInt(testData.getProperty("valid.solutionId"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResource_DL(resourceId, practiceId,
				solutionId, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter nonexisting solution id for existing resourceId and practiceId  and verify error message")
	public void testErrorMessageForNonExistingSolutionIdForExistingResourceId() throws IOException {
		logStep("Enter nonexisting solution id for existing resourceId and practiceId ");
		int resourceId = Integer.parseInt(testData.getProperty("valid.resourceId"));
		int practiceId = Integer.parseInt(testData.getProperty("valid.practiceId.one"));
		int solutionId = Integer.parseInt(testData.getProperty("nonexisting.solutionId"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResource_DL(resourceId, practiceId,
				solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateSolutionForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing solution id for non-existing resourceId and existing practice id and verify error message")
	public void testErrorMessageForNonExistingSolutionIdForNonExistingResourceId() throws IOException {
		logStep("Enter nonexisting solution id for non-existing resourceId and existing practiceId ");
		int resourceId = Integer.parseInt(testData.getProperty("nonexisting.resourceId"));
		int practiceId = Integer.parseInt(testData.getProperty("valid.practiceId.one"));
		int solutionId = Integer.parseInt(testData.getProperty("valid.solutionId"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResource_DL(resourceId, practiceId,
				solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateSolutionForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid location id for nonexisting provider and practiceId and verify error message")
	public void testValidLocationIdForNonExistingProviderIdAndPracticeId() throws IOException {
		logStep("Enter nonexisting solution id for existing resourceId and practiceId ");
		int resourceId = Integer.parseInt(testData.getProperty("valid.resourceId"));
		int practiceId = Integer.parseInt(testData.getProperty("valid.practiceId.one"));
		int solutionId = Integer.parseInt(testData.getProperty("nonexisting.solutionId"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResource_DL(resourceId, practiceId,
				solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidInput(updateSolutionForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter blank input in request payload and verify error message")
	public void testErrorMessageForBlankRequestPayload() throws IOException {
		logStep("Enable existing solution id for existing resourceId  and practiceId ");
		int resourceId = Integer.parseInt(testData.getProperty("valid.resourceId"));
		int practiceId = Integer.parseInt(testData.getProperty("valid.practiceId.one"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResourceForNullPayload_DL(resourceId,
				practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Make a POST method call instead of PUT method call and verify error message")
	public void testErrorMessageForPostMethodCall() throws IOException {
		logStep("Enable existing solution id for existing resourceId  and practiceId ");
		int resourceId = Integer.parseInt(testData.getProperty("valid.resourceId"));
		int practiceId = Integer.parseInt(testData.getProperty("valid.practiceId.one"));
		int solutionId = Integer.parseInt(testData.getProperty("valid.solutionId"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResourceUsingInvalidMethod_DL(resourceId,
				practiceId, solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(updateSolutionForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url to Enable or disable solutions for resources and verify error message")
	public void testErrorMessageEnableOrDisableSolutionsForResourceWithInvalidUrl() throws IOException {
		logStep("Enable existing solution id for existing resourceId  and practiceId ");
		int resourceId = Integer.parseInt(testData.getProperty("valid.resourceId"));
		int practiceId = Integer.parseInt(testData.getProperty("valid.practiceId.one"));
		int solutionId = Integer.parseInt(testData.getProperty("valid.solutionId"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResourceUsingInvalidUrl_DL(resourceId,
				practiceId, solutionId, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateSolutionForResources);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter null values for resourceId and practiceId to add Locations and verify error message")
	public void testAddToLocationForNullValues() throws IOException {
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResourceUsingNullValue_DL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateSolutionForResources);
		CCUtils.assertEquals(updateSolutionForResources.getStatusCode(), 400);
	}
}