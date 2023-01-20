// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AppointmentTypeInfo extends BaseRest {

	public static RequestSpecification requestSpecification;

	public Response createAppointmentType_BL(int practiceId, String externalsystem, String name, String externalId,
			String duration, String categoryId, String nameone, String externalIdone) {
		String url = ApiPath.CREATE_APPOINTMENT_TYPE_BL(practiceId);
		Map<String, Object> categoriesIdPayload = categoriesIdRequestPayload(categoryId, nameone, externalIdone);
		List<Object> categoriesPayload = categoriesRequestPayload(categoriesIdPayload);
		Map<String, Object> createAppointmentTypePayload = createAppointmentTypeRequestPayload(name, externalId,
				duration, categoriesPayload);
		return given().spec(requestSpec).header("x-external-system", externalsystem).body(createAppointmentTypePayload)
				.when().post(url).then().extract().response();
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
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getAppoinmentTypesAssociatedWithResourcesNullValues_BL() {
		String url = ApiPath.GET_APPOINTMENT_TYPES_FOR_RESOURCE_NULL_VALUE_BL();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response createAppointmentType_DL(int practiceId, String externalsystem, String name, String externalId,
			String duration, String categoryId, String nameone, String externalIdone) {
		String url = ApiPath.CREATE_APPOINTMENT_TYPE_DL(practiceId);
		Map<String, Object> categoriesIdPayload = categoriesIdRequestPayload(categoryId, nameone, externalIdone);
		List<Object> categoriesPayload = categoriesRequestPayload(categoriesIdPayload);
		Map<String, Object> createAppointmentTypePayload = createAppointmentTypeRequestPayload(name, externalId,
				duration, categoriesPayload);
		return given().spec(requestSpec).header("x-external-system", externalsystem).body(createAppointmentTypePayload)
				.when().post(url).then().extract().response();
	}

	public Response enableOrDisableSolutionsForAppTypes_BL(int appointmentTypeId, int practiceId, int solutionId,
			boolean status) {
		String url = ApiPath.SOLUTIONS_FOR_APPOINTMENT_TYPE_BL(appointmentTypeId, practiceId);
		Map<String, Object> solutionsIdPayload = solutionIdRequestPayload(solutionId, status);
		List<Object> solutionsPayload = solutionsRequestPayload(solutionsIdPayload);
		return given().spec(requestSpec).body(solutionsPayload).when().put(url).then().extract().response();
	}

	private List<Object> solutionsRequestPayload(Map<String, Object> solutionsPayload) {
		List<Object> solutions = new ArrayList<Object>();
		solutions.add(solutionsPayload);
		return solutions;
	}

	private Map<String, Object> solutionIdRequestPayload(int solutionId, boolean active) {
		Map<String, Object> solutionsMap = new HashMap<String, Object>();
		solutionsMap.put("solutionId", solutionId);
		solutionsMap.put("active", active);
		return solutionsMap;
	}

	public Response enableOrDisableSolutionsForAppTypes_NullValues_BL(int solutionId, boolean status) {
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_APPOINTMENT_TYPE_USING_NULL_VALUE_BL();
		Map<String, Object> solutionsIdPayload = solutionIdRequestPayload(solutionId, status);
		List<Object> solutionsPayload = solutionsRequestPayload(solutionsIdPayload);
		return given().spec(requestSpec).body(solutionsPayload).when().put(url).then().extract().response();
	}

	public Response getSolutionsForAppTypes_BL(int appointmentTypeId, int practiceId) {
		String url = ApiPath.SOLUTIONS_FOR_APPOINTMENT_TYPE_BL(appointmentTypeId, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getSolutionsForAppTypesNullValues_BL() {
		String url = ApiPath.GET_SOLUTIONS_FOR_APPOINTMENT_TYPE_NULL_VALUES_BL();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response enableDisableAppType_BL(int practiceId, int appointmentTypeId, String active) {
		Map<String, Object> enableDisablePayload = enableDisableAppType(active);
		String url = ApiPath.ENABLE_DISABLE_APP_TYPES_BL(practiceId, appointmentTypeId);
		return given().spec(requestSpec).body(enableDisablePayload).when().patch(url).then().extract().response();
	}

	private Map<String, Object> enableDisableAppType(String active) {
		Map<String, Object> enableDisablePayload = new HashMap<String, Object>();
		enableDisablePayload.put("active", active);
		return enableDisablePayload;
	}

	public Response enableDisableAppTypes_NullValues_BL() {
		String url = ApiPath.ENABLE_DISABLE_APP_TYPES_NULL_VALUES_BL();
		return given().spec(requestSpec).when().patch(url).then().extract().response();
	}

	public Response enableDisableAppType_DL(int practiceId, int appointmentTypeId, String active) {
		Map<String, Object> enableDisablePayload = enableDisableAppType(active);
		String url = ApiPath.ENABLE_DISABLE_APP_TYPES_DL(practiceId, appointmentTypeId);
		return given().spec(requestSpec).body(enableDisablePayload).when().patch(url).then().extract().response();
	}
	
	public Response enableDisableAppTypes_NullValues_DL() {
		String url = ApiPath.ENABLE_DISABLE_APP_TYPES_NULL_VALUES_DL();
		return given().spec(requestSpec).when().patch(url).then().extract().response();
	}
}
