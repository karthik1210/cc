// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.PracticeInterfaces;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpdateAnInterfaceApiTests_DataLayer extends BaseRest {
    private OrgInfo orgInfo;
    private ClientInfo clientInfo;
    private Validations validations;
    Response updatePracticeInterfaceApiResponse, addPracticeInterfaceApiResponse, getPracticeInterfaceApiResponse, getByInterfaceResponse, createClientApiResponse, createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse;
    HashMap<String, Object> params = new HashMap<String, Object>();
    HashMap<String, Object> params1 = new HashMap<String, Object>();
    PracticeInterfaces practiceInterfacesObj, practiceInterfacesObj1;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        orgInfo = new OrgInfo();
        clientInfo = new ClientInfo();
        validations = new Validations();
    }

    @DataProvider(name = "invalidPracticeId")
    public Object[][] invalidPracticeId() throws Exception {
        return new Object[][]{{testData.getProperty("Non.existing.practice.id").trim(), "1"}, {testData.getProperty("api.client.id").trim(), "2"}, {testData.getProperty("api.system.id").trim(), "3"}, {testData.getProperty("api.enterprise.id").trim(), "4"}};
    }

    @DataProvider(name = "nullAndStringPracticeId")
    public Object[][] nullAndStringPracticeId() throws Exception {
        return new Object[][]{{testData.getProperty("api.practice.interface.practiceid") + "Sample", "12345XYZ"}, {null, null}, {"", ""}};
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-23459_Update Interface to given practiceId and interfaceId")
    public void updatePracticeInterfaceApiSuccess() throws IOException {
        logStep("TC 001_Verify the Response code as 200 when user try to update An Interface by using PUT Request [DL]");
        ArrayList<Integer> orgIds = createPractice();
        logStep("Create a payload to Add an InterfaceId for a given practiceId");
        int practiceId = orgIds.get(2);
        String nextGenApiId = "NextGenApiId" + orgIds.get(0);
        String nextGenEnterpriseId = "NextGenEnterpriseId" + orgIds.get(1);
        String nextGenPracticeId = "NextGenPracticeId" + orgIds.get(2);
        params.clear();
        params.put("5", nextGenEnterpriseId);
        params.put("6", nextGenPracticeId);
        params.put("7", nextGenApiId);
        int expectedExternalSystemId = Integer.parseInt(testData.getProperty("api.practice.interface.externalSystemId"));
        String expectedName = testData.getProperty("api.practice.interface.name");
        boolean active = true;
        practiceInterfacesObj = new PracticeInterfaces(expectedExternalSystemId, params, active);
        logStep("Add an InterfaceId for a given practiceId");
        addPracticeInterfaceApiResponse = orgInfo.addPracticeInterfacesResponse("Valid", "DL", practiceId, practiceInterfacesObj);
        logStep("Getting InterfaceId information for a given practiceId");
        getPracticeInterfaceApiResponse = orgInfo.getPracticeInterfacesResponse("Valid", "DL", practiceId);
        int interfaceId = getPracticeInterfaceApiResponse.jsonPath().get("interfaceId[0]");
        getByInterfaceResponse = orgInfo.getByInterfaceResponse("Valid", "DL", practiceId, interfaceId);
        CCUtils.assertEquals(getByInterfaceResponse.jsonPath().get("params.key[4]"), 5);
        logStep("Create a payload to update an InterfaceId for a given practiceId");
        String updatedValue = (getByInterfaceResponse.jsonPath().get("params.value[4]")).toString().concat("updated");
        params1.clear();
        params1.put("5", updatedValue);
        practiceInterfacesObj1 = new PracticeInterfaces(expectedExternalSystemId, params1, active);
        logStep("Update an InterfaceId for a given practiceId");
        updatePracticeInterfaceApiResponse = orgInfo.updatePracticeInterfacesResponse("Valid", "DL", practiceId, interfaceId, practiceInterfacesObj1);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePracticeInterfaceApiResponse);
        CCUtils.assertEquals(updatePracticeInterfaceApiResponse.getStatusCode(), 200);
        getByInterfaceResponse = orgInfo.getByInterfaceResponse("Valid", "DL", practiceId, interfaceId);
        String getValue = (getByInterfaceResponse.jsonPath().get("params.value[4]"));
        CCUtils.assertEquals(getValue, updatedValue);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-23460_Update an Interface to given practiceId and interfaceId with Invalid URI")
    public void updatePracticeInterfaceWithInvalidUriApi() throws IOException {
        logStep("TC 002_Verify the Response code as 404, when user try to give invalid URI for an Interface by using PUT Request [DL]");
        int practiceId = Integer.parseInt(testData.getProperty("api.practice.interface.practiceid"));
        logStep("Getting InterfaceId information for a given practiceId");
        getPracticeInterfaceApiResponse = orgInfo.getPracticeInterfacesResponse("Valid", "DL", practiceId);
        int interfaceId = getPracticeInterfaceApiResponse.jsonPath().get("interfaceId[0]");
        getByInterfaceResponse = orgInfo.getByInterfaceResponse("Valid", "DL", practiceId, interfaceId);
        logStep("Create a payload to update an InterfaceId for a given practiceId");
        int expectedExternalSystemId = getByInterfaceResponse.jsonPath().get("externalSystemId");
        String updatedValue = "NextgenPracticeforUpdate";
        String keyPair = getByInterfaceResponse.jsonPath().get("params.key[4]").toString();
        params1.clear();
        params1.put(keyPair, updatedValue);
        practiceInterfacesObj1 = new PracticeInterfaces(expectedExternalSystemId, params1, true);
        logStep("Update an InterfaceId for a given practiceId");
        updatePracticeInterfaceApiResponse = orgInfo.updatePracticeInterfacesResponse("InValid", "DL", practiceId, interfaceId, practiceInterfacesObj1);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePracticeInterfaceApiResponse);
        CCUtils.assertEquals(updatePracticeInterfaceApiResponse.getStatusCode(), 404);
    }

    @Test(priority = 3, dataProvider = "nullAndStringPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-23461_PXPP-23462_PXPP-23464_PXPP-23465_Update Practice Interfaces " + "for null and Invalid Datatype of PracticeId and interfaceId")
    public void updatePracticeInterfacesWithInvalidDatatypePracticeApi(String practiceId, String interfaceId) throws IOException {
        logStep("TC 003_TC 004_TC 006_TC 007_verify response code 400 when user enters null and Invalid Datatype value on practiceId and interfaceId by using PUT Method [DL]");
        logStep("Create a payload to update an InterfaceId for a given practiceId");
        int expectedExternalSystemId = Integer.parseInt(testData.getProperty("api.practice.interface.externalSystemId"));
        String updatedValue = "NextgenPracticeforUpdate";
        params1.clear();
        params1.put("5", updatedValue);
        practiceInterfacesObj1 = new PracticeInterfaces(expectedExternalSystemId, params1, true);
        logStep("Update an InterfaceId for a given practiceId");
        updatePracticeInterfaceApiResponse = orgInfo.updatePracticeInterfacesInvaliDatatypeResponse("DL", practiceId, interfaceId, practiceInterfacesObj1);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePracticeInterfaceApiResponse);
        CCUtils.assertEquals(updatePracticeInterfaceApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-23463_Update an Interface to given practiceId and interfaceId with Invalid Mathod Type")
    public void getPracticeInterfacesWithInvalidMethodApi() throws IOException {
        logStep("TC 005_Verify the Response code as 405, when user try PATCH method instead of PUT Request for An Interface[DL]");
        int practiceId = Integer.parseInt(testData.getProperty("api.practice.interface.practiceid"));
        logStep("Getting InterfaceId information for a given practiceId");
        getPracticeInterfaceApiResponse = orgInfo.getPracticeInterfacesResponse("Valid", "DL", practiceId);
        int interfaceId = getPracticeInterfaceApiResponse.jsonPath().get("interfaceId[0]");
        getByInterfaceResponse = orgInfo.getByInterfaceResponse("Valid", "DL", practiceId, interfaceId);
        logStep("Create a payload to update an InterfaceId for a given practiceId");
        int expectedExternalSystemId = Integer.parseInt(testData.getProperty("api.practice.interface.externalSystemId"));
        String updatedValue = "NextgenPracticeforUpdate";
        String keyPair = getByInterfaceResponse.jsonPath().get("params.key[4]").toString();
        params1.clear();
        params1.put(keyPair, updatedValue);
        practiceInterfacesObj1 = new PracticeInterfaces(expectedExternalSystemId, params1, true);
        logStep("Update an InterfaceId for a given practiceId");
        updatePracticeInterfaceApiResponse = orgInfo.updatePracticeInterfacesResponse("InValidMethod", "DL", practiceId, interfaceId, practiceInterfacesObj1);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePracticeInterfaceApiResponse);
        CCUtils.assertEquals(updatePracticeInterfaceApiResponse.getStatusCode(), 405);
    }

    @Test(priority = 5, dataProvider = "invalidPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-23466_Update Practice Interfaces for Invalid practiceId and interfaceId")
    public void updatePracticeInterfacesWithInvalidPracticeApi(String practiceId, String interfaceId) throws IOException {
        logStep("TC 008_Verify the Response code as 400, when user try to give invalid/Non Existing practice id for An interface by using PUT Request [DL]");
        logStep("Getting InterfaceId information for a given practiceId");
        int expectedExternalSystemId = Integer.parseInt(testData.getProperty("api.practice.interface.externalSystemId"));
        String updatedValue = "NextgenPracticeforUpdate";
        params1.clear();
        params1.put("5", updatedValue);
        practiceInterfacesObj1 = new PracticeInterfaces(expectedExternalSystemId, params1, true);
        logStep("Update an InterfaceId for a given practiceId");
        updatePracticeInterfaceApiResponse = orgInfo.updatePracticeInterfacesInvaliDatatypeResponse("DL", practiceId, interfaceId, practiceInterfacesObj1);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePracticeInterfaceApiResponse);
        CCUtils.assertEquals(updatePracticeInterfaceApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-23467_Update an Interface to given practiceId and interfaceId with Invalid Payload")
    public void updatePracticeInterfaceWithInvalidPayloadApi() throws IOException {
        logStep("TC 009_Verify the Response code as 400, when user try to give Valid practice id ,interface id and invalid /empty payload for An interface by using PUT Request [DL]");
        int practiceId = Integer.parseInt(testData.getProperty("api.practice.interface.practiceid"));
        logStep("Getting InterfaceId information for a given practiceId");
        getPracticeInterfaceApiResponse = orgInfo.getPracticeInterfacesResponse("Valid", "DL", practiceId);
        int interfaceId = getPracticeInterfaceApiResponse.jsonPath().get("interfaceId[0]");
        getByInterfaceResponse = orgInfo.getByInterfaceResponse("Valid", "DL", practiceId, interfaceId);
        logStep("Create a payload to update an InterfaceId for a given practiceId");
        String updatedValue = "NextgenPracticeforUpdate";
        String keyPair = getByInterfaceResponse.jsonPath().get("params.key[4]").toString();
        params1.clear();
        params1.put(keyPair, updatedValue);
        practiceInterfacesObj1 = new PracticeInterfaces(0, params1, true);
        logStep("Update an InterfaceId for a given practiceId");
        updatePracticeInterfaceApiResponse = orgInfo.updatePracticeInterfacesResponse("Valid", "DL", practiceId, interfaceId, practiceInterfacesObj1);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(updatePracticeInterfaceApiResponse);
        CCUtils.assertEquals(updatePracticeInterfaceApiResponse.getStatusCode(), 400);
    }

    @Test(enabled = false)
    public ArrayList<Integer> createPractice() throws IOException {
        logStep("Create nge practice below the client with valid details");
        String clientName = testData.getProperty("api.client.name").concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
        String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
        createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"), clientName, clientSapNumber, testData.getProperty("api.system.testclient"), Integer.parseInt(testData.getProperty("api.client.type")), testData.getProperty("api.client.timezoneid"));
        int clientId = createClientApiResponse.jsonPath().getInt("clientId");

        logStep("Create a new system below client.");
        createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "", testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"), "", Integer.parseInt(testData.getProperty("ehr.identifier")), Integer.parseInt(testData.getProperty("pm.identifier")));
        int systemId = createSystemApiResponse.jsonPath().getInt("orgId");

        logStep("Create a new enterprise below system.");
        createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"), "", testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"), systemId, testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")), Integer.parseInt(testData.getProperty("pm.identifier")));
        int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");

        logStep("Create a new practice below enterprise.");
        createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"), testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"), testData.getProperty("api.practice.orgtype"), enterpriseId, testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")), Integer.parseInt(testData.getProperty("pm.identifier")));
        logStep("Validating Response Information");
        CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
        CCUtils.responseTimeValidation(createPracticeApiResponse);
        validations.verifyCreatePracticeResponseBody(createPracticeApiResponse, testData.getProperty("api.practice.name"));
        int orgId = createPracticeApiResponse.jsonPath().get("orgId");
        ArrayList<Integer> allOrgIds = new ArrayList<Integer>();
        allOrgIds.clear();
        allOrgIds.add(clientId);
        allOrgIds.add(enterpriseId);
        allOrgIds.add(orgId);
        return allOrgIds;
    }
}
