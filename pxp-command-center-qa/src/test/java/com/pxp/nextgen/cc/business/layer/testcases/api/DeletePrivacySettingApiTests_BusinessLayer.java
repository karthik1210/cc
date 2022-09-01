//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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

public class DeletePrivacySettingApiTests_BusinessLayer extends BaseRest {

    private OrgInfo orgInfo;
    private Validations validations;
    Response deletePrivacySettingApiResponse;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilder();
        orgInfo = new OrgInfo();
        validations = new Validations();
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a valid practice id and inactive tou id")
    public void getPrivacySettingWithPracticeIdAndInactiveTouId() throws IOException {
        deletePrivacySettingApiResponse = orgInfo.deletePrivacySettingByPracticeIdAndTouId_BL(Integer.parseInt(testData.getProperty("valid.practice.id.tou")), Integer.parseInt(testData.getProperty("valid.tou.id.inactive")),testData.getProperty("delete.true"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePrivacySettingApiResponse);
        CCUtils.assertEquals(deletePrivacySettingApiResponse.getStatusCode(), 200);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a valid practice id, inactive tou id and Invalid URI")
    public void getPrivacySettingWithPracticeIdAndInactiveTouIdAlongWithInvalidURI() throws IOException {
        deletePrivacySettingApiResponse = orgInfo.deletePrivacySettingByPracticeIdAndTouIdWithInvalidURI_BL(Integer.parseInt(testData.getProperty("valid.practice.id.tou")), Integer.parseInt(testData.getProperty("valid.tou.id.inactive")),testData.getProperty("delete.true"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePrivacySettingApiResponse);
        CCUtils.assertEquals(deletePrivacySettingApiResponse.getStatusCode(), 404);
        validations.verifyErrorInResponseForInvalidUrl(deletePrivacySettingApiResponse);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a valid practice id, inactive tou id and Invalid Request")
    public void getPrivacySettingWithPracticeIdAndInactiveTouIdAlongWithInvalidRequest() throws IOException {
        deletePrivacySettingApiResponse = orgInfo.deletePrivacySettingByPracticeIdAndTouIdWithInvalidRequest_BL(Integer.parseInt(testData.getProperty("valid.practice.id.tou")), Integer.parseInt(testData.getProperty("valid.tou.id.inactive")),testData.getProperty("delete.true"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePrivacySettingApiResponse);
        CCUtils.assertEquals(deletePrivacySettingApiResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethodCall(deletePrivacySettingApiResponse);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a non existing practice id, inactive tou id")
    public void getPrivacySettingWithNonExistingPracticeIdAndInactiveTouId() throws IOException {
        deletePrivacySettingApiResponse = orgInfo.deletePrivacySettingByPracticeIdAndTouId_BL(Integer.parseInt(testData.getProperty("Non.existing.practice.id")), Integer.parseInt(testData.getProperty("valid.tou.id.inactive")),testData.getProperty("delete.true"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePrivacySettingApiResponse);
        CCUtils.assertEquals(deletePrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(deletePrivacySettingApiResponse);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a valid practice id, non-existing tou id")
    public void getPrivacySettingWithValidPracticeIdAndNonExistingTouId() throws IOException {
        deletePrivacySettingApiResponse = orgInfo.deletePrivacySettingByPracticeIdAndTouId_BL(Integer.parseInt(testData.getProperty("valid.practice.id.tou")), Integer.parseInt(testData.getProperty("Non.existing.tou.id")),testData.getProperty("delete.true"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePrivacySettingApiResponse);
        CCUtils.assertEquals(deletePrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(deletePrivacySettingApiResponse);
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a non-existing practice id, non-existing tou id")
    public void getPrivacySettingWithNonExistingPracticeIdAndNonExistingTouId() throws IOException {
        deletePrivacySettingApiResponse = orgInfo.deletePrivacySettingByPracticeIdAndTouId_BL(Integer.parseInt(testData.getProperty("Non.existing.practice.id")), Integer.parseInt(testData.getProperty("Non.existing.tou.id")),testData.getProperty("delete.true"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePrivacySettingApiResponse);
        CCUtils.assertEquals(deletePrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(deletePrivacySettingApiResponse);
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a Invalid datatype on practice id, inactive tou id")
    public void getPrivacySettingWithInvalidDataTypeOnPracticeIdAndInactiveTouId() throws IOException {
        deletePrivacySettingApiResponse = orgInfo.deletePrivacySettingByPracticeIdAndTouId_BL(testData.getProperty("invalid.practice.id.tou"), Integer.parseInt(testData.getProperty("valid.tou.id.inactive")),testData.getProperty("delete.true"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePrivacySettingApiResponse);
        CCUtils.assertEquals(deletePrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(deletePrivacySettingApiResponse);
    }

    @Test(priority = 8, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a valid practice id, invalid datatype on tou id")
    public void getPrivacySettingWithValidPracticeIdAndInvalidDataTypeOnTouId() throws IOException {
        deletePrivacySettingApiResponse = orgInfo.deletePrivacySettingByPracticeIdAndTouId_BL(Integer.parseInt(testData.getProperty("valid.practice.id.tou")), testData.getProperty("invalid.datatype.tou.id"),testData.getProperty("delete.true"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePrivacySettingApiResponse);
        CCUtils.assertEquals(deletePrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(deletePrivacySettingApiResponse);
    }

    @Test(priority = 9, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a invalid datatype on both practice id and tou id")
    public void getPrivacySettingWithInvalidDataTypeOnBothPracticeIdAndTouId() throws IOException {
        deletePrivacySettingApiResponse = orgInfo.deletePrivacySettingByPracticeIdAndTouId_BL(testData.getProperty("invalid.practice.id.tou"), testData.getProperty("invalid.datatype.tou.id"),testData.getProperty("delete.true"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePrivacySettingApiResponse);
        CCUtils.assertEquals(deletePrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(deletePrivacySettingApiResponse);
    }

    @Test(priority = 10, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a null on practice id, inactive tou id")
    public void getPrivacySettingWithNullOnPracticeIdAndInactiveTouId() throws IOException {
        deletePrivacySettingApiResponse = orgInfo.deletePrivacySettingByPracticeIdAndTouId_BL(null, Integer.parseInt(testData.getProperty("valid.tou.id.inactive")),testData.getProperty("delete.true"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePrivacySettingApiResponse);
        CCUtils.assertEquals(deletePrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(deletePrivacySettingApiResponse);
    }

    @Test(priority = 11, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a valid practice id, null on tou id")
    public void getPrivacySettingWithValidPracticeIdAndNullOnTouId() throws IOException {
        deletePrivacySettingApiResponse = orgInfo.deletePrivacySettingByPracticeIdAndTouId_BL(Integer.parseInt(testData.getProperty("valid.practice.id.tou")), null,testData.getProperty("delete.true"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePrivacySettingApiResponse);
        CCUtils.assertEquals(deletePrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(deletePrivacySettingApiResponse);
    }

    @Test(priority = 12, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a null on both practice id and tou id")
    public void getPrivacySettingWithNullOnBothPracticeIdAndTouId() throws IOException {
        deletePrivacySettingApiResponse = orgInfo.deletePrivacySettingByPracticeIdAndTouId_BL(null, null,testData.getProperty("delete.true"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePrivacySettingApiResponse);
        CCUtils.assertEquals(deletePrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(deletePrivacySettingApiResponse);
    }

    @Test(priority = 13, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a valid practice id and active tou id")
    public void getPrivacySettingWithPracticeIdAndActiveTouId() throws IOException {
        deletePrivacySettingApiResponse = orgInfo.deletePrivacySettingByPracticeIdAndTouId_BL(Integer.parseInt(testData.getProperty("valid.practice.id.tou")), Integer.parseInt(testData.getProperty("valid.tou.id.active")),testData.getProperty("delete.true"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePrivacySettingApiResponse);
        CCUtils.assertEquals(deletePrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(deletePrivacySettingApiResponse);
    }

    @Test(priority = 14, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a valid practice id and active tou id along with invalid payload")
    public void getPrivacySettingWithPracticeIdAndInactiveTouIdAlongWithInvalidPayload() throws IOException {
        deletePrivacySettingApiResponse = orgInfo.deletePrivacySettingByPracticeIdAndTouId_BL(Integer.parseInt(testData.getProperty("valid.practice.id.tou")), Integer.parseInt(testData.getProperty("valid.tou.id.inactive")),testData.getProperty("delete.invalid"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePrivacySettingApiResponse);
        CCUtils.assertEquals(deletePrivacySettingApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(deletePrivacySettingApiResponse);
    }

    @Test(priority = 15, retryAnalyzer = RetryAnalyzer.class, description = "Fetching privacy setting with a valid practice id and inactive tou id with delete false")
    public void getPrivacySettingWithPracticeIdAndInactiveTouIdWithDeleteFalse() throws IOException {
        deletePrivacySettingApiResponse = orgInfo.deletePrivacySettingByPracticeIdAndTouId_BL(Integer.parseInt(testData.getProperty("valid.practice.id.tou")), Integer.parseInt(testData.getProperty("valid.tou.id.inactive")),testData.getProperty("delete.false"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePrivacySettingApiResponse);
        CCUtils.assertEquals(deletePrivacySettingApiResponse.getStatusCode(), 200);
    }
}
