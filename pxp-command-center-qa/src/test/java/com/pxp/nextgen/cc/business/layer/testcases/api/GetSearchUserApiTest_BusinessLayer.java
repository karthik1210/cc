/*
JIRA Id: PXPP-17559
Objective: QA: API Automation : Command Center User - Search Users (Internal, client user, Var/Reseller user) [BL]
Functionality: Search Users (Internal, client user, Var/Reseller user) [BL]
Covered Response Codes: 200,400,404,204
Designer: Karthikeyan Natarajan
Phase: Command Center Phase 1
 */

// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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

public class GetSearchUserApiTest_BusinessLayer extends BaseRest {

    private UserInfo userInfo;
    private Validations validation;
    Response getSearchUserApiResponse;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilder();
        userInfo = new UserInfo();
        validation = new Validations();
    }

    @DataProvider(name = "userTypeValue")
    public Object[][] getUserTypes() throws Exception {
        return new Object[][] { { testData.getProperty("user.type.Internal").trim() },
                { testData.getProperty("user.type.Client").trim() },
                { testData.getProperty("user.type.VAReseller").trim() } };
    }

    @Test(priority = 1,retryAnalyzer = RetryAnalyzer.class, dataProvider = "userTypeValue", description = "TC_17675,TC_17712,TC_17713 Get Search valid users")
    public void getSearchValidUserApi(String userType) throws IOException {
        getSearchUserApiResponse = userInfo.getSearchUser(userType,"Valid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getSearchUserApiResponse);
        CCUtils.assertEquals(getSearchUserApiResponse.getStatusCode(), 200);
        validation.verifyUserOutputWithValidInput(getSearchUserApiResponse,userType);
    }

    @DataProvider(name = "userType")
    public Object[][] getUserType() throws Exception {
        return new Object[][] {
                { testData.getProperty("user.type.internal").concat(CCUtils.generateAlphaNumericString(5)) },
                { testData.getProperty("api.existing.client.id") } };
    }

    @Test(priority = 2,retryAnalyzer = RetryAnalyzer.class, dataProvider = "userType", description = "TC_17710 Enter non existing user type and verify response status code")
    public void testResponseCodeForNonExistingUserType(String userType) throws IOException {
        getSearchUserApiResponse = userInfo.getSearchUser(userType,"Valid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getSearchUserApiResponse);
        CCUtils.assertEquals(getSearchUserApiResponse.getStatusCode(), 400);
        validation.verifyResponseCodeForNonExistingUserType(getSearchUserApiResponse);
    }

    @Test(priority = 3,retryAnalyzer = RetryAnalyzer.class, description = "TC_17688 Enter type as VAReseller and inputs on first_name, last_name, email of Client user")
    public void testResponseCodeForVAResellerTypeInternalInputs() throws IOException {
        getSearchUserApiResponse = userInfo.verifyResponseForValidUserIncorrectData(testData.getProperty("user.type.VAReseller").trim(),testData.getProperty("api.internal.firstName").trim(),
                testData.getProperty("api.internal.lastName").trim(),testData.getProperty("api.internal.email").trim(),
                testData.getProperty("api.internal.clientName").trim());
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getSearchUserApiResponse);
        CCUtils.assertEquals(getSearchUserApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 4,retryAnalyzer = RetryAnalyzer.class, description = "TC_17715 Enter type as Client and inputs on first_name, last_name, email of Internal user")
    public void testResponseCodeForClientTypeInternalInputs() throws IOException {
        getSearchUserApiResponse = userInfo.verifyResponseForValidUserIncorrectData(testData.getProperty("user.type.Client").trim(),testData.getProperty("api.internal.firstName").trim(),
                testData.getProperty("api.internal.lastName").trim(),testData.getProperty("api.internal.email").trim(),
                testData.getProperty("api.internal.clientName").trim());
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getSearchUserApiResponse);
        CCUtils.assertEquals(getSearchUserApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 5,retryAnalyzer = RetryAnalyzer.class, description = "TC_17714 Enter type as Internal and inputs on first_name, last_name, email of Client user")
    public void testResponseCodeForInternalTypeClientInputs() throws IOException {
        getSearchUserApiResponse = userInfo.verifyResponseForValidUserIncorrectData(testData.getProperty("user.type.Internal").trim(),testData.getProperty("api.client.firstName").trim(),
                testData.getProperty("api.client.lastName").trim(),testData.getProperty("api.client.email").trim(),
                testData.getProperty("api.client.clientName").trim());
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getSearchUserApiResponse);
        CCUtils.assertEquals(getSearchUserApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 6,retryAnalyzer = RetryAnalyzer.class, description = "TC_17711 Verify response code for valid type with non-existing input")
        public void testResponseCodeForValidTypeAndNonExistingInput() throws IOException{
            getSearchUserApiResponse = userInfo.verifyResponseForValidUserIncorrectData(testData.getProperty("user.type.Internal").trim(),testData.getProperty("api.invalid.clientUser.firstName").trim(),
                    testData.getProperty("api.invalid.clientUser.lastName").trim(),testData.getProperty("invalid.client.user.emailid").trim(),
                    testData.getProperty("api.invalid.client.name").trim());
            logStep("Validating response information");
            CCUtils.responseTimeValidation(getSearchUserApiResponse);
            CCUtils.assertEquals(getSearchUserApiResponse.getStatusCode(), 204);
        }

    @Test(priority = 7,retryAnalyzer = RetryAnalyzer.class, description = "TC_17745 Verify response code for Empty type with existing input")
    public void testResponseCodeForEmptyTypeAndExistingInput() throws IOException{
        getSearchUserApiResponse = userInfo.verifyResponseForValidUserIncorrectData(" ",testData.getProperty("api.client.firstName").trim(),
                testData.getProperty("api.client.lastName").trim(),testData.getProperty("api.client.email").trim(),
                testData.getProperty("api.client.clientName").trim());
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getSearchUserApiResponse);
        CCUtils.assertEquals(getSearchUserApiResponse.getStatusCode(), 400);
        validation.verifyResponseForEmptyOrMisFormatData(getSearchUserApiResponse);
    }

    @Test(priority = 8,retryAnalyzer = RetryAnalyzer.class, description = "TC_17689 Verify response code for Invalid URL")
    public void testResponseCodeForInvalidUrl()throws IOException{
        getSearchUserApiResponse = userInfo.getSearchUser(testData.getProperty("user.type.Internal").trim(),"Invalid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getSearchUserApiResponse);
        CCUtils.assertEquals(getSearchUserApiResponse.getStatusCode(), 404);
        validation.verifyErrorInResponseForInvalidUrl(getSearchUserApiResponse);
    }

    @DataProvider(name = "userdatavalidinput")
    public Object[] searchUserDetailsWithValidInput() {
        Object[][] obj = new Object[][] {
                { testData.getProperty("user.type.Internal").trim(), testData.getProperty("api.full.first.name").trim(),
                        testData.getProperty("api.full.last.name").trim(), null, null, null, null, null, null,null,
                        (testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
                { testData.getProperty("user.type.Internal").trim(),
                        testData.getProperty("api.partial.first.name").trim(),
                        testData.getProperty("api.partial.last.name").trim(), null, null, null, null, null, null,null,
                        (testData.getProperty("api.page.no").trim()),
                        (testData.getProperty("api.page.size").trim()) },
                { testData.getProperty("user.type.Client").trim(), testData.getProperty("api.full.client.firstname").trim(),
                        testData.getProperty("api.full.client.lastname").trim(), null, null, null, null, null, null,null,
                        (testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
                { testData.getProperty("user.type.Client").trim(),
                        testData.getProperty("api.partial.client.firstname").trim(),
                        testData.getProperty("api.partial.client.lastname").trim(), null, null, null, null, null, null,null,
                        (testData.getProperty("api.page.no").trim()),
                        (testData.getProperty("api.page.size").trim()) },
                { testData.getProperty("user.type.VAReseller").trim(), testData.getProperty("valid.user.first.name").trim(),
                        testData.getProperty("valid.user.last.name").trim(), null, null, null, null, null, null,null,
                        (testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
                { testData.getProperty("user.type.VAReseller").trim(),
                        testData.getProperty("api.VAReseller.partial.firstName").trim(),
                        testData.getProperty("api.VAReseller.partial.lastName").trim(), null, null, null, null, null, null,null,
                        (testData.getProperty("api.page.no").trim()),
                        (testData.getProperty("api.page.size").trim()) }};
        return obj;
    }

    @Test(priority = 9, retryAnalyzer = RetryAnalyzer.class, dataProvider = "userdatavalidinput", description = "Fetching User Details with valid type and valid full inputs and partial inputs")
    public void testGetSearchUserForGivenType(String[] getInputsForFetchUserDetails)throws IOException {
        getSearchUserApiResponse = userInfo.getSearchUserDetailsForGivenType(getInputsForFetchUserDetails[0],getInputsForFetchUserDetails[1],getInputsForFetchUserDetails[2],
                getInputsForFetchUserDetails[3],getInputsForFetchUserDetails[4],getInputsForFetchUserDetails[5],getInputsForFetchUserDetails[6],getInputsForFetchUserDetails[7],
                getInputsForFetchUserDetails[8],getInputsForFetchUserDetails[9],getInputsForFetchUserDetails[10],getInputsForFetchUserDetails[11]);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getSearchUserApiResponse);
        CCUtils.assertEquals(getSearchUserApiResponse.getStatusCode(), 200);
        validation.verifyUserOutputWithValidInput(getSearchUserApiResponse,getInputsForFetchUserDetails[1]);
    }

    @Test(priority = 10, retryAnalyzer = RetryAnalyzer.class, dataProvider = "userTypeValue", description = "Get search user details with invalid POST method and verifying response code and error message")
    public void getSearchUserWithInvalidPost(String userType)throws IOException {
        getSearchUserApiResponse = userInfo.getSearchUserDetailsWithInvalidPut(userType);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getSearchUserApiResponse);
        CCUtils.assertEquals(getSearchUserApiResponse.getStatusCode(), 405);
        validation.verifyErrorMessageInResponseForInvalidMethodCall(getSearchUserApiResponse);
    }

    @DataProvider(name = "userdatavalidemailandmobileinput")
    public Object[] searchUserDetailsWithValidEmailInput() {
        Object[][] obj = new Object[][] {
                { testData.getProperty("user.type.Internal").trim(), null, null,
                        testData.getProperty("api.full.email.id").trim(), null, null, null, null, null,null,
                        (testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
                { testData.getProperty("user.type.Internal").trim(), null, null,
                        testData.getProperty("api.partial.email.id").trim(), null, null, null, null, null,null,
                        (testData.getProperty("api.page.no").trim()),
                        (testData.getProperty("api.page.size").trim()) },
                { testData.getProperty("user.type.Internal").trim(), null, null,null,null,
                testData.getProperty("api.full.mobile.number").trim(), null, null, null, null,
                (testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
        { testData.getProperty("user.type.Internal").trim(), null, null,null,null,
                testData.getProperty("api.partial.mobile.number").trim(), null, null, null, null,
                (testData.getProperty("api.page.no").trim()),
                (testData.getProperty("api.page.size").trim()) },
                { testData.getProperty("user.type.Client").trim(), null, null,
                testData.getProperty("api.full.client.emailId").trim(), null, null, null, null, null,null,
                (testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
    { testData.getProperty("user.type.Client").trim(), null, null,
            testData.getProperty("api.partial.client.emailId").trim(), null, null, null, null, null,null,
            (testData.getProperty("api.page.no").trim()),
            (testData.getProperty("api.page.size").trim()) },
                { testData.getProperty("user.type.Client").trim(), null, null,null,null,
                        testData.getProperty("api.full.client.mobilenumber").trim(), null, null, null, null,
                        (testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
                { testData.getProperty("user.type.Client").trim(), null, null,null,null,
                        testData.getProperty("api.partial.client.mobilenumber").trim(), null, null, null, null,
                        (testData.getProperty("api.page.no").trim()),
                        (testData.getProperty("api.page.size").trim()) },
                { testData.getProperty("user.type.VAReseller").trim(), null, null,
                        testData.getProperty("api.full.vareseller.email").trim(), null, null, null, null, null,null,
                        (testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
                { testData.getProperty("user.type.VAReseller").trim(), null, null,
                        testData.getProperty("api.partial.vareseller.email").trim(), null, null, null, null, null,null,
                        (testData.getProperty("api.page.no").trim()),
                        (testData.getProperty("api.page.size").trim()) },
                { testData.getProperty("user.type.VAReseller").trim(), null, null,null,null,
                        testData.getProperty("api.full.VAResller.mobile").trim(), null, null, null, null,
                        (testData.getProperty("api.page.no").trim()), (testData.getProperty("api.page.size").trim()) },
                { testData.getProperty("user.type.VAReseller").trim(), null, null,null,null,
                        testData.getProperty("api.partial.varesellser.mobile").trim(), null, null, null, null,
                        (testData.getProperty("api.page.no").trim()),
                        (testData.getProperty("api.page.size").trim()) }
        };
        return obj;
    }

    @Test(priority = 11, retryAnalyzer = RetryAnalyzer.class, dataProvider = "userdatavalidemailandmobileinput", description = "Fectching values of User details with valid full and partail email/mobileNo")
    public void verifyGetSearchUserWithValidMailOrMobileNo(String[] getSearchUserInputData) throws IOException {
        getSearchUserApiResponse = userInfo.getSearchUserDetailsForGivenType(getSearchUserInputData[0],getSearchUserInputData[1],getSearchUserInputData[2],
                getSearchUserInputData[3],getSearchUserInputData[4],getSearchUserInputData[5],getSearchUserInputData[6],getSearchUserInputData[7],
                getSearchUserInputData[8],getSearchUserInputData[9],getSearchUserInputData[10],getSearchUserInputData[11]);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getSearchUserApiResponse);
        CCUtils.assertEquals(getSearchUserApiResponse.getStatusCode(), 200);
        validation.verifyUserOutputWithValidInput(getSearchUserApiResponse,getSearchUserInputData[0]);
    }
}
