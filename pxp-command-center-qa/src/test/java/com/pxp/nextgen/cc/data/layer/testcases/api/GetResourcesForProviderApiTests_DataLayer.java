/*
JIRA Id: PXPP-17610
Objective: QA: API Automation : Shared Data | [Provider] Get Resources for the Provider [DL]
Functionality: Get Resources for the Provider [DL]
Covered Response Codes: 200, 400, 404, 204, 500, 405
Designer: Karthikeyan Natarajan
Phase: Command Center Phase 1
 */

//Copyright 2022 NXGN Management, LLC. All Rights Reserved.
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

public class GetResourcesForProviderApiTests_DataLayer extends BaseRest {

    private SharedInfo sharedInfo;
    private Validations validation;
    Response getResourceForProviderApiResponse;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        sharedInfo = new SharedInfo();
        validation = new Validations();
    }

    @Test(priority = 1,retryAnalyzer = RetryAnalyzer.class, description = "Fetching resources for the provider with valid inputs and valid url")
    public void getResourcesForProviderWithValidInputs() throws IOException {
        getResourceForProviderApiResponse = sharedInfo.getResourcesForProviderWithValidInputs_DL(Integer.parseInt(testData.getProperty("valid.provider.id_NEW")), Integer.parseInt(testData.getProperty("valid.practice.id")), "valid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getResourceForProviderApiResponse);
        CCUtils.assertEquals(getResourceForProviderApiResponse.getStatusCode(), 200);
        validation.verifyResponseBodyForGetResourceForProvider_DL(getResourceForProviderApiResponse);
    }

    @Test(priority = 2,retryAnalyzer = RetryAnalyzer.class, description = "Fetching resources for provider with valid inputs and invalid Url")
    public void getResourcesForProviderWithValidInputsAndInvalidUrl() throws IOException {
        getResourceForProviderApiResponse = sharedInfo.getResourcesForProviderWithValidInputs_DL(Integer.parseInt(testData.getProperty("valid.provider.id_NEW")), Integer.parseInt(testData.getProperty("valid.practice.id")), "Invalid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getResourceForProviderApiResponse);
        CCUtils.assertEquals(getResourceForProviderApiResponse.getStatusCode(), 404);
        validation.verifyErrorInResponseForInvalidUrl(getResourceForProviderApiResponse);
    }

    @Test(priority = 3,retryAnalyzer = RetryAnalyzer.class, description = "Fetching resources for provider with Invalid POST Method")
    public void getResourceForProviderWithInvalidPOST() throws IOException {
        getResourceForProviderApiResponse = sharedInfo.getResourceForProviderWithInvalidPOSTRequest_DL(Integer.parseInt(testData.getProperty("valid.provider.id_NEW")), Integer.parseInt(testData.getProperty("valid.practice.id")), "Valid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getResourceForProviderApiResponse);
        CCUtils.assertEquals(getResourceForProviderApiResponse.getStatusCode(), 405);
        validation.verifyErrorMessageInResponseForInvalidPostMethodCall(getResourceForProviderApiResponse);
    }

    @Test(priority = 4,retryAnalyzer = RetryAnalyzer.class, description = "Fetching resources for provider with Null Inputs")
    public void getResourceForProviderWithNullInputs() throws IOException {
        getResourceForProviderApiResponse = sharedInfo.getResourcesForProviderWithValidInputs_DL(null, null, "Valid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getResourceForProviderApiResponse);
        CCUtils.assertEquals(getResourceForProviderApiResponse.getStatusCode(), 400);
        validation.verifyResponseForEmptyOrNULLOrMisFormatData_DL(getResourceForProviderApiResponse);
    }

    @Test(priority = 5,retryAnalyzer = RetryAnalyzer.class, description = "Fetching resources for provider with Empty Inputs")
    public void getResourceForProviderWithEmptyInputs() throws IOException {
        getResourceForProviderApiResponse = sharedInfo.getResourcesForProviderWithValidInputs_DL(" ", " ", "valid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getResourceForProviderApiResponse);
        CCUtils.assertEquals(getResourceForProviderApiResponse.getStatusCode(), 500);
        validation.verifyResponseForEmptyData_DL(getResourceForProviderApiResponse);
    }

    @Test(priority = 6,retryAnalyzer = RetryAnalyzer.class, description = "Fetching resources for the provider with Existing Provider Id and Non-Existing practice Id along with valid url")
    public void getResourcesForProviderWithNonExistingProviderIdAndExistingPracticeId() throws IOException {
        getResourceForProviderApiResponse = sharedInfo.getResourcesForProviderWithValidInputs_DL(Integer.parseInt(testData.getProperty("non.existing.provider.id")), Integer.parseInt(testData.getProperty("valid.practice.id")), "valid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getResourceForProviderApiResponse);
        CCUtils.assertEquals(getResourceForProviderApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 7,retryAnalyzer = RetryAnalyzer.class, description = "Fetching resources for the provider with Non Existing Provider Id and Existing practice Id along with valid url")
    public void getResourcesForProviderWithExistingProviderIdAndNonExistingPracticeId()throws IOException{
        getResourceForProviderApiResponse = sharedInfo.getResourcesForProviderWithValidInputs_DL(Integer.parseInt(testData.getProperty("valid.provider.id_NEW")), Integer.parseInt(testData.getProperty("non.existing.practice.id")), "valid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getResourceForProviderApiResponse);
        CCUtils.assertEquals(getResourceForProviderApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 8,retryAnalyzer = RetryAnalyzer.class, description = "Fetching resources for the provider with Non Existing Provider Id and Non Existing practice Id along with valid url")
    public void getResourcesForProviderWithNonExistingProviderIdAndNonExistingPracticeId()throws IOException{
        getResourceForProviderApiResponse = sharedInfo.getResourcesForProviderWithValidInputs_DL(Integer.parseInt(testData.getProperty("non.existing.provider.id")), Integer.parseInt(testData.getProperty("non.existing.practice.id")), "valid");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getResourceForProviderApiResponse);
        CCUtils.assertEquals(getResourceForProviderApiResponse.getStatusCode(), 204);
    }


}
