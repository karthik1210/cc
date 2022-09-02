// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetPrivacySettingsByPracticeIdApiTests_DataLayer extends BaseRest {

    private OrgInfo orgInfo;
    private Validations validations;
    Response getPrivacySettingsApiResponse;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        orgInfo = new OrgInfo();
        validations = new Validations();
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Fetching list of privacy settings for a practice with practice Id")
    public void getPrivacySettingsByPracticeId() throws IOException {
        getPrivacySettingsApiResponse = orgInfo.getListOfTouForAPractice_DL(Integer.parseInt(testData.getProperty("api.valid.practice.id.tou")), testData.getProperty("api.valid.tou.type"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingsApiResponse);
        CCUtils.assertEquals(getPrivacySettingsApiResponse.getStatusCode(), 200);
        validations.verifyOutputFromValidInput(getPrivacySettingsApiResponse);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Fetching list of privacy settings for a practice with practice Id and invalid touType")
    public void getPrivacySettingsByPracticeIdWithInvalidTouType() throws IOException {
        getPrivacySettingsApiResponse = orgInfo.getListOfTouForAPractice_DL(Integer.parseInt(testData.getProperty("api.valid.practice.id.tou")), testData.getProperty("api.invalid.tou.type"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingsApiResponse);
        CCUtils.assertEquals(getPrivacySettingsApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Fetching list of privacy settings for practice with non-existing practice Id and valid touType")
    public void getPrivacySettingsWithInvalidPracticeIdAndValidTouType() throws IOException {
        getPrivacySettingsApiResponse = orgInfo.getListOfTouForAPractice_DL(Integer.parseInt(testData.getProperty("api.Non.existing.practice.id")), testData.getProperty("api.valid.tou.type"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingsApiResponse);
        CCUtils.assertEquals(getPrivacySettingsApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Fetching list of privacy settings for a practice with practice Id by PUT Request instead of GET")
    public void getPrivacySettingsByPracticeIdWithInvalidRequest() throws IOException {
        getPrivacySettingsApiResponse = orgInfo.getListOfTouForAPracticeWithInvalidRequest_DL(Integer.parseInt(testData.getProperty("api.valid.practice.id.tou")), testData.getProperty("api.valid.tou.type"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingsApiResponse);
        CCUtils.assertEquals(getPrivacySettingsApiResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethod(getPrivacySettingsApiResponse);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "Fetching list of privacy settings for a practice with practice Id and invalid URI")
    public void getPrivacySettingsByPracticeIdWithInvalidURI() throws IOException {
        getPrivacySettingsApiResponse = orgInfo.getListOfTouForAPracticeWithInvalidURI_DL(Integer.parseInt(testData.getProperty("api.valid.practice.id.tou")), testData.getProperty("api.valid.tou.type"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingsApiResponse);
        CCUtils.assertEquals(getPrivacySettingsApiResponse.getStatusCode(), 404);
        validations.verifyErrorInResponseForInvalidUrl(getPrivacySettingsApiResponse);
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "Fetching list of privacy settings for a practice with null practice Id and valid URI")
    public void getPrivacySettingsWithNullPracticeIdAndValidTouType() throws IOException {
        getPrivacySettingsApiResponse = orgInfo.getListOfTouForAPractice_DL(null, testData.getProperty("api.valid.tou.type"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingsApiResponse);
        CCUtils.assertEquals(getPrivacySettingsApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(getPrivacySettingsApiResponse);
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "Fetching list of privacy settings for a practice with wrong datatype practice Id and valid Tou type")
    public void getPrivacySettingsWithWrongDataTypePracticeIdAndValidTouType() throws IOException {
        getPrivacySettingsApiResponse = orgInfo.getListOfTouForAPractice_DL(testData.getProperty("api.invalid.practice.id.tou"), testData.getProperty("api.valid.tou.type"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingsApiResponse);
        CCUtils.assertEquals(getPrivacySettingsApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(getPrivacySettingsApiResponse);
    }

    @Test(priority = 8, retryAnalyzer = RetryAnalyzer.class, description = "Fetching list of privacy settings for practice with non-existing practice Id and non-existing touType")
    public void getPrivacySettingsWithInvalidPracticeIdAndNonExistingTouType() throws IOException {
        getPrivacySettingsApiResponse = orgInfo.getListOfTouForAPractice_DL(Integer.parseInt(testData.getProperty("api.Non.existing.practice.id")), testData.getProperty("api.invalid.tou.type"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingsApiResponse);
        CCUtils.assertEquals(getPrivacySettingsApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 9, retryAnalyzer = RetryAnalyzer.class, description = "Fetching list of privacy settings for a practice with Valid practice Id and Null tou type")
    public void getPrivacySettingsWithValidPracticeIdAndNullTouType() throws IOException {
        getPrivacySettingsApiResponse = orgInfo.getListOfTouForAPractice_DL(Integer.parseInt(testData.getProperty("api.valid.practice.id.tou")), "null");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingsApiResponse);
        CCUtils.assertEquals(getPrivacySettingsApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 10, retryAnalyzer = RetryAnalyzer.class, description = "Fetching list of privacy settings for a practice with null practice Id and Null tou type")
    public void getPrivacySettingsWithNullPracticeIdAndNullTouType() throws IOException {
        getPrivacySettingsApiResponse = orgInfo.getListOfTouForAPractice_DL(null, null);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingsApiResponse);
        CCUtils.assertEquals(getPrivacySettingsApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(getPrivacySettingsApiResponse);
    }

    @Test(priority = 11, retryAnalyzer = RetryAnalyzer.class, description = "Fetching list of privacy settings for a practice with Valid practice Id and Empty/null tou type")
    public void getPrivacySettingsWithValidPracticeIdAndEmptyNullTouType() throws IOException {
        getPrivacySettingsApiResponse = orgInfo.getListOfTouForAPractice_DL(Integer.parseInt(testData.getProperty("api.valid.practice.id.tou")), null);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingsApiResponse);
        CCUtils.assertEquals(getPrivacySettingsApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(getPrivacySettingsApiResponse);
    }

    @Test(priority = 12, retryAnalyzer = RetryAnalyzer.class, description = "Fetching list of privacy settings for a practice with one space on practice Id and one space on tou type")
    public void getPrivacySettingsWithOneSpaceOnBothPracticeIdAndTouType() throws IOException {
        getPrivacySettingsApiResponse = orgInfo.getListOfTouForAPractice_DL(" ", " ");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingsApiResponse);
        CCUtils.assertEquals(getPrivacySettingsApiResponse.getStatusCode(), 500);
        validations.verifyServerRequestRequestValidation(getPrivacySettingsApiResponse);
    }

    @Test(priority = 13, retryAnalyzer = RetryAnalyzer.class, description = "Fetching list of privacy settings for a practice with valid practice Id and wrong datatype Tou type")
    public void getPrivacySettingsWithValidPracticeIdAndWrongDataTypeTouType() throws IOException {
        getPrivacySettingsApiResponse = orgInfo.getListOfTouForAPractice_DL(Integer.parseInt(testData.getProperty("api.valid.practice.id.tou")), Integer.parseInt(testData.getProperty("api.invalid.datatype.tou.type")));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingsApiResponse);
        CCUtils.assertEquals(getPrivacySettingsApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 14, retryAnalyzer = RetryAnalyzer.class, description = "Fetching list of privacy settings for a practice with wrong datatype practice Id and wrong datatype Tou type")
    public void getPrivacySettingsWithWrongDataTypePracticeIdAndWrongDataTypeTouType() throws IOException {
        getPrivacySettingsApiResponse = orgInfo.getListOfTouForAPractice_DL(testData.getProperty("api.invalid.practice.id.tou"), Integer.parseInt(testData.getProperty("api.invalid.datatype.tou.type")));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingsApiResponse);
        CCUtils.assertEquals(getPrivacySettingsApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(getPrivacySettingsApiResponse);
    }

    @Test(priority = 15, retryAnalyzer = RetryAnalyzer.class, description = "Fectching list of privacy settings for a practice without even passing practice Id")
    public void getPrivacySettingsWithoutPracticeId() throws IOException {
        getPrivacySettingsApiResponse = orgInfo.getListOfTouForPracticeWithoutPracticeId_DL(testData.getProperty("api.valid.tou.type"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingsApiResponse);
        CCUtils.assertEquals(getPrivacySettingsApiResponse.getStatusCode(), 404);
        validations.verifyErrorInResponseForInvalidUrl(getPrivacySettingsApiResponse);
    }
}
