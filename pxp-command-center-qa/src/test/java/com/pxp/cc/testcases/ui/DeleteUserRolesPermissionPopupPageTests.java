// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.DeleteUserRolesAndPermissionPopupPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.ClientUserDetailsPage;
import com.pxp.cc.objectmaps.ClientUsersPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;



public class DeleteUserRolesPermissionPopupPageTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public ClientUsersPage clientUsersPage;
	public ClientUserDetailsPage clientUserDetailsPage;
	public DeleteUserRolesAndPermissionPopupPage deleteUserRolesPermissionPage;

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
		deleteUserRolesPermissionPage = clientUserDetailsPage.clickOnRedIconButton();
	}
	
	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify delete user roles & permissions popup")
	public void testDeleteUserRolesAndPermissionsPopup() throws Exception {
		logStep("Verify edit user roles and permissions popup");
		deleteUserRolesPermissionPage.verifyConfirmationQuestion();
		deleteUserRolesPermissionPage.verifyCrossIconIsDisplayed();
		deleteUserRolesPermissionPage.verifyCancelButtonIsDisplayed();
		deleteUserRolesPermissionPage.verifyYesButtonIsDisplayed();
		}
	
	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify  Yes ,cancel and cross button delete user roles & permissions popup")
	public void testDeleteUserRolesAndPermissionsYesButtonCancelAndCrossIconFunctionality() throws Exception {
		
		
		logStep("Verify after clicking on cancel button, user redirected to Client user details page");
		deleteUserRolesPermissionPage.clickOnCancelButton();
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		logStep("Verify after clicking on Cross Icon button, user redirected to Client user details page");
		deleteUserRolesPermissionPage.clickOnCrossIcon();
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		logStep("Verify after clicking on Yes button, user Should delete user roles & permissions popup  ");
		deleteUserRolesPermissionPage.clickOnYesButton();
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		
	}

}