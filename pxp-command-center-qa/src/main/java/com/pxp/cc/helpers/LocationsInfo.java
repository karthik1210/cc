//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import com.pxp.cc.model.BaseRest;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import com.pxp.cc.apipath.ApiPath;

public class LocationsInfo extends BaseRest {

	public Response getLocationsWithFilter_DL(String locName, int systemId, int enterpriseId, int practiceId,
			int solution, String sortcolumn, String sortdir, int page, int pageSize) {
		return given().spec(requestSpec).queryParam("name", locName).queryParam("page", page)
				.queryParam("pageSize", pageSize).when().get(ApiPath.SEARCH_LOCATIONS_DL).then().extract().response();
	}

	public Response getLocationsWithFilterSystemId_DL(String locName, int systemId, int enterpriseId, int practiceId,
			int solution, String sortcolumn, String sortdir, int page, int pageSize) {
		return given().spec(requestSpec).queryParam("name", locName).queryParams("systemid", systemId)
				.queryParam("page", page).queryParam("pageSize", pageSize).when().get(ApiPath.SEARCH_LOCATIONS_DL)
				.then().extract().response();
	}

	public Response getLocationsWithFilterEnterpriseId_DL(String locName, int systemId, int enterpriseId,
			int practiceId, int solution, String sortcolumn, String sortdir, int page, int pageSize) {
		return given().spec(requestSpec).queryParam("name", locName).queryParams("enterpriseid", enterpriseId)
				.queryParam("page", page).queryParam("pageSize", pageSize).when().get(ApiPath.SEARCH_LOCATIONS_DL)
				.then().extract().response();
	}

	public Response getLocationsWithFilterPracticeId_DL(String locName, int systemId, int enterpriseId, int practiceId,
			int solution, String sortcolumn, String sortdir, int page, int pageSize) {
		return given().spec(requestSpec).queryParam("name", locName).queryParams("practiceid", practiceId)
				.queryParam("page", page).queryParam("pageSize", pageSize).when().get(ApiPath.SEARCH_LOCATIONS_DL)
				.then().extract().response();
	}

	public Response getLocationsWithFilterSolutionId_DL(String locName, int systemId, int enterpriseId, int practiceId,
			int solution, String sortcolumn, String sortdir, int page, int pageSize) {
		return given().spec(requestSpec).queryParam("name", locName).queryParams("solution", solution)
				.queryParam("page", page).queryParam("pageSize", pageSize).when().get(ApiPath.SEARCH_LOCATIONS_DL)
				.then().extract().response();
	}

	public Response getLocationsWithFilterSortColumnSortDir_DL(String locName, int systemId, int enterpriseId,
			int practiceId, int solution, String sortcolumn, String sortdir, int page, int pageSize) {
		return given().spec(requestSpec).queryParam("name", locName).queryParam("sortcolumn", sortcolumn)
				.queryParam("sortdir", sortdir).queryParam("page", page).queryParam("pageSize", pageSize).when()
				.get(ApiPath.SEARCH_LOCATIONS_DL).then().extract().response();
	}

	public Response getLocationsWithFilterSortDir_DL(String locName, int systemId, int enterpriseId, int practiceId,
			int solution, String sortcolumn, String sortdir, int page, int pageSize) {
		return given().spec(requestSpec).queryParam("name", locName).queryParam("page", page)
				.queryParam("sortdir", sortdir).queryParam("pageSize", pageSize).when().get(ApiPath.SEARCH_LOCATIONS_DL)
				.then().extract().response();
	}

	public Response getLocationsInvalidUrl_DL(String locName, int systemId, int enterpriseId, int practiceId,
			int solution, String sortcolumn, String sortdir, int page, int pageSize) {
		return given().spec(requestSpec).queryParam("name", locName).queryParam("page", page)
				.queryParam("pageSize", pageSize).when().get(ApiPath.INVALIDURL_SEARCH_LOCATIONS_DL).then().extract()
				.response();
	}

	public Response getLocationsInvalidMethodCall_DL(String locName, int systemId, int enterpriseId, int practiceId,
			int solution, String sortcolumn, String sortdir, int page, int pageSize) {
		return given().spec(requestSpec).queryParam("name", locName).queryParam("page", page)
				.queryParam("pageSize", pageSize).when().post(ApiPath.SEARCH_LOCATIONS_DL).then().extract().response();
	}

	public Response getProvidersForLocation_BL(int locationId, int practiceId, int page, int pageSize, String name) {
		String url = ApiPath.GET_PROVIDER_FOR_LOCATION_BL(locationId, practiceId);
		return given().spec(requestSpec).queryParam("page", page).queryParam("pageSize", pageSize)
				.queryParam("name", name).when().get(url).then().extract().response();
	}

	public Response getProvidersForLocationInvalidUrl_BL(int locationId, int practiceId, int page, int pageSize,
			String name) {
		String url = ApiPath.INVALID_PATH_GET_PROVIDER_FOR_LOCATION_BL(locationId, practiceId);
		return given().spec(requestSpec).queryParam("page", page).queryParam("pageSize", pageSize)
				.queryParam("name", name).when().get(url).then().extract().response();
	}

	public Response getProvidersForLocationInvalidMethodCall_BL(int locationId, int practiceId, int page, int pageSize,
			String name) {
		String url = ApiPath.GET_PROVIDER_FOR_LOCATION_BL(locationId, practiceId);
		return given().spec(requestSpec).queryParam("page", page).queryParam("pageSize", pageSize)
				.queryParam("name", name).when().put(url).then().extract().response();
	}

	public Response getProvidersForLocation_BL_NullValues() {
		String url = ApiPath.GET_PROVIDER_FOR_LOCATION_BL_NULL_VALUES;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getProvidersForLocationInvalidUrl_DL(int locationId, int practiceId, int page, int pageSize,
			String name) {
		String url = ApiPath.GET_PROVIDERS_FOR_LOCATION_INVALID_PATH_DL(locationId, practiceId);
		return given().spec(requestSpec).queryParam("page", page).queryParam("pageSize", pageSize)
				.queryParam("name", name).when().get(url).then().extract().response();
	}

	public Response getProvidersForLocationInvalidMethodCall_DL(int locationId, int practiceId, int page, int pageSize,
			String name) {
		String url = ApiPath.GET_PROVIDERS_FOR_LOCATION_DL(locationId, practiceId);
		return given().spec(requestSpec).queryParam("page", page).queryParam("pageSize", pageSize)
				.queryParam("name", name).when().put(url).then().extract().response();
	}

	public Response getProvidersForLocation_DL_NullValues() {
		String url = ApiPath.GET_PROVIDERS_FOR_LOCATION_DL_NULL_VALUES;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getProvidersForLocation_DL(int locationId, int practiceId, int page, int pageSize, String name) {
		String url = ApiPath.GET_PROVIDERS_FOR_LOCATION_DL(locationId, practiceId);
		return given().spec(requestSpec).queryParam("page", page).queryParam("pageSize", pageSize)
				.queryParam("name", name).when().get(url).then().extract().response();
	}

	public Response getCCUserRoleForLocations(int ccUserId, int orgId) {
		String url = ApiPath.GET_LOCATIONLIST_ROLE_BL(ccUserId, orgId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getCCUserRoleForLocationsNullValues() {
		String url = ApiPath.GET_LOCATIONLIST_ROLE_BL_NULL_VALUES;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getCCUserRoleForLocationsAlphaNumValues() {
		String url = ApiPath.GET_LOCATIONLIST_ROLE_BL_ALPHANUM_VALUES;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getCCUserRoleForLocationsInvaliMethodCall(int ccUserId, int orgId) {
		String url = ApiPath.GET_LOCATIONLIST_ROLE_BL(ccUserId, orgId);
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response getCCUserRoleForLocationsInvalid() {
		String url = ApiPath.INVALID_GET_LOCATIONLIST_ROLE_BL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getLocationsForResource_DL(int resourceId, int practiceId) {
		String url = ApiPath.GET_LOCATION_FOR_RESOURCE_DL(resourceId, practiceId);
		return given().spec(requestSpec).get(url).then().extract().response();
	}

	public Response getLocationsForResourceForInvalidUrl_DL(int resourceId, int practiceId) {
		String url = ApiPath.GET_LOCATION_FOR_RESOURCE_INVALID_URL_DL(resourceId, practiceId);
		return given().spec(requestSpec).get(url).then().extract().response();
	}

	public Response getLocationsForResourceForNullValues() {
		String url = ApiPath.GET_LOCATION_FOR_RESOURCE_DL_NULL_VALUES();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

}
