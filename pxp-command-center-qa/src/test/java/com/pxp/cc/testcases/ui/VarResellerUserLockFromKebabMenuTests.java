// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.PasswordResetPage;
import com.pxp.cc.objectmaps.SearchOptionsPage;
import com.pxp.cc.objectmaps.VarResellerUsersPage;
import com.pxp.cc.objectmaps.LockUserConfirmationPage;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.objectmaps.VarResellerUserDetailsPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class VarResellerUserLockFromKebabMenuTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public VarResellerUsersPage varResellerUsersPage;
	public VarResellerUserDetailsPage varResellerUserDetailsPage;
	public LockUserConfirmationPage lockUserConfirmationPage;
	public CommonMethods commonMethods;
	public PasswordResetPage resetPage;
	SecureRandom random = new SecureRandom();
	String autogeneratePassword = PasswordResetPage.genPassword(random);
	public SearchOptionsPage searchOptionsPage;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Click on command center users icon and verify internal users tab is by default selected");
		commandCenterUsersPage = clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		logStep("Click on VAR/Reseller tab on the command center users page");
		varResellerUsersPage = commandCenterUsersPage.clickOnVarResellerUsersTab();
		logStep("Verify VAR/Reseller users page title");
		varResellerUsersPage.verifyVarResellerUsersPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify lock button is displayed in kebab menu for active user")
	public void testVarResellerUserLockButtonIsPresentForActiveUser() throws Exception {
		logStep("Search and select active user");
		searchOptionsPage = varResellerUsersPage.clickOnSearchOptionsButton();
		List<String> userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickToSearchButton();
		String userStatus = varResellerUsersPage.getVarResellerUserStatus();
		varResellerUsersPage.selectUser();
		varResellerUserDetailsPage = new VarResellerUserDetailsPage(driver);
		varResellerUserDetailsPage.verifyVarResellerUserDetailsPageTitle();
		logStep("Verify selected VarReseller user status is same on VarReseller user details page");
		varResellerUserDetailsPage.verifySelectedUserStatusIsSameOnVarResellerUserDetailsPage(userStatus);
		varResellerUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify lock button is displayed in kebab menu for active user");
		varResellerUserDetailsPage.verifyLockUserButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify lock button is displayed in kebab menu for no roles assigned user")
	public void testVarResellerUserLockButtonIsPresentForNoRolesAssignedUser() throws Exception {
		logStep("Search and select no roles assigned user");
		searchOptionsPage = varResellerUsersPage.clickOnSearchOptionsButton();
		List<String> userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.no.roles.assigned"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickToSearchButton();
		String userStatus = varResellerUsersPage.getVarResellerUserStatus();
		varResellerUsersPage.selectUser();
		varResellerUserDetailsPage = new VarResellerUserDetailsPage(driver);
		varResellerUserDetailsPage.verifyVarResellerUserDetailsPageTitle();
		logStep("Verify selected VarReseller user status is same on VarReseller user details page");
		varResellerUserDetailsPage.verifySelectedUserStatusIsSameOnVarResellerUserDetailsPage(userStatus);
		varResellerUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify lock button is displayed in kebab menu for no roles assigned user");
		varResellerUserDetailsPage.verifyLockUserButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify lock button is not displayed in kebab menu for inactive user")
	public void testVarResellerUserLockButtonIsNotPresentForInactiveUser() throws Exception {
		logStep("Search and select inactive user");
		searchOptionsPage = varResellerUsersPage.clickOnSearchOptionsButton();
		List<String> userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.inactive"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickToSearchButton();
		String userStatus = varResellerUsersPage.getVarResellerUserStatus();
		varResellerUsersPage.selectUser();
		varResellerUserDetailsPage = new VarResellerUserDetailsPage(driver);
		varResellerUserDetailsPage.verifyVarResellerUserDetailsPageTitle();
		logStep("Verify selected VarReseller user status is same on VarReseller user details page");
		varResellerUserDetailsPage.verifySelectedUserStatusIsSameOnVarResellerUserDetailsPage(userStatus);
		varResellerUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify lock button is not displayed in kebab menu for inactive user");
		varResellerUserDetailsPage.verifyLockUserButtonIsNotDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify lock button is not displayed in kebab menu for locked user")
	public void testVarResellerUserLockButtonIsNotPresentForAlreadyLockedUser() throws Exception {
		logStep("Search and select locked user");
		searchOptionsPage = varResellerUsersPage.clickOnSearchOptionsButton();
		List<String> userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.lockedout"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickToSearchButton();
		String userStatus = varResellerUsersPage.getVarResellerUserStatus();
		varResellerUsersPage.selectUser();
		varResellerUserDetailsPage = new VarResellerUserDetailsPage(driver);
		varResellerUserDetailsPage.verifyVarResellerUserDetailsPageTitle();
		logStep("Verify selected VarReseller user status is same on VarReseller user details page");
		varResellerUserDetailsPage.verifySelectedUserStatusIsSameOnVarResellerUserDetailsPage(userStatus);
		varResellerUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify lock button is not displayed in kebab menu for locked user");
		varResellerUserDetailsPage.verifyLockUserButtonIsNotDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify lock user button")
	public void testVarResellerUserLockButton() throws Exception {
		logStep("Search and select active user");
		searchOptionsPage = varResellerUsersPage.clickOnSearchOptionsButton();
		List<String> userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickToSearchButton();
		String userStatus = varResellerUsersPage.getVarResellerUserStatus();
		varResellerUsersPage.selectUser();
		varResellerUserDetailsPage = new VarResellerUserDetailsPage(driver);
		varResellerUserDetailsPage.verifyVarResellerUserDetailsPageTitle();
		logStep("Verify selected VarReseller user status is same on VarReseller user details page");
		varResellerUserDetailsPage.verifySelectedUserStatusIsSameOnVarResellerUserDetailsPage(userStatus);
		varResellerUserDetailsPage.clickOnKebabMenuButton();
		logStep("Click on the lock user button in the kebab menu");
		varResellerUserDetailsPage.clickOnLockUserButton();
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Success! User status has been successfully locked");
		commonMethods.closeToasterMessage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify unlock user functionality")
	public void testVarResellerUserUnlockFunctionality() throws Exception {
		logStep("Search and select active user");
		searchOptionsPage = varResellerUsersPage.clickOnSearchOptionsButton();
		List<String> userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickToSearchButton();
		String userStatus = varResellerUsersPage.getVarResellerUserStatus();
		varResellerUsersPage.selectUser();
		varResellerUserDetailsPage = new VarResellerUserDetailsPage(driver);
		varResellerUserDetailsPage.verifyVarResellerUserDetailsPageTitle();
		logStep("Verify selected client user status is same on client user details page");
		varResellerUserDetailsPage.verifySelectedUserStatusIsSameOnVarResellerUserDetailsPage(userStatus);
		varResellerUserDetailsPage.clickOnKebabMenuButton();
		log("Click on the lock user button in the kebab menu");
		varResellerUserDetailsPage.clickOnLockUserButton();
		commonMethods = new CommonMethods(driver);
		logStep("Verify success toaster message");
		commonMethods.verifyToasterMessage("Success! User status has been successfully locked");
		commonMethods.closeToasterMessage();
		logStep("Verify Unlock button is displayed and click on unlock button");
		varResellerUserDetailsPage.verifyUnlockButtonIsDispalyed();
		varResellerUserDetailsPage.clickOnUnlockButton();
		logStep("Verify success toaster message");
		commonMethods.verifyToasterMessage("Success! User status has been successfully activated");
		commonMethods.closeToasterMessage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to set new password using password reset link found in the email for locked out user")
	public void testVarResellerUserLockedOutFunctionality() throws Exception {
		logStep("Search and select active user");
		searchOptionsPage = varResellerUsersPage.clickOnSearchOptionsButton();
        List<String> userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickToSearchButton();
		String userStatus = varResellerUsersPage.getVarResellerUserStatus();
		varResellerUsersPage.selectUser();
		varResellerUserDetailsPage = new VarResellerUserDetailsPage(driver);
		varResellerUserDetailsPage.verifyVarResellerUserDetailsPageTitle();
		logStep("Verify selected VarReseller user status is same on VarReseller user details page");
		varResellerUserDetailsPage.verifySelectedUserStatusIsSameOnVarResellerUserDetailsPage(userStatus);
		logStep("Click on kebab menu button");
		varResellerUserDetailsPage.clickOnKebabMenuButton();
		log("Click on the lock user button in the kebab menu");
		varResellerUserDetailsPage.clickOnLockUserButton();
		commonMethods = new CommonMethods(driver);
		logStep("Verify success toaster message");
		commonMethods.verifyToasterMessage("Lockedout email sent to users successfully");
		logStep("Login to CC portal");
		LoginPage commandcenterloginpage = new LoginPage(driver, ccuser.getUrl());
		clientDirectoryPage = login(ccuser.getUrl(), commonMethods.getUserName(), ccuser.getPassword());
		logStep("Verify an error message is displayed on the login page when the user tries to log in using the locked user credentials");
		CCUtils.assertEquals(commandcenterloginpage.getLockOutErrMsg().trim(),
				commandcenterloginpage.lockoutErrorMsg.trim());
		logStep("Verify user able to set new password using password reset link found in the email");
		resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		resetPage.urlInEmail(ccuser.getResetEmail(), autogeneratePassword);
		CCUtils.assertEquals(resetPage.getNewPaswwordCreatedMsg(), "Success! Your password has been reset.");
	}

}
