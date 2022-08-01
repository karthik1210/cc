/*
JIRA Id: PXPP-17580
Objective: QA: API Automation for Get Practices associated to Location (DL)
Functionality: Get the practices associated to location by providing location_Id
Covered Response Codes: 200,204,404,405
Designer: PallaviB
Phase: Command Center Phase 1
 */

// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import com.pxp.cc.helpers.*;



public class GetPracticestoLocation_DataLayer extends BaseRest  {

    private SharedInfo sharedInfo;
    private Validations validations;
    Response getPracticeForLocationApi;
    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        sharedInfo = new SharedInfo();
        validations = new Validations();

    }

    @DataProvider(name = "locationId"  )
    public Object[][] getLocationId() throws Exception{
        return new Object[][]{ {4140},{4000} };
    }


    @Test(dataProvider = "locationId",priority =1 ,retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17718_Get a practice by providing valid location id for single & multiple practices")
    public void getPracticeForLocationWithValidURLAndValidInput(Integer locationId)throws IOException {
        logStep(" initiating....Verify the Response code as 200, when user provided valid locationid");
        getPracticeForLocationApi = sharedInfo.getPracticeForLocationWithExistingOrNonExistingInput(locationId);
        CCUtils.assertEquals(getPracticeForLocationApi.getStatusCode(),200);
    }

    @Test (priority =2 ,retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17756_Get a practice bu providing Invalid URL")
    public void getPracticeForLocationWithValidURLAndInvalidInput()throws IOException{
        logStep(" initiating...Verify the Response code as 404, when user provides Invalid URL");
        getPracticeForLocationApi = sharedInfo.getPracticeForLocationWithInvalidInput(4140);
        CCUtils.assertEquals(getPracticeForLocationApi.getStatusCode(),404);
        validations.verifyErrorInResponseForInvalidUrl(getPracticeForLocationApi);

    }
    @Test (priority =3 ,retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17722_Get a practice bu providing Invalid locationId")
    public void getPracticeForLocationWithIncorrectInput()throws IOException{
        logStep(" initiating...Verify the Response code as 204, when user provides Invalid locationid");
        getPracticeForLocationApi = sharedInfo.getPracticeForLocationWithExistingOrNonExistingInput(4018);
        CCUtils.assertEquals(getPracticeForLocationApi.getStatusCode(),204);
    }
    @Test (priority =4 ,retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17723_Make a POST call Instead of GET by providing valid locationId")
    public  void getPracticeForLocationWithInvalidPost()throws IOException{
        logStep(" initiating...Verify the Response code as 405, when user makes Incorrect CALL");
        getPracticeForLocationApi = sharedInfo.getPracticeForLocationWithValidInputForPost((4140));
        CCUtils.assertEquals(getPracticeForLocationApi.getStatusCode(),405);
        validations.verifyErrorMessageInResponseForInvalidPostMethodCall(getPracticeForLocationApi);
    }


}



