// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.DeactivateUserConfirmationPage;
import com.pxp.cc.objectmaps.VarResellerUserDetailsPage;
import com.pxp.cc.objectmaps.VarResellerUsersPage;
import com.pxp.cc.objectmaps.SearchOptionsPage;
import com.pxp.cc.util.CommonMethods;

public class VarResellerUserDeactivationFromKebabMenuTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public VarResellerUserDetailsPage VarResellerUserDetailsPage;
	public VarResellerUsersPage VarResellerUsersPage;
	public SearchOptionsPage searchOptionsPage;
	public DeactivateUserConfirmationPage deactivateUserConfirmationPage;
	public List<String> userStatusList = null;
	public CommonMethods commonMethods;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		commandCenterUsersPage = clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		VarResellerUsersPage = new VarResellerUsersPage(driver);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user confirmation popup")
	public void testDeactivateConfirmationPopupForVarResellerUser() throws Exception {
		commandCenterUsersPage.clickOnVarResellerUsersTab();
		searchOptionsPage = VarResellerUsersPage.clickOnSearchOptionPage();
		logStep("Search and select active user");
		VarResellerUsersPage.clickOnActiveCheckbox();
		searchOptionsPage.clickOnSearchButton();
		VarResellerUsersPage.verifyVarResellerUsersPageTitle();
		VarResellerUsersPage.selectVarResellerUser();
		logStep("Verify selected VarReseller user status is same on VarReseller user details page");
		VarResellerUserDetailsPage = new VarResellerUserDetailsPage(driver);
		VarResellerUserDetailsPage.verifyVarResellerUserDetailsPageTitle();
		VarResellerUserDetailsPage.verifySelectedUserStatusIsSameOnVarResellerUserDetailsPage();
		String userName = VarResellerUserDetailsPage.getUserName();
		VarResellerUserDetailsPage.clickOnKebabMenuButton();
		deactivateUserConfirmationPage = VarResellerUserDetailsPage.clickOnDeactivateUserButton();
		logStep("Verify deactivate user confirmation popup");
		deactivateUserConfirmationPage.verifyConfirmationTextForSingleUser(userName);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user button is displayed in kebab menu for active user")
	public void testDeactivateUserOptionIsPresentForActiveVarResellerUser() throws Exception {
		commandCenterUsersPage.clickOnVarResellerUsersTab();
		searchOptionsPage = VarResellerUsersPage.clickOnSearchOptionPage();
		logStep("Search and select active user");
		VarResellerUsersPage.clickOnActiveCheckbox();
		searchOptionsPage.clickOnSearchButton();
		VarResellerUsersPage.verifyVarResellerUsersPageTitle();
		VarResellerUsersPage.selectVarResellerUser();
		logStep("Verify selected VarReseller user status is same on VarReseller user details page");
		VarResellerUserDetailsPage = new VarResellerUserDetailsPage(driver);
		VarResellerUserDetailsPage.verifyVarResellerUserDetailsPageTitle();
		VarResellerUserDetailsPage.verifySelectedUserStatusIsSameOnVarResellerUserDetailsPage();
		VarResellerUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify deactivate button is displayed in kebab menu for active VarReseller user");
		VarResellerUserDetailsPage.verifyDeactivateUserButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user button is displayed in kebab menu for no roles assigned user")
	public void testDeactivateUserOptionIsPresentForNoRolesAssignedVarResellerUser() throws Exception {
		commandCenterUsersPage.clickOnVarResellerUsersTab();
		searchOptionsPage = VarResellerUsersPage.clickOnSearchOptionPage();
		logStep("Search and select no roles assigned user");
		VarResellerUsersPage.clickOnNoRolesAssignedCheckbox();
		searchOptionsPage.clickOnSearchButton();
		VarResellerUsersPage.verifyVarResellerUsersPageTitle();
		VarResellerUsersPage.selectVarResellerUser();
		logStep("Verify selected VarReseller user status is same on VarReseller user details page");
		VarResellerUserDetailsPage = new VarResellerUserDetailsPage(driver);
		VarResellerUserDetailsPage.verifyVarResellerUserDetailsPageTitle();
		VarResellerUserDetailsPage.verifySelectedUserStatusIsSameOnVarResellerUserDetailsPage();
		VarResellerUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify deactivate user button is displayed in kebab menu for no roles assigned VarReseller user");
		VarResellerUserDetailsPage.verifyDeactivateUserButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user button is not displayed in kebab menu for inactive user")
	public void testDeactivateUserOptionIsNotPresentForInactiveVarResellerUser() throws Exception {
		commandCenterUsersPage.clickOnVarResellerUsersTab();
		searchOptionsPage = VarResellerUsersPage.clickOnSearchOptionPage();
		logStep("Search and select inactive user");
		VarResellerUsersPage.clickOnInactiveCheckbox();
		searchOptionsPage.clickOnSearchButton();
		VarResellerUsersPage.verifyVarResellerUsersPageTitle();
		VarResellerUsersPage.selectVarResellerUser();
		logStep("Verify selected VarReseller user status is same on VarReseller user details page");
		VarResellerUserDetailsPage = new VarResellerUserDetailsPage(driver);
		VarResellerUserDetailsPage.verifyVarResellerUserDetailsPageTitle();
		VarResellerUserDetailsPage.verifySelectedUserStatusIsSameOnVarResellerUserDetailsPage();
		VarResellerUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify deactivate user button is not displayed in kebab menu for inactive VarReseller user");
		VarResellerUserDetailsPage.verifyActivateUserButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user button is not displayed in kebab menu for locked user")
	public void testDeactivateUserOptionIsNotPresentForLockedVarResellerUser() throws Exception {
		commandCenterUsersPage.clickOnVarResellerUsersTab();
		searchOptionsPage = VarResellerUsersPage.clickOnSearchOptionPage();
		logStep("Search and select locked user");
		VarResellerUsersPage.clickOnLockedCheckbox();
		searchOptionsPage.clickOnSearchButton();
		VarResellerUsersPage.verifyVarResellerUsersPageTitle();
		VarResellerUsersPage.selectVarResellerUser();
		logStep("Verify selected VarReseller user status is same on VarReseller user details page");
		VarResellerUserDetailsPage = new VarResellerUserDetailsPage(driver);
		VarResellerUserDetailsPage.verifyVarResellerUserDetailsPageTitle();
		VarResellerUserDetailsPage.verifySelectedUserStatusIsSameOnVarResellerUserDetailsPage();
		VarResellerUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify deactivate user button is not displayed in kebab menu for locked VarReseller user");
		VarResellerUserDetailsPage.verifyDeactivateUsertButtonIsNotDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify cancel and cross button functionality for deactivate user button popup")
	public void testCancelAndCrossIconFunctionalityOnDeactivateVarResellerUserButton() throws Exception {
		commandCenterUsersPage.clickOnVarResellerUsersTab();
		searchOptionsPage = VarResellerUsersPage.clickOnSearchOptionPage();
		logStep("Search and select active user");
		VarResellerUsersPage.clickOnActiveCheckbox();
		searchOptionsPage.clickOnSearchButton();
		VarResellerUsersPage.verifyVarResellerUsersPageTitle();
		VarResellerUsersPage.selectVarResellerUser();
		logStep("Verify selected VarReseller user status is same on VarReseller user details page");
		VarResellerUserDetailsPage = new VarResellerUserDetailsPage(driver);
		VarResellerUserDetailsPage.verifyVarResellerUserDetailsPageTitle();
		VarResellerUserDetailsPage.verifySelectedUserStatusIsSameOnVarResellerUserDetailsPage();
		VarResellerUserDetailsPage.clickOnKebabMenuButton();
		deactivateUserConfirmationPage = VarResellerUserDetailsPage.clickOnDeactivateUserButton();
		logStep("Verify after clicking on cancel button, user redirected to VarReseller user details page");
		deactivateUserConfirmationPage.clickOnCancelButton();
		VarResellerUserDetailsPage.verifyVarResellerUserDetailsPageTitle();
		VarResellerUserDetailsPage.clickOnKebabMenuButton();
		VarResellerUserDetailsPage.clickOnDeactivateUserButton();
		logStep("Verify after clicking on cross icon, user redirected to VarReseller user details page");
		deactivateUserConfirmationPage.clickOnCloseWindow();
		VarResellerUserDetailsPage.verifyVarResellerUserDetailsPageTitle();
	}

	@Test(enabled = true, groups = {"smoke"}, retryAnalyzer = RetryAnalyzer.class, description = "Verify deactivate user functionality")
	public void testVarResellerUserDeactivateFunctionality() throws Exception {
		commandCenterUsersPage.clickOnVarResellerUsersTab();
		searchOptionsPage = VarResellerUsersPage.clickOnSearchOptionPage();
		logStep("Search and select active user");
		VarResellerUsersPage.clickOnActiveCheckbox();
		searchOptionsPage.clickOnSearchButton();
		VarResellerUsersPage.verifyVarResellerUsersPageTitle();
		VarResellerUsersPage.selectVarResellerUser();
		logStep("Verify selected VarReseller user status is same on VarReseller user details page");
		VarResellerUserDetailsPage = new VarResellerUserDetailsPage(driver);
		VarResellerUserDetailsPage.verifyVarResellerUserDetailsPageTitle();
		String userName = VarResellerUserDetailsPage.getUserName();
		VarResellerUserDetailsPage.clickOnKebabMenuButton();
		deactivateUserConfirmationPage = VarResellerUserDetailsPage.clickOnDeactivateUserButton();
		logStep("Verify deactivate user confirmation popup");
		deactivateUserConfirmationPage.verifyConfirmationTextForSingleUser(userName);
		deactivateUserConfirmationPage.clickOnYesDeactivateButton();
		logStep("verify success toaster message");
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Success! User status has been successfully deactivated");
	}
}
