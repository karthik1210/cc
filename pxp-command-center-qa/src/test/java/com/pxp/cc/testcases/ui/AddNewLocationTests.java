// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import com.pxp.cc.objectmaps.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

import java.util.HashMap;

import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;

public class AddNewLocationTests extends CommandCenterGenericTest {
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

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify add new location popup")
    public void testAddNewLocationPopup() throws Exception {
        ngHierarchyClientCreate();
        addLocationPopUp();
        logStep("Verify add new location popup");
        ccui.getAddLocationPage().verifyAddNewLocationPopup();
        runOnce = false;
    }

    @Test(retryAnalyzer = RetryAnalyzer.class,  priority = 1, description = "Verify navigation to Locations page with Locations tab and Locations tile on General Settins page")
    public void testNavigationToLocationsPageWithLocationsTile() throws Exception {
        ngHierarchyClientCreate();
        addLocationPopUp();
        logStep("Click on cancel button");
        ccui.getAddLocationPage().clickOnCancelButton();
        logStep("Click on General Settings tab");
        ccui.getGeneralSettingsPage().clickOnGeneralSettingsTab();
        logStep("Click on Locations tile on General Settings page");
        ccui.getGeneralSettingsPage().clickOnLocationsTile();
        logStep("Verify after clicking on Locations tile user directed to Locations page");
        ccui.getAddLocationPage().verifyLocationsPageTitle();
        runOnce = false;
    }

    @Test(retryAnalyzer = RetryAnalyzer.class,  priority = 2, groups = {"buildvalidation"}, description = "Verify add new location functionality with valid details in all input fields")
    public void testAddNewLocationFunctionalityWithAllValidDetails() throws Exception {
        ngHierarchyClientCreate();
        addLocationPopUp();
        logStep("Enter data in all input fields");
        ccui.getAddLocationPage().enterLocationName(CCUtils.generateAlphaNumericString(18));
        ccui.getAddLocationPage().enterLocationDisplayName(CCUtils.generateAlphaNumericString(18));
        ccui.getAddLocationPage().enterAddressOne(CCUtils.generateAlphaNumericString(18));
        ccui.getAddLocationPage().enterAddressTwo(CCUtils.generateAlphaNumericString(18));
        ccui.getAddLocationPage().enterCity(CCUtils.generateAlphaNumericString(18));
        ccui.getAddLocationPage().enterLocationIdExternal(CCUtils.generateAlphaNumericString(18));
        ccui.getAddLocationPage().enterZip(CCUtils.generateAlphaNumericString(18));
        ccui.getAddLocationPage().addStateAndTimezone(testData.getProperty("new.locations.state"),
                testData.getProperty("new.locations.timezone"));
        logStep("Click on save and add button");
        ccui.getAddLocationPage().clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        ccui.getAddLocationPage().verifyToasterMessage("Success! New location has been successfully created");
        runOnce = false;
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, priority = 3, description = "Verify add new location functionality with valid details in all mandatory fields")
    public void testAddNewLocationFunctionalityWithAllValidDetailsInMandatoryFields() throws Exception {
        ngHierarchyClientCreate();
        addLocationPopUp();
        logStep("Enter data in mandatory fields");
        ccui.getAddLocationPage().enterLocationName(CCUtils.generateAlphaNumericString(18));
        ccui.getAddLocationPage().enterLocationDisplayName(CCUtils.generateAlphaNumericString(18));
        ccui.getAddLocationPage().enterAddressOne(CCUtils.generateAlphaNumericString(18));
        ccui.getAddLocationPage().enterCity(CCUtils.generateAlphaNumericString(18));
        ccui.getAddLocationPage().enterLocationIdExternal(CCUtils.generateAlphaNumericString(18));
        ccui.getAddLocationPage().enterZip(CCUtils.generateAlphaNumericString(18));
        ccui.getAddLocationPage().addStateAndTimezone(testData.getProperty("new.locations.state"),
                testData.getProperty("new.locations.timezone"));
        logStep("Click on save and add button");
        ccui.getAddLocationPage().clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        ccui.getAddLocationPage().verifyToasterMessage("Success! New location has been successfully created");
        runOnce = false;
    }

    @Test(retryAnalyzer = RetryAnalyzer.class,  priority = 4, description = "Enter more than 100 characters in locationName, locationDisplayName, Address1, city, zip, locationId fields and verify error message")
    public void testHundredMaximumCharactersErrorMessageForInputFields() throws Exception {
        ngHierarchyClientCreate();
        addLocationPopUp();
        logStep("Verify maximum character reached error message for locationName, locationDisplayName, Address1");
        ccui.getAddLocationPage().verifyHundredMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101),
                CCUtils.generateAlphaNumericString(101), CCUtils.generateAlphaNumericStringIncludingSpecialChar(101));
		logStep("Verify maximum character reached error message for city, zipcode, locationId fields");
		ccui.getAddLocationPage().verifyFiftyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(51),
				CCUtils.generateAlphaNumericString(51), CCUtils.generateAlphaNumericStringIncludingSpecialChar(51));
        runOnce = false;
    }

    @Test(retryAnalyzer = RetryAnalyzer.class,  priority = 5, description = "Verify functionality of cancel button and cross icon")
    public void testCancelAndCrossIconFunctionality() throws Exception {
        ngHierarchyClientCreate();
        addLocationPopUp();
        logStep("Click on cancel button");
        ccui.getAddLocationPage().clickOnCancelButton();
        logStep("Verify after clicking on cancel button user redirected to Locations page");
        ccui.getAddLocationPage().verifyLocationsPageTitle();
        logStep("Click on Add button");
        ccui.getAddLocationPage().clickOnAddLocationButton();
        logStep("Click on cross button");
        ccui.getAddLocationPage().clickOnCrossIcon();
        logStep("Verify after clicking on cross button user redirected to Locations page");
        ccui.getAddLocationPage().verifyLocationsPageTitle();
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
        }
    }

    @Test(enabled = false)
    public void addLocationPopUp() throws Exception {
        ccui.getGeneralSettingsPage().navigatetoClientNode(clientSystementErpriseandPractice.get("clientNam"));
        logStep("Verify Locations tab is displayed");
        ccui.getGeneralSettingsPage().verifyLocationsTab();
        ccui.getGeneralSettingsPage().clickOnLocationsTab();
        logStep("Click on Add button");
        ccui.getAddLocationPage().verifyLocationsPageTitle();
        ccui.getAddLocationPage().verifyAddLocationButton();
        ccui.getAddLocationPage().clickOnAddLocationButton();
    }
}
