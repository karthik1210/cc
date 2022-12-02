package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.UserSendEnrollmentConsumerDataLayer;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SearchUserWithEnrollmentStatusApiTests_DataLayer extends BaseRest {

    private UserInfo userInfo;
    private SharedInfo sharedInfo;
    private CreateNGHierachyClientCreation_DataLayer ngOrgPracticeObj;
    private UserSendEnrollmentConsumerDataLayer userSendEnrollmentConsumerPayloadObj;
    Response createUserApiResponse, createProviderApiResponse,
            searchUserWithEnrollmentApiResponse, sendUserEnrollmentConsumerResponse,updatePasswordDuringEnrollmentApiResponse,updateExpiredStatusApiResponse;
    HashMap<String, Integer> orgListMap = new HashMap<String, Integer>();
    HashMap<String, Object> userMap = new HashMap<String, Object>();
    int userId, providerId, practiceId, clientOrgId;
    String userType1;
    private Validations validations;
    static String internalEnrollmentId, internalUserName;
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
    Date date1 = new Date(System.currentTimeMillis()-26*60*60*1000);
    Date date2 = new Date(System.currentTimeMillis()-50*60*60*1000);
    String currentTs = dateFormat.format(new Date(date.getTime() + (1000 * 60 * 60 * 24)));
    String expiryTs = dateFormat.format(new Date(date1.getTime() + (1000 * 60 * 60 * 24)));
    String initiatedTs = dateFormat.format(new Date(date2.getTime() + (1000 * 60 * 60 * 24)));
    boolean runOnce = true;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        userInfo = new UserInfo();
        sharedInfo = new SharedInfo();
        ngOrgPracticeObj = new CreateNGHierachyClientCreation_DataLayer();
        validations = new Validations();
    }

    @Test(priority = 1)
    public void searchInternalUserWithEnrollmentStatusApiSuccess() throws IOException {
        userMap.clear();
        userMap = createNewInternalUser(testData.getProperty("pxpp.26460.enrollment.status.not.sent"));
        userType1 = userMap.get("userType").toString();
        userId = Integer.parseInt(userMap.get("userId").toString());
        validatingResponseCodeForSearchUserWithEnrollmentStatus();
        runOnce = false;
    }

    @Test(priority = 2)
    public void searchClientUserWithEnrollmentStatusApiSuccess() throws Exception {
        orgListMap.clear();
        orgListMap = getOrgMapList();
        practiceId = orgListMap.get("practiceId");
        clientOrgId = orgListMap.get("clientId");
        providerId = createProvider(practiceId);
        userMap.clear();
        userMap = createNewClientUser(clientOrgId, providerId, testData.getProperty("pxpp.26460.enrollment.status.not.sent"));
        userId = Integer.parseInt(userMap.get("userId").toString());
        userType1 = userMap.get("userType").toString();
        validatingResponseCodeForSearchUserWithEnrollmentStatus();
        runOnce = false;
    }

    @Test(priority = 3)
    public void searchVARResellerUserWithEnrollmentStatusApiSuccess() throws Exception {
        userMap.clear();
        userMap = createNewVarResellerUser(clientOrgId, providerId, testData.getProperty("pxpp.26460.enrollment.status.not.sent"));
        userType1 = userMap.get("userType").toString();
        validatingResponseCodeForSearchUserWithEnrollmentStatus();
        runOnce = false;
    }

    @Test(priority = 4)
    public void searchInternalUserWithEnrollmentStatusWithNoContent() throws IOException {
        if(runOnce) {
            userMap.clear();
            userMap = createNewInternalUser(testData.getProperty("pxpp.26460.enrollment.status.not.sent"));
            userType1 = userMap.get("userType").toString();
            userId = Integer.parseInt(userMap.get("userId").toString());
        }
        userType1 = userMap.get("userType").toString();
        setupServiceRequestSpecificationBuilderForDataLayer();
        searchUserWithEnrollmentApiResponse = userInfo.getUserInfo("DL", userId);
        log(searchUserWithEnrollmentApiResponse.getBody().jsonPath().get("enrollmentStatus").toString());
        searchUserWithEnrollmentApiResponse = userInfo.searchUserWithEnrollmentStatus("DL", userType1, "Valid", testData.getProperty("pxpp.26460.enrollment.status.pending"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(searchUserWithEnrollmentApiResponse);
        CCUtils.assertEquals(searchUserWithEnrollmentApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 5)
    public void searchClientUserWithEnrollmentStatusWithNoContent() throws IOException {
        if(runOnce) {
            orgListMap.clear();
            orgListMap = getOrgMapList();
            practiceId = orgListMap.get("practiceId");
            clientOrgId = orgListMap.get("clientId");
            providerId = createProvider(practiceId);
            userMap.clear();
            userMap = createNewClientUser(clientOrgId, providerId, testData.getProperty("pxpp.26460.enrollment.status.not.sent"));
            userId = Integer.parseInt(userMap.get("userId").toString());
            userType1 = userMap.get("userType").toString();
        }
        userType1 = userMap.get("userType").toString();
        setupServiceRequestSpecificationBuilderForDataLayer();
        searchUserWithEnrollmentApiResponse = userInfo.getUserInfo("DL", userId);
        log(searchUserWithEnrollmentApiResponse.getBody().jsonPath().get("enrollmentStatus").toString());
        searchUserWithEnrollmentApiResponse = userInfo.searchUserWithEnrollmentStatus("DL", userType1, "Valid", testData.getProperty("pxpp.26460.enrollment.status.pending"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(searchUserWithEnrollmentApiResponse);
        CCUtils.assertEquals(searchUserWithEnrollmentApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 6)
    public void searchVARResellerUserWithEnrollmentStatusWithNoContent() throws IOException {
        if(runOnce) {
            userMap.clear();
            userMap = createNewVarResellerUser(clientOrgId, providerId, testData.getProperty("pxpp.26460.enrollment.status.not.sent"));
            userType1 = userMap.get("userType").toString();
        }
        userType1 = userMap.get("userType").toString();
        setupServiceRequestSpecificationBuilderForDataLayer();
        searchUserWithEnrollmentApiResponse = userInfo.getUserInfo("DL", userId);
        log(searchUserWithEnrollmentApiResponse.getBody().jsonPath().get("enrollmentStatus").toString());
        searchUserWithEnrollmentApiResponse = userInfo.searchUserWithEnrollmentStatus("DL", userType1, "Valid", testData.getProperty("pxpp.26460.enrollment.status.pending"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(searchUserWithEnrollmentApiResponse);
        CCUtils.assertEquals(searchUserWithEnrollmentApiResponse.getStatusCode(), 204);
    }

    @Test(enabled = false)
    public HashMap<String, Object> createNewInternalUser(String enrollmentStatus) throws IOException {
        logStep("Create Internal user using valid details ");
        List<Integer> roleIds = new ArrayList<Integer>();
        String firstName = testData.getProperty("api.user.firstName").concat(CCUtils.generateAlphaNumericString(5));
        String lastName = testData.getProperty("api.user.lastName").concat(CCUtils.generateAlphaNumericString(5));
        roleIds.add(Integer.parseInt(testData.getProperty("api.internal.user.roleId")));
        String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
        String userName = testData.getProperty("api.user.userName").concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
        Long mobileNumber = CCUtils.generateMobileNumber();
        String userType = "Internal";
        setupServiceRequestSpecificationBuilder();
        createUserApiResponse = userInfo.createNewUser_BusinessLayer(userType,
                firstName, lastName, userName, email,
                mobileNumber, testData.getProperty("api.user.userStatus"), enrollmentStatus, null, null, null, roleIds,
                false);
        userId = createUserApiResponse.jsonPath().getInt("id");
        userMap.clear();
        userMap.put("userId", userId);
        userMap.put("username", userName);
        userMap.put("userType", userType);
        return userMap;
    }

    @Test(enabled = false)
    public HashMap<String, Object> createNewClientUser(int clientOrgId, int providerId, String enrollmentStatus) throws IOException {
        logStep("Create client user using valid details ");
        String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
        String userName = testData.getProperty("api.user.userName")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
        Long mobileNumber = CCUtils.generateMobileNumber();
        setupServiceRequestSpecificationBuilder();
        String userType = "Client";
        createUserApiResponse = userInfo.createNewUser_BusinessLayer(userType,
                testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
                mobileNumber, testData.getProperty("api.user.userStatus"), enrollmentStatus,
                clientOrgId, testData.getProperty("api.reseller.id"), providerId, null, false);
        userId = createUserApiResponse.jsonPath().getInt("id");
        userMap.clear();
        userMap.put("userId", userId);
        userMap.put("username", userName);
        userMap.put("userType", userType);
        return userMap;
    }

    @Test(enabled = false)
    public HashMap<String, Object> createNewVarResellerUser(int clientOrgId, int providerId, String enrollmentStatus) throws IOException {
        logStep("Create Var/Reseller user using valid details ");
        List<Integer> roleIds = new ArrayList<Integer>();
        roleIds.add(Integer.parseInt(testData.getProperty("api.VAResellerUser.roleId")));
        String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
        String userName = testData.getProperty("api.user.userName")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
        Long mobileNumber = CCUtils.generateMobileNumber();
        setupServiceRequestSpecificationBuilder();
        String userType = "VARReseller";
        createUserApiResponse = userInfo.createNewUser_BusinessLayer(userType,
                testData.getProperty("api.user.firstName"), testData.getProperty("api.user.lastName"), userName, email,
                mobileNumber, testData.getProperty("api.user.userStatus"), enrollmentStatus, clientOrgId, testData.getProperty("api.resellerId"), providerId, roleIds,
                false);
        userId = createUserApiResponse.jsonPath().getInt("id");
        userMap.clear();
        userMap.put("userId", userId);
        userMap.put("username", userName);
        userMap.put("userType", userType);
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

    @Test(enabled = false)
    public void validatingResponseCodeForSearchUserWithEnrollmentStatus() throws IOException {
        setupServiceRequestSpecificationBuilderForDataLayer();
        searchUserWithEnrollmentApiResponse = userInfo.getUserInfo("DL", userId);
        log(searchUserWithEnrollmentApiResponse.getBody().jsonPath().get("enrollmentStatus").toString());
        CCUtils.assertEquals(searchUserWithEnrollmentApiResponse.getBody().jsonPath().get("enrollmentStatus").toString().equalsIgnoreCase(testData.getProperty("pxpp.26460.enrollment.status.not.sent")), true);
        searchUserWithEnrollmentApiResponse = userInfo.searchUserWithEnrollmentStatus("DL", userType1, "Valid", testData.getProperty("pxpp.26460.enrollment.status.not.sent"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(searchUserWithEnrollmentApiResponse);
        CCUtils.assertEquals(searchUserWithEnrollmentApiResponse.getStatusCode(), 200);
        validations.verifyUserOutputWithValidInput(searchUserWithEnrollmentApiResponse, userType1);
        internalUserName = userMap.get("username").toString();
        internalEnrollmentId = CCUtils.generateAuthId();
        userSendEnrollmentConsumerPayloadObj = new UserSendEnrollmentConsumerDataLayer(internalEnrollmentId, initiatedTs, expiryTs, testData.getProperty("pxpp.26460.enrollment.status.pending"));
        sendUserEnrollmentConsumerResponse = userInfo.sendUserEnrollmentConsumerDL("Valid", userId, userSendEnrollmentConsumerPayloadObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(sendUserEnrollmentConsumerResponse);
        CCUtils.assertEquals(sendUserEnrollmentConsumerResponse.getStatusCode(), 200);
        searchUserWithEnrollmentApiResponse = userInfo.getUserInfo("DL", userId);
        log(searchUserWithEnrollmentApiResponse.getBody().jsonPath().get("enrollmentStatus").toString());
        CCUtils.assertEquals(searchUserWithEnrollmentApiResponse.getBody().jsonPath().get("enrollmentStatus").toString().equalsIgnoreCase(testData.getProperty("pxpp.26460.enrollment.status.pending")), true);
        searchUserWithEnrollmentApiResponse = userInfo.searchUserWithEnrollmentStatus("DL", userType1, "Valid", testData.getProperty("pxpp.26460.enrollment.status.pending"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(searchUserWithEnrollmentApiResponse);
        CCUtils.assertEquals(searchUserWithEnrollmentApiResponse.getStatusCode(), 200);
        updateExpiredStatusApiResponse = userInfo.updateExpiredStatus_DL("DL", "Valid", testData.getProperty("pxpp.26403.valid.expired.status"), currentTs);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updateExpiredStatusApiResponse);
        CCUtils.assertEquals(updateExpiredStatusApiResponse.getStatusCode(), 200);
        searchUserWithEnrollmentApiResponse = userInfo.searchUserWithEnrollmentStatus("DL", userType1, "Valid", testData.getProperty("pxpp.26403.valid.expired.status"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(searchUserWithEnrollmentApiResponse);
        CCUtils.assertEquals(searchUserWithEnrollmentApiResponse.getStatusCode(), 200);
        setupServiceRequestSpecificationBuilderForDataLayer();
        searchUserWithEnrollmentApiResponse = userInfo.getUserInfo("DL", userId);
        log(searchUserWithEnrollmentApiResponse.getBody().jsonPath().get("enrollmentStatus").toString());
        CCUtils.assertEquals(searchUserWithEnrollmentApiResponse.getBody().jsonPath().get("enrollmentStatus").toString().equalsIgnoreCase(testData.getProperty("pxpp.26403.valid.expired.status")), true);
        internalEnrollmentId = CCUtils.generateAuthId();
        userSendEnrollmentConsumerPayloadObj = new UserSendEnrollmentConsumerDataLayer(internalEnrollmentId, initiatedTs, expiryTs, testData.getProperty("pxpp.26460.enrollment.status.pending"));
        sendUserEnrollmentConsumerResponse = userInfo.sendUserEnrollmentConsumerDL("Valid", userId, userSendEnrollmentConsumerPayloadObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(sendUserEnrollmentConsumerResponse);
        CCUtils.assertEquals(sendUserEnrollmentConsumerResponse.getStatusCode(), 200);
        searchUserWithEnrollmentApiResponse = userInfo.searchUserWithEnrollmentStatus("DL", userType1, "Valid", testData.getProperty("pxpp.26460.enrollment.status.pending"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(searchUserWithEnrollmentApiResponse);
        CCUtils.assertEquals(searchUserWithEnrollmentApiResponse.getStatusCode(), 200);
        searchUserWithEnrollmentApiResponse = userInfo.getUserInfo("DL", userId);
        log(searchUserWithEnrollmentApiResponse.getBody().jsonPath().get("enrollmentStatus").toString());
        CCUtils.assertEquals(searchUserWithEnrollmentApiResponse.getBody().jsonPath().get("enrollmentStatus").toString().equalsIgnoreCase(testData.getProperty("pxpp.26460.enrollment.status.pending")), true);
        setupServiceRequestSpecificationBuilder();
        String password = CCUtils.generateAlphaNumericStringIncludingSpecialChar(25)+"&9";
        updatePasswordDuringEnrollmentApiResponse = userInfo.updatePassword_BL("valid", internalEnrollmentId, password);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePasswordDuringEnrollmentApiResponse);
        CCUtils.assertEquals(updatePasswordDuringEnrollmentApiResponse.getStatusCode(), 200);
        setupServiceRequestSpecificationBuilderForDataLayer();
        searchUserWithEnrollmentApiResponse = userInfo.searchUserWithEnrollmentStatus("DL", userType1, "Valid", testData.getProperty("pxpp.26460.enrollment.status.confirmed"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(searchUserWithEnrollmentApiResponse);
        CCUtils.assertEquals(searchUserWithEnrollmentApiResponse.getStatusCode(), 200);
        searchUserWithEnrollmentApiResponse = userInfo.getUserInfo("DL", userId);
        log(searchUserWithEnrollmentApiResponse.getBody().jsonPath().get("enrollmentStatus").toString());
        CCUtils.assertEquals(searchUserWithEnrollmentApiResponse.getBody().jsonPath().get("enrollmentStatus").toString().equalsIgnoreCase(testData.getProperty("pxpp.26460.enrollment.status.confirmed")), true);
    }

}
