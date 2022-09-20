// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.PracticeSocials;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class AddUpdatePracticeSocialsApiTests_BusinessLayer extends BaseRest {
    private OrgInfo orgInfo;
    private ClientInfo clientInfo;
    private Validations validations;
    Response addUpdatePracticeSocialsApiResponse, getPracticeSocialApiResponse, createClientApiResponse, createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse;
    PracticeSocials practiceSocialsObj;
    ArrayList<PracticeSocials> practiceSocialsReqList = new ArrayList<PracticeSocials>();

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilder();
        orgInfo = new OrgInfo();
        clientInfo = new ClientInfo();
        validations = new Validations();
    }

    @DataProvider(name = "invalidPracticeId")
    public Object[][] invalidPracticeId() throws Exception {
        return new Object[][]{{testData.getProperty("Non.existing.practice.id").trim()},
                {testData.getProperty("api.client.id").trim()}, {testData.getProperty("api.system.id").trim()},
                {testData.getProperty("api.enterprise.id").trim()}, {null}};
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20070_Add Single social to given practiceId")
    public void addSinglePracticeSocialsApiSuccess() throws IOException {
        logStep("TC 001_Verify the Response code as 200 when user try to update the single valid socials for the practice for External Links by using POST Request [BL]");
        int orgId = createPractice();
        logStep("Add a SocialId for a given orgId");
        String socialHref = testData.getProperty("api.practice.socials.twitter") + orgId;
        practiceSocialsObj = new PracticeSocials(1, socialHref, true);
        practiceSocialsReqList.clear();
        practiceSocialsReqList.add(practiceSocialsObj);
        addUpdatePracticeSocialsApiResponse = orgInfo.addUpdatePracticeSocialsResponse("Valid", "BL", orgId, practiceSocialsReqList);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(addUpdatePracticeSocialsApiResponse);
        CCUtils.assertEquals(addUpdatePracticeSocialsApiResponse.getStatusCode(), 200);
        logStep("Getting SocialId information for a given orgId");
        getPracticeSocialApiResponse = orgInfo.getPracticeSocialsResponse("Valid", "BL", orgId);
        int socialId = getPracticeSocialApiResponse.jsonPath().get("socialId[0]");
        String href = getPracticeSocialApiResponse.jsonPath().get("href[0]");
        if (socialId == 1 && href.equalsIgnoreCase(socialHref)) {
            logStep("Added Socials successfully to " + orgId);
        }
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20086_Add multiple socials to given practiceId")
    public void addMultiplePracticeSocialsApiSuccess() throws IOException {
        logStep("TC 010_Verify the Response code as 200 when user try to update the multiple valid socials for the practice for External Links by using POST Request [BL]");
        int orgId = createPractice();
        logStep("Add a SocialId for a given orgId");
        LinkedHashSet<String> socialHref = new LinkedHashSet<String>();
        socialHref.add(testData.getProperty("api.practice.socials.twitter") + orgId);
        socialHref.add(testData.getProperty("api.practice.socials.facebook") + orgId);
        socialHref.add(testData.getProperty("api.practice.socials.linkedIn") + orgId);
        socialHref.add(testData.getProperty("api.practice.socials.youTube") + orgId);
        ArrayList<String> listSocialHref = new ArrayList<String>(socialHref);
        practiceSocialsReqList.clear();
        for (int i = 0; i < listSocialHref.size(); i++) {
            practiceSocialsObj = new PracticeSocials(i + 1, listSocialHref.get(i), true);
            practiceSocialsReqList.add(practiceSocialsObj);
        }
        addUpdatePracticeSocialsApiResponse = orgInfo.addUpdatePracticeSocialsResponse("Valid", "BL", orgId, practiceSocialsReqList);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(addUpdatePracticeSocialsApiResponse);
        CCUtils.assertEquals(addUpdatePracticeSocialsApiResponse.getStatusCode(), 200);
        logStep("Getting SocialId information for a given orgId");
        getPracticeSocialApiResponse = orgInfo.getPracticeSocialsResponse("Valid", "BL", orgId);
        for (int i = 0; i < listSocialHref.size(); i++) {
            int socialId = getPracticeSocialApiResponse.jsonPath().get("socialId[" + i + "]");
            String href = getPracticeSocialApiResponse.jsonPath().get("href[" + i + "]");
            if (socialHref.contains(href)) {
                logStep(href+" added Socials successfully to " + orgId);
            }
        }
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20071_Add Single social to given practiceId with Invalid URI")
    public void addSinglePracticeSocialsWithInvalidUriApi() throws IOException {
        logStep("TC 002_Verify the Response code as 404, when user try to give invalid URI for Practice socials of External Links by using POST Request [BL]");
        int orgId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        logStep("Add a SocialId for a given orgId");
        String socialHref = testData.getProperty("api.practice.socials.twitter") + orgId;
        practiceSocialsObj = new PracticeSocials(1, socialHref, true);
        practiceSocialsReqList.clear();
        practiceSocialsReqList.add(practiceSocialsObj);
        addUpdatePracticeSocialsApiResponse = orgInfo.addUpdatePracticeSocialsResponse("InValid", "BL", orgId, practiceSocialsReqList);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(addUpdatePracticeSocialsApiResponse);
        CCUtils.assertEquals(addUpdatePracticeSocialsApiResponse.getStatusCode(), 404);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20072_Add Single social to Invalid Datatype PracticeId")
    public void addSinglePracticeSocialsWithInvalidDatatypePracticeApi() throws IOException {
        logStep("TC 003_Verify the Response code as 400, when user try to give alphanumeric practice Id for Practice socials of External Links by using POST Request [BL]");
        String orgId = testData.getProperty("PXPP.18282.api.practice.id") + "Sample";
        logStep("Add a SocialId for a given orgId");
        String socialHref = testData.getProperty("api.practice.socials.twitter") + orgId;
        practiceSocialsObj = new PracticeSocials(1, socialHref, true);
        practiceSocialsReqList.clear();
        practiceSocialsReqList.add(practiceSocialsObj);
        addUpdatePracticeSocialsApiResponse = orgInfo.addUpdatePracticeSocialsInvaliDatatypeResponse("BL", orgId, practiceSocialsReqList);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(addUpdatePracticeSocialsApiResponse);
        CCUtils.assertEquals(addUpdatePracticeSocialsApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20073_Add Single social to given practiceId with Invalid Mathod Type")
    public void addSinglePracticeSocialsWithInvalidMethodApi() throws IOException {
        logStep("TC 004_Verify the Response code as 405, when user try  PUT method instead of POST Request for practice socials of External Links [BL]");
        int orgId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        logStep("Add a SocialId for a given orgId");
        String socialHref = testData.getProperty("api.practice.socials.twitter") + orgId;
        practiceSocialsObj = new PracticeSocials(1, socialHref, true);
        practiceSocialsReqList.clear();
        practiceSocialsReqList.add(practiceSocialsObj);
        addUpdatePracticeSocialsApiResponse = orgInfo.addUpdatePracticeSocialsResponse("InValidMethodType", "BL", orgId, practiceSocialsReqList);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(addUpdatePracticeSocialsApiResponse);
        CCUtils.assertEquals(addUpdatePracticeSocialsApiResponse.getStatusCode(), 405);
    }

    @Test(priority = 6, dataProvider = "invalidPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20074_PXPP-20075_PXPP-20078_add a Practice Socials with invalid or null practice id")
    public void addPracticesocialsWithInvalidAndNullPracticeId(String practiceId) throws IOException {
        logStep("TC 005,TC006 and TC009 _ add a Practice Socials with invalid and different orgtypes(0,1,2) and null practice id [BL]");
        logStep("Add a SocialId for a given orgId");
        String socialHref = testData.getProperty("api.practice.socials.twitter") + practiceId;
        practiceSocialsObj = new PracticeSocials(1, socialHref, true);
        practiceSocialsReqList.clear();
        practiceSocialsReqList.add(practiceSocialsObj);
        addUpdatePracticeSocialsApiResponse = orgInfo.addUpdatePracticeSocialsInvaliDatatypeResponse("BL", practiceId, practiceSocialsReqList);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(addUpdatePracticeSocialsApiResponse);
        CCUtils.assertEquals(addUpdatePracticeSocialsApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20076_Add Single social to given practiceId with Invalid Payloadf")
    public void addSinglePracticeSocialsWithInvalidPayloadApi() throws IOException {
        logStep("TC 007_Verify the Response code as 400, when user try to give Valid practice and invalid /empty payload for Practice socials of External Links by using POST Request [BL]");
        int orgId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        logStep("Add a SocialId for a given orgId");
        practiceSocialsObj = new PracticeSocials(0, null, true);
        practiceSocialsReqList.clear();
        practiceSocialsReqList.add(practiceSocialsObj);
        addUpdatePracticeSocialsApiResponse = orgInfo.addUpdatePracticeSocialsResponse("Valid", "BL", orgId, practiceSocialsReqList);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(addUpdatePracticeSocialsApiResponse);
        CCUtils.assertEquals(addUpdatePracticeSocialsApiResponse.getStatusCode(), 400);
        CCUtils.assertEquals(addUpdatePracticeSocialsApiResponse.jsonPath().get("message"), "upsertSocials.socials[0].href: href cannot be empty");
    }

    @Test(priority = 8, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20077_Update multiple socials to given practiceId")
    public void updateMultiplePracticeSocialsApiSuccess() throws IOException {
        logStep("TC 008_Verify the Response code as 200, when user try to update the  existing Practice socials  for External Links by using POST Request [BL]");
        int orgId = createPractice();
        logStep("Add a SocialId for a given orgId");
        LinkedHashSet<String> socialHref = new LinkedHashSet<String>();
        socialHref.add(testData.getProperty("api.practice.socials.twitter") + orgId);
        socialHref.add(testData.getProperty("api.practice.socials.facebook") + orgId);
        socialHref.add(testData.getProperty("api.practice.socials.linkedIn") + orgId);
        socialHref.add(testData.getProperty("api.practice.socials.youTube") + orgId);
        ArrayList<String> listSocialHref = new ArrayList<String>(socialHref);
        practiceSocialsReqList.clear();
        for (int i = 0; i < listSocialHref.size(); i++) {
            practiceSocialsObj = new PracticeSocials(i + 1, listSocialHref.get(i), true);
            practiceSocialsReqList.add(practiceSocialsObj);
        }
        addUpdatePracticeSocialsApiResponse = orgInfo.addUpdatePracticeSocialsResponse("Valid", "BL", orgId, practiceSocialsReqList);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(addUpdatePracticeSocialsApiResponse);
        CCUtils.assertEquals(addUpdatePracticeSocialsApiResponse.getStatusCode(), 200);
        LinkedHashSet<String> socialHrefUpdated = new LinkedHashSet<String>();
        socialHrefUpdated.add(testData.getProperty("api.practice.socials.twitter") + orgId + "/Updated");
        socialHrefUpdated.add(testData.getProperty("api.practice.socials.facebook") + orgId + "/Updated");
        socialHrefUpdated.add(testData.getProperty("api.practice.socials.linkedIn") + orgId + "/Updated");
        socialHrefUpdated.add(testData.getProperty("api.practice.socials.youTube") + orgId + "/Updated");
        ArrayList<String> listSocialHrefUpdated = new ArrayList<String>(socialHrefUpdated);
        practiceSocialsReqList.clear();
        for (int i = 0; i < listSocialHrefUpdated.size(); i++) {
            practiceSocialsObj = new PracticeSocials(i + 1, listSocialHrefUpdated.get(i), false);
            practiceSocialsReqList.add(practiceSocialsObj);
        }
        addUpdatePracticeSocialsApiResponse = orgInfo.addUpdatePracticeSocialsResponse("Valid", "BL", orgId, practiceSocialsReqList);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(addUpdatePracticeSocialsApiResponse);
        CCUtils.assertEquals(addUpdatePracticeSocialsApiResponse.getStatusCode(), 200);
        logStep("Getting SocialId information for a given orgId");
        getPracticeSocialApiResponse = orgInfo.getPracticeSocialsResponse("Valid", "BL", orgId);
        for (int i = 0; i < listSocialHrefUpdated.size(); i++) {
            int socialId = getPracticeSocialApiResponse.jsonPath().get("socialId[" + i + "]");
            String href = getPracticeSocialApiResponse.jsonPath().get("href[" + i + "]");
            if (listSocialHrefUpdated.contains(href)) {
                logStep(href + " Updated successfully to " + orgId);
            }
        }
    }

    @Test(enabled = false)
    public int createPractice() throws IOException {
        logStep("Create nge practice below the client with valid details");
        String clientName = testData.getProperty("api.client.name")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
        String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
        createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"),
                clientName, clientSapNumber, testData.getProperty("api.system.testclient"),
                Integer.parseInt(testData.getProperty("api.client.type")),
                testData.getProperty("api.client.timezoneid"));
        int clientId = createClientApiResponse.jsonPath().getInt("clientId");

        logStep("Create a new system below client.");
        createSystemApiResponse = orgInfo.createOrg(testData.getProperty("api.system.active"),
                testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
                testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
                testData.getProperty("api.system.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")),
                Integer.parseInt(testData.getProperty("pm.identifier")));
        int systemId = createSystemApiResponse.jsonPath().getInt("orgId");

        logStep("Create a new enterprise below system.");
        createEnterpriseApiResponse = orgInfo.createOrg(testData.getProperty("api.enterprise.active"),
                testData.getProperty("api.enterprise.displayname"), testData.getProperty("api.enterprise.name"),
                testData.getProperty("api.enterprise.orgtype"), systemId,
                testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
                Integer.parseInt(testData.getProperty("ehr.identifier")),
                Integer.parseInt(testData.getProperty("pm.identifier")));
        int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");

        logStep("Create a new practice below enterprise.");
        createPracticeApiResponse = orgInfo.createOrg(testData.getProperty("api.practice.active"),
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
