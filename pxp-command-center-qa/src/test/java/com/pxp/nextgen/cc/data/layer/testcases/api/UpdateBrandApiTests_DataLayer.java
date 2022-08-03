// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.*;
import java.io.IOException;

public class UpdateBrandApiTests_DataLayer extends BaseRest {
    private BrandsInfo brandInfo;
    private Validations brandingValidation;
    private ClientInfo clientInfo;
    private OrgInfo orgInfo;
    Response updateBrandApiResponse;
    int brandId;
    int orgId;
    String namePayLoad;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        brandInfo = new BrandsInfo();
        clientInfo = new ClientInfo();
        brandingValidation = new Validations();
    }
    
    @Test(priority =1 ,retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17694_Update Brand for Client")
    public void updateBrandforClientSuccess() throws IOException {
        logStep(" initiating....TC 001_Verify the Response code as 200, when user provided all requested fields with valid values for a client in payload for PUT method of Branding [DL]");
        logStep("Fetching Brand details Test Data file");
        int brandId=Integer.parseInt(testData.getProperty("api.org.brand.id"));
        int orgId= Integer.parseInt(testData.getProperty("api.org.id"));
        namePayLoad = "AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        logStep("Sending Request for Name Update and getting Response");
        updateBrandApiResponse = brandInfo.putUpdateBrand(brandId, orgId, namePayLoad);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(updateBrandApiResponse);
        CCUtils.assertEquals(updateBrandApiResponse.getStatusCode(), 200);
        logStep("Sending Request for Show Logo and color Update and getting Response");
        Boolean showlogo=false;
        String color="#HHHHHH";
        updateBrandApiResponse = brandInfo.putUpdateAllBranddetails(brandId, orgId, namePayLoad, showlogo, color);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(updateBrandApiResponse);
        CCUtils.assertEquals(updateBrandApiResponse.getStatusCode(), 200);
    }

    @Test(priority =5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17695_Update Brand with Payload of NullValues")
    public void putorgIdNullValidation() throws IOException,NullPointerException {
        logStep("TC 002_Verify the Response code as 400, when user provided null values in payload for PUT method of Branding [DL]");
        logStep("when orgId is null");
        int brandId=1;
        Integer norgId = null;
        Boolean showlogo=true;
        String color="#EEEEEE";
        namePayLoad="AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        updateBrandApiResponse = brandInfo.errorPutUpateBrand(brandId, norgId, namePayLoad, showlogo, color);
        logStep("Validating response information");
        brandingValidation.verifyInvalidRequestValidation(updateBrandApiResponse);
        orgId=101;
        showlogo=true;
        color="#BBBBBB";
        namePayLoad=null;
        updateBrandApiResponse = brandInfo.errorPutUpateBrand(brandId, orgId, namePayLoad, showlogo, color);
        logStep("Validating response information");
        brandingValidation.verifyInvalidRequestValidation(updateBrandApiResponse);
    }

    @Test(priority =7, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17696_Update Brand with Payload of null (Show Logo)")
    public void incorrectPayloadUpdateBandValidation() throws IOException,NullPointerException {
        logStep("TC 003_Verify the Response code as 400, when user provided Null values to showlogo in payload for PUT method of Branding [DL]");
        Integer brandId=Integer.parseInt(testData.getProperty("api.org.brand.id"));
        Integer orgId= Integer.parseInt(testData.getProperty("api.org.id"));
        String namePayLoaddb=testData.getProperty("api.org.brand.name");
        Boolean showlogo=null;
        String color="#BBBBBB";
        updateBrandApiResponse = brandInfo.errorPutUpateBrand(brandId, orgId, namePayLoaddb, showlogo, color);
        logStep("Validating response information");
        brandingValidation.verifyInvalidRequestValidation(updateBrandApiResponse);
    }

    @Test(priority =8, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17697_Update Brand with incorrect URI")
    public void invalidURIUpateBandValidation() throws IOException,NullPointerException {
        logStep("TC 004_Verify the Response code as 404, when user provided incorrect URI for PUT method of Branding [DL]");
        Integer brandId=Integer.parseInt(testData.getProperty("api.org.brand.id"));
        Integer orgId= Integer.parseInt(testData.getProperty("api.org.id"));
        namePayLoad="AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        updateBrandApiResponse = brandInfo.InvalidPutUpateBrand(brandId, orgId, namePayLoad);
        logStep("Validating response information");
        brandingValidation.verifyErrorInResponseForInvalidUrl(updateBrandApiResponse);
    }

    @Test(priority =2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17727_Update Brand for System")
    public void updateBrandforSystemSuccess() throws IOException
    {
        logStep(" initiating....TC 005_Verify the Response code as 200, when user provided all requested fields with valid values for a System in payload for PUT method of Branding [DL]");
        logStep("Fetching Brand details from database");
        int brandId=Integer.parseInt(testData.getProperty("api.system.brand.id"));
        int orgId= Integer.parseInt(testData.getProperty("api.system.id"));
        namePayLoad = "AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        logStep("Sending Request for Name Update and getting Response");
        updateBrandApiResponse = brandInfo.putUpdateBrand(brandId, orgId, namePayLoad);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(updateBrandApiResponse);
        CCUtils.assertEquals(updateBrandApiResponse.getStatusCode(), 200);
        logStep("Sending Request for Show Logo and color Update and getting Response");
        Boolean showlogo=false;
        String color="#FFFFFF";
        updateBrandApiResponse = brandInfo.putUpdateAllBranddetails(brandId, orgId, namePayLoad, showlogo, color);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(updateBrandApiResponse);
        CCUtils.assertEquals(updateBrandApiResponse.getStatusCode(), 200);

    }

    @Test(priority =3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17728_Update Brand for Enterprise")
    public void updateBrandforEnterpriseSuccess() throws IOException
    {
        logStep("TC 006_Verify the Response code as 200, when user provided all requested fields with valid values for a Enterprise in payload for PUT method of Branding [DL]");
        logStep("Fetching Brand details from database");
        int brandId=Integer.parseInt(testData.getProperty("api.Enterprise.brand.id"));
        int orgId= Integer.parseInt(testData.getProperty("api.Enterprise.id"));
        namePayLoad = "AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        logStep("Sending Request for Name Update and getting Response");
        updateBrandApiResponse = brandInfo.putUpdateBrand(brandId, orgId, namePayLoad);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(updateBrandApiResponse);
        CCUtils.assertEquals(updateBrandApiResponse.getStatusCode(), 200);
        logStep("Sending Request for Show Logo and color Update and getting Response");
        Boolean showlogo=false;
        String color="#FFFFFF";
        updateBrandApiResponse = brandInfo.putUpdateAllBranddetails(brandId, orgId, namePayLoad, showlogo, color);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(updateBrandApiResponse);
        CCUtils.assertEquals(updateBrandApiResponse.getStatusCode(), 200);
    }

    @Test(priority =4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17729_Update Brand for Practice")
    public void updateBrandforPracticeSuccess() throws IOException
    {
        logStep("TC 007_Verify the Response code as 200, when user provided all requested fields with valid values for a Practice in payload for PUT method of Branding [DL]");
        logStep("Fetching Brand details from database");
        int brandId=Integer.parseInt(testData.getProperty("api.practice.brand.id"));
        int orgId= Integer.parseInt(testData.getProperty("api.practice.id"));
        namePayLoad = "AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        logStep("Sending Request for Name Update and getting Response");
        updateBrandApiResponse = brandInfo.putUpdateBrand(brandId, orgId, namePayLoad);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(updateBrandApiResponse);
        CCUtils.assertEquals(updateBrandApiResponse.getStatusCode(), 200);
        logStep("Sending Request for Show Logo and color Update and getting Response");
        Boolean showlogo=false;
        String color="#FFFFFF";
        updateBrandApiResponse = brandInfo.putUpdateAllBranddetails(brandId, orgId, namePayLoad, showlogo, color);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(updateBrandApiResponse);
        CCUtils.assertEquals(updateBrandApiResponse.getStatusCode(), 200);
        }

    @Test(priority =6, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17730_Update Brand with Payload of other orgId")
    public void otherorgIdupdatebrandValidation() throws IOException,NullPointerException {
        logStep("TC 008_Verify the Response code , when user trying to update the orgId which is associated with the brandId for a client in payload for PUT method of Branding [DL]");
        logStep("when other orgId is going to update for given brand id");
        Integer brandId=Integer.parseInt(testData.getProperty("api.org.brand.id"));
        Integer orgId= Integer.parseInt(testData.getProperty("api.practice.id"));
        Boolean showlogo=true;
        String color="#EEEEEE";
        namePayLoad="AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        updateBrandApiResponse = brandInfo.errorPutUpateBrand(brandId, orgId, namePayLoad, showlogo, color);
        logStep("Validating response information");
        brandingValidation.verifyInvalidRequestValidation(updateBrandApiResponse);
    }

    @Test(priority =9, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17965_Version Name length validation")
    public void nameFieldLengthUpdateValidation() throws IOException,NullPointerException {
        logStep("TC 009_Verify the Response codes, when user provided Name attribute Values are =50 char and greater than 50 char payload for PUT method of Branding [DL]");
        int brandId=Integer.parseInt(testData.getProperty("api.org.brand.id"));
        int orgId= Integer.parseInt(testData.getProperty("api.org.id"));
        Boolean showlogo=true;
        String color="#EEEEEE";
        logStep("Version Name length validation with 50 charecters");
        namePayLoad="AutoBrand".concat(CCUtils.generateAlphaNumericString(41));
        updateBrandApiResponse = brandInfo.putUpdateBrand(brandId, orgId, namePayLoad);;
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(updateBrandApiResponse);
        CCUtils.assertEquals(updateBrandApiResponse.getStatusCode(), 200);
        logStep("Version Name length validation with 51 charecters");
        namePayLoad="AutoBrand".concat(CCUtils.generateAlphaNumericString(51));
        updateBrandApiResponse = brandInfo.errorPutUpateBrand(brandId, orgId, namePayLoad, showlogo, color);
        logStep("Validating response information");
        brandingValidation.verifyInvalidRequestValidation(updateBrandApiResponse);
    }

}