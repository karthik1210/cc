// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.CreateTemplateAttributes;
import com.pxp.cc.pojos.CreateTemplateVersion;
import com.pxp.cc.pojos.UpdateTemplateVersion;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import com.pxp.nextgen.cc.business.layer.testcases.api.CreateNGHierachyClientCreation_BusinessLayer;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class UpdateEmailTemplateApiTests_DataLayer extends BaseRest {
    private OrgInfo orgInfo;
    private Validations validations;
    private ClientInfo clientInfo;
    private CreateNGHierachyClientCreation_DataLayer ngOrgPracticeObj;
    CreateTemplateVersion createTemplateVersionObj, createTemplateVersionObj1;
    CreateTemplateAttributes createTemplateAttributesObj;
    UpdateTemplateVersion updateTemplateVersionObj;
    Response createTemplateVersionResponse, getTemplateVersionResponse, updateTemplateVersionResponse;
    Integer templateId, practiceId;
    Long templateVersionId, expectedVersionId;
    String versionName, templateType, emailSubject, emailBody, actualTemplatename;
    ArrayList<Integer> getTemplateIds = new ArrayList<Integer>();
    HashMap<String, Object> templateVersionMap = new HashMap<String, Object>();

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
        return new Object[][]{{testData.getProperty("PXPP.18282.api.practice.id") + "Sample", testData.getProperty("PXPP.18282.api.practice.id") + "Sample"},
                {null, null},
                {"", ""}, {testData.getProperty("Non.existing.practice.id").trim(), testData.getProperty("Non.existing.practice.id").trim()}
        };
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27406_ Update Email Version Template for Practice")
    public void updateTemplateVersionForPracticeApiSuccess() throws Exception {
        logStep("TC 001_Verify the Response code as 200, when user try to update template-versions for a Email of Practice by using PUT Request [BL/DL]");
        practiceId = createPractice();
        templateVersionMap = getTemplateDetails(practiceId);
        templateVersionId = Long.parseLong(templateVersionMap.get("versionId").toString());
        templateId = Integer.parseInt(templateVersionMap.get("templateId").toString());
        logStep("Create Payload for Update Email Template Version");
        versionName = templateVersionMap.get("name").toString().concat("Update");
        emailSubject = "Subject: " + CCUtils.generateAlphaNumericString(10);
        emailBody = "Body Regards: " + CCUtils.generateAlphaNumericString(20);
        createTemplateAttributesObj = new CreateTemplateAttributes(emailSubject, emailBody);
        updateTemplateVersionObj = new UpdateTemplateVersion(versionName, createTemplateAttributesObj);
        logStep("Update the Email Template Version for practice : " + practiceId);
        updateTemplateVersionResponse = orgInfo.updateTemplateVersionResponse("Valid", "DL", practiceId, templateVersionId, updateTemplateVersionObj);
        logStep("Validating Response Information");
        CCUtils.assertEquals(updateTemplateVersionResponse.getStatusCode(), 200);
        getTemplateVersionResponse = orgInfo.getTemplateVersionResponse("Valid", "DL", practiceId, templateId);
        CCUtils.assertEquals(getTemplateVersionResponse.getStatusCode(), 200);
        getTemplateIds = getTemplateVersionResponse.jsonPath().get("versionId");
        for (int i = 0; i < getTemplateIds.size(); i++) {
            String actualTemplatename = getTemplateVersionResponse.jsonPath().get("name[" + i + "]");
            if (versionName.equalsIgnoreCase(actualTemplatename)) {
                log("Version Name is available: " + versionName);
                expectedVersionId = Long.parseLong(getTemplateVersionResponse.jsonPath().get("versionId[" + i + "]").toString());
                log("Version Id is available: " + expectedVersionId);
                break;
            } else
                log("Version Name is not available: " + versionName);
        }
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27401_ Update duplicate version Template for Practice")
    public void updateDuplicateTemplateVersionForPracticeApiSuccess() throws IOException {
        logStep("TC 002_Verify the Response code as 400, when user try to add same template-version name for a Email of Practice by using PUT Request [BL/DL]");
        practiceId = createPractice();
        templateId = Integer.parseInt(testData.getProperty("email.template.id1"));
        versionName = "EmailTemplateVersionName" + CCUtils.generateNumericString(5);
        String versionName1 = "EmailTemplateVersionName" + CCUtils.generateNumericString(5);
        templateType = testData.getProperty("template.type.email");
        emailSubject = "Subject: " + CCUtils.generateAlphaNumericString(10);
        emailBody = "Body Regards: " + CCUtils.generateAlphaNumericString(20);
        createTemplateAttributesObj = new CreateTemplateAttributes(emailSubject, emailBody);
        createTemplateVersionObj = new CreateTemplateVersion(templateId, versionName, templateType, createTemplateAttributesObj);
        logStep("Create Email Template Version 1 for practice : " + practiceId);
        createTemplateVersionResponse = orgInfo.createTemplateVersionResponse("Valid", "DL", practiceId, createTemplateVersionObj);
        CCUtils.assertEquals(createTemplateVersionResponse.getStatusCode(), 200);
        createTemplateVersionObj1 = new CreateTemplateVersion(templateId, versionName1, templateType, createTemplateAttributesObj);
        logStep("Create Email Template Version 2 for practice : " + practiceId);
        createTemplateVersionResponse = orgInfo.createTemplateVersionResponse("Valid", "DL", practiceId, createTemplateVersionObj1);
        getTemplateVersionResponse = orgInfo.getTemplateVersionResponse("Valid", "DL", practiceId, templateId);
        CCUtils.assertEquals(getTemplateVersionResponse.getStatusCode(), 200);
        getTemplateIds = getTemplateVersionResponse.jsonPath().get("versionId");
        for (int i = 0; i < getTemplateIds.size(); i++) {
            String actualTemplatename = getTemplateVersionResponse.jsonPath().get("name[" + i + "]");
            if (versionName.equalsIgnoreCase(actualTemplatename)) {
                log("Version Name is available: " + versionName);
                expectedVersionId = Long.parseLong(getTemplateVersionResponse.jsonPath().get("versionId[" + i + "]").toString());
                log("Version Id is available: " + expectedVersionId);
                break;
            } else
                log("Version Name is not available: " + versionName);
        }
        emailSubject = "Subject: " + CCUtils.generateAlphaNumericString(10);
        emailBody = "Body Regards: " + CCUtils.generateAlphaNumericString(20);
        createTemplateAttributesObj = new CreateTemplateAttributes(emailSubject, emailBody);
        updateTemplateVersionObj = new UpdateTemplateVersion(versionName1, createTemplateAttributesObj);
        logStep("Update the Email Template Version 1 with Version 2 for practice : " + practiceId);
        updateTemplateVersionResponse = orgInfo.updateTemplateVersionResponse("Valid", "DL", practiceId, expectedVersionId, updateTemplateVersionObj);
        logStep("Validating Response Information");
        CCUtils.assertEquals(updateTemplateVersionResponse.getStatusCode(), 400);
        CCUtils.assertEquals(updateTemplateVersionResponse.jsonPath().get("message"), "Version name exists. Please create unique name.");
    }

    @Test(priority = 3, dataProvider = "nullAndStringPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27403_ Update Email Template Version for Alphanumeric/Null/Empty practice")
    public void updateTemplateVersionForAlphaNumericNullEmptyPractice(String practiceId, String templateVersionId) throws IOException {
        logStep("TC 003_Verify the Response code as 400, when user try to give Invalid practice Id and Template version Id for Email Template Versions by using PUT Request [BL/DL]");
        logStep("Create Payload for Update Email Template Version");
        versionName = CCUtils.generateAlphaNumericString(10).concat("Update");
        emailSubject = "Subject: " + CCUtils.generateAlphaNumericString(10);
        emailBody = "Body Regards: " + CCUtils.generateAlphaNumericString(20);
        createTemplateAttributesObj = new CreateTemplateAttributes(emailSubject, emailBody);
        updateTemplateVersionObj = new UpdateTemplateVersion(versionName, createTemplateAttributesObj);
        logStep("Update the Email Template Version for practice : " + practiceId);
        updateTemplateVersionResponse = orgInfo.updateTemplateVersionInvalidDatatypeResponse("DL", practiceId, templateVersionId, updateTemplateVersionObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(updateTemplateVersionResponse);
        CCUtils.assertEquals(updateTemplateVersionResponse.getStatusCode(), 400);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27402 _ Update Email Template Version for Invalid Method Type")
    public void updateTemplateVersionToPracticeInvalidMethodType() throws Exception {
        logStep("TC 004_Verify the Response code as 405, when user try to DELETE method instead of PUT Request for Email Template [BL/DL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        templateVersionMap = getTemplateDetails(practiceId);
        templateVersionId = Long.parseLong(templateVersionMap.get("versionId").toString());
        templateId = Integer.parseInt(templateVersionMap.get("templateId").toString());
        logStep("Create Payload for Update Email Template Version");
        versionName = templateVersionMap.get("name").toString().concat("Update");
        emailSubject = "Subject: " + CCUtils.generateAlphaNumericString(10);
        emailBody = "Body Regards: " + CCUtils.generateAlphaNumericString(20);
        createTemplateAttributesObj = new CreateTemplateAttributes(emailSubject, emailBody);
        updateTemplateVersionObj = new UpdateTemplateVersion(versionName, createTemplateAttributesObj);
        logStep("Update the Email Template Version for practice : " + practiceId);
        updateTemplateVersionResponse = orgInfo.updateTemplateVersionResponse("InValidMethod", "DL", practiceId, templateVersionId, updateTemplateVersionObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(updateTemplateVersionResponse);
        CCUtils.assertEquals(updateTemplateVersionResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethod(updateTemplateVersionResponse, "DELETE");
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27404 _ Update Email Template Version for practice with Invalid URI")
    public void updateTemplateVersionToPracticeWithInvalidURI() throws Exception {
        logStep("TC 005_Verify the Response code as 404, when user try to give invalid URI for Practice Email Template versions by using PUT Request [BL/DL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        templateVersionMap = getTemplateDetails(practiceId);
        templateVersionId = Long.parseLong(templateVersionMap.get("versionId").toString());
        templateId = Integer.parseInt(templateVersionMap.get("templateId").toString());
        logStep("Create Payload for Update Email Template Version");
        versionName = templateVersionMap.get("name").toString().concat("Update");
        emailSubject = "Subject: " + CCUtils.generateAlphaNumericString(10);
        emailBody = "Body Regards: " + CCUtils.generateAlphaNumericString(20);
        createTemplateAttributesObj = new CreateTemplateAttributes(emailSubject, emailBody);
        updateTemplateVersionObj = new UpdateTemplateVersion(versionName, createTemplateAttributesObj);
        logStep("Update the Email Template Version for practice : " + practiceId);
        updateTemplateVersionResponse = orgInfo.updateTemplateVersionResponse("InValid", "DL", practiceId, templateVersionId, updateTemplateVersionObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(updateTemplateVersionResponse);
        CCUtils.assertEquals(updateTemplateVersionResponse.getStatusCode(), 404);
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27405 _ Update Email Template Version for Invalid payload")
    public void updateEmailTemplateVersionToInvalidPayload() throws Exception {
        logStep("TC 006_Verify the Response code as 400, when user try to give Valid practice Id with Template Version Id and invalid payload for Practice Email Template versions by using PUT Request [BL/DL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        templateVersionMap = getTemplateDetails(practiceId);
        templateVersionId = Long.parseLong(templateVersionMap.get("versionId").toString());
        logStep("Create Payload for Update Email Template Version");
        versionName = null;
        emailSubject = "Subject: " + CCUtils.generateAlphaNumericString(10);
        emailBody = "Body Regards: " + CCUtils.generateAlphaNumericString(20);
        createTemplateAttributesObj = new CreateTemplateAttributes(emailSubject, emailBody);
        updateTemplateVersionObj = new UpdateTemplateVersion(versionName, createTemplateAttributesObj);
        logStep("Update the Email Template Version for practice : " + practiceId);
        updateTemplateVersionResponse = orgInfo.updateTemplateVersionResponse("Valid", "DL", practiceId, templateVersionId, updateTemplateVersionObj);
        logStep("Validating Response Information");
        CCUtils.assertEquals(updateTemplateVersionResponse.getStatusCode(), 400);
        versionName = null;
        emailSubject = null;
        emailBody = "Body Regards: " + CCUtils.generateAlphaNumericString(20);
        createTemplateAttributesObj = new CreateTemplateAttributes(emailSubject, emailBody);
        updateTemplateVersionObj = new UpdateTemplateVersion(versionName, createTemplateAttributesObj);
        logStep("Update the Email Template Version for practice : " + practiceId);
        updateTemplateVersionResponse = orgInfo.updateTemplateVersionResponse("Valid", "DL", practiceId, templateVersionId, updateTemplateVersionObj);
        logStep("Validating Response Information");
        CCUtils.assertEquals(updateTemplateVersionResponse.getStatusCode(), 400);
        versionName = null;
        emailSubject = null;
        emailBody = null;
        createTemplateAttributesObj = new CreateTemplateAttributes(emailSubject, emailBody);
        updateTemplateVersionObj = new UpdateTemplateVersion(versionName, createTemplateAttributesObj);
        logStep("Update the Email Template Version for practice : " + practiceId);
        updateTemplateVersionResponse = orgInfo.updateTemplateVersionResponse("Valid", "DL", practiceId, templateVersionId, updateTemplateVersionObj);
        logStep("Validating Response Information");
        CCUtils.assertEquals(updateTemplateVersionResponse.getStatusCode(), 400);
    }

    @Test(enabled = false)
    public HashMap<String, Object> getTemplateDetails(Integer practiceId) throws Exception {
        templateId = Integer.parseInt(testData.getProperty("email.template.id1"));
        templateType = testData.getProperty("template.type.email");
        String expectedVersionName = "EmailTemplateVersionName" + CCUtils.generateNumericString(5);
        emailSubject = "Subject: " + CCUtils.generateAlphaNumericString(10);
        emailBody = "Body Regards: " + CCUtils.generateAlphaNumericString(20);
        createTemplateAttributesObj = new CreateTemplateAttributes(emailSubject, emailBody);
        createTemplateVersionObj = new CreateTemplateVersion(templateId, expectedVersionName, templateType, createTemplateAttributesObj);
        logStep("Create Email Template Version for practice : " + practiceId);
        createTemplateVersionResponse = orgInfo.createTemplateVersionResponse("Valid", "DL", practiceId, createTemplateVersionObj);
        CCUtils.assertEquals(createTemplateVersionResponse.getStatusCode(), 200);
        logStep("Get Details of Email Template Version for practice : " + practiceId);
        getTemplateVersionResponse = orgInfo.getTemplateVersionResponse("Valid", "DL", practiceId, templateId);
        CCUtils.assertEquals(getTemplateVersionResponse.getStatusCode(), 200);
        getTemplateIds = getTemplateVersionResponse.jsonPath().get("versionId");
        for (int i = 0; i < getTemplateIds.size(); i++) {
            String actualTemplatename = getTemplateVersionResponse.jsonPath().get("name[" + i + "]");
            if (expectedVersionName.equalsIgnoreCase(actualTemplatename)) {
                log("Version Name is available: " + expectedVersionName);
                expectedVersionId = Long.parseLong(getTemplateVersionResponse.jsonPath().get("versionId[" + i + "]").toString());
                log("Version Id is available: " + expectedVersionId);
                break;
            } else
                log("Version Name is not available: " + expectedVersionName);
        }
        HashMap<String, Object> templateMap = new HashMap<String, Object>();
        templateMap.clear();
        templateMap.put("versionId", expectedVersionId);
        templateMap.put("name", expectedVersionName);
        templateMap.put("templateId", templateId);
        return templateMap;
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
