// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddLocationPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class AddNewLocationTests extends CommandCenterGenericTest {
	private ClientDirectoryPage clientDirectoryPage;
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	private GeneralSettingsPage generalSettingsPage;
	private AddLocationPage addLocationPage;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
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
		commonMethods = new CommonMethods(driver);
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.verifyClientNodeIsSelected();
		logStep("Verify Locations tab is displayed");
		generalSettingsPage.verifyLocationsTab();
		generalSettingsPage.clickOnLocationsTab();
		addLocationPage = new AddLocationPage(driver);
		logStep("Click on Add button");
		addLocationPage.verifyLocationsPageTitle();
		addLocationPage.verifyAddLocationButton();
		addLocationPage.clickOnAddLocationButton();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify add new location popup")
	public void testAddNewLocationPopup() throws Exception {
		logStep("Verify add new location popup");
		addLocationPage.verifyAddNewLocationPopup();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify navigation to Locations page with Locations tab and Locations tile on General Settins page")
	public void testNavigationToLocationsPageWithLocationsTile() throws Exception {
		logStep("Click on cancel button");
		addLocationPage.clickOnCancelButton();
		logStep("Click on General Settings tab");
		generalSettingsPage.clickOnGeneralSettingsTab();
		logStep("Click on Locations tile on General Settings page");
		generalSettingsPage.clickOnLocationsTile();
		logStep("Verify after clicking on Locations tile user directed to Locations page");
		addLocationPage.verifyLocationsPageTitle();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify add new location functionality with valid details in all input fields")
	public void testAddNewLocationFunctionalityWithAllValidDetails() throws Exception {
		logStep("Enter data in all input fields");
		addLocationPage.enterLocationName(CCUtils.generateAlphaNumericString(18));
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
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify add new location functionality with valid details in all mandatory fields")
	public void testAddNewLocationFunctionalityWithAllValidDetailsInMandatoryFields() throws Exception {
		logStep("Enter data in mandatory fields");
		addLocationPage.enterLocationName(CCUtils.generateAlphaNumericString(18));
		addLocationPage.enterLocationDisplayName(CCUtils.generateAlphaNumericString(18));
		addLocationPage.enterAddressOne(CCUtils.generateAlphaNumericString(18));
		addLocationPage.enterCity(CCUtils.generateAlphaNumericString(18));
		addLocationPage.enterLocationIdExternal(CCUtils.generateAlphaNumericString(18));
		addLocationPage.enterZip(CCUtils.generateAlphaNumericString(18));
		addLocationPage.addStateAndTimezone(testData.getProperty("new.locations.state"),
				testData.getProperty("new.locations.timezone"));
		logStep("Click on save and add button");
		addLocationPage.clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		addLocationPage.verifyToasterMessage("Success! New location has been successfully created");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in locationName, locationDisplayName, Address1 fields and verify error message")
	public void testHundredMaximumCharactersErrorMessageForInputFields() throws Exception {
		logStep("Verify maximum character reached error message for locationName, locationDisplayName, Address1");
		addLocationPage.verifyHundredMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101),
				CCUtils.generateAlphaNumericString(101), CCUtils.generateAlphaNumericStringIncludingSpecialChar(101));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 50 characters in city, zip, locationId fields and verify error message")
	public void testFiftyMaximumCharactersErrorMessageForInputFields() throws Exception {
		logStep("Verify maximum character reached error message for city, zipcode, locationId fields");
		addLocationPage.verifyFiftyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(51),
				CCUtils.generateAlphaNumericString(51), CCUtils.generateAlphaNumericStringIncludingSpecialChar(51));
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel button and cross icon")
	public void testCancelAndCrossIconFunctionality() throws Exception {
		logStep("Click on cancel button");
		addLocationPage.clickOnCancelButton();
		logStep("Verify after clicking on cancel button user redirected to Locations page");
		addLocationPage.verifyLocationsPageTitle();
		logStep("Click on Add button");
		addLocationPage.clickOnAddLocationButton();
		logStep("Click on cross button");
		addLocationPage.clickOnCrossIcon();
		logStep("Verify after clicking on cross button user redirected to Locations page");
		addLocationPage.verifyLocationsPageTitle();
	}
}
