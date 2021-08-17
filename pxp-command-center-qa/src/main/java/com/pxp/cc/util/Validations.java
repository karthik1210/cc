// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Validations {
	JsonPath jsonpath;

	public void verifyCreateClientResponseBody(Response response, String clientNameValue) throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertNotNull(jsonpath.get("id"), "Client id is not in the response");
		CCUtils.assertNotNull(jsonpath.get("name"), "Client name is not in the response");
		CCUtils.assertEquals(response.jsonPath().get("name").toString(), clientNameValue);

	}

	public void verifySearchClientResponseBody(Response response, String clientId, String clientName, String type,
			String sapNumber, String active, String testClient, String timezoneId, String timezoneDisplayName,
			Integer practiceCountValue, Boolean isPurchased, List<Integer> purchasedSolutionList) throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("clientId").toString(), clientId);
		CCUtils.assertEquals(jsonpath.get("name").toString(), clientName);
		CCUtils.assertEquals(jsonpath.get("type").toString(), type);
		CCUtils.assertEquals(jsonpath.get("sapNumber").toString(), sapNumber);
		CCUtils.assertEquals(jsonpath.get("active").toString(), active);
		CCUtils.assertEquals(jsonpath.get("testClient").toString(), testClient);
		Map<String, String> timezone = response.jsonPath().getMap("timezone");
		CCUtils.assertEquals(timezone.get("id").toString(), timezoneId);
		CCUtils.assertEquals(timezone.get("displayName").toString(), timezoneDisplayName);
		int practiceCount = jsonpath.get("practiceCount");
		CCUtils.assertEquals(practiceCount, practiceCountValue);
		List<String> solutionsList = new ArrayList<String>();
		solutionsList = response.jsonPath().getList("solutions");
		if (practiceCount == 0) {
			CCUtils.assertTrue(solutionsList.isEmpty(), "Solutions list is not empty");

		} else {
			if (isPurchased.booleanValue()) {
				if (!purchasedSolutionList.isEmpty()) {
					CCUtils.assertTrue(!solutionsList.isEmpty(), "Solutions list is empty");
				}

			} else {
				CCUtils.assertTrue(solutionsList.isEmpty(), "Solutions list is not empty");
			}

		}

	}

	public void verifyNullErrorMessageResponseBody(Response response) throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
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
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Client name already use");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorMessageInResponseForSapNumberField(Response response) throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Client sap number already use");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorMessageInResponseForClientTypeField(Response response, String clientType)
			throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Client type=" + clientType + " doesn't exist");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorMessageInResponseForClientTimezoneIdField(Response response, String clientTimezoneId)
			throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Timezone id=" + clientTimezoneId + " doesn't exist");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorMessageInResponseForClientSapNumberField(Response response) throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Sap number size maximum 50 character");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyMaximumCharacterErrorMessageInResponseForClientNameField(Response response) throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Name size maximum 100 character");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyCreateSystemResponseBody(Response response, String systemName) throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertNotNull(jsonpath.get("id"), "System id was not in the response");
		CCUtils.assertNotNull(jsonpath.get("name"), "System name was not in the response");
		CCUtils.assertEquals(response.jsonPath().get("name").toString(), systemName);

	}

	public void verifySearchSystemResponseBody(Response response, String orgId, String systemName, String orgType,
			String clientType, String displayName, String sapNumber, String systemeActive, String systemTestClient,
			String timezoneId, String orgTimezoneDisplayNameApi, Boolean isPurchased) throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("orgId").toString(), orgId);
		CCUtils.assertEquals(jsonpath.get("name").toString(), systemName);
		CCUtils.assertEquals(jsonpath.get("orgType").toString(), orgType);
		CCUtils.assertEquals(jsonpath.get("clientType").toString(), clientType);
		CCUtils.assertEquals(jsonpath.get("displayName").toString(), displayName);
		CCUtils.assertEquals(jsonpath.get("sapNumber").toString(), sapNumber);
		CCUtils.assertEquals(jsonpath.get("active").toString(), systemeActive);
		CCUtils.assertEquals(jsonpath.get("testClient").toString(), systemTestClient);
		Map<String, String> timezone = response.jsonPath().getMap("timezone");
		CCUtils.assertEquals(timezone.get("id").toString(), timezoneId);
		CCUtils.assertEquals(timezone.get("displayName").toString(), orgTimezoneDisplayNameApi);
		CCUtils.assertTrue(jsonpath.get("practiceCount") == null, "practice count is not null");
	}

	public void verifyOrgNullErrorMessageResponseBody(Response response) throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
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
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertNotNull(jsonpath.get("id"), "Enterprise id was not in the response");
		CCUtils.assertNotNull(jsonpath.get("name"), "Enterprise name was not in the response");
		CCUtils.assertEquals(response.jsonPath().get("name").toString(), enterprise);

	}

	public void verifySearchEnterpriseResponseBody(Response response, String enterpriseId, String enterpriseName,
			String orgType, String clientType, String displayName, String sapNumber, String active, String testClient,
			String timezoneId, String enterpriseTimezoneDisplayNameApi, Integer practiceCountValue, Boolean isPurchased,
			List<Integer> purchasedSolutionList) throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("orgId").toString(), enterpriseId);
		CCUtils.assertEquals(jsonpath.get("name").toString(), enterpriseName);
		CCUtils.assertEquals(jsonpath.get("orgType").toString(), orgType);
		CCUtils.assertEquals(jsonpath.get("clientType").toString(), clientType);
		CCUtils.assertEquals(jsonpath.get("displayName").toString(), displayName);
		CCUtils.assertEquals(jsonpath.get("sapNumber").toString(), sapNumber);
		CCUtils.assertEquals(jsonpath.get("active").toString(), active);
		CCUtils.assertEquals(jsonpath.get("testClient").toString(), testClient);
		Map<String, String> timezone = response.jsonPath().getMap("timezone");
		CCUtils.assertEquals(timezone.get("id").toString(), timezoneId);
		CCUtils.assertEquals(timezone.get("displayName").toString(), enterpriseTimezoneDisplayNameApi);
		int practiceCount = jsonpath.get("practiceCount");
		CCUtils.assertEquals(practiceCount, practiceCountValue);
		List<String> solutionsList = new ArrayList<String>();
		solutionsList = response.jsonPath().getList("solutions");
		if (practiceCount == 0) {
			CCUtils.assertTrue(solutionsList.isEmpty(), "Solutions list is empty");
		} else {
			if (isPurchased.booleanValue()) {
				if (!purchasedSolutionList.isEmpty()) {
					CCUtils.assertTrue(!solutionsList.isEmpty(), "Solutions list is not empty");
				}

			} else {
				CCUtils.assertTrue(solutionsList.isEmpty(), "Solutions list is empty");
			}

		}

	}

	public void verifyCreatePracticeResponseBody(Response response, String practice) throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertNotNull(jsonpath.get("id"), "Practice id was not in the response");
		CCUtils.assertNotNull(jsonpath.get("name"), "Practice name was not in the response");
		CCUtils.assertEquals(response.jsonPath().get("name").toString(), practice);

	}

	public void verifySearchPracticeResponseBody(Response response, String practiceId, String practiceName,
			String orgType, String clientType, String displayName, String sapNumber, String active, String testClient,
			String timezoneId, String orgTimezoneDisplayNameApi, Boolean isPurchased) throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertNotNull(jsonpath, "Response is null");
		CCUtils.assertEquals(jsonpath.get("orgId").toString(), practiceId);
		CCUtils.assertEquals(jsonpath.get("name").toString(), practiceName);
		CCUtils.assertEquals(jsonpath.get("orgType").toString(), orgType);
		CCUtils.assertEquals(jsonpath.get("clientType").toString(), clientType);
		CCUtils.assertEquals(jsonpath.get("displayName").toString(), displayName);
		CCUtils.assertEquals(jsonpath.get("sapNumber").toString(), sapNumber);
		CCUtils.assertEquals(jsonpath.get("active").toString(), active);
		CCUtils.assertEquals(jsonpath.get("testClient").toString(), testClient);
		Map<String, String> timezone = response.jsonPath().getMap("timezone");
		CCUtils.assertEquals(timezone.get("id").toString(), timezoneId);
		CCUtils.assertEquals(timezone.get("displayName").toString(), orgTimezoneDisplayNameApi);
		CCUtils.assertTrue(jsonpath.get("practiceCount") == null, "practice count is not null");
	}

	public void verifyErrorMessageInResponseForSystemOrgTypeField(Response response, String systemOrgType)
			throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid org type=" + systemOrgType);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForEnterpriseOrgTypeField(Response response, String enterpriseOrgType)
			throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid org type=" + enterpriseOrgType);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForPracticeOrgTypeField(Response response, String practiceOrgType)
			throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid org type=" + practiceOrgType);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForSystemParentIdField(Response response, int orgSystemParentId)
			throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Parentid=" + orgSystemParentId + " doesn't exist");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForEnterpriseParentIdField(Response response, int orgEnterpriseParentId)
			throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Parentid=" + orgEnterpriseParentId + " doesn't exist");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForPracticeParentIdField(Response response, int orgPracticeParentId)
			throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Parentid=" + orgPracticeParentId + " doesn't exist");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");
	}

	public void verifyErrorMessageInResponseForParentTypeFieldSystem(Response response, String orgSystemParentType)
			throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid parent type=" + orgSystemParentType);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorMessageInResponseForParentTypeFieldEnterprise(Response response,
			String orgEnterpriseParentType) throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid parent type=" + orgEnterpriseParentType);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorMessageInResponseForParentTypeFieldPractice(Response response, String orgPracticeParentType)
			throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Invalid parent type=" + orgPracticeParentType);
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorMessageInResponseForSystemTimezoneIdField(Response response, String systemTimezoneId)
			throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(), "Timezone id=" + systemTimezoneId + " doesn't exist");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorMessageInResponseForEnterpriseTimezoneIdField(Response response, String enterpriseTimezoneId)
			throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Timezone id=" + enterpriseTimezoneId + " doesn't exist");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyErrorMessageInResponseForPracticeTimezoneIdField(Response response, String practiceTimezoneId)
			throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonpath.get("message").toString(),
				"Timezone id=" + practiceTimezoneId + " doesn't exist");
		CCUtils.assertEquals(jsonpath.get("error").toString(), "Bad Request");

	}

	public void verifyMaximumCharacterErrorMessageInResponseForSystemField(Response response) throws IOException {
		jsonpath = new JsonPath(response.getBody().asString());
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
		jsonpath = new JsonPath(response.getBody().asString());
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
		jsonpath = new JsonPath(response.getBody().asString());
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

}
