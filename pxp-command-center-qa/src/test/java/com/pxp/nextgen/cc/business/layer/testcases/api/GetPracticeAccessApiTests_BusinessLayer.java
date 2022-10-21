// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetPracticeAccessApiTests_BusinessLayer extends BaseRest {

    private OrgInfo orgInfo;
    private Validations validations;
    Response getPracticeAccessApiResponse;
    int practiceId;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilder();
        orgInfo = new OrgInfo();
        validations = new Validations();
    }

    @DataProvider(name = "invalidOrNullPracticeId")
    public Object[][] invalidOrNullPracticeId() throws Exception {
        return new Object[][]{{testData.getProperty("api.Non.existing.practice.id"), "PXPP-24261 TC_002 Verfiy 400 error code when practice id is Invalid/Non-existing"},
                {testData.getProperty("api.invalid.practice.id.tou"), "PXPP-24265 TC 003_verify response code 400 when Practice Id is in Invalid format"},
                {null, "PXPP-24266 TC_004 Verfiy 400 error code when practice id is null"},
                {"", "PXPP-24269 TC_005 verify response code 400 when user enters empty value on practice Id"}};
    }

    @DataProvider(name = "practiceIdHasOrNotHavingPatientAccess")
    public Object[][] practiceIdHasOrNotHavingPatientAccess() throws Exception {
        return new Object[][]{{Integer.parseInt(testData.getProperty("api.valid.practice.id.practice.access")), "PXPP-24259 Verify 200 response code when practice id is valid and having patient access"},
                {Integer.parseInt(testData.getProperty("api.valid.practice.id.not.having.practice.access")), "PXPP-25014 Verify 200 response code when practice id is valid and not having patient access"}};
    }

    @Test(priority = 1, dataProvider = "practiceIdHasOrNotHavingPatientAccess", retryAnalyzer = RetryAnalyzer.class, description = "Fetching Patient Guardian Access details with valid practice id")
    public void getPatientAccessWithValidPracticeIdApiTestSuccess(int practiceId, String testCaseName) throws IOException {
        logStep(testCaseName);
        getPracticeAccessApiResponse = orgInfo.getPatientAccess_BusinessLayer("Valid", practiceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPracticeAccessApiResponse);
        CCUtils.assertEquals(getPracticeAccessApiResponse.getStatusCode(), 200);
        //validations.verifyOutputFromValidInputForGetPatientAgeout(getPracticeAccessApiResponse);
    }

    @Test(priority = 2, dataProvider = "invalidOrNullPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "Verifying error code 400 and error message when user enters invalid/incorrect/null practice Id")
    public void getPatientAccessWithInvalidDataOnPracticeId(String practiceId, String testCaseName) throws IOException {
        logStep(testCaseName);
        getPracticeAccessApiResponse = orgInfo.getPatientAccess_BusinessLayer(practiceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPracticeAccessApiResponse);
        CCUtils.assertEquals(getPracticeAccessApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24271 verify response code 404 and error message when user enters invalid uri")
    public void getPatientAccessWithInvalidURI() throws IOException {
        logStep("TC 006_verify response code 404 and error message when user enters valid practice Id with Invalid URI by using GET Request [BL]");
        practiceId = Integer.parseInt(testData.getProperty("api.valid.practice.id.practice.access"));
        getPracticeAccessApiResponse = orgInfo.getPatientAccess_BusinessLayer("Invalid", practiceId);
        logStep("Validating response information");
        CCUtils.assertEquals(getPracticeAccessApiResponse.getStatusCode(), 404);
        validations.verifyErrorInResponseForInvalidUrl(getPracticeAccessApiResponse);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24272 verify response code 405 and error message when user enters valid practice Id with Invalid PATCH method for [BL]")
    public void getPatientAccessWithInvalidMethod() throws IOException {
        logStep("TC 007_verify response code 405 and error message when user enters valid practice Id instead of get with patch method [BL]");
        practiceId = Integer.parseInt(testData.getProperty("api.valid.practice.id.practice.access"));
        getPracticeAccessApiResponse = orgInfo.getPatientAccess_BusinessLayer("InvalidMethod", practiceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPracticeAccessApiResponse);
        CCUtils.assertEquals(getPracticeAccessApiResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethodCall(getPracticeAccessApiResponse);
    }

}
