// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ResourceInfo extends BaseRest {

	public static RequestSpecification requestSpecification;

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

	public Response addResourceImage_BL(int resourceId, File file, String fileType) {
		String url = ApiPath.ADD_RESOURCE_IMAGE(resourceId);
		requestSpecification = new RequestSpecBuilder().addFilter(new ResponseLoggingFilter())
				.addFilter(new RequestLoggingFilter()).build();
		return given().spec(requestSpecification).contentType("multipart/form-data").multiPart("file", file, fileType)
				.post(url).then().extract().response();
	}

	public Response addResourceImageInvalidUrl_BL(int resourceId, File file) {
		String url = ApiPath.ADD_RESOURCE_IMAGE_INVALID_URL(resourceId);
		requestSpecification = new RequestSpecBuilder().addFilter(new ResponseLoggingFilter())
				.addFilter(new RequestLoggingFilter()).build();
		return given().spec(requestSpecification).contentType("multipart/form-data")
				.multiPart("file", file, "image/png").post(url).then().extract().response();
	}

	public Response addResourceImageInvalidMethodCall_BL(int resourceId, File file) {
		String url = ApiPath.ADD_RESOURCE_IMAGE(resourceId);
		requestSpecification = new RequestSpecBuilder().addFilter(new ResponseLoggingFilter())
				.addFilter(new RequestLoggingFilter()).build();
		return given().spec(requestSpecification).contentType("multipart/form-data")
				.multiPart("file", file, "image/png").put(url).then().extract().response();
	}

	public Response createResource_BL(int practiceId, String name, String type, String externalId, String linkId,
			String linkType) {
		String url = ApiPath.CREATE_RESOURCE_BL(practiceId);
		Map<String, Object> createResourcePayload = createResourceRequestPayload(name, type, externalId, linkId,
				linkType);
		return given().spec(requestSpec).body(createResourcePayload).when().post(url).then().extract().response();
	}

	public Response createResourceInvalidUrl_BL(int practiceId, String name, String type, String externalId,
			String linkId, String linkType) {
		String url = ApiPath.CREATE_RESOURCE_INVALID_URL_BL(practiceId);
		Map<String, Object> createResourcePayload = createResourceRequestPayload(name, type, externalId, linkId,
				linkType);
		return given().spec(requestSpec).body(createResourcePayload).when().post(url).then().extract().response();
	}

	public Response createResourceInvalidMethodCall_BL(int practiceId, String name, String type, String externalId,
			String linkId, String linkType) {
		String url = ApiPath.CREATE_RESOURCE_BL(practiceId);
		Map<String, Object> createResourcePayload = createResourceRequestPayload(name, type, externalId, linkId,
				linkType);
		return given().spec(requestSpec).body(createResourcePayload).when().put(url).then().extract().response();
	}

	public Response createResourceNullValue_BL(String name, String type, String externalId, String linkId,
			String linkType) {
		String url = ApiPath.CREATE_RESOURCE_NULL_VALUE_BL;
		Map<String, Object> createResourcePayload = createResourceRequestPayload(name, type, externalId, linkId,
				linkType);
		return given().spec(requestSpec).body(createResourcePayload).when().post(url).then().extract().response();
	}

	private Map<String, Object> createResourceRequestPayload(String name, String type, String externalId, String linkId,
			String linkType) {
		Map<String, Object> createResourceMap = new HashMap<String, Object>();
		createResourceMap.put("name", name);
		createResourceMap.put("type", type);
		createResourceMap.put("externalId", externalId);
		createResourceMap.put("linkId", linkId);
		createResourceMap.put("linkType", linkType);
		return createResourceMap;
	}

	public Response createResource_DL(int practiceId, String name, String type, String externalId, String linkId,
			String linkType) {
		String url = ApiPath.CREATE_RESOURCE_DL(practiceId);
		Map<String, Object> createResourcePayload = createResourceRequestPayload(name, type, externalId, linkId,
				linkType);
		return given().spec(requestSpec).body(createResourcePayload).when().post(url).then().extract().response();
	}

	public Response createResourceInvalidUrl_DL(int practiceId, String name, String type, String externalId,
			String linkId, String linkType) {
		String url = ApiPath.CREATE_RESOURCE_INVALID_URL_DL(practiceId);
		Map<String, Object> createResourcePayload = createResourceRequestPayload(name, type, externalId, linkId,
				linkType);
		return given().spec(requestSpec).body(createResourcePayload).when().post(url).then().extract().response();
	}

	public Response createResourceInvalidPutMethodCall_DL(int practiceId, String name, String type, String externalId,
			String linkId, String linkType) {
		String url = ApiPath.CREATE_RESOURCE_DL(practiceId);
		Map<String, Object> createResourcePayload = createResourceRequestPayload(name, type, externalId, linkId,
				linkType);
		return given().spec(requestSpec).body(createResourcePayload).when().put(url).then().extract().response();
	}

	public Response createResourceNullValue_DL(String name, String type, String externalId, String linkId,
			String linkType) {
		String url = ApiPath.CREATE_RESOURCE_NULL_VALUE_DL;
		Map<String, Object> createResourcePayload = createResourceRequestPayload(name, type, externalId, linkId,
				linkType);
		return given().spec(requestSpec).body(createResourcePayload).when().post(url).then().extract().response();
	}

	public Response searchResources_DL(String clientId, String resourceName, String systemId, String enterpriseId,
			String practiceId, String solutionId, String sortColumn, String sortDir, String page, String pagesize) {
		int clientID = Integer.parseInt(clientId);
		int systemID = Integer.parseInt(systemId);
		int enterpriseID = Integer.parseInt(enterpriseId);
		int practiceID = Integer.parseInt(practiceId);
		int solutionID = Integer.parseInt(solutionId);
		int pages = Integer.parseInt(page);
		int pageSize = Integer.parseInt(pagesize);
		String url = ApiPath.SEARCH_RESOURCES_DL(clientID);
		return given().spec(requestSpec).queryParam("name", resourceName).queryParam("systemid", systemID)
				.queryParam("enterpriseid", enterpriseID).queryParam("practiceid", practiceID)
				.queryParam("solutionid", solutionID).queryParam("sortcolumn", sortColumn)
				.queryParam("sortdir", sortDir).queryParam("page", pages).queryParam("pagesize", pageSize).when()
				.get(url).then().extract().response();
	}
	
	public Response getDisplayNameOfPracticeResource_BL(int resourceId,int practiceId) {
		String url = ApiPath.GET_DISPLAY_DETAILS_BL(resourceId,practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}
	
	public Response  getDisplayNameOfPracticeResource_BL_NULL() {
		String url = ApiPath.GET_DISPLAY_DETAILS_BL_NULL();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}	

	public Response getResourceDetails_DL(int resourceId) {
		String url = ApiPath.GET_RESOURCE_DETAILS_DL(resourceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getResourceDetails_DL_NULL() {
		String url = ApiPath.GET_RESOURCE_DETAILS_DL_NULL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response deleteResourceImage_BL(int resourceId) {
		String url = ApiPath.DELETE_RESOURCE_IMAGE_BL(resourceId);
		return given().spec(requestSpec).when().delete(url).then().extract().response();
	}
}
