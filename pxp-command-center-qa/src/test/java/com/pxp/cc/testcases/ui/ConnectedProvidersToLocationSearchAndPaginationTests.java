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
import com.pxp.cc.objectmaps.LocationDetailsPage;

public class ConnectedProvidersToLocationSearchAndPaginationTests extends CommandCenterGenericTest {
	private ClientDirectoryPage clientDirectoryPage;
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	private GeneralSettingsPage generalSettingsPage;
	private AddLocationPage addLocationPage;
	private LocationDetailsPage locationDetailsPage;
	private AddNewProviderPage addNewProviderPage;
	private AddOrgNodePage addOrgNodePage;

	String locationName, providerFirstName, clientName, externalId, providerOne, providerTwo, providerThree,
			providerFour, providerFive, providerSix, providerSeven, providerEight, providerNine, providerTen,
			providerEleven;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		commonMethods = new CommonMethods(driver);
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

	public String createProvider() throws Exception {
		createAgnosticPracticeNode();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Searching a practice");
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText("PracticeForDemo");
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		addNewProviderPage = new AddNewProviderPage(driver);
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		addNewProviderPage.clickOnAddProviderButton();
		providerFirstName = "Pro"
				+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
		String lastName = CCUtils.generateAlphaNumericString(5).concat("-")
				.concat(CCUtils.generateAlphaNumericString(6)).concat("'s");
		String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		String credentials = testData.getProperty("provider.credentials");
		addNewProviderPage.enterFirstName(providerFirstName);
		addNewProviderPage.enterLastName(lastName);
		addNewProviderPage.enterCredentials(credentials);
		addNewProviderPage.enterExternalId(externalId);
		addNewProviderPage.selectGenderValue("Male");
		addNewProviderPage.verifySaveAndAddButtonIsEnabled();
		addNewProviderPage.clickOnSaveAndAddButton();
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		return providerFirstName;
	}

	public String createMultipleProviders() throws Exception {
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		addNewProviderPage.clickOnAddProviderButton();
		providerFirstName = "Pro"
				+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
		String lastName = CCUtils.generateAlphaNumericString(5).concat("-")
				.concat(CCUtils.generateAlphaNumericString(6)).concat("'s");
		String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		String credentials = testData.getProperty("provider.credentials");
		addNewProviderPage.enterFirstName(providerFirstName);
		addNewProviderPage.enterLastName(lastName);
		addNewProviderPage.enterCredentials(credentials);
		addNewProviderPage.enterExternalId(externalId);
		addNewProviderPage.selectGenderValue("Male");
		addNewProviderPage.verifySaveAndAddButtonIsEnabled();
		addNewProviderPage.clickOnSaveAndAddButton();
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		return providerFirstName;
	}

	public void createLocation() throws Exception {
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.clickOnGeneralSettingsTab();
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(clientName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		generalSettingsPage.verifyLocationsTab();
		generalSettingsPage.clickOnLocationsTab();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.verifyLocationsPageTitle();
		addLocationPage.verifyAddLocationButton();
		addLocationPage.verifyAddButtonIsEnabled();
		addLocationPage.clickOnAddLocationButton();
		locationName = "Loc" + CCUtils.generateAlphaNumericString(18);
		addLocationPage.enterLocationName(locationName);
		addLocationPage.enterAddressOne(CCUtils.generateAlphaNumericString(18));
		addLocationPage.enterCity(CCUtils.generateAlphaNumericString(18));
		addLocationPage.enterLocationIdExternal(CCUtils.generateAlphaNumericString(18));
		addLocationPage.enterZip(CCUtils.generateAlphaNumericString(18));
		addLocationPage.addStateAndTimezone(testData.getProperty("new.locations.state"),
				testData.getProperty("new.locations.timezone"));
		addLocationPage.clickOnSaveAndAddButton();
	}

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Verify search connected Providers section")
	public void testSearchConnectedProvidersSection() throws Exception {
		logStep("Verify Search Connected Providers Section");
		createLocation();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.verifyConnectedProvidersLabel();
		locationDetailsPage.verifyProvidersSearchTextBoxAndPlaceholderIsDisplayed();
		locationDetailsPage.verifyProvidersSearchButtonIsDisplayed();
	}

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Test to check Search Button Functionality for Connected Providers")
	public void testSearchConnectedProvidersFunctionality() throws Exception {
		logStep("Verify Search Button Functionality for Connected Providers");
		createProvider();
		createLocation();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.verifyLocationsPageTitle();
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
		locationDetailsPage.verifyConnectedProvidersLabel();
		logStep("checking search button functionality");
		locationDetailsPage.verifyConnectedProvidersSearchButtonFunctionality(providerFirstName);
		logStep("Verifying results for searched provider");
		locationDetailsPage.verifySearchedResult(providerFirstName);
	}

	@Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Test to check CLEAR Button Functionality for Connected Providers")
	public void testClearButtonFunctionality() throws Exception {
		logStep("Verify CLEAR Button Functionality for Connected Providers");
		createLocation();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.verifyConnectedProvidersLabel();
		locationDetailsPage.searchProviders("connectedProvider");
		logStep("checking clear button functionality");
		locationDetailsPage.verifyProvidersClearButtonFunctionality("connectedProvider");
	}

	@Test(priority = 4, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to check No results found is displayed if Providers name is not present")
	public void testToCheckNoResultsFoundMessageForNonExistingResource() throws Exception {
		logStep("Searching a non-existing Providers in connected Providers table");
		createLocation();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.verifyConnectedProvidersLabel();
		locationDetailsPage.searchProviders("nonexisting provider");
		locationDetailsPage.clickProvidersSearchButton();
		logStep("Verifying no result found scenario");
		locationDetailsPage.testToCheckNoResultsFoundMessage();
	}

	@Test(priority = 5, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Display of default pagination i.e., 10 items per page.")
	public void testDefaultItemsPerPageAndDefaultPaginationInConnectedProvidersWebtable() throws Exception {
		logStep("Comparing the default Items per Page");
		createLocation();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.verifyConnectedProvidersLabel();
		CCUtils.assertEquals(locationDetailsPage.getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(locationDetailsPage.getDefaultPagination(), "0");
		locationDetailsPage.isFirstPageButtonDisabled();
		locationDetailsPage.isPreviousPageButtonDisabled();
		locationDetailsPage.isNextPageButtonDisabled();
		locationDetailsPage.isLastPageButtonDisabled();
	}

	@Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "Verify numbers of rows and Pagination should be change it we changes 'Items Per Page' value.")
	public void testAllPaginationButtonsClickable() throws Exception {
		logStep("Verify numbers of rows and Pagination should be change it we changes 'Items Per Page' value.");
		providerOne = createProvider();
		providerTwo = createMultipleProviders();
		providerThree = createMultipleProviders();
		providerFour = createMultipleProviders();
		providerFive = createMultipleProviders();
		providerSix = createMultipleProviders();
		providerSeven = createMultipleProviders();
		providerEight = createMultipleProviders();
		providerNine = createMultipleProviders();
		providerTen = createMultipleProviders();
		providerEleven = createMultipleProviders();
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		createLocation();
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.clickOnGeneralSettingsTab();
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(clientName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		generalSettingsPage.verifyLocationsTab();
		generalSettingsPage.clickOnLocationsTab();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.verifyLocationsPageTitle();
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
		locationDetailsPage.addConnectedProviders(providerOne);
		locationDetailsPage.addConnectedProviders(providerTwo);
		locationDetailsPage.addConnectedProviders(providerThree);
		locationDetailsPage.addConnectedProviders(providerFour);
		locationDetailsPage.addConnectedProviders(providerFive);
		locationDetailsPage.addConnectedProviders(providerSix);
		locationDetailsPage.addConnectedProviders(providerSeven);
		locationDetailsPage.addConnectedProviders(providerEight);
		locationDetailsPage.addConnectedProviders(providerNine);
		locationDetailsPage.addConnectedProviders(providerTen);
		locationDetailsPage.addConnectedProviders(providerEleven);
		CCUtils.assertEquals(locationDetailsPage.getDefaultItemsPerPage(), "10");
		logStep("Check Next Button functionality when Items Per Page value is = 10.");
		locationDetailsPage.clickProvidersNextPageButton();
		logStep("Check Previous Button functionality when Items Per Page value is = 10.");
		locationDetailsPage.clickProvidersPreviousPageButton();
		logStep("Check Next Last functionality when Items Per Page value is = 10.");
		locationDetailsPage.clickProvidersLastPageButton();
		logStep("Check Next first functionality when Items Per Page value is = 10.");
		locationDetailsPage.clickProvidersFirstPageButton();
	}
}