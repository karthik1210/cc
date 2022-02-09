// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 
package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.ClientUserDetailsPage;
import com.pxp.cc.objectmaps.ClientUsersPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.ManageAssociatedEntitiesPage;
import com.pxp.cc.util.CommonMethods;

public class ClientUserManageAssociatedEntitiesPopupTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public ClientUsersPage clientUsersPage;
	public ClientUserDetailsPage clientUserDetailsPage;
	public ManageAssociatedEntitiesPage manageAssociatedEntitiesPage;

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
		logStep("Verify manage button is present and click to manage button");
		manageAssociatedEntitiesPage = clientUserDetailsPage.clickOnManageButton();
		manageAssociatedEntitiesPage.verifyManageAssociatedEntitiesPopupTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Manage Associated Entities popup")
	public void testManageAssociatedEntitiesPopup() throws Exception {
		logStep("Verify Manage Associated Entities popup");
		manageAssociatedEntitiesPage.verifySearchTextbox();
		manageAssociatedEntitiesPage.verifySaveApplyButton();
		manageAssociatedEntitiesPage.verifyCancelButton();
		manageAssociatedEntitiesPage.verifyCrossButton();
		manageAssociatedEntitiesPage.verifySystemFilter();
		manageAssociatedEntitiesPage.verifyEnterpriseFilter();
		manageAssociatedEntitiesPage.verifyPracticeFilter();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Manage Associated Entities popup selected entities should be same as Entities in Associated Entities container")
	public void testSelectedEntitiesShouldDisplayInAssociatedEntitiesContainer() {
		logStep("Fetching list of selected Entities from Manage Associated Entities Pop up");
		String list = manageAssociatedEntitiesPage.fetchListOfSelectedEntities();
		clientUserDetailsPage = manageAssociatedEntitiesPage.clickOnCancelButton();
		logStep("Verfying fetched entities from Manage popup should be same as associated entities container");
		clientUserDetailsPage.verifySelectedEntitiesInManagePopupSameAsAssociatedEntities(list);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search functionality of Manage Associated Entities popup")
	public void testSearchFunctionalityInManageAssociatedEntitiesPopup() {
		logStep("Search the entity and check whether it is found or not.");
		manageAssociatedEntitiesPage.searchEntity(testData.getProperty("manage.search.entity"));
		logStep("Verify search result is displayed in web table or not");
		manageAssociatedEntitiesPage.verifySearchResultDisplayed(testData.getProperty("manage.search.entity"));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify after selecting to 'SELECT ALL' checkbox all entities selected and Save & Apply button enabled")
	public void testSelectAllEntitiesFunctionality() {
		logStep("verify select all checkbox is unchecked");
		manageAssociatedEntitiesPage.verifySelectAllCheckBoxStateInManagePopup("false");
		logStep("verify Save & Apply button is disabled.");
		manageAssociatedEntitiesPage.verifySaveApplyButtonStatusInManagePopup("confirmbutton disabled");
		manageAssociatedEntitiesPage.clickToSelectAllCheckBox();
		logStep("verify select all checkbox is checked");
		manageAssociatedEntitiesPage.verifySelectAllCheckBoxStateInManagePopup("true");
		logStep("verify Save & Apply button is enabled.");
		manageAssociatedEntitiesPage.verifySaveApplyButtonStatusInManagePopup("confirmbutton");

	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify cross icon and cancel button functionality on manage associated entities popup")
	public void testCrossIconAndCancelButtonFunctionality() throws Exception {
		logStep("Click the cancel button on manage associated entities pop up");
		clientUserDetailsPage = manageAssociatedEntitiesPage.clickOnCancelButton();
		logStep("Verify user redirected to client user details page after clicking on cancel button");
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		logStep("Click on manage button on the client users details page.");
		manageAssociatedEntitiesPage = clientUserDetailsPage.clickOnManageButton();
		logStep("Click the cross icon on the edit user roles & permissions pop up");
		clientUserDetailsPage = manageAssociatedEntitiesPage.clickOnCloseWindow();
		logStep("Verify user redirected to client user details page after clicking on cross icon");
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new entity from non-selected entities list by selecting it's checkbox")
	public void testAddNewEntityInManagePopUp() {
		logStep("Add a new entity from non-selected entities list by selecting it's checkbox");
		manageAssociatedEntitiesPage.addNonSelectedEntities(testData.getProperty("new.entity"));
		logStep("Verify Save & Apply button is enabled");
		manageAssociatedEntitiesPage.verifySaveApplyButtonStatusInManagePopup("confirmbutton");
		logStep("click to save & apply button and navigate to client users details page.");
		clientUserDetailsPage = manageAssociatedEntitiesPage.clickOnSaveApplyButton();
		logStep("Verify user redirected to client user details page after clicking on save and apply button");
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		CommonMethods commonMethods = new CommonMethods(driver);
		logStep("Verify success toaster");
		commonMethods.verifyToasterMessage("Success! Entities have been updated");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify existing entity should be removed from selected entities list by selecting it's checkbox")
	public void testRemoveExistingEntityInManagePopUp() {
		logStep("Remove existing entity from selected entities list by selecting it's checkbox");
		manageAssociatedEntitiesPage.removeSelectedEntities(testData.getProperty("new.entity"));
		logStep("Verify Save & Apply button is enabled");
		manageAssociatedEntitiesPage.verifySaveApplyButtonStatusInManagePopup("confirmbutton");
		logStep("click to save & apply button and navigate to client users details page.");
		clientUserDetailsPage = manageAssociatedEntitiesPage.clickOnSaveApplyButton();
		logStep("Verify user redirected to client user details page after clicking on save and apply button");
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		CommonMethods commonMethods = new CommonMethods(driver);
		logStep("Verify success toaster");
		commonMethods.verifyToasterMessage("Success! Entities have been updated");
	}
}
