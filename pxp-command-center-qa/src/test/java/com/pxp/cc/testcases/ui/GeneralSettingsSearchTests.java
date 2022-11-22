// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddOrgNodePage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class GeneralSettingsSearchTests extends CommandCenterGenericTest {
	ClientDirectoryPage clientDirectoryPage;
	GeneralSettingsPage generalSettingsPage;
	public CommonMethods commonMethods;
	public CommandCenterGenericTest commandCenterGenericTest;
	public CreateClientPage createClientPage;
	public AddOrgNodePage addOrgNodePage;
	public NGHierarchyClientCreation ngHierarchyClientCreation;
	String systemName, enterpriseName, clientName, practiceName, timeZone, solution;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(testData.getProperty("base.url"), testData.getProperty("user.name"),
				testData.getProperty("user.password"));
	}

	public void createNewActiveClient() throws Exception {
		createClientPage = new CreateClientPage(driver);
		createClientPage.clickCreateClient();
		commonMethods = new CommonMethods(driver);
		clientName = testData.getProperty("new.test.client.name").concat("12")
				.concat(CCUtils.generateAlphaNumericString(10).concat("_"));
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientPage.createNewClient(clientName, clientSapNumber, testData.getProperty("new.client.type"),
				testData.getProperty("new.client.timezone"));
		createClientPage.verifyActiveRadioButtonIsByDefaultSelected();
		createClientPage.clickConfirmPopup();
		clientDirectoryPage = new ClientDirectoryPage(driver);
		clientDirectoryPage.searchNode(clientName);
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.verifyClientNodeIsSelected();
	}

	private void createNewActiveSystem() throws NullPointerException, InterruptedException {
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.verifyManageClientButton();
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.verifyAddSystemButton();
		addOrgNodePage.clickOnAddSystem();
		systemName = "System" + CCUtils.generateAlphaNumericString(5).concat("@");
		addOrgNodePage.enterSystemName(systemName);
		addOrgNodePage.addPmSystemAndEhrSystem(testData.getProperty("new.system"), testData.getProperty("new.system"));
		addOrgNodePage.verifyActiveRadioButtonIsByDefaultSelected();
		addOrgNodePage.clickOnSaveAndAddButton();
		addOrgNodePage.verifyToasterMessage("Changes updated successfully");
	}

	private void createNewActiveEnterprise() throws NullPointerException, InterruptedException {
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(systemName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.verifyManageClientButton();
        addOrgNodePage.clickOnManageClient();
        addOrgNodePage.clickOnExpandArrow();
        driver.navigate().refresh();
        addOrgNodePage.clickOnManageClient();
        addOrgNodePage.clickOnExpandArrow();
		addOrgNodePage.verifyAddEnterpriseButton();
		addOrgNodePage.clickOnAddEnterprise();
		enterpriseName = CCUtils.generateAlphaNumericString(18);
		addOrgNodePage.enterEnterpriseName(enterpriseName);
		addOrgNodePage.addPmSystemAndEhrSystem(testData.getProperty("new.system"), testData.getProperty("new.system"));
		addOrgNodePage.addTimezone(testData.getProperty("new.timezone.display.name"));
		addOrgNodePage.clickOnSaveAndAddButton();
		addOrgNodePage.verifyToasterMessage("Changes updated successfully");
	}

	private void createNewActivePractice() throws NullPointerException, InterruptedException {
	        generalSettingsPage = new GeneralSettingsPage(driver);
	        addOrgNodePage = new AddOrgNodePage(driver);
	        addOrgNodePage.verifyManageClientButton();
	        addOrgNodePage.clickOnManageClient();
	        addOrgNodePage.clickOnSystemExpandArrow();
	        addOrgNodePage.clickOnEnterpriseExpandArrow();
	        addOrgNodePage.verifyAddPracticeButton();
	        addOrgNodePage.clickOnAddPracticeButton();
	        practiceName = CCUtils.generateAlphaNumericString(18);
	        addOrgNodePage.enterPracticeName(practiceName);
	        addOrgNodePage.addPracticePmSystemAndEhrSystem(testData.getProperty("new.system"),
					testData.getProperty("new.system"));
			addOrgNodePage.addTimezone(testData.getProperty("new.timezone.display.name"));
			addOrgNodePage.enterSolutionsDisplayName(testData.getProperty("solution.forms"));
			addOrgNodePage.verifyActiveRadioButtonIsByDefaultSelected();
			addOrgNodePage.clickOnSaveAndAddButton();
			addOrgNodePage.verifyToasterMessage("Changes updated successfully");
		}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Verify default text, search icon and search node functionality")
	public void testGeneralSettingScreen() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.clickSearchIcon();
		logStep("Verifying default text");
		String actualDefaultText = generalSettingsPage.defaultTextInSearch();
		CCUtils.assertEquals(actualDefaultText, "Search");
		generalSettingsPage.verifyStatusIcon();
		logStep("Verifying Auto-suggestion list is displayed");
		generalSettingsPage.enterSearchText(systemName);
		generalSettingsPage.verifyAutoSuggestionList(systemName);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to select and verify Auto Suggestion list for symbol search in node name")
	public void testAutoSuggestionForSingleCharacterSearch() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		logStep("Searching for nodes with single character name in General settings search");
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(ccuser.searchSymbolInGeneralSetting);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		generalSettingsPage.verifySearchedResult();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to select and verify Auto Suggestion list for numbers search in node name")
	public void testAutoSuggestionForNumbersSearch() throws Exception {
		createNewActiveClient();
		logStep("Searching for nodes with number in General settings search");
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(ccuser.searchNumberInGeneralSetting);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		generalSettingsPage.verifySearchedResult();
	}

	@Test(enabled = true, groups = {
			"smoke" }, retryAnalyzer = RetryAnalyzer.class, description = "Test to search system node functionality in Client's General settings page")
	public void testNodeSearch() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		generalSettingsPage.clickSearchIcon();
		logStep("Searching a system node under client in general settings page");
		generalSettingsPage.enterSearchText(systemName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		generalSettingsPage.verifySearchedResult();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to search enterprise node functionality in Client's General settings page")
	public void testEnterpriseNodeSearch() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewActiveEnterprise();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		generalSettingsPage.clickSearchIcon();
		logStep("Searching a enterprise node under client in general settings page");
		generalSettingsPage.enterSearchText(enterpriseName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		generalSettingsPage.verifySearchedResult();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to search practice node functionality in Client's General settings page")
	public void testPracticeNodeSearch() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewActiveEnterprise();
		createNewActivePractice();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		generalSettingsPage.clickSearchIcon();
		logStep("Searching a practice node under client in general settings page");
		generalSettingsPage.enterSearchText(practiceName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		generalSettingsPage.verifySearchedResult();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to search node which is not available in Client's General settings page")
	public void testNoResultFound() throws Exception {
		createNewActiveClient();
		logStep("Searching invalid node under client in general settings page, to check client details are highlighted");
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText(ccuser.searchInvalidNodeInGeneralSetting);
		CCUtils.pressEnterKey();
	}
}
