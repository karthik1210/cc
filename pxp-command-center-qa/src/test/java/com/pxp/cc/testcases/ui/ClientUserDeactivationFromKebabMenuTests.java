//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
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

public class ClientUserDeactivationFromKebabMenuTests extends CommandCenterGenericTest {
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

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(testData.getProperty("base.url"), testData.getProperty("user.name"),
				testData.getProperty("user.password"));
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
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.addNewSystem("SystemDemo");
		generalSettingsPage.clickSearchIcon();
		generalSettingsPage.enterSearchText("SystemDemo");
		generalSettingsPage.clickFirstInAutoSuggestion();
		CCUtils.pressEnterKey();
		addOrgNodePage.clickOnManageClient();
		addOrgNodePage.clickOnExpandArrow();
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
		logStep("Create provider");
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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user confirmation popup")
	public void testDeactivateConfirmationPopupForClientUser() throws Exception {
		createActiveClientUser();
		clientUserPage.verifyClientUsersPageTitle();
		logStep("Click on search button");
		searchOptionsPage = clientUserPage.clickOnSearchOptionPage();
		logStep("Search and select active user");
		clientUserPage.clickOnActiveCheckbox();
		searchOptionsPage.clickOnSearchButton();
		clientUserPage.verifyClientUsersPageTitle();
		clientUserPage.selectClientUser();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage();
		String userName = clientUserDetailsPage.getUserName();
		clientUserDetailsPage.clickOnKebabMenuButton();
		deactivateUserConfirmationPage = clientUserDetailsPage.clickOnDeactivateUserButton();
		logStep("Verify deactivate user confirmation popup");
		deactivateUserConfirmationPage.verifyConfirmationTextForSingleUser(userName);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user button is displayed in kebab menu for active user")
	public void testDeactivateUserOptionIsPresentForActiveClientUser() throws Exception {
		createActiveClientUser();
		clientUserPage.verifyClientUsersPageTitle();
		logStep("Click on search button");
		searchOptionsPage = clientUserPage.clickOnSearchOptionPage();
		logStep("Search and select active user");
		clientUserPage.clickOnActiveCheckbox();
		searchOptionsPage.clickOnSearchButton();
		clientUserPage.verifyClientUsersPageTitle();
		clientUserPage.selectClientUser();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage();
		clientUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify deactivate button is displayed in kebab menu for active user");
		clientUserDetailsPage.verifyDeactivateUserButtonIsDisplayed();
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user button is displayed in kebab menu for no roles assigned user")
	public void testDeactivateUserOptionIsPresentForNoRolesAssignedClientUser() throws Exception {
		createActiveClientUser();
		clientUserPage.verifyClientUsersPageTitle();
		logStep("Click on search button");
		searchOptionsPage = clientUserPage.clickOnSearchOptionPage();
		logStep("Search and select no roles assigned user");
		clientUserPage.clickOnNoRolesAssignedCheckbox();
		searchOptionsPage.clickOnSearchButton();
		clientUserPage.verifyClientUsersPageTitle();
		clientUserPage.selectClientUser();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage();
		clientUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify deactivate user button is displayed in kebab menu for no roles assigned user");
		clientUserDetailsPage.verifyDeactivateUserButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify activate user button is displayed in kebab menu for inactive user")
	public void testDeactivateUserOptionIsNotPresentForInactiveClientUser() throws Exception {
		createInactiveClientUser();
		clientUserPage.verifyClientUsersPageTitle();
		logStep("Click on search button");
		searchOptionsPage = clientUserPage.clickOnSearchOptionPage();
		logStep("Search and select inactive user");
		clientUserPage.clickOnInactiveCheckbox();
		searchOptionsPage.clickOnSearchButton();
		clientUserPage.verifyClientUsersPageTitle();
		clientUserPage.selectClientUser();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage();
		clientUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify deactivate user button is not displayed in kebab menu for inactive user");
		clientUserDetailsPage.verifyActivateUserButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user button is displayed but not enabled in kebab menu for locked user")
	public void testDeactivateUserOptionIsNotPresentForLockedClientUser() throws Exception {
		createActiveClientUser();
		clientUserPage.verifyClientUsersPageTitle();
		logStep("Click on search button");
		searchOptionsPage = clientUserPage.clickOnSearchOptionPage();
		logStep("Search and select active user");
		clientUserPage.clickOnActiveCheckbox();
		searchOptionsPage.clickOnSearchButton();
		clientUserPage.verifyClientUsersPageTitle();
		clientUserPage.selectClientUser();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage();
		clientUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify lock button is displayed in kebab menu for active user");
		clientUserDetailsPage.verifyLockUserButtonIsDisplayed();
		logStep("Click on the lock user button in the kebab menu");
		clientUserDetailsPage.clickOnLockUserButton();
		commonMethods.verifyToasterMessage("Success! User status has been successfully locked");
		clientUserDetailsPage.verifyUserStatusAfterClickingOnLockUserButton();
		clientUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify deactivate user button is displayed but not enabled in kebab menu for locked user");
		clientUserDetailsPage.verifyDeactivateUserButtonIsNotDisplayed();
		clientUserDetailsPage.verifyDeactivateUserButtonIsNotEnabled();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify cancel and cross button functionality for deactivate user button popup")
	public void testCancelAndCrossIconFunctionalityOnDeactivateClientUserButton() throws Exception {
		createActiveClientUser();
		clientUserPage.verifyClientUsersPageTitle();
		logStep("Click on search button");
		searchOptionsPage = clientUserPage.clickOnSearchOptionPage();
		logStep("Search and select active user");
		clientUserPage.clickOnActiveCheckbox();
		searchOptionsPage.clickOnSearchButton();
		clientUserPage.verifyClientUsersPageTitle();
		clientUserPage.selectClientUser();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage();
		clientUserDetailsPage.clickOnKebabMenuButton();
		deactivateUserConfirmationPage = clientUserDetailsPage.clickOnDeactivateUserButton();
		logStep("Verify after clicking on cancel button, user redirected to client user details page");
		deactivateUserConfirmationPage.clickOnCancelButton();
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		clientUserDetailsPage.clickOnKebabMenuButton();
		clientUserDetailsPage.clickOnDeactivateUserButton();
		logStep("Verify after clicking on cross icon, user redirected to client user details page");
		deactivateUserConfirmationPage.clickOnCloseWindow();
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user functionality")
	public void testClientUserDeactivateFunctionality() throws Exception {
		createActiveClientUser();
		clientUserPage.verifyClientUsersPageTitle();
		logStep("Click on search button");
		searchOptionsPage = clientUserPage.clickOnSearchOptionPage();
		logStep("Search and select active user");
		clientUserPage.clickOnActiveCheckbox();
		searchOptionsPage.clickOnSearchButton();
		clientUserPage.verifyClientUsersPageTitle();
		clientUserPage.selectClientUser();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage();
		clientUserDetailsPage.clickOnKebabMenuButton();
		deactivateUserConfirmationPage = clientUserDetailsPage.clickOnDeactivateUserButton();
		logStep("Verify deactivate user confirmation popup");
		String userName = clientUserDetailsPage.getUserName();
		deactivateUserConfirmationPage.verifyConfirmationTextForSingleUser(userName);
		deactivateUserConfirmationPage.clickOnYesDeactivateButton();
		logStep("verify success toaster message");
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Success! User status has been successfully deactivated");
		logStep("Verify client user status is changed from active to inactive on client user details page");
		clientUserDetailsPage.checkInactiveStatus();
	}
}
