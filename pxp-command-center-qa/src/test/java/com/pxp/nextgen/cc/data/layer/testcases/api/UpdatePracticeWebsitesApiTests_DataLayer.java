// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.DisplayText;
import com.pxp.cc.pojos.OrgWebsites;
import com.pxp.cc.pojos.SavePracticeWebsite;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdatePracticeWebsitesApiTests_DataLayer extends BaseRest {
    private OrgInfo orgInfo;
    private ClientInfo clientInfo;
    private Validations validations;
    Response updatePracticeWebsiteApiResponse, getPracticeWebsitesApiResponse, addPracticeWebsitesApiResponse, createClientApiResponse, createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse;
    List<SavePracticeWebsite> savePracticeWebsite = new ArrayList<SavePracticeWebsite>();
    SavePracticeWebsite savePracticeWebsiteObj;
    DisplayText displayTextObj, displayTextObj1;
    List<OrgWebsites> orgWebsites = new ArrayList<OrgWebsites>();
    Map<String, Object> displayText1 = new HashMap<String, Object>();
    OrgWebsites orgWebsites1;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        orgInfo = new OrgInfo();
        clientInfo = new ClientInfo();
        validations = new Validations();
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Update the Single Practice Website")
    public void updatePracticeWebsiteApiSuccess() throws IOException {
        int orgId = createPractice();
        logStep("Add a SiteId for a given orgId");
        displayText1.put("en", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.en")).concat(CCUtils.generateAlphaNumericString(2)));
        displayText1.put("es", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.es")).concat(CCUtils.generateAlphaNumericString(2)));
        orgWebsites1 = new OrgWebsites(testData.getProperty("api.practice.website.url").replace("1", CCUtils.generateAlphaNumericString(2).trim()), testData.getProperty("api.practice.website.type"),
                Integer.parseInt(testData.getProperty("api.practice.website.sortOrder")), displayText1);
        orgWebsites.clear();
        orgWebsites.add(orgWebsites1);
        addPracticeWebsitesApiResponse = orgInfo.addPracticeWebsiteWithPracticeIdHasExternalLinks("DL", orgId, orgWebsites);
        logStep("Getting SiteId information for a given orgId");
        getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
        int siteId = getPracticeWebsitesApiResponse.jsonPath().get("siteId[0]");
        String url = getPracticeWebsitesApiResponse.jsonPath().get("url[0]");
        String updatedUrl = url.concat(CCUtils.generateAlphaNumericString(2));
        String englishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].en");
        String updatedEnglishDisplay = englishDisplay.concat(CCUtils.generateAlphaNumericString(2));
        String spanishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].es");
        String updatedSpanishDisplay = spanishDisplay.concat(CCUtils.generateAlphaNumericString(2));
        String type = getPracticeWebsitesApiResponse.jsonPath().get("type[0]");
        int sortOrder = getPracticeWebsitesApiResponse.jsonPath().get("sortOrder[0]");
        displayTextObj = new DisplayText(updatedEnglishDisplay, updatedSpanishDisplay);
        savePracticeWebsiteObj = new SavePracticeWebsite(siteId, updatedUrl, type, sortOrder, displayTextObj, false);
        savePracticeWebsite.clear();
        savePracticeWebsite.add(savePracticeWebsiteObj);
        logStep("Update a SiteId for a given orgId");
        updatePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesResponse("Valid", "DL", orgId, savePracticeWebsite);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePracticeWebsiteApiResponse);
        CCUtils.assertEquals(updatePracticeWebsiteApiResponse.getStatusCode(), 200);
        Response updateGetPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
        int siteId1 = updateGetPracticeWebsitesApiResponse.jsonPath().get("siteId[0]");
        String url1 = updateGetPracticeWebsitesApiResponse.jsonPath().get("url[0]");
        String englishDisplay1 = updateGetPracticeWebsitesApiResponse.jsonPath().get("displayText[0].en");
        String spanishDisplay1 = updateGetPracticeWebsitesApiResponse.jsonPath().get("displayText[0].es");
        if (siteId == siteId1) {
            if (url1.equalsIgnoreCase(updatedUrl) && englishDisplay1.equalsIgnoreCase(updatedEnglishDisplay) && spanishDisplay1.equalsIgnoreCase(updatedSpanishDisplay))
                logStep("Updated successfully");
        }
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Update the multiple Practice Website")
    public void updateMultiPracticeWebsiteApiSuccess() throws IOException {
        int orgId = createPractice();
        logStep("Add multiple SiteIds for a given orgId");
        orgWebsites.clear();
        for (int i = 0; i < 3; i++) {
            displayText1.put("en", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.en")).concat(CCUtils.generateAlphaNumericString(2)));
            displayText1.put("es", CCUtils.generateStringWithSpace(testData.getProperty("api.practice.website.es")).concat(CCUtils.generateAlphaNumericString(2)));
            orgWebsites1 = new OrgWebsites(testData.getProperty("api.practice.website.url").replace("1", CCUtils.generateAlphaNumericString(2).trim()), testData.getProperty("api.practice.website.type"),
                    Integer.parseInt(testData.getProperty("api.practice.website.sortOrder")), displayText1);
            orgWebsites.add(orgWebsites1);
        }
        addPracticeWebsitesApiResponse = orgInfo.addPracticeWebsiteWithPracticeIdHasExternalLinks("DL", orgId, orgWebsites);
        logStep("Getting SiteId information for a given orgId");
        getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
        List<String> siteIds = getPracticeWebsitesApiResponse.jsonPath().get("siteId");
        savePracticeWebsite.clear();
        for (int i = 0; i < siteIds.size(); i++) {
            int siteId = getPracticeWebsitesApiResponse.jsonPath().get("siteId[" + i + "]");
            String url = getPracticeWebsitesApiResponse.jsonPath().get("url[" + i + "]");
            String updatedUrl = url.concat(CCUtils.generateAlphaNumericString(2));
            String englishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[" + i + "].en");
            String updatedEnglishDisplay = englishDisplay.concat(CCUtils.generateAlphaNumericString(2));
            String spanishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[" + i + "].es");
            String updatedSpanishDisplay = spanishDisplay.concat(CCUtils.generateAlphaNumericString(2));
            String type = getPracticeWebsitesApiResponse.jsonPath().get("type[" + i + "]");
            int sortOrder = getPracticeWebsitesApiResponse.jsonPath().get("sortOrder[" + i + "]");
            displayTextObj = new DisplayText(updatedEnglishDisplay, updatedSpanishDisplay);
            savePracticeWebsiteObj = new SavePracticeWebsite(siteId, updatedUrl, type, sortOrder, displayTextObj, false);
            savePracticeWebsite.add(savePracticeWebsiteObj);
        }
        logStep("Update multiple SiteIds for a given orgId");
        updatePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesResponse("Valid", "DL", orgId, savePracticeWebsite);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePracticeWebsiteApiResponse);
        CCUtils.assertEquals(updatePracticeWebsiteApiResponse.getStatusCode(), 200);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Update the Single Practice Website for Invalid datatype of Practice Id")
    public void updatePracticeWebsiteInvaliDataTypePracticeApi() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links"));
        logStep("Getting SiteId information for a given orgId");
        getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
        int siteId = getPracticeWebsitesApiResponse.jsonPath().get("siteId[0]");
        String url = getPracticeWebsitesApiResponse.jsonPath().get("url[0]");
        String englishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].en");
        String spanishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].es");
        String type = getPracticeWebsitesApiResponse.jsonPath().get("type[0]");
        int sortOrder = getPracticeWebsitesApiResponse.jsonPath().get("sortOrder[0]");
        displayTextObj = new DisplayText(englishDisplay, spanishDisplay);
        savePracticeWebsiteObj = new SavePracticeWebsite(siteId, url, type, sortOrder, displayTextObj, true);
        savePracticeWebsite.clear();
        savePracticeWebsite.add(savePracticeWebsiteObj);
        String orgIdString = orgId + "Sample";
        logStep("Update a SiteId for a given orgId");
        updatePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesInvaliDatatypeResponse("DL", orgIdString, savePracticeWebsite);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePracticeWebsiteApiResponse);
        CCUtils.assertEquals(updatePracticeWebsiteApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Update the Single Practice Website for Invalid Method Call")
    public void updatePracticeWebsiteInvalidMethodCallApi() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links"));
        logStep("Getting SiteId information for a given orgId");
        getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
        int siteId = getPracticeWebsitesApiResponse.jsonPath().get("siteId[0]");
        String url = getPracticeWebsitesApiResponse.jsonPath().get("url[0]");
        String englishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].en");
        String spanishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].es");
        String type = getPracticeWebsitesApiResponse.jsonPath().get("type[0]");
        int sortOrder = getPracticeWebsitesApiResponse.jsonPath().get("sortOrder[0]");
        displayTextObj = new DisplayText(englishDisplay, spanishDisplay);
        savePracticeWebsiteObj = new SavePracticeWebsite(siteId, url, type, sortOrder, displayTextObj, true);
        savePracticeWebsite.add(savePracticeWebsiteObj);
        logStep("Update a SiteId for a given orgId");
        updatePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesResponse("InValidMethodType", "DL", orgId, savePracticeWebsite);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePracticeWebsiteApiResponse);
        CCUtils.assertEquals(updatePracticeWebsiteApiResponse.getStatusCode(), 405);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "Update the Single Practice Website for Null PracticeId")
    public void updatePracticeWebsiteNullValueApi() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links"));
        logStep("Getting SiteId information for a given orgId");
        getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
        int siteId = getPracticeWebsitesApiResponse.jsonPath().get("siteId[0]");
        String url = getPracticeWebsitesApiResponse.jsonPath().get("url[0]");
        String englishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].en");
        String spanishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].es");
        String type = getPracticeWebsitesApiResponse.jsonPath().get("type[0]");
        int sortOrder = getPracticeWebsitesApiResponse.jsonPath().get("sortOrder[0]");
        displayTextObj = new DisplayText(englishDisplay, spanishDisplay);
        savePracticeWebsiteObj = new SavePracticeWebsite(siteId, url, type, sortOrder, displayTextObj, true);
        savePracticeWebsite.add(savePracticeWebsiteObj);
        logStep("Update a SiteId for a given orgId");
        updatePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesResponse("NullValue", "DL", orgId, savePracticeWebsite);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePracticeWebsiteApiResponse);
        CCUtils.assertEquals(updatePracticeWebsiteApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "Update the Practice Website for invalid PracticeId")
    public void updatePracticeWebsiteInvalidPracticeApi() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links"));
        logStep("Getting SiteId information for a given orgId");
        getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
        int siteId = getPracticeWebsitesApiResponse.jsonPath().get("siteId[0]");
        String url = getPracticeWebsitesApiResponse.jsonPath().get("url[0]");
        String englishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].en");
        String spanishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].es");
        String type = getPracticeWebsitesApiResponse.jsonPath().get("type[0]");
        int sortOrder = getPracticeWebsitesApiResponse.jsonPath().get("sortOrder[0]");
        displayTextObj = new DisplayText(englishDisplay, spanishDisplay);
        savePracticeWebsiteObj = new SavePracticeWebsite(siteId, url, type, sortOrder, displayTextObj, true);
        savePracticeWebsite.add(savePracticeWebsiteObj);
        int clientId = Integer.parseInt(testData.getProperty("api.client.id"));
        logStep("Update a SiteId for a given orgId");
        updatePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesResponse("Valid", "DL", clientId, savePracticeWebsite);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePracticeWebsiteApiResponse);
        validations.verifyErrorResponseBodyForNonExistingPracticeIdTou(updatePracticeWebsiteApiResponse, clientId);
        int systemId = Integer.parseInt(testData.getProperty("api.system.id"));
        logStep("Update a SiteId for a given orgId");
        updatePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesResponse("Valid", "DL", systemId, savePracticeWebsite);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePracticeWebsiteApiResponse);
        validations.verifyErrorResponseBodyForNonExistingPracticeIdTou(updatePracticeWebsiteApiResponse, systemId);
        int enterpriseId = Integer.parseInt(testData.getProperty("api.enterprise.id"));
        logStep("Update a SiteId for a given orgId");
        updatePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesResponse("Valid", "DL", enterpriseId, savePracticeWebsite);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePracticeWebsiteApiResponse);
        validations.verifyErrorResponseBodyForNonExistingPracticeIdTou(updatePracticeWebsiteApiResponse, enterpriseId);
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "Update the Single Practice Website for Invalid URI")
    public void updatePracticeWebsiteInvaliUriApi() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links"));
        logStep("Getting SiteId information for a given orgId");
        getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
        int siteId = getPracticeWebsitesApiResponse.jsonPath().get("siteId[0]");
        String url = getPracticeWebsitesApiResponse.jsonPath().get("url[0]");
        String englishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].en");
        String spanishDisplay = getPracticeWebsitesApiResponse.jsonPath().get("displayText[0].es");
        String type = getPracticeWebsitesApiResponse.jsonPath().get("type[0]");
        int sortOrder = getPracticeWebsitesApiResponse.jsonPath().get("sortOrder[0]");
        displayTextObj = new DisplayText(englishDisplay, spanishDisplay);
        savePracticeWebsiteObj = new SavePracticeWebsite(siteId, url, type, sortOrder, displayTextObj, true);
        savePracticeWebsite.add(savePracticeWebsiteObj);
        logStep("Update a SiteId for a given orgId");
        updatePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesResponse("InValid", "DL", orgId, savePracticeWebsite);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePracticeWebsiteApiResponse);
        CCUtils.assertEquals(updatePracticeWebsiteApiResponse.getStatusCode(), 404);
    }

    @Test(priority = 8, retryAnalyzer = RetryAnalyzer.class, description = "Update Invalid payload")
    public void updatePracticeWebsitesWithPracticeIdHasExternalLinksWithNullPayload() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("api.practice.website.practice.id.existing.links"));
        logStep("Getting SiteId information for a given orgId");
        getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites("DL", orgId);
        int siteId = getPracticeWebsitesApiResponse.jsonPath().get("siteId[0]");
        displayTextObj = new DisplayText(null, null);
        savePracticeWebsiteObj = new SavePracticeWebsite(siteId, null, null, 0, displayTextObj, false);
        savePracticeWebsite.add(savePracticeWebsiteObj);
        logStep("Update a SiteId for a given orgId");
        updatePracticeWebsiteApiResponse = orgInfo.updateDeletePracticeWebsitesResponse("Valid", "DL", orgId, savePracticeWebsite);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePracticeWebsiteApiResponse);
        CCUtils.assertEquals(updatePracticeWebsiteApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(updatePracticeWebsiteApiResponse);
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
                testData.getProperty("api.client.timezoneid"), testData.getProperty("ehr.identifier"),
				testData.getProperty("pm.identifier"));
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
