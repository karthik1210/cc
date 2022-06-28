// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
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
}
