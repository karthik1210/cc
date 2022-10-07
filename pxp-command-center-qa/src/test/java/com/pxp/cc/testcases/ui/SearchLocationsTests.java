// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddLocationPage;
import com.pxp.cc.objectmaps.AddOrgNodePage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.objectmaps.SearchLocationPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class SearchLocationsTests extends CommandCenterGenericTest {
	private ClientDirectoryPage clientDirectoryPage;
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	private GeneralSettingsPage generalSettingsPage;
	private AddLocationPage addLocationPage;
	private AddOrgNodePage addOrgNodePage;
	private SearchLocationPage searchLocationPage;
	public String systemNameOne, systemNameTwo, agPracticeNameOne, practiceNameTwo, enterpriseOne, practiceNameThree;
	String aglocOne, agLocTwo, locOne, locTwo, locThree, locFour;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal with url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		commonMethods = new CommonMethods(driver);
		createClientPage = new CreateClientPage(driver);
		logStep("Select create client button from new dropdown");
		createClientPage.clickCreateClient();
		commonMethods = new CommonMethods(driver);
		logStep("Enter valid details in mandatory fields");
		String clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientPage.createNewClient(clientName, clientSapNumber, testData.getProperty("new.client.type"),
				testData.getProperty("new.client.timezone"));
		logStep("Verify active radio button is by default selected on the create client popup");
		createClientPage.verifyActiveRadioButtonIsByDefaultSelected();
		logStep("Click on create button on the create client popup");
		createClientPage.clickConfirmPopup();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! " + clientName + " has been successfully created");
		logStep("Searching a created client in client directory");
		clientDirectoryPage.searchNode(clientName);
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.verifyClientNodeIsSelected();
		generalSettingsPage.verifyLocationsTab();
		generalSettingsPage.clickOnLocationsTab();
		searchLocationPage = new SearchLocationPage(driver);
	}

	public String createSystem() throws InterruptedException {
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnAddSystem();
		String sysName = "SystemDemo".concat(CCUtils.generateAlphaNumericString(8));
		addOrgNodePage.enterNewSystemDetails(sysName);
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(sysName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		return sysName;
	}

	public String createEnterprise(String orgName) throws InterruptedException {
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnExpandArrow(orgName);
		String enterpriseName = "EnterpriseForDemo".concat(CCUtils.generateAlphaNumericString(8));
		addOrgNodePage.addNewEnterprise(enterpriseName);
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(enterpriseName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		return enterpriseName;
	}

	public String createPractice(String orgSystemName, String orgEnterpriseName) throws InterruptedException {
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnExpandArrow(orgSystemName);
		addOrgNodePage.clickOnExpandArrow(orgEnterpriseName);
		String practiceName = "PracticeForDemo".concat(CCUtils.generateAlphaNumericString(8));
		addOrgNodePage.addNewPractice(practiceName);
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(practiceName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		return practiceName;
	}

	public String createAgnosticPractice(String orgName) throws InterruptedException {
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnExpandArrow(orgName);
		String practiceName = "PracticeForDemo".concat(CCUtils.generateAlphaNumericString(8));
		addOrgNodePage.addNewPractice(practiceName);
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(practiceName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		return practiceName;
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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result of a location")
	public void testSearchLocationScreenWebElements() throws Exception {
		searchLocationPage = new SearchLocationPage(driver);
		searchLocationPage.verifySearchTextBoxAndPlaceholderIsDisplayed();
		searchLocationPage.verifySearchButtonIsDisplayed();
		searchLocationPage.verifyFilterLabel();
		searchLocationPage.verifySystemFilterLabelTextAndPlaceholder();
		searchLocationPage.verifyEnterpriseFilterLabelTextAndPlaceholder();
		searchLocationPage.verifyPracticeFilterLabelTextAndPlaceholder();
		searchLocationPage.verifySolutionFilterLabelTextAndPlaceholder();
		searchLocationPage.clearButtonDisplayed();
		searchLocationPage.clearApplyDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result of a location")
	public void testFunctionalityOfHideFiltersButtonOnLocationScreen() throws Exception {
		searchLocationPage = new SearchLocationPage(driver);
		searchLocationPage.verifyFilterLabel();
		searchLocationPage.verifySystemFilterLabelTextAndPlaceholder();
		searchLocationPage.verifyEnterpriseFilterLabelTextAndPlaceholder();
		searchLocationPage.verifyPracticeFilterLabelTextAndPlaceholder();
		searchLocationPage.verifySolutionFilterLabelTextAndPlaceholder();
		searchLocationPage.clearButtonDisplayed();
		searchLocationPage.clearApplyDisplayed();
		searchLocationPage.clickToHideFiltersButton();
		searchLocationPage.verifyAllElementsOfFilterNotVisible();
	}

	@Test(priority = 0,enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result of a location")
	public void testSearchExistingLocationOnLocationScreen() throws Exception {
		String createLocationOne = createLocation();
		String createLocationTwo = createLocation();
		String createLocationThree = createLocation();
		String createLocationFour = createLocation();
		searchLocationPage = new SearchLocationPage(driver);
		searchLocationPage.searchLocationAndVerifyResult(createLocationOne);
		searchLocationPage.searchLocationAndVerifyResult(createLocationTwo);
		searchLocationPage.searchLocationAndVerifyResult(createLocationThree);
		searchLocationPage.searchLocationAndVerifyResult(createLocationFour);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify error message when searching non-existing location")
	public void testErrorMessageWhenSearchingNonExistingLocation() throws Exception {
		searchLocationPage.searchNonExistingLocationAndVerifyResult("NonExistingLocation");
	}

	@Test(priority = 1,enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result when apply system filter in search location screen")
	public void testSearchExistingLocationByFilterSystem() throws Exception {
		systemNameOne = createSystem();
		agPracticeNameOne = createAgnosticPractice(systemNameOne);
		generalSettingsPage.enterSearchText(agPracticeNameOne);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		aglocOne = createLocation();
		agLocTwo = createLocation();
		searchLocationPage = new SearchLocationPage(driver);
		searchLocationPage.clickToSystemFilter();
		searchLocationPage.selectFilter(systemNameOne);
		searchLocationPage.clickToApplyFilterButton();
		List<String> listOfLocations = new ArrayList<String>();
		listOfLocations.add(aglocOne);
		listOfLocations.add(agLocTwo);
		searchLocationPage.verifyListOfLocationMatchedSearchResult(listOfLocations);
	}

	@Test(priority = 2,enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result when apply enterprise filter in search location screen")
	public void testSearchExistingLocationByFilterEnterprise() throws Exception {
		systemNameTwo = createSystem();
		enterpriseOne = createEnterprise(systemNameTwo);
		practiceNameTwo = createPractice(systemNameTwo, enterpriseOne);
		practiceNameThree = createPractice(systemNameTwo, enterpriseOne);
		generalSettingsPage.enterSearchText(practiceNameTwo);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		locOne = createLocation();
		locTwo = createLocation();
		generalSettingsPage.enterSearchText(practiceNameThree);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		locThree = createLocation();
		locFour = createLocation();
		searchLocationPage = new SearchLocationPage(driver);
		searchLocationPage.clickToEnterpriseFilter();
		searchLocationPage.selectFilter(enterpriseOne);
		searchLocationPage.clickToApplyFilterButton();
		List<String> listOfLocations = new ArrayList<String>();
		listOfLocations.add(locOne);
		listOfLocations.add(locTwo);
		listOfLocations.add(locThree);
		listOfLocations.add(locFour);
		searchLocationPage.verifyListOfLocationMatchedSearchResult(listOfLocations);
	}

	@Test(priority = 3,enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result when apply practice filter in search location screen")
	public void testSearchExistingLocationByFilterPractice() throws Exception {
		searchLocationPage.clickToPracticeFilter();
		searchLocationPage.selectFilter(practiceNameTwo);
		searchLocationPage.clickToApplyFilterButton();
		List<String> listOfLocations = new ArrayList<String>();
		listOfLocations.add(locOne);
		listOfLocations.add(locTwo);
		searchLocationPage.verifyListOfLocationMatchedSearchResult(listOfLocations);
	}

	@Test(priority = 4,enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result when apply solution filter in search location screen")
	public void testSearchExistingLocationByFilterSolution() throws Exception {
		searchLocationPage = new SearchLocationPage(driver);
		searchLocationPage.clickToSolutionFilter();
		searchLocationPage.selectFilter("prac 27-09");
		searchLocationPage.clickToApplyFilterButton();
		List<String> listOfLocations = new ArrayList<String>();
		listOfLocations.add(locThree);
		listOfLocations.add(locFour);
		searchLocationPage.verifyListOfLocationMatchedSearchResult(listOfLocations);
	}
}
