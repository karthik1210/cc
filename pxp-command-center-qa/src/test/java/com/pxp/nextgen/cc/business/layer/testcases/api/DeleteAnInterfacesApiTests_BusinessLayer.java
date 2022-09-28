// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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

public class DeleteAnInterfacesApiTests_BusinessLayer extends BaseRest {
    private OrgInfo orgInfo;
    private ClientInfo clientInfo;
    private Validations validations;
    Response deletePracticeInterfaceApiResponse, addPracticeInterfaceApiResponse, getPracticeInterfaceApiResponse, getByInterfaceResponse, createClientApiResponse, createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse;
    HashMap<String, Object> params = new HashMap<String, Object>();
    PracticeInterfaces practiceInterfacesObj;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilder();
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

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19958_delete Interface to given practiceId and interfaceId")
    public void deletePracticeInterfaceApiSuccess() throws IOException {
        logStep("TC 001_Verify the Response code as 200, when user try to delete the practice interface by using DELETE Request [BL]");
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
        addPracticeInterfaceApiResponse = orgInfo.addPracticeInterfacesResponse("Valid", "BL", practiceId, practiceInterfacesObj);
        logStep("Getting InterfaceId information for a given practiceId");
        getPracticeInterfaceApiResponse = orgInfo.getPracticeInterfacesResponse("Valid", "BL", practiceId);
        int interfaceId = getPracticeInterfaceApiResponse.jsonPath().get("interfaceId[0]");
        logStep("Delete an InterfaceId for a given practiceId");
        deletePracticeInterfaceApiResponse = orgInfo.deleteAnInterfaceResponse("Valid", "BL", practiceId, interfaceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePracticeInterfaceApiResponse);
        CCUtils.assertEquals(deletePracticeInterfaceApiResponse.getStatusCode(), 200);
        getByInterfaceResponse = orgInfo.getByInterfaceResponse("Valid", "BL", practiceId, interfaceId);
        if (getByInterfaceResponse.getStatusCode() == 400)
            logStep("Validating that interface is deleted successfully for " + interfaceId);
        else
            logStep("Validating that interface is not deleted for " + interfaceId);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19959_delete an Interface to given practiceId and interfaceId with Invalid URI")
    public void deletePracticeInterfaceWithInvalidUriApi() throws IOException {
        logStep("TC 002_Verify the Response code as 404, when user try to give invalid URI for DELETE call of the practice interfaces for Interface SetUp [BL]");
        int practiceId = Integer.parseInt(testData.getProperty("api.practice.interface.practiceid"));
        logStep("Getting InterfaceId information for a given practiceId");
        getPracticeInterfaceApiResponse = orgInfo.getPracticeInterfacesResponse("Valid", "BL", practiceId);
        int interfaceId = getPracticeInterfaceApiResponse.jsonPath().get("interfaceId[0]");
        getByInterfaceResponse = orgInfo.getByInterfaceResponse("Valid", "BL", practiceId, interfaceId);
        logStep("delete an InterfaceId for a given practiceId");
        deletePracticeInterfaceApiResponse = orgInfo.deleteAnInterfaceResponse("InValid", "BL", practiceId, interfaceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePracticeInterfaceApiResponse);
        CCUtils.assertEquals(deletePracticeInterfaceApiResponse.getStatusCode(), 404);
    }

    @Test(priority = 3, dataProvider = "nullAndStringPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19960_PXPP-19962_delete Practice Interfaces for null and Invalid Datatype of PracticeId and interfaceId")
    public void deletePracticeInterfacesWithInvalidDatatypePracticeApi(String practiceId, String interfaceId) throws IOException {
        logStep("TC 003_TC 005_verify response code 400 when user enters null and Invalid Datatype value on practiceId and interfaceId by using DELETE Method [BL]");
        logStep("delete an InterfaceId for a given practiceId");
        deletePracticeInterfaceApiResponse = orgInfo.deleteAnInterfaceInvaliDatatypeResponse("BL", practiceId, interfaceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePracticeInterfaceApiResponse);
        CCUtils.assertEquals(deletePracticeInterfaceApiResponse.getStatusCode(), 400);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19961_delete an Interface to given practiceId and interfaceId with Invalid Mathod Type")
    public void deletePracticeInterfacesWithInvalidMethodApi() throws IOException {
        logStep("TC 004_Verify the Response code as 405, when user try to Patch method instead of DELETE Request for practice interfaces [BL]");
        int practiceId = Integer.parseInt(testData.getProperty("api.practice.interface.practiceid"));
        logStep("Getting InterfaceId information for a given practiceId");
        getPracticeInterfaceApiResponse = orgInfo.getPracticeInterfacesResponse("Valid", "BL", practiceId);
        int interfaceId = getPracticeInterfaceApiResponse.jsonPath().get("interfaceId[0]");
        getByInterfaceResponse = orgInfo.getByInterfaceResponse("Valid", "BL", practiceId, interfaceId);
        logStep("delete an InterfaceId for a given practiceId");
        deletePracticeInterfaceApiResponse = orgInfo.deleteAnInterfaceResponse("InValidMethod", "BL", practiceId, interfaceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePracticeInterfaceApiResponse);
        CCUtils.assertEquals(deletePracticeInterfaceApiResponse.getStatusCode(), 405);
    }

    @Test(priority = 5, dataProvider = "invalidPracticeId", retryAnalyzer = RetryAnalyzer.class, description = "PXPP-19963_delete Practice Interfaces for Invalid/nonexisting practiceId and interfaceId")
    public void deletePracticeInterfacesWithInvalidPracticeApi(String practiceId, String interfaceId) throws IOException {
        logStep("TC 006_Verify the Response code as 400, when user try to give invalid/non existing practice id and interface Id for practice interfaces by using DELETE Request[BL]");
        logStep("delete an InterfaceId for a given practiceId");
        deletePracticeInterfaceApiResponse = orgInfo.deleteAnInterfaceInvaliDatatypeResponse("BL", practiceId, interfaceId);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(deletePracticeInterfaceApiResponse);
        CCUtils.assertEquals(deletePracticeInterfaceApiResponse.getStatusCode(), 400);
    }

    @Test(enabled = false)
    public ArrayList<Integer> createPractice() throws IOException {
        logStep("Create nge practice below the client with valid details");
        String clientName = testData.getProperty("api.client.name")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
        String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
        createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"),
                clientName, clientSapNumber, testData.getProperty("api.system.testclient"),
                Integer.parseInt(testData.getProperty("api.client.type")),
                testData.getProperty("api.client.timezoneid"));
        int clientId = createClientApiResponse.jsonPath().getInt("clientId");

        logStep("Create a new system below client.");
        createSystemApiResponse = orgInfo.createOrg(testData.getProperty("api.system.active"),
                testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
                testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
                testData.getProperty("api.system.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")),
                Integer.parseInt(testData.getProperty("pm.identifier")));
        int systemId = createSystemApiResponse.jsonPath().getInt("orgId");

        logStep("Create a new enterprise below system.");
        createEnterpriseApiResponse = orgInfo.createOrg(testData.getProperty("api.enterprise.active"),
                testData.getProperty("api.enterprise.displayname"), testData.getProperty("api.enterprise.name"),
                testData.getProperty("api.enterprise.orgtype"), systemId,
                testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
                Integer.parseInt(testData.getProperty("ehr.identifier")),
                Integer.parseInt(testData.getProperty("pm.identifier")));
        int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");

        logStep("Create a new practice below enterprise.");
        createPracticeApiResponse = orgInfo.createOrg(testData.getProperty("api.practice.active"),
                testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
                testData.getProperty("api.practice.orgtype"), enterpriseId,
                testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
                Integer.parseInt(testData.getProperty("ehr.identifier")),
                Integer.parseInt(testData.getProperty("pm.identifier")));
        logStep("Validating Response Information");
        CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
        CCUtils.responseTimeValidation(createPracticeApiResponse);
        validations.verifyCreatePracticeResponseBody(createPracticeApiResponse,
                testData.getProperty("api.practice.name"));
        int orgId = createPracticeApiResponse.jsonPath().get("orgId");
        ArrayList<Integer> allOrgIds = new ArrayList<Integer>();
        allOrgIds.clear();
        allOrgIds.add(clientId);
        allOrgIds.add(enterpriseId);
        allOrgIds.add(orgId);
        return allOrgIds;
    }
}
