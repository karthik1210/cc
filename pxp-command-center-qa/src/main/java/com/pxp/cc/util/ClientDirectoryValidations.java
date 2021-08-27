package com.pxp.cc.util;

import java.util.List;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ClientDirectoryValidations {

	JsonPath jsonPath;
	
	public void verifyClientOutputWithoutFilter(Response response, String getSearchClientDetails)
	{
		jsonPath = new JsonPath(response.asString());
		Log4jUtil.log("Display list of clients in response body : " + jsonPath.get("data"));
		Log4jUtil.log("Get the size of clients in response body : " + jsonPath.getInt("data.size()"));
		Log4jUtil.log("Page Number : " + jsonPath.getInt("pageNo"));
		Log4jUtil.log("Items(Clients) per Page : " + jsonPath.getInt("pageSize"));
		Log4jUtil.log("Comparing Searched Items names/ids with client data received in Response body.");
		for (int dataArrayIteration = 0; dataArrayIteration < jsonPath.getInt("data.size()"); dataArrayIteration++) {
			String clientIteratePerData = jsonPath.get("data[" + dataArrayIteration + "].name");
			Log4jUtil.log("Print Value : " + clientIteratePerData);
			if (clientIteratePerData.contains(getSearchClientDetails)) {
				Log4jUtil.log("searched value :" + getSearchClientDetails + "present within response body's value"
						+ clientIteratePerData);
				CCUtils.assertTrue(true, "Found data " + clientIteratePerData + " is present in expected data"
						+ getSearchClientDetails);
			}
		}
	}

	public void verifyClientOutputWithFilter(Response response , Object searchedClientData, Object appliedWithFilter)
	{
		jsonPath = new JsonPath(response.asString());
		Log4jUtil.log("Print data[] in response body : " + jsonPath.get("data"));
		int clientdataSize = jsonPath.getInt("data.size()");
		List<String> filterType = (List<String>) appliedWithFilter;
		Log4jUtil.log("Comparing Searched Items names/ids with client data received in Response body.");
		for (int dataArrayIteration = 0; dataArrayIteration < clientdataSize; dataArrayIteration++) {
			String clientIteratePerData = jsonPath.get("data[" + dataArrayIteration + "].name");
			if (clientIteratePerData.contains(searchedClientData.toString())) {
				Log4jUtil.log("Searched value :" + searchedClientData + " is present within response body's value : "
						+ clientIteratePerData);
				CCUtils.assertTrue(true, "Found data " + clientIteratePerData + " is present in expected data : "
						+ searchedClientData.toString());
			}
		}
		Log4jUtil.log("Comparing Searched Items applied  Multifilter Type with client type data received in Response body.");
		for (int dataArrayIteration = 0; dataArrayIteration < clientdataSize; dataArrayIteration++) {
			String clientTypePerData = jsonPath.get("data[" + dataArrayIteration + "].type");
			for (int multiFilterIteration = 0; multiFilterIteration < filterType.size(); multiFilterIteration++) {
				if (clientTypePerData.contains(filterType.get(multiFilterIteration))) {
					CCUtils.assertTrue(true, "Found data : " + clientTypePerData + " is matches with expected data : "
							+ filterType.get(multiFilterIteration).toString());
				} else {
					Log4jUtil.log("Found data : " + clientTypePerData + " does not matches with expected data : "
							+ filterType.get(multiFilterIteration).toString());
				}
			}
		}
	}
	
	public void verifySixthClientCantBeFavorited(Response response, String clientId) {
		jsonPath = new JsonPath(response.getBody().asString());
		if (response.getStatusCode() == 400) {
			CCUtils.assertEquals(jsonPath.get("message").toString(),
					"User cannot set client as favourites as it has exceeded maximum limit of 5");
		} else {
			CCUtils.assertEquals(response.getStatusCode(), 200);
			Log4jUtil.log("Client Id : " + clientId + " is favorited.");
		}
	}

	public void verifyClientIdNotFoundErrorMsgWhileFavoritingClient(Response response, String clientId) {
		jsonPath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonPath.get("message").toString(), "Clientid=" + clientId + " doesn't exist");
	}

	public void verifyClientIdNotFoundErrorMsgWhileUnFavoritingClient(Response response) {
		jsonPath = new JsonPath(response.getBody().asString());
		CCUtils.assertEquals(jsonPath.get("message").toString(), "Client Id not found");
	}

}
