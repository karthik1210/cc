/*
JIRA Id: PXPP-17590
Objective: QA: API Automation to Save Practice Resource[DL]
Functionality: save the practices,resource providing practice_Id,resource_Id
Covered Response Codes: 200,400,404,405
Designer: PallaviB
Phase: Command Center Phase 1
 */

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

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in  field and verify response body")
    public void testCreatePracticesUsingValidDetails() throws IOException {
//        String en = testData.getProperty("practice.en").concat(CCUtils.generateStringWithSpace("vijay"));
//        String es = testData.getProperty("practice.es").concat(CCUtils.generateStringWithSpace("YYY"));
        createPracticeApiResponse = sharedInfo.savePracticeResourceWithValidInputs(Integer.parseInt(testData.getProperty("valid.practice.id")), Integer.parseInt(testData.getProperty("owner.id")), testData.getProperty("api.en"), testData.getProperty("api.es"));
        CCUtils.responseTimeValidation(createPracticeApiResponse);
        CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter IN valid details in  field and verify response body")
    public void testCreatePracticesUsingINValidDetails() throws IOException {
//        String en = testData.getProperty("practice.en").concat(CCUtils.generateStringWithSpace("vijay"));
//        String es = testData.getProperty("practice.es").concat(CCUtils.generateStringWithSpace("YYY"));
        createPracticeApiResponse = sharedInfo.savePracticeResourceWithValidInputs(Integer.parseInt(testData.getProperty("non.existing.practice.id")), Integer.parseInt(testData.getProperty("non.existing.resource.id")), testData.getProperty("api.en"), testData.getProperty("api.es"));
        CCUtils.responseTimeValidation(createPracticeApiResponse);
        CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 400);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter IN valid details in  field and verify response body")
    public void testCreatePracticesUsingINValidResource() throws IOException {
//        String en = testData.getProperty("practice.en").concat(CCUtils.generateStringWithSpace("vijay"));
//        String es = testData.getProperty("practice.es").concat(CCUtils.generateStringWithSpace("YYY"));
        createPracticeApiResponse = sharedInfo.savePracticeResourceWithValidInputs(Integer.parseInt(testData.getProperty("valid.practice.id")), Integer.parseInt(testData.getProperty("non.existing.resource.id")), testData.getProperty("api.en"), testData.getProperty("api.es"));
        CCUtils.responseTimeValidation(createPracticeApiResponse);
        CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 400);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter IN valid details in  field and verify response body")
    public void testCreatePracticesUsingINValidpractice() throws IOException {
//        String en = testData.getProperty("practice.en").concat(CCUtils.generateStringWithSpace("vijay"));
//        String es = testData.getProperty("practice.es").concat(CCUtils.generateStringWithSpace("YYY"));
        createPracticeApiResponse = sharedInfo.savePracticeResourceWithValidInputs(Integer.parseInt(testData.getProperty("non.existing.practice.id")), Integer.parseInt(testData.getProperty("valid.resource.id.one")), testData.getProperty("api.en"), testData.getProperty("api.es"));
        CCUtils.responseTimeValidation(createPracticeApiResponse);
        CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 400);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter IN valid details in  field and verify response body")
    public void testCreatePracticesUsingINValidMethodCallGET() throws IOException {
//        String en = testData.getProperty("practice.en").concat(CCUtils.generateStringWithSpace("vijay"));
//        String es = testData.getProperty("practice.es").concat(CCUtils.generateStringWithSpace("YYY"));
        createPracticeApiResponse = sharedInfo.savePracticeResourceWithInValidMethodcall(Integer.parseInt(testData.getProperty("valid.practice.id")), Integer.parseInt(testData.getProperty("valid.resource.id.one")), testData.getProperty("api.en"), testData.getProperty("api.es"));
        CCUtils.responseTimeValidation(createPracticeApiResponse);
        CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 405);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter IN valid details in  field and verify response body")
    public void testCreatePracticesUsingINVALID_DATATYPE() throws IOException {
//        String en = testData.getProperty("practice.en").concat(CCUtils.generateStringWithSpace("vijay"));
//        String es = testData.getProperty("practice.es").concat(CCUtils.generateStringWithSpace("YYY"));
        createPracticeApiResponse = sharedInfo.savePracticeResourceWithINValidDATATYPE (Integer.parseInt(testData.getProperty("valid.practice.id"))," ", testData.getProperty("api.en"), testData.getProperty("api.es"));

        CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 404);
    }
    }
