// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.helpers.SharedInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class DeleteBrandDetailsApiTests_DataLayer extends BaseRest {
	private BrandsInfo brandInfo;
	private Validations validation;
	private SharedInfo sharedInfo;
	Response createBrandApiResponse, deleteBrandingDetailsApiResponse;
	int clientId, systemId, enterpriseId, practiceId, brandId, orgId, brandIdOne, brandIdTwo, brandIdThree;
	private CreateClientHierarchy_DL clientHierObj;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		brandInfo = new BrandsInfo();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientHierObj = new CreateClientHierarchy_DL();

		clientId = clientHierObj.createClient_DL();
		systemId = clientHierObj.createSystem_DL(clientId);
		enterpriseId = clientHierObj.createEnterprise_DL(systemId);
		practiceId = clientHierObj.createPractice_DL(enterpriseId);
		brandId = createBrandForClientLevel(clientId);
		brandIdOne = createBrandForSystemLevel(systemId);
		brandIdTwo = createBrandForEntepriseLevel(enterpriseId);
		brandIdThree = createBrandForPracticeLevel(practiceId);
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
