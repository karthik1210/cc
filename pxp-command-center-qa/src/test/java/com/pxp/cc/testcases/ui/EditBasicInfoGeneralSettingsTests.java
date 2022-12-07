//Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.util.CCUtils;

public class EditBasicInfoGeneralSettingsTests extends CommandCenterGenericTest {
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

	@Test(priority=3, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit basic info section at Client level")
	public void testBasicInfoSectionAtClientLevel() throws Exception {
		logStep("Verify Basic Info section At Client Level");
		ngHierarchyClientCreate();
		ccui.editBasicInfoGeneralSettingsPage().clickClientOrg();
		ccui.editBasicInfoGeneralSettingsPage().verifyEditBasicInfoAtClientLevel();
		runOnce = false;
	}

	@Test(priority=4, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button is displayed, edit and save basic info at Client level")
	public void editAndSaveBasicInfoSectionAtClientLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.editBasicInfoGeneralSettingsPage().clickClientOrg();
		ccui.editBasicInfoGeneralSettingsPage().verifyEditButtonIsDisplayed();
		ccui.editBasicInfoGeneralSettingsPage().clickEditButton();
		String clientSapNumber = testData.getProperty("new.client.sap")
				.concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
		String clientType = testData.getProperty("edit.client.type");
		ccui.editBasicInfoGeneralSettingsPage().editSapIdClientType(clientSapNumber, clientType);
		ccui.editBasicInfoGeneralSettingsPage().editTimezone(testData.getProperty("edit.timezone.display.name"));
		ccui.editBasicInfoGeneralSettingsPage().clickSaveButton();
		ccui.editBasicInfoGeneralSettingsPage().verifySuccessToasterMessage();
		runOnce = false;
	}

	@Test(priority=5, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit basic info section at System level")
	public void testBasicInfoSectionAtSystemLevel() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify Basic Info section At System Level");
		ccui.editBasicInfoGeneralSettingsPage().clickSystemOrg();
		ccui.editBasicInfoGeneralSettingsPage().verifyEditBasicInfoAtSystemLevel();
		runOnce = false;
	}

	@Test(priority=6, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button is displayed, edit and save basic info at System level")
	public void editAndSaveBasicInfoSectionAtSystemLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.editBasicInfoGeneralSettingsPage().clickSystemOrg();
		ccui.editBasicInfoGeneralSettingsPage().verifyEditButtonIsDisplayed();
		ccui.editBasicInfoGeneralSettingsPage().clickEditButton();
		ccui.editBasicInfoGeneralSettingsPage().clickSaveButton();
		ccui.editBasicInfoGeneralSettingsPage().verifySuccessToasterMessage();
		runOnce = false;
	}

	@Test(priority=7, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit basic info section at Enterprise level")
	public void testBasicInfoSectionAtEnterpriseLevel() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify Basic Info section At Enterprise Level");
		ccui.editBasicInfoGeneralSettingsPage().clickEnterpriseOrg();
		ccui.editBasicInfoGeneralSettingsPage().verifyEditBasicInfoAtEnterpriseLevel();
		runOnce = false;
	}

	@Test(priority=8, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button is displayed, edit and save basic info at Enterprise level")
	public void editAndSaveBasicInfoSectionAtEnterpriseLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.editBasicInfoGeneralSettingsPage().clickEnterpriseOrg();
		ccui.editBasicInfoGeneralSettingsPage().verifyEditButtonIsDisplayed();
		ccui.editBasicInfoGeneralSettingsPage().clickEditButton();
		ccui.editBasicInfoGeneralSettingsPage().editTimezone(testData.getProperty("edit.timezone.display.name"));
		ccui.editBasicInfoGeneralSettingsPage().clickSaveButton();
		ccui.editBasicInfoGeneralSettingsPage().verifySuccessToasterMessage();
		runOnce = false;
	}

	@Test(priority=1, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit basic info section at Practice level")
	public void testBasicInfoSectionAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		logStep("Verify Basic Info section At Practice Level");
		ccui.editBasicInfoGeneralSettingsPage().verifyEditBasicInfoAtPracticeLevel();
		runOnce = false;
	}

	@Test(priority=2, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button is displayed, edit and save basic info at Practice level")
	public void editAndSaveBasicInfoSectionAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.editBasicInfoGeneralSettingsPage().verifyEditButtonIsDisplayed();
		ccui.editBasicInfoGeneralSettingsPage().clickEditButton();
		ccui.editBasicInfoGeneralSettingsPage().enterSolutionsDisplayName("Engage");
		ccui.editBasicInfoGeneralSettingsPage().editTimezone(testData.getProperty("edit.timezone.display.name"));
		ccui.editBasicInfoGeneralSettingsPage().clickSaveButton();
		ccui.editBasicInfoGeneralSettingsPage().verifySuccessToasterMessage();
		runOnce = false;
	}
	
	@Test(priority=9, retryAnalyzer = RetryAnalyzer.class, description = "Verify Basic Info Section Collapse Arrow functionality")
	public void testBasicInfoSectionCollapseArrow() throws Exception {
		ngHierarchyClientCreate();
		ccui.editBasicInfoGeneralSettingsPage().clickOnBasicInfoCollapseButton();
		runOnce = false;
	}
	
	@Test(priority=10, retryAnalyzer = RetryAnalyzer.class, description = "Verify Basic Info Section Cancel Button Functionality")
	public void testCancelButtonFunctionality() throws Exception {
		ngHierarchyClientCreate();
		ccui.editBasicInfoGeneralSettingsPage().verifyEditButtonIsDisplayed();
		ccui.editBasicInfoGeneralSettingsPage().clickEditButton();		
		ccui.editBasicInfoGeneralSettingsPage().enterSolutionsDisplayName("Engage");
		ccui.editBasicInfoGeneralSettingsPage().editTimezone(testData.getProperty("edit.timezone.display.name"));
		ccui.editBasicInfoGeneralSettingsPage().clickOnCancelButton();
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
			ccui.getNgHierarchyClientCreationPage().clickSearchResult(clientSystementErpriseandPractice.get("clientNam"));
			logStep("Navigating To General Settings page");
			ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
			ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
			ccui.getPrivacyPolicySettingsPage()
					.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
			ccui.getPrivacyPolicySettingsPage()
					.clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
		}
	}

}
