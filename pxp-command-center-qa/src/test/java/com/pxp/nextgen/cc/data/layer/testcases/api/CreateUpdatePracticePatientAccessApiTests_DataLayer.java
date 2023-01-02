// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;


import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.*;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateUpdatePracticePatientAccessApiTests_DataLayer extends BaseRest {
    private OrgInfo orgInfo;
    private Validations validations;
    private ClientInfo clientInfo;
    private CreateNGHierachyClientCreation_DataLayer ngOrgPracticeObj;
    PatientAccessAgeoutRules patientAccessAgeoutRulesObj1, patientAccessAgeoutRulesObj2, patientAccessAgeoutRulesObj3;
    PatientAccessAgeout patientAccessAgeoutObj;
    PatientAccessPermission patientAccessPermissionObj1, patientAccessPermissionObj2, patientAccessPermissionObj3, patientAccessPermissionObj4, patientAccessPermissionObj5;
    PatientAccessSettings patientAccessSettingsObj;
    PatientAccessDetails patientAccessDetailsObj;
    Response createUpdatePatientAccessApiResponse, getPatientAccessSettingApiResponse, getPatientAccessAgeOutApiResponse, getPatientAccessGaurdianApiResponse, createClientApiResponse, createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse;
    int practiceId;
    boolean linkAccounts, autoAgeout;
    ArrayList<PatientAccessAgeoutRules> patientAccessAgeoutRulesList = new ArrayList<PatientAccessAgeoutRules>();
    ArrayList<PatientAccessPermission> patientAccessPermissionList = new ArrayList<PatientAccessPermission>();

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
        return new Object[][]{{testData.getProperty("PXPP.18282.api.practice.id") + "Sample", "PXPP-24589_TC_03_Verify the Response code as 400, when user try to give alphanumeric practice Id for Patient Access by using POST Request [DL]"},
                {null, "PXPP-24591_TC_05_Verify the Response code as 400, when user try to give null practice Id for Patient Access by using POST Request [DL]"},
                {"", "Verify the Response Code 400, when user enter Empty practice Id"},
                {testData.getProperty("Non.existing.practice.id").trim(), "PXPP-24592_TC_06_Verify the Response code as 400, when user try to give invalid practice id for Patient Access by using POST Request [DL]"}};
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Add/Update Patient Access for practice")
    public void addUpdatePracticePatientAccessSuccess() throws IOException {
        logStep("PXPP-24587 : TC_01_Verify the Response code as 200, when user try to Create Patient Access by using POST Request [DL]");
        practiceId = createPractice();
        patientAccessDetailsObj = createPayload();
        logStep("Add patient access details for practice : " + practiceId);
        createUpdatePatientAccessApiResponse = orgInfo.createUpdatePracticePatientAccessResponse("Valid", "DL", practiceId, patientAccessDetailsObj);
        logStep("Validating Response Information");
        CCUtils.assertEquals(createUpdatePatientAccessApiResponse.getStatusCode(), 200);
        getPatientAccessSettingApiResponse = orgInfo.getPatientAccessSettings_DataLayer("Valid", practiceId);
        logStep("Validating Response for Patient Access Settings");
        validations.verifyOutputFromValidInputForGetPatientAccessSettings(getPatientAccessSettingApiResponse, linkAccounts, autoAgeout);
        getPatientAccessAgeOutApiResponse = orgInfo.getPatientAgeout_DataLayer("Valid", practiceId);
        logStep("Validating Response for Patient AgeOut Details");
        validations.verifyOutputFromValidInputForGetPatientAgeOutAccess(getPatientAccessAgeOutApiResponse, 18, patientAccessAgeoutRulesList, 18);
        getPatientAccessGaurdianApiResponse = orgInfo.getPatientGuardianAccess_DataLayer("Valid", practiceId);
        logStep("Validating Response for Patient Guardian Access Settings");
        validations.verifyOutputFromValidInputForGetPatientGuardianAccess(getPatientAccessGaurdianApiResponse, patientAccessPermissionList);
        logStep("PXPP-24594: TC 008_Verify the Response code as 200, when user try to update the existing Patient Access by using POST Request [DL]");
        patientAccessAgeoutRulesObj1 = new PatientAccessAgeoutRules(18, testData.getProperty("patient.access.state4"));
        patientAccessAgeoutRulesObj2 = new PatientAccessAgeoutRules(16, testData.getProperty("patient.access.state5"));
        patientAccessAgeoutRulesObj3 = new PatientAccessAgeoutRules(17, testData.getProperty("patient.access.state1"));
        patientAccessAgeoutRulesList.clear();
        patientAccessAgeoutRulesList.add(patientAccessAgeoutRulesObj1);
        patientAccessAgeoutRulesList.add(patientAccessAgeoutRulesObj2);
        patientAccessAgeoutRulesList.add(patientAccessAgeoutRulesObj3);
        patientAccessAgeoutObj = new PatientAccessAgeout(19, patientAccessAgeoutRulesList, 18);
        patientAccessPermissionObj2 = new PatientAccessPermission(Integer.parseInt(testData.getProperty("patient.access.feature.id2")), testData.getProperty("patient.access.feature.name2"), testData.getProperty("patient.access.permission.fullaccess"));
        patientAccessPermissionObj3 = new PatientAccessPermission(Integer.parseInt(testData.getProperty("patient.access.feature.id3")), testData.getProperty("patient.access.feature.name3"), testData.getProperty("patient.access.permission.noaccess"));
        patientAccessPermissionList.clear();
        patientAccessPermissionList.add(patientAccessPermissionObj1);
        patientAccessPermissionList.add(patientAccessPermissionObj2);
        patientAccessPermissionList.add(patientAccessPermissionObj3);
        patientAccessPermissionList.add(patientAccessPermissionObj4);
        patientAccessPermissionList.add(patientAccessPermissionObj5);
        logStep("Prepared Payload to add patient access details for practice : " + practiceId);
        patientAccessDetailsObj = new PatientAccessDetails(patientAccessSettingsObj, patientAccessAgeoutObj, patientAccessPermissionList);
        logStep("Update patient access details for practice : " + practiceId);
        createUpdatePatientAccessApiResponse = orgInfo.createUpdatePracticePatientAccessResponse("Valid", "DL", practiceId, patientAccessDetailsObj);
        logStep("Validating Response Information");
        CCUtils.assertEquals(createUpdatePatientAccessApiResponse.getStatusCode(), 200);
        getPatientAccessSettingApiResponse = orgInfo.getPatientAccessSettings_DataLayer("Valid", practiceId);
        logStep("Validating Response for Patient Access Settings");
        validations.verifyOutputFromValidInputForGetPatientAccessSettings(getPatientAccessSettingApiResponse, linkAccounts, autoAgeout);
        getPatientAccessAgeOutApiResponse = orgInfo.getPatientAgeout_DataLayer("Valid", practiceId);
        logStep("Validating Response for Patient AgeOut Details");
        validations.verifyOutputFromValidInputForGetPatientAgeOutAccess(getPatientAccessAgeOutApiResponse, 19, patientAccessAgeoutRulesList, 18);
        getPatientAccessGaurdianApiResponse = orgInfo.getPatientGuardianAccess_DataLayer("Valid", practiceId);
        logStep("Validating Response for Patient Guardian Access Settings");
        validations.verifyOutputFromValidInputForGetPatientGuardianAccess(getPatientAccessGaurdianApiResponse, patientAccessPermissionList);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24588_Add Patient Access for practice with Invalid URI")
    public void addPatientAccessToPracticeWithInvalidURI() throws IOException {
        logStep("TC_02_Verify the Response code as 404, when user try to give invalid URI for Patient Access using POST Request [DL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        patientAccessDetailsObj = createPayload();
        logStep("Add a Patient Access to practice with Invalid URI");
        createUpdatePatientAccessApiResponse = orgInfo.createUpdatePracticePatientAccessResponse("InValid", "DL", practiceId, patientAccessDetailsObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(createUpdatePatientAccessApiResponse);
        CCUtils.assertEquals(createUpdatePatientAccessApiResponse.getStatusCode(), 404);
    }

    @Test(priority = 3, dataProvider = "nullAndStringPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "Add Patient Access for Alphanumeric/Null/Empty practice")
    public void addPatientAccessForAlphaNumericNullEmptyPractice(String practiceId, String testCaseName) throws IOException {
        logStep(testCaseName);
        patientAccessDetailsObj = createPayload();
        logStep("Add a Patient Access to practice with Invalid Datatype");
        createUpdatePatientAccessApiResponse = orgInfo.createUpdatePracticePatientAccessInvaliDatatypeResponse("DL", practiceId, patientAccessDetailsObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(createUpdatePatientAccessApiResponse);
        CCUtils.assertEquals(createUpdatePatientAccessApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24590_Add Patient Access for Invalid Method Type")
    public void addPatientAccessToPracticeInvalidMethodType() throws IOException {
        logStep("TC_04_Verify the Response code as 405, when user try to call PATCH method instead of POST Request for Patient Access [DL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        patientAccessDetailsObj = createPayload();
        logStep("Add a Patient Access to practice with Invalid Datatype");
        createUpdatePatientAccessApiResponse = orgInfo.createUpdatePracticePatientAccessResponse("InValidMethod", "DL", practiceId, patientAccessDetailsObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(createUpdatePatientAccessApiResponse);
        CCUtils.assertEquals(createUpdatePatientAccessApiResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethod(createUpdatePatientAccessApiResponse, "PATCH");
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24593_Add Patient Access for Invalid payload")
    public void addPatientAccessToInvalidPayload() throws IOException {
        logStep("TC_07_Verify the Response code as 400, when user try to give Valid practice Id and invalid payload for an Patient Access by using POST Request [DL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        logStep("Prepared Payload to add patient access details for practice : " + practiceId);
        linkAccounts = true;
        autoAgeout = true;
        patientAccessSettingsObj = new PatientAccessSettings(linkAccounts, autoAgeout);
        patientAccessAgeoutRulesObj1 = new PatientAccessAgeoutRules(18, null);
        patientAccessAgeoutRulesList.clear();
        patientAccessAgeoutRulesList.add(patientAccessAgeoutRulesObj1);
        patientAccessAgeoutObj = new PatientAccessAgeout(18, patientAccessAgeoutRulesList, 18);
        patientAccessPermissionObj1 = new PatientAccessPermission(Integer.parseInt(testData.getProperty("patient.access.feature.id1")), testData.getProperty("patient.access.feature.name1"), null);
        patientAccessPermissionObj2 = new PatientAccessPermission(Integer.parseInt(testData.getProperty("patient.access.feature.id2")), null, testData.getProperty("patient.access.permission.viewonly"));
        patientAccessPermissionList.clear();
        patientAccessPermissionList.add(patientAccessPermissionObj1);
        patientAccessPermissionList.add(patientAccessPermissionObj2);
        logStep("Prepared Payload to add patient access details for practice : " + practiceId);
        patientAccessDetailsObj = new PatientAccessDetails(patientAccessSettingsObj, patientAccessAgeoutObj, patientAccessPermissionList);
        logStep("Update patient access details for practice : " + practiceId);
        createUpdatePatientAccessApiResponse = orgInfo.createUpdatePracticePatientAccessResponse("Valid", "DL", practiceId, patientAccessDetailsObj);
        logStep("Validating Response Information");
        CCUtils.assertEquals(createUpdatePatientAccessApiResponse.getStatusCode(), 400);
    }

    @Test(enabled = false)
    public PatientAccessDetails createPayload() throws IOException {
        linkAccounts = true;
        autoAgeout = true;
        patientAccessSettingsObj = new PatientAccessSettings(linkAccounts, autoAgeout);
        patientAccessAgeoutRulesObj1 = new PatientAccessAgeoutRules(18, testData.getProperty("patient.access.state1"));
        patientAccessAgeoutRulesObj2 = new PatientAccessAgeoutRules(16, testData.getProperty("patient.access.state2"));
        patientAccessAgeoutRulesObj3 = new PatientAccessAgeoutRules(17, testData.getProperty("patient.access.state3"));
        patientAccessAgeoutRulesList.clear();
        patientAccessAgeoutRulesList.add(patientAccessAgeoutRulesObj1);
        patientAccessAgeoutRulesList.add(patientAccessAgeoutRulesObj2);
        patientAccessAgeoutRulesList.add(patientAccessAgeoutRulesObj3);
        patientAccessAgeoutObj = new PatientAccessAgeout(18, patientAccessAgeoutRulesList, 18);
        patientAccessPermissionObj1 = new PatientAccessPermission(Integer.parseInt(testData.getProperty("patient.access.feature.id1")), testData.getProperty("patient.access.feature.name1"), testData.getProperty("patient.access.permission.fullaccess"));
        patientAccessPermissionObj2 = new PatientAccessPermission(Integer.parseInt(testData.getProperty("patient.access.feature.id2")), testData.getProperty("patient.access.feature.name2"), testData.getProperty("patient.access.permission.viewonly"));
        patientAccessPermissionObj3 = new PatientAccessPermission(Integer.parseInt(testData.getProperty("patient.access.feature.id3")), testData.getProperty("patient.access.feature.name3"), testData.getProperty("patient.access.permission.fullaccess"));
        patientAccessPermissionObj4 = new PatientAccessPermission(Integer.parseInt(testData.getProperty("patient.access.feature.id4")), testData.getProperty("patient.access.feature.name4"), testData.getProperty("patient.access.permission.viewonly"));
        patientAccessPermissionObj5 = new PatientAccessPermission(Integer.parseInt(testData.getProperty("patient.access.feature.id5")), testData.getProperty("patient.access.feature.name5"), testData.getProperty("patient.access.permission.noaccess"));
        patientAccessPermissionList.clear();
        patientAccessPermissionList.add(patientAccessPermissionObj1);
        patientAccessPermissionList.add(patientAccessPermissionObj2);
        patientAccessPermissionList.add(patientAccessPermissionObj3);
        patientAccessPermissionList.add(patientAccessPermissionObj4);
        patientAccessPermissionList.add(patientAccessPermissionObj5);
        logStep("Prepared Payload to add patient access details for practice : " + practiceId);
        patientAccessDetailsObj = new PatientAccessDetails(patientAccessSettingsObj, patientAccessAgeoutObj, patientAccessPermissionList);
        return patientAccessDetailsObj;
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
