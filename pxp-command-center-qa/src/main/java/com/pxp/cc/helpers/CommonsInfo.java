// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;

import io.restassured.response.Response;

public class CommonsInfo extends BaseRest {

	public Response getTimezone() throws IOException {
		String url = ApiPath.GET_TIMEZONES;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getInvalidTimezone() throws IOException {
		String url = ApiPath.GET_TIMEZONES;
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response getInvalidTimezoneUrl() throws IOException {
		String url = ApiPath.GET_TIMEZONES.concat(CCUtils.generateAlphaNumericString(4));
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
