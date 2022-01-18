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
	public LockUserConfirmationPage lockUserConfirmationPage;
	public List<String> userStatusList = null;
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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify lock user confirmation popup")
	public void testClientUserLockConfirmationPopup() throws Exception {
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
		String userName = clientUserDetailsPage.getUserName();
		clientUserDetailsPage.clickOnKebabMenuButton();
		lockUserConfirmationPage = clientUserDetailsPage.clickOnLockUserButton();
		logStep("Verify lock user confirmation popup");
		lockUserConfirmationPage.verifyConfirmationTextForSingleUser(userName);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify cancel and cross button functionality for lock button popup")
	public void testClientUserLockButtonCancelAndCrossIconFunctionality() throws Exception {
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
		lockUserConfirmationPage = clientUserDetailsPage.clickOnLockUserButton();
		logStep("Verify after clicking on cancel button, user redirected to client user details page");
		lockUserConfirmationPage.clickOnCancelButton();
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		clientUserDetailsPage.clickOnKebabMenuButton();
		clientUserDetailsPage.clickOnLockUserButton();
		logStep("Verify after clicking on cross icon, user redirected to client user details page");
		lockUserConfirmationPage.clickOnCrossIcon();
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify locked out user functionality")
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
		String userName = clientUserDetailsPage.getUserName();
		clientUserDetailsPage.clickOnKebabMenuButton();
		lockUserConfirmationPage = clientUserDetailsPage.clickOnLockUserButton();
		logStep("Verify lock user confirmation popup");
		lockUserConfirmationPage.verifyConfirmationTextForSingleUser(userName);
		lockUserConfirmationPage.clickOnYesSendButton();
		logStep("verify success toaster message");
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Lockedout email sent to users successfully");
		logStep("Login to CC portal");
		LoginPage commandcenterloginpage = new LoginPage(driver, ccuser.getUrl());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		CCUtils.assertEquals(commandcenterloginpage.getLockOutErrMsg().trim(),
				commandcenterloginpage.lockoutErrorMsg.trim());
		logStep("Verify user able to set new password using password reset link found in the email");
		resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		resetPage.urlInEmail(ccuser.getResetEmail(), autogeneratePassword);
		CCUtils.assertEquals(resetPage.getNewPaswwordCreatedMsg(), "Success! Your password has been reset.");
	}

}
