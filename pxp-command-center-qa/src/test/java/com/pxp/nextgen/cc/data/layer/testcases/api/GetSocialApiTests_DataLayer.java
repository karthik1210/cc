// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.CommonsInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetSocialApiTests_DataLayer extends BaseRest {
    private CommonsInfo commonsInfo;
    private Validations validation;
    Response getSocialApiResponse;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        commonsInfo = new CommonsInfo();
        validation = new Validations();
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "PXPP-18765_Get socials")
    public void getSocialsApiTestSuccess() throws IOException {
        logStep("TC 001_Verify the Response code as 200, when user try to get the socials  by using GET Request [DL]");
        getSocialApiResponse = commonsInfo.getSocialsResponse("Valid", "DL");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getSocialApiResponse);
        CCUtils.assertEquals(getSocialApiResponse.getStatusCode(), 200);
        logStep("Validating Socials");
        validation.verifyGetSocialDetails(getSocialApiResponse);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "PXPP-18820_Enter invalid url and validate error status code")
    public void getSocialsForInvalidUrlApiTest() throws IOException {
        logStep("TC 002_Verify the Response code as 404, when user try to give invalid URI for GET Call of the socials [DL]");
        getSocialApiResponse = commonsInfo.getSocialsResponse("InValid", "DL");
        logStep("Validating Response Information");
        CCUtils.responseTimeValidation(getSocialApiResponse);
        validation.verifyErrorInResponseForInvalidUrl(getSocialApiResponse);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19059_ Get Socials to check error if PUT is used instead of GET")
    public void getSocialsInvalidMethodApiTest() throws IOException {
        logStep("TC 003_Verify the Response code as 405, when user try PUT method instead of GET Request for socials [DL]");
        getSocialApiResponse = commonsInfo.getSocialsResponse("", "DL");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getSocialApiResponse);
        validation.verifyErrorMessageInResponseForInvalidMethod(getSocialApiResponse);
    }
}
