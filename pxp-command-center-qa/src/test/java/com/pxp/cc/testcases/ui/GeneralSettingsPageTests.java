// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify add new system,enterprise,practice page web elements and max character reached error")
	public void testAddNewSystemEnterprisePracticePageWebElementsAndMaxCharacterReachedErrorMessage() throws Exception {
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
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "create inactive client and all inactive orgs")
	public void testCreateInactiveClientAndItsOrgs() throws Exception {
		ccui.getGeneralSettingsPage().clickClientDirectory();
		logStep("ceate inactive client");
		ccui.getInactiveClient();
		logStep("create inactive system");
		ccui.getInactiveSystem();
		logStep("create inactive enterprise");
		ccui.getInactiveEnterprise();
		logStep("create inactive practice");
		ccui.getInactivePractice();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "create active client and all inactive orgs")
	public void testCreateActiveClientAndInactiveOrgs() throws Exception {
		ccui.getGeneralSettingsPage().clickClientDirectory();
		logStep("ceate active client");
		ccui.getClient();
		logStep("create inactive system");
		ccui.getInactiveSystem();
		logStep("create inactive enterprise");
		ccui.getInactiveEnterprise();
		logStep("create inactive practice");
		ccui.getInactivePractice();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "create active client and active system and inactive enterprise and practice")
	public void testCreateActiveClientAndSystemAndInactiveEnterpriseAndPractice() throws Exception {
		ccui.getGeneralSettingsPage().clickClientDirectory();
		logStep("ceate active client");
		ccui.getClient();
		logStep("create active system");
		ccui.getSystem();
		logStep("create inactive enterprise");
		ccui.getInactiveEnterprise();
		logStep("create inactive practice");
		ccui.getInactivePractice();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "create active client,system,enterprise and inactive practice")
	public void testCreateActiveClientSystemEnterpriseAndInactivePractice() throws Exception {
		ccui.getGeneralSettingsPage().clickClientDirectory();
		logStep("ceate inactive client");
		ccui.getClient();
		logStep("create active system");
		ccui.getSystem();
		logStep("create active enterprise");
		ccui.getEnterprise();
		logStep("create inactive practice");
		ccui.getInactivePractice();
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel button at add org page and when user click on 'CLOSE' button User redirected to general setting page")
	public void testSearchCancelAndCloseButtonFunctionalityOfManageClientPage() throws Exception {
		ccui.getGeneralSettingsPage().clickClientDirectory();
		clientDirectorPage.searchAndSelectClientNameInClientTable(clientSystementErpriseandPractice.get("clientNam"));
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().verifySearchIconAndSearchTextBox();
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
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of edit client name and client status")
	public void testEditClientNameAndStatus() throws Exception {
		ccui.getClientDirectoryPage().selectSearchedClientNameInClientTable();
		logStep("user redirected to General Settings page");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickEditButtonOfClient();
		String newClientName = CCUtils.generateAlphaNumericString(10);
		ccui.getGeneralSettingsPage().enterClientName(newClientName);
		ccui.getGeneralSettingsPage().changeStatusToInactive();
		logStep("Verify success toaster message");
		ccui.getGeneralSettingsPage().verifyToasterMessage("Changes updated successfully");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel button and verify error mesaages- 'Maximum characters reached' and 'Client name required' ")
	public void testEditClientCancelButtonAndClientRequiredAndMaxCharacterReachedError() throws Exception {
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
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit and save System name and status")
	public void editAndSaveNameAndStatusAtSystemLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
		logStep("Edit and save System name and status");
		ccui.editBasicInfoGeneralSettingsPage().clickSystemOrg();
		ccui.editOrgDetailsGeneralSettingsPage().clickEditButton();
		String orgName = CCUtils.generateAlphaNumericString(8);
		ccui.editOrgDetailsGeneralSettingsPage().EditAndSaveNameAndStatus(orgName);
		logStep("Verify success toaster message");
		ccui.editOrgDetailsGeneralSettingsPage().verifyToasterMessage("Changes updated successfully");
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit and save Enterprise name and status")
	public void editAndSaveNameAndStatusAtEnterpriseLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
		ccui.getPrivacyPolicySettingsPage()
				.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
		logStep("Edit and save Enterprise name and status");
		ccui.editBasicInfoGeneralSettingsPage().clickEnterpriseOrg();
		ccui.editOrgDetailsGeneralSettingsPage().clickEditButton();
		String orgName = CCUtils.generateAlphaNumericString(8);
		ccui.editOrgDetailsGeneralSettingsPage().EditAndSaveNameAndStatus(orgName);
		logStep("Verify success toaster message");
		ccui.editOrgDetailsGeneralSettingsPage().verifyToasterMessage("Changes updated successfully");
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Edit and save Practice name and status")
	public void editAndSaveNameAndStatusAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
		ccui.getPrivacyPolicySettingsPage()
				.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getPrivacyPolicySettingsPage().clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
		logStep("Edit and save Practice name and status");
		ccui.editOrgDetailsGeneralSettingsPage().clickEditButton();
		String orgName = CCUtils.generateAlphaNumericString(8);
		ccui.editOrgDetailsGeneralSettingsPage().EditAndSaveNameAndStatus(orgName);
		logStep("Verify success toaster message");
		ccui.editOrgDetailsGeneralSettingsPage().verifyToasterMessage("Changes updated successfully");
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify default text, search icon and search node functionality")
	public void testGeneralSettingScreen() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Verifying default text");
		String actualDefaultText = ccui.getGeneralSettingsPage().defaultTextInSearch();
		CCUtils.assertEquals(actualDefaultText, "Search");
		ccui.getGeneralSettingsPage().verifyStatusIcon();
		logStep("Verifying Auto-suggestion list is displayed");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("systemName"));
		ccui.getGeneralSettingsPage().verifyAutoSuggestionList(clientSystementErpriseandPractice.get("systemName"));
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to select and verify Auto Suggestion list for symbol search in node name")
	public void testAutoSuggestionForSingleCharacterSearch() throws Exception {
		ngHierarchyClientCreate();
		createNewSystemForNumberSymbolSearch();
		logStep("Searching for nodes with single character name in General settings search");
		ccui.getGeneralSettingsPage().clickSearchIcon();
		ccui.getGeneralSettingsPage().enterSearchText(ccuser.searchSymbolInGeneralSetting);
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifySearchedResult();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to select and verify Auto Suggestion list for numbers search in node name")
	public void testAutoSuggestionForNumbersSearch() throws Exception {
		ngHierarchyClientCreate();
		createNewSystemForNumberSymbolSearch();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		ccui.getGeneralSettingsPage().enterSearchText(ccuser.searchNumberInGeneralSetting);
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifySearchedResult();
		runOnce = false;
	}

	@Test(enabled = false, groups = {
			"smoke" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to search system node functionality in Client's General settings page")
	public void testNodeSearch() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a system node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("systemName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifySearchedResult();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to search enterprise node functionality in Client's General settings page")
	public void testEnterpriseNodeSearch() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a enterprise node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifySearchedResult();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to search practice node functionality in Client's General settings page")
	public void testPracticeNodeSearch() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifySearchedResult();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to search node which is not available in Client's General settings page")
	public void testNoResultFound() throws Exception {
		ngHierarchyClientCreate();
		logStep("Searching invalid node under client in general settings page, to check client details are highlighted");
		ccui.getGeneralSettingsPage().clickSearchIcon();
		ccui.getGeneralSettingsPage().enterSearchText(ccuser.searchInvalidNodeInGeneralSetting);
		CCUtils.pressEnterKey();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify locations, Providers & Resources, Appointment types tab & tiles are present at Client level")
	public void verifyVisibilityOfTabsAndTilesAtClientLevel() throws Exception {
		logStep("Verify locations, Providers & Resources, Appointment types tab & tiles is presents at Client level");
		ngHierarchyClientCreate();
		ccui.editBasicInfoGeneralSettingsPage().clickClientOrg();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTiles();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify locations,Providers & Resources, Appointment types tab & tiles navigate respective pages at Client level")
	public void verifyVisibilityOfTabsAndTilesNavigateRespectivePagesAtClientLevel() throws Exception {
		logStep("Verify locations, Providers & Resources, Appointment types tab & tiles navigate respective pages at Client level");
		ngHierarchyClientCreate();
		ccui.editBasicInfoGeneralSettingsPage().clickClientOrg();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTile();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify locations,Providers & Resources, Appointment types tab & tiles are present at System level")
	public void verifyVisibilityOfTabsAndTilesAtSystemLevel() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify locations, Providers & Resources, Appointment types tab & tiles is presents At System Level");
		ccui.editBasicInfoGeneralSettingsPage().clickSystemOrg();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTiles();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify locations,Providers & Resources, Appointment types tab & tiles navigate respective pages at System Level")
	public void verifyVisibilityOfTabsAndTilesNavigateRespectivePagesAtSystemLevel() throws Exception {
		logStep("Verify locations, Providers & Resources, Appointment types tab & tiles navigate respective pages at System Level");
		ngHierarchyClientCreate();
		ccui.editBasicInfoGeneralSettingsPage().clickSystemOrg();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTile();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify locations,Providers & Resources, Appointment types tab & tiles are present at Enterprise level")
	public void verifyVisibilityOfTabsAndTilesAtEnterpriseLevel() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify locations, Providers & Resources, Appointment types tab & tiles is presents At Enterprise Level");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		// ccui.editBasicInfoGeneralSettingsPage().clickEnterpriseOrg();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTiles();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify locations, Providers & Resources, Appointment types tab & tiles navigate respective pages at Enterprise Level")
	public void verifyVisibilityOfTabsAndTilesNavigateRespectivePagesAtEnterpriseLevel() throws Exception {
		logStep("Verify locations, Providers & Resources, Appointment types tab & tiles navigate respective pages at Enterprise Level");
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		// ccui.editBasicInfoGeneralSettingsPage().clickEnterpriseOrg();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyNavigationOfTile();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify locations, Providers & Resources, Appointment types tab & tiles are present at Practice level")
	public void verifyVisibilityOfTabsAndTilesAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify locations, Providers & Resources, Appointment types tab & tiles is presents At Practice Level");
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTab();
		ccui.tabsNavigationOnGeneralSettingPage().verifyVisibilityOfTiles();
		runOnce = false;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify locations, Providers & Resources,, Appointment types tab & tiles navigate respective pages at Practice Level")
	public void verifyVisibilityOfTabsAndTilesNavigateRespectivePagesAtPracticeLevel() throws Exception {
		logStep("Verify locations, Providers & Resources, Appointment types tab & tiles navigate respective pages at Enterprise Level");
		ngHierarchyClientCreate();
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
		/*
		 * ccui.getGeneralSettingsPage().clickToActivateButtonOfVirtualVisitsSolution();
		 * ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		 * ccui.getCommonMethods().
		 * verifyToasterMessage("Success! Solution has been successfully updated");
		 */
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPaySolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToActivateButtonOfEngageSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		/*
		 * ccui.getGeneralSettingsPage().clickToActivateButtonOfChatSolution();
		 * ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		 * ccui.getCommonMethods().
		 * verifyToasterMessage("Success! Solution has been successfully updated");
		 */
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPortalSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPSSSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		/*
		 * ccui.getGeneralSettingsPage().clickToVirtualVisitsMoreOptions();
		 * ccui.getGeneralSettingsPage().clickToTerminateOption();
		 * ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		 * ccui.getCommonMethods().
		 * verifyToasterMessage("Success! Solution has been successfully updated");
		 */
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
		/*
		 * ccui.getGeneralSettingsPage().clickToPatientPortalMoreOptions();
		 * ccui.getGeneralSettingsPage().clickToTerminateOption();
		 * ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		 * ccui.getCommonMethods().
		 * verifyToasterMessage("Success! Solution has been successfully updated");
		 */
		ccui.getGeneralSettingsPage().clickToPSSMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		runOnce = false;
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify activate all the solutions within practice from available to purchase section add ons")
	public void testAddPurchaseSolutionAddOnsAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsAddOnsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutionsAddOns();
		ccui.getGeneralSettingsPage().verifyAvailableToPurchaseSection();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfVirtualVisitsSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToActivateButtonOfChatSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToVirtualVisitsAddOnsMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify activate pop up web elements")
	public void testPurchasedSolutionsAddOns() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		ccui.getGeneralSettingsPage().clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(clientSystementErpriseandPractice.get("practiceName"));
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		ccui.getGeneralSettingsPage().verifySearchedResult();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsAddOnsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutionsAddOns();
		ccui.getGeneralSettingsPage().verifyAvailableToPurchaseSection();
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
