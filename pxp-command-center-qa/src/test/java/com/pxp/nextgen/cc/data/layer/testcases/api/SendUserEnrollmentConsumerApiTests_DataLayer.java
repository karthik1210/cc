// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.DisplayText;
import com.pxp.cc.pojos.PracticeTouDetails;
import com.pxp.cc.pojos.TouSolutions;
import com.pxp.cc.pojos.UserSendEnrollmentConsumerDataLayer;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.*;
import java.util.*;

public class SendUserEnrollmentConsumerApiTests_DataLayer extends BaseRest {
    private UserInfo userInfo;
    private SharedInfo sharedInfo;
    private CreateNGHierachyClientCreation_DataLayer ngOrgPracticeObj;
    private UserSendEnrollmentConsumerDataLayer userSendEnrollmentConsumerPayloadObj;
    Response createUserApiResponse, sendUserEnrollmentConsumerRespose, createProviderApiResponse, getUserEnrollmentRespose;
    HashMap<String, Integer> orgListMap = new HashMap<String, Integer>();
    HashMap<String, Object> userMap = new HashMap<String, Object>();
    int userId, clientId, varSellerId, providerId, practiceId, clientOrgId;
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
    String initiatedTs = dateFormat.format(date);
    String expiryTs = dateFormat.format(new Date(date.getTime() + (1000 * 60 * 60 * 24)));

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        userInfo = new UserInfo();
        sharedInfo = new SharedInfo();
        ngOrgPracticeObj = new CreateNGHierachyClientCreation_DataLayer();
    }

    @DataProvider(name = "invalidUserIds")
    public Object[][] invalidUserIds() throws Exception {
        return new Object[][]{{testData.getProperty("non.existing.user.id"), "PXPP-25837_TC002_Verify the Response Code 400, when user enter non-existing userId for send enrollment consumer"},
                {testData.getProperty("existing.user.id.with.user.roles") + "Sample", "PXPP-25840_TC 004_ user enters Invalid datatype/null on user id for send enrollment consumer"},
                {null, "PXPP-25840_TC 004_ user enters Invalid datatype/null on user id for send enrollment consume"},
                {"", "Verify the Response Code 400, when user enter Empty user Id"}};
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-25835_Send Enrollment Consumer for all User Types(Internal, Client, VarReseller) ")
    public void sendUserEnrollmentConsumerApiTestsSuccess() throws IOException {
        logStep("TC 001_verify response code 200 when user enters valid user id and valid payload for Send Enrollment Consumer by using PATCH Method");
        logStep("-----Send Enrollment Consumer for Internal User Type-----");
        userMap.clear();
        userMap = createNewInternalUser();
        userId = Integer.parseInt(userMap.get("userId").toString());
        String internalUserName = userMap.get("username").toString();
        String internalEnrollmentId = CCUtils.generateAuthId();
        userSendEnrollmentConsumerPayloadObj = new UserSendEnrollmentConsumerDataLayer(internalEnrollmentId, initiatedTs, expiryTs, "PENDING");
        sendUserEnrollmentConsumerRespose = userInfo.sendUserEnrollmentConsumerDL("Valid", userId, userSendEnrollmentConsumerPayloadObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(sendUserEnrollmentConsumerRespose);
        CCUtils.assertEquals(sendUserEnrollmentConsumerRespose.getStatusCode(), 200);
        getUserEnrollmentRespose = userInfo.getUserEnrollmentDL("Valid", internalEnrollmentId, "PENDING");
        CCUtils.assertEquals(getUserEnrollmentRespose.jsonPath().get("userId"), userId);
        CCUtils.assertEquals(getUserEnrollmentRespose.jsonPath().get("username"), internalUserName);
        logStep("-----Send Enrollment Consumer for Client User Type-----");
        orgListMap.clear();
        orgListMap = getOrgMapList();
        practiceId = orgListMap.get("practiceId");
        clientOrgId = orgListMap.get("clientId");
        providerId = createProvider(practiceId);
        userMap.clear();
        userMap = createNewClientUser(clientOrgId, providerId);
        clientId = Integer.parseInt(userMap.get("userId").toString());
        String clientUserName = userMap.get("username").toString();
        String clientEnrollmentId = CCUtils.generateAuthId();
        userSendEnrollmentConsumerPayloadObj = new UserSendEnrollmentConsumerDataLayer(clientEnrollmentId, initiatedTs, expiryTs, "NOT_SENT");
        sendUserEnrollmentConsumerRespose = userInfo.sendUserEnrollmentConsumerDL("Valid", clientId, userSendEnrollmentConsumerPayloadObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(sendUserEnrollmentConsumerRespose);
        CCUtils.assertEquals(sendUserEnrollmentConsumerRespose.getStatusCode(), 200);
        getUserEnrollmentRespose = userInfo.getUserEnrollmentDL("Valid", clientEnrollmentId, "NOT_SENT");
        CCUtils.assertEquals(getUserEnrollmentRespose.jsonPath().get("userId"), clientId);
        CCUtils.assertEquals(getUserEnrollmentRespose.jsonPath().get("username"), clientUserName);
        logStep("-----Send Enrollment Consumer for VarReseller User Type-----");
        userMap.clear();
        userMap = createNewVarResellerUser(clientOrgId, providerId);
        ;
        varSellerId = Integer.parseInt(userMap.get("userId").toString());
        String varResellerUserName = userMap.get("username").toString();
        String varResellerEnrollmentId = CCUtils.generateAuthId();
        userSendEnrollmentConsumerPayloadObj = new UserSendEnrollmentConsumerDataLayer(varResellerEnrollmentId, initiatedTs, expiryTs, "CONFIRMED");
        sendUserEnrollmentConsumerRespose = userInfo.sendUserEnrollmentConsumerDL("Valid", varSellerId, userSendEnrollmentConsumerPayloadObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(sendUserEnrollmentConsumerRespose);
        CCUtils.assertEquals(sendUserEnrollmentConsumerRespose.getStatusCode(), 200);
        getUserEnrollmentRespose = userInfo.getUserEnrollmentDL("Valid", varResellerEnrollmentId, "CONFIRMED");
        CCUtils.assertEquals(getUserEnrollmentRespose.jsonPath().get("userId"), varSellerId);
        CCUtils.assertEquals(getUserEnrollmentRespose.jsonPath().get("username"), varResellerUserName);
    }

    @Test(priority = 2, dataProvider = "invalidUserIds", retryAnalyzer = RetryAnalyzer.class, description = "Send Enrollment Consumer for Non-existing/Alphanumeric/Null/Empty UserIds")
    public void sendEnrollmentsForAlphaNumericNullEmptyUserIds(String userId, String testCaseName) throws IOException {
        logStep(testCaseName);
        userSendEnrollmentConsumerPayloadObj = new UserSendEnrollmentConsumerDataLayer(CCUtils.generateAuthId(), initiatedTs, expiryTs, "PENDING");
        logStep("Send Enrollment Consumer for Internal User Type");
        sendUserEnrollmentConsumerRespose = userInfo.sendUserEnrollmentConsumerInvaliDatatypeResponseDL(userId, userSendEnrollmentConsumerPayloadObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(sendUserEnrollmentConsumerRespose);
        CCUtils.assertEquals(sendUserEnrollmentConsumerRespose.getStatusCode(), 400);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-25838_Send enrollment consumer for user with Invalid payload")
    public void sendEnrollmentConsumerUserWithInvalidPayload() throws IOException {
        logStep("TC 003_verify response code 400 and error message when user enters valid user id and invalid payload for send enrollment consumer by using PATCH method");
        userMap.clear();
        userMap = createNewInternalUser();
        userId = Integer.parseInt(userMap.get("userId").toString());
        userSendEnrollmentConsumerPayloadObj = new UserSendEnrollmentConsumerDataLayer(CCUtils.generateAuthId(), "null", expiryTs, "PENDING");
        logStep("Send Enrollment Consumer for Internal User Type");
        sendUserEnrollmentConsumerRespose = userInfo.sendUserEnrollmentConsumerDL("Valid", userId, userSendEnrollmentConsumerPayloadObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(sendUserEnrollmentConsumerRespose);
        CCUtils.assertEquals(sendUserEnrollmentConsumerRespose.getStatusCode(), 400);
        userSendEnrollmentConsumerPayloadObj = new UserSendEnrollmentConsumerDataLayer(CCUtils.generateAuthId(), "null", "null", "PENDING");
        logStep("Send Enrollment Consumer for Internal User Type");
        sendUserEnrollmentConsumerRespose = userInfo.sendUserEnrollmentConsumerDL("Valid", userId, userSendEnrollmentConsumerPayloadObj);
        logStep("Validating response Code");
        CCUtils.assertEquals(sendUserEnrollmentConsumerRespose.getStatusCode(), 400);
        userSendEnrollmentConsumerPayloadObj = new UserSendEnrollmentConsumerDataLayer(CCUtils.generateAuthId(), initiatedTs, expiryTs, "STARTED");
        logStep("Send Enrollment Consumer for Internal User Type");
        sendUserEnrollmentConsumerRespose = userInfo.sendUserEnrollmentConsumerDL("Valid", userId, userSendEnrollmentConsumerPayloadObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(sendUserEnrollmentConsumerRespose);
        CCUtils.assertEquals(sendUserEnrollmentConsumerRespose.getStatusCode(), 400);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-25842_Send Enrollment for user with Invalid URI")
    public void sendEnrollmentConsumerUserWithInvalidURI() throws IOException {
        logStep("TC 005_verify response code 404 and error message when user enters valid user id and valid payload for send enrollment consumer by using PATCH method with Invalid URI");
        userMap.clear();
        userMap = createNewInternalUser();
        userId = Integer.parseInt(userMap.get("userId").toString());
        userSendEnrollmentConsumerPayloadObj = new UserSendEnrollmentConsumerDataLayer(CCUtils.generateAuthId(), initiatedTs, expiryTs, "PENDING");
        logStep("Send Enrollment Consumer for Internal User Type");
        sendUserEnrollmentConsumerRespose = userInfo.sendUserEnrollmentConsumerDL("InValid", userId, userSendEnrollmentConsumerPayloadObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(sendUserEnrollmentConsumerRespose);
        CCUtils.assertEquals(sendUserEnrollmentConsumerRespose.getStatusCode(), 404);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-25852_Send Enrollment Consumer for User with Invalid Method Type")
    public void sendEnrollmentUserWithInvalidMethodType() throws IOException {
        logStep("TC 006_verify response code 405 and error message when user enters valid user id and valid payload for send enrollment consumer by using DELETE Method");
        userMap.clear();
        userMap = createNewInternalUser();
        userId = Integer.parseInt(userMap.get("userId").toString());
        userSendEnrollmentConsumerPayloadObj = new UserSendEnrollmentConsumerDataLayer(CCUtils.generateAuthId(), initiatedTs, expiryTs, "PENDING");
        logStep("Send Enrollment Consumer for Internal User Type");
        sendUserEnrollmentConsumerRespose = userInfo.sendUserEnrollmentConsumerDL("InValidMethod", userId, userSendEnrollmentConsumerPayloadObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(sendUserEnrollmentConsumerRespose);
        CCUtils.assertEquals(sendUserEnrollmentConsumerRespose.getStatusCode(), 405);
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

    @Test(enabled = false)
    public HashMap<String, Object> createNewClientUser(int clientOrgId, int providerId) throws IOException {
        logStep("Create client user using valid details ");
        String authId = CCUtils.generateAuthId();
        String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
        String userName = testData.getProperty("api.user.userName")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
        Long mobileNumber = CCUtils.generateMobileNumber();
        createUserApiResponse = userInfo.createNewUser_DataLayer(authId, testData.getProperty("user.type.Client"),
                testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
                mobileNumber, testData.getProperty("api.user.userStatus"), clientOrgId,
                null, providerId, null, null);
        userId = createUserApiResponse.jsonPath().getInt("id");
        userMap.clear();
        userMap.put("userId", userId);
        userMap.put("username", userName);
        return userMap;
    }

    @Test(enabled = false)
    public HashMap<String, Object> createNewVarResellerUser(int clientOrgId, int providerId) throws IOException {
        logStep("Create Var/Reseller user using valid details ");
        String authId = CCUtils.generateAuthId();
        List<Integer> roleIds = new ArrayList<Integer>();
        roleIds.add(Integer.parseInt(testData.getProperty("api.VAResellerUser.roleId")));
        String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
        String userName = testData.getProperty("api.user.userName")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
        Long mobileNumber = CCUtils.generateMobileNumber();
        createUserApiResponse = userInfo.createNewUser_DataLayer(authId, testData.getProperty("user.type.VAReseller"),
                testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
                mobileNumber, testData.getProperty("varReseller.user.userStatus"), clientOrgId,
                testData.getProperty("api.reseller.id"), providerId, null, roleIds);
        userId = createUserApiResponse.jsonPath().getInt("id");
        userMap.clear();
        userMap.put("userId", userId);
        userMap.put("username", userName);
        return userMap;
    }

    @Test(enabled = false)
    public int createProvider(int practiceId) throws IOException {
        logStep("Create new provider");
        String firstName = testData.getProperty("api.provider.firstname").concat(CCUtils.generateAlphaNumericString(5));
        String lastName = testData.getProperty("api.provider.lastname").concat(CCUtils.generateAlphaNumericString(5));
        String externalId = CCUtils.generateAlphaNumericString(10);
        createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
                testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
        CCUtils.assertEquals(createProviderApiResponse.getStatusCode(), 200);
        providerId = createProviderApiResponse.jsonPath().getInt("providerId");
        return providerId;
    }

    @Test(enabled = false)
    public HashMap<String, Integer> getOrgMapList() throws IOException {
        String clientName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.client.name").concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5))));
        String clientSapNumber = CCUtils.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.client.sapnumber").concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5))));
        String testFlag = testData.getProperty("api.client.testclient");
        String clientTimezoneId = testData.getProperty("api.client.timezoneid");
        String status = testData.getProperty("api.client.active");
        int clientType = Integer.parseInt(testData.getProperty("api.client.type"));
        String systemName = testData.getProperty("api.system.name");
        String orgType = testData.getProperty("api.system.orgtype");
        String parentType = testData.getProperty("api.parenttype");
        int ehrIdentifier = Integer.parseInt(testData.getProperty("ehr.identifier"));
        int pmIdentifier = Integer.parseInt(testData.getProperty("pm.identifier"));
        String enterpriseName = testData.getProperty("api.enterprise.name");
        String enterpriseType = testData.getProperty("api.enterprise.orgtype");
        String enterpriseParentType = testData.getProperty("api.enterprise.parenttype");
        String enterpriseTimeZone = testData.getProperty("api.enterprise.timezoneid");
        String practiceDisplayName = testData.getProperty("api.practice.displayname");
        String practiceName = testData.getProperty("api.practice.name");
        String practiceOrgType = testData.getProperty("api.practice.orgtype");
        String practiceParentType = testData.getProperty("api.practice.parenttype");
        String practiceTimeZone = testData.getProperty("api.practice.timezoneid");
        orgListMap.clear();
        orgListMap = ngOrgPracticeObj.getOrgHierPractice(status, clientName, clientSapNumber, testFlag, clientType, clientTimezoneId, systemName, orgType, parentType,
                ehrIdentifier, pmIdentifier, enterpriseName, enterpriseType, enterpriseParentType, enterpriseTimeZone, practiceDisplayName, practiceName,
                practiceOrgType, practiceParentType, practiceTimeZone);
        return orgListMap;
    }

}
