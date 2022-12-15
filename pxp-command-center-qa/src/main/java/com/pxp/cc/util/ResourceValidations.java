// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
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

	public void verifyResponseBodyForExistingResourceId(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		Log4jUtil.log("value of name is : " + jsonpath.get("name").toString());
		Log4jUtil.log("value of image url is : " + jsonpath.get("imageUrl"));
		Log4jUtil.log("value of external Id is : " + jsonpath.get("externalId"));
		Log4jUtil.log("value of staff Id is : " + jsonpath.get("staffId"));
		Log4jUtil.log("value of status is : " + jsonpath.get("status"));
	}

	public void verifyErrorResponseForNullPayload_DL(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "ExternalId cannot be empty or null, linkId cannot be null, linkType cannot be empty or null";
	}
	
	public void verifyErrorResponseForNullPayload(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "LinkType cannot be blank, Type cannot be blank, LinkId cannot be blank, ExternalId cannot be blank, Name cannot be blank";
		List<String> actualValue = new ArrayList<String>();
		for (String val : Arrays.asList(actual.split(","))) {
			actualValue.add(val.trim());
		}
		Collections.sort(actualValue);
		String expected = jsonpath.get("message").toString();
		List<String> expectedValue = new ArrayList<String>();
		for (String val : Arrays.asList(expected.split(","))) {
			expectedValue.add(val.trim());
		}
		Collections.sort(expectedValue);
		CCUtils.assertEquals(actualValue, expectedValue);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorResponseForMaxCharacterLimit_DL(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "ExternalId size maximum 50 characters");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorResponseForMaxCharacterLimit(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "ExternalId size maximum 50 characters, Name size maximum 100 characters";
		List<String> actualValue = new ArrayList<String>();
		for (String val : Arrays.asList(actual.split(","))) {
			actualValue.add(val.trim());
		}
		Collections.sort(actualValue);
		String expected = jsonpath.get("message").toString();
		List<String> expectedValue = new ArrayList<String>();
		for (String val : Arrays.asList(expected.split(","))) {
			expectedValue.add(val.trim());
		}
		Collections.sort(expectedValue);
		CCUtils.assertEquals(actualValue, expectedValue);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyGetResourcesApiResponseBody(Response response, int resourceId, String resourceName) throws IOException {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			CCUtils.assertEquals(jsonObject.get("resourceId"), resourceId);
			CCUtils.assertEquals(jsonObject.get("name"), resourceName);
		}
	}
	
	public void verifyErrorMessageForResourceIdWithNonExistingImage(Response response, int nonExistingResourceId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Resource image not found by resourceId=" + nonExistingResourceId);
	}
	
	public void verifyGetDisplayNameOfPracticeResourcesApiResponseBody(Response response)
			 throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		Log4jUtil.log("value of name is : " + jsonpath.get("en").toString());
	}
	
	public void verifyErrorMessageForBlankField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid input");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}
	
	public void verifyErrorMessageForResourceWithNonExisting(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Error while download the file");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}
}
