//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.DisplayText;
import com.pxp.cc.pojos.OrgSolutions;
import com.pxp.cc.pojos.PracticeTouDetails;
import com.pxp.cc.pojos.TouSolutions;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetPrivacySettingByPracticeIdAndTouIdApiTests_DataLayer extends BaseRest {
    private OrgInfo orgInfo;
    private Validations validations;
    Response addTouApiResponse, getTousApiResponse, getTouIdApiResponse, fetchOrgSolutionDetailsApiResponse, updateSolutionsApiResponse, createClientApiResponse,
            createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse;
    int practiceId, touId;
    List<Integer> solutionList = null;
    private ClientInfo clientInfo;
    PracticeTouDetails practiceTouDetailsObj;
    TouSolutions touSolutionsObj;
    DisplayText addTouContentObj;
    String touType, touName;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        orgInfo = new OrgInfo();
        clientInfo = new ClientInfo();
        validations = new Validations();
    }

    @DataProvider(name = "invalidOrValidPracticeIdAndTouId")
    public Object[][] invalidOrValidPracticeIdAndTouId() throws Exception {
        return new Object[][]{{testData.getProperty("api.valid.practice.id.tou").trim(), testData.getProperty("api.Non.existing.tou.id").trim(), "PXPP-20373_TC005_Valid PracticeId and Invalid TouId"},
                {testData.getProperty("api.Non.existing.practice.id").trim(), testData.getProperty("api.valid.tou.id.active").trim(), "PXPP-20374_TC006_Invalid PracticeId and Valid TouId"},
                {testData.getProperty("api.Non.existing.practice.id").trim(), testData.getProperty("api.Non.existing.tou.id").trim(), "PXPP-20375_TC007_Invalid PracticeId and Invalid TouId"},
                {testData.getProperty("api.valid.practice.id.tou").trim(), null, "PXPP-20381_TC008_Valid PracticeId and Null TouId"},
                {null, testData.getProperty("api.valid.tou.id.active").trim(), "PXPP-20383_TC009_Null PracticeId and Valid TouId"},
                {null, null, "PXPP-20385_TC010_Null PracticeId and Null TouId"},
                {testData.getProperty("api.valid.practice.id.tou").trim(), "", "PXPP-20386_TC011_Valid PracticeId and Empty TouId"},
                {"", testData.getProperty("api.valid.tou.id.active").trim(), "PXPP-20387_TC012_Empty PracticeId and Valid TouId"},
                {"", "", "PXPP-20388_TC013_Empty PracticeId and Empty TouId"},
                {testData.getProperty("api.valid.practice.id.tou"), testData.getProperty("api.valid.tou.id.active") + "Sample", "PXPP-20389_TC014_Valid PracticeId and Invalid Datatype TouId"},
                {testData.getProperty("api.valid.practice.id.tou") + "Sample", testData.getProperty("api.valid.tou.id.active"), "PXPP-20390_TC015_InValid DataType PracticeId and valid Datatype TouId"},
                {testData.getProperty("api.valid.practice.id.tou") + "Sample", testData.getProperty("api.valid.tou.id.active") + "Sample", "PXPP-20393_TC016_InValid Datatype PracticeId and Invalid Datatype TouId"}
        };
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20365_Fetching Tou Details with a valid practice id and Draft tou id")
    public void getTouWithPracticeIdAndDraftTouId() throws IOException {
        logStep("TC 001_verify response code 200 and response body, when user enters valid practice Id and valid tou id with Draft status by using GET Request for BL/DL");
        practiceId = createPractice();
        touType = testData.getProperty("PXPP.18282.api.tou.type");
        touName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.tou.name")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
        int touId = getTouIdByAddingtoPractice(practiceId, touName, "Draft", touType);
        logStep("Get the Tou details for the given practiceId");
        getTouIdApiResponse = orgInfo.getByTouIdResponse("Valid", "DL", practiceId, touId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getTouIdApiResponse);
        CCUtils.assertEquals(getTouIdApiResponse.getStatusCode(), 200);
        validations.verifyOutputForTouFromInput(getTouIdApiResponse, touType, touName);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20366_Fetching Tou Details with a valid practice id and Active tou id")
    public void getTouWithPracticeIdAndActiveTouId() throws IOException {
        logStep("TC 002_verify response code 200 and response body, when user enters valid practice Id and valid tou id with Active status by using GET Request for BL/DL");
        practiceId = createPractice();
        touType = testData.getProperty("PXPP.18282.api.tou.type");
        touName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.tou.name")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
        int touId = getTouIdByAddingtoPractice(practiceId, touName, "Active", touType);
        logStep("Get the Tou details for the given practiceId");
        getTouIdApiResponse = orgInfo.getByTouIdResponse("Valid", "DL", practiceId, touId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getTouIdApiResponse);
        CCUtils.assertEquals(getTouIdApiResponse.getStatusCode(), 200);
        validations.verifyOutputForTouFromInput(getTouIdApiResponse, touType, touName);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24146_Fetching Tou Details with a valid practice id and Inactive tou id")
    public void getTouWithPracticeIdAndInactiveTouId() throws IOException {
        logStep("TC 017_verify response code 200 and response body, when user enters valid practice Id and valid tou id with Inactive status by using GET Request for BL/DL");
        practiceId = createPractice();
        touType = testData.getProperty("PXPP.18282.api.tou.type");
        touName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.tou.name")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
        int touId = getTouIdByAddingtoPractice(practiceId, touName, "Inactive", touType);
        logStep("Get the Tou details for the given practiceId");
        getTouIdApiResponse = orgInfo.getByTouIdResponse("Valid", "DL", practiceId, touId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getTouIdApiResponse);
        CCUtils.assertEquals(getTouIdApiResponse.getStatusCode(), 200);
        validations.verifyOutputForTouFromInput(getTouIdApiResponse, touType, touName);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20367_Fetching Tou Details with a valid practice id, active tou id and invalid uri")
    public void getTouWithPracticeIdAndActiveTouIdAlongWithInvalidURI() throws IOException {
        logStep("TC 003_verify response code 404 and error message, when user enters valid practice Id and valid tou id in active status with Invalid URI by using GET Request for BL/DL");
        practiceId = Integer.parseInt(testData.getProperty("api.valid.practice.id.tou"));
        touId = Integer.parseInt(testData.getProperty("api.valid.tou.id.active"));
        logStep("Get the Tou details for the given practiceId");
        getTouIdApiResponse = orgInfo.getByTouIdResponse("InValid", "DL", practiceId, touId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getTouIdApiResponse);
        CCUtils.assertEquals(getTouIdApiResponse.getStatusCode(), 404);
        validations.verifyErrorInResponseForInvalidUrl(getTouIdApiResponse);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20372_Fetching Tou Details with a valid practice id, active tou id and invalid request")
    public void getTouWithPracticeIdAndActiveTouIdAlongWithInvalidRequest() throws IOException {
        logStep("TC 004_verify response code 405 and error message, when user enters valid practice Id and valid tou id in active status with Invalid PATCH Request for BL/DL");
        practiceId = Integer.parseInt(testData.getProperty("api.valid.practice.id.tou"));
        touId = Integer.parseInt(testData.getProperty("api.valid.tou.id.active"));
        logStep("Get the Tou details for the given practiceId");
        getTouIdApiResponse = orgInfo.getByTouIdResponse("InValidMethod", "DL", practiceId, touId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getTouIdApiResponse);
        CCUtils.assertEquals(getTouIdApiResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethodCall(getTouIdApiResponse);
    }

    @Test(priority = 6, dataProvider = "invalidOrValidPracticeIdAndTouId", retryAnalyzer = RetryAnalyzer.class, description = "(Negative Scenarios) Fetching Tou Details with practice id and tou id")
    public void getTouWithInvalidOrValidPracticeIdAndTouId(String practiceId, String touId, String testCaseName) throws IOException {
        logStep(testCaseName);
        logStep("Get the Tou details for the given practiceId");
        getTouIdApiResponse = orgInfo.getByTouIdInvaliDatatypeResponse("DL", practiceId, touId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getTouIdApiResponse);
        CCUtils.assertEquals(getTouIdApiResponse.getStatusCode(), 400);
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
        List<OrgSolutions> productList = Arrays.asList(
                new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
                new OrgSolutions(testData.getProperty("engage.id"), testData.getProperty("status.go.live")),
                new OrgSolutions(testData.getProperty("forms.id"),
                        testData.getProperty("status.active.implementation")));
        updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, orgId);
        return orgId;
    }

    @Test(enabled = false)
    public int getTouIdByAddingtoPractice(int practiceId, String touName, String touStatus, String touType) throws IOException {
        String contentEng = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.content.eng")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(50))));
        String contentSpan = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.content.span")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(50))));
        addTouContentObj = new DisplayText(contentEng, contentSpan);
        fetchOrgSolutionDetailsApiResponse = orgInfo.getOrgSolutionsDetails_DataLayer(practiceId, testData.getProperty("get.org.type"));
        List<String> solutionIds = fetchOrgSolutionDetailsApiResponse.jsonPath().get("solutionId");
        ArrayList<TouSolutions> touSolutionList = new ArrayList<TouSolutions>();
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
        logStep("Add a new tou to practice");
        addTouApiResponse = orgInfo.addPracticeTousResponse("Valid", "DL", practiceId, practiceTouDetailsObj);
        logStep("Get All tous for practice");
        getTousApiResponse = orgInfo.getPracticeTousResponse("Valid", "DL", practiceId, touType);
        int touId = getTousApiResponse.jsonPath().get("touId[0]");
        return touId;
    }
}
