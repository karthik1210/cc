// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.InternalUserDetailsPage;
import com.pxp.cc.objectmaps.InternalUsersPage;
import com.pxp.cc.objectmaps.SearchOptionsPage;

public class InternalUsersDetailsAdminViewTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public InternalUsersPage internalUsersPage;
	public SearchOptionsPage searchOptionsPage;
	public InternalUserDetailsPage internalUserDetailsPage;
	public List<String> userStatusList = null;
	public List<String> enrollmentStatusList = null;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify admin view on internal user details page")
	public void testInternalUsersDetailsAdminViewLabels() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		commandCenterUsersPage = clientDirectoryPage.clickOnCommandCenterUsersIcon();
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click any one of the user name from table");
		internalUserDetailsPage = internalUsersPage.clickOneOfTheUserNameFromTable();
		logStep("Verify admin view internal user labels");
		internalUserDetailsPage.verifyInternalUserAdminViewLabels();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify kebab menu options is displayed with admin login on internal user details page")
	public void testInternalUsersKebabMenuWithAdminView() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		commandCenterUsersPage = clientDirectoryPage.clickOnCommandCenterUsersIcon();
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click any one of the user name from table");
		internalUserDetailsPage = internalUsersPage.clickOneOfTheUserNameFromTable();
		logStep("Verify admin view internal user labels");
		internalUserDetailsPage.verifyKebabMenuButtonForAdminView();
		internalUserDetailsPage.verifyKebabMenuOptionsForAdminView();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify for different user status kebab menu options displayed to admin user")
	public void testKebabMenuOptionsDisplayedToAdminUser() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		commandCenterUsersPage = clientDirectoryPage.clickOnCommandCenterUsersIcon();
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click any one of the user name from table");
		internalUserDetailsPage = internalUsersPage.clickOneOfTheUserNameFromTable();
		logStep("Verify admin view internal user labels");
		internalUserDetailsPage.verifyKebabMenuOptionsDisplayed();
	}

}
