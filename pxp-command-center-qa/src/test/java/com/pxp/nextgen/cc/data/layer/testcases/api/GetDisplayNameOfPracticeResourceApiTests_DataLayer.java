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
import com.pxp.cc.util.ResourceValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetDisplayNameOfPracticeResourceApiTests_DataLayer extends BaseRest {
	private ClientInfo clientInfo;
	int clientId, systemId, enterpriseId, practiceId, locationId, solutionId, providerId, resourceId;
	String clientName, clientSapNumber, systemName, enterpriseName, practiceName;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse, createClientApiResponse,
			createProviderApiResponse, createResourceApiResponse, getResourceDetailsApiResponse, savePracticeResourceApiResponse;
	private OrgInfo orgInfo;
	private SharedInfo sharedInfo;
	private ResourceInfo resourceInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		sharedInfo = new SharedInfo();
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
		String firstName = testData.getProperty("provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
		String lastName = testData.getProperty("provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
		String externalId = CCUtils.generateAlphaNumericString(10);
		createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
				testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
		providerId = createProviderApiResponse.jsonPath().getInt("providerId");
		resourceId = createNewResource();
	}

	public int createNewResource() {
		logStep("Create new resource");
		String resourceExternalId = CCUtils.generateAlphaNumericString(10);
		createResourceApiResponse = resourceInfo.createResource_DL(practiceId, testData.getProperty("resource.name"),
				testData.getProperty("resource.type"), resourceExternalId, String.valueOf(providerId),
				testData.getProperty("resource.link.type"));
		CCUtils.assertEquals(createResourceApiResponse.getStatusCode(), 200);
		return createResourceApiResponse.jsonPath().getInt("resourceId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get resource display details by entering existing resourceId")
	public void testGetGetDisplayNameOfPracticeResource() throws IOException {
		String enDisplayName = testData.getProperty("api.en").concat(CCUtils.generateAlphaNumericString(5));
		String esDisplayName = testData.getProperty("api.es").concat(CCUtils.generateAlphaNumericString(5));
		logStep("Enter valid details in save practice provider payload");
		savePracticeResourceApiResponse = sharedInfo.savePracticeResource_DL(practiceId, resourceId, enDisplayName,
				esDisplayName);
		logStep("fetch resource  display details via correct resource id and Practice id");
		getResourceDetailsApiResponse = resourceInfo.getDisplayNameOfPracticeResource_DL(resourceId, practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get resource display details error message by entering non-existing resourceId")
	public void testGetResourceDisplayNameForNonExistingResourceId() throws IOException {
		logStep("fetch resource details error message via non-existing resource id");
		getResourceDetailsApiResponse = resourceInfo.getDisplayNameOfPracticeResource_DL(
				Integer.parseInt(testData.getProperty("non.existing.resource.id")), practiceId);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get resource display details error message by entering non-existing practiceId")
	public void testGetResourceDisplayNameForNonExistingPracticeId() throws IOException {
		logStep("fetch resource details error message via non-existing practice id");
		getResourceDetailsApiResponse = resourceInfo.getDisplayNameOfPracticeResource_DL(resourceId,
				Integer.parseInt(testData.getProperty("non.existing.practice.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 400);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get resource display details error message by entering non-existing resourceId, non-existing practiceId")
	public void testGetResourceDisplayNameForNonExistingPracticeIdResourceId() throws IOException {
		logStep("fetch resource details error message via non-existing practiceId, non-existing practiceId");
		getResourceDetailsApiResponse = resourceInfo.getDisplayNameOfPracticeResource_DL(Integer.parseInt(testData.getProperty("non.existing.resource.id")),
				Integer.parseInt(testData.getProperty("non.existing.practice.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Get resource details error message by entering null resource id and practice id")
	public void testGetResourceDisplayNameForNullResourceId() throws IOException {
		logStep("fetch resource details error message via Null values");
		getResourceDetailsApiResponse = resourceInfo.getDisplayNameOfPracticeResource_DL_NULL();
		logStep("Validating response information");
		CCUtils.responseTimeValidation(getResourceDetailsApiResponse);
		CCUtils.assertEquals(getResourceDetailsApiResponse.getStatusCode(), 400);
	}
	
	@Test(enabled = false)
    public int createPractice(String status, String clientName, String clientSapNumber, String testFlag, int clientType,
            String clientTimezoneId, String systemName, String sysOrgType, String sysParentType, int ehrIdentifier,
            int pmIdentifier, String enterpriseName, String enterpriseType, String enterpriseParentType,
            String enterpriseTimeZone, String practiceDisplayName, String practiceName, String practiceOrgType,
            String practiceParentType, String practiceTimeZone) throws IOException {
        logStep("Create nge practice below the client with valid details");
        createClientApiResponse = clientInfo.createNewClient_DataLayer(status, clientName, clientSapNumber, testFlag,
                clientType, clientTimezoneId);
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
        return orgId;
    }
}