// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.util;

import java.io.IOException;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ResourceValidations {

	public void verifyErrorMessageInResponseBodyForNonExistingResourceId(Response response, int nonExistingResourceId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Resource not found by id=" + nonExistingResourceId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	
}
