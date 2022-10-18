// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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

public class GetPatientAccessSettingsApiTests_DataLayer extends BaseRest {

    private OrgInfo orgInfo;
    private Validations validations;
    Response getPatientAccessSettingsResponse;
    int practiceId;


    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        orgInfo = new OrgInfo();
        validations = new Validations();
    }

    @DataProvider(name = "invalidOrNullPracticeId")
    public Object[][] invalidOrNullPracticeId() throws Exception {
        return new Object[][]{{testData.getProperty("api.Non.existing.practice.id"), "PXPP-24319 TC_002 Verfiy 400 error code when practice id is Invalid/Non-existing"},
                {testData.getProperty("api.invalid.practice.id.tou"), "PXPP-24322 TC_003 Verify 400 error code when practice id is in Invalid format"},
                {null, "PXPP-24323 TC_004 Verfiy 400 error code when practice id is null"},
                {"", "PXPP-24324 TC_005 verify response code 400 when user enters empty value on practice Id"}};
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24318 Fetching Patient Access settings details with valid practice id")
    public void getPatientAccessSettingsWithValidPracticeIdApiTestSuccess() throws IOException {
        logStep("PXPP-24318 TC 001_verify response code 200 and response body, when user enters valid practice Id to Get Practice Access Detail by using GET Request [DL]");
        practiceId = Integer.parseInt(testData.getProperty("api.valid.practice.id.patient.access.settings"));
        getPatientAccessSettingsResponse = orgInfo.getPatientAccessSettings_DataLayer("Valid", practiceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPatientAccessSettingsResponse);
        CCUtils.assertEquals(getPatientAccessSettingsResponse.getStatusCode(), 200);
    }

    @Test(priority = 2, dataProvider = "invalidOrNullPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "Verifying error code 400 and error message when user enters invalid/incorrect/null practice Id")
    public void getPatientAccessSettingsWithInvalidDataOnPracticeId(String practiceId, String testCaseName) throws IOException {
        logStep(testCaseName);
        getPatientAccessSettingsResponse = orgInfo.getPatientAccessSettings_DataLayer(practiceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPatientAccessSettingsResponse);
        CCUtils.assertEquals(getPatientAccessSettingsResponse.getStatusCode(), 400);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24325 verify response code 404 and error message when user enters invalid uri")
            public void getPatientAccessSettingsWithInvalidURI() throws IOException {
            logStep("TC 006_verify response code 404 and error message when user enters valid practice Id with Invalid URI by using GET Request [DL]");
            practiceId = Integer.parseInt(testData.getProperty("api.valid.practice.id.patient.access.settings"));
            getPatientAccessSettingsResponse = orgInfo.getPatientAccessSettings_DataLayer("Invalid", practiceId);
            logStep("Validating response information");
            CCUtils.assertEquals(getPatientAccessSettingsResponse.getStatusCode(), 404);
            validations.verifyErrorInResponseForInvalidUrl(getPatientAccessSettingsResponse);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24328 verify response code 405 and error message when user enters valid practice Id with Invalid PATCH method for [DL]")
    public void getPatientAccessSettingsWithInvalidMethod() throws IOException {
        logStep("TC 007_verify response code 405 and error message when user enters valid practice Id with Invalid PATCH method for [DL]");
        practiceId = Integer.parseInt(testData.getProperty("api.valid.practice.id.patient.access.settings"));
        getPatientAccessSettingsResponse = orgInfo.getPatientAccessSettings_DataLayer("InvalidMethod", practiceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPatientAccessSettingsResponse);
        CCUtils.assertEquals(getPatientAccessSettingsResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethodCall(getPatientAccessSettingsResponse);
    }
}
