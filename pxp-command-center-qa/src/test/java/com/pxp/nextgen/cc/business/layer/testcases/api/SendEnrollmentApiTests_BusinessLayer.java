//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

public class SendEnrollmentApiTests_BusinessLayer extends BaseRest {
    private UserInfo userInfo;
    Response sendEnrollmentResponse, getUsersResponse, getUserDetailsResponse;
    ArrayList<Integer> usersList=new ArrayList<Integer>();

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilder();
        userInfo = new UserInfo();
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-25731_user enters valid user ids in Payload for Send Enrollement")
    public void sendEnrollmentForUsersApiSuccess() throws Exception {
        logStep("TC 001_verify response code 200, when user enters valid user ids in Payload for Send Enrollement by using POST Method");
        logStep("Send Enrollment for Internal Users");
        sendEnrollmentforUserTypes("Internal");
        logStep("Send Enrollment for Client Users");
        sendEnrollmentforUserTypes("Client");
        logStep("Send Enrollment for VAR/Reseller Users");
        sendEnrollmentforUserTypes("VARReseller");
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-25733_PXPP-25734_ user enters valid and Invalid user ids in Payload for Send Enrollement")
    public void sendEnrollmentForValidAndInvalidUsersApiSuccess() throws IOException {
        logStep("PXPP-25733_TC 002_verify response code 200, when user enters one valid user id and one Non-existing user id in payload for Send Enrollement by using POST Method");
        getUsersResponse=userInfo.getUsersByTypeAndEnrollmentStatusBL("Internal", testData.getProperty("pxpp.26460.enrollment.status.not.sent"),"Active");
        usersList.clear();
        if (getUsersResponse.getStatusCode()==200) {
            usersList = getUsersResponse.jsonPath().get("data.userId");
        }
        else
            logStep("No data available to send enrollment for "+ "Internal");
        logStep("PXPP-25734_TC 003_verify response code 200, when user enters only non-existing user id/null Value as payload in payload for Send Enrollment by using POST Method");
        usersList.add(10000089);
        usersList.add(null);
        sendEnrollmentResponse = userInfo.sendEnrollmentBL("Valid", usersList);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(sendEnrollmentResponse);
        CCUtils.assertEquals(sendEnrollmentResponse.getStatusCode(), 200);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-25735_user enters empty Payload for Send Enrollement")
    public void sendEnrollmentForInvalidTypeUsersApiSuccess() throws IOException {
        logStep("TC 004_verify response code 400 and error message, when users enters invalid datatype/empty on payload values for Send Enrollment by using POST Method");
        usersList.clear();
        sendEnrollmentResponse = userInfo.sendEnrollmentBL("Valid", usersList);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(sendEnrollmentResponse);
        CCUtils.assertEquals(sendEnrollmentResponse.getStatusCode(), 400);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-25736_user enters Valid Payload and Invalid URI for Send Enrollement")
    public void sendEnrollmentForInvalidURIUsersApiSuccess() throws IOException {
        logStep("TC 005_verify response code 404 and error message, when users enters valid user ids in payload for Send Enrollment by using POST Method with invalid URI");
        usersList.clear();
        usersList.add(Integer.parseInt(testData.getProperty("pxpp.17575.cc.userid")));
        sendEnrollmentResponse = userInfo.sendEnrollmentBL("InValid", usersList);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(sendEnrollmentResponse);
        CCUtils.assertEquals(sendEnrollmentResponse.getStatusCode(), 404);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-25737_user enters Valid Payload and Invalid method for Send Enrollement")
    public void sendEnrollmentForInvalidMethodUsersApiSuccess() throws IOException {
        logStep("TC 006_verify response code 405 and error message, when user enters valid user ids in payload for Send Enrollment by using Delete Method");
        usersList.clear();
        usersList.add(Integer.parseInt(testData.getProperty("pxpp.17575.cc.userid")));
        sendEnrollmentResponse = userInfo.sendEnrollmentBL("InValidMethod", usersList);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(sendEnrollmentResponse);
        CCUtils.assertEquals(sendEnrollmentResponse.getStatusCode(), 405);
    }


    @Test(enabled = false)
    public void sendEnrollmentforUserTypes(String userType) throws Exception
    {
        getUsersResponse=userInfo.getUsersByTypeAndEnrollmentStatusBL(userType, testData.getProperty("pxpp.26460.enrollment.status.not.sent"),"Active");
        usersList.clear();
        if (getUsersResponse.getStatusCode()==200) {
            usersList = getUsersResponse.jsonPath().get("data.userId");
            sendEnrollmentResponse = userInfo.sendEnrollmentBL("Valid", usersList);
            Thread.sleep(5000); // this is added for Mail which will triggered to user E-mail. It may not updating fast in AWS compared to Local.
            logStep("Validating response information");
            CCUtils.responseTimeValidation(sendEnrollmentResponse);
            CCUtils.assertEquals(sendEnrollmentResponse.getStatusCode(), 200);
            for (int i = 0; i < usersList.size(); i++) {
                getUserDetailsResponse = userInfo.getUserDetails(usersList.get(i));
                if(getUserDetailsResponse.jsonPath().getInt("enrollmentSentCount")<5)
                CCUtils.assertEquals(getUserDetailsResponse.jsonPath().get("enrollmentStatus"), testData.getProperty("pxpp.26460.enrollment.status.pending"));
                else
                    logStep( usersList.get(i) +"User reached maximum enrollments sent");
            }
        }
        else
            logStep("No data available to send enrollment for "+ userType);
    }

}
