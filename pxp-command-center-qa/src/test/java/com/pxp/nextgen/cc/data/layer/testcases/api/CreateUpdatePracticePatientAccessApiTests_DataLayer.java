// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;


import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CreateUpdatePracticePatientAccessApiTests_DataLayer extends BaseRest {

    private CreateNGHierachyClientCreation_DataLayer createPracticeObj;
    Response createUpdatePatientAccessApiResponse, fetchOrgSolutionDetailsApiResponse, updateSolutionsApiResponse, createClientApiResponse, createPracticeApiResponse, createSystemApiResponse, createEnterpriseApiResponse;
    int practiceId;
    String clientName, clientSapNumber, testFlag, clientTimezoneId;
    private OrgInfo orgInfo;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        createPracticeObj=new CreateNGHierachyClientCreation_DataLayer();
        orgInfo = new OrgInfo();
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-21242_Add Draft tou for practice")
    public void addDraftTouToPracticeSuccess() throws Exception {
        clientName = CCUtils.getName(CCUtils.generateStringWithSpace(testData.getProperty("api.client.name")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5))));
        clientSapNumber = CCUtils
                .getSapNumber(CCUtils.generateStringWithSpace(testData.getProperty("api.client.sapnumber")
                        .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5))));
        testFlag = testData.getProperty("api.client.testclient");
        clientTimezoneId = testData.getProperty("api.client.timezoneid");
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
        practiceId = createPracticeObj.createPractice(status,clientName,clientSapNumber,testFlag,clientType,clientTimezoneId,systemName, orgType, parentType,
                ehrIdentifier, pmIdentifier, enterpriseName, enterpriseType,enterpriseParentType, enterpriseTimeZone,practiceDisplayName, practiceName,
                practiceOrgType,practiceParentType, practiceTimeZone);

        log(String.valueOf(practiceId));

    }
}
