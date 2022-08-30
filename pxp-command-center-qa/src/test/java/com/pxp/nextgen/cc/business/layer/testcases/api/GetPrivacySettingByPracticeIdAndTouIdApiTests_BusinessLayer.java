package com.pxp.nextgen.cc.business.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetPrivacySettingByPracticeIdAndTouIdApiTests_BusinessLayer extends BaseRest {

    private OrgInfo orgInfo;
    private Validations validations;
    Response getPrivacySettingApiResponse;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilder();
        orgInfo = new OrgInfo();
        validations = new Validations();
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a valid practice id and active tou id")
    public void getPrivacySettingWithPracticeIdAndActiveTouId() throws IOException {
        getPrivacySettingApiResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_BL(Integer.parseInt(testData.getProperty("valid.practice.id.tou")), Integer.parseInt(testData.getProperty("valid.tou.id.active")));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingApiResponse);
        CCUtils.assertEquals(getPrivacySettingApiResponse.getStatusCode(), 200);
        validations.verifyOutputForPrivacySettingFromInput(getPrivacySettingApiResponse);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a valid practice id and inactive tou id")
    public void getPrivacySettingWithPracticeIdAndInactiveTouId() throws IOException {
        getPrivacySettingApiResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_BL(Integer.parseInt(testData.getProperty("valid.practice.id.tou")), Integer.parseInt(testData.getProperty("valid.tou.id.inactive")));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingApiResponse);
        CCUtils.assertEquals(getPrivacySettingApiResponse.getStatusCode(), 200);
        validations.verifyOutputForPrivacySettingFromInput(getPrivacySettingApiResponse);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a valid practice id, active tou id and invalid uri")
    public void getPrivacySettingWithPracticeIdAndInactiveTouIdAlongWithInvalidURI() throws IOException {
        getPrivacySettingApiResponse = orgInfo.getPrivacySettingByPracticeIdAndTouIdWithInvalidURI_BL(Integer.parseInt(testData.getProperty("valid.practice.id.tou")), Integer.parseInt(testData.getProperty("valid.tou.id.active")));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingApiResponse);
        CCUtils.assertEquals(getPrivacySettingApiResponse.getStatusCode(), 404);
        validations.verifyErrorInResponseForInvalidUrl(getPrivacySettingApiResponse);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a valid practice id, active tou id and invalid request")
    public void getPrivacySettingWithPracticeIdAndInactiveTouIdAlongWithInvalidRequest() throws IOException {
        getPrivacySettingApiResponse = orgInfo.getPrivacySettingByPracticeIdAndTouIdWithInvalidRequest_BL(Integer.parseInt(testData.getProperty("valid.practice.id.tou")), Integer.parseInt(testData.getProperty("valid.tou.id.active")));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingApiResponse);
        CCUtils.assertEquals(getPrivacySettingApiResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethodCall(getPrivacySettingApiResponse);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a valid practice id and non-existing tou id")
    public void getPrivacySettingWithPracticeIdAndNonExistingTouId() throws IOException {
        getPrivacySettingApiResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_BL(Integer.parseInt(testData.getProperty("valid.practice.id.tou")), Integer.parseInt(testData.getProperty("Non.existing.tou.id")));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingApiResponse);
        CCUtils.assertEquals(getPrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(getPrivacySettingApiResponse);
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a non-existing practice id and valid active tou id")
    public void getPrivacySettingWithNonExistingPracticeIdAndValidActiveTouId() throws IOException {
        getPrivacySettingApiResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_BL(Integer.parseInt(testData.getProperty("Non.existing.practice.id")), Integer.parseInt(testData.getProperty("valid.tou.id.active")));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingApiResponse);
        CCUtils.assertEquals(getPrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(getPrivacySettingApiResponse);
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a non-existing practice id and non-existing tou id")
    public void getPrivacySettingWithNonExistingPracticeIdAndNonExistingTouId() throws IOException {
        getPrivacySettingApiResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_BL(Integer.parseInt(testData.getProperty("Non.existing.practice.id")), Integer.parseInt(testData.getProperty("Non.existing.tou.id")));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingApiResponse);
        CCUtils.assertEquals(getPrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(getPrivacySettingApiResponse);
    }

    @Test(priority = 8, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a valid practice id and null on tou id")
    public void getPrivacySettingWithPracticeIdAndNullOnTouId() throws IOException {
        getPrivacySettingApiResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_BL(Integer.parseInt(testData.getProperty("valid.practice.id.tou")), null);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingApiResponse);
        CCUtils.assertEquals(getPrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(getPrivacySettingApiResponse);
    }

    @Test(priority = 9, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a null on practice id and valid active tou id")
    public void getPrivacySettingWithNullOnPracticeIdAndValidActiveTouId() throws IOException {
        getPrivacySettingApiResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_BL(null, Integer.parseInt(testData.getProperty("valid.tou.id.active")));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingApiResponse);
        CCUtils.assertEquals(getPrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(getPrivacySettingApiResponse);
    }

    @Test(priority = 10, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a null on practice id and null on tou id")
    public void getPrivacySettingWithNullOnBothPracticeIdAndTouId() throws IOException {
        getPrivacySettingApiResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_BL(null, null);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingApiResponse);
        CCUtils.assertEquals(getPrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(getPrivacySettingApiResponse);
    }

    @Test(priority = 11, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a valid practice id and empty value on tou id")
    public void getPrivacySettingWithValidPracticeIdAndEmptyValueOnTouId() throws IOException {
        getPrivacySettingApiResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_BL(Integer.parseInt(testData.getProperty("valid.practice.id.tou")), " ");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingApiResponse);
        CCUtils.assertEquals(getPrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(getPrivacySettingApiResponse);
    }

    @Test(priority = 12, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a without practice id and valid active on tou id")
    public void getPrivacySettingWithoutPracticeIdAndValidActiveTouId() throws IOException {
        getPrivacySettingApiResponse = orgInfo.getPrivacySettingWithoutPracticeIdAndValidTouId_BL(Integer.parseInt(testData.getProperty("valid.tou.id.active")));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingApiResponse);
        CCUtils.assertEquals(getPrivacySettingApiResponse.getStatusCode(), 404);
        validations.verifyErrorInResponseForInvalidUrl(getPrivacySettingApiResponse);
    }

    @Test(priority = 13, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a without practice id and tou id")
    public void getPrivacySettingWithoutPracticeIdAndTouId() throws IOException {
        getPrivacySettingApiResponse = orgInfo.getPrivacySettingWithoutPracticeIdAndTouId_BL();
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingApiResponse);
        CCUtils.assertEquals(getPrivacySettingApiResponse.getStatusCode(), 404);
        validations.verifyErrorInResponseForInvalidUrl(getPrivacySettingApiResponse);
    }

    @Test(priority = 14, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a valid practice id and Invalid datatype on tou id")
    public void getPrivacySettingWithValidPracticeIdAndInvalidDataTypeOnTouId() throws IOException {
        getPrivacySettingApiResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_BL(Integer.parseInt(testData.getProperty("valid.practice.id.tou")), testData.getProperty("invalid.datatype.tou.id"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingApiResponse);
        CCUtils.assertEquals(getPrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(getPrivacySettingApiResponse);
    }

    @Test(priority = 15, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a Invalid datatype on practice id and valid active tou id")
    public void getPrivacySettingWithInvalidDataTypeOnPracticeIdAndValidActiveTouId() throws IOException {
        getPrivacySettingApiResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_BL(testData.getProperty("invalid.practice.id.tou"), Integer.parseInt(testData.getProperty("valid.tou.id.active")));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingApiResponse);
        CCUtils.assertEquals(getPrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(getPrivacySettingApiResponse);
    }

    @Test(priority = 16, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a Invalid Datatype practice id and tou id")
    public void getPrivacySettingWithInvalidDataTypeOnPracticeIdAndTouId() throws IOException {
        getPrivacySettingApiResponse = orgInfo.getPrivacySettingByPracticeIdAndTouId_BL(testData.getProperty("invalid.practice.id.tou"), testData.getProperty("invalid.datatype.tou.id"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPrivacySettingApiResponse);
        CCUtils.assertEquals(getPrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(getPrivacySettingApiResponse);
    }

}
