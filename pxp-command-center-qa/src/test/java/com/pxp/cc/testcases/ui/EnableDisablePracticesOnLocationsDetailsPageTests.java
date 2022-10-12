// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

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
import com.pxp.cc.objectmaps.LocationDetailsPage;
import com.pxp.cc.objectmaps.SearchLocationPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class EnableDisablePracticesOnLocationsDetailsPageTests extends CommandCenterGenericTest {
	private ClientDirectoryPage clientDirectoryPage;
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	private GeneralSettingsPage generalSettingsPage;
	private AddLocationPage addLocationPage;
	private AddOrgNodePage addOrgNodePage;
	private SearchLocationPage searchLocationPage;
	private LocationDetailsPage locationDetailsPage;
	public String systemNameOne, systemNameTwo, systemThree, systemFour, agPracticeNameOne, practiceNameOne,
			practiceNameTwo, enterpriseOne, enterpriseTwo, agPracticeNameTwo;
	String aglocOne, agLocTwo, locOne, locTwo;

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
		clientDirectoryPage.searchNode("Aa_qa_shippuden");
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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify enabling NG practice for created location within client")
	public void testToEnableLocationForNGPractice() throws Exception {
		systemNameOne = createSystem();
		enterpriseOne = createEnterprise(systemNameOne);
		practiceNameOne = createPractice(systemNameOne, enterpriseOne);
		locOne = createLocation();
		locationDetailsPage = searchLocationPage.clickToLocation(locOne);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.clickToSytemTab(systemNameOne);
		locationDetailsPage.clickToEnterpriseTabPracticeAndToggleOn(enterpriseOne, practiceNameTwo);
		locationDetailsPage.clickToBackButton();
		searchLocationPage = new SearchLocationPage(driver);
		searchLocationPage.enterLocationInSearchTextBox(locOne);
		List<String> listOfLocName = new ArrayList<String>();
		listOfLocName.add(locOne);
		searchLocationPage.clickToEnterpriseFilter();
		searchLocationPage.selectFilter(enterpriseOne);
		searchLocationPage.clickToPracticeFilter();
		searchLocationPage.selectFilter(practiceNameTwo);
		searchLocationPage.clickToApplyFilterButton();
		searchLocationPage.verifySearchLocationFound(listOfLocName);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify disabling NG practice for created location within client")
	public void testToDisableLocationForNGPractice() throws Exception {
		systemNameTwo = createSystem();
		enterpriseTwo = createEnterprise(systemNameTwo);
		practiceNameTwo = createPractice(systemNameTwo, enterpriseTwo);
		locTwo = createLocation();
		locationDetailsPage = searchLocationPage.clickToLocation(locTwo);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.clickToSytemTab(systemNameTwo);
		locationDetailsPage.clickToEnterpriseTabPracticeAndToggleOFF(enterpriseTwo, practiceNameTwo);
		locationDetailsPage.clickToBackButton();
		searchLocationPage = new SearchLocationPage(driver);
		searchLocationPage.enterLocationInSearchTextBox(locTwo);
		List<String> listOfLocName = new ArrayList<String>();
		listOfLocName.add(locTwo);
		searchLocationPage.clickToEnterpriseFilter();
		searchLocationPage.selectFilter(enterpriseTwo);
		searchLocationPage.clickToPracticeFilter();
		searchLocationPage.selectFilter(practiceNameTwo);
		searchLocationPage.clickToApplyFilterButton();
		searchLocationPage.verifyNoDataDisplayErrorMessage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify enabling agnostic practice for created location within client")
	public void testToEnableLocationForAgnosticPractice() throws Exception {
		systemThree = createSystem();
		agPracticeNameOne = createAgnosticPractice(systemThree);
		aglocOne = createLocation();
		locationDetailsPage = searchLocationPage.clickToLocation(aglocOne);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.clickToSytemTab(systemThree);
		locationDetailsPage.clickToPracticeTabAndToggleON(agPracticeNameOne);
		locationDetailsPage.clickToBackButton();
		searchLocationPage = new SearchLocationPage(driver);
		searchLocationPage.enterLocationInSearchTextBox(aglocOne);
		List<String> listOfLocName = new ArrayList<String>();
		listOfLocName.add(aglocOne);
		searchLocationPage.clickToPracticeFilter();
		searchLocationPage.selectFilter(agPracticeNameOne);
		searchLocationPage.clickToApplyFilterButton();
		searchLocationPage.verifySearchLocationFound(listOfLocName);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify disabling agnostic practice for created location within client")
	public void testToDisableLocationForAgnosticPractice() throws Exception {
		systemFour = createSystem();
		agPracticeNameTwo = createAgnosticPractice(systemFour);
		agLocTwo = createLocation();
		locationDetailsPage = searchLocationPage.clickToLocation(agLocTwo);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.clickToSytemTab(systemFour);
		locationDetailsPage.clickToPracticeTabAndToggleOFF(agPracticeNameTwo);
		locationDetailsPage.clickToBackButton();
		searchLocationPage = new SearchLocationPage(driver);
		searchLocationPage.enterLocationInSearchTextBox(agLocTwo);
		List<String> listOfLocName = new ArrayList<String>();
		listOfLocName.add(agLocTwo);
		searchLocationPage.clickToPracticeFilter();
		searchLocationPage.selectFilter(agPracticeNameTwo);
		searchLocationPage.clickToApplyFilterButton();
		searchLocationPage.verifyNoDataDisplayErrorMessage();
	}
}
