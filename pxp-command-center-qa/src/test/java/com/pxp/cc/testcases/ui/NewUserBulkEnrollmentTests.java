// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.HashMap;

public class NewUserBulkEnrollmentTests extends CommandCenterGenericTest {

    public CCUI ccui;
    boolean runOnce = true;

    @BeforeMethod(alwaysRun = true)
    public void loginToCommandCenterPortal() throws Exception {
        if (runOnce) {
            ccui = new CCUI(testData, driver);
            logStep("Login to command center portal");
            ccui.getClientDirectoryPage();
            ccui.getUserBulkEnrollmentPage().clickOnElement(ccui.getUserBulkEnrollmentPage().getCommandCenterUsersIcon(), "Command Center User Icon");
        }
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Verifying elements on Bulk Enrollment")
    public void verifyElementsOnBulkEnrollment() throws Exception {
        initialPreRequisite();
        HashMap<String, Boolean> verificationAndValidation1 = new HashMap<String, Boolean>();
        HashMap<String, Boolean> verificationAndValidation2 = new HashMap<String, Boolean>();
        verificationAndValidation1 = ccui.getInternalVarResellerUserBulkEnrollmentPage().verifyUserBulkEnrollmentElements("Internal");
        verificationAndValidation2 = ccui.getInternalVarResellerUserBulkEnrollmentPage().verifyUserBulkEnrollmentElements("VarReseller");
        runOnce = false;
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, description = "Types of User Search On Bulk Enrollment")
    public void giventypeOfUserSearchOnBulkEnrollment() throws Exception {
        initialPreRequisite();
        ccui.getInternalVarResellerUserBulkEnrollmentPage().typesOfUserSearchOnBulkEnrollment("Inactive", "Internal");
        ccui.getInternalVarResellerUserBulkEnrollmentPage().typesOfUserSearchOnBulkEnrollment("Inactive", "VarReseller");
        ccui.getInternalVarResellerUserBulkEnrollmentPage().typesOfUserSearchOnBulkEnrollment("No roles", "Internal");
        ccui.getInternalVarResellerUserBulkEnrollmentPage().typesOfUserSearchOnBulkEnrollment("Locked", "Internal");
        ccui.getInternalVarResellerUserBulkEnrollmentPage().typesOfUserSearchOnBulkEnrollment("Locked", "VarReseller");
        ccui.getInternalVarResellerUserBulkEnrollmentPage().confirmedUserSearchOnBulkEnrollment("Internal");
        ccui.getInternalVarResellerUserBulkEnrollmentPage().confirmedUserSearchOnBulkEnrollment("VarReseller");
        runOnce = false;
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class, description = "Searching user with all attributes on bulk enrollment")
    public void verifyUserWithAllAttributes() throws InterruptedException, AWTException {
        initialPreRequisite();
        ccui.getInternalVarResellerUserBulkEnrollmentPage().userSearchWithAllAtributes("Internal");
        ccui.getInternalVarResellerUserBulkEnrollmentPage().userSearchWithAllAtributes("VarReseller");
        runOnce = false;
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "select and de-select users on bulk enrollment")
    public void validingSelectAndDeSelectUsers() throws Exception {
        initialPreRequisite();
        ccui.getInternalVarResellerUserBulkEnrollmentPage().selectAndDeselectInBulkEnrollment("Internal");
        ccui.getInternalVarResellerUserBulkEnrollmentPage().selectAndDeselectInBulkEnrollment("VarReseller");
        runOnce = false;
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class, description = "Types of Status of the users search on bulk enrollment")
    public void typesOfStatusUserSearchOnBulkEnrollment() throws InterruptedException, AWTException {
        initialPreRequisite();
        ccui.getInternalVarResellerUserBulkEnrollmentPage().typesOfStatusUserSearchOnBulkEnrollment("Not Sent", "Internal");
        ccui.getInternalVarResellerUserBulkEnrollmentPage().typesOfStatusUserSearchOnBulkEnrollment("Pending", "Internal");
        ccui.getInternalVarResellerUserBulkEnrollmentPage().typesOfStatusUserSearchOnBulkEnrollment("Expired", "Internal");
        ccui.getInternalVarResellerUserBulkEnrollmentPage().typesOfStatusUserSearchOnBulkEnrollment("Not Sent", "VarReseller");
        ccui.getInternalVarResellerUserBulkEnrollmentPage().typesOfStatusUserSearchOnBulkEnrollment("Pending", "VarReseller");
        ccui.getInternalVarResellerUserBulkEnrollmentPage().typesOfStatusUserSearchOnBulkEnrollment("Expired", "VarReseller");
        runOnce = false;
    }

    @Test(enabled = false)
    public void initialPreRequisite() throws InterruptedException {
        if (!runOnce) {
            ccui = new CCUI(testData, driver);
            logStep("Login to command center portal");
            ccui.getClientDirectoryPage();
            ccui.getUserBulkEnrollmentPage().clickCommandCenterUserIcon();
        }
    }
}
