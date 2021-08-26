// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;

import io.restassured.response.Response;

public class ClientInfo extends BaseRest {

	public Response getClientDetails(String clientId) {
		String url = ApiPath.GET_CLIENT + clientId;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response createNewClientUpdateClient(String active, String clientId, String clientName, String sapNumber,
			String testClient, String type, String timezoneId) throws IOException {
		Map<String, Object> createNewClientPayload = postCreateClientUpdateClient(active, clientId, clientName,
				sapNumber, testClient, type, timezoneId);
		return given().spec(requestSpec).body(createNewClientPayload).when().post(ApiPath.POST_CLIENT).then().extract()
				.response();
	}

	public Response getSapNumber(String clientSapNumber) throws IOException {
		String url = ApiPath.GET_CLIENTSAPNUMBER + clientSapNumber;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response patchClientStatus(String clientId, String clientStatus) throws IOException {
		String url = ApiPath.GET_CLIENT + clientId + ApiPath.GET_CLIENT_STATUS + clientStatus;
		return given().spec(requestSpec).when().patch(url).then().extract().response();
	}

	public Response getClientTypes() throws IOException {
		String url = ApiPath.GET_CLIENT_TYPES;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	private Map<String, Object> postCreateClientUpdateClient(String active, String clientId, String clientName,
			String clientSapNumber, String testClient, String type, String timezoneId) {
		Map<String, Object> createClientMap = new HashMap<String, Object>();
		createClientMap.put("active", active);
		createClientMap.put("clientId", clientId);
		createClientMap.put("name", clientName);
		createClientMap.put("sapNumber", clientSapNumber);
		createClientMap.put("testClient", testClient);
		createClientMap.put("type", type);
		createClientMap.put("timezoneId", timezoneId);
		return createClientMap;
	}

}
