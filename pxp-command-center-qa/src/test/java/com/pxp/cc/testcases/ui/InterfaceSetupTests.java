package com.pxp.cc.testcases.ui;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CreateClientPage;
import com.pxp.cc.objectmaps.InterfaceSetupPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class InterfaceSetupTests extends CommandCenterGenericTest {

    public InterfaceSetupPage interfaceSetupPage;
    public NGHierarchyClientCreation ngHierarchyClientCreation;
    public CommonMethods commonMethods;
    public ClientDirectoryPage cchomepage;
    public CreateClientPage createClientPage;

    @BeforeMethod(alwaysRun = true)
    public void loginToCommandCenterPortal() throws Exception {
        logStep("Login to command center portal");
        cchomepage = login(testData.getProperty("base.url"), testData.getProperty("user.name"),
                testData.getProperty("user.password"));
        createClientPage = new CreateClientPage(driver);
        logStep("Select create client button from new dropdown");
        commonMethods = new CommonMethods(driver);
        ngHierarchyClientCreation = new NGHierarchyClientCreation(driver);
        String clientNam = testData.getProperty("new.client.name2").concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
        String clientSap = testData.getProperty("new.client.sap").concat(CCUtils.generateAlphaNumericStringIncludingSpecialChar(20));
        String clientType = testData.getProperty("new.client.type");
        String timeZone = testData.getProperty("new.client.timezone");
        String systemOrEnterprise = testData.getProperty("new.system");
        String timeZoneDisplayName = testData.getProperty("new.timezone.display.name");
        String solution = testData.getProperty("solution.forms");
        ngHierarchyClientCreation.createNewActiveClient(clientNam, clientSap, clientType, timeZone);
        ngHierarchyClientCreation.createNewActiveSystem(systemOrEnterprise, systemOrEnterprise);
        ngHierarchyClientCreation.createNewActiveEnterprise(systemOrEnterprise, systemOrEnterprise, timeZoneDisplayName);
        ngHierarchyClientCreation.createNewActivePractice(systemOrEnterprise, systemOrEnterprise, timeZoneDisplayName, solution);
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, description = "Verifying Elements in Interface setup for the first time")
    public void verifyingElementsInInterfaceSetup() {
        interfaceSetupPage = new InterfaceSetupPage(driver);
        List<String> fieldNames = new ArrayList<String>();
        List<String> editableFieldNamesForMedfusionRest_NextGen = new ArrayList<String>();
        List<String> editableFieldContentForMedfusionRest_NextGen = new ArrayList<String>();
        fieldNames.add("Integration Practice ID");
        fieldNames.add("Username");
        fieldNames.add("Password");
        fieldNames.add("App Token");
        editableFieldNamesForMedfusionRest_NextGen.add("NextGen Enterprise ID");
        editableFieldNamesForMedfusionRest_NextGen.add("API Site ID");
        editableFieldNamesForMedfusionRest_NextGen.add("NextGen Practice ID");
        editableFieldContentForMedfusionRest_NextGen.add("NextGen Enterprise 001");
        editableFieldContentForMedfusionRest_NextGen.add("Api Site 001");
        editableFieldContentForMedfusionRest_NextGen.add("NextGen Practice 001");
        editableFieldContentForMedfusionRest_NextGen.add("Enterpriseupdate01");
        editableFieldContentForMedfusionRest_NextGen.add("Apiupdate001");
        editableFieldContentForMedfusionRest_NextGen.add("Practiceupdate001");
        logStep("PXPP-24116 TC001 Verify that User can able to see the interface Setup section in Command Center Application");
        logStep("PXPP-24147 TC002 Verify that \"No interfaces setup.” is displayed when practice don't have Interfaces");
        logStep("PXPP-25724 TC005 Verify the expand / collapse functionality Interface Setup Section");
        interfaceSetupPage.verifyElementsInInterfaceSetUpSection();
        logStep("PXPP-24148 TC003 Verify that user can able to click on \"+ New \" button under Interface Setup Section");
        interfaceSetupPage.verifyElementsOnNewInterface();
        interfaceSetupPage.clickOnCancelButton();
        logStep("PXPP-24189 TC004 Verify that user can able to click on \"+ New \" button and able to select all the fields in new page under Interface Setup Section");
        interfaceSetupPage.clickOnNewButton();
        interfaceSetupPage.clickOnExternalSystemComboBox();
        interfaceSetupPage.selectExternalSystemInDropDown("MedfusionREST - NextGen");
        interfaceSetupPage.verifyElementsForExternalSystem("MedfusionREST - NextGen");
        interfaceSetupPage.verifyElementsOnNewInterface();
        logStep("PXPP-24149 TC006 Verify that user can able to Add new interface with external name \"MedfusionREST -NextGen\" under Interface Setup Section");
        interfaceSetupPage.enterValue(interfaceSetupPage.getNextgenEnterpriseIdTextBox(), editableFieldContentForMedfusionRest_NextGen.get(0));
        interfaceSetupPage.enterValue(interfaceSetupPage.getApiSiteIdTextBox(), editableFieldContentForMedfusionRest_NextGen.get(1));
        interfaceSetupPage.enterValue(interfaceSetupPage.getNextgenPracticeIdTextBox(), editableFieldContentForMedfusionRest_NextGen.get(2));
        interfaceSetupPage.verifyRadioButtonEnabled("Active");
        interfaceSetupPage.clickOnSaveButton();
        commonMethods = new CommonMethods(driver);
        commonMethods.verifyToasterMessage("Success! Interface added successfully");
        logStep("PXPP-25725 TC007 Verify Kebab menu options for Active Interfaces under Interface Setup Section");
        interfaceSetupPage.verifyInterfaceSetupStatus("MedfusionREST - NextGen", "Active");
        interfaceSetupPage.verifyInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        interfaceSetupPage.clickOnInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        interfaceSetupPage.verifyKebabEdit();
        interfaceSetupPage.clickOnInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        interfaceSetupPage.clickOnExpandButtonOfExternalSystem("MedfusionREST - NextGen");
        logStep("PXPP-25726 TC008 Verify that user can view the auto generated field for \"MedfusionREST -NextGen\" interface under Interface Setup Section");
        interfaceSetupPage.verifyAutoGeneratedFields("MedfusionREST - NextGen", fieldNames);
        interfaceSetupPage.verifyEditableContent(editableFieldNamesForMedfusionRest_NextGen, editableFieldContentForMedfusionRest_NextGen);
        logStep("PXPP-25728 TC009 Verify that user can able to Edit and Cancel interface \"MedfusionREST -NextGen\" under Interface Setup Section");
        interfaceSetupPage.clickOnInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        interfaceSetupPage.clickOnKebabEdit();
        interfaceSetupPage.updateValue(interfaceSetupPage.getNextgenEnterpriseIdTextBox(), editableFieldContentForMedfusionRest_NextGen.get(4));
        interfaceSetupPage.updateValue(interfaceSetupPage.getApiSiteIdTextBox(), editableFieldContentForMedfusionRest_NextGen.get(5));
        interfaceSetupPage.clickOnCancelButtonOnEdit();
        interfaceSetupPage.clickOnExpandButtonOfExternalSystem("MedfusionREST - NextGen");
        interfaceSetupPage.verifyEditableContent(editableFieldNamesForMedfusionRest_NextGen, editableFieldContentForMedfusionRest_NextGen);
        logStep("PXPP-25738 TC010 Verify that user can able to Edit and Save interface \"MedfusionREST -NextGen\" under Interface Setup Section");
        interfaceSetupPage.clickOnInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        interfaceSetupPage.clickOnKebabEdit();
        interfaceSetupPage.updateValue(interfaceSetupPage.getNextgenEnterpriseIdTextBox(), editableFieldContentForMedfusionRest_NextGen.get(4));
        interfaceSetupPage.updateValue(interfaceSetupPage.getApiSiteIdTextBox(), editableFieldContentForMedfusionRest_NextGen.get(5));
        interfaceSetupPage.clickOnSaveButtonOnEdit();
        commonMethods.verifyToasterMessage("Changes updated successfully");
        interfaceSetupPage.verifyEditableContent(editableFieldNamesForMedfusionRest_NextGen, editableFieldContentForMedfusionRest_NextGen);
        /*logStep("PXPP-24223 TC011 Verify that whether User can change status from Active to Inactive interface under Interface Setup Section");
        interfaceSetupPage.clickOnInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        interfaceSetupPage.clickOnKebabEdit();
        interfaceSetupPage.verifyRadioButtonEnabledOnEdit("Inactive");
        interfaceSetupPage.clickOnSaveButtonOnEdit();
        commonMethods.verifyToasterMessage("Changes updated successfully");
        interfaceSetupPage.verifyInterfaceSetupStatus("MedfusionREST - NextGen", "Inactive");*/
        /*logStep("PXPP-25739 TC012 Verify Kebab menu options for Inactive Interfaces under Interface Setup Section");
        interfaceSetupPage.clickOnInterfaceSetupKebabMenu("MedfusionREST - NextGen");
        interfaceSetupPage.verifyKebabEdit();
        interfaceSetupPage.verifyKebabDelete();*/
    }
}
