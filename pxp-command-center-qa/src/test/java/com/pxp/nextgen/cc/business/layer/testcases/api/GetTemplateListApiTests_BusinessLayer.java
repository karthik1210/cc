// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

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

public class GetTemplateListApiTests_BusinessLayer extends BaseRest {
    private CommonsInfo commonsInfo;
    private Validations validation;
    Response getTemplateListApiResponse;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilder();
        commonsInfo = new CommonsInfo();
        validation = new Validations();
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-26790_Get templates list with valid product")
    public void getCommonTemplateListWithValidProductApiTestSuccess() throws IOException {
        logStep("TC 001_verify response code 200 and response body, when user enters valid product by using GET Request [BL/DL]");
        getTemplateListApiResponse = commonsInfo.getCommonTemplatesResponse_BL("Valid", testData.getProperty("template.product.name"), "Email");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getTemplateListApiResponse);
        CCUtils.assertEquals(getTemplateListApiResponse.getStatusCode(), 200);
        logStep("Validating Templates");
        validation.verifyGetTemplateListDetails(getTemplateListApiResponse);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-26791_Get templates list with invalid product")
    public void getCommonTemplateListWithInvalidProductApiTestSuccess() throws IOException {
        logStep("TC 002_verify response code 400 and error message, when user enters non-existing/invalid datatype/null on product by using GET Request [BL/DL]");
        getTemplateListApiResponse = commonsInfo.getCommonTemplatesResponse_BL("Valid", testData.getProperty("template.product.name").concat("Sample"), "Email");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getTemplateListApiResponse);
        CCUtils.assertEquals(getTemplateListApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-26794_Get templates list with invalid URI")
    public void getTemplatesForInvalidUrlApiTest() throws IOException {
        logStep("TC 003_verify response code 404 and error message, when user enters valid product by using GET Request with Invalid URI [BL/DL]");
        getTemplateListApiResponse = commonsInfo.getCommonTemplatesResponse_BL("InValid", testData.getProperty("template.product.name"), "Email");
        logStep("Validating Response Information");
        CCUtils.responseTimeValidation(getTemplateListApiResponse);
        validation.verifyErrorInResponseForInvalidUrl(getTemplateListApiResponse);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-26795_Get templates list with invalid method type instead of GET")
    public void getTemplatesWithInvalidMethodApiTest() throws IOException {
        logStep("TC 004_verify response code 405 and error message, when user enters valid product by using DELETE Request [BL/DL]");
        getTemplateListApiResponse = commonsInfo.getCommonTemplatesResponse_BL("InvalidValid", testData.getProperty("template.product.name"), "Email");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getTemplateListApiResponse);
        validation.verifyErrorMessageInResponseForInvalidMethod(getTemplateListApiResponse, "DELETE");
    }
}
