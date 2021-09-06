package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;

import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;

import io.restassured.response.Response;

public class GeneralSettingsClientDropdown extends BaseRest {

	public Response getClientFavRecentVisitsDropdown(String clientID) {
		int clientId = Integer.parseInt(clientID);
		return given().spec(requestSpec).when().get(ApiPath.MENU_CLIENTS(clientId)).then().extract().response();
	}

}
