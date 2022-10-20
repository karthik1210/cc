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

public class GetPatientGuardianAccessApiTests_DataLayer extends BaseRest {

    private OrgInfo orgInfo;
    private Validations validations;
    Response getPatientGuardianAccessResponse;
    int practiceId;


    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        orgInfo = new OrgInfo();
        validations = new Validations();
    }

    @DataProvider(name = "invalidOrNullPracticeId")
    public Object[][] invalidOrNullPracticeId() throws Exception {
        return new Object[][]{{testData.getProperty("api.Non.existing.practice.id"), "PXPP-24342 TC_004 Verfiy 400 error code when practice id is Invalid/Non-existing"},
                {testData.getProperty("api.invalid.practice.id.tou"), "PXPP-24341 TC 003_verify response code 400 when Practice Id is in Invalid format"},
                {null, "PXPP-24595 TC_008 Verfiy 400 error code when practice id is null"},
                {"", "PXPP-24343 TC_005 verify response code 400 when user enters empty value on practice Id"}};
    }

    @DataProvider(name = "practiceIdHasOrNotHavingPatientGuardianAccess")
    public Object[][] practiceIdHasOrNotHavingPatientGuardianAccess() throws Exception {
        return new Object[][]{{Integer.parseInt(testData.getProperty("api.valid.practice.id.patient.guardian.access")), "PXPP-24377 Verify 200 response code when practice id is valid and having patient guardian access"},
                {Integer.parseInt(testData.getProperty("api.valid.practice.id.not.having.patient.guardian.access")), "PXPP-24340 Verify 200 response code when practice id is valid and not having patient guardian access"}};
    }

    @Test(priority = 1, dataProvider = "practiceIdHasOrNotHavingPatientGuardianAccess", retryAnalyzer = RetryAnalyzer.class, description = "Fetching Patient Guardian Access details with valid practice id")
    public void getPatientGuardianAccessWithValidPracticeIdApiTestSuccess(int practiceId, String testCaseName) throws IOException {
        logStep(testCaseName);
        getPatientGuardianAccessResponse = orgInfo.getPatientGuardianAccess_DataLayer("Valid", practiceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPatientGuardianAccessResponse);
        CCUtils.assertEquals(getPatientGuardianAccessResponse.getStatusCode(), 200);
        validations.verifyOutputFromValidInputForGetPatientGuardianAccess(getPatientGuardianAccessResponse);
    }

    @Test(priority = 2, dataProvider = "invalidOrNullPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "Verifying error code 400 and error message when user enters invalid/incorrect/null practice Id")
    public void getPatientGuardianAccessWithInvalidDataOnPracticeId(String practiceId, String testCaseName) throws IOException {
        logStep(testCaseName);
        getPatientGuardianAccessResponse = orgInfo.getPatientGuardianAccess_DataLayer(practiceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPatientGuardianAccessResponse);
        CCUtils.assertEquals(getPatientGuardianAccessResponse.getStatusCode(), 400);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24344 verify response code 404 and error message when user enters invalid uri")
    public void getPatientGuardianAccessWithInvalidURI() throws IOException {
        logStep("TC 006_verify response code 404 and error message when user enters valid practice Id with Invalid URI by using GET Request [DL]");
        practiceId = Integer.parseInt(testData.getProperty("api.valid.practice.id.patient.guardian.access"));
        getPatientGuardianAccessResponse = orgInfo.getPatientAccessSettings_DataLayer("Invalid", practiceId);
        logStep("Validating response information");
        CCUtils.assertEquals(getPatientGuardianAccessResponse.getStatusCode(), 404);
        validations.verifyErrorInResponseForInvalidUrl(getPatientGuardianAccessResponse);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24345 verify response code 405 and error message when user enters valid practice Id with Invalid PATCH method for [DL]")
    public void getPatientGuardianAccessWithInvalidMethod() throws IOException {
        logStep("TC 007_verify response code 405 and error message when user enters valid practice Id instead of get with patch method [DL]");
        practiceId = Integer.parseInt(testData.getProperty("api.valid.practice.id.patient.guardian.access"));
        getPatientGuardianAccessResponse = orgInfo.getPatientAccessSettings_DataLayer("InvalidMethod", practiceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPatientGuardianAccessResponse);
        CCUtils.assertEquals(getPatientGuardianAccessResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethodCall(getPatientGuardianAccessResponse);
    }

}
