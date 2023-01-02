//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.DuplicateBrandingValidations;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class AssociateDeassociateBrandApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private DuplicateBrandingValidations brandingValidations;
	private Validations validation;
	Response associateDeassociateBrandApiResponse, getBrandListApiResponse, createBrandApiResponse,
			createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			createAgnosticPracticeApiResponse, createClientApiResponse;
	private BrandsInfo brandInfo;
	int orgId, brandIdone, brandITwo;
	String clientName, clientSapNumber;
	private ClientInfo clientInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		brandingValidations = new DuplicateBrandingValidations();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		brandInfo = new BrandsInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		orgId = createClient();
		brandIdone = CreateBrandOne(orgId);
		brandITwo = CreateBrandTwo(orgId);

	}

	public int createClient() throws Exception {
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient_DataLayer(ccUser.getActive(), clientName, clientSapNumber,
				"true", Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId(),
				testData.getProperty("ehr.identifier"), testData.getProperty("pm.identifier"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	public int CreateBrandOne(int orgId) throws IOException {
		logStep("Enter valid values in request payload to create branding template at client level");
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(orgId), name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		return createBrandApiResponse.jsonPath().get("brandId");
	}

	public int CreateBrandTwo(int orgId) throws IOException {
		logStep("Enter valid values in request payload to create branding template at client level");
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(orgId), name, "false", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		return createBrandApiResponse.jsonPath().get("brandId");
	}

	@Test(priority = 0, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to deactivate branding template")
	public void testDeassociateBrandingTemplateFunctionalityAtOrgLevel() throws Exception {
		logStep("Get branding list");
		getBrandListApiResponse = sharedInfo.getBrandList_DL(orgId);
		logStep("Fetch active template brandId");
		logStep("Change branding template status from active to inactive");
		associateDeassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_DL(orgId, brandIdone, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociateBrandApiResponse);
		CCUtils.assertEquals(associateDeassociateBrandApiResponse.getStatusCode(), 200);
		getBrandListApiResponse = sharedInfo.getBrandList_DL(orgId);
		logStep("Verify branding template status is changed from active to inactive");
		brandingValidations.VerifyActiveFlagStatus(getBrandListApiResponse, false, brandIdone);

	}

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to activate branding template")
	public void testAssociateBrandingTemplateFunctionalityAtOrgLevel() throws Exception {
		logStep("Get branding list");
		getBrandListApiResponse = sharedInfo.getBrandList_DL(orgId);
		logStep("Fetch active template brandId");
		logStep("Change branding template status from inactive to active");
		associateDeassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_DL(orgId, brandITwo, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociateBrandApiResponse);
		CCUtils.assertEquals(associateDeassociateBrandApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing org id and verify error message")
	public void testErrorMessageForNonExistingOrgId() throws Exception {
		logStep("Enter non existing org id");
		associateDeassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_DL(
				Integer.parseInt(testData.getProperty("non.existing.org.id")), brandITwo, false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociateBrandApiResponse);
		CCUtils.assertEquals(associateDeassociateBrandApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing brand id and verify error message")
	public void testErrorMessageForNonExistingBrandId() throws Exception {
		logStep("Enter non existing brand id");
		associateDeassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_DL(
				Integer.parseInt(testData.getProperty("org.client.id")),
				Integer.parseInt(testData.getProperty("non.existing.brand.id")), false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociateBrandApiResponse);
		CCUtils.assertEquals(associateDeassociateBrandApiResponse.getStatusCode(), 400);
		brandingValidations.verifyErrorMessageInResponseForNonExistingBrandId(associateDeassociateBrandApiResponse,
				testData.getProperty("non.existing.brand.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter empty payload and verify error message")
	public void testErrorMessageForBlankPayload() throws Exception {
		logStep("Enter non existing brand id");
		associateDeassociateBrandApiResponse = sharedInfo
				.associateDeassociateBrandUsingEmptyPayload_DL(Integer.parseInt(testData.getProperty("org.client.id")));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociateBrandApiResponse);
		CCUtils.assertEquals(associateDeassociateBrandApiResponse.getStatusCode(), 400);
		brandingValidations.verifyErrorMessageForEmptyPayload(associateDeassociateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to check error if POST is used instead of PUT")
	public void testErrorForInvalidMethodCall() throws IOException {
		logStep("Enter non existing brand id");
		associateDeassociateBrandApiResponse = sharedInfo.associateDeassociateBrandUsingInvalidMethodCall_DL(
				Integer.parseInt(testData.getProperty("org.client.id")),
				Integer.parseInt(testData.getProperty("client.brand.id")), false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociateBrandApiResponse);
		CCUtils.assertEquals(associateDeassociateBrandApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidPostMethodCall(associateDeassociateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorForInvalidUrl() throws IOException {
		logStep("Enter non existing brand id");
		associateDeassociateBrandApiResponse = sharedInfo.associateDeassociateBrandUsingInvalidUrl_DL(
				Integer.parseInt(testData.getProperty("org.client.id")),
				Integer.parseInt(testData.getProperty("client.brand.id")), false);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociateBrandApiResponse);
		CCUtils.assertEquals(associateDeassociateBrandApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(associateDeassociateBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to activate/deactivate in use branding template status from inactive to active")
	public void testAssociateDeassociateInUseBrandFunctionalityAtOrgLevel() throws Exception {
		logStep("Get branding list");
		getBrandListApiResponse = sharedInfo.getBrandList_DL(orgId);
		logStep("Fetch active template brandId");
		logStep("Change branding template status from inactive to active");
		associateDeassociateBrandApiResponse = sharedInfo.associateDeassociateBrand_DL(orgId, brandIdone, true);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(associateDeassociateBrandApiResponse);
		CCUtils.assertEquals(associateDeassociateBrandApiResponse.getStatusCode(), 200);
	}
}
