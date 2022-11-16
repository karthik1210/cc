// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import com.pxp.cc.objectmaps.*;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class NGHierarchyClientCreation extends CCMainPage {

    public GeneralSettingsPage generalSettingsPage;
    public CommonMethods commonMethods;
    String systemName, enterpriseName, clientName, practiceName;
    public NGHierarchyClientCreationPage ngHierarchyClientCreationPage;

    public NGHierarchyClientCreation(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String createNewActiveClient(String clientNam, String clientSap, String clientType, String clientTimeZone) throws Exception {
        ngHierarchyClientCreationPage = new NGHierarchyClientCreationPage(driver);
        logStep("Select create client button from new dropdown");
        ngHierarchyClientCreationPage.clickCreateClient();
        commonMethods = new CommonMethods(driver);
        logStep("Enter valid details in mandatory fields");
        clientName = clientNam;
        String clientSapNumber = clientSap;
        ngHierarchyClientCreationPage.createNewClient(clientName, clientSapNumber, clientType, clientTimeZone);
        logStep("Verify active radio button is by default selected on the create client popup");
        ngHierarchyClientCreationPage.verifyActiveRadioButtonIsByDefaultSelected();
        logStep("Click on create button on the create client popup");
        ngHierarchyClientCreationPage.clickConfirmPopup();
        String actualMessage = commonMethods.getToasterMessage();
        logStep("Verify success toaster message");
        CCUtils.assertEquals(actualMessage, "Success! " + clientName + " has been successfully created");
        commonMethods = new CommonMethods(driver);
        logStep("Searching a client in client directory");
        ngHierarchyClientCreationPage.searchNode(clientName);
        ngHierarchyClientCreationPage.clickSearchButton();
        ngHierarchyClientCreationPage.clickSearchResult();
        logStep("Navigating To General Settings page");
        generalSettingsPage = new GeneralSettingsPage(driver);
        generalSettingsPage.verifyClientNodeIsSelected();
        return clientName;
    }

    public String createNewActiveSystem(String system, String enterprise) throws NullPointerException, InterruptedException {
        logStep("Verify manage client button is displayed");
        ngHierarchyClientCreationPage.verifyManageClientButton();
        logStep("Click on manage client button");
        ngHierarchyClientCreationPage.clickOnManageClient();
        ngHierarchyClientCreationPage.verifyAddSystemButton();
        ngHierarchyClientCreationPage.clickOnAddSystem();
        logStep("Enter data in all input fields");
        systemName = CCUtils.generateAlphaNumericString(18);
        ngHierarchyClientCreationPage.enterSystemName(systemName);
        ngHierarchyClientCreationPage.addPmSystemAndEhrSystem(system, enterprise);
        ngHierarchyClientCreationPage.verifyActiveRadioButtonIsByDefaultSelected();
        logStep("Click on save and add button");
        ngHierarchyClientCreationPage.clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        ngHierarchyClientCreationPage.verifyToasterMessage("Changes updated successfully");
        return systemName;
    }

    public String createNewActiveEnterprise(String system, String enterprise, String timeZone) throws NullPointerException, InterruptedException {
        logStep("Navigating To General Settings page");
        generalSettingsPage = new GeneralSettingsPage(driver);
        logStep("Verify general settings page title");
        generalSettingsPage.verifyGeneralSettingsPageTitle();
        logStep("Verify manage client button is displayed");
        ngHierarchyClientCreationPage.verifyManageClientButton();
        logStep("Click on manage client button");
        ngHierarchyClientCreationPage.clickOnManageClient();
        ngHierarchyClientCreationPage.clickOnExpandArrow();
        logStep("Verify add new Enterprise page");
        driver.navigate().refresh();
        ngHierarchyClientCreationPage.clickOnManageClient();
        ngHierarchyClientCreationPage.clickOnExpandArrow();
        ngHierarchyClientCreationPage.verifyAddEnterpriseButton();
        ngHierarchyClientCreationPage.clickOnAddEnterprise();
        logStep("Enter data in all input fields");
        enterpriseName = CCUtils.generateAlphaNumericString(18);
        ngHierarchyClientCreationPage.enterEnterpriseName(enterpriseName);
        ngHierarchyClientCreationPage.addPmSystemAndEhrSystem(system, enterprise);
        ngHierarchyClientCreationPage.addTimezone(timeZone);
        logStep("Click on save and add button");
        ngHierarchyClientCreationPage.clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        ngHierarchyClientCreationPage.verifyToasterMessage("Changes updated successfully");
        return enterpriseName;
    }

    public String createNewActivePractice(String system, String enterprise, String timeZone, String solution) throws NullPointerException, InterruptedException {
        logStep("Navigating To General Settings page");
        generalSettingsPage = new GeneralSettingsPage(driver);
        logStep("Verify manage client button is displayed");
        ngHierarchyClientCreationPage.verifyManageClientButton();
        logStep("Click on manage client button");
        ngHierarchyClientCreationPage.clickOnManageClient();
        logStep("Click on arrow");
        ngHierarchyClientCreationPage.clickOnSystemExpandArrow();
        ngHierarchyClientCreationPage.clickOnEnterpriseExpandArrow();
        logStep("Verify add new practice page");
        ngHierarchyClientCreationPage.verifyAddPracticeButton();
        ngHierarchyClientCreationPage.clickOnAddPracticeButton();
        logStep("Enter data in all input fields");
        practiceName = CCUtils.generateNumericString(10);
        ngHierarchyClientCreationPage.enterPracticeName(practiceName);
        ngHierarchyClientCreationPage.addPracticePmSystemAndEhrSystem(system, enterprise);
        ngHierarchyClientCreationPage.addTimezone(timeZone);
        ngHierarchyClientCreationPage.enterSolutionsDisplayName(solution);
        ngHierarchyClientCreationPage.verifyActiveRadioButtonIsByDefaultSelected();
        logStep("Click on save and add button");
        ngHierarchyClientCreationPage.clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        ngHierarchyClientCreationPage.verifyToasterMessage("Changes updated successfully");
        return practiceName;
    }
}
