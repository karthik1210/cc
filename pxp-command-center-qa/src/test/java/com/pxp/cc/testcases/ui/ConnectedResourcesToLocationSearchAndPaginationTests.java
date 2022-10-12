//Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
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
import com.pxp.cc.objectmaps.AddNewResourcePage;

public class ConnectedResourcesToLocationSearchAndPaginationTests extends CommandCenterGenericTest {
	private ClientDirectoryPage clientDirectoryPage;
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	private GeneralSettingsPage generalSettingsPage;
	private AddLocationPage addLocationPage;
	private LocationDetailsPage locationDetailsPage;
	private AddNewProviderPage addNewProviderPage;
	private AddNewResourcePage addNewResourcePage;
	private AddOrgNodePage addOrgNodePage;

	String locationName, providerFirstName, resourceName, clientName, externalId, resourceNameOne, resourceNameTwo;

	@BeforeMethod()
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

	public void createProvider() throws Exception {
		createAgnosticPracticeNode();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Searching a practice");
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText("Client Prac12");
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		addNewProviderPage = new AddNewProviderPage(driver);
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		addNewProviderPage.verifyAddButtonIsEnabled();
		addNewProviderPage.clickOnAddProviderButton();
		providerFirstName = "New"
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
	}

	@DataProvider(name = "validTestData")
	public Object[] getValidTestData() {
		Object[][] obj = new Object[][] {
				{ "New" + CCUtils.generateAlphaNumericString(5).concat("-")
						.concat(CCUtils.generateAlphaNumericString(6)) },
				{ "New" + CCUtils.generateAlphaNumericString(5).concat("-")
						.concat(CCUtils.generateAlphaNumericString(6)) },
				{ "New" + CCUtils.generateAlphaNumericString(5).concat("-")
						.concat(CCUtils.generateAlphaNumericString(6)) },
				{ "New" + CCUtils.generateAlphaNumericString(5).concat("-")
						.concat(CCUtils.generateAlphaNumericString(6)) },
				{ "New" + CCUtils.generateAlphaNumericString(5).concat("-")
						.concat(CCUtils.generateAlphaNumericString(6)) },
				{ "New" + CCUtils.generateAlphaNumericString(5).concat("-")
						.concat(CCUtils.generateAlphaNumericString(6)) },
				{ "New" + CCUtils.generateAlphaNumericString(5).concat("-")
						.concat(CCUtils.generateAlphaNumericString(6)) },
				{ "New" + CCUtils.generateAlphaNumericString(5).concat("-")
						.concat(CCUtils.generateAlphaNumericString(6)) },
				{ "New" + CCUtils.generateAlphaNumericString(5).concat("-")
						.concat(CCUtils.generateAlphaNumericString(6)) },
				{ "New" + CCUtils.generateAlphaNumericString(5).concat("-")
						.concat(CCUtils.generateAlphaNumericString(6)) },
				{ "New" + CCUtils.generateAlphaNumericString(5).concat("-")
						.concat(CCUtils.generateAlphaNumericString(6)) },
				{ "New" + CCUtils.generateAlphaNumericString(5).concat("-")
						.concat(CCUtils.generateAlphaNumericString(6)) },
		};
		return obj;
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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search connected scheduling resources section")
	public void testSearchConnectedSchedulingResourcesSection() throws Exception {
		createLocation();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.verifyConnectedSchedulingResourcesLabel();
		locationDetailsPage.verifySearchTextBoxAndPlaceholderIsDisplayed();
		locationDetailsPage.verifySearchButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to check Search Button Functionality for Connected Scheduling Resources")
	public void testSearchConnectedSchedulingResourcesFunctionality() throws Exception {
		createProvider();
		addNewProviderPage = new AddNewProviderPage(driver);
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		addNewResourcePage = new AddNewResourcePage(driver);
		addNewResourcePage.clickOnResourcesTab();
		addNewResourcePage.verifyAddButtonIsEnabled();
		addNewResourcePage.clickOnAddResourceButton();
		resourceName = "New"
				+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
		externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		addNewResourcePage.enterResourceName(resourceName);
		addNewResourcePage.enterExternalId(externalId);
		addNewResourcePage.selectResourceType("Person");
		addNewResourcePage.enterConnectedProvider(providerFirstName);
		addNewResourcePage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		addNewResourcePage.clickOnSaveAndAddButton();
		addNewProviderPage = new AddNewProviderPage(driver);
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		createLocation();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.verifyConnectedSchedulingResourcesLabel();
		locationDetailsPage.clickOnAddButton();
		locationDetailsPage.enterResourceName(resourceName);
		CCUtils.pressEnterKey();
		locationDetailsPage.VerifyAddToSaveIsDisplayed();
		locationDetailsPage.clickOnAddToSave();
		locationDetailsPage.verifyConnectedSchedulingResourcesLabel();
		logStep("checking search button functionality");
		locationDetailsPage.verifySearchButtonFunctionality(resourceName);
		logStep("Verifying results for searched resource");
		locationDetailsPage.verifySearchResult(resourceName);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to check CLEAR Button Functionality for Connected Scheduling Resources")
	public void testClearButtonFunctionality() throws Exception {
		logStep("Test to check CLEAR Button Functionality for Connected Scheduling Resources");
		createLocation();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.verifyConnectedSchedulingResourcesLabel();
		locationDetailsPage.searchResource("connectedresource");
		logStep("checking clear button functionality");
		locationDetailsPage.verifyClearButtonFunctionality("connectedresource");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to check No results found is displayed if resource name not present")
	public void testToCheckNoResultsFoundMessageForNonExistingResource() throws Exception {
		logStep("Searching a non-existing resource in connected scheduling resources table");
		createLocation();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.verifyConnectedSchedulingResourcesLabel();
		locationDetailsPage.searchResource("nonexisting resource");
		locationDetailsPage.clickSearchButton();
		logStep("Verifying no result found scenario");
		locationDetailsPage.testToCheckNoResultsFound();
		locationDetailsPage.verifyConnectedSchedulingResourcesLabel();
		locationDetailsPage.searchResource("nonexisting resource");
		locationDetailsPage.clickSearchButton();
		logStep("Verifying no result found scenario");
		locationDetailsPage.testToCheckNoResultsFound();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Display of default pagination i.e., 10 items per page.")
	public void testDefaultItemsPerPageAndDefaultPaginationInConnectedResourcesWebtable() throws Exception {
		logStep("Comparing the default Items per Page");
		createLocation();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.verifyConnectedSchedulingResourcesLabel();
		locationDetailsPage.searchResource("nonexisting resource");
		locationDetailsPage.clickSearchButton();
		logStep("Verifying no result found scenario");
		locationDetailsPage.testToCheckNoResultsFound();
		locationDetailsPage.verifyConnectedSchedulingResourcesLabel();
		locationDetailsPage.verifyConnectedSchedulingResourcesLabel();
		CCUtils.assertEquals(locationDetailsPage.getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(locationDetailsPage.getDefaultPagination(), "0");
		locationDetailsPage.isFirstPageButtonDisabled();
		locationDetailsPage.isPreviousPageButtonDisabled();
		locationDetailsPage.isNextPageButtonDisabled();
		locationDetailsPage.isLastPageButtonDisabled();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "validTestData", description = "Verify numbers of rows and Pagination should be change it we changes 'Items Per Page' value.")
	public void testAllPaginationButtonsClickable(String[] getTestData) throws Exception {
		logStep("Verify numbers of rows and Pagination should be change it we changes 'Items Per Page' value.");
		createProvider();
		addNewProviderPage = new AddNewProviderPage(driver);
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		addNewResourcePage = new AddNewResourcePage(driver);
		addNewResourcePage.clickOnResourcesTab();
		addNewResourcePage.verifyAddButtonIsEnabled();
		addNewResourcePage.clickOnAddResourceButton();
		externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		addNewResourcePage.enterResourceName(getTestData[0]);
		addNewResourcePage.enterExternalId(externalId);
		addNewResourcePage.selectResourceType("Person");
		addNewResourcePage.enterConnectedProvider(providerFirstName);
		addNewResourcePage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		addNewResourcePage.clickOnSaveAndAddButton();
		addNewProviderPage = new AddNewProviderPage(driver);
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		createLocation();
		addLocationPage = new AddLocationPage(driver);
		addLocationPage.clickSearchIcon();
		addLocationPage.enterSearchText(locationName);
		CCUtils.pressEnterKey();
		addLocationPage.selectLocation();
		locationDetailsPage = new LocationDetailsPage(driver);
		locationDetailsPage.verifyLocationDetailsTitle();
		locationDetailsPage.verifyConnectedSchedulingResourcesLabel();
		locationDetailsPage.clickOnAddButton();
		locationDetailsPage.enterResourceName(getTestData[0]);
		CCUtils.pressEnterKey();
		locationDetailsPage.VerifyAddToSaveIsDisplayed();
		locationDetailsPage.clickOnAddToSave();
		CCUtils.assertEquals(locationDetailsPage.getDefaultItemsPerPage(), "10");
		logStep("Check Next Button functionality when Items Per Page value is = 10.");
		locationDetailsPage.clickNextPageButton();
		logStep("Check Previous Button functionality when Items Per Page value is = 10.");
		locationDetailsPage.clickPreviousPageButton();
		logStep("Check Next Last functionality when Items Per Page value is = 10.");
		locationDetailsPage.clickLastPageButton();
		logStep("Check Next first functionality when Items Per Page value is = 10.");
		locationDetailsPage.clickFirstPageButton();
	}
}