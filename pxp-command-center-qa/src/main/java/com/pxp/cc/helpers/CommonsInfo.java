// Copyright 2022-23 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import io.restassured.response.Response;

public class CommonsInfo extends BaseRest {

	public Response getTimezones_BusinessLayer() throws IOException {
		String url = ApiPath.TIMEZONES_BL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response invalidMethodCall_BusinessLayer() throws IOException {
		String url = ApiPath.TIMEZONES_BL;
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response getTimezonesUsingInvalidUrl_BusinessLayerLayer() throws IOException {
		String url = ApiPath.INVALID_PATH_TIMEZONES_BL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getTimezones_DataLayer() throws IOException {
		String url = ApiPath.TIMEZONES_DL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response invalidMethodCall_DataLayer() throws IOException {
		String url = ApiPath.TIMEZONES_DL;
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response getTimezonesUsingInvalidUrl_DataLayer() throws IOException {
		String url = ApiPath.INVALID_PATH_TIMEZONES_DL;
		return given().spec(requestSpec).when().put(url).then().extract().response();
	}

	public Response getSocialsResponse(String validURI, String serviceLayer) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.GET_SOCIALS(serviceLayer);
			return given().spec(requestSpec).when().get(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.GET_SOCIALS_INVALID_URI(serviceLayer);
			return given().spec(requestSpec).when().get(url).then().extract().response();
		} else  {
			String url = ApiPath.GET_SOCIALS(serviceLayer);
			return given().spec(requestSpec).when().put(url).then().extract().response();
		}
	}

	public Response getStatesResponse(String validURI, String serviceLayer) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.GET_STATES(serviceLayer);
			return given().spec(requestSpec).when().get(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.GET_STATES_WITH_INVLAID_URI(serviceLayer);
			return given().spec(requestSpec).when().get(url).then().extract().response();
		} else  {
			String url = ApiPath.GET_STATES(serviceLayer);
			return given().spec(requestSpec).when().patch(url).then().extract().response();
		}
	}

	public Response getCommonTemplatesResponse_DL(String validURI, String product) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.GET_COMMON_TEMPLATE_LIST_DL(validURI);
			return given().spec(requestSpec).when().queryParam("product", product).get(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.GET_COMMON_TEMPLATE_LIST_DL(validURI);
			return given().spec(requestSpec).when().queryParam("product", product).get(url).then().extract().response();
		} else {
			String url = ApiPath.GET_COMMON_TEMPLATE_LIST_DL("Valid");
			return given().spec(requestSpec).when().queryParam("product", product).delete(url).then().extract().response();
		}
	}

	public Response getCommonTemplatesResponse_BL(String validURI, String product, String type) {
		if (validURI.equalsIgnoreCase("Valid")) {
			String url = ApiPath.GET_COMMON_TEMPLATE_LIST_BL(validURI);
			return given().spec(requestSpec).when().queryParam("product", product).queryParam("type", type).get(url).then().extract().response();
		} else if (validURI.equalsIgnoreCase("InValid")) {
			String url = ApiPath.GET_COMMON_TEMPLATE_LIST_BL(validURI);
			return given().spec(requestSpec).when().queryParam("product", product).queryParam("type", type).get(url).then().extract().response();
		} else {
			String url = ApiPath.GET_COMMON_TEMPLATE_LIST_BL("Valid");
			return given().spec(requestSpec).when().queryParam("product", product).queryParam("type", type).delete(url).then().extract().response();
		}
	}
}
