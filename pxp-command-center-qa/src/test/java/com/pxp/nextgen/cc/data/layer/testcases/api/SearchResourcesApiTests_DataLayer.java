//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.OrgSolutions;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;

import io.restassured.response.Response;

public class SearchResourcesApiTests_DataLayer extends BaseRest {

	private SharedInfo sharedInfo;
	private ResourceInfo resourceInfo;
	Response createProviderApiResponse, createResourceApiResponse, createSystemApiResponse, createClientApiResponse,
			createPracticeApiResponse, createEnterpriseApiResponse, searchResourceApiResponse, getResourcesApiResponse,
			updateSolutionsApiResponse, fetchOrgSolutionDetailsApiResponse, updateSolutionForResources;
	public static int clientId, systemId, enterpriseId, practiceId, resourceId;
	public static String resourceName;
	public int providerId;
	private OrgInfo orgInfo;
	private Validations validations;
	private ClientInfo clientInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
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
		enableSolutionForResource();
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
		validations.verifyCreateProviderApiResponseBody(createProviderApiResponse, firstName, lastName);
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

	public void enableSolutionForResource() throws IOException {
		int solutionId = Integer.parseInt(testData.getProperty("Portal.id"));
		updateSolutionForResources = sharedInfo.enableOrDisableSolutionsForResource_DL(resourceId, practiceId,
				solutionId, true);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
	}

	@Test(enabled = false)
	public int createPractice(String status, String clientName, String clientSapNumber, String testFlag, int clientType,
			String clientTimezoneId, String systemName, String sysOrgType, String sysParentType, int ehrIdentifier,
			int pmIdentifier, String enterpriseName, String enterpriseType, String enterpriseParentType,
			String enterpriseTimeZone, String practiceDisplayName, String practiceName, String practiceOrgType,
			String practiceParentType, String practiceTimeZone) throws IOException {
		logStep("Create nge practice below the client with valid details");
		createClientApiResponse = clientInfo.createNewClient_DataLayer(status, clientName, clientSapNumber, testFlag,
				clientType, clientTimezoneId, testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
		clientId = createClientApiResponse.jsonPath().getInt("clientId");
		logStep("Create a new system below client.");
		createSystemApiResponse = orgInfo.createOrg_DataLayer(status, "", systemName, sysOrgType, clientId,
				sysParentType, "", ehrIdentifier, pmIdentifier);
		systemId = createSystemApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new enterprise below system.");
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(status, "", enterpriseName, enterpriseType, systemId,
				enterpriseParentType, enterpriseTimeZone, ehrIdentifier, pmIdentifier);
		enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");
		logStep("Create a new practice below enterprise.");
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(status, practiceDisplayName, practiceName,
				practiceOrgType, enterpriseId, practiceParentType, practiceTimeZone, ehrIdentifier, pmIdentifier);
		logStep("Validating Response Information");
		CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
		int orgId = createPracticeApiResponse.jsonPath().get("orgId");
		List<OrgSolutions> productList = Arrays.asList(
				new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased").trim()));
		updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, orgId);
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(orgId,
				testData.getProperty("get.org.type"));
		CCUtils.assertEquals(updateSolutionsApiResponse.getStatusCode(), 200);
		return orgId;
	}

	@DataProvider(name = "resourcevaliddata")
	public Object[] searchResources() {
		Object[][] obj = new Object[][] {
				{ String.valueOf(clientId), testData.getProperty("resource.name").trim(), "0", "0", "0", "0", null,
						null },
				{ String.valueOf(clientId), testData.getProperty("resource.name").trim(), String.valueOf(systemId), "0",
						"0", "0", null, null },
				{ String.valueOf(clientId), testData.getProperty("resource.name").trim(), String.valueOf(systemId),
						String.valueOf(enterpriseId), "0", "0", null, null },
				{ String.valueOf(clientId), testData.getProperty("resource.name").trim(), String.valueOf(systemId),
						String.valueOf(enterpriseId), String.valueOf(practiceId), "0", null, null },
				{ String.valueOf(clientId), testData.getProperty("resource.name").trim(), String.valueOf(systemId),
						String.valueOf(enterpriseId), String.valueOf(practiceId), testData.getProperty("Portal.id"),
						null, null } };
		return obj;
	}

	@DataProvider(name = "resourceinvaliddata")
	public Object[] searchResourcesWithInvalidData() {
		Object[][] obj = new Object[][] { { String.valueOf(clientId), "invalid", "0", "0", "0", "0", null, null },
				{ String.valueOf(clientId), "invalid", "1234", "0", "0", "0", null, null },
				{ String.valueOf(clientId), "invalid", "1234", "1234", "0", "0", null, null },
				{ String.valueOf(clientId), "invalid", "1234", "1234", "1234", "0", null, null },
				{ String.valueOf(clientId), "invalid", "1234", "1234", "1234", "1234", null, null } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "resourcevaliddata", description = "Search resources for valid client id ,system id, enterprise id, practice id and solution id")
	public void testSearchResourcesForValidData(String[] getSearchResources) throws NullPointerException {
		getResourcesApiResponse = resourceInfo.searchResources_DL(getSearchResources[0], getSearchResources[1],
				getSearchResources[2], getSearchResources[3], getSearchResources[4], getSearchResources[5],
				getSearchResources[6], getSearchResources[7]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourcesApiResponse);
		CCUtils.assertEquals(getResourcesApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "resourceinvaliddata", description = "Search resources for invalid client id ,system id, enterprise id, practice id and solution id")
	public void testSearchResourcesForInvalidData(String[] getSearchResources) throws NullPointerException {
		getResourcesApiResponse = resourceInfo.searchResources_DL(getSearchResources[0], getSearchResources[1],
				getSearchResources[2], getSearchResources[3], getSearchResources[4], getSearchResources[5],
				getSearchResources[6], getSearchResources[7]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourcesApiResponse);
		CCUtils.assertEquals(getResourcesApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search resources for non-existing client id")
	public void testSearchResourcesForNonExistingClientId() throws NullPointerException {
		getResourcesApiResponse = resourceInfo.searchResources_DL("1234", null, "0", "0", "0", "0", null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourcesApiResponse);
		CCUtils.assertEquals(getResourcesApiResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Search resources for null client id")
	public void testSearchResourcesForNullClientId() throws NullPointerException {
		getResourcesApiResponse = resourceInfo.searchResources_NULL_DL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourcesApiResponse);
		CCUtils.assertEquals(getResourcesApiResponse.getStatusCode(), 400);
	}
}
