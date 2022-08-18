// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.pxp.nextgen.cc.business.layer.testcases.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.helpers.ClientDirectory;
import com.pxp.cc.model.BaseRest;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.ClientDirectoryValidations;
import com.pxp.cc.util.Validations;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class FavoriteUnFavoriteClientApiTests_BusinessLayer extends BaseRest {

	private ClientDirectory clientDirectory;
	private ClientDirectoryValidations clientDirectoryValidations;
	private Validations validation;
	JsonPath jsonPath;
	List<String> list = null;

	@BeforeClass()
	public void setBaseUri() throws Exception {
		setupQaServiceRequestSpecificationBuilder();
		clientDirectory = new ClientDirectory();
		clientDirectoryValidations = new ClientDirectoryValidations();
		validation = new Validations();
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating 400 response code if we make non-existing Client as favorite.")
	public void testClientIdNotFoundErrorWhileFavoritingClient() {
		Response selectFavClient = clientDirectory
				.selectClientFavorite(testData.getProperty("api.nonexisting.full.command.center.id"));
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(selectFavClient);
		CCUtils.assertEquals(selectFavClient.getStatusCode(), 400);
		clientDirectoryValidations.verifyClientIdNotFoundErrorMsgWhileFavoritingClient(selectFavClient,
				testData.getProperty("api.nonexisting.full.command.center.id"));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Validating 400 response code if we make more than five clients as favorite.")
	public void testMoreThanFiveFavoritedClientsError() throws Exception {
		Response searchClientDirResult = clientDirectory.getClientFromClientDirectoryWithoutFilter("",
				testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim());
		List<Integer> list = clientDirectoryValidations.verifyFavoriteClientOutputWithoutFilter(searchClientDirResult,
				"");
		logStep("Print the list of favorite clients" + list);
		logStep("Delete all favorite clients");
		clientDirectoryValidations.verifyExistingFavClientsIsFive(list);
		List<String> listOfClientToBeFavorited = listOfFavoritingClients();
		for (String noOfFavoritingClients : listOfClientToBeFavorited) {
			Response favoritingMoreThanFiveClients = clientDirectory.selectClientFavorite(noOfFavoritingClients);
			logStep("Validating Response Time : " + favoritingMoreThanFiveClients.toString());
			CCUtils.responseTimeValidation(favoritingMoreThanFiveClients);
			logStep("Validating Response Code");
			clientDirectoryValidations.verifySixthClientCantBeFavorited(favoritingMoreThanFiveClients,
					noOfFavoritingClients);
		}
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Select Client as Favorite in Client Directory")
	public void testSelectClientAsFavorite() throws IOException {
		Response searchClientDirResult = clientDirectory.getClientFromClientDirectoryWithoutFilter("",
				testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim());
		List<Integer> list = clientDirectoryValidations.verifyFavoriteClientOutputWithoutFilter(searchClientDirResult,
				"");
		logStep("Print the list of favorite clients" + list);
		clientDirectoryValidations.verifyExistingFavClientsIsFive(list);
		Response selectFavClient = clientDirectory.selectClientFavorite(testData.getProperty("favoriting.client.one"));
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(selectFavClient);
		CCUtils.assertEquals(selectFavClient.getStatusCode(), 200);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Deselect Favorite Client as not favorite in Client Directory")
	public void testDeleteFavoritesClient() throws IOException {
		Response checkNumberOfFavClients = clientDirectory.getClientFromClientDirectoryWithoutFilter("",
				testData.getProperty("api.page").trim(), testData.getProperty("api.pageSize1").trim());
		List<Integer> list = clientDirectoryValidations.verifyFavoriteClientOutputWithoutFilter(checkNumberOfFavClients,
				"");
		logStep("Print the list of favorite clients" + list);
		if (list == null) {
			clientDirectory.selectClientFavorite(testData.getProperty("favoriting.client.one"));
			Response deselectFavClient = clientDirectory
					.deleteFavoriteClient(Integer.parseInt(testData.getProperty("favoriting.client.one")));
			logStep("Validating Response Time");
			CCUtils.responseTimeValidation(deselectFavClient);
			logStep("Validating Response Code");
			CCUtils.assertEquals(deselectFavClient.getStatusCode(), 200);
		} else {
			for (int i : list) {
				Response deselectFavClient = clientDirectory.deleteFavoriteClient(i);
				logStep("Validating Response Time");
				CCUtils.responseTimeValidation(deselectFavClient);
				logStep("Validating Response Code");
				CCUtils.assertEquals(deselectFavClient.getStatusCode(), 200);
			}
		}
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid url and validate error status code")
	public void testErrorMessageForInvalidUrl() throws IOException {
		Response selectFavClient = clientDirectory
				.selectClientFavoriteWithInvalidUrl(testData.getProperty("favoriting.client.one"));
		logStep("Validating Response Time");
		CCUtils.responseTimeValidation(selectFavClient);
		logStep("Validating Response Information");
		CCUtils.responseTimeValidation(selectFavClient);
		CCUtils.assertEquals(selectFavClient.getStatusCode(), 404);
		validation.verifyErrorInResponseForInvalidUrl(selectFavClient);
	}
}
