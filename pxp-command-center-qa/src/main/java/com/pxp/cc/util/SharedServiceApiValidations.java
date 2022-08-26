//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.util;

import java.io.IOException;
import java.util.Objects;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class SharedServiceApiValidations {

	public Boolean validateInageUrlValueIsNull(Response response) {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		if (Objects.isNull(jsonpath.get("imageUrl"))) {
			Log4jUtil.log("imageUrl is null");
			return true;
		} else {
			return false;
		}
	}

	public void verifyErrorResponseForInvalidResourceId(Response response, int resourceId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Resource not found by id=" + resourceId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorResponseForImageUrlField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Image url can not be null");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

}
