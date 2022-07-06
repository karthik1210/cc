//Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import io.restassured.response.Response;

public class PracticeAppointmentTypeInfo extends BaseRest {

	public Response getResourcesForAppointmentType_DL(int practiceId, int appointmentTypeId) {
		String url = ApiPath.FETCH_RESOURCES_FOR_APPOINTMENTTYPE_DL(practiceId, appointmentTypeId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getResourcesForAppointmentType_DL_InvalidUrl(int practiceId, int appointmentTypeId) {
		String url = ApiPath.FETCH_RESOURCES_FOR_APPOINTMENTTYPE_DL_INVALID_URL(practiceId, appointmentTypeId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getResourcesForAppointmentType_DL_InvalidMethodCall(int practiceId, int appointmentTypeId) {
		String url = ApiPath.FETCH_RESOURCES_FOR_APPOINTMENTTYPE_DL(practiceId, appointmentTypeId);
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	public Response getResourcesForAppointmentTypeDL_NullValues() {
		String url = ApiPath.FETCH_RESOURCES_FOR_APPOINTMENTTYPE_DL_NULL_VALUES;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}
	
	public Response getSolutionsForAppointmentType_DL(int practiceId, int appointmentTypeId) {
		String url = ApiPath.FETCH_SOLUTIONS_FOR_APPOINTMENTTYPE_DL(practiceId, appointmentTypeId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getSolutionsForAppointmentType_DL_InvalidUrl(int practiceId, int appointmentTypeId) {
		String url = ApiPath.FETCH_SOLUTIONS_FOR_APPOINTMENTTYPE_DL_INVALID_URL(practiceId, appointmentTypeId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getSolutionsForAppointmentType_DL_InvalidMethodCall(int practiceId, int appointmentTypeId) {
		String url = ApiPath.FETCH_SOLUTIONS_FOR_APPOINTMENTTYPE_DL(practiceId, appointmentTypeId);
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	public Response getSolutionsForAppointmentTypeDL_NullValues() {
		String url = ApiPath.FETCH_SOLUTIONS_FOR_APPOINTMENTTYPE_DL_NULL_VALUES;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}
}
