// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.util;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.pxp.cc.pojos.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Validations {
	private static final String TIMEZONE = "TimeZone";
	private static final String CLIENT_TYPE = "ClientType";
	private static final String SAP_ID = "Sap-Number";
	private static final String STATUS = "Status";
	private static final String FIELD = "field";
	private static final String RESOURCE_ID = "resourceId";
	private static final String TARGETDATA = "targetData";

	public void verifyCreateAndUpdateClientResponseBody(Response response, String clientNameValue) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertNotNull(jsonpath.get("clientId"), "Client id is not in the response");
		CCUtils.assertNotNull(jsonpath.get("name"), "Client name is not in the response");
		CCUtils.assertEquals(response.jsonPath().get("name").toString(), clientNameValue);
	}

	public void verifySearchClientResponseBody(Response response, String clientId, String clientName, int clientTypeId,
			String type, String sapNumber, String active, String testClient, String timezoneId,
			String timezoneDisplayName) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("clientId").toString(), clientId);
		CCUtils.assertEquals(jsonpath.get("name").toString(), clientName);
		Map<String, String> clientTypeMap = response.jsonPath().getMap("clientType");
		CCUtils.assertEquals(clientTypeMap.get("id"), clientTypeId);
		CCUtils.assertEquals(clientTypeMap.get("name").toString(), type);
		CCUtils.assertEquals(jsonpath.get("sapNumber").toString(), sapNumber);
		CCUtils.assertEquals(jsonpath.get("active").toString(), active);
		CCUtils.assertEquals(jsonpath.get("testClient").toString(), testClient);
		Map<String, String> timezone = response.jsonPath().getMap("timezone");
		CCUtils.assertEquals(timezone.get("id").toString(), timezoneId);
		CCUtils.assertEquals(timezone.get("displayName").toString(), timezoneDisplayName);
	}

	public void validateTimeZoneInGetClientDetails(Response response, JsonPath jsonpath, String timezoneId,
			String timezoneDisplayName) {
		if (Objects.isNull(jsonpath.get("timezone"))) {
			Log4jUtil.log("timezone is null");
		} else {
			Map<String, String> timezone = response.jsonPath().getMap("timezone");
			CCUtils.assertEquals(timezone.get("id").toString(), timezoneId);
			CCUtils.assertEquals(timezone.get("displayName").toString(), timezoneDisplayName);
		}
	}

	public int validateActiveandSolutionStatusInGetPracticeDetails(List<Response> response) {
		int flag = 0;
		Log4jUtil.log("print size of response : " + response.size());
		if (response.size() > 0) {
			Log4jUtil.log("Print List of response :" + response.toString());
			for (Response res : response) {
				Log4jUtil.log("Print response data solutions key value : " + res.jsonPath().getList("solutions"));
				if (res.jsonPath().get("active").equals(true)) {
					if (validateSolutionStatusOfGetOrgDetails(res.jsonPath().getList("solutions"))) {
						flag++;
						Log4jUtil.log("Verified that a practice is created with solutions");
					} else {
						Log4jUtil.log("Active practice is created without solutions");
					}
				} else
					Log4jUtil.log("Inactive practice is created");
			}
		} else {
			Log4jUtil.log("Response List is null");
		}
		return flag;
	}

	public Boolean validateSolutionStatusOfGetOrgDetails(List<Object> solutions) {
		for (Object obj : solutions) {
			String jsonInString = new Gson().toJson(obj);
			JSONObject mJSONObject = new JSONObject(jsonInString);
			if (mJSONObject.has("status") && Objects.nonNull(mJSONObject.getString("status"))) {
				Log4jUtil.log("Print the value of status key within solutions : " + mJSONObject.getString("status"));
				return true;
			}
		}
		return false;
	}

	public void verifyNullErrorMessageResponseBody(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Sap number can not be blank, Name can not be blank";
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

	public void verifyNullValuesErrorMessageResponseBody(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Name can not be blank, Sap number can not be blank, Active can not be null";
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

	public void verifyErrorMessageInResponseForClientNameField(Response response, String nonExistingClientName)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Client not found by name=" + nonExistingClientName);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForSapNumberField(Response response, String nonExistingClientSapNumber)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Client not found by sap number=" + nonExistingClientSapNumber);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForClientTypeField(Response response, int clientType) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Client type not found by " + clientType);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForClientTimezoneIdField(Response response, String clientTimezoneId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "TimezoneId=" + clientTimezoneId + " does not exist");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForClientSapNumberField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Sap number size maximum 50 character");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyMaximumCharacterErrorMessageInResponseForClientNameField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Name size maximum 100 character");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyCreateSystemResponseBody(Response response, String systemName) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertNotNull(jsonpath.get("orgId"), "System id was not in the response");
		CCUtils.assertNotNull(jsonpath.get("name"), "System name was not in the response");
		CCUtils.assertEquals(response.jsonPath().get("name").toString(), systemName);

	}

	public void verifyGetOrgDeatilsApiResponseBody(Response response, int orgId, String orgName, String orgType,
			String displayName, String active, String timezoneId, String displayNameOne) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("orgId"), orgId);
		CCUtils.assertEquals(jsonpath.get("name").toString(), orgName);
		CCUtils.assertEquals(jsonpath.get("orgType").toString(), orgType);
		CCUtils.assertEquals(jsonpath.get("displayName").toString(), displayName);
		CCUtils.assertEquals(jsonpath.get("active").toString(), active);
		if (Objects.isNull(jsonpath.get("timezone"))) {
			Log4jUtil.log("timezone is null");
		} else {
			Map<String, String> timezone = response.jsonPath().getMap("timezone");
			CCUtils.assertEquals(timezone.get("id").toString(), timezoneId);
			CCUtils.assertEquals(timezone.get("displayName").toString(), displayNameOne);
		}
	}

	public void verifyErrorMessageInResponseForInvalidOrgIdInOrgIdField(Response response, int orgId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Org not found for orgId=" + orgId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifySearchSystemResponseBody(Response response, String orgId, String systemName, String orgType,
			String clientType, String displayName, String sapNumber, String systemeActive, String systemTestClient,
			String timezoneId, String orgTimezoneDisplayNameApi, Boolean isPurchased) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("orgId").toString(), orgId);
		CCUtils.assertEquals(jsonpath.get("name").toString(), systemName);
		CCUtils.assertEquals(jsonpath.get("orgType").toString(), orgType);
		CCUtils.assertEquals(jsonpath.get("clientType").toString(), clientType);
		CCUtils.assertEquals(jsonpath.get("displayName").toString(), displayName);
		CCUtils.assertEquals(jsonpath.get("sapNumber").toString(), sapNumber);
		CCUtils.assertEquals(jsonpath.get("active").toString(), systemeActive);
		CCUtils.assertEquals(jsonpath.get("testClient").toString(), systemTestClient);
		if (Objects.isNull(jsonpath.get("timezone"))) {
			Log4jUtil.log("timezone is null");
		} else {
			Map<String, String> timezone = response.jsonPath().getMap("timezone");
			CCUtils.assertEquals(timezone.get("id").toString(), timezoneId);
			CCUtils.assertEquals(timezone.get("displayName").toString(), orgTimezoneDisplayNameApi);
		}
		CCUtils.assertTrue(jsonpath.get("practiceCount") == null, "practice count is not null");
	}

	public void verifySearchUpdatedSystemResponseBody(Response response, String orgId, String displayName,
			String sapNumber, String systemeActive, String timezoneId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("orgId").toString(), orgId);
		CCUtils.assertEquals(jsonpath.get("displayName").toString(), displayName);
		CCUtils.assertEquals(jsonpath.get("sapNumber").toString(), sapNumber);
		CCUtils.assertEquals(jsonpath.get("active").toString(), systemeActive);
		Map<String, String> timezone = response.jsonPath().getMap("timezone");
		CCUtils.assertEquals(timezone.get("id").toString(), timezoneId);
		CCUtils.assertTrue(jsonpath.get("practiceCount") == null, "practice count is not null");
	}

	public void verifyOrgNullErrorMessageResponseBody(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Active can not be null, Org type can not be blank, Parent type can not be blank";
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

	public void verifyCreateEnterpriseResponseBody(Response response, String enterprise) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertNotNull(jsonpath.get("orgId"), "Enterprise id was not in the response");
		CCUtils.assertNotNull(jsonpath.get("name"), "Enterprise name was not in the response");
		CCUtils.assertEquals(response.jsonPath().get("name").toString(), enterprise);

	}

	public void verifySearchEnterpriseResponseBody(Response response, String enterpriseId, String enterpriseName,
			String orgType, String clientType, String displayName, String sapNumber, String active, String testClient,
			String timezoneId, String enterpriseTimezoneDisplayNameApi, Integer practiceCountValue, Boolean isPurchased)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("orgId").toString(), enterpriseId);
		CCUtils.assertEquals(jsonpath.get("name").toString(), enterpriseName);
		CCUtils.assertEquals(jsonpath.get("orgType").toString(), orgType);
		CCUtils.assertEquals(jsonpath.get("clientType").toString(), clientType);
		CCUtils.assertEquals(jsonpath.get("displayName").toString(), displayName);
		CCUtils.assertEquals(jsonpath.get("sapNumber").toString(), sapNumber);
		CCUtils.assertEquals(jsonpath.get("active").toString(), active);
		CCUtils.assertEquals(jsonpath.get("testClient").toString(), testClient);
		if (Objects.isNull(jsonpath.get("timezone"))) {
			Log4jUtil.log("timezone is null");
		} else {
			Map<String, String> timezone = response.jsonPath().getMap("timezone");
			CCUtils.assertEquals(timezone.get("id").toString(), timezoneId);
			CCUtils.assertEquals(timezone.get("displayName").toString(), enterpriseTimezoneDisplayNameApi);
		}
		int practiceCount = jsonpath.get("practiceCount");
		CCUtils.assertEquals(practiceCount, practiceCountValue);
		List<String> solutionsList = new ArrayList<String>();
		solutionsList = response.jsonPath().getList("solutions");

	}

	public void verifySearchUpdatedEnterpriseResponseBody(Response response, String orgId, String displayName,
			String sapNumber, String enterpriseActive, String timezoneId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("orgId").toString(), orgId);
		CCUtils.assertEquals(jsonpath.get("displayName").toString(), displayName);
		CCUtils.assertEquals(jsonpath.get("sapNumber").toString(), sapNumber);
		CCUtils.assertEquals(jsonpath.get("active").toString(), enterpriseActive);
		Map<String, String> timezone = response.jsonPath().getMap("timezone");
		CCUtils.assertEquals(timezone.get("id").toString(), timezoneId);
	}

	public void verifyCreatePracticeResponseBody(Response response, String practice) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertNotNull(jsonpath.get("orgId"), "Practice id was not in the response");
		CCUtils.assertNotNull(jsonpath.get("name"), "Practice name was not in the response");
		CCUtils.assertEquals(response.jsonPath().get("name").toString(), practice);

	}

	public void verifySearchPracticeResponseBody(Response response, String practiceId, String practiceName,
			String orgType, String clientType, String displayName, String sapNumber, String active, String testClient,
			String timezoneId, String orgTimezoneDisplayNameApi, Boolean isPurchased) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("orgId").toString(), practiceId);
		CCUtils.assertEquals(jsonpath.get("name").toString(), practiceName);
		CCUtils.assertEquals(jsonpath.get("orgType").toString(), orgType);
		CCUtils.assertEquals(jsonpath.get("clientType").toString(), clientType);
		CCUtils.assertEquals(jsonpath.get("displayName").toString(), displayName);
		CCUtils.assertEquals(jsonpath.get("sapNumber").toString(), sapNumber);
		CCUtils.assertEquals(jsonpath.get("active").toString(), active);
		CCUtils.assertEquals(jsonpath.get("testClient").toString(), testClient);
		if (Objects.isNull(jsonpath.get("timezone"))) {
			Log4jUtil.log("timezone is null");
		} else {
			Map<String, String> timezone = response.jsonPath().getMap("timezone");
			CCUtils.assertEquals(timezone.get("id").toString(), timezoneId);
			CCUtils.assertEquals(timezone.get("displayName").toString(), orgTimezoneDisplayNameApi);
		}
		CCUtils.assertTrue(jsonpath.get("practiceCount") == null, "practice count is not null");
	}

	public void verifySearchUpdatedPracticeResponseBody(Response response, String orgId, String displayName,
			String sapNumber, String active, String timezoneId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("orgId").toString(), orgId);
		CCUtils.assertEquals(jsonpath.get("displayName").toString(), displayName);
		CCUtils.assertEquals(jsonpath.get("sapNumber").toString(), sapNumber);
		CCUtils.assertEquals(jsonpath.get("active").toString(), active);
		Map<String, String> timezone = response.jsonPath().getMap("timezone");
		CCUtils.assertEquals(timezone.get("id").toString(), timezoneId);
	}

	public void verifyErrorMessageInResponseForSystemOrgTypeField(Response response, String systemOrgType)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid org type=" + systemOrgType);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForEnterpriseOrgTypeField(Response response, String enterpriseOrgType)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid org type=" + enterpriseOrgType);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForPracticeOrgTypeField(Response response, String practiceOrgType)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid org type=" + practiceOrgType);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForSystemParentIdField(Response response, int orgSystemParentId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Parentid=" + orgSystemParentId + " doesn't exist");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForEnterpriseParentIdField(Response response, int orgEnterpriseParentId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Parentid=" + orgEnterpriseParentId + " doesn't exist");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForPracticeParentIdField(Response response, int orgPracticeParentId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Parentid=" + orgPracticeParentId + " doesn't exist");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForParentTypeFieldSystem(Response response, String orgSystemParentType)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid parent type=" + orgSystemParentType);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorMessageInResponseForParentTypeFieldEnterprise(Response response,
			String orgEnterpriseParentType) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid parent type=" + orgEnterpriseParentType);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorMessageInResponseForParentTypeFieldPractice(Response response, String orgPracticeParentType)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid parent type=" + orgPracticeParentType);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorMessageInResponseForSystemTimezoneIdField(Response response, String systemTimezoneId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Timezone id=" + systemTimezoneId + " doesn't exist");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorMessageInResponseForEnterpriseTimezoneIdField(Response response, String enterpriseTimezoneId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Timezone id=" + enterpriseTimezoneId + " doesn't exist");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorMessageInResponseForPracticeTimezoneIdField(Response response, String practiceTimezoneId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Timezone id=" + practiceTimezoneId + " doesn't exist");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyMaximumCharacterErrorMessageInResponseBody(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Name size maximum 100 character, Display name size maximum 100 character";
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

	public void verifyResponseBodyForSapNumberField(Response response) throws IOException {
		CCUtils.assertEquals(response.getBody().asString(), "true");

	}

	public void verifyInactiveClientErrorResponsforSystem(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "System cannot be Activated as Client is Inactive");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyInactiveSystemErrorResponsForPractice(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Practice cannot be Activated as System is Inactive");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyInactiveSystemErrorResponForEnterprise(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Enterprise cannot be Activated as System is Inactive");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyInactivePracticeErrorResponsWhileUpdataingActivePractice(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Practice cannot be Activated as Enterprise is Inactive");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageForClientIdField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Client not found");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorMessageForOrgIdField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Org not found for org id: 8978");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyMaximumCharacterAndFreeTextFieldErrorResponsForOrgNode(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Sap number size maximum 50 character, Display name size maximum 100 character";
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

	public void verifyGetClientTypesApiResponseBody(Response response) throws IOException {
		List<ClientTypes> actualClientTypeList = new ArrayList<ClientTypes>();
		actualClientTypeList.add(new ClientTypes(1, "Direct Client"));
		actualClientTypeList.add(new ClientTypes(101, "Dedicated IT"));
		actualClientTypeList.add(new ClientTypes(102, "GBS"));
		actualClientTypeList.add(new ClientTypes(103, "Health Prime International"));
		actualClientTypeList.add(new ClientTypes(104, "Itelagen"));
		actualClientTypeList.add(new ClientTypes(105, "KM Strategic"));
		actualClientTypeList.add(new ClientTypes(106, "MdCentric"));
		actualClientTypeList.add(new ClientTypes(107, "Medicalistics"));
		actualClientTypeList.add(new ClientTypes(108, "MTS/MMIC"));
		actualClientTypeList.add(new ClientTypes(109, "Newport Medical Group"));
		actualClientTypeList.add(new ClientTypes(110, "New Stork Healthdata Management"));
		actualClientTypeList.add(new ClientTypes(111, "OSIS"));
		actualClientTypeList.add(new ClientTypes(112, "SharedMed"));
		actualClientTypeList.add(new ClientTypes(113, "TSI"));
		actualClientTypeList.add(new ClientTypes(114, "Virmedice"));

		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		if (actualClientTypeList.size() == jsonArray.length()) {
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				CCUtils.assertTrue(
						actualClientTypeList
								.contains(new ClientTypes(jsonObject.getInt("id"), jsonObject.get("name").toString())),
						"Client type " + jsonObject.get("name").toString() + " doesn't exist");
			}
		} else if (jsonArray.length() == 0) {
			Log4jUtil.log("Client types list is empty");
		} else {
			Log4jUtil.log("Size of actual client types list and expected client types list is not same");
		}
	}

	public void verifyResponseBodyForClientNameField(Response response) throws IOException {
		CCUtils.assertEquals(response.getBody().asString(), "true");
	}

	public void verifyErrorMessageIfClientIdIsNull(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"null\"");
	}

	public void verifyErrorResponseBodyForClientIdField(Response response, String clientId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Client not found by clientId=" + clientId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorResponseBodyForOrgId(Response response, String orgId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Org not found for orgId=" + orgId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageForInvalidCombinationOfSourceAndTargetType(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Business unit source and target not found");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageForMovingOrgNodeToInactiveClient(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Client selected is inactive. Activate Client and try again.");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageForMovingOrgNodeToInactiveTarget(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Business unit cannot be moved to an inactive Target");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageForAlreadyExistSource(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Business unit source already exist in target");
	}

	public void verifyGetTimezonesApiResponseBody(Response response) throws IOException {
		List<Timezone> actualTimezoneList = new ArrayList<Timezone>();
		final List<String> expectedTimezoneDisplayName = Arrays.asList("International Date Line West",
				"Midway Island, Samoa", "Hawaii Standard Time", "Alaska Standard Time", "Pacific Standard Time",
				"Tijuana, Baja California", "Arizona Standard Time", "Chihuahua, La Paz, Mazatlan",
				"Mountain Standard Time", "Central America", "Central Standard Time",
				"Guadalajara, Mexico City, Monterrey", "Saskatchewan", "Bagota, Lima, Quito, Rio Branco",
				"Eastern Standard Time", "Indiana Standard Time", "Caracas", "Atlantic Standard Time", "La Paz",
				"Manaus", "Santiago", "Newfoundland", "Buenos Aires, Georgetown", "Greenland", "Montevideo",
				"Mid-Atlantic", "Azores", "Cape Verde Is.", "Casablanca,Monrovia,Reykjavik", "Greenwich Mean Time",
				"Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna", "Belgrade, Bratislava, Budapest, Ljubljana, Prague",
				"Brussels, Copenhagen, Madrid, Paris", "Sarajevo, Skopje, Warsaw, Zagreb", "West Central Africa",
				"Amman", "Athens, Bucharest, Istanbul", "Beirut", "Cairo", "Harare, Pretoria",
				"Helsinki, Kyiv, Riga, Sofia, Tallinn, Vilnius", "Jerusalem", "Minsk", "Baghdad", "Kuwait, Riyadh",
				"Moscow, St. Petersburg, Volgograd", "Nairobi", "Tehran", "Abu Dhabi, Muscat", "Baku", "Yerevan",
				"Kabul", "Ekaterinburg", "Islamabad, Karachi, Tashkent", "Chennai, Kolkata, Mumbai, New Delhi",
				"Kathmandu", "Almaty, Novosibirsk", "Astana, Dhaka", "Yangon (Rangoon)", "Bangkok, Hanoi, Jakarta",
				"Krasnoyarsk", "Hong Kong Standard Time", "Irkutsk, Ulaan Bataar", "Kuala Lumpur, Singapore", "Perth",
				"Taipei", "Osaka, Sapporo, Tokyo", "Seoul", "Yakutsk", "Adelaide", "Darwin", "Brisbane",
				"Canberra, Melbourne, Sydney", "Guam, Port Moresby", "Hobart", "Vladivostok",
				"Magadan, Solomon Is., New Caledonia", "Auckland, Wellington", "Nuku'alofa");

		final List<String> expectedTimezoneId = Arrays.asList("Etc/GMT-12", "US/Samoa", "US/Hawaii", "US/Alaska",
				"US/Pacific", "America/Tijuana", "US/Arizona", "America/Mazatlan", "US/Mountain", "Mexico/General",
				"US/Central", "Mexico/General", "Canada/Saskatchewan", "America/Lima", "US/Eastern", "US/East-Indiana",
				"America/Caracas", "Canada/Atlantic", "America/La_Paz", "America/Manaus", "America/Santiago",
				"Canada/Newfoundland", "America/Buenos_Aires", "America/Godthab", "America/Montevideo",
				"Atlantic/South_Georgia", "Atlantic/Azores", "Atlantic/Cape_Verde", "Africa/Casablanca",
				"Europe/Dublin", "Europe/Amsterdam", "Europe/Belgrade", "Europe/Brussels", "Europe/Sarajevo",
				"Africa/Lagos", "Asia/Amman", "Europe/Athens", "Asia/Beirut", "Africa/Cairo", "Africa/Harare",
				"Europe/Helsinki", "Asia/Jerusalem", "Europe/Minsk", "Asia/Baghdad", "Asia/Kuwait", "Europe/Moscow",
				"Africa/Nairobi", "Asia/Tehran", "Asia/Muscat", "Asia/Baku", "Asia/Yerevan", "Asia/Kabul",
				"Asia/Yekaterinburg", "Asia/Karachi", "Asia/Calcutta", "Asia/Katmandu", "Asia/Almaty", "Asia/Dhaka",
				"Asia/Rangoon", "Asia/Bangkok", "Asia/Krasnoyarsk", "Asia/Hong_Kong", "Asia/Irkutsk", "Asia/Singapore",
				"Australia/Perth", "Asia/Taipei", "Asia/Tokyo", "Asia/Seoul", "Asia/Yakutsk", "Australia/Adelaide",
				"Australia/Darwin", "Australia/Brisbane", "Australia/Sydney", "Pacific/Guam", "Australia/Hobart",
				"Asia/Vladivostok", "Asia/Magadan", "Pacific/Auckland", "Pacific/Tongatapu");

		for (int i = 0; i < expectedTimezoneDisplayName.size(); i++) {
			actualTimezoneList.add(new Timezone(expectedTimezoneId.get(i), expectedTimezoneDisplayName.get(i)));
		}
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		if (actualTimezoneList.size() == jsonArray.length()) {
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				CCUtils.assertEquals(actualTimezoneList.get(i),
						new Timezone(jsonObject.get("id").toString(), jsonObject.get("displayName").toString()));
			}
		} else if (jsonArray.length() == 0) {
			Log4jUtil.log("timezone list is empty");
		} else {
			Log4jUtil.log("Size of actual timezone list and expected timezone list is not same");
		}
	}

	public void verifyInvalidOrgTypeEnterpriseErrorResponsBelowClient(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid org type=ENTERPRISE");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageForInvalidParentId(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Parentid=401455678 doesn't exist");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageForNullValuesAgnosticPractice(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Org type can not be blank, Parent type can not be blank";
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

	public void verifyGetClientOrgIdResponseBody(Response response, Response createPracticeApiResponse)
			throws IOException {
		JsonPath jsonpathPractice = new JsonPath(createPracticeApiResponse.getBody().asString());
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			CCUtils.assertEquals(jsonpathPractice.get("name").toString(), jsonObject.get("name"));
			CCUtils.assertEquals(jsonpathPractice.getInt("id"), jsonObject.getInt("id"));
		}
	}

	public List<ClientOrg> validateAddMultiplePractice(Response createPracticeApiResponse,
			List<ClientOrg> actualClientOrgList) {

		JsonPath jsonpathPractice = new JsonPath(createPracticeApiResponse.getBody().asString());
		int practiceId = jsonpathPractice.get("id");
		String practiceName = jsonpathPractice.get("name");
		actualClientOrgList.add(new ClientOrg(practiceId, practiceName));
		return actualClientOrgList;
	}

	public void verifyMultipleGetClientOrgIdResponseBody(Response response, List<ClientOrg> actualClientOrgList)
			throws IOException {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		if (actualClientOrgList.size() == jsonArray.length()) {
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				Log4jUtil.log("json client id " + jsonObject.getInt("id"));
				CCUtils.assertTrue(
						actualClientOrgList
								.contains(new ClientOrg(jsonObject.getInt("id"), jsonObject.get("name").toString())),
						jsonObject.getInt("id") + jsonObject.get("name").toString());
			}
		} else if (jsonArray.length() == 0) {
			Log4jUtil.log("Response is null");
		} else {
			Log4jUtil.log("Size of actual response list and expected response list is not same");
		}

	}

	public void verifyAuditLogResponseBodyForNewlyCreatedOrgNode(Response response, int orgId, String context)
			throws IOException {
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		LocalDate date = LocalDate.now();
		JSONArray jsonArray = new JSONArray(jsonPath.getList(date.toString()));
		JSONObject jsonObject;
		if (jsonArray.length() > 0) {
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				if (jsonObject.get("resourceId").equals(orgId)) {
					CCUtils.assertEquals(jsonObject.get("context"), context);
					CCUtils.assertEquals(jsonObject.get("activityType"), context + "-CREATED-SUCCEEDED");
					CCUtils.assertEquals(jsonObject.get("resourceId"), orgId);
				}
			}
		} else if (jsonArray.length() == 0) {
			Log4jUtil.log("Response is null");
		}
	}

	public void verifyAuditLogResponseBodyForUpdatedOrgNode(JSONObject jsonObject, int orgId, String context,
			String field, String oldValue, String newValue) throws IOException {
		if (jsonObject.get("resourceId").equals(orgId)) {
			CCUtils.assertEquals(jsonObject.get("context"), context);
			CCUtils.assertEquals(jsonObject.get("activityType"), context + "-EDITED-SUCCEEDED");
			CCUtils.assertEquals(jsonObject.get("resourceId"), orgId);
			JSONObject targetData = jsonObject.getJSONObject("targetData");
			CCUtils.assertEquals(targetData.getString("field"), field);
			CCUtils.assertEquals(targetData.getString("oldValue"), oldValue);
			CCUtils.assertEquals(targetData.getString("newValue"), newValue);

		}
	}

	public void verifyErrorMessageInResponseWhenUsernameFieldBlank(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Required request body is missing: public com.medfusion.pxp.cc.api.PasswordResetResponse com.medfusion.pxp.cc.rest.PasswordResetController.forgetPassword(java.lang.String)");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseWhenUsernameFieldIsInvalid(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid request for forgot password");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseWhenUsernameIsUnregistered(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "You do not have access to this system.");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyMessageInResponseWhenUserIsExternal(Response response) {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Cannot send email to External user");
	}

	public void verifySuccessMessageInResponseWhenUserIsRegistered(Response response, String Username) {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Reset key updated for username " + Username);
	}

	public void verifySuccessMessageInResponseForValidToken(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Password reset successfully. You can now login with new credentials");
	}

	public void verifyErrorMessageInResponseWhenSpaceInPassword(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Space not allowed in password");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseWhenEnteredInvalidPassword(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForInvalidToken(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "This password reset token is invalid");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyGetProductsApiResponseBody(Response response) throws IOException {
		List<String> actualProductList = new ArrayList<String>();
		actualProductList.add("Forms");
		actualProductList.add("Pay");
		actualProductList.add("Virtual Visits");
		actualProductList.add("Portal");
		actualProductList.add("PSS");
		actualProductList.add("Chat");
		actualProductList.add("Engage");

		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		if (actualProductList.size() == jsonArray.length()) {
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				CCUtils.assertTrue(actualProductList.contains(jsonObject.getString("name")),
						"Product " + jsonObject.get("name").toString() + " doesn't exist");
			}
		} else if (jsonArray.length() == 0) {
			Log4jUtil.log("Product list is empty");
		} else {
			Log4jUtil.log("Size of actual Product list and expected Product list is not same");
		}
	}

	public void verifyAuditLogResponseBodyForMoveOrgNode(JSONObject jsonObject, int orgId, String context, String field,
			String oldValue, String newValue) throws IOException {
		if (jsonObject.get("resourceId").equals(orgId)) {
			CCUtils.assertEquals(jsonObject.get("context"), context);
			CCUtils.assertEquals(jsonObject.get("activityType"), context + "-MOVED-SUCCEEDED");
			CCUtils.assertEquals(jsonObject.get("resourceId"), orgId);
			JSONObject targetData = jsonObject.getJSONObject("targetData");
			CCUtils.assertEquals(targetData.getString("field"), field);
			CCUtils.assertEquals(targetData.getString("oldValue"), oldValue);
			CCUtils.assertEquals(targetData.getString("newValue"), newValue);
		} else {
			Log4jUtil.log("Incorrect resource id");
		}
	}

	public void validateAuditLogForMoveOrgNodeWithDoNotInherit(Response response, int orgId, String context,
			String orgNodeName, String oldParentName, String newParentName, String oldClientType, String newClientType)
			throws IOException {
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		LocalDate date = LocalDate.now();
		JSONArray jsonArray = new JSONArray(jsonPath.getList(date.toString()));
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			JSONObject targetData = jsonObject.getJSONObject(TARGETDATA);
			if (jsonObject.get(RESOURCE_ID).equals(orgId)) {
				if (targetData.getString(FIELD).equals(orgNodeName)) {
					verifyAuditLogResponseBodyForMoveOrgNode(jsonObject, orgId, context, orgNodeName, oldParentName,
							newParentName);
				} else if (targetData.getString(FIELD).equals(CLIENT_TYPE)) {
					verifyAuditLogResponseBodyForMoveOrgNode(jsonObject, orgId, context, CLIENT_TYPE, oldClientType,
							newClientType);
				}
			}
		}
	}

	public void validateAuditLogForMoveOrgNodeWithInherit(Response response, int orgId, String context,
			String orgNodeName, String oldParentName, String newParentName, String oldTimezone, String newTimezone,
			String oldSapId, String newSapId, String oldStatus, String newStatus, String oldClientType,
			String newClientType) throws IOException {
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		LocalDate date = LocalDate.now();
		JSONArray jsonArray = new JSONArray(jsonPath.getList(date.toString()));
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			JSONObject targetData = jsonObject.getJSONObject(TARGETDATA);
			if (jsonObject.get(RESOURCE_ID).equals(orgId)) {
				if (targetData.getString(FIELD).equals(orgNodeName)) {
					verifyAuditLogResponseBodyForMoveOrgNode(jsonObject, orgId, context, orgNodeName, oldParentName,
							newParentName);
				} else if (targetData.getString(FIELD).equals(TIMEZONE)) {
					verifyAuditLogResponseBodyForMoveOrgNode(jsonObject, orgId, context, TIMEZONE, oldTimezone,
							newTimezone);
				} else if (targetData.getString(FIELD).equals(SAP_ID)) {
					verifyAuditLogResponseBodyForMoveOrgNode(jsonObject, orgId, context, SAP_ID, oldSapId, newSapId);
				} else if (targetData.getString(FIELD).equals(STATUS)) {
					verifyAuditLogResponseBodyForMoveOrgNode(jsonObject, orgId, context, STATUS, oldStatus, newStatus);
				} else if (targetData.getString(FIELD).equals(CLIENT_TYPE)) {
					verifyAuditLogResponseBodyForMoveOrgNode(jsonObject, orgId, context, CLIENT_TYPE, oldClientType,
							newClientType);
				}
			}
		}
	}

	public void verifyGetSearchedAutosuggestionsClientResponses(Response response, String searchedData) {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		if ((CCUtils.verifyCharacterIsNumberOrDigit(searchedData).equals("letter"))
				|| ((CCUtils.verifyCharacterIsNumberOrDigit(searchedData).equals("letter"))
						&& (CCUtils.verifyCharacterIsNumberOrDigit(searchedData).equals("alphanumeric")))
				|| ((CCUtils.verifyCharacterIsNumberOrDigit(searchedData).equals("letter"))
						&& (CCUtils.verifyCharacterIsNumberOrDigit(searchedData).equals("alphanumeric"))
						&& (CCUtils.verifyCharacterIsNumberOrDigit(searchedData).equals("number")))
				|| ((CCUtils.verifyCharacterIsNumberOrDigit(searchedData).equals("letter"))
						&& (CCUtils.verifyCharacterIsNumberOrDigit(searchedData).equals("number")))) {
			Log4jUtil.log("Checking search data " + searchedData + " is 'letter','alphanumeric' or number.");
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				if (jsonObject.get("name").toString().contains(searchedData)) {
					CCUtils.assertTrue(true, "Validation for searched data present within name of client");
				}
			}
		} else if ((CCUtils.verifyCharacterIsNumberOrDigit(searchedData).equals("number")
				|| CCUtils.verifyCharacterIsNumberOrDigit(searchedData).equals("numberletter"))) {
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				if (jsonObject.get("name").toString().contains(searchedData)) {
					Log4jUtil.log(searchedData
							+ " is checked whether it is numberic or letter or both within id and name of client");
					CCUtils.assertTrue(true, "Validation for searched data present within id and name of client");
				}
			}
		}
	}

	public void verifyAuditLogResponseForMoveOrgNodeWithDoNotInherit(Response response, int orgId, String context,
			String orgNodeName, String oldParentName, String newParentName, String oldClientType, String newClientType)
			throws IOException {
		CCUtils.responseTimeValidation(response);
		CCUtils.assertEquals(response.getStatusCode(), 200);
		validateAuditLogForMoveOrgNodeWithDoNotInherit(response, orgId, context, orgNodeName, oldParentName,
				newParentName, oldClientType, newClientType);
	}

	public void verifyAuditLogResponseForMoveOrgNodeWithInherit(Response response, int orgId, String context,
			String orgNodeName, String oldParentName, String newParentName, String oldTimezone, String newTimezone,
			String oldSapId, String newSapId, String oldStatus, String newStatus, String oldClientType,
			String newClientType) throws IOException {
		CCUtils.responseTimeValidation(response);
		CCUtils.assertEquals(response.getStatusCode(), 200);
		validateAuditLogForMoveOrgNodeWithInherit(response, orgId, context, orgNodeName, oldParentName, newParentName,
				oldTimezone, newTimezone, oldSapId, newSapId, oldStatus, newStatus, oldClientType, newClientType);
	}

	public void validateAutosuggestionsClientMaxThreeCharactersErrorMessage(Response response, String searchData) {
		if ((response.jsonPath().get("message").toString()
				.contains("Client search data size should be minimum of 3 characters"))) {
			Log4jUtil.log("search data :" + searchData
					+ " is either letter or numeric value so validating with max 3 characters needed error message.");
			CCUtils.assertTrue(true, "Verification max 3 characters needed error");
		} else if ((response.jsonPath().get("message").toString().contains("Input search data is invalid"))) {
			Log4jUtil.log("search data :" + searchData
					+ " is either null or blank space value so validating with Input search data is invalid error message.");
			CCUtils.assertTrue(true, "Verification Input search data is invalid");
		}
	}

	public void verifyErrorMessageInResponseForInvalidMethod(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Request method 'PUT' not supported");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Method Not Allowed");
	}

	public void verifyErrorMessageInResponseForSapIdField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Client SAP ID already use");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorInResponseForInvalidUrl(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.getString("error"), "Not Found");
	}

	public void verifyResponseBodyUpdatingClientWithOutClientIdAndAllValuesNull(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.getString("error"), "Method Not Allowed");
		CCUtils.assertEquals(jsonpath.getString("message"), "Request method 'PUT' not supported");
	}

	public void verifyResponseBodyUpdatingClientWithClientIdAndAllValuesNull(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.getString("error"), "Bad Request");
		if (jsonpath.get("message").toString().contains(
				"Type can not be blank, Name can not be blank, Active can not be null, Sap number can not be blank"))
			CCUtils.assertTrue(true, "Message : " + jsonpath.get("message").toString() + " is not verified");
	}

	public void verifyResponseForExistingClientName(Response response, String existingClientName) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath.get("clientId"), "Client id is not in the response");
		CCUtils.assertNotNull(jsonpath.get("name"), "Client name is not in the response");
		CCUtils.assertEquals(response.jsonPath().get("name").toString(), existingClientName);
	}

	public void verifyErrorMessageIfClientNameIsNull(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
		CCUtils.assertEquals(jsonpath.get("message"),
				"Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"name\"");
	}

	public void verifyResponseForExistingClientSapNumber(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath.get("clientId"), "Client id is not in the response");
		CCUtils.assertNotNull(jsonpath.get("name"), "Client name is not in the response");
	}

	public void verifyErrorMessageIfClientSapNumberIsNull(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"sapnumber\"");
	}

	public void verifyErrorMessageInResponseForClientName(Response response, String existingClientName)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Client name=" + existingClientName + " already in use");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyGetClientDetailsApiResponseBody(Response response, String clientId, String clientName,
			int clientTypeId, String type, String sapNumber, String active, String testClient, String timezoneId,
			String timezoneDisplayName) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("clientId").toString(), clientId);
		CCUtils.assertEquals(jsonpath.get("name").toString(), clientName);
		Map<String, String> clientTypeMap = response.jsonPath().getMap("clientType");
		CCUtils.assertEquals(clientTypeMap.get("id"), clientTypeId);
		CCUtils.assertEquals(clientTypeMap.get("name").toString(), type);
		CCUtils.assertEquals(jsonpath.get("sapNumber").toString(), sapNumber);
		CCUtils.assertEquals(jsonpath.get("active").toString(), active);
		CCUtils.assertEquals(jsonpath.get("testClient").toString(), testClient);
		if (Objects.isNull(jsonpath.get("timezone"))) {
			Log4jUtil.log("timezone is null");
		} else {
			Map<String, String> timezone = response.jsonPath().getMap("timezone");
			CCUtils.assertEquals(timezone.get("id").toString(), timezoneId);
			CCUtils.assertEquals(timezone.get("displayName").toString(), timezoneDisplayName);
		}
	}

	public void verifyResponseForNonExistingClientId(Response response, int nonExistingClientId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Client not found by clientId=" + nonExistingClientId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForClientSapNumber(Response response, String existingClientsapNumber)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Client sap number=" + existingClientsapNumber + " already in use");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyGetOrganizationListApiResponse(Response response, List<OrgDetails> orgList) throws IOException {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		CCUtils.assertTrue(jsonArray.length() > 0, "Org list is empty");
		CCUtils.assertTrue(orgList.size() == jsonArray.length(),
				"Size of actual org list and expected org list is not same");
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			CCUtils.assertTrue(
					orgList.contains(new OrgDetails(jsonObject.getInt("orgId"), jsonObject.getString("name"))),
					"Org id " + jsonObject.getInt("orgId") + " doesn't exist");
		}
	}

	public void verifyErrorResponseBodyForClientIdSolutionsField(Response response, int clientId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Client not found by clientId=" + clientId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyCreateOrgIdPracticeResponseBody(Response response, String practice) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertNotNull(jsonpath.get("orgId"), "Practice id was not in the response");
		CCUtils.assertNotNull(jsonpath.get("name"), "Practice name was not in the response");
		CCUtils.assertEquals(response.jsonPath().get("name").toString(), practice);
	}

	public void verifySearchClientIdSolutionsResponseBody(Response response, int orgId, String practiceName,
			int solutionId, String status) throws IOException {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			JSONObject org = object.getJSONObject("org");
			CCUtils.assertEquals(org.getInt("orgId"), orgId);
			CCUtils.assertEquals(org.get("name").toString(), practiceName);
			JSONArray getSolutionsArray = object.getJSONArray("solutions");
			for (int j = 0; j < getSolutionsArray.length(); j++) {
				JSONObject solutionsObjects = getSolutionsArray.getJSONObject(j);
				System.out.println("print the solutions id" + solutionsObjects.getInt("solutionId"));
				CCUtils.assertEquals(solutionsObjects.getInt("solutionId"), solutionId);
				CCUtils.assertEquals(solutionsObjects.get("status").toString(), status);
				CCUtils.assertNotNull(solutionsObjects.get("solutionName"), "solutionName was not in the response");
				CCUtils.assertNotNull(solutionsObjects.get("date"), "date was not in the response");
				CCUtils.assertNotNull(solutionsObjects.get("statusNotes"), "statusNotes was not in the response");
			}
		}
	}

	public void verifyGetUserRolesResponseBody(Response response, String userType) throws IOException {
		List<String> actualUserRolesList = new ArrayList<String>();
		if (userType.equals("INTERNAL")) {
			actualUserRolesList.add("Solution Admin");
			actualUserRolesList.add("Super Admin");
			actualUserRolesList.add("Support T3");
			actualUserRolesList.add("Implementation");
			actualUserRolesList.add("Support T1");
			actualUserRolesList.add("Support T2");
			actualUserRolesList.add("Demo/Sales");
		} else if (userType.equals("CLIENT")) {
			actualUserRolesList.add("Client Admin");
			actualUserRolesList.add("Client Staff");
		} else if (userType.equals("VARESELLER")) {
			actualUserRolesList.add("VAR/Reseller Admin");
			actualUserRolesList.add("VAR/Reseller Staff");
		}

		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		CCUtils.assertTrue(jsonArray.length() > 0, "User roles list is empty");
		CCUtils.assertTrue(actualUserRolesList.size() == jsonArray.length(),
				"Size of actual user roles list and expected user roles list is not same");
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			System.out.println(actualUserRolesList.get(i).toString());
			CCUtils.assertTrue(actualUserRolesList.contains(jsonObject.getString("name")),
					jsonObject.getString("name") + "user role doesn't exist");
		}
	}

	public void verifyErrorResponseBodyForUserTypeField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid userType");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifySearchClientOrgHierarchyResponseBody(Response response, List<OrgNode> datalist)
			throws IOException {
		List<OrgNode> responseDatalist = new ArrayList<OrgNode>();
		JSONArray responseBody = new JSONArray(response.getBody().asString());
		for (int i = 0; i < responseBody.length(); i++) {
			responseDatalist.add(new OrgNode(responseBody.getJSONObject(i).getInt("orgId"),
					responseBody.getJSONObject(i).getInt("parentId"), responseBody.getJSONObject(i).getString("type"),
					responseBody.getJSONObject(i).getString("name"),
					responseBody.getJSONObject(i).getBoolean("active")));
		}

		CCUtils.assertTrue(responseDatalist.containsAll(datalist), "data list is not matching with response data list");
	}

	public void verifySearchClientOrgHierarchyResponseBodyDataLayer(Response response, List<OrgNode> datalist)
			throws IOException {
		List<OrgNode> responseDatalist = new ArrayList<OrgNode>();
		JSONArray responseBody = new JSONArray(response.getBody().asString());
		for (int i = 0; i < responseBody.length(); i++) {
			Integer parentId = null;
			if (!responseBody.getJSONObject(i).isNull("parentId")) {
				parentId = responseBody.getJSONObject(i).getInt("parentId");
			}
			responseDatalist.add(new OrgNode(responseBody.getJSONObject(i).getInt("orgId"), parentId,
					responseBody.getJSONObject(i).getString("type"), responseBody.getJSONObject(i).getString("name"),
					responseBody.getJSONObject(i).getBoolean("active")));
			if (responseDatalist.contains(datalist)) {
				CCUtils.assertTrue(true, "verifying response body");
			}
		}
	}

	public void verifyOrgNodeNullErrorMessageResponseBody(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Name cannot be null or empty, Status cannot be null";
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

	public void verifyErrorResponseForTimezoneField(Response response, String invalidTimezoneId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Timezone not found for id=" + invalidTimezoneId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyUpdateOrgApiResponseBody(Response response, int orgId, String nodeName) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.getInt("orgId"), orgId);
		CCUtils.assertEquals(jsonpath.get("name").toString(), nodeName);
	}

	public void verifyOrgNodeStatus(Response response, int orgId, String nodeName, String status) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.getInt("orgId"), orgId);
		CCUtils.assertEquals(jsonpath.get("name").toString(), nodeName);
		CCUtils.assertEquals(jsonpath.get("active").toString(), status);
	}

	public void verifyErrorMessageInResponseForOrgTypeField(Response response, String orgType) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "OrgType not found for type=" + orgType);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForParentTypeField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid parent type");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForOrgTimezoneIdField(Response response, String timezoneId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Timezone not found for id=" + timezoneId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForOrgParentIdField(Response response, int parentId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Client not found for clientId=" + parentId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyCreateOrgNodeApiResponseBody(Response response, String nodeName) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertNotNull(jsonpath.get("orgId"), "Org id was not in the response");
		CCUtils.assertNotNull(jsonpath.get("name"), "Org name was not in the response");
		CCUtils.assertEquals(response.jsonPath().get("name").toString(), nodeName);
	}

	public void verifyGetOrgNodeDetailsApiResponseBody(Response response, int orgId, String orgName, String orgType,
			String clientType, String displayName, String sapNumber, String active, String testClient,
			String timezoneId, String timezoneDisplayName, int practiceCount, Boolean isPurchased,
			List<Map<String, Object>> purchasedSolutionList) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.getInt("orgId"), orgId);
		CCUtils.assertEquals(jsonpath.get("name").toString(), orgName);
		CCUtils.assertEquals(jsonpath.get("orgType").toString(), orgType);
		CCUtils.assertEquals(jsonpath.get("clientType").toString(), clientType);
		CCUtils.assertEquals(jsonpath.get("displayName").toString(), displayName);
		CCUtils.assertEquals(jsonpath.get("sapNumber").toString(), sapNumber);
		CCUtils.assertEquals(jsonpath.get("active").toString(), active);
		CCUtils.assertEquals(jsonpath.get("testClient").toString(), testClient);
		if (Objects.isNull(jsonpath.get("timezone"))) {
			Log4jUtil.log("timezone is null");
		} else {
			Map<String, String> timezone = response.jsonPath().getMap("timezone");
			CCUtils.assertEquals(timezone.get("id").toString(), timezoneId);
			CCUtils.assertEquals(timezone.get("displayName").toString(), timezoneDisplayName);
		}
		CCUtils.assertEquals(jsonpath.getInt("practiceCount"), practiceCount);
		List<Integer> solutionsList = new ArrayList<Integer>();
		solutionsList = response.jsonPath().getList("solutions");
		List<Solutions> actualSolutionList = new ArrayList<Solutions>();
		if (practiceCount == 0) {
			Boolean solutionsObjectIsNull = jsonpath.getList("solutions").isEmpty();
			if (solutionsObjectIsNull.booleanValue()) {
				CCUtils.assertTrue(solutionsObjectIsNull, "solutions object is not null");
			} else {
				JSONArray jsonArray = new JSONArray(response.jsonPath().getList("solutions"));
				JSONObject jsonObject;
				actualSolutionList.add(new Solutions(0, 1103));
				actualSolutionList.add(new Solutions(0, 1105));
				actualSolutionList.add(new Solutions(0, 1401));
				actualSolutionList.add(new Solutions(0, 1101));
				actualSolutionList.add(new Solutions(0, 1104));
				actualSolutionList.add(new Solutions(0, 1106));
				actualSolutionList.add(new Solutions(0, 1102));
				CCUtils.assertTrue(actualSolutionList.size() == jsonArray.length(),
						"Size of actual solution list and expected solution list is not same");
				CCUtils.assertTrue(jsonArray.length() > 0, "solution list is empty");
				for (int i = 0; i < jsonArray.length(); i++) {
					jsonObject = jsonArray.getJSONObject(i);
					CCUtils.assertTrue(
							actualSolutionList.contains(
									new Solutions(jsonObject.getInt("activeCount"), jsonObject.getInt("solutionId"))),
							"Purchased Solution is not present");
				}

			}

		} else {
			if (isPurchased.booleanValue()) {
				if (!purchasedSolutionList.isEmpty()) {
					CCUtils.assertTrue(!solutionsList.isEmpty(), "Solutions list is empty");
					Integer value;
					List<Integer> solutionId = new ArrayList<Integer>();
					for (Map<String, Object> solutionList : purchasedSolutionList) {
						for (Entry<String, Object> entry : solutionList.entrySet()) {
							if (entry.getKey().contains("solutionId")) {
								value = Integer.parseInt(entry.getValue().toString());
								solutionId.add(value);
							}
						}
					}

					Log4jUtil.log(solutionId.toString());
					Map<Integer, Integer> productMap = new HashMap<Integer, Integer>();
					for (Integer productId : solutionId) {
						if (productMap.containsKey(productId)) {
							productMap.put(productId, productMap.get(productId) + 1);
						} else {
							productMap.put(productId, 1);
						}
					}

					for (Map.Entry<Integer, Integer> entry : productMap.entrySet()) {
						actualSolutionList.add(new Solutions(entry.getValue(), entry.getKey()));
						System.out.println("Product id" + entry.getKey() + "Active Count" + entry.getValue());
					}
					JSONArray jsonArray = new JSONArray(response.jsonPath().getList("solutions"));
					JSONObject jsonObject;
					if (actualSolutionList.size() == jsonArray.length()) {
						for (int i = 0; i < jsonArray.length(); i++) {
							jsonObject = jsonArray.getJSONObject(i);
							CCUtils.assertTrue(actualSolutionList.contains(
									new Solutions(jsonObject.getInt("activeCount"), jsonObject.getInt("solutionId"))),
									"Purchased Solution is not present");
						}
					} else if (jsonArray.length() == 0) {
						Log4jUtil.log("solution list is empty");
					} else {
						Log4jUtil.log("Size of actual solution list and expected solution list is not same");
					}
				}
			}
		}
	}

	public void verifyErrorMessageResponseBodyForMandatoryFields(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Status cannot be null, Name cannot be null";
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

	public void verifyErrorResponseForBlankFields(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "orgType, name, parentType cannot be null or empty");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorResponseForSolutionIdAndStatusField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "upsertOrgSolutions.solutionStatuses[0].status: Status can not be blank, upsertOrgSolutions.solutionStatuses[0].solutionId: SolutionId can not be null";
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

	public void verifyErrorResponseForInvalidOrgIdInParentIdField(Response response, int orgId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Parent org not found for orgId=" + orgId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageResponseBodyForNullValueInMandatoryField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Name cannot be null, Status cannot be null";
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

	public void verifyOrgNodeDetailsApiResponseBody(Response response, int orgId, String orgName, String active,
			String parentType, String sapNumber, String timezoneId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.getInt("orgId"), orgId);
		CCUtils.assertEquals(jsonpath.get("name").toString(), orgName);
		CCUtils.assertEquals(jsonpath.get("active").toString(), active);
		CCUtils.assertEquals(jsonpath.get("clientType").toString(), parentType);
		if (Objects.isNull(jsonpath.get("timezone"))) {
			Log4jUtil.log("timezone is null");
		} else {
			Map<String, String> timezone = response.jsonPath().getMap("timezone");
			CCUtils.assertEquals(timezone.get("id").toString(), timezoneId);
		}
	}

	public void verifyErrorResponseForDuplicateEmail(Response response, String existingEmail) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath.get("error").toString(), "Bad request");
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Duplicate email=" + existingEmail);
	}

	public void verifyErrorResponseForDuplicateUsername(Response response, String existingUsername) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath.get("error").toString(), "Bad request");
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Duplicate username=" + existingUsername);
	}

	public void verifyErrorResponseForDuplicateMobile(Response response, long existingMobileNumber) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath.get("error").toString(), "Bad request");
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Duplicate mobile no=" + existingMobileNumber);
	}

	public void verifyErrorResponseBodyForUserIdField(Response response, String userId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "User not found for userId=" + userId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyClientUserDetailsResponseBody(Response response, String authId, String userType, String firstName,
			String lastName, String userName, String email, String mobile, String userStatus, String clientId,
			String clientName, String providerId, String providerName, String statusMsg) throws NullPointerException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("authId").toString(), authId);
		CCUtils.assertEquals(jsonpath.get("userType").toString(), userType);
		CCUtils.assertEquals(jsonpath.get("firstName").toString(), firstName);
		CCUtils.assertEquals(jsonpath.get("lastName").toString(), lastName);
		CCUtils.assertEquals(jsonpath.get("userName").toString(), userName);
		CCUtils.assertEquals(jsonpath.get("email").toString(), email);
		CCUtils.assertEquals(jsonpath.get("mobile").toString(), mobile);
		CCUtils.assertEquals(jsonpath.get("userStatus").toString(), userStatus);
		CCUtils.assertEqualsNull(jsonpath.get("expiryDate"), null);
		CCUtils.assertEquals(jsonpath.get("client.id").toString(), clientId);
		CCUtils.assertEquals(jsonpath.get("client.name").toString(), clientName);
		CCUtils.assertEqualsNull(jsonpath.get("reseller"), null);
		CCUtils.assertEquals(jsonpath.get("provider.id").toString(), providerId);
		CCUtils.assertEquals(jsonpath.get("provider.name").toString(), providerName);
		CCUtils.assertEquals(jsonpath.get("statusMsg").toString(), statusMsg);
		return;
	}

	public void verifyUserDetailsResponseBody(Response response, String authId, String userType, String firstName,
			String lastName, String userName, String email, String mobile, String userStatus)
			throws NullPointerException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("authId").toString(), authId);
		CCUtils.assertEquals(jsonpath.get("userType").toString(), userType);
		CCUtils.assertEquals(jsonpath.get("firstName").toString(), firstName);
		CCUtils.assertEquals(jsonpath.get("lastName").toString(), lastName);
		CCUtils.assertEquals(jsonpath.get("userName").toString(), userName);
		CCUtils.assertEquals(jsonpath.get("email").toString(), email);
		CCUtils.assertEquals(jsonpath.get("mobile").toString(), mobile);
		CCUtils.assertEquals(jsonpath.get("userStatus").toString(), userStatus);
	}

	public void verifyErrorMessageForInactiveTargetOrgNode(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Org selected is inactive. Activate Org and try again.");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public String getClientType(Response response) {
		return response.jsonPath().getString("clientType");
	}

	public String getSapId(Response response) {
		return response.jsonPath().getString("sapNumber");
	}

	public String getTimezone(Response response) {
		Map<String, String> timezone = response.jsonPath().getMap("timezone");
		return timezone.get("id").toString();
	}

	public String getStatus(Response response) {
		return response.jsonPath().getString("active");
	}

	public String getClientNodeClientType(Response response) {
		return response.jsonPath().getString("type");
	}

	public void verifyErrorMessageForInvalidCombinationOfSourceAndTargetNode(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid orgType");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorResponseForInvalidFormatEmail(Response response, String existingEmail) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath.get("error").toString(), "Bad request");
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"validateUserDetail.email: must be a well-formed email address");
	}

	public void verifyErrorMessageInResponseForInvalidFirstName(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String responseMsg = jsonpath.getString("message").toString();
		String expectedResponse = "First name size maximum 100 characters, Only alphanumeric, ' and - are allowed";
		String alternateExpectedResponse = "Only alphanumeric, ' and - are allowed, First name size maximum 100 characters";
		if (responseMsg.equals(expectedResponse)) {
			CCUtils.assertEquals(responseMsg, expectedResponse);
		} else {
			CCUtils.assertEquals(responseMsg, alternateExpectedResponse);
		}

		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForInvalidUserType(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());

		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"JSON parse error: Cannot construct instance of `com.nextgen.pxp.cc.userdataservice.model.User$UserType`, problem: input value = reseller, but value must be one of Internal, Client, VAReseller; nested exception is com.fasterxml.jackson.databind.exc.ValueInstantiationException: Cannot construct instance of `com.nextgen.pxp.cc.userdataservice.model.User$UserType`, problem: input value = reseller, but value must be one of Internal, Client, VAReseller\n at [Source: (PushbackInputStream); line: 1, column: 246] (through reference chain: com.nextgen.pxp.cc.userdataservice.model.User[\"userType\"])");

		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForInvalidMobile(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Mobile number should be 10 digits");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForInvalidUsername(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Username size maximum 100 characters");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForSpetialCharacterInFirstName(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Only alphanumeric, ' and - are allowed, Only alphanumeric, ' and - are allowed");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForInvalidClientId(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid clientId");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForInvalidProviderId(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid providerId");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyNullUserErrorMessageResponseBody(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Email cannot be empty or null, FirstName cannot be empty or null, LastName cannot be empty or null, UserName cannot be empty or null, must not be null";
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

	public void verifyProductListForEcosystemPxp(Response response) throws IOException {
		List<String> actualProductList = new ArrayList<String>();
		actualProductList.add("Forms");
		actualProductList.add("Pay");
		actualProductList.add("Portal");
		actualProductList.add("PSS");
		actualProductList.add("Chat");
		actualProductList.add("Engage");
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		CCUtils.assertTrue(jsonArray.length() > 0, "Product list is empty");
		CCUtils.assertTrue(actualProductList.size() == jsonArray.length(),
				"Size of actual product list and expected product list is not same");
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			CCUtils.assertEquals(actualProductList.get(i), jsonObject.getString("name"));
		}
	}

	public void verifyProductListForEcosystemNgeAndTypePmehr(Response response) throws IOException {
		List<String> actualProductList = new ArrayList<String>();
		actualProductList.add("NextGen Enterprise");
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		CCUtils.assertTrue(jsonArray.length() > 0, "Product list is empty");
		CCUtils.assertTrue(actualProductList.size() == jsonArray.length(),
				"Size of actual product list and expected product list is not same");
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			CCUtils.assertEquals(actualProductList.get(i), jsonObject.getString("name"));
		}
	}

	public void verifyProductListForEcosystemOtto(Response response) throws IOException {
		List<String> actualProductList = new ArrayList<String>();
		actualProductList.add("Virtual Visits");
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		CCUtils.assertTrue(jsonArray.length() > 0, "Product list is empty");
		CCUtils.assertTrue(actualProductList.size() == jsonArray.length(),
				"Size of actual product list and expected product list is not same");
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			CCUtils.assertEquals(actualProductList.get(i), jsonObject.getString("name"));
		}
	}

	public void verifyProductListForTypePxp(Response response) throws IOException {
		List<String> actualProductList = new ArrayList<String>();
		actualProductList.add("Portal");
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		CCUtils.assertTrue(jsonArray.length() > 0, "Product list is empty");
		CCUtils.assertTrue(actualProductList.size() == jsonArray.length(),
				"Size of actual product list and expected product list is not same");
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			CCUtils.assertEquals(actualProductList.get(i), jsonObject.getString("name"));
		}
	}

	public void verifyErrorMessageForBlankField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid input");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyClientUserDetailsResponseBodyForBusinessLayer(Response response, String authId, String userType,
			String firstName, String lastName, String userName, String email, String mobile, String userStatus,
			String clientId, String clientName, String providerId, String providerName, String statusMsg)
			throws NullPointerException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("userType").toString(), userType);
		CCUtils.assertEquals(jsonpath.get("firstName").toString(), firstName);
		CCUtils.assertEquals(jsonpath.get("lastName").toString(), lastName);
		CCUtils.assertEquals(jsonpath.get("userName").toString(), userName);
		CCUtils.assertEquals(jsonpath.get("email").toString(), email);
		CCUtils.assertEquals(jsonpath.get("mobile").toString(), mobile);
		CCUtils.assertEquals(jsonpath.get("userStatus").toString(), userStatus);
		CCUtils.assertEqualsNull(jsonpath.get("expiryDate"), null);
		CCUtils.assertEquals(jsonpath.get("client.id").toString(), clientId);
		CCUtils.assertEquals(jsonpath.get("client.name").toString(), clientName);
		CCUtils.assertEqualsNull(jsonpath.get("reseller"), null);
		CCUtils.assertEquals(jsonpath.get("provider.id").toString(), providerId);
		CCUtils.assertEquals(jsonpath.get("provider.name").toString(), providerName);
		CCUtils.assertEquals(jsonpath.get("statusMsg").toString(), statusMsg);
		return;
	}

	public void verifyUserDetailsResponseBody(Response response, String userType, String firstName, String lastName,
			String userName, String email) throws NullPointerException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("userType").toString(), userType);
		CCUtils.assertEquals(jsonpath.get("firstName").toString(), firstName);
		CCUtils.assertEquals(jsonpath.get("lastName").toString(), lastName);
		CCUtils.assertEquals(jsonpath.get("userName").toString(), userName);
		CCUtils.assertEquals(jsonpath.get("email").toString(), email);
	}

	public void verifyErrorResponseForExistingUsername(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath.get("error").toString(), "Bad request");
		String errormsg = "User account already exists";
		if (jsonpath.get("message").toString().contains(errormsg)) {
			CCUtils.assertTrue(true, "found expected error msg in response body which is : " + errormsg);
		} else
			CCUtils.assertTrue(false, "expected error msg not found : " + errormsg);
	}

	public void verifyErrorMessageInResponseForInvalidUserTypeForBusinessLayer(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"JSON parse error: Cannot construct instance of `com.nextgen.pxp.cc.userservice.model.User$UserType`, problem: input value = reseller, but value must be one of Internal, Client, VAReseller; nested exception is com.fasterxml.jackson.databind.exc.ValueInstantiationException: Cannot construct instance of `com.nextgen.pxp.cc.userservice.model.User$UserType`, problem: input value = reseller, but value must be one of Internal, Client, VAReseller\n at [Source: (PushbackInputStream); line: 1, column: 212] (through reference chain: com.nextgen.pxp.cc.userservice.model.User[\"userType\"])");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorInResponseForInvalidMethod(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath.get("error").toString(), "Method Not Allowed");
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Request method 'PUT' not supported");
	}

	public void verifyErrorInResponseForNullValueForOrgIdField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.getString("error"), "Bad Request");
		CCUtils.assertEquals(jsonpath.getString("message"), "Invalid OrgId");
	}

	public void verifyErrorInResponseForNullValuesForOrgIdField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.getString("error"), "Bad Request");
		CCUtils.assertEquals(jsonpath.getString("message"), "Invalid input");
	}

	public void verifyErrorInResponseForNullValueForNonExistingUserId(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.getString("error"), "Bad Request");
		CCUtils.assertEquals(jsonpath.getString("message"), "User not found for userId=123");
	}

	public void verifyErrorInResponseForInvalidValueForOrgIdField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.getString("error"), "Bad Request");
		CCUtils.assertEquals(jsonpath.getString("message"),
				"JSON parse error: Cannot deserialize value of type `long` from String \"abc#$%\": not a valid `long` value; nested exception is com.fasterxml.jackson.databind.exc.InvalidFormatException: Cannot deserialize value of type `long` from String \"abc#$%\": not a valid `long` value\n at [Source: (PushbackInputStream); line: 1, column: 2] (through reference chain: java.util.ArrayList[0])");
	}

	public void verifyNonExistingAssociatedEntities(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.getString("error"), "Bad Request");
		CCUtils.assertEquals(jsonpath.getString("message"), "Entities not found");
	}

	public void getAssociatedEntities(Response response, ArrayList<Object> orgId) throws IOException {
		for (int i = 0; i < orgId.size(); i++) {
			if (response.asString().contains(String.valueOf(orgId.get(i)))) {
				CCUtils.assertTrue(true, " : this value is present in response body" + orgId.get(i));
			}
		}
	}

	public void verifyNullUserErrorMessageResponseBodyForInternalUser(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "UserName cannot be empty or null, LastName cannot be empty or null, FirstName cannot be empty or null, Email cannot be empty or null";
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

	public void verifyErrorResponseForDuplicateInternalUsername(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Duplicate username=testUsername";
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

	public void verifyErrorResponseForDuplicateInternalUserEmailId(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Duplicate email=TestEmail@gmail.com";
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

	public void verifyErrorResponseForDuplicateInternalUserMobileNumber(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Duplicate mobile no=9867007898";
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

	public void verifyErrorResponseForInternalUserMaximumLength(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Only alphanumeric, ' and - are allowed, Last name size maximum 100 characters, Only alphanumeric, ' and - are allowed, Username size maximum 100 characters, First name size maximum 100 characters";
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

	public void verifyErrorMessageInResponseForInvalidInternalUserType(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String responseString = jsonpath.get("message").toString();
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
		CCUtils.assertTrue(responseString.contains("value must be one of Internal, Client, VAReseller"),
				"When user entered incorrect user type then it display error message");
	}

	public void verifyErrorMessageInResponseForInvalidPostMethodCall(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Request method 'POST' not supported");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Method Not Allowed");
	}

	public void verifyErrorMessageInResponseForInternalUserInvalidUserType(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "User not found for userId=400228");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForInvalidMethodCall(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Method Not Allowed");
	}

	public void verifyOrgHierarchyResponseBody(Response response, List<OrgHierarchyDetails> datalist)
			throws IOException {
		List<OrgHierarchyDetails> responseDataList = new ArrayList<OrgHierarchyDetails>();
		JSONArray responseBody = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		CCUtils.assertTrue(responseBody.length() > 0, "Org list is empty");
		CCUtils.assertTrue(datalist.size() == responseBody.length(),
				"Size of actual org list and expected org list is not same");
		for (int i = 0; i < responseBody.length(); i++) {
			jsonObject = responseBody.getJSONObject(i);
			if (jsonObject.isNull("parentId")) {
				jsonObject.put("parentId", 0);
			}
			responseDataList.add(new OrgHierarchyDetails(jsonObject.getInt("orgId"), jsonObject.getString("name"),
					jsonObject.getString("type"), jsonObject.getBoolean("active"), jsonObject.getInt("parentId")));
		}
		CCUtils.assertEquals(responseDataList, datalist);
	}

	public void verifyErrorMessageInResponseForInvalidInput(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "The request is Invalid");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseUpdateInternalUserStatusWithInvaliduserId(Response response, String userId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "User not found for userId=" + userId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForInvalidUserStatus(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid status");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyActivateDeactivateUserApiResponseBody(Response response, List<Integer> userIds, String status,
			String message) throws IOException {
		List<UserStatus> actualUserStatusList = new ArrayList<UserStatus>();
		for (int i = 0; i < userIds.size(); i++) {
			actualUserStatusList.add(new UserStatus(userIds.get(i), status, message));
		}
		JSONArray responseBody = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		CCUtils.assertTrue(responseBody.length() > 0, "User list is empty");
		CCUtils.assertTrue(actualUserStatusList.size() == responseBody.length(),
				"Size of actual user list and expected user list is not same");
		for (int i = 0; i < responseBody.length(); i++) {
			jsonObject = responseBody.getJSONObject(i);
			CCUtils.assertTrue(
					actualUserStatusList.contains(new UserStatus(jsonObject.getInt("userId"),
							jsonObject.getString("status"), jsonObject.getString("message"))),
					"User Id " + jsonObject.getInt("userId") + " doesn't exist");
		}
	}

	public void verifyErrorMessageInResponseForBlankUserIdField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "userIds cannot be empty or null");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForBlankStatusField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "status cannot be empty or null");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyUpdateInternalResponseBody(Response response, int id) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.getInt("id"), id);
	}

	public void verifyErrorResponseForDuplicateInternalUsernameInDataLayer(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Duplicate username=testUsername");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Conflict");
	}

	public void verifyErrorResponseForDuplicateInternalEmailIdInDataLayer(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Duplicate email=TestEmail@gmail.com");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Conflict");
	}

	public void verifyErrorResponseForDuplicateInternalMobileNumberInDataLayer(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Duplicate mobile no=9867007898");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Conflict");
	}

	public void verifyErrorResponseBodyForNonexistingUserIdField(Response response, String userId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Audit not found for userId=" + userId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyResponseBodyForLoginDetail(Response response, String loginCount, String loginAttempts)
			throws NullPointerException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("loginCount").toString(), loginCount);
		CCUtils.assertEquals(jsonpath.get("loginAttempts").toString(), loginAttempts);
	}

	public void verifyNullUserErrorMessageResponseBodyForClientUser(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "UserName cannot be empty or null, LastName cannot be empty or null, FirstName cannot be empty or null, Email cannot be empty or null";
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

	public void verifyErrorResponseForDuplicateClientUsername(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Duplicate username=testUsername");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Conflict");
	}

	public void verifyErrorResponseForDuplicateClientEmailId(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Duplicate email=TestEmail@gmail.com");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Conflict");
	}

	public void verifyErrorResponseForDuplicateClientMobileNumber(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Duplicate mobile no=9867007898");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Conflict");
	}

	public void verifyErrorMessageInResponseForInvalidClientUserType(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String responseString = jsonpath.get("message").toString();
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
		CCUtils.assertTrue(responseString.contains("value must be one of Internal, Client, VAReseller"),
				"When user entered incorrect user type then it display error message");
	}

	public void verifyErrorMessageInResponseForClientUserInvalidUserType(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "User not found for userId=400228");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorResponseForClientUserMaximumLength(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Only alphanumeric, ' and - are allowed, Last name size maximum 100 characters, Only alphanumeric, ' and - are allowed, Username size maximum 100 characters, First name size maximum 100 characters";
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

	public void verifyNullRoleIdApiResponse(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid role");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyInvalidRoleIdApiResponse(Response response, int userId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "User not found for userId=" + userId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyInvalidUserIdApiResponse(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "User not found for userId=123");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForBlankStatusField_BL(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "status cannot be empty or null, must not be blank";
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

	public void verifyNullUserErrorMessageResponseBodyForVAResellerUser(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "UserName cannot be empty or null, LastName cannot be empty or null, FirstName cannot be empty or null, Email cannot be empty or null";
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

	public void verifyErrorResponseForDuplicateVAResellerUsername(Response response, String username)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Duplicate username=" + username);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Conflict");
	}

	public void verifyErrorResponseForDuplicateVAResellerEmailId(Response response, String emailId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Duplicate email=" + emailId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Conflict");
	}

	public void verifyErrorResponseForDuplicateVAResellerMobileNumber(Response response, String mobileNumber)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Duplicate mobile no=" + mobileNumber);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Conflict");
	}

	public void verifyMaximumCharactersErrorResponseForFirstNameLastNameAndUsernameField(Response response)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Only alphanumeric, ' and - are allowed, Last name size maximum 100 characters, Only alphanumeric, ' and - are allowed, Username size maximum 100 characters, First name size maximum 100 characters";
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

	public void verifyErrorMessageInResponseForInvalidVAResellerUserType(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String responseString = jsonpath.get("message").toString();
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
		CCUtils.assertTrue(responseString.contains("value must be one of Internal, Client, VAReseller"),
				"When user entered incorrect user type then it display error message");
	}

	public void verifyErrorResponseForNullUsernameAndEmailField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "UserName cannot be empty or null, Email cannot be empty or null";
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

	public void verifyUpdateVAResellerResponseBody(Response response, int id) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.getInt("id"), id);
	}

	public void verifyErrorMessageForInvalidBrandId(Response response, int brandId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Brand not found by id=" + brandId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageForInvalidImage(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid file");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyBrandingDetails(Response response, String orgId, String orgType, String name, String showLogo,
			String color, int brandId, String logo, String favicon, String emailLogo, String active)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		if (Objects.isNull(jsonpath.get("orgId"))) {
			Log4jUtil.log("orgId is null");
		} else {
			CCUtils.assertEquals(jsonpath.get("orgId").toString(), orgId);
		}
		if (Objects.isNull(jsonpath.get("orgType"))) {
			Log4jUtil.log("orgType is null");
		} else {
			CCUtils.assertEquals(jsonpath.get("orgType").toString(), orgType);
		}
		CCUtils.assertEquals(jsonpath.get("name").toString(), name);
		CCUtils.assertEquals(jsonpath.get("showLogo").toString(), showLogo);
		CCUtils.assertEquals(jsonpath.get("color").toString(), color);
		CCUtils.assertEquals(jsonpath.get("logo").toString(), logo);
		CCUtils.assertEquals(jsonpath.get("favicon").toString().toString(), favicon);
		CCUtils.assertEquals(jsonpath.get("emailLogo").toString(), emailLogo);
	}

	public void verifyErrorMessageInResponseForNullInput(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorResponseBodyForNonexistingproviderId(Response response, int providerId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Staff not found by id=" + providerId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyResponseBodyForProviderDetails(Response response, String firstName, String lastName,
			String gender, String status) throws NullPointerException, IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("firstName").toString(), firstName);
		CCUtils.assertEquals(jsonpath.get("lastName").toString(), lastName);
		CCUtils.assertEquals(jsonpath.get("gender").toString(), gender);
		CCUtils.assertEquals(jsonpath.get("status").toString(), status);

	}

	public void verifyErrorMessageInResponseForInvalidGenderValue(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"JSON parse error: Cannot deserialize value of type `java.lang.Character` from String \"ABC\": Expected either Integer value code or 1-character String; nested exception is com.fasterxml.jackson.databind.exc.InvalidFormatException: Cannot deserialize value of type `java.lang.Character` from String \"ABC\": Expected either Integer value code or 1-character String\n at [Source: (PushbackInputStream); line: 1, column: 60] (through reference chain: com.nextgen.pxp.cc.shareddataservice.model.ProviderData[\"gender\"])");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorResponseBodyForNonExistingPracticeId(Response response, int practiceId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Practice not found by id=" + practiceId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyAddOrRemoveResourcesForAppointmentTypeApiResponseBody(Response response,
			List<Integer> resourceIds, String status, String message) throws IOException {
		List<ResourceStatus> actualResourceStatusList = new ArrayList<ResourceStatus>();
		for (int i = 0; i < resourceIds.size(); i++) {
			actualResourceStatusList.add(new ResourceStatus(resourceIds.get(i), status, message));
		}
		JSONArray responseBody = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		CCUtils.assertTrue(responseBody.length() > 0, "User list is empty");
		CCUtils.assertTrue(actualResourceStatusList.size() == responseBody.length(),
				"Size of actual resource list and expected user list is not same");
		for (int i = 0; i < responseBody.length(); i++) {
			jsonObject = responseBody.getJSONObject(i);
			CCUtils.assertTrue(
					actualResourceStatusList.contains(new ResourceStatus(jsonObject.getInt("resourceId"),
							jsonObject.getString("status"), jsonObject.getString("message"))),
					"Resource Id " + jsonObject.getInt("resourceId") + " doesn't exist");
		}
	}

	public void verifyErrorResponseBodyForEmptyList(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid Solution statuses");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyGetOrgIdSolutionsResponseBody(Response response, int orgId, String practiceName,
			List<OrgSolutions> productList) throws IOException {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			JSONObject org = object.getJSONObject("org");
			CCUtils.assertEquals(org.getInt("orgId"), orgId);
			CCUtils.assertEquals(org.get("name").toString(), practiceName);
			JSONArray getSolutionsArray = object.getJSONArray("solutions");
			CCUtils.assertTrue(getSolutionsArray.length() == productList.size(),
					"Productlist size is not same as solution array size");
			for (int j = 0; j < getSolutionsArray.length(); j++) {
				JSONObject solutionsObjects = getSolutionsArray.getJSONObject(j);
				CCUtils.assertTrue(
						productList.contains(new OrgSolutions(solutionsObjects.get("solutionId").toString(),
								solutionsObjects.get("status").toString())),
						"Product Id " + solutionsObjects.get("solutionId") + " doesn't exist");
				CCUtils.assertNotNull(solutionsObjects.get("solutionName"), "solutionName was not in the response");
			}
		}
	}

	public List<OrgSolutions> getSolutionsDetails(Response response) {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		List<OrgSolutions> actualList = new ArrayList<OrgSolutions>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			JSONArray getSolutionsArray = object.getJSONArray("solutions");
			for (int j = 0; j < getSolutionsArray.length(); j++) {
				JSONObject solutionsObjects = getSolutionsArray.getJSONObject(j);
				actualList.add(new OrgSolutions(String.valueOf(solutionsObjects.getInt("solutionId")),
						solutionsObjects.get("status").toString()));
			}
		}
		return actualList;
	}

	public void verifyResponseBodyForProviderAssociatedToPractices(Response response, ArrayList<Integer> practiceIds)
			throws IOException {
		for (int i = 0; i < practiceIds.size(); i++) {
			if (response.asString().contains(String.valueOf(practiceIds.get(i)))) {
				CCUtils.assertTrue(true, " : this value is present in response body" + practiceIds.get(i));
			}
		}
	}

	public void verifyUserInfoApiResponseBody(Response response, int userId, String firstName, String lastName,
			String type, String clientId) throws NullPointerException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.getInt("userId"), userId);
		CCUtils.assertEquals(jsonpath.get("firstName").toString(), firstName);
		CCUtils.assertEquals(jsonpath.get("lastName").toString(), lastName);
		CCUtils.assertEquals(jsonpath.get("type").toString(), type);
		if (Objects.isNull(jsonpath.get("clientId"))) {
			Log4jUtil.log("client id is null");
		} else {
			CCUtils.assertEquals(jsonpath.getInt("clientId"), Integer.valueOf(clientId));
		}
	}

	public void verifyErrorResponseBodyForInvalidAuthId(Response response, String authId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "User not found for authId=" + authId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorResponseForInvalidClientEmailId(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"must be a well-formed email address, Email address must be well formed");
	}

	public void verifyUpdateClientResponseBody(Response response, int id) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.getInt("id"), id);
	}

	public void verifyBrandingDetails_DL(Response response, String orgId, String orgType, String name, String color)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("name").toString(), name);
		CCUtils.assertEquals(jsonpath.get("color").toString(), color);
	}

	public void verifyInternalUserOutputWithValidInput(Response response, String getSearchUserDetails) {
		JsonPath jsonPath = new JsonPath(response.asString());
		Log4jUtil.log("Display total count of locations in response body : " + jsonPath.get("totalCount"));
		Log4jUtil.log("Display list of clients in response body : " + jsonPath.get("data"));
		Log4jUtil.log("Get the size of clients in response body : " + jsonPath.getInt("data.size()"));
		Log4jUtil.log("Page Number : " + jsonPath.getInt("pageNo"));
		Log4jUtil.log("Items(Clients) per Page : " + jsonPath.getInt("pageSize"));
		Log4jUtil.log("Comparing Searched Items names/ids with user data received in Response body.");
		for (int dataArrayIteration = 0; dataArrayIteration < jsonPath.getInt("data.size()"); dataArrayIteration++) {
			String userIteratePerData = jsonPath.get("data[" + dataArrayIteration + "].firstName");
			Log4jUtil.log("Print Value : " + userIteratePerData);
			if (userIteratePerData.contains(getSearchUserDetails)) {
				Log4jUtil.log("searched value :" + getSearchUserDetails + "present within response body's value"
						+ userIteratePerData);
				CCUtils.assertTrue(true,
						"Found data " + userIteratePerData + " is present in expected data" + getSearchUserDetails);
			} else if (userIteratePerData.contains(getSearchUserDetails)) {
				Log4jUtil.log("searched value :" + getSearchUserDetails + "present within response body's value"
						+ userIteratePerData);
				CCUtils.assertTrue(true,
						"Found data " + userIteratePerData + " is present in expected data" + getSearchUserDetails);
			}
		}
	}

	public void verifyErrorMessageForInvalidInput(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid input");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageForActiveStatusAsNull(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Active cannot be null");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorResponseBodyWhenUserEnterClientIdInOrgIdField(Response response, int clientId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Org not found for orgId=" + clientId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyMaximumCharacterErrorMessageInResponseBodyForNameAndSolutionsDisplayField(Response response)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Name size maximum 100 character, Display name size maximum 100 character";
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

	public void verifyErrorResponseBodyForInvalidAppointmentTypeId(Response response, String AppointmentTypeId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Appointment not found for id=" + AppointmentTypeId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyGetAppointmentResponseBody(Response response, String name, String externalId, int duration,
			String durationUnit, String status) throws NullPointerException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("name").toString(), name);
		CCUtils.assertEquals(jsonpath.get("externalId").toString(), externalId);
		CCUtils.assertEquals(jsonpath.getInt("duration"), duration);
		CCUtils.assertEquals(jsonpath.get("durationUnit").toString(), durationUnit);
		CCUtils.assertEquals(jsonpath.get("status").toString(), status);
	}

	public void verifyInvalidRequestValidation(Response CreateBrandApiResponse) throws IOException {
		CCUtils.responseTimeValidation(CreateBrandApiResponse);
		JsonPath jsonpath = new JsonPath(CreateBrandApiResponse.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("status"), 400);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyServerRequestRequestValidation(Response CreateBrandApiResponse) throws IOException {
		CCUtils.responseTimeValidation(CreateBrandApiResponse);
		JsonPath jsonpath = new JsonPath(CreateBrandApiResponse.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("status"), 500);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Internal Server Error");

	}

	public void verifyResponseCodeForNonExistingUserType(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(response.getStatusCode(), 400);
		CCUtils.assertEquals(jsonPath.get("error"), "Bad Request");
	}

	public void verifyResponseForEmptyOrMisFormatData(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonPath.get("message").toString(),
				"input value =  , but value must be one of Internal, Client, VAReseller");
		CCUtils.assertEquals(jsonPath.get("error").toString(), "Bad Request");
	}

	public void verifyUserOutputWithValidInput(Response response, String getSearchUserDetails) {
		JsonPath jsonPath = new JsonPath(response.asString());
		Log4jUtil.log("Display total count of locations in response body : " + jsonPath.get("totalCount"));
		Log4jUtil.log("Display list of clients in response body : " + jsonPath.get("data"));
		Log4jUtil.log("Get the size of clients in response body : " + jsonPath.getInt("data.size()"));
		Log4jUtil.log("Items(Clients) per Page : " + jsonPath.getInt("pageSize"));
		Log4jUtil.log("Comparing Searched Items names/ids with user data received in Response body.");
		for (int dataArrayIteration = 0; dataArrayIteration < jsonPath.getInt("data.size()"); dataArrayIteration++) {
			String userIteratePerData = jsonPath.get("data[" + dataArrayIteration + "].firstName");
			Log4jUtil.log("Print Value : " + userIteratePerData);
			if (userIteratePerData.contains(getSearchUserDetails)) {
				Log4jUtil.log("searched value :" + getSearchUserDetails + "present within response body's value"
						+ userIteratePerData);
				CCUtils.assertTrue(true,
						"Found data " + userIteratePerData + " is present in expected data" + getSearchUserDetails);
			}
		}
	}

	public void verifyResponseBodyForGetResourceForProvider(Response response) throws IOException {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
		}
		CCUtils.assertEquals(jsonObject.getString("type"), "PROVIDER");
		CCUtils.assertEquals(jsonArray.get(0).toString().contains("resourceId"), true);
	}

	public void verifyResponseForEmptyOrNULLOrMisFormatData(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonPath.get("error"), "Bad Request");
		CCUtils.assertEquals(jsonPath.get("message").toString().contains("NumberFormatException"), true);
	}

	public void verifyResponseForEmptyData(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonPath.get("error"), "Internal Server Error");
	}

	public void verifyResponseBodyForGetResourceForProvider_DL(Response response) throws IOException {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
		}
		CCUtils.assertEquals(jsonObject.getString("type"), "PROVIDER");
		CCUtils.assertEquals(jsonArray.get(0).toString().contains("resourceId"), true);
	}

	public void verifyResponseForEmptyOrNULLOrMisFormatData_DL(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonPath.get("error"), "Bad Request");
		CCUtils.assertEquals(jsonPath.get("message").toString().contains("NumberFormatException"), true);
	}

	public void verifyResponseForEmptyData_DL(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonPath.get("error"), "Internal Server Error");
	}

	public void verifyUpdateBrandingDetails(Response response, String brandId, String name) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("name").toString(), name);
		CCUtils.assertEquals(jsonpath.get("brandId").toString(), brandId);
	}

	public void verifyErrorMessageInResponseForInvalidOrgId(Response response, String orgId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Org not found for OrgId=" + orgId);
	}

	public void verifyErrorMessageForOrgIdAsNull(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "OrgId cannot be empty or null");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageForNameFieldAsNull(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Name cannot be empty or null");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageForNullValuesInPayload(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String responseMsg = jsonpath.getString("message").toString();
		String expectedResponse = "OrgId cannot be empty or null, Name cannot be empty or null";
		String alternateExpectedResponse = "Name cannot be empty or null, OrgId cannot be empty or null";
		if (responseMsg.equals(expectedResponse)) {
			CCUtils.assertEquals(responseMsg, expectedResponse);
		} else {
			CCUtils.assertEquals(responseMsg, alternateExpectedResponse);
		}

		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorResponseMessageForNonExistingAppointmentTypeId(Response response, String AppointmentTypeId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Appointment not found for id=" + AppointmentTypeId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifySuccessResponseBodyForExistingAppointmentTypeId(Response response, String name, String externalId,
			String duration) {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.getString("name"), name);
		CCUtils.assertEquals(jsonpath.get("externalId").toString(), externalId);
		CCUtils.assertEquals(jsonpath.get("duration").toString(), duration);
	}

	public void verifyBrandingDetails_BL(Response response, int orgId, String orgType, String name, String showLogo,
			String color, String logo, String favicon, String emailLogo) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		if (Objects.isNull(jsonpath.get("orgId"))) {
			Log4jUtil.log("orgId is null");
		} else {
			CCUtils.assertEquals(jsonpath.getInt("orgId"), orgId);
		}
		if (Objects.isNull(jsonpath.get("orgType"))) {
			Log4jUtil.log("orgType is null");
		} else {
			CCUtils.assertEquals(jsonpath.get("orgType").toString(), orgType);
		}
		if (Objects.isNull(jsonpath.get("logo"))) {
			Log4jUtil.log("logo is null");
		} else {
			CCUtils.assertEquals(jsonpath.getInt("logo"), logo);
		}
		if (Objects.isNull(jsonpath.get("favicon"))) {
			Log4jUtil.log("favicon logo is null");
		} else {
			CCUtils.assertEquals(jsonpath.getInt("favicon"), favicon);
		}
		if (Objects.isNull(jsonpath.get("emailLogo"))) {
			Log4jUtil.log("emailLogo is null");
		} else {
			CCUtils.assertEquals(jsonpath.getInt("emailLogo"), emailLogo);
		}
		CCUtils.assertEquals(jsonpath.get("name").toString(), name);
		CCUtils.assertEquals(jsonpath.get("showLogo").toString(), showLogo);
		CCUtils.assertEquals(jsonpath.get("color").toString(), color);
	}

	public void verifyResponseBodyForProviderDetails_BL(Response response, String firstName, String lastName,
			String gender, String status) throws NullPointerException, IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("firstName").toString(), firstName);
		CCUtils.assertEquals(jsonpath.get("lastName").toString(), lastName);
		CCUtils.assertEquals(jsonpath.get("status").toString(), status);
	}

	public void verifyOutputFromValidInput(Response response) {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
		}
		CCUtils.assertEquals(jsonArray.get(0).toString().contains("name"), true);
	}

	public void verifyResponseBodyForGetPracticeProvider(Response response, String en, String es, String email,
			String mobile) throws NullPointerException, IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		Map<String, String> displayName = response.jsonPath().getMap("displayName");
		CCUtils.assertEquals(displayName.get("en").toString(), en);
		CCUtils.assertEquals(displayName.get("es").toString(), es);
		CCUtils.assertEquals(jsonpath.get("email").toString(), email);
		CCUtils.assertEquals(jsonpath.get("mobile").toString(), mobile);
	}

	public void verifyErrorResponseBodyForNonExistingPracticeIdTou(Response response, int practiceId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Practice not found for orgId=" + practiceId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorResponseBodyForInvalidSolTou(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Solution not found for practice");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyOutputFromValidInputForPracticeWebsites(Response response) {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
		}
		CCUtils.assertEquals(jsonArray.get(0).toString().contains("siteId"), true);
	}

	public void verifyErrorResponseBodyForNullTimezone(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid timezone");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorResponseBodyForNameField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Name size maximum 100 character");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorResponseBodyForInvalidProductId(Response response, int productId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Product not found by productId=" + productId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForInvalidPatchMethodCall(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Request method 'PATCH' not supported");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Method Not Allowed");
	}

	public void verifyCreateLocationApiResponseBody(Response response, String locationName) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertNotNull(jsonpath.get("id"), "location id is not in the response");
		CCUtils.assertNotNull(jsonpath.get("name"), "location name is not in the response");
		CCUtils.assertEquals(response.jsonPath().get("name").toString(), locationName);
	}

	public void verifyGetLocationDetailsApiResponseBody(Response response, String locationName, String addressLine1,
			String addressLine2, String city, String state, String zipcode, float lat, float lng, String code,
			String timezoneDisplayName) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.getString("name"), locationName);
		Map<String, String> timezone = jsonpath.getMap("timezone");
		CCUtils.assertEquals(timezone.get("code").toString(), code);
		CCUtils.assertEquals(timezone.get("name").toString(), timezoneDisplayName);
		Map<String, String> address = jsonpath.getMap("address");
		CCUtils.assertEquals(address.get("addressLine1").toString(), addressLine1);
		CCUtils.assertEquals(address.get("addressLine2").toString(), addressLine2);
		CCUtils.assertEquals(address.get("city").toString(), city);
		CCUtils.assertEquals(address.get("state").toString(), state);
		CCUtils.assertEquals(address.get("zipcode").toString(), zipcode);
		CCUtils.assertEquals(address.get("lat"), lat);
		CCUtils.assertEquals(address.get("lng"), lng);
	}

	public void verifyErrorResponseBodyForInvalidData(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Address Line1 size maximum 100 characters, Address Line2 size maximum 100 characters, Display Name size maximum 100 characters, Zipcode size maximum 50 characters, City size maximum 50 characters, State size maximum 2 characters, Name size maximum 100 characters";
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

	public void verifyErrorResponseBodyForMandatoryFieldsForLocation(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Zipcode cannot be empty or null, TimezoneCode cannot be empty or null, City cannot be empty or null, Address Line1 cannot be empty or null, State cannot be empty or null, Name cannot be empty or null";
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

	public void verifyCreateProviderApiResponseBody(Response response, String firstName, String lastName)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertNotNull(jsonpath.get("providerId"), "provider id is not in the response");
		CCUtils.assertEquals(jsonpath.getString("firstName"), firstName);
		CCUtils.assertEquals(jsonpath.getString("lastName"), lastName);
	}

	public void verifyErrorResponseForInvalidEmailFormat(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath.get("error").toString(), "Bad request");
		CCUtils.assertEquals(jsonpath.get("message").toString(), "must be a well-formed email address");
	}

	public void verifyErrorResponseForInvalidMobileNumber(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Only numeric values are allowed, Mobile number should be 10 digits";
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

	public void verifyErrorResponseForTimezoneCodeField(Response response, String timezoneCode) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath.get("error").toString(), "Bad request");
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Timezone not found for TimezoneId=" + timezoneCode);
	}

	public void verifyBrandingImageDetails_BL(Response response, String orgId, String orgType, String name,
			String showLogo, String color) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		if (Objects.isNull(jsonpath.get("orgId"))) {
			Log4jUtil.log("orgId is null");
		} else {
			CCUtils.assertEquals(jsonpath.get("orgId").toString(), orgId);
		}
		if (Objects.isNull(jsonpath.get("orgType"))) {
			Log4jUtil.log("orgType is null");
		} else {
			CCUtils.assertEquals(jsonpath.get("orgType").toString(), orgType);
		}
		CCUtils.assertEquals(jsonpath.get("name").toString(), name);
		CCUtils.assertEquals(jsonpath.get("showLogo").toString(), showLogo);
		CCUtils.assertEquals(jsonpath.get("color").toString(), color);
	}

	public void verifyGetSocialDetails(Response getSocialApiResponse) {
		HashMap<String, Object> addSocials1 = new HashMap<String, Object>();
		HashMap<String, Object> addSocials2 = new HashMap<String, Object>();
		HashMap<String, Object> addSocials3 = new HashMap<String, Object>();
		HashMap<String, Object> addSocials4 = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> getSocials = new ArrayList<HashMap<String, Object>>();
		addSocials1.put("socialId", 1);
		addSocials1.put("name", "Twitter");
		addSocials1.put("icon", "twitter-square");
		addSocials2.put("socialId", 2);
		addSocials2.put("name", "Facebook");
		addSocials2.put("icon", "facebook-square");
		addSocials3.put("socialId", 3);
		addSocials3.put("name", "Linkedin");
		addSocials3.put("icon", "linkedin-square");
		addSocials4.put("socialId", 4);
		addSocials4.put("name", "Youtube");
		addSocials4.put("icon", "youtube-play");
		getSocials.clear();
		getSocials.add(addSocials1);
		getSocials.add(addSocials2);
		getSocials.add(addSocials3);
		getSocials.add(addSocials4);
		List<String> socialIds = getSocialApiResponse.jsonPath().get("socialId");
		for (int i = 0; i < socialIds.size(); i++) {
			String responseName = getSocialApiResponse.jsonPath().get("name[" + i + "]");
			String expectedName = getSocials.get(i).get("name").toString();
			if (responseName.equalsIgnoreCase(expectedName)) {
				int responseSocialId = getSocialApiResponse.jsonPath().get("socialId[" + i + "]");
				String responseIcon = getSocialApiResponse.jsonPath().get("icon[" + i + "]");
				int expectedSocialId = Integer.parseInt(getSocials.get(i).get("socialId").toString());
				String expectedIcon = getSocials.get(i).get("icon").toString();
				if (responseSocialId == expectedSocialId && responseIcon.equalsIgnoreCase(expectedIcon)) {
					Log4jUtil.log("Validation is Successful for " + responseName);
				} else {
					Log4jUtil.log("Validation is not Successful for " + responseName);
				}
			}
		}
	}

	public void verifyErrorResponseForSolutionId(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
		if (jsonpath.get("message").toString().contains("SolutionId can not be null")) {
			CCUtils.assertTrue(true, "SolutionId can not be null message validated");
		}
	}

	public void verifyErrorResponseForStatus(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
		if (jsonpath.get("message").toString().contains("Status can not be blank")) {
			CCUtils.assertTrue(true, "Status can not be blank message validated");
		}
	}

	public void verifyGetProductDetailsWithDescription(Response getProductApiResponse) {
		HashMap<String, Object> addProduct1 = new HashMap<String, Object>();
		HashMap<String, Object> addProduct2 = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> addProducts = new ArrayList<HashMap<String, Object>>();
		addProduct1.put("productId", 88);
		addProduct1.put("name", "MFRest_NG");
		addProduct1.put("active", true);
		addProduct1.put("description", "MedfusionREST - NextGen");
		addProduct2.put("productId", 82);
		addProduct2.put("name", "GW");
		addProduct2.put("active", true);
		addProduct2.put("description", "Greenway PrimeSuite EMR");
		addProducts.clear();
		addProducts.add(addProduct1);
		addProducts.add(addProduct2);
		List<String> productIds = getProductApiResponse.jsonPath().get("productId");
		for (int i = 0; i < productIds.size(); i++) {
			int responseProductId = getProductApiResponse.jsonPath().get("productId[" + i + "]");
			int expectedProductId = Integer.parseInt(addProducts.get(0).get("productId").toString());
			int expectedProductId1 = Integer.parseInt(addProducts.get(1).get("productId").toString());
			if (responseProductId == expectedProductId || responseProductId == expectedProductId1) {
				String responseName = getProductApiResponse.jsonPath().get("name[" + i + "]");
				String expectedName = addProducts.get(0).get("name").toString();
				String expectedName1 = addProducts.get(1).get("name").toString();
				String responseDescription = getProductApiResponse.jsonPath().get("description[" + i + "]");
				String expectedDescription = addProducts.get(0).get("description").toString();
				String expectedDescription1 = addProducts.get(1).get("description").toString();
				if ((responseName.equalsIgnoreCase(expectedName)
						&& responseDescription.equalsIgnoreCase(expectedDescription))
						|| responseName.equalsIgnoreCase(expectedName1)
								&& responseDescription.equalsIgnoreCase(expectedDescription1)) {
					Log4jUtil.log("Validation is Successful for " + responseName);
				} else {
					Log4jUtil.log("Validation is not Successful for " + responseName);
				}
			}
		}
	}

	public void verifyErrorMessageForNonExistingInput(Response response, int solutionId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath.get("error").toString(), "Bad request");
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Org product not found by practiceId=" + solutionId);
	}

	public void verifyAddOrRemoveAppointmentTypesForResourceApiResponseBody(Response response,
			List<Integer> appointmentTypeIds, String status, String message) throws IOException {
		List<ResourceStatus> actualResourceStatusList = new ArrayList<ResourceStatus>();
		for (int i = 0; i < appointmentTypeIds.size(); i++) {
			actualResourceStatusList.add(new ResourceStatus(appointmentTypeIds.get(i), status, message));
		}
		JSONArray responseBody = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		CCUtils.assertTrue(responseBody.length() > 0, "appointmentid list is empty");
		CCUtils.assertTrue(actualResourceStatusList.size() == responseBody.length(),
				"Size of actual appointment type list and expected appointment id list is not same");
		for (int i = 0; i < responseBody.length(); i++) {
			jsonObject = responseBody.getJSONObject(i);
			CCUtils.assertTrue(
					actualResourceStatusList.contains(new ResourceStatus(jsonObject.getInt("id"),
							jsonObject.getString("status"), jsonObject.getString("message"))),
					"Resource Id " + jsonObject.getInt("id") + " doesn't exist");
		}
	}

	public void verifyGetOrgSolutionsResponseBody(Response response, List<OrgSolutions> finalList) throws IOException {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		for (int i = 0; i < finalList.size(); i++) {
			for (int soluDetail = 0; soluDetail < jsonArray.length(); soluDetail++) {
				JSONObject jsonObject = jsonArray.getJSONObject(soluDetail);
				int perJSONSolutionId = jsonObject.getInt("solutionId");
				int perFinalListSolutionId = Integer.parseInt(finalList.get(i).getSolutionId());
				if (perJSONSolutionId == perFinalListSolutionId) {
					Boolean perJSONSolutionActiveValue = jsonObject.getBoolean("active");
					if (perJSONSolutionActiveValue == true) {
						if (finalList.get(i).getStatus().equals("New")) {
							CCUtils.assertEquals(jsonObject.getInt("status"), 0);
							Log4jUtil.log("Solution ID : " + jsonObject.get("solutionId") + " with solution name : "
									+ jsonObject.get("name") + " is in active status with New status value"
									+ jsonObject.get("status"));
						} else if (finalList.get(i).getStatus().equals("Active Implementation")) {
							CCUtils.assertEquals(jsonObject.get("status"), 1);
							Log4jUtil.log("Solution ID : " + jsonObject.get("solutionId") + " with solution name : "
									+ jsonObject.get("name")
									+ " is in active status with Active Implementation status value"
									+ jsonObject.get("status"));
						} else if (finalList.get(i).getStatus().equals("Go-live")) {
							CCUtils.assertEquals(jsonObject.get("status"), 2);
							Log4jUtil.log("Solution ID : " + jsonObject.get("solutionId") + " with solution name : "
									+ jsonObject.get("name") + " is in active status with Go-live status value"
									+ jsonObject.get("status"));
						} else if (finalList.get(i).getStatus().equals("On Hold")) {
							CCUtils.assertEquals(jsonObject.get("status"), 3);
							Log4jUtil.log("Solution ID : " + jsonObject.get("solutionId") + " with solution name : "
									+ jsonObject.get("name") + " is in active status with On Hold status value"
									+ jsonObject.get("status"));
						} else if (finalList.get(i).getStatus().equals("Terminated")) {
							CCUtils.assertEquals(jsonObject.get("status"), 4);
							Log4jUtil.log("Solution ID : " + jsonObject.get("solutionId") + " with solution name : "
									+ jsonObject.get("name") + " is in active status with Terminated status value"
									+ jsonObject.get("status"));
						}
					}
				}
			}
		}
	}

	public void verifySaveDisplayInfoForPracticeApiResponseBody(Response response, String enDisplayName,
			String esDisplayName, String addressExtension, String phoneNumber, String startTime, String endTime,
			String enDirection, String esDirection) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		Map<String, String> dsName = jsonpath.getMap("displayName");
		CCUtils.assertEquals(dsName.get("en").toString(), enDisplayName);
		CCUtils.assertEquals(dsName.get("es").toString(), esDisplayName);
		CCUtils.assertEquals(jsonpath.get("addressExtension"), addressExtension);
		CCUtils.assertEquals(jsonpath.getString("phoneNumber"), phoneNumber);
		Map<String, String> officeTimings = jsonpath.getMap("officeHours");
		CCUtils.assertEquals(officeTimings.get("startTime").toString(), startTime);
		CCUtils.assertEquals(officeTimings.get("endTime").toString(), endTime);
		Map<String, String> diectionInfo = jsonpath.getMap("direction");
		CCUtils.assertEquals(diectionInfo.get("en"), enDirection);
		CCUtils.assertEquals(diectionInfo.get("es"), esDirection);
	}

	public void verifySaveDisplayInfoForSelectivePayloadApiResponseBody(Response response, String enDisplayName,
			String esDisplayName, String addressExtension, String phoneNumber, String enDirection, String esDirection)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		Map<String, String> dsName = jsonpath.getMap("displayName");
		CCUtils.assertEquals(dsName.get("en").toString(), enDisplayName);
		CCUtils.assertEquals(dsName.get("es").toString(), esDisplayName);
		CCUtils.assertEquals(jsonpath.get("addressExtension"), addressExtension);
		CCUtils.assertEquals(jsonpath.getString("phoneNumber"), phoneNumber);
		Map<String, String> diectionInfo = jsonpath.getMap("direction");
		CCUtils.assertEquals(diectionInfo.get("en"), enDirection);
		CCUtils.assertEquals(diectionInfo.get("es"), esDirection);
	}

	public void verifyOutputForPrivacySettingFromInput(Response response) {
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonPath.get("touType"), "NPP");
	}

	public void verifyOutputForTouFromInput(Response response, String touType, String touName) {
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonPath.get("touType"), touType);
		CCUtils.assertEquals(jsonPath.get("name"), touName);
	}

	public void getTouIdValidation(Response getTouApiResponse, String actualName, int practiceId) {
		List<String> touIds = getTouApiResponse.jsonPath().get("touId");
		for (int i = 0; i < touIds.size(); i++) {
			int touId = getTouApiResponse.jsonPath().get("touId[" + i + "]");
			String name = getTouApiResponse.jsonPath().get("name[" + i + "]");
			if (name.equalsIgnoreCase(actualName)) {
				Log4jUtil.log(touId + " added/fetched/updated successfully for " + practiceId);
			}
		}
	}

	public void getTouIdDetailValidation(Response getTouApiResponse, int practiceId, int touId, String actualName,
			String englishContent, String spanishContent) {
		String expectedName = getTouApiResponse.jsonPath().get("name");
		String expectedEnglishContent = getTouApiResponse.jsonPath().get("content.en");
		String expectedSpanishContent = getTouApiResponse.jsonPath().get("content.es");
		if (expectedName.equalsIgnoreCase(actualName) && englishContent.equalsIgnoreCase(expectedEnglishContent)
				&& spanishContent.equalsIgnoreCase(expectedSpanishContent)) {
			Log4jUtil.log(touId + " updated successfully for " + practiceId);
		} else {
			Log4jUtil.log(touId + " is not updated successfully for " + practiceId);
		}
	}

	public void verifyGetStatesDetails(Response getStateApiResponse) {
		List<String> stateCodes = Arrays.asList("AA", "AE", "AK", "AL", "AP", "AR", "AS", "AZ", "CA", "CO", "CT", "DC",
				"DE", "FL", "GA", "GU", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN",
				"MO", "MP", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "OU", "PA",
				"PR", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY");
		List<String> stateNames = Arrays.asList("Armed Forces - Americas", "Armed Forces - Other", "Alaska", "Alabama",
				"Armed Forces - Pacific", "Arkansas", "American Samoa", "Arizona", "California", "Colorado",
				"Connecticut", "District of Columbia", "Delaware", "Florida", "Georgia", "Guam", "Hawaii", "Iowa",
				"Idaho", "Illinois", "Indiana", "Kansas", "Kentucky", "Louisiana", "Massachusetts", "Maryland", "Maine",
				"Michigan", "Minnesota", "Missouri", "Northern Mariana Islands", "Mississippi", "Montana",
				"North Carolina", "North Dakota", "Nebraska", "New Hampshire", "New Jersey", "New Mexico", "Nevada",
				"New York", "Ohio", "Oklahoma", "Oregon", "Outside_US", "Pennsylvania", "Puerto Rico", "Rhode Island",
				"South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Virginia", "US Virgin Islands",
				"Vermont", "Washington", "Wisconsin", "West Virginia", "Wyoming");
		List<String> stateCodeCount = getStateApiResponse.jsonPath().get("stateCode");
		for (int i = 0; i < stateCodeCount.size(); i++) {
			String responseName = getStateApiResponse.jsonPath().get("name[" + i + "]");
			String expectedName = stateNames.get(i).toString();
			String responseStateCode = getStateApiResponse.jsonPath().get("stateCode[" + i + "]");
			String expectedStateCode = stateCodes.get(i).toString();
			if (responseStateCode.equalsIgnoreCase(expectedStateCode)) {
				CCUtils.assertEquals(responseName, expectedName);
			}
		}
	}

	public void verifyErrorMessageInResponseForInvalidMethod(Response response, String httpMethod) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Request method '" + httpMethod + "' not supported");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Method Not Allowed");
	}

	public void verifyOutputFromValidInputForGetPatientAccessSettings(Response response) {
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonPath.get("linkAccounts").toString(), "true");
		CCUtils.assertEquals(jsonPath.get("autoAgeout").toString(), "true");
	}

	public void verifyOutputFromValidInputForNotHavingPatientAccessSettings(Response response) {
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonPath.get("linkAccounts").toString(), "false");
		CCUtils.assertEquals(jsonPath.get("autoAgeout").toString(), "false");
	}

	public void verifyOutputFromValidInputForGetPatientGuardianAccess(Response response) {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
		}
		CCUtils.assertEquals(jsonArray.get(0).toString().contains("featureId"), true);
		CCUtils.assertEquals(jsonArray.get(0).toString().contains("featureName"), true);
		CCUtils.assertEquals(jsonArray.get(0).toString().contains("access"), true);
	}

	public void verifyOutputFromValidInputForGetPatientAgeout(Response response) {
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonPath.get("defaultAgeout").toString(), "18");
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

	public void verifyOutputFromValidInputForGetPatientAccessSettings(Response getPatientAccessSettingApiResponse,
			boolean linkAccounts, boolean autoAgeout) {
		boolean actualLinkAccounts = getPatientAccessSettingApiResponse.jsonPath().get("linkAccounts");
		boolean actualAutoAgeout = getPatientAccessSettingApiResponse.jsonPath().get("autoAgeout");
		CCUtils.assertEquals(actualLinkAccounts, linkAccounts);
		CCUtils.assertEquals(actualAutoAgeout, autoAgeout);
	}

	public void verifyOutputFromValidInputForGetPatientAgeOutAccess(Response getPatientAccessAgeOutApiResponse,
			int defaultAgeout, ArrayList<PatientAccessAgeoutRules> patientAccessAgeoutRulesList, int accessTillAge) {
		int actualDefaultAgeout = getPatientAccessAgeOutApiResponse.jsonPath().get("defaultAgeout");
		int actualAccessTillAge = getPatientAccessAgeOutApiResponse.jsonPath().get("accessTillAge");
		CCUtils.assertEquals(actualDefaultAgeout, defaultAgeout);
		CCUtils.assertEquals(actualAccessTillAge, accessTillAge);
		List<String> statesIds = getPatientAccessAgeOutApiResponse.jsonPath().get("ageoutRules.state");
		for (int i = 0; i < statesIds.size(); i++) {
			for (int j = 0; j < statesIds.size(); j++) {
				int actualAge = getPatientAccessAgeOutApiResponse.jsonPath().get("ageoutRules.age[" + j + "]");
				int expectedAge = patientAccessAgeoutRulesList.get(i).getAge();
				String actualState = getPatientAccessAgeOutApiResponse.jsonPath().get("ageoutRules.state[" + j + "]");
				String expectedState = patientAccessAgeoutRulesList.get(i).getState().toString();
				if (expectedState.equalsIgnoreCase(actualState)) {
					CCUtils.assertEquals(actualAge, expectedAge);
					CCUtils.assertEquals(actualState, expectedState);
					break;
				}
			}
		}
	}

	public void verifyOutputFromValidInputForGetPatientGuardianAccess(Response getPatientAccessGaurdianApiResponse,
			ArrayList<PatientAccessPermission> patientAccessPermissionList) {
		List<String> featureIds = getPatientAccessGaurdianApiResponse.jsonPath().get("featureId");
		for (int i = 0; i < featureIds.size(); i++) {
			for (int j = 0; j < featureIds.size(); j++) {
				int actualFeatureId = getPatientAccessGaurdianApiResponse.jsonPath().get("featureId[" + j + "]");
				int expectedFeatureId = patientAccessPermissionList.get(i).getFeatureId();
				String actualFeatureName = getPatientAccessGaurdianApiResponse.jsonPath().get("featureName[" + j + "]");
				String expectedFeatureName = patientAccessPermissionList.get(i).getFeatureName();
				String actualAccess = getPatientAccessGaurdianApiResponse.jsonPath().get("access[" + j + "]");
				String expectedAccess = patientAccessPermissionList.get(i).getAccess().toString();
				if (expectedFeatureId == actualFeatureId) {
					CCUtils.assertEquals(actualFeatureName, expectedFeatureName);
					CCUtils.assertEquals(actualAccess, expectedAccess);
					break;
				}
			}
		}
	}

	public void verifyPatientAccessGetBL(Response getPatientAccessDetailApiResponse, boolean linkAccounts,
			boolean autoAgeout, int defaultAgeout) {
		boolean actualLinkAccounts = getPatientAccessDetailApiResponse.jsonPath().get("settings.linkAccounts");
		boolean actualAutoAgeout = getPatientAccessDetailApiResponse.jsonPath().get("ageout.autoAgeout");
		int actualDefaultAgeout = getPatientAccessDetailApiResponse.jsonPath().get("ageout.defaultAgeout");
		int actualAccessTillAge = getPatientAccessDetailApiResponse.jsonPath().get("guardianAccess.accessTillAge");
		CCUtils.assertEquals(actualLinkAccounts, linkAccounts);
		CCUtils.assertEquals(actualAutoAgeout, autoAgeout);
		CCUtils.assertEquals(actualDefaultAgeout, defaultAgeout);
		CCUtils.assertEquals(actualAccessTillAge, defaultAgeout);
	}

	public void verifyErrorMessageForInvalidPracticeId(Response response, int practiceId, int providerId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Org staff not found by orgId=" + practiceId + " and " + "staffId=" + providerId);
	}

	public void verifyGetProvidersByClientIdResponseBody(Response response, List<Integer> providerId, String firstname,
			String lastname) throws IOException {
		List<Provider> actualRProvidersList = new ArrayList<Provider>();
		for (int i = 0; i < providerId.size(); i++) {
			actualRProvidersList.add(new Provider(providerId.get(i), firstname, lastname));
		}
		JSONArray responseBody = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		CCUtils.assertTrue(responseBody.length() > 0, "provider list is empty");
		CCUtils.assertTrue(actualRProvidersList.size() == responseBody.length(),
				"Size of actual privider list and expected providers list is not same");
		for (int i = 0; i < responseBody.length(); i++) {
			jsonObject = responseBody.getJSONObject(i);
			CCUtils.assertTrue(
					actualRProvidersList.contains(new Provider(jsonObject.getInt("providerId"),
							jsonObject.getString("firstName"), jsonObject.getString("lastName"))),
					"provider Id " + jsonObject.getInt("providerId") + " doesn't exist");
		}

	}

	public void validateGetPracticeProviderResponseBody(Response response, String providerName) {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(response.jsonPath().get(), "Response is null");
		CCUtils.assertEquals(jsonpath.get("displayName.en").toString().trim(), providerName.trim());
	}

	public void validateGetPracticeProviderResponseBodyWithEmailAndMobile(Response response, String providerName,
			String email, String mobile) {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(response.jsonPath().get(), "Response is null");
		CCUtils.assertEquals(jsonpath.get("displayName.en").toString().trim(), providerName.trim());
		CCUtils.assertEquals(jsonpath.get("mobile").toString().trim(), mobile.trim());
		CCUtils.assertEquals(jsonpath.get("email").toString().trim(), email.trim());
	}

	public void validateGetConnectedPracticeProvidersResponseBody(Response response, int providerId,
			String providerName) {
		JSONArray responseBody = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		CCUtils.assertTrue(responseBody.length() > 0, "provider list is empty");
		for (int i = 0; i < responseBody.length(); i++) {
			jsonObject = responseBody.getJSONObject(i);
			CCUtils.assertEquals(jsonObject.getInt("providerId"), providerId);
			CCUtils.assertEquals(jsonObject.get("firstName"), providerName);
		}
	}

	public void verifyAddOrRemoveResourcesApiResponseBody(Response response, int resourceId) throws IOException {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
		}
		CCUtils.assertEquals(jsonArray.get(0).toString().contains("resourceId"), true);
	}

	public void verifyGetPracticesAssociatedToProviderApiResponseBody(Response response, List<Integer> practiceIds)
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

	public void verifyAddOrRemoveMultipleResourcesApiResponseBody(Response response, ArrayList<Integer> resourceIds)
			throws IOException {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
		}
		CCUtils.assertEquals(jsonArray.get(0).toString().contains("resourceId"), true);
	}

	public void verifyAddOrRemoveAppointmentIdApiResponseBody(Response response, int appointmentTypeId)
			throws IOException {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
		}
		CCUtils.assertEquals(jsonArray.get(0).toString().contains("appointmentTypeId"), true);
	}

	public void verifyCreatedUserId(Response response, int id) {
		JsonPath jsonPath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonPath.get("id"), id);
	}

	public void verifyErrorMessageInResponseForNonExistingUserId(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "User Id doesn't exist");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyGetUserRolesByUserApiResponse(Response response, List<Integer> roleIds) throws IOException {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
		}
		CCUtils.assertEquals(jsonArray.get(0).toString().contains("roleId"), true);
	}

	public void verifyResponseBodyForUpdateResource(Response response, int resourceId, String name) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("resourceId"), resourceId);
		CCUtils.assertEquals(jsonpath.get("name").toString(), name);
	}

	public void verifyErrorResponseBodyForUpdateResource(Response response, int resourceId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Resource not found by id=" + resourceId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyGetProviderByClientIdApiResponseBody(Response response, List<Integer> providerIds,
			String firstName, String lastName) throws IOException {
		List<ProviderInfo> actualProviderInfoList = new ArrayList<ProviderInfo>();
		for (int i = 0; i < providerIds.size(); i++) {
			actualProviderInfoList.add(new ProviderInfo(providerIds.get(i), firstName, lastName));
		}
		JSONArray responseBody = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		CCUtils.assertTrue(responseBody.length() > 0, "Providers list is empty");
		CCUtils.assertTrue(actualProviderInfoList.size() == responseBody.length(),
				"Size of actual Providers list and expected Providers list is not same");
		for (int i = 0; i < responseBody.length(); i++) {
			jsonObject = responseBody.getJSONObject(i);
			CCUtils.assertTrue(
					actualProviderInfoList.contains(new ProviderInfo(jsonObject.getInt("providerId"),
							jsonObject.getString("firstName"), jsonObject.getString("lastName"))),
					"providerId" + jsonObject.getInt("providerId") + " doesn't exist");
		}
	}

	public void verifySearchResourcesToAssociateWithProviderApiResponseBody(Response response, int resourceId,
			String resourceName) throws IOException {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			CCUtils.assertEquals(jsonObject.get("resourceId"), resourceId);
			CCUtils.assertEquals(jsonObject.get("name"), resourceName);
		}
	}

	public void verifyResponseBodyOfGetResource(Response response, int resourceId) {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			CCUtils.assertEquals(jsonObject.get("resourceId"), resourceId);
			Log4jUtil.log("verifying the resource Id");
		}
	}

	public void verifyResponseBodyOfAddRemoveResource(Response response, int resourceId, String status) {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			CCUtils.assertEquals(jsonObject.get("resourceId"), resourceId);
			CCUtils.assertEquals(jsonObject.get("status"), status);
			Log4jUtil.log("verifying the resource Id and status");
		}
	}

	public void verifyAddRemoveResourceForNullResourceIdAndActive(Response response, String value) {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		if (jsonpath.get("message").toString().contains(value)) {
			CCUtils.assertTrue(true,
					"verifying error message " + value + " is present in " + jsonpath.get("message").toString());
		}
	}

	public void verifyResponseBodyForResourceAssociatedToPractices(Response response, ArrayList<Integer> practiceIds)
			throws IOException {
		for (int i = 0; i < practiceIds.size(); i++) {
			if (response.asString().contains(String.valueOf(practiceIds.get(i)))) {
				CCUtils.assertTrue(true, " : this value is present in response body" + practiceIds.get(i));
			}
		}
	}

	public void verifyResponseBodyForGetPracticeResource(Response response, String en, String es)
			throws NullPointerException, IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("en").toString(), en);
		CCUtils.assertEquals(jsonpath.get("es").toString(), es);
	}

	public void verifyResponseBodyForGetLocationResource(Response response) {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
		}
		CCUtils.assertEquals(jsonArray.get(0).toString().contains("locationId"), true);
		CCUtils.assertEquals(jsonArray.get(0).toString().contains("name"), true);
	}

	public void verifyGetPracticesAssociatedToResourceApiResponse(Response response, ArrayList<Object> orgId)
			throws IOException {
		for (int i = 0; i < orgId.size(); i++) {
			if (response.asString().contains(String.valueOf(orgId.get(i)))) {
				CCUtils.assertTrue(true, " : this value is present in response body" + orgId.get(i));
			}
		}
	}

	public void verifyResponseBodyOfProvidersLocation(Response response, int providerId, String providerFirstName,
			String providerLastName) {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			CCUtils.assertEquals(jsonObject.get("providerId"), providerId);
			CCUtils.assertEquals(jsonObject.get("firstName"), providerFirstName);
			CCUtils.assertEquals(jsonObject.get("lastName"), providerLastName);
			Log4jUtil.log("verifying provider Id and provider first name and last name");
		}
	}

	public void verifyResponseBodyForStaffAssociatedToPractices(Response response, ArrayList<Integer> practiceIds)
			throws IOException {
		for (int i = 0; i < practiceIds.size(); i++) {
			if (response.asString().contains(String.valueOf(practiceIds.get(i)))) {
				CCUtils.assertTrue(true, " : this value is present in response body" + practiceIds.get(i));
			}
		}
	}

	public void verifyResponseBodyForStaff(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		Log4jUtil.log("value of staff Id is :" + jsonpath.get("id"));
		Log4jUtil.log("value of firstName  is : " + jsonpath.get("firstName").toString());
		Log4jUtil.log("value of lastName  is : " + jsonpath.get("lastName").toString());
	}

	public void verifyErrorResponseBodyForExternalIdField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "ExternalId size maximum 50 characters");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyResponseBodyForStaffDetails(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		Log4jUtil.log("value of firstName  is : " + jsonpath.get("firstName").toString());
		Log4jUtil.log("value of lastName  is : " + jsonpath.get("lastName").toString());
	}

	public void verifyErrorResponseBodyInvalidStaffId(Response response, int staffId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Staff not found by id=" + staffId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyGetTemplateListDetails(Response getTemplateListApiResponse) {
		List<String> templateNames = Arrays.asList("Patient Portal Invitaion (Adults)",
				"Patient Portal Invitaion (Parent/Guardian)", "Patient Portal Invitaion (Trusted Representative)",
				"New Health Data Notification", "New Message Notification", "Electronic Statement Notification",
				"Online Payment Confirmation", "Password Reset Email");
		List<String> templateIdCount = getTemplateListApiResponse.jsonPath().get("templateId");
		for (int i = 0; i < templateNames.size(); i++) {
			String responseName = getTemplateListApiResponse.jsonPath().get("name[" + i + "]");
			String expectedName = templateNames.get(i).toString();
			if (templateNames.contains(responseName))
				Log4jUtil.log(responseName + " is available for templates");
			else
				Log4jUtil.log(responseName + " is not available for templates");
		}
	}

	public void verifyErrorResponseBodyForLocationField(Response response, String locationId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Location not found for locationId=" + locationId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyAppointmentTypesForResourcesApiResponseBody(Response response, int appointmentTypeId)
			throws IOException {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			CCUtils.assertEquals(jsonObject.get("appointmentTypeId"), appointmentTypeId);
		}
	}

	public void verifyErrorResponseBodyForInvalidInput(Response response, int appointmentTypeId, int practiceId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Appointment not found for appointmentTypeId=" + appointmentTypeId + " and practiceId=" + practiceId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyResponseBodyOfAppTypesSolutions(Response response, int solutionId, String status) {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			CCUtils.assertEquals(jsonObject.get("solutionId"), solutionId);
			CCUtils.assertEquals(jsonObject.get("status"), status);
		}
	}

	public void verifyNoOfSolutionsFetchedSolutionNameandId(Response response) {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		jsonArray = new JSONArray(response.getBody().asString());
		CCUtils.assertTrue(jsonArray.length() > 0, "Product list is empty");
		Log4jUtil.log("Number of Solution fetched is " + jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			Log4jUtil.log("print the solution name : " + jsonObject.getString("name") + " and solution Id : "
					+ jsonObject.get("solutionId"));
		}
	}

	public void verifyResponseBodyForExistingStaffd(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		Log4jUtil.log("value of staffId is : " + jsonpath.get("staffId").toString());
		Log4jUtil.log("value of firstName is : " + jsonpath.get("firstName"));
		Log4jUtil.log("value of lastName is : " + jsonpath.get("lastName"));
		Log4jUtil.log("value of imgUrl is : " + jsonpath.get("imgUrl"));
	}

	public void verifyErrorMessageInResponseBodyForNonExistingStaffId(Response response, int nonExistingStaffId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Staff not found by id=" + nonExistingStaffId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorResponseForImageUrlField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Image url can not be null");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyResponseBodyOfStatisticsData(Response response, int locationCount, int resourceCount,
			int appointmentTypeCount) {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			if (jsonObject.get("key").equals("LOCATION")) {
				CCUtils.assertEquals(jsonObject.get("count"), locationCount);
				Log4jUtil.log("Location count " + locationCount
						+ " matches with response body location statistics data count : " + jsonObject.get("count"));
			} else if (jsonObject.get("key").equals("RESOURCE")) {
				CCUtils.assertEquals(jsonObject.get("count"), resourceCount);
				Log4jUtil.log("resource count " + resourceCount
						+ " matches with response body resource statistics data count : " + jsonObject.get("count"));
			} else if (jsonObject.get("key").equals("APPOINTMENTTYPE")) {
				CCUtils.assertEquals(jsonObject.get("count"), appointmentTypeCount);
				Log4jUtil.log("appointment type count " + appointmentTypeCount
						+ " matches with response body appointment type statistics data count : "
						+ jsonObject.get("count"));
			}
		}
	}

	public void verifyResponseBodyForExistingStaffId_BL(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		Log4jUtil.log("value of firstName is : " + jsonpath.get("firstName"));
		Log4jUtil.log("value of lastName is : " + jsonpath.get("lastName"));
		Log4jUtil.log("value of imgUrl is : " + jsonpath.get("imageUrl"));
		Log4jUtil.log("value of gender is  :" + jsonpath.get("gender"));
		Log4jUtil.log("value of externalId is  :" + jsonpath.get("externalId"));
		Log4jUtil.log("value of status is  :" + jsonpath.get("status"));
	}

	public void verifyCreateAppTypeResponseBody(Response response, int id, String name) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("id"), id);
		CCUtils.assertEquals(jsonpath.get("name").toString(), name);
	}
	
	public void verifyErrorResponseBodyForStaffPracticeField(Response response, int practiceId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Client not found for practiceId=" + practiceId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyResponseBodyOfOrgSol(Response response, int solution) {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			if (jsonObject.get("solutionId").equals("solution")) {
				CCUtils.assertEquals(jsonObject.get("active"), "true");
				Log4jUtil.log("solution Id " + jsonObject.get("solutionId") + " is activated");
			} else
				Log4jUtil.log("solution Id " + jsonObject.get("solutionId") + " is not activated");
		}
	}
	
	public void verifyResponseBodyOfAppTypesResourses(Response response, int resourceId, String name,String type) {
		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			CCUtils.assertEquals(jsonObject.get("resourceId"), resourceId);
			CCUtils.assertEquals(jsonObject.get("name"), name);
			CCUtils.assertEquals(jsonObject.get("type"), type);
		}
	}

	public void verifyResponseBodyForExternalIdValidation(Response response, int id) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("id"), id);
	}

	public void verifyErrorResponseBodyForLocationSharedType(Response response, String externalId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Location not found by externalId=" + externalId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorResponseBodyForStaffSharedType(Response response, String externalId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Staff not found by externalId=" + externalId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorResponseBodyForResourceSharedType(Response response, String externalId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Resource not found by externalId=" + externalId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorResponseBodyForAppTypeSharedType(Response response, String externalId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "AppointmentType not found by externalId=" + externalId);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageForInvalidStaffUrl(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
		CCUtils.assertEquals(jsonpath.get("message"),"Error while download the file");
	}
}
