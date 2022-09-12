// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetUpdateOrgWebsiteApiTests_DataLayer extends BaseRest {
    private OrgInfo orgInfo;
    private ClientInfo clientInfo;
    private Validations validations;
    Response updateOrgWebsiteApiResponse, fetchOrgDetailsApiResponse, createClientApiResponse, createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse;
    Map<String, Object> websiteUrl = new HashMap<String, Object>();

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        orgInfo = new OrgInfo();
        clientInfo = new ClientInfo();
        validations = new Validations();
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Update the Org Website")
    public void updateOrgWebsiteApiSuccess() throws IOException {
        int orgId = createPractice();
        logStep("Update websiteUrl for a given orgId");
        String generateWebsiteUrl=testData.getProperty("api.org.website.Url").concat(CCUtils.generateAlphaNumericString(2));
        websiteUrl.put("websiteUrl",generateWebsiteUrl);
        updateOrgWebsiteApiResponse = orgInfo.updateGetOrgPracticeWebsitesResponse("Valid", "DL", orgId, websiteUrl);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateOrgWebsiteApiResponse);
        CCUtils.assertEquals(updateOrgWebsiteApiResponse.getStatusCode(), 200);
        fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(orgId);
        CCUtils.assertEquals(fetchOrgDetailsApiResponse.jsonPath().get("websiteUrl"), generateWebsiteUrl);
    }

    @Test(priority = 9, retryAnalyzer = RetryAnalyzer.class, description = "Update the Org Website for existing websiteUrl")
    public void updateExistingOrgWebsiteApiSuccess() throws IOException {
        int orgId = createPractice();
        logStep("Update websiteUrl for a given orgId");
        String generateWebsiteUrl=testData.getProperty("api.org.website.Url").concat(CCUtils.generateAlphaNumericString(2));
        websiteUrl.clear();
        websiteUrl.put("websiteUrl",generateWebsiteUrl);
        updateOrgWebsiteApiResponse = orgInfo.updateGetOrgPracticeWebsitesResponse("Valid", "DL", orgId, websiteUrl);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateOrgWebsiteApiResponse);
        CCUtils.assertEquals(updateOrgWebsiteApiResponse.getStatusCode(), 200);
        fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(orgId);
        CCUtils.assertEquals(fetchOrgDetailsApiResponse.jsonPath().get("websiteUrl"), generateWebsiteUrl);
        String responseWebsiteUrl=fetchOrgDetailsApiResponse.jsonPath().get("websiteUrl");
        String updateWebsiteUrl=responseWebsiteUrl.concat(CCUtils.generateAlphaNumericString(4));
        websiteUrl.clear();
        websiteUrl.put("websiteUrl",updateWebsiteUrl);
        updateOrgWebsiteApiResponse = orgInfo.updateGetOrgPracticeWebsitesResponse("Valid", "DL", orgId, websiteUrl);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateOrgWebsiteApiResponse);
        CCUtils.assertEquals(updateOrgWebsiteApiResponse.getStatusCode(), 200);
        fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(orgId);
        CCUtils.assertEquals(fetchOrgDetailsApiResponse.jsonPath().get("websiteUrl"), updateWebsiteUrl);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Update the Org Website for Invalid datatype of Practice Id")
    public void updateOrgWebsiteInvaliDataTypePracticeApi() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links"));
        logStep("Update websiteUrl for a given orgId");
        String generateWebsiteUrl=testData.getProperty("api.org.website.Url").concat(CCUtils.generateAlphaNumericString(2));
        websiteUrl.put("websiteUrl",generateWebsiteUrl);
        String orgIdString = orgId + "Sample";
        updateOrgWebsiteApiResponse = orgInfo.updateGetOrgPracticeWebsitesInvaliDatatypeResponse("DL", orgIdString, websiteUrl);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateOrgWebsiteApiResponse);
        CCUtils.assertEquals(updateOrgWebsiteApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Update the Org Website for Invalid Method Call")
    public void updateOrgWebsiteInvalidMethodCallApi() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links"));
        logStep("Update websiteUrl for a given orgId");
        String generateWebsiteUrl=testData.getProperty("api.org.website.Url").concat(CCUtils.generateAlphaNumericString(2));
        websiteUrl.put("websiteUrl",generateWebsiteUrl);
        updateOrgWebsiteApiResponse = orgInfo.updateGetOrgPracticeWebsitesResponse("InValidMethodType", "DL", orgId, websiteUrl);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateOrgWebsiteApiResponse);
        CCUtils.assertEquals(updateOrgWebsiteApiResponse.getStatusCode(), 405);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Update the org Website for Null PracticeId")
    public void updateOrgWebsiteNullValueApi() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links"));
        logStep("Update websiteUrl for a given orgId");
        String generateWebsiteUrl=testData.getProperty("api.org.website.Url").concat(CCUtils.generateAlphaNumericString(2));
        websiteUrl.put("websiteUrl",generateWebsiteUrl);
        updateOrgWebsiteApiResponse = orgInfo.updateGetOrgPracticeWebsitesResponse("NullValue", "DL", orgId, websiteUrl);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateOrgWebsiteApiResponse);
        CCUtils.assertEquals(updateOrgWebsiteApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "Update the Org Website for all org types")
    public void updateOrgWebsiteForAllOrgTypesApi() throws IOException {
        int clientId = Integer.parseInt(testData.getProperty("api.client.id"));
        String generateWebsiteUrl=testData.getProperty("api.org.website.Url").concat(CCUtils.generateAlphaNumericString(2));
        websiteUrl.put("websiteUrl",generateWebsiteUrl);
        logStep("Update websiteUrl for a given orgId");
        updateOrgWebsiteApiResponse = orgInfo.updateGetOrgPracticeWebsitesResponse("Valid", "DL", clientId, websiteUrl);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateOrgWebsiteApiResponse);
        CCUtils.assertEquals(updateOrgWebsiteApiResponse.getStatusCode(), 200);
        int systemId = Integer.parseInt(testData.getProperty("api.system.id"));
        logStep("Update websiteUrl for a given orgId");
        updateOrgWebsiteApiResponse = orgInfo.updateGetOrgPracticeWebsitesResponse("Valid", "DL", systemId, websiteUrl);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateOrgWebsiteApiResponse);
        CCUtils.assertEquals(updateOrgWebsiteApiResponse.getStatusCode(), 200);
        fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(systemId);
        CCUtils.assertEquals(fetchOrgDetailsApiResponse.jsonPath().get("websiteUrl"), generateWebsiteUrl);
        int enterpriseId = Integer.parseInt(testData.getProperty("api.enterprise.id"));
        logStep("Update websiteUrl for a given orgId");
        updateOrgWebsiteApiResponse = orgInfo.updateGetOrgPracticeWebsitesResponse("Valid", "DL", enterpriseId, websiteUrl);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateOrgWebsiteApiResponse);
        CCUtils.assertEquals(updateOrgWebsiteApiResponse.getStatusCode(), 200);
        fetchOrgDetailsApiResponse = orgInfo.getOrgId_DataLayer(enterpriseId);
        CCUtils.assertEquals(fetchOrgDetailsApiResponse.jsonPath().get("websiteUrl"), generateWebsiteUrl);
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "Update the Org Website for Invalid URI")
    public void updateOrgWebsiteInvaliUriApi() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links"));
        logStep("Update websiteUrl for a given orgId");
        String generateWebsiteUrl=testData.getProperty("api.org.website.Url").concat(CCUtils.generateAlphaNumericString(2));
        websiteUrl.put("websiteUrl",generateWebsiteUrl);
        updateOrgWebsiteApiResponse = orgInfo.updateGetOrgPracticeWebsitesResponse("InValid", "DL", orgId, websiteUrl);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateOrgWebsiteApiResponse);
        CCUtils.assertEquals(updateOrgWebsiteApiResponse.getStatusCode(), 404);
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "Update with Invalid payload")
    public void updateOrgWebsitesWithNullPayload() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links"));
        logStep("Update websiteUrl for a given orgId");
        String generateWebsiteUrl=null;
        websiteUrl.put("websiteUrl",generateWebsiteUrl);
        updateOrgWebsiteApiResponse = orgInfo.updateGetOrgPracticeWebsitesResponse("Valid", "DL", orgId, websiteUrl);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateOrgWebsiteApiResponse);
       validations.verifyInvalidRequestValidation(updateOrgWebsiteApiResponse);
    }

    @Test(priority = 8, retryAnalyzer = RetryAnalyzer.class, description = "Update with Invalid Practice Id")
    public void updateOrgWebsitesWithInvalidPracticeId() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links"))+10000000;
        logStep("Update websiteUrl for a given orgId");
        String generateWebsiteUrl=testData.getProperty("api.org.website.Url").concat(CCUtils.generateAlphaNumericString(2));;
        websiteUrl.put("websiteUrl",generateWebsiteUrl);
        updateOrgWebsiteApiResponse = orgInfo.updateGetOrgPracticeWebsitesResponse("Valid", "DL", orgId, websiteUrl);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateOrgWebsiteApiResponse);
        validations.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(updateOrgWebsiteApiResponse,orgId);
    }

    @Test(enabled = false)
    public int createPractice() throws IOException {
        logStep("Create nge practice below the client with valid details");
        String clientName = testData.getProperty("api.client.name")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
        String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
        createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
                clientName, clientSapNumber, testData.getProperty("api.system.testclient"),
                Integer.parseInt(testData.getProperty("api.client.type")),
                testData.getProperty("api.client.timezoneid"));
        int clientId = createClientApiResponse.jsonPath().getInt("clientId");

        logStep("Create a new system below client.");
        createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "",
                testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"), clientId,
                testData.getProperty("api.parenttype"), "", Integer.parseInt(testData.getProperty("ehr.identifier")),
                Integer.parseInt(testData.getProperty("pm.identifier")));
        int systemId = createSystemApiResponse.jsonPath().getInt("orgId");

        logStep("Create a new enterprise below system.");
        createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"), "",
                testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"), systemId,
                testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
                Integer.parseInt(testData.getProperty("ehr.identifier")),
                Integer.parseInt(testData.getProperty("pm.identifier")));
        int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");

        logStep("Create a new practice below enterprise.");
        createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
                testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
                testData.getProperty("api.practice.orgtype"), enterpriseId,
                testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
                Integer.parseInt(testData.getProperty("ehr.identifier")),
                Integer.parseInt(testData.getProperty("pm.identifier")));
        logStep("Validating Response Information");
        CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
        CCUtils.responseTimeValidation(createPracticeApiResponse);
        validations.verifyCreatePracticeResponseBody(createPracticeApiResponse,
                testData.getProperty("api.practice.name"));
        int orgId = createPracticeApiResponse.jsonPath().get("orgId");
        return orgId;
    }
    
}
