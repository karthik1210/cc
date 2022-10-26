// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import java.awt.AWTException;
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
import com.pxp.cc.objectmaps.LocationDetailsPage;
import com.pxp.cc.objectmaps.SearchLocationPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class EnableDisableSolutionsOnLocationsDetailsPageTests extends CommandCenterGenericTest {

	private ClientDirectoryPage clientDirectoryPage;
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	private GeneralSettingsPage generalSettingsPage;
	private AddLocationPage addLocationPage;
	private AddOrgNodePage addOrgNodePage;
	private SearchLocationPage searchLocationPage;
	private LocationDetailsPage locationDetailsPage;
	public String systemNameOne, systemNameTwo, agPracticeNameOne, practiceNameTwo, enterpriseTwo;
	String aglocOne, locOne;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal with url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		commonMethods = new CommonMethods(driver);
		clientDirectoryPage = new ClientDirectoryPage(driver);
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

	public void addSolutionsWithinPractice(String practiceName) throws AWTException {
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(practiceName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		CCUtils.scrollPageToEnd();
		generalSettingsPage.verifyAvailableToPurchaseSection();
		generalSettingsPage.clickToActivateButtonOfChatSolution();
		generalSettingsPage.clickToYesActivateButtonInActivateSolutions();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! Solution has been successfully updated");
		generalSettingsPage.clickToActivateButtonOfEngageSolution();
		generalSettingsPage.clickToYesActivateButtonInActivateSolutions();
		String actualMsg = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMsg, "Success! Solution has been successfully updated");
		generalSettingsPage.verifyPurchaseSolutionsSection();
		generalSettingsPage.verifySolutionIsPresentInPurchasedSolutions(testData.getProperty("solution.engage"));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify enable solution of associated agnostic practice of location")
	public void testToEnableSolutionForLocationForAgnosticPractice() throws Exception {
		systemNameOne = createSystem();
		agPracticeNameOne = createAgnosticPractice(systemNameOne);
		addSolutionsWithinPractice(agPracticeNameOne);
		generalSettingsPage.verifyLocationsTab();
		generalSettingsPage.clickOnLocationsTab();
		aglocOne = createLocation();
		locationDetailsPage = searchLocationPage.clickToLocation(aglocOne);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.clickToSytemTab(systemNameOne);
		locationDetailsPage.clickToPracticeTabAndToggleOn(agPracticeNameOne);
		locationDetailsPage.clickToPracticeTab(agPracticeNameOne);
		locationDetailsPage.verifyConnectedSolutionsTitle();
		locationDetailsPage.toggleOnEngage();
		locationDetailsPage.clickToBackButton();
		searchLocationPage.clickToSolutionFilter();
		searchLocationPage.selectFilter(testData.getProperty("solution.engage"));
		searchLocationPage.clickToPracticeFilter();
		searchLocationPage.selectFilter(agPracticeNameOne);
		searchLocationPage.clickToSystemFilter();
		searchLocationPage.selectFilter(systemNameOne);
		searchLocationPage.clickToApplyFilterButton();
		List<String> listOfLocations = new ArrayList<String>();
		listOfLocations.add(aglocOne);
		searchLocationPage.verifyListOfLocationMatchedSearchResult(listOfLocations);
	}

	@Test(enabled = true, dependsOnMethods = "testToEnableLocationForAgnosticPractice", retryAnalyzer = RetryAnalyzer.class, description = "Verify disable solution of associated agnostic practice of location")
	public void testToDisableSolutionForLocationForAgnosticPractice() throws Exception {
		locationDetailsPage = searchLocationPage.clickToLocation(aglocOne);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.clickToSytemTab(systemNameOne);
		locationDetailsPage.clickToPracticeTabAndToggleOn(agPracticeNameOne);
		locationDetailsPage.clickToPracticeTab(agPracticeNameOne);
		locationDetailsPage.verifyConnectedSolutionsTitle();
		locationDetailsPage.toggleOffEngage();
		locationDetailsPage.clickToBackButton();
		searchLocationPage.clickToSolutionFilter();
		searchLocationPage.selectFilter(testData.getProperty("solution.engage"));
		searchLocationPage.clickToPracticeFilter();
		searchLocationPage.selectFilter(agPracticeNameOne);
		searchLocationPage.clickToSystemFilter();
		searchLocationPage.selectFilter(systemNameOne);
		searchLocationPage.clickToApplyFilterButton();
		searchLocationPage.searchNonExistingLocationAndVerifyResult(aglocOne);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify enable solution of associated ng practice of location")
	public void testToEnableSolutionForLocationForNGPractice() throws Exception {
		systemNameTwo = createSystem();
		enterpriseTwo = createEnterprise(systemNameTwo);
		practiceNameTwo = createPractice(systemNameTwo, enterpriseTwo);
		addSolutionsWithinPractice(practiceNameTwo);
		generalSettingsPage.verifyLocationsTab();
		generalSettingsPage.clickOnLocationsTab();
		locOne = createLocation();
		locationDetailsPage = searchLocationPage.clickToLocation(locOne);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.clickToSytemTab(systemNameTwo);
		locationDetailsPage.clickToEnterpriseTabPracticeAndToggleOn(enterpriseTwo, practiceNameTwo);
		locationDetailsPage.clickToPracticeTab(practiceNameTwo);
		locationDetailsPage.verifyConnectedSolutionsTitle();
		locationDetailsPage.toggleOnEngage();
		locationDetailsPage.clickToBackButton();
		searchLocationPage.clickToSolutionFilter();
		searchLocationPage.selectFilter(testData.getProperty("solution.engage"));
		searchLocationPage.clickToEnterpriseFilter();
		searchLocationPage.selectFilter(enterpriseTwo);
		searchLocationPage.clickToPracticeFilter();
		searchLocationPage.selectFilter(practiceNameTwo);
		searchLocationPage.clickToSystemFilter();
		searchLocationPage.selectFilter(systemNameTwo);
		searchLocationPage.clickToApplyFilterButton();
		List<String> listOfLocations = new ArrayList<String>();
		listOfLocations.add(locOne);
		searchLocationPage.verifyListOfLocationMatchedSearchResult(listOfLocations);
	}

	@Test(enabled = true, dependsOnMethods = "testToEnableLocationForNGPractice", retryAnalyzer = RetryAnalyzer.class, description = "Verify disable solution of associated ng practice of location")
	public void testToDisableSolutionForLocationForNGPractice() throws Exception {
		locationDetailsPage = searchLocationPage.clickToLocation(locOne);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.clickToSytemTab(systemNameTwo);
		locationDetailsPage.clickToEnterpriseTabPracticeAndToggleOn(enterpriseTwo, practiceNameTwo);
		locationDetailsPage.clickToPracticeTab(practiceNameTwo);
		locationDetailsPage.verifyConnectedSolutionsTitle();
		locationDetailsPage.toggleOffEngage();
		locationDetailsPage.clickToBackButton();
		searchLocationPage.clickToSolutionFilter();
		searchLocationPage.selectFilter(testData.getProperty("solution.engage"));
		searchLocationPage.clickToEnterpriseFilter();
		searchLocationPage.selectFilter(enterpriseTwo);
		searchLocationPage.clickToPracticeFilter();
		searchLocationPage.selectFilter(practiceNameTwo);
		searchLocationPage.clickToSystemFilter();
		searchLocationPage.selectFilter(systemNameTwo);
		searchLocationPage.clickToApplyFilterButton();
		searchLocationPage.searchNonExistingLocationAndVerifyResult(locOne);
	}
}
