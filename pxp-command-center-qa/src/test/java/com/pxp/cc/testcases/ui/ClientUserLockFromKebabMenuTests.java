// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.ClientUserDetailsPage;
import com.pxp.cc.objectmaps.ClientUsersPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.SearchOptionsPage;
import com.pxp.cc.util.CommonMethods;

public class ClientUserLockFromKebabMenuTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public ClientUsersPage clientUsersPage;
	public SearchOptionsPage searchOptionsPage;
	public ClientUserDetailsPage clientUserDetailsPage;
	public List<String> userStatusList = null;
	public CommonMethods commonMethods;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Select client users tab in command center users page");
		clientUsersPage = commandCenterUsersPage.clickOnClientUsersTab();
		clientUsersPage.verifyClientUsersPageTitle();
		commonMethods = new CommonMethods(driver);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to lock and unlock active user and Verify lock button is not displayed in kebab menu for locked user")
	public void testClientUserLockButtonIsPresentForActiveUserAndNotPresentForLockedUser() throws Exception {
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		logStep("Search and select active user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = clientUsersPage.getClientUserStatus();
		clientUsersPage.selectUser();
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage(userStatus);
		clientUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify lock button is displayed in kebab menu for active user");
		clientUserDetailsPage.verifyLockUserButtonIsDisplayed();
		logStep("Click on the lock user button in the kebab menu");
		clientUserDetailsPage.clickOnLockUserButton();
		commonMethods.verifyToasterMessage("Success! User status has been successfully locked");
		clientUserDetailsPage.verifyUserStatusAfterClickingOnLockUserButton();
		logStep("Verify lock button is not displayed in kebab menu for locked user");
		clientUserDetailsPage.clickOnKebabMenuButton();
		clientUserDetailsPage.verifyLockUserButtonIsNotDisplayed();
		logStep("Click on unlock user button in kebab menu");
		clientUserDetailsPage.clickOnUnlockUserButton();
		logStep("verify success toaster message");
		commonMethods.verifyToasterMessage("Success! User status has been successfully activated");
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to lock and unlock no roles assigned user")
	public void testClientUserLockButtonIsPresentForNoRolesAssignedUser() throws Exception {
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		logStep("Search and select no roles assigned user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.no.roles.assigned"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = clientUsersPage.getClientUserStatus();
		clientUsersPage.selectUser();
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage(userStatus);
		clientUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify lock button is displayed in kebab menu for no roles assigned user");
		clientUserDetailsPage.verifyLockUserButtonIsDisplayed();
		logStep("Click on the lock user button in the kebab menu");
		clientUserDetailsPage.clickOnLockUserButton();
		commonMethods.verifyToasterMessage("Success! User status has been successfully locked");
		clientUserDetailsPage.verifyUserStatusAfterClickingOnLockUserButton();
		logStep("Click on unlock user button in kebab menu");
		clientUserDetailsPage.clickOnUnlockUserButton();
		logStep("verify success toaster message");
		commonMethods.verifyToasterMessage("Success! User status has been successfully activated");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify lock button is not displayed in kebab menu for inactive user")
	public void testClientUserLockButtonIsNotPresentForInactiveUser() throws Exception {
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		logStep("Search and select inactive user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.inactive"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = clientUsersPage.getClientUserStatus();
		clientUsersPage.selectUser();
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage(userStatus);
		clientUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify lock button is not displayed in kebab menu for inactive user");
		clientUserDetailsPage.verifyLockUserButtonIsNotDisplayed();
	}
}
