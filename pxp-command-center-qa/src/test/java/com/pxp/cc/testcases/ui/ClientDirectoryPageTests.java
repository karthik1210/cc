// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.util.CCUtils;

public class ClientDirectoryPageTests extends CommandCenterGenericTest {

	static boolean runOnce = true;
	public CCUI ccui;
	public static String clientName;
	public String clientNameOne, clientNameTwo, clientNameThree, clientNameFour, clientNamefive, clientNameSix,
			systemName;
	public ClientDirectoryPage clientDirectoryPageRefernce;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		if (runOnce) {
			ccui = new CCUI(testData, driver);
			logStep("Login to command center portal");
			clientDirectoryPageRefernce = ccui.getClientDirectoryPage();
			ccui.initializeTestData();
		}
	}

	public List<String> listOfFavClients() throws Exception {
		clientNameOne = ccui.getClient();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		clientNameTwo = ccui.getClient();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		clientNameThree = ccui.getClient();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		clientNameFour = ccui.getClient();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		clientNamefive = ccui.getClient();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		clientNameSix = ccui.getClient();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		List<String> listOfFavoritingClient = new ArrayList<String>();
		listOfFavoritingClient.add(clientNameOne);
		listOfFavoritingClient.add(clientNameTwo);
		listOfFavoritingClient.add(clientNameThree);
		listOfFavoritingClient.add(clientNameFour);
		listOfFavoritingClient.add(clientNamefive);
		listOfFavoritingClient.add(clientNameSix);
		return listOfFavoritingClient;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to Search Client name in Client Directory")
	public void testNewActiveClientCreationAndClientNameSearch() throws Exception {
		logStep("Searching a newly created client in client directory");
		clientName = ccui.getClient();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		clientDirectoryPageRefernce.searchNode(clientName);
		clientDirectoryPageRefernce.clickSearchButton();
		logStep("Verifying results for searched client");
		clientDirectoryPageRefernce.verfiySearchResult(clientName);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify error messages on create client popup")
	public void testErrorMessagesOnCreateClientPopUp() throws Exception {
		String searchClientName = ccui.getClient();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		String SapId = ccui.getGeneralSettingsPage().getSAPIdValueForBasicInfo();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		ccui.getNgHierarchyClientCreationPage().clickCreateClient();
		logStep("Enter already existing client name and sap id in client name and sap id field and verify error message");
		ccui.getNgHierarchyClientCreationPage().verifyClientNameAndSapUnique(searchClientName, SapId);
		logStep("Remove all characters from client name and sap id field and verify required error message is displayed for mandatory fields");
		String clientNam = searchClientName.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(6));
		String clientSapNumber = SapId.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(6));
		ccui.getNgHierarchyClientCreationPage().verifyRequiredErrorMessageForClientNameAndSapIdField(clientNam,
				clientSapNumber);
		logStep("Verify no items found message is displayed for client type and timezone field when user enter non existing client type and timezone");
		ccui.getNgHierarchyClientCreationPage().verifyNoItemsFoundMsgForClientTypeAndTimezoneField(
				testData.getProperty("new.client.type").concat(CCUtils.generateAlphaNumericString(4)),
				testData.getProperty("new.client.timezone").concat(CCUtils.generateAlphaNumericString(4)));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Create a new inactive test client and verify success toaster message")
	public void testNewInactiveClientCreation() throws Exception {
		clientName = ccui.getInactiveClient();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		String clientStatus = ccui.getGeneralSettingsPage().getClientStatus();
		CCUtils.assertEquals(clientStatus, "Inactive");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify create client popup")
	public void testCreateClientPopUp() throws Exception {
		logStep("Verify create client popup");
		ccui.getNgHierarchyClientCreationPage().clickCreateClient();
		ccui.getNgHierarchyClientCreationPage().verifyCreateClientPageTitle();
		ccui.getNgHierarchyClientCreationPage().verifyCreateClientPageSubTitle();
		ccui.getNgHierarchyClientCreationPage().verifyClientNameLabelTextAndTextboxDefaultValue();
		ccui.getNgHierarchyClientCreationPage().verifySapIdLabelTextAndTextboxDefaultValue();
		ccui.getNgHierarchyClientCreationPage().verifyClientTypeLabelTextAndDropDownDefaultValue();
		ccui.getCommonMethods().getClientTypeDropdownValue();
		ccui.getCommonMethods().verifyClientTypeDropdownValues(testData.getProperty("data.layer.service.base.url"));
		ccui.getNgHierarchyClientCreationPage().verifyTimezoneLabelTextAndDropDownDefaultValue();
		ccui.getCommonMethods().verifyTimezoneDropdownValues();
		ccui.getNgHierarchyClientCreationPage().verifyStatusLabelTextAndRadioButtons();
		ccui.getNgHierarchyClientCreationPage().verifyActiveRadioButtonIsByDefaultSelected();
		ccui.getNgHierarchyClientCreationPage().verifyTestLabelTextAndCheckboxDefaultValue();
		ccui.getNgHierarchyClientCreationPage().verifyCancelButtonIsDisplayed();
		ccui.getNgHierarchyClientCreationPage().verifyCloseWindowButtonIsDisplayed();
		ccui.getNgHierarchyClientCreationPage().verifyCreateButtonIsDisplayedAndIsDisabled();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to check No results found is displayed if client name not present")
	public void testToCheckNoResultsFound() throws Exception {
		logStep("Searching a non-existing client in client directory");
		clientDirectoryPageRefernce.searchNode(testData.getProperty("search.non.existing.client"));
		clientDirectoryPageRefernce.selectClientTypeDropDown();
		clientDirectoryPageRefernce.selectClientType(testData.getProperty("api.client.thirteen.type"));
		clientDirectoryPageRefernce.applyClientTypeFilter();
		clientDirectoryPageRefernce.clickSearchButton();
		logStep("Verifying no result found scenario");
		boolean result = clientDirectoryPageRefernce.testToCheckNoResultsFound();
		if (result == true) {
			logStep("Test Case Pass, No Result For search text is displayed ");
		} else {
			logStep("Matching Result for Searched string found, "
					+ "kindly provide client name that is not present in Commandcenter");
		}
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to check Default text of Search bar & functionality of Search Button")
	public void testDefaultTextInSearchBar() throws Exception {
		logStep("checking search button functionality");
		clientDirectoryPageRefernce.verfiySearchButtonFunctionality(ccuser.searchClientInClientDirectory);
	}

	@Test(enabled = true, groups = {
			"smoke" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to check results after ClientType filter is applied")
	public void testClientTypeFilters() throws Exception {
		logStep("Apply Client Type Filter in Client Directory.");
		clientDirectoryPageRefernce.selectClientTypeDropDown();
		clientDirectoryPageRefernce.selectClientType(ccuser.getClientThreeType());
		clientDirectoryPageRefernce.applyClientTypeFilter();
		logStep("Verify client type filter");
		clientDirectoryPageRefernce.verfiySearchResultForClientType(ccuser.getClientThreeType());
	}

	@Test(enabled = true, groups = {
			"smoke" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to favorite a client from client directory")
	public void testFavoriteClient() throws Exception {
		clientNameOne = ccui.getClient();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		logStep("Verfying whether favorited client count is equal or less than five");
		boolean value = clientDirectoryPageRefernce.getNoOfFavoriteClients();
		clientDirectoryPageRefernce.verifyNoOfFavoriteClient(value);
		clientDirectoryPageRefernce.searchNode(clientNameOne);
		clientDirectoryPageRefernce.clickSearchButton();
		clientDirectoryPageRefernce.FavoriteAClient();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to favorite more than 5 client from client directory")
	public void testMoreThanFiveClientFavoritedError() throws Exception {
		logStep("Verfying whether favorited client count is equal or less than five");
		int noOfFavClients = clientDirectoryPageRefernce.getSizeListOfFavClients();
		log("Print no of Fav clients : " + noOfFavClients);
		clientDirectoryPageRefernce.validateFavClientsAndUnFavThem(noOfFavClients);
		List<String> listOfFavoritingClient = listOfFavClients();
		clientDirectoryPageRefernce.listOfFavoritingClients(listOfFavoritingClient);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to unfavorite a client from client directory")
	public void testUnFavoriteClient() throws Exception {
		logStep("Verfying whether favorited client count is equal or less than five");
		int noOfFavClients = clientDirectoryPageRefernce.getSizeListOfFavClients();
		boolean data = clientDirectoryPageRefernce.checkNoOfFavClientsAndUnFavoriteThem(noOfFavClients);
		clientDirectoryPageRefernce.FavoriteClientThenUnfavorite(data, ccuser.getProperty("favorite.client.name"));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to check nodes can be searched in Client Directory screen")
	public void testClientNodeSearch() throws Exception {
		clientName = ccui.getClient();
		systemName = ccui.getSystem();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		logStep("Searching for node under a client in client directory page");
		clientDirectoryPageRefernce.searchNode(systemName);
		clientDirectoryPageRefernce.clickSearchButton();
		clientDirectoryPageRefernce.clickSearchResult();
		logStep("Verifying result in general setting screen");
		List<String> allNodesInClient = clientDirectoryPageRefernce.getClientNodeinGeneralSetting();
		for (int i = 0; i <= allNodesInClient.size(); i++) {
			String actualResultNode = allNodesInClient.get(i).toLowerCase();
			if (actualResultNode.contains(systemName.toLowerCase())) {
				logStep("Test case Pass, Node present under client is '" + allNodesInClient.get(i)
						+ "' & searched text is '" + systemName + "'");
				break;
			} else {
				log("Checking next node in Client, current node is " + allNodesInClient.get(i));
				continue;
			}
		}
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Verify Display of default pagination i.e., 10 items per page.")
	public void testDefaultItemsPerPageAndDefaultClientPaginationInClientWebTable()
			throws InterruptedException, RuntimeException {
		logStep("Now comparing the default Items per Page .Ideally it should be 10.");
		CCUtils.assertEquals(clientDirectoryPageRefernce.getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(clientDirectoryPageRefernce.getDefaultPagination(), "1 – 10");
		String noOfClients = clientDirectoryPageRefernce.getTotalNoOfClients();
		logStep("Get the Total No of Clients : " + noOfClients);
		int getNoOfRowsPagination = Integer.parseInt(clientDirectoryPageRefernce.getNoOfRowsPagination());
		logStep("value of no of rows effected in Pagination : " + getNoOfRowsPagination);
		CCUtils.assertEquals(clientDirectoryPageRefernce.isFirstPageButtonDisabled(), false);
		CCUtils.assertEquals(clientDirectoryPageRefernce.isPreviousPageButtonDisabled(), false);
		CCUtils.assertEquals(clientDirectoryPageRefernce.isNextPageButtonDisabled(), true);
		CCUtils.assertEquals(clientDirectoryPageRefernce.isLastPageButtonDisabled(), true);
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Verify numbers of rows and Pagination should be change it we changes 'Items Per Page' value.")
	public void testAllPaginationButtonsClickable() throws InterruptedException {
		logStep("Now comparing the default Items per Page" + ".Ideally it should be 10.");
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		CCUtils.assertEquals(clientDirectoryPageRefernce.getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(clientDirectoryPageRefernce.getDefaultPagination(), "1 – 10");
		String noOfClients = clientDirectoryPageRefernce.getTotalNoOfClients();
		logStep("Get the Total No of Clients : " + noOfClients);
		clientDirectoryPageRefernce.clickNextPageButton();
		clientDirectoryPageRefernce.clickNextPageButton();
		clientDirectoryPageRefernce.clickNextPageButton();
		clientDirectoryPageRefernce.clickPreviousPageButton();
		clientDirectoryPageRefernce.clickLastPageButton();
		clientDirectoryPageRefernce.clickFirstPageButton();
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 10.")
	public void testNextPageButtonWhenItemsPerPageValueisTen() throws InterruptedException {
		String defaultItemsPerPage = clientDirectoryPageRefernce.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		clientDirectoryPageRefernce
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 20.")
	public void testNextPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException {
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPageTwenty = clientDirectoryPageRefernce.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		clientDirectoryPageRefernce
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwenty));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 50.")
	public void testNextPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException {
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPageFifty = clientDirectoryPageRefernce.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		clientDirectoryPageRefernce
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 100.")
	public void testNextPageButtonWhenItemsPerPageValueisHundred() throws InterruptedException {
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPageHundred = clientDirectoryPageRefernce.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		clientDirectoryPageRefernce
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 200.")
	public void testNextPageButtonWhenItemsPerPageValueisTwoHundred() throws InterruptedException {
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = clientDirectoryPageRefernce.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		clientDirectoryPageRefernce
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 10.")
	public void testLastPageButtonWhenItemsPerPageValueisTen() throws InterruptedException {
		String defaultItemsPerPage = clientDirectoryPageRefernce.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		log("Print the RHS Pagination : " + clientDirectoryPageRefernce.getRHSPagination());
		clientDirectoryPageRefernce
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 20.")

	public void testLastPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException {
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPageRefernce.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		log("get default pagination : " + clientDirectoryPageRefernce.getDefaultPagination());
		log("Print the RHS Pagination : " + clientDirectoryPageRefernce.getRHSPagination());
		clientDirectoryPageRefernce.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 50.")
	public void testLastPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException {
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPageRefernce.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "50");
		log("Print the RHS Pagination : " + clientDirectoryPageRefernce.getRHSPagination());
		clientDirectoryPageRefernce.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 100.")
	public void testLastPageButtonWhenItemsPerPageValueisHundred() throws InterruptedException {
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPageRefernce.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "100");
		log("Print the RHS Pagination : " + clientDirectoryPageRefernce.getRHSPagination());
		clientDirectoryPageRefernce.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 200.")
	public void testLastPageButtonWhenItemsPerPageValueisTwoHundred() throws InterruptedException {
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPageRefernce.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "200");
		log("Print the RHS Pagination : " + clientDirectoryPageRefernce.getRHSPagination());
		clientDirectoryPageRefernce.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 10.")
	public void testPreviousPageButtonWhenItemsPerPageValueisTen() throws InterruptedException {
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		String defaultItemsPerPage = clientDirectoryPageRefernce.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		clientDirectoryPageRefernce
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 20.")
	public void testPreviousPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException {
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPageRefernce.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		clientDirectoryPageRefernce.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 50.")
	public void testPreviousPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException {
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPageRefernce.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "50");
		clientDirectoryPageRefernce.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 100.")
	public void testPreviousPageButtonWhenItemsPerPageValueisHundred() throws InterruptedException {
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPageRefernce.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "100");
		clientDirectoryPageRefernce.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 200.")
	public void testPreviousPageButtonWhenItemsPerPageValueisTwoHundred() throws InterruptedException {
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPageRefernce.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "200");
		clientDirectoryPageRefernce.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 10.")
	public void testFirstPageButtonWhenItemsPerPageValueisTen() throws InterruptedException {
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		String defaultItemsPerPage = clientDirectoryPageRefernce.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		clientDirectoryPageRefernce
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 20.")
	public void testFirstPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException {
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPageRefernce.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		clientDirectoryPageRefernce.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 50.")
	public void testFirstPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException {
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPageRefernce.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "50");
		clientDirectoryPageRefernce.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 100.")
	public void testFirstPageButtonWhenItemsPerPageValueisHundred() throws InterruptedException {
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPageRefernce.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "100");
		clientDirectoryPageRefernce.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 200.")
	public void testFirstPageButtonWhenItemsPerPageValueisTwoHundred() throws InterruptedException {
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPageRefernce.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "200");
		clientDirectoryPageRefernce.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}
}
