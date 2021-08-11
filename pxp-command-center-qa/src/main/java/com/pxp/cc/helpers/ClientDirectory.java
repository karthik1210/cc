// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.helpers;

import com.pxp.cc.constants.Constants;
import com.pxp.cc.model.BaseRest;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ClientDirectory extends BaseRest {

	public Response getClientFromClientDirectoryWithFilter(String searchdata, String page, String pageSize,
			Object types) {
		log.info("search data : ", searchdata);
		log.info("page data : ", page);
		log.info("page size : ", pageSize);
		log.info("type data : ", types);
		String url = Constants.GET_CLIENT_DIRECTORY;
		return given().spec(requestSpec).queryParam("query", searchdata).queryParams("type", types)
				.queryParam("page", page).queryParam("pageSize", pageSize).when().get(url).then().extract().response();
	}

	public Response getClientFromClientDirectoryWithoutFilter(String searchdata, String page, String pageSize) {
		int pageValue = Integer.parseInt(page);
		int pageSizeValue = Integer.parseInt(pageSize);
		String url = Constants.GET_CLIENT_DIRECTORY;
		return given().spec(requestSpec).queryParam("query", searchdata).queryParam("type")
				.queryParam("page", pageValue).queryParam("pageSize", pageSizeValue).when().get(url).then().extract()
				.response();
	}

}
