// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import com.pxp.cc.objectmaps.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.util.CCUtils;
import java.util.ArrayList;
import java.util.List;

import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;

public class LocationsPageTests extends CommandCenterGenericTest {
	static boolean runOnce = true;
	public CCUI ccui;
	String locationName;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		if (runOnce) {
			ccui = new CCUI(testData, driver);
			logStep("Login to command center portal");
			ccui.getClientDirectoryPage();
			ccui.initializeTestData();
			getClientSystementErpriseandPractice();
		}
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify add new location popup")
	public void testAddNewLocationPopup() throws Exception {
		ngHierarchyClientCreate();
		addLocationPopUp();
		logStep("Verify add new location popup");
		ccui.getAddLocationPage().verifyAddNewLocationPopup();
		runOnce = false;
	}

	@Test(enabled = false)
	public void ngHierarchyClientCreate() throws Exception {
		if (!runOnce) {
			ccui = new CCUI(testData, driver);
			logStep("Login to command center portal");
			LoginPage loginPage = new LoginPage(driver, testData.getProperty("base.url"));
			loginPage.login(testData.getProperty("user.name"), testData.getProperty("user.password"));
			logStep("Select create client button from new dropdown");
			logStep("Searching a client in client directory");
			ccui.getNgHierarchyClientCreationPage().searchNode(clientSystementErpriseandPractice.get("clientNam"));
			ccui.getNgHierarchyClientCreationPage().clickSearchButton();
			ccui.getNgHierarchyClientCreationPage()
					.clickSearchResult(clientSystementErpriseandPractice.get("clientNam"));
			logStep("Navigating To General Settings page");
			ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
		}
	}

	@Test(enabled = false)
	public void addLocationPopUp() throws Exception {
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		logStep("Click on Add button");
		ccui.getAddLocationPage().verifyLocationsPageTitle();
		ccui.getAddLocationPage().verifyAddLocationButton();
		ccui.getAddLocationPage().clickOnAddLocationButton();
	}

	//  AddNewLocationTests 
	
	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 1, description = "Verify navigation to Locations page with Locations tab and Locations tile on General Settings page")
	public void testNavigationToLocationsPageWithLocationsTile() throws Exception {
		ngHierarchyClientCreate();
		addLocationPopUp();
		logStep("Click on cancel button");
		ccui.getAddLocationPage().clickOnCancelButton();
		logStep("Click on General Settings tab");
		ccui.getGeneralSettingsPage().clickOnGeneralSettingsTab();
		logStep("Click on Locations tile on General Settings page");
		ccui.getGeneralSettingsPage().clickOnLocationsTile();
		logStep("Verify after clicking on Locations tile user directed to Locations page");
		ccui.getAddLocationPage().verifyLocationsPageTitle();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 2, groups = {
			"buildvalidation" }, description = "Verify add new location functionality with valid details in all input fields")
	public void testAddNewLocationFunctionalityWithAllValidDetails() throws Exception {
		ngHierarchyClientCreate();
		addLocationPopUp();
		logStep("Enter data in all input fields");
		ccui.getAddLocationPage().enterLocationName(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterLocationDisplayName(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterAddressOne(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterAddressTwo(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterCity(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterLocationIdExternal(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterZip(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().addStateAndTimezone(testData.getProperty("new.locations.state"),
				testData.getProperty("new.locations.timezone"));
		logStep("Click on save and add button");
		ccui.getAddLocationPage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.getAddLocationPage().verifyToasterMessage("Success! New location has been successfully created");
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 3, description = "Verify add new location functionality with valid details in all mandatory fields")
	public void testAddNewLocationFunctionalityWithAllValidDetailsInMandatoryFields() throws Exception {
		ngHierarchyClientCreate();
		addLocationPopUp();
		logStep("Enter data in mandatory fields");
		ccui.getAddLocationPage().enterLocationName(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterLocationDisplayName(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterAddressOne(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterCity(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterLocationIdExternal(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterZip(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().addStateAndTimezone(testData.getProperty("new.locations.state"),
				testData.getProperty("new.locations.timezone"));
		logStep("Click on save and add button");
		ccui.getAddLocationPage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.getAddLocationPage().verifyToasterMessage("Success! New location has been successfully created");
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 4, description = "Enter more than 100 characters in locationName, locationDisplayName, Address1, city, zip, locationId fields and verify error message")
	public void testHundredMaximumCharactersErrorMessageForInputFields() throws Exception {
		ngHierarchyClientCreate();
		addLocationPopUp();
		logStep("Verify maximum character reached error message for locationName, locationDisplayName, Address1");
		ccui.getAddLocationPage().verifyHundredMaximumCharactersReachedErrorMessage(
				CCUtils.generateAlphaNumericString(101), CCUtils.generateAlphaNumericString(101),
				CCUtils.generateAlphaNumericStringIncludingSpecialChar(101));
		logStep("Verify maximum character reached error message for city, zipcode, locationId fields");
		ccui.getAddLocationPage().verifyFiftyMaximumCharactersReachedErrorMessage(
				CCUtils.generateAlphaNumericString(51), CCUtils.generateAlphaNumericString(51),
				CCUtils.generateAlphaNumericStringIncludingSpecialChar(51));
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, priority = 5, description = "Verify functionality of cancel button and cross icon")
	public void testCancelAndCrossIconFunctionality() throws Exception {
		ngHierarchyClientCreate();
		addLocationPopUp();
		logStep("Click on cancel button");
		ccui.getAddLocationPage().clickOnCancelButton();
		logStep("Verify after clicking on cancel button user redirected to Locations page");
		ccui.getAddLocationPage().verifyLocationsPageTitle();
		logStep("Click on Add button");
		ccui.getAddLocationPage().clickOnAddLocationButton();
		logStep("Click on cross button");
		ccui.getAddLocationPage().clickOnCrossIcon();
		logStep("Verify after clicking on cross button user redirected to Locations page");
		ccui.getAddLocationPage().verifyLocationsPageTitle();
		runOnce = false;
	}

	// SearchLocationSortPaginationTests

	@Test(enabled = false)
	public void createlocation() throws Exception {
		logStep("Click on Add button");
		ccui.getAddLocationPage().verifyLocationsPageTitle();
		ccui.getAddLocationPage().verifyAddLocationButton();
		ccui.getAddLocationPage().clickOnAddLocationButton();
		logStep("Enter data in mandatory fields");
		locationName = CCUtils.generateAlphaNumericString(18);
		ccui.getAddLocationPage().enterLocationName(locationName);
		ccui.getAddLocationPage().enterLocationDisplayName(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterAddressOne(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterCity(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterLocationIdExternal(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterZip(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().addStateAndTimezone(testData.getProperty("new.locations.state"),
				testData.getProperty("new.locations.timezone"));
		logStep("Click on save and add button");
		ccui.getAddLocationPage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.getAddLocationPage().verifyToasterMessage("Success! New location has been successfully created");

	}

	@Test(priority = 6, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify numbers of rows and Pagination should be change it we changes 'Items Per Page' value.")
	public void testDefaultItemsPerPageAndDefaultClientPaginationInClientWebTable() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		createlocation();
		createlocation();
		createlocation();
		createlocation();
		createlocation();
		createlocation();
		createlocation();
		createlocation();
		createlocation();
		createlocation();
		createlocation();
		createlocation();
		logStep("Now comparing the default Items per Page" + ".Ideally it should be 10.");
		CCUtils.assertEquals(ccui.getSearchLocationDetailsPage().getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(ccui.getSearchLocationDetailsPage().getDefaultPagination(), "1 – 10");
		String noOfClients = ccui.getSearchLocationDetailsPage().getTotalNoOfLocations();
		logStep("Get the Total No of Clients : " + noOfClients);
		ccui.getSearchLocationDetailsPage().clickNextPageButton();
		ccui.getSearchLocationDetailsPage().clickNextPageButton();
		ccui.getSearchLocationDetailsPage().clickNextPageButton();
		ccui.getSearchLocationDetailsPage().clickPreviousPageButton();
		ccui.getSearchLocationDetailsPage().clickLastPageButton();
		ccui.getSearchLocationDetailsPage().clickFirstPageButton();
	}

	@Test(priority = 7, enabled = true, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = "testDefaultItemsPerPageAndDefaultClientPaginationInClientWebTable", description = "Verify numbers of rows and Pagination should be change it we changes 'Items Per Page' value.")
	public void testAllPaginationButtonsClickable() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		logStep("Now comparing the default Items per Page" + ".Ideally it should be 10.");
		CCUtils.assertEquals(ccui.getSearchLocationDetailsPage().getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(ccui.getSearchLocationDetailsPage().getDefaultPagination(), "1 – 10");
		String noOfClients = ccui.getSearchLocationDetailsPage().getTotalNoOfLocations();
		logStep("Get the Total No of Clients : " + noOfClients);
		ccui.getSearchLocationDetailsPage().clickNextPageButton();
		ccui.getSearchLocationDetailsPage().clickNextPageButton();
		ccui.getSearchLocationDetailsPage().clickNextPageButton();
		ccui.getSearchLocationDetailsPage().clickPreviousPageButton();
		ccui.getSearchLocationDetailsPage().clickLastPageButton();
		ccui.getSearchLocationDetailsPage().clickFirstPageButton();
	}

	@Test(priority = 8, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Next Button functionality")
	public void testNextPageButtonFunctionality() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		logStep("Check Next Button functionality when Items Per Page value is = 10");
		String defaultItemsPerPage = ccui.getSearchLocationDetailsPage().getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getSearchLocationDetailsPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check Next Button functionality when Items Per Page value is = 20");
		ccui.getSearchLocationDetailsPage().clickItemsPerPageDropdown();
		String itemsPerPageTwenty = ccui.getSearchLocationDetailsPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwenty, "20");
		ccui.getSearchLocationDetailsPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwenty));
		logStep("Check Next Button functionality when Items Per Page value is = 50");
		ccui.getSearchLocationDetailsPage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getSearchLocationDetailsPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		ccui.getSearchLocationDetailsPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		logStep("Check Next Button functionality when Items Per Page value is = 100");
		ccui.getSearchLocationDetailsPage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getSearchLocationDetailsPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		ccui.getSearchLocationDetailsPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
		logStep("Check Next Button functionality when Items Per Page value is = 200");
		ccui.getSearchLocationDetailsPage().clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = ccui.getSearchLocationDetailsPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		ccui.getSearchLocationDetailsPage()
				.checkNextPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
	}

	@Test(priority = 9, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Last Button functionality")
	public void testLastPageButtonFunctionality() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		String defaultItemsPerPage = ccui.getSearchLocationDetailsPage().getDefaultItemsPerPage();
		logStep("Check Last Button functionality when Items Per Page value is = 10.");
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		log("Print the RHS Pagination : " + ccui.getSearchLocationDetailsPage().getRHSPagination());
		ccui.getSearchLocationDetailsPage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check Last Button functionality when Items Per Page value is = 20.");
		ccui.getSearchLocationDetailsPage().clickItemsPerPageDropdown();
		String itemsPerPage = ccui.getSearchLocationDetailsPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		log("get default pagination : " + ccui.getSearchLocationDetailsPage().getDefaultPagination());
		log("Print the RHS Pagination : " + ccui.getSearchLocationDetailsPage().getRHSPagination());
		ccui.getSearchLocationDetailsPage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
		logStep("Check Last Button functionality when Items Per Page value is = 50.");
		ccui.getSearchLocationDetailsPage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getSearchLocationDetailsPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		log("Print the RHS Pagination : " + ccui.getSearchLocationDetailsPage().getRHSPagination());
		ccui.getSearchLocationDetailsPage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		logStep("Check Last Button functionality when Items Per Page value is = 100.");
		ccui.getSearchLocationDetailsPage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getSearchLocationDetailsPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		log("Print the RHS Pagination : " + ccui.getSearchLocationDetailsPage().getRHSPagination());
		ccui.getSearchLocationDetailsPage()
				.checkLastPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
		logStep("Check Last Button functionality when Items Per Page value is = 200.");
		ccui.getSearchLocationDetailsPage().clickItemsPerPageDropdown();
		String itemsPerPageTwohundred = ccui.getSearchLocationDetailsPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwohundred, "200");
		ccui.getSearchLocationDetailsPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwohundred));
	}

	@Test(priority = 10, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check Previous Button functionality")
	public void testPreviousPageButtonFunctionality() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		logStep("Check Previous Button functionality when Items Per Page value is = 10");
		String defaultItemsPerPage = ccui.getSearchLocationDetailsPage().getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getSearchLocationDetailsPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check Previous Button functionality when Items Per Page value is = 20");
		ccui.getSearchLocationDetailsPage().clickItemsPerPageDropdown();
		String itemsPerPage = ccui.getSearchLocationDetailsPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		ccui.getSearchLocationDetailsPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
		logStep("Check Previous Button functionality when Items Per Page value is = 50");
		ccui.getSearchLocationDetailsPage().clickItemsPerPageDropdown();
		String itemsPerPagFifty = ccui.getSearchLocationDetailsPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPagFifty, "50");
		ccui.getSearchLocationDetailsPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPagFifty));
		logStep("Check Previous Button functionality when Items Per Page value is = 100");
		ccui.getSearchLocationDetailsPage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getSearchLocationDetailsPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		ccui.getSearchLocationDetailsPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
		logStep("Check Previous Button functionality when Items Per Page value is = 200");
		ccui.getSearchLocationDetailsPage().clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = ccui.getSearchLocationDetailsPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		ccui.getSearchLocationDetailsPage()
				.checkPreviousPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));
	}

	@Test(priority = 11, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check First Button functionality")
	public void testFirstPageButtonFunctionality() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		logStep("Check First Button functionality when Items Per Page value is = 10.");
		String defaultItemsPerPage = ccui.getSearchLocationDetailsPage().getDefaultItemsPerPage();
		CCUtils.assertEquals(defaultItemsPerPage, "10");
		ccui.getSearchLocationDetailsPage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(defaultItemsPerPage));
		logStep("Check First Button functionality when Items Per Page value is = 20.");
		ccui.getSearchLocationDetailsPage().clickItemsPerPageDropdown();
		String itemsPerPage = ccui.getSearchLocationDetailsPage().clickTwentyItemsPerPage();
		CCUtils.assertEquals(itemsPerPage, "20");
		ccui.getSearchLocationDetailsPage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPage));
		logStep("Check First Button functionality when Items Per Page value is = 50.");
		ccui.getSearchLocationDetailsPage().clickItemsPerPageDropdown();
		String itemsPerPageFifty = ccui.getSearchLocationDetailsPage().clickFiftyItemsPerPage();
		CCUtils.assertEquals(itemsPerPageFifty, "50");
		ccui.getSearchLocationDetailsPage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageFifty));
		logStep("Check First Button functionality when Items Per Page value is = 100.");
		ccui.getSearchLocationDetailsPage().clickItemsPerPageDropdown();
		String itemsPerPageHundred = ccui.getSearchLocationDetailsPage().clickHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageHundred, "100");
		ccui.getSearchLocationDetailsPage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageHundred));
		logStep("Check First Button functionality when Items Per Page value is = 200.");
		ccui.getSearchLocationDetailsPage().clickItemsPerPageDropdown();
		String itemsPerPageTwoHundred = ccui.getSearchLocationDetailsPage().clickTwoHundredItemsPerPage();
		CCUtils.assertEquals(itemsPerPageTwoHundred, "200");
		ccui.getSearchLocationDetailsPage()
				.checkFirstPageButtonFunctionalityTillGetDisabled(Integer.parseInt(itemsPerPageTwoHundred));

	}

	@Test(priority = 12, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check location name sorting from ascending to descending")
	public void testLocationNameSortingToDescending() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		ccui.getSearchLocationDetailsPage().clickAscLocationNameSortToDsc();
		ccui.getSearchLocationDetailsPage().clickDscLocationNameSortToAsc();

	}

	@Test(priority = 13, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Check location status sorting from ascending to descending")
	public void testLocationStatusSortingToAscending() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		ccui.getSearchLocationDetailsPage().clickAscLocationStatusSortToDsc();
		ccui.getSearchLocationDetailsPage().clickDscLocationStatusSortToAsc();
	}

	@Test(priority = 14, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify the visibility and presence of web elements of search location screen")
	public void testSearchLocationScreenWebElements() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		ccui.getSearchLocationDetailsPage().verifySearchTextBoxAndPlaceholderIsDisplayed();
		ccui.getSearchLocationDetailsPage().verifySearchButtonIsDisplayed();
		ccui.getSearchLocationDetailsPage().verifyFilterLabel();
		ccui.getSearchLocationDetailsPage().verifySystemFilterLabelTextAndPlaceholder();
		ccui.getSearchLocationDetailsPage().verifyEnterpriseFilterLabelTextAndPlaceholder();
		ccui.getSearchLocationDetailsPage().verifyPracticeFilterLabelTextAndPlaceholder();
		ccui.getSearchLocationDetailsPage().verifySolutionFilterLabelTextAndPlaceholder();
		ccui.getSearchLocationDetailsPage().clearButtonDisplayed();
		ccui.getSearchLocationDetailsPage().clearApplyDisplayed();
	}

	@Test(priority = 15, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify after clicking hide button filter section should not get displayed")
	public void testFunctionalityOfHideFiltersButtonOnLocationScreen() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		ccui.getSearchLocationDetailsPage().verifyFilterLabel();
		ccui.getSearchLocationDetailsPage().verifySystemFilterLabelTextAndPlaceholder();
		ccui.getSearchLocationDetailsPage().verifyEnterpriseFilterLabelTextAndPlaceholder();
		ccui.getSearchLocationDetailsPage().verifyPracticeFilterLabelTextAndPlaceholder();
		ccui.getSearchLocationDetailsPage().verifySolutionFilterLabelTextAndPlaceholder();
		ccui.getSearchLocationDetailsPage().clearButtonDisplayed();
		ccui.getSearchLocationDetailsPage().clearApplyDisplayed();
		ccui.getSearchLocationDetailsPage().clickToHideFiltersButton();
		ccui.getSearchLocationDetailsPage().verifyAllElementsOfFilterNotVisible();
	}

	@Test(priority = 16, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result of a location")
	public void testSearchExistingLocationOnLocationScreen() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		createlocation();
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
	}

	@Test(priority = 17, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify error message when searching non-existing location")
	public void testErrorMessageWhenSearchingNonExistingLocation() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		ccui.getSearchLocationDetailsPage().searchNonExistingLocationAndVerifyResult("NonExistingLocation");
	}

	// SearchLocationsTests

	@Test(priority = 18, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result when apply practice, enterprise, system filter in search location screen")
	public void testSearchExistingLocationByFilterPracticeEnterpriseAndSysten() throws Exception {
		ngHierarchyClientCreate();
		enablepractice();
		logStep("Apply filter for Practice");
		ccui.getSearchLocationDetailsPage().clickToPracticeFilter();
		ccui.getSearchLocationDetailsPage().selectFilter(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getSearchLocationDetailsPage().clickToApplyFilterButton();
		List<String> listOfLocations = new ArrayList<String>();
		listOfLocations.add(locationName);
		ccui.getSearchLocationDetailsPage().verifyListOfLocationMatchedSearchResult(listOfLocations);
		ccui.getSearchLocationDetailsPage().clickToClearFilterButton();
		logStep("Apply filter for enterprise");
		ccui.getSearchLocationDetailsPage().clickToEnterpriseFilter();
		ccui.getSearchLocationDetailsPage().selectFilter(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getSearchLocationDetailsPage().clickToApplyFilterButton();
		ccui.getSearchLocationDetailsPage().verifyListOfLocationMatchedSearchResult(listOfLocations);
		ccui.getSearchLocationDetailsPage().clickToClearFilterButton();
		logStep("Apply filter for System");
		ccui.getSearchLocationDetailsPage().clickToSystemFilter();
		ccui.getSearchLocationDetailsPage().selectFilter(clientSystementErpriseandPractice.get("systemName"));
		ccui.getSearchLocationDetailsPage().clickToApplyFilterButton();
		ccui.getSearchLocationDetailsPage().verifyListOfLocationMatchedSearchResult(listOfLocations);
		ccui.getSearchLocationDetailsPage().clickToClearFilterButton();
		logStep("Apply filter for System, enterprise, practice at a time");
		ccui.getSearchLocationDetailsPage().clickToSystemFilter();
		ccui.getSearchLocationDetailsPage().selectFilter(clientSystementErpriseandPractice.get("systemName"));
		ccui.getSearchLocationDetailsPage().clickToEnterpriseFilter();
		ccui.getSearchLocationDetailsPage().selectFilter(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getSearchLocationDetailsPage().clickToPracticeFilter();
		ccui.getSearchLocationDetailsPage().selectFilter(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getSearchLocationDetailsPage().clickToApplyFilterButton();
		ccui.getSearchLocationDetailsPage().verifyListOfLocationMatchedSearchResult(listOfLocations);
	}
	
   // Enable test case once issue is resolved #PXPP-27241
	@Test(priority = 19, enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result when apply solution filter in search location screen")
	public void testSearchExistingLocationByFilterSolution() throws Exception {
		ngHierarchyClientCreate();
		addPurchaseSolutionAtPracticeLevel();
		enableSolution();
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		ccui.getSearchLocationDetailsPage().clickToSolutionFilter();
		ccui.getSearchLocationDetailsPage().selectFilter(testData.getProperty("solution.chat"));
		ccui.getSearchLocationDetailsPage().clickToApplyFilterButton();
		List<String> listOfLocations = new ArrayList<String>();
		listOfLocations.add(locationName);
		ccui.getSearchLocationDetailsPage().verifyListOfLocationMatchedSearchResult(listOfLocations);
	}

	@Test(enabled = false)
	public void enablepractice() throws Exception {
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		createlocation();
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().clickToEntepriseArrow();
		ccui.getLocationDetailsPage().clickOnToggleButton();
		ccui.getLocationDetailsPage().clickToBackButton();
	}

	@Test(enabled = false)
	public void enableSolution() throws Exception {
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		createlocation();
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().clickToEntepriseArrow();
		ccui.getLocationDetailsPage().clickOnToggleButton();
		ccui.getLocationDetailsPage().verifyConnectedSolutionsTitle();
		ccui.getLocationDetailsPage().toggleOnChats();
		ccui.getLocationDetailsPage().clickToBackButton();
	}

	public void addPurchaseSolutionAtPracticeLevel() throws Exception {
		logStep("Navigating To General Settings page");
		ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
		ccui.getPrivacyPolicySettingsPage()
				.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getPrivacyPolicySettingsPage().clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().verifyAvailableToPurchaseSection();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPaySolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfEngageSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfChatSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		runOnce = false;
	}
}
