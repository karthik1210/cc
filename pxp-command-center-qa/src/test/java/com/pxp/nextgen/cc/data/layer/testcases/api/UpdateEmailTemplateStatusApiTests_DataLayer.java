// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.CreateTemplateAttributes;
import com.pxp.cc.pojos.CreateTemplateVersion;
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

public class UpdateEmailTemplateStatusApiTests_DataLayer extends BaseRest {
    private OrgInfo orgInfo;
    private Validations validations;
    private ClientInfo clientInfo;
    private CreateNGHierachyClientCreation_DataLayer ngOrgPracticeObj;
    CreateTemplateVersion createTemplateVersionObj, createTemplateVersionObj1;
    CreateTemplateAttributes createTemplateAttributesObj;
    Response createTemplateVersionResponse, getTemplateVersionResponse, updateTemplateVersionStatusResponse;
    Integer templateId, practiceId;
    Long templateVersionId, expectedVersionId;
    String versionName, templateType, emailSubject, emailBody, actualTemplatename;
    ArrayList<Integer> getTemplateIds = new ArrayList<Integer>();
    HashMap<String, Object> templateVersionMap = new HashMap<String, Object>();
    HashMap<String, Boolean> requestMap = new HashMap<String, Boolean>();

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

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27457_ Update Email Version Template status as Active - True for Practice")
    public void updateTemplateVersionActiveStatusForPracticeApiSuccess() throws Exception {
        logStep("TC 001_verify response code 200 when user enters valid org Id and template version Id with valid payload to update template version status by using PATCH Request [BL/DL]");
        practiceId = createPractice();
        templateVersionMap = getTemplateDetails(practiceId);
        templateVersionId = Long.parseLong(templateVersionMap.get("versionId").toString());
        templateId = Integer.parseInt(templateVersionMap.get("templateId").toString());
        versionName = templateVersionMap.get("name").toString();
        logStep("Create Payload for Update Email Template Version Status");
        requestMap.clear();
        requestMap.put("active", true);
        logStep("Update the Email Template Version Status for practice : " + practiceId);
        updateTemplateVersionStatusResponse = orgInfo.updateTemplateVersionStatusResponse("Valid", "DL", practiceId, templateVersionId, requestMap);
        logStep("Validating Response Information");
        CCUtils.assertEquals(updateTemplateVersionStatusResponse.getStatusCode(), 200);
        getTemplateVersionResponse = orgInfo.getTemplateVersionResponse("Valid", "DL", practiceId, templateId);
        CCUtils.assertEquals(getTemplateVersionResponse.getStatusCode(), 200);
        getTemplateIds = getTemplateVersionResponse.jsonPath().get("versionId");
        for (int i = 0; i < getTemplateIds.size(); i++) {
            String actualTemplatename = getTemplateVersionResponse.jsonPath().get("name[" + i + "]");
            if (versionName.equalsIgnoreCase(actualTemplatename)) {
                log("Version Name is available: " + versionName);
                log("Email Template Status is update as : " + getTemplateVersionResponse.jsonPath().get("active[" + i + "]"));
                break;
            } else
                log("Email Template Status is not update as : " + getTemplateVersionResponse.jsonPath().get("active[" + i + "]"));
        }
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27458 _ Update Email template version status as delete -true for Practice")
    public void updateTemplateVersionStatusDeleteForPracticeApiSuccess() throws Exception {
        logStep("TC 002_verify response code 200 when user enters valid org Id and template version Id with Delete attribute as true/false payload to update template version status by using PATCH Request [BL/DL]");
        practiceId = createPractice();
        templateVersionMap = getTemplateDetails(practiceId);
        templateVersionId = Long.parseLong(templateVersionMap.get("versionId").toString());
        templateId = Integer.parseInt(templateVersionMap.get("templateId").toString());
        logStep("Create Payload for Update Email Template Version Status");
        requestMap.clear();
        requestMap.put("delete", true);
        logStep("Update the Email Template Version Status for practice : " + practiceId);
        updateTemplateVersionStatusResponse = orgInfo.updateTemplateVersionStatusResponse("Valid", "DL", practiceId, templateVersionId, requestMap);
        logStep("Validating Response Information");
        CCUtils.assertEquals(updateTemplateVersionStatusResponse.getStatusCode(), 200);
    }

    @Test(priority = 3, dataProvider = "nullAndStringPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27459_Update Email Template Version Status for Alphanumeric/Null/Empty practice")
    public void updateTemplateVersionStatusForAlphaNumericNullEmptyPractice(String practiceId, String templateVersionId) throws IOException {
        logStep("TC 003_Verify the Response code as 400, when user try to give Invalid org Id and Template version Id with valid Payload for update Template Version status by using PATCH Request [BL/DL]");
        logStep("Create Payload for Update Email Template Version Status");
        requestMap.clear();
        requestMap.put("delete", true);
        logStep("Update the Email Template Version Status for practice : " + practiceId);
        updateTemplateVersionStatusResponse = orgInfo.updateTemplateVersionStatusInvalidDatatypeResponse("DL", practiceId, templateVersionId, requestMap);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(updateTemplateVersionStatusResponse);
        CCUtils.assertEquals(updateTemplateVersionStatusResponse.getStatusCode(), 400);
    }


    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27460 _ Update Email Template Version Status for Invalid payload")
    public void updateEmailTemplateVersionStatusToInvalidPayload() throws Exception {
        logStep("TC 004_Verify the Response code as 400, when user try to give Valid practice Id with Template Version Id and invalid payload for update Template version status by using PATCH Request [BL/DL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        templateVersionMap = getTemplateDetails(practiceId);
        templateVersionId = Long.parseLong(templateVersionMap.get("versionId").toString());
        logStep("Create Payload for Update Email Template Version Status");
        requestMap.clear();
        requestMap.put("delete", null);
        logStep("Update the Email Template Version Status for practice : " + practiceId);
        updateTemplateVersionStatusResponse = orgInfo.updateTemplateVersionStatusResponse("Valid", "DL", practiceId, templateVersionId, requestMap);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(updateTemplateVersionStatusResponse);
        CCUtils.assertEquals(updateTemplateVersionStatusResponse.getStatusCode(), 400);
        requestMap.clear();
        requestMap.put("active", null);
        logStep("Update the Email Template Version Status for practice : " + practiceId);
        updateTemplateVersionStatusResponse = orgInfo.updateTemplateVersionStatusResponse("Valid", "DL", practiceId, templateVersionId, requestMap);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(updateTemplateVersionStatusResponse);
        CCUtils.assertEquals(updateTemplateVersionStatusResponse.getStatusCode(), 400);
        requestMap.clear();
        requestMap.put("delete", null);
        requestMap.put("active", null);
        logStep("Update the Email Template Version Status for practice : " + practiceId);
        updateTemplateVersionStatusResponse = orgInfo.updateTemplateVersionStatusResponse("Valid", "DL", practiceId, templateVersionId, requestMap);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(updateTemplateVersionStatusResponse);
        CCUtils.assertEquals(updateTemplateVersionStatusResponse.getStatusCode(), 400);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27461 _ Update Email Template Version status for practice with Invalid URI")
    public void updateTemplateVersionStatusToPracticeWithInvalidURI() throws Exception {
        logStep("TC 005_Verify the Response code as 404, when user try to give invalid URI for Practice Email Template versions by using PUT Request [BL/DL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        templateVersionMap = getTemplateDetails(practiceId);
        templateVersionId = Long.parseLong(templateVersionMap.get("versionId").toString());
        logStep("Create Payload for Update Email Template Version Status");
        requestMap.clear();
        requestMap.put("delete", true);
        logStep("Update the Email Template Version Status for practice : " + practiceId);
        updateTemplateVersionStatusResponse = orgInfo.updateTemplateVersionStatusResponse("InValid", "DL", practiceId, templateVersionId, requestMap);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(updateTemplateVersionStatusResponse);
        CCUtils.assertEquals(updateTemplateVersionStatusResponse.getStatusCode(), 404);
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27462 _ Update Email Template Version status for Invalid Method Type")
    public void updateTemplateVersionToPracticeInvalidMethodType() throws Exception {
        logStep("TC 006_Verify the Response code as 405, when user try to DELETE method instead of PATCH Request for update Template version status[BL/DL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        templateVersionMap = getTemplateDetails(practiceId);
        templateVersionId = Long.parseLong(templateVersionMap.get("versionId").toString());
        logStep("Create Payload for Update Email Template Version Status");
        requestMap.clear();
        requestMap.put("delete", true);
        logStep("Update the Email Template Version Status for practice : " + practiceId);
        updateTemplateVersionStatusResponse = orgInfo.updateTemplateVersionStatusResponse("InValidMethod", "DL", practiceId, templateVersionId, requestMap);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(updateTemplateVersionStatusResponse);
        CCUtils.assertEquals(updateTemplateVersionStatusResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethod(updateTemplateVersionStatusResponse, "DELETE");
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27463 _ Update Email template version Active status as delete -true for Practice")
    public void updateTemplateVersionActiveStatusDeleteForPracticeApiSuccess() throws Exception {
        logStep("TC 007_verify response code 400 when user tries to delete active template version by using PATCH Request [BL/DL]");
        practiceId = createPractice();
        templateVersionMap = getTemplateDetails(practiceId);
        templateVersionId = Long.parseLong(templateVersionMap.get("versionId").toString());
        templateId = Integer.parseInt(templateVersionMap.get("templateId").toString());
        versionName = templateVersionMap.get("name").toString();
        logStep("Create Payload for Update Email Template Version Status");
        requestMap.clear();
        requestMap.put("active", true);
        logStep("Update the Email Template Version Status for practice : " + practiceId);
        updateTemplateVersionStatusResponse = orgInfo.updateTemplateVersionStatusResponse("Valid", "DL", practiceId, templateVersionId, requestMap);
        logStep("Validating Response Information");
        CCUtils.assertEquals(updateTemplateVersionStatusResponse.getStatusCode(), 200);
        requestMap.clear();
        requestMap.put("delete", true);
        logStep("Update the Email Template Version Status for practice : " + practiceId);
        updateTemplateVersionStatusResponse = orgInfo.updateTemplateVersionStatusResponse("Valid", "DL", practiceId, templateVersionId, requestMap);
        logStep("Validating Response Information");
        CCUtils.assertEquals(updateTemplateVersionStatusResponse.getStatusCode(), 400);
        CCUtils.assertEquals(updateTemplateVersionStatusResponse.jsonPath().get("message"), "Invalid input parameter");
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
