// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.ClientUserDetailsPage;
import com.pxp.cc.objectmaps.ClientUsersPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.LockUserConfirmationPage;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.objectmaps.PasswordResetPage;
import com.pxp.cc.objectmaps.SearchOptionsPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class ClientUserLockFromKebabMenuTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public ClientUsersPage clientUsersPage;
	public SearchOptionsPage searchOptionsPage;
	public ClientUserDetailsPage clientUserDetailsPage;
	public List<String> userStatusList = null;
	public CommonMethods commonMethods;
	SecureRandom random = new SecureRandom();
	String autogeneratePassword = PasswordResetPage.genPassword(random);
	public PasswordResetPage resetPage;
	public LockUserConfirmationPage lockUserConfirmationPage;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Select client users tab in command center users page");
		clientUsersPage = commandCenterUsersPage.clickOnClientUsersTab();
		clientUsersPage.verifyClientUsersPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify lock button is displayed in kebab menu for active user")
	public void testClientUserLockButtonIsPresentForActiveUser() throws Exception {
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
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify lock button is displayed in kebab menu for no roles assigned user")
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
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify lock button is not displayed in kebab menu for inactive user")
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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify lock button is not displayed in kebab menu for locked user")
	public void testClientUserLockButtonIsNotPresentForAlreadyLockedUser() throws Exception {
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		logStep("Search and select locked user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.lockedout"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = clientUsersPage.getClientUserStatus();
		clientUsersPage.selectUser();
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage(userStatus);
		clientUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify lock button is not displayed in kebab menu for locked user");
		clientUserDetailsPage.verifyLockUserButtonIsNotDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify lock user button")
	public void testClientUserLockButton() throws Exception {
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
		log("Click on the lock user button in the kebab menu");
		clientUserDetailsPage.clickOnLockUserButton();
		commonMethods.verifyToasterMessage("Success! User status has been successfully locked");
		commonMethods.closeToasterMessage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify unlock user functionality")
	public void testClientUserUnlockFunctionality() throws Exception {
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
		log("Click on the lock user button in the kebab menu");
		clientUserDetailsPage.clickOnLockUserButton();
		logStep("Verify success toaster message");
		commonMethods.verifyToasterMessage("Success! User status has been successfully locked");
		commonMethods.closeToasterMessage();
		logStep("Verify Unlock button is displayed and click on unlock button");
		clientUserDetailsPage.verifyUnlockButtonIsDispalyed();
		clientUserDetailsPage.clickOnUnlockButton();
		logStep("Verify success toaster message");
		commonMethods.verifyToasterMessage("Success! User status has been successfully activated");
		commonMethods.closeToasterMessage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to set new password using password reset link found in the email for locked out user")
	public void testClientUserLockedOutFunctionality() throws Exception {
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
		logStep("Click on kebab menu button");
		clientUserDetailsPage.clickOnKebabMenuButton();
		log("Click on the lock user button in the kebab menu");
		clientUserDetailsPage.clickOnLockUserButton();
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Lockedout email sent to users successfully");
		logStep("Login to CC portal");
		LoginPage commandcenterloginpage = new LoginPage(driver, ccuser.getUrl());
		clientDirectoryPage = login(ccuser.getUrl(), commonMethods.getClientUserName(), ccuser.getPassword());
		logStep("Verify an error message is displayed on the login page when the user tries to log in using the locked user credentials");
		CCUtils.assertEquals(commandcenterloginpage.getLockOutErrMsg().trim(),
				commandcenterloginpage.lockoutErrorMsg.trim());
		logStep("Verify user able to set new password using password reset link found in the email");
		resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		resetPage.urlInEmail(ccuser.getResetEmail(), autogeneratePassword);
		CCUtils.assertEquals(resetPage.getNewPaswwordCreatedMsg(), "Success! Your password has been reset.");
	}

}
