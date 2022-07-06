//Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.util;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class LocationsValidations {

	JsonPath jsonPath;

	public void verifySearchLocationOutputWithoutFilter(Response response, String getSearchLocations) {
		jsonPath = new JsonPath(response.asString());
		Log4jUtil.log("Display list of locations in response body : " + jsonPath.get("items"));
		Log4jUtil.log("Display total count of locations in response body : " + jsonPath.get("totalCount"));
		Log4jUtil.log("Get the size of items in response body : " + jsonPath.getInt("items.size()"));
		Log4jUtil.log("Comparing Searched Items names/ids with location data received in Response body.");
		for (int dataArrayIteration = 0; dataArrayIteration < jsonPath.getInt("items.size()"); dataArrayIteration++) {
			String locationIteratePerData = jsonPath.get("items[" + dataArrayIteration + "].name");
			Log4jUtil.log("Print Value : " + locationIteratePerData);
			if (locationIteratePerData.contains(getSearchLocations)) {
				Log4jUtil.log("searched value :" + getSearchLocations + "present within response body's value"
						+ locationIteratePerData);
				CCUtils.assertTrue(true,
						"Found data " + locationIteratePerData + " is present in expected data" + getSearchLocations);
			}
		}
	}
}
