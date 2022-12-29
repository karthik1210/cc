// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.util.CCUtils;

public class ClientDirectoryPageTests extends CommandCenterGenericTest {

	static boolean runOnce = true;
	public CCUI ccui;
	public static String clientName, clientNameOne, clientNameTwo, clientNameThree, clientNameFour, clientNamefive,
			clientNameSix;
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
		clientNameTwo = ccui.getClient();
		clientNameThree = ccui.getClient();
		clientNameFour = ccui.getClient();
		clientNamefive = ccui.getClient();
		clientNameSix = ccui.getClient();
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
		ccui.clientDirectoryPage.searchNode(clientName);
		ccui.clientDirectoryPage.clickSearchButton();
		logStep("Verifying results for searched client");
		ccui.clientDirectoryPage.verfiySearchResult(clientName);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify error messages on create client popup")
	public void testErrorMessagesOnCreateClientPopUp() throws Exception {
		clientName = ccui.getClient();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		ccui.clientDirectoryPage.searchNode(clientName);
		ccui.clientDirectoryPage.clickSearchButton();
		ccui.getNgHierarchyClientCreationPage().clickCreateClient();
		logStep("Enter already existing client name and sap id in client name and sap id field and verify error message");
		ccui.getNgHierarchyClientCreationPage().verifyClientNameAndSapUnique(clientName, CCUI.clientSap);
		logStep("Remove all characters from client name and sap id field and verify required error message is displayed for mandatory fields");
		String clientNam = clientName.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(6));
		String clientSapNumber = CCUI.clientSap.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(6));
		ccui.getNgHierarchyClientCreationPage().verifyRequiredErrorMessageForClientNameAndSapIdField(clientNam,
				clientSapNumber);
		logStep("Verify error message when a user enters blank space in client name and the sap id field");
		ccui.getNgHierarchyClientCreationPage().verifyErrorMessageForClientNameAndSapIdFieldWhenUserEnterBlankSpace(" ",
				" ");
		logStep("Verify no items found message is displayed for client type and timezone field when user enter non existing client type and timezone");
		ccui.getNgHierarchyClientCreationPage().verifyNoItemsFoundMsgForClientTypeAndTimezoneField(
				testData.getProperty("new.client.type").concat(CCUtils.generateAlphaNumericString(4)),
				testData.getProperty("new.client.timezone").concat(CCUtils.generateAlphaNumericString(4)));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Create a new inactive test client and verify success toaster message")
	public void testNewInactiveClientCreation() throws Exception {
		logStep("Enter valid details in mandatory fields");
		String clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(6));
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(6));
		ccui.getNgHierarchyClientCreationPage().createNewClient(clientName, clientSapNumber,
				testData.getProperty("new.client.type"), "Pacific Standard Time");
		logStep("Select inactive radio button and click on test checkbox on the create client pop-up");
		ccui.getNgHierarchyClientCreationPage().selectInactiveRadiobutton();
		ccui.getNgHierarchyClientCreationPage().selectTestClient();
		logStep("Click on create button on the create client pop-up");
		ccui.getNgHierarchyClientCreationPage().clickConfirmPopup();
		String actualMessage = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! " + clientName + " has been successfully created");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify create client popup")
	public void testCreateClientPopUp() throws Exception {
		logStep("Verify create client popup");
		ccui.getNgHierarchyClientCreationPage().verifyCreateClientPageTitle();
		ccui.getNgHierarchyClientCreationPage().verifyCreateClientPageSubTitle();
		ccui.getNgHierarchyClientCreationPage().verifyClientNameLabelTextAndTextboxDefaultValue();
		ccui.getNgHierarchyClientCreationPage().verifySapIdLabelTextAndTextboxDefaultValue();
		ccui.getNgHierarchyClientCreationPage().verifyClientTypeLabelTextAndDropDownDefaultValue();
		ccui.getCommonMethods().getClientTypeDropdownValue();
		ccui.getCommonMethods().verifyClientTypeDropdownValues(testData.getProperty("qa.data.layer.service.base.url"));
		ccui.getNgHierarchyClientCreationPage().verifyTimezoneLabelTextAndDropDownDefaultValue();
		ccui.getCommonMethods().getTimezoneDropdownValue();
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
		ccui.getClientDirectoryPage().searchNode(testData.getProperty("search.non.existing.client"));
		ccui.getClientDirectoryPage().selectClientTypeDropDown();
		ccui.getClientDirectoryPage().selectClientType(testData.getProperty("api.client.thirteen.type"));
		ccui.getClientDirectoryPage().applyClientTypeFilter();
		ccui.getClientDirectoryPage().clickSearchButton();
		logStep("Verifying no result found scenario");
		boolean result = ccui.getClientDirectoryPage().testToCheckNoResultsFound();
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
		ccui.getClientDirectoryPage().verfiySearchButtonFunctionality(ccuser.searchClientInClientDirectory);
	}

	@Test(enabled = true, groups = {
			"smoke" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to check results after ClientType filter is applied")
	public void testClientTypeFilters() throws Exception {
		logStep("Apply Client Type Filter in Client Directory.");
		ccui.getClientDirectoryPage().selectClientTypeDropDown();
		ccui.getClientDirectoryPage().selectClientType(ccuser.getClientThreeType());
		ccui.getClientDirectoryPage().applyClientTypeFilter();
		logStep("Verify client type filter");
		ccui.getClientDirectoryPage().verfiySearchResultForClientType(ccuser.getClientThreeType());
	}

	@Test(enabled = true, groups = {
			"smoke" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to favorite a client from client directory")
	public void testFavoriteClient() throws Exception {
		GeneralSettingsPage generalSettingsPage = new GeneralSettingsPage(driver);
		clientNameOne = ccui.getClient();
		generalSettingsPage.clickClientDirectoryIcon();
		logStep("Verfying whether favorited client count is equal or less than five");
		boolean value = ccui.getClientDirectoryPage().getNoOfFavoriteClients();
		ccui.getClientDirectoryPage().verifyNoOfFavoriteClient(value);
		ccui.getClientDirectoryPage().searchNode(clientNameOne);
		ccui.getClientDirectoryPage().clickSearchButton();
		ccui.getClientDirectoryPage().FavoriteAClient();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to favorite more than 5 client from client directory")
	public void testMoreThanFiveClientFavoritedError() throws Exception {
		logStep("Verfying whether favorited client count is equal or less than five");
		int noOfFavClients = ccui.getClientDirectoryPage().getSizeListOfFavClients();
		log("Print no of Fav clients : " + noOfFavClients);
		ccui.getClientDirectoryPage().validateFavClientsAndUnFavThem(noOfFavClients);
		List<String> listOfFavoritingClient = listOfFavClients();
		ccui.getClientDirectoryPage().listOfFavoritingClients(listOfFavoritingClient);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to unfavorite a client from client directory")
	public void testUnFavoriteClient() throws Exception {
		logStep("Verfying whether favorited client count is equal or less than five");
		int noOfFavClients = ccui.getClientDirectoryPage().getSizeListOfFavClients();
		boolean data = ccui.getClientDirectoryPage().checkNoOfFavClientsAndUnFavoriteThem(noOfFavClients);
		ccui.getClientDirectoryPage().FavoriteClientThenUnfavorite(data, ccuser.getProperty("favorite.client.name"));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to check nodes can be searched in Client Directory screen")
	public void testClientNodeSearch() throws Exception {
		logStep("Searching for node under a client in client directory page");
		ccui.getClientDirectoryPage().searchNode(ccuser.searchSystemInClientDirectory);
		ccui.getClientDirectoryPage().clickSearchButton();
		ccui.getClientDirectoryPage().clickSearchResult();
		logStep("Verifying result in general setting screen");
		List<String> allNodesInClient = ccui.getClientDirectoryPage().getClientNodeinGeneralSetting();
		for (int i = 0; i <= allNodesInClient.size(); i++) {
			String actualResultNode = allNodesInClient.get(i).toLowerCase();
			if (actualResultNode.contains(ccuser.searchSystemInClientDirectory.toLowerCase())) {
				logStep("Test case Pass, Node present under client is '" + allNodesInClient.get(i)
						+ "' & searched text is '" + ccuser.searchSystemInClientDirectory + "'");
				break;
			} else {
				log("Checking next node in Client, current node is " + allNodesInClient.get(i));
				continue;
			}
		}
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Display of default pagination i.e., 10 items per page.")
	public void testDefaultItemsPerPageAndDefaultClientPaginationInClientWebTable()
			throws InterruptedException, RuntimeException, AWTException {
		logStep("Now comparing the default Items per Page .Ideally it should be 10.");
		CCUtils.scrollPageToEnd();
		CCUtils.assertEquals(ccui.getClientDirectoryPage().getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(ccui.getClientDirectoryPage().getDefaultPagination(), "1 – 10");
		String noOfClients = ccui.getClientDirectoryPage().getTotalNoOfClients();
		logStep("Get the Total No of Clients : " + noOfClients);
		int getNoOfRowsPagination = Integer.parseInt(ccui.getClientDirectoryPage().getNoOfRowsPagination());
		logStep("value of no of rows effected in Pagination : " + getNoOfRowsPagination);
		CCUtils.assertEquals(ccui.getClientDirectoryPage().isFirstPageButtonDisabled(), false);
		CCUtils.assertEquals(ccui.getClientDirectoryPage().isPreviousPageButtonDisabled(), false);
		CCUtils.assertEquals(ccui.getClientDirectoryPage().isNextPageButtonDisabled(), true);
		CCUtils.assertEquals(ccui.getClientDirectoryPage().isLastPageButtonDisabled(), true);
		ccui.getClientDirectoryPage().clickUserIcon();
		ccui.getClientDirectoryPage().clickLogout();

	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Verify numbers of rows and Pagination should be change it we changes 'Items Per Page' value.")
	public void testAllPaginationButtonsClickable() throws InterruptedException, AWTException {
		logStep("Now comparing the default Items per Page" + ".Ideally it should be 10.");
		CCUtils.assertEquals(ccui.getClientDirectoryPage().getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(ccui.getClientDirectoryPage().getDefaultPagination(), "1 – 10");
		String noOfClients = ccui.getClientDirectoryPage().getTotalNoOfClients();
		logStep("Get the Total No of Clients : " + noOfClients);
		ccui.getClientDirectoryPage().clickNextPageButton();
		ccui.getClientDirectoryPage().clickNextPageButton();
		ccui.getClientDirectoryPage().clickNextPageButton();
		ccui.getClientDirectoryPage().clickPreviousPageButton();
		ccui.getClientDirectoryPage().clickLastPageButton();
		ccui.getClientDirectoryPage().clickFirstPageButton();
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 10.")
	public void testNextPageButtonWhenItemsPerPageValueisTen() throws InterruptedException, AWTException {
		String defaultItemsPerPage = ccui.getClientDirectoryPage().getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getClientDirectoryPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 20.")
	public void testNextPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException, AWTException {
		ccui.getClientDirectoryPage().clickItemsPerPageDropdown();
		String itemsPerPageTwenty = ccui.getClientDirectoryPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		ccui.getClientDirectoryPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwenty));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 50.")
	public void testNextPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException, AWTException {
		ccui.getClientDirectoryPage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getClientDirectoryPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		ccui.getClientDirectoryPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 100.")
	public void testNextPageButtonWhenItemsPerPageValueisHundred() throws InterruptedException, AWTException {
		ccui.getClientDirectoryPage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getClientDirectoryPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		ccui.getClientDirectoryPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 200.")
	public void testNextPageButtonWhenItemsPerPageValueisTwoHundred() throws InterruptedException, AWTException {
		ccui.getClientDirectoryPage().clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = ccui.getClientDirectoryPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		ccui.getClientDirectoryPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 10.")
	public void testLastPageButtonWhenItemsPerPageValueisTen() throws InterruptedException, AWTException {
		String defaultItemsPerPage = ccui.getClientDirectoryPage().getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		log("Print the RHS Pagination : " + ccui.getClientDirectoryPage().getRHSPagination());
		ccui.getClientDirectoryPage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 20.")

	public void testLastPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException, AWTException {
		ccui.getClientDirectoryPage().clickItemsPerPageDropdown();
		String itemsPerPage = ccui.getClientDirectoryPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		log("get default pagination : " + ccui.getClientDirectoryPage().getDefaultPagination());
		log("Print the RHS Pagination : " + ccui.getClientDirectoryPage().getRHSPagination());
		ccui.getClientDirectoryPage().checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 50.")
	public void testLastPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException, AWTException {
		ccui.getClientDirectoryPage().clickItemsPerPageDropdown();
		String itemsPerPage = ccui.getClientDirectoryPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "50");
		log("Print the RHS Pagination : " + ccui.getClientDirectoryPage().getRHSPagination());
		ccui.getClientDirectoryPage().checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 100.")
	public void testLastPageButtonWhenItemsPerPageValueisHundred() throws InterruptedException, AWTException {
		ccui.getClientDirectoryPage().clickItemsPerPageDropdown();
		String itemsPerPage = ccui.getClientDirectoryPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "100");
		log("Print the RHS Pagination : " + ccui.getClientDirectoryPage().getRHSPagination());
		ccui.getClientDirectoryPage().checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 200.")
	public void testLastPageButtonWhenItemsPerPageValueisTwoHundred() throws InterruptedException, AWTException {
		ccui.getClientDirectoryPage().clickItemsPerPageDropdown();
		String itemsPerPage = ccui.getClientDirectoryPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "200");
		log("Print the RHS Pagination : " + ccui.getClientDirectoryPage().getRHSPagination());
		ccui.getClientDirectoryPage().checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 10.")
	public void testPreviousPageButtonWhenItemsPerPageValueisTen() throws InterruptedException, AWTException {
		String defaultItemsPerPage = ccui.getClientDirectoryPage().getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getClientDirectoryPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 20.")
	public void testPreviousPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException, AWTException {
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPageRefernce.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		clientDirectoryPageRefernce
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 50.")
	public void testPreviousPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException {
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPageRefernce.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "50");
		clientDirectoryPageRefernce
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 100.")
	public void testPreviousPageButtonWhenItemsPerPageValueisHundred() throws InterruptedException, AWTException {
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPageRefernce.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "100");
		clientDirectoryPageRefernce.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 200.")
	public void testPreviousPageButtonWhenItemsPerPageValueisTwoHundred() throws InterruptedException, AWTException {
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPageRefernce.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "200");
		clientDirectoryPageRefernce.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 10.")
	public void testFirstPageButtonWhenItemsPerPageValueisTen() throws InterruptedException {
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		String defaultItemsPerPage = clientDirectoryPageRefernce.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		clientDirectoryPageRefernce
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 20.")
	public void testFirstPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException {
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPageRefernce.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		clientDirectoryPageRefernce.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 50.")
	public void testFirstPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException {
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage = clientDirectoryPageRefernce.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "50");
		clientDirectoryPageRefernce.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 100.")
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
