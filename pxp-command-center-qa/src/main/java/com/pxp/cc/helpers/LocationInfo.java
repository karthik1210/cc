package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;

import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;

import io.restassured.response.Response;

public class LocationInfo extends BaseRest {

	public Response getLocations(String name, int systemId, int enterpriseId, int practiceId, int solution,
			String sortColumn, String sortDir, int page, int pageSize) {
		String url = ApiPath.SEARCH_LOCATIONS_DL;
		return given().spec(requestSpec).queryParam("name", name).queryParam("systemid", systemId)
				.queryParam("enterpriseid", enterpriseId).queryParam("practiceid", practiceId)
				.queryParam("solution", solution).queryParam("sortcolumn", sortColumn).queryParam("sortdir", sortDir)
				.queryParam("page", page).queryParam("pagesize", pageSize).when().get(url).then().extract().response();
	}
}
