// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.LocationsInfo;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.LocationsValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.pxp.cc.model.BaseRest;

import java.io.IOException;

public class GetListRoleLocationsApiTests_BusinessLayer extends BaseRest {
    private LocationsInfo locationsInfo;
    private LocationsValidations locationsValidations;
    private Validations validation;
    Response GetLocationListCCRoleResponse,getCCUserRoleForLocationsNullResponse;

    @BeforeClass()
    public void setBaseUri() throws Exception {
    	setupServiceRequestSpecificationBuilder();
        locationsInfo = new LocationsInfo();
        locationsValidations = new LocationsValidations();
        validation = new Validations();
    }

    /*
	 * Enable test class once below issue is resolved.
	 *  Jira PXPP-20816
	 */
	/*
	 * @Test(retryAnalyzer = RetryAnalyzer.class, description =
	 * "Enter ccuserid , orgid to get list of locations and verify status code and response body"
	 * ) public void testGetListLocationForCCUserRole() throws IOException {
	 * logStep("Enter ccuserId , orgId"); int ccUserId =
	 * Integer.parseInt(testData.getProperty("pxpp.17575.cc.userid")); int Org_Id =
	 * Integer.parseInt(testData.getProperty("pxpp.17575.cc.orgid"));
	 * GetLocationListCCRoleResponse =
	 * locationsInfo.getCCUserRoleForLocations(ccUserId, Org_Id);
	 * logStep("Validating response information");
	 * CCUtils.responseTimeValidation(GetLocationListCCRoleResponse);
	 * CCUtils.assertEquals(GetLocationListCCRoleResponse.getStatusCode(), 200);
	 * locationsValidations.verifyGetCCRoleLocationsValidInput(
	 * GetLocationListCCRoleResponse,testData.getProperty("pxpp.17575.cc.roleid"),
	 * testData.getProperty("pxpp.17575.cc.name"));
	 * 
	 * }
	 */

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter ccuserid , orgid which are not available in system and verify status code and response body")
    public void testGetLocationListFornonExistingUserIdandOrgId() throws IOException {
        logStep("Enter ccuserid , orgid which are not available in system");
        int ccUserId = Integer.parseInt(testData.getProperty("pxpp.17575.cc.nonuserid"));
        int Org_Id = Integer.parseInt(testData.getProperty("pxpp.17575.cc.nonorgid"));
        GetLocationListCCRoleResponse = locationsInfo.getCCUserRoleForLocations(ccUserId, Org_Id);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(GetLocationListCCRoleResponse);
        CCUtils.assertEquals(GetLocationListCCRoleResponse.getStatusCode(), 204);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter ccuserid which is not available and orgid is available in system and verify status code and response body")
    public void testGetLocationListFornonExistingUserIdandExistsOrgId() throws IOException {
        logStep("Enter ccuserid which is not available and orgid is available in system");
        int ccUserId = Integer.parseInt(testData.getProperty("pxpp.17575.cc.nonuserid"));
        int Org_Id = Integer.parseInt(testData.getProperty("pxpp.17575.cc.orgid"));
        GetLocationListCCRoleResponse = locationsInfo.getCCUserRoleForLocations(ccUserId, Org_Id);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(GetLocationListCCRoleResponse);
        CCUtils.assertEquals(GetLocationListCCRoleResponse.getStatusCode(), 204);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter orgid which is not available and ccuserid is available in system and verify status code and response body")
    public void testGetLocationListForExistingUserIdandnonExistsOrgId() throws IOException {
        logStep("Enter orgid which is not available and ccuserid is available in system");
        int ccUserId = Integer.parseInt(testData.getProperty("pxpp.17575.cc.userid"));
        int Org_Id = Integer.parseInt(testData.getProperty("pxpp.17575.cc.nonorgid"));
        GetLocationListCCRoleResponse = locationsInfo.getCCUserRoleForLocations(ccUserId, Org_Id);
        logStep("Validating response information");
        CCUtils.responseTimeValidation(GetLocationListCCRoleResponse);
        CCUtils.assertEquals(GetLocationListCCRoleResponse.getStatusCode(), 204);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error massage for null CCUserid & orgId")
    public void testForNullValuesofGetCCRoleLocation() throws IOException {
        logStep("Enter null values to CCUserid & orgId");
        getCCUserRoleForLocationsNullResponse = locationsInfo.getCCUserRoleForLocationsNullValues();
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getCCUserRoleForLocationsNullResponse);
        validation.verifyInvalidRequestValidation(getCCUserRoleForLocationsNullResponse);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter ccuserid as alphanumeric and verify status code and response body")
    public void testGetLocationListForAlphanumUserIdandExistsOrgId() throws IOException {
        logStep("Enter ccuserid as alphanumeric");
        getCCUserRoleForLocationsNullResponse = locationsInfo.getCCUserRoleForLocationsAlphaNumValues();
        logStep("Validating response information");
        CCUtils.responseTimeValidation(getCCUserRoleForLocationsNullResponse);
        validation.verifyInvalidRequestValidation(getCCUserRoleForLocationsNullResponse);
    }

     @Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of GET")
    public void testErrorForInvalidMethodCall() throws IOException {
        logStep("Enter existing Userid and orgid");
         int ccUserId = Integer.parseInt(testData.getProperty("pxpp.17575.cc.userid"));
         int orgId= Integer.parseInt(testData.getProperty("pxpp.17575.cc.orgid"));
         GetLocationListCCRoleResponse = locationsInfo.getCCUserRoleForLocationsInvaliMethodCall(ccUserId, orgId);
         logStep("Validating response information");
        CCUtils.responseTimeValidation(GetLocationListCCRoleResponse);
        CCUtils.assertEquals(GetLocationListCCRoleResponse.getStatusCode(), 405);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter Incorrect URI and verify status code and response body")
    public void testGetLocationListForInvalidRequest() throws IOException {
        logStep("Enter Incorrect URI ");
        GetLocationListCCRoleResponse = locationsInfo.getCCUserRoleForLocationsInvalid();
        logStep("Validating response information");
        CCUtils.responseTimeValidation(GetLocationListCCRoleResponse);
        validation.verifyErrorInResponseForInvalidUrl(GetLocationListCCRoleResponse);
    }


}
