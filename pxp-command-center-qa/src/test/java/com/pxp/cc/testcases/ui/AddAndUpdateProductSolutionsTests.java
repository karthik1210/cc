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
				ccui.getNgHierarchyClientCreationPage().clickSearchResult();
				logStep("Navigating To General Settings page");
				ccui.getGeneralSettingsPage().verifyClientNodeIsSelected();
				ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
				ccui.getPrivacyPolicySettingsPage()
						.clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
				ccui.getPrivacyPolicySettingsPage()
						.clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
			}
		}

	    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify activate all the solutions within practice from available to purchase section")
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
	    
	    @Test(priority=0,retryAnalyzer = RetryAnalyzer.class, description = "Verify terminate all the solutions within practice from available to purchase section")
		public void testTerminatePurchaseSolutionAtPracticeLevel() throws Exception {
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
			ccui.getGeneralSettingsPage().clickToVirtualVisitsMoreOptions();
			ccui.getGeneralSettingsPage().clickToTerminateOption();
			ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
			ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
			ccui.getGeneralSettingsPage().clickToPayMoreOptions();
			ccui.getGeneralSettingsPage().clickToTerminateOption();
			ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
			ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
			ccui.getGeneralSettingsPage().clickToEngageMoreOptions();
			ccui.getGeneralSettingsPage().clickToTerminateOption();
			ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
			ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
			ccui.getGeneralSettingsPage().clickToPatientChatMoreOptions();
			ccui.getGeneralSettingsPage().clickToTerminateOption();
			ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
			ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
			ccui.getGeneralSettingsPage().clickToPatientPortalMoreOptions();
			ccui.getGeneralSettingsPage().clickToTerminateOption();
			ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
			ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
			ccui.getGeneralSettingsPage().clickToPSSMoreOptions();
			ccui.getGeneralSettingsPage().clickToTerminateOption();
			ccui.getGeneralSettingsPage().clickToYesActivateButtonInActivateSolutions();
			ccui.getCommonMethods().verifyToasterMessage("Success! Solution has been successfully updated");
			runOnce = false;
		}
}
