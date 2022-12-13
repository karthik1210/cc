// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.LoginPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;

public class PatientAccessDetailsTests extends CommandCenterGenericTest {

    public boolean runOnce = true;
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

    @Test(priority = 1,retryAnalyzer = RetryAnalyzer.class, description = "Verifying Titles, labels and other elements are present for Patient Access Details Section")
    public void verifyPatientAccessSectionAvailability() throws Exception {
        ngHierarchyClientCreate();
        HashMap<String, Boolean> verification = new HashMap<String, Boolean>();
        verification = ccui.getPatientAccessPage().verifyPatientAccessDetailElements();
        runOnce = false;
    }

    @Test(priority = 2,retryAnalyzer = RetryAnalyzer.class, description = "Verifying Respective label and other elements are present for Patient Access Details Section")
    public void verifyEditandCancelLinkedAccountsAutoAgeoutGaurdianAccessDetails() throws Exception {
        ngHierarchyClientCreate();
        logStep("Edit and Cancel mode for Patient Access Details Section");
        ccui.getPatientAccessPage().validateEditandCancelPatientAccess();
        runOnce = false;
    }

    @Test(priority = 3,retryAnalyzer = RetryAnalyzer.class, description = "Verifying Respective label and other elements are present for Patient Access Details Section")
    public void verifyEditandSaveLinkedAccountsAutoAgeoutGaurdianAccessDetails() throws Exception {
        ngHierarchyClientCreate();
        logStep("Edit and Save mode for Patient Access Details Section");
        ccui.getPatientAccessPage().validateEditandSavePatientAccessDetails();
        ccui.getCommonMethods().verifyToasterMessage("Patient Access Rules updated Successfully");
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
            ccui.getPrivacyPolicySettingsPage().clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
            ccui.getPrivacyPolicySettingsPage().clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
        }
    }
}
