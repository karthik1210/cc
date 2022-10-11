package com.pxp.cc.testcases.ui;

import com.pxp.cc.objectmaps.*;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;

public class ExternaUrlsTests extends CommandCenterGenericTest {
    private ClientDirectoryPage clientDirectoryPage;
    public ClientDirectoryPage cchomepage;
    private GeneralSettingsPage generalSettingsPage;
    public CreateClientPage createClientPage;
    public CommonMethods commonMethods;
    public AddOrgNodePage addOrgNodePage;
    String systemName, enterpriseName, clientName, practiceName;
    ExternalURLsPage externalURLsPage;

    @BeforeMethod
    public void loginToCommandCenterPortal() throws Exception {
        logStep("Login to command center portal");
        cchomepage = login(testData.getProperty("base.url"), testData.getProperty("user.name"),
                testData.getProperty("user.password"));
        createClientPage = new CreateClientPage(driver);
        logStep("Select create client button from new dropdown");
        commonMethods = new CommonMethods(driver);
        createNewActiveClient();
        createNewActiveSystem();
        createNewActiveEnterprise();
        createNewActivePractice();
    }

    @Test(priority = 1, description = "")
    public void verifyPracticeLinkSectionAvailability() throws InterruptedException, AWTException {
        logStep("Verify External URLs displayed at practice level");
        externalURLsPage = new ExternalURLsPage(driver);
        externalURLsPage.verifyPracticeLinksLabelIsDisplayed();
        externalURLsPage.verifyExternalUrlIsDisplayed();
        externalURLsPage.verifyExternalUrlEditIsDisplayed();
        externalURLsPage.verifyNoExternalUrlConfigureLabelIsDisplayed();
        externalURLsPage.verifyExternalUrlAddIsDisplayed();
    }

    private void createNewActiveClient() throws Exception {
        createClientPage = new CreateClientPage(driver);
        logStep("Select create client button from new dropdown");
        createClientPage.clickCreateClient();
        commonMethods = new CommonMethods(driver);
        logStep("Enter valid details in mandatory fields");
        clientName = testData.getProperty("new.client.name")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
        String clientSapNumber = testData.getProperty("new.client.sap")
                .concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
        createClientPage.createNewClient(clientName, clientSapNumber, testData.getProperty("new.client.type"),
                testData.getProperty("new.client.timezone"));
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

    private void createNewActiveSystem() throws NullPointerException, InterruptedException {
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
        addOrgNodePage.addPmSystemAndEhrSystem(testData.getProperty("new.system"), testData.getProperty("new.system"));
        addOrgNodePage.verifyActiveRadioButtonIsByDefaultSelected();
        logStep("Click on save and add button");
        addOrgNodePage.clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        addOrgNodePage.verifyToasterMessage("Changes updated successfully");
    }

    private void createNewActiveEnterprise() throws NullPointerException, InterruptedException {
        logStep("Navigating To General Settings page");
        generalSettingsPage = new GeneralSettingsPage(driver);
        logStep("Verify general settings page title");
        generalSettingsPage.verifyGeneralSettingsPageTitle();
        generalSettingsPage.clickSearchIcon();
        logStep("Searching a node under client in general settings page");
        generalSettingsPage.enterSearchText(systemName);
        generalSettingsPage.clickFirstInAutoSuggestion();
        CCUtils.pressEnterKey();
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
        addOrgNodePage.addPmSystemAndEhrSystem(testData.getProperty("new.system"), testData.getProperty("new.system"));
        addOrgNodePage.addTimezone(testData.getProperty("new.timezone.display.name"));
        logStep("Click on save and add button");
        addOrgNodePage.clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        addOrgNodePage.verifyToasterMessage("Changes updated successfully");
    }

    private void createNewActivePractice() throws NullPointerException, InterruptedException {
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
        addOrgNodePage.addPracticePmSystemAndEhrSystem(testData.getProperty("new.system"),
                testData.getProperty("new.system"));
        addOrgNodePage.addTimezone(testData.getProperty("new.timezone.display.name"));
        addOrgNodePage.enterSolutionsDisplayName(testData.getProperty("solution.forms"));
        addOrgNodePage.verifyActiveRadioButtonIsByDefaultSelected();
        logStep("Click on save and add button");
        addOrgNodePage.clickOnSaveAndAddButton();
        logStep("Verify success toaster message");
        addOrgNodePage.verifyToasterMessage("Changes updated successfully");
    }
}
