// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import io.restassured.response.Response;

public class Timezone extends BaseRest {

	public Response getTimezone() throws IOException {
		String url = ApiPath.GET_TIMEZONES;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getInvalidTimezone() throws IOException {
		String url = ApiPath.GET_TIMEZONES;
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}
}
