// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientUserDeleteUserRolesAndPermissionPopupPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.ClientUserDetailsPage;
import com.pxp.cc.objectmaps.ClientUsersPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;

public class ClientUserDeleteUserRolesAndPermissionPopupPageTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public ClientUsersPage clientUsersPage;
	public ClientUserDetailsPage clientUserDetailsPage;
	public ClientUserDeleteUserRolesAndPermissionPopupPage deleteUserRolesPermissionPage;
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
		deleteUserRolesPermissionPage = clientUserDetailsPage.clickOnRedIconButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify delete user roles & permissions popup")
	public void testDeleteUserRolesAndPermissionsPopup() throws Exception {
		logStep("Verify delete user roles and permissions popup");
		deleteUserRolesPermissionPage.verifyConfirmationQuestion();
		deleteUserRolesPermissionPage.verifyCrossIconIsDisplayed();
		deleteUserRolesPermissionPage.verifyCancelButtonIsDisplayed();
		deleteUserRolesPermissionPage.verifyYesButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify  Yes button ,cancel button and cross  button delete user roles & permissions popup")
	public void testYesCancelAndCrossBtnFunctionalityOnDeleteUserRolesPopup() throws Exception {

		logStep("Verify after clicking on cancel button, user redirected to Client user details page");
		deleteUserRolesPermissionPage.clickOnCancelButton();
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();

		clientUserDetailsPage.clickOnRedIconButton();
		logStep("Verify after clicking on Cross Icon button, user redirected to Client user details page");
		deleteUserRolesPermissionPage.clickOnCrossIcon();
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();

		clientUserDetailsPage.clickOnRedIconButton();
		logStep("Verify after clicking on Yes button, user redirected to clicent user details page");
		deleteUserRolesPermissionPage.clickOnButtonYes();
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();

		commonMethods = new CommonMethods(driver);
		String actualToasterMessage = commonMethods.getToasterMessage();
		if (actualToasterMessage.equalsIgnoreCase("Roles and Permissions has been successfully deleted")) {
			log("Popup message is displayed");
			CCUtils.assertTrue(true, "Test Pass");
		} else {
			log("popup message is incorrect : " + actualToasterMessage);
			CCUtils.assertTrue(false, "Test Failed");
		}

	}

}