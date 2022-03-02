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

	public Response createNewClient(String active, String clientId, String clientName, String sapNumber,
			String testClient, String type, String timezoneId) throws IOException {
		Map<String, Object> createNewClientPayload = postCreateClient(active, clientId, clientName, sapNumber,
				testClient, type, timezoneId);
		return given().spec(requestSpec).body(createNewClientPayload).when().post(ApiPath.POST_CLIENT_BL).then()
				.extract().response();
	}

	public Response updateClient(String active, String clientId, String clientName, String sapNumber, String testClient,
			String type, String timezoneId) throws IOException {
		String url = ApiPath.GET_CLIENT_BL + clientId;
		Map<String, Object> updateClientPayload = putUpdateClient(active, clientName, sapNumber, testClient, type,
				timezoneId);
		return given().spec(requestSpec).body(updateClientPayload).when().put(url).then().extract().response();
	}

	public Response updateClientWithInvalidUrl(String active, String clientId, String clientName, String sapNumber,
			String testClient, String type, String timezoneId) throws IOException {
		String url = ApiPath.INVALID_PATH_PUT_UPDATECLIENT_BL + clientId;
		Map<String, Object> updateClientPayload = putUpdateClient(active, clientName, sapNumber, testClient, type,
				timezoneId);
		return given().spec(requestSpec).body(updateClientPayload).when().put(url).then().extract().response();
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

	private Map<String, Object> postCreateClient(String active, String clientId, String clientName,
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

	public Response createNewClientUsingInvalidUrl(String active, String clientId, String clientName, String sapNumber,
			String testClient, String type, String timezoneId) throws IOException {
		Map<String, Object> createNewClientPayload = postCreateClient(active, clientId, clientName, sapNumber,
				testClient, type, timezoneId);
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

	public Response getClientTypeUsingInvalidUrl_DataLayer() throws IOException {
		String url = ApiPath.INVALID_PATH_GET_CLIENTTYPE_DL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getClientTypes_DataLayer() {
		String url = ApiPath.GET_CLIENT_TYPES_DL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getClientSapNumber_DataLayer(String clientSapNumber) throws IOException {
		String url = ApiPath.GET_CLIENTSAPNUMBER_DL + "{sapNumber}";
		return given().spec(requestSpec).pathParam("sapNumber", clientSapNumber).when().get(url).then().extract()
				.response();
	}

	public Response getClientSapNumbrUsingInvalidUrl_DataLayer(String clietSapNumber) throws IOException {
		String url = ApiPath.INVALID_PATH_GET_CLIENTSAPNUMBER_DL + clietSapNumber;
		return given().spec(requestSpec).when().get(url).then().extract().response();

	}

	public Response createNewClient_DataLayer(String active, String clientId, String clientName, String sapNumber,
			String testClient, String type, String timezoneId, int userId) throws IOException {
		Map<String, Object> createNewClientPayload = postCreateClient(active, clientId, clientName, sapNumber,
				testClient, type, timezoneId);
		return given().spec(requestSpec).header(ApiPath.PXP_CC_USER, userId).body(createNewClientPayload).when()
				.post(ApiPath.POST_CLIENT_DL).then().extract().response();

	}

	public Response createNewClient_DataLayerUsingInvalidUrl(String active, String clientId, String clientName,
			String sapNumber, String testClient, String type, String timezoneId, int userId) throws IOException {
		Map<String, Object> createNewClientPayload = postCreateClient(active, clientId, clientName, sapNumber,
				testClient, type, timezoneId);
		String invalidUrl = ApiPath.POST_CLIENT_DL + "abc";
		return given().spec(requestSpec).header(ApiPath.PXP_CC_USER, userId).body(createNewClientPayload).when()
				.post(invalidUrl).then().extract().response();
	}

	public Response getClientDetails_DataLayer(String clientId) {
		String url = ApiPath.GET_CLIENT_DL + clientId;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getClientDetails_DataLayerInvalidUrl(String clientId) {
		String url = ApiPath.INVALID_PATH_GET_CLIENT_DL + clientId;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response UpdateClient_DataLayer(int clientId, String active, String clientName, String sapNumber,
			String testClient, String type, String timezoneId, int userId) throws IOException {
		Map<String, Object> updateClientPayload = putUpdateClient(active, clientName, sapNumber, testClient, type,
				timezoneId);
		String url = ApiPath.PUT_CLIENT_DL + "{clientId}";
		return given().spec(requestSpec).header(ApiPath.PXP_CC_USER, userId).pathParam("clientId", clientId)
				.body(updateClientPayload).when().put(url).then().extract().response();
	}

	public Response UpdateClientIsingInvalidUrl_DataLayer(int clientId, String active, String clientName,
			String sapNumber, String testClient, String type, String timezoneId, int userId) throws IOException {
		Map<String, Object> updateClientPayload = putUpdateClient(active, clientName, sapNumber, testClient, type,
				timezoneId);
		String url = ApiPath.INVALID_PATH_PUT_CLIENT_DL + "{clientId}";
		return given().spec(requestSpec).header(ApiPath.PXP_CC_USER, userId).pathParam("clientId", clientId)
				.body(updateClientPayload).when().put(url).then().extract().response();
	}

	private Map<String, Object> putUpdateClient(String active, String clientName, String clientSapNumber,
			String testClient, String type, String timezoneId) {
		Map<String, Object> updateClientMap = new HashMap<String, Object>();
		updateClientMap.put("active", active);
		updateClientMap.put("name", clientName);
		updateClientMap.put("sapNumber", clientSapNumber);
		updateClientMap.put("testClient", testClient);
		updateClientMap.put("type", type);
		updateClientMap.put("timezoneId", timezoneId);
		return updateClientMap;
	}

}
