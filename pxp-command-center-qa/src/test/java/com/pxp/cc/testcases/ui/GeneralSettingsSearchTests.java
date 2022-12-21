// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.util.CCUtils;

public class GeneralSettingsSearchTests extends CommandCenterGenericTest {
	static boolean runOnce = true;
	public CCUI ccui;

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

	String systemName;

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Verify default text, search icon and search node functionality")
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
