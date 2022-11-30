package com.pxp.nextgen.cc.business.layer.testcases.api;

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

public class UpdateExpiredStatusApiTests_BusinessLayer extends BaseRest {

    private UserInfo userInfo;
    private Validations validations;
    Response updateExpiredStatusApiResponse;
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
    String currentTs = dateFormat.format(new Date(date.getTime() + (1000 * 60 * 60 * 24)));

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilder();
        userInfo = new UserInfo();
        validations = new Validations();
    }

    @DataProvider(name = "invalidStatus")
    public Object[][] invalidStatusAndCurrentTs() throws Exception {
        return new Object[][]{{testData.getProperty("pxpp.26403.invalid.expired.status")},
                {null}};
    }


    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP- 26067 TC 001_verify response code 200 when user enters valid payload for Update Expired status by using PATCH method")
    public void updateExpiredStatusWithValidPayload() {
        updateExpiredStatusApiResponse = userInfo.updateExpiredStatus_BL("BL", "Valid", testData.getProperty("pxpp.26403.valid.expired.status"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateExpiredStatusApiResponse);
        CCUtils.assertEquals(updateExpiredStatusApiResponse.getStatusCode(), 200);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidStatus", description = "PXPP-26069 TC 002_verify response code 400 and error message when user enters invalid payload for update Expired status by using PATCH method")
    public void updateExpiredStatusWithInvalidPayload(String status) throws IOException {
        updateExpiredStatusApiResponse = userInfo.updateExpiredStatus_BL("BL", "Valid", status);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateExpiredStatusApiResponse);
        CCUtils.assertEquals(updateExpiredStatusApiResponse.getStatusCode(), 400);
        validations.verifyResponseCodeForNonExistingUserType(updateExpiredStatusApiResponse);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-26070 TC 003_verify response code 404 and error message, when user enters valid payload for update Expired status by using PATCH method with Invalid URI")
    public void updateExpiredStatusWithInvalidURI() throws IOException {
        updateExpiredStatusApiResponse = userInfo.updateExpiredStatus_BL("BL", "Invalid", testData.getProperty("pxpp.26403.valid.expired.status"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateExpiredStatusApiResponse);
        CCUtils.assertEquals(updateExpiredStatusApiResponse.getStatusCode(), 404);
        validations.verifyErrorInResponseForInvalidUrl(updateExpiredStatusApiResponse);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-26071 TC 004_verify response code 405 and error message when user enters valid payload for update Expired status by using DELETE method")
    public void updateExpiredStatusWithInvalidMethod() throws IOException {
        updateExpiredStatusApiResponse = userInfo.updateExpiredStatus_BL("BL", "", testData.getProperty("pxpp.26403.valid.expired.status"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateExpiredStatusApiResponse);
        CCUtils.assertEquals(updateExpiredStatusApiResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethodCall(updateExpiredStatusApiResponse);
    }
}
