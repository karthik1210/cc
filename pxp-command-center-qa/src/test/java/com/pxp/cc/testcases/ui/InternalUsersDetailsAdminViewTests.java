// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.InternalUserDetailsPage;
import com.pxp.cc.objectmaps.InternalUsersPage;
import com.pxp.cc.objectmaps.SearchOptionsPage;

public class InternalUsersDetailsAdminViewTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public InternalUsersPage internalUsersPage;
	public SearchOptionsPage searchOptionsPage;
	public InternalUserDetailsPage internalUserDetailsPage;
	public List<String> userStatusList = null;
	public List<String> enrollmentStatusList = null;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify admin view on internal user details page")
	public void testInternalUsersDetailsAdminViewLabels() throws Exception {
		logStep("Click any one of the user name from table");
		internalUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify admin view internal user labels");
		internalUserDetailsPage.verifyInternalUserAdminViewLabels();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify kebab menu options is displayed with admin login on internal user details page")
	public void testInternalUsersKebabMenuWithAdminView() throws Exception {
		logStep("Click any one of the user name from table");
		internalUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify admin user display the kebab menu options on internal user page");
		internalUserDetailsPage.verifyKebabMenuOptionsForAdminView();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for different user status kebab menu options displayed to admin user")
	public void testKebabMenuOptionsDisplayedToAdminUserForInactiveStatus() throws Exception {
		logStep("Verify admin view for inactive user on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.inactive"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.selectUser();
		logStep("Click any one of the user name from table");
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickOnKebabMenuButton();
		internalUserDetailsPage.verifyActivateOptionDisplayedToAdminUser();
		internalUserDetailsPage.verifySendEnrollmentOptionDisplayedToAdminUser();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for different user status kebab menu options displayed to admin user")
	public void testKebabMenuOptionsDisplayedToAdminUserForActiveStatus() throws Exception {
		logStep("Verify admin view for active user on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.selectUser();
		logStep("Click any one of the user name from table");
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickOnKebabMenuButton();
		internalUserDetailsPage.verifyActivateOptionDisplayedToAdminUser();
		internalUserDetailsPage.verifySendEnrollmentOptionDisplayedToAdminUser();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for different user status kebab menu options displayed to admin user")
	public void testKebabMenuOptionsDisplayedToAdminUserForLockedStatus() throws Exception {
		logStep("Verify admin view for locked user on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.lockedout"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.selectUser();
		logStep("Click any one of the user name from table");
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickOnKebabMenuButton();
		internalUserDetailsPage.verifyLockedOptionDisplayedToAdminUser();
		internalUserDetailsPage.verifySendEnrollmentOptionDisplayedToAdminUser();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for different user status kebab menu options displayed to admin user")
	public void testKebabMenuOptionsDisplayedToAdminUserForNoRolesAssignedStatus() throws Exception {
		logStep("Verify admin view for no roles assigned user on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.no.roles.assigned"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.selectUser();
		logStep("Click any one of the user name from table");
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickOnKebabMenuButton();
		internalUserDetailsPage.verifySendEnrollmentOptionDisplayedToAdminUser();
	}

}
