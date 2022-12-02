// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.UserSendEnrollmentConsumerDataLayer;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UpdatePasswordDuringEnrollmentApiTests_BusinessLayer extends BaseRest {

    private UserInfo userInfo;
    private UserSendEnrollmentConsumerDataLayer userSendEnrollmentConsumerPayloadObj;
    Response createUserApiResponse, sendUserEnrollmentConsumerResponse, updatePasswordDuringEnrollmentApiResponse, getUserEnrollmentResponse;
    private Validations validations;
    HashMap<String, Object> userMap = new HashMap<String, Object>();
    int userId;
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
    String initiatedTs = dateFormat.format(date);
    String expiryTs = dateFormat.format(new Date(date.getTime() + (1000 * 60 * 60 * 24)));
    static String internalEnrollmentId, internalUserName;
    boolean runOnce = true;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        userInfo = new UserInfo();
        validations = new Validations();
    }

    @BeforeMethod()
    public void createUserWithSendEnrollment() throws Exception {
        setupServiceRequestSpecificationBuilder();
        if(runOnce) {
            userMap.clear();
            userMap = createNewInternalUser();
            userId = Integer.parseInt(userMap.get("userId").toString());
            internalUserName = userMap.get("username").toString();
            setupServiceRequestSpecificationBuilderForDataLayer();
            internalEnrollmentId = CCUtils.generateAuthId();
            userSendEnrollmentConsumerPayloadObj = new UserSendEnrollmentConsumerDataLayer(internalEnrollmentId, initiatedTs, expiryTs, "PENDING");
            sendUserEnrollmentConsumerResponse = userInfo.sendUserEnrollmentConsumerDL("Valid", userId, userSendEnrollmentConsumerPayloadObj);
            logStep("Validating response Code");
            CCUtils.responseTimeValidation(sendUserEnrollmentConsumerResponse);
            CCUtils.assertEquals(sendUserEnrollmentConsumerResponse.getStatusCode(), 200);
            getUserEnrollmentResponse = userInfo.getUserEnrollmentDL("Valid", internalEnrollmentId, "PENDING");
            CCUtils.assertEquals(getUserEnrollmentResponse.jsonPath().get("userId"), userId);
            CCUtils.assertEquals(getUserEnrollmentResponse.jsonPath().get("username"), internalUserName);
            setupServiceRequestSpecificationBuilder();
        }
    }

    @DataProvider(name = "invalidEnrollmentIds")
    public Object[][] invalidEnrollmentId() throws Exception {
        return new Object[][]{{null, "pxpp-26080 TC 003_verify response code 400 when user enters invalid datatype/null on enrollment id and valid payload for update password during enrollment by using PATCH method"},
                {testData.getProperty("pxpp.26417.invalid.enrollment.id"), "pxpp-26080 TC 003_verify response code 400 when user enters invalid datatype/null on enrollment id and valid payload for update password during enrollment by using PATCH method"},
                {testData.getProperty("pxpp.26417.non.existing.enrollment.id"), "pxpp-26079 TC 002_verify response code 400 and error message when user enters non-existing enrollment id and valid payload for update password during enrollment by using PATCH method "}};
    }

    @DataProvider(name = "invalidPasswords")
    public Object[][] invalidPassword() throws Exception {
        return new Object[][]{{CCUtils.generateRandomStringWithoutSymbolsOrNumbers(20)},
                {CCUtils.generateMobileNumber().toString()},
                {CCUtils.generateinvalidMobileNumber().toString()},
                {testData.getProperty("pxpp.26417.invalid.length.password")}
        };
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-26078 TC 001_verify response code 200 when user enters valid enrollment id and valid payload for update password during enrollment by using PATCH method")
    public void updatePasswordDuringEnrollmentApiSuccess() throws IOException {
        logStep("-----Update password during Enrollment for Internal User Type-----");
        String password = CCUtils.generateAlphaNumericStringIncludingSpecialChar(25)+"&9";
        updatePasswordDuringEnrollmentApiResponse = userInfo.updatePassword_BL("valid", internalEnrollmentId, password);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePasswordDuringEnrollmentApiResponse);
        CCUtils.assertEquals(updatePasswordDuringEnrollmentApiResponse.getStatusCode(), 200);
        setupServiceRequestSpecificationBuilderForDataLayer();
        getUserEnrollmentResponse = userInfo.getUserEnrollmentDL("Valid", internalEnrollmentId, "CONFIRMED");
        CCUtils.assertEquals(getUserEnrollmentResponse.jsonPath().get("userId"), userId);
        CCUtils.assertEquals(getUserEnrollmentResponse.jsonPath().get("username"), internalUserName);
        runOnce = false;
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidEnrollmentIds", description = "Update password during enrollment with invalid enrollment ids")
    public void updatePasswordDuringEnrollmentWithInvalidEnrollmentId(String enrollmentId, String testCaseName) throws Exception {
        log(testCaseName);
        String password = CCUtils.generateAlphaNumericStringIncludingSpecialChar(25);
        updatePasswordDuringEnrollmentApiResponse = userInfo.updatePassword_BL("valid", enrollmentId, password);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePasswordDuringEnrollmentApiResponse);
        CCUtils.assertEquals(updatePasswordDuringEnrollmentApiResponse.getStatusCode(), 400);
        validations.verifyResponseCodeForNonExistingUserType(updatePasswordDuringEnrollmentApiResponse);
        runOnce = false;
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidPasswords", description = "pxpp - 26081 TC 004_verify response code 400 when user enters valid enrollment id and invalid payload for update password during enrollment by using PATCH method")
    public void updatePasswordDuringEnrollmentWithInvalidPayload(String password) throws Exception {
        updatePasswordDuringEnrollmentApiResponse = userInfo.updatePassword_BL("valid", internalEnrollmentId, password);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePasswordDuringEnrollmentApiResponse);
        CCUtils.assertEquals(updatePasswordDuringEnrollmentApiResponse.getStatusCode(), 400);
        validations.verifyResponseCodeForNonExistingUserType(updatePasswordDuringEnrollmentApiResponse);
        runOnce = false;
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "pxpp-26082 TC 005_verify response code 404 and error message when user enters valid enrollment id and valid payload for update password during enrollment by using PATCH method with invalid URI")
    public void updatePasswordDuringEnrollmentWithInvalidURI() throws Exception {
        logStep("-----Update password during Enrollment for Internal User Type-----");
        String password = CCUtils.generateAlphaNumericStringIncludingSpecialChar(25);
        updatePasswordDuringEnrollmentApiResponse = userInfo.updatePassword_BL("Invalid", internalEnrollmentId, password);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePasswordDuringEnrollmentApiResponse);
        CCUtils.assertEquals(updatePasswordDuringEnrollmentApiResponse.getStatusCode(), 404);
        validations.verifyErrorInResponseForInvalidUrl(updatePasswordDuringEnrollmentApiResponse);
        runOnce = false;
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "pxpp-26083 TC 006_verify response code 405 and error message when user enters valid enrollment id and valid payload for update password during enrollment by using DELETE method")
    public void updatePasswordDuringEnrollmentWithDeleteMethod() throws IOException {
        logStep("-----Update password during Enrollment for Internal User Type-----");
        String password = CCUtils.generateAlphaNumericStringIncludingSpecialChar(25);
        updatePasswordDuringEnrollmentApiResponse = userInfo.updatePassword_BL("", internalEnrollmentId, password);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePasswordDuringEnrollmentApiResponse);
        CCUtils.assertEquals(updatePasswordDuringEnrollmentApiResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethodCall(updatePasswordDuringEnrollmentApiResponse);
        runOnce = false;
    }

    @Test(enabled = false)
    public HashMap<String, Object> createNewInternalUser() throws IOException {
        logStep("Create Internal user using valid details ");
        String authIdOne = CCUtils.generateAuthId();
        List<Integer> roleIds = new ArrayList<Integer>();
        String firstName = testData.getProperty("api.user.firstName").concat(CCUtils.generateAlphaNumericString(5));
        String lastName = testData.getProperty("api.user.lastName").concat(CCUtils.generateAlphaNumericString(5));
        roleIds.add(Integer.parseInt(testData.getProperty("api.internal.user.roleId")));
        String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
        String userName = testData.getProperty("api.user.userName").concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
        Long mobileNumber = CCUtils.generateMobileNumber();
        createUserApiResponse = userInfo.createNewUser_BusinessLayer("Internal",
                testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
                mobileNumber, testData.getProperty("api.user.userStatus"), "NOT_SENT", null, null, null, roleIds,
                false);
        userId = createUserApiResponse.jsonPath().getInt("id");
        userMap.clear();
        userMap.put("userId", userId);
        userMap.put("username", userName);
        return userMap;
    }
}
