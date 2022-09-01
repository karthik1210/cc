// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;

import java.io.File;

import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ResourceInfo extends BaseRest {
	
    public static RequestSpecification requestSpecification;

	public Response getResourceDetails_BL(int resourceId) {
		String url = ApiPath.GET_RESOURCE_DETAILS_BL(resourceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}
	
	public Response getResourceDetails_BL_NULL() {
		String url = ApiPath.GET_RESOURCE_DETAILS_BL_NULL;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}
	
	public Response getResourceDetails_BL_INVALID_URL(int resourceId) {
		String url = ApiPath.GET_RESOURCE_DETAILS_BL_INVALID_URL(resourceId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}
	
	public Response getResourceDetails_BL_INVALID_METHOD_CALL(int resourceId) {
		String url = ApiPath.GET_RESOURCE_DETAILS_BL(resourceId);
		return given().spec(requestSpec).when().post(url).then().extract().response();
	}
	
	public Response addResourceImage_BL(int resourceId, File file) {
        String url = ApiPath.ADD_RESOURCE_IMAGE(resourceId);
        requestSpecification = new RequestSpecBuilder().addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter()).build();
        return given().spec(requestSpecification).contentType("multipart/form-data").multiPart("file", file).post(url).then().extract().response();
    }

	public Response addResourceImageInvalidUrl_BL(int resourceId, File file) {
        String url = ApiPath.ADD_RESOURCE_IMAGE_INVALID_URL(resourceId);
        requestSpecification = new RequestSpecBuilder().addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter()).build();
        return given().spec(requestSpecification).contentType("multipart/form-data").multiPart("file", file).post(url).then().extract().response();
    }
	
	public Response addResourceImageInvalidMethodCall_BL(int resourceId, File file) {
        String url = ApiPath.ADD_RESOURCE_IMAGE(resourceId);
        requestSpecification = new RequestSpecBuilder().addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter()).build();
        return given().spec(requestSpecification).contentType("multipart/form-data").multiPart("file", file).put(url).then().extract().response();
    }
}
