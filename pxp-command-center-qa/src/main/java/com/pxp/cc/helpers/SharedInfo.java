// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.ResourceDetails;
import io.restassured.response.Response;

public class SharedInfo extends BaseRest {
	public Response getBrandDetails(int brandId) {
		String url = ApiPath.BRAND_DETAILS_BL(brandId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getBrandDetailsUsingInvalidUrl(int brandId) {
		String url = ApiPath.INVALID_PATH_BRAND_DETAILS_BL(brandId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getProviderDetails(int providerId) {
		String url = ApiPath.PROVIDER_DETAILS_BL(providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getProviderDetailsInvalidUrl(int providerId) {
		String url = ApiPath.PROVIDER_DETAILS_INVALID_URL_BL(providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response createProvider_DataLayer(int practiceId, String firstName, String lastName, String gender,
			String externalId) {
		String url = ApiPath.CREATE_PROVIDER_DL(practiceId);
		Map<String, Object> createProviderPayload = createProviderRequestPayload(firstName, lastName, gender,
				externalId);
		return given().spec(requestSpec).body(createProviderPayload).when().post(url).then().extract().response();
	}

	public Response createProviderUsingInvalidUrl_DataLayer(int practiceId, String firstName, String lastName,
			String gender, String externalId) {
		String url = ApiPath.INVALID_PATH_CREATE_PROVIDER_DL(practiceId);
		Map<String, Object> createProviderPayload = createProviderRequestPayload(firstName, lastName, gender,
				externalId);
		return given().spec(requestSpec).body(createProviderPayload).when().post(url).then().extract().response();
	}

	public Response createProviderUsingInvalidMethod_DataLayer(int practiceId, String firstName, String lastName,
			String gender, String externalId) {
		String url = ApiPath.CREATE_PROVIDER_DL(practiceId);
		Map<String, Object> createProviderPayload = createProviderRequestPayload(firstName, lastName, gender,
				externalId);
		return given().spec(requestSpec).body(createProviderPayload).when().put(url).then().extract().response();
	}

	private Map<String, Object> createProviderRequestPayload(String firstName, String lastName, String gender,
			String externalId) {
		Map<String, Object> createProviderMap = new HashMap<String, Object>();
		createProviderMap.put("firstName", firstName);
		createProviderMap.put("lastName", lastName);
		createProviderMap.put("gender", gender);
		createProviderMap.put("externalId", externalId);
		return createProviderMap;
	}

	public Response getProviderDetails_DL(int providerId) {
		String url = ApiPath.PROVIDER_DETAILS_DL(providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getProviderDetailsInvalidUrl_DL(int providerId) {
		String url = ApiPath.PROVIDER_DETAILS_INVALID_URL_DL(providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response invalidMethodCallGetProviderDetails_DL(int providerId) {
		String url = ApiPath.PROVIDER_DETAILS_DL(providerId);
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response addOrRemoveResourcesForAppoinmentType(int practiceId, int appointmentTypeId,
			List<ResourceDetails> resourceList) {
		String url = ApiPath.ADD_OR_REMOVE_RESOURCES_FOR_APPOINTMENT_TYPE(practiceId, appointmentTypeId);
		return given().spec(requestSpec).body(resourceList).when().put(url).then().extract().response();
	}

	public Response invalidMethodCallForAddOrRemoveResourcesForAppoinmentType(int practiceId, int appointmentTypeId,
			List<ResourceDetails> resourceList) {
		String url = ApiPath.ADD_OR_REMOVE_RESOURCES_FOR_APPOINTMENT_TYPE(practiceId, appointmentTypeId);
		return given().spec(requestSpec).body(resourceList).when().post(url).then().extract().response();
	}

	public Response addOrRemoveResourcesForAppoinmentTypeUsingInvalidUrl(int practiceId, int appointmentTypeId,
			List<ResourceDetails> resourceList) {
		String url = ApiPath.INVALID_PATH_ADD_OR_REMOVE_RESOURCES_FOR_APPOINTMENT_TYPE(practiceId, appointmentTypeId);
		return given().spec(requestSpec).body(resourceList).when().post(url).then().extract().response();

	}

	public Response getPracticesAssociatedToProvider(int providerId) {
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_PROVIDER_BL(providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticesAssociatedToProviderInvalidUrl(int providerId) {
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_PROVIDER_INVALID_URL_BL(providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticesAssociatedToProviderInvalidMethodCall(int providerId) {
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_PROVIDER_BL(providerId);
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	public Response getPracticesAssociatedToProvider_NullValues() {
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_PROVIDER_NULL_VALUES_BL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticesAssociatedToProvider_DL(int providerId) {
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_PROVIDER_DL(providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticesAssociatedToProviderInvalidUrl_DL(int providerId) {
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_PROVIDER_INVALID_URL_DL(providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticesAssociatedToProviderInvalidMethodCall_DL(int providerId) {
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_PROVIDER_DL(providerId);
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	public Response getBrandDetails_DL(int brandId) {
		String url = ApiPath.BRAND_DETAILS_DL(brandId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getBrandDetailsUsingInvalidUrl_DL(int brandId) {
		String url = ApiPath.INVALID_PATH_BRAND_DETAILS_DL(brandId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response updateProviderDetails(int providerId, String practiceId, String active) {
		Map<String, Object> providerDetailsPayload = updateProvider(practiceId, active);
		String url = ApiPath.UPDATE_PROVIDER_DETAILS(providerId);
		return given().spec(requestSpec).body(providerDetailsPayload).when().put(url).then().extract().response();
	}

	public Response updateProviderDetailsInvalidMethod(int providerId, String practiceId, String active) {
		Map<String, Object> providerDetailsPayload = updateProvider(practiceId, active);
		String url = ApiPath.UPDATE_PROVIDER_DETAILS(providerId);
		return given().spec(requestSpec).body(providerDetailsPayload).when().post(url).then().extract().response();
	}

	public Response updateProviderDetailsInvalidUrl(int providerId, String practiceId, String active) {
		Map<String, Object> providerDetailsPayload = updateProvider(practiceId, active);
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_PROVIDER_INVALID_URL_DL(providerId);
		return given().spec(requestSpec).body(providerDetailsPayload).when().put(url).then().extract().response();
	}

	private Map<String, Object> updateProvider(String practiceId, String active) {
		Map<String, Object> updateProviderDetailsMap = new HashMap<String, Object>();
		updateProviderDetailsMap.put("practiceId", practiceId);
		updateProviderDetailsMap.put("active", active);
		return updateProviderDetailsMap;
	}
	
	public Response deleteBrandDetails(int brandId) {
		String url = ApiPath.DELETE_BRAND_DETAILS_BL(brandId);
		return given().spec(requestSpec).when().delete(url).then().extract().response();
	}

	public Response deleteBrandDetailsUsingInvalidUrl(int brandId) {
		String url = ApiPath.INVALID_PATH_DELETE_BRAND_DETAILS_BL(brandId);
		return given().spec(requestSpec).when().delete(url).then().extract().response();
	}

	public Response getAppointmentType_DL(int appointmentTypeId) {
		String url = ApiPath.GET_APPOINTMENT_TYPE_DL(appointmentTypeId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response invalidMethodCallForGetAppointmentType_DL(int appointmentTypeId) {
		String url = ApiPath.GET_APPOINTMENT_TYPE_DL(appointmentTypeId);
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	public Response getAppointmentTypeUsingInvalidUrl_DL(int appointmentTypeId) {
		String url = ApiPath.INVALID_PATH_FOR_GET_APPOINTMENT_TYPE_DL(appointmentTypeId);
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	public Response getResourcesForProviderWithValidInputs(int providerId, int practiceId, String urlType) {
		String url = ApiPath.FETCH_RESOURCES_FOR_PROVIDERS_VALID_URL(providerId, practiceId);
		String invalidUrl = ApiPath.FETCH_RESOURCES_FOR_PROVIDERS_INVALID_URL(providerId, practiceId);
		if (urlType.equalsIgnoreCase("valid"))
			return given().spec(requestSpec).when().get(url).then().extract().response();
		else
			return given().spec(requestSpec).when().get(invalidUrl).then().extract().response();
	}

	public Response getResourceForProviderWithInvalidPOSTRequest(int providerId, int practiceId, String urlType) {
		String url = ApiPath.FETCH_RESOURCES_FOR_PROVIDERS_VALID_URL(providerId, practiceId);
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	public Response getResourcesForProviderWithInvalidDataType(String providerId, String practiceId, String urlType) {
		String url = ApiPath.FETCH_RESOURCES_FOR_PROVIDERS_WITH_INVALID_DATATYPE(providerId, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getResourcesForProviderWithValidInputs_DL(int providerId, int practiceId, String urlType) {
		String url = ApiPath.FETCH_RESOURCE_FOR_PROVIDER_VALID_URL_DL(providerId, practiceId);
		String invalidUrl = ApiPath.FETCH_RESOURCE_FOR_PROVIDER_INVALID_URL_DL(providerId, practiceId);
		if (urlType.equalsIgnoreCase("valid"))
			return given().spec(requestSpec).when().get(url).then().extract().response();
		else
			return given().spec(requestSpec).when().get(invalidUrl).then().extract().response();
	}

	public Response getResourceForProviderWithInvalidPOSTRequest_DL(int providerId, int practiceId, String urlType) {
		String url = ApiPath.FETCH_RESOURCE_FOR_PROVIDER_VALID_URL_DL(providerId, practiceId);
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	public Response getResourcesForProviderWithValidInputs_DL(String providerId, String practiceId, String urlType) {
		String url = ApiPath.FETCH_RESOURCE_FOR_PROVIDER_VALID_URL_DL(providerId, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

}
