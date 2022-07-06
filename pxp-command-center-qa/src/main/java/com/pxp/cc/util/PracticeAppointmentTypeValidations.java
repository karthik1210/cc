//Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.util;

import org.json.JSONArray;
import org.json.JSONObject;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import io.restassured.response.Response;

public class PracticeAppointmentTypeValidations {

	JSONArray jsonArray;
	JSONObject jsonObject;

	public void verifyNoOfResourcesFetchedResourceNameandId(Response response) {
		jsonArray = new JSONArray(response.getBody().asString());
		CCUtils.assertTrue(jsonArray.length() > 0, "Product list is empty");
		Log4jUtil.log("Number of resources fetched is " + jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			Log4jUtil.log("print the resource name : " + jsonObject.getString("name") + " and  resource Id : "
					+ jsonObject.get("resourceId"));
		}
	}

	public void verifyNoOfSolutionsFetchedSolutionNameandId(Response response) {
		jsonArray = new JSONArray(response.getBody().asString());
		CCUtils.assertTrue(jsonArray.length() > 0, "Product list is empty");
		Log4jUtil.log("Number of resources fetched is " + jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			Log4jUtil.log("print the resource name : " + jsonObject.getString("name") + " and  resource Id : "
					+ jsonObject.get("solutionId"));
		}
	}
}
