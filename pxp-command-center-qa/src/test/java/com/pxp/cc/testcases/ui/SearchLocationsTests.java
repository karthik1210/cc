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
	String systemNameOne,systemNameTwo, agPracticeNameOne,practiceNameTwo,enterpriseOne,practiceOne,practiceTwo;
	String aglocOne,agLocTwo,locOne,locTwo,locThree,locFour;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal with url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		/*
		 * commonMethods = new CommonMethods(driver); createClientPage = new
		 * CreateClientPage(driver);
		 * logStep("Select create client button from new dropdown");
		 * createClientPage.clickCreateClient(); commonMethods = new
		 * CommonMethods(driver); logStep("Enter valid details in mandatory fields");
		 * String clientName = testData.getProperty("new.client.name")
		 * .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20)); String
		 * clientSapNumber = testData.getProperty("new.client.sap")
		 * .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		 * createClientPage.createNewClient(clientName, clientSapNumber,
		 * testData.getProperty("new.client.type"),
		 * testData.getProperty("new.client.timezone"));
		 * logStep("Verify active radio button is by default selected on the create client popup"
		 * ); createClientPage.verifyActiveRadioButtonIsByDefaultSelected();
		 * logStep("Click on create button on the create client popup");
		 * createClientPage.clickConfirmPopup(); String actualMessage =
		 * commonMethods.getToasterMessage(); logStep("Verify success toaster message");
		 * CCUtils.assertEquals(actualMessage, "Success! " + clientName +
		 * " has been successfully created");
		 */
		logStep("Searching a created client in client directory");
		clientDirectoryPage.searchNode("Aa_qa_shippuden");
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.verifyClientNodeIsSelected();
		generalSettingsPage.verifyLocationsTab();
		generalSettingsPage.clickOnLocationsTab();
		/*
		 * addOrgNodePage = new AddOrgNodePage(driver);
		 * addOrgNodePage.clickOnManageClient(); addOrgNodePage.clickOnAddSystem();
		 * systemNameOne = "SystemDemo".concat(CCUtils.generateAlphaNumericString(8));
		 * addOrgNodePage.enterNewSystemDetails(systemNameOne);
		 * addOrgNodePage.clickOnManageClient();
		 * addOrgNodePage.clickOnExpandArrow(systemNameOne); practiceNameOne =
		 * "PracticeForDemo".concat(CCUtils.generateAlphaNumericString(8));
		 * addOrgNodePage.addNewPractice(practiceNameOne);
		 * addOrgNodePage.clickOnManageClient();
		 * addOrgNodePage.clickOnExpandArrow(systemNameOne); practiceNameTwo =
		 * "PracticeForDemo".concat(CCUtils.generateAlphaNumericString(8));
		 * generalSettingsPage.clickSearchIcon();
		 * generalSettingsPage.enterSearchText("SystemDemo");
		 * generalSettingsPage.clickFirstInAutoSuggestion(); CCUtils.pressEnterKey();
		 */
		
//		 
		/*
		 * generalSettingsPage.verifyLocationsTab();
		 * generalSettingsPage.clickOnLocationsTab(); addLocationPage = new
		 * AddLocationPage(driver); addLocationPage.clickOnAddLocationButton();
		 * logStep("Enter data in all input fields");
		 * addLocationPage.enterLocationName("RandomLoc".concat(CCUtils.
		 * generateAlphaNumericString(10)));
		 * addLocationPage.enterLocationDisplayName(CCUtils.generateAlphaNumericString(
		 * 10));
		 * addLocationPage.enterAddressOne(CCUtils.generateAlphaNumericString(10));
		 * addLocationPage.enterAddressTwo(CCUtils.generateAlphaNumericString(10));
		 * addLocationPage.enterCity(CCUtils.generateAlphaNumericString(8));
		 * addLocationPage.enterLocationIdExternal(CCUtils.generateAlphaNumericString(12
		 * )); addLocationPage.enterZip(CCUtils.generateAlphaNumericString(8));
		 * addLocationPage.addStateAndTimezone(testData.getProperty(
		 * "new.locations.state"), testData.getProperty("new.locations.timezone"));
		 * logStep("Click on save and add button");
		 * addLocationPage.clickOnSaveAndAddButton();
		 * logStep("Verify success toaster message"); addLocationPage.
		 * verifyToasterMessage("Success! New location has been successfully created");
		 */

		/*
		 * addOrgNodePage.addNewPractice(practiceNameTwo);
		 * generalSettingsPage.verifyClientNodeIsSelected();
		 * logStep("Verify Locations tab is displayed");
		 * generalSettingsPage.verifyLocationsTab();
		 * generalSettingsPage.clickOnLocationsTab(); addLocationPage = new
		 * AddLocationPage(driver); logStep("Click on Add button");
		 * addLocationPage.verifyLocationsPageTitle();
		 * addLocationPage.verifyAddLocationButton();
		 * addLocationPage.clickOnAddLocationButton();
		 * logStep("Enter data in all input fields");
		 * addLocationPage.enterLocationName("LocOne".concat(CCUtils.
		 * generateAlphaNumericString(18)));
		 * addLocationPage.enterLocationDisplayName(CCUtils.generateAlphaNumericString(
		 * 18));
		 * addLocationPage.enterAddressOne(CCUtils.generateAlphaNumericString(18));
		 * addLocationPage.enterAddressTwo(CCUtils.generateAlphaNumericString(18));
		 * addLocationPage.enterCity(CCUtils.generateAlphaNumericString(18));
		 * addLocationPage.enterLocationIdExternal(CCUtils.generateAlphaNumericString(18
		 * )); addLocationPage.enterZip(CCUtils.generateAlphaNumericString(18));
		 * addLocationPage.addStateAndTimezone(testData.getProperty(
		 * "new.locations.state"), testData.getProperty("new.locations.timezone"));
		 * logStep("Click on save and add button");
		 * addLocationPage.clickOnSaveAndAddButton();
		 * logStep("Verify success toaster message"); addLocationPage.
		 * verifyToasterMessage("Success! New location has been successfully created");
		 */
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

	public String createPractice(String orgName) throws InterruptedException {
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnExpandArrow(systemNameOne);
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
		searchLocationPage = new SearchLocationPage(driver);
		searchLocationPage.searchNonExistingLocationAndVerifyResult("NonExistingLocation");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result of a location")
	public void testSearchExistingLocationByFilterSystem() throws Exception {
		/*
		 * systemNameTwo = createSystem(); agPracticeNameOne =
		 * createPractice(systemNameOne); aglocOne = createLocation(); agLocTwo =
		 * createLocation();
		 */
		/*
		 * clientDirectoryPage.searchNode("Aa_qa_shippuden");
		 * clientDirectoryPage.clickSearchButton();
		 * clientDirectoryPage.clickSearchResult();
		 */
		searchLocationPage = new SearchLocationPage(driver);
		searchLocationPage.clickToSystemFilter();
		//searchLocationPage.selectFilter(systemNameOne);
		searchLocationPage.selectFilter("System27-09");
		searchLocationPage.clickToApplyFilterButton();
		/*
		 * searchLocationPage.searchLocationAndVerifyResult(aglocOne);
		 * searchLocationPage.searchLocationAndVerifyResult(agLocTwo);
		 */
		List<String> listOfLocations = new ArrayList<String>();
		listOfLocations.add("asdf");
		listOfLocations.add("hahaha");
		listOfLocations.add("Location_one@5ad");
		searchLocationPage.verifyListOfLocationMatchedSearchResult(listOfLocations);
	}
	
	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search result of a location")
	public void testSearchExistingLocationByFilterEnterprise() throws Exception {
		systemNameOne = createSystem();
		enterpriseOne = 
		agPracticeNameOne = createPractice(systemNameOne);
		aglocOne = createLocation();
		agLocTwo = createLocation();		
		clientDirectoryPage.searchNode("Aa_qa_shippuden");
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		searchLocationPage = new SearchLocationPage(driver);
		searchLocationPage.clickToSystemFilter();
		searchLocationPage.selectFilter(systemNameOne);
		searchLocationPage.clickToApplyFilterButton();
		searchLocationPage.searchLocationAndVerifyResult(aglocOne);
		searchLocationPage.searchLocationAndVerifyResult(agLocTwo);
	}

}
