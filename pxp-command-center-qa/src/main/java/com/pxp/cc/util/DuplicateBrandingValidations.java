//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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

public class DuplicateBrandingValidations {

	public void verifyErrorMessageInResponseForNonExistingBrandId(Response response, String nonExistingBrandId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Brand not found by id=" + nonExistingBrandId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForNonExistingOrgIdOfOrg(Response response, String nonExistingOwnerId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Org not found for OrgId=" + nonExistingOwnerId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForNonExistingOrgIdOfClient(Response response, String nonExistingOwnerId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Client not found by id=" + nonExistingOwnerId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForInvalidOwnerType(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid ownerType");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForNonExistingOwnerIdForClient(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid ownerType");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForNullOwnerType(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "ownerType cannot be empty or null");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForNullOwnerId(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "ownerId cannot be null");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public int getActiveBrandId(Response getBrandListApiResponse) throws Exception {
		JsonPath jsonPath = new JsonPath(getBrandListApiResponse.getBody().asString());
		JSONArray jsonArray = new JSONArray(jsonPath.getList("brands"));
		int brandId = 0;
		if (jsonPath.getInt("brands.size()") == jsonArray.length()) {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if (jsonObject.has("active") == true) {
					if (jsonObject.get("active").equals(true)) {
						brandId = jsonObject.getInt("brandId");
						break;
					} else {
						Log4jUtil.log("Active key value is false");
					}
				} else {
					Log4jUtil.log("Active key is not present in jsonArray");
				}
			}
		} else if (jsonArray.length() == 0) {
			Log4jUtil.log("Brand list is empty");
		} else {
			Log4jUtil.log("Size of brands list and json array list is not same");
		}
		return brandId;
	}

	public void VerifyActiveFlagStatus(Response getBrandListApiResponse, Boolean activeFlagStatus, Integer brandId)
			throws Exception {
		JsonPath jsonPath = new JsonPath(getBrandListApiResponse.getBody().asString());
		JSONArray jsonArray = new JSONArray(jsonPath.getList("brands"));
		if (jsonPath.getInt("brands.size()") == jsonArray.length()) {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if (jsonObject.get("brandId").equals(brandId)) {
					CCUtils.assertTrue(jsonObject.get("active").equals(activeFlagStatus),
							"Active flag status is not matching");
					break;
				} else {
					Log4jUtil.log("Brand id is not matching");
				}
			}
		} else if (jsonArray.length() == 0) {
			Log4jUtil.log("Brand list is empty");
		} else {
			Log4jUtil.log("Size of brands list and json array list is not same");
		}
	}

	public void verifyErrorMessageForEmptyPayload(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "BrandId cannot be null, Active cannot be null";
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

	public int getInactiveInUseBrandId(Response getBrandListApiResponse) throws Exception {
		JsonPath jsonPath = new JsonPath(getBrandListApiResponse.getBody().asString());
		JSONArray jsonArray = new JSONArray(jsonPath.getList("brands"));
		int brandId = 0;
		if (jsonPath.getInt("brands.size()") == jsonArray.length()) {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if (jsonObject.has("inUse") == true) {
					if (jsonObject.get("inUse").equals(true) && jsonObject.get("active").equals(false)) {
						brandId = jsonObject.getInt("brandId");
						break;
					} else {
						Log4jUtil.log("in use key value is false");
					}
				} else {
					Log4jUtil.log("in use key is not present in jsonArray");
				}
			}
		} else if (jsonArray.length() == 0)

		{
			Log4jUtil.log("Brand list is empty");
		} else {
			Log4jUtil.log("Size of brands list and json array list is not same");
		}
		return brandId;
	}

}
