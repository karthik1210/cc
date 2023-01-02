// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

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
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class DeleteBrandDetailsApiTests_DataLayer extends BaseRest {
	private BrandsInfo brandInfo;
	private Validations validation;
	private SharedInfo sharedInfo;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			createAgnosticPracticeApiResponse, createClientApiResponse, createBrandApiResponse,
			deleteBrandingDetailsApiResponse;
	int clientId, systemId, enterpriseId, practiceId, brandId, orgId, brandIdOne, brandIdTwo, brandIdThree;
	String clientName, clientSapNumber;
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		brandInfo = new BrandsInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		clientId = createClient();
		systemId = createSystemNode(clientId);
		enterpriseId = createEnterpriseNode(systemId);
		practiceId = createPracticeNode(enterpriseId);
		brandId = createBrandForClientLevel(clientId);
		brandIdOne = createBrandForSystemLevel(systemId);
		brandIdTwo = createBrandForEntepriseLevel(enterpriseId);
		brandIdThree = createBrandForPracticeLevel(practiceId);
	}

	public int createClient() throws Exception {
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient_DataLayer(ccUser.getActive(), clientName, clientSapNumber,
				"true", Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId(),testData.getProperty("ehr.identifier"),testData.getProperty("pm.identifier"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	public int createSystemNode(int clientId) throws Exception {
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"), "",
				testData.getProperty("api.system.name"), testData.getProperty("api.system.orgtype"), clientId,
				testData.getProperty("api.parenttype"), "", Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createSystemApiResponse.jsonPath().get("orgId");
	}

	public int createEnterpriseNode(int SystemId) throws Exception {
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"), "",
				testData.getProperty("api.enterprise.name"), testData.getProperty("api.enterprise.orgtype"), SystemId,
				testData.getProperty("api.enterprise.parenttype"), testData.getProperty("api.enterprise.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createEnterpriseApiResponse.jsonPath().get("orgId");
	}

	public int createPracticeNode(int enterpriseId) throws Exception {
		createPracticeApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.practice.active"),
				testData.getProperty("api.practice.displayname"), testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), enterpriseId,
				testData.getProperty("api.practice.parenttype"), testData.getProperty("api.practice.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse.jsonPath().get("orgId");
	}

	public int createBrandForClientLevel(int clientId) {
		logStep("Enter valid values in request payload to create branding template at client level");
		String name = "New_Node".concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = "#ffffff";
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(clientId), name, "false", color);
		return createBrandApiResponse.jsonPath().getInt("brandId");
	}

	public int createBrandForSystemLevel(int systemId) {
		logStep("Enter valid values in request payload to create branding template at client level");
		String name = "New_Node".concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = "#ffffff";
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(systemId), name, "false", color);
		return createBrandApiResponse.jsonPath().getInt("brandId");
	}

	public int createBrandForEntepriseLevel(int enterpriseId) {
		logStep("Enter valid values in request payload to create branding template at client level");
		String name = "New_Node".concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = "#ffffff";
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(enterpriseId), name, "false",
				color);
		return createBrandApiResponse.jsonPath().getInt("brandId");
	}

	public int createBrandForPracticeLevel(int practiceId) {
		logStep("Enter valid values in request payload to create branding template at client level");
		String name = "New_Node".concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = "#ffffff";
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(practiceId), name, "false", color);
		return createBrandApiResponse.jsonPath().getInt("brandId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Delete inactive branding template at client level and verify response code")
	public void testResponseCodeForDeleteInactiveBrandingTemplateAtClientLevel() throws IOException {

		deleteBrandingDetailsApiResponse = sharedInfo.deleteBrandDetails_DL(brandId);
		CCUtils.responseTimeValidation(deleteBrandingDetailsApiResponse);
		CCUtils.assertEquals(deleteBrandingDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Delete inactive branding template at system level and verify response code")
	public void testResponseCodeForDeleteInactiveBrandingTemplateAtSystemLevel() throws IOException {
		deleteBrandingDetailsApiResponse = sharedInfo.deleteBrandDetails_DL(brandIdOne);
		CCUtils.responseTimeValidation(deleteBrandingDetailsApiResponse);
		CCUtils.assertEquals(deleteBrandingDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Delete inactive branding template at enterprise level and verify response code")
	public void testResponseCodeForDeleteInactiveBrandingTemplateAtEnterpriseLevel() throws IOException {
		deleteBrandingDetailsApiResponse = sharedInfo.deleteBrandDetails_DL(brandIdTwo);
		CCUtils.responseTimeValidation(deleteBrandingDetailsApiResponse);
		CCUtils.assertEquals(deleteBrandingDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Delete inactive branding template at practice level and verify response code")
	public void testResponseCodeForDeleteInactiveBrandingTemplateAtPracticeLevel() throws IOException {
		deleteBrandingDetailsApiResponse = sharedInfo.deleteBrandDetails_DL(brandIdThree);
		CCUtils.responseTimeValidation(deleteBrandingDetailsApiResponse);
		CCUtils.assertEquals(deleteBrandingDetailsApiResponse.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing branId in brandId field and verify error message")
	public void testErrorResponseForNonExistingBrandId() throws IOException {
		deleteBrandingDetailsApiResponse = sharedInfo
				.deleteBrandDetails_DL(Integer.parseInt(testData.getProperty("non.existing.brand.id")));
		CCUtils.responseTimeValidation(deleteBrandingDetailsApiResponse);
		CCUtils.assertEquals(deleteBrandingDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidBrandId(deleteBrandingDetailsApiResponse,
				Integer.parseInt(testData.getProperty("non.existing.brand.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error message")
	public void testErrorResponseForInvalidUrl() throws IOException {
		deleteBrandingDetailsApiResponse = sharedInfo.deleteBrandDetailsUsingInvalidUrl_DL(brandIdThree);
		CCUtils.responseTimeValidation(deleteBrandingDetailsApiResponse);
		CCUtils.assertEquals(deleteBrandingDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(deleteBrandingDetailsApiResponse);
	}
}
