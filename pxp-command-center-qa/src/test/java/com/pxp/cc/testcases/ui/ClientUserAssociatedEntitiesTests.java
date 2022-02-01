// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 
package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.ClientUserDetailsPage;
import com.pxp.cc.objectmaps.ClientUsersPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;

public class ClientUserAssociatedEntitiesTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public ClientUsersPage clientUsersPage;
	public ClientUserDetailsPage clientUserDetailsPage;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to Command Center portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		commandCenterUsersPage = clientDirectoryPage.clickOnCommandCenterUsersIcon();
		logStep("Select client users tab in command center users page");
		clientUsersPage = commandCenterUsersPage.clickOnClientUsersTab();
		clientUsersPage.verifyClientUsersPageTitle();
		logStep("Select client user");
		clientUserDetailsPage = clientUsersPage.selectUser();
		logStep("Verify Asscoiated Entities container is present with title 'Associated Entities'");
		clientUserDetailsPage.verifyAssociateEntitiesTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Associated Entities container elements in client users details page")
	public void testAssociatedEntitiesContainer() throws Exception {
		logStep("Verify Associated Entities container elements");
		clientUserDetailsPage.verifyManageButtonPresentOnAssociatedEntitiesTab();
		clientUserDetailsPage.verifySearchTextbox();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search functionality in Associated Entities container")
	public void testSearchFunctionality() throws Exception {
		logStep("Verify search entity textbox");
		clientUserDetailsPage.searchEntity(testData.getProperty("search.entity"));
		logStep("Verify search result is displayed in web table or not");
		clientUserDetailsPage.verifySearchResultDisplayed(testData.getProperty("search.entity"));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify User roles and it's location should display after clicking to any entity in Associated Entities container")
	public void testDisplayUserRolesAndLocationOfClickedAssociatedEntity() throws Exception {
		logStep("click to desired associated entity");
		clientUserDetailsPage.clickDesiredAssociatedEntity("search.entity");
		logStep("Verify after clicking it's corresponding user roles and location is displayed or not");
		clientUserDetailsPage.displayAssociatedEntityUserRoleAndLocation();
	}
}
