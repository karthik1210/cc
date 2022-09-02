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

import java.util.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AddTouApiTests_BusinessLayer extends BaseRest {
    private OrgInfo orgInfo;
    private Validations touValidation;
    Response addTouApiResponse;
    int practiceId;
    String namePayLoad;
    Map<String, Object> addTouContent = new HashMap<String, Object>();
    HashMap<String, Object> SolutionInformation = new HashMap<String, Object>();
    ArrayList<HashMap<String, Object>> TouSolutions = new ArrayList<HashMap<String, Object>>();

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilder();
        orgInfo = new OrgInfo();
        touValidation = new Validations();
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20036_Add tou for practice")
    public void addTouToPracticeSuccess() throws IOException {
        logStep("TC_01_Verify the Response code as 200, when user try to add Privacy settings by using POST Request [BL/DL]");
        String touName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.tou.name")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
        String contentEng = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.content.eng")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(50))));
        String contentSpan = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("PXPP.18282.api.content.span")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(50))));
        addTouContent.put("en", contentEng);
        addTouContent.put("es", contentSpan);
        SolutionInformation.put("solutionId", Integer.parseInt(testData.getProperty("PXPP.18282.api.sol.id")));
        SolutionInformation.put("active", true);
        SolutionInformation.put("name", testData.getProperty("PXPP.18282.api.sol.name"));
        TouSolutions.add(SolutionInformation);
        logStep("Add a new tou to practice");
        addTouApiResponse = orgInfo.addTou_BL(Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id")),
                touName, false, TouSolutions, addTouContent,
                testData.getProperty("PXPP.18282.api.tou.type"));
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(addTouApiResponse);
        CCUtils.assertEquals(addTouApiResponse.getStatusCode(), 200);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20038_Add tou for practice with Invalid URI")
    public void addTouToPracticeWithInvalidURI() throws IOException {
        logStep("TC_02_Verify the Response code as 404, when user try to give invalid URI for Practice of privacy settings by using POST Request[BL/DL]");
        addTouContent.put("en", testData.getProperty("PXPP.18282.api.content.eng"));
        addTouContent.put("es", testData.getProperty("PXPP.18282.api.content.span"));
        SolutionInformation.put("solutionId", Integer.parseInt(testData.getProperty("PXPP.18282.api.sol.id")));
        SolutionInformation.put("active", true);
        SolutionInformation.put("name", testData.getProperty("PXPP.18282.api.sol.name"));
        TouSolutions.add(SolutionInformation);
        logStep("Add a new tou to practice");
        addTouApiResponse = orgInfo.addTouInvalidURI_BL(Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id")),
                testData.getProperty("PXPP.18282.api.tou.name"), false, TouSolutions, addTouContent,
                testData.getProperty("PXPP.18282.api.tou.type"));
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(addTouApiResponse);
        CCUtils.assertEquals(addTouApiResponse.getStatusCode(), 404);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20046_Add tou for Alphanumeric practice")
    public void addTouToAlphanumericPractice() throws IOException {
        logStep("TC_03_Verify the Response code as 400, when user try to give alphanumeric practice Id for Practice of privacy settings by using POST Request [BL/DL]");
        String practiceId = CCUtils.generateAlphaNumericStringIncludingSpecialChar(8);
        addTouContent.put("en", testData.getProperty("PXPP.18282.api.content.eng"));
        addTouContent.put("es", testData.getProperty("PXPP.18282.api.content.span"));
        SolutionInformation.put("solutionId", Integer.parseInt(testData.getProperty("PXPP.18282.api.sol.id")));
        SolutionInformation.put("active", true);
        SolutionInformation.put("name", testData.getProperty("PXPP.18282.api.sol.name"));
        TouSolutions.add(SolutionInformation);
        logStep("Add a new tou to practice");
        addTouApiResponse = orgInfo.addTouAlphaNumericPracticeId_BL(practiceId, testData.getProperty("PXPP.18282.api.tou.name"), false, TouSolutions, addTouContent,
                testData.getProperty("PXPP.18282.api.tou.type"));
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(addTouApiResponse);
        touValidation.verifyResponseCodeForNonExistingUserType(addTouApiResponse);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20063_Add tou for Invalid Method Type")
    public void addTouToPracticeInvalidMethodType() throws IOException {
        logStep("TC_04_Verify the Response code as 405, when user try to call PUT method instead of POST Request for practice of privacy settings [BL/DL]");
        addTouContent.put("en", testData.getProperty("PXPP.18282.api.content.eng"));
        addTouContent.put("es", testData.getProperty("PXPP.18282.api.content.span"));
        SolutionInformation.put("solutionId", Integer.parseInt(testData.getProperty("PXPP.18282.api.sol.id")));
        SolutionInformation.put("active", true);
        SolutionInformation.put("name", testData.getProperty("PXPP.18282.api.sol.name"));
        TouSolutions.add(SolutionInformation);
        logStep("Add a new tou to practice");
        addTouApiResponse = orgInfo.addTouInvalidMethod_BL(Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id")), testData.getProperty("PXPP.18282.api.tou.name"), false, TouSolutions, addTouContent,
                testData.getProperty("PXPP.18282.api.tou.type"));
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(addTouApiResponse);
        CCUtils.assertEquals(addTouApiResponse.getStatusCode(), 405);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20056_Add tou for null Practice Id")
    public void addTouToNullPractice() throws IOException {
        logStep("TC_05_Verify the Response code as 400, when user try to give null practice Id for add the privacy settings by using POST Request [BL/DL]");
        addTouContent.put("en", testData.getProperty("PXPP.18282.api.content.eng"));
        addTouContent.put("es", testData.getProperty("PXPP.18282.api.content.span"));
        SolutionInformation.put("solutionId", Integer.parseInt(testData.getProperty("PXPP.18282.api.sol.id")));
        SolutionInformation.put("active", true);
        SolutionInformation.put("name", testData.getProperty("PXPP.18282.api.sol.name"));
        TouSolutions.add(SolutionInformation);
        logStep("Add a new tou to practice");
        addTouApiResponse = orgInfo.addTouToNullPractice_BL(testData.getProperty("PXPP.18282.api.tou.name"), false, TouSolutions, addTouContent,
                testData.getProperty("PXPP.18282.api.tou.type"));
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(addTouApiResponse);
        CCUtils.assertEquals(addTouApiResponse.getStatusCode(), 400);
        touValidation.verifyErrorMessageIfClientIdIsNull(addTouApiResponse);
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20060_Add tou for Invalid Practice Id")
    public void addTouToInvalidPractice() throws IOException {
        logStep("TC_06_Verify the Response code as 400, when user try to give invalid practice id of privacy settings POST Request [BL/DL]");
        addTouContent.put("en", testData.getProperty("PXPP.18282.api.content.eng"));
        addTouContent.put("es", testData.getProperty("PXPP.18282.api.content.span"));
        SolutionInformation.put("solutionId", Integer.parseInt(testData.getProperty("PXPP.18282.api.sol.id")));
        SolutionInformation.put("active", true);
        SolutionInformation.put("name", testData.getProperty("PXPP.18282.api.sol.name"));
        TouSolutions.add(SolutionInformation);
        logStep("Add a new tou to practice");
        Integer practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.invalid.practice.id"));
        addTouApiResponse = orgInfo.addTou_BL(practiceId,
                testData.getProperty("PXPP.18282.api.tou.name"), false, TouSolutions, addTouContent,
                testData.getProperty("PXPP.18282.api.tou.type"));
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(addTouApiResponse);
        touValidation.verifyErrorResponseBodyForNonExistingPracticeIdTou(addTouApiResponse, practiceId);
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20060_Add tou for Invalid payload")
    public void addTouToInvalidPayload() throws IOException {
        logStep("TC_07_Verify the Response code as 400, when user try to give Valid practice Id and invalid payload of privacy settings by using POST Request [BL/DL]");
        addTouContent.put("en", testData.getProperty("PXPP.18282.api.content.eng"));
        addTouContent.put("es", testData.getProperty("PXPP.18282.api.content.span"));
        SolutionInformation.put("solutionId", null);
        SolutionInformation.put("active", true);
        SolutionInformation.put("name", testData.getProperty("PXPP.18282.api.sol.name"));
        TouSolutions.add(SolutionInformation);
        logStep("Add a new tou to practice");
        Integer practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        addTouApiResponse = orgInfo.addTou_BL(practiceId,
                testData.getProperty("PXPP.18282.api.tou.name"), false, TouSolutions, addTouContent,
                testData.getProperty("PXPP.18282.api.tou.type"));
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(addTouApiResponse);
        touValidation.verifyErrorResponseBodyForInvalidSolTou(addTouApiResponse);
    }

}