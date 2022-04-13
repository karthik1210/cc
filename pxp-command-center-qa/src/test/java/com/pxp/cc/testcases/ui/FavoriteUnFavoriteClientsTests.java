// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 
package com.pxp.cc.testcases.ui;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;

public class FavoriteUnFavoriteClientsTests extends CommandCenterGenericTest {

	ClientDirectoryPage clientDirectoryPage;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage = new ClientDirectoryPage(driver, ccuser.getUrl());
	}

	public List<String> listOfFavClients() throws Exception {
		List<String> listOfFavoritingClient = new ArrayList<String>();
		listOfFavoritingClient.add(ccuser.getProperty("favorite.client.one"));
		listOfFavoritingClient.add(ccuser.getProperty("favorite.client.two"));
		listOfFavoritingClient.add(ccuser.getProperty("favorite.client.three"));
		listOfFavoritingClient.add(ccuser.getProperty("favorite.client.four"));
		listOfFavoritingClient.add(ccuser.getProperty("favorite.client.five"));
		listOfFavoritingClient.add(ccuser.getProperty("favorite.client.six"));
		return listOfFavoritingClient;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to favorite a client from client directory")
	public void testFavoriteClient() throws Exception {
		logStep("Verfying whether favorited client count is equal or less than five");
		boolean value = clientDirectoryPage.getNoOfFavoriteClients();
		clientDirectoryPage.verifyNoOfFavoriteClient(value);
		clientDirectoryPage.searchNode(ccuser.getProperty("favorite.client.name"));
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.FavoriteAClient();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to favorite a client from client directory")
	public void testMoreThanClientFavoritedToolTipError() throws Exception {
		logStep("Verfying whether favorited client count is equal or less than five");
		int noOfFavClients = clientDirectoryPage.getSizeListOfFavClients();
		log("Print no of Fav clients : " + noOfFavClients);
		clientDirectoryPage.validateFavClientsAndUnFavThem(noOfFavClients);
		List<String> listOfFavoritingClient = listOfFavClients();
		clientDirectoryPage.listOfFavoritingClients(listOfFavoritingClient);

	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to favorite a client from client directory")
	public void testUnFavoriteClient() throws Exception {
		logStep("Verfying whether favorited client count is equal or less than five");
		int noOfFavClients = clientDirectoryPage.getSizeListOfFavClients();
		boolean data = clientDirectoryPage.checkNoOfFavClientsAndUnFavoriteThem(noOfFavClients);
		clientDirectoryPage.FavoriteClientThenUnfavorite(data, ccuser.getProperty("favorite.client.name"));
	}
}
