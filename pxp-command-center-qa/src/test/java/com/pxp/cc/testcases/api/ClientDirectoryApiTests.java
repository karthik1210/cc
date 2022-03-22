// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientDirectory;
import com.pxp.cc.helpers.GeneralSettingsClientDropdown;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.ClientDirectoryValidations;
import com.pxp.cc.util.ClientTopNavDropdownValidations;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ClientDirectoryApiTests extends BaseRest {

	private ClientDirectory clientDirectory;
	private GeneralSettingsClientDropdown generalSettingsDropdown;
	private ClientTopNavDropdownValidations clientTopNavDropdownValidations = new ClientTopNavDropdownValidations();
	private ClientDirectoryValidations clientDirectoryValidations;
	JsonPath jsonPath;
	List<String> list = null;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupServiceRequestSpecBuilder();
		clientDirectory = new ClientDirectory();
		generalSettingsDropdown = new GeneralSettingsClientDropdown();
		clientTopNavDropdownValidations = new ClientTopNavDropdownValidations();
		clientDirectoryValidations = new ClientDirectoryValidations();
	}

	@DataProvider(name = "clientdatawithoutfilter")
	public Object[] searchClientDetailsWithoutFilter() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("api.query.full.client.name").trim(), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim() },
				{ testData.getProperty("api.query.partial.client.name").trim(), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim() },
				{ testData.getProperty("api.query.full.command.center.id").trim(),
						testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim() },
				{ testData.getProperty("api.query.partial.command.center.id").trim(),
						testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim() },
				{ testData.getProperty("api.query.full.system.name").trim(), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim() },
				{ testData.getProperty("api.query.partial.system.name").trim(), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim() },
				{ testData.getProperty("api.query.full.enterprise.name").trim(),
						testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim() },
				{ testData.getProperty("api.query.partial.enterprise.name").trim(),
						testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim() },
				{ testData.getProperty("api.query.full.practice.name").trim(), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim() },
				{ testData.getProperty("api.query.partial.practice.name").trim(),
						testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim() } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "clientdatawithoutfilter", description = "search client nodes in client directory by keeping Items per page as 10 and current page as 1-10")
	public void testSearchClientOutputWithoutFilter(String[] getSearchClientDetails) throws IOException {
		Response searchClientDirResult = clientDirectory.getClientFromClientDirectoryWithoutFilter(
				getSearchClientDetails[0], getSearchClientDetails[1], getSearchClientDetails[2]);
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(searchClientDirResult);
		logStep("Validating Response Code");
		CCUtils.assertEquals(searchClientDirResult.getStatusCode(), 200);
		clientDirectoryValidations.verifyClientOutputWithoutFilter(searchClientDirResult, getSearchClientDetails[0]);
	}

	@DataProvider(name = "clientdatawithfilter")
	public Object[] searchClientDetailsWithFilter() {
		ArrayList<String> filterType = new ArrayList<String>();
		filterType.add(ccUser.getClientOneType());
		filterType.add(ccUser.getClientElevenType());
		filterType.add(ccUser.getClientTwoType());
		filterType.add(ccUser.getClientEightType());
		Object[][] obj = new Object[][] {
				{ testData.getProperty("api.query.full.client.name").trim(), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim(), filterType },
				{ testData.getProperty("api.query.partial.client.name").trim(), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim(), filterType },
				{ testData.getProperty("api.query.full.command.center.id").trim(),
						testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim(),
						filterType },
				{ testData.getProperty("api.query.partial.command.center.id"), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim(), filterType },
				{ testData.getProperty("api.query.full.system.name"), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim(), filterType },
				{ testData.getProperty("api.query.partial.system.name").trim(), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim(), filterType },
				{ testData.getProperty("api.query.full.enterprise.name").trim(),
						testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim(),
						filterType },
				{ testData.getProperty("api.query.partial.enterprise.name").trim(),
						testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim(),
						filterType },
				{ testData.getProperty("api.query.full.practice.name").trim(), testData.getProperty("api.page").trim(),
						testData.getProperty("api.pageSize1").trim(), filterType },
				{ testData.getProperty("api.query.partial.practice.name").trim(),
						testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim(),
						filterType } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "clientdatawithfilter", description = "search client nodes by applying multiselect Client Type Filter in client directory by keeping Items per page as 10 and current page as 1-10")
	public void testSearchClientOutputWithFilter(Object[] clientDataWithFilter) throws IOException {
		System.out.println("Type :" + clientDataWithFilter[3]);
		Response searchClientDirResultWithFilter = clientDirectory.getClientFromClientDirectoryWithFilter(
				clientDataWithFilter[0].toString(), clientDataWithFilter[1].toString(),
				clientDataWithFilter[2].toString(), clientDataWithFilter[3]);
		logStep("Validating Response Time : " + searchClientDirResultWithFilter.toString());
		CCUtils.responseTimeValidation(searchClientDirResultWithFilter);
		logStep("Validating Response Code");
		CCUtils.assertEquals(searchClientDirResultWithFilter.getStatusCode(), 200);
		clientDirectoryValidations.verifyClientOutputWithFilter(searchClientDirResultWithFilter,
				clientDataWithFilter[0], clientDataWithFilter[3]);
	}

	@DataProvider(name = "clientdatanonexisting")
	public Object[] searchClientDetails() {
		Object[][] obj = new Object[][] {
				{ testData.getProperty("api.nonexisting.full.client.name").trim(),
						testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim() },
				{ testData.getProperty("api.nonexisting.full.command.center.id").trim(),
						testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim() },
				{ testData.getProperty("api.nonexisting.full.system.name").trim(),
						testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim() },
				{ testData.getProperty("api.nonexisting.full.enterprise.name").trim(),
						testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim() },
				{ testData.getProperty("api.nonexisting.full.practice.name").trim(),
						testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim() } };
		return obj;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "clientdatanonexisting", description = "search non existing client/client nodes in client directory by keeping Items per page as 10 and current page as 1-10")
	public void testSearchNonExistingClient(Object[] clientDataNonExisting) {
		Response nonexistingClient = clientDirectory.getClientFromClientDirectoryWithoutFilter(
				clientDataNonExisting[0].toString(), clientDataNonExisting[1].toString(),
				clientDataNonExisting[2].toString());
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(nonexistingClient);
		logStep("Validating Response Code");
		CCUtils.assertEquals(nonexistingClient.getStatusCode(), 200);
		jsonPath = new JsonPath(nonexistingClient.asString());
		logStep("Print data[] in response body : " + jsonPath.get("data"));
		int clientDataSize = jsonPath.getInt("data.size()");
		logStep("Get the size of data[] in response body : " + clientDataSize);
		if (clientDataSize == 0) {
			logStep("Client Data array in Response body is empty,so expected searched data : "
					+ clientDataNonExisting[0].toString() + " does not found in response body.");
		}
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Select Client as Favorite in Client Directory")
	public void testSelectClientAsFavorite() throws IOException {
		Response checkHowManyFavClients = generalSettingsDropdown
				.getClientFavRecentVisitsDropdown(testData.getProperty("favoriting.client.one"));
		clientDirectoryValidations.verifyExistingFavClientsNotMoreThanFive(checkHowManyFavClients);
		Response selectFavClient = clientDirectory
				.selectClientAsFavorite(testData.getProperty("favoriting.client.one"));
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(selectFavClient);
		CCUtils.assertEquals(selectFavClient.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Deselect Favorite Client as not favorite in Client Directory")
	public void testDeleteFavoritesClient() throws IOException {
		Response checkNumberOfFavClients = generalSettingsDropdown
				.getClientFavRecentVisitsDropdown(testData.getProperty("favoriting.client.one"));
		String clientId = clientDirectoryValidations.verifyHowManyExistingFavClients(checkNumberOfFavClients);
		logStep("Client Id which is getting de-favorited : " + clientId);
		if (clientId == null) {
			Response verfiyClientFavorited = clientDirectory
					.selectClientAsFavorite(testData.getProperty("favoriting.client.one"));
			logStep("Validating Response Code");
			CCUtils.assertEquals(verfiyClientFavorited.getStatusCode(), 200);
			Response deselectFavClient = clientDirectory
					.deleteFavoritesClient(testData.getProperty("favoriting.client.one"));
			logStep("Validating Response Time");
			CCUtils.responseTimeValidation(deselectFavClient);
			logStep("Validating Response Code");
			CCUtils.assertEquals(deselectFavClient.getStatusCode(), 200);
		} else {
			Response deselectFavClient = clientDirectory.deleteFavoritesClient(clientId);
			logStep("Validating Response Time");
			CCUtils.responseTimeValidation(deselectFavClient);
			logStep("Validating Response Code");
			CCUtils.assertEquals(deselectFavClient.getStatusCode(), 200);
		}

	}

	public List<String> listOfFavoritingClients() throws Exception {
		list = new ArrayList<String>();
		list.add(testData.getProperty("favoriting.client.one"));
		list.add(testData.getProperty("favoriting.client.two"));
		list.add(testData.getProperty("favoriting.client.three"));
		list.add(testData.getProperty("favoriting.client.four"));
		list.add(testData.getProperty("favoriting.client.five"));
		list.add(testData.getProperty("favoriting.client.six"));
		return list;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating 400 response code if we make more than five clients as favorite.")
	public void testMoreThanFiveFavoritedClientsError() throws Exception {
		Response getFavoriteClientsDetails = generalSettingsDropdown
				.getClientFavRecentVisitsDropdown(testData.getProperty("favoriting.client.one"));
		clientTopNavDropdownValidations.verifyExistingFavClientsGetDeleted(getFavoriteClientsDetails);
		list = listOfFavoritingClients();
		for (String noOfFavoritingClients : list) {
			Response favoritingMoreThanFiveClients = clientDirectory.selectClientAsFavorite(noOfFavoritingClients);
			logStep("Validating Response Time : " + favoritingMoreThanFiveClients.toString());
			CCUtils.responseTimeValidation(favoritingMoreThanFiveClients);
			logStep("Validating Response Code");
			clientDirectoryValidations.verifySixthClientCantBeFavorited(favoritingMoreThanFiveClients,
					noOfFavoritingClients);
		}

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating 400 response code if we make non-existing Client as favorite.")
	public void testClientIdNotFoundErrorWhileFavoritingClient() {
		Response selectFavClient = clientDirectory
				.selectClientAsFavorite(testData.getProperty("api.nonexisting.full.command.center.id"));
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(selectFavClient);
		CCUtils.assertEquals(selectFavClient.getStatusCode(), 400);
		clientDirectoryValidations.verifyClientIdNotFoundErrorMsgWhileFavoritingClient(selectFavClient,
				testData.getProperty("api.nonexisting.full.command.center.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating 400 response code if we unfavorite non-existing client.")
	public void testClientIdNotFoundErrorWhileUnFavoritingClient() {
		Response deselectFavClient = clientDirectory
				.deleteFavoritesClient(testData.getProperty("api.nonexisting.full.command.center.id"));
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(deselectFavClient);
		CCUtils.assertEquals(deselectFavClient.getStatusCode(), 400);
		clientDirectoryValidations.verifyClientIdNotFoundErrorMsgWhileUnFavoritingClient(deselectFavClient);
	}

}
