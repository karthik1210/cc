// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class CreateUpdatePracticePatientAccessApiTests_DataLayer extends BaseRest {
    private OrgInfo orgInfo;
    private Validations validations;
    private CreateNGHierachyClientCreation_DataLayer createPracticeObj;
    Response createUpdatePatientAccessApiResponse;
    int practiceId;

    @BeforeClass()
    public void setBaseUri() throws Exception {
        setupServiceRequestSpecificationBuilderForDataLayer();
        orgInfo = new OrgInfo();
        validations = new Validations();
        createPracticeObj=new CreateNGHierachyClientCreation_DataLayer();
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "PXPP-21242_Add Draft tou for practice")
    public void addDraftTouToPracticeSuccess() throws IOException {
        logStep("TC_01_Verify the Response code as 200, when user try to add Privacy settings by using POST Request [BL/DL]");

        practiceId = createPracticeObj.createPractice();

    }
}
