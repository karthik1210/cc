// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import java.io.IOException;

public class UpdateBrandingDetailsApiTests_BusinessLayer extends BaseRest {
	private BrandsInfo brandInfo;
	private Validations validation;
	Response updateBrandDetailsApiResponse, createBrandApiResponse;
	int clientId, systemId, enterpriseId, practiceId, agnosticPracticeId, clientBrandId, systemBrandId,
			enterpriseBrandId, practiceBrandId, agnosticBrandId;
	private CreateClientHierarchy_BL clientHierObj;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		brandInfo = new BrandsInfo();
		validation = new Validations();
		clientHierObj = new CreateClientHierarchy_BL();

		clientId = clientHierObj.createClient_BL();
		systemId = clientHierObj.createSystem_BL(clientId);
		enterpriseId = clientHierObj.createEnterprise_BL(systemId);
		practiceId = clientHierObj.createPractice_BL(enterpriseId);
		agnosticPracticeId = clientHierObj.createAgnosticPractice_BL(systemId);
		clientBrandId = createBrand(clientId);
		systemBrandId = createBrand(systemId);
		enterpriseBrandId = createBrand(enterpriseId);
		practiceBrandId = createBrand(practiceId);
		agnosticBrandId = createBrand(agnosticPracticeId);
	}

	public int createBrand(int orgId) {
		logStep("Enter valid values in request payload to create branding template at client level");
		String name = "New_Node".concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = "#ffffff";
		createBrandApiResponse = brandInfo.createBrandingTemplate(String.valueOf(orgId), name, "true", color);
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
						testData.getProperty("showlogo.false").trim(), testData.getProperty("practice.brand.color") },
				{ String.valueOf(agnosticBrandId), String.valueOf(agnosticPracticeId),
						testData.getProperty("practice.org.brand.name").trim()
								.concat(CCUtils.generateAlphaNumericString(3)),
						testData.getProperty("showlogo.true").trim(), testData.getProperty("practice.brand.color") },
				{ String.valueOf(agnosticBrandId), String.valueOf(agnosticPracticeId),
						testData.getProperty("practice.org.brand.name").trim()
								.concat(CCUtils.generateAlphaNumericString(3)),
						testData.getProperty("showlogo.false").trim(), testData.getProperty("practice.brand.color") } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "updateBrandingValidDetails", description = "Enter existing BrandID, orgId to update brand at CLIENT, SYSTEM, ENTERPRISE, PRACTICE level")
	public void testSuccessResponseCodeForExistingBrandIdOrgIdToUpdateBranding(String[] getDetails)
			throws NullPointerException, IOException {
		logStep("Enter existing brandId, orgId");
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails(getDetails[0], getDetails[1], getDetails[2],
				getDetails[3], getDetails[4]);
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 200);
		validation.verifyUpdateBrandingDetails(updateBrandDetailsApiResponse, getDetails[0], getDetails[2]);
	}

	/*
	 * Enable test suite once pipeline issue is addressed for for shared services
	 * Jira # PXPP-17732
	 */
	/*
	 * @Test(retryAnalyzer = RetryAnalyzer.class, description =
	 * "Enter existing BrandID, non-existing orgId and validate error response code"
	 * ) public void testErrorResponseCodeForExistingBrandIdNonExistingOrgId()
	 * throws NullPointerException, IOException {
	 * logStep("Enter existing brandId, non-existing orgId"); String orgId =
	 * testData.getProperty("non.existing.org.id"); String brandName =
	 * testData.getProperty("practice.org.brand.name")
	 * .concat(CCUtils.generateAlphaNumericString(5)); updateBrandDetailsApiResponse
	 * = brandInfo.updateBrandDetails(String.valueOf(practiceBrandId), orgId,
	 * brandName, "true", testData.getProperty("client.brand.color"));
	 * logStep("Validating response information");
	 * CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
	 * CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 400);
	 * validation.verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(
	 * updateBrandDetailsApiResponse, orgId); }
	 */

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing BrandID, existing orgId and validate error response code")
	public void testErrorResponseCodeForNonExistingBrandIdExistingOrgId() throws NullPointerException, IOException {
		logStep("Enter non-existing BrandID, existing orgId");
		String brandName = testData.getProperty("practice.org.brand.name")
				.concat(CCUtils.generateAlphaNumericString(5));
		String brandId = testData.getProperty("non.existing.brand.id");
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails(brandId, String.valueOf(clientId), brandName,
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
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails(String.valueOf(clientBrandId), null, brandName,
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
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails(null, String.valueOf(clientId), brandName, "true",
				testData.getProperty("client.brand.color"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 400);
		validation.verifyInvalidRequestValidation(updateBrandDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing BrandID and request payload as null and validate error response code")
	public void testErrorResponseCodeForRequestPayloadAsNull() throws NullPointerException, IOException {
		logStep("Enter existing BrandID and request payload as null");
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails(String.valueOf(clientBrandId), null, null, null,
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
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails(String.valueOf(clientBrandId),
				String.valueOf(clientId), brandName, "true", testData.getProperty("client.brand.color"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 200);
		logStep("Version Name length validation with 51 charecters");
		brandName = "AutoBrand".concat(CCUtils.generateAlphaNumericString(51));
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetails(String.valueOf(clientBrandId),
				String.valueOf(clientId), brandName, "true", testData.getProperty("client.brand.color"));
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 400);
		validation.verifyInvalidRequestValidation(updateBrandDetailsApiResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Check error response code for invalid Url")
	public void testErrorResponseCodeForInvalidUrl() throws NullPointerException, IOException {
		logStep("Enter existing brandId");
		String brandName = testData.getProperty("practice.org.brand.name");
		updateBrandDetailsApiResponse = brandInfo.updateBrandDetailsInvalidUrl(practiceBrandId,
				String.valueOf(practiceId), brandName, "true", testData.getProperty("practice.brand.color"));
		logStep("Validating response information");
		CCUtils.responseTimeValidation(updateBrandDetailsApiResponse);
		CCUtils.assertEquals(updateBrandDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(updateBrandDetailsApiResponse);
	}
}
