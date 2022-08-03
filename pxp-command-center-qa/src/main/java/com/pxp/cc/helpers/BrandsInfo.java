// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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
	//Added By Sarada Burugula as part of PXPP-17497 on 25/07/2022
	private Map<String, Object> postCreateBrandPayload(int orgId, String brandname) {
		Map<String, Object> CreateBrandMap = new HashMap<String, Object>();
		CreateBrandMap.put("orgId", orgId);
		CreateBrandMap.put("name", brandname);
		CreateBrandMap.put("showLogo", true);
		CreateBrandMap.put("color", "#aabbcc");
		return CreateBrandMap;
	}
	private Map<String, Object> errorpostCreateBrandPayload(Integer orgId, String brandname,Boolean showLogo,String color) {
		Map<String, Object> CreateBrandMap = new HashMap<String, Object>();
		CreateBrandMap.put("orgId", orgId);
		CreateBrandMap.put("name", brandname);
		CreateBrandMap.put("showLogo", showLogo);
		CreateBrandMap.put("color", color);
		return CreateBrandMap;
	}
	public Response postCreateBrand( int orgId, String brandname) {
		String url = ApiPath.CREATEBRANDS_DL;
		Map<String, Object> postCreateBrandPayload = postCreateBrandPayload(orgId,brandname);
		return given().spec(requestSpec).body(postCreateBrandPayload).when().post(url).then().extract()
				.response();
	}
	public Response errorPostCreateBrand( Integer orgId, String brandname,Boolean showLogo,String color) {
		String url = ApiPath.CREATEBRANDS_DL;
		Map<String, Object> errorPostCreateBrand = errorpostCreateBrandPayload(orgId, brandname,showLogo,color);
		return given().spec(requestSpec).body(errorPostCreateBrand).when().post(url).then().extract()
				.response();
	}
	public Response InvalidPostCreateBrand( int orgId, String brandname) {
		String url = ApiPath.INVALID_CREATE_BRANDS_DL;
		Map<String, Object> postCreateBrandPayload = postCreateBrandPayload(orgId,brandname);
		return given().spec(requestSpec).body(postCreateBrandPayload).when().post(url).then().extract()
				.response();
	}

	public Response putUpdateBrand(int BrandId,int orgId, String brandname) {
		String url = ApiPath.UPDATE_BRAND_DL(BrandId);
		Map<String, Object> putUpdateBrandPayload = postCreateBrandPayload(orgId,brandname);
		return given().spec(requestSpec).body(putUpdateBrandPayload).when().put(url)
				.then().extract().response();

	}
	public Response putUpdateAllBranddetails( int BrandId,Integer orgId, String brandname,Boolean showLogo,String color) {
		String url = ApiPath.UPDATE_BRAND_DL(BrandId);
		Map<String, Object> putUpdateBrandPayload = errorpostCreateBrandPayload(orgId, brandname,showLogo,color);
		return given().spec(requestSpec).body(putUpdateBrandPayload).when().put(url)
				.then().extract().response();

	}
	public Response errorPutUpateBrand( int BrandId,Integer orgId, String brandname,Boolean showLogo,String color) {
		String url = ApiPath.UPDATE_BRAND_DL(BrandId);
		Map<String, Object> errorPutUpdateBrandPayload = errorpostCreateBrandPayload(orgId, brandname, showLogo, color);
		return given().spec(requestSpec).body(errorPutUpdateBrandPayload).when().put(url).then().extract().response();
	}
	public Response InvalidPutUpateBrand( int BrandId,int orgId, String brandname) {
		String url = ApiPath.Invalid_UPDATE_BRAND_DL(BrandId);
		Map<String, Object> putUpdateBrandPayload = postCreateBrandPayload(orgId,brandname);
		return given().spec(requestSpec).body(putUpdateBrandPayload).when().put(url).then().extract()
				.response();
	}
}
