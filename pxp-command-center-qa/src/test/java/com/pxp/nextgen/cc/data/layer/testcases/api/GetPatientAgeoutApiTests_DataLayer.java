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

public class GetPatientAgeoutApiTests_DataLayer extends BaseRest {

    private OrgInfo orgInfo;
    private Validations validations;
    Response getPatientAgeoutResponse;
    int practiceId;


    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        orgInfo = new OrgInfo();
        validations = new Validations();
    }

    @DataProvider(name = "invalidOrNullPracticeId")
    public Object[][] invalidOrNullPracticeId() throws Exception {
        return new Object[][]{{testData.getProperty("api.Non.existing.practice.id"), "PXPP-24655 TC_004 Verfiy 400 error code when practice id is Invalid/Non-existing"},
                {testData.getProperty("api.invalid.practice.id.tou"), "PXPP-24331 TC 003_verify response code 400 when Practice Id is in Invalid format"},
                {null, "PXPP-24332 TC_008 Verfiy 400 error code when practice id is null"},
                {"", "PXPP-24333 TC_005 verify response code 400 when user enters empty value on practice Id"}};
    }

    @DataProvider(name = "practiceIdHasOrNotHavingPatientAgeout")
    public Object[][] practiceIdHasOrNotHavingPatientAgeout() throws Exception {
        return new Object[][] {{Integer.parseInt(testData.getProperty("api.valid.practice.id.patient.ageout")), "PXPP-24329 Verify 200 response code when practice id is valid and having patient ageout"},
                {Integer.parseInt(testData.getProperty("api.valid.practice.id.not.having.patient.ageout")), "PXPP-24330 Verify 200 response code when practice id is valid and not having patient ageout"}};
    }

    @Test(priority = 1, dataProvider = "practiceIdHasOrNotHavingPatientAgeout", retryAnalyzer = RetryAnalyzer.class, description = "Fetching Patient Guardian Access details with valid practice id")
    public void getPatientAgeoutWithValidPracticeIdApiTestSuccess(int practiceId, String testCaseName) throws IOException {
        logStep(testCaseName);
        getPatientAgeoutResponse = orgInfo.getPatientAgeout_DataLayer("Valid", practiceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPatientAgeoutResponse);
        CCUtils.assertEquals(getPatientAgeoutResponse.getStatusCode(), 200);
        validations.verifyOutputFromValidInputForGetPatientAgeout(getPatientAgeoutResponse);
    }

    @Test(priority = 2, dataProvider = "invalidOrNullPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "Verifying error code 400 and error message when user enters invalid/incorrect/null practice Id")
    public void getPatientAgeoutWithInvalidDataOnPracticeId(String practiceId, String testCaseName) throws IOException {
        logStep(testCaseName);
        getPatientAgeoutResponse = orgInfo.getPatientAgeout_DataLayer(practiceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPatientAgeoutResponse);
        CCUtils.assertEquals(getPatientAgeoutResponse.getStatusCode(), 400);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24334 verify response code 404 and error message when user enters invalid uri")
    public void getPatientAgeoutWithInvalidURI() throws IOException {
        logStep("TC 006_verify response code 404 and error message when user enters valid practice Id with Invalid URI by using GET Request [DL]");
        practiceId = Integer.parseInt(testData.getProperty("api.valid.practice.id.patient.ageout"));
        getPatientAgeoutResponse = orgInfo.getPatientAgeout_DataLayer("Invalid", practiceId);
        logStep("Validating response information");
        CCUtils.assertEquals(getPatientAgeoutResponse.getStatusCode(), 404);
        validations.verifyErrorInResponseForInvalidUrl(getPatientAgeoutResponse);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24335 verify response code 405 and error message when user enters valid practice Id with Invalid PATCH method for [DL]")
    public void getPatientAgeoutWithInvalidMethod() throws IOException {
        logStep("TC 007_verify response code 405 and error message when user enters valid practice Id instead of get with patch method [DL]");
        practiceId = Integer.parseInt(testData.getProperty("api.valid.practice.id.patient.ageout"));
        getPatientAgeoutResponse = orgInfo.getPatientAgeout_DataLayer("InvalidMethod", practiceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPatientAgeoutResponse);
        CCUtils.assertEquals(getPatientAgeoutResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethodCall(getPatientAgeoutResponse);
    }

}
