// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.CommonsInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetStatesApiTests_BusinessLayer extends BaseRest {
    private CommonsInfo commonsInfo;
    private Validations validation;
    Response getStatesApiResponse;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilder();
        commonsInfo = new CommonsInfo();
        validation = new Validations();
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24346_Get states")
    public void getStatesApiTestSuccess() throws IOException {
        logStep("TC 001_Verify the Response code as 200, when user try to get the states by using GET Request [BL/DL]");
        getStatesApiResponse = commonsInfo.getStatesResponse("Valid", "BL");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getStatesApiResponse);
        CCUtils.assertEquals(getStatesApiResponse.getStatusCode(), 200);
        logStep("Validating states");
        validation.verifyGetStatesDetails(getStatesApiResponse);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24347_Enter invalid url and validate error status code")
    public void getStatesForInvalidUrlApiTest() throws IOException {
        logStep("TC 002_Verify the Response code as 404, when user try to give invalid URI for GET Call of the states [BL/DL]");
        getStatesApiResponse = commonsInfo.getStatesResponse("InValid", "BL");
        logStep("Validating Response Information");
        CCUtils.responseTimeValidation(getStatesApiResponse);
        validation.verifyErrorInResponseForInvalidUrl(getStatesApiResponse);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24348_ Get states to check error if PATCH is used instead of GET")
    public void getStatesInvalidMethodApiTest() throws IOException {
        logStep("TC 003_Verify the Response code as 405, when user try PATCH method instead of GET Request for states [BL/DL]");
        getStatesApiResponse = commonsInfo.getStatesResponse("InvalidMethod", "BL");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getStatesApiResponse);
        validation.verifyErrorMessageInResponseForInvalidMethod(getStatesApiResponse,"PATCH");
    }
}
