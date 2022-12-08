// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.LoginPage;

public class AddAndUpdateProductSolutionsTests extends CommandCenterGenericTest {
	public static boolean runOnce = true;
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

	@Test(priority = 0,retryAnalyzer = RetryAnalyzer.class, description = "Verify activate all the solutions within practice from available to purchase section")
	public void testAddPurchaseSolutionAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().verifyAvailableToPurchaseSection();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfVirtualVisitsSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPaySolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToActivateButtonOfEngageSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToActivateButtonOfChatSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPortalSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		ccui.getGeneralSettingsPage().clickToActivateButtonOfPSSSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		runOnce = false;
	}

	@Test(priority = 1,retryAnalyzer = RetryAnalyzer.class, description = "Verify terminate all the solutions within practice from available to purchase section")
	public void testTerminateVirtualVisitsPurchaseSolutionAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().clickToVirtualVisitsMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		runOnce = false;
	}

	@Test(priority = 2,retryAnalyzer = RetryAnalyzer.class, description = "Verify terminate all the solutions within practice from available to purchase section")
	public void testTerminatePayPurchaseSolutionAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().clickToPayMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		runOnce = false;
	}

	@Test(priority = 3,retryAnalyzer = RetryAnalyzer.class, description = "Verify terminate all the solutions within practice from available to purchase section")
	public void testTerminateEngagePurchaseSolutionAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().clickToEngageMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		runOnce = false;
	}

	@Test(priority = 4,retryAnalyzer = RetryAnalyzer.class, description = "Verify terminate all the solutions within practice from available to purchase section")
	public void testTerminateChatPurchaseSolutionAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().clickToPatientChatMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		runOnce = false;
	}

	@Test(priority = 5,retryAnalyzer = RetryAnalyzer.class, description = "Verify terminate all the solutions within practice from available to purchase section")
	public void testTerminatePortalPurchaseSolutionAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().clickToPatientPortalMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		runOnce = false;
	}

	@Test(priority = 6,retryAnalyzer = RetryAnalyzer.class, description = "Verify terminate all the solutions within practice from available to purchase section")
	public void testTerminatePSSPurchaseSolutionAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().clickToPSSMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		runOnce = false;
	}
	
	@Test(priority = 7,retryAnalyzer = RetryAnalyzer.class, description = "Verify activate pop up web elements")
	public void testActivatePopupVirtualVisitsPurchaseSolutionAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfVirtualVisitsSolution();
		String pracName = clientSystementErpriseandPractice.get("practiceName");
		log("name of practice : "+pracName);
		ccui.getGeneralSettingsPage().verifyActivatePopupTitle();
		ccui.getGeneralSettingsPage().verifyActivateTerminatePopupCancelButtonAndCrossIcon();
		ccui.getGeneralSettingsPage().cautionActivateMessage(pracName);
		runOnce = false;
	}
	
	@Test(priority = 8,retryAnalyzer = RetryAnalyzer.class, description = "Verify terminate pop up web elements")
	public void testTerminatePopupVirtualVisitsPurchaseSolutionAtPracticeLevel() throws Exception {
		ngHierarchyClientCreate();
		ccui.getGeneralSettingsPage().verifyPurchaseSolutionsSection();
		ccui.getGeneralSettingsPage().clickToExpandPurchaseSolutions();
		ccui.getGeneralSettingsPage().clickToActivateButtonOfVirtualVisitsSolution();
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
		String pracName = clientSystementErpriseandPractice.get("practiceName");
		log("name of practice : "+pracName);
		ccui.getGeneralSettingsPage().clickToVirtualVisitsMoreOptions();
		ccui.getGeneralSettingsPage().clickToTerminateOption();
		ccui.getGeneralSettingsPage().verifyTerminatePopupTitle();
		ccui.getGeneralSettingsPage().verifyActivateTerminatePopupCancelButtonAndCrossIcon();
		ccui.getGeneralSettingsPage().cautionTerminateMessage(pracName);
		ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
		ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");		
		runOnce = false;
	}
}
