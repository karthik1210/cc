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
import com.pxp.cc.util.CCUtils;

public class InternalUsersDetailsSelfViewTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public InternalUsersPage internalUsersPage;
	public SearchOptionsPage searchOptionsPage;
	public InternalUserDetailsPage internalUserDetailsPage;
	public List<String> userStatusList = null;
	public List<String> enrollmentStatusList = null;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for self view all the labels on internal user details page")
	public void testInternalUsersDetailsSelfViewLabels() throws Exception {
		logStep("Click any one of the user name from table");
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		logStep("Verify self view internal user labels");
		internalUserDetailsPage.verifyInternalUserSelfViewLabels();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for self view last login date, enrollment status & user status is displayed on internal user details page")
	public void testInternalUserLastLoginDateEnrollmentAndUserStatusInSelfViewMode() throws Exception {
		logStep("Click any one of the user name from table");
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		logStep("Verify for self view internal user get last login date, enrollment status & user status details");
		internalUserDetailsPage.verifyInternalUserLastLoginDateEnrollmentAndUserStatusInSelfViewMode();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "verify user redirected to internal users page after clicking on back arrow on internal user details page")
	public void testInternalUsersDetailsBackArrowFunctionality() throws Exception {
		logStep("Click any one of the user name from table");
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		logStep("Verify after clicking on back arrow button user redirected to internal users page");
		internalUserDetailsPage.clickBackArrowOnInternalUserDetailsPage();
		internalUsersPage.verifyInternalUsersPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify kebab menu options is displayed with self user login on internal user details page")
	public void testInternalUsersKebabMenuWithSelfView() throws Exception {
		logStep("Click any one of the user name from table");
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify self user display the kebab menu options on internal user page");
		internalUserDetailsPage.verifyInternalUserKebabMenuOptionsDisplayedForSelfView();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for active user status kebab menu options displayed to self user")
	public void testKebabMenuOptionsDisplayedToSelfUserWithActiveStatus() throws Exception {
		logStep("Verify self view for active user on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		logStep("Click any one of the user name from table");
		internalUsersPage.selectUser();		
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify deactivate user & send enrollment email options are not enable in kebab menu");
		internalUserDetailsPage.verifyActivateOrDeactivateOptionDisabledToSelfUser();
		internalUserDetailsPage.verifySendEnrollmentOptionInKebabMenuToSelfUser();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for inactive user status kebab menu options displayed to self user")
	public void testKebabMenuOptionsDisplayedToSelfUserForInactiveStatus() throws Exception {
		logStep("Verify self view for inactive user on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.inactive"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		logStep("Click any one of the user name from table");
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify activate user option should display & send enrollment email option shouldn't displayed in kebab menu");
		internalUserDetailsPage.verifyActivateOrDeactivateOptionDisabledToSelfUser();
		internalUserDetailsPage.verifySendEnrollmentOptionInKebabMenuToSelfUser();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for locked out user status kebab menu options displayed to self user")
	public void testKebabMenuOptionsDisplayedToSelfUserForLockedStatus() throws Exception {
		logStep("Verify self view for locked user on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.lockedout"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		logStep("Click any one of the user name from table");
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify unlock user option is displayed & send enrollment email options should not displayed in kebab menu");
		internalUserDetailsPage.verifyLockAndUnlockOptionDisplayedToSelfUser();
		internalUserDetailsPage.verifySendEnrollmentOptionInKebabMenuToSelfUser();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for no roles assiged user status kebab menu options displayed to self user")
	public void testKebabMenuOptionsDisplayedToSelfUserForNoRolesAssignedStatus() throws Exception {
		logStep("Verify self view for no roles assigned user on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.no.roles.assigned"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		logStep("Click any one of the user name from table");
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify send enrollment email options should not displayed in kebab menu");
		internalUserDetailsPage.verifySendEnrollmentOptionInKebabMenuToSelfUser();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button is displayed & fields are editable")
	public void testEditButtonEnabledToSelfUserForDemographicDetails() throws Exception {
		logStep("Verify self view for no roles assigned user on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		logStep("Click any one of the user name from table");
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		logStep("Verifying demographic fields are editable to self user");
		internalUserDetailsPage.verifykEditDemographicDetailsInInternalUserDetailsPage();
		logStep("Clicked on edit button on internal user details page");
		internalUserDetailsPage.clickEditInInternalUser();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button is displayed & fields are uneditable")
	public void testEditButtonEnabledToSelfUserForUserRole() throws Exception {
		logStep("Verify self view for no roles assigned user on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		logStep("Click any one of the user name from table");
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		logStep("Verifying demographic fields are non editable to self user");
		internalUserDetailsPage.verifyUserRoleNonEditableInInternalUserDetailsPage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for self view locked status on hover on internal user details page")
	public void testHoverMessageForInternalUsersOnLockedStatusSelfView() throws Exception {
		logStep("Verify self view for locked user on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.lockedout"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		logStep("Click any one of the user name from table");
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.verifyHoverOverMessageOnLockedUserStatus();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for self view active status on hover on internal user details page")
	public void testHoverMessageForInternalUsersOnActiveStatusSelfView() throws Exception {
		logStep("Verify self view for locked user on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		logStep("Click any one of the user name from table");
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.verifyHoverOverMessageOnActiveUserStatus();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for self view inactive status on hover on internal user details page")
	public void testHoverMessageForInternalUsersOnInactiveStatusSelfView() throws Exception {
		logStep("Verify self view for locked user on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.inactive"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		logStep("Click any one of the user name from table");
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.verifyHoverOverMessageOnInactiveUserStatus();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for self view no role assigned status on hover on internal user details page")
	public void testHoverMessageForInternalUsersOnNoRoleAssignedStatusSelfView() throws Exception {
		logStep("Verify self view for locked user on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.no.roles.assigned"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		logStep("Click any one of the user name from table");
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.verifyHoverOverMessageOnNoRoleAssignedUserStatus();
	}

}
