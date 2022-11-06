//Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddLocationPage;
import com.pxp.cc.objectmaps.AddNewProviderPage;
import com.pxp.cc.objectmaps.AddOrgNodePage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;
import com.pxp.cc.objectmaps.LocationDetailsPage;;

public class AddOrRemoveConnectedProvidersToLocationTests extends CommandCenterGenericTest {
	private ClientDirectoryPage clientDirectoryPage;
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	private GeneralSettingsPage generalSettingsPage;
	private AddLocationPage addLocationPage;
	private LocationDetailsPage locationDetailsPage;
	private AddNewProviderPage addNewProviderPage;
	private AddOrgNodePage addOrgNodePage;

	String locationName, providerFirstName, resourceName, clientName;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		createClientPage = new CreateClientPage(driver);
		createClientPage.clickCreateClient();
		commonMethods = new CommonMethods(driver);
		clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientPage.createNewClient(clientName, clientSapNumber, testData.getProperty("new.client.type"),
				testData.getProperty("new.client.timezone"));
		createClientPage.clickConfirmPopup();
		clientDirectoryPage.searchAndSelectClientNameInClientTable(clientName);
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.verifyClientNodeIsSelected();
	}

	public void createAgnosticPracticeNode() throws Exception {
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.addNewSystem("SystemDemo");
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnExpandArrow();
		addOrgNodePage.addNewPractice("PracticeForDemo");
	}

	public void createProvider() throws Exception {
		createAgnosticPracticeNode();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Searching a practice");
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText("PracticeForDemo");
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify Providers And Providers tab is displayed");
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		addNewProviderPage = new AddNewProviderPage(driver);
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		logStep("Verify ADD button is enabled for practice level on provider page");
		addNewProviderPage.verifyAddButtonIsEnabled();
		addNewProviderPage.clickOnAddProviderButton();
		providerFirstName = "Pro"
				+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
		String lastName = CCUtils.generateAlphaNumericString(5).concat("-")
				.concat(CCUtils.generateAlphaNumericString(6)).concat("'s");
		String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		String credentials = testData.getProperty("provider.credentials");
		logStep("Enter valid details in mandatory field and verify success toaster message");
		addNewProviderPage.enterFirstName(providerFirstName);
		addNewProviderPage.enterLastName(lastName);
		addNewProviderPage.enterCredentials(credentials);
		addNewProviderPage.enterExternalId(externalId);
		addNewProviderPage.selectGenderValue("Male");
		addNewProviderPage.verifySaveAndAddButtonIsEnabled();
		addNewProviderPage.clickOnSaveAndAddButton();
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Success! New Provider has been successfully created");
		addNewProviderPage = new AddNewProviderPage(driver);
		addNewProviderPage.verifyProvidersAndResourcesTitle();
	}

	public void createLocation() throws Exception {
		generalSettingsPage = new GeneralSettingsPage(driver);
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
		locationName = "Loc".concat(CCUtils.generateAlphaNumericString(10));
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

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Verify Connected Providers Section on Location details page")
	public void testConnectedProvidersSection() throws Exception {
		logStep("Verify Connected Providers Section on Location details page");
		createLocation();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.verifyConnectedProvidersSection();
	}

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Verify Add Providers Popup on Location details page")
	public void testAddProvidersPopup() throws Exception {
		logStep("Verify Add Providers Popup on Location details page");
		createLocation();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.verifyConnectedProvidersLabel();
		locationDetailsPage.clickOnAdd();
		locationDetailsPage.verifyAddProviderPopupTitle();
		locationDetailsPage.verifyProviderNameLabelTextAndTextboxDefaultValue();
	}

	@Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Verify add Connected Providers Functionality")
	public void testAddConnectedProvidersFunctionality() throws Exception {
		logStep("Verify add Connected Providers Functionality");
		createProvider();
		createLocation();
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.clickToSytemTab("SystemDemo");
		locationDetailsPage.clickToPracticeTabAndToggleOn("PracticeForDemo");
		locationDetailsPage.clickOnPracticeNode();
		locationDetailsPage.verifyConnectedProvidersLabel();
		locationDetailsPage.clickOnAdd();
		locationDetailsPage.enterProviderName(providerFirstName);
		locationDetailsPage.clickOnAddToSave();
		addLocationPage = new AddLocationPage(driver);
		logStep("Verify success toaster message");
		addLocationPage.verifyToasterMessage("Successfully added provider");
	}

	@Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Verify add and remove Connected Providers Functionality")
	public void testAddAndRemoveConnectedProvidersFunctionality() throws Exception {
		logStep("Verify add and remove Connected Providers Functionality");
		createProvider();
		createLocation();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.clickToSytemTab("SystemDemo");
		locationDetailsPage.clickToPracticeTabAndToggleOn("PracticeForDemo");
		locationDetailsPage.clickOnPracticeNode();
		locationDetailsPage.verifyConnectedProvidersLabel();
		locationDetailsPage.clickOnAdd();
		locationDetailsPage.enterProviderName(providerFirstName);
		locationDetailsPage.clickOnAddToSave();
		logStep("Verify success toaster message");
		locationDetailsPage.verifySuccessToasterMessage("Successfully added provider");
		locationDetailsPage.verifyProviderNameColumnLabel();
		locationDetailsPage.enterSearchText(providerFirstName);
		CCUtils.pressEnterKey();
		locationDetailsPage.verifyRemoveConnectionButtonIsDisplayed();
		locationDetailsPage.clickOnRemoveConnection();
		addLocationPage = new AddLocationPage(driver);
		logStep("Verify success toaster message");
		addLocationPage.verifyToasterMessage("Successfully removed provider");
	}

	@Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "Verify Connected Providers functionality with invalid resource")
	public void testConnectedProvidersFunctionalityWithInvalidResource() throws Exception {
		logStep("Verify Connected Providers functionality with invalid resource");
		createLocation();
		logStep("Verify Locations tab is displayed");
		generalSettingsPage.verifyLocationsTab();
		generalSettingsPage.clickOnLocationsTab();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.verifyConnectedProvidersLabel();
		locationDetailsPage.clickOnAdd();
		locationDetailsPage.verifyProviderNameLabelTextAndTextboxDefaultValue();
		locationDetailsPage.enterNonExistingProviderName(CCUtils.generateAlphaNumericString(10));
		logStep("Verify Add Button is not enabled");
		locationDetailsPage.verifyAddButtonIsDisabled();
	}

	@Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel button and cross icon on Add Providers popup")
	public void testCancelAndCrossIconFunctionality() throws Exception {
		logStep("Verify functionality of cancel button and cross icon on Add Providers popup");
		createLocation();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.verifyConnectedProvidersLabel();
		logStep("Click on Add button");
		locationDetailsPage.clickOnAdd();
		logStep("Click on cancel button");
		locationDetailsPage.clickOnCancelButton();
		logStep("Verify after clicking on cancel button user redirected to Location Details page");
		locationDetailsPage.verifyConnectedProvidersLabel();
		logStep("Click on Add button");
		locationDetailsPage.clickOnAdd();
		logStep("Click on cross button");
		locationDetailsPage.clickOnCrossIcon();
		logStep("Verify after clicking on cross button user redirected to Locations Details page");
		locationDetailsPage.verifyConnectedProvidersLabel();
	}
}
