// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
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

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Error messages are displayed on add new system popup")
	public void testErrorMessagesAtAddNewSystem() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on system option in new dropdown");
		generalSettingsPage.clickOnAddSystem();
		addOrgNodePage = new AddOrgNodePage(driver);
		String systemNameLabelText = addOrgNodePage.getSystemNameLabelText();
		logStep("Verify error messages are displayed for mandatory field");
		addOrgNodePage.clickOnSaveAndAddButton();
		addOrgNodePage.verifyOrgNameIsRequiredField(systemNameLabelText);
		addOrgNodePage.enterSpaceInRequiredField();
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("System name cannot be empty");
		commonMethods.closeToasterMessage();
		logStep("Click on add new system button");
		generalSettingsPage.clickOnAddSystem();
		logStep("Enter more than 100 characters in system name field and verify error message for system name field");
		addOrgNodePage.verifyErrorMessageForOrgNodeNameField(ccuser.getInvalidOrgName());
		logStep("Enter more than 100 characters in solution display name field and verify error message for solution display name field");
		addOrgNodePage.verifyErrorMessageForSolutionDisplayNameField(ccuser.getInvalidSolutionDisplayName());
		logStep("Enter more than 50 characters in sap id field and verify error message for sap id field");
		addOrgNodePage.verifyErrorMessageForSapIdField(ccuser.getInvalidSap());
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Enter valid details in add new system popup and verify add new system functionality")
	public void testAddNewSystem() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on Add new system option");
		generalSettingsPage.clickOnAddSystem();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Enter details in add new system popup");
		addOrgNodePage.createNewOrgNode(ccuser.getSystemName(), ccuser.getSystemSapId(), ccuser.getSystemTimezone(),
				ccuser.getSystemSolutionDisplayName());
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added system node displayed in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(ccuser.getSystemName(),
				ccuser.getSystemSapId(), ccuser.getSystemSolutionDisplayName(), ccuser.getSystemTimezone());
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(ccuser.getSystemName(), true);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify Parent Entity dropdown is read only field in add new system pop up")
	public void testParentEntityDropdownAtAddNewSystem() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		String clientName = generalSettingsPage.getClientName();
		logStep("Verify Parent Entity dropdown functionality on add new system pop up");
		generalSettingsPage.clickOnAddSystem();
		addOrgNodePage = new AddOrgNodePage(driver);
		addOrgNodePage.verifyParentEntityDropdownisReadonlyField();
		addOrgNodePage.verifyClientNameIsDisplayedOnParentEntityDropdown(clientName);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify sap id, timezone and status values are inherited from selected parent in add new system popup")
	public void testInheritanceFunctionalityAtAddNewSystem() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Fetch client sap id,client timezone and client status values from general settings page");
		String parentSapId = generalSettingsPage.getClientSapId();
		String parentTimezone = generalSettingsPage.getClientTimezone();
		String parentStatus = generalSettingsPage.getClientStatus();
		logStep("Click on system option in new drop down");
		generalSettingsPage.clickOnAddSystem();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify sap id,timezone and status value fetched from selected parent(client) at add new system popup");
		addOrgNodePage.verifyTestDataFetchedFromSelectedParent(parentSapId, parentTimezone, parentStatus);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add inactive system below active client")
	public void testAddNewInactiveSystemBelowActiveClient() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on system option in new dropdown");
		generalSettingsPage.clickOnAddSystem();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Enter details in add new system popup");
		addOrgNodePage.createNewOrgNode(ccuser.getInactiveSystemName(), ccuser.getInactiveSystemSapId(),
				ccuser.getInactiveSystemTimezone(), ccuser.getInactiveSystemSolutionDisplayName());
		logStep("Click on inactive radio button in add new system popup");
		addOrgNodePage.selectInactiveRadiobutton();
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added system node is displayed on general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(ccuser.getInactiveSystemName(),
				ccuser.getInactiveSystemSapId(), ccuser.getInactiveSystemSolutionDisplayName(),
				ccuser.getInactiveSystemTimezone());
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(false);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(ccuser.getInactiveSystemName(), false);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify box text displayed for textbox fields on add new system popup")
	public void testBoxTextValueForTextboxFieldAtAddNewSystem() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on system option in new dropdown");
		generalSettingsPage.clickOnAddSystem();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify box text displayed for textbox field on add new system popup");
		addOrgNodePage.verifyBoxTextForNameField();
		addOrgNodePage.verifyBoxTextForSolutionDisplayNameField();
		addOrgNodePage.verifyBoxTextForSapIdField();
		addOrgNodePage.verifyBoxTextForTimezoneField();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add active system below inactive client and status of newly added node is by default inactive")
	public void testAddNewActiveSystemBelowInactiveClient() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getInactiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on system option in new dropdown");
		generalSettingsPage.clickOnAddSystem();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Enter details in add new system popup");
		addOrgNodePage.createNewOrgNode(ccuser.getSystemName(), ccuser.getSystemSapId(), ccuser.getSystemTimezone(),
				ccuser.getSystemSolutionDisplayName());
		logStep("Click on active radio button in add new system popup");
		addOrgNodePage.selectActiveRadiobutton();
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added system node displayed on general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(ccuser.getSystemName(),
				ccuser.getSystemSapId(), ccuser.getSystemSolutionDisplayName(), ccuser.getSystemTimezone());
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(ccuser.getSystemName(), false);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify cancel and cross button functionality in add new system popup")
	public void testCancelButtonAndCrossIconAtSystem() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on system option in new drop down");
		generalSettingsPage.clickOnAddSystem();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify after clicking on cancel button user redirected to general settings page");
		addOrgNodePage.clickOnCancelButton();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Click on system option in new drop down");
		generalSettingsPage.clickOnAddSystem();
		logStep("Verify after clicking on X icon user redirected to general settings page");
		addOrgNodePage.clickOnCrossIcon();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify no items found message displayed for parent entity field on add new enterprise popup")
	public void testNoItemsFoundAtAddNewEnterprise() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on enterprise option in new dropdown");
		generalSettingsPage.clickOnAddEnterprise();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Enter random text in parent entity textbox");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName("abcdef@##24");
		logStep("Verify No Items Found message is displayed for parent entity field on add new enterprise pop up");
		addOrgNodePage.verifyNoItemsFound();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new enterprise popup")
	public void testAddNewEnterprisePopup() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on enterprise option in new dropdown");
		generalSettingsPage.clickOnAddEnterprise();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify Add new enterprise popup");
		addOrgNodePage.verifyActiveButtonByDefaultSelected();
		addOrgNodePage.getTimezoneDropdownValue();
		addOrgNodePage.verifyTimezoneDropdownValues();
		addOrgNodePage.clickOnParentEntityNameDropdown();
		Boolean isParentEntityNameNotSelected = addOrgNodePage.verifyValueIsNotSelectedInParentEntity();
		addOrgNodePage.verifyDataIsNotFetchedIfParentEntityNotSelected(isParentEntityNameNotSelected);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify box text displayed for textbox fields on add new enterprise popup")
	public void testBoxTextValueForTextboxFieldAtAddNewEnterprise() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on enterprise option in new dropdown");
		generalSettingsPage.clickOnAddEnterprise();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify box text displayed for textbox fields on add new enterprise popup");
		addOrgNodePage.verifyBoxTextForNameField();
		addOrgNodePage.verifyBoxTextForSolutionDisplayNameField();
		addOrgNodePage.verifyBoxTextForSapIdField();
		addOrgNodePage.verifyBoxTextForParentEntityField();
		addOrgNodePage.verifyBoxTextForTimezoneField();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify error messages are displayed on add new enterprise popup")
	public void testErrorMessagesAtAddNewEnterprise() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on enterprise option in new dropdown");
		generalSettingsPage.clickOnAddEnterprise();
		addOrgNodePage = new AddOrgNodePage(driver);
		String enterpriseNameLabelText = addOrgNodePage.getEnterpriseNameLabelText();
		logStep("Verify parent entity is required field");
		addOrgNodePage.clickOnSaveAndAddButton();
		addOrgNodePage.verifyParentEntityNameIsRequiredField();
		logStep("Verify Enterprise Name is required field");
		addOrgNodePage.verifyOrgNameIsRequiredField(enterpriseNameLabelText);
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(ccuser.getSystemName());
		addOrgNodePage.enterSpaceInRequiredField();
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Enterprise name cannot be empty");
		commonMethods.closeToasterMessage();
		logStep("Click on add new enterprise button");
		generalSettingsPage.clickOnAddEnterprise();
		logStep("Enter more than 100 characters in enterprise name field and verify error message for system name field");
		addOrgNodePage.verifyErrorMessageForOrgNodeNameField(ccuser.getInvalidOrgName());
		logStep("Enter more than 100 characters in solution display name field and verify error message for solution display name field");
		addOrgNodePage.verifyErrorMessageForSolutionDisplayNameField(ccuser.getInvalidSolutionDisplayName());
		logStep("Enter more than 50 characters in sap id field and verify error message for sap id field");
		addOrgNodePage.verifyErrorMessageForSapIdField(ccuser.getInvalidSap());
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify cancel button and cross button functionality in add new enterprise pop up")
	public void testCancelButtonAndCrossIconAtEnterprise() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on enterprise option in new dropdown");
		generalSettingsPage.clickOnAddEnterprise();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify after clicking on cancel button user redirected to general settings page");
		addOrgNodePage.clickOnCancelButton();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Click on enterprise option in new dropdown");
		generalSettingsPage.clickOnAddEnterprise();
		logStep("Verify after clicking on X icon user redirected to general settings page");
		addOrgNodePage.clickOnCrossIcon();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify sap id, timezone and status value fetch from selected parent (system) in add new enterprise popup")
	public void testInheritanceFunctionalityAtAddNewEnterprise() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Add new system node");
		generalSettingsPage.clickOnAddSystem();
		addOrgNodePage = new AddOrgNodePage(driver);
		String systemName = ccuser.getSystemName().concat(CCUtils.generateRandomString());
		addOrgNodePage.createNewOrgNode(systemName, ccuser.getSystemSapId(), ccuser.getSystemTimezone(),
				ccuser.getSystemSolutionDisplayName());
		addOrgNodePage.selectActiveRadiobutton();
		addOrgNodePage.clickOnSaveAndAddButton();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new enterprise functionality")
	public void testAddNewEnterprise() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on enterprise option in new dropdown");
		generalSettingsPage.clickOnAddEnterprise();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Enter details in add new enterprise pop up");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(ccuser.getSystemName());
		addOrgNodePage.createNewOrgNode(ccuser.getEnterpriseName(), ccuser.getEnterpriseSapId(),
				ccuser.getEnterpriseTimezone(), ccuser.getEnterpriseSolutionDisplayName());
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added enterprise node displayed on general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(ccuser.getEnterpriseName(),
				ccuser.getEnterpriseSapId(), ccuser.getEnterpriseSolutionDisplayName(), ccuser.getEnterpriseTimezone());
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(ccuser.getEnterpriseName(), true);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add active enterprise below inactive system and status of newly added node is by default inactive")
	public void testAddNewActiveEnterpriseBelowInactiveSystem() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on enterprise option in new dropdown");
		generalSettingsPage.clickOnAddEnterprise();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new active enterprise node below inactive system node");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(ccuser.getInactiveSystemName());
		addOrgNodePage.createNewOrgNode(ccuser.getEnterpriseName(), ccuser.getEnterpriseSapId(),
				ccuser.getEnterpriseTimezone(), ccuser.getEnterpriseSolutionDisplayName());
		addOrgNodePage.selectActiveRadiobutton();
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added enterprise node displayed on general settings page and status of enterprise node is inactive");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(ccuser.getEnterpriseName(),
				ccuser.getEnterpriseSapId(), ccuser.getEnterpriseSolutionDisplayName(), ccuser.getEnterpriseTimezone());
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(ccuser.getEnterpriseName(), false);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add inactive enterprise node below active system. ")
	public void testAddNewInactiveEnterpriseBelowActiveSystem() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on enterprise option in new dropdown");
		generalSettingsPage.clickOnAddEnterprise();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new inactive enterprise node below active system node");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(ccuser.getSystemName());
		addOrgNodePage.createNewOrgNode(ccuser.getInactiveEnterpriseName(), ccuser.getInactiveEnterpriseSapId(),
				ccuser.getInactiveEnterpriseTimezone(), ccuser.getInactiveEnterpriseSolutionDisplayName());
		addOrgNodePage.selectInactiveRadiobutton();
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added enterprise node displayed on general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(ccuser.getInactiveEnterpriseName(),
				ccuser.getInactiveEnterpriseSapId(), ccuser.getInactiveEnterpriseSolutionDisplayName(),
				ccuser.getInactiveEnterpriseTimezone());
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(false);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(ccuser.getInactiveEnterpriseName(), false);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify no items found message displayed for parent entity field on add new practice popup")
	public void testNoItemsFoundAtAddNewPractice() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Enter random text in parent entity textbox");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName("abcdef@##24");
		logStep("Verify No Items Found message is displayed for parent entity field on add new enterprise pop up");
		addOrgNodePage.verifyNoItemsFound();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new practice popup")
	public void testAddNewPracticePopup() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify Add new practice popup");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		Boolean isParentEntityNameNotSelected = addOrgNodePage.verifyValueIsNotSelectedInParentEntity();
		addOrgNodePage.verifyDataIsNotFetchedIfParentEntityNotSelected(isParentEntityNameNotSelected);
		addOrgNodePage.verifyActiveButtonByDefaultSelected();
		addOrgNodePage.getSolutionValues();
		addOrgNodePage.verifySolutionValues();
		addOrgNodePage.getTimezoneDropdownValue();
		addOrgNodePage.verifyTimezoneDropdownValues();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify error messages are displayed on add new practice popup")
	public void testErrorMessagesAtAddNewPractice() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
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
		logStep("Enter more than 100 characters in enterprise name field and verify error message for system name field");
		addOrgNodePage.verifyErrorMessageForOrgNodeNameField(ccuser.getInvalidOrgName());
		logStep("Enter more than 100 characters in solution display name field and verify error message for solution display name field");
		addOrgNodePage.verifyErrorMessageForSolutionDisplayNameField(ccuser.getInvalidSolutionDisplayName());
		logStep("Enter more than 50 characters in sap id field and verify error message for sap id field");
		addOrgNodePage.verifyErrorMessageForSapIdField(ccuser.getInvalidSap());
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify cancel button and cross button functionality in add new practice pop up")
	public void testCancelButtonAndCrossIconAtPractice() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify after clicking on cancel button user redirected to general settings page");
		addOrgNodePage.clickOnCancelButton();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		logStep("Verify after clicking on X icon user redirected to general settings page");
		addOrgNodePage.clickOnCrossIcon();
		generalSettingsPage.verifyGeneralSettingsPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify sap id, timezone and status value fetch from selected parent (enterprise) in add new practice popup")
	public void testInheritanceFunctionalityAtAddNewNgePractice() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on enterprise option in new dropdown");
		generalSettingsPage.clickOnAddEnterprise();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new enterprise node");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(ccuser.getSystemName());
		String enterpriseName = ccuser.getEnterpriseName().concat(CCUtils.generateRandomString());
		addOrgNodePage.createNewOrgNode(enterpriseName, ccuser.getEnterpriseSapId(), ccuser.getEnterpriseTimezone(),
				ccuser.getEnterpriseSolutionDisplayName());
		addOrgNodePage.selectActiveRadiobutton();
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Get enterprise name, sap id, timezone and status values from enterpeise node in general settings page");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
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
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify sap id, timezone and status value fetch from selected parent (client) in add new practice popup")
	public void testInheritanceFunctionalityAtAddNewAgnosticPractice() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Get client name, sap id, timezone and status values from client node in general settings page");
		String parentName = generalSettingsPage.getClientName();
		String parentSapId = generalSettingsPage.getClientSapId();
		String parentTimezone = generalSettingsPage.getClientTimezone();
		String parentStatus = generalSettingsPage.getClientStatus();
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify sap id, timezone and status value fetch from selected parent (client) in add new practice popup");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(parentName);
		addOrgNodePage.verifyTestDataFetchedFromSelectedParent(parentSapId, parentTimezone, parentStatus);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new agnostic practice(without solutions) functionality")
	public void testAddNewAgnosticPracticeWithoutSolutions() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		String parentName = generalSettingsPage.getClientName();
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new agnostic practice node below client node");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(parentName);
		addOrgNodePage.createNewOrgNode(ccuser.getPracticeName(), ccuser.getPracticeSapId(),
				ccuser.getPracticeTimezone(), ccuser.getPracticeSolutionDisplayName());
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(ccuser.getPracticeName(),
				ccuser.getPracticeSapId(), ccuser.getPracticeSolutionDisplayName(), ccuser.getPracticeTimezone());
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(ccuser.getPracticeName(), true);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new agnostic practice(with solutions) functionality")
	public void testAddNewAgnosticPracticeWithSolutions() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		String parentName = generalSettingsPage.getClientName();
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new agnostic practice node below client node");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(parentName);
		addOrgNodePage.createNewOrgNode(ccuser.getPracticeName(), ccuser.getPracticeSapId(),
				ccuser.getPracticeTimezone(), ccuser.getPracticeSolutionDisplayName());
		addOrgNodePage.addSolutions();
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(ccuser.getPracticeName(),
				ccuser.getPracticeSapId(), ccuser.getPracticeSolutionDisplayName(), ccuser.getPracticeTimezone());
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(ccuser.getPracticeName(), true);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new nge practice(without solutions) functionality")
	public void testAddNewNgePracticeWithoutSolutions() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new nge practice node below enterprise node");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(ccuser.getEnterpriseName());
		addOrgNodePage.createNewOrgNode(ccuser.getPracticeName(), ccuser.getPracticeSapId(),
				ccuser.getPracticeTimezone(), ccuser.getPracticeSolutionDisplayName());
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(ccuser.getPracticeName(),
				ccuser.getPracticeSapId(), ccuser.getPracticeSolutionDisplayName(), ccuser.getPracticeTimezone());
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(ccuser.getPracticeName(), true);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new nge practice(with solutions) functionality")
	public void testAddNewNgePracticeWithSolutions() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new nge practice node below enterprise node");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(ccuser.getEnterpriseName());
		addOrgNodePage.createNewOrgNode(ccuser.getPracticeName(), ccuser.getPracticeSapId(),
				ccuser.getPracticeTimezone(), ccuser.getPracticeSolutionDisplayName());
		addOrgNodePage.addSolutions();
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(ccuser.getPracticeName(),
				ccuser.getPracticeSapId(), ccuser.getPracticeSolutionDisplayName(), ccuser.getPracticeTimezone());
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(ccuser.getPracticeName(), true);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify box text displayed on add new practice popup")
	public void testBoxTextValueForTextboxFieldAtAddNewPractice() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Verify box text displayed for textbox field on add new practice popup");
		addOrgNodePage.verifyBoxTextForNameField();
		addOrgNodePage.verifyBoxTextForSolutionDisplayNameField();
		addOrgNodePage.verifyBoxTextForSapIdField();
		addOrgNodePage.verifyBoxTextForParentEntityField();
		addOrgNodePage.verifyBoxTextForTimezoneField();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add active agnostic practice node below inactive client")
	public void testAddNewActiveAgnosticPracticeBelowInactiveClient() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getInactiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		String parentName = generalSettingsPage.getClientName();
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add active agnostic practice below inactive client");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(parentName);
		addOrgNodePage.createNewOrgNode(ccuser.getPracticeName(), ccuser.getPracticeSapId(),
				ccuser.getPracticeTimezone(), ccuser.getPracticeSolutionDisplayName());
		addOrgNodePage.selectActiveRadiobutton();
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(ccuser.getPracticeName(),
				ccuser.getPracticeSapId(), ccuser.getPracticeSolutionDisplayName(), ccuser.getPracticeTimezone());
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(ccuser.getPracticeName(), false);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add inactive agnostic practice node below active client")
	public void testAddNewInactiveAgnosticPracticeBelowActiveClient() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		String parentName = generalSettingsPage.getClientName();
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new inactive agnostic practice below active client");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(parentName);
		addOrgNodePage.createNewOrgNode(ccuser.getInactivePracticeName(), ccuser.getInactivePracticeSapId(),
				ccuser.getInactivePracticeTimezone(), ccuser.getInactivePracticeSolutionDisplayName());
		addOrgNodePage.selectInactiveRadiobutton();
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(ccuser.getInactivePracticeName(),
				ccuser.getInactivePracticeSapId(), ccuser.getInactivePracticeSolutionDisplayName(),
				ccuser.getInactivePracticeTimezone());
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(false);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(ccuser.getInactivePracticeName(), false);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add active nge practice node below inactive enterprise")
	public void testAddNewActiveNgePracticeBelowInactieEnterprise() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new active nge practice below inactive enterprise");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(ccuser.getInactiveEnterpriseName());
		addOrgNodePage.createNewOrgNode(ccuser.getPracticeName(), ccuser.getPracticeSapId(),
				ccuser.getPracticeTimezone(), ccuser.getPracticeSolutionDisplayName());
		addOrgNodePage.selectActiveRadiobutton();
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(ccuser.getPracticeName(),
				ccuser.getPracticeSapId(), ccuser.getPracticeSolutionDisplayName(), ccuser.getPracticeTimezone());
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusIsInactiveIfParentInactive(true);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(ccuser.getPracticeName(), false);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to add inactive nge practice node below active enterprise")
	public void testAddNewInactiveNgePracticeBelowActieEnterprise() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new inactive nge practice below active enterprise");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(ccuser.getEnterpriseName());
		addOrgNodePage.createNewOrgNode(ccuser.getInactivePracticeName(), ccuser.getInactivePracticeSapId(),
				ccuser.getInactivePracticeTimezone(), ccuser.getInactivePracticeSolutionDisplayName());
		addOrgNodePage.selectInactiveRadiobutton();
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify newly added practice node details in general settings page");
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsDisplayedInRightPane(ccuser.getInactivePracticeName(),
				ccuser.getInactivePracticeSapId(), ccuser.getInactivePracticeSolutionDisplayName(),
				ccuser.getInactivePracticeTimezone());
		generalSettingsPage.verifyNewlyAddedOrgNodeStatusOnGeneralSettings(false);
		generalSettingsPage.verifyNewlyAddedOrgNodeDetailsInOrgTree(ccuser.getInactivePracticeName(), false);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter space in sap id field in add new system popup and verify hyphen sysmbol is displayed for blank sap id field in general sttings page")
	public void testSapIdIsBlankAtAddSystemPopup() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on Add new system option");
		generalSettingsPage.clickOnAddSystem();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Enter details in add new system popup");
		String systemName = ccuser.getSystemName().concat(CCUtils.generateRandomString());
		addOrgNodePage.verifySapIdFieldAcceptBlankSpace(systemName);
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify hyphen symbol is displayed for sap id field for newly added system node in general settings page");
		generalSettingsPage.verifyHyphenSymbolIsDisplayedForSapIdField(systemName);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter space in sap id field in add new enterprise popup and verify hyphen sysmbol is displayed for blank sap id field in general sttings page")
	public void testSapIdIsBlankAtAddEnterprisePopup() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		logStep("Click on enterprise option in new dropdown");
		generalSettingsPage.clickOnAddEnterprise();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Enter details in add new enterprise pop up");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(ccuser.getSystemName());
		String enterpriseName = ccuser.getEnterpriseName().concat(CCUtils.generateRandomString());
		addOrgNodePage.verifySapIdFieldAcceptBlankSpace(enterpriseName);
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify hyphen symbol is displayed for sap id field for newly added enterprise node in general settings page");
		generalSettingsPage.verifyHyphenSymbolIsDisplayedForSapIdField(enterpriseName);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter space in sap id field in add new practice popup and verify hyphen sysmbol is displayed for blank sap id field in general sttings page")
	public void testSapIdIsBlankAtAddPracticePopup() throws Exception {
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(ccuser.getActiveClientName());
		generalSettingsPage = new GeneralSettingsPage(driver);
		String parentName = generalSettingsPage.getClientName();
		logStep("Click on practice option in new dropdown");
		generalSettingsPage.clickOnAddPractice();
		addOrgNodePage = new AddOrgNodePage(driver);
		logStep("Add new agnostic practice node below client node");
		addOrgNodePage.clickOnParentEntityNameDropdown();
		addOrgNodePage.searchTextForParentEntityName(parentName);
		String practiceName = ccuser.getPracticeName().concat(CCUtils.generateRandomString());
		addOrgNodePage.verifySapIdFieldAcceptBlankSpace(practiceName);
		logStep("Click on save and add button");
		addOrgNodePage.clickOnSaveAndAddButton();
		logStep("Verify general settings page title");
		generalSettingsPage.verifyGeneralSettingsPageTitle();
		logStep("Verify hyphen symbol is displayed for sap id field for newly added system node in general settings page");
		generalSettingsPage.verifyHyphenSymbolIsDisplayedForSapIdField(practiceName);
	}

}
