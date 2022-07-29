// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
/*
JIRA Id: PXPP-17480: QA: API Automation for | Branding | Update Branding for Client,System,Enterprise and Practice.
Designer: Sarada Burugula
 */
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
    private Validations BrandingValidation;
    private ClientInfo clientInfo;
    private OrgInfo orgInfo;
    Response UpdateBrandApiResponse;
    int brand_id;
    String brandname;
    int org_id;
    String Namepayload;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        brandInfo = new BrandsInfo();
        clientInfo = new ClientInfo();
        BrandingValidation = new Validations();
    }
    //PXPP-17694 - Update Brand for Client
    @Test(priority =1 ,retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17694_Update Brand for Client")
    public void updateBrandforClientSuccess() throws IOException {
        logStep(" initiating....TC 001_Verify the Response code as 200, when user provided all requested fields with valid values for a client in payload for PUT method of Branding [DL]");
        logStep("Fetching Brand details Test Data file");
        int brandid=Integer.parseInt(testData.getProperty("api.org.brand.id"));
        int orgid= Integer.parseInt(testData.getProperty("api.org.id"));
        Namepayload = "AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        logStep("Sending Request for Name Update and getting Response");
        UpdateBrandApiResponse = brandInfo.putUpdateBrand(brandid,orgid, Namepayload);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(UpdateBrandApiResponse);
        CCUtils.assertEquals(UpdateBrandApiResponse.getStatusCode(), 200);
        logStep("Sending Request for Show Logo and Color Update and getting Response");
        Boolean Showlogo=false;
        String Color="#HHHHHH";
        UpdateBrandApiResponse = brandInfo.putUpdateAllBranddetails(brandid,orgid, Namepayload,Showlogo,Color);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(UpdateBrandApiResponse);
        CCUtils.assertEquals(UpdateBrandApiResponse.getStatusCode(), 200);
    }

    //PXPP-17695_Update Brand with Payload of NullValues"
    @Test(priority =5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17695_Update Brand with Payload of NullValues")
    public void putOrgidNullValidation() throws IOException,NullPointerException {
        logStep("TC 002_Verify the Response code as 400, when user provided null values in payload for PUT method of Branding [DL]");
        logStep("when org_id is null");
        int brand_id=1;
        Integer norg_id = null;
        Boolean showlogo=true;
        String Color="#EEEEEE";
        Namepayload="AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        UpdateBrandApiResponse = brandInfo.errorPutUpateBrand(brand_id,norg_id,Namepayload,showlogo,Color);
        logStep("Validating response information");
        BrandingValidation.verifyInvalidRequestValidation(UpdateBrandApiResponse);
        org_id=101;
        showlogo=true;
        Color="#BBBBBB";
        Namepayload=null;
        UpdateBrandApiResponse = brandInfo.errorPutUpateBrand(brand_id,org_id,Namepayload,showlogo,Color);
        logStep("Validating response information");
        BrandingValidation.verifyInvalidRequestValidation(UpdateBrandApiResponse);
    }
    //PXPP-17696_Update Brand with Payload of null (Show Logo)
    @Test(priority =7, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17696_Update Brand with Payload of null (Show Logo)")
    public void incorrectPayloadUpdateBandValidation() throws IOException,NullPointerException {
        logStep("TC 003_Verify the Response code as 500, when user provided Null values to ShowLogo in payload for PUT method of Branding [DL]");
        Integer brand_id=Integer.parseInt(testData.getProperty("api.org.brand.id"));
        Integer org_id= Integer.parseInt(testData.getProperty("api.org.id"));
        String Namepayloaddb=testData.getProperty("api.org.brand.name");
        Boolean showlogo=null;
        String Color="#BBBBBB";
        UpdateBrandApiResponse = brandInfo.errorPutUpateBrand(brand_id,org_id,Namepayloaddb,showlogo,Color);
        logStep("Validating response information");
        BrandingValidation.verifyServerRequestRequestValidation(UpdateBrandApiResponse);
    }
    //PXPP-17697_Update Brand with incorrect URI
    @Test(priority =8, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17697_Update Brand with incorrect URI")
    public void invalidURIUpateBandValidation() throws IOException,NullPointerException {
        logStep("TC 004_Verify the Response code as 404, when user provided incorrect URI for PUT method of Branding [DL]");
        Integer brand_id=Integer.parseInt(testData.getProperty("api.org.brand.id"));
        Integer org_id= Integer.parseInt(testData.getProperty("api.org.id"));
        Namepayload="AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        UpdateBrandApiResponse = brandInfo.InvalidPutUpateBrand(brand_id,org_id,Namepayload);
        logStep("Validating response information");
        BrandingValidation.verifynotfoundRequestRequestValidation(UpdateBrandApiResponse);
    }

    //PXPP-17727_Update Brand for System"
    @Test(priority =2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17727_Update Brand for System")
    public void updateBrandforSystemSuccess() throws IOException
    {
        logStep(" initiating....TC 005_Verify the Response code as 200, when user provided all requested fields with valid values for a System in payload for PUT method of Branding [DL]");
        logStep("Fetching Brand details from database");
        int brandid=Integer.parseInt(testData.getProperty("api.system.brand.id"));
        int orgid= Integer.parseInt(testData.getProperty("api.system.id"));
        Namepayload = "AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        logStep("Sending Request for Name Update and getting Response");
        UpdateBrandApiResponse = brandInfo.putUpdateBrand(brandid,orgid, Namepayload);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(UpdateBrandApiResponse);
        CCUtils.assertEquals(UpdateBrandApiResponse.getStatusCode(), 200);
        logStep("Sending Request for Show Logo and Color Update and getting Response");
        Boolean Showlogo=false;
        String Color="#FFFFFF";
        UpdateBrandApiResponse = brandInfo.putUpdateAllBranddetails(brandid,orgid, Namepayload,Showlogo,Color);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(UpdateBrandApiResponse);
        CCUtils.assertEquals(UpdateBrandApiResponse.getStatusCode(), 200);

    }

    //PXPP-17728_Update Brand for Enterprise"
    @Test(priority =3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17728_Update Brand for Enterprise")
    public void updateBrandforEnterpriseSuccess() throws IOException
    {
        logStep("TC 006_Verify the Response code as 200, when user provided all requested fields with valid values for a Enterprise in payload for PUT method of Branding [DL]");
        logStep("Fetching Brand details from database");
        int brandid=Integer.parseInt(testData.getProperty("api.Enterprise.brand.id"));
        int orgid= Integer.parseInt(testData.getProperty("api.Enterprise.id"));
        Namepayload = "AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        logStep("Sending Request for Name Update and getting Response");
        UpdateBrandApiResponse = brandInfo.putUpdateBrand(brandid,orgid, Namepayload);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(UpdateBrandApiResponse);
        CCUtils.assertEquals(UpdateBrandApiResponse.getStatusCode(), 200);
        logStep("Sending Request for Show Logo and Color Update and getting Response");
        Boolean Showlogo=false;
        String Color="#FFFFFF";
        UpdateBrandApiResponse = brandInfo.putUpdateAllBranddetails(brandid,orgid, Namepayload,Showlogo,Color);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(UpdateBrandApiResponse);
        CCUtils.assertEquals(UpdateBrandApiResponse.getStatusCode(), 200);
    }
    //PXPP-17729_Update Brand for Practice"
    @Test(priority =4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17729_Update Brand for Practice")
    public void updateBrandforPracticeSuccess() throws IOException
    {
        logStep("TC 007_Verify the Response code as 200, when user provided all requested fields with valid values for a Practice in payload for PUT method of Branding [DL]");
        logStep("Fetching Brand details from database");
        int brandid=Integer.parseInt(testData.getProperty("api.practice.brand.id"));
        int orgid= Integer.parseInt(testData.getProperty("api.practice.id"));
        Namepayload = "AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        logStep("Sending Request for Name Update and getting Response");
        UpdateBrandApiResponse = brandInfo.putUpdateBrand(brandid,orgid, Namepayload);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(UpdateBrandApiResponse);
        CCUtils.assertEquals(UpdateBrandApiResponse.getStatusCode(), 200);
        logStep("Sending Request for Show Logo and Color Update and getting Response");
        Boolean Showlogo=false;
        String Color="#FFFFFF";
        UpdateBrandApiResponse = brandInfo.putUpdateAllBranddetails(brandid,orgid, Namepayload,Showlogo,Color);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(UpdateBrandApiResponse);
        CCUtils.assertEquals(UpdateBrandApiResponse.getStatusCode(), 200);
        }


    //PXPP-17730_Update Brand with Payload of other orgId"
    @Test(priority =6, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17730_Update Brand with Payload of other orgId")
    public void otherOrgIdupdatebrandValidation() throws IOException,NullPointerException {
        logStep("TC 008_Verify the Response code , when user trying to update the org_id which is associated with the brand_id for a client in payload for PUT method of Branding [DL]");
        logStep("when other org_id is going to update for given brand id");
        Integer brand_id=Integer.parseInt(testData.getProperty("api.org.brand.id"));
        Integer org_id= Integer.parseInt(testData.getProperty("api.practice.id"));
        Boolean showlogo=true;
        String Color="#EEEEEE";
        Namepayload="AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        UpdateBrandApiResponse = brandInfo.errorPutUpateBrand(brand_id,org_id,Namepayload,showlogo,Color);
        logStep("Validating response information");
        BrandingValidation.verifyInvalidRequestValidation(UpdateBrandApiResponse);
    }
    //PXPP-17965_Version Name length validation"
    @Test(priority =9, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17965_Version Name length validation")
    public void nameFieldLengthUpdateValidation() throws IOException,NullPointerException {
        logStep("TC 009_Verify the Response codes, when user provided Name attribute Values are =50 char and greater than 50 char payload for PUT method of Branding [DL]");
        int brandid=Integer.parseInt(testData.getProperty("api.org.brand.id"));
        int orgid= Integer.parseInt(testData.getProperty("api.org.id"));
        Boolean showlogo=true;
        String Color="#EEEEEE";
        logStep("Version Name length validation with 50 charecters");
        Namepayload="AutoBrand".concat(CCUtils.generateAlphaNumericString(41));
        UpdateBrandApiResponse = brandInfo.putUpdateBrand(brandid,orgid, Namepayload);;
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(UpdateBrandApiResponse);
        CCUtils.assertEquals(UpdateBrandApiResponse.getStatusCode(), 200);
        logStep("Version Name length validation with 51 charecters");
        Namepayload="AutoBrand".concat(CCUtils.generateAlphaNumericString(51));
        UpdateBrandApiResponse = brandInfo.errorPutUpateBrand(brandid,orgid,Namepayload,showlogo,Color);
        logStep("Validating response information");
        BrandingValidation.verifyInvalidRequestValidation(UpdateBrandApiResponse);
    }

}