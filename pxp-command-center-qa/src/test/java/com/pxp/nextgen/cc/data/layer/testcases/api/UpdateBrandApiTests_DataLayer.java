// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.helpers.OrgInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;

public class UpdateBrandApiTests_DataLayer extends BaseRest {
	private BrandsInfo brandInfo;
	private Validations validation;
	Response createSystemApiResponse, createEnterpriseApiResponse, createPracticeApiResponse,
			createAgnosticPracticeApiResponse, createClientApiResponse, createBrandApiResponse,updateBrandDetailsApiResponse;
	int clientId, systemId, enterpriseId, practiceId,   clientBrandId, systemBrandId,enterpriseBrandId,practiceBrandId;
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
				"true", Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	public int createSystemNode(int clientId) throws Exception {
		createSystemApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.system.active"),
				testData.getProperty("api.system.displayname"), testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), clientId, testData.getProperty("api.parenttype"),
				testData.getProperty("api.system.timezoneid"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		return createSystemApiResponse.jsonPath().get("orgId");
	}

	public int createEnterpriseNode(int SystemId) throws Exception {
		createEnterpriseApiResponse = orgInfo.createOrg_DataLayer(testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.displayname"), testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), SystemId,
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
	public int createBrand(int orgId) {
		logStep("Enter valid values in request payload to create branding template at client level");
		String name = "New_Node".concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = "#ffffff";
		createBrandApiResponse =brandInfo.createBrandingTemplate_DL(String.valueOf(orgId), name, "true", color);
		return createBrandApiResponse.jsonPath().getInt("brandId");
	}
	@DataProvider(name = "updateBrandingValidDetails")
	public Object[] getDetails() {
		Object[][] obj = new Object[][] {
				{ String.valueOf(clientBrandId), String.valueOf(clientId),
						testData.getProperty("client.org.brand.name").trim()
								.concat(CCUtils.generateAlphaNumericString(3)),
						testData.getProperty("showlogo.true").trim(), testData.getProperty("client.brand.color") },
				{ String.valueOf(clientBrandId), String.valueOf(clientId),
						testData.getProperty("client.org.brand.name").trim()
								.concat(CCUtils.generateAlphaNumericString(3)),
						testData.getProperty("showlogo.false").trim(), testData.getProperty("client.brand.color") },
				{ String.valueOf(systemBrandId), String.valueOf(systemId),
						testData.getProperty("system.org.brand.name").trim()
								.concat(CCUtils.generateAlphaNumericString(3)),
						testData.getProperty("showlogo.true").trim(), testData.getProperty("system.brand.color") },
				{ String.valueOf(systemBrandId), String.valueOf(systemId),
						testData.getProperty("system.org.brand.name").trim()
								.concat(CCUtils.generateAlphaNumericString(3)),
						testData.getProperty("showlogo.false").trim(), testData.getProperty("system.brand.color") },
				{ String.valueOf(enterpriseBrandId), String.valueOf(enterpriseId),
						testData.getProperty("enterprise.org.brand.name").trim()
								.concat(CCUtils.generateAlphaNumericString(3)),
						testData.getProperty("showlogo.true").trim(), testData.getProperty("enterprise.brand.color") },
				{ String.valueOf(enterpriseBrandId), String.valueOf(enterpriseId),
						testData.getProperty("enterprise.org.brand.name").trim()
								.concat(CCUtils.generateAlphaNumericString(3)),
						testData.getProperty("showlogo.false").trim(), testData.getProperty("enterprise.brand.color") },
				{ String.valueOf(practiceBrandId), String.valueOf(practiceId),
						testData.getProperty("practice.org.brand.name").trim()
								.concat(CCUtils.generateAlphaNumericString(3)),
						testData.getProperty("showlogo.true").trim(), testData.getProperty("practice.brand.color") },
				{ String.valueOf(practiceBrandId), String.valueOf(practiceId),
						testData.getProperty("practice.org.brand.name").trim()
								.concat(CCUtils.generateAlphaNumericString(3)),
						testData.getProperty("showlogo.false").trim(), testData.getProperty("practice.brand.color") } };
		return obj;
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "updateBrandingValidDetails", description = "Enter existing BrandID, orgId to update brand at CLIENT, SYSTEM, ENTERPRISE, PRACTICE level")
	public void testSuccessResponseCodeForExistingBrandIdOrgIdToUpdateBranding(String[] getDetails)
			throws NullPointerException, IOException {
		logStep("Enter existing brandId, orgId");
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails_DL(getDetails[0], getDetails[1], getDetails[2],
				getDetails[3], getDetails[4]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 200);
		validation.verifyUpdateBrandingDetails(updateBrandDetailsApiResponse, getDetails[0], getDetails[2]);
	}
   
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing BrandID, existing orgId and validate error response code")
	public void testErrorResponseCodeForNonExistingBrandIdExistingOrgId() throws NullPointerException, IOException {
		logStep("Enter non-existing BrandID, existing orgId");
		String brandName = testData.getProperty("practice.org.brand.name")
				.concat(CCUtils.generateAlphaNumericString(5));
		String brandId = testData.getProperty("non.existing.brand.id");
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails_DL(brandId, String.valueOf(clientId), brandName,
				"true", testData.getProperty("client.brand.color"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidBrandId(updateBrandDetailsApiResponse,
				Integer.parseInt(testData.getProperty("non.existing.brand.id")));
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing BrandID, orgId as null and validate error response code")
	public void testErrorResponseCodeForExistingBrandIdAndOrgIdAsNull() throws NullPointerException, IOException {
		logStep("Enter existing brandId, orgId as null ");
		String brandName = testData.getProperty("client.org.brand.name").concat(CCUtils.generateAlphaNumericString(5));
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails_DL(String.valueOf(clientBrandId), null, brandName,
				"true", testData.getProperty("client.brand.color"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForOrgIdAsNull(updateBrandDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing orgId, BrandID as null and validate error response code")
	public void testErrorResponseCodeForExistingOrgIdAndBrandIdAsNull() throws NullPointerException, IOException {
		logStep("Enter existing orgId, BrandID as null");
		String brandName = testData.getProperty("client.org.brand.name").concat(CCUtils.generateAlphaNumericString(5));
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails_DL(null, String.valueOf(clientId), brandName, "true",
				testData.getProperty("client.brand.color"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 400);
		validation.verifyInvalidRequestValidation(updateBrandDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing BrandID and request payload as null and validate error response code")
	public void testErrorResponseCodeForRequestPayloadAsNull() throws NullPointerException, IOException {
		logStep("Enter existing BrandID and request payload as null");
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails_DL(String.valueOf(clientBrandId), null, null, null,
				null);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 400);
		validation.verifyInvalidRequestValidation(updateBrandDetailsApiResponse);
	}

	@Test(priority = 9, retryAnalyzer = RetryAnalyzer.class, description = "Check Version Name length validation and validate response code")
	public void versionNameFieldLengthValidation() throws IOException, NullPointerException {
		logStep("Version Name length validation with 50 charecters");
		String brandName = "AutoBrand".concat(CCUtils.generateAlphaNumericString(41));
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails_DL(String.valueOf(clientBrandId),
				String.valueOf(clientId), brandName, "true", testData.getProperty("client.brand.color"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 200);
		logStep("Version Name length validation with 51 charecters");
		brandName = "AutoBrand".concat(CCUtils.generateAlphaNumericString(51));
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails_DL(String.valueOf(clientBrandId),
				String.valueOf(clientId), brandName, "true", testData.getProperty("client.brand.color"));
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 400);
		validation.verifyInvalidRequestValidation(updateBrandDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check error response code for invalid Url")
	public void testErrorResponseCodeForInvalidUrl() throws NullPointerException, IOException {
		logStep("Enter existing brandId");
		String brandName = testData.getProperty("practice.org.brand.name");
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetailsInvalidUrl_DL(String.valueOf(practiceBrandId),
				String.valueOf(practiceId), brandName, "true", testData.getProperty("practice.brand.color"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateBrandDetailsApiResponse);
	}}