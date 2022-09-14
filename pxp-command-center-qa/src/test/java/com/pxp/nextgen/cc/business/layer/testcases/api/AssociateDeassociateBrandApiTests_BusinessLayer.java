// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.DuplicateBrandingValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AssociateDeassociateBrandApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private DuplicateBrandingValidations brandingValidations;
	private Validations validation;
	private ClientInfo clientInfo;
	private BrandsInfo brandsInfo;
	private OrgInfo orgInfo;
	int clientId, systemId, enterpriseId, practiceId;
	Response associateDisassociateBrandApiResponse, createClientApiResponse,
			createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			createBrandApiResponse;

	@BeforeClass
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		brandingValidations = new DuplicateBrandingValidations();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		brandsInfo=new BrandsInfo();
		clientId = createClient(ccUser.getClientOneType());
		systemId = createSystemNode(clientId);
		enterpriseId = createEnterpriseNode(systemId);
		practiceId = createPracticeNode(enterpriseId);
	}

	public int createClient(String clientType) throws Exception {
        String clientName = testData.getProperty("api.client.name")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
        String clientSapNumber = testData.getProperty("api.client.sapnumber").concat(CCUtils.generateRandomString());
        createClientApiResponse = clientInfo.createNewClient(testData.getProperty("api.client.active"), clientName,
                clientSapNumber, testData.getProperty("api.client.testclient"),
                Integer.parseInt(testData.getProperty("api.client.type")),
                testData.getProperty("api.client.timezoneid"));
        logStep("Validating response information");
        CCUtils.responseTimeValidation(createClientApiResponse);
        CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
        validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
        return createClientApiResponse.jsonPath().get("clientId");
    }

		public int createSystemNode(int clientId) throws Exception {
				createSystemApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
                testData.getProperty("api.system.orgtype"), "", testData.getProperty("api.system.active"), "", clientId,
                testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
                Integer.parseInt(testData.getProperty("pm.identifier")));
        return createSystemApiResponse.jsonPath().get("orgId");
    }

   public int createEnterpriseNode(int SystemId) throws Exception {
        createEnterpriseApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
                testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
                testData.getProperty("api.enterprise.timezoneid"), systemId,
                testData.getProperty("api.enterprise.parenttype"),
                Integer.parseInt(testData.getProperty("ehr.identifier")),
                Integer.parseInt(testData.getProperty("pm.identifier")));
        return createEnterpriseApiResponse.jsonPath().get("orgId");
    }

   public int createPracticeNode(int enterpriseId) throws Exception {
        createPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
                testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
                testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
                enterpriseId, testData.getProperty("api.practice.parenttype"),
                Integer.parseInt(testData.getProperty("ehr.identifier")),
                Integer.parseInt(testData.getProperty("pm.identifier")));
        return createPracticeApiResponse.jsonPath().get("orgId");
    }
	
	public int createBrandTemplate(int orgId)
	{
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.org.brand.color");
		createBrandApiResponse = brandsInfo.createBrandingTemplate(String.valueOf(orgId), name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		return createBrandApiResponse.jsonPath().get("brandId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to associate and disaasociate branding template at client level")
	public void associateAndDisassociateBrandToClient() throws IOException
	{
		logStep("Create new branding template at Client level");
		int brandId=createBrandTemplate(clientId);
		logStep("associate branding template to Client");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_BL(clientId, brandId, true);
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 200);
		logStep("disassociate Branding template to Client");
		associateDisassociateBrandApiResponse=sharedInfo.associateDeassociateBrand_BL(clientId, brandId, false);
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 200);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to associate and disaasociate branding template at system level")
	public void associateAndDisassociateBrandToSystem() throws IOException
	{
		logStep("Create new branding template at System level");
		int brandId=createBrandTemplate(systemId);
		logStep("associate branding template to System");
		associateDisassociateBrandApiResponse=sharedInfo.associateDeassociateBrand_BL(systemId, brandId, true);
		logStep("Validating response Information");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 200);
		logStep("disasscoiate branding template to system");
		associateDisassociateBrandApiResponse=sharedInfo.associateDeassociateBrand_BL(systemId, brandId, false);
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 200);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to associate and disaasociate branding template at Enterprise level")
	public void associateAndDisassociateBrandToEnterprise() throws IOException
	{
		logStep("Create new branding template at Enterprise level");
		int brandId=createBrandTemplate(enterpriseId);
		logStep("associate branding template to enterprise");
		associateDisassociateBrandApiResponse=sharedInfo.associateDeassociateBrand_BL(enterpriseId, brandId, true);
		logStep("Validating respone information");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 200);
		logStep("disassociate branding template to enterprise");
		associateDisassociateBrandApiResponse=sharedInfo.associateDeassociateBrand_BL(enterpriseId, brandId, false);
		logStep("validating response information");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 200);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to associate and disaasociate branding template at Practice level")
	public void associateAndDisassociateBrandToPractice() throws IOException
	{
		logStep("create new branding template at Practice level");
		int brandId= createBrandTemplate(practiceId);
		logStep("associate branding template to practice");
		associateDisassociateBrandApiResponse=sharedInfo.associateDeassociateBrand_BL(practiceId, brandId, true);
		logStep("validating response information");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 200);
		logStep("disassociate branding template to practice");
		associateDisassociateBrandApiResponse=sharedInfo.associateDeassociateBrand_BL(practiceId, brandId, false);
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 200);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing Org Id and verify error message")
	public void testErrorMessageForNonExistingOrgId() throws Exception {
		logStep("Enter non-existing Org Id");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_BL(
				Integer.parseInt(testData.getProperty("non.existing.org.id")),
				Integer.parseInt(testData.getProperty("brand.id")), false);
		logStep("Validating Response Body");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 400);
		brandingValidations.verifyErrorMessageInResponseForNonExistingOrgIdOfOrg(associateDisassociateBrandApiResponse,
				testData.getProperty("non.existing.org.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing Brand Id and verify error message")
	public void testErrorMessageForNonExistingBrandId() throws Exception {
		logStep("Enter non-existing Brand Id");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_BL(
				Integer.parseInt(testData.getProperty("org.client.id")),
				Integer.parseInt(testData.getProperty("non.existing.brand.id")), false);
		logStep("Validating Response body");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 400);
		brandingValidations.verifyErrorMessageInResponseForNonExistingBrandId(associateDisassociateBrandApiResponse,
				testData.getProperty("non.existing.brand.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter empty payload and verify error message")
	public void testErrorMessageForEmptyPayload() throws Exception {
		logStep("Enter existing Client id");
		associateDisassociateBrandApiResponse = sharedInfo
				.associateDeassociateBrandUsingEmptyPayload_BL(Integer.parseInt(testData.getProperty("org.client.id")));
		logStep("validating Response body");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 400);
		brandingValidations.verifyErrorMessageForEmptyPayload(associateDisassociateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Hit invalid URL and verify error message")
	public void testErrorMessageForInvalidURL() throws Exception {
		logStep("Enter existing Org Id");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrandUsingInvalidUrl_BL(
				Integer.parseInt(testData.getProperty("org.client.id")),
				Integer.parseInt(testData.getProperty("client.brand.id")), true);
		logStep("Validate Response body");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(associateDisassociateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Use call POST instead of PUT and verify error message")
	public void testErrorMessageForInvalidMethodCall() throws Exception {
		logStep("Enter existing Org id");
		associateDisassociateBrandApiResponse = sharedInfo.associateDeassociateBrandUsingInvalidMethodCall_BL(
				Integer.parseInt(testData.getProperty("org.client.id")),
				Integer.parseInt(testData.getProperty("client.brand.id")), false);
		logStep("Validate response body");
		CCUtils.responseTimeValidation(associateDisassociateBrandApiResponse);
		CCUtils.assertEquals(associateDisassociateBrandApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(associateDisassociateBrandApiResponse);
	}
}