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

}
