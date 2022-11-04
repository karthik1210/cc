// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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
    Response fetchOrgSolutionDetailsApiResponse, updateSolutionsApiResponse, createClientApiResponse, createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse;
    int practiceId;
    private ClientInfo clientInfo;
    String clientName, clientSapNumber, testFlag, clientTimezoneId;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
    }

    @Test(enabled = true)
    public int createPractice(String status, String clientName, String clientSapNumber, String testFlag, int clientType, String clientTimezoneId, String systemName, String sysOrgType, String sysParentType, int ehrIdentifier, int pmIdentifier, String enterpriseName, String enterpriseType, String enterpriseParentType, String enterpriseTimeZone, String practiceDisplayName, String practiceName, String practiceOrgType, String practiceParentType, String practiceTimeZone) throws IOException {
        logStep("Create nge practice below the client with valid details");
        orgInfo = new OrgInfo();
        clientInfo = new ClientInfo();
        validations = new Validations();
        createClientApiResponse = clientInfo.createNewClient_DataLayer(status, clientName, clientSapNumber, testFlag, clientType, clientTimezoneId);
        int clientId = createClientApiResponse.jsonPath().getInt("clientId");

        logStep("Create a new system below client.");
        createSystemApiResponse = orgInfo.createOrg_DataLayer(status, "", systemName, sysOrgType, clientId, sysParentType, "", ehrIdentifier, pmIdentifier);
        int systemId = createSystemApiResponse.jsonPath().getInt("orgId");

        logStep("Create a new enterprise below system.");
        createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(status, "", enterpriseName, enterpriseType, systemId, enterpriseParentType, enterpriseTimeZone, ehrIdentifier, pmIdentifier);
        int enterpriseId = createEnterpriseApiResponse.jsonPath().getInt("orgId");

        logStep("Create a new practice below enterprise.");
        createPracticeApiResponse = orgInfo.createOrg_DataLayer(status, practiceDisplayName, practiceName, practiceOrgType, enterpriseId, practiceParentType, practiceTimeZone, ehrIdentifier, pmIdentifier);
        logStep("Validating Response Information");
        CCUtils.assertEquals(createPracticeApiResponse.getStatusCode(), 200);
        CCUtils.responseTimeValidation(createPracticeApiResponse);
        validations.verifyCreatePracticeResponseBody(createPracticeApiResponse, practiceName);
        int orgId = createPracticeApiResponse.jsonPath().get("orgId");
        return orgId;
    }
}

