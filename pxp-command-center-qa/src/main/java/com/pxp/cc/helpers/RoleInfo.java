// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import io.restassured.response.Response;

public class RoleInfo extends BaseRest {
	public Response getUserRolesByUserType(String userType) {
		String url = ApiPath.USER_ROLES_BY_USERTYPE_BL(userType);
		return given().spec(requestSpec).pathParam("userType", userType).when().get(url).then().extract().response();
	}

	public Response getUserRolesByUserTypeUsingInvalidUrl(String userType) {
		String url = ApiPath.INVALID_PATH_USER_ROLES_BY_USERTYPE_BL(userType);
		return given().spec(requestSpec).pathParam("userType", userType).when().get(url).then().extract().response();
	}

}
