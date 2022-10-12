// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.*;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

public class UpdateTouApiTests_BusinessLayer extends BaseRest {
    private OrgInfo orgInfo;
    private Validations validations;
    Response updateTouApiResponse, addTouApiResponse, getTousApiResponse, getTouIdApiResponse, fetchOrgSolutionDetailsApiResponse, updateSolutionsApiResponse,
            createClientApiResponse, createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse;
    int practiceId, touId, touId1;
    List<Integer> solutionList = null;
    private ClientInfo clientInfo;
    PracticeTouDetails practiceTouDetailsObj;
    TouSolutions touSolutionsObj;
    DisplayText addTouContentObj;
    String touType, touName, contentEng, contentSpan, contentEngUpdate, contentSpanUpdate;
    ArrayList<TouSolutions> touSolutionList;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilder();
        orgInfo = new OrgInfo();
        clientInfo = new ClientInfo();
        validations = new Validations();
        touSolutionList = new ArrayList<TouSolutions>();
    }

    @DataProvider(name = "invalidOrValidPracticeIdAndTouId")
    public Object[][] invalidOrValidPracticeIdAndTouId() throws Exception {
        return new Object[][]{{testData.getProperty("api.valid.practice.id.tou").trim(), testData.getProperty("api.Non.existing.tou.id").trim(), "PXPP-20084_TC06_Valid PracticeId and Invalid TouId"},
                {testData.getProperty("api.Non.existing.practice.id").trim(), testData.getProperty("api.valid.tou.id.active").trim(), "PXPP-20084_TC06_Invalid PracticeId and Valid TouId"},
                {testData.getProperty("api.Non.existing.practice.id").trim(), testData.getProperty("api.Non.existing.tou.id").trim(), "PXPP-20084_TC06_Invalid PracticeId and Invalid TouId"},
                {testData.getProperty("api.valid.practice.id.tou").trim(), null, "PXPP-20083_TC05_Valid PracticeId and Null TouId"},
                {null, testData.getProperty("api.valid.tou.id.active").trim(), "PXPP-20083_TC05_Null PracticeId and Valid TouId"},
                {null, null, "PXPP-20083_TC05_Null PracticeId and Null TouId"},
                {"", testData.getProperty("api.valid.tou.id.active").trim(), "PXPP-20083_TC05_Empty PracticeId and Valid TouId"},
                {"", "", "PXPP-20083_TC05_Empty PracticeId and Empty TouId"},
                {testData.getProperty("api.valid.practice.id.tou"), testData.getProperty("api.valid.tou.id.active") + "Sample", "PXPP-20081_TC03_Valid PracticeId and Invalid Datatype TouId"},
                {testData.getProperty("api.valid.practice.id.tou") + "Sample", testData.getProperty("api.valid.tou.id.active"), "PXPP-20081_TC03_InValid DataType PracticeId and valid Datatype TouId"},
                {testData.getProperty("api.valid.practice.id.tou") + "Sample", testData.getProperty("api.valid.tou.id.active") + "Sample", "PXPP-20081_TC03_InValid Datatype PracticeId and Invalid Datatype TouId"}
        };
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20079_Create New Version TouId for the existing TouId by changing Content with status")
    public void updateTouContentForGivenTouIdApiSuccess() throws IOException {
        logStep("TC_01_Verify the Response code as 200, when user try to update content for Privacy settings by using POST Request [BL/DL]");
        practiceId = createPractice();
        touType = testData.getProperty("PXPP.18282.api.tou.type");
        touName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.tou.name")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
        contentEng = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.content.eng")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(50))));
        contentSpan = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.content.span")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(50))));
        touId = getTouIdByAddingtoPractice(practiceId, touName, "Draft", touType, contentEng, contentSpan);
        logStep("Get the Tou details for the given practiceId");
        getTouIdApiResponse = orgInfo.getByTouIdResponse("Valid", "BL", practiceId, touId);
        String name = getTouIdApiResponse.jsonPath().get("name");
        Integer solutionId = getTouIdApiResponse.jsonPath().get("solutions.solutionId[0]");
        String solutionName = getTouIdApiResponse.jsonPath().get("solutions.name[0]");
        boolean solutionActive = getTouIdApiResponse.jsonPath().get("solutions.active[0]");
        touSolutionsObj = new TouSolutions(solutionId, solutionName, solutionActive);
        touSolutionList.clear();
        touSolutionList.add(touSolutionsObj);
        contentEngUpdate = getTouIdApiResponse.jsonPath().get("content.en") + "updated";
        contentSpanUpdate = getTouIdApiResponse.jsonPath().get("content.es") + "updated";
        addTouContentObj = new DisplayText(contentEngUpdate, contentSpanUpdate);
        logStep("Create a payload to update tou details");
        practiceTouDetailsObj = new PracticeTouDetails(name, "Active", touSolutionList, touType, addTouContentObj);
        logStep("Update a version of tou for a given orgId and touId by changing Content");
        updateTouApiResponse = orgInfo.updatePracticeTousResponse("Valid", "BL", practiceId, touId, practiceTouDetailsObj);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateTouApiResponse);
        CCUtils.assertEquals(updateTouApiResponse.getStatusCode(), 200);
        getTousApiResponse = orgInfo.getPracticeTousResponse("Valid", "BL", practiceId, touType);
        List<String> touIds = getTousApiResponse.jsonPath().get("touId");
        for (int i = 0; i < touIds.size(); i++) {
            touId = getTousApiResponse.jsonPath().get("touId[" + i + "]");
            getTouIdApiResponse = orgInfo.getByTouIdResponse("Valid", "BL", practiceId, touId);
            String expectedEngUpdate = getTouIdApiResponse.jsonPath().get("content.en");
            if (contentEngUpdate.equalsIgnoreCase(expectedEngUpdate)) {
                touId1 = getTousApiResponse.jsonPath().get("touId[" + i + "]");
                break;
            } else {
                logStep("TouId not fetched for " + practiceId);
            }
        }
        logStep("Get the Tou details for the given practiceId and touId");
        getTouIdApiResponse = orgInfo.getByTouIdResponse("Valid", "BL", practiceId, touId1);
        logStep("Validating response information against to new touId");
        validations.getTouIdDetailValidation(getTouIdApiResponse, practiceId, touId1, name, contentEngUpdate, contentSpanUpdate);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-21550_Update Tou solutions for the existing TouId with status")
    public void updateTouSolutionsForGivenTouIdApiSuccess() throws IOException {
        logStep("TC_08_Verify the Response code as 200, when user try to give Valid practice Id and tou Id and Update the solutions of privacy settings by using POST request [BL/DL]");
        practiceId = createPractice();
        touType = testData.getProperty("PXPP.18282.api.tou.type");
        touName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.tou.name")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
        contentEng = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.content.eng")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(50))));
        contentSpan = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.content.span")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(50))));
        touId = getTouIdByAddingtoPractice(practiceId, touName, "Draft", touType, contentEng, contentSpan);
        logStep("Get the Tou details for the given practiceId");
        getTouIdApiResponse = orgInfo.getByTouIdResponse("Valid", "BL", practiceId, touId);
        String name = getTouIdApiResponse.jsonPath().get("name");
        Integer solutionId = getTouIdApiResponse.jsonPath().get("solutions.solutionId[0]");
        String solutionName = getTouIdApiResponse.jsonPath().get("solutions.name[0]");
        boolean solutionActive = false;
        touSolutionsObj = new TouSolutions(solutionId, solutionName, solutionActive);
        touSolutionList.clear();
        touSolutionList.add(touSolutionsObj);
        addTouContentObj = new DisplayText(contentEng, contentSpan);
        logStep("Create a payload to update tou details");
        practiceTouDetailsObj = new PracticeTouDetails(name, "Inactive", touSolutionList, touType, addTouContentObj);
        logStep("Update a version of tou for a given orgId and touId by changing Content");
        updateTouApiResponse = orgInfo.updatePracticeTousResponse("Valid", "BL", practiceId, touId, practiceTouDetailsObj);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateTouApiResponse);
        CCUtils.assertEquals(updateTouApiResponse.getStatusCode(), 200);
        logStep("Get the Tou details for the given practiceId and touId");
        getTouIdApiResponse = orgInfo.getByTouIdResponse("Valid", "BL", practiceId, touId);
        logStep("Validating response information against to new touId");
        validations.getTouIdDetailValidation(getTouIdApiResponse, practiceId, touId, name, contentEng, contentSpan);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-21551_Create New Version TouId for the existing TouId by changing Name and Content with status")
    public void createNewVersionTouIdForGivenTouIdApiSuccess() throws IOException {
        logStep("TC_09_Verify the Response code as 200 and new version touId generated , when user Update the payload with name,content for Valid PracticeId and TouId by using POST request [BL/DL]");
        practiceId = createPractice();
        touType = testData.getProperty("PXPP.18282.api.tou.type");
        touName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.tou.name")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
        contentEng = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.content.eng")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(50))));
        contentSpan = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.content.span")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(50))));
        touId = getTouIdByAddingtoPractice(practiceId, touName, "Draft", touType, contentEng, contentSpan);
        logStep("Get the Tou details for the given practiceId");
        getTouIdApiResponse = orgInfo.getByTouIdResponse("Valid", "BL", practiceId, touId);
        String name = getTouIdApiResponse.jsonPath().get("name");
        String updatedName = name.concat(CCUtils.generateAlphaNumericString(2));
        Integer solutionId = getTouIdApiResponse.jsonPath().get("solutions.solutionId[0]");
        String solutionName = getTouIdApiResponse.jsonPath().get("solutions.name[0]");
        boolean solutionActive = getTouIdApiResponse.jsonPath().get("solutions.active[0]");
        touSolutionsObj = new TouSolutions(solutionId, solutionName, solutionActive);
        touSolutionList.clear();
        touSolutionList.add(touSolutionsObj);
        contentEngUpdate = getTouIdApiResponse.jsonPath().get("content.en") + "updated";
        contentSpanUpdate = getTouIdApiResponse.jsonPath().get("content.es") + "updated";
        addTouContentObj = new DisplayText(contentEngUpdate, contentSpanUpdate);
        logStep("Create a payload to update tou details");
        practiceTouDetailsObj = new PracticeTouDetails(updatedName, "Active", touSolutionList, touType, addTouContentObj);
        logStep("Create a new Version tou for a given orgId and touId by changing Name and Content");
        updateTouApiResponse = orgInfo.updatePracticeTousResponse("Valid", "BL", practiceId, touId, practiceTouDetailsObj);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateTouApiResponse);
        CCUtils.assertEquals(updateTouApiResponse.getStatusCode(), 200);
        logStep("Get the list of Tous for the given practiceId");
        getTousApiResponse = orgInfo.getPracticeTousResponse("Valid", "BL", practiceId, touType);
        List<String> touIds = getTousApiResponse.jsonPath().get("touId");
        for (int i = 0; i < touIds.size(); i++) {
            if (updatedName.equalsIgnoreCase(getTousApiResponse.jsonPath().get("name[" + i + "]").toString())) {
                touId1 = getTousApiResponse.jsonPath().get("touId[" + i + "]");
                break;
            } else {
                logStep("TouId not fetched for " + practiceId);
            }
        }
        logStep("Get the Tou details for the given practiceId and touId");
        getTouIdApiResponse = orgInfo.getByTouIdResponse("Valid", "BL", practiceId, touId1);
        logStep("Validating response information against to new touId");
        validations.getTouIdDetailValidation(getTouIdApiResponse, practiceId, touId1, updatedName, contentEngUpdate, contentSpanUpdate);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20080_Update the touId for Invalid URI")
    public void updateTouInvaliUriApi() throws IOException {
        logStep("TC_02_Verify the Response code as 404, when user try to give invalid URI for Update Privacy Setting by using POST Request [BL/DL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        touType = testData.getProperty("api.valid.tou.type");
        touName = testData.getProperty("PXPP.18282.api.tou.name")+"InvalidUri";
        contentEng = testData.getProperty("PXPP.18282.api.content.eng");
        contentSpan = testData.getProperty("PXPP.18282.api.content.span");
        touId = getTouIdByAddingtoPractice(practiceId, touName, "Draft", touType, contentEng, contentSpan);
        logStep("Create a payload to update tou details");
        practiceTouDetailsObj = practiceTouPayload(practiceId, touName, "Draft");
        logStep("Update the touId with invalid URI");
        updateTouApiResponse = orgInfo.updatePracticeTousResponse("InValid", "BL", practiceId, touId, practiceTouDetailsObj);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateTouApiResponse);
        CCUtils.assertEquals(updateTouApiResponse.getStatusCode(), 404);
    }

    @Test(priority = 5, dataProvider = "invalidOrValidPracticeIdAndTouId", retryAnalyzer = RetryAnalyzer.class, description = "Update tou details for Invalid practice and touId")
    public void updateTouForInvalidPracticeAndTouId(String practiceId, String touId, String testCaseName) throws IOException {
        logStep(testCaseName);
        int payloadOrgId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        touName = testData.getProperty("PXPP.18282.api.tou.name")+"Invalid";
        logStep("Create a payload to update tou details");
        practiceTouDetailsObj = practiceTouPayload(payloadOrgId, touName, "Draft");
        logStep("Update the touId with invalid URI");
        updateTouApiResponse = orgInfo.updatePracticeTousInvaliDatatypeResponse( "BL", practiceId, touId, practiceTouDetailsObj);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateTouApiResponse);
        CCUtils.assertEquals(updateTouApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20082_update tou details with Invalid Method Type")
    public void updateTouDetailsToPracticeInvalidMethodType() throws IOException {
        logStep("TC_04_Verify the Response code as 405, when user try patch method instead of POST Request for practice of privacy settings [BL/DL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        touType = testData.getProperty("api.valid.tou.type");
        touName = testData.getProperty("PXPP.18282.api.tou.name")+"InvalidUri";
        contentEng = testData.getProperty("PXPP.18282.api.content.eng");
        contentSpan = testData.getProperty("PXPP.18282.api.content.span");
        touId = getTouIdByAddingtoPractice(practiceId, touName, "Draft", touType, contentEng, contentSpan);
        logStep("Create a payload to update tou details");
        practiceTouDetailsObj = practiceTouPayload(practiceId, touName, "Draft");
        logStep("Update the touId with invalid URI");
        updateTouApiResponse = orgInfo.updatePracticeTousResponse("InValidMethod", "BL", practiceId, touId, practiceTouDetailsObj);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateTouApiResponse);
        CCUtils.assertEquals(updateTouApiResponse.getStatusCode(), 405);
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20085_Update tou details with Invalid payload")
    public void updateTouDetailsWithInvalidPayload() throws IOException {
        logStep("TC_07_Verify the Response code as 400, when user try to give Valid practice Id and tou Id and invalid/Empty payload of privacy settings by using POST request [BL/DL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        touName = testData.getProperty("PXPP.18282.api.tou.name")+"InvalidUri";
        contentEng = testData.getProperty("PXPP.18282.api.content.eng");
        contentSpan = testData.getProperty("PXPP.18282.api.content.span");
        touType = testData.getProperty("PXPP.18282.api.tou.type");
        touId = getTouIdByAddingtoPractice(practiceId, touName, "Draft", touType, contentEng, contentSpan);
        addTouContentObj = new DisplayText(contentEng, contentSpan);
        ArrayList<TouSolutions> touSolutionList = new ArrayList<TouSolutions>();
        touSolutionList.clear();
        touSolutionsObj = new TouSolutions(null, testData.getProperty("PXPP.18282.api.sol.name"), true);
        touSolutionList.add(touSolutionsObj);
        practiceTouDetailsObj = new PracticeTouDetails("", "Draft", touSolutionList, touType, addTouContentObj);
        logStep("Update a tou for a given practice and touId");
        updateTouApiResponse = orgInfo.updatePracticeTousResponse("Valid", "BL", practiceId, touId, practiceTouDetailsObj);
        logStep("Validating response Code");
        CCUtils.assertEquals(updateTouApiResponse.getStatusCode(), 400);
        CCUtils.assertEquals(updateTouApiResponse.jsonPath().get("message").toString(), "Name is required");
        practiceTouDetailsObj = new PracticeTouDetails("TestTou", "", touSolutionList, touType, addTouContentObj);
        logStep("Update a tou for a given practice and touId");
        updateTouApiResponse = orgInfo.updatePracticeTousResponse("Valid", "BL", practiceId, touId, practiceTouDetailsObj);
        logStep("Validating response Code");
        CCUtils.assertEquals(updateTouApiResponse.getStatusCode(), 400);
    }

    @Test(enabled = false)
    public int createPractice() throws IOException {
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
        List<OrgSolutions> productList = Arrays.asList(
                new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
                new OrgSolutions(testData.getProperty("engage.id"), testData.getProperty("status.go.live")),
                new OrgSolutions(testData.getProperty("forms.id"),
                        testData.getProperty("status.active.implementation")));
        updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_BusinessLayer(productList, orgId);
        return orgId;
    }

    @Test(enabled = false)
    public PracticeTouDetails practiceTouPayload(int practiceId, String touName, String touStatus) throws IOException {
        String contentEng = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.content.eng")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(50))));
        String contentSpan = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.content.span")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(50))));
        addTouContentObj = new DisplayText(contentEng, contentSpan);
        fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_BusinessLayer(practiceId, testData.getProperty("get.org.type"));
        List<String> solutionIds = fetchOrgSolutionDetailsApiResponse.jsonPath().get("solutionId");
        ArrayList<com.pxp.cc.pojos.TouSolutions> touSolutionList = new ArrayList<TouSolutions>();
        touSolutionList.clear();
        for (int i = 0; i < solutionIds.size(); i++) {
            int solutionId = fetchOrgSolutionDetailsApiResponse.jsonPath().get("solutionId[" + i + "]");
            String name = fetchOrgSolutionDetailsApiResponse.jsonPath().get("name[" + i + "]");
            boolean active = fetchOrgSolutionDetailsApiResponse.jsonPath().get("active[" + i + "]");
            if (active) {
                touSolutionsObj = new TouSolutions(solutionId, name, active);
                touSolutionList.add(touSolutionsObj);
            }
        }
        touType = testData.getProperty("PXPP.18282.api.tou.type");
        practiceTouDetailsObj = new PracticeTouDetails(touName, touStatus, touSolutionList, touType, addTouContentObj);
        return practiceTouDetailsObj;
    }

    @Test(enabled = false)
    public int getTouIdByAddingtoPractice(int practiceId, String touName, String touStatus, String touType, String contentEng, String contentSpan) throws IOException {
        practiceTouDetailsObj = practiceTouPayload(practiceId, touName, touStatus);
        logStep("Add a new tou to practice");
        addTouApiResponse = orgInfo.addPracticeTousResponse("Valid", "BL", practiceId, practiceTouDetailsObj);
        logStep("Get All tous for practice");
        getTousApiResponse = orgInfo.getPracticeTousResponse("Valid", "BL", practiceId, touType);
        touId = getTousApiResponse.jsonPath().get("touId[0]");
        return touId;
    }
}