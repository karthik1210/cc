// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.util;

import java.util.List;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ApplyToInheritFromValidations {

	JsonPath jsonPath;

	public void validateNonExistingSourcePractice(Response response, String PracId) {
		String msg = response.jsonPath().get("message").toString();
		Log4jUtil.log("Print Message : " + msg);
		CCUtils.assertEquals(msg, "Source org id=" + PracId + " doesn't exist");
	}

	public void validateSourcePracIdTargetPracIdSameErrorMsg(Response response) {
		String msg = response.jsonPath().get("message").toString();
		Log4jUtil.log("Print Message : " + msg);
		CCUtils.assertEquals(msg, "Source and target practices can not same");
	}

	public void validateSourcePracIdNullSameErrorMsg(Response response) {
		String msg = response.jsonPath().get("message").toString();
		Log4jUtil.log("Print Message : " + msg);
		CCUtils.assertEquals(msg, "Source practices cannot be empty or null");
	}

	public void validateTargetPracIdNullSameErrorMsg(Response response) {
		String msg = response.jsonPath().get("message").toString();
		Log4jUtil.log("Print Message : " + msg);
		CCUtils.assertEquals(msg, "Target practices cannot be empty or null");
	}

	public void validateNonExistingTargetPractice(Response response, String PracId) {
		String msg = response.jsonPath().get("message").toString();
		Log4jUtil.log("Print Message : " + msg);
		CCUtils.assertEquals(msg, "Target org id=" + PracId + " doesn't exist");
	}

	public void fetchResponseOfGetOrg(Response response) {
		List<Object> jsonArray = response.jsonPath().getList("solutions");
		Log4jUtil.log(jsonArray.toString());
	}
}