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

	public void verifyClickedClientPresentInDropdownHeaderRecentVisitsSection(Response response, String clientName) {
		List<Object> responseFavData = null;
		responseFavData = new ArrayList<Object>();
		String getResponseBodyClientName = response.jsonPath().get("selectedClient.name");
		Log4jUtil.log("display the name of client in response body : " + getResponseBodyClientName);
		Log4jUtil.log("Validating viewed client name with response body Selected client name.");
		CCUtils.assertEquals(getResponseBodyClientName, clientName);
		responseFavData.addAll(response.jsonPath().getList("recentlyVisitedClients.name"));
		Log4jUtil.log("Validating viewed client name with response body recently visited first client name.");
		for (Object ClientName : responseFavData) {
			String recentVisitsFirstClientName = ClientName.toString();
			CCUtils.assertEquals(recentVisitsFirstClientName, clientName);
			break;
		}
	}

	public void verifyOnlyFiveRecentVisitsClients(Response response, List<String> listOfVisitedClients) {
		List<Object> responseRecentVisitsData = null;
		List<String> listOfProvidedClientsName = new ArrayList<String>();
		responseRecentVisitsData = new ArrayList<Object>();
		responseRecentVisitsData.addAll(response.jsonPath().getList("recentlyVisitedClients.name"));
		for (int i = listOfVisitedClients.size() - 1; i > 0; i--) {
			listOfProvidedClientsName.add(listOfVisitedClients.get(i).toString());
		}
		if (listOfProvidedClientsName.equals(responseRecentVisitsData) == true) {
			System.out.println("values are equal");
			CCUtils.assertEquals(listOfProvidedClientsName, responseRecentVisitsData);
		}

	}

}
