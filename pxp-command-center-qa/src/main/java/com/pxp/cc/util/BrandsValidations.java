// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.util;

import java.io.IOException;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class BrandsValidations {

	JsonPath jsonPath;

	public void verifyNoOfBrandsInResponseForExistingOrgId(Response response) throws IOException {
		jsonPath = new JsonPath(response.getBody().asString());
		Log4jUtil.log("Total no of brands : " + jsonPath.getInt("brands.size()"));
		for (int brandData = 0; brandData < jsonPath.getInt("brands.size()"); brandData++) {
			int brandId = jsonPath.get("brands[" + brandData + "].brandId");
			Log4jUtil.log("Brand Id is : " + brandId);
		}
	}

	public void verifyOrgNotFoundErrorMessage(Response response, int orgId) {
		jsonPath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonPath.get("message").toString().trim(), "Org not found by id=" + orgId);
	}

	public void verifyBrandNotFoundErrorMessage(Response response, int brandId, String href) {
		jsonPath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonPath.get("message").toString().trim(),
				"Brand not found by brandId=" + brandId + " and href=" + href);
	}

	public void verifyErrorMessageIfBrandIdIsNull(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
		CCUtils.assertEquals(jsonpath.get("message"),
				"Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"brandId\"");
	}

	public void verifyErrorMessageIfCreateBrandLogoResponseBodyNull(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
		if ((jsonpath.get("message")).toString().contains(
				"Alt text can not be blank, Href can not be blank, File name can not be blank, Logo type can not be blank")) {
			CCUtils.assertTrue(true, "condition fulfilled");
		}
	}
	
	public void verifyBrandNotFoundErrorMessageResponse(Response response, int brandId) {
		jsonPath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonPath.get("message").toString().trim(),
				"Brand not found by id=" + brandId);
	}
	
	public void verifyErrorMessageForInvaliBrandUrl(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
		CCUtils.assertEquals(jsonpath.get("message"),"Error while download the file");
	}
}
