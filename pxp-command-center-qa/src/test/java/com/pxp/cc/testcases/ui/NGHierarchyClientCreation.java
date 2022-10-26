package com.pxp.cc.testcases.ui;

import com.pxp.cc.objectmaps.*;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class NGHierarchyClientCreation extends CCMainPage {

    public CommandCenterGenericTest commandCenterGenericTest;
    public ClientDirectoryPage clientDirectoryPage;
    public GeneralSettingsPage generalSettingsPage;
    public CreateClientPage createClientPage;
    public CommonMethods commonMethods;
    public AddOrgNodePage addOrgNodePage;
    String systemName, enterpriseName, clientName, practiceName;

    public NGHierarchyClientCreation(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void createNewActiveClient(String clientNam, String clientSap, String clientType, String clientTimeZone) throws Exception {
        createClientPage = new CreateClientPage(driver);
        logStep("Select create client button from new dropdown");
        createClientPage.clickCreateClient();
        commonMethods = new CommonMethods(driver);
        commandCenterGenericTest = new CommandCenterGenericTest();
        logStep("Enter valid details in mandatory fields");
        clientName = clientNam;
        String clientSapNumber = clientSap;
        createClientPage.createNewClient(clientName, clientSapNumber, clientType, clientTimeZone);
        logStep("Verify active radio button is by default selected on the create client popup");
        createClientPage.verifyActiveRadioButtonIsByDefaultSelected();
        logStep("Click on create button on the create client popup");
        createClientPage.clickConfirmPopup();
        String actualMessage = commonMethods.getToasterMessage();
        logStep("Verify success toaster message");
        CCUtils.assertEquals(actualMessage, "Success! " + clientName + " has been successfully created");
        commonMethods = new CommonMethods(driver);
        clientDirectoryPage = new ClientDirectoryPage(driver);
        logStep("Searching a client in client directory");
        clientDirectoryPage.searchNode(clientName);
        clientDirectoryPage.clickSearchButton();
        clientDirectoryPage.clickSearchResult();
        logStep("Navigating To General Settings page");
        generalSettingsPage = new GeneralSettingsPage(driver);
        generalSettingsPage.verifyClientNodeIsSelected();
    }

    public void createNewActiveSystem(String system, String enterprise) throws NullPointerException, InterruptedException {
        addOrgNodePage = new AddOrgNodePage(driver);
        logStep("Verify manage client button is displayed");
        addOrgNodePage.verifyManageClientButton();
        logStep("Click on manage client button");
        addOrgNodePage.clickOnManageClient();
        addOrgNodePage.verifyAddSystemButton();
        addOrgNodePage.clickOnAddSystem();
        logStep("Enter data in all input fields");
        systemName = CCUtils.generateAlphaNumericString(18);
        addOrgNodePage.enterSystemName(systemName);
        addOrgNodePage.addPmSystemAndEhrSystem(system, enterprise);
        addOrgNodePage.verifyActiveRadioButtonIsByDefaultSelected();
        logStep("Click on save and add button");
        addOrgNodePage.clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        addOrgNodePage.verifyToasterMessage("Changes updated successfully");
    }

    public void createNewActiveEnterprise(String system, String enterprise, String timeZone) throws NullPointerException, InterruptedException {
        logStep("Navigating To General Settings page");
        generalSettingsPage = new GeneralSettingsPage(driver);
        logStep("Verify general settings page title");
        generalSettingsPage.verifyGeneralSettingsPageTitle();
        addOrgNodePage = new AddOrgNodePage(driver);
        logStep("Verify manage client button is displayed");
        addOrgNodePage.verifyManageClientButton();
        logStep("Click on manage client button");
        addOrgNodePage.clickOnManageClient();
        addOrgNodePage.clickOnExpandArrow();
        logStep("Verify add new Enterprise page");
        driver.navigate().refresh();
        addOrgNodePage.clickOnManageClient();
        addOrgNodePage.clickOnExpandArrow();
        addOrgNodePage.verifyAddEnterpriseButton();
        addOrgNodePage.clickOnAddEnterprise();
        logStep("Enter data in all input fields");
        enterpriseName = CCUtils.generateAlphaNumericString(18);
        addOrgNodePage.enterEnterpriseName(enterpriseName);
        addOrgNodePage.addPmSystemAndEhrSystem(system, enterprise);
        addOrgNodePage.addTimezone(timeZone);
        logStep("Click on save and add button");
        addOrgNodePage.clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        addOrgNodePage.verifyToasterMessage("Changes updated successfully");
    }

    public void createNewActivePractice(String system, String enterprise, String timeZone, String solution) throws NullPointerException, InterruptedException {
        logStep("Navigating To General Settings page");
        generalSettingsPage = new GeneralSettingsPage(driver);
        addOrgNodePage = new AddOrgNodePage(driver);
        logStep("Verify manage client button is displayed");
        addOrgNodePage.verifyManageClientButton();
        logStep("Click on manage client button");
        addOrgNodePage.clickOnManageClient();
        logStep("Click on arrow");
        addOrgNodePage.clickOnSystemExpandArrow();
        driver.navigate().refresh();
        logStep("Click on manage client button");
        generalSettingsPage = new GeneralSettingsPage(driver);
        generalSettingsPage.clickSearchIcon();
        logStep("Searching a node under client in general settings page");
        generalSettingsPage.enterSearchText(enterpriseName);
        generalSettingsPage.clickFirstInAutoSuggestion();
        CCUtils.pressEnterKey();
        addOrgNodePage.clickOnManageClient();
        addOrgNodePage.clickOnSystemExpandArrow();
        addOrgNodePage.clickOnEnterpriseExpandArrow();
        logStep("Verify add new practice page");
        addOrgNodePage.verifyAddPracticeButton();
        addOrgNodePage.clickOnAddPracticeButton();
        logStep("Enter data in all input fields");
        practiceName = CCUtils.generateAlphaNumericString(18);
        addOrgNodePage.enterPracticeName(practiceName);
        addOrgNodePage.addPracticePmSystemAndEhrSystem(system, enterprise);
        addOrgNodePage.addTimezone(timeZone);
        addOrgNodePage.enterSolutionsDisplayName(solution);
        addOrgNodePage.verifyActiveRadioButtonIsByDefaultSelected();
        logStep("Click on save and add button");
        addOrgNodePage.clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        addOrgNodePage.verifyToasterMessage("Changes updated successfully");
    }


}
