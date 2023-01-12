// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.AppointmentTypeDetails;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AppointmentTypeInfo extends BaseRest {

	public static RequestSpecification requestSpecification;

	public Response createAppointmentType_BL(int practiceId, String name, String externalId, String duration,
			String categoryId, String nameone, String externalIdone) {
		String url = ApiPath.CREATE_APPOINTMENT_TYPE_BL(practiceId);
		Map<String, Object> categoriesIdPayload = categoriesIdRequestPayload(categoryId, nameone, externalIdone);
		List<Object> categoriesPayload = categoriesRequestPayload(categoriesIdPayload);
		Map<String, Object> createAppointmentTypePayload = createAppointmentTypeRequestPayload(name, externalId,
				duration, categoriesPayload);

		return given().spec(requestSpec).body(createAppointmentTypePayload).when().post(url).then().extract()
				.response();
	}

	private List<Object> categoriesRequestPayload(Map<String, Object> categoriesIdPayload) {
		List<Object> Categories = new ArrayList<Object>();
		Categories.add(categoriesIdPayload);
		return Categories;
	}

	private Map<String, Object> categoriesIdRequestPayload(String categoryId, String nameone, String externalIdone) {
		Map<String, Object> categoriesMap = new HashMap<String, Object>();
		categoriesMap.put("categoryId", categoryId);
		categoriesMap.put("name", nameone);
		categoriesMap.put("externalId", externalIdone);
		return categoriesMap;
	}

	private Map<String, Object> createAppointmentTypeRequestPayload(String name, String externalId, String duration,
			List<Object> categoriesPayload) {
		Map<String, Object> createAppointmentTypeMap = new HashMap<String, Object>();
		createAppointmentTypeMap.put("name", name);
		createAppointmentTypeMap.put("externalId", externalId);
		createAppointmentTypeMap.put("duration", duration);
		createAppointmentTypeMap.put("categories", categoriesPayload);
		return createAppointmentTypeMap;
	}

	public Response getAppoinmentTypesAssociatedWithResources_BL(int practiceId, int resourceId) {
		String url = ApiPath.ADD_OR_REMOVE_APPOINTMENT_TYPES_FOR_RESOURCE_BL(resourceId, practiceId);
		return given().spec(requestSpec).when().get(url).then()
				.extract().response();
	}
	
	public Response getAppoinmentTypesAssociatedWithResourcesNullValues_BL() {
		String url = ApiPath.GET_APPOINTMENT_TYPES_FOR_RESOURCE_NULL_VALUE_BL();
		return given().spec(requestSpec).when().get(url).then()
				.extract().response();
	}
	
	public Response createAppointmentType_DL(int practiceId, String name, String externalId, String duration,
			String categoryId, String nameone, String externalIdone) {
		String url = ApiPath.CREATE_APPOINTMENT_TYPE_DL(practiceId);
		Map<String, Object> categoriesIdPayload = categoriesIdRequestPayload(categoryId, nameone, externalIdone);
		List<Object> categoriesPayload = categoriesRequestPayload(categoriesIdPayload);
		Map<String, Object> createAppointmentTypePayload = createAppointmentTypeRequestPayload(name, externalId,
				duration, categoriesPayload);

		return given().spec(requestSpec).body(createAppointmentTypePayload).when().post(url).then().extract()
				.response();
	}

}
