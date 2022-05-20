// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import io.restassured.response.Response;

public class Product extends BaseRest {
	public Response getProducts(String ecoSystem, String type) throws IOException {
		String url = ApiPath.PRODUCTS_BL;
		return given().spec(requestSpec).queryParam("ecosystem", ecoSystem).queryParam("type", type).when().get(url)
				.then().extract().response();
	}

	public Response invalidMethodForFetchProductList(String ecoSystem, String type) throws IOException {
		String url = ApiPath.INVALID_PATH_PRODUCTS_BL;
		return given().spec(requestSpec).queryParam("ecosystem", ecoSystem).queryParam("type", type).when().put(url)
				.then().extract().response();
	}

	public Response invalidMethodCall(String ecoSystem, String type) throws IOException {
		String url = ApiPath.PRODUCTS_BL;
		return given().spec(requestSpec).queryParam("ecosystem", ecoSystem).queryParam("type", type).when().put(url)
				.then().extract().response();
	}

}
