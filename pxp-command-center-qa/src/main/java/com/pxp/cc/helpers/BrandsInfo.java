//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import io.restassured.response.Response;

public class BrandsInfo extends BaseRest {

	public Response postDuplicateBrand(int brandId, int ownerId, String ownerType) {
		String url = ApiPath.DUPLICATE_BRANDS_DL + brandId;
		if (ownerId > 0) {
			Map<String, Object> postDuplicateBrandPayload = postDuplicateBrand(ownerId, ownerType);
			return given().spec(requestSpec).body(postDuplicateBrandPayload).when().post(url).then().extract()
					.response();
		} else {
			Map<String, Object> postDuplicateBrandPayload = postDuplicateBrand(ownerType);
			return given().spec(requestSpec).body(postDuplicateBrandPayload).when().post(url).then().extract()
					.response();
		}
	}

	public Response postDuplicateBrandInvalidUrl(int brandId, int ownerId, String ownerType) {
		String url = ApiPath.INVALID_DUPLICATE_BRANDS_DL + brandId;
		Map<String, Object> postDuplicateBrandPayload = postDuplicateBrand(ownerId, ownerType);
		return given().spec(requestSpec).body(postDuplicateBrandPayload).when().post(url).then().extract().response();
	}

	public Response duplicateBrandInvalidMethodCall(int brandId, int ownerId, String ownerType) {
		String url = ApiPath.DUPLICATE_BRANDS_DL + brandId;
		Map<String, Object> postDuplicateBrandPayload = postDuplicateBrand(ownerId, ownerType);
		return given().spec(requestSpec).body(postDuplicateBrandPayload).when().put(url).then().extract().response();
	}

	private Map<String, Object> postDuplicateBrand(String ownerType) {
		Map<String, Object> duplicateBrandMap = new HashMap<String, Object>();
		duplicateBrandMap.put("ownerId", null);
		duplicateBrandMap.put("ownerType", ownerType);
		return duplicateBrandMap;
	}

	public Response postDuplicateBrandBL(int brandId, int ownerId, String ownerType) {
		String url = ApiPath.DUPLICATE_BRANDS_BL + brandId;
		if (ownerId > 0) {
			Map<String, Object> postDuplicateBrandPayload = postDuplicateBrand(ownerId, ownerType);
			return given().spec(requestSpec).body(postDuplicateBrandPayload).when().post(url).then().extract()
					.response();
		} else {
			Map<String, Object> postDuplicateBrandPayload = postDuplicateBrand(ownerType);
			return given().spec(requestSpec).body(postDuplicateBrandPayload).when().post(url).then().extract()
					.response();
		}
	}
	
	public Response postDuplicateBrandInvalidUrlBL(int brandId, int ownerId, String ownerType) {
		String url = ApiPath.INVALID_DUPLICATE_BRANDS_BL + brandId;
		Map<String, Object> postDuplicateBrandPayload = postDuplicateBrand(ownerId, ownerType);
		return given().spec(requestSpec).body(postDuplicateBrandPayload).when().post(url).then().extract().response();
	}

	public Response duplicateBrandInvalidMethodCallBL(int brandId, int ownerId, String ownerType) {
		String url = ApiPath.DUPLICATE_BRANDS_BL + brandId;
		Map<String, Object> postDuplicateBrandPayload = postDuplicateBrand(ownerId, ownerType);
		return given().spec(requestSpec).body(postDuplicateBrandPayload).when().patch(url).then().extract().response();
	}
	
	private Map<String, Object> postDuplicateBrand(int ownerId, String ownerType) {
		Map<String, Object> duplicateBrandMap = new HashMap<String, Object>();
		duplicateBrandMap.put("ownerId", ownerId);
		duplicateBrandMap.put("ownerType", ownerType);
		return duplicateBrandMap;
	}
}
