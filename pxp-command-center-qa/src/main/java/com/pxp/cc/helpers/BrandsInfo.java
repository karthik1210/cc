// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import io.restassured.response.Response;

public class BrandsInfo extends BaseRest {

	public Response postDuplicateBrand(int brandId, int ownerId) {
		String url = ApiPath.DUPLICATE_BRANDS_DL + brandId;
		if (ownerId > 0) {
			Map<String, Object> postDuplicateBrandPayload = postDuplicateBrand(ownerId);
			return given().spec(requestSpec).body(postDuplicateBrandPayload).when().post(url).then().extract()
					.response();
		} else {
			Map<String, Object> postDuplicateBrandPayload = postDuplicateBrand();
			return given().spec(requestSpec).body(postDuplicateBrandPayload).when().post(url).then().extract()
					.response();
		}
	}

	public Response postDuplicateBrandInvalidUrl(int brandId, int ownerId) {
		String url = ApiPath.INVALID_DUPLICATE_BRANDS_DL + brandId;
		Map<String, Object> postDuplicateBrandPayload = postDuplicateBrand(ownerId);
		return given().spec(requestSpec).body(postDuplicateBrandPayload).when().post(url).then().extract().response();
	}

	public Response duplicateBrandInvalidMethodCall(int brandId, int ownerId) {
		String url = ApiPath.DUPLICATE_BRANDS_DL + brandId;
		Map<String, Object> postDuplicateBrandPayload = postDuplicateBrand(ownerId);
		return given().spec(requestSpec).body(postDuplicateBrandPayload).when().patch(url).then().extract().response();
	}

	private Map<String, Object> postDuplicateBrand() {
		Map<String, Object> duplicateBrandMap = new HashMap<String, Object>();
		duplicateBrandMap.put("ownerId", null);
		return duplicateBrandMap;
	}

	public Response postDuplicateBrandBL(int brandId, int ownerId) {
		String url = ApiPath.DUPLICATE_BRANDS_BL + brandId;
		if (ownerId > 0) {
			Map<String, Object> postDuplicateBrandPayload = postDuplicateBrand(ownerId);
			return given().spec(requestSpec).body(postDuplicateBrandPayload).when().post(url).then().extract()
					.response();
		} else {
			Map<String, Object> postDuplicateBrandPayload = postDuplicateBrand();
			return given().spec(requestSpec).body(postDuplicateBrandPayload).when().post(url).then().extract()
					.response();
		}
	}

	public Response postDuplicateBrandInvalidUrlBL(int brandId, int ownerId) {
		String url = ApiPath.INVALID_DUPLICATE_BRANDS_BL + brandId;
		Map<String, Object> postDuplicateBrandPayload = postDuplicateBrand(ownerId);
		return given().spec(requestSpec).body(postDuplicateBrandPayload).when().post(url).then().extract().response();
	}

	public Response duplicateBrandInvalidMethodCallBL(int brandId, int ownerId) {
		String url = ApiPath.DUPLICATE_BRANDS_BL + brandId;
		Map<String, Object> postDuplicateBrandPayload = postDuplicateBrand(ownerId);
		return given().spec(requestSpec).body(postDuplicateBrandPayload).when().patch(url).then().extract().response();
	}

	private Map<String, Object> postDuplicateBrand(int ownerId) {
		Map<String, Object> duplicateBrandMap = new HashMap<String, Object>();
		duplicateBrandMap.put("ownerId", ownerId);
		return duplicateBrandMap;
	}

	private Map<String, Object> postCreateBrandPayload(int orgId, String brandname) {
		Map<String, Object> CreateBrandMap = new HashMap<String, Object>();
		CreateBrandMap.put("orgId", orgId);
		CreateBrandMap.put("name", brandname);
		CreateBrandMap.put("showLogo", true);
		CreateBrandMap.put("color", "#aabbcc");
		return CreateBrandMap;
	}

	private Map<String, Object> errorpostCreateBrandPayload(Integer orgId, String brandname, Boolean showLogo,
			String color) {
		Map<String, Object> CreateBrandMap = new HashMap<String, Object>();
		CreateBrandMap.put("orgId", orgId);
		CreateBrandMap.put("name", brandname);
		CreateBrandMap.put("showLogo", showLogo);
		CreateBrandMap.put("color", color);
		return CreateBrandMap;
	}

	public Response postCreateBrand(int orgId, String brandname) {
		String url = ApiPath.CREATEBRANDS_DL;
		Map<String, Object> postCreateBrandPayload = postCreateBrandPayload(orgId, brandname);
		return given().spec(requestSpec).body(postCreateBrandPayload).when().post(url).then().extract().response();
	}

	public Response errorPostCreateBrand(Integer orgId, String brandname, Boolean showLogo, String color) {
		String url = ApiPath.CREATEBRANDS_DL;
		Map<String, Object> errorPostCreateBrand = errorpostCreateBrandPayload(orgId, brandname, showLogo, color);
		return given().spec(requestSpec).body(errorPostCreateBrand).when().post(url).then().extract().response();
	}

	public Response InvalidPostCreateBrand(int orgId, String brandname) {
		String url = ApiPath.INVALID_CREATE_BRANDS_DL;
		Map<String, Object> postCreateBrandPayload = postCreateBrandPayload(orgId, brandname);
		return given().spec(requestSpec).body(postCreateBrandPayload).when().post(url).then().extract().response();
	}

	public Response putUpdateBrand(int BrandId, int orgId, String brandname) {
		String url = ApiPath.UPDATE_BRAND_DL(BrandId);
		Map<String, Object> putUpdateBrandPayload = postCreateBrandPayload(orgId, brandname);
		return given().spec(requestSpec).body(putUpdateBrandPayload).when().put(url).then().extract().response();
	}

	public Response putUpdateAllBranddetails(int BrandId, Integer orgId, String brandname, Boolean showLogo,
			String color) {
		String url = ApiPath.UPDATE_BRAND_DL(BrandId);
		Map<String, Object> putUpdateBrandPayload = errorpostCreateBrandPayload(orgId, brandname, showLogo, color);
		return given().spec(requestSpec).body(putUpdateBrandPayload).when().put(url).then().extract().response();

	}

	public Response errorPutUpateBrand(int BrandId, Integer orgId, String brandname, Boolean showLogo, String color) {
		String url = ApiPath.UPDATE_BRAND_DL(BrandId);
		Map<String, Object> errorPutUpdateBrandPayload = errorpostCreateBrandPayload(orgId, brandname, showLogo, color);
		return given().spec(requestSpec).body(errorPutUpdateBrandPayload).when().put(url).then().extract().response();
	}

	public Response InvalidPutUpateBrand(int BrandId, int orgId, String brandname) {
		String url = ApiPath.Invalid_UPDATE_BRAND_DL(BrandId);
		Map<String, Object> putUpdateBrandPayload = postCreateBrandPayload(orgId, brandname);
		return given().spec(requestSpec).body(putUpdateBrandPayload).when().put(url).then().extract().response();
	}

	public Response fetchListOfBrands_DL(int orgId) {
		return given().spec(requestSpec).when().get(ApiPath.FETCH_LIST_OF_BRANDS_DL(orgId)).then().extract()
				.response();
	}
	
	public Response updateBrandDetails(String brandId, String orgId, String name, String showLogo, String color) {
		Map<String, Object> brandDetailsPayload = updateBrands(orgId, name, showLogo, color);
		String url = ApiPath.UPDATE_BRAND_DETAILS_BL(brandId);
		return given().spec(requestSpec).body(brandDetailsPayload).when().put(url).then().extract().response();
	}

	public Response updateBrandDetailsInvalidMethod(String brandId, String orgId, String name, String showLogo, String color) {
		Map<String, Object> brandDetailsPayload = updateBrands(orgId, name, showLogo, color);
		String url = ApiPath.UPDATE_BRAND_DETAILS_BL(brandId);
		return given().spec(requestSpec).body(brandDetailsPayload).when().post(url).then().extract().response();
	}

	public Response fetchListOfBrandsInvalidOrgId_DL(int orgId) {
		return given().spec(requestSpec).when().get(ApiPath.FETCH_LIST_OF_BRANDS_DL_INVALID_ORG(orgId)).then().extract()
				.response();
	}
	
	public Response fetchListOfBrandsInvalidHTTPMethod_DL(int orgId) {
		return given().spec(requestSpec).when().post(ApiPath.FETCH_LIST_OF_BRANDS_DL(orgId)).then().extract()
				.response();
	}
	
	public Response fetchListOfBrandsInvalidUrl_DL(int orgId) {
		return given().spec(requestSpec).when().get(ApiPath.FETCH_LIST_OF_BRANDS_INVALID_URL_DL(orgId)).then().extract()
				.response();
	}

	public Response updateBrandDetailsInvalidUrl(int brandId, String orgId, String name, String showLogo, String color) {
		Map<String, Object> brandDetailsPayload = updateBrands(orgId, name, showLogo, color);
		String url = ApiPath.INVALID_PATH_DELETE_BRAND_DETAILS_BL(brandId);
		return given().spec(requestSpec).body(brandDetailsPayload).when().put(url).then().extract().response();
	}

	private Map<String, Object> updateBrands(String orgId, String name, String showLogo, String color) {
		Map<String, Object> updateBrandDetailsMap = new HashMap<String, Object>();
		updateBrandDetailsMap.put("orgId", orgId);
		updateBrandDetailsMap.put("name", name);
		updateBrandDetailsMap.put("showLogo", showLogo);
		updateBrandDetailsMap.put("color", color);
		return updateBrandDetailsMap;
	}
	
	public Response createBrandingTemplate(String orgId, String name, String showLogo, String color) {
		Map<String, Object> createBrandPayload = updateBrands(orgId, name, showLogo, color);
        String url = ApiPath.CREATE_BRAND_BL;
	    return given().spec(requestSpec).body(createBrandPayload).when().post(url).then().extract()
					.response();
	}
			
	public Response createBrandingTemplateInvalidUrl_BL(String orgId, String name, String showLogo, String color) {
		Map<String, Object> createBrandPayload = updateBrands(orgId, name, showLogo, color);
        String url = ApiPath.INVALID_DUPLICATE_BRANDS_BL;
		return given().spec(requestSpec).body(createBrandPayload).when().post(url).then().extract().response();
	}

	public Response createBrandingTemplateInvalidMethodCall_BL(String orgId, String name, String showLogo, String color) {
		String url = ApiPath.CREATE_BRAND_BL;
		Map<String, Object> createBrandPayload = updateBrands(orgId, name, showLogo, color);
		return given().spec(requestSpec).body(createBrandPayload).when().put(url).then().extract().response();
	}
	
	public Response fetchListOfBrands_BL(int orgId) {
		return given().spec(requestSpec).when().get(ApiPath.FETCH_LIST_OF_BRANDS_BL(orgId)).then().extract()
				.response();
	}
	
	public Response fetchListOfBrandsInvalidOrgId_BL(int orgId) {
		return given().spec(requestSpec).when().get(ApiPath.FETCH_LIST_OF_BRANDS_BL_INVALID_ORG(orgId)).then().extract()
				.response();
	}
	
	public Response fetchListOfBrandsInvalidHTTPMethod_BL(int orgId) {
		return given().spec(requestSpec).when().post(ApiPath.FETCH_LIST_OF_BRANDS_BL(orgId)).then().extract()
				.response();
	}
	
	public Response fetchListOfBrandsInvalidUrl_BL(int orgId) {
		return given().spec(requestSpec).when().get(ApiPath.FETCH_LIST_OF_BRANDS_INVALID_URL_BL(orgId)).then().extract()
				.response();
	}
}
