//Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.util.CCUtils;

public class AddOrgNodeTests extends CommandCenterGenericTest {
	static boolean runOnce = true;
	public CCUI ccui;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		if (runOnce) {
			ccui = new CCUI(testData, driver);
			logStep("Login to command center portal");
			ccui.getClientDirectoryPage();
			ccui.initializeTestData();
			}
	}

	String clientName, systemName, enterpriseName, practiceName;

	public void createNewActiveClient() throws Exception {
		logStep("Select create client button from new dropdown");
		ccui.getNgHierarchyClientCreationPage().clickCreateClient();
		logStep("Enter valid details in mandatory fields");
		clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		ccui.getNgHierarchyClientCreationPage().createNewClient(clientName, clientSapNumber, testData.getProperty("new.client.type"),
				testData.getProperty("new.client.timezone"));
		logStep("Verify active radio button is by default selected on the create client popup");
		ccui.getNgHierarchyClientCreationPage().verifyActiveRadioButtonIsByDefaultSelected();
		logStep("Click on create button on the create client popup");
		ccui.getNgHierarchyClientCreationPage().clickConfirmPopup();
		String actualMessage = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! " + clientName + " has been successfully created");
		logStep("Searching a client in client directory");
		ccui.getNgHierarchyClientCreationPage().searchNode(clientName);
		ccui.getNgHierarchyClientCreationPage().clickSearchButton();
		ccui.getNgHierarchyClientCreationPage().clickSearchResult(clientName);
		logStep("Navigating To General Settings page");
		ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
	}

	private void createNewInactiveClient() throws Exception {
		logStep("Select create client button from new dropdown");
		ccui.getNgHierarchyClientCreationPage().clickCreateClient();
		logStep("Enter valid details in mandatory fields");
		clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		ccui.getNgHierarchyClientCreationPage().createNewClient(clientName, clientSapNumber, testData.getProperty("new.client.type"),
				testData.getProperty("new.client.timezone"));
		logStep("Select inactive radio button and click on test checkbox on the create client pop-up");
		ccui.getNgHierarchyClientCreationPage().selectInactiveRadiobutton();
		ccui.getNgHierarchyClientCreationPage().selectTestClient();
		logStep("Click on create button on the create client pop-up");
		ccui.getNgHierarchyClientCreationPage().clickConfirmPopup();
		String actualMessage = ccui.getCommonMethods().getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! " + clientName + " has been successfully created");
		logStep("Searching a client in client directory");
		ccui.getNgHierarchyClientCreationPage().searchNode(clientName);
		ccui.getNgHierarchyClientCreationPage().clickSearchButton();
		ccui.getNgHierarchyClientCreationPage().clickSearchResult(clientName);
		logStep("Navigating To General Settings page");
		ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
	}

	private void createNewActiveSystem() throws NullPointerException, InterruptedException {
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().verifyAddSystemButton();
		ccui.getAddOrgNodePage().clickOnAddSystem();
		logStep("Enter data in all input fields");
		systemName = CCUtils.generateAlphaNumericString(18);
		ccui.getAddOrgNodePage().enterSystemName(systemName);
		ccui.getAddOrgNodePage().addPmSystemAndEhrSystem(testData.getProperty("new.system"), testData.getProperty("new.system"));
		logStep("Click on save and add button");
		ccui.getAddOrgNodePage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.getAddOrgNodePage().verifyToasterMessage("Changes updated successfully");
	}

	private void createNewInactiveSystem() throws NullPointerException, InterruptedException {
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().verifyAddSystemButton();
		ccui.getAddOrgNodePage().clickOnAddSystem();
		logStep("Enter data in all input fields");
		systemName = CCUtils.generateAlphaNumericString(18);
		ccui.getAddOrgNodePage().enterSystemName(systemName);
		ccui.getAddOrgNodePage().addPmSystemAndEhrSystem(testData.getProperty("new.system"), testData.getProperty("new.system"));
		ccui.getAddOrgNodePage().verifyInactiveRadioButton();
		logStep("Click on inactive radio button");
		ccui.getAddOrgNodePage().selectInactiveRadiobutton();
		logStep("Click on save and add button");
		ccui.getAddOrgNodePage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.getAddOrgNodePage().verifyToasterMessage("Changes updated successfully");
	}

	private void createNewActiveEnterprise() throws NullPointerException, InterruptedException {
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Searching a node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(systemName);
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().clickOnExpandArrow();
		logStep("Verify add new Enterprise page");
		ccui.getAddOrgNodePage().verifyAddEnterpriseButton();
		ccui.getAddOrgNodePage().clickOnAddEnterprise();
		logStep("Enter data in all input fields");
		enterpriseName = CCUtils.generateAlphaNumericString(18);
		ccui.getAddOrgNodePage().enterEnterpriseName(enterpriseName);
		ccui.getAddOrgNodePage().addPmSystemAndEhrSystem(testData.getProperty("new.system"), testData.getProperty("new.system"));
		ccui.getAddOrgNodePage().addTimezone(testData.getProperty("new.timezone.display.name"));
		logStep("Click on save and add button");
		ccui.getAddOrgNodePage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.getAddOrgNodePage().verifyToasterMessage("Changes updated successfully");
	}

	private void createNewInactiveEnterprise() throws NullPointerException, InterruptedException {
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Searching a node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(systemName);
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().clickOnExpandArrow();
		logStep("Verify add new Enterprise page");
		ccui.getAddOrgNodePage().verifyAddEnterpriseButton();
		ccui.getAddOrgNodePage().clickOnAddEnterprise();
		logStep("Enter data in all input fields");
		enterpriseName = CCUtils.generateAlphaNumericString(18);
		ccui.getAddOrgNodePage().enterEnterpriseName(enterpriseName);
		ccui.getAddOrgNodePage().addPmSystemAndEhrSystem(testData.getProperty("new.system"), testData.getProperty("new.system"));
		ccui.getAddOrgNodePage().addTimezone(testData.getProperty("new.timezone.display.name"));
		ccui.getAddOrgNodePage().verifyInactiveRadioButton();
		logStep("Click on inactive radio button");
		ccui.getAddOrgNodePage().selectInactiveRadiobutton();
		logStep("Click on save and add button");
		ccui.getAddOrgNodePage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.getAddOrgNodePage().verifyToasterMessage("Changes updated successfully");
	}

	private void createNewActivePractice() throws NullPointerException, InterruptedException {
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Searching a node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(enterpriseName);
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().verifyOrgName();
		ccui.getAddOrgNodePage().clickOnExpandArrow();
		logStep("Verify add new practice page");
		ccui.getAddOrgNodePage().verifyAddPracticeButton();
		ccui.getAddOrgNodePage().clickOnAddPracticeButton();
		logStep("Enter data in all input fields");
		practiceName = CCUtils.generateAlphaNumericString(18);
		ccui.getAddOrgNodePage().enterPracticeName(practiceName);
		ccui.getAddOrgNodePage().addPracticePmSystemAndEhrSystem(testData.getProperty("new.system"),
				testData.getProperty("new.system"));
		ccui.getAddOrgNodePage().addTimezone(testData.getProperty("new.timezone.display.name"));
		ccui.getAddOrgNodePage().enterSolutionsDisplayName(testData.getProperty("solution.forms"));
		ccui.getAddOrgNodePage().verifyActiveRadioButtonIsByDefaultSelected();
		logStep("Click on save and add button");
		ccui.getAddOrgNodePage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.getAddOrgNodePage().verifyToasterMessage("Changes updated successfully");
	}

	private void createNewInactivePractice() throws NullPointerException, InterruptedException {
		logStep("Searching a node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(enterpriseName);
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().verifyOrgName();
		ccui.getAddOrgNodePage().clickOnExpandArrow();
		logStep("Verify add new practice page");
		ccui.getAddOrgNodePage().verifyAddPracticeButton();
		ccui.getAddOrgNodePage().clickOnAddPracticeButton();
		logStep("Enter data in all input fields");
		practiceName = CCUtils.generateAlphaNumericString(18);
		ccui.getAddOrgNodePage().enterPracticeName(practiceName);
		ccui.getAddOrgNodePage().addPracticePmSystemAndEhrSystem(testData.getProperty("new.system"),
				testData.getProperty("new.system"));
		ccui.getAddOrgNodePage().addTimezone(testData.getProperty("new.timezone.display.name"));
		ccui.getAddOrgNodePage().enterSolutionsDisplayName(testData.getProperty("solution.forms"));
		ccui.getAddOrgNodePage().verifyInactiveRadioButton();
		logStep("Click on inactive radio button");
		ccui.getAddOrgNodePage().selectInactiveRadiobutton();
		logStep("Click on save and add button");
		ccui.getAddOrgNodePage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.getAddOrgNodePage().verifyToasterMessage("Changes updated successfully");
	}

	private void createNewActiveAgnosticPractice() throws NullPointerException, InterruptedException {
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Searching a node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(systemName);
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().verifyOrgName();
		ccui.getAddOrgNodePage().clickOnExpandArrow();
		logStep("Verify add new practice page");
		ccui.getAddOrgNodePage().verifyAddPracticeButton();
		ccui.getAddOrgNodePage().clickOnAddPracticeButton();
		logStep("Enter data in all input fields");
		practiceName = CCUtils.generateAlphaNumericString(18);
		ccui.getAddOrgNodePage().enterPracticeName(practiceName);
		ccui.getAddOrgNodePage().addPracticePmSystemAndEhrSystem(testData.getProperty("new.system"),
				testData.getProperty("new.system"));
		ccui.getAddOrgNodePage().addTimezone(testData.getProperty("new.timezone.display.name"));
		ccui.getAddOrgNodePage().enterSolutionsDisplayName(testData.getProperty("solution.forms"));
		ccui.getAddOrgNodePage().verifyActiveRadioButtonIsByDefaultSelected();
		logStep("Click on save and add button");
		ccui.getAddOrgNodePage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.getAddOrgNodePage().verifyToasterMessage("Changes updated successfully");
	}

	private void createNewInactiveAgnosticPractice() throws NullPointerException, InterruptedException {
		logStep("Searching a node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(systemName);
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().verifyOrgName();
		ccui.getAddOrgNodePage().clickOnExpandArrow();
		logStep("Verify add new practice page");
		ccui.getAddOrgNodePage().verifyAddPracticeButton();
		ccui.getAddOrgNodePage().clickOnAddPracticeButton();
		logStep("Enter data in all input fields");
		practiceName = CCUtils.generateAlphaNumericString(18);
		ccui.getAddOrgNodePage().enterPracticeName(practiceName);
		ccui.getAddOrgNodePage().addPracticePmSystemAndEhrSystem(testData.getProperty("new.system"),
				testData.getProperty("new.system"));
		ccui.getAddOrgNodePage().addTimezone(testData.getProperty("new.timezone.display.name"));
		ccui.getAddOrgNodePage().enterSolutionsDisplayName(testData.getProperty("solution.forms"));
		ccui.getAddOrgNodePage().verifyInactiveRadioButton();
		logStep("Click on inactive radio button");
		ccui.getAddOrgNodePage().selectInactiveRadiobutton();
		logStep("Click on save and add button");
		ccui.getAddOrgNodePage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.getAddOrgNodePage().verifyToasterMessage("Changes updated successfully");
	}

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new system page")
	public void testAddNewSystemPage() throws Exception {
		createNewActiveClient();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		logStep("Verify add new system page");
		ccui.getAddOrgNodePage().verifyAddSystemButton();
		ccui.getAddOrgNodePage().clickOnAddSystem();
		ccui.getAddOrgNodePage().verifyAddNewSystemPage();
	}

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in system Name field and verify error message")
	public void testErrorMessagesAtAddNewSystem() throws Exception {
		createNewActiveClient();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		logStep("Verify maximum character reached error message for system Name");
		ccui.getAddOrgNodePage().verifyAddSystemButton();
		ccui.getAddOrgNodePage().clickOnAddSystem();
		ccui.getAddOrgNodePage().verifyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101));
	}

	@Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add active system below inactive client and status of newly added node is by default inactive")
	public void testAddNewActiveSystemBelowInactiveClient() throws Exception {
		createNewInactiveClient();
		createNewActiveSystem();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Verify newly added system node displayed on general settings page");
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(systemName);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsInOrgTree(systemName, false);
	}

	@Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add inactive system below active client")
	public void testAddNewInactiveSystemBelowActiveClient() throws Exception {
		createNewActiveClient();
		createNewInactiveSystem();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Verify newly added system node is displayed on general settings page");
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(systemName);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeStatusOnGeneralSettings(false);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsInOrgTree(systemName, false);
	}

	@Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in add new system page and verify add new system functionality")
	public void testAddNewActiveSystemBelowActiveClient() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Verify newly added system node displayed on general settings page");
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(systemName);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeStatusOnGeneralSettings(true);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsInOrgTree(systemName, true);
	}

	@Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new Enterprise page")
	public void testAddNewEnterprisePage() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		logStep("Navigating To General Settings page");
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Searching a node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(systemName);
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().clickOnExpandArrow();
		logStep("Verify add new Enterprise page");
		ccui.getAddOrgNodePage().verifyAddEnterpriseButton();
		ccui.getAddOrgNodePage().clickOnAddEnterprise();
		ccui.getAddOrgNodePage().verifyAddNewEnterprisePage();
	}

	@Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in enterprise Name field and verify error message")
	public void testErrorMessagesAtAddNewEnterprise() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		logStep("Navigating To General Settings page");
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Searching a node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(systemName);
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().clickOnExpandArrow();
		ccui.getAddOrgNodePage().verifyOrgName();
		logStep("Verify add new Enterprise page");
		ccui.getAddOrgNodePage().verifyAddEnterpriseButton();
		ccui.getAddOrgNodePage().clickOnAddEnterprise();
		ccui.getAddOrgNodePage().verifyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101));
	}

	@Test(priority = 8, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new enterprise functionality")
	public void testAddNewActiveEnterpriseBelowActiveSystem() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewActiveEnterprise();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Verify newly added enterprise node displayed on general settings page");
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(enterpriseName);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeStatusOnGeneralSettings(true);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsInOrgTree(enterpriseName, true);
	}

	@Test(priority = 9, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add active enterprise below inactive system and status of newly added node is by default inactive")
	public void testAddNewActiveEnterpriseBelowInactiveSystem() throws Exception {
		createNewActiveClient();
		createNewInactiveSystem();
		createNewActiveEnterprise();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Verify newly added enterprise node displayed on general settings page and status of enterprise node is inactive");
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(enterpriseName);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsInOrgTree(enterpriseName, false);
	}

	@Test(priority = 10, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add inactive enterprise node below active system")
	public void testAddNewInactiveEnterpriseBelowActiveSystem() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewInactiveEnterprise();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Verify newly added enterprise node displayed on general settings page");
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(enterpriseName);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeStatusOnGeneralSettings(false);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsInOrgTree(enterpriseName, false);
	}

	@Test(priority = 11, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new Practice page")
	public void testAddNewPracticePage() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewActiveEnterprise();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle(); 
		logStep("Searching a node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(enterpriseName);
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().verifyOrgName();
		ccui.getAddOrgNodePage().clickOnExpandArrow();
		logStep("Verify add new Enterprise page");
		ccui.getAddOrgNodePage().verifyAddPracticeButton();
		ccui.getAddOrgNodePage().clickOnAddPracticeButton();
		ccui.getAddOrgNodePage().verifyAddNewPracticePage();
	}

	@Test(priority = 12, retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in Practice Name field and verify error message")
	public void testMaximumCharactersErrorMessageForPracticeNameInputField() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewActiveEnterprise();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle(); 
		logStep("Searching a node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(enterpriseName);
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().verifyOrgName();
		ccui.getAddOrgNodePage().clickOnExpandArrow();
		logStep("Verify add new Enterprise page");
		ccui.getAddOrgNodePage().verifyAddPracticeButton();
		ccui.getAddOrgNodePage().clickOnAddPracticeButton();
		ccui.getAddOrgNodePage().verifyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101));
	}

	@Test(priority = 13, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add active nge practice node below inactive enterprise")
	public void testAddNewActiveNgePracticeBelowInactiveEnterprise() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewInactiveEnterprise();
		createNewActivePractice();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(practiceName);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactiveForPractice(true);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsInOrgTree(practiceName, false);
	}

	@Test(priority = 14, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add inactive nge practice node below active enterprise")
	public void testAddNewInactiveNgePracticeBelowActiveEnterprise() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewActiveEnterprise();
		createNewInactivePractice();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(practiceName);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactiveForPractice(true);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsInOrgTree(practiceName, false);
	}

	@Test(priority = 15, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new nge practice(without solutions) functionality")
	public void testAddNewActiveNgePracticeWithoutSolutionsBelowActiveEnterprise() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewActiveEnterprise();
		logStep("Searching a node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(enterpriseName);
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().verifyOrgName();
		ccui.getAddOrgNodePage().clickOnExpandArrow();
		logStep("Verify add new practice page");
		ccui.getAddOrgNodePage().verifyAddPracticeButton();
		ccui.getAddOrgNodePage().clickOnAddPracticeButton();
		logStep("Enter data in all input fields");
		String practiceName = CCUtils.generateAlphaNumericString(18);
		ccui.getAddOrgNodePage().enterPracticeName(practiceName);
		ccui.getAddOrgNodePage().addPracticePmSystemAndEhrSystem(testData.getProperty("new.system"),
				testData.getProperty("new.system"));
		ccui.getAddOrgNodePage().addTimezone(testData.getProperty("new.timezone.display.name"));
		ccui.getAddOrgNodePage().verifyActiveRadioButtonIsByDefaultSelected();
		logStep("Click on save and add button");
		ccui.getAddOrgNodePage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.getAddOrgNodePage().verifyToasterMessage("Changes updated successfully");
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(practiceName);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactiveForPractice(true);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsInOrgTree(practiceName, false);
	}

	@Test(priority = 16, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new nge practice(with solutions) functionality")
	public void testAddNewInactiveNgePracticeWithSolutionsBelowActiveEnterprise() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewActiveEnterprise();
		createNewActivePractice();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(practiceName);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactiveForPractice(true);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsInOrgTree(practiceName, false);
	}

	@Test(priority = 17, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new agnostic practice(without solutions) functionality")
	public void testAddNewActiveAgnosticPracticeWithoutSolutionsBelowActiveClient() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		logStep("Navigating To General Settings page");
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle(); 
		logStep("Searching a node under client in general settings page");
		ccui.getGeneralSettingsPage().enterSearchText(systemName);
		ccui.getGeneralSettingsPage().clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().clickOnExpandArrow();
		logStep("Verify add new practice page");
		ccui.getAddOrgNodePage().verifyAddPracticeButton();
		ccui.getAddOrgNodePage().clickOnAddPracticeButton();
		logStep("Enter data in all input fields");
		String practiceName = CCUtils.generateAlphaNumericString(18);
		ccui.getAddOrgNodePage().enterPracticeName(practiceName);
		ccui.getAddOrgNodePage().addPracticePmSystemAndEhrSystem(testData.getProperty("new.system"),
				testData.getProperty("new.system"));
		ccui.getAddOrgNodePage().addTimezone(testData.getProperty("new.timezone.display.name"));
		ccui.getAddOrgNodePage().verifyActiveRadioButtonIsByDefaultSelected();
		logStep("Click on save and add button");
		ccui.getAddOrgNodePage().clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		ccui.getAddOrgNodePage().verifyToasterMessage("Changes updated successfully");
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(practiceName);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactiveForPractice(true);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsInOrgTree(practiceName, false);
	}

	@Test(priority = 18, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new inactive agnostic practice(with solutions) functionality")
	public void testAddNewInactiveAgnosticPracticeWithSolutionsBelowActiveClient() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewInactiveAgnosticPractice();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(practiceName);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactiveForPractice(true);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsInOrgTree(practiceName, false);
	}

	@Test(priority = 19, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add active agnostic practice node below inactive client")
	public void testAddNewActiveAgnosticPracticeBelowInactiveClient() throws Exception {
		createNewInactiveClient();
		createNewActiveSystem();
		createNewActiveAgnosticPractice();
		logStep("Verify general settings page title");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(practiceName);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		ccui.getGeneralSettingsPage().verifyNewlyAddedOrgNodeDetailsInOrgTree(practiceName, false);
	}

	@Test(priority = 20, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel button and close button on add org page")
	public void testCancelAndCrossIconFunctionality() throws Exception {
		createNewActiveClient();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		ccui.getAddOrgNodePage().verifyAddSystemButton();
		ccui.getAddOrgNodePage().clickOnAddSystem();
		logStep("Click on cancel button");
		ccui.getAddOrgNodePage().clickOnCancelButton();
		logStep("Verify after clicking on cancel button user redirected to General Settings page");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
		logStep("Click on Add button");
		ccui.getAddOrgNodePage().clickOnAddSystem();
		logStep("Click on org close button");
		ccui.getAddOrgNodePage().clickOnOrgCloseButton();
		logStep("Verify after clicking on cross button user redirected to General Settings page");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
	}

	@Test(priority = 21, retryAnalyzer = RetryAnalyzer.class, description = "Verify when user click on 'CLOSE' button User redirected to general setting page")
	public void testCloseButtonFunctionality() throws Exception {
		createNewActiveClient();
		logStep("Verify manage client button is displayed");
		ccui.getAddOrgNodePage().verifyManageClientButton();
		logStep("Click on manage client button");
		ccui.getAddOrgNodePage().clickOnManageClient();
		logStep("Click on close button");
		ccui.getAddOrgNodePage().clickOnCloseButton();
		logStep("Verify after clicking on close button user redirected to General Settings page");
		ccui.getGeneralSettingsPage().verifyGeneralSettingsPageTitle();
	}
}