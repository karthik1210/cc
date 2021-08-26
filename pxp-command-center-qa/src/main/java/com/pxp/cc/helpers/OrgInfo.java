// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;

import io.restassured.response.Response;

public class OrgInfo extends BaseRest {

	public Response getOrgNodesDetails(String orgId) {

		String url = ApiPath.GET_ORG_DETAILS + orgId;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response createSystem(String active, String displayName, String name, String orgType, int parentId,
			String parentType, String sapNumber, String timezoneId) throws IOException {

		Map<String, Object> createNewSystemPayload = postCreateSystem(active, displayName, name, orgType, parentId,
				parentType, sapNumber, timezoneId);

		return given().spec(requestSpec).body(createNewSystemPayload).when().post(ApiPath.POST_SYSTEM_PRACTICE).then()
				.extract().response();
	}
	
	public Response createAgnosticPractice(String active, String displayName, String name, String orgType, int parentId,
			String parentType, String sapNumber, String timezoneId) throws IOException {

		Map<String, Object> createNewSystemPayload = postCreateSystem(active, displayName, name, orgType, parentId,
				parentType, sapNumber, timezoneId);

		return given().spec(requestSpec).body(createNewSystemPayload).when().post(ApiPath.POST_SYSTEM_PRACTICE).then()
				.extract().response();
	}
	
	public Response updateSystem(String active, String displayName, int orgId,
			 String sapNumber, String timezoneId) throws IOException {

		Map<String, Object> updateSystemPayload = putUpdateSystem(active, displayName, orgId,sapNumber, timezoneId);

		return given().spec(requestSpec).body(updateSystemPayload).when().put(ApiPath.PUT_ORG_CONTROLLER).then()
				.extract().response();
	}
	
	public Response getOrgId(int orgId) throws IOException {
		String url = ApiPath.GET_ORGID + orgId;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response createEnterprise(String active, String displayName, String name, String orgType, int orgId,
			String parentType, String sapNumber, String timezoneId) throws IOException {

		Map<String, Object> createNewEnterprisePayload = postCreateEnterprise(active, displayName, name, orgType,
				orgId, parentType, sapNumber, timezoneId);

		return given().spec(requestSpec).body(createNewEnterprisePayload).when().post(ApiPath.POST_ENTERPRISE_PRACTICE)
				.then().extract().response();
	}
	
	public Response updateEnterprise(String active, String displayName, int orgId,
			 String sapNumber, String timezoneId) throws IOException {

		Map<String, Object> updateEnterprisePayload = putUpdateEnterprise(active, displayName, orgId,sapNumber, timezoneId);

		return given().spec(requestSpec).body(updateEnterprisePayload).when().put(ApiPath.PUT_ORG_CONTROLLER).then()
				.extract().response();
	}

	public Response createPractice(String active, String displayName, String name, String orgType, int parentId,
			String parentType, String sapNumber, String timezoneId) throws IOException {

		Map<String, Object> createNewPracticePayload = postCreatePractice(active, displayName, name, orgType, parentId,
				parentType, sapNumber, timezoneId);

		return given().spec(requestSpec).body(createNewPracticePayload).when().post(ApiPath.POST_ENTERPRISE_PRACTICE)
				.then().extract().response();
	}
	
	public Response updatePractice(String active, String displayName, int orgId,
			 String sapNumber, String timezoneId) throws IOException {

		Map<String, Object> updatePracticePayload = putUpdatePractice(active, displayName, orgId,sapNumber, timezoneId);

		return given().spec(requestSpec).body(updatePracticePayload).when().put(ApiPath.PUT_ORG_CONTROLLER).then()
				.extract().response();
	}

	public static Map<String, Object> postCreateSystem(String active, String displayName, String name, String orgType,
			int parentId, String parentType, String sapNumber, String timezoneId) {
		Map<String, Object> systemMap = new HashMap<String, Object>();
		systemMap.put("active", active);
		systemMap.put("displayName", displayName);
		systemMap.put("name", name);
		systemMap.put("orgType", orgType);
		systemMap.put("parentId", parentId);
		systemMap.put("parentType", parentType);
		systemMap.put("sapNumber", sapNumber);
		systemMap.put("timezoneId", timezoneId);
		return systemMap;
	}
	
	public static Map<String, Object> putUpdateSystem(String active, String displayName,
			int orgId, String sapNumber, String timezoneId) {
		Map<String, Object> systemMap = new HashMap<String, Object>();
		systemMap.put("active", active);
		systemMap.put("displayName", displayName);
		systemMap.put("id", orgId);
		systemMap.put("sapNumber", sapNumber);
		systemMap.put("timezoneId", timezoneId);
		return systemMap;
	}

	public static Map<String, Object> postCreateEnterprise(String active, String displayName, String name,
			String orgType, int parentId, String parentType, String sapNumber, String timezoneId) {
		Map<String, Object> enterpriseMap = new HashMap<String, Object>();
		enterpriseMap.put("active", active);
		enterpriseMap.put("displayName", displayName);
		enterpriseMap.put("name", name);
		enterpriseMap.put("orgType", orgType);
		enterpriseMap.put("parentId", parentId);
		enterpriseMap.put("parentType", parentType);
		enterpriseMap.put("sapNumber", sapNumber);
		enterpriseMap.put("timezoneId", timezoneId);
		return enterpriseMap;
	}
	
	public static Map<String, Object> putUpdateEnterprise(String active, String displayName,
			int orgId, String sapNumber, String timezoneId) {
		Map<String, Object> systemMap = new HashMap<String, Object>();
		systemMap.put("active", active);
		systemMap.put("displayName", displayName);
		systemMap.put("id", orgId);
		systemMap.put("sapNumber", sapNumber);
		systemMap.put("timezoneId", timezoneId);
		return systemMap;
	}

	public static Map<String, Object> postCreatePractice(String active, String displayName, String name, String orgType,
			int parentId, String parentType, String sapNumber, String timezoneId) {
		Map<String, Object> practiceMap = new HashMap<String, Object>();
		practiceMap.put("active", active);
		practiceMap.put("displayName", displayName);
		practiceMap.put("name", name);
		practiceMap.put("orgType", orgType);
		practiceMap.put("parentId", parentId);
		practiceMap.put("parentType", parentType);
		practiceMap.put("sapNumber", sapNumber);
		practiceMap.put("timezoneId", timezoneId);
		return practiceMap;
	}
	
	public static Map<String, Object> putUpdatePractice(String active, String displayName,
			int orgId, String sapNumber, String timezoneId) {
		Map<String, Object> systemMap = new HashMap<String, Object>();
		systemMap.put("active", active);
		systemMap.put("displayName", displayName);
		systemMap.put("id", orgId);
		systemMap.put("sapNumber", sapNumber);
		systemMap.put("timezoneId", timezoneId);
		return systemMap;
	}

}
