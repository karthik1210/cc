// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

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

public class GetByInterfaceApiTests_BusinessLayer extends BaseRest {

    private OrgInfo orgInfo;
    private ClientInfo clientInfo;
    private Validations validations;
    Response addPracticeInterfaceApiResponse, getPracticeInterfaceApiResponse, createClientApiResponse, createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse,getByInterfaceResponse;
    HashMap<String, Object> params = new HashMap<String, Object>();
    PracticeInterfaces practiceInterfacesObj;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilder();
        orgInfo = new OrgInfo();
        clientInfo = new ClientInfo();
        validations = new Validations();
    }

    @DataProvider(name = "invalidPracticeIdAndInterfaceId")
    public Object[][] invalidPracticeId() throws Exception {
        return new Object[][]{{testData.getProperty("Non.existing.practice.id").trim(), "1"}, {testData.getProperty("api.client.id").trim(), "2"}, {testData.getProperty("api.system.id").trim(), "3"}, {testData.getProperty("api.enterprise.id").trim(), "4"},
                {testData.getProperty("api.practice.interface.practiceid").trim(),"0000"}, {"0000", "0000"}, {null, "1"}, {testData.getProperty("api.practice.interface.practiceid").trim(), null}, {null, null},
                {testData.getProperty("api.practice.interface.practiceid").trim() + "k","1"}, {testData.getProperty("api.practice.interface.practiceid").trim(), "1k"}, {testData.getProperty("api.practice.interface.practiceid").trim() + "k", "1k"}};
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20089 Get practice interface by interface id using GET")
    public void getByInterface() throws IOException {
        logStep("TC 001_Verify the Response code as 200, when user try to get the Practice interfaces for that interface by using GET Request [BL]");
        ArrayList<Integer> orgIds = createPractice();
        logStep("Create a payload to Add an InterfaceId for a given practiceId");
        int practiceId = orgIds.get(2);
        String nextGenApiId = "NextGenApiId" + orgIds.get(0);
        String nextGenEnterpriseId = "NextGenEnterpriseId" + orgIds.get(1);
        String nextGenPracticeId = "NextGenPracticeId" + orgIds.get(2);
        params.put("5", nextGenEnterpriseId);
        params.put("6", nextGenPracticeId);
        params.put("7", nextGenApiId);
        int expectedExternalSystemId = Integer.parseInt(testData.getProperty("api.practice.interface.externalSystemId"));
        String expectedName = testData.getProperty("api.practice.interface.name");
        boolean active = true;
        practiceInterfacesObj = new PracticeInterfaces(expectedExternalSystemId, params, active);
        logStep("Add an InterfaceId for a given practiceId");
        addPracticeInterfaceApiResponse = orgInfo.addPracticeInterfacesResponse("Valid", "BL", practiceId, practiceInterfacesObj);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(addPracticeInterfaceApiResponse);
        CCUtils.assertEquals(addPracticeInterfaceApiResponse.getStatusCode(), 200);
        getPracticeInterfaceApiResponse = orgInfo.getPracticeInterfacesResponse("Valid", "BL", practiceId);
        int interfaceId = getPracticeInterfaceApiResponse.jsonPath().get("interfaceId[0]");
        int externalSystemId = getPracticeInterfaceApiResponse.jsonPath().get("externalSystemId[0]");
        getByInterfaceResponse = orgInfo.getByInterfaceResponse("Valid", "BL", practiceId, interfaceId);
        CCUtils.responseTimeValidation(getByInterfaceResponse);
        CCUtils.assertEquals(getByInterfaceResponse.getStatusCode(), 200);
        CCUtils.assertEquals(externalSystemId, Integer.parseInt(getByInterfaceResponse.jsonPath().get("externalSystemId").toString()));
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20090 Get practice interface by interface id using GET With Invalid URI")
    public void getByInterfaceWithInvalidURI() throws IOException {
        logStep("TC 002_Verify the Response code as 404, when user try to give invalid URI for GET Call of the practice interfaces [BL]");
        int practiceId = Integer.parseInt(testData.getProperty("api.practice.interface.practiceid"));
        logStep("Getting InterfaceId information for a given practiceId");
        getPracticeInterfaceApiResponse = orgInfo.getPracticeInterfacesResponse("Valid", "BL", practiceId);
        int interfaceId = getPracticeInterfaceApiResponse.jsonPath().get("interfaceId[0]");
        getByInterfaceResponse = orgInfo.getByInterfaceResponse("Invalid", "BL", practiceId, interfaceId);
        CCUtils.responseTimeValidation(getByInterfaceResponse);
        CCUtils.assertEquals(getByInterfaceResponse.getStatusCode(), 404);
        validations.verifyErrorInResponseForInvalidUrl(getByInterfaceResponse);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-20092 Get practice interface by interface id using GET With Invalid PATCH Request instead of GET")
    public void getByInterfaceWithInvalidMethod() throws IOException {
        logStep("TC 004_Verify the Response code as 405, when user try to PATCH method instead of GET Request for practice interfaces [BL]");
        int practiceId = Integer.parseInt(testData.getProperty("api.practice.interface.practiceid"));
        logStep("Getting InterfaceId information for a given practiceId");
        getPracticeInterfaceApiResponse = orgInfo.getPracticeInterfacesResponse("Valid", "BL", practiceId);
        int interfaceId = getPracticeInterfaceApiResponse.jsonPath().get("interfaceId[0]");
        getByInterfaceResponse = orgInfo.getByInterfaceResponse("InvalidMethod", "BL", practiceId, interfaceId);
        CCUtils.responseTimeValidation(getByInterfaceResponse);
        CCUtils.assertEquals(getByInterfaceResponse.getStatusCode(), 405);
        validations.verifyErrorMessageInResponseForInvalidPatchMethodCall(getByInterfaceResponse);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidPracticeIdAndInterfaceId", description = "Get practice interface by with invalid practice id and invalid interface id")
    public void getByInterfaceWithInvalidPracticeIdAndInterfaceId(String practiceId, String interfaceId) throws IOException {
        logStep("When user try with invalid/alphanumeric/null practice id and interface id");
        getByInterfaceResponse = orgInfo.getByInterfaceInvaliDatatypeResponse("BL", practiceId, interfaceId);
        CCUtils.responseTimeValidation(getByInterfaceResponse);
        CCUtils.assertEquals(getByInterfaceResponse.getStatusCode(), 400);
        validations.verifyInvalidRequestValidation(getByInterfaceResponse);
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
                testData.getProperty("api.client.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
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
