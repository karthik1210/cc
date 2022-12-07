//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.util.CCUtils;

public class EditClientAndOrgDetailsGeneralSettingsTests extends CommandCenterGenericTest {
	static boolean runOnce = true;
	public CCUI ccui;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		if (runOnce) {
			ccui = new CCUI(testData, driver);
			logStep("Login to command center portal");
			ccui.getClientDirectoryPage();
			ccui.initializeTestData();
			getClientSystementErpriseandPractice();
		}
	}

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit org details section")
	public void testEditOrgDetailsSection() throws Exception {
		logStep("Verify edit org details section");
		ngHierarchyClientCreate();
		ccui.editOrgDetailsGeneralSettingsPage().clickClientOrg();
		ccui.editOrgDetailsGeneralSettingsPage().verifyEditOrgDetailsAtClientLevel();
		runOnce = false;
	}

	@Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Check error message for maximum character")
	public void checkErrorMessageForMaximumCharacter() throws Exception {
		ngHierarchyClientCreate();
		logStep("Check error message for maximum character");
		ccui.editBasicInfoGeneralSettingsPage().clickSystemOrg();
		ccui.editOrgDetailsGeneralSettingsPage().clickEditButton();
		String orgName = CCUtils.generateAlphaNumericString(101);
		ccui.editOrgDetailsGeneralSettingsPage().editOrgName(orgName);
		logStep("Verify error message");
		ccui.editOrgDetailsGeneralSettingsPage().verifyErrorMessage("Maximum characters reached");
		runOnce = false;
	}

	@Test(priority = 6, groups = {
			"smoke" }, retryAnalyzer = RetryAnalyzer.class, description = "Edit and save Client name and status")
	public void editAndSaveNameAndStatusAtClientLevel() throws Exception {
		ngHierarchyClientCreate();
		logStep("Edit and save System name and status");
		ccui.editBasicInfoGeneralSettingsPage().clickClientOrg();
		ccui.editOrgDetailsGeneralSettingsPage().clickEditButton();
		String orgName = CCUtils.generateAlphaNumericString(8);
		ccui.editOrgDetailsGeneralSettingsPage().EditAndSaveNameAndStatus(orgName);
		logStep("Verify success toaster message");
		ccui.editOrgDetailsGeneralSettingsPage().verifyToasterMessage("Changes updated successfully");
		runOnce = false;
	}

	@Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "Edit and save System name and status")
	public void editAndSaveNameAndStatusAtSystemLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
		logStep("Edit and save System name and status");
		ccui.editBasicInfoGeneralSettingsPage().clickSystemOrg();
		ccui.editOrgDetailsGeneralSettingsPage().clickEditButton();
		String orgName = CCUtils.generateAlphaNumericString(8);
		ccui.editOrgDetailsGeneralSettingsPage().EditAndSaveNameAndStatus(orgName);
		logStep("Verify success toaster message");
		ccui.editOrgDetailsGeneralSettingsPage().verifyToasterMessage("Changes updated successfully");
		runOnce = false;
	}

	@Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Edit and save Enterprise name and status")
	public void editAndSaveNameAndStatusAtEnterpriseLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
		ccui.getPrivacyPolicySettingsPage()
				.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
		logStep("Edit and save Enterprise name and status");
		ccui.editBasicInfoGeneralSettingsPage().clickEnterpriseOrg();
		ccui.editOrgDetailsGeneralSettingsPage().clickEditButton();
		String orgName = CCUtils.generateAlphaNumericString(8);
		ccui.editOrgDetailsGeneralSettingsPage().EditAndSaveNameAndStatus(orgName);
		logStep("Verify success toaster message");
		ccui.editOrgDetailsGeneralSettingsPage().verifyToasterMessage("Changes updated successfully");
		runOnce = false;
	}

	@Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Edit and save Practice name and status")
	public void editAndSaveNameAndStatusAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
		ccui.getPrivacyPolicySettingsPage()
				.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
		ccui.getPrivacyPolicySettingsPage().clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
		logStep("Edit and save Practice name and status");
		ccui.editOrgDetailsGeneralSettingsPage().clickEditButton();
		String orgName = CCUtils.generateAlphaNumericString(8);
		ccui.editOrgDetailsGeneralSettingsPage().EditAndSaveNameAndStatus(orgName);
		logStep("Verify success toaster message");
		ccui.editOrgDetailsGeneralSettingsPage().verifyToasterMessage("Changes updated successfully");
		runOnce = false;
	}

	@Test(enabled = false)
	public void ngHierarchyClientCreate() throws Exception {
		if (!runOnce) {
			ccui = new CCUI(testData, driver);
			logStep("Login to command center portal");
			LoginPage loginPage = new LoginPage(driver, testData.getProperty("base.url"));
			loginPage.login(testData.getProperty("user.name"), testData.getProperty("user.password"));
			logStep("Select create client button from new dropdown");
			logStep("Searching a client in client directory");
			ccui.getNgHierarchyClientCreationPage().searchNode(clientSystementErpriseandPractice.get("clientNam"));
			ccui.getNgHierarchyClientCreationPage().clickSearchButton();
			ccui.getNgHierarchyClientCreationPage().clickSearchResult();
			logStep("Navigating To General Settings page");
			ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
		}
	}
}
