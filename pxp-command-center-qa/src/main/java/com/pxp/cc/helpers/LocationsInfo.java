//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import com.pxp.cc.model.BaseRest;
import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	public Response getLocationsForResourceForInvalidMethod_DL(int resourceId, int practiceId) {
		String url = ApiPath.GET_LOCATION_FOR_RESOURCE_DL(resourceId, practiceId);
		return given().spec(requestSpec).post(url).then().extract().response();
	}

	public Response createLocation_BL(String clientId, String name, String addressLine1, String addressLine2,
			String city, String state, int zipcode, int lat, int lng, int externalId, String timezoneCode,
			String displayName) throws IOException {
		String url = ApiPath.LOCATION_FOR_CLIENT_BL(clientId);
		Map<String, Object> createAddressPayload = postAddressLocation(addressLine1, addressLine2, city, state, zipcode,
				lat, lng);
		Map<String, Object> createLocationPayload = postLocationApiPayload(name, createAddressPayload, externalId,
				timezoneCode, displayName);
		return given().spec(requestSpec).body(createLocationPayload).when().post(url).then().extract().response();
	}

	public Response createLocation_DL(String clientId, String name, String addressLine1, String addressLine2,
			String city, String state, int zipcode, int lat, int lng, int externalId, String timezoneCode,
			String displayName) throws IOException {
		String url = ApiPath.LOCATION_FOR_CLIENT_DL(clientId);
		Map<String, Object> createAddressPayload = postAddressLocation(addressLine1, addressLine2, city, state, zipcode,
				lat, lng);
		Map<String, Object> createLocationPayload = postLocationApiPayload(name, createAddressPayload, externalId,
				timezoneCode, displayName);
		return given().spec(requestSpec).body(createLocationPayload).when().post(url).then().extract().response();
	}

	private Map<String, Object> postAddressLocation(String addressLine1, String addressLine2, String city, String state,
			int zipcode, int lat, int lng) {
		Map<String, Object> createAddressMap = new HashMap<String, Object>();
		createAddressMap.put("addressLine1", addressLine1);
		createAddressMap.put("addressLine2", addressLine2);
		createAddressMap.put("city", city);
		createAddressMap.put("state", state);
		createAddressMap.put("zipcode", zipcode);
		createAddressMap.put("lat", lat);
		createAddressMap.put("lng", lng);
		return createAddressMap;
	}

	private Map<String, Object> postLocationApiPayload(String name, Map<String, Object> address, int externalId,
			String timezoneCode, String displayName) {
		Map<String, Object> createLocationMap = new HashMap<String, Object>();
		createLocationMap.put("name", name);
		createLocationMap.put("displayName", displayName);
		createLocationMap.put("address", address);
		createLocationMap.put("externalId", externalId);
		createLocationMap.put("timezoneCode", timezoneCode);
		return createLocationMap;
	}

	public Response locationAssociateToPractice_BL(int locationId, int practiceId, boolean active) {
		String url = ApiPath.UPDATE_LOCATION_FOR_PRACTICE_BL(locationId);
		Map<String, Object> locationAssociateToPracticePayload = locationAssociateToPractice(practiceId, active);
		return given().spec(requestSpec).body(locationAssociateToPracticePayload).when().put(url).then().extract()
				.response();
	}

	public Response locationAssociateToPractice_DL(int locationId, int practiceId, boolean active) {
		String url = ApiPath.UPDATE_LOCATION_FOR_PRACTICE_DL(locationId);
		Map<String, Object> locationAssociateToPracticePayload = locationAssociateToPractice(practiceId, active);
		return given().spec(requestSpec).body(locationAssociateToPracticePayload).when().put(url).then().extract()
				.response();
	}

	private Map<String, Object> locationAssociateToPractice(Integer practiceId, Boolean active) {
		Map<String, Object> locationassociatetoPracticeMap = new HashMap<String, Object>();
		locationassociatetoPracticeMap.put("practiceId", practiceId);
		locationassociatetoPracticeMap.put("active", active);
		return locationassociatetoPracticeMap;
	}

	public Response solutionAssociateToLocation_BL(int locationId, int practiceId, int solutionId, boolean active) {
		String url = ApiPath.UPDATE_SOLUTION_FOR_LOCATION_BL(locationId, practiceId);
		Map<String, Object> solutionAssociateToLocationPayload = solutionAssociateToLocation(solutionId, active);
		return given().spec(requestSpec).body(solutionAssociateToLocationPayload).when().put(url).then().extract()
				.response();
	}

	public Response solutionAssociateToLocation_DL(int locationId, int practiceId, int solutionId, boolean active) {
		String url = ApiPath.UPDATE_SOLUTION_FOR_LOCATION_DL(locationId, practiceId);
		Map<String, Object> solutionAssociateToLocationPayload = solutionAssociateToLocation(solutionId, active);
		return given().spec(requestSpec).body(solutionAssociateToLocationPayload).when().put(url).then().extract()
				.response();
	}

	private Map<String, Object> solutionAssociateToLocation(Integer solutionId, Boolean active) {
		Map<String, Object> solutionAssociateToLocationMap = new HashMap<String, Object>();
		solutionAssociateToLocationMap.put("solutionId", solutionId);
		solutionAssociateToLocationMap.put("active", active);
		return solutionAssociateToLocationMap;
	}

	public Response getLocationsForClient_BL(String clientId, String name, String systemid, String enterpriseid,
			String practiceid, String solutionid, String pagesize) {
		String url = ApiPath.LOCATION_FOR_CLIENT_BL(clientId);
		return given().spec(requestSpec).queryParam("name", name).queryParam("systemid", systemid)
				.queryParam("enterpriseid", enterpriseid).queryParam("practiceid", practiceid)
				.queryParam("solutionid", solutionid).queryParam("pagesize", pagesize).when().get(url).then().extract()
				.response();
	}

	public Response getLocationsForClient_DL(String clientId, String name, String systemid, String enterpriseid,
			String practiceid, String solutionid, String pagesize) {
		String url = ApiPath.LOCATION_FOR_CLIENT_DL(clientId);
		return given().spec(requestSpec).queryParam("name", name).queryParam("systemid", systemid)
				.queryParam("enterpriseid", enterpriseid).queryParam("practiceid", practiceid)
				.queryParam("solutionid", solutionid).queryParam("pagesize", pagesize).when().get(url).then().extract()
				.response();
	}

	public Response getLocationsForClientInvalidUrl_BL(String clientId, String name, String systemid,
			String enterpriseid, String practiceid, String solutionid, String pagesize) {
		String url = ApiPath.INVALID_URL_LOCATION_FOR_CLIENT_BL(clientId);
		return given().spec(requestSpec).queryParam("name", name).queryParam("systemid", systemid)
				.queryParam("enterpriseid", enterpriseid).queryParam("practiceid", practiceid)
				.queryParam("solutionid", solutionid).queryParam("pagesize", pagesize).when().get(url).then().extract()
				.response();
	}

	public Response getLocationsForClientInvalidUrl_DL(String clientId, String name, String systemid,
			String enterpriseid, String practiceid, String solutionid, String pagesize) {
		String url = ApiPath.INVALID_URL_LOCATION_FOR_CLIENT_DL(clientId);
		return given().spec(requestSpec).queryParam("name", name).queryParam("systemid", systemid)
				.queryParam("enterpriseid", enterpriseid).queryParam("practiceid", practiceid)
				.queryParam("solutionid", solutionid).queryParam("pagesize", pagesize).when().get(url).then().extract()
				.response();
	}

	public Response getLocationsForClientInvalidMethodCall_BL(String clientId, String name, String systemid,
			String enterpriseid, String practiceid, String solutionid, String pagesize) {
		String url = ApiPath.LOCATION_FOR_CLIENT_BL(clientId);
		return given().spec(requestSpec).queryParam("name", name).queryParam("systemid", systemid)
				.queryParam("enterpriseid", enterpriseid).queryParam("practiceid", practiceid)
				.queryParam("solutionid", solutionid).queryParam("pagesize", pagesize).when().put(url).then().extract()
				.response();
	}

	public Response getLocationsForClientInvalidMethodCall_DL(String clientId, String name, String systemid,
			String enterpriseid, String practiceid, String solutionid, String pagesize) {
		String url = ApiPath.LOCATION_FOR_CLIENT_DL(clientId);
		return given().spec(requestSpec).queryParam("name", name).queryParam("systemid", systemid)
				.queryParam("enterpriseid", enterpriseid).queryParam("practiceid", practiceid)
				.queryParam("solutionid", solutionid).queryParam("pagesize", pagesize).when().put(url).then().extract()
				.response();
	}

	public Response getLocationsForClientForNullValue_BL() {
		String url = ApiPath.LOCATION_FOR_CLIENT_NULL_VALUE_BL();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getLocationsForClientForNullValue_DL() {
		String url = ApiPath.LOCATION_FOR_CLIENT_NULL_VALUE_DL();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response addResourceForLocation_BL(int locationId, int practiceId, ArrayList<Integer> resourceIds) {
		String url = ApiPath.ADD_RESOURCES_FOR_LOCATION_BL(locationId, practiceId);
		List<Integer> addResourceForLocationPayload = addResourceForLocation(resourceIds);
		return given().spec(requestSpec).body(addResourceForLocationPayload).when().post(url).then().extract()
				.response();
	}

	public Response addResourceForLocationInvalidMethod_BL(int locationId, int practiceId, List<Integer> resourceIds) {
		String url = ApiPath.ADD_RESOURCES_FOR_LOCATION_BL(locationId, practiceId);
		List<Integer> addResourceForLocationPayload = addResourceForLocation(resourceIds);
		return given().spec(requestSpec).body(addResourceForLocationPayload).when().put(url).then().extract()
				.response();
	}

	public Response addResourceForLocationInvalidUrl_BL(int locationId, int practiceId, List<Integer> resourceIds) {
		String url = ApiPath.ADD_RESOURCES_FOR_LOCATION_INVALID_URL_BL(locationId, practiceId);
		List<Integer> addResourceForLocationPayload = addResourceForLocation(resourceIds);
		return given().spec(requestSpec).body(addResourceForLocationPayload).when().post(url).then().extract()
				.response();
	}

	public Response addResourceForLocation_BL() {
		String url = ApiPath.ADD_RESOURCES_FOR_LOCATION_NULL_VALUE_BL();
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	private List<Integer> addResourceForLocation(List<Integer> resourceId) {
		List<Integer> resourceIds = new ArrayList<Integer>();
		resourceIds.addAll(resourceId);
		return resourceIds;
	}

	public Response getSolutionStatusApiResponse_BL(int locationId, int practiceId) {
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_PRACICE_AND_LOCATION_BL(locationId, practiceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response toggleSolutionStatusApiResponse_BL(int locationId, int practiceId, int solutionId, boolean active) {
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_PRACICE_AND_LOCATION_BL(locationId, practiceId);
		Map<String, Object> toggleSolutionStatusPayload = toggleSolutionStatus(solutionId, active);
		return given().spec(requestSpec).body(toggleSolutionStatusPayload).when().put(url).then().extract().response();
	}

	public Response toggleSolutionStatusApiResponseInvalidUrl_BL(int locationId, int practiceId, int solutionId,
			boolean active) {
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_PRACICE_AND_LOCATION_INVALID_URL_BL(locationId, practiceId);
		Map<String, Object> toggleSolutionStatusPayload = toggleSolutionStatus(solutionId, active);
		return given().spec(requestSpec).body(toggleSolutionStatusPayload).when().put(url).then().extract().response();
	}

	public Response toggleSolutionStatusApiResponseInvalidMethodCall_BL(int locationId, int practiceId, int solutionId,
			boolean active) {
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_PRACICE_AND_LOCATION_BL(locationId, practiceId);
		Map<String, Object> toggleSolutionStatusPayload = toggleSolutionStatus(solutionId, active);
		return given().spec(requestSpec).body(toggleSolutionStatusPayload).when().post(url).then().extract().response();
	}

	public Response toggleSolutionStatusApiResponseNullValue_BL() {
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_PRACICE_AND_LOCATION_USING_NULL_VALUE_BL();
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	private Map<String, Object> toggleSolutionStatus(Integer solutionId, Boolean active) {
		Map<String, Object> toggleSolutionStatusMap = new HashMap<String, Object>();
		toggleSolutionStatusMap.put("solutionId", solutionId);
		toggleSolutionStatusMap.put("active", active);
		return toggleSolutionStatusMap;
	}

	public Response getLocationsForResourceForInvalidUrl_BL(int resourceId, int practiceId) {
		String url = ApiPath.GET_LOCATION_FOR_RESOURCE_INVALID_URL_BL(resourceId, practiceId);
		return given().spec(requestSpec).get(url).then().extract().response();
	}

	public Response getLocationsForResourceForInvalidMethod_BL(int resourceId, int practiceId) {
		String url = ApiPath.GET_LOCATION_FOR_RESOURCE_BL(resourceId, practiceId);
		return given().spec(requestSpec).post(url).then().extract().response();
	}

	public Response getLocationsForResourceUsingNullValues() {
		String url = ApiPath.GET_LOCATION_FOR_RESOURCE_BL_NULL_VALUES();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getLocationsForResource_BL(int resourceId, int practiceId) {
		String url = ApiPath.GET_LOCATION_FOR_RESOURCE_BL(resourceId, practiceId);
		return given().spec(requestSpec).get(url).then().extract().response();
	}

	public Response addResourceForLocation_DL(int locationId, int practiceId, ArrayList<Integer> resourceIds) {
		String url = ApiPath.ADD_RESOURCES_FOR_LOCATION_DL(locationId, practiceId);
		List<Integer> addResourceForLocationPayload = addResourceForLocation(resourceIds);
		return given().spec(requestSpec).body(addResourceForLocationPayload).when().post(url).then().extract()
				.response();
	}

	public Response addResourceForLocation_DL() {
		String url = ApiPath.ADD_RESOURCES_FOR_LOCATION_NULL_VALUE_DL();
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	public Response associateProvidersToLocation_BL(int locationId, int practiceId, List<Integer> providerIdList) {
		String url = ApiPath.ASSOCIATE_PROVIDERS_TO_LOCATION_BL(locationId, practiceId);
		return given().spec(requestSpec).body(providerIdList).when().post(url).then().extract().response();
	}

	public Response associateProvidersToLocationInvalidMethodCall_BL(int locationId, int practiceId,
			List<Integer> providerIdList) {
		String url = ApiPath.ASSOCIATE_PROVIDERS_TO_LOCATION_BL(locationId, practiceId);
		return given().spec(requestSpec).body(providerIdList).when().put(url).then().extract().response();
	}

	public Response associateProvidersToLocationInvalidUrl_BL(int locationId, int practiceId,
			List<Integer> providerIdList) {
		String url = ApiPath.ASSOCIATE_PROVIDERS_TO_LOCATION_INVALID_URL_BL(locationId, practiceId);
		return given().spec(requestSpec).body(providerIdList).when().post(url).then().extract().response();
	}

	public Response associateProvidersToLocation_BL() {
		String url = ApiPath.ASSOCIATE_PROVIDERS_TO_LOCATION_NULL_VALUE_BL();
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}

	public Response associateProvidersToLocation_DL(int locationId, int practiceId, List<Integer> providerIdList) {
		String url = ApiPath.GET_PROVIDERS_FOR_LOCATION_DL(locationId, practiceId);
		return given().spec(requestSpec).body(providerIdList).when().post(url).then().extract().response();
	}
	
	public Response getSolutionStatusApiResponseNullValue_BL() {
		String url = ApiPath.UPDATE_SOLUTIONS_FOR_RESOURCES_USING_NULL_VALUE_BL();
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}
}
