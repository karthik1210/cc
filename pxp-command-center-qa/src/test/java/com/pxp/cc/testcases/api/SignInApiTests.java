// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.testcases.api;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import org.apache.log4j.BasicConfigurator;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.PropertyFileLoader;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.SignInValidations;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SignInApiTests extends BaseTestNG {

	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;
	public PropertyFileLoader testData;
	public Response responseData;
	public SignInValidations signInValidations = new SignInValidations();

	@BeforeSuite(alwaysRun = true)
	public void setUpTestData() throws Exception {
		testData = new PropertyFileLoader();
		log("Setting up the Test Data.");
		BasicConfigurator.configure();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating Simple Sign In Functionality.")
	public void testNormalSignInFunctionality() throws IOException {
		RestAssured.baseURI = testData.getProperty("auth.baseurl");
		Response response = given().auth().preemptive()
				.basic(testData.getProperty("commandcenter.user"), testData.getProperty("commandcenter.password"))
				.when().post(ApiPath.POST_SIGNIN).then().log().all().extract().response();
		String token = response.jsonPath().get("token").toString();
		RestAssured.baseURI = testData.getProperty("service.baseurl");
		requestSpec = new RequestSpecBuilder().setContentType(ApiPath.CONTENT_TYPE)
				.addHeader(ApiPath.AUTHORIZATION, ApiPath.BEARER_TYPE + token).addFilter(new ResponseLoggingFilter())
				.addFilter(new RequestLoggingFilter()).build();
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(response);
		logStep("Validating Response Code");
		CCUtils.assertEquals(response.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating Simple Sign In Functionality with invalid username.")
	public void testErrorInvalidUsername() throws IOException {
		RestAssured.baseURI = testData.getProperty("auth.baseurl");
		Response response = given().auth().preemptive()
				.basic(testData.getProperty("invalid.commandcenter.user").trim(),
						testData.getProperty("commandcenter.password").trim())
				.when().post(ApiPath.POST_SIGNIN).then().log().all().extract().response();
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(response);
		logStep("Validating Response Code");
		CCUtils.assertEquals(response.getStatusCode(), 400);
		signInValidations.getErrorInvalidUsername(response);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating Simple Sign In Functionality with invalid password.")
	public void testErrorInvalidPassword() throws IOException {
		RestAssured.baseURI = testData.getProperty("auth.baseurl");
		Response response = given().auth().preemptive()
				.basic(testData.getProperty("commandcenter.user").trim(),
						testData.getProperty("invalid.commandcenter.password").trim())
				.when().post(ApiPath.POST_SIGNIN).then().log().all().extract().response();
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(response);
		logStep("Validating Response Code");
		CCUtils.assertEquals(response.getStatusCode(), 400);
		signInValidations.getErrorInvalidPassword(response);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating Simple Sign In Functionality with invalid username and password.")
	public void testErrorPasswordAttemptsExceeded() throws IOException {
		Response response = null;
		for (int i = 0; i < 7; i++) {
			RestAssured.baseURI = testData.getProperty("auth.baseurl");
			response = given().auth().preemptive()
					.basic(testData.getProperty("commandcenter.user.six.attempts").trim(),
							testData.getProperty("commandcenter.password.six.attempts").trim())
					.when().post(ApiPath.POST_SIGNIN).then().log().all().extract().response();
			logStep("Validating Response Time");
			CCUtils.responseTimeValidation(response);
			logStep("Validating Response Code");
			CCUtils.assertEquals(response.getStatusCode(), 400);
		}
		signInValidations.getErrorPasswordAttemptsExceeded(response);
	}

}
