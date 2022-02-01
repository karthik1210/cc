// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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
import com.pxp.cc.objectmaps.SearchOptionsPage;

public class ClientUsersDetailsSelfViewTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public ClientUsersPage clientUsersPage;
	public SearchOptionsPage searchOptionsPage;
	public ClientUserDetailsPage clientUserDetailsPage;
	public List<String> userStatusList = null;
	public AddUserRolesAndPermissionsPage addUserRolesAndPermissionsPage;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		commandCenterUsersPage = clientDirectoryPage.clickOnCommandCenterUsersIcon();
		logStep("Select client users tab in command center users page");
		clientUsersPage = commandCenterUsersPage.clickOnClientUsersTab();
		clientUsersPage.verifyClientUsersPageTitle();
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for self view all the labels on client user details page")
	public void testClientUsersDetailsSelfViewLabels() throws Exception {
		logStep("Select client user");
		clientUsersPage.selectUser();
		logStep("Verify self view client user labels");
		clientUserDetailsPage.verifyClientUserSelfViewLabels();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for self view last login date, enrollment status & user status all the field is present on client user details page")
	public void testClientUserLastLoginDateEnrollmentAndUserStatusInSelfViewMode() throws Exception {
		logStep("Select client user");
		clientUsersPage.selectUser();
		logStep("Verify all the fields on client user details page");
		clientUserDetailsPage.verifyClientLastLoginDateEnrollmentAndStatusIsBlankInSelfViewMode();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "verify user redirected to client users page after clicking on back arrow on client user details page")
	public void testClientUsersDetailsBackArrowFunctionality() throws Exception {
		logStep("Select client user");
		clientUsersPage.selectUser();
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		logStep("Verify after clicking on back arrow button from client user details page user redirected to client users page");
		clientUserDetailsPage.clickBackArrowOnClientUserDetailsPage();
		clientUsersPage.verifyClientUsersPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify kebab menu options is displayed with self user login on client user details page")
	public void testClientUsersKebabMenuWithSelfView() throws Exception {
		logStep("Select client user");
		clientUsersPage.selectUser();
		clientUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify self user display the kebab menu options on client user page");
		clientUserDetailsPage.verifyClientUserKebabMenuOptionsDisplayedForSelfView();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for active user status kebab menu options displayed to self user")
	public void testKebabMenuOptionsDisplayedToSelfUserWithActiveStatus() throws Exception {
		logStep("Verify kebab menu option for active user on client user details page for self view");
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = clientUsersPage.getClientUserStatus();
		logStep("Select client user");
		clientUsersPage.selectUser();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage(userStatus);
		clientUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify deactivate user & send enrollment email options are not enable in kebab menu");
		clientUserDetailsPage.verifyActivateOrDeactivateOptionDisabledToSelfUser();
		clientUserDetailsPage.verifySendEnrollmentOptionInKebabMenuToSelfUser();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for inactive user status kebab menu options displayed to self user")
	public void testKebabMenuOptionsDisplayedToSelfUserForInactiveStatus() throws Exception {
		logStep("Verify kebab menu option for self view to the inactive user on client user details page");
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.inactive"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = clientUsersPage.getClientUserStatus();
		logStep("Select client user");
		clientUsersPage.selectUser();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage(userStatus);
		clientUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify activate user option should display & send enrollment email option shouldn't displayed in kebab menu");
		clientUserDetailsPage.verifyActivateOrDeactivateOptionDisabledToSelfUser();
		clientUserDetailsPage.verifySendEnrollmentOptionInKebabMenuToSelfUser();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for locked out user status kebab menu options displayed to self user")
	public void testKebabMenuOptionsDisplayedToSelfUserWithLockedStatus() throws Exception {
		logStep("Verify self view for locked user on client user page");
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		logStep("search and select user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.lockedout"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = clientUsersPage.getClientUserStatus();
		logStep("Select client user");
		clientUsersPage.selectUser();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage(userStatus);
		clientUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify unlock & send enrollment email options should not displayed in kebab menu");
		clientUserDetailsPage.verifyLockAndUnlockOptionDisplayedToSelfUser();
		clientUserDetailsPage.verifySendEnrollmentOptionInKebabMenuToSelfUser();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for no roles assiged user status kebab menu options displayed to self user")
	public void testKebabMenuOptionsDisplayedToSelfUserForNoRolesAssignedStatus() throws Exception {
		logStep("Verify self view for no roles assigned user on client user page");
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.no.roles.assigned"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = clientUsersPage.getClientUserStatus();
		logStep("Select client user");
		clientUsersPage.selectUser();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage(userStatus);
		clientUserDetailsPage.clickOnKebabMenuButton();
		logStep("Verify send enrollment email options should not displayed in kebab menu");
		clientUserDetailsPage.verifySendEnrollmentOptionInKebabMenuToSelfUser();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button is displayed & fields are editable")
	public void testEditButtonEnabledToSelfUserForDemographicDetails() throws Exception {
		logStep("Verify self view for no roles assigned user on client user page");
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = clientUsersPage.getClientUserStatus();
		logStep("Select client user");
		clientUsersPage.selectUser();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage(userStatus);
		logStep("Verifying demographic fields are editable to self user");
		clientUserDetailsPage.verifykEditDemographicDetailsInClientUserDetailsPage();
		logStep("Clicked on edit button on client user details page");
		clientUserDetailsPage.clickEditInClientUser();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for self view all the labels on client user details page")
	public void testClientUserConnectedResourceLinkInSelfViewMode() throws Exception {
		logStep("Select client user");
		clientUsersPage.selectUser();
		logStep("Verify Connected Resource link in self view mode on client user details page");
		clientUserDetailsPage.verifyConnectedResourceLinkIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Associated Entities container elements in client users details page")
	public void testManageButtonInsideAssociatedEntitiesContainer() throws Exception {
		logStep("Select client user");
		clientUsersPage.selectUser();
		logStep("Verify under Associated Entities 'Mangae' button is displayed in self view mode");
		clientUserDetailsPage.verifyAssociateEntitiesTitle();
		clientUserDetailsPage.verifyManageButtonPresentOnAssociatedEntitiesTab();
		clientUserDetailsPage.verifySearchTextbox();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search functionality in Associated Entities container")
	public void testSearchFunctionalityOfAssociatedEntities() throws Exception {
		logStep("Select client user");
		clientUsersPage.selectUser();
		logStep("Verify search entity textbox");
		clientUserDetailsPage.searchEntity(testData.getProperty("search.entity"));
		logStep("Verify search result is displayed in web table or not");
		clientUserDetailsPage.verifySearchResultDisplayed(testData.getProperty("search.entity"));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for self view add button is displayed")
	public void testAddUserRolesAndPermissionsPopup() throws Exception {
		logStep("Select client user");
		clientUsersPage.selectUser();
		logStep("Verify add button is displayed in self view mode inside user roles & permissions page");
		addUserRolesAndPermissionsPage.verifyPageTitle();
		addUserRolesAndPermissionsPage.verifyAddButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for self view locked status on hover on client user details page")
	public void testHoverMessageForClientUsersOnLockedStatusSelfView() throws Exception {
		logStep("Verify self view for locked user on client user page");
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.lockedout"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = clientUsersPage.getClientUserStatus();
		logStep("Select client user");
		clientUsersPage.selectUser();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage(userStatus);
		clientUserDetailsPage.verifyHoverOverMessageOnLockedUserStatus();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for self view active status on hover on client user details page")
	public void testHoverMessageForClientUsersOnActiveStatusSelfView() throws Exception {
		logStep("Verify self view for locked user on client user page");
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = clientUsersPage.getClientUserStatus();
		logStep("Select client user");
		clientUsersPage.selectUser();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage(userStatus);
		clientUserDetailsPage.verifyHoverOverMessageOnActiveUserStatus();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for self view inactive status on hover on client user details page")
	public void testHoverMessageForClientUsersOnInactiveStatusSelfView() throws Exception {
		logStep("Verify self view for locked user on client user page");
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.inactive"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = clientUsersPage.getClientUserStatus();
		logStep("Select client user");
		clientUsersPage.selectUser();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage(userStatus);
		clientUserDetailsPage.verifyHoverOverMessageOnInactiveUserStatus();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for self view no role assigned status on hover on client user details page")
	public void testHoverMessageForClientUsersOnNoRoleAssignedStatusSelfView() throws Exception {
		logStep("Verify self view for locked user on client user page");
		searchOptionsPage = clientUsersPage.clickOnSearchOptionPage();
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.no.roles.assigned"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		String userStatus = clientUsersPage.getClientUserStatus();
		logStep("Select client user");
		clientUsersPage.selectUser();
		logStep("Verify selected client user status is same on client user details page");
		clientUserDetailsPage.verifySelectedUserStatusIsSameOnClientUserDetailsPage(userStatus);
		clientUserDetailsPage.verifyHoverOverMessageOnNoRoleAssignedUserStatus();
	}

}
