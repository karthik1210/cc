// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.util;

import java.io.IOException;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ResourceValidations {

	public void verifyErrorMessageInResponseBodyForNonExistingResourceId(Response response, int nonExistingResourceId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Resource not found by id=" + nonExistingResourceId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyResponseBodyForExistingResourceId(Response response)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		Log4jUtil.log("value of name is : "+jsonpath.get("name").toString());
		Log4jUtil.log("value of image url is : "+jsonpath.get("imageUrl"));
		Log4jUtil.log("value of external Id is : "+jsonpath.get("externalId"));
		Log4jUtil.log("value of staff Id is : "+jsonpath.get("staffId"));
		Log4jUtil.log("value of status is : "+jsonpath.get("status"));
	}

}
