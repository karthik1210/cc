// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

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
import com.pxp.cc.pojos.Locations;
import com.pxp.cc.pojos.ResourceDetails;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SharedInfo extends BaseRest {
	public static RequestSpecification requestSpecification;

	public Response getBrandDetails(int brandId) {
		String url = ApiPath.BRAND_DETAILS_BL(brandId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getBrandDetailsUsingInvalidUrl(int brandId) {
		String url = ApiPath.INVALID_PATH_BRAND_DETAILS_BL(brandId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getProviderDetails(int clientId, int providerId) {
		String url = ApiPath.GET_PROVIDER_DETAILS_BL(clientId, providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getProviderDetailsInvalidUrl(int providerId) {
		String url = ApiPath.PROVIDER_DETAILS_INVALID_URL_BL(providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response createProvider_DataLayer(int practiceId, String firstName, String lastName, String gender,
			String externalId, String credentials) {
		String url = ApiPath.CREATE_PROVIDER_DL(practiceId);
		Map<String, Object> createProviderPayload = createProviderRequestPayload(firstName, lastName, gender,
				externalId, credentials);
		return given().spec(requestSpec).body(createProviderPayload).when().post(url).then().extract().response();
	}

	public Response createProviderUsingInvalidUrl_DataLayer(int practiceId, String firstName, String lastName,
			String gender, String externalId, String credentials) {
		String url = ApiPath.INVALID_PATH_CREATE_PROVIDER_DL(practiceId);
		Map<String, Object> createProviderPayload = createProviderRequestPayload(firstName, lastName, gender,
				externalId, credentials);
		return given().spec(requestSpec).body(createProviderPayload).when().post(url).then().extract().response();
	}

	public Response createProviderUsingInvalidMethod_DataLayer(int practiceId, String firstName, String lastName,
			String gender, String externalId, String credentials) {
		String url = ApiPath.CREATE_PROVIDER_DL(practiceId);
		Map<String, Object> createProviderPayload = createProviderRequestPayload(firstName, lastName, gender,
				externalId, credentials);
		return given().spec(requestSpec).body(createProviderPayload).when().put(url).then().extract().response();
	}

	private Map<String, Object> createProviderRequestPayload(String firstName, String lastName, String gender,
			String externalId, String credentials) {
		Map<String, Object> createProviderMap = new HashMap<String, Object>();
		createProviderMap.put("firstName", firstName);
		createProviderMap.put("lastName", lastName);
		createProviderMap.put("gender", gender);
		createProviderMap.put("externalId", externalId);
		createProviderMap.put("credentials", credentials);
		return createProviderMap;
	}

	public Response getProviderDetails_DL(int clientId, int providerId) {
		String url = ApiPath.PROVIDER_DETAILS_DL(clientId, providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getProviderDetailsInvalidUrl_DL(int providerId) {
		String url = ApiPath.PROVIDER_DETAILS_INVALID_URL_DL(providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
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

	public Response deleteBrandDetails_DL(int brandId) {
		String url = ApiPath.DELETE_BRAND_DETAILS_DL(brandId);
		return given().spec(requestSpec).when().delete(url).then().extract().response();
	}

	public Response deleteBrandDetailsUsingInvalidUrl_DL(int brandId) {
		String url = ApiPath.INVALID_PATH_DELETE_BRAND_DETAILS_DL(brandId);
		return given().spec(requestSpec).when().delete(url).then().extract().response();
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

	public Response getResourcesForProviderWithValidInputs_DL_Invalid_DataType(String providerId, String practiceId,
			String urlType) {
		String url = ApiPath.FETCH_RESOURCE_FOR_PROVIDER_VALID_URL_DL_INVALID_DATATYPE(providerId, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getBrandList_DL(int orgId) {
		String url = ApiPath.BRAND_LIST_DL(orgId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response associateDeassociateBrand_DL(int orgId, int brandId, boolean active) {
		String url = ApiPath.BRAND_LIST_DL(orgId);
		Map<String, Object> associateDeassociateBrandPayload = associateDeassociateBrandTemplate(brandId, active);
		return given().spec(requestSpec).body(associateDeassociateBrandPayload).when().put(url).then().extract()
				.response();
	}

	private Map<String, Object> associateDeassociateBrandTemplate(Integer brandId, Boolean active) {
		Map<String, Object> associateDeassociateBrandMap = new HashMap<String, Object>();
		associateDeassociateBrandMap.put("brandId", brandId);
		associateDeassociateBrandMap.put("active", active);
		return associateDeassociateBrandMap;
	}

	public Response associateDeassociateBrandUsingEmptyPayload_DL(int orgId) {
		String url = ApiPath.BRAND_LIST_DL(orgId);
		Map<String, Object> associateDeassociateBrandPayload = new HashMap<String, Object>();
		return given().spec(requestSpec).body(associateDeassociateBrandPayload).when().put(url).then().extract()
				.response();
	}

	public Response associateDeassociateBrandUsingInvalidMethodCall_DL(int orgId, int brandId, boolean active) {
		String url = ApiPath.BRAND_LIST_DL(orgId);
		Map<String, Object> associateDeassociateBrandPayload = associateDeassociateBrandTemplate(brandId, active);
		return given().spec(requestSpec).body(associateDeassociateBrandPayload).when().post(url).then().extract()
				.response();
	}

	public Response associateDeassociateBrandUsingInvalidUrl_DL(int orgId, int brandId, boolean active) {
		String url = ApiPath.INVALID_PATH_BRAND_LIST_DL(orgId);
		Map<String, Object> associateDeassociateBrandPayload = associateDeassociateBrandTemplate(brandId, active);
		return given().spec(requestSpec).body(associateDeassociateBrandPayload).when().post(url).then().extract()
				.response();
	}

	public Response getAppointmentTypes_BL(int appointmentTypeId) {
		String url = ApiPath.GET_APPOINTMENT_TYPES_BL(appointmentTypeId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getAppointmentTypesInvalidMethodCall_BL(int appointmentTypeId) {
		String url = ApiPath.GET_APPOINTMENT_TYPES_BL(appointmentTypeId);
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	public Response getAppointmentTypesInvalidUrl_BL(int appointmentTypeId) {
		String url = ApiPath.GET_APPOINTMENT_TYPES_INVALID_PATH_BL(appointmentTypeId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getAppointmentTypes_NullValues() {
		String url = ApiPath.GET_APPOINTMENT_TYPES_BL_NULL_VALUES;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getResourceDetails_DL(int resourceId) {
		String url = ApiPath.GET_RESOURCE_DETAILS_DL(resourceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response deleteResourceImage_DL(int resourceId) {
		String url = ApiPath.RESOURCE_IMAGE_DL(resourceId);
		return given().spec(requestSpec).when().delete(url).then().extract().response();
	}

	public Response addResourceImage_DL(int resourceId, String imageUrl) {
		String url = ApiPath.RESOURCE_IMAGE_DL(resourceId);
		return given().spec(requestSpec).body(addImageUrlPayload(imageUrl)).when().patch(url).then().extract()
				.response();
	}

	private Map<String, Object> addImageUrlPayload(String imageUrl) {
		Map<String, Object> addImageUrlMap = new HashMap<String, Object>();
		addImageUrlMap.put("href", imageUrl);
		return addImageUrlMap;
	}

	public Response addResourceImageWithEmptyPayload_DL(int resourceId) {
		String url = ApiPath.RESOURCE_IMAGE_DL(resourceId);
		Map<String, Object> addImageUrlMap = new HashMap<String, Object>();
		return given().spec(requestSpec).body(addImageUrlMap).when().patch(url).then().extract().response();
	}

	public Response addResourceImageUsingInvalidMethodCall_DL(int resourceId, String imageUrl) {
		String url = ApiPath.RESOURCE_IMAGE_DL(resourceId);
		return given().spec(requestSpec).body(addImageUrlPayload(imageUrl)).when().post(url).then().extract()
				.response();
	}

	public Response addResourceImageUsingInvalidUrl_DL(int resourceId, String imageUrl) {
		String url = ApiPath.INVALID_PATH_RESOURCE_IMAGE_DL(resourceId);
		return given().spec(requestSpec).body(addImageUrlPayload(imageUrl)).when().patch(url).then().extract()
				.response();
	}

	private Map<String, Object> createPracticeresourceRequestPayload(String en, String es) {
		Map<String, Object> createPracticesMap = new HashMap<String, Object>();
		createPracticesMap.put("en", en);
		createPracticesMap.put("es", es);
		return createPracticesMap;
	}

	public Response savePracticeResourceWithValidInputs(int practiceId, int resourceId, String en, String es) {
		String url = ApiPath.UPDATE_PRACTICE_AND_RESOURCES_DL_VALID_URL(practiceId, resourceId);
		Map<String, Object> createPracticeresourcePayload = createPracticeresourceRequestPayload(en, es);
		return given().spec(requestSpec).body(createPracticeresourcePayload).when().put(url).then().extract()
				.response();
	}

	public Response savePracticeResourceWithInValidInputs(int practiceId, int resourceId, String en, String es) {
		String url = ApiPath.UPDATE_PRACTICE_AND_RESOURCES_DL_VALID_URL(practiceId, resourceId);
		Map<String, Object> createPracticeresourcePayload = createPracticeresourceRequestPayload(en, es);
		return given().spec(requestSpec).body(createPracticeresourcePayload).when().put(url).then().extract()
				.response();
	}

	public Response savePracticeResourceWithInValidMethodcall(int practiceId, int resourceId, String en, String es) {
		String url = ApiPath.UPDATE_PRACTICE_AND_RESOURCES_DL_VALID_URL(practiceId, resourceId);
		Map<String, Object> createPracticeresourcePayload = createPracticeresourceRequestPayload(en, es);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response savePracticeResourceWithINValidDATATYPE(String practiceId, String resourceId, String en,
			String es) {
		String url = ApiPath.UPDATE_PRACTICE_AND_RESOURCES_DL_INVALID_DATATYPE(practiceId, resourceId);
		Map<String, Object> createPracticeresourcePayload = createPracticeresourceRequestPayload(en, es);
		return given().spec(requestSpec).body(createPracticeresourcePayload).when().put(url).then().extract()
				.response();
	}

	public Response getPracticeForLocationWithExistingOrNonExistingInput(int locationId) {
		String url = ApiPath.FETCH_PRACTICE_FOR_LOCATIONS_DL_VALID_URL(locationId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticeForLocationWithInvalidInput(int locationId) {
		String url = ApiPath.FETCH_PRACTICE_FOR_LOCATIONS_DL_INVALID_URL(locationId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticeForLocationWithValidInputForPost(int locationId) {
		String url = ApiPath.FETCH_PRACTICE_FOR_LOCATIONS_DL_VALID_URL(locationId);
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	public Response associateDeassociateBrand_BL(int orgId, int brandId, boolean active) {
		String url = ApiPath.BRAND_LIST_BL(orgId);
		Map<String, Object> associateDeassociateBrandPayload = associateDeassociateBrandTemplate(brandId, active);
		return given().spec(requestSpec).body(associateDeassociateBrandPayload).when().put(url).then().extract()
				.response();
	}

	public Response associateDeassociateBrandUsingEmptyPayload_BL(int orgId) {
		String url = ApiPath.BRAND_LIST_BL(orgId);
		Map<String, Object> associateDeassociateBrandPayload = new HashMap<String, Object>();
		return given().spec(requestSpec).body(associateDeassociateBrandPayload).when().put(url).then().extract()
				.response();
	}

	public Response associateDeassociateBrandUsingInvalidMethodCall_BL(int orgId, int brandId, boolean active) {
		String url = ApiPath.BRAND_LIST_BL(orgId);
		Map<String, Object> associateDeassociateBrandPayload = associateDeassociateBrandTemplate(brandId, active);
		return given().spec(requestSpec).body(associateDeassociateBrandPayload).when().post(url).then().extract()
				.response();
	}

	public Response associateDeassociateBrandUsingInvalidUrl_BL(int orgId, int brandId, boolean active) {
		String url = ApiPath.INVALID_PATH_BRAND_LIST_BL(orgId);
		Map<String, Object> associateDeassociateBrandPayload = associateDeassociateBrandTemplate(brandId, active);
		return given().spec(requestSpec).body(associateDeassociateBrandPayload).when().post(url).then().extract()
				.response();
	}

	public Response getBrandList_BL(int orgId) {
		String url = ApiPath.BRAND_LIST_BL(orgId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response createProvider_BL(int practiceId, String firstName, String lastName, String gender,
			String externalId, String credentials) {
		String url = ApiPath.CREATE_PROVIDER_BL(practiceId);
		Map<String, Object> createProviderPayload = createProviderRequestPayload(firstName, lastName, gender,
				externalId, credentials);
		return given().spec(requestSpec).body(createProviderPayload).when().post(url).then().extract().response();
	}

	private Map<String, Object> savePracticeProviderApiPayload(Map<String, Object> displayName, String email,
			String mobile) {
		Map<String, Object> savePracticeProviderMap = new HashMap<String, Object>();
		savePracticeProviderMap.put("displayName", displayName);
		savePracticeProviderMap.put("email", email);
		savePracticeProviderMap.put("mobile", mobile);
		return savePracticeProviderMap;
	}

	public Response savePracticeProvider_BL(int practiceId, int providerId, String en, String es, String email,
			String mobile) {
		String url = ApiPath.SAVE_PRACTICE_PROVIDER_BL(practiceId, providerId);
		Map<String, Object> createPracticeresourcePayload = createPracticeresourceRequestPayload(en, es);
		Map<String, Object> savePracticeProviderPayload = savePracticeProviderApiPayload(createPracticeresourcePayload,
				email, mobile);
		return given().spec(requestSpec).body(savePracticeProviderPayload).when().put(url).then().extract().response();
	}

	public Response savePracticeProviderUsingInvalidUrl_BL(int practiceId, int providerId, String en, String es,
			String email, String mobile) {
		String url = ApiPath.INVALID_PATH_SAVE_PRACTICE_PROVIDER_BL(practiceId, providerId);
		Map<String, Object> createPracticeresourcePayload = createPracticeresourceRequestPayload(en, es);
		Map<String, Object> savePracticeProviderPayload = savePracticeProviderApiPayload(createPracticeresourcePayload,
				email, mobile);
		return given().spec(requestSpec).body(savePracticeProviderPayload).when().put(url).then().extract().response();
	}

	public Response savePracticeProviderUsingInvalidMethod_BL(int practiceId, int providerId, String en, String es,
			String email, String mobile) {
		String url = ApiPath.SAVE_PRACTICE_PROVIDER_BL(practiceId, providerId);
		Map<String, Object> createPracticeresourcePayload = createPracticeresourceRequestPayload(en, es);
		Map<String, Object> savePracticeProviderPayload = savePracticeProviderApiPayload(createPracticeresourcePayload,
				email, mobile);
		return given().spec(requestSpec).body(savePracticeProviderPayload).when().post(url).then().extract().response();
	}

	public Response getPracticeProviderDetails_BL(int practiceId, int providerId) {
		String url = ApiPath.GET_PRACTICE_PROVIDER_BL(practiceId, providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response addOrRemoveLocationForProvider(int providerId, int practiceId, List<Locations> location) {
		String url = ApiPath.UPDATE_LOCATION_FOR_PROVIDER_DL(providerId, practiceId);
		return given().spec(requestSpec).body(location).when().put(url).then().extract().response();
	}

	public Response invalidMethodCallForaddOrRemoveLocationForProvider(int providerId, int practiceId,
			List<Locations> location) {
		String url = ApiPath.UPDATE_LOCATION_FOR_PROVIDER_DL(providerId, practiceId);
		return given().spec(requestSpec).body(location).when().post(url).then().extract().response();
	}

	public Response addOrRemoveLocationForProvider_InvalidUrl(int providerId, int practiceId,
			List<Locations> location) {
		String url = ApiPath.UPDATE_LOCATION_FOR_PROVIDER_INVALID_URL_DL(providerId, practiceId);
		return given().spec(requestSpec).body(location).when().put(url).then().extract().response();
	}

	public Response getLocationsForProviderForNullValues() {
		String url = ApiPath.UPDATE_LOCATION_FOR_PROVIDER__NULL_VALUE_DL();
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response enableOrDisableSolutionsForResource_DL(int resourceId, int practiceId, int solutionId,
			boolean active) {
		Map<String, Object> EnableOrDisableSolutionsForResourcePayload = enableOrDisableSolutionsForResource(solutionId,
				active);
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_RESOURCES_DL(resourceId, practiceId);
		return given().spec(requestSpec).body(EnableOrDisableSolutionsForResourcePayload).when().put(url).then()
				.extract().response();
	}

	public Response enableOrDisableSolutionsForResourceUsingInvalidMethod_DL(int resourceId, int practiceId,
			int solutionId, boolean active) {
		Map<String, Object> EnableOrDisableSolutionsForResourcePayload = enableOrDisableSolutionsForResource(solutionId,
				active);
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_RESOURCES_DL(resourceId, practiceId);
		return given().spec(requestSpec).body(EnableOrDisableSolutionsForResourcePayload).when().post(url).then()
				.extract().response();
	}

	public Response enableOrDisableSolutionsForResourceUsingInvalidUrl_DL(int resourceId, int practiceId,
			int solutionId, boolean active) {
		Map<String, Object> EnableOrDisableSolutionsForResourcePayload = enableOrDisableSolutionsForResource(solutionId,
				active);
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_RESOURCES_INVALID_URL_DL(resourceId, practiceId);
		return given().spec(requestSpec).body(EnableOrDisableSolutionsForResourcePayload).when().put(url).then()
				.extract().response();
	}

	public Response enableOrDisableSolutionsForResourceForNullPayload_DL(int resourceId, int practiceId) {
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_RESOURCES_DL(resourceId, practiceId);
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response enableOrDisableSolutionsForResourceUsingNullValue_DL() {
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_RESOURCES__NULL_VALUE_DL();
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	private Map<String, Object> enableOrDisableSolutionsForResource(int solutionId, boolean active) {
		Map<String, Object> EnableOrDisableSolutionsForResourceMap = new HashMap<String, Object>();
		EnableOrDisableSolutionsForResourceMap.put("solutionId", solutionId);
		EnableOrDisableSolutionsForResourceMap.put("active", active);
		return EnableOrDisableSolutionsForResourceMap;
	}

	public Response createNewLocation_BL(int clientId, String addressLine1, String addressLine2, String city,
			String state, String zipcode, float lat, float lng, String name, String externalId, String timezoneCode,
			String displayName) {
		String url = ApiPath.CREATE_LOCATION_BL(clientId);
		Map<String, Object> addressPayload = createAddressPayload(addressLine1, addressLine2, city, state, zipcode, lat,
				lng);
		Map<String, Object> addNewLocationPayload = createNewLocationPayload(addressPayload, name, externalId,
				timezoneCode, displayName);
		return given().spec(requestSpec).body(addNewLocationPayload).when().post(url).then().extract().response();
	}

	private Map<String, Object> createAddressPayload(String addressLine1, String addressLine2, String city,
			String state, String zipcode, float lat, float lng) {
		Map<String, Object> addressMap = new HashMap<String, Object>();
		addressMap.put("addressLine1", addressLine1);
		addressMap.put("addressLine2", addressLine2);
		addressMap.put("city", city);
		addressMap.put("state", state);
		addressMap.put("zipcode", zipcode);
		addressMap.put("lat", lat);
		addressMap.put("lng", lng);
		return addressMap;
	}

	private Map<String, Object> createNewLocationPayload(Map<String, Object> address, String name, String externalId,
			String timezoneCode, String displayName) {
		Map<String, Object> createLocationMap = new HashMap<String, Object>();
		createLocationMap.put("name", name);
		createLocationMap.put("displayName", displayName);
		createLocationMap.put("address", address);
		createLocationMap.put("externalId", externalId);
		createLocationMap.put("timezoneCode", timezoneCode);
		return createLocationMap;
	}

	public Response getLocationDetails(int clientId, int locationId){
		String url = ApiPath.LOCATION_DETAILS_BL(clientId, locationId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response createNewLocationUsingInvalidUrl_BL(int clientId, String addressLine1, String addressLine2,
			String city, String state, String zipcode, float lat, float lng, String name, String externalId,
			String timezoneCode, String displayName) {
		String url = ApiPath.INVALID_PATH_CREATE_LOCATION_BL(clientId);
		Map<String, Object> addressPayload = createAddressPayload(addressLine1, addressLine2, city, state, zipcode, lat,
				lng);
		Map<String, Object> addNewLocationPayload = createNewLocationPayload(addressPayload, name, externalId,
				timezoneCode, displayName);
		return given().spec(requestSpec).body(addNewLocationPayload).when().post(url).then().extract().response();
	}

	public Response createNewLocationUsingInvalidMethod_BL(int clientId, String addressLine1, String addressLine2,
			String city, String state, String zipcode, float lat, float lng, String name, String externalId,
			String timezoneCode, String displayName) {
		String url = ApiPath.CREATE_LOCATION_BL(clientId);
		Map<String, Object> addressPayload = createAddressPayload(addressLine1, addressLine2, city, state, zipcode, lat,
				lng);
		Map<String, Object> addNewLocationPayload = createNewLocationPayload(addressPayload, name, externalId,
				timezoneCode, displayName);
		return given().spec(requestSpec).body(addNewLocationPayload).when().put(url).then().extract().response();
	}

	public Response createNewLocation_DL(int clientId, String addressLine1, String addressLine2, String city,
			String state, String zipcode, float lat, float lng, String name, String externalId, String timezoneCode,
			String displayName) {
		String url = ApiPath.CREATE_LOCATION_DL(clientId);
		Map<String, Object> addressPayload = createAddressPayload(addressLine1, addressLine2, city, state, zipcode, lat,
				lng);
		Map<String, Object> addNewLocationPayload = createNewLocationPayload(addressPayload, name, externalId,
				timezoneCode, displayName);
		return given().spec(requestSpec).body(addNewLocationPayload).when().post(url).then().extract().response();
	}

	public Response createNewLocationUsingInvalidUrl_DL(int clientId, String addressLine1, String addressLine2,
			String city, String state, String zipcode, float lat, float lng, String name, String externalId,
			String timezoneCode, String displayName) {
		String url = ApiPath.INVALID_PATH_CREATE_LOCATION_DL(clientId);
		Map<String, Object> addressPayload = createAddressPayload(addressLine1, addressLine2, city, state, zipcode, lat,
				lng);
		Map<String, Object> addNewLocationPayload = createNewLocationPayload(addressPayload, name, externalId,
				timezoneCode, displayName);
		return given().spec(requestSpec).body(addNewLocationPayload).when().post(url).then().extract().response();
	}

	public Response createNewLocationUsingInvalidMethod_DL(int clientId, String addressLine1, String addressLine2,
			String city, String state, String zipcode, float lat, float lng, String name, String externalId,
			String timezoneCode, String displayName) {
		String url = ApiPath.CREATE_LOCATION_DL(clientId);
		Map<String, Object> addressPayload = createAddressPayload(addressLine1, addressLine2, city, state, zipcode, lat,
				lng);
		Map<String, Object> addNewLocationPayload = createNewLocationPayload(addressPayload, name, externalId,
				timezoneCode, displayName);
		return given().spec(requestSpec).body(addNewLocationPayload).when().put(url).then().extract().response();
	}

	public Response getLocationDetails_DL(int clientId,int locationId) {
		String url = ApiPath.LOCATION_DETAILS_DL(clientId, locationId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response associateDisassociateBrandUsingNullValue_BL() {
		String url = ApiPath.ASSOCIATEDISASSOCIATE_BRAND_FOR_CLIENTORORG__NULL_VALUE_BL();
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response getLocationDetailsUsingInvalidUrl_BL(int locationId) {
		String url = ApiPath.INVALID_PATH_LOCATION_DETAILS_BL(locationId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	private Map<String, Object> associateDeassociatePracticeMapPayload(int practiceId, Boolean active) {
		Map<String, Object> associateDeassociatePracticeMap = new HashMap<String, Object>();
		associateDeassociatePracticeMap.put("practiceId", practiceId);
		associateDeassociatePracticeMap.put("active", active);
		return associateDeassociatePracticeMap;
	}

	public Response associateDeassociatePracticeForLocation_BL(int locationId, int practiceId, boolean active) {
		String url = ApiPath.UPDATE_LOCATION_FOR_PRACTICE_BL(locationId);
		Map<String, Object> associateDeassociatePracticeForLocationPayload = associateDeassociatePracticeMapPayload(
				practiceId, active);
		return given().spec(requestSpec).body(associateDeassociatePracticeForLocationPayload).when().put(url).then()
				.extract().response();
	}

	public Response getPracticesAssociatedToLocation_BL(int locationId) {
		String url = ApiPath.UPDATE_LOCATION_FOR_PRACTICE_BL(locationId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response associateDeassociatePracticeForLocationWithEmptyPayload_BL(int locationId) {
		String url = ApiPath.UPDATE_LOCATION_FOR_PRACTICE_BL(locationId);
		Map<String, Object> associateDeassociatePracticeForLocationPayload = new HashMap<String, Object>();
		return given().spec(requestSpec).body(associateDeassociatePracticeForLocationPayload).when().put(url).then()
				.extract().response();
	}

	public Response associateDeassociatePracticeForLocationUsingInvalidUrl_BL(int locationId, int practiceId,
			boolean active) {
		String url = ApiPath.INVALID_PATH_UPDATE_LOCATION_FOR_PRACTICE_BL(locationId);
		Map<String, Object> associateDeassociatePracticeForLocationPayload = associateDeassociatePracticeMapPayload(
				practiceId, active);
		return given().spec(requestSpec).body(associateDeassociatePracticeForLocationPayload).when().put(url).then()
				.extract().response();
	}

	public Response associateDeassociatePracticeForLocationUsingInvalidMethod_BL(int locationId, int practiceId,
			boolean active) {
		String url = ApiPath.UPDATE_LOCATION_FOR_PRACTICE_BL(locationId);
		Map<String, Object> associateDeassociatePracticeForLocationPayload = associateDeassociatePracticeMapPayload(
				practiceId, active);
		return given().spec(requestSpec).body(associateDeassociatePracticeForLocationPayload).when().post(url).then()
				.extract().response();
	}

	public Response associateDeassociatePracticeForLocation_DL(int locationId, int practiceId, boolean active) {
		String url = ApiPath.UPDATE_LOCATION_FOR_PRACTICE_DL(locationId);
		Map<String, Object> associateDeassociatePracticeForLocationPayload = associateDeassociatePracticeMapPayload(
				practiceId, active);
		return given().spec(requestSpec).body(associateDeassociatePracticeForLocationPayload).when().put(url).then()
				.extract().response();
	}

	public Response getPracticesAssociatedToLocation_DL(int locationId) {
		String url = ApiPath.UPDATE_LOCATION_FOR_PRACTICE_DL(locationId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response associateDeassociatePracticeForLocationWithEmptyPayload_DL(int locationId) {
		String url = ApiPath.UPDATE_LOCATION_FOR_PRACTICE_DL(locationId);
		Map<String, Object> associateDeassociatePracticeForLocationPayload = new HashMap<String, Object>();
		return given().spec(requestSpec).body(associateDeassociatePracticeForLocationPayload).when().put(url).then()
				.extract().response();
	}

	public Response associateDeassociatePracticeForLocationUsingInvalidUrl_DL(int locationId, int practiceId,
			boolean active) {
		String url = ApiPath.INVALID_PATH_UPDATE_LOCATION_FOR_PRACTICE_DL(locationId);
		Map<String, Object> associateDeassociatePracticeForLocationPayload = associateDeassociatePracticeMapPayload(
				practiceId, active);
		return given().spec(requestSpec).body(associateDeassociatePracticeForLocationPayload).when().put(url).then()
				.extract().response();
	}

	public Response associateDeassociatePracticeForLocationUsingInvalidMethod_DL(int locationId, int practiceId,
			boolean active) {
		String url = ApiPath.UPDATE_LOCATION_FOR_PRACTICE_DL(locationId);
		Map<String, Object> associateDeassociatePracticeForLocationPayload = associateDeassociatePracticeMapPayload(
				practiceId, active);
		return given().spec(requestSpec).body(associateDeassociatePracticeForLocationPayload).when().post(url).then()
				.extract().response();
	}

	public Response getPracticesAssociatedToLocationUsingInvalidUrl_BL(int locationId) {
		String url = ApiPath.INVALID_PATH_UPDATE_LOCATION_FOR_PRACTICE_BL(locationId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticesAssociatedToLocationUsingInvalidMethod_BL(int locationId) {
		String url = ApiPath.UPDATE_LOCATION_FOR_PRACTICE_BL(locationId);
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	public Response createProvider_DL(int practiceId, String firstName, String lastName, String gender,
			String externalId, String credentials) {
		String url = ApiPath.CREATE_PROVIDER_DL(practiceId);
		Map<String, Object> createProviderPayload = createProviderRequestPayload(firstName, lastName, gender,
				externalId, credentials);
		return given().spec(requestSpec).body(createProviderPayload).when().post(url).then().extract().response();
	}

	public Response addResourceForLocation_BL(int locationId, int practiceId, List<Integer> resourceIdList) {
		String url = ApiPath.ADD_RESOURCE_FOR_LOCATION_BL(locationId, practiceId);
		return given().spec(requestSpec).body(resourceIdList).when().post(url).then().extract().response();
	}

	public Response deleteResourceForLocation_BL(int locationId, int practiceId, int resourceId) {
		String url = ApiPath.DELETE_RESOURCE_FOR_LOCATION_BL(locationId, practiceId, resourceId);
		return given().spec(requestSpec).when().delete(url).then().extract().response();
	}

	public Response getResourcesForLocation_BL(int locationId, int practiceId, int page, int pageSize, String name) {
		String url = ApiPath.ADD_RESOURCE_FOR_LOCATION_BL(locationId, practiceId);
		return given().spec(requestSpec).queryParam("page", page).queryParam("pageSize", pageSize)
				.queryParam("name", name).when().get(url).then().extract().response();
	}

	public Response addResourceForLocation_DL(int locationId, int practiceId, List<Integer> resourceIdList) {
		String url = ApiPath.ADD_RESOURCE_FOR_LOCATION_DL(locationId, practiceId);
		return given().spec(requestSpec).body(resourceIdList).when().post(url).then().extract().response();
	}

	public Response deleteResourceForLocation_DL(int locationId, int practiceId, int resourceId) {
		String url = ApiPath.DELETE_RESOURCE_FOR_LOCATION_DL(locationId, practiceId, resourceId);
		return given().spec(requestSpec).when().delete(url).then().extract().response();
	}

	public Response getResourcesForLocation_DL(int locationId, int practiceId, int page, int pageSize, String name) {
		String url = ApiPath.ADD_RESOURCE_FOR_LOCATION_DL(locationId, practiceId);
		return given().spec(requestSpec).queryParam("page", page).queryParam("pageSize", pageSize)
				.queryParam("name", name).when().get(url).then().extract().response();
	}

	public Response associateDeassociatePracticeToProvider_DL(int providerId, int PracticeId, boolean active) {
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_PROVIDER_DL(providerId);
		Map<String, Object> associateDeassociatePracticeToProviderPayload = associateDeassociatePracticeToProvider(
				PracticeId, active);
		return given().spec(requestSpec).body(associateDeassociatePracticeToProviderPayload).when().put(url).then()
				.extract().response();
	}

	private Map<String, Object> associateDeassociatePracticeToProvider(int PracticeId, Boolean active) {
		Map<String, Object> associateDeassociatePracticeToProviderMap = new HashMap<String, Object>();
		associateDeassociatePracticeToProviderMap.put("PracticeId", PracticeId);
		associateDeassociatePracticeToProviderMap.put("active", active);
		return associateDeassociatePracticeToProviderMap;
	}

	public Response addOrRemoveAppoinmentTypesAssociatedWithResources_BL(int practiceId, int resourceId,
			List<AppointmentTypeDetails> appointmentTypeList) {
		String url = ApiPath.ADD_OR_REMOVE_APPOINTMENT_TYPES_FOR_RESOURCE_BL(resourceId, practiceId);
		return given().spec(requestSpec).body(appointmentTypeList).when().put(url).then().extract().response();
	}

	public Response associateDeassociatePractice_BL(int practiceId, int locationId, boolean active) {
		String url = ApiPath.ASSOCIATE_DEASSOCIATE_PRACTICE_TO_LOCATION_BL(locationId);
		Map<String, Object> associatePracticePayload = associateDeassociatePracticeToLocation(practiceId, active);
		return given().spec(requestSpec).body(associatePracticePayload).when().put(url).then().extract().response();
	}

	public Map<String, Object> associateDeassociatePracticeToLocation(Integer practiceId, Boolean active) {
		Map<String, Object> practicePayload = new HashMap<String, Object>();
		practicePayload.put("practiceId", practiceId);
		practicePayload.put("active", active);
		return practicePayload;
	}

	public Response getLocationByPracticeUsingValidInputs(int locationId, int practiceId) {
		String url = ApiPath.FETCH_LOCATION_BY_PRACTICE_BL_VALID_DETAILS(locationId, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getLocationByPracticeUsingNullValue_BL() {
		String url = ApiPath.FETCH_LOCATION_BY_PRACTICE_USING_NULL_VALUE_BL();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getLocationByPracticeUsingInvalidUrlPath(int locationId, int practiceId) {
		String url = ApiPath.INVALID_PATH_TO_FETCH_LOCATION_BY_PRACTICE_BL(locationId, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getLocationByPracticeUsingInvalidMethodCall(int locationId, int practiceId) {
		String url = ApiPath.FETCH_LOCATION_BY_PRACTICE_BL_VALID_DETAILS(locationId, practiceId);
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	public Response enableOrDisableSolutionsForResource_BL(int resourceId, int practiceId, int solutionId,
			boolean active) {
		Map<String, Object> EnableOrDisableSolutionsForResourcePayload = enableOrDisableSolutionsForResource(solutionId,
				active);
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_RESOURCES_BL(resourceId, practiceId);
		return given().spec(requestSpec).body(EnableOrDisableSolutionsForResourcePayload).when().put(url).then()
				.extract().response();
	}

	public Response enableOrDisableSolutionsForResourceForNullPayload_BL(int resourceId, int practiceId) {
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_RESOURCES_BL(resourceId, practiceId);
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response enableOrDisableSolutionsForResourceUsingInvalidMethod_BL(int resourceId, int practiceId,
			int solutionId, boolean active) {
		Map<String, Object> EnableOrDisableSolutionsForResourcePayload = enableOrDisableSolutionsForResource(solutionId,
				active);
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_RESOURCES_BL(resourceId, practiceId);
		return given().spec(requestSpec).body(EnableOrDisableSolutionsForResourcePayload).when().post(url).then()
				.extract().response();
	}

	public Response enableOrDisableSolutionsForResourceUsingInvalidUrl_BL(int resourceId, int practiceId,
			int solutionId, boolean active) {
		Map<String, Object> EnableOrDisableSolutionsForResourcePayload = enableOrDisableSolutionsForResource(solutionId,
				active);
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_RESOURCES_INVALID_URL_BL(resourceId, practiceId);
		return given().spec(requestSpec).body(EnableOrDisableSolutionsForResourcePayload).when().put(url).then()
				.extract().response();
	}

	public Response enableOrDisableSolutionsForResourceUsingNullValue_BL() {
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_RESOURCES_USING_NULL_VALUE_BL();
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response saveLocationInfoForPractice_BL(int locationId, int practiceId, String enDisplayName,
			String esDisplayName, String addressExtension, String phoneNumber, String startTime, String endTime,
			String enDirection, String esDirection) {
		String url = ApiPath.SAVE_LOCATION_INFO_FOR_PRACTICE_BL(locationId, practiceId);
		Map<String, Object> createEnESDisplayNamePayload = createDisplayNamePayload(enDisplayName, esDisplayName);
		Map<String, Object> createTimePayload = createOfficeHoursPayload(startTime, endTime);
		Map<String, Object> createDirectionPayload = createDirectionPayload(enDirection, esDirection);
		Map<String, Object> saveLocationInfoPayload = saveLocationInfoForPracticeApiPayload(
				createEnESDisplayNamePayload, addressExtension, phoneNumber, createTimePayload, createDirectionPayload);
		return given().spec(requestSpec).body(saveLocationInfoPayload).when().put(url).then().extract().response();
	}

	private Map<String, Object> createDisplayNamePayload(String en, String es) {
		Map<String, Object> createDisplayNameMap = new HashMap<String, Object>();
		createDisplayNameMap.put("en", en);
		createDisplayNameMap.put("es", es);
		return createDisplayNameMap;
	}

	private Map<String, Object> createOfficeHoursPayload(String startTime, String endTime) {
		Map<String, Object> createOfficeTimeMap = new HashMap<String, Object>();
		createOfficeTimeMap.put("startTime", startTime);
		createOfficeTimeMap.put("endTime", endTime);
		return createOfficeTimeMap;
	}

	private Map<String, Object> createDirectionPayload(String enDirection, String esDirection) {
		Map<String, Object> createDirectionTimeMap = new HashMap<String, Object>();
		createDirectionTimeMap.put("en", enDirection);
		createDirectionTimeMap.put("es", esDirection);
		return createDirectionTimeMap;
	}

	private Map<String, Object> saveLocationInfoForPracticeApiPayload(Map<String, Object> displayName,
			String addressExtension, String phoneNumber, Map<String, Object> createTimePayload,
			Map<String, Object> createDirectionPayload) {
		Map<String, Object> saveLocationInfoDetailsMap = new HashMap<String, Object>();
		saveLocationInfoDetailsMap.put("displayName", displayName);
		saveLocationInfoDetailsMap.put("addressExtension", addressExtension);
		saveLocationInfoDetailsMap.put("phoneNumber", phoneNumber);
		saveLocationInfoDetailsMap.put("officeHours", createTimePayload);
		saveLocationInfoDetailsMap.put("direction", createDirectionPayload);
		return saveLocationInfoDetailsMap;
	}

	public Response saveLocationInfoForPracticeUsingInvalidMethodCall_BL(int locationId, int practiceId,
			String enDisplayName, String esDisplayName, String addressExtension, String phoneNumber, String startTime,
			String endTime, String enDirection, String esDirection) {
		String url = ApiPath.SAVE_LOCATION_INFO_FOR_PRACTICE_BL(locationId, practiceId);
		Map<String, Object> createEnESDisplayNamePayload = createDisplayNamePayload(enDisplayName, esDisplayName);
		Map<String, Object> createTimePayload = createOfficeHoursPayload(startTime, endTime);
		Map<String, Object> createDirectionPayload = createDirectionPayload(enDirection, esDirection);

		Map<String, Object> saveLocationInfoPayload = saveLocationInfoForPracticeApiPayload(
				createEnESDisplayNamePayload, addressExtension, phoneNumber, createTimePayload, createDirectionPayload);
		return given().spec(requestSpec).body(saveLocationInfoPayload).when().post(url).then().extract().response();
	}

	public Response saveLocationInfoForPracticeUsingInvalidUrl_BL(int locationId, int practiceId, String enDisplayName,
			String esDisplayName, String addressExtension, String phoneNumber, String startTime, String endTime,
			String enDirection, String esDirection) {
		String url = ApiPath.SAVE_LOCATION_INFO_FOR_PRACTICE_USING_INVALID_URL_BL(locationId, practiceId);
		Map<String, Object> createEnESDisplayNamePayload = createDisplayNamePayload(enDisplayName, esDisplayName);
		Map<String, Object> createTimePayload = createOfficeHoursPayload(startTime, endTime);
		Map<String, Object> createDirectionPayload = createDirectionPayload(enDirection, esDirection);
		Map<String, Object> saveLocationInfoPayload = saveLocationInfoForPracticeApiPayload(
				createEnESDisplayNamePayload, addressExtension, phoneNumber, createTimePayload, createDirectionPayload);
		return given().spec(requestSpec).body(saveLocationInfoPayload).when().put(url).then().extract().response();
	}

	public Response saveLocationInfoForPracticeUsingNullValues_BL(String enDisplayName, String esDisplayName,
			String addressExtension, String phoneNumber, String startTime, String endTime, String enDirection,
			String esDirection) {
		String url = ApiPath.SAVE_LOCATION_INFO_FOR_PRACTICE_USING_NULL_VALUE_BL();
		Map<String, Object> createEnESDisplayNamePayload = createDisplayNamePayload(enDisplayName, esDisplayName);
		Map<String, Object> createTimePayload = createOfficeHoursPayload(startTime, endTime);
		Map<String, Object> createDirectionPayload = createDirectionPayload(enDirection, esDirection);
		Map<String, Object> saveLocationInfoPayload = saveLocationInfoForPracticeApiPayload(
				createEnESDisplayNamePayload, addressExtension, phoneNumber, createTimePayload, createDirectionPayload);
		return given().spec(requestSpec).body(saveLocationInfoPayload).when().put(url).then().extract().response();
	}

	public Response saveLocationInfoForPracticeForSelectivePayload_BL(int locationId, int practiceId,
			String enDisplayName, String esDisplayName, String addressExtension, String phoneNumber, String enDirection,
			String esDirection) {
		String url = ApiPath.SAVE_LOCATION_INFO_FOR_PRACTICE_BL(locationId, practiceId);
		Map<String, Object> createEnESDisplayNamePayload = createDisplayNamePayload(enDisplayName, esDisplayName);
		Map<String, Object> createDirectionPayload = createDirectionPayload(enDirection, esDirection);
		Map<String, Object> saveLocationInfoPayload = saveLocationInfoForPracticeApiPayload(
				createEnESDisplayNamePayload, addressExtension, phoneNumber, createDirectionPayload);
		return given().spec(requestSpec).body(saveLocationInfoPayload).when().put(url).then().extract().response();
	}

	private Map<String, Object> saveLocationInfoForPracticeApiPayload(Map<String, Object> displayName,
			String addressExtension, String phoneNumber, Map<String, Object> createDirectionPayload) {
		Map<String, Object> saveLocationInfoDetailsMap = new HashMap<String, Object>();
		saveLocationInfoDetailsMap.put("displayName", displayName);
		saveLocationInfoDetailsMap.put("addressExtension", addressExtension);
		saveLocationInfoDetailsMap.put("phoneNumber", phoneNumber);
		saveLocationInfoDetailsMap.put("direction", createDirectionPayload);
		return saveLocationInfoDetailsMap;
	}

	public Response associateDeassociatePractice_DL(int practiceId, int locationId, boolean active) {
		String url = ApiPath.ASSOCIATE_DEASSOCIATE_PRACTICE_TO_LOCATION_DL(locationId);
		Map<String, Object> associatePracticePayload = associateDeassociatePracticeToLocation(practiceId, active);
		return given().spec(requestSpec).body(associatePracticePayload).when().put(url).then().extract().response();
	}

	public Response getLocationByPractice_DL(int locationId, int practiceId) {
		String url = ApiPath.FETCH_LOCATION_BY_PRACTICE_DL(locationId, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getLocationByPracticeUsingNullValues_DL() {
		String url = ApiPath.FETCH_LOCATION_BY_PRACTICE_USING_NULL_VALUES_DL();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response deleteProviderForLocation_BL(int locationId, int practiceId, int providerId) {
		String url = ApiPath.DELETE_PROVIDER_FOR_LOCATION_BL(locationId, practiceId, providerId);
		return given().spec(requestSpec).when().delete(url).then().extract().response();
	}

	public Response deleteProviderForLocation_DL(int locationId, int practiceId, int providerId) {
		String url = ApiPath.DELETE_PROVIDER_FOR_LOCATION_DL(locationId, practiceId, providerId);
		return given().spec(requestSpec).when().delete(url).then().extract().response();
	}

	public Response enableDisableSolutionForLocation_DL(int practiceId, int locationId, int solutionId,
			boolean active) {
		String url = ApiPath.UPDATE_SOLUTION_FOR_LOCATION_DL(locationId, practiceId);
		Map<String, Object> enableDisableSolutionForLocationPayload = enableOrDisableSolutionsForResource(solutionId,
				active);
		return given().spec(requestSpec).body(enableDisableSolutionForLocationPayload).when().put(url).then().extract()
				.response();
	}

	public Response enableDisableSolutionForLocation_DL_NULL_SOLUTIONID(int practiceId, int locationId,
			boolean active) {
		String url = ApiPath.UPDATE_SOLUTION_FOR_LOCATION_DL(locationId, practiceId);
		Map<String, Object> enableDisableSolutionForLocationPayload = enableOrDisableSolutionsForLocation_NULL_solutionId(
				active);
		return given().spec(requestSpec).body(enableDisableSolutionForLocationPayload).when().put(url).then().extract()
				.response();
	}

	public Response enableDisableSolutionForLocation_DL_NULL_Active(int practiceId, int locationId, int solutionId) {
		String url = ApiPath.UPDATE_SOLUTION_FOR_LOCATION_DL(locationId, practiceId);
		Map<String, Object> enableDisableSolutionForLocationPayload = enableOrDisableSolutionsForLocation_NULL_Active(
				solutionId);
		return given().spec(requestSpec).body(enableDisableSolutionForLocationPayload).when().put(url).then().extract()
				.response();
	}

	public Response enableDisableSolutionForLocation_DL_NULL_SOLUTIONID_ACTIVE(int practiceId, int locationId) {
		String url = ApiPath.UPDATE_SOLUTION_FOR_LOCATION_DL(locationId, practiceId);
		Map<String, Object> enableDisableSolutionForLocationPayload = enableOrDisableSolutionsForLocation_NULL_Active_SolutionId();
		return given().spec(requestSpec).body(enableDisableSolutionForLocationPayload).when().put(url).then().extract()
				.response();
	}

	public Response enableDisableSolutionForLocation_DL_INVALID_Method_Call(int practiceId, int locationId,
			int solutionId, boolean active) {
		String url = ApiPath.UPDATE_SOLUTION_FOR_LOCATION_DL(locationId, practiceId);
		Map<String, Object> enableDisableSolutionForLocationPayload = enableOrDisableSolutionsForResource(solutionId,
				active);
		return given().spec(requestSpec).body(enableDisableSolutionForLocationPayload).when().patch(url).then()
				.extract().response();
	}

	public Response enableDisableSolutionForLocation_DL_INVALID_URL(int practiceId, int locationId, int solutionId,
			boolean active) {
		String url = ApiPath.UPDATE_SOLUTION_FOR_LOCATION_DL_INVALID_URL(locationId, practiceId);
		Map<String, Object> enableDisableSolutionForLocationPayload = enableOrDisableSolutionsForResource(solutionId,
				active);
		return given().spec(requestSpec).body(enableDisableSolutionForLocationPayload).when().patch(url).then()
				.extract().response();
	}

	private Map<String, Object> enableOrDisableSolutionsForLocation_NULL_solutionId(boolean active) {
		Map<String, Object> EnableOrDisableSolutionsForResourceMap = new HashMap<String, Object>();
		EnableOrDisableSolutionsForResourceMap.put("solutionId", null);
		EnableOrDisableSolutionsForResourceMap.put("active", active);
		return EnableOrDisableSolutionsForResourceMap;
	}

	private Map<String, Object> enableOrDisableSolutionsForLocation_NULL_Active(int solutionId) {
		Map<String, Object> EnableOrDisableSolutionsForResourceMap = new HashMap<String, Object>();
		EnableOrDisableSolutionsForResourceMap.put("solutionId", solutionId);
		EnableOrDisableSolutionsForResourceMap.put("active", null);
		return EnableOrDisableSolutionsForResourceMap;
	}

	private Map<String, Object> enableOrDisableSolutionsForLocation_NULL_Active_SolutionId() {
		Map<String, Object> EnableOrDisableSolutionsForResourceMap = new HashMap<String, Object>();
		EnableOrDisableSolutionsForResourceMap.put("solutionId", null);
		EnableOrDisableSolutionsForResourceMap.put("active", null);
		return EnableOrDisableSolutionsForResourceMap;
	}

	public Response getSolutionForLocation_DL(int practiceId, int locationId) {
		String url = ApiPath.UPDATE_SOLUTION_FOR_LOCATION_DL(locationId, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getSolutionForLocationForNullValue_DL() {
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_RESOURCES__NULL_VALUE_DL();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response saveLocationInfoForPractice_DL(int locationId, int practiceId, String enDisplayName,
			String esDisplayName, String addressExtension, String phoneNumber, String startTime, String endTime,
			String enDirection, String esDirection) {
		String url = ApiPath.FETCH_LOCATION_BY_PRACTICE_DL(locationId, practiceId);
		Map<String, Object> createEnESDisplayNamePayload = createDisplayNamePayload(enDisplayName, esDisplayName);
		Map<String, Object> createTimePayload = createOfficeHoursPayload(startTime, endTime);
		Map<String, Object> createDirectionPayload = createDirectionPayload(enDirection, esDirection);
		Map<String, Object> saveLocationInfoPayload = saveLocationInfoForPracticeApiPayload(
				createEnESDisplayNamePayload, addressExtension, phoneNumber, createTimePayload, createDirectionPayload);
		return given().spec(requestSpec).body(saveLocationInfoPayload).when().put(url).then().extract().response();
	}

	public Response saveLocationInfoForPracticeUsingNullValues_DL(String enDisplayName, String esDisplayName,
			String addressExtension, String phoneNumber, String startTime, String endTime, String enDirection,
			String esDirection) {
		String url = ApiPath.SAVE_LOCATION_INFO_FOR_PRACTICE_USING_NULL_VALUES_DL();
		Map<String, Object> createEnESDisplayNamePayload = createDisplayNamePayload(enDisplayName, esDisplayName);
		Map<String, Object> createTimePayload = createOfficeHoursPayload(startTime, endTime);
		Map<String, Object> createDirectionPayload = createDirectionPayload(enDirection, esDirection);
		Map<String, Object> saveLocationInfoPayload = saveLocationInfoForPracticeApiPayload(
				createEnESDisplayNamePayload, addressExtension, phoneNumber, createTimePayload, createDirectionPayload);
		return given().spec(requestSpec).body(saveLocationInfoPayload).when().put(url).then().extract().response();
	}

	public Response saveLocationInfoForPracticeWithoutMandatoryParametersInPayload_DL(int locationId, int practiceId,
			String enDisplayName, String esDisplayName, String addressExtension, String enDirection,
			String esDirection) {
		String url = ApiPath.FETCH_LOCATION_BY_PRACTICE_DL(locationId, practiceId);
		Map<String, Object> createEnESDisplayNamePayload = createDisplayNamePayload(enDisplayName, esDisplayName);
		Map<String, Object> createDirectionPayload = createDirectionPayload(enDirection, esDirection);
		Map<String, Object> saveLocationInfoPayload = (Map<String, Object>) saveLocationInfoForPracticeNonMandatoryParametersApiPayload(
				createEnESDisplayNamePayload, addressExtension, createDirectionPayload);
		return given().spec(requestSpec).body(saveLocationInfoPayload).when().put(url).then().extract().response();
	}

	public Map<String, Object> saveLocationInfoForPracticeNonMandatoryParametersApiPayload(
			Map<String, Object> displayName, String addressExtension, Map<String, Object> createDirectionPayload) {
		Map<String, Object> saveLocationInfoDetailsMap = new HashMap<String, Object>();
		saveLocationInfoDetailsMap.put("displayName", displayName);
		saveLocationInfoDetailsMap.put("addressExtension", addressExtension);
		saveLocationInfoDetailsMap.put("direction", createDirectionPayload);
		return saveLocationInfoDetailsMap;
	}

	public Response saveLocationInfoForPracticeWithNullPayload_DL(int locationId, int practiceId) {
		String url = ApiPath.FETCH_LOCATION_BY_PRACTICE_DL(locationId, practiceId);
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response addProviderImage_BL(int providerId, File file, String fileType) {
		String url = ApiPath.UPLOAD_PROVIDER_IMAGE(providerId);
		requestSpecification = new RequestSpecBuilder().addFilter(new ResponseLoggingFilter())
				.addFilter(new RequestLoggingFilter()).build();
		return given().spec(requestSpecification).contentType("multipart/form-data").multiPart("file", file, fileType)
				.post(url).then().extract().response();
	}

	public Response deleteProviderImage_BL(int providerId) {
		String url = ApiPath.UPLOAD_PROVIDER_IMAGE(providerId);
		return given().spec(requestSpec).when().delete(url).then().extract().response();
	}

	public Response savePracticeProvider_DL(int practiceId, int providerId, String en, String es, String email,
			String mobile) {
		String url = ApiPath.SAVE_PRACTICE_PROVIDER_DL(practiceId, providerId);
		Map<String, Object> createPracticeresourcePayload = createPracticeresourceRequestPayload(en, es);
		Map<String, Object> savePracticeProviderPayload = savePracticeProviderApiPayload(createPracticeresourcePayload,
				email, mobile);
		return given().spec(requestSpec).body(savePracticeProviderPayload).when().put(url).then().extract().response();
	}

	public Response savePracticeProviderNullPayload_DL(int practiceId, int providerId) {
		String url = ApiPath.SAVE_PRACTICE_PROVIDER_DL(practiceId, providerId);
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response savePracticeProviderNullValues_DL() {
		String url = ApiPath.SAVE_PRACTICE_PROVIDER_NULL_VALUES_DL();
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response getPracticeProviderDetails_DL(int practiceId, int providerId) {
		String url = ApiPath.SAVE_PRACTICE_PROVIDER_DL(practiceId, providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getProviderImage_BL(String imageId) {
		String url = ApiPath.GET_PROVIDER_IMAGE(imageId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response updateProviderDetails_BL(int providerId, String firstName, String lastName, String gender,
			String externalId, String credentials) {
		Map<String, Object> createProviderPayload = createProviderRequestPayload(firstName, lastName, gender,
				externalId, credentials);
		String url = ApiPath.PROVIDER_DETAILS_BL(providerId);
		return given().spec(requestSpec).body(createProviderPayload).when().put(url).then().extract().response();
	}

	public Response enableOrDisableSolutionsForProvider_DL(int ProviderId, int practiceId, int solutionId,
			boolean active) {
		Map<String, Object> EnableOrDisableSolutionsForResourcePayload = enableOrDisableSolutionsForResource(solutionId,
				active);
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_PROVIDER_DL(ProviderId, practiceId);
		return given().spec(requestSpec).body(EnableOrDisableSolutionsForResourcePayload).when().put(url).then()
				.extract().response();
	}

	public Response addOrRemoveLocationForProviders(int providerId, int practiceId, int location) {
		String url = ApiPath.UPDATE_LOCATION_FOR_PROVIDER_DL(providerId, practiceId);
		return given().spec(requestSpec).body(location).when().put(url).then().extract().response();
	}

	public Response getLocationForPractice(int practiceId, int providerId, String solutionName, String en) {
		String url = ApiPath.GET_LOCATION_BY_PRACTICE_DL(practiceId);
		return given().spec(requestSpec).queryParam("providerId", providerId).header("x-product", solutionName)
				.header("x-language", en).get(url).then().extract().response();
	}

	public Response getLocationForPractice_NullValue() {
		String url = ApiPath.GET_LOCATION_BY_PRACTICE_DL();
		return given().spec(requestSpec).get(url).then().extract().response();
	}

	public Response updateProviderDetails_NullPayload_BL(int providerId) {
		String url = ApiPath.PROVIDER_DETAILS_BL(providerId);
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response updateProviderDetails_NullValues_BL() {
		String url = ApiPath.PROVIDER_DETAILS_NULL_VALUES_BL();
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response updateProviderDetails_BL(int providerId, String practiceId, String active) {
		Map<String, Object> providerDetailsPayload = updateProvider(practiceId, active);
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_PROVIDER_BL(providerId);
		return given().spec(requestSpec).body(providerDetailsPayload).when().put(url).then().extract().response();
	}

	public Response enableOrDisableSolutionsForProvider_BL(int ProviderId, int practiceId, int solutionId,
			boolean active) {
		Map<String, Object> EnableOrDisableSolutionsForResourcePayload = enableOrDisableSolutionsForResource(solutionId,
				active);
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_PROVIDER_BL(ProviderId, practiceId);
		return given().spec(requestSpec).body(EnableOrDisableSolutionsForResourcePayload).when().put(url).then()
				.extract().response();
	}

	public Response addOrRemoveLocationForProvider_BL(int providerId, int practiceId, List<Locations> location) {
		String url = ApiPath.UPDATE_LOCATION_FOR_PROVIDER_BL(providerId, practiceId);
		return given().spec(requestSpec).body(location).when().put(url).then().extract().response();
	}

	public Response getLocationForPractice_BL(int practiceId, int providerId, String solutionName, String en) {
		String url = ApiPath.GET_LOCATIONS_BY_PRACTICE_BL(practiceId);
		return given().spec(requestSpec).queryParam("providerId", providerId).header("x-product", solutionName)
				.header("x-language", en).get(url).then().extract().response();
	}

	public Response getLocationForPractice_NullValues_BL() {
		String url = ApiPath.GET_LOCATIONS_BY_PRACTICE_BL();
		return given().spec(requestSpec).get(url).then().extract().response();
	}

	public Response associateProvidersToLocation_DL(int locationId, int practiceId, List<Integer> providerIdList) {
		String url = ApiPath.PROVIDERS_FOR_LOCATION_DL(locationId, practiceId);
		return given().spec(requestSpec).body(providerIdList).when().post(url).then().extract().response();
	}

	public Response associateProvidersToLocation_DL() {
		String url = ApiPath.ASSOCIATE_PROVIDERS_TO_LOCATION_NULL_VALUE_DL();
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	public Response getLocationsForProviderForNullValues_BL() {
		String url = ApiPath.UPDATE_LOCATION_FOR_PROVIDER__NULL_VALUE_BL();
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response getSolutionsForProviders_BL(int providerId, int practiceId) {
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_PROVIDER_BL(providerId, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getSolutionsForProviders_BL_NullValues() {
		String url = ApiPath.FETCH_SOLUTIONS_FOR_PROVIDERS_BL_NULL_VALUES;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getSolutionsForProviders_DL(int providerId, int practiceId) {
		String url = ApiPath.GET_SOLUTIONS_FOR_PROVIDER_DL(providerId, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getSolutionsForProviders_DL_NullValues() {
		String url = ApiPath.GET_SOLUTIONS_FOR_PROVIDERS_DL_NULL_VALUES;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticeLocation_BL(int practiceId, int locationId, String solutionName, String en) {
		String url = ApiPath.GET_PRACTICE_LOCATION_BL(practiceId, locationId);
		return given().spec(requestSpec).queryParam("locationId", locationId).header("x-product", solutionName)
				.header("x-language", en).get(url).then().extract().response();
	}

	public Response getPracticeLocation_NullValues_BL() {
		String url = ApiPath.GET_PRACTICE_LOCATION_BL_NULL_VALUES();
		return given().spec(requestSpec).get(url).then().extract().response();
	}

	public Response enableOrDisableSolutionsForLocation_BL(int locationId, int practiceId, int solutionId,
			boolean active) {
		Map<String, Object> EnableOrDisableSolutionsForResourcePayload = enableOrDisableSolutionsForResource(solutionId,
				active);
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_PROVIDER_BL(locationId, practiceId);
		return given().spec(requestSpec).body(EnableOrDisableSolutionsForResourcePayload).when().put(url).then()
				.extract().response();
	}

	public Response getProviderDetails_BL(int providerId) {
		String url = ApiPath.PROVIDER_DETAILS_BL(providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getExternalInternalMap_DL(int practiceId, String externalsystem, String identifier, String type,
			String maptype) {
		String url = ApiPath.GET_EXTERNAL_INTERNAL_MAP_DL(practiceId);
		return given().spec(requestSpec).queryParam("externalsystem", externalsystem)
				.queryParam("identifier", identifier).queryParam("type", type).queryParam("maptype", maptype).when()
				.get(url).then().extract().response();
	}

	public Response getExternalInternalMap_NullValues_DL(String externalsystem, String identifier, String type,
			String maptype) {
		String url = ApiPath.GET_EXTERNAL_INTERNAL_MAP_NULL_VALUES_DL();
		return given().spec(requestSpec).queryParam("externalsystem", externalsystem)
				.queryParam("identifier", identifier).queryParam("type", type).queryParam("maptype", maptype).when()
				.get(url).then().extract().response();
	}

	public Response enableOrDisableSolutionsForProviderNullRequestBody_DL(int ProviderId, int practiceId) {
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_PROVIDER_DL(ProviderId, practiceId);
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response enableOrDisableSolutionsForNullProviderIdAndPracticeId_DL() {
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_PROVIDER_NULL_VALUES_DL();
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response getPracticeLocation_DL(int practiceId, int locationId, String solutionName, String en) {
		String url = ApiPath.GET_PRACTICE_LOCATION_DL(practiceId, locationId);
		return given().spec(requestSpec).queryParam("locationId", locationId).header("x-product", solutionName)
				.header("x-language", en).get(url).then().extract().response();
	}

	public Response getPracticeLocation_NullValues_DL() {
		String url = ApiPath.GET_PRACTICE_LOCATION_DL_NULL_VALUES();
		return given().spec(requestSpec).get(url).then().extract().response();
	}

	public Response getProviderByClientId_DL(int clientId, String providerName) {
		String url = ApiPath.GET_PROVIDER_BY_CLIENTID_DL();
		return given().spec(requestSpec).queryParam("name", providerName).queryParam("clientId", clientId).when()
				.get(url).then().extract().response();
	}

	public Response getProviderByClientId_NullValues_DL() {
		String url = ApiPath.GET_PROVIDER_BY_CLIENTID_DL();
		return given().spec(requestSpec).queryParam("name", "null").queryParam("clientId", "null").when().get(url)
				.then().extract().response();
	}

	public Response getPracticeProvder_DL(int practiceId, int providerId) {
		String url = ApiPath.GET_PRACTICES_PROVIDERS(practiceId, providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticeProvder_NullValues() {
		String url = ApiPath.GET_PRACTICES_PROVIDERS_NULL_VALUES;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response putPracticeProvder_DL(int practiceId, int providerId, String additionalProp1,
			String additionalProp2, String additionalProp3, String emailId, String mobile) {
		String url = ApiPath.GET_PRACTICES_PROVIDERS(practiceId, providerId);
		Map<String, Object> createPayLoad = createPutProviderRequestPayload(additionalProp1, additionalProp2,
				additionalProp3, emailId, mobile);
		return given().spec(requestSpec).body(createPayLoad).when().put(url).then().extract().response();
	}

	private Map<String, Object> createPutProviderRequestPayload(String additionalProp1, String additionalProp2,
			String additionalProp3, String emailId, String mobile) {
		Map<String, Object> createProviderMap = new HashMap<String, Object>();
		Map<String, Object> createPutProviderMap = createPutProviderRequestPayload(additionalProp1, additionalProp2,
				additionalProp3);
		createProviderMap.put("displayName", createPutProviderMap);
		createProviderMap.put("email", emailId);
		createProviderMap.put("mobile", mobile);
		return createProviderMap;
	}

	private Map<String, Object> createPutProviderRequestPayload(String additionalProp1, String additionalProp2,
			String additionalProp3) {
		Map<String, Object> createDisplayNameMap = new HashMap<String, Object>();
		createDisplayNameMap.put("additionalProp1", additionalProp1);
		createDisplayNameMap.put("additionalProp2", additionalProp2);
		createDisplayNameMap.put("additionalProp3", additionalProp3);
		return createDisplayNameMap;
	}

	public Response getConnectedPracticeProvider_BL(int practiceId, String providerName) {
		String url = ApiPath.GET_CONNECTED_PRACTICE_PROVIDER_BL(practiceId);
		return given().spec(requestSpec).queryParam("name", providerName).when().get(url).then().extract().response();
	}

	public Response getConnectedPracticeProvider_BL_Null_Values() {
		String url = ApiPath.GET_CONNECTED_PRACTICES_PROVIDERS__BL_NULL_VALUES;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getProvidersForClient_BL(String clientId, String name, String systemid, String enterpriseid,
			String practiceid, String solutionid, String pagesize) {
		String url = ApiPath.PROVIDER_FOR_CLIENT_BL(clientId);
		return given().spec(requestSpec).queryParam("name", name).queryParam("systemid", systemid)
				.queryParam("enterpriseid", enterpriseid).queryParam("practiceid", practiceid)
				.queryParam("solutionid", solutionid).queryParam("pagesize", pagesize).when().get(url).then().extract()
				.response();
	}

	public Response practicesAssociateToPractice_BL(int providerId, int practiceId, boolean active) {
		String url = ApiPath.UPDATE_PROVIDER_FOR_PRACTICE_BL(providerId);
		Map<String, Object> providerAssociateToPracticePayload = providerAssociateToPractice(practiceId, active);
		return given().spec(requestSpec).body(providerAssociateToPracticePayload).when().put(url).then().extract()
				.response();
	}

	private Map<String, Object> providerAssociateToPractice(Integer practiceId, Boolean active) {
		Map<String, Object> providerassociatetoPracticeMap = new HashMap<String, Object>();
		providerassociatetoPracticeMap.put("practiceId", practiceId);
		providerassociatetoPracticeMap.put("active", active);
		return providerassociatetoPracticeMap;
	}

	public Response solutionAssociateToProvider_BL(int providerId, int practiceId, int solutionId, boolean active) {
		String url = ApiPath.UPDATE_SOLUTION_FOR_PROVIDER_BL(providerId, practiceId);
		Map<String, Object> solutionAssociateToProviderPayload = solutionAssociateToProvider(solutionId, active);
		return given().spec(requestSpec).body(solutionAssociateToProviderPayload).when().put(url).then().extract()
				.response();
	}

	private Map<String, Object> solutionAssociateToProvider(Integer solutionId, Boolean active) {
		Map<String, Object> solutionAssociateToProviderMap = new HashMap<String, Object>();
		solutionAssociateToProviderMap.put("solutionId", solutionId);
		solutionAssociateToProviderMap.put("active", active);
		return solutionAssociateToProviderMap;
	}

	public Response addOrRemoveResourcesForProvider_BL(int providerId, int practiceId,
			List<ResourceDetails> resourceList) {
		String url = ApiPath.UPDATE_RESOURCES_FOR_PROVIDER_BL(providerId, practiceId);
		return given().spec(requestSpec).body(resourceList).when().put(url).then().extract().response();
	}

	public Response getResourcesForProviderForNullValues_BL() {
		String url = ApiPath.UPDATE_RESOURCES_FOR_PROVIDER_NULL_VALUE_BL();
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response associateDeassociatePracticeForProvider_BL(int ProviderId, int practiceId, boolean active) {
		String url = ApiPath.UPDATE_PROVIDER_FOR_PRACTICE_BL(ProviderId);
		Map<String, Object> associateDeassociatePracticeForLocationPayload = associateDeassociatePracticeMapPayload(
				practiceId, active);
		return given().spec(requestSpec).body(associateDeassociatePracticeForLocationPayload).when().put(url).then()
				.extract().response();
	}

	public Response getPracticesAssociatedToProvider_BL(int ProviderId) {
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_PROVIDER_BL(ProviderId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response associateDeassociatePracticeForProviderWithEmptyPayload_BL(int providerId) {
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_PROVIDER_BL(providerId);
		Map<String, Object> associateDeassociatePracticeForProviderPayload = new HashMap<String, Object>();
		return given().spec(requestSpec).body(associateDeassociatePracticeForProviderPayload).when().put(url).then()
				.extract().response();
	}

	public Response getProvidersForClient_DL(String clientId, String name, String systemid, String enterpriseid,
			String practiceid, String solutionid, String pagesize) {
		String url = ApiPath.PROVIDER_FOR_CLIENT_DL(clientId);
		return given().spec(requestSpec).queryParam("name", name).queryParam("systemid", systemid)
				.queryParam("enterpriseid", enterpriseid).queryParam("practiceid", practiceid)
				.queryParam("solutionid", solutionid).queryParam("pagesize", pagesize).when().get(url).then().extract()
				.response();
	}

	public Response practicesAssociateToProvider_DL(int providerId, int practiceId, boolean active) {
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_PROVIDER_DL(providerId);
		Map<String, Object> providerAssociateToPracticePayload = providerAssociateToPractice(practiceId, active);
		return given().spec(requestSpec).body(providerAssociateToPracticePayload).when().put(url).then().extract()
				.response();
	}

	public Response associateDeassociatePracticeForResource_DL(int resourceId, int practiceId, String active) {
		String url = ApiPath.UPDATE_RESOURCES_FOR_PRACTICE_DL(resourceId);
		Map<String, Object> associateDeassociatePracticeForResourcePayload = associateDeassociatePracticeForResourceMapPayload(
				practiceId, active);
		return given().spec(requestSpec).body(associateDeassociatePracticeForResourcePayload).when().put(url).then()
				.extract().response();
	}

	private Map<String, Object> associateDeassociatePracticeForResourceMapPayload(int practiceId, String active) {
		Map<String, Object> associateDeassociatePracticeMap = new HashMap<String, Object>();
		associateDeassociatePracticeMap.put("practiceId", practiceId);
		associateDeassociatePracticeMap.put("active", active);
		return associateDeassociatePracticeMap;
	}

	public Response associateDeassociatePracticeForResourceUsingEmptyPayload_DL(int resourceId) {
		String url = ApiPath.UPDATE_RESOURCES_FOR_PRACTICE_DL(resourceId);
		Map<String, Object> associateDeassociatePracticeForLocationPayload = new HashMap<String, Object>();
		return given().spec(requestSpec).body(associateDeassociatePracticeForLocationPayload).when().put(url).then()
				.extract().response();
	}

	public Response associateDeassociatePracticeForResourceUsingNullValue_DL() {
		String url = ApiPath.UPDATE_RESOURCES_FOR_PRACTICE_NULL_VALUE_DL();
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response enableOrDisableSolutionsForProviderForNullPayload_BL(int providerId, int practiceId) {
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_PROVIDER_BL(providerId, practiceId);
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response enableOrDisableSolutionsForProvidersUsingNullValue_BL() {
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_PROVIDERS_USING_NULL_VALUE_BL();
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response getPracticeProvider_BL(int practiceId, int providerId) {
		String url = ApiPath.GET_PRACTICES_PROVIDERS_BL(practiceId, providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticeProvider_NullValues_BL() {
		String url = ApiPath.GET_PRACTICES_PROVIDERS_NULL_VALUES_BL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response putPracticeProvider_BL(int practiceId, int providerId, String additionalProp1,
			String additionalProp2, String additionalProp3, String emailId, String mobile) {
		String url = ApiPath.GET_PRACTICES_PROVIDERS_BL(practiceId, providerId);
		Map<String, Object> createPayLoad = createPutProviderRequestPayload_BL(additionalProp1, additionalProp2,
				additionalProp3, emailId, mobile);
		return given().spec(requestSpec).body(createPayLoad).when().put(url).then().extract().response();
	}

	private Map<String, Object> createPutProviderRequestPayload_BL(String additionalProp1, String additionalProp2,
			String additionalProp3, String emailId, String mobile) {
		Map<String, Object> createProviderMap = new HashMap<String, Object>();
		Map<String, Object> createPutProviderMap = createPutProviderRequestPayload_BL(additionalProp1, additionalProp2,
				additionalProp3);
		createProviderMap.put("displayName", createPutProviderMap);
		createProviderMap.put("email", emailId);
		createProviderMap.put("mobile", mobile);
		return createProviderMap;
	}

	private Map<String, Object> createPutProviderRequestPayload_BL(String additionalProp1, String additionalProp2,
			String additionalProp3) {
		Map<String, Object> createDisplayNameMap = new HashMap<String, Object>();
		createDisplayNameMap.put("additionalProp1", additionalProp1);
		createDisplayNameMap.put("additionalProp2", additionalProp2);
		createDisplayNameMap.put("additionalProp3", additionalProp3);
		return createDisplayNameMap;
	}

	public Response getConnectedResource_BL(int practiceId, String resourceName) {
		String url = ApiPath.SEARCH_RESOURCE_BL(practiceId);
		return given().spec(requestSpec).queryParam("name", resourceName).when().get(url).then().extract().response();
	}

	public Response getConnectedResource_Null_BL(String resourceName) {
		String url = ApiPath.SEARCH_RESOURCE_NULL_BL;
		return given().spec(requestSpec).queryParam("name", resourceName).when().get(url).then().extract().response();
	}

	public Response updateResourceDetails_DL(int resourceId, String name, String type, String externalId,
			String linkType, int linkId) {
		Map<String, Object> createResourcePayload = createResourceRequestPayload(name, type, externalId, linkType,
				linkId);
		String url = ApiPath.UPDATE_RESOURCE_DETAILS_DL(resourceId);
		return given().spec(requestSpec).body(createResourcePayload).when().put(url).then().extract().response();
	}

	private Map<String, Object> createResourceRequestPayload(String name, String type, String externalId,
			String linkType, int linkId) {
		Map<String, Object> createResourceMap = new HashMap<String, Object>();
		createResourceMap.put("name", name);
		createResourceMap.put("type", type);
		createResourceMap.put("externalId", externalId);
		createResourceMap.put("linkType", linkType);
		createResourceMap.put("linkId", linkId);
		return createResourceMap;
	}

	public Response updateResourceDetails_NullPayload_DL(int resourceId) {
		String url = ApiPath.UPDATE_RESOURCE_DETAILS_DL(resourceId);
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response updateResourceDetails_NullValues_DL() {
		String url = ApiPath.UPDATE_RESOURCE_DETAILS_NULL_VALUES_DL();
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response searchLocationForProvider_DL(int providerId, int practiceId, String providerName) {
		String url = ApiPath.SEARCH_LOCATION_FOR_PROVIDER_DL(providerId, practiceId);
		return given().spec(requestSpec).queryParam("name", providerName).when().get(url).then().extract().response();
	}

	public Response searchLocationForProviderForNullValue_DL() {
		String url = ApiPath.SEARCH_LOCATION_FOR_PROVIDER_DL();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getProviderByClientId_BL(int clientId, String providerName) {
		String url = ApiPath.GET_PROVIDER_BY_CLIENT_ID_BL();
		return given().spec(requestSpec).queryParam("name", providerName).queryParam("clientId", clientId).when()
				.get(url).then().extract().response();
	}

	public Response getProviderByClientId_NullValues_BL() {
		String url = ApiPath.GET_PROVIDER_BY_CLIENT_ID_BL();
		return given().spec(requestSpec).queryParam("name", "null").queryParam("clientId", "null").when().get(url)
				.then().extract().response();
	}

	public Response searchLocationForProvider_BL(int providerId, int practiceId, String providerName) {
		String url = ApiPath.SEARCH_LOCATION_FOR_PROVIDER_BL(providerId, practiceId);
		return given().spec(requestSpec).queryParam("name", providerName).when().get(url).then().extract().response();
	}

	public Response searchLocationForProviderForNullValue_BL() {
		String url = ApiPath.SEARCH_LOCATION_FOR_PROVIDER_BL();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response addOrRemoveResourcesForProvider_DL(int providerId, int practiceId,
			List<ResourceDetails> resourceList) {
		String url = ApiPath.UPDATE_RESOURCES_FOR_PROVIDER_DL(providerId, practiceId);
		return given().spec(requestSpec).body(resourceList).when().put(url).then().extract().response();
	}

	public Response searchResourcesToAssociateWithProvider_DL(int providerId, int practiceId, String resourceName) {
		String url = ApiPath.SEARCH_RESOURCES_FOR_PROVIDER_DL(providerId, practiceId);
		return given().spec(requestSpec).queryParam("name", resourceName).when().get(url).then().extract().response();
	}

	public Response searchResourcesToAssociateWithProvider_Null_Values_DL() {
		String url = ApiPath.SEARCH_RESOURCES_FOR_PROVIDER_NULL_VALUES_DL();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response addOrRemoveLocationForResource_DL(int ResourceId, int practiceId, List<Locations> location) {
		String url = ApiPath.UPDATE_LOCATION_FOR_RESOURCE_DL(ResourceId, practiceId);
		return given().spec(requestSpec).body(location).when().put(url).then().extract().response();
	}

	public Response getLocationsForResourceForNullValues_DL() {
		String url = ApiPath.UPDATE_LOCATION_FOR_RESOURCES_NULL_VALUE_DL();
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response putAddRemoveResourcesForProvider(int providerId, int practiceId,
			List<ResourceDetails> addRemoveResourcePayload) {
		String url = ApiPath.ADD_REMOVE_RESOURCE_FOR_PROVIDER(providerId, practiceId);
		List<ResourceDetails> addRemoveResource = addRemoveResourcePayload;
		return given().spec(requestSpec).body(addRemoveResource).when().put(url).then().extract().response();
	}

	public Response putAddRemoveResourcesForProvider_Null(List<ResourceDetails> addRemoveResourcePayload) {
		String url = ApiPath.ADD_REMOVE_RESOURCE_FOR_PROVIDER_NULL;
		List<ResourceDetails> addRemoveResource = addRemoveResourcePayload;
		return given().spec(requestSpec).body(addRemoveResource).when().put(url).then().extract().response();
	}

	public Response getResourcesForProvider(int providerId, int practiceId) {
		String url = ApiPath.ADD_REMOVE_RESOURCE_FOR_PROVIDER(providerId, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticesAssociatedToResource_DL(int resourceId) {
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_RESOURCE_DL(resourceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticesAssociatedToResourceNullValues_DL() {
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_RESOURCE_NULL_VALUE_DL();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response searchResourcesToAssociateWithProvider_BL(int providerId, int practiceId, String resourceName) {
		String url = ApiPath.SEARCH_RESOURCES_FOR_PROVIDER_BL(providerId, practiceId);
		return given().spec(requestSpec).queryParam("name", resourceName).when().get(url).then().extract().response();
	}

	public Response searchResourcesToAssociateWithProvider_Null_Values_BL() {
		String url = ApiPath.SEARCH_RESOURCES_FOR_PROVIDER_NULL_VALUES_BL();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response associateDeassociatePracticeForResource_BL(int resourceId, int practiceId, String active) {
		String url = ApiPath.UPDATE_RESOURCES_FOR_PRACTICE_BL(resourceId);
		Map<String, Object> associateDeassociatePracticeForResourcePayload = associateDeassociatePracticeForResourceMapPayload(
				practiceId, active);
		return given().spec(requestSpec).body(associateDeassociatePracticeForResourcePayload).when().put(url).then()
				.extract().response();
	}

	public Response associateDeassociatePracticeForResourceUsingEmptyPayload_BL(int resourceId) {
		String url = ApiPath.UPDATE_RESOURCES_FOR_PRACTICE_BL(resourceId);
		Map<String, Object> associateDeassociatePracticeForLocationPayload = new HashMap<String, Object>();
		return given().spec(requestSpec).body(associateDeassociatePracticeForLocationPayload).when().put(url).then()
				.extract().response();
	}

	public Response associateDeassociatePracticeForResourceUsingNullValue_BL() {
		String url = ApiPath.UPDATE_RESOURCES_FOR_PRACTICE_NULL_VALUE_BL();
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response savePracticeResource_BL(int practiceId, int resourcerId, String en, String es) {
		String url = ApiPath.SAVE_PRACTICE_RESOURCES_BL(practiceId, resourcerId);
		Map<String, Object> createPracticeresourcePayload = createPracticeresourceRequestPayload(en, es);
		return given().spec(requestSpec).body(createPracticeresourcePayload).when().put(url).then().extract()
				.response();
	}

	public Response getPracticeResourceDetails_BL(int practiceId, int resourceId) {
		String url = ApiPath.GET_PRACTICE_RESOURCES_BL(practiceId, resourceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response savePracticeResourceUsingNullValues_BL() {
		String url = ApiPath.SAVE_PRACTICE_RESOURCES_NULL_VALUES_BL();
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response addOrRemoveLocationForResource_BL(int ResourceId, int practiceId, List<Locations> location) {
		String url = ApiPath.UPDATE_LOCATION_FOR_RESOURCE_BL(ResourceId, practiceId);
		return given().spec(requestSpec).body(location).when().put(url).then().extract().response();
	}

	public Response getLocationsForResourceForNullValues_BL() {
		String url = ApiPath.UPDATE_LOCATION_FOR_RESOURCES_NULL_VALUE_BL();
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response updateResourceDetails_BL(int resourceId, String name, String type, String externalId,
			String linkType, int linkId) {
		Map<String, Object> createResourcePayload = createResourceRequestPayload(name, type, externalId, linkType,
				linkId);
		String url = ApiPath.UPDATE_RESOURCE_DETAILS_BL(resourceId);
		return given().spec(requestSpec).body(createResourcePayload).when().put(url).then().extract().response();
	}

	public Response updateResourceDetails_NullPayload_BL(int resourceId) {
		String url = ApiPath.UPDATE_RESOURCE_DETAILS_BL(resourceId);
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response updateResourceDetails_NullValues_BL() {
		String url = ApiPath.UPDATE_RESOURCE_DETAILS_NULL_VALUES_BL();
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response getResouresForClient_BL(String clientId, String name, String systemid, String enterpriseid,
			String practiceid, String solutionid) {
		String url = ApiPath.RESOURCE_FOR_CLIENT_BL(clientId);
		return given().spec(requestSpec).queryParam("name", name).queryParam("systemid", systemid)
				.queryParam("enterpriseid", enterpriseid).queryParam("practiceid", practiceid)
				.queryParam("solutionid", solutionid).when().get(url).then().extract().response();
	}

	public Response searchLocationForResource_BL(int ResourceId, int practiceId, String name) {
		String url = ApiPath.SEARCH_LOCATION_FOR_RESOURCE_BL(ResourceId, practiceId);
		return given().spec(requestSpec).queryParam("name", name).when().get(url).then().extract().response();
	}

	public Response searchLocationForResource_NullValue_BL() {
		String url = ApiPath.SEARCH_LOCATION_FOR_RESOURCE_NULL_VALUES_BL();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticeForResource_BL(int resourceId) {
		String url = ApiPath.UPDATE_RESOURCES_FOR_PRACTICE_BL(resourceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response searchProviderLocation_DL(int practiceId, String providerName) {
		String url = ApiPath.SEARCH_PROVIDERS_LOCATION_DL(practiceId);
		return given().spec(requestSpec).queryParam("name", providerName).when().get(url).then().extract().response();
	}

	public Response searchProviderLocation_NULL_DL(String providerName) {
		String url = ApiPath.SEARCH_PROVIDERS_LOCATION_NULL_DL;
		return given().spec(requestSpec).queryParam("name", providerName).when().get(url).then().extract().response();
	}

	public Response searchProviders_DL(String clientId, String resourceName, String systemId, String enterpriseId,
			String practiceId, String solutionId, String sortColumn, String sortDir) {
		int clientID = Integer.parseInt(clientId);
		int systemID = Integer.parseInt(systemId.trim());
		int enterpriseID = Integer.parseInt(enterpriseId.trim());
		int practiceID = Integer.parseInt(practiceId.trim());
		int solutionID = Integer.parseInt(solutionId.trim());
		String url = ApiPath.SEARCH_PROVIDERS_DL(clientID);
		if ((systemID <= 0) && (enterpriseID <= 0) && (practiceID <= 0) && (solutionID <= 0) && (sortColumn == null)
				&& (sortDir == null)) {
			return given().spec(requestSpec).queryParam("name", resourceName).when().get(url).then().extract()
					.response();
		} else if ((enterpriseID <= 0) && (practiceID <= 0) && (solutionID <= 0) && (sortColumn == null)
				&& (sortDir == null)) {
			return given().spec(requestSpec).queryParam("name", resourceName).queryParam("systemid", systemID).when()
					.get(url).then().extract().response();
		} else if ((practiceID <= 0) && (solutionID <= 0) && (sortColumn == null) && (sortDir == null)) {
			return given().spec(requestSpec).queryParam("name", resourceName).queryParam("systemid", systemID)
					.queryParam("enterpriseid", enterpriseID).when().get(url).then().extract().response();
		} else if ((solutionID <= 0) && (sortColumn == null) && (sortDir == null)) {
			return given().spec(requestSpec).queryParam("name", resourceName).queryParam("systemid", systemID)
					.queryParam("enterpriseid", enterpriseID).queryParam("practiceid", practiceID).when().get(url)
					.then().extract().response();
		} else if ((sortColumn == null) && (sortDir == null)) {
			return given().spec(requestSpec).queryParam("name", resourceName).queryParam("systemid", systemID)
					.queryParam("enterpriseid", enterpriseID).queryParam("practiceid", practiceID)
					.queryParam("solutionid", solutionID).when().get(url).then().extract().response();
		} else
			return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response searchProviders_NULL_DL() {
		String url = ApiPath.SEARCH_PROVIDERS_NULL_DL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getResourcesForProvider_NullValue() {
		String url = ApiPath.ADD_REMOVE_RESOURCE_FOR_PROVIDER_NULL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response savePracticeResource_DL(int practiceId, int resourcerId, String en, String es) {
		String url = ApiPath.SAVE_PRACTICE_RESOURCES_DL(practiceId, resourcerId);
		Map<String, Object> createPracticeresourcePayload = createPracticeresourceRequestPayload(en, es);
		return given().spec(requestSpec).body(createPracticeresourcePayload).when().put(url).then().extract()
				.response();
	}

	public Response getConnectedResource_DL(int practiceId, String resourceName) {
		String url = ApiPath.SEARCH_RESOURCE_DL(practiceId);
		return given().spec(requestSpec).queryParam("name", resourceName).when().get(url).then().extract().response();
	}

	public Response getConnectedResource_Null_DL(String resourceName) {
		String url = ApiPath.SEARCH_RESOURCE_NULL_DL;
		return given().spec(requestSpec).queryParam("name", resourceName).when().get(url).then().extract().response();
	}

	private Map<String, Object> createStaffRequestPayload(String firstName, String lastName, String gender,
			String externalId) {
		Map<String, Object> createStaffMap = new HashMap<String, Object>();
		createStaffMap.put("firstName", firstName);
		createStaffMap.put("lastName", lastName);
		createStaffMap.put("gender", gender);
		createStaffMap.put("externalId", externalId);
		return createStaffMap;
	}

	public Response createStaff_DL(int clientId, String firstName, String lastName, String gender, String externalId) {
		String url = ApiPath.CREATE_STAFF_DL(clientId);
		Map<String, Object> createStaffPayload = createStaffRequestPayload(firstName, lastName, gender, externalId);
		return given().spec(requestSpec).body(createStaffPayload).when().post(url).then().extract().response();
	}

	public Response getStaffForClient_DL(String clientId, String name, String systemid, String enterpriseid,
			String practiceid, String solutionid, String pagesize) {
		String url = ApiPath.STAFF_FOR_CLIENT_DL(clientId);
		return given().spec(requestSpec).queryParam("name", name).queryParam("systemid", systemid)
				.queryParam("enterpriseid", enterpriseid).queryParam("practiceid", practiceid)
				.queryParam("solutionid", solutionid).queryParam("pagesize", pagesize).when().get(url).then().extract()
				.response();
	}

	public Response practicesAssociateToStaff_DL(int staffId, int practiceId, boolean active) {
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_STAFF_DL(staffId);
		Map<String, Object> staffAssociateToPracticePayload = staffAssociateToPractice(practiceId, active);
		return given().spec(requestSpec).body(staffAssociateToPracticePayload).when().put(url).then().extract()
				.response();
	}

	private Map<String, Object> staffAssociateToPractice(Integer practiceId, Boolean active) {
		Map<String, Object> staffassociatetoPracticeMap = new HashMap<String, Object>();
		staffassociatetoPracticeMap.put("practiceId", practiceId);
		staffassociatetoPracticeMap.put("active", active);
		return staffassociatetoPracticeMap;
	}

	public Response createStaff_BL(int clientId, String firstName, String lastName, String gender, String externalId) {
		String url = ApiPath.CREATE_STAFF_BL(clientId);
		Map<String, Object> createStaffPayload = createStaffRequestPayload(firstName, lastName, gender, externalId);
		return given().spec(requestSpec).body(createStaffPayload).when().post(url).then().extract().response();
	}

	public Response getPracticesAssociatedToStaff_BL(int staffId) {
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_STAFF_BL(staffId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response practicesAssociateToStaff_BL(int staffId, int practiceId, boolean active) {
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_STAFF_BL(staffId);
		Map<String, Object> staffAssociateToPracticePayload = staffAssociateToPractice(practiceId, active);
		return given().spec(requestSpec).body(staffAssociateToPracticePayload).when().put(url).then().extract()
				.response();
	}
	
	public Response getPracticesAssociatedToStaff_NullValues_BL() {
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_STAFF_NULL_VALUES_BL();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}
	
	public Response associateDeassociatePracticeForStaffUsingEmptyPayload_BL(int staffId) {
 		String url = ApiPath.PRACTICES_ASSOCIATED_TO_STAFF_BL(staffId);
 		Map<String, Object> associateDeassociatePracticeForStaffPayload = new HashMap<String, Object>();
 		return given().spec(requestSpec).body(associateDeassociatePracticeForStaffPayload).when().put(url).then()
 				.extract().response();
 	}
 	
 	public Response associateDeassociatePracticeForStaffUsingNullValue_BL() {
 		String url = ApiPath.PRACTICES_ASSOCIATED_TO_STAFF_NULL_VALUES_BL();
 		return given().spec(requestSpec).when().put(url).then().extract().response();
 	}
 	
 	public Response getStaff_DL(int staffId) {
		String url = ApiPath.GET_STAFF_DL(staffId);
		return given().spec(requestSpec).when().get(url).then().extract()
				.response();
	}
	
	public Response getStaff_DL_Null_value(){
		String url = ApiPath.GET_STAFF_DL_NULL_VALUE();
		return given().spec(requestSpec).when().get(url).then().extract()
				.response();
	}	
	
	public Response getStaffDetails_BL(int clientId, int staffId) {
		String url = ApiPath.GET_STAFF_DETAILS_BL(clientId, staffId);
		return given().spec(requestSpec).when().get(url).then().extract()
				.response();
	}
	
	public Response getStaffDetails_BL_Null_value(){
		String url = ApiPath.GET_STAFF_DETAILS_BL_NULL_VALUE();
		return given().spec(requestSpec).when().get(url).then().extract()
				.response();
	}	

	public Response associateDeassociatePracticeForStaffUsingEmptyPayload_DL(int staffId) {
 		String url = ApiPath.PRACTICES_ASSOCIATED_TO_STAFF_DL(staffId);
 		return given().spec(requestSpec).when().put(url).then()
 				.extract().response();
 	}
	
	public Response associateDeassociatePracticeForStaffUsingNullValue_DL() {
 		String url = ApiPath.PRACTICES_ASSOCIATED_TO_STAFF_NULL_VALUES_DL();
 		return given().spec(requestSpec).when().put(url).then().extract().response();
 	}
	
	public Response getPracticesAssociatedToStaff_DL(int staffId) {
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_STAFF_DL(staffId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}
   
	public Response getPracticesAssociatedToStaff_NullValues_DL() {
		String url = ApiPath.PRACTICES_ASSOCIATED_TO_STAFF_NULL_VALUES_DL();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}
	
	public Response addOrRemoveResourcesForProviderForNullValues_DL() {
		String url = ApiPath.UPDATE_RESOURCES_FOR_PROVIDER_NULL_VALUE_DL();
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}
	
	public Response getStaffForClient_BL(String clientId, String name, String systemid, String enterpriseid,
			String practiceid, String solutionid, String pagesize) {
		String url = ApiPath.STAFF_FOR_CLIENT_BL(clientId);
		return given().spec(requestSpec).queryParam("name", name).queryParam("systemid", systemid)
				.queryParam("enterpriseid", enterpriseid).queryParam("practiceid", practiceid)
				.queryParam("solutionid", solutionid).queryParam("pagesize", pagesize).when().get(url).then().extract()
				.response();
	}
}
