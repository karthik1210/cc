// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import java.awt.AWTException;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.pxp.cc.objectmaps.AddLocationPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.objectmaps.SearchLocationPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class SearchLocationPaginationTests extends CommandCenterGenericTest {

	private ClientDirectoryPage clientDirectoryPage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	private GeneralSettingsPage generalSettingsPage;
	private SearchLocationPage searchLocationPage;
	private AddLocationPage addLocationPage;
	public String locOne, locTwo, locThree, locFour, locFive, locSix, locSeven, locEight, locNine, locTen, locEleven;

	@BeforeMethod
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		commonMethods = new CommonMethods(driver);
		createClientPage = new CreateClientPage(driver);
		createClientPage.clickCreateClient();
		String clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientPage.createNewClient(clientName, clientSapNumber, testData.getProperty("new.client.type"),
				testData.getProperty("new.client.timezone"));
		createClientPage.clickConfirmPopup();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! " + clientName + " has been successfully created");
		logStep("Searching a created client in client directory");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(clientName);
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.verifyClientNodeIsSelected();
		generalSettingsPage.verifyLocationsTab();
		generalSettingsPage.clickOnLocationsTile();
		searchLocationPage = new SearchLocationPage(driver);
	}

	public String createLocation() throws NullPointerException, InterruptedException {
		addLocationPage = new AddLocationPage(driver);
		logStep("Click on Add button");
		addLocationPage.verifyLocationsPageTitle();
		addLocationPage.verifyAddLocationButton();
		addLocationPage.clickOnAddLocationButton();
		logStep("Enter data in all input fields");
		String locationName = "Loc".concat(CCUtils.generateAlphaNumericString(10));
		addLocationPage.enterLocationName(locationName);
		addLocationPage.enterLocationDisplayName(CCUtils.generateAlphaNumericString(18));
		addLocationPage.enterAddressOne(CCUtils.generateAlphaNumericString(18));
		addLocationPage.enterAddressTwo(CCUtils.generateAlphaNumericString(18));
		addLocationPage.enterCity(CCUtils.generateAlphaNumericString(18));
		addLocationPage.enterLocationIdExternal(CCUtils.generateAlphaNumericString(18));
		addLocationPage.enterZip(CCUtils.generateAlphaNumericString(18));
		addLocationPage.addStateAndTimezone(testData.getProperty("new.locations.state"),
				testData.getProperty("new.locations.timezone"));
		logStep("Click on save and add button");
		addLocationPage.clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		addLocationPage.verifyToasterMessage("Success! New location has been successfully created");
		return locationName;
	}

	@Test(priority = 0, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Display of default pagination i.e., 10 items per page.")
	public void testDefaultItemsPerPageAndDefaultClientPaginationInClientWebTable()
			throws InterruptedException, RuntimeException, AWTException {
		locOne = createLocation();
		locTwo = createLocation();
		locThree = createLocation();
		locFour = createLocation();
		locFive = createLocation();
		locSix = createLocation();
		locSeven = createLocation();
		locEight = createLocation();
		locNine = createLocation();
		locTen = createLocation();
		locEleven = createLocation();
		logStep("Now comparing the default Items per Page .Ideally it should be 10.");
		CCUtils.scrollPageToEnd();
		CCUtils.assertEquals(searchLocationPage.getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(searchLocationPage.getDefaultPagination(), "1 – 10");
		String noOfClients = searchLocationPage.getTotalNoOfLocations();
		logStep("Get the Total No of Clients : " + noOfClients);
		int getNoOfRowsPagination = Integer.parseInt(searchLocationPage.getNoOfRowsPagination());
		logStep("value of no of rows effected in Pagination : " + getNoOfRowsPagination);
		CCUtils.assertEquals(searchLocationPage.isFirstPageButtonDisabled(), false);
		CCUtils.assertEquals(searchLocationPage.isPreviousPageButtonDisabled(), false);
		CCUtils.assertEquals(searchLocationPage.isNextPageButtonDisabled(), true);
		CCUtils.assertEquals(searchLocationPage.isLastPageButtonDisabled(), true);
		clientDirectoryPage.clickUserIcon();
		clientDirectoryPage.clickLogout();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify numbers of rows and Pagination should be change it we changes 'Items Per Page' value.")
	public void testAllPaginationButtonsClickable() throws InterruptedException, AWTException {
		logStep("Now comparing the default Items per Page" + ".Ideally it should be 10.");
		CCUtils.assertEquals(searchLocationPage.getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(searchLocationPage.getDefaultPagination(), "1 – 10");
		String noOfClients = searchLocationPage.getTotalNoOfLocations();
		logStep("Get the Total No of Clients : " + noOfClients);
		searchLocationPage.clickNextPageButton();
		searchLocationPage.clickNextPageButton();
		searchLocationPage.clickNextPageButton();
		searchLocationPage.clickPreviousPageButton();
		searchLocationPage.clickLastPageButton();
		searchLocationPage.clickFirstPageButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 10.")
	public void testNextPageButtonWhenItemsPerPageValueisTen() throws InterruptedException, AWTException {
		String defaultItemsPerPage = searchLocationPage.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		searchLocationPage.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 20.")
	public void testNextPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException, AWTException {
		searchLocationPage.clickItemsPerPageDropdown();
		String itemsPerPageTwenty = searchLocationPage.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		searchLocationPage.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwenty));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 50.")
	public void testNextPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException, AWTException {
		searchLocationPage.clickItemsPerPageDropdown();
		String itemsPerPageFifty = searchLocationPage.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		searchLocationPage.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 100.")
	public void testNextPageButtonWhenItemsPerPageValueisHundred() throws InterruptedException, AWTException {
		searchLocationPage.clickItemsPerPageDropdown();
		String itemsPerPageHundred = searchLocationPage.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		searchLocationPage.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality when Items Per Page value is = 200.")
	public void testNextPageButtonWhenItemsPerPageValueisTwoHundred() throws InterruptedException, AWTException {
		searchLocationPage.clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = searchLocationPage.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		searchLocationPage.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Last Button functionality when Items Per Page value is = 10.")
	public void testLastPageButtonWhenItemsPerPageValueisTen() throws InterruptedException, AWTException {
		String defaultItemsPerPage = searchLocationPage.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		log("Print the RHS Pagination : " + searchLocationPage.getRHSPagination());
		searchLocationPage.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Last Button functionality when Items Per Page value is = 20.")

	public void testLastPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException, AWTException {
		searchLocationPage.clickItemsPerPageDropdown();
		String itemsPerPage = searchLocationPage.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		log("get default pagination : " + searchLocationPage.getDefaultPagination());
		log("Print the RHS Pagination : " + searchLocationPage.getRHSPagination());
		searchLocationPage.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Last Button functionality when Items Per Page value is = 50.")
	public void testLastPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException, AWTException {
		searchLocationPage.clickItemsPerPageDropdown();
		String itemsPerPage = searchLocationPage.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "50");
		log("Print the RHS Pagination : " + searchLocationPage.getRHSPagination());
		searchLocationPage.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Last Button functionality when Items Per Page value is = 100.")
	public void testLastPageButtonWhenItemsPerPageValueisHundred() throws InterruptedException, AWTException {
		searchLocationPage.clickItemsPerPageDropdown();
		String itemsPerPage = searchLocationPage.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "100");
		log("Print the RHS Pagination : " + searchLocationPage.getRHSPagination());
		searchLocationPage.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Last Button functionality when Items Per Page value is = 200.")
	public void testLastPageButtonWhenItemsPerPageValueisTwoHundred() throws InterruptedException, AWTException {
		searchLocationPage.clickItemsPerPageDropdown();
		String itemsPerPage = searchLocationPage.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "200");
		log("Print the RHS Pagination : " + searchLocationPage.getRHSPagination());
		searchLocationPage.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 10.")
	public void testPreviousPageButtonWhenItemsPerPageValueisTen() throws InterruptedException, AWTException {
		String defaultItemsPerPage = searchLocationPage.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		searchLocationPage.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 20.")
	public void testPreviousPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException, AWTException {
		searchLocationPage.clickItemsPerPageDropdown();
		String itemsPerPage = searchLocationPage.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		searchLocationPage.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 50.")
	public void testPreviousPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException, AWTException {
		searchLocationPage.clickItemsPerPageDropdown();
		String itemsPerPage = searchLocationPage.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "50");
		searchLocationPage.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 100.")
	public void testPreviousPageButtonWhenItemsPerPageValueisHundred() throws InterruptedException, AWTException {
		searchLocationPage.clickItemsPerPageDropdown();
		String itemsPerPage = searchLocationPage.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "100");
		searchLocationPage.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality when Items Per Page value is = 200.")
	public void testPreviousPageButtonWhenItemsPerPageValueisTwoHundred() throws InterruptedException, AWTException {
		searchLocationPage.clickItemsPerPageDropdown();
		String itemsPerPage = searchLocationPage.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "200");
		searchLocationPage.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 10.")
	public void testFirstPageButtonWhenItemsPerPageValueisTen() throws InterruptedException, AWTException {
		String defaultItemsPerPage = searchLocationPage.getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		searchLocationPage.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 20.")
	public void testFirstPageButtonWhenItemsPerPageValueisTwenty() throws InterruptedException, AWTException {
		searchLocationPage.clickItemsPerPageDropdown();
		String itemsPerPage = searchLocationPage.clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		searchLocationPage.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 50.")
	public void testFirstPageButtonWhenItemsPerPageValueisFifty() throws InterruptedException, AWTException {
		searchLocationPage.clickItemsPerPageDropdown();
		String itemsPerPage = searchLocationPage.clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "50");
		searchLocationPage.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 100.")
	public void testFirstPageButtonWhenItemsPerPageValueisHundred() throws InterruptedException, AWTException {
		searchLocationPage.clickItemsPerPageDropdown();
		String itemsPerPage = searchLocationPage.clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "100");
		searchLocationPage.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality when Items Per Page value is = 200.")
	public void testFirstPageButtonWhenItemsPerPageValueisTwoHundred() throws InterruptedException, AWTException {
		searchLocationPage.clickItemsPerPageDropdown();
		String itemsPerPage = searchLocationPage.clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "200");
		searchLocationPage.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
	}
}
