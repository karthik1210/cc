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

public class AddNewResourceTests extends CommandCenterGenericTest {
    static boolean runOnce = true;
    public CCUI ccui;
    String externalId = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
    String resourceName;
    String firstName = "New"
            + CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(6));
    String lastName = CCUtils.generateAlphaNumericString(5).concat("-")
            .concat(CCUtils.generateAlphaNumericString(6)).concat("'s");

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

    @Test(retryAnalyzer = RetryAnalyzer.class, priority = 1, description = "Verify add new Resource popup")
    public void testAddNewResourcePopup() throws Exception {
        ngHierarchyClientCreate();
        navigatetoResources();
        logStep("Verify add new Resource popup");
        ccui.getAddNewResourcePage().verifyAddResourcePopupTitle();
        runOnce = false;
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, groups = {"buildvalidation"}, priority = 2, description = "Create new Resource using valid test data")
    public void testAddNewResourcesFunctionalityUsingValidData() throws Exception {
        ngHierarchyClientCreate();
        createProvider();
        navigatetoResources();
        logStep("Create new Resources using valid test data");
        resourceName = "New"
                + CCUtils.generateAlphaNumericString(5).concat(CCUtils.generateAlphaNumericString(6));
        String externalIds = "Ext_Id_" + CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
        ccui.getAddNewResourcePage().enterResourceName(resourceName);
        ccui.getAddNewResourcePage().enterExternalId(externalIds);
        ccui.getAddNewResourcePage().selectResourceType("Person");
        ccui.getAddNewResourcePage().enterConnectedProvider(firstName);
        ccui.getAddNewResourcePage().clickFirstInAutoSuggestion();
        ccui.getAddNewResourcePage().verifySaveAndAddButtonIsEnabled();
        ccui.getAddNewResourcePage().clickOnSaveAndAddButton();
        ccui.getCommonMethods().verifyToasterMessage("Success! New Resource has been successfully created");
        runOnce = false;
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, priority = 3, description = "Verify navigation to Resource page with Resource tab and Resource tile on General Settins page")
    public void testNavigationToResourcePageWithResourceTile() throws Exception {
        ngHierarchyClientCreate();
        navigatetoResources();
        logStep("Verify Cancel button functionality on add a resources page");
        ccui.getAddNewResourcePage().clickOnCancelButton();
        ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
        logStep("Verify Cross button functionality on add a resources page");
        ccui.getAddNewResourcePage().clickOnAddResourceButton();
        ccui.getAddNewResourcePage().clickOnCrossIcon();
        ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
        runOnce = false;
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, priority = 4, description = "Enter more than 100 characters in ResourceName, more than 50 characters in ExternalID fields ,verify error message and invalid value Resource type")
    public void testHundredMaximumCharactersErrorMessageForInputFields() throws Exception {
        ngHierarchyClientCreate();
        navigatetoResources();
        logStep("Verify maximum character reached error message for ResourceName");
        ccui.getAddNewResourcePage().verifyHundredMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(101));
        logStep("Verify maximum character reached error message for EXternalId fields");
        ccui.getAddNewResourcePage().verifyFiftyMaximumCharactersReachedErrorMessage(CCUtils.generateAlphaNumericString(51));
        logStep("Enter invalid value Resource type and verify error message");
        ccui.getAddNewResourcePage().clickOnResourceType();
        ccui.getAddNewResourcePage().enterResourceType(testData.getProperty("resource.name"));
        CCUtils.pressEnterKey();
        ccui.getAddNewResourcePage().verifyNoItemsFound();
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
        public void createProvider() throws Exception
        {
            logStep("Verify Providers And Resources tab is displayed");
            ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
            ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
            ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
            logStep("Verify ADD button is enabled for client level on provider page");
            ccui.getAddNewProviderPage().verifyAddButtonIsEnabled();
            ccui.getAddNewProviderPage().clickOnAddProviderButton();
            logStep("Enter valid details in mandatory field and verify success toaster message");
            String credentials = testData.getProperty("provider.credentials");
            ccui.getAddNewProviderPage().enterFirstName(firstName);
            ccui.getAddNewProviderPage().enterLastName(lastName);
            ccui.getAddNewProviderPage().enterCredentials(credentials);
            ccui.getAddNewProviderPage().enterExternalId(externalId);
            ccui.getAddNewProviderPage().selectGenderValue("Male");
            ccui.getAddNewProviderPage().verifySaveAndAddButtonIsEnabled();
            ccui.getAddNewProviderPage().clickOnSaveAndAddButton();
            ccui.getCommonMethods().verifyToasterMessage("Success! New Provider has been successfully created");
        }

        @Test(enabled = false)
        public void navigatetoResources() throws Exception
        {
            logStep("Verify Providers And Resources tab is displayed");
            ccui.getGeneralSettingsPage().verifyProvidersAndResourcesTab();
            ccui.getGeneralSettingsPage().clickOnProvidersAndResourcesTab();
            ccui.getAddNewProviderPage().verifyProvidersAndResourcesTitle();
            ccui.getAddNewResourcePage().clickOnResourcesTab();
            ccui.getAddNewResourcePage().verifyAddButtonIsEnabled();
            ccui.getAddNewResourcePage().clickOnAddResourceButton();
        }
    }