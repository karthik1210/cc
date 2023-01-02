// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

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
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetBrandDetailsApiTests_DataLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	private ClientInfo clientInfo;
	private BrandsInfo brandInfo;
	Response getBrandingDetailsApiResponse, createBrandApiResponse, createClientApiResponse;
	int clientId, brandId;
	String name, color;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		clientInfo = new ClientInfo();
		brandInfo = new BrandsInfo();
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient_DataLayer(ccUser.getActive(), clientName, clientSapNumber,
				"true", Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId(),
				testData.getProperty("ehr.identifier"), testData.getProperty("pm.identifier"));
		clientId = createClientApiResponse.jsonPath().get("clientId");
		logStep("create brand");
		name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate_DL(String.valueOf(clientId), name, "true", color);
		brandId = createBrandApiResponse.jsonPath().get("brandId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing brandId in brand id field and verify response body")
	public void testGetBrandDetailsApi() throws IOException, NullPointerException {
		getBrandingDetailsApiResponse = sharedInfo.getBrandDetails_DL(brandId);
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 200);
		validation.verifyBrandingDetails_DL(getBrandingDetailsApiResponse, String.valueOf(clientId), "CLIENT", name,
				color);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing brandId in brand id field and verify error response")
	public void testErrorResponseForInvalidBrandId() throws IOException {
		getBrandingDetailsApiResponse = sharedInfo
				.getBrandDetails_DL(Integer.parseInt(testData.getProperty("non.existing.brand.id")));
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidBrandId(getBrandingDetailsApiResponse,
				Integer.parseInt(testData.getProperty("non.existing.brand.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error message")
	public void testErrorResponseForInvalidUrl_DL() throws IOException {
		getBrandingDetailsApiResponse = sharedInfo
				.getBrandDetailsUsingInvalidUrl_DL(Integer.parseInt(testData.getProperty("brand.id")));
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getBrandingDetailsApiResponse);
	}
}
