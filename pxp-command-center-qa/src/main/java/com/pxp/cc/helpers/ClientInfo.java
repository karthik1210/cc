// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.helpers;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.util.Map;
import com.pxp.cc.apipath.ApiPath;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.pojos.Client;
import io.restassured.response.Response;

public class ClientInfo extends BaseRest {

	public Response getClientDetails(String clientId) {

		String url = ApiPath.GET_CLIENT + clientId;
		return given().spec(requestSpec).when().get(url).then().extract().response();
	}

	public Response createNewClientUpdateClient(String active, String clientId, String clientName, String sapNumber,
			String testClient, String type, String timezoneId) throws IOException {

		Map<String, Object> createNewClientPayload = Client.postCreateClientUpdateClient(active, clientId, clientName,
				sapNumber, testClient, type, timezoneId);

		return given().spec(requestSpec).body(createNewClientPayload).when().post(ApiPath.POST_CLIENT).then().extract()
				.response();
	}

}
