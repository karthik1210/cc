// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.*;
import org.testng.annotations.*;
import io.restassured.response.Response;
import java.io.*;

public class CreateBrandApiTests_DataLayer extends BaseRest {
	private BrandsInfo brandInfo;
	private Validations validation;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			createAgnosticPracticeApiResponse, createClientApiResponse, createBrandApiResponse;
	int clientId, systemId, enterpriseId, practiceId, agnosticPracticeId;
	String clientName, clientSapNumber;
	private ClientInfo clientInfo;
	private OrgInfo orgInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		brandInfo = new BrandsInfo();
		new SharedInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		clientId = createClient();
		systemId = createSystemNode(clientId);
		enterpriseId = createEnterpriseNode(systemId);
		practiceId = createPracticeNode(enterpriseId);
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template at Client Level and fetch newly created brand details using brandId")
	public void testCreateBrandAtClientLevel() throws IOException {
		logStep("Enter valid values in request payload to create branding template at client level");
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(clientId), name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		validation.verifyUpdateBrandingDetails(createBrandApiResponse, brandId, name);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template at System Level and fetch newly created brand details using brandId")
	public void testCreateBrandAtSystemLevel() throws IOException {
		logStep("Enter valid values in request payload to create branding template at system level");
		String name = testData.getProperty("system.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("system.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(systemId), name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		validation.verifyUpdateBrandingDetails(createBrandApiResponse, brandId, name);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template at Enterprise Level and fetch newly created brand details using brandId")
	public void testCreateBrandAtEnterpriseLevel() throws IOException {
		logStep("Enter valid values in request payload to create branding template at enterprise level");
		String name = testData.getProperty("enterprise.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("enterprise.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(enterpriseId), name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		validation.verifyUpdateBrandingDetails(createBrandApiResponse, brandId, name);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template at Practice Level and fetch newly created brand details using brandId")
	public void testCreateBrandAtPracticeLevel() throws IOException {
		logStep("Enter valid values in request payload to create branding template at Practice level");
		String name = testData.getProperty("practice.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("practice.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(practiceId), name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		validation.verifyUpdateBrandingDetails(createBrandApiResponse, brandId, name);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template with null values in color field")
	public void testCreateBrandWithColorFieldAsNull() throws IOException {
		logStep("Enter valid values in request payload with null values in color field to create branding template");
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(clientId), name, "true", "    ");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		validation.verifyUpdateBrandingDetails(createBrandApiResponse, brandId, name);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template with null values in payload and verify error response code")
	public void testCreateBrandWithNullValuesInPayload() throws IOException {
		logStep("Enter null values in request payload");
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template with null values in name field and verify error response code")
	public void testCreateBrandWithNameFieldAsNull() throws IOException {
		logStep("Enter null values in name field in request payload");
		String orgId = testData.getProperty("client.org.id");
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(orgId, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template with null values in orgId field and verify error response code")
	public void testCreateBrandWithOrgIdFieldAsNull() throws IOException {
		logStep("Enter null values in orgId field in request payload");
		String name = testData.getProperty("client.org.brand.name");
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(null, name, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 400);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check error response code for invalid Url")
	public void testErrorResponseCodeForInvalidUrl() throws NullPointerException, IOException {
		logStep("Enter valid values in request payload to create branding template");
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplateInvalidUrl_DL(String.valueOf(clientId), name, "true",
				color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(createBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check error response code for invalid method call")
	public void testErrorResponseCodeForInvalidMethodCall() throws NullPointerException, IOException {
		logStep("Enter valid values in request payload to create branding template");
		String name = testData.getProperty("client.org.brand.name");
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplateInvalidMethodCall_DL(String.valueOf(clientId), name,
				"true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 405);
		validation.verifyErrorMessageInResponseForInvalidMethod(createBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check Version Name length validation and validate response code")
	public void testNameFieldLengthValidation() throws IOException, NullPointerException {
		logStep("Enter valid values in request payload to create branding template");
		String color = testData.getProperty("client.brand.color");
		logStep("Version Name length validation with 50 characters");
		String name = "AutoBrand".concat(CCUtils.generateAlphaNumericString(41));
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(clientId), name, "true", color);
		logStep("Validating response Code");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		logStep("Version Name length validation with 51 characters");
		name = "AutoBrand".concat(CCUtils.generateAlphaNumericString(51));
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(clientId), name, "true", color);
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 400);
		validation.verifyInvalidRequestValidation(createBrandApiResponse);
	}
}
