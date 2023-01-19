// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.CreateTemplateAttributes;
import com.pxp.cc.pojos.CreateTemplateVersion;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import com.pxp.nextgen.cc.data.layer.testcases.api.CreateNGHierachyClientCreation_DataLayer;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;

public class GetTemplateAttributesApiTests_BusinessLayer extends BaseRest {
    private OrgInfo orgInfo;
    private Validations validations;
    private ClientInfo clientInfo;
    private CreateNGHierachyClientCreation_BusinessLayer ngOrgPracticeObj;
    CreateTemplateVersion createTemplateVersionObj;
    CreateTemplateAttributes createTemplateAttributesObj;
    Response createTemplateVersionResponse, getTemplateVersionResponse, getTemplateAttributeResponse;
    Integer templateId, practiceId;
    Long expectedVersionId;
    String versionName, templateType, emailSubject, emailBody;
    ArrayList<Integer> getTemplateIds = new ArrayList<Integer>();

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilder();
        orgInfo = new OrgInfo();
        clientInfo = new ClientInfo();
        validations = new Validations();
        ngOrgPracticeObj = new CreateNGHierachyClientCreation_BusinessLayer();
    }

    @DataProvider(name = "nullAndStringPracticeId")
    public Object[][] nullAndStringPracticeId() throws Exception {
        return new Object[][]{
                {testData.getProperty("PXPP.18282.api.practice.id"), testData.getProperty("Non.existing.practice.id") + "Sample", testData.getProperty("Non.existing.practice.id"), testData.getProperty("template.type.email"),
                        "PXPP-27413_TC 003_verify response code 400 and error message when user enters valid org id other than 0 and invalid/null/empty on version id to get list of template version attributes by using GET Request [BL/DL]"},
                {testData.getProperty("PXPP.18282.api.practice.id"), testData.getProperty("PXPP.18282.api.practice.id") + "Sample", testData.getProperty("PXPP.18282.api.practice.id") + "Sample", testData.getProperty("template.type.email"),
                        "PXPP-27414_TC 004_verify response code 400 and error message when user enters valid org id and invalid/null on template id, version id and type to get list of template version attributes by using GET Request [BL/DL]"},
                {testData.getProperty("PXPP.18282.api.practice.id"), null, null, testData.getProperty("template.type.email"),
                        "PXPP-27413_TC 003_verify response code 400 and error message when user enters valid org id other than 0 and invalid/null/empty on version id to get list of template version attributes by using GET Request [BL/DL]"},
                {testData.getProperty("PXPP.18282.api.practice.id"), testData.getProperty("PXPP.18282.api.practice.id") + "Sample", null, testData.getProperty("template.type.email"),
                        "PXPP-27414_TC 004_verify response code 400 and error message when user enters valid org id and invalid/null on template id, version id and type to get list of template version attributes by using GET Request [BL/DL]"},
                {testData.getProperty("PXPP.18282.api.practice.id") + "Sample", testData.getProperty("email.template.id1"), testData.getProperty("email.template.version.id"), testData.getProperty("template.type.email"),
                        "PXPP-27415_TC 005_verify response code 400 and error message when user enters invalid/null on org id and valid on template id, version id and type to get list of template version attributes by using GET Request [BL/DL]"},
                {null, testData.getProperty("email.template.id1"), testData.getProperty("email.template.version.id"), testData.getProperty("template.type.email"),
                        "PXPP-27415_TC 005_verify response code 400 and error message when user enters invalid/null on org id and valid on template id, version id and type to get list of template version attributes by using GET Request [BL/DL]"},
                {"", "", " ", " ", "PXPP-27413_PXPP-27414_TC 003_verify response code 400 and error message when user enters valid org id other than 0 and invalid/null/empty on version id to get list of template version attributes by using GET Request [BL/DL]"},
                {null, null, null, null, "PXPP-27413_PXPP-27414_TC 003_verify response code 400 and error message when user enters valid org id other than 0 and invalid/null/empty on version id to get list of template version attributes by using GET Request [BL/DL]"},
                {testData.getProperty("PXPP.18282.api.practice.id"), null, null, null, "PXPP-27413_PXPP-27414_TC 003_verify response code 400 and error message when user enters valid org id other than 0 and invalid/null/empty on version id to get list of template version attributes by using GET Request [BL/DL]"}
        };
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27374_Get Email Template Attributes for Practice")
    public void getTemplateAttributeForPracticeApiSuccess() throws Exception {
        logStep("TC 001_verify response code 200 and response body when user enters valid org id, template id, version id and type to get List of Template version attribute by using GET Request [BL/DL]");
        practiceId = createPractice();
        templateId = Integer.parseInt(testData.getProperty("email.template.id1"));
        templateType = testData.getProperty("template.type.email");
        String expectedVersionName = "EmailTemplateVersionName" + CCUtils.generateNumericString(5);
        emailSubject = "Subject: " + CCUtils.generateAlphaNumericString(10);
        emailBody = "Body Regards: " + CCUtils.generateAlphaNumericString(20);
        createTemplateAttributesObj = new CreateTemplateAttributes(emailSubject, emailBody);
        createTemplateVersionObj = new CreateTemplateVersion(templateId, expectedVersionName, templateType, createTemplateAttributesObj);
        logStep("Create Email Template Version for practice : " + practiceId);
        createTemplateVersionResponse = orgInfo.createTemplateVersionResponse("Valid", "BL", practiceId, createTemplateVersionObj);
        CCUtils.assertEquals(createTemplateVersionResponse.getStatusCode(), 200);
        logStep("Get Details of Email Template Version for practice : " + practiceId);
        getTemplateVersionResponse = orgInfo.getTemplateVersionResponse("Valid", "BL", practiceId, templateId);
        logStep("Validating the Get Template Response");
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
        logStep("Get Details of Email Template Attributes " + expectedVersionId + " for practice : " + practiceId);
        getTemplateAttributeResponse = orgInfo.getTemplateAttributesResponse("Valid", "BL", practiceId, templateId, expectedVersionId, templateType);
        logStep("Validating the Get Template Response");
        CCUtils.assertEquals(getTemplateAttributeResponse.getStatusCode(), 200);
        CCUtils.assertEquals(getTemplateAttributeResponse.jsonPath().get("name"), expectedVersionName);
    }

    @Test(priority = 2, dataProvider = "nullAndStringPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "Get Email Template Version for Alphanumeric/Null/Empty practice")
    public void getTemplateAttributeForAlphaNumericNullEmptyPractice(String practiceId, String templateId, String expectedVersionId, String type, String testCaseName) throws
            IOException {
        logStep(testCaseName);
        logStep("Get Details of Email Template Attributes for practice : " + practiceId);
        getTemplateAttributeResponse = orgInfo.getTemplateAttributesInvalidDatatypeResponse("BL", practiceId, templateId, expectedVersionId, type);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(getTemplateAttributeResponse);
        CCUtils.assertEquals(getTemplateAttributeResponse.getStatusCode(), 400);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27416 _ Get Email Template Version Attributes for practice with Invalid Template Id")
    public void getTemplateAttributeToPracticeWithInvalidTemplateID() throws Exception {
        logStep("TC 006_verify response code 204 when user enters valid org id and non-existing template id, version id and valid type to get List of Template version attribute by using GET Request [BL/DL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        templateId = Integer.parseInt(testData.getProperty("Non.existing.practice.id"));
        expectedVersionId = Long.parseLong(testData.getProperty("Non.existing.practice.id"));
        templateType = testData.getProperty("template.type.email");
        getTemplateAttributeResponse = orgInfo.getTemplateAttributesResponse("Valid", "BL", practiceId, templateId, expectedVersionId, templateType);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(getTemplateAttributeResponse);
        CCUtils.assertEquals(getTemplateAttributeResponse.getStatusCode(), 204);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27417 _ Get Email Template Version Attributesfor practice with non existing org")
    public void getTemplateAttributeToPracticeWithNonExistingOrg() throws Exception {
        logStep("TC 007_verify response code 200 when user enters non-existing org id and valid template id, version id and type to get List of Template version attribute by using GET Request [BL/DL]");
        practiceId = Integer.parseInt(testData.getProperty("Non.existing.practice.id"));
        templateId = Integer.parseInt(testData.getProperty("email.template.id1"));
        expectedVersionId = Long.parseLong(testData.getProperty("email.template.version.id"));
        templateType = testData.getProperty("template.type.email");
        getTemplateAttributeResponse = orgInfo.getTemplateAttributesResponse("Valid", "BL", practiceId, templateId, expectedVersionId, templateType);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(getTemplateAttributeResponse);
        CCUtils.assertEquals(getTemplateAttributeResponse.getStatusCode(), 200);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27418 _ Get Email Template Version Attributes for practice with Invalid URI")
    public void getTemplateAttributeToPracticeWithInvalidURI() throws Exception {
        logStep("TC 008_verify response code 404 and error message when user enters valid org id, template id, version id and type to get list of Template version attributes by GET Request with Invalid URI [BL/DL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        templateId = Integer.parseInt(testData.getProperty("email.template.id1"));
        expectedVersionId = Long.parseLong(testData.getProperty("email.template.version.id"));
        templateType = testData.getProperty("template.type.email");
        getTemplateAttributeResponse = orgInfo.getTemplateAttributesResponse("InValid", "BL", practiceId, templateId, expectedVersionId, templateType);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(getTemplateAttributeResponse);
        CCUtils.assertEquals(getTemplateAttributeResponse.getStatusCode(), 404);
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-27419 _ get Email Template Version Attributes for Invalid Method Type")
    public void getTemplateAttributeToPracticeInvalidMethodType() throws Exception {
        logStep("TC 009_verify response code 405 and error message when user enters valid org id, template id, version id and type to get list of Template version attributes by DELETE Request [BL/DL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        templateId = Integer.parseInt(testData.getProperty("email.template.id1"));
        expectedVersionId = Long.parseLong(testData.getProperty("email.template.version.id"));
        templateType = testData.getProperty("template.type.email");
        getTemplateAttributeResponse = orgInfo.getTemplateAttributesResponse("InValidMethod", "BL", practiceId, templateId, expectedVersionId, templateType);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(getTemplateAttributeResponse);
        CCUtils.assertEquals(getTemplateAttributeResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethod(getTemplateAttributeResponse, "DELETE");
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
