// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

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
import com.pxp.cc.objectmaps.PasswordResetPage;
import com.pxp.cc.objectmaps.SearchOptionsPage;
import com.pxp.cc.objectmaps.SendBulkEnrollmentPage;
import com.pxp.cc.objectmaps.SendEnrollmentConfirmationPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class ClientUserSendEnrollmentEmailUsingKebabMenuTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public ClientUsersPage clientUsersPage;
	public SendBulkEnrollmentPage sendBulkEnrollmentPage;
	public SendEnrollmentConfirmationPage sendEnrollmentConfirmationPage;
	public ClientUserDetailsPage clientUserDetailsPage;
	public SearchOptionsPage searchOptionsPage;
	public List<String> userStatusList = null;
	public List<String> enrollmentStatusList = null;
	public PasswordResetPage resetPage;
	SecureRandom random = new SecureRandom();
	String autogeneratePassword = PasswordResetPage.genPassword(random);
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
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify send enrollment button is not displayed in kebab menu for inactive user")
	public void testSendEnrollmentButtonIsNotPresentForInactiveUser() throws Exception {
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		logStep("Search and select inactive user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.inactive"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = clientUsersPage.getClientUserStatus();
		clientUsersPage.selectUser();
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage(userStatus);
		clientUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify send enrollment button is not displayed in kebab menu for inactive user");
		clientUserDetailsPage.verifySendEnrollmentButtonIsNotDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify send enrollment button is not displayed in kebab menu for no roles assigned user")
	public void testSendEnrollmentButtonIsNotPresentForNoRolesAssignedUser() throws Exception {
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		logStep("Search and select no roles assigned user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.no.roles.assigned"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		clientUsersPage.selectUser();
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage(
				testData.getProperty("user.status.no.roles.assigned"));
		clientUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify send enrollment button is not displayed in kebab menu for no roles assigned user");
		clientUserDetailsPage.verifySendEnrollmentButtonIsNotDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify send enrollment button is not displayed in kebab menu for locked user")
	public void testSendEnrollmentButtonIsNotPresentForLockedUser() throws Exception {
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		logStep("Search and select locked user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.lockedout"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = clientUsersPage.getClientUserStatus();
		clientUsersPage.selectUser();
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage(userStatus);
		clientUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify send enrollment button is not displayed in kebab menu for locked user");
		clientUserDetailsPage.verifySendEnrollmentButtonIsNotDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify send enrollment button is displayed in kebab menu for active user")
	public void testSendEnrollmentButtonIsPresentForActiveUser() throws Exception {
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		logStep("Search and select active user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = clientUsersPage.getClientUserStatus();
		clientUsersPage.selectUser();
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage(userStatus);
		clientUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify send enrollment button is displayed in kebab menu for active user");
		clientUserDetailsPage.verifySendEnrollmentButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify send enrollment confirmation popup")
	public void testSendEnrollmentConfirmarionPopup() throws Exception {
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		logStep("Search and select active user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = clientUsersPage.getClientUserStatus();
		clientUsersPage.selectUser();
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage(userStatus);
		String userName = clientUserDetailsPage.getUserName();
		clientUserDetailsPage.clickOnKebabMenuButton();
		sendEnrollmentConfirmationPage = clientUserDetailsPage.clickOnSendEnrollmentEmailButton();
		logStep("Verify send enrollment confirmation popup");
		sendEnrollmentConfirmationPage.VerifyConfirmationPopupForSingleUser(userName);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify cancel and cross button functionality on send enrollment confirmation popup")
	public void testCancelAndCrossIconFunctionality() throws Exception {
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		logStep("Search and select active user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = clientUsersPage.getClientUserStatus();
		clientUsersPage.selectUser();
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage(userStatus);
		clientUserDetailsPage.clickOnKebabMenuButton();
		sendEnrollmentConfirmationPage = clientUserDetailsPage.clickOnSendEnrollmentEmailButton();
		logStep("Verify after clicking on cancel button, user redirected to client user details page");
		sendEnrollmentConfirmationPage.clickOnCancelButton();
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		clientUserDetailsPage.clickOnKebabMenuButton();
		clientUserDetailsPage.clickOnSendEnrollmentEmailButton();
		logStep("Verify after clicking on cross icon, user redirected to client user details page");
		sendEnrollmentConfirmationPage.clickOnCrossIcon();
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to set new password using password reset link found in the email")
	public void testResetPasswordFunctionality() throws Exception {
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		logStep("search and select user");
		List<String> userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		List<String> enrollStatusList = new ArrayList<String>();
		enrollStatusList.add(testData.getProperty("enrollment.status.pending"));
		searchOptionsPage.selectEnrollmentStatusValues(enrollStatusList);
		searchOptionsPage.clickOnSearchButton();
		clientUsersPage.selectUser();
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
		String userName = clientUserDetailsPage.getUserName();
		logStep("Select send enrollment email button from kebab menu");
		clientUserDetailsPage.clickOnKebabMenuButton();
		sendEnrollmentConfirmationPage = clientUserDetailsPage.clickOnSendEnrollmentEmailButton();
		sendEnrollmentConfirmationPage.verifyConfirmationTextForSingleUser(userName);
		sendEnrollmentConfirmationPage.clickOnYesSendButton();
		logStep("verify success toaster message");
		commonMethods = new CommonMethods(driver);
		commonMethods.verifySuccessToaster("Enrollment sent to users successfully");
		logStep("Verify user able to set new password using password reset link found in the email");
		resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		resetPage.urlInEmail(ccuser.getResetEmail(), autogeneratePassword);
		CCUtils.assertEquals(resetPage.getNewPaswwordCreatedMsg(), "Success! Your password has been reset.");
	}

}
