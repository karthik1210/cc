// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.*;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import java.io.IOException;
import java.util.ArrayList;

public class CreateNewEmailTemplateVersionApiTest_DataLayer extends BaseRest {
    private OrgInfo orgInfo;
    private Validations validations;
    private ClientInfo clientInfo;
    private CreateNGHierachyClientCreation_DataLayer ngOrgPracticeObj;
    CreateTemplateVersion createTemplateVersionObj;
    CreateTemplateAttributes createTemplateAttributesObj;
    Response createTemplateVersionResponse;
    Integer templateId, practiceId;
    String versionName, templateType, emailSubject, emailBody;
    ArrayList<Integer> templateIdLIst = new ArrayList<Integer>();

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        orgInfo = new OrgInfo();
        clientInfo = new ClientInfo();
        validations = new Validations();
        ngOrgPracticeObj = new CreateNGHierachyClientCreation_DataLayer();
    }

    @DataProvider(name = "nullAndStringPracticeId")
    public Object[][] nullAndStringPracticeId() throws Exception {
        return new Object[][]{{testData.getProperty("PXPP.18282.api.practice.id") + "Sample"}, {null},
                {""}, {testData.getProperty("Non.existing.practice.id").trim()}
        };
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27388_ Create Email Version Template for Practice")
    public void createTemplateVersionForPracticeApiSuccess() throws IOException {
        logStep("TC 001_Verify the Response code as 200, when user try to add template-versions for a Email of Practice by using POST Request [BL/DL]");
        practiceId = createPractice();
        templateIdLIst.clear();
        templateIdLIst.add(Integer.parseInt(testData.getProperty("email.template.id1")));
        templateIdLIst.add(Integer.parseInt(testData.getProperty("email.template.id2")));
        templateIdLIst.add(Integer.parseInt(testData.getProperty("email.template.id3")));
        templateIdLIst.add(Integer.parseInt(testData.getProperty("email.template.id4")));
        templateIdLIst.add(Integer.parseInt(testData.getProperty("email.template.id5")));
        templateIdLIst.add(Integer.parseInt(testData.getProperty("email.template.id6")));
        templateIdLIst.add(Integer.parseInt(testData.getProperty("email.template.id7")));
        templateIdLIst.add(Integer.parseInt(testData.getProperty("email.template.id8")));
        templateType = testData.getProperty("template.type.email");
        for (int i = 0; i < templateIdLIst.size(); i++) {
            templateId = templateIdLIst.get(i);
            createTemplateVersionObj = createPayload(templateId, templateType);
            logStep("Create Email Template Version for practice : " + practiceId);
            createTemplateVersionResponse = orgInfo.createTemplateVersionResponse("Valid", "DL", practiceId, createTemplateVersionObj);
            logStep("Validating Response Information");
            CCUtils.assertEquals(createTemplateVersionResponse.getStatusCode(), 200);
        }
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27389_ Create duplicate version Template for Practice")
    public void createDuplicateTemplateVersionForPracticeApiSuccess() throws IOException {
        logStep("TC 002_Verify the Response code as 400, when user try to add same template-version name for a Email of Practice by using POST Request [BL/DL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        templateId = Integer.parseInt(testData.getProperty("email.template.id1"));
        versionName = "EmailTemplateVersionName" + CCUtils.generateNumericString(5);
        templateType = testData.getProperty("template.type.email");
        emailSubject = "Subject: " + CCUtils.generateAlphaNumericString(10);
        emailBody = "Body Regards: " + CCUtils.generateAlphaNumericString(20);
        createTemplateAttributesObj = new CreateTemplateAttributes(emailSubject, emailBody);
        createTemplateVersionObj = new CreateTemplateVersion(templateId, versionName, templateType, createTemplateAttributesObj);
        logStep("Create Email Template Version for practice : " + practiceId);
        createTemplateVersionResponse = orgInfo.createTemplateVersionResponse("Valid", "DL", practiceId, createTemplateVersionObj);
        logStep("Validating Response Information");
        CCUtils.assertEquals(createTemplateVersionResponse.getStatusCode(), 200);
        logStep("Create Duplicate Email Template Version for practice : " + practiceId);
        createTemplateVersionResponse = orgInfo.createTemplateVersionResponse("Valid", "DL", practiceId, createTemplateVersionObj);
        logStep("Validating Response Information");
        CCUtils.assertEquals(createTemplateVersionResponse.getStatusCode(), 400);
        CCUtils.assertEquals(createTemplateVersionResponse.jsonPath().get("message"), "Version name exists. Please create unique name.");
    }

    @Test(priority = 3, dataProvider = "nullAndStringPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27390_Create Email Template Version for Alphanumeric/Null/Empty practice")
    public void createTemplateVersionForAlphaNumericNullEmptyPractice(String practiceId) throws IOException {
        logStep("TC 003_Verify the Response code as 400, when user try to give Invalid practice Id for Email Template Versions by using POST Request [BL/DL]");
        templateId = Integer.parseInt(testData.getProperty("email.template.id1"));
        templateType = testData.getProperty("template.type.email");
        createTemplateVersionObj = createPayload(templateId, templateType);
        logStep("Create Email Template Version to practice with Invalid Datatype");
        createTemplateVersionResponse = orgInfo.createTemplateVersionInvalidDatatypeResponse("DL", practiceId, createTemplateVersionObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(createTemplateVersionResponse);
        CCUtils.assertEquals(createTemplateVersionResponse.getStatusCode(), 400);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24590_Create Email Template Version for Invalid Method Type")
    public void createTemplateVersionToPracticeInvalidMethodType() throws IOException {
        logStep("TC 004_Verify the Response code as 405, when user try to DELETE method instead of POST Request for Email Template [BL/DL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        templateId = Integer.parseInt(testData.getProperty("email.template.id1"));
        templateType = testData.getProperty("template.type.email");
        createTemplateVersionObj = createPayload(templateId, templateType);
        logStep("Create Email Template Version to practice with Invalid Datatype");
        createTemplateVersionResponse = orgInfo.createTemplateVersionResponse("InValidMethod", "DL", practiceId, createTemplateVersionObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(createTemplateVersionResponse);
        CCUtils.assertEquals(createTemplateVersionResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethod(createTemplateVersionResponse, "DELETE");
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27392_Create Email Template Version for practice with Invalid URI")
    public void createTemplateVersionToPracticeWithInvalidURI() throws IOException {
        logStep("TC 005_Verify the Response code as 404, when user try to give invalid URI for Practice Email Template versions by using POST Request [BL/DL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        templateId = Integer.parseInt(testData.getProperty("email.template.id1"));
        templateType = testData.getProperty("template.type.email");
        createTemplateVersionObj = createPayload(templateId, templateType);
        logStep("Create Email Template Version to practice with Invalid URI");
        createTemplateVersionResponse = orgInfo.createTemplateVersionResponse("InValid", "DL", practiceId, createTemplateVersionObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(createTemplateVersionResponse);
        CCUtils.assertEquals(createTemplateVersionResponse.getStatusCode(), 404);
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24593_Create Email Template Version for Invalid payload")
    public void createEmailTemplateVersionToInvalidPayload() throws IOException {
        logStep("TC 006_Verify the Response code as 400, when user try to give Valid practice Id and invalid payload for Practice Email Template versions by using POST Request [BL/DL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        logStep("Prepared Payload to Create Email Template Version details for practice : " + practiceId);
        versionName = "EmailTemplateVersionName" + CCUtils.generateNumericString(5);
        emailSubject = "Subject: " + CCUtils.generateAlphaNumericString(10);
        emailBody = "Body Regards: " + CCUtils.generateAlphaNumericString(20);
        templateId = null;
        templateType = testData.getProperty("template.type.email");
        createTemplateAttributesObj = new CreateTemplateAttributes(emailSubject, emailBody);
        createTemplateVersionObj = new CreateTemplateVersion(templateId, versionName, templateType, createTemplateAttributesObj);
        createTemplateVersionResponse = orgInfo.createTemplateVersionResponse("Valid", "DL", practiceId, createTemplateVersionObj);
        logStep("Validating Response Information");
        CCUtils.assertEquals(createTemplateVersionResponse.getStatusCode(), 400);
        versionName = null;
        emailSubject = "Subject: " + CCUtils.generateAlphaNumericString(10);
        emailBody = "Body Regards: " + CCUtils.generateAlphaNumericString(20);
        templateId = null;
        templateType = testData.getProperty("template.type.email");
        createTemplateAttributesObj = new CreateTemplateAttributes(emailSubject, emailBody);
        createTemplateVersionObj = new CreateTemplateVersion(templateId, versionName, templateType, createTemplateAttributesObj);
        createTemplateVersionResponse = orgInfo.createTemplateVersionResponse("Valid", "DL", practiceId, createTemplateVersionObj);
        logStep("Validating Response Information");
        CCUtils.assertEquals(createTemplateVersionResponse.getStatusCode(), 400);
        versionName = null;
        emailSubject = null;
        emailBody = null;
        templateId = null;
        templateType = null;
        createTemplateAttributesObj = new CreateTemplateAttributes(emailSubject, emailBody);
        createTemplateVersionObj = new CreateTemplateVersion(templateId, versionName, templateType, createTemplateAttributesObj);
        createTemplateVersionResponse = orgInfo.createTemplateVersionResponse("Valid", "DL", practiceId, createTemplateVersionObj);
        logStep("Validating Response Information");
        CCUtils.assertEquals(createTemplateVersionResponse.getStatusCode(), 400);
    }

    @Test(enabled = false)
    public CreateTemplateVersion createPayload(Integer templateId, String templateType) throws IOException {
        versionName = "EmailTemplateVersionName" + CCUtils.generateNumericString(5);
        emailSubject = "Subject: " + CCUtils.generateAlphaNumericString(10);
        emailBody = "Body Regards: " + CCUtils.generateAlphaNumericString(20);
        createTemplateAttributesObj = new CreateTemplateAttributes(emailSubject, emailBody);
        createTemplateVersionObj = new CreateTemplateVersion(templateId, versionName, templateType, createTemplateAttributesObj);
        return createTemplateVersionObj;
    }

    @Test(enabled = false)
    public int createPractice() throws IOException {
        String clientName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.client.name").concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5))));
        String clientSapNumber = CCUtils.getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.client.sapnumber").concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5))));
        String testFlag = testData.getProperty("api.client.testclient");
        String clientTimezoneId = testData.getProperty("api.client.timezoneid");
        String status = testData.getProperty("api.client.active");
        int clientType = Integer.parseInt(testData.getProperty("api.client.type"));
        String systemName = testData.getProperty("api.system.name");
        String orgType = testData.getProperty("api.system.orgtype");
        String parentType = testData.getProperty("api.parenttype");
        int ehrIdentifier = Integer.parseInt(testData.getProperty("ehr.identifier"));
        int pmIdentifier = Integer.parseInt(testData.getProperty("pm.identifier"));
        String enterpriseName = testData.getProperty("api.enterprise.name");
        String enterpriseType = testData.getProperty("api.enterprise.orgtype");
        String enterpriseParentType = testData.getProperty("api.enterprise.parenttype");
        String enterpriseTimeZone = testData.getProperty("api.enterprise.timezoneid");
        String practiceDisplayName = testData.getProperty("api.practice.displayname");
        String practiceName = testData.getProperty("api.practice.name");
        String practiceOrgType = testData.getProperty("api.practice.orgtype");
        String practiceParentType = testData.getProperty("api.practice.parenttype");
        String practiceTimeZone = testData.getProperty("api.practice.timezoneid");
        practiceId = ngOrgPracticeObj.createPractice(status, clientName, clientSapNumber, testFlag, clientType, clientTimezoneId, systemName, orgType, parentType,
                ehrIdentifier, pmIdentifier, enterpriseName, enterpriseType, enterpriseParentType, enterpriseTimeZone, practiceDisplayName, practiceName,
                practiceOrgType, practiceParentType, practiceTimeZone);
        return practiceId;
    }
}
