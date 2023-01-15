// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.util.CCUtils;

public class GeneralSettingsPageTests extends CommandCenterGenericTest {
	static boolean runOnce = true;
	public CCUI ccui;
	public static String systemName, clientName, enterpriseName, practiceName;
	public ClientDirectoryPage clientDirectorPage;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		if (runOnce) {
			ccui = new CCUI(testData, driver);
			logStep("Login to command center portal");
			clientDirectorPage = ccui.getClientDirectoryPage();
			ccui.initializeTestData();
			getClientSystementErpriseandPractice();
		}
	}

	@Test(enabled = false)
	public void addLocationPopUp() throws Exception {
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify Locations tab is displayed");
		ccui.getGeneralSettingsPage().verifyLocationsTab();
		ccui.getGeneralSettingsPage().clickOnLocationsTab();
		logStep("Click on Add button");
		ccui.getAddLocationPage().verifyLocationsPageTitle();
	}

	@Test(enabled = false)
	public String testAddNewLocationFunctionalityWithAllValidDetails() throws Exception {
		logStep("Enter data in all input fields");
		ccui.getAddLocationPage().verifyAddLocationButton();
		ccui.getAddLocationPage().clickOnAddLocationButton();
		String locationName = "Loc".concat(CCUtils.generateAlphaNumericString(10));
		ccui.getAddLocationPage().enterLocationName(locationName);
		ccui.getAddLocationPage().enterLocationDisplayName(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterAddressOne(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterAddressTwo(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterCity(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterLocationIdExternal(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().enterZip(CCUtils.generateAlphaNumericString(18));
		ccui.getAddLocationPage().addStateAndTimezone(testData.getProperty("new.locations.state"),
				testData.getProperty("new.locations.timezone"));
		logStep("Click on save and add button");
		ccui.getAddLocationPage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.getAddLocationPage().verifyToasterMessage("Success! New location has been successfully created");
		ccui.getSearchLocationDetailsPage().searchLocationAndVerifyResult(locationName);
		CCUtils.pressEnterKey();
		ccui.getAddLocationPage().selectLocation();
		ccui.getLocationDetailsPage().verifyLocationDetailsTitle();
		ccui.getLocationDetailsPage().clickToEntepriseArrow();
		ccui.getLocationDetailsPage().clickOnToggleButton();
		ccui.getLocationDetailsPage().clickToBackButton();
		ccui.getSearchLocationDetailsPage().verifyLocationTitle();
		return locationName;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify add new system,enterprise,practice page web elements and max character reached error")
	public void testAddNewSystemEnterprisePracticePageWebElementsAndMaxCharacterReachedErrorMessage() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().clickClientDirectory();
		clientName = ccui.getClient();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		logStep("click to add new system button");
		ccui.getAddOrgNodePage().verifyAddSystemButton();
		ccui.getAddOrgNodePage().clickOnAddSystem();
		logStep("Verify add new system page");
		ccui.getAddOrgNodePage().verifyAddNewSystemPage();
		logStep("Verify maximum character reached error message");
		ccui.getAddOrgNodePage().verifyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101));
		ccui.getAddOrgNodePage().clickOnOrgCloseButton();
		systemName = ccui.getSystem();
		ccui.getAddOrgNodePage().clickOnExpandArrow(systemName);
		logStep("Verify add new Enterprise page");
		ccui.getAddOrgNodePage().verifyAddEnterpriseButton();
		ccui.getAddOrgNodePage().clickOnAddEnterprise();
		ccui.getAddOrgNodePage().verifyAddNewEnterprisePage();
		ccui.getAddOrgNodePage().verifyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101));
		ccui.getAddOrgNodePage().clickOnOrgCloseButton();
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().verifyOrgName();
		enterpriseName = ccui.getEnterprise();
		ccui.getAddOrgNodePage().clickOnExpandArrow(systemName);
		ccui.getAddOrgNodePage().clickOnExpandArrow(enterpriseName);
		logStep("Verify add new practice page");
		ccui.getAddOrgNodePage().verifyAddPracticeButton();
		ccui.getAddOrgNodePage().clickOnAddPracticeButton();
		ccui.getAddOrgNodePage().verifyAddNewPracticePage();
		ccui.getAddOrgNodePage().verifyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101));
		ccui.getAddOrgNodePage().clickOnOrgCloseButton();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "create inactive and active client and all inactive orgs")
	public void testCreateInactiveAndActiveClientAndItsOrgs() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().clickClientDirectory();
		logStep("ceate inactive client");
		ccui.getInactiveClient();
		logStep("create inactive system");
		ccui.getInactiveSystem();
		logStep("create inactive enterprise");
		ccui.getInactiveEnterprise();
		logStep("create inactive practice");
		ccui.getInactivePractice();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		logStep("create active client and inactive orgs");
		logStep("ceate active client");
		ccui.getClient();
		logStep("create inactive system");
		ccui.getInactiveSystem();
		logStep("create inactive enterprise");
		ccui.getInactiveEnterprise();
		logStep("create inactive practice");
		ccui.getInactivePractice();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		logStep("create active client and system and inactive enterprise and practice");
		logStep("ceate active client");
		ccui.getClient();
		logStep("create active system");
		ccui.getSystem();
		logStep("create inactive enterprise");
		ccui.getInactiveEnterprise();
		logStep("create inactive practice");
		ccui.getInactivePractice();
		ccui.getGeneralSettingsPage().clickClientDirectoryIcon();
		logStep("create active client,system and enterprise and inactive practice");
		logStep("ceate active client");
		ccui.getClient();
		logStep("create active system");
		ccui.getSystem();
		logStep("create active enterprise");
		ccui.getEnterprise();
		logStep("create inactive practice");
		ccui.getInactivePractice();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel button at add org page and when user click on 'CLOSE' button User redirected to general setting page")
	public void testSearchCancelAndCloseButtonFunctionalityOfManageClientPage() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().verifySearchIconAndSearchTextBox();
		ccui.getAddOrgNodePage().enterSearchValue(clientSystementErpriseandPractice.get("systemName"));
		ccui.getAddOrgNodePage().clickFirstInAutoSuggestion();
		ccui.getAddOrgNodePage().verifySearchOrgResult(clientSystementErpriseandPractice.get("systemName"));
		ccui.getAddOrgNodePage().verifyAddSystemButton();
		ccui.getAddOrgNodePage().clickOnAddSystem();
		logStep("Click on cancel button");
		ccui.getAddOrgNodePage().clickOnCancelButton();
		logStep("Verify after clicking on cancel button user redirected to General Settings page");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Click on close button");
		ccui.getAddOrgNodePage().clickOnCloseButton();
		logStep("Verify after clicking on close button user redirected to General Settings page");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel button and verify error mesaages- 'Maximum characters reached' and 'Client name required' ")
	public void testEditClientCancelButtonAndClientRequiredAndMaxCharacterReachedError() throws Exception {
		ngHierarchyClientCreate();
		ccui.getClientDirectoryPage().selectSearchedClientNameInClientTable();
		logStep("user redirected to General Settings page");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickEditButtonOfClient();
		String newClientName = CCUtils.generateAlphaNumericString(101);
		ccui.getGeneralSettingsPage().enterClientName(newClientName);
		String actualNameError = ccui.getGeneralSettingsPage().getErrorMessage();
		logStep("Verify Maximum charater reached error message");
		CCUtils.assertEquals(actualNameError, "Maximum characters reached");
		ccui.getGeneralSettingsPage().clearClientNameField();
		logStep("Verify success toaster message");
		ccui.getGeneralSettingsPage().verifyToasterMessage("Changes updated successfully");
		String errorMessage = ccui.getGeneralSettingsPage().getClientNameErrorMessage();
		logStep("Verify Client name required error message");
		CCUtils.assertEquals(errorMessage, "Client name is required");
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of edit client,system,enterprise and practice name and client status")
	public void testEditClientSystemEnterprisePracticeNameAndStatus() throws Exception {
		ngHierarchyClientCreate();
		logStep("Edit and save client name and status");
		ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickEditButtonOfClient();
		String newClientName = CCUtils.generateAlphaNumericString(10);
		ccui.getGeneralSettingsPage().enterClientName(newClientName);
		ccui.getGeneralSettingsPage().changeStatusToInactive();
		logStep("Verify success toaster message");
		ccui.getGeneralSettingsPage().verifyToasterMessage("Changes updated successfully");
		ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
		logStep("Edit and save System name and status");
		ccui.editBasicInfoGeneralSettingsPage().clickSystemOrg();
		ccui.editOrgDetailsGeneralSettingsPage().clickEditButton();
		String orgName = CCUtils.generateAlphaNumericString(8);
		ccui.editOrgDetailsGeneralSettingsPage().EditAndSaveNameAndStatus(orgName);
		logStep("Verify success toaster message");
		ccui.editOrgDetailsGeneralSettingsPage().verifyToasterMessage("Changes updated successfully");
		ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
		ccui.getPrivacyPolicySettingsPage()
				.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
		logStep("Edit and save Enterprise name and status");
		ccui.editBasicInfoGeneralSettingsPage().clickEnterpriseOrg();
		ccui.editOrgDetailsGeneralSettingsPage().clickEditButton();
		String entName = CCUtils.generateAlphaNumericString(8);
		ccui.editOrgDetailsGeneralSettingsPage().EditAndSaveNameAndStatus(entName);
		logStep("Verify success toaster message");
		ccui.editOrgDetailsGeneralSettingsPage().verifyToasterMessage("Changes updated successfully");
		ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
		ccui.getPrivacyPolicySettingsPage()
				.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getPrivacyPolicySettingsPage().clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
		logStep("Edit and save Practice name and status");
		ccui.editOrgDetailsGeneralSettingsPage().clickEditButton();
		String pracName = CCUtils.generateAlphaNumericString(8);
		ccui.editOrgDetailsGeneralSettingsPage().EditAndSaveNameAndStatus(pracName);
		logStep("Verify success toaster message");
		ccui.editOrgDetailsGeneralSettingsPage().verifyToasterMessage("Changes updated successfully");
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit and save Practice name and status")
	public void tabCountforLocationsTabAtClientSystemEnterprisePracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		logStep("create multiple locations at client level");
		addLocationPopUp();
		testAddNewLocationFunctionalityWithAllValidDetails();
		testAddNewLocationFunctionalityWithAllValidDetails();
		testAddNewLocationFunctionalityWithAllValidDetails();
		testAddNewLocationFunctionalityWithAllValidDetails();
		ccui.getSearchLocationDetailsPage().clickToGeneralSettingsIcon();
		String clientLocationTabCount = ccui.getGeneralSettingsPage().getLocationCount();
		ccui.getGeneralSettingsPage().clickOnLocationsTile();
		String noOfLocations = ccui.getSearchLocationDetailsPage().getTotalNoOfLocations();
		logStep("verifying the no of location on location tab in general settings and location details page at client level");
		CCUtils.assertEquals(clientLocationTabCount, noOfLocations.trim());
		ccui.getSearchLocationDetailsPage().clickToGeneralSettingsIcon();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a system node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("systemName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		String systemLocationTabCount = ccui.getGeneralSettingsPage().getLocationCount();
		ccui.getGeneralSettingsPage().clickOnLocationsTile();
		String noOfLocationsSystemFilter = ccui.getSearchLocationDetailsPage().getTotalNoOfLocations();
		logStep("verifying the no of location on location tab in general settings and location details page at system level");
		CCUtils.assertEquals(systemLocationTabCount, noOfLocationsSystemFilter.trim());
		ccui.getSearchLocationDetailsPage().clickToGeneralSettingsIcon();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching an enterprise node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		String enterpriseLocationTabCount = ccui.getGeneralSettingsPage().getLocationCount();
		ccui.getGeneralSettingsPage().clickOnLocationsTile();
		String noOfLocationsEnterpriseFilter = ccui.getSearchLocationDetailsPage().getTotalNoOfLocations();
		logStep("verifying the no of location on location tab in general settings and location details page at enterprise level");
		CCUtils.assertEquals(enterpriseLocationTabCount, noOfLocationsEnterpriseFilter.trim());
		ccui.getSearchLocationDetailsPage().clickToGeneralSettingsIcon();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		String practiceLocationTabCount = ccui.getGeneralSettingsPage().getLocationCount();
		ccui.getGeneralSettingsPage().clickOnLocationsTile();
		String noOfLocationsPracticeFilter = ccui.getSearchLocationDetailsPage().getTotalNoOfLocations();
		logStep("verifying the no of location on location tab in general settings and location details page at practice level");
		CCUtils.assertEquals(practiceLocationTabCount, noOfLocationsPracticeFilter.trim());
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify default text, search icon and search node functionality and test to select and verify Auto Suggestion list for symbol and number search in node name")
	public void testGeneralSettingScreenAndAutoSuggestionForSingleCharacterSearchAndNumberSearch() throws Exception {
		ngHierarchyClientCreate();
		logStep("PXPP-12909 PXPP-12910 PXPP-12911 PXPP-12921 Verify default text, autosuggestions ,search icon and search node functionality ");
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Verifying default text");
		String actualDefaultText = ccui.getGeneralSettingsPage().defaultTextInSearch();
		CCUtils.assertEquals(actualDefaultText, "Search");
		ccui.getGeneralSettingsPage().verifyStatusIcon();
		logStep("Verifying Auto-suggestion list is displayed");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("systemName"));
		ccui.getGeneralSettingsPage().verifyAutoSuggestionList(clientSystementErpriseandPractice.get("systemName"));
		logStep("PXPP-12967 Verify Auto suggestion list is displayed when searched text is entered - Command Center Admin");
		createNewSystemForNumberSymbolSearch();
		logStep("Searching for nodes with single character name in General settings search");
		ccui.getGeneralSettingsPage().clickSearchIcon();
		ccui.getGeneralSettingsPage().enterSearchText(systemName);
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifySearchedResult();
		createNewSystemForNumberSymbolSearch();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		ccui.getGeneralSettingsPage().enterSearchText(systemName);
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifySearchedResult();
		runOnce = false;
	}

	@Test(enabled = false, groups = {
			"smoke" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to search system,enterprise,practice node and non-existing node functionality in Client's General settings page")
	public void testSystemEnterprisePracticeNodeSearchAndNonExistingNodeSearch() throws Exception {
		ngHierarchyClientCreate();
		logStep("PXPP-12979 Verify full & partial search can be performed on general setting screen - Command Center Admin");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Verified general settings page title");
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a system node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("systemName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifySearchedResult();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a enterprise node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifySearchedResult();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifySearchedResult();
		logStep("Searching invalid node under client in general settings page, to check client details are highlighted");
		ccui.getGeneralSettingsPage().clickSearchIcon();
		ccui.getGeneralSettingsPage().enterSearchText(ccuser.searchInvalidNodeInGeneralSetting);
		CCUtils.pressEnterKey();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify locations,Providers & Resources, Appointment types tab & tiles navigate respective pages at Client,System,Enterprise,Practice level")
	public void verifyVisibilityOfTabsAndTilesNavigateRespectivePagesAtClientSystemEnterprisePracticeLevel() throws Exception {
		logStep("Verify locations, Providers & Resources, Appointment types tab & tiles navigate respective pages at Client,System,Enterprise,Practice level");
		ngHierarchyClientCreate();
		ccui.editBasicInfoGeneralSettingsPage().clickClientOrg();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTiles();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTile();
		ccui.getGeneralSettingsPage().clickOnGeneralSettingsTab();
		ccui.editBasicInfoGeneralSettingsPage().clickSystemOrg();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTiles();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTile();
		ccui.getGeneralSettingsPage().clickOnGeneralSettingsTab();
		logStep("Verify locations, Providers & Resources, Appointment types tab & tiles is presents At Enterprise Level");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify locations, Providers & Resources, Appointment types tab & tiles navigate respective pages at Enterprise Level");
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTiles();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTile();
		ccui.getGeneralSettingsPage().clickOnGeneralSettingsTab();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify locations, Providers & Resources, Appointment types tab & tiles navigate respective pages at practice Level");
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTiles();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTile();
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify activate all the solutions within practice from available to purchase section")
	public void testAddPurchaseSolutionAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().verifyAvailableToPurchaseSection();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPaySolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToActivateButtonOfEngageSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPortalSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPSSSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToActivateButtonOfVirtualVisitsSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToPayMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToEngageMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToPatientChatMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToPSSMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToVirtualVisitsAddOnsMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify activate all the solutions within practice from available to purchase section add ons")
	public void testandVerifyAddPurchaseSolutionAddOnsAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsAddOnsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutionsAddOns();
		ccui.getGeneralSettingsPage().verifyAvailableToPurchaseSection();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfChatSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToPatientChatAddOnsMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify activate pop up web elements")
	public void testActivatePopupVirtualVisitsPurchaseSolutionAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfVirtualVisitsSolution();
		String pracName = clientSystementErpriseandPractice.get("practiceName");
		log("name of practice : " + pracName);
		ccui.getGeneralSettingsPage().verifyActivatePopupTitle();
		ccui.getGeneralSettingsPage().verifyActivateTerminatePopupCancelButtonAndCrossIcon();
		ccui.getGeneralSettingsPage().cautionActivateMessage(pracName);
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify terminate pop up web elements")
	public void testTerminatePopupVirtualVisitsPurchaseSolutionAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfVirtualVisitsSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		String pracName = clientSystementErpriseandPractice.get("practiceName");
		log("name of practice : " + pracName);
		ccui.getGeneralSettingsPage().clickToVirtualVisitsMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().verifyTerminatePopupTitle();
		ccui.getGeneralSettingsPage().verifyActivateTerminatePopupCancelButtonAndCrossIcon();
		ccui.getGeneralSettingsPage().cautionTerminateMessage(pracName);
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		runOnce = false;
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
		}
	}

	private void createNewSystemForNumberSymbolSearch() throws NullPointerException, InterruptedException {
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().verifyAddSystemButton();
		ccui.getAddOrgNodePage().clickOnAddSystem();
		logStep("Enter data in all input fields");
		systemName = "System" + CCUtils.generateAlphaNumericString(18).concat("12").concat("@");
		ccui.getAddOrgNodePage().enterSystemName(systemName);
		ccui.getAddOrgNodePage().addPmSystemAndEhrSystem(testData.getProperty("new.system"),
				testData.getProperty("new.system"));
		ccui.getAddOrgNodePage().verifyInactiveRadioButton();
		logStep("Click on inactive radio button");
		ccui.getAddOrgNodePage().selectInactiveRadiobutton();
		logStep("Click on save and add button");
		ccui.getAddOrgNodePage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.getAddOrgNodePage().verifyToasterMessage("Changes updated successfully");
	}
}
