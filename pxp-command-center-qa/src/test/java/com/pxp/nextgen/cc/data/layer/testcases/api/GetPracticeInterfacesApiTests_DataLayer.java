// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetPracticeInterfacesApiTests_DataLayer extends BaseRest {
    private OrgInfo orgInfo;
    private ClientInfo clientInfo;
    private Validations validations;
    Response getPracticeInterfacesApiResponse;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        orgInfo = new OrgInfo();
        clientInfo = new ClientInfo();
        validations = new Validations();
    }

    @DataProvider(name = "invalidPracticeId")
    public Object[][] invalidPracticeId() throws Exception {
        return new Object[][]{{testData.getProperty("Non.existing.practice.id").trim()},
                {testData.getProperty("api.client.id").trim()}, {testData.getProperty("api.system.id").trim()},
                {testData.getProperty("api.enterprise.id").trim()}};
    }

    @DataProvider(name = "nullAndStringPracticeId")
    public Object[][] nullAndStringPracticeId() throws Exception {
        return new Object[][]{{testData.getProperty("api.practice.interface.practiceid") + "Sample"}, {null}, {""}};
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19876_Get Interface details for given practiceId")
    public void getPracticeInterfacesApi() throws IOException {
        logStep("TC 001_Verify the Response code as 200, when user try to get the Practice interfaces for given practiceId by using GET Request [DL]");
        int practiceId = Integer.parseInt(testData.getProperty("api.practice.interface.practiceid"));
        logStep("Getting InterfacesId information for a given practiceId");
        getPracticeInterfacesApiResponse = orgInfo.getPracticeInterfacesResponse("Valid", "DL", practiceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPracticeInterfacesApiResponse);
        CCUtils.assertEquals(getPracticeInterfacesApiResponse.getStatusCode(), 200);
        List<String> interfaceIds = getPracticeInterfacesApiResponse.jsonPath().get("interfaceId");
        for (int i = 0; i < interfaceIds.size(); i++) {
            int actualExternalSystemId = getPracticeInterfacesApiResponse.jsonPath().get("externalSystemId[" + i + "]");
            int expectedExternalSystemId = Integer.parseInt(testData.getProperty("api.practice.interface.externalSystemId"));
            String actualName = getPracticeInterfacesApiResponse.jsonPath().get("name[" + i + "]");
            String expectedname = testData.getProperty("api.practice.interface.name");
            if (actualExternalSystemId == expectedExternalSystemId && actualName.equalsIgnoreCase(expectedname)) {
                logStep("Interface is available for " + expectedname + " as " + actualName);
                break;
            } else {
                logStep("Interface is available for " + expectedname + " as " + actualName);
            }
        }
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19877_Get Interfaces to given practiceId with Invalid URI Type")
    public void getPracticeInterfacesWithInvalidUriApi() throws IOException {
        logStep("TC 002_Verify the Response code as 404, when user try to give invalid URI for GET Call of the practice interfaces for Interface Setup [DL]");
        int practiceId = Integer.parseInt(testData.getProperty("api.practice.interface.practiceid"));
        logStep("Getting InterfacesId information for a given practiceId");
        getPracticeInterfacesApiResponse = orgInfo.getPracticeInterfacesResponse("InValid", "DL", practiceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPracticeInterfacesApiResponse);
        CCUtils.assertEquals(getPracticeInterfacesApiResponse.getStatusCode(), 404);
    }

    @Test(priority = 3, dataProvider = "nullAndStringPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19878_PXPP-19882_Get Practice Interfaces for null and Invalid Datatype PracticeId")
    public void getPracticeInterfacesWithInvalidDatatypePracticeApi(String practiceId) throws IOException {
        logStep("TC 003_TC 005_verify response code 400 and error message when user enters null and Invalid Datatype value on practice Id by using GET Method [DL]");
        logStep("Getting InterfacesId information for a given practiceId");
        getPracticeInterfacesApiResponse = orgInfo.getPracticeInterfacesInvaliDatatypeResponse("DL", practiceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPracticeInterfacesApiResponse);
        CCUtils.assertEquals(getPracticeInterfacesApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19879_Get Interfaces to given practiceId with Invalid Mathod Type")
    public void getPracticeInterfacesWithInvalidMethodApi() throws IOException {
        logStep("TC 004_Verify the Response code as 405, when user try to PATCH method instead of GET Request for practice interfaces of Interface Setup [DL]");
        int practiceId = Integer.parseInt(testData.getProperty("api.practice.interface.practiceid"));
        logStep("Getting InterfacesId information for a given practiceId");
        getPracticeInterfacesApiResponse = orgInfo.getPracticeInterfacesResponse("InValidMethodType", "DL", practiceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPracticeInterfacesApiResponse);
        CCUtils.assertEquals(getPracticeInterfacesApiResponse.getStatusCode(), 405);
    }

    @Test(priority = 5, dataProvider = "invalidPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19883_Get Practice Interfaces for Invalid PracticeId")
    public void getPracticeInterfacesWithInvalidPracticeApi(String practiceId) throws IOException {
        logStep("TC 006_Verify the Response code as 204, when user try to give invalid/ non existing practice id for interfaces by using GET Request [DL]");
        logStep("Getting InterfacesId information for a given practiceId");
        getPracticeInterfacesApiResponse = orgInfo.getPracticeInterfacesInvaliDatatypeResponse("DL", practiceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPracticeInterfacesApiResponse);
        CCUtils.assertEquals(getPracticeInterfacesApiResponse.getStatusCode(), 204);
    }

}
