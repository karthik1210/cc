//Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.nextgen.cc.data.layer.testcases.api;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.medfusion.common.utils.PropertyFileLoader;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import io.restassured.response.Response;

public class CreateClientHierarchy_DL extends BaseRest {
	
	public int createClient_DL() throws IOException {
		String clientName, clientSapNumber;
		Response createClientApiResponse;
		testData = new PropertyFileLoader();
		clientName = CCUtils.generateAlphaNumericString(11);
		clientSapNumber = CCUtils.generateAlphaNumericString(11);
		createClientApiResponse = createNewClient_DL(testData.getProperty("api.client.active"), clientName, clientSapNumber, "false",
				Integer.parseInt(testData.getProperty("api.client.type")), testData.getProperty("api.client.timezoneid"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int clientId = createClientApiResponse.jsonPath().get("clientId");
		return clientId;
	}

	public int createSystem_DL(int clientId) throws IOException {
		Response createSystemApiResponse;
		testData = new PropertyFileLoader();
		createSystemApiResponse = createOrgNode_DL(testData.getProperty("api.system.name"),
				testData.getProperty("api.system.orgtype"), "", testData.getProperty("api.system.active"), "", clientId,
				testData.getProperty("api.parenttype"), Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int systemId = createSystemApiResponse.jsonPath().get("orgId");
		return systemId;
	}

	public int createEnterprise_DL(int systemId) throws IOException {
		Response createEnterpriseApiResponse;
		testData = new PropertyFileLoader();
		createEnterpriseApiResponse = createOrgNode_DL(testData.getProperty("api.enterprise.name"),
				testData.getProperty("api.enterprise.orgtype"), "", testData.getProperty("api.enterprise.active"),
				testData.getProperty("api.enterprise.timezoneid"), systemId,
				testData.getProperty("api.enterprise.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int enterpriseId = createEnterpriseApiResponse.jsonPath().get("orgId");
		return enterpriseId;
	}

	public int createPractice_DL(int enterpriseId) throws IOException {
		Response createPracticeApiResponse;
		testData = new PropertyFileLoader();
		createPracticeApiResponse = createOrgNode_DL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				enterpriseId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		return practiceId;
	}
	
	public int createAgnosticPractice_DL(int systemId) throws IOException {
		Response createPracticeApiResponse;
		testData = new PropertyFileLoader();
		createPracticeApiResponse = createOrgNode_DL(testData.getProperty("api.practice.name"),
				testData.getProperty("api.practice.orgtype"), testData.getProperty("api.practice.displayname"),
				testData.getProperty("api.practice.active"), testData.getProperty("api.practice.timezoneid"),
				systemId, testData.getProperty("api.practice.parenttype"),
				Integer.parseInt(testData.getProperty("ehr.identifier")),
				Integer.parseInt(testData.getProperty("pm.identifier")));
		int practiceId = createPracticeApiResponse.jsonPath().get("orgId");
		return practiceId;
	}


	public Response createNewClient_DL(String active, String clientName, String sapNumber, String testClient, int type,
			String timezoneId, int ehrIdentifier, int pmIdentifier) throws IOException {
		Map<String, Object> createNewClientPayload = postCreateClient_DL(active, clientName, sapNumber, testClient, type,
				timezoneId, ehrIdentifier, pmIdentifier);
		return given().spec(requestSpec).body(createNewClientPayload).when().post(ApiPath.CLIENT_DL).then().extract()
				.response();
	}

	private Map<String, Object> postCreateClient_DL(String active, String clientName, String clientSapNumber,
			String testClient, int type, String timezoneId, int ehrIdentifier, int pmIdentifier) {
		Map<String, Object> createClientMap = new HashMap<String, Object>();
		createClientMap.put("active", active);
		createClientMap.put("name", clientName);
		createClientMap.put("sapNumber", clientSapNumber);
		createClientMap.put("testClient", testClient);
		createClientMap.put("type", type);
		createClientMap.put("timezoneId", timezoneId);
		createClientMap.put("ehrIdentifier", ehrIdentifier);
		createClientMap.put("pmIdentifier", pmIdentifier);
		return createClientMap;
	}

	public Response createOrgNode_DL(String name, String orgType, String displayName, String active, String timezoneId,
			int parentId, String parentType, int ehrIdentifier, int pmIdentifier) throws IOException {
		Map<String, Object> createNewOrgPayload = postCreateOrgMap_DL(name, orgType, displayName, active, timezoneId,
				parentId, parentType, ehrIdentifier, pmIdentifier);
		return given().spec(requestSpec).body(createNewOrgPayload).when().post(ApiPath.ORG_DL).then().extract()
				.response();
	}

	public static Map<String, Object> postCreateOrgMap_DL(String name, String orgType, String displayName, String active,
			String timezoneId, int parentId, String parentType, int ehrIdentifier, int pmIdentifier) {
		Map<String, Object> orgMap = new HashMap<String, Object>();
		orgMap.put("name", name);
		orgMap.put("orgType", orgType);
		orgMap.put("displayName", displayName);
		orgMap.put("active", active);
		orgMap.put("timezoneId", timezoneId);
		orgMap.put("parentId", parentId);
		orgMap.put("parentType", parentType);
		orgMap.put("ehrIdentifier", ehrIdentifier);
		orgMap.put("pmIdentifier", pmIdentifier);
		return orgMap;
	}

}
