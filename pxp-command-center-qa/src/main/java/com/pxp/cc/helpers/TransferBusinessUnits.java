// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import io.restassured.response.Response;

public class TransferBusinessUnits extends BaseRest {
	public Response moveOrgNode(Boolean inheritFlag, int sourceId, String sourceType, int targetId, String targetType)
			throws IOException {
		Map<String, Object> transferBusinessUnitsPayload = postTransferBusinessUnits(inheritFlag, sourceId, sourceType,
				targetId, targetType);
		return given().spec(requestSpec).body(transferBusinessUnitsPayload).when().post(ApiPath.POST_MOVE_ORG_NODE)
				.then().extract().response();
	}

	public Response moveOrgNode_BL(int clientId, int sourceId, Boolean inheritFlag) throws IOException {
		Map<String, Object> transferBusinessUnitsPayload = putTransferBusinessUnits(sourceId, inheritFlag);
		String url = ApiPath.MOVE_ORG_NODE(clientId);
		return given().spec(requestSpec).body(transferBusinessUnitsPayload).when().put(url).then().extract().response();
	}

	private Map<String, Object> postTransferBusinessUnits(Boolean inheritFlag, int sourceId, String sourceType,
			int targetId, String targetType) {
		Map<String, Object> transferBusinessUnitsMap = new HashMap<String, Object>();
		transferBusinessUnitsMap.put("inheritFlag", inheritFlag);
		transferBusinessUnitsMap.put("sourceId", sourceId);
		transferBusinessUnitsMap.put("sourceType", sourceType);
		transferBusinessUnitsMap.put("targetId", targetId);
		transferBusinessUnitsMap.put("targetType", targetType);
		return transferBusinessUnitsMap;
	}

	private Map<String, Object> putTransferBusinessUnits(int orgId, Boolean inheritFlag) {
		Map<String, Object> transferBusinessUnitsMap = new HashMap<String, Object>();
		transferBusinessUnitsMap.put("orgId", orgId);
		transferBusinessUnitsMap.put("inherit", inheritFlag);
		return transferBusinessUnitsMap;
	}
}