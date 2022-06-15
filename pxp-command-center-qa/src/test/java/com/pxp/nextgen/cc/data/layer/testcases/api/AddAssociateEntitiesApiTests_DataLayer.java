//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.UserInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AddAssociateEntitiesApiTests_DataLayer extends BaseRest {
	     private UserInfo userInfo;
	     private Validations validation;
	     Response addAssociatedEntitiesResponse, fetchAssociateEntitiesApiResponse;
	     int userId, pxp_cc_user;
		
		@BeforeClass()
		public void setBaseUri() throws Exception {
			setupServiceRequestSpecificationBuilderForDataLayer();
			userInfo = new UserInfo();
			validation = new Validations();
		}
	
		@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing userID, pxp-cc-user & existing OrgIDs  and fetch user by using useId verify status code")
		public void testAddAssociateEntitiesFunctionalityUsingValidData() throws IOException {
			    ArrayList<Object> OrgIDs = new ArrayList<Object>();
				OrgIDs.add(testData.getProperty("org.id.two"));
				OrgIDs.add(testData.getProperty("org.id.three"));;
				int userId = Integer.parseInt(testData.getProperty("existing.user.id"));
				int pxp_cc_user = Integer.parseInt(testData.getProperty("pxpp.cc.user.id"));
				addAssociatedEntitiesResponse = userInfo.addAssociateEntities_DL(userId, OrgIDs, pxp_cc_user);
				logStep("Validating response information");
				CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
				CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 200);
				fetchAssociateEntitiesApiResponse = userInfo.getAssociateEntities_DL(userId, pxp_cc_user);
				CCUtils.responseTimeValidation(fetchAssociateEntitiesApiResponse);
				CCUtils.assertEquals(fetchAssociateEntitiesApiResponse.getStatusCode(), 200);
				validation.getAssociatedEntities(fetchAssociateEntitiesApiResponse, OrgIDs);

			}
				
		@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing userId in userId field, pxp_cc_user in pxp-cc-user field & invalid value in orgId and verify error message for orgId field")
		public void testErrorMessageForInvalidValueForOrgIdField() throws IOException {
				ArrayList<Object> OrgIDs = new ArrayList<Object>();
				OrgIDs.add(testData.getProperty("invalid.org.id"));
                int userId = Integer.parseInt(testData.getProperty("existing.user.id"));
				int pxp_cc_user = Integer.parseInt(testData.getProperty("pxpp.cc.user.id"));
				addAssociatedEntitiesResponse = userInfo.addAssociateEntities_DL(userId, OrgIDs, pxp_cc_user);
				logStep("Validating response information");
				CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
				CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 400);
				validation.verifyErrorInResponseForInvalidValueForOrgIdField(addAssociatedEntitiesResponse);
		}
		
		@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify error message for non existing userId in userId field")
		public void testErrorMessageForNonExistingUserId() throws IOException {
				ArrayList<Object> OrgIDs = new ArrayList<Object>();
				OrgIDs.add(testData.getProperty("org.id.one"));
				int userId = Integer.parseInt(testData.getProperty("nonexisting.user.id"));
				int pxp_cc_user = Integer.parseInt(testData.getProperty("pxpp.cc.user.id"));
				addAssociatedEntitiesResponse = userInfo.addAssociateEntities_DL(userId, OrgIDs, pxp_cc_user );
				logStep("Validating response information");
				CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
				CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 400);
				validation.verifyErrorInResponseForNullValueForNonExistingUserId(addAssociatedEntitiesResponse);
			}
				
		@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
		public void testErrorMessageInvalidUrl() throws IOException {
				ArrayList<Object> OrgIDs = new ArrayList<Object>();
				OrgIDs.add(testData.getProperty("org.id.one"));
				int userId = Integer.parseInt(testData.getProperty("existing.user.id"));
				int pxp_cc_user = Integer.parseInt(testData.getProperty("pxpp.cc.user.id"));
				addAssociatedEntitiesResponse = userInfo.addAssociateEntitiesInvalidUrl_DL(userId, OrgIDs, pxp_cc_user);
				logStep("Validating response information");
				CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
				CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 404);
				validation.verifyErrorInResponseForInvalidUrl(addAssociatedEntitiesResponse);
			}
			
	    @Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if PUT is used instead of POST")
		public void testErrorForInvalidMethodCall() throws IOException {
				ArrayList<Object> OrgIDs = new ArrayList<Object>();
				OrgIDs.add(testData.getProperty("org.id.one"));
				int userId = Integer.parseInt(testData.getProperty("existing.user.id"));
				int pxp_cc_user = Integer.parseInt(testData.getProperty("pxpp.cc.user.id"));
				addAssociatedEntitiesResponse = userInfo.invalidMethodCall_DL(userId, OrgIDs, pxp_cc_user );
				logStep("Validating response information");
				CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
				CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 405);
				validation.verifyErrorMessageInResponseForInvalidMethod(addAssociatedEntitiesResponse);
			}
			
		@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing userId in userId field, pxp_cc_user in pxp-cc-user field & null values in orgId and Verify error message for orgId field")
		public void testErrorMessageForNullValueForOrgIdField() throws IOException {
				ArrayList<Object> OrgIDs = new ArrayList<Object>();
				int userId = Integer.parseInt(testData.getProperty("existing.user.id"));
				int pxp_cc_user = Integer.parseInt(testData.getProperty("pxpp.cc.user.id"));
				addAssociatedEntitiesResponse = userInfo.addAssociateEntities_DL(userId, OrgIDs, pxp_cc_user );
				logStep("Validating response information");
				CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
				CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 400);
				validation.verifyErrorInResponseForNullValueForOrgIdField(addAssociatedEntitiesResponse);
			}
			
		@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing userID, pxp-cc-user & nonexisting org id and Verify error response for orgId field")
		public void testErrorMessageForExistingUserIdAndNonExistingOrgId() throws IOException {
				ArrayList<Object> OrgIDs = new ArrayList<Object>();
				int userId = Integer.parseInt(testData.getProperty("non.existing.org.id"));
                int pxp_cc_user = Integer.parseInt(testData.getProperty("pxpp.cc.user.id"));
				addAssociatedEntitiesResponse = userInfo.addAssociateEntities_DL(userId, OrgIDs, pxp_cc_user);
				logStep("Validating response information");
				CCUtils.responseTimeValidation(addAssociatedEntitiesResponse);
				CCUtils.assertEquals(addAssociatedEntitiesResponse.getStatusCode(), 400);
			}
}
