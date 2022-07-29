/*
JIRA Id: PXPP-17559
Objective: QA: API Automation : Command Center User - Search Users (Internal, client user, Var/Reseller user) [BL]
Functionality: Search Users (Internal, client user, Var/Reseller user) [BL]
Covered Response Codes: 200,400,404,204
Designer: Karthikeyan Natarajan
Phase: Command Center Phase 1
 */

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

//17559

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

    @Test(priority = 1,retryAnalyzer = RetryAnalyzer.class, dataProvider = "userTypeValue", description = "17675,17712,17713 Get Search valid users")
    public void getSearchValidUserApi(String userType) throws IOException {
        getSearchUserApiResponse = userInfo.getSearchUser(userType,"Valid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getSearchUserApiResponse);
        CCUtils.assertEquals(getSearchUserApiResponse.getStatusCode(), 200);
    }

    @DataProvider(name = "userType")
    public Object[][] getUserType() throws Exception {
        return new Object[][] {
                { testData.getProperty("user.type.internal").concat(CCUtils.generateAlphaNumericString(5)) },
                { testData.getProperty("api.existing.client.id") } };
    }

    @Test(priority = 2,retryAnalyzer = RetryAnalyzer.class, dataProvider = "userType", description = "17710 Enter non existing user type and verify response status code")
    public void testResponseCodeForNonExistingUserType(String userType) throws IOException {
        getSearchUserApiResponse = userInfo.getSearchUser(userType,"Valid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getSearchUserApiResponse);
        CCUtils.assertEquals(getSearchUserApiResponse.getStatusCode(), 400);
        validation.verifyResponseCodeForNonExistingUserType(getSearchUserApiResponse);
    }

    @Test(priority = 3,retryAnalyzer = RetryAnalyzer.class, description = "17688 Enter type as VAReseller and inputs on first_name, last_name, email of Client user")
    public void testResponseCodeForVAResellerTypeInternalInputs() throws IOException {
        getSearchUserApiResponse = userInfo.verifyResponseForValidUserIncorrectData(testData.getProperty("user.type.VAReseller").trim(),testData.getProperty("api.internal.firstName").trim(),
                testData.getProperty("api.internal.lastName").trim(),testData.getProperty("api.internal.email").trim(),
                testData.getProperty("api.internal.clientName").trim());
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getSearchUserApiResponse);
        CCUtils.assertEquals(getSearchUserApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 4,retryAnalyzer = RetryAnalyzer.class, description = " 17715 Enter type as Client and inputs on first_name, last_name, email of Internal user")
    public void testResponseCodeForClientTypeInternalInputs() throws IOException {
        getSearchUserApiResponse = userInfo.verifyResponseForValidUserIncorrectData(testData.getProperty("user.type.Client").trim(),testData.getProperty("api.internal.firstName").trim(),
                testData.getProperty("api.internal.lastName").trim(),testData.getProperty("api.internal.email").trim(),
                testData.getProperty("api.internal.clientName").trim());
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getSearchUserApiResponse);
        CCUtils.assertEquals(getSearchUserApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 5,retryAnalyzer = RetryAnalyzer.class, description = "17714 Enter type as Internal and inputs on first_name, last_name, email of Client user")
    public void testResponseCodeForInternalTypeClientInputs() throws IOException {
        getSearchUserApiResponse = userInfo.verifyResponseForValidUserIncorrectData(testData.getProperty("user.type.Internal").trim(),testData.getProperty("api.client.firstName").trim(),
                testData.getProperty("api.client.lastName").trim(),testData.getProperty("api.client.email").trim(),
                testData.getProperty("api.client.clientName").trim());
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getSearchUserApiResponse);
        CCUtils.assertEquals(getSearchUserApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 6,retryAnalyzer = RetryAnalyzer.class, description = "17711 Verify response code for valid type with non-existing input")
        public void testResponseCodeForValidTypeAndNonExistingInput() throws IOException{
            getSearchUserApiResponse = userInfo.verifyResponseForValidUserIncorrectData(testData.getProperty("user.type.Internal").trim(),testData.getProperty("api.invalid.clientUser.firstName").trim(),
                    testData.getProperty("api.invalid.clientUser.lastName").trim(),testData.getProperty("invalid.client.user.emailid").trim(),
                    testData.getProperty("api.invalid.client.name").trim());
            logStep("Validating response information");
            CCUtils.responseTimeValidation(getSearchUserApiResponse);
            CCUtils.assertEquals(getSearchUserApiResponse.getStatusCode(), 204);
        }

    @Test(priority = 7,retryAnalyzer = RetryAnalyzer.class, description = "17745 Verify response code for Empty type with existing input")
    public void testResponseCodeForEmptyTypeAndExistingInput() throws IOException{
        getSearchUserApiResponse = userInfo.verifyResponseForValidUserIncorrectData(" ",testData.getProperty("api.client.firstName").trim(),
                testData.getProperty("api.client.lastName").trim(),testData.getProperty("api.client.email").trim(),
                testData.getProperty("api.client.clientName").trim());
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getSearchUserApiResponse);
        CCUtils.assertEquals(getSearchUserApiResponse.getStatusCode(), 400);
        validation.verifyResponseForEmptyOrMisFormatData(getSearchUserApiResponse);
    }

    @Test(priority = 8,retryAnalyzer = RetryAnalyzer.class, description = "17689 Verify response code for Invalid URL")
    public void testResponseCodeForInvalidUrl()throws IOException{
        getSearchUserApiResponse = userInfo.getSearchUser(testData.getProperty("user.type.Internal").trim(),"Invalid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getSearchUserApiResponse);
        CCUtils.assertEquals(getSearchUserApiResponse.getStatusCode(), 404);
        validation.verifyErrorInResponseForInvalidUrl(getSearchUserApiResponse);
    }
}
