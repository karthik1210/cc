//Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ResourceInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;

import io.restassured.response.Response;

public class SearchResourcesApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private ResourceInfo resourceInfo;
	Response createProviderApiResponse, createResourceApiResponse, searchResourceApiResponse, getResourcesApiResponse,
			updateSolutionsApiResponse, fetchOrgSolutionDetailsApiResponse, updateSolutionForResources;
	public static int clientId, systemId, enterpriseId, practiceId, resourceId;
	public static String resourceName;
	public int providerId;
	private Validations validations;
	private CreateClientHierarchy_DL clientHierObj;
	
	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validations = new Validations();
		resourceInfo = new ResourceInfo();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
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
