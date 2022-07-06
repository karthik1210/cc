// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import java.util.List;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import io.restassured.response.Response;

public class ClientDirectory extends BaseRest {

	List<String> list;

	public Response getClientFromClientDirectoryWithFilter(String searchData, String page, String pageSize,
			Object types) {
		return given().spec(requestSpec).queryParam("query", searchData).queryParams("type", types)
				.queryParam("page", page).queryParam("pageSize", pageSize).when().get(ApiPath.CLIENT_DIRECTORY_BL)
				.then().extract().response();
	}

	public Response getClientFromClientDirectoryWithoutFilter(String searchData, String page, String pageSize) {
		int pageValue = Integer.parseInt(page);
		int pageSizeValue = Integer.parseInt(pageSize);
		String url = ApiPath.CLIENT_DIRECTORY_BL;
		return given().spec(requestSpec).queryParam("query", searchData).queryParam("type")
				.queryParam("page", pageValue).queryParam("pageSize", pageSizeValue).when().get(url).then().extract()
				.response();
	}

	public Response selectClientAsFavorite(String clientId) {
		int clientID = Integer.parseInt(clientId);
		Log4jUtil.log("Favoriting client Id : " + clientID);
		return given().spec(requestSpec).when().post(ApiPath.ADD_FAVORITES(clientID)).then().extract().response();
	}

	public Response deleteFavoritesClient(String clientId) {
		int clientID = Integer.parseInt(clientId);
		Log4jUtil.log("DE Favoriting client Id : " + clientID);
		return given().spec(requestSpec).when().delete(ApiPath.DELETE_FAVORITE(clientID)).then().extract().response();
	}

	public Response getClientFromClientDirectoryWithFilter_DL(String searchData, String page, String pageSize,
			String pxp_cc_user, Object types) {

		return given().spec(requestSpec).queryParam("query", searchData).queryParams("type", types)
				.queryParam("page", page).queryParam("pageSize", pageSize).header(ApiPath.PXP_CC_USER, pxp_cc_user)
				.when().get(ApiPath.CLIENT_DIRECTORY_DL).then().extract().response();
	}

	public Response getClientFromClientDirectoryWithoutFilter_DL(String searchData, String page, String pageSize,
			String pxp_cc_user) {

		return given().spec(requestSpec).queryParam("query", searchData).queryParam("type", "").queryParam("page", page)
				.queryParam("pageSize", pageSize).header(ApiPath.PXP_CC_USER, pxp_cc_user).when()
				.get(ApiPath.CLIENT_DIRECTORY_DL).then().extract().response();
	}

	public Response selectClientFavorite(String clientId) {
		int clientID = Integer.parseInt(clientId);
		Log4jUtil.log("Favoriting client Id : " + clientID);
		return given().spec(requestSpec).when().post(ApiPath.FAVORITE(clientID)).then().extract().response();
	}

	public Response selectClientFavoriteWithInvalidUrl(String clientId) {
		int clientID = Integer.parseInt(clientId);
		Log4jUtil.log("Favoriting client Id : " + clientID);
		return given().spec(requestSpec).when().post(ApiPath.INVALID_PATH_FAVORITE(clientID)).then().extract()
				.response();
	}

	public Response deleteFavoriteClient(int clientId) {
		Log4jUtil.log("DE Favoriting client Id : " + clientId);
		return given().spec(requestSpec).when().put(ApiPath.FAVORITE(clientId)).then().extract().response();
	}

}
