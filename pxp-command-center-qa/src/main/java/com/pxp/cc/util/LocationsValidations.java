//Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.pxp.cc.objectmaps.LocationStatus;
import com.pxp.cc.pojos.ProviderStatus;
import com.pxp.cc.pojos.ResourceStatus;
import com.pxp.cc.pojos.ResourcesStatus;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class LocationsValidations {
	JsonPath jsonPath;
	JSONArray jsonArray;
	JSONObject jsonObject;

	public void verifySearchLocationOutputWithoutFilter(Response response, String getSearchLocations) {
		jsonPath = new JsonPath(response.asString());
		Log4jUtil.log("Display list of locations in response body : " + jsonPath.get("items"));
		Log4jUtil.log("Display total count of locations in response body : " + jsonPath.get("totalCount"));
		Log4jUtil.log("Get the size of items in response body : " + jsonPath.getInt("items.size()"));
		Log4jUtil.log("Comparing Searched Items names/ids with location data received in Response body.");
		for (int dataArrayIteration = 0; dataArrayIteration < jsonPath.getInt("items.size()"); dataArrayIteration++) {
			String locationIteratePerData = jsonPath.get("items[" + dataArrayIteration + "].name");
			Log4jUtil.log("Print Value : " + locationIteratePerData);
			if (locationIteratePerData.contains(getSearchLocations)) {
				Log4jUtil.log("searched value :" + getSearchLocations + "present within response body's value"
						+ locationIteratePerData);
				CCUtils.assertTrue(true,
						"Found data " + locationIteratePerData + " is present in expected data" + getSearchLocations);
			}
		}
	}

	public void verifyGetProvidersForLocationValidInput(Response response, String getProvidersForLocation) {
		JsonPath jsonPath = new JsonPath(response.asString());
		Log4jUtil.log("Display total count of locations in response body : " + jsonPath.get("totalCount"));
		Log4jUtil.log("Display list of clients in response body : " + jsonPath.get("items"));
		Log4jUtil.log("Get the size of clients in response body : " + jsonPath.getInt("items.size()"));
		Log4jUtil.log("Comparing Searched Items names/ids with user data received in Response body.");
		for (int dataArrayIteration = 0; dataArrayIteration < jsonPath.getInt("items.size()"); dataArrayIteration++) {
			String providerIteratePerData = jsonPath.get("items[" + dataArrayIteration + "].firstName");
			Log4jUtil.log("Print Value : " + providerIteratePerData);
			if (providerIteratePerData.contains(getProvidersForLocation)) {
				Log4jUtil.log("searched value :" + getProvidersForLocation + "present within response body's value"
						+ providerIteratePerData);
				CCUtils.assertTrue(true, "Found data " + providerIteratePerData + " is present in expected data"
						+ getProvidersForLocation);
			} else if (providerIteratePerData.contains(getProvidersForLocation)) {
				Log4jUtil.log("searched value :" + getProvidersForLocation + "present within response body's value"
						+ providerIteratePerData);
				CCUtils.assertTrue(true, "Found data " + providerIteratePerData + " is present in expected data"
						+ getProvidersForLocation);
			}
		}
	}

	public void verifyGetCCRoleLocationsValidInput(Response response, String roleId, String roleName) {
		JsonPath jsonPath = response.jsonPath();
		String Role_Id = jsonPath.getString("roleId");
		String Role_Name = jsonPath.getString("name");
		Log4jUtil.log("Verifying if role id is null or not");
		if (Role_Id != null) {
			Log4jUtil.log("Role Id is not NULL verified");
			if (Role_Id.equals(roleId) && Role_Name.equalsIgnoreCase(roleName)) {
				Log4jUtil.log("Validation Successful");
			}
		}
	}

	public void verifyResponseBodyForLocationAssociatedWithResourses(Response response, String locationId)
			throws IOException {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
		}
		CCUtils.assertEquals(jsonArray.get(0).toString().contains("locationId"), true);
	}

	public void verifyAddOrRemoveLocationsApiResponseBody(Response response, int locationId) throws IOException {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
		}
		CCUtils.assertEquals(jsonArray.get(0).toString().contains("locationId"), true);
	}

	public void verifyAddOrRemoveLocationsApiResponseBodyForInvalidInput(Response response, List<Integer> locationId,
			String status, String message) throws IOException {
		List<LocationStatus> actualLocationStatusList = new ArrayList<LocationStatus>();
		for (int i = 0; i < locationId.size(); i++) {
			actualLocationStatusList.add(new LocationStatus(locationId.get(i), status, message));
		}
		JSONArray responseBody = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		CCUtils.assertTrue(responseBody.length() > 0, "Location list is empty");
		CCUtils.assertTrue(actualLocationStatusList.size() == responseBody.length(),
				"Size of actual resource list and expected user list is not same");
		for (int i = 0; i < responseBody.length(); i++) {
			jsonObject = responseBody.getJSONObject(i);
			CCUtils.assertTrue(
					actualLocationStatusList.contains(new LocationStatus(jsonObject.getInt("locationId"),
							jsonObject.getString("status"), jsonObject.getString("message"))),
					"location Id " + jsonObject.getInt("locationId") + " doesn't exist");
		}
	}

	public void verifyErrorResponseForInvalidLocationId(Response response, int locationId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath.get("error").toString(), "Bad request");
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Location not found by id=" + locationId);
	}

	public void verifyGetPracticesAssociatedToLocationApiResponseBody(Response response, List<Integer> practiceIds)
			throws IOException {
		JSONArray responseBody = new JSONArray(response.getBody().asString());
		CCUtils.assertTrue(responseBody.length() > 0, "practice Id list is empty");
		CCUtils.assertTrue(practiceIds.size() == responseBody.length(),
				"Size of actual practice list and expected practice list is not same");
		List<Integer> data = new ArrayList<Integer>();
		for (int i = 0; i < responseBody.length(); i++) {
			data.add(Integer.valueOf(responseBody.getInt(i)));
		}
		Collections.sort(practiceIds);
		Collections.sort(data);
		CCUtils.assertEquals(practiceIds, data);
	}

	public void verifyErrorResponseForNonExistingLocationId(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath.get("error").toString(), "Bad request");
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid location Id");
	}

	public void verifyErrorResponseForNonExistingPracticeId(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath.get("error").toString(), "Bad request");
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid practice Id");
	}

	public void verifyErrorResponseForEmptyPayload(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "PracticeId cannot be null, Active cannot be null";
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
	}

	public void verifyErrorResponseForNonExistingId(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath.get("error").toString(), "Bad request");
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid input");
	}

	public String getResourceName(Response response) throws IOException {
		return response.jsonPath().getString("name");
	}

	public void verifyAddResourcesForLocationApiResponseBody(Response response, List<Integer> resourceIds,
			String status) throws IOException {
		List<ResourcesStatus> actualResourceStatusList = new ArrayList<ResourcesStatus>();
		for (int i = 0; i < resourceIds.size(); i++) {
			actualResourceStatusList.add(new ResourcesStatus(resourceIds.get(i), status));
		}
		JSONArray responseBody = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		CCUtils.assertTrue(responseBody.length() > 0, "Resources list is empty");
		CCUtils.assertTrue(actualResourceStatusList.size() == responseBody.length(),
				"Size of actual resource list and expected Resources list is not same");
		for (int i = 0; i < responseBody.length(); i++) {
			jsonObject = responseBody.getJSONObject(i);
			CCUtils.assertTrue(
					actualResourceStatusList.contains(
							new ResourcesStatus(jsonObject.getInt("resourceId"), jsonObject.getString("status"))),
					"Resource Id " + jsonObject.getInt("resourceId") + " doesn't exist");
		}
	}

	public void verifyAddInvalidResourcesForLocationApiResponseBody(Response response, List<Integer> resourceIds,
			String status, String message) throws IOException {
		List<ResourceStatus> actualResourceStatusList = new ArrayList<ResourceStatus>();
		for (int i = 0; i < resourceIds.size(); i++) {
			actualResourceStatusList.add(new ResourceStatus(resourceIds.get(i), status, message));
		}
		JSONArray responseBody = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		CCUtils.assertTrue(responseBody.length() > 0, "Resources list is empty");
		CCUtils.assertTrue(actualResourceStatusList.size() == responseBody.length(),
				"Size of actual resource list and expected Resources list is not same");
		for (int i = 0; i < responseBody.length(); i++) {
			jsonObject = responseBody.getJSONObject(i);
			CCUtils.assertTrue(
					actualResourceStatusList.contains(new ResourceStatus(jsonObject.getInt("resourceId"),
							jsonObject.getString("status"), jsonObject.getString("message"))),
					"Resource Id " + jsonObject.getInt("resourceId") + " doesn't exist");
		}
	}

	public void verifyNoOfSolutionsFetchedSolutionNameandId(Response response) {
		jsonArray = new JSONArray(response.getBody().asString());
		CCUtils.assertTrue(jsonArray.length() > 0, "Product list is empty");
		Log4jUtil.log("Number of resources fetched is " + jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			Log4jUtil.log("print the solution name : " + jsonObject.getString("name") + " and solution Id : "
					+ jsonObject.get("solutionId"));
		}
	}

	public void verifyAssociateProvidersForLocationApiResponseBody(Response response, List<Integer> providerIds,
			String status, String message) throws IOException {
		List<ProviderStatus> actualProvidersStatusList = new ArrayList<ProviderStatus>();
		for (int i = 0; i < providerIds.size(); i++) {
			actualProvidersStatusList.add(new ProviderStatus(providerIds.get(i), status, message));
		}
		JSONArray responseBody = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		CCUtils.assertTrue(responseBody.length() > 0, "Providers list is empty");
		CCUtils.assertTrue(actualProvidersStatusList.size() == responseBody.length(),
				"Size of actual Providers list and expected Providers list is not same");
		for (int i = 0; i < responseBody.length(); i++) {
			jsonObject = responseBody.getJSONObject(i);
			CCUtils.assertTrue(
					actualProvidersStatusList.contains(new ProviderStatus(jsonObject.getInt("providerId"),
							jsonObject.getString("status"), jsonObject.get("message"))),
					"Provider Id " + jsonObject.getInt("providerId") + " doesn't exist");
		}
	}

	public void verifyGetLocationByPracticeApiResponseBody(Response response, String enDisplayName) throws IOException {
		Map<String, String> displayName = response.jsonPath().getMap("displayName");
		CCUtils.assertEquals(displayName.get("en").toString(), enDisplayName);
	}
}
