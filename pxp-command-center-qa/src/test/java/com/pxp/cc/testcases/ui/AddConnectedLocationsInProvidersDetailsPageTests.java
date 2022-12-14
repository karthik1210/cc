// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.util.CCUtils;

public class AddConnectedLocationsInProvidersDetailsPageTests extends CommandCenterGenericTest {

	static boolean runOnce = true;
	public CCUI ccui;
	public String providerFirstName, locationName;

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
			ccui.getNgHierarchyClientCreationPage().clickSearchResult();
			logStep("Navigating To General Settings page");
			ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
			ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
			ccui.getPrivacyPolicySettingsPage()
					.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
			ccui.getPrivacyPolicySettingsPage()
					.clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
		}
	}

	@Test(enabled = false)
	public void createProviderWithinPractice() throws Exception {
		providerFirstName = "NewProvider"
				+ CCUtils.generateAlphaNumericString(3).concat("-").concat(CCUtils.generateAlphaNumericString(3));
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTile();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTile();
		ccui.addNewProviderPage().verifyProvidersAndResourcesTitle();
		logStep("Verify ADD button is enabled for client level on provider page");
		ccui.addNewProviderPage().verifyAddButtonIsEnabled();
		ccui.addNewProviderPage().clickOnAddProviderButton();
		String lastName = CCUtils.generateAlphaNumericString(3).concat("-")
				.concat(CCUtils.generateAlphaNumericString(3)).concat("'s");
		String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		String credentials = testData.getProperty("provider.credentials");
		logStep("Enter valid details in mandatory field and verify success toaster message");
		ccui.addNewProviderPage().enterFirstName(providerFirstName);
		ccui.addNewProviderPage().enterLastName(lastName);
		ccui.addNewProviderPage().enterCredentials(credentials);
		ccui.addNewProviderPage().enterExternalId(externalId);
		ccui.addNewProviderPage().selectGenderValue("Male");
		ccui.addNewProviderPage().verifySaveAndAddButtonIsEnabled();
		ccui.addNewProviderPage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New Provider has been successfully created");
	}

	public void createLocation() throws Exception {
		ccui.getGeneralSettingsPage().clickToClientNode();
		ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		logStep("Click on Add button");
		ccui.addLocationsPage().verifyLocationsPageTitle();
		ccui.addLocationsPage().verifyAddLocationButton();
		ccui.addLocationsPage().clickOnAddLocationButton();
		locationName = "Location".concat(CCUtils.generateAlphaNumericString(8));
		ccui.addLocationsPage().enterLocationName(locationName);
		ccui.addLocationsPage().enterLocationDisplayName(CCUtils.generateAlphaNumericString(8));
		ccui.addLocationsPage().enterAddressOne(CCUtils.generateAlphaNumericString(8));
		ccui.addLocationsPage().enterAddressTwo(CCUtils.generateAlphaNumericString(8));
		ccui.addLocationsPage().enterCity(CCUtils.generateAlphaNumericString(8));
		ccui.addLocationsPage().enterLocationIdExternal(CCUtils.generateAlphaNumericString(8));
		ccui.addLocationsPage().enterZip(CCUtils.generateAlphaNumericString(8));
		ccui.addLocationsPage().addStateAndTimezone(testData.getProperty("new.locations.state"),
				testData.getProperty("new.locations.timezone"));
		logStep("Click on save and add button");
		ccui.addLocationsPage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.addLocationsPage().verifyToasterMessage("Success! New location has been successfully created");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to add location in provider details page")
	public void testAddLocationInProviderDetailsPage() throws Exception {
		ngHierarchyClientCreate();
		createProviderWithinPractice();
		ccui.getResourceAndStaffSearchPage().clickGeneralSettingsIcon();
		createLocation();
		ccui.getSearchLocationDetailsPage().clickToLocation(locationName);
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().clickToSytemTab(clientSystementErpriseandPractice.get("systemName"));
		ccui.getLocationDetailsPage().clickToEnterpriseTabPracticeAndToggleOn(
				clientSystementErpriseandPractice.get("enterpriseName"),
				clientSystementErpriseandPractice.get("practiceName"));
		ccui.getLocationDetailsPage().clickToBackButton();
		ccui.getResourceAndStaffSearchPage().clickGeneralSettingsIcon();
		ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
		ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
		ccui.getPrivacyPolicySettingsPage()
				.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getPrivacyPolicySettingsPage().clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getResourceAndStaffSearchPage().verifyProvidersAndResourcesPageTitle();
		ccui.getResourceAndStaffSearchPage().enterProviderInSearchTextbox(providerFirstName);
		ccui.getResourceAndStaffSearchPage().clickToSearchButton();
		ccui.getResourceAndStaffSearchPage().verifySearchProviderFound(providerFirstName);
		ccui.getProviderDetailsPage().selectPractice(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getProviderDetailsPage().verifyConnectedLocationSection();
		ccui.getProviderDetailsPage().clickToAddLocationButton();
		ccui.getProviderDetailsPage().enterTextInLocationTextBox(locationName);
		ccui.getProviderDetailsPage().clickToLocationCheckBox(locationName);
		ccui.getProviderDetailsPage().clickToAddButtonInPopup();
		ccui.getCommonMethods().verifyToasterMessage("Changes updated successfully");
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to remove location in provider details page")
	public void testRemoveLocationInProviderDetailsPage() throws Exception {
		logStep("Verify Basic Info section At Client Level");
		ngHierarchyClientCreate();
		createProviderWithinPractice();
		ccui.getResourceAndStaffSearchPage().clickGeneralSettingsIcon();
		createLocation();
		ccui.getSearchLocationDetailsPage().clickToLocation(locationName);
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().clickToSytemTab(clientSystementErpriseandPractice.get("systemName"));
		ccui.getLocationDetailsPage().clickToEnterpriseTabPracticeAndToggleOn(
				clientSystementErpriseandPractice.get("enterpriseName"),
				clientSystementErpriseandPractice.get("practiceName"));
		ccui.getLocationDetailsPage().clickToBackButton();
		ccui.getResourceAndStaffSearchPage().clickGeneralSettingsIcon();
		ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
		ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
		ccui.getPrivacyPolicySettingsPage()
				.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getPrivacyPolicySettingsPage().clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getResourceAndStaffSearchPage().verifyProvidersAndResourcesPageTitle();
		ccui.getResourceAndStaffSearchPage().enterProviderInSearchTextbox(providerFirstName);
		ccui.getResourceAndStaffSearchPage().clickToSearchButton();
		ccui.getResourceAndStaffSearchPage().verifySearchProviderFound(providerFirstName);
		ccui.getProviderDetailsPage().selectPractice(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getProviderDetailsPage().verifyConnectedLocationSection();
		ccui.getProviderDetailsPage().clickToAddLocationButton();
		ccui.getProviderDetailsPage().enterTextInLocationTextBox(locationName);
		ccui.getProviderDetailsPage().clickToLocationCheckBox(locationName);
		ccui.getProviderDetailsPage().clickToAddButtonInPopup();
		ccui.getCommonMethods().verifyToasterMessage("Changes updated successfully");
		ccui.getProviderDetailsPage().clickToRemoveParticularLocation(locationName);
		ccui.getProviderDetailsPage().verifyNoDataDisplayConnectedLocationsMessage();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test web elements of add location popup")
	public void testWebElementsOfAddLocationPopup() throws Exception {
		logStep("Verify Basic Info section At Client Level");
		ngHierarchyClientCreate();
		createProviderWithinPractice();
		ccui.getResourceAndStaffSearchPage().clickGeneralSettingsIcon();
		createLocation();
		ccui.getSearchLocationDetailsPage().clickToLocation(locationName);
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().clickToSytemTab(clientSystementErpriseandPractice.get("systemName"));
		ccui.getLocationDetailsPage().clickToEnterpriseTabPracticeAndToggleOn(
				clientSystementErpriseandPractice.get("enterpriseName"),
				clientSystementErpriseandPractice.get("practiceName"));
		ccui.getLocationDetailsPage().clickToBackButton();
		ccui.getResourceAndStaffSearchPage().clickGeneralSettingsIcon();
		ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
		ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
		ccui.getPrivacyPolicySettingsPage()
				.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getPrivacyPolicySettingsPage().clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getResourceAndStaffSearchPage().verifyProvidersAndResourcesPageTitle();
		ccui.getResourceAndStaffSearchPage().enterProviderInSearchTextbox(providerFirstName);
		ccui.getResourceAndStaffSearchPage().clickToSearchButton();
		ccui.getResourceAndStaffSearchPage().verifySearchProviderFound(providerFirstName);
		ccui.getProviderDetailsPage().selectPractice(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getProviderDetailsPage().verifyConnectedLocationSection();
		ccui.getProviderDetailsPage().clickToAddLocationButton();
		ccui.getProviderDetailsPage().verifyPopupTitle("Add Location");
		ccui.getProviderDetailsPage().verifyPopupLabel();
		ccui.getProviderDetailsPage().verifyCrossIcon();
		ccui.getProviderDetailsPage().verifyCancelButton();
		ccui.getProviderDetailsPage().verifyAddButton();
		runOnce = false;
	}
}
