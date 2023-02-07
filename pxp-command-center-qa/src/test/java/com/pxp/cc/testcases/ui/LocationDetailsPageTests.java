//Copyright 2023 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.util.CCUtils;
import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;

public class LocationDetailsPageTests extends CommandCenterGenericTest {
	static boolean runOnce = true;
	public CCUI ccui;

	String resourceName;
	String firstName = "New"
			+ CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
	String lastName = CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6))
			.concat("'s");
	String locationName, clientName, providerOne, providerTwo, providerThree, providerFour, providerFive, providerSix,
			providerSeven, providerEight, providerNine, providerTen, providerEleven;

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
	public void createlocation() throws Exception {
		logStep("Click on Add button");
		ccui.getAddLocationPage().verifyLocationsPageTitle();
		ccui.getAddLocationPage().verifyAddLocationButton();
		ccui.getAddLocationPage().clickOnAddLocationButton();
		logStep("Enter data in mandatory fields");
		locationName = CCUtils.generateAlphaNumericString(18);
		ccui.getAddLocationPage().enterLocationName(locationName);
		ccui.getAddLocationPage().enterLocationDisplayName(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterAddressOne(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterCity(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterLocationIdExternal(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterZip(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().addStateAndTimezone(testData.getProperty("new.locations.state"),
				testData.getProperty("new.locations.timezone"));
		logStep("Click on save and add button");
		ccui.getAddLocationPage().clickOnSaveAndAddButton();
	}

	// ViewLocationMapLocationInfoTests

	@Test(priority = 1, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Location label And Location Info Section")
	public void verifyLocationLabelAndLocationInfoSection() throws Exception {
		logStep("PXPP-11512 Verify Command Center ID, External ID, Address, Latitude(based on address), Longitude(based on address), Timezone and Google Maps Link are read only fields");
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		createlocation();
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().verifyLocationInfoSection();
		ccui.getLocationDetailsPage().verifyCopiedText();
		ccui.getLocationDetailsPage().verifyLocationNameAddressAndTimezone();
		runOnce = false;
	}

	// LocationDetailsSolutionsDisplayInfoTests

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Verify location Display Info Section")
	public void testLocationDisplayInfoSection() throws Exception {
		logStep("PXPP-11510, PXPP-11511, PXPP-11578, PXPP-11577 Verify Location Display Name (EN), Location Display Name (ES), Address 2 (suite, floor, etc), Directions (EN) and Directions (ES) fields accept free text");
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().verifyLocationDisplayInfoSection();
		runOnce = false;
	}

	@Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in all input fields and verify save functionality")
	public void testSaveFunctionalityWithValidDetailsInAllInputFields() throws Exception {
		logStep("PXPP-11585 Verify changes are applied for all entities");
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().verifyEditButtonLabel();
		ccui.getLocationDetailsPage().clickOnEditButton();
		ccui.getLocationDetailsPage().verifyLocationDisplayInfoSectionLabel();
		ccui.getLocationDetailsPage().enterLocationDisplayNameEn(CCUtils.generateAlphaNumericString(8));
		ccui.getLocationDetailsPage().enterLocationDisplayNameEs(CCUtils.generateAlphaNumericString(8));
		ccui.getLocationDetailsPage().enterAddress2(CCUtils.generateAlphaNumericString(8));
		ccui.getLocationDetailsPage().enterPhoneNumber(String.valueOf(CCUtils.generateMobileNumber()));
		ccui.getLocationDetailsPage().enterDirectionsEn(CCUtils.generateAlphaNumericString(8));
		ccui.getLocationDetailsPage().enterDirectionsEs(CCUtils.generateAlphaNumericString(8));
		ccui.getLocationDetailsPage().enterStartOfficeHours("10:00");
		ccui.getLocationDetailsPage().enterEndOfficeHours("20:00");
		ccui.getLocationDetailsPage().clickOnSaveButton();
		runOnce = false;
	}

	@Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Verify error message for LocationDisplayNameEn, LocationDisplayNameEs, address2,  phoneNumber and officeHours fields and verify error message")
	public void testMaximumCharactersErrorMessageForInputFields() throws Exception {
		logStep("Verify error message for LocationDisplayNameEn, LocationDisplayNameEs, address2, directionsEn, directionsEs, phoneNumber and officeHours");
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		driver.navigate().refresh();
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().verifyEditButtonLabel();
		ccui.getLocationDetailsPage().clickOnEditButton();
		ccui.getLocationDetailsPage().verifyLocationDisplayInfoSectionLabel();
		logStep("PXPP-11576, PXPP-11575, PXPP-11572,PXPP-11571,PXPP-11568 Verify field limit for Location Display info");
		ccui.getLocationDetailsPage().verifyMaximumCharactersReachedErrorMessage(
				CCUtils.generateAlphaNumericString(101), CCUtils.generateAlphaNumericString(101),
				CCUtils.generateAlphaNumericString(102));
		ccui.getLocationDetailsPage().verifyErrorMessageForInvalidPhoneNumber(CCUtils.generateAlphaNumericString(10));
		ccui.getLocationDetailsPage().verifyErrorMessageForInvalidOfficeHours("20:00", "10:00");
		runOnce = false;
	}

	@Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "Verify cancel button functionality")
	public void testCancelFunctionality() throws Exception {
		logStep("Verify cancel button functionality");
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		logStep("Verify cancel button functionality");
		ccui.getLocationDetailsPage().verifyEditButtonLabel();
		ccui.getLocationDetailsPage().clickOnEditButton();
		ccui.getLocationDetailsPage().clickOnCancel();
		runOnce = false;
	}

	@Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in mandatory input fields and verify save functionality")
	public void testSaveFunctionalityWithValidDetailsInMandatoryInputFields() throws Exception {
		logStep("Enter valid details in mandatory input fields and verify save functionality");
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().verifyEditButtonLabel();
		ccui.getLocationDetailsPage().clickOnEditButton();
		ccui.getLocationDetailsPage().verifyLocationDisplayInfoSectionLabel();
		ccui.getLocationDetailsPage().enterPhoneNumber(String.valueOf(CCUtils.generateMobileNumber()));
		ccui.getLocationDetailsPage().enterStartOfficeHours("10:00");
		ccui.getLocationDetailsPage().enterEndOfficeHours("20:00");
		ccui.getLocationDetailsPage().clickOnSaveButton();
		runOnce = false;
	}

	@Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in Non mandatory input fields and verify save button is disabled")
	public void testSaveFunctionalityWithValidDetailsInAllInputNonMandatoryFields() throws Exception {
		logStep("Enter valid details in Non mandatory input fields and verify save button is disabled");
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().verifyEditButtonLabel();
		ccui.getLocationDetailsPage().clickOnEditButton();
		ccui.getLocationDetailsPage().verifyLocationDisplayInfoSectionLabel();
		ccui.getLocationDetailsPage().enterLocationDisplayNameEn(CCUtils.generateAlphaNumericString(8));
		ccui.getLocationDetailsPage().enterLocationDisplayNameEs(CCUtils.generateAlphaNumericString(8));
		ccui.getLocationDetailsPage().enterAddress2(CCUtils.generateAlphaNumericString(8));
		ccui.getLocationDetailsPage().enterDirectionsEn(CCUtils.generateAlphaNumericString(8));
		ccui.getLocationDetailsPage().enterDirectionsEs(CCUtils.generateAlphaNumericString(8));
		ccui.getLocationDetailsPage().verifySaveButtonIsDisabled();
		runOnce = false;
	}

	// EnableDisablePracticesOnLocationsDetailsPageTests

	@Test(priority = 8, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify enable and disable practice for created location")
	public void testToEnableAndDisableLocationForPractice() throws Exception {
		logStep("PXPP-11486 Test Cases for Location Details - Enable/Disable for Specific Practices");
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().clickToEntepriseArrow();
		ccui.getLocationDetailsPage().clickOnToggleButton();
		ccui.getLocationDetailsPage().clickToBackButton();
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().clickOnToggleButton();
		ccui.getLocationDetailsPage().clickToBackButton();
		runOnce = false;
	}

	// EnableDisableSolutionsOnLocationsDetailsPageTests

	@Test(priority = 9, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify enable and disable solution of associated practice of location")
	public void testToEnableAndDisableSolutionForLocationForPractice() throws Exception {
		logStep("PXPP-22192  Test Cases for Connected Solutions Section");
		ngHierarchyClientCreate();
		addPurchaseSolutionAtPracticeLevel();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().clickToEntepriseArrow();
		ccui.getLocationDetailsPage().clickOnToggleButton();
		ccui.getLocationDetailsPage().clickOnPracticeNode();
		ccui.getLocationDetailsPage().verifyConnectedSolutionsTitle();
		ccui.getLocationDetailsPage().clickOnChatToggle();
		ccui.getLocationDetailsPage().clickOffChatToggle();
		ccui.getLocationDetailsPage().clickOnPayToggle();
		ccui.getLocationDetailsPage().clickOffPayToggle();
		ccui.getLocationDetailsPage().clickOnVirtualVisitToggle();
		ccui.getLocationDetailsPage().clickOffVirtualVisitToggle();
		ccui.getLocationDetailsPage().clickOnPortalToggle();
		ccui.getLocationDetailsPage().clickOffPortalToggle();
		ccui.getLocationDetailsPage().clickOnPSSToggle();
		ccui.getLocationDetailsPage().clickOffPSSToggle();
		runOnce = false;
	}

	// AddOrRemoveConnectedProvidersToLocationTests

	@Test(priority = 10, retryAnalyzer = RetryAnalyzer.class, description = "Verify Connected Providers popup, add & remove Connected Providers and search Connected Providers Functionality")
	public void testAddRemoveSearchConnectedProvidersFunctionality() throws Exception {
		logStep("PXPP-11645, PXPP-11653, PXPP-11656 Verify all the elements of container 'Connected Providers' in Location Details page ");
		ngHierarchyClientCreate();
		String providerFirstName= createProvider();
		ccui.getGeneralSettingsPage().clickOnGeneralSettingsTab();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		driver.navigate().refresh();

		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		createlocation();
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().clickToEntepriseArrow();
		ccui.getLocationDetailsPage().clickOnToggleButton();
		ccui.getLocationDetailsPage().clickOnPracticeNode();
		ccui.getLocationDetailsPage().verifyConnectedProvidersSection();
		logStep("Verify Add Providers Popup on Location details page");
		ccui.getLocationDetailsPage().verifyConnectedProvidersLabel();
		ccui.getLocationDetailsPage().clickOnAdd();
		logStep("Verify Add Providers Popup on Location details page");
		ccui.getLocationDetailsPage().verifyAddProviderPopupTitle();
		ccui.getLocationDetailsPage().enterProviderName(providerFirstName);
		ccui.getLocationDetailsPage().clickFirstInAutoSuggestion();
		ccui.getLocationDetailsPage().clickOnAddToSave();
		logStep("Verify Successfully added provider");
		logStep("PXPP-11645 Verify we can search provider in search text box within Connected Provider container");
		ccui.getLocationDetailsPage().verifyProviderNameColumnLabel();
		ccui.getLocationDetailsPage().enterSearchText(firstName);
		CCUtils.pressEnterKey();
		ccui.getLocationDetailsPage().clickOnRemoveConnection();
		logStep("Verify Successfully removed provider");
		logStep("Verify Connected Providers functionality with non existing provider");
		ccui.getLocationDetailsPage().clickOnAdd();
		logStep("Verify functionality of cancel button and cross icon on Add Providers popup");
		ccui.getLocationDetailsPage().clickOnCancelButton();
		logStep("Verify after clicking on cancel button user redirected to Location Details page");
		ccui.getLocationDetailsPage().verifyConnectedProvidersLabel();
		logStep("Click on Add button");
		ccui.getLocationDetailsPage().clickOnAdd();
		logStep("Click on cross button");
		ccui.getLocationDetailsPage().clickOnCrossIcon();
		logStep("Verify after clicking on cross button user redirected to Locations Details page");
		ccui.getLocationDetailsPage().verifyConnectedProvidersLabel();
		ccui.getLocationDetailsPage().enterSearchText(CCUtils.generateAlphaNumericString(5));
		CCUtils.pressEnterKey();
		runOnce = false;
	}

	// ConnectedProvidersToLocationSearchAndPaginationTests

	@Test(priority = 11, retryAnalyzer = RetryAnalyzer.class, description = "Verify numbers of rows and Pagination should be change it we changes 'Items Per Page' value.")
	public void testAllPaginationButtonsClickable() throws Exception {
		logStep("PXPP-11728,PXPP-11732, /PXPP-11733 Verify pagination");
		ngHierarchyClientCreate();
		providerOne = createProvider();
		providerTwo = createProvider();
		providerThree = createProvider();
		providerFour = createProvider();
		providerFive = createProvider();
		providerSix = createProvider();
		providerSeven = createProvider();
		ccui.getGeneralSettingsPage().clickOnGeneralSettingsTab();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		driver.navigate().refresh();
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		createlocation();
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().clickToEntepriseArrow();
		ccui.getLocationDetailsPage().clickOnToggleButton();
		ccui.getLocationDetailsPage().clickOnPracticeNode();
		ccui.getLocationDetailsPage().verifyConnectedProvidersSection();
		logStep("Verify Add Providers Popup on Location details page");
		ccui.getLocationDetailsPage().verifyConnectedProvidersLabel();
		ccui.getLocationDetailsPage().clickOnAdd();
		ccui.getLocationDetailsPage().enterProviderName(providerOne);
		ccui.getLocationDetailsPage().clickOnAddToSave();
		ccui.getLocationDetailsPage().verifyConnectedProvidersSection();
		ccui.getLocationDetailsPage().clickOnAdd();
		ccui.getLocationDetailsPage().enterProviderName(providerTwo);
		ccui.getLocationDetailsPage().clickOnAddToSave();
		ccui.getLocationDetailsPage().verifyConnectedProvidersSection();
		ccui.getLocationDetailsPage().clickOnAdd();
		ccui.getLocationDetailsPage().enterProviderName(providerThree);
		ccui.getLocationDetailsPage().clickOnAddToSave();
		ccui.getLocationDetailsPage().verifyConnectedProvidersSection();
		ccui.getLocationDetailsPage().clickOnAdd();
		ccui.getLocationDetailsPage().enterProviderName(providerFour);
		ccui.getLocationDetailsPage().clickOnAddToSave();
		ccui.getLocationDetailsPage().verifyConnectedProvidersSection();
		ccui.getLocationDetailsPage().clickOnAdd();
		ccui.getLocationDetailsPage().enterProviderName(providerFive);
		ccui.getLocationDetailsPage().clickOnAddToSave();
		ccui.getLocationDetailsPage().verifyConnectedProvidersSection();
		ccui.getLocationDetailsPage().clickOnAdd();
		ccui.getLocationDetailsPage().enterProviderName(providerSix);
		ccui.getLocationDetailsPage().clickOnAddToSave();
		ccui.getLocationDetailsPage().verifyConnectedProvidersSection();
		ccui.getLocationDetailsPage().clickOnAdd();
		ccui.getLocationDetailsPage().enterProviderName(providerSeven);
		ccui.getLocationDetailsPage().clickOnAddToSave();
		CCUtils.assertEquals(ccui.getLocationDetailsPage().getDefaultItemsPerPage(), "10");
		ccui.getLocationDetailsPage().clickItemsPerPageDropdown();
		ccui.getLocationDetailsPage().clickFiveItemsPerPage();
		logStep("Check Next Button functionality when Items Per Page value is = 5.");
		ccui.getLocationDetailsPage().clickProvidersNextPageButton();
		logStep("Check Previous Button functionality when Items Per Page value is = 5.");
		ccui.getLocationDetailsPage().clickProvidersPreviousPageButton();
		logStep("Check Next Last functionality when Items Per Page value is = 5.");
		ccui.getLocationDetailsPage().clickProvidersLastPageButton();
		logStep("Check Next first functionality when Items Per Page value is = 5.");
		ccui.getLocationDetailsPage().clickProvidersFirstPageButton();
	}

	@Test(priority = 12, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Display of default pagination i.e., 10 items per page.")
	public void testDefaultItemsPerPageAndDefaultPaginationInConnectedProvidersWebtable() throws Exception {
		logStep("PXPP-11728 Verify pagination and Items per page dropdown should be present in 'Connected Provider' container");
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		createlocation();
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().verifyConnectedProvidersLabel();
		CCUtils.assertEquals(ccui.getLocationDetailsPage().getDefaultItemsPerPage(), "10");
		CCUtils.assertEquals(ccui.getLocationDetailsPage().getDefaultPagination(), "0");
		ccui.getLocationDetailsPage().isFirstPageButtonDisabled();
		ccui.getLocationDetailsPage().isPreviousPageButtonDisabled();
		ccui.getLocationDetailsPage().isNextPageButtonDisabled();
		ccui.getLocationDetailsPage().isLastPageButtonDisabled();
	}

	// AddOrRemoveConnectedResourcesToLocationTests

	@Test(priority = 13, retryAnalyzer = RetryAnalyzer.class, description = "Verify Connected Resources popup, add & remove Connected Resources and search Connected Resources Functionality")
	public void testAddRemoveSearchConnectedResourcesFunctionality() throws Exception {
		logStep("Verify add Connected Scheduling Resources Functionality");
		ngHierarchyClientCreate();
		createProvider();
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		testAddNewResourcesFunctionalityUsingValidData();
		ccui.getGeneralSettingsPage().clickOnGeneralSettingsTab();
		driver.navigate().refresh();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		createlocation();
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().clickToEntepriseArrow();
		ccui.getLocationDetailsPage().clickOnToggleButton();
		ccui.getLocationDetailsPage().clickOnPracticeNode();
		ccui.getLocationDetailsPage().verifyConnectedSchedulingResourcesLabel();
		ccui.getLocationDetailsPage().clickOnAddButton();
		ccui.getLocationDetailsPage().verifyAddResourcePopupTitle();
		ccui.getLocationDetailsPage().enterResourceName(resourceName);
		ccui.getLocationDetailsPage().clickFirstInAutoSuggestion();
		ccui.getLocationDetailsPage().clickOnAddToSave();
		logStep("Verify success toaster message");
		ccui.getCommonMethods().verifyToasterMessage("Successfully added resource");
		driver.navigate().refresh();
		ccui.getLocationDetailsPage().verifyResourceNameColumnLabel();
		ccui.getLocationDetailsPage().verifyNewlyAddedConnectedSchedulingResource();
		ccui.getLocationDetailsPage().clickOnRemoveConnectionButton();
		logStep("Verify success toaster message");
		ccui.getCommonMethods().verifyToasterMessage("Successfully removed resource");
		logStep("Verify Connected Scheduling Resources functionality with invalid resource");
		ccui.getLocationDetailsPage().clickOnAddButton();
		ccui.getLocationDetailsPage().enterResourceName(CCUtils.generateAlphaNumericString(8));
		ccui.getLocationDetailsPage().verifyAddButtonIsDisabled();
		logStep("Verify functionality of cancel button and cross icon on Add Resources popup");
		logStep("Click on cancel button");
		ccui.getLocationDetailsPage().clickOnCancelButton();
		logStep("Verify after clicking on cross button user redirected to Locations Details page");
		ccui.getLocationDetailsPage().verifyConnectedSchedulingResourcesLabel();
		logStep("Click on Add button");
		ccui.getLocationDetailsPage().clickOnAddButton();
		logStep("Click on cross button");
		ccui.getLocationDetailsPage().clickOnCrossIcon();
		logStep("Verify after clicking on cross button user redirected to Locations Details page");
		ccui.getLocationDetailsPage().verifyConnectedSchedulingResourcesLabel();
		ccui.getLocationDetailsPage().searchResource(resourceName);
		CCUtils.pressEnterKey();
		logStep("Searched result displayed");
		ccui.getLocationDetailsPage().searchResource(CCUtils.generateAlphaNumericString(5));
		CCUtils.pressEnterKey();
		logStep("Searched result is not displayed");
		ccui.getLocationDetailsPage().testToCheckNoResultsFound();
	}

	// ConnectedResourcesToLocationSearchAndPaginationTests

	@Test(priority = 14, retryAnalyzer = RetryAnalyzer.class, description = "Verify numbers of rows and Pagination should be change it we changes 'Items Per Page' value.")
	public void testAllPaginationButtonsClickableForConnectedResources() throws Exception {
		logStep("Verify numbers of rows and Pagination should be change it we changes 'Items Per Page' value.");
		ngHierarchyClientCreate();
		createProvider();
		ccui.getAddNewResourcePage().clickOnResourcesTab();
		String resourceNameOne = testAddNewResourcesFunctionalityUsingValidData();
		String resourceNameTwo = testAddNewResourcesFunctionalityUsingValidData();
		String resourceNameThree = testAddNewResourcesFunctionalityUsingValidData();
		String resourceNameFour = testAddNewResourcesFunctionalityUsingValidData();
		String resourceNameFive = testAddNewResourcesFunctionalityUsingValidData();
		String resourceNameSix = testAddNewResourcesFunctionalityUsingValidData();
		ccui.getGeneralSettingsPage().clickOnGeneralSettingsTab();
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		driver.navigate().refresh();
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		createlocation();
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().clickToEntepriseArrow();
		ccui.getLocationDetailsPage().clickOnToggleButton();
		ccui.getLocationDetailsPage().clickOnPracticeNode();
		ccui.getLocationDetailsPage().verifyConnectedSchedulingResourcesLabel();
		ccui.getLocationDetailsPage().clickOnAddButton();
		ccui.getLocationDetailsPage().enterResourceName(resourceNameOne);
		ccui.getLocationDetailsPage().clickFirstInAutoSuggestion();
		ccui.getLocationDetailsPage().clickOnAddToSave();
		ccui.getLocationDetailsPage().verifyConnectedSchedulingResourcesLabel();
		ccui.getLocationDetailsPage().clickOnAddButton();
		ccui.getLocationDetailsPage().enterResourceName(resourceNameTwo);
		ccui.getLocationDetailsPage().clickFirstInAutoSuggestion();
		ccui.getLocationDetailsPage().clickOnAddToSave();
		ccui.getLocationDetailsPage().verifyConnectedSchedulingResourcesLabel();
		ccui.getLocationDetailsPage().clickOnAddButton();
		ccui.getLocationDetailsPage().enterResourceName(resourceNameThree);
		ccui.getLocationDetailsPage().clickFirstInAutoSuggestion();
		ccui.getLocationDetailsPage().clickOnAddToSave();
		ccui.getLocationDetailsPage().verifyConnectedSchedulingResourcesLabel();
		ccui.getLocationDetailsPage().clickOnAddButton();
		ccui.getLocationDetailsPage().enterResourceName(resourceNameFour);
		ccui.getLocationDetailsPage().clickFirstInAutoSuggestion();
		ccui.getLocationDetailsPage().clickOnAddToSave();
		ccui.getLocationDetailsPage().verifyConnectedSchedulingResourcesLabel();
		ccui.getLocationDetailsPage().clickOnAddButton();
		ccui.getLocationDetailsPage().enterResourceName(resourceNameFive);
		ccui.getLocationDetailsPage().clickFirstInAutoSuggestion();
		ccui.getLocationDetailsPage().clickOnAddToSave();
		ccui.getLocationDetailsPage().verifyConnectedSchedulingResourcesLabel();
		ccui.getLocationDetailsPage().clickOnAddButton();
		ccui.getLocationDetailsPage().enterResourceName(resourceNameSix);
		ccui.getLocationDetailsPage().clickFirstInAutoSuggestion();
		ccui.getLocationDetailsPage().clickOnAddToSave();
		CCUtils.assertEquals(ccui.getLocationDetailsPage().getDefaultItemsPerPage(), "10");
		ccui.getLocationDetailsPage().clickItemsPerPageDropdownResource();
		ccui.getLocationDetailsPage().clickFiveItemsPerPage();
		logStep("Check Next Button functionality when Items Per Page value is = 5.");
		ccui.getLocationDetailsPage().clickNextPageButton();
		logStep("Check Previous Button functionality when Items Per Page value is = 5.");
		ccui.getLocationDetailsPage().clickPreviousPageButton();
		logStep("Check Next Last functionality when Items Per Page value is = 5.");
		ccui.getLocationDetailsPage().clickLastPageButton();
		logStep("Check Next first functionality when Items Per Page value is = 5.");
		ccui.getLocationDetailsPage().clickFirstPageButton();
	}

	public void addPurchaseSolutionAtPracticeLevel() throws Exception {
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().verifyAvailableToPurchaseSection();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfVirtualVisitsSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPaySolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfEngageSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfChatSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPortalSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPSSSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		runOnce = false;
	}

	@Test(enabled = false)
	public String testAddNewResourcesFunctionalityUsingValidData() throws Exception {
		logStep("Create new Resources using valid test data");

		ccui.getAddNewResourcePage().verifyAddButtonIsEnabled();
		ccui.getAddNewResourcePage().clickOnAddResourceButton();
		resourceName = "New" + CCUtils.generateAlphaNumericString(5).concat(CCUtils.generateAlphaNumericString(6));
		String externalIds = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		ccui.getAddNewResourcePage().enterResourceName(resourceName);
		ccui.getAddNewResourcePage().enterExternalId(externalIds);
		ccui.getAddNewResourcePage().selectResourceType("Person");
		ccui.getAddNewResourcePage().enterConnectedProvider(firstName);
		ccui.getAddNewResourcePage().clickFirstInAutoSuggestion();
		ccui.getAddNewResourcePage().verifySaveAndAddButtonIsEnabled();
		ccui.getAddNewResourcePage().clickOnSaveAndAddButton();
		return resourceName;
	}

	@Test(enabled = false)
	public String createProvider() throws Exception {
		logStep("Verify Providers And Resources tab is displayed");
		ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
		ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
		ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
		logStep("Verify ADD button is enabled for client level on provider page");
		ccui.getAddNewProviderPage().verifyAddButtonIsEnabled();
		ccui.getAddNewProviderPage().clickOnAddProviderButton();
		logStep("Enter valid details in mandatory field and verify success toaster message");
		String credentials = testData.getProperty("provider.credentials");
		ccui.getAddNewProviderPage().enterFirstName(firstName);
		ccui.getAddNewProviderPage().enterLastName(lastName);
		ccui.getAddNewProviderPage().enterCredentials(credentials);
		String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		ccui.getAddNewProviderPage().enterExternalId(externalId);
		ccui.getAddNewProviderPage().selectGenderValue("Male");
		ccui.getAddNewProviderPage().verifySaveAndAddButtonIsEnabled();
		ccui.getAddNewProviderPage().clickOnSaveAndAddButton();
		ccui.getCommonMethods().verifyToasterMessage("Success! New Provider has been successfully created");
		return firstName;
	}
}