// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.InternalUserDetailsPage;
import com.pxp.cc.objectmaps.InternalUsersPage;
import com.pxp.cc.objectmaps.PasswordResetPage;
import com.pxp.cc.objectmaps.SearchOptionsPage;
import com.pxp.cc.util.CommonMethods;

public class InternalUserLockFromKebabMenuTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public InternalUsersPage internalUsersPage;
	public SearchOptionsPage searchOptionsPage;
	public InternalUserDetailsPage internalUserDetailsPage;
	public List<String> userStatusList = null;
	public List<String> enrollmentStatusList = null;
	public CommonMethods commonMethods;
	SecureRandom random = new SecureRandom();
	String autogeneratePassword = PasswordResetPage.genPassword(random);
	public PasswordResetPage resetPage;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		commonMethods = new CommonMethods(driver);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to lock and unlock active internal user")
	public void testInternalUserLockButtonIsPresentForActiveUser() throws Exception {
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		logStep("Search and select active user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = internalUsersPage.getInternalUserStatus();
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.verifyInternalUserDetailsPageTitle();
		logStep("Verify selected internal user status is same on internal user details page");
		internalUserDetailsPage.verifySelectedUserStatusIsSameOnInternalUserDetailsPage(userStatus);
		logStep("Click on kebab menu");
		internalUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify lock button is displayed in kebab menu for active user");
		internalUserDetailsPage.verifyLockUserButtonIsDisplayed();
		internalUserDetailsPage.clickOnLockUserButton();
		logStep("verify success toaster message");
		commonMethods.verifyToasterMessage("Success! User status has been successfully locked");
		logStep("Verify user status after clicking on lock user button");
		internalUserDetailsPage.verifyUserStatusAfterClickingOnLockUserButton();
		logStep("Click on unlock user button in kebab menu");
		internalUserDetailsPage.clickOnUnlockUserButton();
		logStep("verify success toaster message");
		commonMethods.verifyToasterMessage("Success! User status has been successfully activated");

	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to lock and unlock no roles assigned user")
	public void testInternalUserLockButtonIsPresentForNoRolesAssignedUser() throws Exception {
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		logStep("Search and select no roles assigned user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.no.roles.assigned"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = internalUsersPage.getInternalUserStatus();
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.verifyInternalUserDetailsPageTitle();
		logStep("Verify selected internal user status is same on internal user details page");
		internalUserDetailsPage.verifySelectedUserStatusIsSameOnInternalUserDetailsPage(userStatus);
		internalUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify lock button is displayed in kebab menu for no roles assigned user");
		internalUserDetailsPage.verifyLockUserButtonIsDisplayed();
		logStep("Click on lock user button");
		internalUserDetailsPage.clickOnLockUserButton();
		logStep("verify success toaster message");
		commonMethods.verifyToasterMessage("Success! User status has been successfully locked");
		logStep("Verify user status after clicking on lock user button");
		internalUserDetailsPage.verifyUserStatusAfterClickingOnLockUserButton();
		logStep("Click on unlock user button in kebab menu");
		internalUserDetailsPage.clickOnUnlockUserButton();
		logStep("verify success toaster message");
		commonMethods.verifyToasterMessage("Success! User status has been successfully activated");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify lock button is not displayed in kebab menu for inactive user")
	public void testInternalUserLockButtonIsNotPresentForInactiveUser() throws Exception {
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		logStep("Search and select inactive user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.inactive"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = internalUsersPage.getInternalUserStatus();
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.verifyInternalUserDetailsPageTitle();
		logStep("Verify selected internal user status is same on internal user details page");
		internalUserDetailsPage.verifySelectedUserStatusIsSameOnInternalUserDetailsPage(userStatus);
		internalUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify lock button is displayed in kebab menu for inactive user");
		internalUserDetailsPage.verifyLockUsertButtonIsNotDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify lock button is not displayed in kebab menu for locked user")
	public void testInternalUserLockButtonIsNotPresentForAlreadyLockedUser() throws Exception {
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		logStep("Search and select locked user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.lockedout"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = internalUsersPage.getInternalUserStatus();
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.verifyInternalUserDetailsPageTitle();
		logStep("Verify selected internal user status is same on internal user details page");
		internalUserDetailsPage.verifySelectedUserStatusIsSameOnInternalUserDetailsPage(userStatus);
		internalUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify lock button is displayed in kebab menu for inactive user");
		internalUserDetailsPage.verifyLockUsertButtonIsNotDisplayed();
	}

}
