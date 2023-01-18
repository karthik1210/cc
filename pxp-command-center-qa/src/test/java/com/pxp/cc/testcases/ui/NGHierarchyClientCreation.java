// Copyright 2022-2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import com.pxp.cc.objectmaps.*;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class NGHierarchyClientCreation extends CCMainPage {

    public GeneralSettingsPage generalSettingsPage;
    public CommonMethods commonMethods;
    public static String systemName, enterpriseName, clientName, practiceName;
    public NGHierarchyClientCreationPage ngHierarchyClientCreationPage;

    public NGHierarchyClientCreation(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        ngHierarchyClientCreationPage = new NGHierarchyClientCreationPage(driver);
        commonMethods = new CommonMethods(driver);
        generalSettingsPage = new GeneralSettingsPage(driver);
    }

    public String createNewActiveClient(String clientNam, String clientSap, String clientType, String clientTimeZone) throws Exception {
        logStep("Select create client button from new dropdown");
        ngHierarchyClientCreationPage.clickCreateClient();
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
        return clientName;
    }
    
    public String createNewInactiveClient(String clientNam, String clientSap, String clientType, String clientTimeZone) throws Exception {
    	logStep("Select create client button from new dropdown");
        ngHierarchyClientCreationPage.clickCreateClient();
        logStep("Enter valid details in mandatory fields");
        clientName = clientNam;
        String clientSapNumber = clientSap;
        ngHierarchyClientCreationPage.createNewClient(clientName, clientSapNumber, clientType, clientTimeZone);
        logStep("Verify active radio button is by default selected on the create client popup");
        ngHierarchyClientCreationPage.verifyActiveRadioButtonIsByDefaultSelected();
        logStep("Click on the Inactive radio button");
        ngHierarchyClientCreationPage.selectInactiveRadiobutton();
        logStep("Click on create button on the create client popup");
        ngHierarchyClientCreationPage.clickConfirmPopup();
        String actualMessage = commonMethods.getToasterMessage();
        logStep("Verify success toaster message");
        CCUtils.assertEquals(actualMessage, "Success! " + clientName + " has been successfully created");
        return clientName;
    }

    public String createNewActiveSystem(String system, String enterprise) throws Exception {
        logStep("Verify manage client button is displayed");
        ngHierarchyClientCreationPage.verifyManageClientButton();
        logStep("Click on manage client button");
        ngHierarchyClientCreationPage.clickOnManageClient();
        ngHierarchyClientCreationPage.verifyAddSystemButton();
        ngHierarchyClientCreationPage.clickOnAddSystem();
        logStep("Enter data in all input fields");
        systemName = "S" + CCUtils.generateRandomStringWithoutSymbolsOrNumbers(18).toLowerCase();
        ngHierarchyClientCreationPage.enterSystemName(systemName);
        ngHierarchyClientCreationPage.addPmSystemAndEhrSystem(system, enterprise);
        ngHierarchyClientCreationPage.verifyActiveRadioButtonForOrgIsByDefaultSelected();
        logStep("Click on save and add button");
        ngHierarchyClientCreationPage.clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        ngHierarchyClientCreationPage.verifyToasterMessage("Changes updated successfully");
        return systemName;
    }
    
    public String createNewInactiveSystem(String pmSystem, String ehrSystem) throws Exception {
        logStep("Verify manage client button is displayed");
        ngHierarchyClientCreationPage.verifyManageClientButton();
        logStep("Click on manage client button");
        ngHierarchyClientCreationPage.clickOnManageClient();
        ngHierarchyClientCreationPage.verifyAddSystemButton();
        ngHierarchyClientCreationPage.clickOnAddSystem();
        logStep("Enter data in all input fields");
        systemName = "S" + CCUtils.generateRandomStringWithoutSymbolsOrNumbers(18).toLowerCase();
        ngHierarchyClientCreationPage.enterSystemName(systemName);
        ngHierarchyClientCreationPage.addPmSystemAndEhrSystem(pmSystem,ehrSystem);
        ngHierarchyClientCreationPage.verifyInactiveRadioButton();
        ngHierarchyClientCreationPage.clickToInactiveRadioButton();
        logStep("Click on save and add button");
        ngHierarchyClientCreationPage.clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        ngHierarchyClientCreationPage.verifyToasterMessage("Changes updated successfully");
        return systemName;
    }

    public String createNewActiveEnterprise(String system, String enterprise, String timeZone) throws NullPointerException, InterruptedException {
        ngHierarchyClientCreationPage.clickOnExpandArrow(systemName);
        ngHierarchyClientCreationPage.verifyAddEnterpriseButton();
        ngHierarchyClientCreationPage.clickOnAddEnterprise();
        logStep("Enter data in all input fields");
        enterpriseName = "E" + CCUtils.generateRandomStringWithoutSymbolsOrNumbers(18).toLowerCase();
        ngHierarchyClientCreationPage.enterEnterpriseName(enterpriseName);
        ngHierarchyClientCreationPage.addPmSystemAndEhrSystem(system, enterprise);
        ngHierarchyClientCreationPage.addTimezone(timeZone);
        logStep("Click on save and add button");
        ngHierarchyClientCreationPage.clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        ngHierarchyClientCreationPage.verifyToasterMessage("Changes updated successfully");
        return enterpriseName;
    }
    
    public String createNewInactiveEnterprise(String system, String enterprise, String timeZone) throws NullPointerException, InterruptedException {
        ngHierarchyClientCreationPage.clickOnExpandArrow(systemName);
        ngHierarchyClientCreationPage.verifyAddEnterpriseButton();
        ngHierarchyClientCreationPage.clickOnAddEnterprise();
        logStep("Enter data in all input fields");
        enterpriseName = "E" + CCUtils.generateRandomStringWithoutSymbolsOrNumbers(18).toLowerCase();
        ngHierarchyClientCreationPage.enterEnterpriseName(enterpriseName);
        ngHierarchyClientCreationPage.addPmSystemAndEhrSystem(system, enterprise);
        ngHierarchyClientCreationPage.addTimezone(timeZone);
        ngHierarchyClientCreationPage.verifyInactiveRadioButton();
        ngHierarchyClientCreationPage.clickToInactiveRadioButton();
        logStep("Click on save and add button");
        ngHierarchyClientCreationPage.clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        ngHierarchyClientCreationPage.verifyToasterMessage("Changes updated successfully");
        return enterpriseName;
    }

    public String createNewActivePractice(String system, String enterprise, String timeZone, String solution) throws Exception {
        logStep("Click on arrow");
        ngHierarchyClientCreationPage.clickOnExpandArrow(systemName);
        ngHierarchyClientCreationPage.clickOnEnterpriseExpandArrow(enterpriseName);
        logStep("Verify add new practice page");
        ngHierarchyClientCreationPage.verifyAddPracticeButton();
        ngHierarchyClientCreationPage.clickOnAddPracticeButton();
        logStep("Enter data in all input fields");
        practiceName = "P" + CCUtils.generateRandomStringWithoutSymbolsOrNumbers(18).toLowerCase();
        ngHierarchyClientCreationPage.enterPracticeName(practiceName);
        ngHierarchyClientCreationPage.addPracticePmSystemAndEhrSystem(system, enterprise);
        ngHierarchyClientCreationPage.addTimezone(timeZone);
        ngHierarchyClientCreationPage.enterSolutionsDisplayName(solution);
        ngHierarchyClientCreationPage.verifyActiveRadioButtonForOrgIsByDefaultSelected();
        logStep("Click on save and add button");
        ngHierarchyClientCreationPage.clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        ngHierarchyClientCreationPage.verifyToasterMessage("Changes updated successfully");
        ngHierarchyClientCreationPage.clickOnCloseButton();
        return practiceName;
    }
    
    public String createNewInactivePractice(String system, String enterprise, String timeZone, String solution) throws Exception {
        logStep("Click on arrow");
        ngHierarchyClientCreationPage.clickOnExpandArrow(systemName);
        ngHierarchyClientCreationPage.clickOnEnterpriseExpandArrow(enterpriseName);
        logStep("Verify add new practice page");
        ngHierarchyClientCreationPage.verifyAddPracticeButton();
        ngHierarchyClientCreationPage.clickOnAddPracticeButton();
        logStep("Enter data in all input fields");
        practiceName = "P" + CCUtils.generateRandomStringWithoutSymbolsOrNumbers(18).toLowerCase();
        ngHierarchyClientCreationPage.enterPracticeName(practiceName);
        ngHierarchyClientCreationPage.addPracticePmSystemAndEhrSystem(system, enterprise);
        ngHierarchyClientCreationPage.addTimezone(timeZone);
        ngHierarchyClientCreationPage.enterSolutionsDisplayName(solution);
        ngHierarchyClientCreationPage.verifyInactiveRadioButton();
        ngHierarchyClientCreationPage.clickToInactiveRadioButton();
        logStep("Click on save and add button");
        ngHierarchyClientCreationPage.clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        ngHierarchyClientCreationPage.verifyToasterMessage("Changes updated successfully");
        return practiceName;
    }
}
