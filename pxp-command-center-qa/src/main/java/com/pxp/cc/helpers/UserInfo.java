// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import io.restassured.response.Response;

public class UserInfo extends BaseRest {

	public Response getUserRoles(String userType) {
		String url = ApiPath.GET_USER_ROLES_BL + "{userType}" + "/roles";
		return given().spec(requestSpec).pathParam("userType", userType).when().get(url).then().extract().response();
	}

	public Response getUserRolesUsingInvalidUrl(String userType) {
		String url = ApiPath.GET_USER_ROLES_BL + "{userType}" + "/rolesssss";
		return given().spec(requestSpec).pathParam("userType", userType).when().get(url).then().extract().response();
	}

}
