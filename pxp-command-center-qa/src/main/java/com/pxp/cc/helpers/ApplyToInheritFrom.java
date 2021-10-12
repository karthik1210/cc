// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;

import io.restassured.response.Response;

public class ApplyToInheritFrom extends BaseRest {

	public Response applyInheritSolutions(List<Integer> sourcePractices, List<Integer> targetPractices)
			throws IOException {
		List<Integer> sourcePrac = sourcePractices;
		List<Integer> targetPrac = targetPractices;
		Map<String, Object> applyToInheritFromPayload = putSourceIdTargetId(sourcePrac, targetPrac);
		return given().spec(requestSpec).body(applyToInheritFromPayload).when().put(ApiPath.PUT_APPLYTO_INHERITFROM)
				.then().extract().response();
	}

	public Response applySolutionsWhereSourceIDNull(List<Integer> targetPractices) throws IOException {
		List<Integer> targetPrac = targetPractices;
		Map<String, Object> applyToInheritFromPayload = putSourceIdAsNull(targetPrac);
		return given().spec(requestSpec).body(applyToInheritFromPayload).when().put(ApiPath.PUT_APPLYTO_INHERITFROM)
				.then().extract().response();
	}

	public Response applySolutionsWhereTargetIDNull(List<Integer> sourcePractices) throws IOException {
		List<Integer> sourcePrac = sourcePractices;
		Map<String, Object> applyToInheritFromPayload = putTargetIdAsNull(sourcePrac);
		return given().spec(requestSpec).body(applyToInheritFromPayload).when().put(ApiPath.PUT_APPLYTO_INHERITFROM)
				.then().extract().response();
	}

	public static Map<String, Object> putTargetIdAsNull(List<Integer> sourcePractices) {
		Map<String, Object> systemMap = new HashMap<String, Object>();
		systemMap.put("sourcePractices", sourcePractices);
		systemMap.put("targetPractices", null);
		return systemMap;
	}

	public static Map<String, Object> putSourceIdAsNull(List<Integer> targetPractices) {
		Map<String, Object> systemMap = new HashMap<String, Object>();
		systemMap.put("sourcePractices", null);
		systemMap.put("targetPractices", targetPractices);
		return systemMap;
	}

	public static Map<String, Object> putSourceIdTargetId(List<Integer> sourcePractices,
			List<Integer> targetPractices) {
		Map<String, Object> systemMap = new HashMap<String, Object>();
		systemMap.put("sourcePractices", sourcePractices);
		systemMap.put("targetPractices", targetPractices);
		return systemMap;
	}
}
