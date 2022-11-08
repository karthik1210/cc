// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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

public class CreateUpdatePatientAccessApiTests_BusinessLayer extends BaseRest {
    private OrgInfo orgInfo;
    private Validations validations;
    private ClientInfo clientInfo;
    private CreateNGHierachyClientCreation_BusinessLayer ngOrgPracticeObj;
    PatientAccessAgeOutRulesBusinessLayer PatientAccessAgeOutRulesBusinessLayerObj1, PatientAccessAgeOutRulesBusinessLayerObj2, PatientAccessAgeOutRulesBusinessLayerObj3;
    PatientAccessAgeoutBusinessLayer PatientAccessAgeoutBusinessLayerObj;
    PatientGuardianAccessBusinessLayer PatientGuardianAccessBusinessLayerobj;
    PatientAccessPermission patientAccessPermissionObj1, patientAccessPermissionObj2, patientAccessPermissionObj3, patientAccessPermissionObj4, patientAccessPermissionObj5;
    PatientAccessSettingsBusinessLayer PatientAccessSettingsBusinessLayerObj;
    PatientAccessDetailsBusinessLayer PatientAccessDetailsBusinessLayerObj;
    Response createUpdatePatientAccessApiResponse, getPracticeAccessApiResponse;
    int practiceId;
    boolean linkAccounts, autoAgeout;
    ArrayList<PatientAccessAgeOutRulesBusinessLayer> PatientAccessAgeOutRulesBusinessLayerList = new ArrayList<PatientAccessAgeOutRulesBusinessLayer>();
    ArrayList<PatientAccessPermission> patientAccessPermissionList = new ArrayList<PatientAccessPermission>();
    ArrayList<String> statesList1 = new ArrayList<String>();
    ArrayList<String> statesList2 = new ArrayList<String>();
    ArrayList<String> statesList3 = new ArrayList<String>();

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
        return new Object[][]{{testData.getProperty("PXPP.18282.api.practice.id") + "Sample", "PXPP-24047_TC_03_Verify the Response code as 400, when user try to give alphanumeric practice Id for Patient Access by using POST Request [BL]"},
                {null, "PXPP-24058_TC_05_Verify the Response code as 400, when user try to give null practice Id for Patient Access by using POST Request [BL]"},
                {"", "Verify the Response Code 400, when user enter Empty practice Id"},
                {testData.getProperty("Non.existing.practice.id").trim(), "PXPP-24059_TC_06_Verify the Response code as 400, when user try to give invalid practice id for Patient Access by using POST Request [BL]"}};
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Add/Update Patient Access for practice")
    public void addUpdatePracticePatientAccessSuccess() throws IOException {
        logStep("PXPP-24045 : TC_01_Verify the Response code as 200, when user try to Create Patient Access by using POST Request [BL]");
        practiceId = createPractice();
        logStep("Prepared Payload to add patient access details for practice : " + practiceId);
        linkAccounts = true;
        autoAgeout = true;
        int defaultAgeout = Integer.parseInt(testData.getProperty("patient.access.defaultAgeout"));
        PatientAccessDetailsBusinessLayerObj = createPayload(linkAccounts, autoAgeout, defaultAgeout);
        logStep("Add patient access details for practice : " + practiceId);
        createUpdatePatientAccessApiResponse = orgInfo.createUpdatePracticePatientAccessResponseBL("Valid", practiceId, PatientAccessDetailsBusinessLayerObj);
        logStep("Validating Response Information");
        CCUtils.responseTimeValidation(createUpdatePatientAccessApiResponse);
        CCUtils.assertEquals(createUpdatePatientAccessApiResponse.getStatusCode(), 200);
        logStep("Get the Patient Access Details");
        getPracticeAccessApiResponse = orgInfo.getPatientAccess_BusinessLayer("Valid", practiceId);
        validations.verifyPatientAccessGetBL(getPracticeAccessApiResponse, linkAccounts, autoAgeout, defaultAgeout);
        logStep("PXPP-24061: TC 008_Verify the Response code as 200, when user try to update the existing Patient Access by using POST Request [BL]");
        linkAccounts = false;
        autoAgeout = false;
        PatientAccessDetailsBusinessLayerObj = createPayload(linkAccounts, autoAgeout, ++defaultAgeout);
        logStep("Update patient access details for practice : " + practiceId);
        createUpdatePatientAccessApiResponse = orgInfo.createUpdatePracticePatientAccessResponseBL("Valid", practiceId, PatientAccessDetailsBusinessLayerObj);
        logStep("Validating Response Information");
        CCUtils.responseTimeValidation(createUpdatePatientAccessApiResponse);
        CCUtils.assertEquals(createUpdatePatientAccessApiResponse.getStatusCode(), 200);
        logStep("Get the Patient Access Details");
        getPracticeAccessApiResponse = orgInfo.getPatientAccess_BusinessLayer("Valid", practiceId);
        validations.verifyPatientAccessGetBL(getPracticeAccessApiResponse, linkAccounts, autoAgeout, defaultAgeout);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24046_Add Patient Access for practice with Invalid URI")
    public void addPatientAccessToPracticeWithInvalidURI() throws IOException {
        logStep("TC_02_Verify the Response code as 404, when user try to give invalid URI for Patient Access using POST Request [BL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        logStep("Prepared Payload to add patient access details for practice : " + practiceId);
        PatientAccessDetailsBusinessLayerObj = createPayload(true, true, 18);
        logStep("Add patient access details for practice : " + practiceId);
        createUpdatePatientAccessApiResponse = orgInfo.createUpdatePracticePatientAccessResponseBL("InValid", practiceId, PatientAccessDetailsBusinessLayerObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(createUpdatePatientAccessApiResponse);
        CCUtils.assertEquals(createUpdatePatientAccessApiResponse.getStatusCode(), 404);
    }

    @Test(priority = 3, dataProvider = "nullAndStringPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "Add Patient Access for Alphanumeric/Null/Empty practice")
    public void addPatientAccessForAlphaNumericNullEmptyPractice(String practiceId, String testCaseName) throws IOException {
        logStep(testCaseName);
        logStep("Prepared Payload to add patient access details for practice : " + practiceId);
        PatientAccessDetailsBusinessLayerObj = createPayload(true, true, 18);
        logStep("Add patient access details for practice : " + practiceId);
        createUpdatePatientAccessApiResponse = orgInfo.createUpdatePracticePatientAccessInvaliDatatypeResponseBL(practiceId, PatientAccessDetailsBusinessLayerObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(createUpdatePatientAccessApiResponse);
        CCUtils.assertEquals(createUpdatePatientAccessApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24057_Add Patient Access for Invalid Method Type")
    public void addPatientAccessToPracticeInvalidMethodType() throws IOException {
        logStep("TC_04_Verify the Response code as 405, when user try to call PATCH method instead of POST Request for Patient Access [BL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        logStep("Prepared Payload to add patient access details for practice : " + practiceId);
        PatientAccessDetailsBusinessLayerObj = createPayload(true, true, 18);
        logStep("Add patient access details for practice : " + practiceId);
        createUpdatePatientAccessApiResponse = orgInfo.createUpdatePracticePatientAccessResponseBL("InValidMethod", practiceId, PatientAccessDetailsBusinessLayerObj);
        logStep("Validating response Code");
        CCUtils.responseTimeValidation(createUpdatePatientAccessApiResponse);
        CCUtils.assertEquals(createUpdatePatientAccessApiResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidMethod(createUpdatePatientAccessApiResponse, "PATCH");
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-24060_Add Patient Access for Invalid payload")
    public void addPatientAccessToInvalidPayload() throws IOException {
        logStep("TC_07_Verify the Response code as 400, when user try to give Valid practice Id and invalid payload for an Patient Access by using POST Request [BL]");
        practiceId = Integer.parseInt(testData.getProperty("PXPP.18282.api.practice.id"));
        linkAccounts = true;
        autoAgeout = true;
        int defaultAgeout = Integer.parseInt(testData.getProperty("patient.access.defaultAgeout"));
        PatientAccessSettingsBusinessLayerObj = new PatientAccessSettingsBusinessLayer(linkAccounts);
        statesList1.clear();
        statesList1.add(testData.getProperty("patient.access.state1"));
        statesList1.add(testData.getProperty("patient.access.state2"));
        statesList1.add(testData.getProperty("patient.access.state3"));
        PatientAccessAgeOutRulesBusinessLayerObj1 = new PatientAccessAgeOutRulesBusinessLayer(18, statesList1);
        statesList2.clear();
        statesList2.add(testData.getProperty("patient.access.state3"));
        statesList2.add(testData.getProperty("patient.access.state4"));
        PatientAccessAgeOutRulesBusinessLayerObj2 = new PatientAccessAgeOutRulesBusinessLayer(17, statesList2);
        PatientAccessAgeOutRulesBusinessLayerList.clear();
        PatientAccessAgeOutRulesBusinessLayerList.add(PatientAccessAgeOutRulesBusinessLayerObj1);
        PatientAccessAgeOutRulesBusinessLayerList.add(PatientAccessAgeOutRulesBusinessLayerObj2);
        PatientAccessAgeoutBusinessLayerObj = new PatientAccessAgeoutBusinessLayer(autoAgeout, defaultAgeout, PatientAccessAgeOutRulesBusinessLayerList);
        PatientGuardianAccessBusinessLayerobj = new PatientGuardianAccessBusinessLayer(defaultAgeout, patientAccessPermissionList);
        patientAccessPermissionObj1 = new PatientAccessPermission(Integer.parseInt(testData.getProperty("patient.access.feature.id1")), testData.getProperty("patient.access.feature.name1"), testData.getProperty("patient.access.permission.fullaccess"));
        patientAccessPermissionObj2 = new PatientAccessPermission(Integer.parseInt(testData.getProperty("patient.access.feature.id2")), testData.getProperty("patient.access.feature.name2"), testData.getProperty("patient.access.permission.viewonly"));
        patientAccessPermissionList.clear();
        patientAccessPermissionList.add(patientAccessPermissionObj1);
        patientAccessPermissionList.add(patientAccessPermissionObj2);
        logStep("Prepared Payload to add patient access details for practice : " + practiceId);
        PatientAccessDetailsBusinessLayerObj = new PatientAccessDetailsBusinessLayer(PatientAccessSettingsBusinessLayerObj, PatientAccessAgeoutBusinessLayerObj, PatientGuardianAccessBusinessLayerobj);
        logStep("Update patient access details for practice : " + practiceId);
        createUpdatePatientAccessApiResponse = orgInfo.createUpdatePracticePatientAccessResponseBL("Valid", practiceId, PatientAccessDetailsBusinessLayerObj);
        logStep("Validating Response Information");
        CCUtils.assertEquals(createUpdatePatientAccessApiResponse.getStatusCode(), 400);
    }

    @Test(enabled = false)
    public PatientAccessDetailsBusinessLayer createPayload(boolean linkAccounts, boolean autoAgeout, int defaultAgeout) throws IOException {
        PatientAccessSettingsBusinessLayerObj = new PatientAccessSettingsBusinessLayer(linkAccounts);
        statesList1.clear();
        statesList1.add(testData.getProperty("patient.access.state1"));
        statesList1.add(testData.getProperty("patient.access.state2"));
        statesList1.add(testData.getProperty("patient.access.state3"));
        PatientAccessAgeOutRulesBusinessLayerObj1 = new PatientAccessAgeOutRulesBusinessLayer(18, statesList1);
        statesList2.clear();
        statesList2.add(testData.getProperty("patient.access.state4"));
        statesList2.add(testData.getProperty("patient.access.state5"));
        statesList2.add(testData.getProperty("patient.access.state6"));
        PatientAccessAgeOutRulesBusinessLayerObj2 = new PatientAccessAgeOutRulesBusinessLayer(16, statesList2);
        statesList3.clear();
        statesList3.add(testData.getProperty("patient.access.state7"));
        PatientAccessAgeOutRulesBusinessLayerObj3 = new PatientAccessAgeOutRulesBusinessLayer(17, statesList3);
        PatientAccessAgeOutRulesBusinessLayerList.clear();
        PatientAccessAgeOutRulesBusinessLayerList.add(PatientAccessAgeOutRulesBusinessLayerObj1);
        PatientAccessAgeOutRulesBusinessLayerList.add(PatientAccessAgeOutRulesBusinessLayerObj2);
        PatientAccessAgeOutRulesBusinessLayerList.add(PatientAccessAgeOutRulesBusinessLayerObj3);
        PatientAccessAgeoutBusinessLayerObj = new PatientAccessAgeoutBusinessLayer(autoAgeout, defaultAgeout, PatientAccessAgeOutRulesBusinessLayerList);
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
        PatientGuardianAccessBusinessLayerobj = new PatientGuardianAccessBusinessLayer(defaultAgeout, patientAccessPermissionList);
        PatientAccessDetailsBusinessLayerObj = new PatientAccessDetailsBusinessLayer(PatientAccessSettingsBusinessLayerObj, PatientAccessAgeoutBusinessLayerObj, PatientGuardianAccessBusinessLayerobj);
        return PatientAccessDetailsBusinessLayerObj;
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
