// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.business.layer.testcases.api;

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

public class GetBrandDetailsApiTests_BusinessLayer extends BaseRest {
	private SharedInfo sharedInfo;
	private Validations validation;
	Response getBrandingDetailsApiResponse, createBrandApiResponse, createClientApiResponse;
	private BrandsInfo brandInfo;
	private ClientInfo clientInfo;
	int clientId;
	
	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilder();
		sharedInfo = new SharedInfo();
		validation = new Validations();
		brandInfo = new BrandsInfo();
		clientInfo = new ClientInfo();
		clientId = createClient("true", "true");
	}

	public int createClient(String status, String testClient) throws Exception {
		String clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		String clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient(status, clientName, clientSapNumber, testClient,
				ccUser.getClientType(), ccUser.getTimezoneId());
		logStep("Validating response information");
		CCUtils.responseTimeValidation(createClientApiResponse);
		CCUtils.assertEquals(createClientApiResponse.getStatusCode(), 201);
		validation.verifyCreateAndUpdateClientResponseBody(createClientApiResponse, clientName);
		return createClientApiResponse.jsonPath().get("clientId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter non-existing brandId in brand id field and verify error response")
	public void testErrorResponseForInvalidBrandId() throws IOException {
		getBrandingDetailsApiResponse = sharedInfo
				.getBrandDetails(Integer.parseInt(testData.getProperty("non.existing.brand.id")));
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 400);
		validation.verifyErrorMessageForInvalidBrandId(getBrandingDetailsApiResponse,
				Integer.parseInt(testData.getProperty("non.existing.brand.id")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter existing brandId in brand id field and verify response body")
	public void testGetBrandDetailsApi() throws IOException {
		logStep("Enter valid values in request payload to create branding template at client level");
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandInfo.createBrandingTemplate(String.valueOf(clientId), name, "true", color);
		CCUtils.assertEquals(createBrandApiResponse.getStatusCode(), 200);
		String brandId = createBrandApiResponse.jsonPath().getString("brandId");
		getBrandingDetailsApiResponse = sharedInfo.getBrandDetails(Integer.parseInt(brandId));
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 200);
		validation.verifyBrandingDetails_BL(getBrandingDetailsApiResponse, clientId,
				testData.getProperty("brand.id.one.org.type"), name, testData.getProperty("brand.id.one.showLogo"),
				color, testData.getProperty("brand.id.one.logo"), testData.getProperty("brand.id.one.favicon"),
				testData.getProperty("brand.id.one.emailLogo"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error message")
	public void testErrorResponseForInvalidUrl() throws IOException {
		getBrandingDetailsApiResponse = sharedInfo
				.getBrandDetailsUsingInvalidUrl(Integer.parseInt(testData.getProperty("brand.id.one")));
		CCUtils.responseTimeValidation(getBrandingDetailsApiResponse);
		CCUtils.assertEquals(getBrandingDetailsApiResponse.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(getBrandingDetailsApiResponse);
	}
}
