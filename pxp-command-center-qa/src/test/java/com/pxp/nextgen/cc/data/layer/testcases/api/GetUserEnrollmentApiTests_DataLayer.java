// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.UserSendEnrollmentConsumerDataLayer;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GetUserEnrollmentApiTests_DataLayer extends BaseRest {
    private UserInfo userInfo;
    private SharedInfo sharedInfo;
    private UserSendEnrollmentConsumerDataLayer userSendEnrollmentConsumerPayloadObj;
    Response createUserApiResponse, sendUserEnrollmentConsumerRespose, getUserEnrollmentRespose;
    HashMap<String, Integer> orgListMap = new HashMap<String, Integer>();
    HashMap<String, Object> userMap = new HashMap<String, Object>();
    int userId, clientId, varSellerId, providerId, practiceId, clientOrgId;
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
    String initiatedTs = dateFormat.format(date);
    String expiryTs = dateFormat.format(new Date(date.getTime() + (1000 * 60 * 60 * 24)));
    String enrollmentId = CCUtils.generateAuthId();

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        userInfo = new UserInfo();
        sharedInfo = new SharedInfo();
    }

    @DataProvider(name = "invalidEnrollmentIds")
    public Object[][] invalidEnrollmentIds() throws Exception {
        return new Object[][]{{testData.getProperty("non.existing.user.id"), "PXPP-25930_TC003_Verify the Response Code 204, when user enter non-existing enrollmentId "},
                {null, "PXPP-25929_TC 005_Verify the Response Code 204,  When user enters null value for user enrollment Id"}};
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-25926_Get Enrollment for Internal User ")
    public void getUserEnrollmentApiTestsSuccess() throws IOException {
        logStep("TC 001_Verify the Response code as 200, when user try to get the User details by using EnrollmentId and email status with GET Request [DL]");
        userMap.clear();
        userMap = createNewInternalUser();
        userId = Integer.parseInt(userMap.get("userId").toString());
        String internalUserName = userMap.get("username").toString();
        logStep("Send Enrollment Consumer for Internal User Type");
        userSendEnrollmentConsumerPayloadObj = new UserSendEnrollmentConsumerDataLayer(enrollmentId, initiatedTs, expiryTs, "PENDING");
        sendUserEnrollmentConsumerRespose = userInfo.sendUserEnrollmentConsumerDL("Valid", userId, userSendEnrollmentConsumerPayloadObj);
        logStep("Get Enrollment for Internal User Type");
        getUserEnrollmentRespose = userInfo.getUserEnrollmentDL("Valid", enrollmentId, "PENDING");
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(sendUserEnrollmentConsumerRespose);
        CCUtils.assertEquals(sendUserEnrollmentConsumerRespose.getStatusCode(), 200);
        CCUtils.assertEquals(getUserEnrollmentRespose.jsonPath().get("userId"), userId);
        CCUtils.assertEquals(getUserEnrollmentRespose.jsonPath().get("username"), internalUserName);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-25927_Get Enrollment for user with Invalid URI")
    public void getUserEnrollmentWithInvalidURI() throws IOException {
        logStep("TC 002_Verify the Response code as 404, when user try to give invalid URI for User Email Enrollment status by using GET Call [DL]");
        getUserEnrollmentRespose = userInfo.getUserEnrollmentDL("InValid", enrollmentId, "PENDING");
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(getUserEnrollmentRespose);
        CCUtils.assertEquals(getUserEnrollmentRespose.getStatusCode(), 404);
    }

    @Test(priority = 3, dataProvider = "invalidEnrollmentIds", retryAnalyzer = RetryAnalyzer.class, description = "Get user enrollments for Non-existing/Null UserIds")
    public void getUserEnrollmentwithInvalidNullEmptyEnrollmentIds(String invalidEnrollmentId, String testCaseName) throws IOException {
        logStep(testCaseName);
        getUserEnrollmentRespose = userInfo.getUserEnrollmentDL("Valid", invalidEnrollmentId, "PENDING");
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(getUserEnrollmentRespose);
        CCUtils.assertEquals(getUserEnrollmentRespose.getStatusCode(), 204);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-25928_Get User Enrollment for give enrollment Id with Invalid Method Type")
    public void getUserEnrollmentWithInvalidMethodType() throws IOException {
        logStep("TC 004_Verify the Response code as 405, when user try to DELETE method instead of GET Request for User Details by using Enrollment Id with Email Enrollment status [DL]");
        getUserEnrollmentRespose = userInfo.getUserEnrollmentDL("InValidMethod", enrollmentId, "PENDING");
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(getUserEnrollmentRespose);
        CCUtils.assertEquals(getUserEnrollmentRespose.getStatusCode(), 405);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-25931_Get Enrollment for user with Invalid enrollment status")
    public void getUserEnrollmentWithNonRelevantStatus() throws IOException {
        logStep("TC 006_Verify the Response code as 400, when user try to give valid enrollment Id and invalid/ non existing Enrollment status for User Enrollment by using GET Request [DL]");
        getUserEnrollmentRespose = userInfo.getUserEnrollmentDL("Valid", enrollmentId, "STARTED");
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(getUserEnrollmentRespose);
        CCUtils.assertEquals(getUserEnrollmentRespose.getStatusCode(), 400);
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-26191_Get Enrollment for user with non relevant enrollment status")
    public void getUserEnrollmentWithInvalidStatus() throws IOException {
        logStep("TC 007_Verify the Response code as 204, when user try to give valid enrollment Id and not relevant Enrollment status for User Enrollment by using GET Request [DL]");
        getUserEnrollmentRespose = userInfo.getUserEnrollmentDL("Valid", enrollmentId, "EXPIRED");
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(getUserEnrollmentRespose);
        CCUtils.assertEquals(getUserEnrollmentRespose.getStatusCode(), 204);
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
        createUserApiResponse = userInfo.createNewUser_DataLayer(authIdOne, testData.getProperty("user.type.Internal"), firstName, lastName, userName,
                email, mobileNumber, testData.getProperty("api.user.userStatus"), null, null, null, null, roleIds);
        userId = createUserApiResponse.jsonPath().getInt("id");
        userMap.clear();
        userMap.put("userId", userId);
        userMap.put("username", userName);
        return userMap;
    }

}
