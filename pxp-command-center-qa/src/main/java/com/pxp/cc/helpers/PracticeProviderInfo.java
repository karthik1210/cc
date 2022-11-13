// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;

import io.restassured.response.Response;

public class PracticeProviderInfo extends BaseRest {

	public Response getPracticeProvder_DL(int practiceId, int providerId) {
		String url = ApiPath.GET_PRACTICES_PROVIDERS(practiceId, providerId);
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response getPracticeProvder_NullValues() {
		String url = ApiPath.GET_PRACTICES_PROVIDERS_NULL_VALUES;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response putPracticeProvder_DL(int practiceId, int providerId, String additionalProp1,
			String additionalProp2, String additionalProp3, String emailId, String mobile) {
		String url = ApiPath.GET_PRACTICES_PROVIDERS(practiceId, providerId);
		Map<String, Object> createPayLoad = createPutProviderRequestPayload(additionalProp1, additionalProp2,
				additionalProp3, emailId, mobile);
		return given().spec(requestSpec).body(createPayLoad).when().put(url).then().extract().response();
	}

	private Map<String, Object> createPutProviderRequestPayload(String additionalProp1, String additionalProp2,
			String additionalProp3, String emailId, String mobile) {
		Map<String, Object> createProviderMap = new HashMap<String, Object>();
		Map<String, Object> createPutProviderMap = createPutProviderRequestPayload(additionalProp1, additionalProp2,
				additionalProp3);
		createProviderMap.put("displayName", createPutProviderMap);
		createProviderMap.put("email", emailId);
		createProviderMap.put("mobile", mobile);
		return createProviderMap;
	}

	private Map<String, Object> createPutProviderRequestPayload(String additionalProp1, String additionalProp2,
			String additionalProp3) {
		Map<String, Object> createDisplayNameMap = new HashMap<String, Object>();
		createDisplayNameMap.put("additionalProp1", additionalProp1);
		createDisplayNameMap.put("additionalProp2", additionalProp2);
		createDisplayNameMap.put("additionalProp3", additionalProp3);
		return createDisplayNameMap;
	}
}
