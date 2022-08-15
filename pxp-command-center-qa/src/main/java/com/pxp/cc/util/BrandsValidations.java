// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.util;

import java.io.IOException;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class BrandsValidations {

	JsonPath jsonPath;

	public void verifyNoOfBrandsInResponseForExistingOrgId(Response response)
			throws IOException {
		jsonPath = new JsonPath(response.getBody().asString());
		Log4jUtil.log("Total no of brands : " + jsonPath.getInt("brands.size()"));
		for (int brandData = 0; brandData < jsonPath.getInt("brands.size()"); brandData++) {
			int brandId = jsonPath.get("brands[" + brandData + "].brandId");
			Log4jUtil.log("Brand Id is : " + brandId);
		}
	}
	
	public void verifyOrgNotFoundErrorMessage(Response response, int orgId) {
		jsonPath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonPath.get("message").toString().trim(), "Org not found by id="+orgId);
	}
}
