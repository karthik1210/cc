// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 
package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.ClientUserDetailsPage;
import com.pxp.cc.objectmaps.ClientUsersPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.ManagePopupPage;
import com.pxp.cc.util.CommonMethods;

public class ClientUserManageAssociatedEntitiesPopupTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public ClientUsersPage clientUsersPage;
	public ClientUserDetailsPage clientUserDetailsPage;
	public ManagePopupPage managePopupPage;

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
		managePopupPage = clientUserDetailsPage.clickOnManageButton();
		managePopupPage.verifyManageAssociatedEntitiesPopupTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Manage Associated Entities popup")
	public void testManageAssociatedEntitiesPopup() throws Exception {
		logStep("Verify Manage Associated Entities popup");
		managePopupPage.verifySearchTextbox();
		managePopupPage.verifySaveApplyButton();
		managePopupPage.verifyCancelButton();
		managePopupPage.verifyCrossButton();
		managePopupPage.verifySystemFilter();
		managePopupPage.verifyEnterpriseFilter();
		managePopupPage.verifyPracticeFilter();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Manage Associated Entities popup selected entities should be same as Entities in Associated Entities container")
	public void testSelectedEntitiesShouldDisplayInAssciatedEntities() {
		logStep("Fetching list of selected Entities from Manage Associated Entities Pop up");
		String list = managePopupPage.fetchListOfSelectedEntities();
		clientUserDetailsPage = managePopupPage.clickOnCancelButton();
		logStep("Verfying fetched entities from Manage popup should be same as associated entities container");
		clientUserDetailsPage.verifySelectedEntitiesInManagePopupSameAsAssociatedEntities(list);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify search functionality of Manage Associated Entities popup")
	public void testSearchFunctionalityInManageTextBox() {
		logStep("Search the entity and check whether it is found or not.");
		managePopupPage.searchEntity(testData.getProperty("manage.search.entity"));
		logStep("Verify search result is displayed in web table or not");
		managePopupPage.verifySearchResultDisplayed(testData.getProperty("manage.search.entity"));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify after selecting to 'SELECT ALL' checkbox all entities selected and Save & Apply button enabled")
	public void testSelectAllEntitiesFunctionality() {
		logStep("Select All CheckBox is selected which selects all entities and Save/Apply button enabled.");
		logStep("verify select all checkbox is unchecked");
		managePopupPage.verifySelectAllCheckBoxInManagePopup("false");
		logStep("verify Save & Apply button is disabled and non-clickable");
		managePopupPage.verifySaveApplyButtonStatusInManagePopup("confirmbutton disabled");
		managePopupPage.clickToSelectAllCheckBox();
		logStep("verify select all checkbox is checked");
		managePopupPage.verifySelectAllCheckBoxInManagePopup("true");
		logStep("verify Save & Apply button is enabled and clickable");
		managePopupPage.verifySaveApplyButtonStatusInManagePopup("confirmbutton");

	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify cross icon and cancel button functionality on manage associated entities popup")
	public void testCrossIconAndCancelButtonFunctionality() throws Exception {
		logStep("Click the cancel button on manage associated entities pop up");
		clientUserDetailsPage = managePopupPage.clickOnCancelButton();
		logStep("Verify user redirected to client user details page after clicking on cancel button");
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		logStep("Click on manage button on the client users details page.");
		managePopupPage = clientUserDetailsPage.clickOnManageButton();
		logStep("Click the cross icon on the edit user roles & permissions pop up");
		clientUserDetailsPage = managePopupPage.clickOnCloseWindow();
		logStep("Verify user redirected to client user details page after clicking on cross icon");
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new entity from non-selected entities list by selecting it's checkbox")
	public void testAddNewEntityInManagePopUp() {
		logStep("Add a new entity from non-selected entities list by selecting it's checkbox");
		managePopupPage.fetchListOfNonSelectedEntities(testData.getProperty("addNewEntity"));
		logStep("verifying Save & Apply buuton enabled and clickable after selecting new entity");
		managePopupPage.verifySaveApplyButtonStatusInManagePopup("confirmbutton");
		logStep("click to save & apply button and navigate to client users details page.");
		clientUserDetailsPage = managePopupPage.clickOnSaveApplyButton();
		logStep("Verify user redirected to client user details page after clicking on save and apply button");
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		CommonMethods commonMethods = new CommonMethods(driver);
		logStep("Verify success toaster");
		commonMethods.verifyToasterMessage("Success! Entities have been updated");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify existing entity should be from selected entities list by selecting it's checkbox")
	public void testRemoveExistingEntityInManagePopUp() {
		logStep("Remove existing entity from selected entities list by selecting it's checkbox");
		managePopupPage.fetchListOfSelectedEntities(testData.getProperty("addNewEntity"));
		logStep("verifying Save & Apply buuton enabled and clickable after selecting new entity");
		managePopupPage.verifySaveApplyButtonStatusInManagePopup("confirmbutton");
		logStep("click to save & apply button and navigate to client users details page.");
		clientUserDetailsPage = managePopupPage.clickOnSaveApplyButton();
		logStep("Verify user redirected to client user details page after clicking on save and apply button");
		clientUserDetailsPage.verifyClientUserDetailsPageTitle();
		CommonMethods commonMethods = new CommonMethods(driver);
		logStep("Verify success toaster");
		commonMethods.verifyToasterMessage("Success! Entities have been updated");
	}
}
