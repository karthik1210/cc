// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddAndUpdateProductSolutionsPage;
import com.pxp.cc.objectmaps.AddOrgNodePage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class AddOrgNodeTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public AddOrgNodePage addOrgNodePage;
	public GeneralSettingsPage generalSettingsPage;
	public CommonMethods commonMethods;
	public AddAndUpdateProductSolutionsPage addAndUpdateProductSolutionsPage;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(testData.getProperty("base.url"), testData.getProperty("user.name"),
				testData.getProperty("user.password"));
		commonMethods = new CommonMethods(driver);
	}

	@Test(priority = 0, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new system pop up")
	public void testAddNewSystemPopup() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Fetch client name,sap id,client timezone and client status values from general settings page");
		String parentSapId = generalSettingsPage.getClientSapId();
		String parentTimezone = generalSettingsPage.getClientTimezone();
		String parentStatus = generalSettingsPage.getClientStatus();
		String clientName = generalSettingsPage.getClientName();
		logStep("Verify Parent Entity dropdown functionality on add new system pop up");
		addOrgNodePage = generalSettingsPage.clickOnAddSystem();
		addOrgNodePage.verifyParentEntityDropdownisReadonlyField();
		addOrgNodePage.verifyClientNameIsDisplayedOnParentEntityDropdown(clientName);
		logStep("Verify sap id,timezone and status value fetched from selected parent(client) at add new system popup");
		addOrgNodePage.verifyTestDataFetchedFromSelectedParent(parentSapId, parentTimezone, parentStatus);
		logStep("Verify add system node popup page title and labels present on add new system popup");
		addOrgNodePage.verifyLabelPresentOnAddNewNodePopup(testData.getProperty("api.system.orgtype"));
		logStep("Verify timezone dropdown values");
		commonMethods.getTimezoneDropdownValue();
		commonMethods.verifyTimezoneDropdownValues();
		logStep("Verify box text displayed for textbox field on add new system popup");
		addOrgNodePage.verifyBoxTextForNameField();
		addOrgNodePage.verifyBoxTextForSolutionDisplayNameField();
		addOrgNodePage.verifyBoxTextForSapIdField();
		addOrgNodePage.verifyBoxTextForTimezoneField();
		logStep("Verify after clicking on cancel button user redirected to general settings page");
		addOrgNodePage.clickOnCancelButton();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Click on system option in new drop down");
		generalSettingsPage.clickOnAddSystem();
		logStep("Verify after clicking on X icon user redirected to general settings page");
		addOrgNodePage.clickOnCrossIcon();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
	}

	@Test(priority = 1, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Error messages are displayed on add new system popup")
	public void testErrorMessagesAtAddNewSystem() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on system option in new dropdown");
		addOrgNodePage = generalSettingsPage.clickOnAddSystem();
		String systemNameLabelText = addOrgNodePage.getSystemNameLabelText();
		logStep("Verify error messages are displayed for mandatory field");
		addOrgNodePage.clickOnSaveAndAddButton();
		addOrgNodePage.verifyOrgNameIsRequiredField(systemNameLabelText);
		addOrgNodePage.enterSpaceInRequiredField();
		commonMethods.verifyToasterMessage("System name cannot be empty");
		commonMethods.closeToasterMessage();
		logStep("Click on add new system button");
		generalSettingsPage.clickOnAddSystem();
		logStep("Enter more than 100 characters in system name field and verify error message for system name field");
		addOrgNodePage.verifyErrorMessageForOrgNodeNameField(testData.getProperty("invalid.name"));
		logStep("Enter more than 100 characters in solution display name field and verify error message for solution display name field");
		addOrgNodePage
				.verifyErrorMessageForSolutionDisplayNameField(testData.getProperty("invalid.solutionDisplayName"));
		logStep("Enter more than 50 characters in sap id field and verify error message for sap id field");
		addOrgNodePage.verifyErrorMessageForSapIdField(testData.getProperty("invalid.sap"));
	}

	@Test(priority = 2, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add active system below inactive client and status of newly added node is by default inactive")
	public void testAddNewActiveSystemBelowInactiveClient() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.inactive.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		String parentSapId = generalSettingsPage.getClientSapId();
		String parentTimezone = generalSettingsPage.getClientTimezone();
		logStep("Click on system option in new dropdown");
		addOrgNodePage = generalSettingsPage.clickOnAddSystem();
		logStep("Enter details in add new system popup");
		String systemName = testData.getProperty("api.system.name").concat(CCUtils.generateAlphaNumericString(5));
		addOrgNodePage.enterOrgName(systemName);
		logStep("Click on active radio button in add new system popup");
		addOrgNodePage.selectActiveRadiobutton();
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added system node displayed on general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(systemName, parentSapId, systemName,
				parentTimezone);
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(systemName, false);
	}

	@Test(priority = 3, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add inactive system below active client")
	public void testAddNewInactiveSystemBelowActiveClient() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on system option in new dropdown");
		addOrgNodePage = generalSettingsPage.clickOnAddSystem();
		logStep("Enter details in add new system popup");
		addOrgNodePage.createNewOrgNode(testData.getProperty("new.inactive.system.name"),
				testData.getProperty("new.inactive.system.sapid"), testData.getProperty("new.inactive.system.timezone"),
				testData.getProperty("new.inactive.system.solutiondisplayname"));
		logStep("Click on inactive radio button in add new system popup");
		addOrgNodePage.selectInactiveRadiobutton();
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added system node is displayed on general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(
				testData.getProperty("new.inactive.system.name"), testData.getProperty("new.inactive.system.sapid"),
				testData.getProperty("new.inactive.system.solutiondisplayname"),
				testData.getProperty("new.inactive.system.timezone"));
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(false);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(testData.getProperty("new.inactive.system.name"),
				false);
	}

	@Test(priority = 4, enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in add new system popup and verify add new system functionality")
	public void testAddNewActiveSystemBelowActiveClient() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on Add new system option");
		addOrgNodePage = generalSettingsPage.clickOnAddSystem();
		logStep("Enter details in add new system popup");
		addOrgNodePage.createNewOrgNode(testData.getProperty("new.system.name"),
				testData.getProperty("new.system.sapid"), testData.getProperty("new.system.timezone"),
				testData.getProperty("new.system.solutiondisplayname"));
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added system node displayed in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(testData.getProperty("new.system.name"),
				testData.getProperty("new.system.sapid"), testData.getProperty("new.system.solutiondisplayname"),
				testData.getProperty("new.system.timezone"));
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(testData.getProperty("new.system.name"), true);
	}

	@Test(priority = 5, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new enterprise popup")
	public void testAddNewEnterprisePopup() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on enterprise option in new dropdown");
		addOrgNodePage = generalSettingsPage.clickOnAddEnterprise();
		logStep("Verify sap id, timezone and status fields are blank, if the parent is not selected");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		Boolean isParentEntityNameNotSelected = addOrgNodePage.verifyValueIsNotSelectedInParentEntity();
		addOrgNodePage.verifyDataIsNotFetchedIfParentEntityNotSelected(isParentEntityNameNotSelected);
		logStep("Verify add enterprise node popup page title and labels present on add new enterprise popup");
		addOrgNodePage.verifyLabelPresentOnAddNewNodePopup(testData.getProperty("api.enterprise.orgtype"));
		logStep("Verify active radio button is by default selected");
		addOrgNodePage.verifyActiveButtonByDefaultSelected();
		logStep("Verify timezone dropdown value");
		commonMethods.getTimezoneDropdownValue();
		commonMethods.verifyTimezoneDropdownValues();
		logStep("Verify box text displayed for textbox fields on add new enterprise popup");
		addOrgNodePage.verifyBoxTextForNameField();
		addOrgNodePage.verifyBoxTextForSolutionDisplayNameField();
		addOrgNodePage.verifyBoxTextForSapIdField();
		addOrgNodePage.verifyBoxTextForParentEntityField();
		addOrgNodePage.verifyBoxTextForTimezoneField();
		logStep("Verify after clicking on cancel button user redirected to general settings page");
		addOrgNodePage.clickOnCancelButton();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Click on enterprise option in new dropdown");
		generalSettingsPage.clickOnAddEnterprise();
		logStep("Verify after clicking on X icon user redirected to general settings page");
		addOrgNodePage.clickOnCrossIcon();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		generalSettingsPage.searchNode(testData.getProperty("new.system.name"));
		logStep("Get system name, sap id, timezone and status values from system node in general settings page");
		String parentEntitySystemName = generalSettingsPage.getOrgName();
		String parentEntitySystemSapId = generalSettingsPage.getOrgSapId();
		String parentEntitySystemTimezone = generalSettingsPage.getOrgTimezone();
		String parentEntitySystemStatus = generalSettingsPage.getOrgStatus();
		logStep("Click on enterprise option in new dropdown");
		generalSettingsPage.clickOnAddEnterprise();
		logStep("Select system name in parent entity drop down");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(parentEntitySystemName);
		logStep("Verify sap id, timezone and status values inherited from selected parent entity(system) in add new enterprise popup");
		addOrgNodePage.verifyTestDataFetchedFromSelectedParent(parentEntitySystemSapId, parentEntitySystemTimezone,
				parentEntitySystemStatus);
	}

	@Test(priority = 6, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify error messages are displayed on add new enterprise popup")
	public void testErrorMessagesAtAddNewEnterprise() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on enterprise option in new dropdown");
		addOrgNodePage = generalSettingsPage.clickOnAddEnterprise();
		String enterpriseNameLabelText = addOrgNodePage.getEnterpriseNameLabelText();
		logStep("Verify parent entity is required field");
		addOrgNodePage.clickOnSaveAndAddButton();
		addOrgNodePage.verifyParentEntityNameIsRequiredField();
		logStep("Verify Enterprise Name is required field");
		addOrgNodePage.verifyOrgNameIsRequiredField(enterpriseNameLabelText);
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(testData.getProperty("new.system.name"));
		addOrgNodePage.enterSpaceInRequiredField();
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Enterprise name cannot be empty");
		commonMethods.closeToasterMessage();
		logStep("Click on add new enterprise button");
		generalSettingsPage.clickOnAddEnterprise();
		logStep("Enter random text in parent entity textbox");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName("abcdef@##24");
		logStep("Verify No Items Found message is displayed for parent entity field on add new enterprise pop up");
		addOrgNodePage.verifyNoItemsFound();
		logStep("Enter more than 100 characters in enterprise name field and verify error message for system name field");
		addOrgNodePage.verifyErrorMessageForOrgNodeNameField(testData.getProperty("invalid.name"));
		logStep("Enter more than 100 characters in solution display name field and verify error message for solution display name field");
		addOrgNodePage
				.verifyErrorMessageForSolutionDisplayNameField(testData.getProperty("invalid.solutionDisplayName"));
		logStep("Enter more than 50 characters in sap id field and verify error message for sap id field");
		addOrgNodePage.verifyErrorMessageForSapIdField(testData.getProperty("invalid.sap"));
	}

	@Test(priority = 7, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new enterprise functionality")
	public void testAddNewActiveEnterpriseBelowActiveSystem() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on enterprise option in new dropdown");
		addOrgNodePage = generalSettingsPage.clickOnAddEnterprise();
		logStep("Enter details in add new enterprise pop up");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(testData.getProperty("new.system.name"));
		addOrgNodePage.createNewOrgNode(testData.getProperty("new.enterprise.name"),
				testData.getProperty("new.enterprise.sapid"), testData.getProperty("new.enterprise.timezone"),
				testData.getProperty("new.enterprise.solutiondisplayname"));
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added enterprise node displayed on general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(
				testData.getProperty("new.enterprise.name"), testData.getProperty("new.enterprise.sapid"),
				testData.getProperty("new.enterprise.solutiondisplayname"),
				testData.getProperty("new.enterprise.timezone"));
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(testData.getProperty("new.enterprise.name"), true);
	}

	@Test(priority = 8, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add active enterprise below inactive system and status of newly added node is by default inactive")
	public void testAddNewActiveEnterpriseBelowInactiveSystem() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.searchNode(testData.getProperty("new.inactive.system.name"));
		logStep("Fetch system sap id, timezone values from general settings page");
		String parentSapId = generalSettingsPage.getOrgSapId();
		String parentTimezone = generalSettingsPage.getOrgTimezone();
		logStep("Click on enterprise option in new dropdown");
		addOrgNodePage = generalSettingsPage.clickOnAddEnterprise();
		logStep("Add new active enterprise node below inactive system node");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(testData.getProperty("new.inactive.system.name"));
		String enterpriseName = testData.getProperty("api.enterprise.orgtype")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		addOrgNodePage.enterOrgName(enterpriseName);
		addOrgNodePage.selectActiveRadiobutton();
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added enterprise node displayed on general settings page and status of enterprise node is inactive");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(enterpriseName, parentSapId,
				enterpriseName, parentTimezone);
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(enterpriseName, false);
	}

	@Test(priority = 9, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add inactive enterprise node below active system. ")
	public void testAddNewInactiveEnterpriseBelowActiveSystem() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on enterprise option in new dropdown");
		addOrgNodePage = generalSettingsPage.clickOnAddEnterprise();
		logStep("Add new inactive enterprise node below active system node");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(testData.getProperty("new.system.name"));
		addOrgNodePage.createNewOrgNode(testData.getProperty("new.inactive.enterprise.name"),
				testData.getProperty("new.inactive.enterprise.sapid"),
				testData.getProperty("new.inactive.enterprise.timezone"),
				testData.getProperty("new.inactive.enterprise.solutiondisplayname"));
		addOrgNodePage.selectInactiveRadiobutton();
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added enterprise node displayed on general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(
				testData.getProperty("new.inactive.enterprise.name"),
				testData.getProperty("new.inactive.enterprise.sapid"),
				testData.getProperty("new.inactive.enterprise.solutiondisplayname"),
				testData.getProperty("new.inactive.enterprise.timezone"));
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(false);
		generalSettingsPage
				.verifyNewlyAddedOrgNodeDetailsInOrgTree(testData.getProperty("new.inactive.enterprise.name"), false);
	}

	@Test(priority = 10, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new practice popup")
	public void testAddNewPracticePopup() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Get client name, sap id, timezone and status values from client node in general settings page");
		String parentName = generalSettingsPage.getClientName();
		String parentSapId = generalSettingsPage.getClientSapId();
		String parentTimezone = generalSettingsPage.getClientTimezone();
		String parentStatus = generalSettingsPage.getClientStatus();
		logStep("Click on practice option in new dropdown");
		addOrgNodePage = generalSettingsPage.clickOnAddPractice();
		logStep("Verify sap id, timezone and status fields are blank, if the parent is not selected");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		Boolean isParentEntityNameNotSelected = addOrgNodePage.verifyValueIsNotSelectedInParentEntity();
		addOrgNodePage.verifyDataIsNotFetchedIfParentEntityNotSelected(isParentEntityNameNotSelected);
		logStep("Verify add practice node popup page title and labels present on add new practice popup");
		addOrgNodePage.verifyLabelPresentOnAddNewNodePopup(testData.getProperty("api.practice.orgtype"));
		logStep("Verify active radio button is by default selected");
		addOrgNodePage.verifyActiveButtonByDefaultSelected();
		logStep("Verify solutions value");
		addOrgNodePage.getSolutionValues();
		addOrgNodePage.verifySolutionValues();
		logStep("Verify timezone dropdown value");
		commonMethods.getTimezoneDropdownValue();
		commonMethods.verifyTimezoneDropdownValues();
		logStep("Verify box text displayed for textbox field on add new practice popup");
		addOrgNodePage.verifyBoxTextForNameField();
		addOrgNodePage.verifyBoxTextForSolutionDisplayNameField();
		addOrgNodePage.verifyBoxTextForSapIdField();
		addOrgNodePage.verifyBoxTextForParentEntityField();
		addOrgNodePage.verifyBoxTextForTimezoneField();
		logStep("Verify after clicking on cancel button user redirected to general settings page");
		addOrgNodePage.clickOnCancelButton();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		logStep("Verify after clicking on X icon user redirected to general settings page");
		addOrgNodePage.clickOnCrossIcon();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		generalSettingsPage.searchNode(testData.getProperty("new.enterprise.name"));
		logStep("Get enterprise name, sap id, timezone and status values from enterpeise node in general settings page");
		String parentEntityEnterpriseName = generalSettingsPage.getOrgName();
		String parentEntityEnterpriseSapId = generalSettingsPage.getOrgSapId();
		String parentEntityEnterpriseTimezone = generalSettingsPage.getOrgTimezone();
		String parentEntityEnterpriseStatus = generalSettingsPage.getOrgStatus();
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		logStep("Verify sap id, timezone and status value fetch from selected parent (enterprise) in add new practice popup");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(parentEntityEnterpriseName);
		addOrgNodePage.verifyTestDataFetchedFromSelectedParent(parentEntityEnterpriseSapId,
				parentEntityEnterpriseTimezone, parentEntityEnterpriseStatus);
		logStep("Verify sap id, timezone and status value fetch from selected parent (client) in add new practice popup");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(parentName);
		addOrgNodePage.verifyTestDataFetchedFromSelectedParent(parentSapId, parentTimezone, parentStatus);
	}

	@Test(priority = 11, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify error messages are displayed on add new practice popup")
	public void testErrorMessagesAtAddNewPractice() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on practice option in new dropdown");
		addOrgNodePage = generalSettingsPage.clickOnAddPractice();
		String practiceNameLabelText = addOrgNodePage.getPracticeNameLabelText();
		logStep("Verify parent entity is required field");
		addOrgNodePage.clickOnSaveAndAddButton();
		addOrgNodePage.verifyParentEntityNameIsRequiredField();
		logStep("Verify Practice Name is required field");
		addOrgNodePage.verifyOrgNameIsRequiredField(practiceNameLabelText);
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(ccuser.getEnterpriseName());
		addOrgNodePage.enterSpaceInRequiredField();
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Practice name cannot be empty");
		commonMethods.closeToasterMessage();
		logStep("Click on add new enterprise button");
		generalSettingsPage.clickOnAddPractice();
		logStep("Enter random text in parent entity textbox");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName("abcdef@##24");
		logStep("Verify No Items Found message is displayed for parent entity field on add new enterprise pop up");
		addOrgNodePage.verifyNoItemsFound();
		logStep("Enter more than 100 characters in enterprise name field and verify error message for system name field");
		addOrgNodePage.verifyErrorMessageForOrgNodeNameField(testData.getProperty("invalid.name"));
		logStep("Enter more than 100 characters in solution display name field and verify error message for solution display name field");
		addOrgNodePage
				.verifyErrorMessageForSolutionDisplayNameField(testData.getProperty("invalid.solutionDisplayName"));
		logStep("Enter more than 50 characters in sap id field and verify error message for sap id field");
		addOrgNodePage.verifyErrorMessageForSapIdField(testData.getProperty("invalid.sap"));
	}

	@Test(priority = 12, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new agnostic practice(without solutions) functionality")
	public void testAddNewActiveAgnosticPracticeWithoutSolutionsBelowActiveClient() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		String parentName = generalSettingsPage.getClientName();
		logStep("Click on practice option in new dropdown");
		addOrgNodePage = generalSettingsPage.clickOnAddPractice();
		logStep("Add new agnostic practice node below client node");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(parentName);
		addOrgNodePage.createNewOrgNode(testData.getProperty("new.practice.name"),
				testData.getProperty("new.practice.sapid"), testData.getProperty("new.practice.timezone"),
				testData.getProperty("new.practice.solutiondisplayname"));
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(
				testData.getProperty("new.practice.name"), testData.getProperty("new.practice.sapid"),
				testData.getProperty("new.practice.solutiondisplayname"),
				testData.getProperty("new.practice.timezone"));
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(testData.getProperty("new.practice.name"), true);
		addAndUpdateProductSolutionsPage = new AddAndUpdateProductSolutionsPage(driver);
		addAndUpdateProductSolutionsPage.verifySolutionsNameForAvailableToPurchase();
	}

	@Test(priority = 13, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new inactive agnostic practice(with solutions) functionality")
	public void testAddNewInactiveAgnosticPracticeWithSolutionsBelowActiveClient() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		String parentName = generalSettingsPage.getClientName();
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new agnostic practice node below client node");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(parentName);
		addOrgNodePage.createNewOrgNode(testData.getProperty("new.inactive.practice.name"),
				testData.getProperty("new.inactive.practice.sapid"),
				testData.getProperty("new.inactive.practice.timezone"),
				testData.getProperty("new.inactive.practice.solutiondisplayname"));
		addOrgNodePage.selectInactiveRadiobutton();
		addOrgNodePage.addSolutions();
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(
				testData.getProperty("new.inactive.practice.name"), testData.getProperty("new.inactive.practice.sapid"),
				testData.getProperty("new.inactive.practice.solutiondisplayname"),
				testData.getProperty("new.inactive.practice.timezone"));
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(false);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(testData.getProperty("new.inactive.practice.name"),
				false);
		addAndUpdateProductSolutionsPage = new AddAndUpdateProductSolutionsPage(driver);
		addAndUpdateProductSolutionsPage.verifyActiveSolutions();
	}

	@Test(priority = 14, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add active agnostic practice node below inactive client")
	public void testAddNewActiveAgnosticPracticeBelowInactiveClient() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getInactiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		String parentName = generalSettingsPage.getClientName();
		String parentSapId = generalSettingsPage.getClientSapId();
		String parentTimezone = generalSettingsPage.getClientTimezone();
		logStep("Click on practice option in new dropdown");
		addOrgNodePage = generalSettingsPage.clickOnAddPractice();
		logStep("Add active agnostic practice below inactive client");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(parentName);
		String practiceName = ccuser.getProperty("api.practice.orgtype").concat(CCUtils.generateAlphaNumericString(5));
		addOrgNodePage.enterOrgName(practiceName);
		addOrgNodePage.selectActiveRadiobutton();
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(practiceName, parentSapId, practiceName,
				parentTimezone);
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(practiceName, false);
	}

	@Test(priority = 15, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new nge practice(without solutions) functionality")
	public void testAddNewActiveNgePracticeWithoutSolutionsBelowActiveEnterprise() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on practice option in new dropdown");
		addOrgNodePage = generalSettingsPage.clickOnAddPractice();
		logStep("Add new nge practice node below enterprise node");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(testData.getProperty("new.enterprise.name"));
		addOrgNodePage.createNewOrgNode(testData.getProperty("new.practice.name"),
				testData.getProperty("new.practice.sapid"), testData.getProperty("new.practice.timezone"),
				testData.getProperty("new.practice.solutiondisplayname"));
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(
				testData.getProperty("new.practice.name"), testData.getProperty("new.practice.sapid"),
				testData.getProperty("new.practice.solutiondisplayname"),
				testData.getProperty("new.practice.timezone"));
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(testData.getProperty("new.practice.name"), true);
		addAndUpdateProductSolutionsPage = new AddAndUpdateProductSolutionsPage(driver);
		addAndUpdateProductSolutionsPage.verifySolutionsNameForAvailableToPurchase();
	}

	@Test(priority = 16, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new nge practice(with solutions) functionality")
	public void testAddNewInactiveNgePracticeWithSolutionsBelowActiveEnterprise() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new nge practice node below enterprise node");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(testData.getProperty("new.enterprise.name"));
		addOrgNodePage.createNewOrgNode(testData.getProperty("new.inactive.practice.name"),
				testData.getProperty("new.inactive.practice.sapid"),
				testData.getProperty("new.inactive.practice.timezone"),
				testData.getProperty("new.inactive.practice.solutiondisplayname"));
		addOrgNodePage.addSolutions();
		addOrgNodePage.selectInactiveRadiobutton();
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(
				testData.getProperty("new.inactive.practice.name"), testData.getProperty("new.inactive.practice.sapid"),
				testData.getProperty("new.inactive.practice.solutiondisplayname"),
				testData.getProperty("new.inactive.practice.timezone"));
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(false);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(testData.getProperty("new.inactive.practice.name"),
				false);

		addAndUpdateProductSolutionsPage = new AddAndUpdateProductSolutionsPage(driver);
		addAndUpdateProductSolutionsPage.verifyActiveSolutions();
	}

	@Test(priority = 17, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add active nge practice node below inactive enterprise")
	public void testAddNewActiveNgePracticeBelowInactieEnterprise() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		generalSettingsPage.searchNode(testData.getProperty("new.inactive.enterprise.name"));
		String parentSapId = generalSettingsPage.getOrgSapId();
		String parentTimezone = generalSettingsPage.getOrgTimezone();
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new active nge practice below inactive enterprise");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(testData.getProperty("new.inactive.enterprise.name"));
		String practiceName = testData.getProperty("api.practice.orgtype")
				.concat(CCUtils.generateAlphaNumericString(5));
		addOrgNodePage.enterOrgName(practiceName);
		addOrgNodePage.selectActiveRadiobutton();
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(practiceName, parentSapId, practiceName,
				parentTimezone);
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(practiceName, false);
	}

	@Test(priority = 18, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter space in sap id field in add new system popup and verify hyphen sysmbol is displayed for blank sap id field in general sttings page")
	public void testSapIdIsBlankAtAddSystemPopup() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on Add new system option");
		generalSettingsPage.clickOnAddSystem();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Enter details in add new system popup");
		String systemName = testData.getProperty("api.system.name").concat(CCUtils.generateRandomString());
		addOrgNodePage.verifySapIdFieldAcceptBlankSpace(systemName);
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify hyphen symbol is displayed for sap id field for newly added system node in general settings page");
		generalSettingsPage.verifyHyphenSymbolIsDisplayedForSapIdField(systemName);
	}

	@Test(priority = 19, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter space in sap id field in add new enterprise popup and verify hyphen sysmbol is displayed for blank sap id field in general sttings page")
	public void testSapIdIsBlankAtAddEnterprisePopup() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on enterprise option in new dropdown");
		generalSettingsPage.clickOnAddEnterprise();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Enter details in add new enterprise pop up");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(testData.getProperty("new.system.name"));
		String enterpriseName = testData.getProperty("api.enterprise.orgtype")
				.concat(CCUtils.generateAlphaNumericString(5));
		addOrgNodePage.verifySapIdFieldAcceptBlankSpace(enterpriseName);
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify hyphen symbol is displayed for sap id field for newly added enterprise node in general settings page");
		generalSettingsPage.verifyHyphenSymbolIsDisplayedForSapIdField(enterpriseName);
	}

	@Test(priority = 20, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter space in sap id field in add new practice popup and verify hyphen sysmbol is displayed for blank sap id field in general sttings page")
	public void testSapIdIsBlankAtAddPracticePopup() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(testData.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		String parentName = generalSettingsPage.getClientName();
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new agnostic practice node below client node");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(parentName);
		String practiceName = testData.getProperty("api.practice.orgtype").concat(CCUtils.generateRandomString());
		addOrgNodePage.verifySapIdFieldAcceptBlankSpace(practiceName);
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify hyphen symbol is displayed for sap id field for newly added system node in general settings page");
		generalSettingsPage.verifyHyphenSymbolIsDisplayedForSapIdField(practiceName);
	}
}
