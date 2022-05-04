// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import io.restassured.response.Response;

public class UserInfo extends BaseRest {

	public Response getUserRoles(String userType) {
		String url = ApiPath.USER_ROLES_BL(userType);
		return given().spec(requestSpec).pathParam("userType", userType).when().get(url).then().extract().response();
	}

	public Response getUserRolesUsingInvalidUrl(String userType) {
		String url = ApiPath.INVALID_PATH_USER_ROLES_BL(userType);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getUserRoles_DataLayer(String userType) {
		String url = ApiPath.USER_ROLES_DL(userType);
		return given().spec(requestSpec).pathParam("userType", userType).when().get(url).then().extract().response();
	}

	public Response getUserRolesUsingInvalidUrl_DataLayer(String userType) {
		String url = ApiPath.INVALID_PATH_USER_ROLES_DL(userType);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getUserRolesByUser(int userId) {
		String url = ApiPath.USER_ROLES_BY_USER_BL(userId);
		return given().spec(requestSpec).pathParam("userId", userId).when().get(url).then().extract().response();
	}

	public Response getUserRolesByUserUsingInvalidUrl(int userId) {
		String url = ApiPath.INVALID_PATH_USER_ROLES_BY_USER_BL(userId);
		return given().spec(requestSpec).pathParam("userId", userId).when().get(url).then().extract().response();
	}

	public Response getUserRolesByUser_DataLayer(int userId) {
		String url = ApiPath.USER_ROLES_BY_USER_DL(userId);
		return given().spec(requestSpec).pathParam("userId", userId).when().get(url).then().extract().response();
	}

	public Response getUserRolesByUserUsingInvalidUrl_DataLayer(int userId) {
		String url = ApiPath.INVALID_PATH_USER_ROLES_BY_USER_DL(userId);
		return given().spec(requestSpec).pathParam("userId", userId).when().get(url).then().extract().response();
	}

	public Response validateUserDetails(String email, String username, String mobileNumber) {
		String url = ApiPath.VALIDATE_USER_BL();
		return given().spec(requestSpec).queryParam("email", email).queryParam("username", username)
				.queryParam("mobile", mobileNumber).when().get(url).then().extract().response();
	}

	public Response validateUserDetailsUsingInvalidUrl(String email, String username, String mobileNumber) {
		String url = ApiPath.INVALID_PATH_VALIDATE_USER_BL();
		return given().spec(requestSpec).queryParam("email", email).queryParam("username", username)
				.queryParam("mobile", mobileNumber).when().get(url).then().extract().response();
	}
}
