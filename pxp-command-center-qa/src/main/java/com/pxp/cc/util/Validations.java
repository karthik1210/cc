// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.pxp.cc.pojos.ClientTypes;
import com.pxp.cc.pojos.Solutions;
import com.pxp.cc.pojos.Timezone;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Validations {

	public void verifyCreateAndUpdateClientResponseBody(Response response, String clientNameValue) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertNotNull(jsonpath.get("id"), "Client id is not in the response");
		CCUtils.assertNotNull(jsonpath.get("name"), "Client name is not in the response");
		CCUtils.assertEquals(response.jsonPath().get("name").toString(), clientNameValue);

	}

	public void verifySearchClientResponseBody(Response response, String clientId, String clientName, String type,
			String sapNumber, String active, String testClient, String timezoneId, String timezoneDisplayName,
			Integer practiceCountValue, Boolean isPurchased, List<Integer> purchasedSolutionList) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("clientId").toString(), clientId);
		CCUtils.assertEquals(jsonpath.get("name").toString(), clientName);
		CCUtils.assertEquals(jsonpath.get("type").toString(), type);
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
		int practiceCount = jsonpath.get("practiceCount");
		CCUtils.assertEquals(practiceCount, practiceCountValue);
		List<Integer> solutionsList = new ArrayList<Integer>();
		solutionsList = response.jsonPath().getList("solutions");
		if (practiceCount == 0) {
			CCUtils.assertTrue(solutionsList.isEmpty(), "Solutions list is not empty");
		} else {
			if (isPurchased.booleanValue()) {
				if (!purchasedSolutionList.isEmpty()) {
					CCUtils.assertTrue(!solutionsList.isEmpty(), "Solutions list is empty");
					List<Solutions> actualSolutionList = new ArrayList<Solutions>();
					Map<Integer, Integer> productMap = new HashMap<Integer, Integer>();
					for (Integer productId : purchasedSolutionList) {
						if (productMap.containsKey(productId)) {
							productMap.put(productId, productMap.get(productId) + 1);
						} else {
							productMap.put(productId, 1);
						}
					}

					for (Map.Entry<Integer, Integer> entry : productMap.entrySet()) {
						actualSolutionList.add(new Solutions(entry.getValue(), entry.getKey()));
					}
					JSONArray jsonArray = new JSONArray(response.jsonPath().getList("solutions"));
					JSONObject jsonObject;
					if (actualSolutionList.size() == jsonArray.length()) {
						for (int i = 0; i < jsonArray.length(); i++) {
							jsonObject = jsonArray.getJSONObject(i);
							CCUtils.assertTrue(actualSolutionList.contains(
									new Solutions(jsonObject.getInt("activeCount"), jsonObject.getInt("productId"))),
									"Purchased Solution is not present");
						}
					} else if (jsonArray.length() == 0) {
						Log4jUtil.log("solution list is empty");
					} else {
						Log4jUtil.log("Size of actual solution list and expected solution list is not same");
					}
				}
			} else {
				CCUtils.assertTrue(solutionsList.isEmpty(), "Solutions list is not empty");
			}
		}
	}

	public void verifyNullErrorMessageResponseBody(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Active can not be null, Name can not be blank, Sap number can not be blank, Type can not be blank";
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

	public void verifyErrorMessageInResponseForClientNameField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Client name already use");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorMessageInResponseForSapNumberField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Client sap number already use");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorMessageInResponseForClientTypeField(Response response, String clientType)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Client type=" + clientType + " doesn't exist");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorMessageInResponseForClientTimezoneIdField(Response response, String clientTimezoneId)
			throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Timezone id=" + clientTimezoneId + " doesn't exist");
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
		CCUtils.assertNotNull(jsonpath.get("id"), "System id was not in the response");
		CCUtils.assertNotNull(jsonpath.get("name"), "System name was not in the response");
		CCUtils.assertEquals(response.jsonPath().get("name").toString(), systemName);

	}

	public void verifyGetOrgIdResponseBody(Response response, int orgId) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertNotNull(jsonpath.get("orgId"), "System id was not in the response");
		CCUtils.assertEquals(response.jsonPath().get("orgId"), orgId);

	}

	public void verifyErrorMessageInResponseForClientIdInOrgIdField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Client not found for org id=4014");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorMessageInResponseForInvalidInOrgId(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Client not found for org id=401455678");
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
		CCUtils.assertNotNull(jsonpath.get("id"), "Enterprise id was not in the response");
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
		CCUtils.assertNotNull(jsonpath.get("id"), "Practice id was not in the response");
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

	public void verifyMaximumCharacterErrorMessageInResponseForSystemField(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Active can not be null, Display name size maximum 100 character, Name size maximum 100 character, Sap number size maximum 50 character";
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

	public void verifyMaximumCharacterAndFreeTextFieldErrorResponsForEnterprise(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Name size maximum 100 character, Sap number size maximum 50 character, Display name size maximum 100 character";
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

	public void verifyMaximumCharacterAndFreeTextFieldErrorResponsForPractice(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		String actual = "Name size maximum 100 character, Sap number size maximum 50 character, Display name size maximum 100 character";
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

	public void verifyInactiveClientErrorResponsForPractice(Response response) throws IOException {
		JsonPath jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Practice cannot be Activated as Client is Inactive");
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
		actualClientTypeList.add(new ClientTypes(1, "Direct Client", true));
		actualClientTypeList.add(new ClientTypes(101, "Dedicated IT", true));
		actualClientTypeList.add(new ClientTypes(102, "GBS", true));
		actualClientTypeList.add(new ClientTypes(103, "Health Prime International", true));
		actualClientTypeList.add(new ClientTypes(104, "Itelagen", true));
		actualClientTypeList.add(new ClientTypes(105, "KM Strategic", true));
		actualClientTypeList.add(new ClientTypes(106, "MdCentric", true));
		actualClientTypeList.add(new ClientTypes(107, "Medicalistics", true));
		actualClientTypeList.add(new ClientTypes(108, "MTS/MMIC", true));
		actualClientTypeList.add(new ClientTypes(109, "Newport Medical Group", true));
		actualClientTypeList.add(new ClientTypes(110, "New Stork Healthdata Management", true));
		actualClientTypeList.add(new ClientTypes(111, "OSIS", true));
		actualClientTypeList.add(new ClientTypes(112, "SharedMed", true));
		actualClientTypeList.add(new ClientTypes(113, "TSI", true));
		actualClientTypeList.add(new ClientTypes(114, "Virmedice", true));

		JSONArray jsonArray = new JSONArray(response.getBody().asString());
		JSONObject jsonObject;
		if (actualClientTypeList.size() == jsonArray.length()) {
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				CCUtils.assertTrue(
						actualClientTypeList.contains(new ClientTypes(jsonObject.getInt("id"),
								jsonObject.get("name").toString(), jsonObject.getBoolean("active"))),
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
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Clientid=" + clientId + " doesn't exist");
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
				"Client selected is inactive. Activate client and try again.");
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

	public void verifyGetTimezonApiResponseBody(Response response) throws IOException {
		List<Timezone> actualTimezoneList = new ArrayList<Timezone>();
		final List<String> expectedTimezoneDisplayName = Arrays.asList("Eastern Standard Time",
				"Atlantic Standard Time", "Central Standard Time", "Mountain Standard Time", "Pacific Standard Time",
				"Alaska Standard Time", "Hawaii Standard Time", "Hong Kong Standard Time", "Indiana Standard Time",
				"Arizona Standard Time", "Midway Island, Samoa", "International Date Line West", "Nuku'alofa",
				"Tijuana, Baja California", "Chihuahua, La Paz, Mazatlan", "Central America",
				"Guadalajara, Mexico City, Monterrey", "Saskatchewan", "Bagota, Lima, Quito, Rio Branco", "Caracas",
				"La Paz", "Manaus", "Santiago", "Newfoundland", "Buenos Aires, Georgetown", "Greenland", "Montevideo",
				"Azores", "Cape Verde Is.", "Casablanca,Monrovia,Reykjavik", "Greenwich Mean Time",
				"Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna", "Belgrade, Bratislava, Budapest, Ljubljana, Prague",
				"Brussels, Copenhagen, Madrid, Paris", "Sarajevo, Skopje, Warsaw, Zagreb", "West Central Africa",
				"Amman", "Athens, Bucharest, Istanbul", "Beirut", "Cairo", "Harare, Pretoria",
				"Helsinki, Kyiv, Riga, Sofia, Tallinn, Vilnius", "Jerusalem", "Minsk", "Mid-Atlantic", "Baghdad",
				"Kuwait, Riyadh", "Moscow, St. Petersburg, Volgograd", "Nairobi", "Tehran", "Abu Dhabi, Muscat", "Baku",
				"Yerevan", "Kabul", "Ekaterinburg", "Islamabad, Karachi, Tashkent",
				"Chennai, Kolkata, Mumbai, New Delhi", "Kathmandu", "Almaty, Novosibirsk", "Astana, Dhaka",
				"Yangon (Rangoon)", "Bangkok, Hanoi, Jakarta", "Krasnoyarsk", "Irkutsk, Ulaan Bataar",
				"Kuala Lumpur, Singapore", "Perth", "Taipei", "Osaka, Sapporo, Tokyo", "Seoul", "Yakutsk", "Adelaide",
				"Darwin", "Brisbane", "Canberra, Melbourne, Sydney", "Guam, Port Moresby", "Hobart", "Vladivostok",
				"Magadan, Solomon Is., New Caledonia", "Auckland, Wellington");

		final List<String> expectedTimezoneId = Arrays.asList("US/Eastern", "Canada/Atlantic", "US/Central",
				"US/Mountain", "US/Pacific", "US/Alaska", "US/Hawaii", "Asia/Hong_Kong", "US/East-Indiana",
				"US/Arizona", "US/Samoa", "Etc/GMT-12", "Pacific/Tongatapu", "America/Tijuana", "America/Mazatlan",
				"Mexico/General", "Mexico/General", "Canada/Saskatchewan", "America/Lima", "America/Caracas",
				"America/La_Paz", "America/Manaus", "America/Santiago", "Canada/Newfoundland", "America/Buenos_Aires",
				"America/Godthab", "America/Montevideo", "Atlantic/Azores", "Atlantic/Cape_Verde", "Africa/Casablanca",
				"Europe/Dublin", "Europe/Amsterdam", "Europe/Belgrade", "Europe/Brussels", "Europe/Sarajevo",
				"Africa/Lagos", "Asia/Amman", "Europe/Athens", "Asia/Beirut", "Africa/Cairo", "Africa/Harare",
				"Europe/Helsinki", "Asia/Jerusalem", "Europe/Minsk", "Atlantic/South_Georgia", "Asia/Baghdad",
				"Asia/Kuwait", "Europe/Moscow", "Africa/Nairobi", "Asia/Tehran", "Asia/Muscat", "Asia/Baku",
				"Asia/Yerevan", "Asia/Kabul", "Asia/Yekaterinburg", "Asia/Karachi", "Asia/Calcutta", "Asia/Katmandu",
				"Asia/Almaty", "Asia/Dhaka", "Asia/Rangoon", "Asia/Bangkok", "Asia/Krasnoyarsk", "Asia/Irkutsk",
				"Asia/Singapore", "Australia/Perth", "Asia/Taipei", "Asia/Tokyo", "Asia/Seoul", "Asia/Yakutsk",
				"Australia/Adelaide", "Australia/Darwin", "Australia/Brisbane", "Australia/Sydney", "Pacific/Guam",
				"Australia/Hobart", "Asia/Vladivostok", "Asia/Magadan", "Pacific/Auckland");

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
}
