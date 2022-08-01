// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.DeactivateUserConfirmationPage;
import com.pxp.cc.objectmaps.InternalUserDetailsPage;
import com.pxp.cc.objectmaps.InternalUsersPage;
import com.pxp.cc.objectmaps.SearchOptionsPage;
import com.pxp.cc.util.CommonMethods;

public class InternalUserDeactivationFromKebabMenuTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public InternalUserDetailsPage internalUserDetailsPage;
	public InternalUsersPage internalUsersPage;
	public SearchOptionsPage searchOptionsPage;
	public DeactivateUserConfirmationPage deactivateUserConfirmationPage;
	public List<String> userStatusList = null;
	public CommonMethods commonMethods;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		commandCenterUsersPage = clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user confirmation popup")
	public void testDeactivateConfirmationPopupForInternalUser() throws Exception {
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		logStep("Search and select active user");
		internalUsersPage.clickOnActiveCheckbox();
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.verifyInternalUsersPageTitle();
		internalUsersPage.selectInternalUser();
		logStep("Verify selected internal user status is same on internal user details page");
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.verifyInternalUserDetailsPageTitle();
		internalUserDetailsPage.verifySelectedUserStatusIsSameOnInternalUserDetailsPage();
		String userName = internalUserDetailsPage.getUserName();
		internalUserDetailsPage.clickOnKebabMenuButton();
		deactivateUserConfirmationPage = internalUserDetailsPage.clickOnDeactivateUserButton();
		logStep("Verify deactivate user confirmation popup");
		deactivateUserConfirmationPage.verifyConfirmationTextForSingleUser(userName);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user button is displayed in kebab menu for active user")
	public void testDeactivateUserOptionIsPresentForActiveInternalUser() throws Exception {
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		logStep("Search and select active user");
		internalUsersPage.clickOnActiveCheckbox();
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.verifyInternalUsersPageTitle();
		internalUsersPage.selectInternalUser();
		logStep("Verify selected internal user status is same on internal user details page");
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.verifyInternalUserDetailsPageTitle();
		internalUserDetailsPage.verifySelectedUserStatusIsSameOnInternalUserDetailsPage();
		internalUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify deactivate button is displayed in kebab menu for active user");
		internalUserDetailsPage.verifyDeactivateUserButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user button is displayed in kebab menu for no roles assigned user")
	public void testDeactivateUserOptionIsPresentForNoRolesAssignedInternalUser() throws Exception {
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		logStep("Search and select no roles assigned user");
		internalUsersPage.clickOnNoRolesAssignedCheckbox();
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.verifyInternalUsersPageTitle();
		internalUsersPage.selectInternalUser();
		logStep("Verify selected internal user status is same on internal user details page");
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.verifyInternalUserDetailsPageTitle();
		internalUserDetailsPage.verifySelectedUserStatusIsSameOnInternalUserDetailsPage();
		internalUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify deactivate user button is displayed in kebab menu for no roles assigned user");
		internalUserDetailsPage.verifyDeactivateUserButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify activate user button is displayed in kebab menu for inactive user")
	public void testDeactivateUserOptionIsNotPresentForInactiveInternalUser() throws Exception {
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		logStep("Search and select inactive user");
		internalUsersPage.clickOnInactiveCheckbox();
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.verifyInternalUsersPageTitle();
		internalUsersPage.selectInternalUser();
		logStep("Verify selected internal user status is same on internal user details page");
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.verifyInternalUserDetailsPageTitle();
		internalUserDetailsPage.verifySelectedUserStatusIsSameOnInternalUserDetailsPage();
		internalUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify deactivate user button is not displayed in kebab menu for inactive user");
		internalUserDetailsPage.verifyActivateUserButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user button is displayed but not enabled in kebab menu for locked user")
	public void testDeactivateUserOptionIsNotPresentForLockedInternalUser() throws Exception {
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		logStep("Search and select locked user");
		internalUsersPage.clickOnLockedCheckbox();
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.verifyInternalUsersPageTitle();
		internalUsersPage.selectInternalUser();
		logStep("Verify selected internal user status is same on internal user details page");
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.verifyInternalUserDetailsPageTitle();
		internalUserDetailsPage.verifySelectedUserStatusIsSameOnInternalUserDetailsPage();
		internalUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify deactivate user button is displayed but not enabled in kebab menu for locked user");
		internalUserDetailsPage.verifyDeactivateUsertButtonIsNotDisplayed();
		internalUserDetailsPage.verifyDeactivateUsertButtonIsNotEnabled();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify cancel and cross button functionality for deactivate user button popup")
	public void testCancelAndCrossIconFunctionalityOnDeactivateInternalUserButton() throws Exception {
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		logStep("Search and select active user");
		internalUsersPage.clickOnActiveCheckbox();
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.verifyInternalUsersPageTitle();
		internalUsersPage.selectInternalUser();
		logStep("Verify selected internal user status is same on internal user details page");
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.verifyInternalUserDetailsPageTitle();
		internalUserDetailsPage.verifySelectedUserStatusIsSameOnInternalUserDetailsPage();
		internalUserDetailsPage.clickOnKebabMenuButton();
		deactivateUserConfirmationPage = internalUserDetailsPage.clickOnDeactivateUserButton();
		logStep("Verify after clicking on cancel button, user redirected to internal user details page");
		deactivateUserConfirmationPage.clickOnCancelButton();
		internalUserDetailsPage.verifyInternalUserDetailsPageTitle();
		internalUserDetailsPage.clickOnKebabMenuButton();
		internalUserDetailsPage.clickOnDeactivateUserButton();
		logStep("Verify after clicking on cross icon, user redirected to internal user details page");
		deactivateUserConfirmationPage.clickOnCloseWindow();
		internalUserDetailsPage.verifyInternalUserDetailsPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user functionality")
	public void testInternalUserDeactivateFunctionality() throws Exception {
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		logStep("Search and select active user");
		internalUsersPage.clickOnActiveCheckbox();
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.verifyInternalUsersPageTitle();
		internalUsersPage.selectInternalUser();
		logStep("Verify selected internal user status is same on internal user details page");
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.verifyInternalUserDetailsPageTitle();
		internalUserDetailsPage.verifySelectedUserStatusIsSameOnInternalUserDetailsPage();
		String userName = internalUserDetailsPage.getUserName();
		internalUserDetailsPage.clickOnKebabMenuButton();
		deactivateUserConfirmationPage = internalUserDetailsPage.clickOnDeactivateUserButton();
		logStep("Verify deactivate user confirmation popup");
		deactivateUserConfirmationPage.verifyConfirmationTextForSingleUser(userName);
		deactivateUserConfirmationPage.clickOnYesDeactivateButton();
		logStep("verify success toaster message");
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Success! User status has been successfully deactivated");
		logStep("Verify internal user status is changed from active to inactive on internal user details page");
		internalUserDetailsPage.checkInactiveStatus();
	}
}
