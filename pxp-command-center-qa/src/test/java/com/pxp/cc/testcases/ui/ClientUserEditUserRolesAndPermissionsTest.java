// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 
package com.pxp.cc.testcases.ui;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.ClientUserDetailsPage;
import com.pxp.cc.objectmaps.ClientUsersPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.EditUserRolesAndPermissionsPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class ClientUserEditUserRolesAndPermissionsTest extends CommandCenterGenericTest{

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public ClientUsersPage clientUsersPage;
	public ClientUserDetailsPage clientUserDetailsPage;
	public EditUserRolesAndPermissionsPage editUserRolesAndPermissionsPage;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to Command Center portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		commandCenterUsersPage = clientDirectoryPage.clickOnCommandCenterUsersIcon();
		logStep("Select client users tab in command center users page");
		clientUsersPage = commandCenterUsersPage.clickOnClientUsersTab();
		clientUsersPage.verifyClientUsersPageTitle();
		logStep("Select client user");
		clientUserDetailsPage = clientUsersPage.selectFirstUser();
		logStep("Select associate entity on client user details page and click on edit button of any role.");
		clientUserDetailsPage.selectEntity();
		editUserRolesAndPermissionsPage = clientUserDetailsPage.selectUserRolesAndClickEditButton(testData.getProperty("client.user.role.client.super.admin"));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit user roles & permissions popup")
	public void testEditUserRolesAndPermissionsPopup() throws Exception {
		logStep("Verify edit user roles and permissions popup");
		editUserRolesAndPermissionsPage.verifyPageTitle();
		editUserRolesAndPermissionsPage.verifyUserRoleLabelTextAndTextDefaultValue(testData.getProperty("client.user.role.client.super.admin"));
		editUserRolesAndPermissionsPage.verifyLocationsLabelTextAndDropDownDefaultValue();
		editUserRolesAndPermissionsPage.verifyCrossIconIsDisplayed();
		editUserRolesAndPermissionsPage.verifyCancelButtonIsDisplayed();
		editUserRolesAndPermissionsPage.verifySaveButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify cross icon and cancel button functionality on add user roles & permissions popup")
	public void testCrossIconAndCancelButtonFunctionality() throws Exception {
		logStep("Click the cancel button on the edit user roles & permissions pop up");
		clientUserDetailsPage = editUserRolesAndPermissionsPage.clickOnCancelButton();
		logStep("Verify user redirected to client user details page after clicking on cancel button");
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		logStep("Click on edit icon button on the edit user roles and permissions popup");
		editUserRolesAndPermissionsPage = clientUserDetailsPage.selectUserRolesAndClickEditButton(testData.getProperty("client.user.role.client.super.admin"));
		logStep("Click the cross icon on the edit user roles & permissions pop up");
		clientUserDetailsPage = editUserRolesAndPermissionsPage.clickOnCrossIcon();
		logStep("Verify user redirected to client user details page after clicking on cross icon");
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
	}
  
	
	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user select and deselect all locations checkboxes by check and uncheck select all checkbox on the add user roles & permissions popup")
	public void testSelectAllCheckboxFunctionality() throws Exception {
		logStep("Click on locations dropdown");
		editUserRolesAndPermissionsPage.clickOnLocationsDropdown();
		logStep("Verify that the user is able to select all location checkboxes using the select all checkbox");
		editUserRolesAndPermissionsPage.clickOnSelectAllCheckbox();
		editUserRolesAndPermissionsPage.verifyAllCheckboxesAreSelected();
		logStep("Verify that the user is able to deselect all selected location checkboxes by uncheck select all checkbox");
		editUserRolesAndPermissionsPage.clickOnSelectAllCheckbox();
		editUserRolesAndPermissionsPage.verifyAllCheckboxesAreNotSelected();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify clear and apply button functionality on locations dropdown")
	public void testClearAndApplyButtonFunctionalityInLocationsDropdown() throws Exception {
		logStep("Click on locations dropdown");
		editUserRolesAndPermissionsPage.clickOnLocationsDropdown();
		List<String> locations = new ArrayList<String>();
		locations.add(testData.getProperty("client.user.location.one"));
		locations.add(testData.getProperty("client.user.location.two"));
		logStep("Select locations from locations dropdown and click on apply button");
		editUserRolesAndPermissionsPage.selectLocations(locations);
		editUserRolesAndPermissionsPage.clickOnApplyButton();
		logStep("Verify selected locations are displayed in location dropdown with cross icon");
		editUserRolesAndPermissionsPage.verifySelectedLocationsAreDisplayedInLocationsDropdown(locations);
		logStep("Click on locations dropdown and Click on clear button");
		editUserRolesAndPermissionsPage.clickOnLocationsDropdown();
		editUserRolesAndPermissionsPage.clickOnClearButton();
		logStep("Verify selected locations are cleared after clicking on clear button");
		editUserRolesAndPermissionsPage.verifySelectedLocationsAreClearedInLocationsDropdown();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit user roles and permissions functionality")
	public void testEditUserRolesAndPermissionFunctionality() throws Exception {
		logStep("Click on locations dropdown");
		editUserRolesAndPermissionsPage.clickOnLocationsDropdown();
		List<String> locations = new ArrayList<String>();
		locations.add(testData.getProperty("client.user.location.one"));
		locations.add(testData.getProperty("client.user.location.two"));
		logStep("Select locations from locations dropdown and click on apply button");
		editUserRolesAndPermissionsPage.selectLocations(locations);
		editUserRolesAndPermissionsPage.clickOnApplyButton();
		logStep("Click on the save button on edit user roles and permissions popup");
		clientUserDetailsPage = editUserRolesAndPermissionsPage.clickOnSaveButton();
		CommonMethods commonMethods = new CommonMethods(driver);
		logStep("Verify success toaster");
		commonMethods.verifyToasterMessage("Roles and Permission has been successfully edited");
		logStep("Verify user redirected to client user details page");
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to clear selected locations using cross icon in locations dropdown")
	public void testClearSelectedLocationsUsingCrossIcon() throws Exception {
		logStep("Click on locations dropdown");
		editUserRolesAndPermissionsPage.clickOnLocationsDropdown();
		logStep("Verify locations checkbox are not by default selected");
		editUserRolesAndPermissionsPage.verifyAllCheckboxesAreNotSelected();
		List<String> locations = new ArrayList<String>();
		locations.add(testData.getProperty("client.user.location.one"));
		logStep("Select location from locations dropdown and click on apply button");
		editUserRolesAndPermissionsPage.selectLocations(locations);
		editUserRolesAndPermissionsPage.clickOnApplyButton();
		logStep("Click on cross icon and verify selected location is not displayed in locations dropdown");
		editUserRolesAndPermissionsPage.clearSearchedLocationsUsingCrossIcon(locations);
		editUserRolesAndPermissionsPage.verifySelectedLocationsAreClearedInLocationsDropdown();
	}
}
