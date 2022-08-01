//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;

import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;

import io.restassured.response.Response;

public class OrgResourceInfo extends BaseRest{

	public Response getSolutionsForResources_DL(int resourceId, int practiceId) {
		String url = ApiPath.FETCH_SOLUTIONS_FOR_RESOURCES_DL(resourceId, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getSolutionsForResources_DL_InvalidUrl(int resourceId, int practiceId) {
		String url = ApiPath.FETCH_SOLUTIONS_FOR_RESOURCES_DL_INVALID_URL(resourceId, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getSolutionsForResources_DL_InvalidMethodCall(int resourceId, int practiceId) {
		String url = ApiPath.FETCH_SOLUTIONS_FOR_RESOURCES_DL(resourceId, practiceId);
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	public Response getSolutionsForResources_DL_NullValues() {
		String url = ApiPath.FETCH_SOLUTIONS_FOR_RESOURCES_DL_NULL_VALUES;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}
	
	public Response getSolutionsForResources_BL(int resourceId, int practiceId) {
		String url = ApiPath.FETCH_SOLUTIONS_FOR_RESOURCES_BL(resourceId, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getSolutionsForResources_BL_InvalidUrl(int resourceId, int practiceId) {
		String url = ApiPath.FETCH_SOLUTIONS_FOR_RESOURCES_BL_INVALID_URL(resourceId, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getSolutionsForResources_BL_InvalidMethodCall(int resourceId, int practiceId) {
		String url = ApiPath.FETCH_SOLUTIONS_FOR_RESOURCES_BL(resourceId, practiceId);
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	public Response getSolutionsForResources_BL_NullValues() {
		String url = ApiPath.FETCH_SOLUTIONS_FOR_RESOURCES_BL_NULL_VALUES;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}
}
