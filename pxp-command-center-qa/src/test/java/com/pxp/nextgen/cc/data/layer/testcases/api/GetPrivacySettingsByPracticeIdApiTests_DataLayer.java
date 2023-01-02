// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

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

public class GetPrivacySettingsByPracticeIdApiTests_DataLayer extends BaseRest {
    private OrgInfo orgInfo;
    private Validations validations;
    Response addTouApiResponse, addTouApiResponse1, addTouApiResponse2, getTouApiResponse, fetchOrgSolutionDetailsApiResponse, updateSolutionsApiResponse, createClientApiResponse,
            createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse;
    int practiceId;
    List<Integer> solutionList = null;
    private ClientInfo clientInfo;
    PracticeTouDetails practiceTouDetailsObj, practiceTouDetailsObj1, practiceTouDetailsObj2;
    TouSolutions touSolutionsObj;
    DisplayText addTouContentObj;
    String touType, touName, touName1, touName2;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        orgInfo = new OrgInfo();
        clientInfo = new ClientInfo();
        validations = new Validations();
    }

    @DataProvider(name = "invalidOrValidPracticeIdAndTouType")
    public Object[][] invalidOrValidPracticeIdAndTouType() throws Exception {
        return new Object[][]{{Integer.parseInt(testData.getProperty("api.valid.practice.id.tou")), testData.getProperty("api.invalid.tou.type"), "PXPP-20041_TC002_Valid PracticeId and Invalid TouType"},
                {Integer.parseInt(testData.getProperty("api.Non.existing.practice.id")), testData.getProperty("api.valid.tou.type"), "PXPP-20045_TC003_Invalid PracticeId and Valid TouType"},
                {Integer.parseInt(testData.getProperty("api.Non.existing.practice.id")), testData.getProperty("api.invalid.tou.type"), "PXPP-20412_TC009_Invalid PracticeId and Invalid TouType"},
                {Integer.parseInt(testData.getProperty("api.valid.practice.id.tou")), "null", "PXPP-20413_TC010_Valid PracticeId and Null TouType"}
        };
    }

    @DataProvider(name = "nullOrEmptyPracticeIdAndTouType")
    public Object[][] nullOrEmptyPracticeIdAndTouType() throws Exception {
        return new Object[][]{
                {null, testData.getProperty("api.valid.tou.type"), "PXPP-20051_TC006_Null PracticeId and Valid TouType"},
                {"", testData.getProperty("api.valid.tou.type"), "PXPP-20053_TC007_Empty PracticeId and Valid TouType"},
                {testData.getProperty("api.valid.practice.id.tou") + "Sample", testData.getProperty("api.valid.tou.type"), "PXPP-20055_TC008_InValid DataType PracticeId and valid TouType"},
                {testData.getProperty("api.valid.practice.id.tou"), null, "PXPP-20413_TC010_Valid PracticeId and Null TouType"},
                {null, null, "PXPP-20414_TC011_Null PracticeId and Null TouType"},
                {testData.getProperty("api.valid.practice.id.tou").trim(), "", "PXPP-20415_TC012_Valid PracticeId and Empty TouType"},
                {testData.getProperty("api.valid.practice.id.tou") + "Sample", testData.getProperty("api.valid.tou.type"), "PXPP-20418_TC015_InValid Datatype PracticeId and Valid TouId"}
        };
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20031_Fetching list of Tou Details for a practice with practice Id")
    public void getTouByPracticeId() throws IOException {
        logStep("TC 001_verify response code 200 and response body when user enter valid practice Id which has Privacy policy and valid tou type by using GET Method for BL/DL");
        practiceId = createPractice();
        touType = testData.getProperty("PXPP.18282.api.tou.type");
        touName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.tou.name")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
        logStep("Create PayLoad for new tou creation to practice");
        practiceTouDetailsObj = practiceTouPayload(practiceId, touName, "Draft");
        logStep("Add a new tou to practice");
        addTouApiResponse = orgInfo.addPracticeTousResponse("Valid", "DL", practiceId, practiceTouDetailsObj);
        logStep("Get a new tou to practice");
        getTouApiResponse = orgInfo.getPracticeTousResponse("Valid", "DL", practiceId, touType);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(addTouApiResponse);
        CCUtils.assertEquals(addTouApiResponse.getStatusCode(), 200);
        validations.getTouIdValidation(getTouApiResponse, touName, practiceId);
        validations.verifyOutputFromValidInput(getTouApiResponse);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24196_Fetching list of Tou Details for a practice with practice Id")
    public void getMultipleTousByPracticeId() throws IOException {
        logStep("TC 0016_verify response code 200 and response body when user enter valid practice Id which has Multiple status Privacy policy and valid tou type by using GET Method for BL/DL");
        practiceId = createPractice();
        touType = testData.getProperty("PXPP.18282.api.tou.type");
        touName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.tou.name")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
        touName1 = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.tou.name")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
        touName2 = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.tou.name")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
        logStep("Create PayLoad for new tou creation to practice");
        practiceTouDetailsObj = practiceTouPayload(practiceId, touName, "Draft");
        logStep("Add a new tou to practice");
        addTouApiResponse = orgInfo.addPracticeTousResponse("Valid", "DL", practiceId, practiceTouDetailsObj);
        practiceTouDetailsObj1 = practiceTouPayload(practiceId, touName1, "Active");
        logStep("Add a new tou to practice");
        addTouApiResponse1 = orgInfo.addPracticeTousResponse("Valid", "DL", practiceId, practiceTouDetailsObj1);
        practiceTouDetailsObj2 = practiceTouPayload(practiceId, touName2, "Inactive");
        logStep("Add a new tou to practice");
        addTouApiResponse2 = orgInfo.addPracticeTousResponse("Valid", "DL", practiceId, practiceTouDetailsObj2);
        logStep("Get a new tou to practice");
        getTouApiResponse = orgInfo.getPracticeTousResponse("Valid", "DL", practiceId, touType);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(addTouApiResponse);
        CCUtils.assertEquals(addTouApiResponse.getStatusCode(), 200);
        validations.getTouIdValidation(getTouApiResponse, touName, practiceId);
        validations.getTouIdValidation(getTouApiResponse, touName1, practiceId);
        validations.getTouIdValidation(getTouApiResponse, touName2, practiceId);
        validations.verifyOutputFromValidInput(getTouApiResponse);
    }

    @Test(priority = 3, dataProvider = "invalidOrValidPracticeIdAndTouType", retryAnalyzer = RetryAnalyzer.class, description = "Fetching list of Tou Details for a practice with practice Id and invalid touType")
    public void getTouByPracticeIdWithInvalidTouType(int practiceId, String touType, String testCaseName) throws IOException {
        logStep(testCaseName);
        getTouApiResponse = orgInfo.getPracticeTousResponse("Valid", "DL", practiceId, touType);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getTouApiResponse);
        CCUtils.assertEquals(getTouApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20047_Fetching list of Tou Details for a practice with practice Id by PUT Request instead of GET")
    public void getTouByPracticeIdWithInvalidRequest() throws IOException {
        logStep("TC 004_verify response code 405 and error message when user enters Valid practice Id and valid tou type to performs PUT Method Instead of GET for BL/DL");
        getTouApiResponse = orgInfo.getPracticeTousResponse("InValidMethod", "DL", Integer.parseInt(testData.getProperty("api.valid.practice.id.tou")), testData.getProperty("api.valid.tou.type"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getTouApiResponse);
        CCUtils.assertEquals(getTouApiResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethod(getTouApiResponse);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20050_Fetching list of Tou Details for a practice with Invalid/Valid practice Id and touType")
    public void getTouByPracticeIdWithInvalidURI() throws IOException {
        logStep("TC 005_verify response code 404 and error message when user enters valid practice Id and valid tou type with Invalid URI for BL/DL");
        getTouApiResponse = orgInfo.getPracticeTousResponse("InValid", "DL", Integer.parseInt(testData.getProperty("api.valid.practice.id.tou")), testData.getProperty("api.valid.tou.type"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getTouApiResponse);
        CCUtils.assertEquals(getTouApiResponse.getStatusCode(), 404);
        validations.verifyErrorInResponseForInvalidUrl(getTouApiResponse);
    }

    @Test(priority = 6, dataProvider = "nullOrEmptyPracticeIdAndTouType", retryAnalyzer = RetryAnalyzer.class, description = "Fetching list of Tou Details for a practice with null/Empty practice Id and touType ")
    public void getTouWithNullOrEmptyPracticeIdAndTouType(String practiceId, String touType, String testCaseName) throws IOException {
        logStep(testCaseName);
        getTouApiResponse = orgInfo.getPracticeTousInvaliDatatypeResponse("DL", practiceId, touType);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getTouApiResponse);
        CCUtils.assertEquals(getTouApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20416_TC013_Empty PracticeId and Empty TouType")
    public void getTouWithOneSpaceOnBothPracticeIdAndTouType() throws IOException {
        logStep("TC 013_verify response code 500 and error message when user enters one space value on practice Id and one space on tou type by using GET Request for BL/DL");
        getTouApiResponse = orgInfo.getPracticeTousInvaliDatatypeResponse("DL", " ", " ");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getTouApiResponse);
        CCUtils.assertEquals(getTouApiResponse.getStatusCode(), 500);
        validations.verifyServerRequestRequestValidation(getTouApiResponse);
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
        List<OrgSolutions> productList = Arrays.asList(
                new OrgSolutions(testData.getProperty("Portal.id"), testData.getProperty("status.newly.purchased")),
                new OrgSolutions(testData.getProperty("engage.id"), testData.getProperty("status.go.live")),
                new OrgSolutions(testData.getProperty("forms.id"),
                        testData.getProperty("status.active.implementation")));
        updateSolutionsApiResponse = orgInfo.updateOrgsSolutions_DataLayer(productList, orgId);
        return orgId;
    }

    @Test(enabled = false)
    public PracticeTouDetails practiceTouPayload(int practiceId, String touName, String touStatus) throws IOException {
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
        return practiceTouDetailsObj;
    }
}
