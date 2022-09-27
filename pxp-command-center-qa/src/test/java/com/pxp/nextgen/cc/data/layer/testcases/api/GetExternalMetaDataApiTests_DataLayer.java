// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.Product;
import com.pxp.cc.model.BaseRest;
import org.testng.annotations.Test;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.io.IOException;

public class GetExternalMetaDataApiTests_DataLayer extends BaseRest {
    private Product product;
    Response getExternalSystemApiResponse;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        product = new Product();
    }

    @DataProvider(name = "inValidAndNullExternalSystemId")
    public Object[][] nullAndStringexternalSystemId() throws Exception {
        return new Object[][]{{testData.getProperty("api.practice.interface.externalSystemId") + "Sample"}, {null}, {"0"}};
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19979_Get External SystemMetaData  for given externalSystemId")
    public void getExternalSystemMetaDataApi() throws IOException {
        logStep("TC 001_Verify the Response code as 200, when user try to get the externalsystems for Interface SetUp by using GET Request [DL]");
        int externalSystemId = Integer.parseInt(testData.getProperty("api.practice.interface.externalSystemId"));
        int externalSystemId1 = Integer.parseInt(testData.getProperty("api.practice.interface.externalSystemId82"));
        int externalSystemId2 = Integer.parseInt(testData.getProperty("api.practice.interface.externalSystemId84"));
        logStep("Getting InterfacesId information for a given externalSystemId");
        ArrayList<Integer> externalSystemIds = new ArrayList<Integer>();
        externalSystemIds.clear();
        externalSystemIds.add(externalSystemId);
        externalSystemIds.add(externalSystemId1);
        externalSystemIds.add(externalSystemId2);
        for (int i = 0; i < externalSystemIds.size(); i++) {
            int externalId = externalSystemIds.get(i);
            getExternalSystemApiResponse = product.getExternalSystemMetaDataResponse("Valid", "DL", externalId);
            logStep("Validating response information");
            CCUtils.responseTimeValidation(getExternalSystemApiResponse);
            CCUtils.assertEquals(getExternalSystemApiResponse.getStatusCode(), 200);
            if (externalSystemId == externalId)
                CCUtils.assertEquals(testData.getProperty("api.practice.interface.name"), getExternalSystemApiResponse.jsonPath().get("name"));
            else if (externalSystemId1 == externalId)
                CCUtils.assertEquals(testData.getProperty("api.practice.interface.name82"), getExternalSystemApiResponse.jsonPath().get("name"));
            else if (externalSystemId2 == externalId)
                CCUtils.assertEquals(testData.getProperty("api.practice.interface.name84"), getExternalSystemApiResponse.jsonPath().get("name"));
            else
                logStep("Validation failed");
        }
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19980_Get exteralnal meta data to given externalSystemId with Invalid URI Type")
    public void getExternalSystemMetaDataWithInvalidUriApi() throws IOException {
        logStep("TC 002_Verify the Response code as 404, when user try to give invalid URI for GET Call of the externalsystems for Interface SetUp [DL]");
        int externalSystemId = Integer.parseInt(testData.getProperty("api.practice.interface.externalSystemId"));
        logStep("Getting externalSystemMetaData information for a given externalSystemId");
        getExternalSystemApiResponse = product.getExternalSystemMetaDataResponse("InValid", "DL", externalSystemId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getExternalSystemApiResponse);
        CCUtils.assertEquals(getExternalSystemApiResponse.getStatusCode(), 404);
    }

    @Test(priority = 3, dataProvider = "inValidAndNullExternalSystemId", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19981_PXPP-23629_PXPP-23630_Get exteralnal meta data for null and Invalid Datatype externalSystemId")
    public void getExternalSystemMetaDataWithInvalidDatatypePracticeApi(String externalSystemId) throws IOException {
        logStep("TC 003_TC 005_TC 006_verify response code 400, when user enters null and Invalid Datatype value on externalSystemId by using GET Method [DL]");
        logStep("Getting externalSystemMetaData information for a given externalSystemId");
        getExternalSystemApiResponse = product.getExternalSystemMetaDataInvaliDatatypeResponse("DL", externalSystemId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getExternalSystemApiResponse);
        CCUtils.assertEquals(getExternalSystemApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19982_Get exteralnal meta data to given externalSystemId with Invalid Mathod Type")
    public void getExternalSystemMetaDataWithInvalidMethodApi() throws IOException {
        logStep("TC 004_Verify the Response code as 405, when user try to Patch method instead of GET Request for external systems of Interface SetUp [DL]");
        int externalSystemId = Integer.parseInt(testData.getProperty("api.practice.interface.externalSystemId"));
        logStep("Getting externalSystemMetaData information for a given externalSystemId");
        getExternalSystemApiResponse = product.getExternalSystemMetaDataResponse("InValidMethodType", "DL", externalSystemId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getExternalSystemApiResponse);
        CCUtils.assertEquals(getExternalSystemApiResponse.getStatusCode(), 405);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "Get exteralnal meta data to given externalSystemId as emptystring with Invalid URI Type")
    public void getExternalSystemMetaDataWithEmptyStringUriApi() throws IOException {
        logStep("Verify the Response code as 404, when user try to give empty string externalId for GET Call of the externalsystems for Interface SetUp [DL]");
        int externalSystemId = Integer.parseInt(testData.getProperty("api.practice.interface.externalSystemId"));
        logStep("Getting externalSystemMetaData information for a given externalSystemId");
        getExternalSystemApiResponse = product.getExternalSystemMetaDataInvaliDatatypeResponse("DL", "");
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getExternalSystemApiResponse);
        CCUtils.assertEquals(getExternalSystemApiResponse.getStatusCode(), 404);
    }
}
