// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import com.pxp.cc.objectmaps.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

import static com.pxp.cc.testcases.ui.CCUI.clientSystementErpriseandPractice;
import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;

public class AddNewProviderTests extends CommandCenterGenericTest {
    static boolean runOnce = true;
    public CCUI ccui;
    String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);

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

    @Test(retryAnalyzer = RetryAnalyzer.class, priority = 1, description = "Create new provider using valid test data")
    public void testAddNewProviderFunctionalityUsingValidData() throws Exception {
        ngHierarchyClientCreate();
        providerNavigation();
        String firstName = "New"
                + CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
        String lastName = CCUtils.generateAlphaNumericString(5).concat("-")
                .concat(CCUtils.generateAlphaNumericString(6)).concat("'s");
        String credentials = testData.getProperty("provider.credentials");
        logStep("Enter valid details in mandatory field and verify success toaster message");
        ccui.getAddNewProviderPage().enterFirstName(firstName);
        ccui.getAddNewProviderPage().enterLastName(lastName);
        ccui.getAddNewProviderPage().enterCredentials(credentials);
        ccui.getAddNewProviderPage().enterExternalId(externalId);
        ccui.getAddNewProviderPage().selectGenderValue("Male");
        ccui.getAddNewProviderPage().verifySaveAndAddButtonIsEnabled();
        ccui.getAddNewProviderPage().clickOnSaveAndAddButton();
        ccui.getCommonMethods().verifyToasterMessage("Success! New Provider has been successfully created");
        runOnce = false;
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, priority = 2, description = "Verify Add New Provider page UI")
    public void testAddNewProviderPageUi() throws Exception {
        ngHierarchyClientCreate();
        providerNavigation();
        logStep("Verify Add a provider page UI");
        ccui.getAddNewProviderPage().VerifyAddNewProviderPageUi();
        logStep("Verify Maximum characters functionality for input fields");
        ccui.getAddNewProviderPage().enterFirstName(CCUtils.generateAlphaNumericString(101));
        CCUtils.assertEquals(ccui.getAddNewProviderPage().getMaximumCharacterErrorForFirstNameField(),
                "Maximum characters reached");
        ccui.getAddNewProviderPage().enterLastName(CCUtils.generateAlphaNumericString(101));
        CCUtils.assertEquals(ccui.getAddNewProviderPage().getMaximumCharacterErrorForLastNameField(),
                "Maximum characters reached");
        ccui.getAddNewProviderPage().enterExternalId(CCUtils.generateAlphaNumericString(51));
        CCUtils.assertEquals(ccui.getAddNewProviderPage().getMaximumCharacterErrorForExternalIdField(),
                "Maximum characters reached");
        ccui.getAddNewProviderPage().enterCredentials(CCUtils.generateAlphaNumericString(51));
        CCUtils.assertEquals(ccui.getAddNewProviderPage().getMaximumCharacterErrorForCredentialField(),
                "Maximum characters reached");
        logStep("Enter invalid value gender field and verify error message");
        ccui.getAddNewProviderPage().clickOnGenderDropdown();
        ccui.getAddNewProviderPage().enterGender("abcdef");
        CCUtils.pressEnterKey();
        ccui.getAddNewProviderPage().verifyNoItemsFound();
        logStep("Verify Cancel button functionality on add a provider page");
        ccui.getAddNewProviderPage().clickOnCancelButton();
        ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
        logStep("Verify Cross button functionality on add a provider page");
        ccui.getAddNewProviderPage().clickOnAddProviderButton();
        ccui.getAddNewProviderPage().clickOnCrossIcon();
        ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
        logStep("Enter invalid first name and last name in firestName and lastName field");
        ccui.getAddNewProviderPage().clickOnAddProviderButton();
        ccui.getAddNewProviderPage().enterFirstName(testData.getProperty("invalid.user.first.name.one"));
        CCUtils.assertEquals(ccui.getAddNewProviderPage().getValidErrorForFirstNameField(), "Please enter valid first name");
        ccui.getAddNewProviderPage().enterLastName(testData.getProperty("invalid.user.last.name.one"));
        CCUtils.assertEquals(ccui.getAddNewProviderPage().getValidErrorForLastNameField(), "Please enter valid last name");
//        These lines are commented due to external id is not saved in DB . hence we cann't do validation.
//        logStep("Enter existing external id in externalId field and verify error message");
//        ccui.getAddNewProviderPage().enterExternalId(externalId);
//        CCUtils.assertEquals(ccui.getAddNewProviderPage().getUniqueErrorMessageForExternalIdField(), "External Id already in use");
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

    @Test(enabled = false)
    public void providerNavigation() throws Exception {
        logStep("Verify Providers And Resources tab is displayed");
        ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
        ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
        ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
        logStep("Verify ADD button is enabled for client level on provider page");
        ccui.getAddNewProviderPage().verifyAddButtonIsEnabled();
        ccui.getAddNewProviderPage().clickOnAddProviderButton();
    }

}