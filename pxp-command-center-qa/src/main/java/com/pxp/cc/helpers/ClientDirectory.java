// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;

import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;

import io.restassured.response.Response;

public class ClientDirectory extends BaseRest {

	public Response getClientFromClientDirectoryWithFilter(String searchData, String page, String pageSize,
			Object types) {
		String url = ApiPath.GET_CLIENT_DIRECTORY;
		return given().spec(requestSpec).queryParam("query", searchData).queryParams("type", types)
				.queryParam("page", page).queryParam("pageSize", pageSize).when().get(url).then().extract().response();
	}

	public Response getClientFromClientDirectoryWithoutFilter(String searchData, String page, String pageSize) {
		int pageValue = Integer.parseInt(page);
		int pageSizeValue = Integer.parseInt(pageSize);
		String url = ApiPath.GET_CLIENT_DIRECTORY;
		return given().spec(requestSpec).queryParam("query", searchData).queryParam("type")
				.queryParam("page", pageValue).queryParam("pageSize", pageSizeValue).when().get(url).then().extract()
				.response();
	}

}
