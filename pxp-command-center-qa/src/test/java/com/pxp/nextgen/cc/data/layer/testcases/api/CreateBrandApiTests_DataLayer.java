// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
/*
JIRA Id: PXPP-17497 :API Automation for | Branding | Create Branding [DL]
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
import com.pxp.cc.util.*;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.*;
import io.restassured.response.Response;
import java.lang.*;
import java.io.*;


public class CreateBrandApiTests_DataLayer extends BaseRest {
    private BrandsInfo brandInfo;
    private ClientInfo clientInfo;
    private OrgInfo orgInfo;
    private Validations BrandingValidation;
    private String sql;
    Response CreateBrandApiResponse,createClientApiResponse,GetBrandApiResponse;
    int brand_id;
    String brandname;
    int org_id;
    String Namepayload;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        brandInfo = new BrandsInfo();
        clientInfo = new ClientInfo();
        BrandingValidation=new Validations();
    }
    //PXPP-17669 - Create Brand for Client
    @Test(priority =1 ,retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17669_Create Brand for Client")
    public void createBrandforClientSuccess() throws IOException  {
        logStep(" initiating....TC 001_Verify the Response code as 200, when user provided all requested fields with valid values for a client in payload for POST method of Branding [DL]");
        String clientName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.client.name")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8))));
        String clientSapNumber = CCUtils
                .getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.client.sapnumber")
                        .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20))));
        logStep("Create a new client");
        createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
                clientName, clientSapNumber, testData.getProperty("api.client.testclient"),
                testData.getProperty("api.client.type"), testData.getProperty("api.client.timezoneid"),
                Integer.parseInt(testData.getProperty("pxpp.cc.user.id")));
        int clientId = createClientApiResponse.jsonPath().getInt("clientId");
        logStep("New client created with client id : " + clientId);
        logStep("Create a brand for a client");
        Namepayload = "AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        CreateBrandApiResponse = brandInfo.postCreateBrand(clientId, Namepayload);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(CreateBrandApiResponse);
        CCUtils.assertEquals(CreateBrandApiResponse.getStatusCode(), 200);
    }

    //PXPP-17654_Create Brand with Payload of NullValues"
    @Test(priority =5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17654_Create Brand with Payload of NullV alues")
    public void orgidNullValidation() throws IOException,NullPointerException  {
        logStep("TC 002_Verify the Response code as 400, when user provided empty values in payload and null value to org_id,name for POST method of Branding [DL]");
        logStep("when org_id is null");
        Integer norg_id = null;
        Boolean showlogo=true;
        String Color="#EEEEEE";
        Namepayload="AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        CreateBrandApiResponse = brandInfo.errorPostCreateBrand(norg_id,Namepayload,showlogo,Color);
        logStep("Validating response information");
        BrandingValidation.verifyInvalidRequestValidation(CreateBrandApiResponse);
        showlogo=true;
        Color="#BBBBBB";
        Namepayload=null;
        CreateBrandApiResponse = brandInfo.errorPostCreateBrand(org_id,Namepayload,showlogo,Color);
        logStep("Validating response information");
        BrandingValidation.verifyInvalidRequestValidation(CreateBrandApiResponse);
    }
    //PXPP-17672_Create Brand with Payload of null (Show Logo) or duplicate values(name)
    @Test(priority =7, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17672_Create Brand with Payload of null (Show Logo) or duplicate values(name)")
    public void incorrectPayloadCreateBandValidation() throws IOException,NullPointerException  {
        logStep("TC 003_Verify the Response code as 400, when user provided null (Show Logo) or duplicate values(name) in payload for POST method of Branding [DL]");
        Integer org_id=Integer.parseInt(testData.getProperty("api.org.id"));
        String Namepayloaddb=testData.getProperty("api.name.db");
        Boolean showlogo=null;
        String Color="#BBBBBB";
        Namepayload="AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        CreateBrandApiResponse = brandInfo.errorPostCreateBrand(org_id,Namepayload,showlogo,Color);
        logStep("Validating response information");
        BrandingValidation.verifyInvalidRequestValidation(CreateBrandApiResponse);
        showlogo=true;
        CreateBrandApiResponse = brandInfo.errorPostCreateBrand(org_id,Namepayloaddb,showlogo,Color);
        logStep("Validating response information");
        BrandingValidation.verifyInvalidRequestValidation(CreateBrandApiResponse);

    }
    //PXPP-17690_Create Brand with incorrect URI
    @Test(priority =8, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17690_Create Brand with incorrect URI")
    public void invalidPostCreateBandValidation() throws IOException,NullPointerException  {
        logStep("TC 004_Verify the Response code as 404, when user provided incorrect URI for POST method of Branding [DL]");
        int org_id=Integer.parseInt(testData.getProperty("api.org.id"));
        Namepayload="AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        CreateBrandApiResponse = brandInfo.InvalidPostCreateBrand(org_id,Namepayload);
        logStep("Validating response information");
        BrandingValidation.verifynotfoundRequestRequestValidation(CreateBrandApiResponse);
    }

    //PXPP-17691_Create Brand for System"
    @Test(priority =2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17691_Create Brand for System")
    public void createBrandforSystemSuccess() throws IOException 
    {
        logStep("TC 005_Verify the Response code as 200, when user provided all requested fields with valid values for a system in payload for POST method of Branding [DL]");
        logStep("Getting Input system id from org table");
        int system_id=Integer.parseInt(testData.getProperty("api.system.org.id"));
        logStep("Create a brand for a system");
        Namepayload="AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        CreateBrandApiResponse = brandInfo.postCreateBrand(system_id,Namepayload );
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(CreateBrandApiResponse);
        CCUtils.assertEquals(CreateBrandApiResponse.getStatusCode(), 200);
    }
    //PXPP-17692_Create Brand for Practice"
    @Test(priority =4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17692_Create Brand for Practice")
    public void createBrandforPracticeSuccess() throws IOException 
    {
        logStep("TC 006_Verify the Response code as 200, when user provided all requested fields with valid values for an Practice in payload for POST method of Branding [DL]");
        logStep("Getting Input Practice id from org table");
        int Practice_id=Integer.parseInt(testData.getProperty("api.practice.org.id"));
        logStep("Create a brand for a Practice");
        Namepayload="AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        CreateBrandApiResponse = brandInfo.postCreateBrand(Practice_id,Namepayload );
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(CreateBrandApiResponse);
        CCUtils.assertEquals(CreateBrandApiResponse.getStatusCode(), 200);
    }
    //PXPP-17693_Create Brand for Enterprise"
    @Test(priority =3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17693_Create Brand for Enterprise")
    public void createBrandforEnterpriseSuccess() throws IOException 
    {
        logStep("TC 007_Verify the Response code as 200, when user provided all requested fields with valid values for an Enterprise in payload for POST method of Branding [DL]");
        logStep("Getting Input Enterprise id from org table");
        int Enterprise_id=Integer.parseInt(testData.getProperty("api.enterprise.org.id"));
        logStep("Create a brand for a Enterprise");
        Namepayload="AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        CreateBrandApiResponse = brandInfo.postCreateBrand(Enterprise_id,Namepayload );
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(CreateBrandApiResponse);
        CCUtils.assertEquals(CreateBrandApiResponse.getStatusCode(), 200);
    }

    //PXPP-17740_Create Brand with Payload of invalid orgId"
    @Test(priority =6, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17740_Create Brand with Payload of invalid orgId")
    public void incorrectOrgIdValidation() throws IOException,NullPointerException  {
        logStep("TC 008_Verify the Response code as 400, when user provided invalid orgid in payload for POST method of Branding [DL]");
        logStep("when org_id is incorrect");
       Integer org_id=Integer.parseInt(testData.getProperty("api.org.id"))+1000000;
         Boolean showlogo=true;
        String Color="#EEEEEE";
        Namepayload="AutoBrand".concat(CCUtils.generateAlphaNumericString(8));
        CreateBrandApiResponse = brandInfo.errorPostCreateBrand(org_id,Namepayload,showlogo,Color);
        logStep("Validating response information");
        BrandingValidation.verifyInvalidRequestValidation(CreateBrandApiResponse);
    }
    //PXPP-17966_Version Name length validation"
    @Test(priority =9, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-17966_Version Name length validation")
    public void nameFieldLengthValidation() throws IOException,NullPointerException  {
        logStep("TC 009_Verify the Response codes, when user provided Name attribute Values are =50 char and greater than 50 char payload for POST method of Branding [DL]");
        Integer org_id=Integer.parseInt(testData.getProperty("api.org.id"));
        Boolean showlogo=true;
        String Color="#EEEEEE";
        logStep("Version Name length validation with 50 charecters");
        Namepayload="AutoBrand".concat(CCUtils.generateAlphaNumericString(41));
        CreateBrandApiResponse = brandInfo.postCreateBrand(org_id,Namepayload );
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(CreateBrandApiResponse);
        CCUtils.assertEquals(CreateBrandApiResponse.getStatusCode(), 200);
        logStep("Version Name length validation with 51 charecters");
        Namepayload="AutoBrand".concat(CCUtils.generateAlphaNumericString(51));
        CreateBrandApiResponse = brandInfo.errorPostCreateBrand(org_id,Namepayload,showlogo,Color);
        logStep("Validating response information");
        BrandingValidation.verifyInvalidRequestValidation(CreateBrandApiResponse);
    }

}