// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.objectmaps.DeactivateUserConfirmationPage;
import com.pxp.cc.objectmaps.SearchOptionsPage;
import com.pxp.cc.objectmaps.ClientUsersPage;
import com.pxp.cc.objectmaps.ClientUserDetailsPage;
import com.pxp.cc.objectmaps.AddNewClientUserPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.objectmaps.AddNewProviderPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;
import com.pxp.cc.objectmaps.AddOrgNodePage;

public class AddNewClientUserTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public ClientUsersPage clientUserPage;
	public ClientUserDetailsPage clientUserDetailsPage;
	public SearchOptionsPage searchOptionsPage;
	public DeactivateUserConfirmationPage deactivateUserConfirmationPage;
	public AddNewClientUserPage addNewClientUserPage;
	public GeneralSettingsPage generalSettingsPage;
	public AddNewProviderPage addNewProviderPage;
	public CommonMethods commonMethods;
	public CreateClientPage createClientPage;
	public AddOrgNodePage addOrgNodePage;
	String firstName, clientName;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(testData.getProperty("base.url"), testData.getProperty("user.name"),
				testData.getProperty("user.password"));
	}

	private void createProvider() throws NullPointerException, InterruptedException {
		logStep("Create Client");
		createClientPage = new CreateClientPage(driver);
		createClientPage.clickCreateClient();
		clientName = testData.getProperty("new.client.name")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		createClientPage.createNewClient(clientName, clientSapNumber, testData.getProperty("new.client.type"),
				testData.getProperty("new.client.timezone"));
		createClientPage.clickConfirmPopup();
		clientDirectoryPage.searchNode(clientName);
		clientDirectoryPage.clickSearchButton();
		clientDirectoryPage.clickSearchResult();
		commonMethods = new CommonMethods(driver);
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.verifyClientNodeIsSelected();
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.addNewSystem("SystemDemo");
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText("SystemDemo");
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnExpandArrow();
		addOrgNodePage.clickOnAddPractice();
		addOrgNodePage.addNewPractice("PracticeForDemo");
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText("PracticeForDemo");
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		generalSettingsPage.verifyClientNodeIsSelected();
		logStep("Verify Providers And Resources tab is displayed");
		generalSettingsPage.verifyProvidersAndResourcesTab();
		generalSettingsPage.clickOnProvidersAndResourcesTab();
		addNewProviderPage = new AddNewProviderPage(driver);
		addNewProviderPage.verifyProvidersAndResourcesTitle();
		logStep("Create Provider");
		firstName = CCUtils.generateAlphaNumericString(5);
		addNewProviderPage.addProvider(firstName);
		addNewProviderPage.clickSearchIcon();
		addNewProviderPage.enterSearchText(firstName);
		CCUtils.pressEnterKey();
		addNewProviderPage.selectProvider();
		addNewProviderPage.editProvider();
		addNewProviderPage.verifyProvidersAndResourcesTitle();
	}

	private void createActiveClientUser() throws NullPointerException, InterruptedException {
		createProvider();
		logStep("Select command center users icon");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Select Client users tab in command center users page");
		ClientUsersPage clientUsersPage = commandCenterUsersPage.clickOnClientUsersTab();
		addNewClientUserPage = new AddNewClientUserPage(driver);
		clientUsersPage.clickOnKebabMenuButton();
		clientUsersPage.clickOnNewButton();
		logStep("Enter data in mandatory fields and Click on next button");
		addNewClientUserPage.searchClient(clientName);
		addNewProviderPage.searchProvider(firstName);
		addNewClientUserPage.clickOnNextButton();
		logStep("Select entites");
		addNewClientUserPage.clickOnIndeterminateCheckbox();
		logStep("Click on save and add button");
		addNewClientUserPage.clickOnSaveAndAdd();
		logStep("Verify success toaster message");
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Success! New user has been successfully created");
	}

	private void createInactiveClientUser() throws NullPointerException, InterruptedException {
		createProvider();
		logStep("Select command center users icon");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Select Client users tab in command center users page");
		ClientUsersPage clientUsersPage = commandCenterUsersPage.clickOnClientUsersTab();
		addNewClientUserPage = new AddNewClientUserPage(driver);
		clientUsersPage.clickOnKebabMenuButton();
		clientUsersPage.clickOnNewButton();
		logStep("Enter data in mandatory fields and Click on next button");
		addNewClientUserPage.searchClient(clientName);
		addNewProviderPage.searchProvider(firstName);
		addNewClientUserPage.clickOnInactiveStatus();
		addNewClientUserPage.clickOnNextButton();
		logStep("Select entites");
		addNewClientUserPage.clickOnIndeterminateCheckbox();
		logStep("Click on save and add button");
		addNewClientUserPage.clickOnSaveAndAdd();
		logStep("Verify success toaster message");
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Success! New user has been successfully created");
	}

	@Test(priority = 0, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new client user step one popup")
	public void testAddNewClientUserStepOnePopup() throws Exception {
		logStep("Verify add new client user step one popup");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Select Client users tab in command center users page");
		ClientUsersPage clientUsersPage = commandCenterUsersPage.clickOnClientUsersTab();
		addNewClientUserPage = new AddNewClientUserPage(driver);
		clientUsersPage.clickOnKebabMenuButton();
		clientUsersPage.clickOnNewButton();
		addNewClientUserPage.verifyAddNewClientUserPageTitle();
		addNewClientUserPage.verifyStepOneImageIsDisplayed();
		addNewClientUserPage.VerifyHeaderTextForStepOne();
		addNewClientUserPage.verifyClientLabelTextAndSearchTextboxDefaultValue();
		addNewClientUserPage.verifyProviderLabelTextAndSearchTextboxDefaultValue();
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyFirstNameLabelTextAndTextboxDefaultValue();
		commonMethods.verifyLastNameLabelTextAndTextboxDefaultValue();
		commonMethods.verifyUsernameLabelTextAndTextboxDefaultValue();
		commonMethods.verifyEmailLabelTextAndTextboxDefaultValue();
		commonMethods.verifyMobileNumberLabelTextAndTextboxDefaultValue();
		commonMethods.verifyStatusLabelTextAndRadioButtons();
		commonMethods.verifyActiveRadioButtonByDefaultSelected();
		addNewClientUserPage.verifyCrossIconIsDisplayed();
		addNewClientUserPage.verifyClientCancelButtonIsDisplayed();
		addNewClientUserPage.verifyNextButtonIsDisabled();
	}

	@Test(priority = 1, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new client user step two popup")
	public void testAddNewClientUserStepTwoPopup() throws Exception {
		createProvider();
		logStep("Select command center users icon");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Select Client users tab in command center users page");
		ClientUsersPage clientUsersPage = commandCenterUsersPage.clickOnClientUsersTab();
		addNewClientUserPage = new AddNewClientUserPage(driver);
		clientUsersPage.clickOnKebabMenuButton();
		clientUsersPage.clickOnNewButton();
		logStep("Enter data in mandatory fields and Click on next button");
		addNewClientUserPage.searchClient(clientName);
		addNewProviderPage.searchProvider(firstName);
		addNewClientUserPage.clickOnNextButton();
		logStep("Verify add new client user step two popup");
		addNewClientUserPage.verifyStepTwoImageIsDisplayed();
		addNewClientUserPage.verifyEntitySearchTextboxDefaultValue();
		addNewClientUserPage.verifyEntitiesTableHeader();
		addNewClientUserPage.verifyAllCheckboxesAreNotSelected();
		addNewClientUserPage.verifyNoEntitiesSelectedMessageIsDisplayed();
		addNewClientUserPage.verifyEntityCancelButtonIsDisplayed();
		addNewClientUserPage.verifyBackButtonIsDisplayed();
		addNewClientUserPage.verifySaveAndAddButtonIsDisabled();
	}

	@Test(priority = 2, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify maximum characters error message for first name, last name, username and email field")
	public void testMaximumCharacterError() throws Exception {
		commonMethods = new CommonMethods(driver);
		logStep("Select command center users icon");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Select Client users tab in command center users page");
		ClientUsersPage clientUsersPage = commandCenterUsersPage.clickOnClientUsersTab();
		addNewClientUserPage = new AddNewClientUserPage(driver);
		clientUsersPage.clickOnKebabMenuButton();
		clientUsersPage.clickOnNewButton();
		logStep("Enter more than 100 characters in first name, last name, username, email and mobile number field and verify maximum characters error message");
		commonMethods.enterFirstName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(commonMethods.getMaximumCharacterErrorForFirstNameField(), "Maximum characters reached");
		commonMethods.enterLastName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(commonMethods.getMaximumCharacterErrorForLastNameField(), "Maximum characters reached");
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(101));
		CCUtils.assertEquals(commonMethods.getMaximumCharacterErrorForUsernameField(), "Maximum characters reached");
		commonMethods.enterEmail((CCUtils.generateAlphaNumericString(30)).concat(CCUtils.generateNewEmail(101)));
		CCUtils.assertEquals(commonMethods.getMaximumCharacterErrorForEmailField(), "Maximum characters reached");
	}

	@Test(priority = 3, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify required error message for mandatory fields in add new client user step one page")
	public void testRequiredErrorMessage() throws Exception {
		logStep("Select command center users icon");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Select Client users tab in command center users page");
		ClientUsersPage clientUsersPage = commandCenterUsersPage.clickOnClientUsersTab();
		addNewClientUserPage = new AddNewClientUserPage(driver);
		clientUsersPage.clickOnKebabMenuButton();
		clientUsersPage.clickOnNewButton();
		logStep("Enter valid data in First name, last name, username, email and mobile number fiels");
		commonMethods.enterFirstName(CCUtils.generateAlphaNumericString(18));
		commonMethods.enterLastName(CCUtils.generateAlphaNumericString(18));
		commonMethods.enterEmail(CCUtils.generateNewEmail(11));
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		commonMethods.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
		logStep("Remove all characters from mandatory fields and verify required error message");
		commonMethods.clearFirstNameField();
		CCUtils.assertEquals(commonMethods.getRequiredErrorForFirstNameField(), "First name is required");
		commonMethods.clearLastNameField();
		CCUtils.assertEquals(commonMethods.getRequiredErrorForLastNameField(), "Last name is required");
		commonMethods.clearEmailField();
		CCUtils.assertEquals(commonMethods.getRequiredErrorForEmailField(), "Email is required");
		commonMethods.clearUsernameField();
		CCUtils.assertEquals(commonMethods.getRequiredErrorForUsernameField(), "Username is required");
	}

	@DataProvider(name = "invalidTestData")
	public Object[] getInvalidTestData() {
		Object[][] obj = new Object[][] { { testData.getProperty("invalid.user.first.name.one"),
				testData.getProperty("invalid.user.last.name.one"), testData.getProperty("invalid.user.email.one"),
				testData.getProperty("invalid.username.one"), testData.getProperty("invalid.user.mobile.number.one") },
				{ testData.getProperty("invalid.user.first.name.two"),
						testData.getProperty("invalid.user.last.name.two"),
						testData.getProperty("invalid.user.email.two"), testData.getProperty("invalid.username.two"),
						testData.getProperty("invalid.user.mobile.number.two") },
				{ testData.getProperty("invalid.user.first.name.three"),
						testData.getProperty("invalid.user.last.name.three"),
						testData.getProperty("invalid.user.email.three"),
						testData.getProperty("invalid.username.three"),
						testData.getProperty("invalid.user.mobile.number.three") } };
		return obj;
	}

	@Test(priority = 4, enabled = true, retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidTestData", description = "Enter invalid data in first name, last name, email,username and mobile number fields and verify error message")
	public void testEnterValidDetailError(String[] getTestData) throws Exception {
		commonMethods = new CommonMethods(driver);
		logStep("Enter invalid data in first name, last name, email,username and mobile number fields and verify error message");
		commonMethods.enterFirstName(getTestData[0]);
		CCUtils.assertEquals(commonMethods.getValidErrorForFirstNameField(), "Please enter valid first name");
		commonMethods.enterLastName(getTestData[1]);
		CCUtils.assertEquals(commonMethods.getValidErrorForLastNameField(), "Please enter valid last name");
		commonMethods.enterEmail(getTestData[2]);
		CCUtils.assertEquals(commonMethods.getValidErrorForEmailField(), "Please enter valid email address");
		commonMethods.enterUserName(getTestData[3]);
		CCUtils.assertEquals(commonMethods.getValidErrorForUsernameField(), "Please enter valid username");
		commonMethods.enterMobileNumber(getTestData[4]);
		CCUtils.assertEquals(commonMethods.getValidErrorForMobileNumberField(), "Please enter valid mobile number");
	}

	@Test(priority = 5, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user select all checkboxes using indeterminate checkbox and deselect all checkboxes again clicking on indeterminate checkbox")
	public void testFunctionalityOfInderminateCheckbox() throws Exception {
		logStep("Enter data in mandatory fields and Click on next button");
		createProvider();
		logStep("Select command center users icon");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Select Client users tab in command center users page");
		ClientUsersPage clientUsersPage = commandCenterUsersPage.clickOnClientUsersTab();
		addNewClientUserPage = new AddNewClientUserPage(driver);
		clientUsersPage.clickOnKebabMenuButton();
		clientUsersPage.clickOnNewButton();
		logStep("Enter data in mandatory fields and Click on next button");
		addNewClientUserPage.searchClient(clientName);
		addNewProviderPage.searchProvider(firstName);
		addNewClientUserPage.clickOnInactiveStatus();
		addNewClientUserPage.clickOnNextButton();
		logStep("Verify user select all checkboxes using indeterminate checkbox");
		addNewClientUserPage.clickOnIndeterminateCheckbox();
		addNewClientUserPage.verifyAllCheckboxesAreSelected();
		logStep("Verify no entites selected text is not present and save&add button is enabled");
		addNewClientUserPage.verifyNoEntitiesSelectedMessageIsNotPresent();
		addNewClientUserPage.verifySaveAndAddButtonIsEnabled();
		logStep("Verify user deselect all checkboxes using indeterminate checkbox");
		addNewClientUserPage.clickOnIndeterminateCheckbox();
		addNewClientUserPage.verifyAllCheckboxesAreNotSelected();
		logStep("Verify no entites selected text is present and save&add button is disabled");
		addNewClientUserPage.verifyNoEntitiesSelectedMessageIsDisplayed();
		addNewClientUserPage.verifySaveAndAddButtonIsDisabled();
	}

	@Test(priority = 6, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify the functionality of cancel and back button on add new client user step two popup")
	public void testFunctionalityOfCancelAndBackButton() throws Exception {
		createProvider();
		logStep("Select command center users icon");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Select Client users tab in command center users page");
		ClientUsersPage clientUsersPage = commandCenterUsersPage.clickOnClientUsersTab();
		addNewClientUserPage = new AddNewClientUserPage(driver);
		clientUsersPage.clickOnKebabMenuButton();
		clientUsersPage.clickOnNewButton();
		logStep("Enter data in mandatory fields and Click on next button");
		addNewClientUserPage.searchClient(clientName);
		addNewProviderPage.searchProvider(firstName);
		addNewClientUserPage.clickOnNextButton();
		logStep("Click on cancel button and verify user redirected to client user page");
		clientUsersPage = addNewClientUserPage.clickOnEntityCancelButton();
		clientUsersPage.verifyClientUsersPageTitle();
	}

	@Test(priority = 7, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter non existing client name in client search textbox and verify error message")
	public void testErrorMessageForNonExistingClient() throws Exception {
		logStep("Enter non existing client name in client search textbox and verify error message");
		logStep("Select command center users icon");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Select Client users tab in command center users page");
		ClientUsersPage clientUsersPage = commandCenterUsersPage.clickOnClientUsersTab();
		addNewClientUserPage = new AddNewClientUserPage(driver);
		clientUsersPage.clickOnKebabMenuButton();
		clientUsersPage.clickOnNewButton();
		addNewClientUserPage.enterClientName("!!@abc@tuueruru54");
		addNewClientUserPage.verifyNoClientsFoundErrorMessage();
	}

	@Test(priority = 8, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify the functionality of cancel button and cross icon on add new client user step one popup")
	public void testFunctionalityOfCancelAndCrossButton() throws Exception {
		logStep("Click on cancel button and verify user redirected to client user page");
		logStep("Select command center users icon");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Select Client users tab in command center users page");
		ClientUsersPage clientUsersPage = commandCenterUsersPage.clickOnClientUsersTab();
		addNewClientUserPage = new AddNewClientUserPage(driver);
		clientUsersPage.clickOnKebabMenuButton();
		clientUsersPage.clickOnNewButton();
		clientUsersPage = addNewClientUserPage.clickOnCancelButton();
		clientUsersPage.verifyClientUsersPageTitle();
		logStep("Click on new button");
		clientUsersPage.clickOnKebabMenuButton();
		clientUsersPage.clickOnNewButton();
		logStep("Click on cross icon and verify user redirected to client user page");
		clientUsersPage = addNewClientUserPage.clickOnCrossIcon();
		clientUsersPage.verifyClientUsersPageTitle();
	}

	@Test(priority = 9, enabled = true, groups = {"smoke"}, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new active client user functionality")
	public void testCreateActiveClientUser() throws Exception {
		createActiveClientUser();
	}

	@Test(priority = 10, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new inactive client user functionality")
	public void testCreateInactiveClientUser() throws Exception {
		createInactiveClientUser();
	}
}
