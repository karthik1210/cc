// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetPracticeWebsitesApiTests_BusinessLayer extends BaseRest {
    private OrgInfo orgInfo;
    private Validations validations;
    Response getPracticeWebsitesApiResponse;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilder();
        orgInfo = new OrgInfo();
        validations = new Validations();
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Get practice websites with valid practice id which has websites")
    public void getPracticeWebsiteWithValidPracticeIdHasWebsites() throws IOException {
        getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasWebsites_BL(Integer.parseInt(testData.getProperty("api.valid.practice.id.websites")));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPracticeWebsitesApiResponse);
        CCUtils.assertEquals(getPracticeWebsitesApiResponse.getStatusCode(), 200);
        validations.verifyOutputFromValidInputForPracticeWebsites(getPracticeWebsitesApiResponse);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Get practice websites with valid practice id which has not websites")
    public void getPracticeWebsiteWithValidPracticeIdHasNotWebsites() throws IOException {
        getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdHasNotWebsites_BL(Integer.parseInt(testData.getProperty("api.notactive.practice.id.websites")));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPracticeWebsitesApiResponse);
        CCUtils.assertEquals(getPracticeWebsitesApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Get practice websites with Non existing practice id")
    public void getPracticeWebsiteWithNonExistingPracticeId() throws IOException {
        getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithNonExistingPracticeId_BL(Integer.parseInt(testData.getProperty("Non.existing.practice.id")));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPracticeWebsitesApiResponse);
        CCUtils.assertEquals(getPracticeWebsitesApiResponse.getStatusCode(), 204);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Get practice websites with invalid datatype on practice id")
    public void getPracticeWebsiteWithInvalidDataTypePracticeId() throws IOException {
        getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithNullOrInvalidDataTypeOnPracticeId_BL(testData.getProperty("api.invalidDatatype.practice.id"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPracticeWebsitesApiResponse);
        CCUtils.assertEquals(getPracticeWebsitesApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(getPracticeWebsitesApiResponse);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "Get practice websites with Null on practice id")
    public void getPracticeWebsiteWithNullPracticeId() throws IOException {
        getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithNullOrInvalidDataTypeOnPracticeId_BL(null);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPracticeWebsitesApiResponse);
        CCUtils.assertEquals(getPracticeWebsitesApiResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(getPracticeWebsitesApiResponse);
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "Get practice websites with Empty on practice id")
    public void getPracticeWebsiteWithEmptyPracticeId() throws IOException {
        getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithEmptyOnPracticeId_BL();
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPracticeWebsitesApiResponse);
        CCUtils.assertEquals(getPracticeWebsitesApiResponse.getStatusCode(), 404);
        validations.verifyErrorInResponseForInvalidUrl(getPracticeWebsitesApiResponse);
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "Get practice websites with valid practice id which has websites with invalid URI")
    public void getPracticeWebsiteWithValidPracticeIdHasWebsitesWithInvalidURI() throws IOException {
        getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdWithInvalidURI_BL(Integer.parseInt(testData.getProperty("api.valid.practice.id.websites")));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPracticeWebsitesApiResponse);
        CCUtils.assertEquals(getPracticeWebsitesApiResponse.getStatusCode(), 404);
        validations.verifyErrorInResponseForInvalidUrl(getPracticeWebsitesApiResponse);
    }

    @Test(priority = 8, retryAnalyzer = RetryAnalyzer.class, description = "Get practice websites with valid practice id which has websites with invalid Request")
    public void getPracticeWebsiteWithValidPracticeIdHasWebsitesWithInvalidRequest() throws IOException {
        getPracticeWebsitesApiResponse = orgInfo.getPracticeWebsitesWithValidPracticeIdWithInvalidRequest_BL(Integer.parseInt(testData.getProperty("api.valid.practice.id.websites")));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getPracticeWebsitesApiResponse);
        CCUtils.assertEquals(getPracticeWebsitesApiResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethodCall(getPracticeWebsitesApiResponse);
    }
}
