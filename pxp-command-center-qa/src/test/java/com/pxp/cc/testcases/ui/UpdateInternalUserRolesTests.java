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

public class UpdateInternalUserRolesTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public InternalUsersPage internalUsersPage;
	public SearchOptionsPage searchOptionsPage;
	public InternalUserDetailsPage internalUserDetailsPage;
	public List<String> userStatusList = null;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button with user roles label on internal user details page")
	public void testInternalUsersRolesLabelsOnInternalUserDetailsPage() throws Exception {
		logStep("search and select active user");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.verifyLabelsOfInternalUserEditButton();
		internalUserDetailsPage.clickUserRoleEditButtonInInternalUserDetailsPage();
		logStep("Verify labels on internal user details page for edit user roles");
		internalUserDetailsPage.verifyLabelsOfInternalUserRoles();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify admin user able to add & save multiple roles after click on edit button on internal user details page")
	public void testAllInternalUsersRolesAddAndSaveFunctionality() throws Exception {
		logStep("search and select active user");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickUserRoleEditButtonInInternalUserDetailsPage();
		logStep("Select All Internal User roles from edit user roles section");
		internalUserDetailsPage.selectAllUsersRolesAddAndSaveFunctionality();
		internalUserDetailsPage.clickSaveUserRoleButton();
		logStep("Verify selected all users roles displayed on internal user details page after click on save button");
		internalUserDetailsPage.verifySelectedAllUserRoleNamesDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify admin able to add & save single role after click on edit button on internal user details page")
	public void testSingleInternalUsersRolesAddAndSaveFunctionality() throws Exception {
		logStep("search and select active user");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickUserRoleEditButtonInInternalUserDetailsPage();
		logStep("Select single Internal User role from edit user roles section");
		internalUserDetailsPage.selectSingleUsersRoleForAddAndSaveFunctionality();
		internalUserDetailsPage.clickSaveUserRoleButton();
		logStep("Verify selected single user role displayed on internal user details page after click on save button");
		internalUserDetailsPage.verifyAfterAddAndSaveSingleUserRole();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify when admin click on edit button then able to delete & save multiple roles after click on save button on internal user details page")
	public void testAllInternalUsersRolesDeleteAndSaveFunctionality() throws Exception {
		logStep("search and select active user");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickUserRoleEditButtonInInternalUserDetailsPage();
		logStep("Select All Internal User roles from edit user roles section");
		internalUserDetailsPage.selectAllUsersRolesAddAndSaveFunctionality();
		internalUserDetailsPage.clickSaveUserRoleButton();
		logStep("Verify selected all users roles displayed on internal user details page after click on save button");
		internalUserDetailsPage.clickUserRoleEditButtonInInternalUserDetailsPage();
		log("Select All Internal User roles for performing deleted functionality");
		internalUserDetailsPage.selectAllUsersRolesDeleteAndSaveFunctionality();
		internalUserDetailsPage.clickSaveUserRoleButton();
		logStep("Verify deleted all users roles shouldn't display on internal user details page after click on save button");
		internalUserDetailsPage.verifySelectedAllUserRoleNamesDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button is accessable to admin and admin is able to delete & save single role after click on save button on internal user details page")
	public void testSingleInternalUsersRolesDeleteAndSaveFunctionality() throws Exception {
		logStep("search and select active user");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickUserRoleEditButtonInInternalUserDetailsPage();
		logStep("Select single Internal User role from edit user roles section");
		internalUserDetailsPage.selectSingleUsersRoleForAddAndSaveFunctionality();
		logStep("Verify selected single user role displayed on internal user details page after click on save button");
		internalUserDetailsPage.clickSaveUserRoleButton();
		internalUserDetailsPage.clickUserRoleEditButtonInInternalUserDetailsPage();
		logStep("Select single Internal User role for deleted functionality");
		internalUserDetailsPage.selectSingleUsersRoleForDeleteAndSaveFunctionality();
		logStep("Verify deleted single user role shouldn't display on internal user details page after click on save button");
		internalUserDetailsPage.verifyAfterAddAndSaveSingleUserRole();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify admin user able to add & cancel multiple roles after click on cancel button on internal user details page")
	public void testAllInternalUsersRolesAddAndCancelFunctionality() throws Exception {
		logStep("search and select active user");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickUserRoleEditButtonInInternalUserDetailsPage();
		logStep("Select All Internal User roles from edit user roles section");
		internalUserDetailsPage.selectAllUsersRolesAddAndSaveFunctionality();
		internalUserDetailsPage.clickCancelUserRoleButton();
		logStep("Verify selected user roles shouldn't display on internal user details page after click on cancel button");
		internalUserDetailsPage.verifyNoUserRolesNameDisplayed();
	}

}