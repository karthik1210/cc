// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.BrandsInfo;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.BrandsValidations;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.Validations;
import io.restassured.response.Response;

public class GetListOfBrandsApiTests_DataLayer extends BaseRest {
	private BrandsInfo brandsInfo;
	private BrandsValidations brandValidations;
	Response getListofBrandsResponse;
	private Validations validations;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecificationBuilderForDataLayer();
		brandsInfo = new BrandsInfo();
		brandValidations = new BrandsValidations();
		validations = new Validations();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "get list of org brands for existing org id")
	public void testResponseCodeForGetListOfBrands() throws IOException {
		getListofBrandsResponse = brandsInfo
				.fetchListOfBrands_DL(Integer.parseInt(testData.getProperty("get.list.org.brands")));
		CCUtils.responseTimeValidation(getListofBrandsResponse);
		CCUtils.assertEquals(getListofBrandsResponse.getStatusCode(), 200);
		brandValidations.verifyNoOfBrandsInResponseForExistingOrgId(getListofBrandsResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validate status code 204 for fetching brands for non-existing org id")
	public void tesResponseCodeForGetListOfBrandsForNonExistingOrgId() throws IOException {
		getListofBrandsResponse = brandsInfo
				.fetchListOfBrands_DL(Integer.parseInt(testData.getProperty("get.list.non.existing.org.brands")));
		CCUtils.responseTimeValidation(getListofBrandsResponse);
		CCUtils.assertEquals(getListofBrandsResponse.getStatusCode(), 204);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validate status code 400 for fetching brands for invalid org id")
	public void testErrorResponseCodeForGetListOfBrandsForInvalidOrgId() throws IOException {
		getListofBrandsResponse = brandsInfo
				.fetchListOfBrandsInvalidOrgId_DL(Integer.parseInt(testData.getProperty("get.list.org.brands")));
		CCUtils.responseTimeValidation(getListofBrandsResponse);
		CCUtils.assertEquals(getListofBrandsResponse.getStatusCode(), 400);
		validations.verifyResponseForEmptyOrNULLOrMisFormatData_DL(getListofBrandsResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validate status code 400 for fetching brands for entering org id as null")
	public void testErrorResponseCodeForGetListOfBrandsForNullOrgId() throws IOException {
		getListofBrandsResponse = brandsInfo
				.fetchListOfBrandsInvalidOrgId_DL(Integer.parseInt(testData.getProperty("get.list.org.brands")));
		CCUtils.responseTimeValidation(getListofBrandsResponse);
		CCUtils.assertEquals(getListofBrandsResponse.getStatusCode(), 400);
		validations.verifyResponseForEmptyOrNULLOrMisFormatData_DL(getListofBrandsResponse);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validate status code 405 for fetching brands for existing org id for invalid HTTP Method")
	public void testErrorResponseCodeForGetListOfBrandsForInvalidMethodCall() throws IOException {
		getListofBrandsResponse = brandsInfo
				.fetchListOfBrandsInvalidHTTPMethod_DL(Integer.parseInt(testData.getProperty("get.list.org.brands")));
		CCUtils.responseTimeValidation(getListofBrandsResponse);
		CCUtils.assertEquals(getListofBrandsResponse.getStatusCode(), 405);
		validations.verifyErrorMessageInResponseForInvalidPostMethodCall(getListofBrandsResponse);
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "validate status code 405 for fetching brands for existing org id for invalid url")
	public void testErrorResponseCodeForGetListOfBrandsForInvalidUrl() throws IOException {
		getListofBrandsResponse = brandsInfo
				.fetchListOfBrandsInvalidUrl_DL(Integer.parseInt(testData.getProperty("get.list.org.brands")));
		CCUtils.responseTimeValidation(getListofBrandsResponse);
		CCUtils.assertEquals(getListofBrandsResponse.getStatusCode(), 404);
		validations.verifyErrorInResponseForInvalidUrl(getListofBrandsResponse);
	}
}
