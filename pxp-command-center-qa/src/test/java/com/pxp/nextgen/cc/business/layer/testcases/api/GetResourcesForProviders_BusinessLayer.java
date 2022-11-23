//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

/*
JIRA Id: PXPP-17605
Designer: Karthikeyan Natarajan
 */

package com.pxp.nextgen.cc.business.layer.testcases.api;

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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.List;

public class GetResourcesForProviders_BusinessLayer extends BaseRest {
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;
	List<ResourceDetails> resourceList;
	private ResourceInfo resourceInfo;
	Response createClientApiResponse, createProviderApiResponse, createSystemApiResponse, createPracticeApiResponse,
			createEnterpriseApiResponse, createResourceApiResponse, getResourceForProviderApiResponse;
	int clientId, systemId, enterpriseId, practiceId, providerId, resourceId;
	String clientName, clientSapNumber, externalId, firstName, lastName, resourceExternalId;
    private SharedInfo sharedInfo;
    private Validations validation;

    @BeforeClass()
    public void setBaseUri() throws Exception {
    	setupServiceRequestSpecificationBuilder();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		resourceInfo = new ResourceInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();        

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
		logStep("Create provider to fetch resources");
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_BL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		String resourceName = CCUtils.generateAlphaNumericString(8);
		createResourceApiResponse = resourceInfo.createResource_BL(practiceId, resourceName,
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "TC_17780 Fetching resources for the provider with valid inputs and valid url")
    public void getResourcesForProviderWithValidInputs() throws IOException {
        getResourceForProviderApiResponse = sharedInfo.getResourcesForProviderWithValidInputs(providerId, practiceId, "valid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getResourceForProviderApiResponse);
        CCUtils.assertEquals(getResourceForProviderApiResponse.getStatusCode(), 200);
        validation.verifyResponseBodyForGetResourceForProvider(getResourceForProviderApiResponse);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "TC_17786 Fetching resources for provider with valid inputs and invalid Url")
    public void getResourcesForProviderWithValidInputsAndInvalidUrl() throws IOException {
        getResourceForProviderApiResponse = sharedInfo.getResourcesForProviderWithValidInputs(providerId, practiceId, "Invalid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getResourceForProviderApiResponse);
        CCUtils.assertEquals(getResourceForProviderApiResponse.getStatusCode(), 404);
        validation.verifyErrorInResponseForInvalidUrl(getResourceForProviderApiResponse);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "TC_17788 Fetching resources for provider with Invalid POST Method")
    public void getResourceForProviderWithInvalidPOST() throws IOException {
        getResourceForProviderApiResponse = sharedInfo.getResourceForProviderWithInvalidPOSTRequest(providerId, practiceId, "Valid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getResourceForProviderApiResponse);
        CCUtils.assertEquals(getResourceForProviderApiResponse.getStatusCode(), 405);
        validation.verifyErrorMessageInResponseForInvalidPostMethodCall(getResourceForProviderApiResponse);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "TC_17785 Fetching resources for provider with Null Inputs")
    public void getResourceForProviderWithNullInputs() throws IOException {
        getResourceForProviderApiResponse = sharedInfo.getResourcesForProviderWithInvalidDataType(null, null, "Valid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getResourceForProviderApiResponse);
        CCUtils.assertEquals(getResourceForProviderApiResponse.getStatusCode(), 400);
        validation.verifyResponseForEmptyOrNULLOrMisFormatData(getResourceForProviderApiResponse);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "TC_17784 Fetching resources for provider with Empty Inputs")
    public void getResourceForProviderWithEmptyInputs() throws IOException {
        getResourceForProviderApiResponse = sharedInfo.getResourcesForProviderWithInvalidDataType(" ", " ", "valid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getResourceForProviderApiResponse);
        CCUtils.assertEquals(getResourceForProviderApiResponse.getStatusCode(), 500);
        validation.verifyResponseForEmptyData(getResourceForProviderApiResponse);
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "TC_17782 Fetching resources for the provider with Non-Existing Provider Id and Existing practice Id along with valid url")
    public void getResourcesForProviderWithNonExistingProviderIdAndExistingPracticeId() throws IOException {
        getResourceForProviderApiResponse = sharedInfo.getResourcesForProviderWithValidInputs(Integer.parseInt(testData.getProperty("non.existing.provider.id")), practiceId, "valid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getResourceForProviderApiResponse);
        CCUtils.assertEquals(getResourceForProviderApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "TC_17781 Fetching resources for the provider with  Existing Provider Id and Non Existing practice Id along with valid url")
    public void getResourcesForProviderWithExistingProviderIdAndNonExistingPracticeId() throws IOException {
        getResourceForProviderApiResponse = sharedInfo.getResourcesForProviderWithValidInputs(providerId, Integer.parseInt(testData.getProperty("non.existing.practice.id")), "valid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getResourceForProviderApiResponse);
        CCUtils.assertEquals(getResourceForProviderApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 8, retryAnalyzer = RetryAnalyzer.class, description = "TC_17783 Fetching resources for the provider with Non Existing Provider Id and Non Existing practice Id along with valid url")
    public void getResourcesForProviderWithNonExistingProviderIdAndNonExistingPracticeId() throws IOException {
        getResourceForProviderApiResponse = sharedInfo.getResourcesForProviderWithValidInputs(Integer.parseInt(testData.getProperty("non.existing.provider.id")), Integer.parseInt(testData.getProperty("non.existing.practice.id")), "valid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getResourceForProviderApiResponse);
        CCUtils.assertEquals(getResourceForProviderApiResponse.getStatusCode(), 204);
    }

}
