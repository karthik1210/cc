package com.pxp.cc.util;

import java.util.ArrayList;
import java.util.List;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.pxp.cc.helpers.ClientDirectory;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ClientTopNavDropdownValidations {
	JsonPath jsonPath;
	private ClientDirectory clientDirectory = new ClientDirectory();

	public void verifyExistingFavClientsGetDeleted(Response response) {
		List<Object> responseFavData = null;
		responseFavData = new ArrayList<Object>();
		responseFavData.addAll(response.jsonPath().getList("favoriteClients.id"));
		if (responseFavData.isEmpty()) {
			Log4jUtil.log("No clients is within Favorites list.");
		} else {
			Log4jUtil.log("Print responseData : " + responseFavData);
			for (Object ClientId : responseFavData) {
				String s = ClientId.toString();
				Response respons = clientDirectory.deleteFavoritesClient(s);
				CCUtils.assertEquals(respons.getStatusCode(), 200);
			}
		}
	}

}
