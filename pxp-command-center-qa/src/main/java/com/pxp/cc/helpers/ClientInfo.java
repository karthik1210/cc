// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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
		String url = ApiPath.GET_CLIENT_BL + clientId;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response createNewClientUpdateClient(String active, String clientId, String clientName, String sapNumber,
			String testClient, String type, String timezoneId) throws IOException {
		Map<String, Object> createNewClientPayload = postCreateClientUpdateClient(active, clientId, clientName,
				sapNumber, testClient, type, timezoneId);
		return given().spec(requestSpec).body(createNewClientPayload).when().post(ApiPath.POST_CLIENT_BL).then()
				.extract().response();
	}

	public Response getSapNumber(String clientSapNumber) throws IOException {
		String url = ApiPath.GET_CLIENTSAPNUMBER_BL + "{sapNumber}";
		return given().spec(requestSpec).pathParam("sapNumber", clientSapNumber).when().get(url).then().extract()
				.response();
	}

	public Response patchClientStatus(String clientId, String clientStatus) throws IOException {
		String url = ApiPath.GET_CLIENT + clientId + ApiPath.GET_CLIENT_STATUS + clientStatus;
		return given().spec(requestSpec).when().patch(url).then().extract().response();
	}

	public Response getClientTypes() throws IOException {
		String url = ApiPath.GET_CLIENT_TYPES;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getClientOrgId(int clientId) throws IOException {
		String url = ApiPath.GET_CLIENT_ORG_ID + clientId;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getClientName(String clientName) throws IOException {
		String url = ApiPath.GET_CLIENTNAME_BL + "{name}";
		return given().spec(requestSpec).pathParam("name", clientName).when().get(url).then().extract().response();
	}

	public Response getOrgHierarchyWithClientNode(String clientId, Boolean forClientValue) throws IOException {
		String url = ApiPath.GET_ORG_HIERARCHY + clientId;
		return given().spec(requestSpec).queryParam("excludeClientData", forClientValue).when().get(url).then()
				.extract().response();
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

	public Response getParentDropdown(String clientId, String orgType) throws IOException {
		String url = ApiPath.GET_CLIENT + clientId + "/types/" + orgType + "/parents";
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getAutoSuggestionClient(String serachClient) {
		String url = ApiPath.GET_AUTOSUGGESTION_CLIENT;
		return given().spec(requestSpec).queryParam("searchClient", serachClient).when().get(url).then().extract()
				.response();
	}

	public Response createNewClientUpdateClientUsingInvalidUrl(String active, String clientId, String clientName,
			String sapNumber, String testClient, String type, String timezoneId) throws IOException {
		Map<String, Object> createNewClientPayload = postCreateClientUpdateClient(active, clientId, clientName,
				sapNumber, testClient, type, timezoneId);
		String invalidUrl = ApiPath.POST_CLIENT_BL + "abc";
		return given().spec(requestSpec).body(createNewClientPayload).when().post(invalidUrl).then().extract()
				.response();
	}

	public Response getClientNameUsingInvalidUrl(String clientName) throws IOException {
		String url = ApiPath.INVALID_PATH_GET_CLIENTNAME_BL + clientName;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getClientTypeUsingInvalidUrl() throws IOException {
		String url = ApiPath.INVALID_PATH_GET_CLIENTTYPE_BL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getClientSapNumbrUsingInvalidUrl(String clietSapNumber) throws IOException {
		String url = ApiPath.INVALID_PATH_GET_CLIENTSAPNUMBER_BL + clietSapNumber;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}
	
	public Response getClientName_DataLayer(String clientName) throws IOException {
		String url = ApiPath.GET_CLIENTNAME_DL + "{name}";
		return given().spec(requestSpec).pathParam("name", clientName).when().get(url).then().extract().response();
	}
	
	public Response getClientNameUsingInvalidUrl_DataLayer(String clientName) throws IOException {
		String url = ApiPath.INVALID_PATH_GET_CLIENTNAME_DL + clientName;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}



}
