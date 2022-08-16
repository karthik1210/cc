// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class CreatePracticeResourceApiTests_DataLayer extends BaseRest {
    private SharedInfo sharedInfo;
    private Validations validation;
    Response createPracticeApiResponse;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        sharedInfo = new SharedInfo();
        validation = new Validations();
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in practice id and verify response body")
    public void testCreatePracticesUsingValidDetails() throws IOException {
        createPracticeApiResponse = sharedInfo.savePracticeResourceWithValidInputs(Integer.parseInt(testData.getProperty("valid.practice.id")), Integer.parseInt(testData.getProperty("owner.id")), testData.getProperty("api.en"), testData.getProperty("api.es"));
        log("Validating Response code for API");
        CCUtils.responseTimeValidation(createPracticeApiResponse);
        CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid details in practice id and verify response body")
    public void testCreatePracticesUsingINValidDetails() throws IOException {
        createPracticeApiResponse = sharedInfo.savePracticeResourceWithValidInputs(Integer.parseInt(testData.getProperty("non.existing.practice.id")), Integer.parseInt(testData.getProperty("non.existing.resource.id")), testData.getProperty("api.en"), testData.getProperty("api.es"));
        log("Validating Response code for API");
        log("Validating Response code for API");
        CCUtils.responseTimeValidation(createPracticeApiResponse);
        CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 400);
        validation.verifyInvalidRequestValidation(createPracticeApiResponse);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Invalid details in resource id and verify response body")
    public void testCreatePracticesUsingINValidResource() throws IOException {
        createPracticeApiResponse = sharedInfo.savePracticeResourceWithValidInputs(Integer.parseInt(testData.getProperty("valid.practice.id")), Integer.parseInt(testData.getProperty("non.existing.resource.id")), testData.getProperty("api.en"), testData.getProperty("api.es"));
        log("Validating Response code for API");
        CCUtils.responseTimeValidation(createPracticeApiResponse);
        CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 400);
        validation.verifyInvalidRequestValidation(createPracticeApiResponse);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter IN valid details in practice id  and verify response body")
    public void testCreatePracticesUsingINValidpractice() throws IOException {
        createPracticeApiResponse = sharedInfo.savePracticeResourceWithValidInputs(Integer.parseInt(testData.getProperty("non.existing.practice.id")), Integer.parseInt(testData.getProperty("valid.resource.id.one")), testData.getProperty("api.en"), testData.getProperty("api.es"));
        log("Validating Response code for API");
        CCUtils.responseTimeValidation(createPracticeApiResponse);
        CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 400);
        validation.verifyInvalidRequestValidation(createPracticeApiResponse);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid inputs on practice id and resource id with Invalid GET Request")
    public void testCreatePracticesUsingINValidMethodCallGET() throws IOException {
        createPracticeApiResponse = sharedInfo.savePracticeResourceWithInValidMethodcall(Integer.parseInt(testData.getProperty("valid.practice.id")), Integer.parseInt(testData.getProperty("valid.resource.id.one")), testData.getProperty("api.en"), testData.getProperty("api.es"));
        log("Validating Response code for API");
        CCUtils.responseTimeValidation(createPracticeApiResponse);
        CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 405);
        validation.verifyErrorMessageInResponseForInvalidMethodCall(createPracticeApiResponse);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Empty values on practice id and resource id and verify response body")
    public void testCreatePracticesUsingINVALID_DATATYPE() throws IOException {
        createPracticeApiResponse = sharedInfo.savePracticeResourceWithINValidDATATYPE(" ", " ", testData.getProperty("api.en"), testData.getProperty("api.es"));
        log("Validating Response code for API");
        CCUtils.responseTimeValidation(createPracticeApiResponse);
        CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 404);
        validation.verifyErrorInResponseForInvalidUrl(createPracticeApiResponse);
    }

}
