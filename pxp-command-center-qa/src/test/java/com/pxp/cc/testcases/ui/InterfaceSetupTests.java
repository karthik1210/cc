// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.LoginPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;

public class InterfaceSetupTests extends CommandCenterGenericTest {

    static boolean runOnce = true;
    public CCUI ccui;
    public static List<String> fieldNames = new ArrayList<String>();
    public static List<String> editableFieldNamesForMedfusionRest_NextGen = new ArrayList<String>();
    public static List<String> editableFieldContentForMedfusionRest_NextGen = new ArrayList<String>();
    public static List<String> editableFieldNamesForGECentricityPM = new ArrayList<String>();
    public static List<String> editableFieldContentForGECentricityPM = new ArrayList<String>();
    public static List<String> editableFieldContentForGreenwayPrimeSuiteEMR = new ArrayList<String>();
    public static List<String> editableFieldNamesForGreenwayPrimeSuiteEMR = new ArrayList<String>();

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

    @Test(priority = 1, groups = {"smoke"}, retryAnalyzer = RetryAnalyzer.class, description = "Verifying Elements in Interface setup for the first time")
    public void verifyingElementsInInterfaceSetup() throws Exception {
        ngHierarchyClientCreate();
        HashMap<String, Boolean> verification = new HashMap<String, Boolean>();
        verification = ccui.getInterfaceSetupPage().verifyElementsInInterfaceSetup();
        ccui.getInterfaceSetupPage().clickOnCancelButton();
        runOnce = false;
    }

    @Test(priority = 2, groups = {"smoke"}, retryAnalyzer = RetryAnalyzer.class, description = "verifying External System Creation with No Value On Mandatory And Inactive For Interface Setup")
    public void verifyingExternalSystemCreationWithNoValueOnMandatoryAndInactiveForInterfaceSetup() throws Exception {
        ngHierarchyClientCreate();
        initiateData();
        logStep("PXPP-25742 TC016 Verify that whether user notified when forgot to add data for required fields for New Interface");
        ccui.getInterfaceSetupPage().clickOnNewButton();
        ccui.getInterfaceSetupPage().clickOnExternalSystemComboBox();
        if(driver.getCurrentUrl().contains("dev-command-center.dev.pxp-mf-cc.nextgenaws.net")) {
            ccui.getInterfaceSetupPage().selectExternalSystemInDropDown("MedfusionREST - NextGen");
            ccui.getInterfaceSetupPage().verifyElementsForExternalSystem("MedfusionREST - NextGen");
            ccui.getInterfaceSetupPage().enterValue(ccui.getInterfaceSetupPage().getNextgenEnterpriseIdTextBox(), "");
            ccui.getInterfaceSetupPage().verifyMandatoryValueMissingError("MedfusionREST - NextGen");
            ccui.getInterfaceSetupPage().clickOnCancelButton();
        }
        else if(driver.getCurrentUrl().contains("qa-command-center.dev.pxp-mf-cc.nextgenaws.net"))
        {
            ccui.getInterfaceSetupPage().selectExternalSystemInDropDown("Greenway PrimeSuite EMR");
            ccui.getInterfaceSetupPage().verifyElementsForExternalSystem("Greenway PrimeSuite EMR");
            ccui.getInterfaceSetupPage().enterValue(ccui.getInterfaceSetupPage().getExternalPracticeIdTextBox(), "");
            ccui.getInterfaceSetupPage().verifyMandatoryValueMissingError("Greenway PrimeSuite EMR");
            ccui.getInterfaceSetupPage().clickOnCancelButton();
        }
        logStep("PXPP-25743 TC017 Verify that whether user can create new Interface with Inactive Status.");
        ccui.getInterfaceSetupPage().clickOnNewButton();
        ccui.getInterfaceSetupPage().clickOnExternalSystemComboBox();
        ccui.getInterfaceSetupPage().selectExternalSystemInDropDown("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().verifyElementsForExternalSystem("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().enterValue(ccui.getInterfaceSetupPage().getNextgenEnterpriseIdTextBox(), editableFieldContentForMedfusionRest_NextGen.get(0));
        ccui.getInterfaceSetupPage().enterValue(ccui.getInterfaceSetupPage().getApiSiteIdTextBox(), editableFieldContentForMedfusionRest_NextGen.get(1));
        ccui.getInterfaceSetupPage().enterValue(ccui.getInterfaceSetupPage().getNextgenPracticeIdTextBox(), editableFieldContentForMedfusionRest_NextGen.get(2));
        ccui.getInterfaceSetupPage().clickRadioButton("new", "Inactive");
        ccui.getInterfaceSetupPage().clickOnSaveButton();
        ccui.getCommonMethods().verifyToasterMessage("Success! Interface added successfully");
        ccui.getInterfaceSetupPage().verifyInterfaceSetupStatus("MedfusionREST - NextGen", "Inactive");
        ccui.getInterfaceSetupPage().verifyInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnKebabDelete();
        ccui.getInterfaceSetupPage().clickOnYesDelete();
        ccui.getCommonMethods().verifyToasterMessage("Success! Interface has been successfully deleted");
        runOnce = false;
    }


    @Test(priority = 3, groups = {"smoke"}, retryAnalyzer = RetryAnalyzer.class, description = "Verifying Basic Functionality in Interface setup for the first time")
    public void verifyingBasicFunctionalityInInterfaceSetup() throws Exception {
        ngHierarchyClientCreate();
        createNewInterface("MedfusionREST - NextGen");
        runOnce = false;
    }


    @Test(priority = 4, groups = {"smoke"}, retryAnalyzer = RetryAnalyzer.class, description = "Verifying Edit Functionality in Interface setup for the first time")
    public void verifyingEditFunctionalityInInterfaceSetup() throws Exception {
        ngHierarchyClientCreate();
        if (runOnce) {
            createNewInterface("MedfusionREST - NextGen");
        }
        if (!runOnce)
            ccui.getInterfaceSetupPage().clickExpandButtonInInterfaceSetUp();
        logStep("PXPP-25725 TC007 Verify Kebab menu options for Active Interfaces under Interface Setup Section");
        ccui.getInterfaceSetupPage().verifyInterfaceSetupStatus("MedfusionREST - NextGen", "Active");
        ccui.getInterfaceSetupPage().verifyInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().verifyKebabEdit();
        ccui.getInterfaceSetupPage().clickOnInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnExpandButtonOfExternalSystem("MedfusionREST - NextGen");
        logStep("PXPP-25726 TC008 Verify that user can view the auto generated field for \"MedfusionREST -NextGen\" interface under Interface Setup Section");
        ccui.getInterfaceSetupPage().verifyAutoGeneratedFields("MedfusionREST - NextGen", fieldNames);
        ccui.getInterfaceSetupPage().verifyEditableContent("MedfusionREST - NextGen", editableFieldNamesForMedfusionRest_NextGen, editableFieldContentForMedfusionRest_NextGen);
        logStep("PXPP-25728 TC009 Verify that user can able to Edit and Cancel interface \"MedfusionREST -NextGen\" under Interface Setup Section");
        ccui.getInterfaceSetupPage().verifyInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnKebabEdit();
        ccui.getInterfaceSetupPage().updateValue(ccui.getInterfaceSetupPage().getNextgenEnterpriseIdTextBox(), editableFieldContentForMedfusionRest_NextGen.get(4));
        ccui.getInterfaceSetupPage().updateValue(ccui.getInterfaceSetupPage().getApiSiteIdTextBox(), editableFieldContentForMedfusionRest_NextGen.get(5));
        ccui.getInterfaceSetupPage().clickOnCancelButtonOnEdit();
        ccui.getInterfaceSetupPage().clickOnExpandButtonOfExternalSystem("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().verifyEditableContent("MedfusionREST - NextGen", editableFieldNamesForMedfusionRest_NextGen, editableFieldContentForMedfusionRest_NextGen);
        logStep("PXPP-25738 TC010 Verify that user can able to Edit and Save interface \"MedfusionREST -NextGen\" under Interface Setup Section");
        ccui.getInterfaceSetupPage().verifyInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnKebabEdit();
        ccui.getInterfaceSetupPage().updateValue(ccui.getInterfaceSetupPage().getNextgenEnterpriseIdTextBox(), editableFieldContentForMedfusionRest_NextGen.get(3));
        ccui.getInterfaceSetupPage().updateValue(ccui.getInterfaceSetupPage().getApiSiteIdTextBox(), editableFieldContentForMedfusionRest_NextGen.get(4));
        ccui.getInterfaceSetupPage().clickOnSaveButtonOnEdit();
        ccui.getCommonMethods().verifyToasterMessage("Changes updated successfully");
        ccui.getInterfaceSetupPage().verifyEditableContent("MedfusionREST - NextGen", editableFieldNamesForMedfusionRest_NextGen, editableFieldContentForMedfusionRest_NextGen);
        logStep("PXPP-24223 TC011 Verify that whether User can change status from Active to Inactive interface under Interface Setup Section");
        ccui.getInterfaceSetupPage().verifyInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnKebabEdit();
        ccui.getInterfaceSetupPage().clickRadioButton("MedfusionREST - NextGen", "Inactive");
        ccui.getInterfaceSetupPage().clickOnSaveButtonOnEdit();
        ccui.getCommonMethods().verifyToasterMessage("Changes updated successfully");
        ccui.getInterfaceSetupPage().verifyInterfaceSetupStatus("MedfusionREST - NextGen", "Inactive");
        logStep("PXPP-25739 TC012 Verify Kebab menu options for Inactive Interfaces under Interface Setup Section");
        ccui.getInterfaceSetupPage().verifyInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().verifyKebabEdit();
        ccui.getInterfaceSetupPage().verifyKebabDelete();
        runOnce = false;
    }

    @Test(priority = 5, groups = {"smoke"}, retryAnalyzer = RetryAnalyzer.class, description = "Verifying Edit for Inactive Interface setup")
    public void verifyingEditForInactiveInterfaceSetup() throws Exception {
        ngHierarchyClientCreate();
        if (runOnce) {
            createNewInterface("MedfusionREST - NextGen");
            inactiveExternalSystemInInterfaceSetup("MedfusionREST - NextGen");
        }
        logStep("PXPP-24231 TC013 Verify that whether user can edit Inactive interface and make it activate under Interface Setup Section");
        if (!runOnce)
            ccui.getInterfaceSetupPage().clickExpandButtonInInterfaceSetUp();
        ccui.getInterfaceSetupPage().verifyInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnKebabEdit();
        ccui.getInterfaceSetupPage().clickRadioButton("MedfusionREST - NextGen", "Inactive");
        ccui.getInterfaceSetupPage().clickRadioButton("MedfusionREST - NextGen", "Active");
        ccui.getInterfaceSetupPage().clickOnSaveButtonOnEdit();
        ccui.getCommonMethods().verifyToasterMessage("Changes updated successfully");
        logStep("PXPP-25470 TC014 Verify that whether user can delete the Inactive Interface in Interface Setup Section");
        ccui.getInterfaceSetupPage().verifyInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnKebabEdit();
        ccui.getInterfaceSetupPage().clickRadioButton("MedfusionREST - NextGen", "Inactive");
        ccui.getInterfaceSetupPage().clickOnSaveButtonOnEdit();
        ccui.getCommonMethods().verifyToasterMessage("Changes updated successfully");
        ccui.getInterfaceSetupPage().verifyInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnKebabDelete();
        ccui.getInterfaceSetupPage().clickonCancelInDialogBox();
        driver.navigate().refresh();
        ccui.getInterfaceSetupPage().clickExpandButtonInInterfaceSetUp();
        ccui.getInterfaceSetupPage().verifyInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnKebabDelete();
        ccui.getInterfaceSetupPage().clickOnYesDelete();
        ccui.getCommonMethods().verifyToasterMessage("Success! Interface has been successfully deleted");
        logStep("PXPP-25471 TC015 Verify that whether user can expand the multiple interfaces in Interface Setup Section");
        createNewInterface("GE Centricity PM");
        createNewInterface("Greenway PrimeSuite EMR");
        createNewInterface("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().clickOnExpandButtonOfExternalSystem("MedfusionREST - NextGen");
        ccui.getInterfaceSetupPage().verifyAutoGeneratedFields("MedfusionREST - NextGen", fieldNames);
        ccui.getInterfaceSetupPage().verifyEditableContent("MedfusionREST - NextGen", editableFieldNamesForMedfusionRest_NextGen, editableFieldContentForMedfusionRest_NextGen);
        ccui.getInterfaceSetupPage().clickOnExpandButtonOfExternalSystem("GE Centricity PM");
        ccui.getInterfaceSetupPage().verifyAutoGeneratedFields("GE Centricity PM", fieldNames);
        ccui.getInterfaceSetupPage().verifyEditableContent("GE Centricity PM", editableFieldNamesForGECentricityPM, editableFieldContentForGECentricityPM);
        ccui.getInterfaceSetupPage().clickOnExpandButtonOfExternalSystem("Greenway PrimeSuite EMR");
        ccui.getInterfaceSetupPage().verifyAutoGeneratedFields("Greenway PrimeSuite EMR", fieldNames);
        ccui.getInterfaceSetupPage().verifyEditableContent("Greenway PrimeSuite EMR", editableFieldNamesForGreenwayPrimeSuiteEMR, editableFieldContentForGreenwayPrimeSuiteEMR);
        ccui.getInterfaceSetupPage().clickOnSortingArrow();
        ccui.getInterfaceSetupPage().verifySorting("Ascending");
        ccui.getInterfaceSetupPage().clickOnSortingArrow();
        ccui.getInterfaceSetupPage().verifySorting("Descending");
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
            ccui.getPrivacyPolicySettingsPage().clickOnSystemArrow(clientSystementErpriseandPractice.get("systemName"));
            ccui.getPrivacyPolicySettingsPage().clickOnEnterpriseArrow(clientSystementErpriseandPractice.get("enterpriseName"));
            ccui.getPrivacyPolicySettingsPage().clickOnPracticeLevel(clientSystementErpriseandPractice.get("practiceName"));
        }
    }

    @Test(enabled = false)
    public void createNewInterface(String externalSystem) throws InterruptedException {
        initiateData();
        if (externalSystem.equalsIgnoreCase("MedfusionREST - NextGen")) {
            logStep("PXPP-24189 TC004 Verify that user can able to click on \"+ New \" button and able to select all the fields in new page under Interface Setup Section");
            ccui.getInterfaceSetupPage().clickOnNewButton();
            ccui.getInterfaceSetupPage().clickOnExternalSystemComboBox();
            ccui.getInterfaceSetupPage().selectExternalSystemInDropDown("MedfusionREST - NextGen");
            ccui.getInterfaceSetupPage().verifyElementsForExternalSystem("MedfusionREST - NextGen");
            logStep("PXPP-24149 TC006 Verify that user can able to Add new interface with external name \"MedfusionREST -NextGen\" under Interface Setup Section");
            ccui.getInterfaceSetupPage().enterValue(ccui.getInterfaceSetupPage().getNextgenEnterpriseIdTextBox(), editableFieldContentForMedfusionRest_NextGen.get(0));
            ccui.getInterfaceSetupPage().enterValue(ccui.getInterfaceSetupPage().getApiSiteIdTextBox(), editableFieldContentForMedfusionRest_NextGen.get(1));
            ccui.getInterfaceSetupPage().enterValue(ccui.getInterfaceSetupPage().getNextgenPracticeIdTextBox(), editableFieldContentForMedfusionRest_NextGen.get(2));
            ccui.getInterfaceSetupPage().verifyRadioButtonEnabled("Active");
            ccui.getInterfaceSetupPage().clickOnSaveButton();
            ccui.getCommonMethods().verifyToasterMessage("Success! Interface added successfully");
        } else if (externalSystem.equalsIgnoreCase("GE Centricity PM")) {
            editableFieldContentForGECentricityPM.add("External Practice 001");
            editableFieldNamesForGECentricityPM.add("External Practice ID");
            editableFieldContentForGECentricityPM.add("Practiceupdate001");
            ccui.getInterfaceSetupPage().clickOnNewButton();
            ccui.getInterfaceSetupPage().clickOnExternalSystemComboBox();
            ccui.getInterfaceSetupPage().selectExternalSystemInDropDown("GE Centricity PM");
            ccui.getInterfaceSetupPage().verifyElementsForExternalSystem("GE Centricity PM");
            ccui.getInterfaceSetupPage().enterValue(ccui.getInterfaceSetupPage().getExternalPracticeIdTextBox(), editableFieldContentForGECentricityPM.get(0));
            ccui.getInterfaceSetupPage().verifyRadioButtonEnabled("Active");
            ccui.getInterfaceSetupPage().clickOnSaveButton();
            ccui.getCommonMethods().verifyToasterMessage("Success! Interface added successfully");
        } else if (externalSystem.equalsIgnoreCase("Greenway PrimeSuite EMR")) {
            editableFieldNamesForGreenwayPrimeSuiteEMR.add("External Practice ID");
            editableFieldContentForGreenwayPrimeSuiteEMR.add("External Practice 001");
            editableFieldContentForGreenwayPrimeSuiteEMR.add("PracticeUpdate001");
            ccui.getInterfaceSetupPage().clickOnNewButton();
            ccui.getInterfaceSetupPage().clickOnExternalSystemComboBox();
            ccui.getInterfaceSetupPage().selectExternalSystemInDropDown("Greenway PrimeSuite EMR");
            ccui.getInterfaceSetupPage().verifyElementsForExternalSystem("Greenway PrimeSuite EMR");
            ccui.getInterfaceSetupPage().enterValue(ccui.getInterfaceSetupPage().getExternalPracticeIdTextBox(), editableFieldContentForGreenwayPrimeSuiteEMR.get(0));
            ccui.getInterfaceSetupPage().verifyRadioButtonEnabled("Active");
            ccui.getInterfaceSetupPage().clickOnSaveButton();
            ccui.getCommonMethods().verifyToasterMessage("Success! Interface added successfully");
        }
    }

    @Test(enabled = false)
    public void inactiveExternalSystemInInterfaceSetup(String externalSystem) throws InterruptedException {
        if (externalSystem.equalsIgnoreCase("MedfusionREST - NextGen")) {
            ccui.getInterfaceSetupPage().clickOnExpandButtonOfExternalSystem("MedfusionREST - NextGen");
            ccui.getInterfaceSetupPage().verifyInterfaceSetupKebabMenu("MedfusionREST - NextGen");
            ccui.getInterfaceSetupPage().clickOnInterfaceSetupKebabMenu("MedfusionREST - NextGen");
            ccui.getInterfaceSetupPage().clickOnKebabEdit();
            ccui.getInterfaceSetupPage().clickRadioButton("MedfusionREST - NextGen", "Inactive");
            ccui.getInterfaceSetupPage().clickOnSaveButtonOnEdit();
            ccui.getCommonMethods().verifyToasterMessage("Changes updated successfully");
        }
    }

    @Test(enabled = false)
    public void initiateData() {
        if (fieldNames.isEmpty()) {
            fieldNames.add("Integration Practice ID");
            fieldNames.add("Username");
            fieldNames.add("Password");
            fieldNames.add("App Token");
        }
        if (editableFieldNamesForMedfusionRest_NextGen.isEmpty()) {
            editableFieldNamesForMedfusionRest_NextGen.add("NextGen Enterprise ID");
            editableFieldNamesForMedfusionRest_NextGen.add("API Site ID");
            editableFieldNamesForMedfusionRest_NextGen.add("NextGen Practice ID");
        }
        if (editableFieldContentForMedfusionRest_NextGen.isEmpty()) {
            editableFieldContentForMedfusionRest_NextGen.add("NextGen Enterprise 001");
            editableFieldContentForMedfusionRest_NextGen.add("Api Site 001");
            editableFieldContentForMedfusionRest_NextGen.add("NextGen Practice 001");
            editableFieldContentForMedfusionRest_NextGen.add("Enterpriseupdate01");
            editableFieldContentForMedfusionRest_NextGen.add("Apiupdate001");
            editableFieldContentForMedfusionRest_NextGen.add("Practiceupdate001");
        }
    }
}
