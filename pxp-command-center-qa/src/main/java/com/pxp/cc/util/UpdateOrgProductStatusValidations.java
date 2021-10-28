package com.pxp.cc.util;

import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;

public class UpdateOrgProductStatusValidations {

	public void validatePracticeOrgTypeInResponseBody(Response response) {
		CCUtils.assertEquals(response.jsonPath().get("orgType"), "PRACTICE");
	}

	public Map<String, Object> fetchKeyValuesFromResponseBodyOfGetOrg(Response response) {
		Map<String, Object> responseFetch = new HashMap<String, Object>();
		responseFetch.put("active", response.jsonPath().get("active").toString());
		responseFetch.put("displayName", response.jsonPath().get("displayName").toString());
		responseFetch.put("id", response.jsonPath().get("orgId").toString());
		responseFetch.put("sapNumber", response.jsonPath().get("sapNumber").toString());
		responseFetch.put("timezoneId", response.jsonPath().get("timezone.id").toString());
		return responseFetch;
	}

	public String fetchActiveKeyValueFromResponseBody(Response response) {
		return response.jsonPath().get("active").toString();
	}
	
	public String fetchDisplayNameKeyValueFromResponseBody(Response response) {
		return response.jsonPath().get("displayName").toString();
	}

	public String fetchSapNumberKeyValueFromResponseBody(Response response) {
		return response.jsonPath().get("sapNumber").toString();
	}
	
	public String fetchOrgIdKeyValueFromResponseBody(Response response) {
		return response.jsonPath().get("orgId").toString();
	}
	
	public String fetchTimezoneIdKeyValueFromResponseBody(Response response) {
		return response.jsonPath().get("timezone.id").toString();
	}
}
