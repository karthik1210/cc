// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.UpdateTou;
import com.pxp.cc.pojos.TouContent;
import com.pxp.cc.pojos.TouSolutions;
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

public class UpdateTouApiTests_DataLayer extends BaseRest {
    private OrgInfo orgInfo;
    private ClientInfo clientInfo;
    private Validations validations;
    Response updateTouApiResponse, getTousApiResponse, getTouIdResponse;
    ArrayList<TouSolutions> touSolutionsList = new ArrayList<TouSolutions>();
    TouSolutions touSolutionsObj;
    TouContent touContentObj;
    UpdateTou updateTou;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        orgInfo = new OrgInfo();
        clientInfo = new ClientInfo();
        validations = new Validations();
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Create New Version TouId for the existing TouId by changing Name and Content")
    public void createNewVersionTouIdForGivenTouIdApiSuccess() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        String touType = testData.getProperty("api.valid.tou.type");
        logStep("Get all touId details which are associated with give orgId");
        getTousApiResponse = orgInfo.getListOfTouForAPractice_DL(orgId, touType);
        logStep("Get the 1st touId of the given orgId");
        int touId = getTousApiResponse.jsonPath().get("touId[0]");
        logStep("Get the details of the given touId of the orgId");
        getTouIdResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_DL(orgId, touId);
        String name = getTouIdResponse.jsonPath().get("name[0]");
        String updatedName = name.concat(CCUtils.generateAlphaNumericString(2));
        int solutionId = getTouIdResponse.jsonPath().get("solutions.solutionId[0]");
        String solutionName = getTouIdResponse.jsonPath().get("solutions.name[0]");
        boolean solutionActive = getTouIdResponse.jsonPath().get("solutions.active[0]");
        touSolutionsObj = new TouSolutions(solutionId, solutionName, solutionActive);
        touSolutionsList.clear();
        touSolutionsList.add(touSolutionsObj);
        String contentEng = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.content.eng")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(50))));
        String contentSpan = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.content.span")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(50))));
        touContentObj = new TouContent(contentEng, contentSpan);
        logStep("Create a new Version tou for a given orgId and touId by changing Name and Content");
        updateTou = new UpdateTou(updatedName, true, touSolutionsList, touType, touContentObj);
        updateTouApiResponse = orgInfo.updateTouPracticeResponse("Valid", "DL", orgId, touId, updateTou);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateTouApiResponse);
        CCUtils.assertEquals(updateTouApiResponse.getStatusCode(), 200);
        getTousApiResponse = orgInfo.getListOfTouForAPractice_DL(orgId, touType);
        List<String> touIds = getTousApiResponse.jsonPath().get("touId");
        for (int i = touIds.size() - 1; i > 0; i--) {
            String touName = getTousApiResponse.jsonPath().get("name[" + i + "]");
            if (updatedName.equalsIgnoreCase(touName)) {
                int newVersionTouId = getTousApiResponse.jsonPath().get("touId[" + i + "]");
                logStep("New version touId generated for " + touId + "is :" + newVersionTouId);
            }
        }
        logStep("New version touId not generated for " + touId);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Update the solutions for the existing touId ")
    public void updateSolutionsForTouIdApiSuccess() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        String touType = testData.getProperty("api.valid.tou.type");
        logStep("Get all touId details which are associated with give orgId");
        getTousApiResponse = orgInfo.getListOfTouForAPractice_DL(orgId, touType);
        logStep("Get the 1st touId of the given orgId");
        int touId = getTousApiResponse.jsonPath().get("touId[0]");
        logStep("Get the details of the given touId of the orgId");
        getTouIdResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_DL(orgId, touId);
        String name = getTouIdResponse.jsonPath().get("name");
        int solutionId = getTouIdResponse.jsonPath().get("solutions.solutionId[0]");
        String solutionName = getTouIdResponse.jsonPath().get("solutions.name[0]");
        boolean solutionActive = false;
        touSolutionsObj = new TouSolutions(solutionId, solutionName, solutionActive);
        touSolutionsList.clear();
        touSolutionsList.add(touSolutionsObj);
        String contentEng = getTouIdResponse.jsonPath().get("content.en");
        String contentSpan = getTouIdResponse.jsonPath().get("content.es");
        touContentObj = new TouContent(contentEng, contentSpan);
        logStep("update the solution for the given tou_id");
        updateTou = new UpdateTou(name, false, touSolutionsList, touType, touContentObj);
        updateTouApiResponse = orgInfo.updateTouPracticeResponse("Valid", "DL", orgId, touId, updateTou);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateTouApiResponse);
        CCUtils.assertEquals(updateTouApiResponse.getStatusCode(), 200);
        getTouIdResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_DL(orgId, touId);
        boolean updateSolutionActive = getTouIdResponse.jsonPath().get("solutions.active[0]");
        if (solutionActive == updateSolutionActive) {
            logStep("Solution updated successfully for " + touId);
        }
        logStep("Solution not updated successfully for " + touId);
        touSolutionsObj = new TouSolutions(solutionId, solutionName, true);
        touSolutionsList.clear();
        touSolutionsList.add(touSolutionsObj);
        logStep("update back the solution status for the given tou_id");
        updateTou = new UpdateTou(name, false, touSolutionsList, touType, touContentObj);
        updateTouApiResponse = orgInfo.updateTouPracticeResponse("Valid", "DL", orgId, touId, updateTou);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateTouApiResponse);
        CCUtils.assertEquals(updateTouApiResponse.getStatusCode(), 200);
        getTouIdResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_DL(orgId, touId);
        updateSolutionActive = getTouIdResponse.jsonPath().get("solutions.active[0]");
        if (solutionActive == updateSolutionActive) {
            logStep("Solution updated successfully for " + touId);
        }
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Update the Tou Id for Invalid datatype of Practice Id")
    public void updateTouInvaliDataTypePracticeApi() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        String stringOrgId = testData.getProperty("PXPP.18282.api.practice.id").concat("Sample");
        String touType = testData.getProperty("api.valid.tou.type");
        logStep("Get all touId details which are associated with give orgId");
        getTousApiResponse = orgInfo.getListOfTouForAPractice_DL(orgId, touType);
        logStep("Get the 1st touId of the given orgId");
        int touId = getTousApiResponse.jsonPath().get("touId[0]");
        String stringTouId = touId + "Sample";
        logStep("Get the details of the given touId of the orgId");
        getTouIdResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_DL(orgId, touId);
        String name = getTouIdResponse.jsonPath().get("name");
        int solutionId = getTouIdResponse.jsonPath().get("solutions.solutionId[0]");
        String solutionName = getTouIdResponse.jsonPath().get("solutions.name[0]");
        boolean solutionActive = false;
        touSolutionsObj = new TouSolutions(solutionId, solutionName, solutionActive);
        touSolutionsList.clear();
        touSolutionsList.add(touSolutionsObj);
        String contentEng = getTouIdResponse.jsonPath().get("content.en");
        String contentSpan = getTouIdResponse.jsonPath().get("content.es");
        touContentObj = new TouContent(contentEng, contentSpan);
        logStep("update the solution for the given tou_id");
        updateTou = new UpdateTou(name, false, touSolutionsList, touType, touContentObj);
        updateTouApiResponse = orgInfo.updateTouPracticeInvaliDatatypeResponse("DL", stringOrgId, stringTouId, updateTou);
        ;
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateTouApiResponse);
        CCUtils.assertEquals(updateTouApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Update the TouId for Invalid Method Call")
    public void updateTouInvalidMethodCallApi() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        String touType = testData.getProperty("api.valid.tou.type");
        logStep("Get all touId details which are associated with give orgId");
        getTousApiResponse = orgInfo.getListOfTouForAPractice_DL(orgId, touType);
        logStep("Get the 1st touId of the given orgId");
        int touId = getTousApiResponse.jsonPath().get("touId[0]");
        logStep("Get the details of the given touId of the orgId");
        getTouIdResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_DL(orgId, touId);
        String name = getTouIdResponse.jsonPath().get("name");
        int solutionId = getTouIdResponse.jsonPath().get("solutions.solutionId[0]");
        String solutionName = getTouIdResponse.jsonPath().get("solutions.name[0]");
        boolean solutionActive = false;
        touSolutionsObj = new TouSolutions(solutionId, solutionName, solutionActive);
        touSolutionsList.clear();
        touSolutionsList.add(touSolutionsObj);
        String contentEng = getTouIdResponse.jsonPath().get("content.en");
        String contentSpan = getTouIdResponse.jsonPath().get("content.es");
        touContentObj = new TouContent(contentEng, contentSpan);
        logStep("update the solution for the given tou_id");
        updateTou = new UpdateTou(name, false, touSolutionsList, touType, touContentObj);
        updateTouApiResponse = orgInfo.updateTouPracticeResponse("InValidMethodType", "DL", orgId, touId, updateTou);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateTouApiResponse);
        CCUtils.assertEquals(updateTouApiResponse.getStatusCode(), 405);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "Update the touId for Null PracticeId")
    public void updateTouNullValueApi() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        String touType = testData.getProperty("api.valid.tou.type");
        logStep("Get all touId details which are associated with give orgId");
        getTousApiResponse = orgInfo.getListOfTouForAPractice_DL(orgId, touType);
        logStep("Get the 1st touId of the given orgId");
        int touId = getTousApiResponse.jsonPath().get("touId[0]");
        logStep("Get the details of the given touId of the orgId");
        getTouIdResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_DL(orgId, touId);
        String name = getTouIdResponse.jsonPath().get("name");
        int solutionId = getTouIdResponse.jsonPath().get("solutions.solutionId[0]");
        String solutionName = getTouIdResponse.jsonPath().get("solutions.name[0]");
        boolean solutionActive = false;
        touSolutionsObj = new TouSolutions(solutionId, solutionName, solutionActive);
        touSolutionsList.clear();
        touSolutionsList.add(touSolutionsObj);
        String contentEng = getTouIdResponse.jsonPath().get("content.en");
        String contentSpan = getTouIdResponse.jsonPath().get("content.es");
        touContentObj = new TouContent(contentEng, contentSpan);
        logStep("update the solution for the given tou_id");
        updateTou = new UpdateTou(name, false, touSolutionsList, touType, touContentObj);
        updateTouApiResponse = orgInfo.updateTouPracticeResponse("NullValue", "DL", orgId, touId, updateTou);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateTouApiResponse);
        CCUtils.assertEquals(updateTouApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "Update the TouId for invalid PracticeId")
    public void updateTouInvalidPracticeApi() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        String touType = testData.getProperty("api.valid.tou.type");
        logStep("Get all touId details which are associated with give orgId");
        getTousApiResponse = orgInfo.getListOfTouForAPractice_DL(orgId, touType);
        logStep("Get the 1st touId of the given orgId");
        int touId = getTousApiResponse.jsonPath().get("touId[0]");
        logStep("Get the details of the given touId of the orgId");
        getTouIdResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_DL(orgId, touId);
        String name = getTouIdResponse.jsonPath().get("name");
        int solutionId = getTouIdResponse.jsonPath().get("solutions.solutionId[0]");
        String solutionName = getTouIdResponse.jsonPath().get("solutions.name[0]");
        boolean solutionActive = false;
        touSolutionsObj = new TouSolutions(solutionId, solutionName, solutionActive);
        touSolutionsList.clear();
        touSolutionsList.add(touSolutionsObj);
        String contentEng = getTouIdResponse.jsonPath().get("content.en");
        String contentSpan = getTouIdResponse.jsonPath().get("content.es");
        touContentObj = new TouContent(contentEng, contentSpan);
        logStep("update the solution for the given tou_id");
        updateTou = new UpdateTou(name, false, touSolutionsList, touType, touContentObj);
        int clientId = Integer.parseInt(testData.getProperty("api.client.id"));
        logStep("Update a touId for a given orgId");
        updateTouApiResponse = orgInfo.updateTouPracticeResponse("Valid", "DL", clientId, touId, updateTou);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateTouApiResponse);
        validations.verifyErrorResponseBodyForNonExistingPracticeIdTou(updateTouApiResponse, clientId);
        int systemId = Integer.parseInt(testData.getProperty("api.system.id"));
        logStep("Update a TouId for a given orgId");
        updateTouApiResponse = orgInfo.updateTouPracticeResponse("Valid", "DL", systemId, touId, updateTou);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateTouApiResponse);
        validations.verifyErrorResponseBodyForNonExistingPracticeIdTou(updateTouApiResponse, systemId);
        int enterpriseId = Integer.parseInt(testData.getProperty("api.enterprise.id"));
        logStep("Update a TouId for a given orgId");
        updateTouApiResponse = orgInfo.updateTouPracticeResponse("Valid", "DL", enterpriseId, touId, updateTou);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateTouApiResponse);
        validations.verifyErrorResponseBodyForNonExistingPracticeIdTou(updateTouApiResponse, enterpriseId);
        updateTouApiResponse = orgInfo.updateTouPracticeResponse("Valid", "DL", orgId, 1234, updateTou);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateTouApiResponse);
        CCUtils.assertEquals(updateTouApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "Update the touId for Invalid URI")
    public void updateTouInvaliUriApi() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        String touType = testData.getProperty("api.valid.tou.type");
        logStep("Get all touId details which are associated with give orgId");
        getTousApiResponse = orgInfo.getListOfTouForAPractice_DL(orgId, touType);
        logStep("Get the 1st touId of the given orgId");
        int touId = getTousApiResponse.jsonPath().get("touId[0]");
        logStep("Get the details of the given touId of the orgId");
        getTouIdResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_DL(orgId, touId);
        String name = getTouIdResponse.jsonPath().get("name");
        int solutionId = getTouIdResponse.jsonPath().get("solutions.solutionId[0]");
        String solutionName = getTouIdResponse.jsonPath().get("solutions.name[0]");
        boolean solutionActive = false;
        touSolutionsObj = new TouSolutions(solutionId, solutionName, solutionActive);
        touSolutionsList.clear();
        touSolutionsList.add(touSolutionsObj);
        String contentEng = getTouIdResponse.jsonPath().get("content.en");
        String contentSpan = getTouIdResponse.jsonPath().get("content.es");
        touContentObj = new TouContent(contentEng, contentSpan);
        logStep("update the solution for the given tou_id");
        updateTou = new UpdateTou(name, false, touSolutionsList, touType, touContentObj);
        updateTouApiResponse = orgInfo.updateTouPracticeResponse("InValid", "DL", orgId, touId, updateTou);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateTouApiResponse);
        CCUtils.assertEquals(updateTouApiResponse.getStatusCode(), 404);
    }

    @Test(priority = 8, enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Update Invalid payload")
    public void updateTousForPracticeIdWithNullPayload() throws IOException {
        int orgId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        String touType = testData.getProperty("api.valid.tou.type");
        logStep("Get all touId details which are associated with give orgId");
        getTousApiResponse = orgInfo.getListOfTouForAPractice_DL(orgId, touType);
        logStep("Get the 1st touId of the given orgId");
        int touId = getTousApiResponse.jsonPath().get("touId[0]");
        logStep("Get the details of the given touId of the orgId");
        getTouIdResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_DL(orgId, touId);
        String name = getTouIdResponse.jsonPath().get("name");
        int solutionId = getTouIdResponse.jsonPath().get("solutions.solutionId[0]");
        String solutionName = getTouIdResponse.jsonPath().get("solutions.name[0]");
        boolean solutionActive = false;
        touSolutionsObj = new TouSolutions(solutionId, null, solutionActive);
        touSolutionsList.clear();
        touSolutionsList.add(touSolutionsObj);
        touContentObj = new TouContent(null, null);
        logStep("update the solution for the given tou_id");
        updateTou = new UpdateTou(null, false, touSolutionsList, touType, touContentObj);
        updateTouApiResponse = orgInfo.updateTouPracticeResponse("Valid", "DL", orgId, touId, updateTou);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateTouApiResponse);
        CCUtils.assertEquals(updateTouApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(updateTouApiResponse);
    }

}
