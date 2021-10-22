// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.util;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

import io.restassured.response.Response;

public class SignInValidations {

	public void getErrorInvalidUsername(Response response) {
		String msgValue = response.jsonPath().get("message").toString().trim();
		Log4jUtil.log(msgValue);
		Log4jUtil.log(
				"Validating Response body message key with value : " + (response.jsonPath().get("message").toString()));
		CCUtils.assertEquals(msgValue, "Invalid Username or password");
	}

	public void getErrorInvalidPassword(Response response) {
		String msgValue = response.jsonPath().get("message").toString().trim();
		Log4jUtil.log(msgValue);
		Log4jUtil.log(
				"Validating Response body message key with value : " + (response.jsonPath().get("message").toString()));
		CCUtils.assertEquals(msgValue, "Invalid username or password");
	}

	public void getErrorPasswordAttemptsExceeded(Response response) {
		String msgValue = response.jsonPath().get("message").toString().trim();
		Log4jUtil.log(msgValue);
		Log4jUtil.log(
				"Validating Response body message key with value : " + (response.jsonPath().get("message").toString()));
		CCUtils.assertEquals(msgValue, "Password attempts exceeded, please contact your Command Center administrator.");
	}
}
