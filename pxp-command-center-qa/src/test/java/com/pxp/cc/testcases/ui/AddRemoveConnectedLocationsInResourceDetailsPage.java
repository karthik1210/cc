// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;
import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.util.CCUtils;
import com.intuit.ifs.csscat.core.RetryAnalyzer;

public class AddRemoveConnectedLocationsInResourceDetailsPage extends CommandCenterGenericTest{
	
	static boolean runOnce = true;
	public CCUI ccui;
	public String providerFirstName, locationName, resourceName;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		if (runOnce) {
			ccui = new CCUI(testData, driver);
			logStep("Login to command center portal");
			ccui.getClientDirectoryPage();
			ccui.initializeTestData();
			CCUI.getClientSystementErpriseandPractice();
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
			ccui.getNgHierarchyClientCreationPage()
					.clickSearchResult(clientSystementErpriseandPractice.get("clientNam"));
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
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		logStep("Verify ADD button is enabled for client level on provider page");
		ccui.getAddNewProviderPage().verifyAddButtonIsEnabled();
		ccui.getAddNewProviderPage().clickOnAddProviderButton();
		String lastName = CCUtils.generateAlphaNumericString(3).concat("-")
				.concat(CCUtils.generateAlphaNumericString(3)).concat("'s");
		String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		String credentials = testData.getProperty("provider.credentials");
		logStep("Enter valid details in mandatory field and verify success toaster message");
		ccui.getAddNewProviderPage().enterFirstName(providerFirstName);
		ccui.getAddNewProviderPage().enterLastName(lastName);
		ccui.getAddNewProviderPage().enterCredentials(credentials);
		ccui.getAddNewProviderPage().enterExternalId(externalId);
		ccui.getAddNewProviderPage().selectGenderValue("Male");
		ccui.getAddNewProviderPage().verifySaveAndAddButtonIsEnabled();
		ccui.getAddNewProviderPage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New Provider has been successfully created");
	}

	@Test(enabled = false)
	public void createResourceWithinPractice() throws Exception {
		ccui.getResourceAndStaffSearchPage().clickToResourcesTab();
		ccui.getAddNewResourcePage().verifyAddButtonIsEnabled();
		ccui.getAddNewResourcePage().clickOnAddResourceButton();
		resourceName = "NewResource"
				+ CCUtils.generateAlphaNumericString(3).concat("-").concat(CCUtils.generateAlphaNumericString(3));
		logStep("Verify ADD button is enabled for client level on resource page");
		String externalIds = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		ccui.getAddNewResourcePage().enterResourceName(resourceName);
		ccui.getAddNewResourcePage().enterExternalId(externalIds);
		ccui.getAddNewResourcePage().selectResourceType("Person");
		ccui.getAddNewResourcePage().enterConnectedProvider(providerFirstName);
		ccui.getAddNewResourcePage().clickFirstInAutoSuggestion();
		ccui.getAddNewResourcePage().verifySaveAndAddButtonIsEnabled();
		ccui.getAddNewResourcePage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New Resource has been successfully created");
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
		ccui.addLocationsPage().enterZip("00");
		ccui.addLocationsPage().addStateAndTimezone(testData.getProperty("new.locations.state"),
				testData.getProperty("new.locations.timezone"));
		logStep("Click on save and add button");
		ccui.addLocationsPage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.addLocationsPage().verifyToasterMessage("Success! New location has been successfully created");
	}

	@Test(priority = 0, retryAnalyzer = RetryAnalyzer.class, description = "test to add location in resource details page")
	public void testAddLocationInResourceDetailsPage() throws Exception {
		ngHierarchyClientCreate();
		createProviderWithinPractice();
		createResourceWithinPractice();
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
		ccui.getResourceAndStaffSearchPage().clickToResourcesTab();
		ccui.resourceDetailsPage().clickToSearchButton();
		List<String> listOfResource = new ArrayList<String>();
		listOfResource.add(resourceName);
		ccui.resourceDetailsPage().verifySearchResourceFound(resourceName);
		ccui.resourceDetailsPage().verifyResourceDetailsTitle();
		ccui.resourceDetailsPage().selectPractice(clientSystementErpriseandPractice.get("practiceName"));
		ccui.resourceDetailsPage().verifyConnectedLocationSection();
		ccui.resourceDetailsPage().clickToAddLocationButton();
		ccui.resourceDetailsPage().enterTextInLocationTextBox(locationName);
		ccui.resourceDetailsPage().clickToLocationCheckBox(locationName);
		ccui.resourceDetailsPage().clickToAddButtonInPopup();
		ccui.getCommonMethods().verifyToasterMessage("Changes updated successfully");
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test to remove location in provider details page")
	public void testRemoveLocationInProviderDetailsPage() throws Exception {
		ngHierarchyClientCreate();
		createProviderWithinPractice();
		createResourceWithinPractice();
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
		ccui.getResourceAndStaffSearchPage().clickToResourcesTab();
		ccui.resourceDetailsPage().clickToSearchButton();
		List<String> listOfResource = new ArrayList<String>();
		listOfResource.add(resourceName);
		ccui.resourceDetailsPage().verifySearchResourceFound(resourceName);
		ccui.resourceDetailsPage().verifyResourceDetailsTitle();
		ccui.resourceDetailsPage().selectPractice(clientSystementErpriseandPractice.get("practiceName"));
		ccui.resourceDetailsPage().verifyConnectedLocationSection();
		ccui.resourceDetailsPage().clickToAddLocationButton();
		ccui.resourceDetailsPage().enterTextInLocationTextBox(locationName);
		ccui.resourceDetailsPage().clickToLocationCheckBox(locationName);
		ccui.resourceDetailsPage().clickToAddButtonInPopup();
		ccui.getCommonMethods().verifyToasterMessage("Changes updated successfully");
		ccui.resourceDetailsPage().clickToRemoveParticularLocation(locationName);
		ccui.resourceDetailsPage().verifyNoDataDisplayConnectedLocationsMessage();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "test web elements of add location popup")
	public void testWebElementsOfAddLocationPopup() throws Exception {
		ngHierarchyClientCreate();
		createProviderWithinPractice();
		createResourceWithinPractice();
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
		ccui.getResourceAndStaffSearchPage().clickToResourcesTab();
		ccui.resourceDetailsPage().clickToSearchButton();
		List<String> listOfResource = new ArrayList<String>();
		listOfResource.add(resourceName);
		ccui.resourceDetailsPage().verifySearchResourceFound(resourceName);
		ccui.resourceDetailsPage().verifyResourceDetailsTitle();
		ccui.resourceDetailsPage().selectPractice(clientSystementErpriseandPractice.get("practiceName"));
		ccui.resourceDetailsPage().verifyConnectedLocationSection();
		ccui.resourceDetailsPage().clickToAddLocationButton();
		ccui.resourceDetailsPage().verifyPopupTitle("Add Location");
		ccui.resourceDetailsPage().verifyPopupLabel();
		ccui.resourceDetailsPage().verifyCrossIcon();
		ccui.resourceDetailsPage().verifyCancelButton();
		ccui.resourceDetailsPage().verifyAddButton();
		runOnce = false;
	}
}
