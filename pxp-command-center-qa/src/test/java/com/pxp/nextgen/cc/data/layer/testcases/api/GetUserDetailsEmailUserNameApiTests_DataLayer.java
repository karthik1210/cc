//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetUserDetailsEmailUserNameApiTests_DataLayer extends BaseRest {
    private UserInfo userInfo;
    private Validations validation;
    private CreateClientHierarchy_DL clientHierObj;
    private SharedInfo sharedInfo;
    Response createUserApiResponse, getUserDetailsUsernameEmailResponse, createProviderApiResponse;
    String firstName, lastName;
   int clientId, systemId, enterpriseId, practiceId, providerId;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        userInfo = new UserInfo();
        validation = new Validations();
        clientHierObj = new CreateClientHierarchy_DL();
        sharedInfo = new SharedInfo();
    }

    @DataProvider(name = "inValid")
    public Object[][] inValidValues() throws Exception {
        return new Object[][]{ {null}, {testData.getProperty("Non.existing.practice.id").trim()}
        };
    }

    public int createProvider(int practiceId) throws Exception {
        logStep("Create provider for providerId");
        firstName = testData.getProperty("api.user.firstName").concat(CCUtils.generateAlphaNumericString(5));
        lastName = testData.getProperty("api.user.lastName").concat(CCUtils.generateAlphaNumericString(5));
        String externalId = CCUtils.generateAlphaNumericString(10);
        createProviderApiResponse = sharedInfo.createProvider_DL(practiceId, firstName, lastName,
                testData.getProperty("provider.gender"), externalId, testData.getProperty("provider.credentials"));
        return createProviderApiResponse.jsonPath().get("providerId");
    }


    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-28641:Get Internal user details with username/email")
    public void getInternalUserDetailsWithUserNameEmail() throws Exception {
        logStep("TC 001_Verify the Response code as 200, when user try to get the User Details for given User Name by using GET Request [DL]");
        String firstName = testData.getProperty("api.user.firstName").concat(CCUtils.generateAlphaNumericString(5));
        String lastName = testData.getProperty("api.user.lastName").concat(CCUtils.generateAlphaNumericString(5));
        List<Integer> roleIds = new ArrayList<Integer>();
        roleIds.add(Integer.parseInt(testData.getProperty("existing.internal.roleId")));
        String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
        String userName = testData.getProperty("api.user.userName").concat(CCUtils.generateAlphaNumericString(5));
        Long mobileNumber = CCUtils.generateMobileNumber();
        createUserApiResponse = userInfo.createNewUser_DataLayer("Internal", firstName ,lastName, userName, email, mobileNumber,
                testData.getProperty("api.user.userStatus"), "NOT_SENT", null, null, null, roleIds, false);
        logStep("Enter  new internal userid");
        getUserDetailsUsernameEmailResponse = UserInfo.getUserDetailsUserNameEmail_DL("Valid", userName);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getUserDetailsUsernameEmailResponse);
        CCUtils.assertEquals(getUserDetailsUsernameEmailResponse.getStatusCode(), 200);
        validation.verifyUserDetailsWithUserNameEmailResponse(getUserDetailsUsernameEmailResponse, firstName, lastName, userName, email);
        logStep("Enter  new internal userid");
        getUserDetailsUsernameEmailResponse = UserInfo.getUserDetailsUserNameEmail_DL("Valid", email);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getUserDetailsUsernameEmailResponse);
        CCUtils.assertEquals(getUserDetailsUsernameEmailResponse.getStatusCode(), 200);
        validation.verifyUserDetailsWithUserNameEmailResponse(getUserDetailsUsernameEmailResponse, firstName, lastName, userName, email);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-28641:Get Client user details with username/email")
    public void getClientUserDetailsUserNameEmail() throws Exception {
        logStep("TC 001_Verify the Response code as 200, when user try to get the User Details for given User Name by using GET Request [DL] ");
        clientId = clientHierObj.createClient_DL();
        systemId = clientHierObj.createSystem_DL(clientId);
        enterpriseId = clientHierObj.createEnterprise_DL(systemId);
        practiceId = clientHierObj.createPractice_DL(enterpriseId);
        providerId = createProvider(practiceId);
        providerId = createProviderApiResponse.jsonPath().get("providerId");
        String firstName = testData.getProperty("api.user.firstName").concat(CCUtils.generateAlphaNumericString(5));
        String lastName = testData.getProperty("api.user.lastName").concat(CCUtils.generateAlphaNumericString(5));
        String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
        String userName = testData.getProperty("api.user.userName")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
        Long mobileNumber = CCUtils.generateMobileNumber();
        createUserApiResponse = userInfo.createNewUser_DataLayer("Client", firstName, lastName, userName, email, mobileNumber,
                testData.getProperty("api.user.userStatus"), "NOT_SENT", clientId, null, providerId, null, false);
       getUserDetailsUsernameEmailResponse = UserInfo.getUserDetailsUserNameEmail_DL("Valid", userName);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getUserDetailsUsernameEmailResponse);
        CCUtils.assertEquals(getUserDetailsUsernameEmailResponse.getStatusCode(), 200);
       logStep("Enter  new internal userid");
        getUserDetailsUsernameEmailResponse = UserInfo.getUserDetailsUserNameEmail_DL("Valid", email);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getUserDetailsUsernameEmailResponse);
        CCUtils.assertEquals(getUserDetailsUsernameEmailResponse.getStatusCode(), 200);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-28641:Get VAR/Reseller user details with username/email")
    public void getVarResellerUserDetailsUserNameEmail() throws NullPointerException, IOException {
        logStep("TC 001_Verify the Response code as 200, when user try to get the User Details for given User Name by using GET Request [DL] ");
        List<Integer> roleIds = new ArrayList<Integer>();
        roleIds.add(Integer.parseInt(testData.getProperty("existing.varReseller.roleId")));
        String firstName = testData.getProperty("api.user.firstName").concat(CCUtils.generateAlphaNumericString(5));
        String lastName = testData.getProperty("api.user.lastName").concat(CCUtils.generateAlphaNumericString(5));
        String email = testData.getProperty("api.user.email").concat(CCUtils.generateNewEmail(5));
        String userName = testData.getProperty("api.user.userName")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
        Long mobileNumber = CCUtils.generateMobileNumber();
        createUserApiResponse = userInfo.createNewUser_DataLayer("VARReseller",firstName, lastName, userName, email,
                mobileNumber, testData.getProperty("api.user.userStatus"), "NOT_SENT", null,
                testData.getProperty("api.resellerId"), null, roleIds, false);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(createUserApiResponse);
        CCUtils.assertEquals(createUserApiResponse.getStatusCode(), 201);
        getUserDetailsUsernameEmailResponse = UserInfo.getUserDetailsUserNameEmail_DL("Valid", userName);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getUserDetailsUsernameEmailResponse);
        CCUtils.assertEquals(getUserDetailsUsernameEmailResponse.getStatusCode(), 200);
        validation.verifyUserDetailsWithUserNameEmailResponse(getUserDetailsUsernameEmailResponse, firstName, lastName, userName, email);
        logStep("Enter  new internal userid");
        getUserDetailsUsernameEmailResponse = UserInfo.getUserDetailsUserNameEmail_DL("Valid", email);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getUserDetailsUsernameEmailResponse);
        CCUtils.assertEquals(getUserDetailsUsernameEmailResponse.getStatusCode(), 200);
        validation.verifyUserDetailsWithUserNameEmailResponse(getUserDetailsUsernameEmailResponse, firstName, lastName, userName, email);
    }


    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, dataProvider = "inValid",description = "PXPP-28643: Enter Non Existing id in userId field and verify error message for userId field")
    public void getUserDetailsWithNonExistingUser(String userNameEmail) throws IOException {
        logStep("TC 003_Verify the Response code as 400, when user try to give null/non-existing Username for GET Request of Forget Password [DL]");
        getUserDetailsUsernameEmailResponse = UserInfo.getUserDetailsUserNameEmail_DL("Valid", userNameEmail);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getUserDetailsUsernameEmailResponse);
        //commented due to PXPP-28989NEW1 sub-taskGetting 204 response instead of 400 when user enters Non-existing/null on username/email to fetch user details
        //CCUtils.assertEquals(getUserDetailsUsernameEmailResponse.getStatusCode(), 400);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-28642_Enter invalid url and validate error status code")
    public void getUserDetailsUsingInvalidUrl() throws IOException {
        logStep("TC 002_Verify the Response code as 404, when user try to give invalid URI for GET Call of the User Details for Forgot Password [DL]");
        getUserDetailsUsernameEmailResponse = UserInfo.getUserDetailsUserNameEmail_DL("InValid", testData.getProperty("existing.internal.user.username"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getUserDetailsUsernameEmailResponse);
        CCUtils.assertEquals(getUserDetailsUsernameEmailResponse.getStatusCode(), 404);
        validation.verifyErrorInResponseForInvalidUrl(getUserDetailsUsernameEmailResponse);
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-28658: Enter DELETE method instead of GET method and validate error status code")
    public void getUserDetailsUsingInvalidMethod() throws IOException {
        logStep("TC 004_Verify the Response code as 405, when user try to DELETE method instead of GET Request for Forgot Password User [DL]");
        getUserDetailsUsernameEmailResponse = UserInfo.getUserDetailsUserNameEmail_DL("InValidMethod", testData.getProperty("existing.internal.user.username"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getUserDetailsUsernameEmailResponse);
        CCUtils.assertEquals(getUserDetailsUsernameEmailResponse.getStatusCode(), 405);
        validation.verifyErrorMessageInResponseForInvalidMethod(getUserDetailsUsernameEmailResponse,"DELETE" );
    }
}
