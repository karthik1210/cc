// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddUserRolesAndPermissionsPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.ClientUserDetailsPage;
import com.pxp.cc.objectmaps.ClientUsersPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class ClientUserAddUserRolesAndPermissionsTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public ClientUsersPage clientUsersPage;
	public ClientUserDetailsPage clientUserDetailsPage;
	public AddUserRolesAndPermissionsPage addUserRolesAndPermissionsPage;

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
		clientUserDetailsPage = clientUsersPage.selectUser();
		logStep("Select associate entity on client user details page and click on add button");
		clientUserDetailsPage.selectEntity();
		addUserRolesAndPermissionsPage = clientUserDetailsPage.clickOnAddButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add user roles & permissions popup")
	public void testAddUserRolesAndPermissionsPopup() throws Exception {
		logStep("Verify add user roles and permissions popup");
		addUserRolesAndPermissionsPage.verifyPageTitle();
		addUserRolesAndPermissionsPage.verifyUserRoleLabelTextAndDropDownDefaultValue();
		addUserRolesAndPermissionsPage.verifyLocationsLabelTextAndDropDownDefaultValue();
		addUserRolesAndPermissionsPage.verifyCrossIconIsDisplayed();
		addUserRolesAndPermissionsPage.verifyCancelButtonIsDisplayed();
		addUserRolesAndPermissionsPage.verifyAddButtonIsDisplayed();
		addUserRolesAndPermissionsPage.clickOnUserRoleDropdown();
		addUserRolesAndPermissionsPage.getUserRole();
		addUserRolesAndPermissionsPage.verifyUserRoleOptions();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify cross icon and cancel button functionality on add user roles & permissions popup")
	public void testCrossIconAndCancelButtonFunctionality() throws Exception {
		logStep("Click the cancel button on the add user roles & permissions page");
		clientUserDetailsPage = addUserRolesAndPermissionsPage.clickOnCancelButton();
		logStep("Verify user redirected to client user details page after clicking on cancel button");
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		logStep("Click on add button on the add user roles and permissions popup");
		clientUserDetailsPage.clickOnAddButton();
		logStep("Click the cross icon on the add user roles & permissions page");
		clientUserDetailsPage = addUserRolesAndPermissionsPage.clickOnCrossIcon();
		logStep("Verify user redirected to client user details page after clicking on cross icon");
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user role dropdown is required field on add user roles & permissions popup")
	public void testRequiredErrorMessageForUserRoleField() throws Exception {
		logStep("Click on add button on the add user roles and permissions popup");
		addUserRolesAndPermissionsPage.clickOnAddButton();
		logStep("Verify required error message is displayed for user role field");
		CCUtils.assertEquals(addUserRolesAndPermissionsPage.getRequiredFieldErrorMessage(), "User Role is required");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user select and deselect all locations checkboxes by check and uncheck select all checkbox on the add user roles & permissions popup")
	public void testSelectAllCheckboxFunctionality() throws Exception {
		logStep("Click on locations dropdown");
		addUserRolesAndPermissionsPage.clickOnLocationsDropdown();
		logStep("Verify that the user is able to select all location checkboxes using the select all checkbox");
		addUserRolesAndPermissionsPage.clickOnSelectAllCheckbox();
		addUserRolesAndPermissionsPage.verifyAllCheckboxesAreSelected();
		logStep("Verify that the user is able to deselect all selected location checkboxes by unselecting select all checkbox");
		addUserRolesAndPermissionsPage.clickOnSelectAllCheckbox();
		addUserRolesAndPermissionsPage.verifyAllCheckboxesAreNotSelected();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify clear and apply button functionality on locations dropdown")
	public void testClearAndApplyButtonFunctionalityInLocationsDropdown() throws Exception {
		logStep("Click on user role dropdown and select client super admin user role");
		addUserRolesAndPermissionsPage.clickOnUserRoleDropdown();
		addUserRolesAndPermissionsPage.selectUserRole(testData.getProperty("client.user.role.client.super.admin"));
		logStep("Click on locations dropdown");
		addUserRolesAndPermissionsPage.clickOnLocationsDropdown();
		List<String> locations = new ArrayList<String>();
		locations.add(testData.getProperty("client.user.location.one"));
		locations.add(testData.getProperty("client.user.location.two"));
		logStep("Select locations from locations dropdown and click on apply button");
		addUserRolesAndPermissionsPage.selectLocations(locations);
		addUserRolesAndPermissionsPage.clickOnApplyButton();
		logStep("Verify selected locations are displayed in location dropdown with cross icon");
		addUserRolesAndPermissionsPage.verifySelectedLocationsAreDisplayedInLocationsDropdown(locations);
		logStep("Click on locations dropdown and Click on clear button");
		addUserRolesAndPermissionsPage.clickOnLocationsDropdown();
		addUserRolesAndPermissionsPage.clickOnClearButton();
		logStep("Verify selected locations are cleared after clicking on clear button");
		addUserRolesAndPermissionsPage.verifySelectedLocationsAreClearedInLocationsDropdown();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add user roles and permissions functionality")
	public void testAddUserRolesAndPermissionFunctionality() throws Exception {
		logStep("Click on user role dropdown and select client super admin user role");
		addUserRolesAndPermissionsPage.clickOnUserRoleDropdown();
		addUserRolesAndPermissionsPage.selectUserRole(testData.getProperty("client.user.role.client.super.admin"));
		logStep("Click on locations dropdown");
		addUserRolesAndPermissionsPage.clickOnLocationsDropdown();
		List<String> locations = new ArrayList<String>();
		locations.add(testData.getProperty("client.user.location.one"));
		locations.add(testData.getProperty("client.user.location.two"));
		logStep("Select locations from locations dropdown and click on apply button");
		addUserRolesAndPermissionsPage.selectLocations(locations);
		addUserRolesAndPermissionsPage.clickOnApplyButton();
		logStep("Click on the add button on add user roles and permissions popup");
		clientUserDetailsPage = addUserRolesAndPermissionsPage.clickOnAddButton();
		CommonMethods commonMethods = new CommonMethods(driver);
		logStep("Verify success toaster");
		commonMethods.verifyToasterMessage("Roles and Permission has been successfully added");
		logStep("Verify user redirected to client user details page");
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to clear selected locations using cross icon in locations dropdown")
	public void testClearSelectedLocationsUsingCrossIcon() throws Exception {
		logStep("Click on locations dropdown");
		addUserRolesAndPermissionsPage.clickOnLocationsDropdown();
		logStep("Verify locations checkbox are not by default selected");
		addUserRolesAndPermissionsPage.verifyAllCheckboxesAreNotSelected();
		List<String> locations = new ArrayList<String>();
		locations.add(testData.getProperty("client.user.location.one"));
		logStep("Select location from locations dropdown and click on apply button");
		addUserRolesAndPermissionsPage.selectLocations(locations);
		addUserRolesAndPermissionsPage.clickOnApplyButton();
		logStep("Click on cross icon and verify selected location is not displayed in locations dropdown");
		addUserRolesAndPermissionsPage.clearSearchedLocationsUsingCrossIcon(locations);
		addUserRolesAndPermissionsPage.verifySelectedLocationsAreClearedInLocationsDropdown();
	}
}
