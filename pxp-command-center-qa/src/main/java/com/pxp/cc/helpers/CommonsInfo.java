// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import io.restassured.response.Response;

public class CommonsInfo extends BaseRest {

	public Response getTimezones_BusinessLayer() throws IOException {
		String url = ApiPath.TIMEZONES_BL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response invalidMethodCall_BusinessLayer() throws IOException {
		String url = ApiPath.TIMEZONES_BL;
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response getTimezonesUsingInvalidUrl_BusinessLayerLayer() throws IOException {
		String url = ApiPath.INVALID_PATH_TIMEZONES_BL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getTimezones_DataLayer() throws IOException {
		String url = ApiPath.TIMEZONES_DL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response invalidMethodCall_DataLayer() throws IOException {
		String url = ApiPath.TIMEZONES_DL;
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response getTimezonesUsingInvalidUrl_DataLayer() throws IOException {
		String url = ApiPath.INVALID_PATH_TIMEZONES_DL;
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}
}
