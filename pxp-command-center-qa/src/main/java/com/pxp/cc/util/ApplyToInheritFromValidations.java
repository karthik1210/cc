package com.pxp.cc.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.pxp.cc.pojos.Solutions;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ApplyToInheritFromValidations {

	JsonPath jsonPath;
	
	public void validateNonExistingSourcePractice(Response response, String PracId) {
		String msg = response.jsonPath().get("message").toString();
		Log4jUtil.log("Print Message : "+msg);
		Log4jUtil.log("Now will validating the message in Response body");
		CCUtils.assertEquals(msg, "Source org id=" + PracId + " doesn't exist");
	}
	
	public void validateSourcePracIdTargetPracIdSameErrorMsg(Response response) {
		String msg = response.jsonPath().get("message").toString();
		Log4jUtil.log("Print Message : "+msg);
		Log4jUtil.log("Now will validating the message in Response body");
		CCUtils.assertEquals(msg, "Source and target practices can not same");
	}
	
	public void validateSourcePracIdNullSameErrorMsg(Response response) {
		String msg = response.jsonPath().get("message").toString();
		Log4jUtil.log("Print Message : "+msg);
		Log4jUtil.log("Now will validating the message in Response body");
		CCUtils.assertEquals(msg, "Source practices cannot be empty or null");
	}
	
	public void validateTargetPracIdNullSameErrorMsg(Response response) {
		String msg = response.jsonPath().get("message").toString();
		Log4jUtil.log("Print Message : "+msg);
		Log4jUtil.log("Now will validating the message in Response body");
		CCUtils.assertEquals(msg, "Target practices cannot be empty or null");
	}
	
	public void validateNonExistingTargetPractice(Response response, String PracId) {
		String msg = response.jsonPath().get("message").toString();
		Log4jUtil.log("Print Message : "+msg);
		Log4jUtil.log("Now will validating the message in Response body");
		CCUtils.assertEquals(msg, "Target org id=" + PracId + " doesn't exist");
	}
	public void validateResponseOfGetOrg(Response response) {
		List<Object> jsonArray = response.jsonPath().getList("solutions");
		Log4jUtil.log(jsonArray.toString());
	}
}
