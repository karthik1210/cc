package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CreateNGHierachyClientCreation_DataLayer extends BaseRest {

    private OrgInfo orgInfo;
    private Validations validations;
    Response  fetchOrgSolutionDetailsApiResponse, updateSolutionsApiResponse, createClientApiResponse, createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse;
    int practiceId;
    private ClientInfo clientInfo;
    String clientName, clientSapNumber, testFlag, clientTimezoneId;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        orgInfo = new OrgInfo();
        clientInfo = new ClientInfo();
        validations = new Validations();
    }

    @Test(enabled=true)
       public int createPractice() throws IOException {
        logStep("Create nge practice below the client with valid details");
        clientName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.client.name")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5))));
        clientSapNumber = CCUtils
                .getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.client.sapnumber")
                        .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5))));
        testFlag = testData.getProperty("api.client.testclient");
        clientTimezoneId = testData.getProperty("api.client.timezoneid");
        createClientApiResponse = clientInfo.createNewClient_DataLayer(testData.getProperty("api.client.active"),
                clientName, clientSapNumber, testFlag, Integer.parseInt(testData.getProperty("api.client.type")),
                clientTimezoneId);
        int clientId = createClientApiResponse.jsonPath().getInt("clientId");

        logStep("Create a new system below client.");
        createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "",
                testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"), clientId,
                testData.getProperty("api.parenttype"), "", Integer.parseInt(testData.getProperty("ehr.identifier")),
                Integer.parseInt(testData.getProperty("pm.identifier")));
        int systemId = createSystemApiResponse.jsonPath().getInt("orgId");

        logStep("Create a new enterprise below system.");
        createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"), "",
                testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"), systemId,
                testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
                Integer.parseInt(testData.getProperty("ehr.identifier")),
                Integer.parseInt(testData.getProperty("pm.identifier")));
        int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");

        logStep("Create a new practice below enterprise.");
        createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
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
        return orgId;
    }
}
