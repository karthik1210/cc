// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddNewInternalUsersPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.InternalUserDetailsPage;
import com.pxp.cc.objectmaps.InternalUsersPage;
import com.pxp.cc.objectmaps.SearchOptionsPage;
import com.pxp.cc.util.CCUtils;
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
	public AddNewInternalUsersPage addNewInternalUsersPage;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		commonMethods = new CommonMethods(driver);
		logStep("Click on new button");
		internalUsersPage.clickOnNewButton();
		addNewInternalUsersPage = new AddNewInternalUsersPage(driver);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to lock and unlock active internal user and Verify lock button is not displayed in kebab menu for locked user")
	public void testInternalUserLockButtonIsPresentForActiveUserAndNotPresentForLockedUser() throws Exception {
		logStep("Create New Active Internal User");
		String firstName = "ActiveUser" + CCUtils.generateAlphaNumericString(3).concat("'s");
		commonMethods.enterFirstName(firstName);
		String lastName = CCUtils.generateAlphaNumericString(9);
		commonMethods.enterLastName(lastName);
		commonMethods.enterEmail(CCUtils.generateNewEmail(10));
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(9));
		addNewInternalUsersPage.selectUserRole("Super Admin");
		addNewInternalUsersPage.clickOnSaveAndAddButton();
		commonMethods.verifyToasterMessage("Success! New User has been successfully added");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		logStep("Search and select active user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.enterSearchTextInSearchFields(firstName, lastName, "", "", "");
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
		logStep("Verify lock button is not displayed in kebab menu for locked user");
		internalUserDetailsPage.clickOnKebabMenuButton();
		internalUserDetailsPage.verifyLockUsertButtonIsNotDisplayed();
		logStep("Click on unlock user button in kebab menu");
		internalUserDetailsPage.clickOnUnlockUserButton();
		logStep("verify success toaster message");
		commonMethods.verifyToasterMessage("Success! User status has been successfully activated");

	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to lock and unlock no roles assigned user")
	public void testInternalUserLockButtonIsPresentForNoRolesAssignedUser() throws Exception {
		logStep("Create New Internal User without user role");
		String firstName = "NewInternalUser" + CCUtils.generateAlphaNumericString(3).concat("'s");
		commonMethods.enterFirstName(firstName);
		String lastName = CCUtils.generateAlphaNumericString(9);
		commonMethods.enterLastName(lastName);
		commonMethods.enterEmail(CCUtils.generateNewEmail(10));
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(9));
		addNewInternalUsersPage.clickOnSaveAndAddButton();
		commonMethods.verifyToasterMessage("Success! New User has been successfully added");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		logStep("Search and select no roles assigned user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.no.roles.assigned"));
		searchOptionsPage.enterSearchTextInSearchFields(firstName, lastName, "", "", "");
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

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify lock button is not displayed in kebab menu for inactive user")
	public void testInternalUserLockButtonIsNotPresentForInactiveUser() throws Exception {
		logStep("Create New Inactive Internal User");
		String firstName = "InactiveUser" + CCUtils.generateAlphaNumericString(3).concat("'s");
		commonMethods.enterFirstName(firstName);
		String lastName = CCUtils.generateAlphaNumericString(9);
		commonMethods.enterLastName(lastName);
		commonMethods.enterEmail(CCUtils.generateNewEmail(10));
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(9));
		commonMethods.selectInactiveRadiobutton();
		addNewInternalUsersPage.selectUserRole("Super Admin");
		addNewInternalUsersPage.clickOnSaveAndAddButton();
		commonMethods.verifyToasterMessage("Success! New User has been successfully added");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		logStep("Search and select inactive user");
		userStatusList = new ArrayList<String>();
		searchOptionsPage.enterSearchTextInSearchFields(firstName, lastName, "", "", "");
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
		logStep("Verify lock button is not displayed in kebab menu for inactive user");
		internalUserDetailsPage.verifyLockUsertButtonIsNotDisplayed();
	}

}
