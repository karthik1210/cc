// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.CommonsInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GetTemplateVariableListApiTests_DataLayer extends BaseRest {

    CommonsInfo commonsInfo;
    Response getTemplateVariableResponse;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        commonsInfo = new CommonsInfo();
    }

    @DataProvider(name = "invalidOrNullOnTemplateIdAndInvalidOrNullOnType")
    public Object[][] invalidData() throws Exception {
        return new Object[][] {
                {testData.getProperty("email.template.id1").concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(3)),  testData.getProperty("template.type.email")},
                {testData.getProperty("email.template.id1"), testData.getProperty("template.type.email.invaliddatatype")},
                {testData.getProperty("email.template.id1").concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(3)),testData.getProperty("template.type.email.invaliddatatype") },
                {null,  testData.getProperty("template.type.email")},
                {testData.getProperty("email.template.id1"), null},
                {null, null}
        };
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-28318 Get template variables list with valid template id")
    public void getTemplateVariableListsApiSuccess() throws Exception{
        logStep("verify response code 200 and response body when user enters valid template id and type to get template variables list");
        getTemplateVariableResponse = commonsInfo.getTemplateVariableList("DL", "valid", Integer.parseInt(testData.getProperty("email.template.id1")),testData.getProperty("template.type.email"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getTemplateVariableResponse);
        CCUtils.assertEquals(getTemplateVariableResponse.getStatusCode(), 200);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-28321 Get Template variable List with non-existing template id")
    public void getTemplateVariableListsWithNonExistingTemplateId() throws Exception {
        logStep("verify response code 204 when user enters non-existing template id and valid type to get template variable list");
        getTemplateVariableResponse = commonsInfo.getTemplateVariableList("DL", "valid", Integer.parseInt(testData.getProperty("email.template.id.nonexisting")),testData.getProperty("template.type.email"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getTemplateVariableResponse);
        CCUtils.assertEquals(getTemplateVariableResponse.getStatusCode(), 204);
    }

    @Test(priority = 3, dataProvider = "invalidOrNullOnTemplateIdAndInvalidOrNullOnType", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-28322 Get Template variable list with invalid/null on template id/type")
    public void getTemplateVariableListsWithInvalidOrNullOnTemplateIdOrType(String templateId, String type) throws Exception {
        logStep("TC 003_verify response code 400 and error code when user enters invalid/null on template id/type to get template variables list");
        getTemplateVariableResponse = commonsInfo.getTemplateVariableList("DL", "valid", templateId, type);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getTemplateVariableResponse);
        CCUtils.assertEquals(getTemplateVariableResponse.getStatusCode(), 400);
        CCUtils.assertEquals(getTemplateVariableResponse.getBody().jsonPath().get("error").toString().equalsIgnoreCase("Bad Request"), true);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-28323 Get Template variable list with invalid URI")
    public void getTemplateVariableListWithInvalidURI() throws Exception {
        logStep("verify response code 404 and error message when user enters valid template id and type to get template variable list with invalid URI");
        getTemplateVariableResponse = commonsInfo.getTemplateVariableList("DL", "Invalid", Integer.parseInt(testData.getProperty("email.template.id1")),testData.getProperty("template.type.email"));
        CCUtils.responseTimeValidation(getTemplateVariableResponse);
        CCUtils.assertEquals(getTemplateVariableResponse.getStatusCode(), 404);
        CCUtils.assertEquals(getTemplateVariableResponse.getBody().jsonPath().get("error").toString().equalsIgnoreCase("Not Found"), true);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-28324 Get Template variable list with DELETE Request")
    public void getTemplateVariableListWithDELETERequest() throws Exception {
        logStep("verify response code 405 and error message when user enters valid template id and type to get template variable list with DELETE request");
        getTemplateVariableResponse = commonsInfo.getTemplateVariableList("DL", "", Integer.parseInt(testData.getProperty("email.template.id1")),testData.getProperty("template.type.email"));
        CCUtils.responseTimeValidation(getTemplateVariableResponse);
        CCUtils.assertEquals(getTemplateVariableResponse.getStatusCode(), 405);
        CCUtils.assertEquals(getTemplateVariableResponse.getBody().jsonPath().get("error").toString().equalsIgnoreCase("Method Not Allowed"), true);
    }
}
