// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddAndUpdateProductSolutionsPage;
import com.pxp.cc.objectmaps.AddOrgNodePage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class AddOrgNodeTests extends CommandCenterGenericTest {
	private ClientDirectoryPage clientDirectoryPage;
	public ClientDirectoryPage cchomepage;
	public CreateClientPage createClientPage;
	public CommonMethods commonMethods;
	private GeneralSettingsPage generalSettingsPage;
	public AddOrgNodePage addOrgNodePage;
	public AddAndUpdateProductSolutionsPage addAndUpdateProductSolutionsPage;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
	}

	String clientName, systemName, enterpriseName, practiceName;

	public void createNewActiveClient() throws Exception {
		commonMethods = new CommonMethods(driver);
		createClientPage = new CreateClientPage(driver);
		logStep("Select create client button from new dropdown");
		createClientPage.clickCreateClient();
		commonMethods = new CommonMethods(driver);
		logStep("Enter valid details in mandatory fields");
		clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientPage.createNewClient(clientName, clientSapNumber, testData.getProperty("new.client.type"),
				testData.getProperty("new.client.timezone"));
		logStep("Verify active radio button is by default selected on the create client popup");
		createClientPage.verifyActiveRadioButtonIsByDefaultSelected();
		logStep("Click on create button on the create client popup");
		createClientPage.clickConfirmPopup();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! " + clientName + " has been successfully created");
		commonMethods = new CommonMethods(driver);
		clientDirectoryPage = new ClientDirectoryPage(driver);
		logStep("Searching a client in client directory");
		clientDirectoryPage.searchNode(clientName);
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		logStep("Navigating To General Settings page");
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.verifyClientNodeIsSelected();
	}

	private void createNewInactiveClient() throws NullPointerException, InterruptedException {
		commonMethods = new CommonMethods(driver);
		createClientPage = new CreateClientPage(driver);
		logStep("Select create client button from new dropdown");
		createClientPage.clickCreateClient();
		commonMethods = new CommonMethods(driver);
		logStep("Enter valid details in mandatory fields");
		clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(6));
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(6));
		createClientPage.createNewClient(clientName, clientSapNumber, testData.getProperty("new.client.type"),
				"Pacific Standard Time");
		logStep("Select inactive radio button and click on test checkbox on the create client pop-up");
		createClientPage.selectInactiveRadiobutton();
		createClientPage.selectTestClient();
		logStep("Click on create button on the create client pop-up");
		createClientPage.clickConfirmPopup();
		String actualMessage = commonMethods.getToasterMessage();
		logStep("Verify success toaster message");
		CCUtils.assertEquals(actualMessage, "Success! " + clientName + " has been successfully created");
		commonMethods = new CommonMethods(driver);
		clientDirectoryPage = new ClientDirectoryPage(driver);
		logStep("Searching a client in client directory");
		clientDirectoryPage.searchNode(clientName);
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		logStep("Navigating To General Settings page");
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.verifyClientNodeIsSelected();
	}

	private void createNewActiveSystem() throws NullPointerException, InterruptedException {
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify manage client button is displayed");
		addOrgNodePage.verifyManageClientButton();
		logStep("Click on manage client button");
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.verifyAddSystemButton();
		addOrgNodePage.clickOnAddSystem();
		logStep("Enter data in all input fields");
		systemName = CCUtils.generateAlphaNumericString(18);
		addOrgNodePage.enterSystemName(systemName);
		addOrgNodePage.addPmSystemAndEhrSystem(testData.getProperty("new.system"), testData.getProperty("new.system"));
		addOrgNodePage.verifyActiveRadioButtonIsByDefaultSelected();
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		addOrgNodePage.verifyToasterMessage("Changes updated successfully");
	}

	private void createNewInactiveSystem() throws NullPointerException, InterruptedException {
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify manage client button is displayed");
		addOrgNodePage.verifyManageClientButton();
		logStep("Click on manage client button");
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.verifyAddSystemButton();
		addOrgNodePage.clickOnAddSystem();
		logStep("Enter data in all input fields");
		systemName = CCUtils.generateAlphaNumericString(18);
		addOrgNodePage.enterSystemName(systemName);
		addOrgNodePage.addPmSystemAndEhrSystem(testData.getProperty("new.system"), testData.getProperty("new.system"));
		addOrgNodePage.verifyInactiveRadioButton();
		logStep("Click on inactive radio button");
		addOrgNodePage.selectInactiveRadiobutton();
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		addOrgNodePage.verifyToasterMessage("Changes updated successfully");
	}

	private void createNewActiveEnterprise() throws NullPointerException, InterruptedException {
		logStep("Navigating To General Settings page");
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		generalSettingsPage.clickSearchIcon();
		logStep("Searching a node under client in general settings page");
		generalSettingsPage.enterSearchText(systemName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify manage client button is displayed");
		addOrgNodePage.verifyManageClientButton();
		logStep("Click on manage client button");
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnExpandArrow();
		logStep("Verify add new Enterprise page");
		addOrgNodePage.verifyAddEnterpriseButton();
		addOrgNodePage.clickOnAddEnterprise();
		logStep("Enter data in all input fields");
		enterpriseName = CCUtils.generateAlphaNumericString(18);
		addOrgNodePage.enterEnterpriseName(enterpriseName);
		addOrgNodePage.addPmSystemAndEhrSystem(testData.getProperty("new.system"), testData.getProperty("new.system"));
		addOrgNodePage.addTimezone(testData.getProperty("new.timezone.display.name"));
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		addOrgNodePage.verifyToasterMessage("Changes updated successfully");
	}

	private void createNewInactiveEnterprise() throws NullPointerException, InterruptedException {
		logStep("Navigating To General Settings page");
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		generalSettingsPage.clickSearchIcon();
		logStep("Searching a node under client in general settings page");
		generalSettingsPage.enterSearchText(systemName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify manage client button is displayed");
		addOrgNodePage.verifyManageClientButton();
		logStep("Click on manage client button");
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnExpandArrow();
		logStep("Verify add new Enterprise page");
		addOrgNodePage.verifyAddEnterpriseButton();
		addOrgNodePage.clickOnAddEnterprise();
		logStep("Enter data in all input fields");
		enterpriseName = CCUtils.generateAlphaNumericString(18);
		addOrgNodePage.enterEnterpriseName(enterpriseName);
		addOrgNodePage.addPmSystemAndEhrSystem(testData.getProperty("new.system"), testData.getProperty("new.system"));
		addOrgNodePage.addTimezone(testData.getProperty("new.timezone.display.name"));
		addOrgNodePage.verifyInactiveRadioButton();
		logStep("Click on inactive radio button");
		addOrgNodePage.selectInactiveRadiobutton();
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		addOrgNodePage.verifyToasterMessage("Changes updated successfully");
	}

	private void createNewActivePractice() throws NullPointerException, InterruptedException {
		logStep("Navigating To General Settings page");
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.clickSearchIcon();
		logStep("Searching a node under client in general settings page");
		generalSettingsPage.enterSearchText(enterpriseName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify manage client button is displayed");
		addOrgNodePage.verifyManageClientButton();
		logStep("Click on manage client button");
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.verifyOrgName();
		addOrgNodePage.clickOnExpandArrow();
		logStep("Verify add new practice page");
		addOrgNodePage.verifyAddPracticeButton();
		addOrgNodePage.clickOnAddPracticeButton();
		logStep("Enter data in all input fields");
		practiceName = CCUtils.generateAlphaNumericString(18);
		addOrgNodePage.enterPracticeName(practiceName);
		addOrgNodePage.addPracticePmSystemAndEhrSystem(testData.getProperty("new.system"),
				testData.getProperty("new.system"));
		addOrgNodePage.addTimezone(testData.getProperty("new.timezone.display.name"));
		addOrgNodePage.enterSolutionsDisplayName(testData.getProperty("solution.forms"));
		addOrgNodePage.verifyActiveRadioButtonIsByDefaultSelected();
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		addOrgNodePage.verifyToasterMessage("Changes updated successfully");
	}
	
	private void createNewInactivePractice() throws NullPointerException, InterruptedException {
		logStep("Navigating To General Settings page");
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.clickSearchIcon();
		logStep("Searching a node under client in general settings page");
		generalSettingsPage.enterSearchText(enterpriseName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify manage client button is displayed");
		addOrgNodePage.verifyManageClientButton();
		logStep("Click on manage client button");
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.verifyOrgName();
		addOrgNodePage.clickOnExpandArrow();
		logStep("Verify add new practice page");
		addOrgNodePage.verifyAddPracticeButton();
		addOrgNodePage.clickOnAddPracticeButton();
		logStep("Enter data in all input fields");
		practiceName = CCUtils.generateAlphaNumericString(18);
		addOrgNodePage.enterPracticeName(practiceName);
		addOrgNodePage.addPracticePmSystemAndEhrSystem(testData.getProperty("new.system"),
				testData.getProperty("new.system"));
		addOrgNodePage.addTimezone(testData.getProperty("new.timezone.display.name"));
		addOrgNodePage.enterSolutionsDisplayName(testData.getProperty("solution.forms"));
		addOrgNodePage.verifyInactiveRadioButton();
		logStep("Click on inactive radio button");
		addOrgNodePage.selectInactiveRadiobutton();
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		addOrgNodePage.verifyToasterMessage("Changes updated successfully");
	}

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new system page")
	public void testAddNewSystemPage() throws Exception {
		createNewActiveClient();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify manage client button is displayed");
		addOrgNodePage.verifyManageClientButton();
		logStep("Click on manage client button");
		addOrgNodePage.clickOnManageClient();
		logStep("Verify add new system page");
		addOrgNodePage.verifyAddSystemButton();
		addOrgNodePage.clickOnAddSystem();
		addOrgNodePage.verifyAddNewSystemPage();
	}

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in system Name field and verify error message")
	public void testErrorMessagesAtAddNewSystem() throws Exception {
		createNewActiveClient();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify manage client button is displayed");
		addOrgNodePage.verifyManageClientButton();
		logStep("Click on manage client button");
		addOrgNodePage.clickOnManageClient();
		logStep("Verify maximum character reached error message for system Name");
		addOrgNodePage.verifyAddSystemButton();
		addOrgNodePage.clickOnAddSystem();
		addOrgNodePage.verifyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101));
	}

	@Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add active system below inactive client and status of newly added node is by default inactive")
	public void testAddNewActiveSystemBelowInactiveClient() throws Exception {
		createNewInactiveClient();
		createNewActiveSystem();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added system node displayed on general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(systemName);
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(systemName, false);
	}

	@Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add inactive system below active client")
	public void testAddNewInactiveSystemBelowActiveClient() throws Exception {
		createNewActiveClient();
		createNewInactiveSystem();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added system node is displayed on general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(systemName);
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(false);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(systemName, false);
	}

	@Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in add new system page and verify add new system functionality")
	public void testAddNewActiveSystemBelowActiveClient() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added system node displayed on general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(systemName);
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(systemName, true);
	}

	@Test(priority = 6, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new Enterprise page")
	public void testAddNewEnterprisePage() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Navigating To General Settings page");
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		generalSettingsPage.clickSearchIcon();
		logStep("Searching a node under client in general settings page");
		generalSettingsPage.enterSearchText(systemName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify manage client button is displayed");
		addOrgNodePage.verifyManageClientButton();
		logStep("Click on manage client button");
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnExpandArrow();
		logStep("Verify add new Enterprise page");
		addOrgNodePage.verifyAddEnterpriseButton();
		addOrgNodePage.clickOnAddEnterprise();
		addOrgNodePage.verifyAddNewEnterprisePage();
	}

	@Test(priority = 7, retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in enterprise Name field and verify error message")
	public void testErrorMessagesAtAddNewEnterprise() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		logStep("Navigating To General Settings page");
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		generalSettingsPage.clickSearchIcon();
		logStep("Searching a node under client in general settings page");
		generalSettingsPage.enterSearchText(systemName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify manage client button is displayed");
		addOrgNodePage.verifyManageClientButton();
		logStep("Click on manage client button");
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnExpandArrow();
		addOrgNodePage.verifyOrgName();
		logStep("Verify add new Enterprise page");
		addOrgNodePage.verifyAddEnterpriseButton();
		addOrgNodePage.clickOnAddEnterprise();
		addOrgNodePage.verifyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101));
	}

	@Test(priority = 8, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new enterprise functionality")
	public void testAddNewActiveEnterpriseBelowActiveSystem() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewActiveEnterprise();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added enterprise node displayed on general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(enterpriseName);
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(enterpriseName, true);
	}

	@Test(priority = 9, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add active enterprise below inactive system and status of newly added node is by default inactive")
	public void testAddNewActiveEnterpriseBelowInactiveSystem() throws Exception {
		createNewActiveClient();
		createNewInactiveSystem();
		createNewActiveEnterprise();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added enterprise node displayed on general settings page and status of enterprise node is inactive");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(enterpriseName);
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(enterpriseName, false);
	}

	@Test(priority = 10, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add inactive enterprise node below active system")
	public void testAddNewInactiveEnterpriseBelowActiveSystem() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewInactiveEnterprise();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added enterprise node displayed on general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(enterpriseName);
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(false);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(enterpriseName, false);
	}

	@Test(priority = 11, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new Practice page")
	public void testAddNewPracticePage() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewActiveEnterprise();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Searching a node under client in general settings page");
		generalSettingsPage.enterSearchText(enterpriseName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		logStep("Verify manage client button is displayed");
		addOrgNodePage.verifyManageClientButton();
		logStep("Click on manage client button");
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.verifyOrgName();
		addOrgNodePage.clickOnExpandArrow();
		logStep("Verify add new Enterprise page");
		addOrgNodePage.verifyAddPracticeButton();
		addOrgNodePage.clickOnAddPracticeButton();
		addOrgNodePage.verifyAddNewPracticePage();
	}

	@Test(priority = 12, retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in Practice Name field and verify error message")
	public void testMaximumCharactersErrorMessageForPracticeNameInputField() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewActiveEnterprise();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		generalSettingsPage.clickSearchIcon();
		logStep("Searching a node under client in general settings page");
		generalSettingsPage.enterSearchText(enterpriseName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify manage client button is displayed");
		addOrgNodePage.verifyManageClientButton();
		logStep("Click on manage client button");
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.verifyOrgName();
		addOrgNodePage.clickOnExpandArrow();
		logStep("Verify add new Enterprise page");
		addOrgNodePage.verifyAddPracticeButton();
		addOrgNodePage.clickOnAddPracticeButton();
		addOrgNodePage.verifyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101));
	}

	@Test(priority = 13, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add active nge practice node below inactive enterprise")
	public void testAddNewActiveNgePracticeBelowInactiveEnterprise() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewInactiveEnterprise();
		createNewActivePractice();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(practiceName);
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(practiceName, false);
	}

	@Test(priority = 14, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add inactive nge practice node below active enterprise")
	public void testAddNewInactiveNgePracticeBelowActiveEnterprise() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewActiveEnterprise();
		createNewInactivePractice();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(practiceName);
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(practiceName, false);
	}

	@Test(priority = 15, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new nge practice(without solutions) functionality")
	public void testAddNewActiveNgePracticeWithoutSolutionsBelowActiveEnterprise() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewActiveEnterprise();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Navigating To General Settings page");
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.clickSearchIcon();
		logStep("Searching a node under client in general settings page");
		generalSettingsPage.enterSearchText(enterpriseName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify manage client button is displayed");
		addOrgNodePage.verifyManageClientButton();
		logStep("Click on manage client button");
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.verifyOrgName();
		addOrgNodePage.clickOnExpandArrow();
		logStep("Verify add new practice page");
		addOrgNodePage.verifyAddPracticeButton();
		addOrgNodePage.clickOnAddPracticeButton();
		logStep("Enter data in all input fields");
		String practiceName = CCUtils.generateAlphaNumericString(18);
		addOrgNodePage.enterPracticeName(practiceName);
		addOrgNodePage.addPracticePmSystemAndEhrSystem(testData.getProperty("new.system"),
				testData.getProperty("new.system"));
		addOrgNodePage.addTimezone(testData.getProperty("new.timezone.display.name"));
		addOrgNodePage.verifyActiveRadioButtonIsByDefaultSelected();
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		addOrgNodePage.verifyToasterMessage("Changes updated successfully");
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(practiceName);
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(practiceName, false);
		addAndUpdateProductSolutionsPage = new AddAndUpdateProductSolutionsPage(driver);
		addAndUpdateProductSolutionsPage.verifySolutionsNameForAvailableToPurchase();
	}

	@Test(priority = 16, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new nge practice(with solutions) functionality")
	public void testAddNewInactiveNgePracticeWithSolutionsBelowActiveEnterprise() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewActiveEnterprise();
		createNewActivePractice();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(practiceName);
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(practiceName, false);
		addAndUpdateProductSolutionsPage = new AddAndUpdateProductSolutionsPage(driver);
		addAndUpdateProductSolutionsPage.verifyActiveSolutions();
	}

	@Test(priority = 17, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new agnostic practice(without solutions) functionality")
	public void testAddNewActiveAgnosticPracticeWithoutSolutionsBelowActiveClient() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		logStep("Navigating To General Settings page");
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		generalSettingsPage.clickSearchIcon();
		logStep("Searching a node under client in general settings page");
		generalSettingsPage.enterSearchText(systemName);
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify manage client button is displayed");
		addOrgNodePage.verifyManageClientButton();
		logStep("Click on manage client button");
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnExpandArrow();
		logStep("Verify add new practice page");
		addOrgNodePage.verifyAddPracticeButton();
		addOrgNodePage.clickOnAddPracticeButton();
		logStep("Enter data in all input fields");
		String practiceName = CCUtils.generateAlphaNumericString(18);
		addOrgNodePage.enterPracticeName(practiceName);
		addOrgNodePage.addPracticePmSystemAndEhrSystem(testData.getProperty("new.system"),
				testData.getProperty("new.system"));
		addOrgNodePage.addTimezone(testData.getProperty("new.timezone.display.name"));
		addOrgNodePage.verifyActiveRadioButtonIsByDefaultSelected();
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify success toaster message");
		addOrgNodePage.verifyToasterMessage("Changes updated successfully");
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(practiceName);
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(practiceName, false);
		addAndUpdateProductSolutionsPage = new AddAndUpdateProductSolutionsPage(driver);
		addAndUpdateProductSolutionsPage.verifySolutionsNameForAvailableToPurchase();
	}

	@Test(priority = 18, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new inactive agnostic practice(with solutions) functionality")
	public void testAddNewInactiveAgnosticPracticeWithSolutionsBelowActiveClient() throws Exception {
		createNewActiveClient();
		createNewActiveSystem();
		createNewInactivePractice();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(practiceName);
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(practiceName, false);
		addAndUpdateProductSolutionsPage = new AddAndUpdateProductSolutionsPage(driver);
		addAndUpdateProductSolutionsPage.verifyActiveSolutions();
	}

	@Test(priority = 19, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add active agnostic practice node below inactive client")
	public void testAddNewActiveAgnosticPracticeBelowInactiveClient() throws Exception {
		createNewInactiveClient();
		createNewActiveSystem();
		createNewActivePractice();
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsOnGeneralSettingsPage(practiceName);
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(practiceName, false);
	}

	@Test(priority = 20, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel button and close button on add org page")
	public void testCancelAndCrossIconFunctionality() throws Exception {
		createNewActiveClient();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify manage client button is displayed");
		addOrgNodePage.verifyManageClientButton();
		logStep("Click on manage client button");
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.verifyAddSystemButton();
		addOrgNodePage.clickOnAddSystem();
		logStep("Click on cancel button");
		addOrgNodePage.clickOnCancelButton();
		logStep("Verify after clicking on cancel button user redirected to General Settings page");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Click on Add button");
		addOrgNodePage.clickOnAddSystem();
		logStep("Click on org close button");
		addOrgNodePage.clickOnOrgCloseButton();
		logStep("Verify after clicking on cross button user redirected to General Settings page");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
	}

	@Test(priority = 21, retryAnalyzer = RetryAnalyzer.class, description = "Verify when user click on 'CLOSE' button User redirected to general setting page")
	public void testCloseButtonFunctionality() throws Exception {
		createNewActiveClient();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify manage client button is displayed");
		addOrgNodePage.verifyManageClientButton();
		logStep("Click on manage client button");
		addOrgNodePage.clickOnManageClient();
		logStep("Click on close button");
		addOrgNodePage.clickOnCloseButton();
		logStep("Verify after clicking on close button user redirected to General Settings page");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
	}
}
