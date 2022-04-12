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
		String url = ApiPath.CLIENT_DETAILS_BL(clientId);
		return given().spec(requestSpec).when().pathParam("clientId", clientId).get(url).then().extract().response();
	}

	public Response createNewClient(String active, String clientName, String sapNumber, String testClient, String type,
			String timezoneId) throws IOException {
		Map<String, Object> createNewClientPayload = postCreateClient(active, clientName, sapNumber, testClient, type,
				timezoneId);
		return given().spec(requestSpec).body(createNewClientPayload).when().post(ApiPath.CLIENT_BL).then().extract()
				.response();
	}

	public Response updateClient(String active, String clientId, String clientName, String sapNumber, String testClient,
			String type, String timezoneId) throws IOException {
		String url = ApiPath.CLIENT_DETAILS_BL(clientId);
		Map<String, Object> updateClientPayload = putUpdateClient(active, clientName, sapNumber, testClient, type,
				timezoneId);
		return given().spec(requestSpec).body(updateClientPayload).pathParam("clientId", clientId).when().put(url)
				.then().extract().response();
	}

	public Response updateClientWithInvalidUrl(String active, String clientId, String clientName, String sapNumber,
			String testClient, String type, String timezoneId) throws IOException {
		String url = ApiPath.INVALID_PATH_CLIENT_DETAILS_BL(clientId);
		Map<String, Object> updateClientPayload = putUpdateClient(active, clientName, sapNumber, testClient, type,
				timezoneId);
		return given().spec(requestSpec).body(updateClientPayload).pathParam("clientId", clientId).when().put(url)
				.then().extract().response();
	}

	public Response getSapNumber(String clientSapNumber) throws IOException {
		String url = ApiPath.CLIENTSAPNUMBER_BL(clientSapNumber);
		return given().spec(requestSpec).pathParam("sapNumber", clientSapNumber).when().get(url).then().extract()
				.response();
	}

	public Response patchClientStatus(String clientId, String clientStatus) throws IOException {
		String url = ApiPath.GET_CLIENT + clientId + ApiPath.GET_CLIENT_STATUS + clientStatus;
		return given().spec(requestSpec).when().patch(url).then().extract().response();
	}

	public Response getClientTypes() throws IOException {
		String url = ApiPath.CLIENT_TYPES;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getClientOrgId(int clientId) throws IOException {
		String url = ApiPath.GET_CLIENT_ORG_ID + clientId;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getClientName(String clientName) throws IOException {
		String url = ApiPath.CLIENTNAME_BL(clientName);
		return given().spec(requestSpec).pathParam("name", clientName).when().get(url).then().extract().response();
	}

	public Response getOrgHierarchyWithClientNode(String clientId, Boolean forClientValue) throws IOException {
		String url = ApiPath.GET_ORG_HIERARCHY + clientId;
		return given().spec(requestSpec).queryParam("excludeClientData", forClientValue).when().get(url).then()
				.extract().response();
	}

	private Map<String, Object> postCreateClient(String active, String clientName, String clientSapNumber,
			String testClient, String type, String timezoneId) {
		Map<String, Object> createClientMap = new HashMap<String, Object>();
		createClientMap.put("active", active);
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

	public Response getAutoSuggestionClient_BL(String searchData) {
		String url = ApiPath.AUTOSUGGESTION_CLIENT_BL;
		return given().spec(requestSpec).queryParam("query", searchData).when().get(url).then().extract().response();
	}

	public Response createNewClientUsingInvalidUrl(String active, String clientName, String sapNumber,
			String testClient, String type, String timezoneId) throws IOException {
		Map<String, Object> createNewClientPayload = postCreateClient(active, clientName, sapNumber, testClient, type,
				timezoneId);
		String invalidUrl = ApiPath.INVALID_PATH_CLIENT_BL;
		return given().spec(requestSpec).body(createNewClientPayload).when().post(invalidUrl).then().extract()
				.response();
	}

	public Response getClientNameUsingInvalidUrl(String clientName) throws IOException {
		String url = ApiPath.INVALID_PATH_CLIENTNAME_BL(clientName);
		return given().spec(requestSpec).pathParam("name", clientName).when().get(url).then().extract().response();
	}

	public Response getClientTypeUsingInvalidUrl() throws IOException {
		String url = ApiPath.INVALID_PATH_CLIENTTYPE_BL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getClientSapNumbrUsingInvalidUrl(String clientSapNumber) throws IOException {
		String url = ApiPath.INVALID_PATH_CLIENTSAPNUMBER_BL(clientSapNumber);
		return given().spec(requestSpec).pathParam("sapNumber", clientSapNumber).when().get(url).then().extract()
				.response();
	}

	public Response getClientName_DataLayer(String clientName) throws IOException {
		String url = ApiPath.CLIENTNAME_DL(clientName);
		return given().spec(requestSpec).pathParam("name", clientName).when().get(url).then().extract().response();
	}

	public Response getClientNameUsingInvalidUrl_DataLayer(String clientName) throws IOException {
		String url = ApiPath.INVALID_PATH_CLIENTNAME_DL(clientName);
		return given().spec(requestSpec).pathParam("name", clientName).when().get(url).then().extract().response();
	}

	public Response getClientTypeUsingInvalidUrl_DataLayer() throws IOException {
		String url = ApiPath.INVALID_PATH_CLIENTTYPE_DL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getClientTypes_DataLayer() {
		String url = ApiPath.CLIENT_TYPES_DL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getClientSapNumber_DataLayer(String clientSapNumber) throws IOException {
		String url = ApiPath.CLIENTSAPNUMBER_DL(clientSapNumber);
		return given().spec(requestSpec).pathParam("sapNumber", clientSapNumber).when().get(url).then().extract()
				.response();
	}

	public Response getClientSapNumbrUsingInvalidUrl_DataLayer(String clientSapNumber) throws IOException {
		String url = ApiPath.INVALID_PATH_CLIENTSAPNUMBER_DL(clientSapNumber);
		return given().spec(requestSpec).pathParam("sapNumber", clientSapNumber).when().get(url).then().extract()
				.response();

	}

	public Response createNewClient_DataLayer(String active, String clientName, String sapNumber, String testClient,
			String type, String timezoneId, int userId) throws IOException {
		Map<String, Object> createNewClientPayload = postCreateClient(active, clientName, sapNumber, testClient, type,
				timezoneId);
		return given().spec(requestSpec).header(ApiPath.PXP_CC_USER, userId).body(createNewClientPayload).when()
				.post(ApiPath.CLIENT_DL).then().extract().response();

	}

	public Response createNewClient_DataLayerUsingInvalidUrl(String active, String clientName, String sapNumber,
			String testClient, String type, String timezoneId, int userId) throws IOException {
		Map<String, Object> createNewClientPayload = postCreateClient(active, clientName, sapNumber, testClient, type,
				timezoneId);
		String invalidUrl = ApiPath.INVALID_PATH_CLIENT_DL;
		return given().spec(requestSpec).header(ApiPath.PXP_CC_USER, userId).body(createNewClientPayload).when()
				.post(invalidUrl).then().extract().response();
	}

	public Response getClientDetails_DataLayer(int clientId) {
		String url = ApiPath.CLIENT_DETAILS_DL(clientId);
		return given().spec(requestSpec).when().pathParam("clientId", clientId).get(url).then().extract().response();
	}

	public Response getClientDetails_DataLayerInvalidUrl(int clientId) {
		String url = ApiPath.INVALID_PATH_CLIENT_DETAILS_DL(clientId);
		return given().spec(requestSpec).when().pathParam("clientId", clientId).get(url).then().extract().response();
	}

	public Response UpdateClient_DataLayer(int clientId, String active, String clientName, String sapNumber,
			String testClient, String type, String timezoneId, int userId) throws IOException {
		Map<String, Object> updateClientPayload = putUpdateClient(active, clientName, sapNumber, testClient, type,
				timezoneId);
		String url = ApiPath.CLIENT_DETAILS_DL(clientId);
		return given().spec(requestSpec).header(ApiPath.PXP_CC_USER, userId).pathParam("clientId", clientId)
				.body(updateClientPayload).when().put(url).then().extract().response();
	}

	public Response UpdateClientIsingInvalidUrl_DataLayer(int clientId, String active, String clientName,
			String sapNumber, String testClient, String type, String timezoneId, int userId) throws IOException {
		Map<String, Object> updateClientPayload = putUpdateClient(active, clientName, sapNumber, testClient, type,
				timezoneId);
		String url = ApiPath.INVALID_PATH_CLIENT_DETAILS_DL(clientId);
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

	public Response getClientSolutionsDetails_DataLayer(int clientId) {
		String url = ApiPath.CLIENT_SOLUTION_DL(clientId);
		return given().spec(requestSpec).pathParam("clientId", clientId).when().get(url).then().extract().response();
	}

	public Response getClientOrgHierarchyDetails(int clientId) {
		String url = ApiPath.CLIENT_ORG_HIERARCHY_BL(clientId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getClientOrgHierarchyUsingInvalidUrl(int clientId) {
		String url = ApiPath.INVALID_PATH_CLIENT_ORGS_HIERARCHY_BL(clientId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getClientOrgHierarchyDetails_DataLayer(int clientId, int userId) {
		String url = ApiPath.CLIENT_ORG_HIERARCHY_DL(clientId);
		return given().spec(requestSpec).header(ApiPath.PXP_CC_USER, userId).when().get(url).then().extract()
				.response();
	}

	public Response getClientOrgHierarchyUsingInvalidUrl_DataLayer(int clientId, int userId) {
		String url = ApiPath.INVALID_PATH_CLIENT_ORG_HIERARCHY_DL(clientId);
		return given().spec(requestSpec).header(ApiPath.PXP_CC_USER, userId).when().get(url).then().extract()
				.response();
	}
	
	public Response getAutoSuggestionClient_DL(String searchData) {
		String url = ApiPath.AUTOSUGGESTION_CLIENT_DL;
		return given().spec(requestSpec).queryParam("query", searchData).when().get(url).then().extract()
				.response();
	}

    public Response getOrganizationListByClientId(int clientId, String orgType) throws IOException {
		String url = ApiPath.GET_ORGANIZATION_LIST_BL(clientId);
		return given().spec(requestSpec).queryParam("orgType", orgType).pathParam("clientId", clientId).when().get(url)
				.then().extract().response();
	}

	public Response getOrganizationListByClientIdUsingInvalidUrl(int clientId, String orgType) throws IOException {
		String url = ApiPath.INVALID_PATH_GET_ORGANIZATION_LIST_BL(clientId);
		return given().spec(requestSpec).queryParam("orgType", orgType).when().pathParam("clientId", clientId).get(url)
				.then().extract().response();
	}
}
