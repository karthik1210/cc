// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.model;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.medfusion.common.utils.PropertyFileLoader;
import com.pxp.cc.apipath.ApiPath;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseRest extends BaseTestNG {

	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;
	public JsonPath jsonPath;
	public CCUser ccUser;
	public PropertyFileLoader testData;

	@BeforeSuite(alwaysRun = true)
	public void setUpTestData() throws Exception {
		testData = new PropertyFileLoader();
		log("Setting up the Test Data.");
		ccUser = new CCUser(testData);
		ccUser.init();
		BasicConfigurator.configure();
	}

	@BeforeMethod(enabled = true)
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
	}

	public String getTokenValue() throws IOException {
		RestAssured.baseURI = ccUser.getAuthBaseUrl();
		Response response = given().auth().preemptive()
				.basic(ccUser.getCommandCenterUser(), ccUser.getCommandCenterPassword()).when()
				.post(ApiPath.POST_SIGNIN).then().log().all().extract().response();
		String token = response.jsonPath().get("token").toString();
		return token;

	}

	public void setupServiceRequestSpecBuilder() throws IOException {
		RestAssured.baseURI = ccUser.getServiceBaseUrl();
		requestSpec = new RequestSpecBuilder().setContentType(ApiPath.CONTENT_TYPE)
				.addHeader(ApiPath.AUTHORIZATION, ApiPath.BEARER_TYPE + getTokenValue())
				.addFilter(new ResponseLoggingFilter()).addFilter(new RequestLoggingFilter()).build();
	}

}
