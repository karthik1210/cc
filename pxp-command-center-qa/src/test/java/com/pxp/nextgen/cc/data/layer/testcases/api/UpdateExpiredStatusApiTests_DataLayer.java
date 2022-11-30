// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UpdateExpiredStatusApiTests_DataLayer extends BaseRest {

    private UserInfo userInfo;
    private Validations validations;
    Response updateExpiredStatusApiResponse;
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
    String currentTs = dateFormat.format(new Date(date.getTime() + (1000 * 60 * 60 * 24)));

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        userInfo = new UserInfo();
        validations = new Validations();
    }

    @DataProvider(name = "invalidStatusAndCurrentTs")
    public Object[][] invalidStatusAndCurrentTs() throws Exception {
        return new Object[][] {{testData.getProperty("pxpp.26403.invalid.expired.status"), currentTs},
                {null, currentTs},
                {testData.getProperty("pxpp.26403.valid.expired.status"), testData.getProperty("pxpp.26403.invalid.currentTs")},
                {null, null},
                {testData.getProperty("pxpp.26403.valid.expired.status"), null}};
    }


    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP- 26019 TC 001_verify response code 200 when user enters valid payload for Update Expired status by using PATCH method")
    public void updateExpiredStatusWithValidPayload() {
        updateExpiredStatusApiResponse = userInfo.updateExpiredStatus_DL("DL", "Valid", testData.getProperty("pxpp.26403.valid.expired.status"), currentTs);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateExpiredStatusApiResponse);
        CCUtils.assertEquals(updateExpiredStatusApiResponse.getStatusCode(), 200);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidStatusAndCurrentTs", description = "PXPP-26051 TC 002_verify response code 400 and error message when user enters invalid payload for update Expired status by using PATCH method")
    public void updateExpiredStatusWithInvalidPayload(String status, String currentTsValue) throws IOException {
        updateExpiredStatusApiResponse = userInfo.updateExpiredStatus_DL("DL", "Valid", status, currentTsValue);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateExpiredStatusApiResponse);
        CCUtils.assertEquals(updateExpiredStatusApiResponse.getStatusCode(), 400);
        validations.verifyResponseCodeForNonExistingUserType(updateExpiredStatusApiResponse);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-26059 TC 003_verify response code 404 and error message, when user enters valid payload for update Expired status by using PATCH method with Invalid URI")
    public void updateExpiredStatusWithInvalidURI() throws IOException {
        updateExpiredStatusApiResponse = userInfo.updateExpiredStatus_DL("DL", "Invalid", testData.getProperty("pxpp.26403.valid.expired.status"), currentTs);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateExpiredStatusApiResponse);
        CCUtils.assertEquals(updateExpiredStatusApiResponse.getStatusCode(), 404);
        validations.verifyErrorInResponseForInvalidUrl(updateExpiredStatusApiResponse);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-26060 TC 004_verify response code 405 and error message when user enters valid payload for update Expired status by using DELETE method")
    public void updateExpiredStatusWithInvalidMethod() throws IOException {
        updateExpiredStatusApiResponse = userInfo.updateExpiredStatus_DL("DL", "", testData.getProperty("pxpp.26403.valid.expired.status"), currentTs);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateExpiredStatusApiResponse);
        CCUtils.assertEquals(updateExpiredStatusApiResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethodCall(updateExpiredStatusApiResponse);
    }
}
