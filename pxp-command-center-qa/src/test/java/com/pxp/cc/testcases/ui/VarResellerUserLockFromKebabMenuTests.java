// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddNewVarResellerUserPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.SearchOptionsPage;
import com.pxp.cc.objectmaps.VarResellerUserDetailsPage;
import com.pxp.cc.objectmaps.VarResellerUsersPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class VarResellerUserLockFromKebabMenuTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public VarResellerUsersPage varResellerUsersPage;
	public VarResellerUserDetailsPage varResellerUserDetailsPage;
	public CommonMethods commonMethods;
	public SearchOptionsPage searchOptionsPage;
	public AddNewVarResellerUserPage addNewVarResellerUserPage;

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
		logStep("Click on new button");
		addNewVarResellerUserPage = varResellerUsersPage.clickOnNewButton();
		commonMethods = new CommonMethods(driver);
	}

	@Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to lock and unlock active user and Verify lock button is not displayed in kebab menu for locked user")
	public void testVarResellerUserLockButtonIsPresentForActiveUserAndNotPresentForLockedUser() throws Exception {
		logStep("create New Active VAR/Reseller User");
		addNewVarResellerUserPage.searchAndSelectVarReseller(testData.getProperty("api.client.three.type"));
		String firstName = "ActiveUser" + CCUtils.generateAlphaNumericString(5);
		commonMethods.enterFirstName(firstName);
		String lastName = CCUtils.generateAlphaNumericString(5).concat("-")
				.concat(CCUtils.generateAlphaNumericString(5));
		commonMethods.enterLastName(lastName);
		commonMethods.enterEmail(CCUtils.generateNewEmail(8));
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(9));
		commonMethods.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
		addNewVarResellerUserPage.selectUserRole(testData.getProperty("var.reseller.user.role.staff"));
		addNewVarResellerUserPage.clickOnSaveAndAdd();
		logStep("Verify success toaster message");
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Success! New user has been successfully added");
		logStep("Search and select active user");
		searchOptionsPage = varResellerUsersPage.clickOnSearchOptionsButton();
		List<String> userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.enterSearchTextInSearchFields(firstName, lastName, "", "", "");
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
		logStep("Click on the lock user button in the kebab menu");
		varResellerUserDetailsPage.clickOnLockUserButton();
		commonMethods.verifyToasterMessage("Success! User status has been successfully locked");
		varResellerUserDetailsPage.verifyUserStatusAfterClickingOnLockUserButton();
		logStep("Verify lock button is not displayed in kebab menu for locked user");
		varResellerUserDetailsPage.clickOnKebabMenuButton();
		varResellerUserDetailsPage.verifyLockUserButtonIsNotDisplayed();
		logStep("Click on unlock user button in kebab menu");
		varResellerUserDetailsPage.clickOnUnlockUserButton();
		logStep("verify success toaster message");
		commonMethods.verifyToasterMessage("Success! User status has been successfully activated");
	}

	@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class, description = "Verify lock button is displayed in kebab menu for no roles assigned user")
	public void testVarResellerUserLockButtonIsPresentForNoRolesAssignedUser() throws Exception {
		logStep("create New Active VAR/Reseller User without user role");
		addNewVarResellerUserPage.searchAndSelectVarReseller(testData.getProperty("api.client.three.type"));
		String firstName = "NewUser" + CCUtils.generateAlphaNumericString(5);
		commonMethods.enterFirstName(firstName);
		String lastName = CCUtils.generateAlphaNumericString(5).concat("-")
				.concat(CCUtils.generateAlphaNumericString(5));
		commonMethods.enterLastName(lastName);
		commonMethods.enterEmail(CCUtils.generateNewEmail(8));
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(8));
		commonMethods.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
		addNewVarResellerUserPage.selectUserRole(testData.getProperty("user.status.no.roles.assigned"));
		addNewVarResellerUserPage.clickOnSaveAndAdd();
		logStep("Verify success toaster message");
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Success! New user has been successfully added");
		logStep("Search and select no roles assigned user");
		searchOptionsPage = varResellerUsersPage.clickOnSearchOptionsButton();
		List<String> userStatusList = new ArrayList<String>();
		searchOptionsPage.enterSearchTextInSearchFields(firstName, lastName, "", "", "");
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
		logStep("Click on the lock user button in the kebab menu");
		varResellerUserDetailsPage.clickOnLockUserButton();
		commonMethods.verifyToasterMessage("Success! User status has been successfully locked");
		varResellerUserDetailsPage.verifyUserStatusAfterClickingOnLockUserButton();
		logStep("Click on unlock user button in kebab menu");
		varResellerUserDetailsPage.clickOnUnlockUserButton();
		logStep("verify success toaster message");
		commonMethods.verifyToasterMessage("Success! User status has been successfully activated");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify lock button is not displayed in kebab menu for inactive user")
	public void testVarResellerUserLockButtonIsNotPresentForInactiveUser() throws Exception {
		logStep("create New Inactive VAR/Reseller User");
		addNewVarResellerUserPage.searchAndSelectVarReseller(testData.getProperty("api.client.three.type"));
		String firstName = "InactiveUser" + CCUtils.generateAlphaNumericString(5);
		commonMethods.enterFirstName(firstName);
		String lastName = CCUtils.generateAlphaNumericString(5).concat("-")
				.concat(CCUtils.generateAlphaNumericString(5));
		commonMethods.enterLastName(lastName);
		commonMethods.enterEmail(CCUtils.generateNewEmail(8));
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(10));
		commonMethods.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
		addNewVarResellerUserPage.selectUserRole(testData.getProperty("var.reseller.user.role.staff"));
		commonMethods.selectInactiveRadiobutton();
		addNewVarResellerUserPage.clickOnSaveAndAdd();
		logStep("Verify success toaster message");
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Success! New user has been successfully added");
		logStep("Search and select inactive user");
		searchOptionsPage = varResellerUsersPage.clickOnSearchOptionsButton();
		searchOptionsPage.enterSearchTextInSearchFields(firstName, lastName, "", "", "");
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

}
