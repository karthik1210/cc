// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import java.io.File;
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

	public Response getProviderDetails(int providerId) {
		String url = ApiPath.PROVIDER_DETAILS_BL(providerId);
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

	public Response getLocationDetails(int locationId) {
		String url = ApiPath.LOCATION_DETAILS_BL(locationId);
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

	public Response getLocationDetails_DL(int locationId) {
		String url = ApiPath.LOCATION_DETAILS_DL(locationId);
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

	public Response getLocationDetailsUsingInvalidMethod_BL(int locationId) {
		String url = ApiPath.LOCATION_DETAILS_BL(locationId);
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	public Response getLocationDetailsUsingInvalidUrl_DL(int locationId) {
		String url = ApiPath.INVALID_PATH_LOCATION_DETAILS_DL(locationId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getLocationDetailsUsingInvalidMethod_DL(int locationId) {
		String url = ApiPath.LOCATION_DETAILS_DL(locationId);
		return given().spec(requestSpec).when().post(url).then().extract().response();
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

	private Map<String, Object> associateDeassociatePracticeToProvider(Integer PracticeId, Boolean active) {
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
}
