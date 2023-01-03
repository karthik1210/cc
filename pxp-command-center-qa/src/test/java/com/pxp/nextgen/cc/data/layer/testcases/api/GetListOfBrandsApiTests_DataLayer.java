// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.helpers.ClientInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.BrandsValidations;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetListOfBrandsApiTests_DataLayer extends BaseRest {
	private BrandsInfo brandsInfo;
	private BrandsValidations brandValidations;
	private Validations validations;
	Response createClientApiResponse, createBrandApiResponse, getListofBrandsResponse;
	int clientId, brandId;
	String clientName, clientSapNumber;
	private ClientInfo clientInfo;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		brandsInfo = new BrandsInfo();
		brandValidations = new BrandsValidations();
		brandsInfo = new BrandsInfo();
		clientInfo = new ClientInfo();
		validations = new Validations();
		clientName = ccUser.getClientName().concat(CCUtils.generateRandomString());
		clientSapNumber = ccUser.getClientSapNumber().concat(CCUtils.generateRandomString());
		logStep("Create a new client");
		createClientApiResponse = clientInfo.createNewClient_DataLayer(ccUser.getActive(), clientName, clientSapNumber,
				"true", Integer.parseInt(testData.getProperty("api.client.type")), ccUser.getTimezoneId(),
				testData.getProperty("ehr.identifier"), testData.getProperty("pm.identifier"));
		clientId = createClientApiResponse.jsonPath().get("clientId");
		logStep("Create a new brand");
		String name = testData.getProperty("client.org.brand.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		String color = testData.getProperty("client.brand.color");
		createBrandApiResponse = brandsInfo.createBrandingTemplate_DL(String.valueOf(clientId), name, "true", color);
		brandId = createBrandApiResponse.jsonPath().get("brandId");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "get list of org brands for  org id")
	public void testResponseCodeForGetListOfBrands() throws IOException {
		getListofBrandsResponse = brandsInfo.fetchListOfBrands_DL(clientId);
		CCUtils.responseTimeValidation(getListofBrandsResponse);
		CCUtils.assertEquals(getListofBrandsResponse.getStatusCode(), 200);
		brandValidations.verifyNoOfBrandsInResponseForExistingOrgId(getListofBrandsResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validate status code 204 for fetching brands for non-existing org id")
	public void tesResponseCodeForGetListOfBrandsForNonExistingOrgId() throws IOException {
		getListofBrandsResponse = brandsInfo
				.fetchListOfBrands_DL(Integer.parseInt(testData.getProperty("get.list.non.existing.org.brands")));
		CCUtils.responseTimeValidation(getListofBrandsResponse);
		CCUtils.assertEquals(getListofBrandsResponse.getStatusCode(), 400);
		brandValidations.verifyOrgNotFoundErrorMessage(getListofBrandsResponse,
				Integer.parseInt(testData.getProperty("get.list.non.existing.org.brands")));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validate status code 400 for fetching brands for invalid org id")
	public void testErrorResponseCodeForGetListOfBrandsForInvalidOrgId() throws IOException {
		getListofBrandsResponse = brandsInfo.fetchListOfBrandsInvalidOrgId_DL(clientId);
		CCUtils.responseTimeValidation(getListofBrandsResponse);
		CCUtils.assertEquals(getListofBrandsResponse.getStatusCode(), 400);
		validations.verifyResponseForEmptyOrNULLOrMisFormatData_DL(getListofBrandsResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validate status code 400 for fetching brands for entering org id as null")
	public void testErrorResponseCodeForGetListOfBrandsForNullOrgId() throws IOException {
		getListofBrandsResponse = brandsInfo.fetchListOfBrandsInvalidOrgId_DL(clientId);
		CCUtils.responseTimeValidation(getListofBrandsResponse);
		CCUtils.assertEquals(getListofBrandsResponse.getStatusCode(), 400);
		validations.verifyResponseForEmptyOrNULLOrMisFormatData_DL(getListofBrandsResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validate status code 405 for fetching brands for existing org id for invalid HTTP Method")
	public void testErrorResponseCodeForGetListOfBrandsForInvalidMethodCall() throws IOException {
		getListofBrandsResponse = brandsInfo.fetchListOfBrandsInvalidHTTPMethod_DL(clientId);
		CCUtils.responseTimeValidation(getListofBrandsResponse);
		CCUtils.assertEquals(getListofBrandsResponse.getStatusCode(), 405);
		validations.verifyErrorMessageInResponseForInvalidPostMethodCall(getListofBrandsResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validate status code 405 for fetching brands for existing org id for invalid url")
	public void testErrorResponseCodeForGetListOfBrandsForInvalidUrl() throws IOException {
		getListofBrandsResponse = brandsInfo.fetchListOfBrandsInvalidUrl_DL(clientId);
		CCUtils.responseTimeValidation(getListofBrandsResponse);
		CCUtils.assertEquals(getListofBrandsResponse.getStatusCode(), 404);
		validations.verifyErrorInResponseForInvalidUrl(getListofBrandsResponse);
	}
}
