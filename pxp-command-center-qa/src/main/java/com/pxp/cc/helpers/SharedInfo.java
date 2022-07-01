// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import io.restassured.response.Response;

public class SharedInfo extends BaseRest {
	public Response getBrandDetails(int brandId) {
		String url = ApiPath.BRAND_DETAILS_BL(brandId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getBrandDetailsUsingInvalidUrl(int brandId) {
		String url = ApiPath.INVALID_PATH_BRAND_DETAILS_BL(brandId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getProviderDetails(int providerId) {
		String url = ApiPath.PROVIDER_DETAILS_BL(providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getProviderDetailsInvalidUrl(int providerId) {
		String url = ApiPath.PROVIDER_DETAILS_INVALID_URL_BL(providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response invalidMethodCallGetProviderDetails(int providerId) {
		String url = ApiPath.PROVIDER_DETAILS_BL(providerId);
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response createProvider_DataLayer(int practiceId, String firstName, String lastName, String gender,
			String externalId) {
		String url = ApiPath.CREATE_PROVIDER_DL(practiceId);
		Map<String, Object> createProviderPayload = createProviderRequestPayload(firstName, lastName, gender,
				externalId);
		return given().spec(requestSpec).body(createProviderPayload).when().post(url).then().extract().response();
	}

	public Response createProviderUsingInvalidUrl_DataLayer(int practiceId, String firstName, String lastName,
			String gender, String externalId) {
		String url = ApiPath.INVALID_PATH_CREATE_PROVIDER_DL(practiceId);
		Map<String, Object> createProviderPayload = createProviderRequestPayload(firstName, lastName, gender,
				externalId);
		return given().spec(requestSpec).body(createProviderPayload).when().post(url).then().extract().response();
	}
	
	public Response createProviderUsingInvalidMethod_DataLayer(int practiceId, String firstName, String lastName, String gender,
			String externalId) {
		String url = ApiPath.CREATE_PROVIDER_DL(practiceId);
		Map<String, Object> createProviderPayload = createProviderRequestPayload(firstName, lastName, gender,
				externalId);
		return given().spec(requestSpec).body(createProviderPayload).when().put(url).then().extract().response();
	}
	private Map<String, Object> createProviderRequestPayload(String firstName, String lastName, String gender,
			String externalId) {
		Map<String, Object> createProviderMap = new HashMap<String, Object>();
		createProviderMap.put("firstName", firstName);
		createProviderMap.put("lastName", lastName);
		createProviderMap.put("gender", gender);
		createProviderMap.put("externalId", externalId);
		return createProviderMap;
	}

}
