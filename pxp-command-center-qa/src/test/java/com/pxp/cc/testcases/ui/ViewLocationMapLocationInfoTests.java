//Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

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
import com.pxp.cc.objectmaps.LocationDetailsPage;

public class ViewLocationMapLocationInfoTests extends CommandCenterGenericTest {
	private ClientDirectoryPage clientDirectoryPage;
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	private GeneralSettingsPage generalSettingsPage;
	private AddLocationPage addLocationPage;
	private LocationDetailsPage locationDetailsPage;
	String clientName, locationName;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		createClientPage = new CreateClientPage(driver);
		logStep("Select create client button from new dropdown");
		createClientPage.clickCreateClient();
		clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientPage.createNewClient(clientName, clientSapNumber, testData.getProperty("new.client.type"),
				testData.getProperty("new.client.timezone"));
		logStep("Click on create button on the create client popup");
		createClientPage.clickConfirmPopup();
		logStep("Searching a created client in client directory");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(clientName);
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.verifyClientNodeIsSelected();
		createLocation();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
	}

	public void createLocation() throws Exception {
		generalSettingsPage.clickOnGeneralSettingsTab();
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(clientName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify Locations tab is displayed");
		generalSettingsPage.verifyLocationsTab();
		generalSettingsPage.clickOnLocationsTab();
		addLocationPage = new AddLocationPage(driver);
		logStep("Click on Add button");
		addLocationPage.verifyLocationsPageTitle();
		addLocationPage.verifyAddLocationButton();
		addLocationPage.verifyAddButtonIsEnabled();
		addLocationPage.clickOnAddLocationButton();
		logStep("Enter data in all input fields");
		locationName = CCUtils.generateAlphaNumericString(18);
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
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify Location Name and Location Info Section")
	public void testLocationNameAndLocationInfoSection() throws Exception {
		logStep("Verify Location Name and Location Info Section");
		locationDetailsPage.verifyLocationInfoSection();
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify Copied text")
	public void testCopyIcon() throws Exception {
		logStep("Verify copied text");
		locationDetailsPage.verifyCopiedText();
	}
	
	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify Location name, Address and timezone on Location details page")
	public void verifyLocationNameAddressAndTimezone() throws Exception {
		logStep("Verify Location name, Address and timezone on Location details page");	
		locationDetailsPage.verifyLocationNameAddressAndTimezone();
	}
}