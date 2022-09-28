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

	public Response getProducts_DataLayer(String ecoSystem, String type) throws IOException {
		String url = ApiPath.PRODUCTS_DL;
		return given().spec(requestSpec).queryParam("ecosystem", ecoSystem).queryParam("type", type).when().get(url)
				.then().extract().response();
	}

	public Response invalidMethodForFetchProductList_DataLayer(String ecoSystem, String type) throws IOException {
		String url = ApiPath.INVALID_PATH_PRODUCTS_DL;
		return given().spec(requestSpec).queryParam("ecosystem", ecoSystem).queryParam("type", type).when().put(url)
				.then().extract().response();
	}

	public Response invalidMethodCall_DataLayer(String ecoSystem, String type) throws IOException {
		String url = ApiPath.PRODUCTS_DL;
		return given().spec(requestSpec).queryParam("ecosystem", ecoSystem).queryParam("type", type).when().put(url)
				.then().extract().response();
	}

	public Response getExternalSystemMetaDataResponse(String validURI, String serviceLayer, int externalSystemId) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.GET_EXTERNAL_SYSTEMID(serviceLayer, externalSystemId);
			return given().spec(requestSpec).when().get(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.GET_EXTERNAL_SYSTEMID_WITH_INVLAID_URI(serviceLayer, externalSystemId);
			return given().spec(requestSpec).when().get(url).then().extract().response();
		} else {
			String url = ApiPath.GET_EXTERNAL_SYSTEMID(serviceLayer, externalSystemId);
			return given().spec(requestSpec).when().patch(url).then().extract().response();
		}
	}

	public Response getExternalSystemMetaDataInvaliDatatypeResponse(String serviceLayer, String externalSystemId) {
		String url = ApiPath.GET_EXTERNAL_SYSTEMID_WITH_INVLAID_DATATYPE(serviceLayer, externalSystemId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

}
