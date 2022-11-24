// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddOrgNodePage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class FavoriteUnFavoriteClientsTests extends CommandCenterGenericTest {

	ClientDirectoryPage clientDirectoryPage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;

	String clientName, clientSapNumber, externalId, clientNameOne, clientNameTwo, clientNameThree, clientNameFour,
			clientNamefive, clientNameSix;
	public AddOrgNodePage addOrgNodePage;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage = new ClientDirectoryPage(driver);
	}

	public String createClient(String clientName) throws NullPointerException, InterruptedException {
		logStep("Select create client button from new dropdown");
		createClientPage = new CreateClientPage(driver);
		commonMethods = new CommonMethods(driver);
		createClientPage.clickCreateClient();
		logStep("Enter valid details in mandatory fields");
		clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20)).trim();
		clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20)).trim();
		createClientPage.createNewClient(clientName, clientSapNumber, testData.getProperty("new.client.type"),
				testData.getProperty("new.client.timezone"));
		createClientPage.clickConfirmPopup();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! " + clientName + " has been successfully created");
		return clientName;
	}

	public List<String> listOfFavClients() throws Exception {
		clientNameOne = createClient(clientName);
		clientNameTwo = createClient(clientName);
		clientNameThree = createClient(clientName);
		clientNameFour = createClient(clientName);
		clientNamefive = createClient(clientName);
		clientNameSix = createClient(clientName);
		List<String> listOfFavoritingClient = new ArrayList<String>();
		listOfFavoritingClient.add(clientNameOne);
		listOfFavoritingClient.add(clientNameTwo);
		listOfFavoritingClient.add(clientNameThree);
		listOfFavoritingClient.add(clientNameFour);
		listOfFavoritingClient.add(clientNamefive);
		listOfFavoritingClient.add(clientNameSix);
		return listOfFavoritingClient;
	}

	@Test(enabled = true, groups = {
			"smoke" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to favorite a client from client directory")
	public void testFavoriteClient() throws Exception {
		clientNameOne = createClient(clientName);
		logStep("Verfying whether favorited client count is equal or less than five");
		boolean value = clientDirectoryPage.getNoOfFavoriteClients();
		clientDirectoryPage.verifyNoOfFavoriteClient(value);
		clientDirectoryPage.searchNode(clientNameOne);
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.FavoriteAClient();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to favorite more than 5 client from client directory")
	public void testMoreThanFiveClientFavoritedError() throws Exception {
		logStep("Verfying whether favorited client count is equal or less than five");
		int noOfFavClients = clientDirectoryPage.getSizeListOfFavClients();
		log("Print no of Fav clients : " + noOfFavClients);
		clientDirectoryPage.validateFavClientsAndUnFavThem(noOfFavClients);
		List<String> listOfFavoritingClient = listOfFavClients();
		clientDirectoryPage.listOfFavoritingClients(listOfFavoritingClient);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to unfavorite a client from client directory")
	public void testUnFavoriteClient() throws Exception {
		logStep("Verfying whether favorited client count is equal or less than five");
		int noOfFavClients = clientDirectoryPage.getSizeListOfFavClients();
		boolean data = clientDirectoryPage.checkNoOfFavClientsAndUnFavoriteThem(noOfFavClients);
		clientDirectoryPage.FavoriteClientThenUnfavorite(data, ccuser.getProperty("favorite.client.name"));
	}
}
