// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientUserDeleteUserRolesAndPermissionsPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.ClientUserDetailsPage;
import com.pxp.cc.objectmaps.ClientUsersPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.util.CommonMethods;

public class ClientUserDeleteUserRolesAndPermissionsPageTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public ClientUsersPage clientUsersPage;
	public ClientUserDetailsPage clientUserDetailsPage;
	public ClientUserDeleteUserRolesAndPermissionsPage deleteUserRolesPermissionPage;
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
		logStep("Select client user");
		clientUsersPage.selectUser();
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
		logStep("Select associate entity on client user details page and click on add button");
		clientUserDetailsPage.selectEntity();
		deleteUserRolesPermissionPage = clientUserDetailsPage.clickOnDeleteUserRoleButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify delete user roles & permissions popup")
	public void testDeleteUserRolesAndPermissionsPopup() throws Exception {
		logStep("Verify delete user roles and permissions popup");
		deleteUserRolesPermissionPage.verifyConfirmationQuestion();
		deleteUserRolesPermissionPage.verifyCloseWindowIsDisplayed();
		deleteUserRolesPermissionPage.verifyCancelButtonIsDisplayed();
		deleteUserRolesPermissionPage.verifyYesButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of Cancel button on delete user roles & permissions popup")
	public void testCancelBtnFunctionalityOnDeleteUserRolesPopup() throws Exception {

		logStep("Verify after clicking on cancel button, user redirected to Client user details page");
		deleteUserRolesPermissionPage.clickOnCancelButton();
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify  functionality of cross button on delete user roles & permissions popup")
	public void testCrossBtnFunctionalityOnDeleteUserRolesPopup() throws Exception {
		logStep("Verify after clicking on Cross Icon button, user redirected to Client user details page");
		deleteUserRolesPermissionPage.clickOnCloseWindow();
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of yes button on delete user roles & permissions popup")
	public void testYesBtnFunctionalityOnDeleteUserRolesPopup() throws Exception {
		logStep("Click on the yes button on delete user roles and permissions popup");
		deleteUserRolesPermissionPage.clickOnYesButton();
		CommonMethods commonMethods = new CommonMethods(driver);
		logStep("Verify success toaster");
		commonMethods.verifyToasterMessage("Roles and Permissions has been successfully deleted");
		logStep("Verify user redirected to client user details page");
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
	}
}
