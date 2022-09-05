// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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

public class CreateBrandingTemplateApiTests_BusinessLayer extends BaseRest {
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
		setupServiceRequestSpecificationBuilder();
		brandInfo = new BrandsInfo();
		new SharedInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		orgInfo = new OrgInfo();
		clientId = createClient();
		systemId = createSystemNode(clientId);
		enterpriseId = createEnterpriseNode(systemId);
		practiceId = createPracticeNode(enterpriseId);
		agnosticPracticeId = createAgnosticPracticeNode(systemId);
	}

	public int createClient() throws Exception {
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient(ccUser.getActive(), clientName, clientSapNumber, "false",
				Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	public int createSystemNode(int clientId) throws Exception {
		createSystemApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), testData.getProperty("api.system.displayname"), "",
				testData.getProperty("api.system.active"), "", clientId, testData.getProperty("api.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createSystemApiResponse.jsonPath().get("orgId");
	}

	public int createEnterpriseNode(int SystemId) throws Exception {
		createEnterpriseApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), testData.getProperty("api.enterprise.displayname"), "",
				testData.getProperty("api.enterprise.active"), testData.getProperty("api.enterprise.timezoneid"),
				systemId, testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createEnterpriseApiResponse.jsonPath().get("orgId");
	}

	public int createPracticeNode(int enterpriseId) throws Exception {
		createPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"), "",
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createPracticeApiResponse.jsonPath().get("orgId");
	}

	public int createAgnosticPracticeNode(int systemId) throws Exception {
		createAgnosticPracticeApiResponse = orgInfo.createOrgNode_BL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"), "",
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"), systemId,
				testData.getProperty("api.system.orgtype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createAgnosticPracticeApiResponse.jsonPath().get("orgId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template at Client Level and fetch newly created brand details using brandId")
	public void testCreateBrandAtClientLevel() throws IOException {
		logStep("Enter valid values in request payload to create branding template at client level");
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate(String.valueOf(clientId), name, "true", color);
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
		createBrandApiResponse = brandInfo.createBrandingTemplate(String.valueOf(systemId), name, "true", color);
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
		createBrandApiResponse = brandInfo.createBrandingTemplate(String.valueOf(enterpriseId), name, "true", color);
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
		createBrandApiResponse = brandInfo.createBrandingTemplate(String.valueOf(practiceId), name, "true", color);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		validation.verifyUpdateBrandingDetails(createBrandApiResponse, brandId, name);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template at Practice Level and fetch newly created brand details using brandId")
	public void testCreateBrandAtAgnosticPracticeLevel() throws IOException {
		logStep("Enter valid values in request payload to create branding template at Practice level");
		String name = testData.getProperty("practice.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("practice.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate(String.valueOf(agnosticPracticeId), name, "true",
				color);
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
		createBrandApiResponse = brandInfo.createBrandingTemplate(String.valueOf(clientId), name, "true", "    ");
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		validation.verifyUpdateBrandingDetails(createBrandApiResponse, brandId, name);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template with null values in payload and verify error response code")
	public void testCreateBrandWithNullValuesInPayload() throws IOException {
		logStep("Enter null values in request payload");
		createBrandApiResponse = brandInfo.createBrandingTemplate(null, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForNullValuesInPayload(createBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template with null values in name field and verify error response code")
	public void testCreateBrandWithNameFieldAsNull() throws IOException {
		logStep("Enter null values in name field in request payload");
		String orgId = testData.getProperty("client.org.id");
		createBrandApiResponse = brandInfo.createBrandingTemplate(orgId, null, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForNameFieldAsNull(createBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Create Branding Template with null values in orgId field and verify error response code")
	public void testCreateBrandWithOrgIdFieldAsNull() throws IOException {
		logStep("Enter null values in orgId field in request payload");
		String name = testData.getProperty("client.org.brand.name");
		createBrandApiResponse = brandInfo.createBrandingTemplate(null, name, null, null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForOrgIdAsNull(createBrandApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check error response code for invalid Url")
	public void testErrorResponseCodeForInvalidUrl() throws NullPointerException, IOException {
		logStep("Enter valid values in request payload to create branding template");
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplateInvalidUrl_BL(String.valueOf(clientId), name, "true",
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
		createBrandApiResponse = brandInfo.createBrandingTemplateInvalidMethodCall_BL(String.valueOf(clientId), name,
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
		createBrandApiResponse = brandInfo.createBrandingTemplate(String.valueOf(clientId), name, "true", color);
		logStep("Validating response Code");
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		logStep("Version Name length validation with 51 characters");
		name = "AutoBrand".concat(CCUtils.generateAlphaNumericString(51));
		createBrandApiResponse = brandInfo.createBrandingTemplate(String.valueOf(clientId), name, "true", color);
		CCUtils.responseTimeValidation(createBrandApiResponse);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 400);
		validation.verifyInvalidRequestValidation(createBrandApiResponse);
	}
}
