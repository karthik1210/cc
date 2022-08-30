// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;

import io.restassured.response.Response;

public class ResourceInfo extends BaseRest {

	public Response getResourceDetails_BL(int resourceId) {
		String url = ApiPath.GET_RESOURCE_DETAILS_BL(resourceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}
	
	public Response getResourceDetails_BL_NULL() {
		String url = ApiPath.GET_RESOURCE_DETAILS_BL_NULL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}
	
	public Response getResourceDetails_BL_INVALID_URL(int resourceId) {
		String url = ApiPath.GET_RESOURCE_DETAILS_BL_INVALID_URL(resourceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}
	
	public Response getResourceDetails_BL_INVALID_METHOD_CALL(int resourceId) {
		String url = ApiPath.GET_RESOURCE_DETAILS_BL(resourceId);
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	public Response createResource_BL(int practiceId,String name,String type, String externalId, String linkId, String linkType) {
		String url = ApiPath.CREATE_RESOURCE_DETAILS_BL(practiceId);
		Map<String, Object> postResourceCreatePayload = postCreateResourcePayload(name,type,externalId,linkId,linkType);
		return given().spec(requestSpec).body(postResourceCreatePayload).when().post(url).then().extract().response();
	}
	
	private Map<String, Object> postCreateResourcePayload(String name,String type, String externalId, String linkId, String linkType) {
		Map<String, Object> createResourceMap = new HashMap<String, Object>();
		createResourceMap.put("name", name);
		createResourceMap.put("type", type);
		createResourceMap.put("externalId", externalId);
		createResourceMap.put("linkId", linkId);
		createResourceMap.put("linkType", linkType);
		return createResourceMap;
	}
}
