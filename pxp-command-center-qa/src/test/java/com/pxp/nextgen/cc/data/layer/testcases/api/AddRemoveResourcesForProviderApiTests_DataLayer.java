// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.ResourceDetails;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AddRemoveResourcesForProviderApiTests_DataLayer extends BaseRest {

	private SharedInfo sharedInfo;
	private Validations validation;
	private ResourceInfo resourceInfo;
	Response createProviderApiResponse, createResourceApiResponse, createSystemApiResponse, createClientApiResponse,
			createPracticeApiResponse, createEnterpriseApiResponse, searchResourceApiResponse, addResourceResponse,
			removeResourceResponse, getResourceResponse;
	public static int clientId, practiceId, resourceId;
	public int providerId;
	private OrgInfo orgInfo;
	private Validations validations;
	private ClientInfo clientInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		orgInfo = new OrgInfo();
		clientInfo = new ClientInfo();
		validations = new Validations();
		resourceInfo = new ResourceInfo();
		practiceId = createPractice(ccUser.getActive(), ccUser.getClientName().concat(CCUtils.generateRandomString()),
				ccUser.getClientSapNumber().concat(CCUtils.generateRandomString()), "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId(),
				testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"),
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")), testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.enterprise.parenttype"),
				testData.getProperty("api.enterprise.timezoneid"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.name"), testData.getProperty("api.practice.orgtype"),
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"));
		providerId = createProviderForPractice();
		resourceId = createResource(providerId);
	}

	public int createProviderForPractice() throws IOException {
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createProviderApiResponse);
		CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
		validation.verifyCreateProviderApiResponseBody(createProviderApiResponse, firstName, lastName);
		int providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		return providerId;
	}

	public int createResource(int providerId) {
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		int resourceId = createResourceApiResponse.jsonPath().getInt("resourceId");
		return resourceId;
	}

	@Test(enabled = false)
	public int createPractice(String status, String clientName, String clientSapNumber, String testFlag, int clientType,
			String clientTimezoneId, String systemName, String sysOrgType, String sysParentType, int ehrIdentifier,
			int pmIdentifier, String enterpriseName, String enterpriseType, String enterpriseParentType,
			String enterpriseTimeZone, String practiceDisplayName, String practiceName, String practiceOrgType,
			String practiceParentType, String practiceTimeZone) throws IOException {
		logStep("Create nge practice below the client with valid details");
		createClientApiResponse = clientInfo.createNewClient_DataLayer(status, clientName, clientSapNumber, testFlag,
				clientType, clientTimezoneId,testData.getProperty("ehr.identifier"),testData.getProperty("pm.identifier"));
		clientId = createClientApiResponse.jsonPath().getInt("clientId");
		logStep("Create a new system below client.");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(status, "", systemName, sysOrgType, clientId,
				sysParentType, "", ehrIdentifier, pmIdentifier);
		int systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new enterprise below system.");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(status, "", enterpriseName, enterpriseType, systemId,
				enterpriseParentType, enterpriseTimeZone, ehrIdentifier, pmIdentifier);
		int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new practice below enterprise.");
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(status, practiceDisplayName, practiceName,
				practiceOrgType, enterpriseId, practiceParentType, practiceTimeZone, ehrIdentifier, pmIdentifier);
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		CCUtils.responseTimeValidation(createPracticeApiResponse);
		validations.verifyCreatePracticeResponseBody(createPracticeApiResponse, practiceName);
		int orgId = createPracticeApiResponse.jsonPath().get("orgId");
		return orgId;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body for add resources for a provider and a practice")
	public void testAddResourcesForProvider() throws IOException {
		List<ResourceDetails> addResourcePayload = new ArrayList<ResourceDetails>();
		addResourcePayload.add(new ResourceDetails(resourceId, true));
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(providerId, practiceId, addResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceResponse);
		CCUtils.assertEquals(addResourceResponse.getStatusCode(), 207);
		validations.verifyResponseBodyOfAddRemoveResource(addResourceResponse, resourceId,
				testData.getProperty("status.success"));
		getResourceResponse = sharedInfo.getResourcesForProvider(providerId, practiceId);
		validations.verifyResponseBodyOfGetResource(getResourceResponse, resourceId);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body for remove resources for a provider and a practice")
	public void testRemoveResourcesForProvider() throws IOException {
		List<ResourceDetails> addResourcePayload = new ArrayList<ResourceDetails>();
		addResourcePayload.add(new ResourceDetails(resourceId, true));
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(providerId, practiceId, addResourcePayload);
		List<ResourceDetails> removeResourcePayload = new ArrayList<ResourceDetails>();
		removeResourcePayload.add(new ResourceDetails(resourceId, false));
		removeResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(providerId, practiceId,
				removeResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(removeResourceResponse);
		CCUtils.assertEquals(removeResourceResponse.getStatusCode(), 207);
		getResourceResponse = sharedInfo.getResourcesForProvider(providerId, practiceId);
		CCUtils.assertEquals(getResourceResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body for add/remove resources for a provider and a practice when enter resourceId as zero")
	public void testErrorResourcesForProviderWhenResourceIdIsZero() throws IOException {
		List<ResourceDetails> addResourcePayload = new ArrayList<ResourceDetails>();
		addResourcePayload.add(new ResourceDetails(0, true));
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(providerId, practiceId, addResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceResponse);
		CCUtils.assertEquals(addResourceResponse.getStatusCode(), 207);
		validations.verifyResponseBodyOfAddRemoveResource(addResourceResponse, 0, testData.getProperty("status.error"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body for add/remove resources for a provider and a practice when enter non-existing practice Id")
	public void testErrorResourcesForProviderWhenProvideNonExistingPracticeId() throws IOException {
		List<ResourceDetails> addResourcePayload = new ArrayList<ResourceDetails>();
		addResourcePayload.add(new ResourceDetails(0, true));
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(providerId,
				Integer.parseInt(testData.getProperty("nonexisting.practiceId")), addResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceResponse);
		CCUtils.assertEquals(addResourceResponse.getStatusCode(), 400);
		validations.verifyErrorInResponseForNullValuesForOrgIdField(addResourceResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body for add/remove resources for a provider and a practice when enter non-existing provider Id")
	public void testErrorResourcesForProviderWhenProvideNonExistingProviderId() throws IOException {
		List<ResourceDetails> addResourcePayload = new ArrayList<ResourceDetails>();
		addResourcePayload.add(new ResourceDetails(0, true));
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(
				Integer.parseInt(testData.getProperty("nonexisting.providerId")), practiceId, addResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceResponse);
		CCUtils.assertEquals(addResourceResponse.getStatusCode(), 400);
		validations.verifyErrorInResponseForNullValuesForOrgIdField(addResourceResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body for add resources for a provider and a practice when enter resource Id as null")
	public void testErrorAddResourcesForProviderWhenEnterResourceIdNULL() throws IOException {
		List<ResourceDetails> addResourcePayload = new ArrayList<ResourceDetails>();
		addResourcePayload.add(new ResourceDetails(null, true));
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(providerId, practiceId, addResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceResponse);
		CCUtils.assertEquals(addResourceResponse.getStatusCode(), 400);
		validations.verifyAddRemoveResourceForNullResourceIdAndActive(addResourceResponse, "ResourceId cannot be null");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body for add resources for a provider and a practice when enter active as null")
	public void testAddResourcesForProviderWhenEnterActiveNULL() throws IOException {
		List<ResourceDetails> addResourcePayload = new ArrayList<ResourceDetails>();
		addResourcePayload.add(new ResourceDetails(resourceId, null));
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(providerId, practiceId, addResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceResponse);
		CCUtils.assertEquals(addResourceResponse.getStatusCode(), 400);
		validations.verifyAddRemoveResourceForNullResourceIdAndActive(addResourceResponse, "Active cannot be null");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body for add resources for a provider and a practice when enter both active and resource Id as null")
	public void testAddResourcesForProviderWhenEnterBothResourceIdAndActiveAsNULL() throws IOException {
		List<ResourceDetails> addResourcePayload = new ArrayList<ResourceDetails>();
		addResourcePayload.add(new ResourceDetails(null, null));
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider(providerId, practiceId, addResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceResponse);
		CCUtils.assertEquals(addResourceResponse.getStatusCode(), 400);
		validations.verifyAddRemoveResourceForNullResourceIdAndActive(addResourceResponse,
				"ResourceId cannot be null Active cannot be null");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "verify response body for add resources for a provider and a practice when enter both provider id and practice id as null")
	public void testAddResourcesForProviderWhenEnterBothProviderIdAndPracticeIdAsNULL() throws IOException {
		List<ResourceDetails> addResourcePayload = new ArrayList<ResourceDetails>();
		addResourcePayload.add(new ResourceDetails(resourceId, true));
		addResourceResponse = sharedInfo.putAddRemoveResourcesForProvider_Null(addResourcePayload);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(addResourceResponse);
		CCUtils.assertEquals(addResourceResponse.getStatusCode(), 400);
	}
}
