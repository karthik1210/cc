// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.util;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PracticeProviderValidations {	
	
	public void validateGetPracticeProviderResponseBody(Response response, String providerName) {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(response.jsonPath().get(), "Response is null");
		CCUtils.assertEquals(jsonpath.get("displayName.en").toString().trim(),providerName.trim());
	}
	
	public void validateGetPracticeProviderResponseBodyWithEmailAndMobile(Response response, String providerName,String email,String mobile) {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(response.jsonPath().get(), "Response is null");
		CCUtils.assertEquals(jsonpath.get("displayName.en").toString().trim(),providerName.trim());
		CCUtils.assertEquals(jsonpath.get("mobile").toString().trim(),mobile.trim());
		CCUtils.assertEquals(jsonpath.get("email").toString().trim(),email.trim());
		
	}
}
