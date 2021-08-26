// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;

import java.util.List;

import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;

import io.restassured.response.Response;

public class ClientDirectory extends BaseRest {

	List<String> list;

	public Response getClientFromClientDirectoryWithFilter(String searchData, String page, String pageSize,
			Object types) {
		return given().spec(requestSpec).queryParam("query", searchData).queryParams("type", types)
				.queryParam("page", page).queryParam("pageSize", pageSize).when().get(ApiPath.GET_CLIENT_DIRECTORY)
				.then().extract().response();
	}

	public Response getClientFromClientDirectoryWithoutFilter(String searchData, String page, String pageSize) {
		int pageValue = Integer.parseInt(page);
		int pageSizeValue = Integer.parseInt(pageSize);
		String url = ApiPath.GET_CLIENT_DIRECTORY;
		return given().spec(requestSpec).queryParam("query", searchData).queryParam("type")
				.queryParam("page", pageValue).queryParam("pageSize", pageSizeValue).when().get(url).then().extract()
				.response();
	}

	public Response selectClientAsFavorite(String clientId) {
		int clientID = Integer.parseInt(clientId);
		return given().spec(requestSpec).when().post(ApiPath.ADD_FAVORITES(clientID)).then().extract().response();
	}

	public Response deleteFavoritesClient(String clientId) {
		int clientID = Integer.parseInt(clientId);
		return given().spec(requestSpec).when().delete(ApiPath.DELETE_FAVORITE(clientID)).then().extract().response();
	}

}
