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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to Search Client name in Client Directory and no result found error message when searching non-existing client")
	public void testNewActiveClientCreationAndClientNameSearchAndNoSearchResultFoundErrorMessageAndClientTypeFilters() throws Exception {
		logStep("PXPP-477 Verify whether new client is created when all mandatory values are provided");
		logStep("Searching a newly created client in client directory");
		clientName = ccui.getClient();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		clientDirectoryPageRefernce.searchNode(clientName);
		clientDirectoryPageRefernce.clickSearchButton();
		logStep("Verifying results for searched client");
		clientDirectoryPageRefernce.verfiySearchResult(clientName);
		clientDirectoryPageRefernce.pageRefresh();
		logStep("PXPP-1863 Verify 'No Result Found' is displayed when no match is present for searched text");
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
		clientDirectoryPageRefernce.pageRefresh();
		logStep("PXPP-1861 Verify Default text for Client Search & Client Type dropdown");
		logStep("PXPP-1860 Verify Client types are searched based on Filters provided");
		logStep("checking search button functionality");
		clientDirectoryPageRefernce.verfiySearchButtonFunctionality(ccuser.searchClientInClientDirectory);
		clientDirectoryPageRefernce.clearSearchTextInClientDirectory();
		logStep("Apply Client Type Filter in Client Directory.");
		clientDirectoryPageRefernce.selectClientTypeDropDown();
		clientDirectoryPageRefernce.selectClientType(ccuser.getClientThreeType());
		clientDirectoryPageRefernce.applyClientTypeFilter();
		logStep("Verify client type filter");
		clientDirectoryPageRefernce.verfiySearchResultForClientType(ccuser.getClientThreeType());
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify error messages on create client popup")
	public void testErrorMessagesOnCreateClientPopUp() throws Exception {
		logStep("PXPP-480 PXPP-481 Verify error for unique client name and sap id is displayed when user enters text and focus is shifted");
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
		logStep("PXPP-457,PXPP-459,PXPP-461,PXPP-463,PXPP-464,PXPP-466,PXPP-467,PXPP-471,PXPP-472,PXPP-473,PXPP-476 Verify the web elements of create client pop up");
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

	@Test(enabled = true, groups = {
			"smoke" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to favorite a client from client directory")
	public void testFavoriteClientAndUnFavoriteClient() throws Exception {
		logStep("PXPP-1787 Verify the features of Favorites functionality in Client Directory");
		clientNameOne = ccui.getClient();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		logStep("Verfying whether favorited client count is equal or less than five");
		boolean value = clientDirectoryPageRefernce.getNoOfFavoriteClients();
		clientDirectoryPageRefernce.verifyNoOfFavoriteClient(value);
		clientDirectoryPageRefernce.searchNode(clientNameOne);
		clientDirectoryPageRefernce.clickSearchButton();
		clientDirectoryPageRefernce.FavoriteAClient();
		logStep("PXPP-1787 Verify the features of Favorites functionality in Client Directory");
		logStep("Verfying whether favorited client count is equal or less than five");
		int noOfFavClients = clientDirectoryPageRefernce.getSizeListOfFavClients();
		boolean data = clientDirectoryPageRefernce.checkNoOfFavClientsAndUnFavoriteThem(noOfFavClients);
		clientDirectoryPageRefernce.FavoriteClientThenUnfavorite(data, clientNameOne);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to favorite more than 5 client from client directory")
	public void testMoreThanFiveClientFavoritedError() throws Exception {
		logStep("PXPP-1787 Verify the features of Favorites functionality in Client Directory");
		logStep("Verfying whether favorited client count is equal or less than five");
		int noOfFavClients = clientDirectoryPageRefernce.getSizeListOfFavClients();
		log("Print no of Fav clients : " + noOfFavClients);
		clientDirectoryPageRefernce.validateFavClientsAndUnFavThem(noOfFavClients);
		List<String> listOfFavoritingClient = listOfFavClients();
		clientDirectoryPageRefernce.listOfFavoritingClients(listOfFavoritingClient);
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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Display of default pagination i.e., 10 items per page.")
	public void testDefaultItemsPerPageAndDefaultClientPaginationInClientWebTable()
			throws InterruptedException, RuntimeException {
		logStep("PXPP-1711 PXPP-1713 Verify client directory web table and pagination features");
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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify numbers of rows and Pagination should be change it we changes 'Items Per Page' value.")
	public void testAllPaginationButtonsClickable() throws InterruptedException {
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
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

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 10,20,50,100,200.")
	public void testNextPageButtonWhenItemsPerPageValueisTenTwentyyFiftyHundredTwoHundred() throws InterruptedException {
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		String defaultItemsPerPage = clientDirectoryPageRefernce.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		clientDirectoryPageRefernce
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		clientDirectoryPageRefernce.pageRefresh();
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPageTwenty = clientDirectoryPageRefernce.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		clientDirectoryPageRefernce
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwenty));
		clientDirectoryPageRefernce.pageRefresh();
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPageFifty = clientDirectoryPageRefernce.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		clientDirectoryPageRefernce
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		clientDirectoryPageRefernce.pageRefresh();
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPageHundred = clientDirectoryPageRefernce.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		clientDirectoryPageRefernce
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
		clientDirectoryPageRefernce.pageRefresh();
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = clientDirectoryPageRefernce.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		clientDirectoryPageRefernce
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 10.")
	public void testLastPageButtonWhenItemsPerPageValueisTen() throws InterruptedException {
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		String defaultItemsPerPage = clientDirectoryPageRefernce.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		log("Print the RHS Pagination : " + clientDirectoryPageRefernce.getRHSPagination());
		clientDirectoryPageRefernce
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		clientDirectoryPageRefernce.pageRefresh();
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage_20 = clientDirectoryPageRefernce.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage_20, "20");
		log("get default pagination : " + clientDirectoryPageRefernce.getDefaultPagination());
		log("Print the RHS Pagination : " + clientDirectoryPageRefernce.getRHSPagination());
		clientDirectoryPageRefernce.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage_20));
		clientDirectoryPageRefernce.pageRefresh();
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage_50 = clientDirectoryPageRefernce.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage_50, "50");
		log("Print the RHS Pagination : " + clientDirectoryPageRefernce.getRHSPagination());
		clientDirectoryPageRefernce.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage_50));
		clientDirectoryPageRefernce.pageRefresh();
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage_100 = clientDirectoryPageRefernce.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage_100, "100");
		log("Print the RHS Pagination : " + clientDirectoryPageRefernce.getRHSPagination());
		clientDirectoryPageRefernce.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage_100));
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage_200 = clientDirectoryPageRefernce.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage_200, "200");
		log("Print the RHS Pagination : " + clientDirectoryPageRefernce.getRHSPagination());
		clientDirectoryPageRefernce.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage_200));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 10,20,50,100,200.")
	public void testPreviousPageButtonWhenItemsPerPageValueisTenTwentyFiftyHundredTwoHundred() throws InterruptedException {
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		String defaultItemsPerPage = clientDirectoryPageRefernce.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		clientDirectoryPageRefernce
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		clientDirectoryPageRefernce.pageRefresh();
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage_20 = clientDirectoryPageRefernce.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage_20, "20");
		clientDirectoryPageRefernce.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage_20));
		clientDirectoryPageRefernce.pageRefresh();
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage_50 = clientDirectoryPageRefernce.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage_50, "50");
		clientDirectoryPageRefernce.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage_50));
		clientDirectoryPageRefernce.pageRefresh();
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage_100 = clientDirectoryPageRefernce.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage_100, "100");
		clientDirectoryPageRefernce.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage_100));
		clientDirectoryPageRefernce.pageRefresh();
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage_200 = clientDirectoryPageRefernce.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage_200, "200");
		clientDirectoryPageRefernce.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage_200));
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 10,20,50,100,200")
	public void testFirstPageButtonWhenItemsPerPageValueisTenTwentyFiftyHundredTwoHundred() throws InterruptedException {
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		String defaultItemsPerPage = clientDirectoryPageRefernce.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		clientDirectoryPageRefernce
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		clientDirectoryPageRefernce.pageRefresh();
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage_20 = clientDirectoryPageRefernce.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage_20, "20");
		clientDirectoryPageRefernce.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage_20));
		clientDirectoryPageRefernce.pageRefresh();
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage_50 = clientDirectoryPageRefernce.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage_50, "50");
		clientDirectoryPageRefernce.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage_50));
		clientDirectoryPageRefernce.pageRefresh();
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage_100 = clientDirectoryPageRefernce.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage_100, "100");
		clientDirectoryPageRefernce.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage_100));
		clientDirectoryPageRefernce.pageRefresh();
		logStep("PXPP-1713 Verify Client's Web Table Pagination Features");
		clientDirectoryPageRefernce.verifyClientDirectioryPageTitle();
		clientDirectoryPageRefernce.clickItemsPerPageDropdown();
		String itemsPerPage_200 = clientDirectoryPageRefernce.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage_200, "200");
		clientDirectoryPageRefernce.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage_200));
	}
}
