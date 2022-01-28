// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddAndUpdateProductSolutionsPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.EditOrgNodePage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.objectmaps.UnsavedChangesPage;
import com.pxp.cc.util.CCUtils;

public class UnsavedChangesPopupTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public GeneralSettingsPage generalSettingsPage;
	public UnsavedChangesPage unsavedChangesPage;
	public EditOrgNodePage editOrgNodePage;
	public AddAndUpdateProductSolutionsPage addAndUpdateProductSolutionsPage;
	List<String> activeSolutionsList;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Search and select client node in client table");
		clientDirectoryPage
				.searchAndSelectClientNameInClientTable(testData.getProperty("search.client.in.client.directory"));
		generalSettingsPage = new GeneralSettingsPage(driver);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify unsaved changes popup")
	public void testUnsavedChangesPopup() throws Exception {
		logStep("Click on edit button on the general settings page");
		generalSettingsPage.clickEditButtonOfClient();
		logStep("change details in client name & sap id");
		String newClientName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(2);
		generalSettingsPage.enterNewClientName(newClientName);
		String newClientSap = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		generalSettingsPage.enterNewClientSap(newClientSap);
		logStep("Click on client directory icon");
		generalSettingsPage.clickOnClientDirectoryIcon();
		unsavedChangesPage = new UnsavedChangesPage(driver);
		logStep("Verify unsaved changes popup");
		unsavedChangesPage.verifyUnsavedChangesPopupPageTitle();
		unsavedChangesPage.verifyConfirmationQuestionText();
		unsavedChangesPage.verifyReturnToPageButtonIsDisplayed();
		unsavedChangesPage.verifyDisregardChangesButtonIsDisplayed();
		unsavedChangesPage.verifyCrossIconIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify cross icon functionality on unsaved changes popup")
	public void testFunctionalityOfCrossIconOnUnsavedChangesPopup() throws Exception {
		logStep("Click on edit button on the general settings page");
		generalSettingsPage.clickEditButtonOfClient();
		logStep("Change details in client name & sap id");
		String newClientName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		generalSettingsPage.clearClientNameField();
		generalSettingsPage.enterNewClientName(newClientName);
		String newClientSap = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		generalSettingsPage.clearSapField();
		generalSettingsPage.enterNewClientSap(newClientSap);
		logStep("Click on client directory icon");
		generalSettingsPage.clickOnClientDirectoryIcon();
		unsavedChangesPage = new UnsavedChangesPage(driver);
		logStep("Verify unsaved changes popup is displayed on screen");
		unsavedChangesPage.verifyUnsavedChangesPopupPageTitle();
		logStep("Click on cross icon on the unsaved changes popup");
		unsavedChangesPage.clickOnCloseWindow();
		logStep("Verify user is on the same page and all unsaved changes details remain as it is, after clicking on cross icon on the unsaved changes popup");
		CCUtils.assertEquals(generalSettingsPage.getNameInEditMode(), newClientName);
		CCUtils.assertEquals(generalSettingsPage.getSapIdInEditMode(), newClientSap);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify return to page button functionality on the unsaved changes popup")
	public void testFunctionalityOfReturnToPageBtnOnUnsavedChangesPopup() throws Exception {
		logStep("Click on edit button on the general settings page");
		generalSettingsPage.clickEditButtonOfClient();
		logStep("change details in client name & sap id");
		String newClientName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		generalSettingsPage.clearClientNameField();
		generalSettingsPage.enterNewClientName(newClientName);
		String newClientSap = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		generalSettingsPage.clearSapField();
		generalSettingsPage.enterNewClientSap(newClientSap);
		logStep("Click on client directory icon");
		generalSettingsPage.clickOnClientDirectoryIcon();
		logStep("Verify unsaved changes popup is displayed on screen");
		unsavedChangesPage = new UnsavedChangesPage(driver);
		unsavedChangesPage.verifyUnsavedChangesPopupPageTitle();
		logStep("Click on return to page button");
		unsavedChangesPage.clickOnReturnToPageButton();
		logStep("Verify user is on the same page and all unsaved changes details remain as it is, after clicking on return to page button on the unsaved changes popup");
		CCUtils.assertEquals(generalSettingsPage.getNameInEditMode(), newClientName);
		CCUtils.assertEquals(generalSettingsPage.getSapIdInEditMode(), newClientSap);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Update client details and click the disregard changes button on unsaved changes popup. After clicking the disregard changes button on the Unsaved Changes popup, verify that the old client details remain on the client information page")
	public void testFunctionalityOfDisregardChangesBtnForClientNode() throws Exception {
		logStep("Details for Client are as: ");
		String clientNodeStatusBeforeEdit = generalSettingsPage.getClientStatus();
		String clientNameValueBeforeEdit = generalSettingsPage.getClientName();
		String clientSapIdValueBeforeEdit = generalSettingsPage.getClientSapId();
		String clientTimezoneValueBeforeEdit = generalSettingsPage.getClientTimezone();
		String clientTypeValueBeforeEdit = generalSettingsPage.getClientType();
		logStep("Click on edit button on the general settings page");
		generalSettingsPage.clickEditButtonOfClient();
		logStep("Change details in Client Name, SAP ID, Timezone, Client Type and Status fields");
		String newClientName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		generalSettingsPage.clearClientNameField();
		generalSettingsPage.enterNewClientName(newClientName);
		String newClientSap = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		generalSettingsPage.clearSapField();
		generalSettingsPage.enterNewClientSap(newClientSap);
		String newTimezone = testData.getProperty("practice.timezone");
		generalSettingsPage.selectTimezone(newTimezone);
		String newClientType = ccuser.getClientElevenType();
		generalSettingsPage.clickClientType();
		generalSettingsPage.selectClientType(newClientType);
		generalSettingsPage.selectClientNodeStatus(clientNodeStatusBeforeEdit);
		String newStatus = generalSettingsPage.getClientNodeStatus();
		logStep("Click on client directory icon");
		generalSettingsPage.clickOnClientDirectoryIcon();
		logStep("Verify unsaved changes popup is displayed on screen");
		unsavedChangesPage = new UnsavedChangesPage(driver);
		unsavedChangesPage.verifyUnsavedChangesPopupPageTitle();
		logStep("Click on disregard changes button");
		unsavedChangesPage.clickOnDisregardChangesButton();
		logStep("Verify after clicking on disregard changes button, user redirected to client directory page");
		clientDirectoryPage.verifyClientDirectioryPageTitle();
		logStep("Search and select client node in client table");
		clientDirectoryPage
				.searchAndSelectClientNameInClientTable(testData.getProperty("search.client.in.client.directory"));
		logStep("Verify that the old client details remain on the client information page");
		CCUtils.assertNotEquals(clientNameValueBeforeEdit, newClientName);
		CCUtils.assertNotEquals(clientSapIdValueBeforeEdit, newClientSap);
		CCUtils.assertNotEquals(clientTimezoneValueBeforeEdit, testData.getProperty("practice.timezone"));
		CCUtils.assertNotEquals(clientTypeValueBeforeEdit, ccuser.getClientElevenType());
		CCUtils.assertNotEquals(clientNodeStatusBeforeEdit, newStatus);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "After clicking the disregard changes button on the unsaved changes popup, verify that the old system details remain on the system information page")
	public void testFunctionalityOfDisregardChangesBtnForSystemNode() throws Exception {
		logStep("Search and select system node on the general settings page");
		generalSettingsPage.searchNode(testData.getProperty("search.system.in.client.directory"));
		logStep("Get system details as: ");
		String systemNameValueBeforeEdit = generalSettingsPage.getOrgName();
		String systemSapIdValueBeforeEdit = generalSettingsPage.getOrgSapId();
		String systemSolutionDisplayNameBeforeEdit = generalSettingsPage.getOrgSolutionDisplayName();
		String systemTimezoneValueBeforeEdit = generalSettingsPage.getOrgTimezone();
		String systemNodeStatusBeforeEdit = generalSettingsPage.getOrgStatus();
		logStep("Click on edit button on the general settings page");
		generalSettingsPage.clickOnEditButtonOfOrg();
		logStep("Change details in system name, sap id, timezone, solution display name and status fields");
		editOrgNodePage = new EditOrgNodePage(driver);
		String newSystemName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		editOrgNodePage.clearOrgNameField();
		editOrgNodePage.enterOrgName(newSystemName);
		String newSystemSapId = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		editOrgNodePage.clearSapIdField();
		editOrgNodePage.enterOrgSapId(newSystemSapId);
		String newSolutionDisplayName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		editOrgNodePage.clearSolutionDisplayNameField();
		editOrgNodePage.enterOrgSolutionDisplayName(newSolutionDisplayName);
		String newTimeZone = testData.getProperty("practice.timezone");
		editOrgNodePage.selectTimezone(newTimeZone);
		editOrgNodePage.selectOrgNodeStatus(systemNodeStatusBeforeEdit);
		String newStatusValue = editOrgNodePage.getOrgStatus();
		logStep("Click on client directory icon");
		generalSettingsPage.clickOnClientDirectoryIcon();
		logStep("Verify unsaved changes popup is displayed on screen");
		unsavedChangesPage = new UnsavedChangesPage(driver);
		unsavedChangesPage.verifyUnsavedChangesPopupPageTitle();
		logStep("Click on disregard changes button on the unsaved changes popup");
		unsavedChangesPage.clickOnDisregardChangesButton();
		logStep("Verify user redirected to client directory page, after clicking on disregard changes button on the unsaved changes popup");
		clientDirectoryPage.verifyClientDirectioryPageTitle();
		logStep("Search and select client node in client table");
		clientDirectoryPage
				.searchAndSelectClientNameInClientTable(testData.getProperty("search.client.in.client.directory"));
		logStep("search and select system node on general settings page");
		generalSettingsPage.searchNode(testData.getProperty("search.system.in.client.directory"));
		logStep("Verify that the old system details remain on the system information page");
		CCUtils.assertNotEquals(systemNameValueBeforeEdit, newSystemName);
		CCUtils.assertNotEquals(systemSapIdValueBeforeEdit, newSystemSapId);
		CCUtils.assertNotEquals(systemSolutionDisplayNameBeforeEdit, newSolutionDisplayName);
		CCUtils.assertNotEquals(systemTimezoneValueBeforeEdit, newTimeZone);
		CCUtils.assertNotEquals(systemNodeStatusBeforeEdit, newStatusValue);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "After clicking the disregard changes button on the unsaved changes popup, verify that the old enterprise details remain on the enterprise information page")
	public void testFunctionalityOfDisregardChangesBtnForEnterpriseNode() throws Exception {
		logStep("Search and select enterprise node on the general settings page");
		generalSettingsPage.searchNode(testData.getProperty("search.enterprise.in.client.directory"));
		logStep("Get enterprise details as: ");
		String enterpriseNameValueBeforeEdit = generalSettingsPage.getOrgName();
		String enterpriseSapIdValueBeforeEdit = generalSettingsPage.getOrgSapId();
		String enterpriseSolutionDisplayNameBeforeEdit = generalSettingsPage.getOrgSolutionDisplayName();
		String enterpriseTimezoneValueBeforeEdit = generalSettingsPage.getOrgTimezone();
		String enterpriseNodeStatusBeforeEdit = generalSettingsPage.getOrgStatus();
		logStep("Click on edit button on the general settings page");
		generalSettingsPage.clickOnEditButtonOfOrg();
		logStep("Change details in enterprise name, sap id, timezone, solution display name and status fields");
		editOrgNodePage = new EditOrgNodePage(driver);
		String newEnterpriseName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		editOrgNodePage.clearOrgNameField();
		editOrgNodePage.enterOrgName(newEnterpriseName);
		String newEnterpriseSapId = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		editOrgNodePage.clearSapIdField();
		editOrgNodePage.enterOrgSapId(newEnterpriseSapId);
		String newSolutionDisplayName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		editOrgNodePage.clearSolutionDisplayNameField();
		editOrgNodePage.enterOrgSolutionDisplayName(newSolutionDisplayName);
		String newTimeZone = testData.getProperty("practice.timezone");
		editOrgNodePage.selectTimezone(newTimeZone);
		editOrgNodePage.selectOrgNodeStatus(enterpriseNodeStatusBeforeEdit);
		String newStatusValue = editOrgNodePage.getOrgStatus();
		logStep("Click on client directory icon");
		generalSettingsPage.clickOnClientDirectoryIcon();
		logStep("Verify unsaved changes popup is displayed on screen");
		unsavedChangesPage = new UnsavedChangesPage(driver);
		unsavedChangesPage.verifyUnsavedChangesPopupPageTitle();
		logStep("Click on disregard changes button on the unsaved changes popup");
		unsavedChangesPage.clickOnDisregardChangesButton();
		logStep("Verify user redirected to client directory page, after clicking on disregard changes button on the unsaved changes popup");
		clientDirectoryPage.verifyClientDirectioryPageTitle();
		logStep("Search and select client node in client table");
		clientDirectoryPage
				.searchAndSelectClientNameInClientTable(testData.getProperty("search.client.in.client.directory"));
		logStep("Search and select enterprise node on general settings page");
		generalSettingsPage.searchNode(testData.getProperty("search.enterprise.in.client.directory"));
		logStep("Verify that the old enterprise details remain on the enterprise information page");
		CCUtils.assertNotEquals(enterpriseNameValueBeforeEdit, newEnterpriseName);
		CCUtils.assertNotEquals(enterpriseSapIdValueBeforeEdit, newEnterpriseSapId);
		CCUtils.assertNotEquals(enterpriseSolutionDisplayNameBeforeEdit, newSolutionDisplayName);
		CCUtils.assertNotEquals(enterpriseTimezoneValueBeforeEdit, newTimeZone);
		CCUtils.assertNotEquals(enterpriseNodeStatusBeforeEdit, newStatusValue);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "After clicking the disregard changes button on the unsaved changes popup, verify that the old nge practice details remain on the nge practice information page")
	public void testFunctionalityOfUnsavedChangesAtNgePracticeNode() throws Exception {
		logStep("search and select nge practice node on the general settings page");
		generalSettingsPage.searchNode(testData.getProperty("search.practice.in.client.directory"));
		logStep("Get nge practice details as: ");
		String practiceNameValueBeforeEdit = generalSettingsPage.getOrgName();
		String practiceSapIdValueBeforeEdit = generalSettingsPage.getOrgSapId();
		String practiceSolutionDisplayNameBeforeEdit = generalSettingsPage.getOrgSolutionDisplayName();
		String practiceTimezoneValueBeforeEdit = generalSettingsPage.getOrgTimezone();
		String practiceNodeStatusBeforeEdit = generalSettingsPage.getOrgStatus();
		activeSolutionsList = new ArrayList<String>();
		activeSolutionsList.addAll(generalSettingsPage.getListOfActiveSolutions());
		logStep("Click on edit button on the general settings page");
		generalSettingsPage.clickOnEditButtonOfOrg();
		logStep("Change details in nge practice name, sap id, timezone, solution display name and status fields");
		editOrgNodePage = new EditOrgNodePage(driver);
		String newPracticeName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		editOrgNodePage.clearOrgNameField();
		editOrgNodePage.enterOrgName(newPracticeName);
		String newPracticeSapId = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		editOrgNodePage.clearSapIdField();
		editOrgNodePage.enterOrgSapId(newPracticeSapId);
		String newSolutionDisplayName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		editOrgNodePage.clearSolutionDisplayNameField();
		editOrgNodePage.enterOrgSolutionDisplayName(newSolutionDisplayName);
		String newTimeZone = testData.getProperty("practice.timezone");
		editOrgNodePage.selectTimezone(newTimeZone);
		editOrgNodePage.selectOrgNodeStatus(practiceNodeStatusBeforeEdit);
		String newStatusValue = editOrgNodePage.getOrgStatus();
		addAndUpdateProductSolutionsPage = new AddAndUpdateProductSolutionsPage(driver);
		logStep("Select terminate check box for single product on the nge practice information page");
		String terminatedSolutionName = addAndUpdateProductSolutionsPage.selectTerminateCheckboxForSingleProduct();
		String[] list = terminatedSolutionName.split("\n");
		String solutionName = list[0].trim();
		logStep("Click on client directory icon");
		generalSettingsPage.clickOnClientDirectoryIcon();
		unsavedChangesPage = new UnsavedChangesPage(driver);
		logStep("Verify unsaved changes popup is displayed on screen");
		unsavedChangesPage.verifyUnsavedChangesPopupPageTitle();
		logStep("Click on disregard changes button on the unsaved changes popup");
		unsavedChangesPage.clickOnDisregardChangesButton();
		logStep("Verify user redirected to client directory page, after clicking on disregard changes button on the unsaved changes popup");
		clientDirectoryPage.verifyClientDirectioryPageTitle();
		logStep("search and select client name in client table");
		clientDirectoryPage
				.searchAndSelectClientNameInClientTable(testData.getProperty("search.client.in.client.directory"));
		logStep("Search and select client node on the general settings page");
		generalSettingsPage.searchNode(testData.getProperty("search.practice.in.client.directory"));
		logStep("Verify that the old nge practice details remain on the practice information page");
		CCUtils.assertNotEquals(practiceNameValueBeforeEdit, newPracticeName);
		CCUtils.assertNotEquals(practiceSapIdValueBeforeEdit, newPracticeSapId);
		CCUtils.assertNotEquals(practiceSolutionDisplayNameBeforeEdit, newSolutionDisplayName);
		CCUtils.assertNotEquals(practiceTimezoneValueBeforeEdit, newTimeZone);
		CCUtils.assertNotEquals(practiceNodeStatusBeforeEdit, newStatusValue);
		CCUtils.assertTrue(activeSolutionsList.contains(solutionName),
				"Selected solution is not present in active solution section");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "After clicking the disregard changes button on the unsaved changes popup, verify that the old agnostic practice details remain on the agnostic practice information page")
	public void testFunctionalityOfDisregardChangesBtnForAgnosticPracticeNode() throws Exception {
		logStep("Search and select agnostic practice node on the general settings page");
		generalSettingsPage.searchNode(testData.getProperty("search.agnostic.practice.in.client.directory"));
		logStep("Get agnostic practice details as: ");
		String practiceNameValueBeforeEdit = generalSettingsPage.getOrgName();
		String practiceSapIdValueBeforeEdit = generalSettingsPage.getOrgSapId();
		String practiceSolutionDisplayNameBeforeEdit = generalSettingsPage.getOrgSolutionDisplayName();
		String practiceTimezoneValueBeforeEdit = generalSettingsPage.getOrgTimezone();
		String practiceNodeStatusBeforeEdit = generalSettingsPage.getOrgStatus();
		activeSolutionsList = new ArrayList<String>();
		activeSolutionsList.addAll(generalSettingsPage.getListOfActiveSolutions());
		logStep("Click on edit button on the general settings page");
		generalSettingsPage.clickOnEditButtonOfOrg();
		logStep("Change details in agnostic practice name, sap id, timezone, solution display name and status fields");
		editOrgNodePage = new EditOrgNodePage(driver);
		String newPracticeName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		editOrgNodePage.clearOrgNameField();
		editOrgNodePage.enterOrgName(newPracticeName);
		String newPracticeSapId = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		editOrgNodePage.clearSapIdField();
		editOrgNodePage.enterOrgSapId(newPracticeSapId);
		String newSolutionDisplayName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		editOrgNodePage.clearSolutionDisplayNameField();
		editOrgNodePage.enterOrgSolutionDisplayName(newSolutionDisplayName);
		String newTimeZone = testData.getProperty("practice.timezone");
		editOrgNodePage.selectTimezone(newTimeZone);
		editOrgNodePage.selectOrgNodeStatus(practiceNodeStatusBeforeEdit);
		String newStatusValue = editOrgNodePage.getOrgStatus();
		addAndUpdateProductSolutionsPage = new AddAndUpdateProductSolutionsPage(driver);
		logStep("Select terminate check box for single product on the agnostic practice information page");
		String terminatedSolutionName = addAndUpdateProductSolutionsPage.selectTerminateCheckboxForSingleProduct();
		String[] list = terminatedSolutionName.split("\n");
		String solutionName = list[0].trim();
		logStep("Click on client directory icon");
		generalSettingsPage.clickOnClientDirectoryIcon();
		logStep("Verify unsaved changes popup is displayed on screen");
		unsavedChangesPage = new UnsavedChangesPage(driver);
		unsavedChangesPage.verifyUnsavedChangesPopupPageTitle();
		logStep("Click on disregard changes button on the unsaved changes popup");
		unsavedChangesPage.clickOnDisregardChangesButton();
		logStep("Verify user redirected to client directory page, after clicking on disregard changes button on the unsaved changes popup");
		clientDirectoryPage.verifyClientDirectioryPageTitle();
		logStep("Search and select client name in client table");
		clientDirectoryPage
				.searchAndSelectClientNameInClientTable(testData.getProperty("search.client.in.client.directory"));
		logStep("Search and select agnostic practice node on the general settings page");
		generalSettingsPage.searchNode(testData.getProperty("search.agnostic.practice.in.client.directory"));
		logStep("Verify that the old agnostic practice details remain on the practice information page");
		CCUtils.assertNotEquals(practiceNameValueBeforeEdit, newPracticeName);
		CCUtils.assertNotEquals(practiceSapIdValueBeforeEdit, newPracticeSapId);
		CCUtils.assertNotEquals(practiceSolutionDisplayNameBeforeEdit, newSolutionDisplayName);
		CCUtils.assertNotEquals(practiceTimezoneValueBeforeEdit, newTimeZone);
		CCUtils.assertNotEquals(practiceNodeStatusBeforeEdit, newStatusValue);
		CCUtils.assertTrue(activeSolutionsList.contains(solutionName),
				"Selected solution is not present in active solution section");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify unsaved changes popup is displayed on the screen when user navigates to another node without saving modified details in the current node")
	public void testFunctionalityOfUnsavedChangesPopup() throws Exception {
		logStep("Search and select system node on the general settings page");
		generalSettingsPage.searchNode(testData.getProperty("search.system.in.client.directory"));
		logStep("Click on edit button on the general settings page");
		generalSettingsPage.clickOnEditButtonOfOrg();
		logStep("Change details in system name,and sap id fields");
		editOrgNodePage = new EditOrgNodePage(driver);
		String newSystemName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		editOrgNodePage.clearOrgNameField();
		editOrgNodePage.enterOrgName(newSystemName);
		String newSystemSapId = CCUtils.generateAlphaNumericStringIncludingSpecialChar(5);
		editOrgNodePage.clearSapIdField();
		editOrgNodePage.enterOrgSapId(newSystemSapId);
		logStep("Click on client node in the tree");
		generalSettingsPage.selectClientNodeInTheTree();
		logStep("Verify unsaved changes popup is displayed on screen");
		unsavedChangesPage = new UnsavedChangesPage(driver);
		unsavedChangesPage.verifyUnsavedChangesPopupPageTitle();
	}
}
