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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button with user roles lable on internal user details page")
	public void testInternalUsersRolesLabelsOnInternalUserDetailsPage() throws Exception {
		logStep("Verify user roles section on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.no.roles.assigned"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		logStep("Click any one of the user name from table");
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.verifyLabelsOfInternalUserEditButton();
		internalUserDetailsPage.clickUserRoleEditButtonInInternalUserDetailsPage();
		internalUserDetailsPage.verifyLabelsOfInternalUserRoles();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify admin user able to add & save multiple roles after click on edit button on internal user details page")
	public void testAllInternalUsersRolesAddAndSaveFunctionality() throws Exception {
		logStep("Verify user roles section on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		logStep("Click any one of the user name from table");
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickUserRoleEditButtonInInternalUserDetailsPage();
		internalUserDetailsPage.selectAllUsersRolesAddAndSaveFunctionality();
		internalUserDetailsPage.clickSaveUserRoleButton();
		internalUserDetailsPage.verifySelectedAllUserRoleNamesDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify admin able to add & save single role after click on edit button on internal user details page")
	public void testSingleInternalUsersRolesAddAndSaveFunctionality() throws Exception {
		logStep("Verify user roles section on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.selectUser();
		logStep("Click any one of the user name from table");
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickUserRoleEditButtonInInternalUserDetailsPage();
		internalUserDetailsPage.selectSingleUsersRoleForAddAndSaveFunctionality();
		internalUserDetailsPage.clickSaveUserRoleButton();
		internalUserDetailsPage.verifyAfterAddAndSaveSingleUserRole();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify when admin click on edit button then able to delete & save multiple roles after click on save button on internal user details page")
	public void testAllInternalUsersRolesDeleteAndSaveFunctionality() throws Exception {
		logStep("Verify user roles section on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		logStep("Click any one of the user name from table");
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickUserRoleEditButtonInInternalUserDetailsPage();
		internalUserDetailsPage.selectAllUsersRolesAddAndSaveFunctionality();
		internalUserDetailsPage.clickSaveUserRoleButton();
		internalUserDetailsPage.clickUserRoleEditButtonInInternalUserDetailsPage();
		internalUserDetailsPage.selectAllUsersRolesDeleteAndSaveFunctionality();
		internalUserDetailsPage.clickSaveUserRoleButton();
		internalUserDetailsPage.verifySelectedAllUserRoleNamesDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button is accessable to admin and admin is able to delete & save single role after click on save button on internal user details page")
	public void testSingleInternalUsersRolesDeleteAndSaveFunctionality() throws Exception {
		logStep("Verify user roles section on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		logStep("Click any one of the user name from table");
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickUserRoleEditButtonInInternalUserDetailsPage();
		internalUserDetailsPage.selectSingleUsersRoleForAddAndSaveFunctionality();
		internalUserDetailsPage.clickSaveUserRoleButton();
		internalUserDetailsPage.clickUserRoleEditButtonInInternalUserDetailsPage();
		internalUserDetailsPage.selectSingleUsersRoleForDeleteAndSaveFunctionality();
		internalUserDetailsPage.verifyAfterAddAndSaveSingleUserRole();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify admin user able to add & cancel multiple roles after click on cancel button on internal user details page")
	public void testAllInternalUsersRolesAddAndCancelFunctionality() throws Exception {
		logStep("Verify user roles section on internal user page");
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		logStep("Click any one of the user name from table");
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickUserRoleEditButtonInInternalUserDetailsPage();
		internalUserDetailsPage.selectAllUsersRolesAddAndSaveFunctionality();
		internalUserDetailsPage.clickCancelUserRoleButton();
		internalUserDetailsPage.verifyNoUserRolesNameDisplayed();
	}

}