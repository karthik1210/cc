// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import com.pxp.cc.util.CCUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class PatientAccessPage extends CCMainPage {

    public PatientAccessPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='flex']/span")
    private WebElement toasterMessage;

    @FindBy(xpath = "//div[normalize-space(text())='Patient Access Rules']")
    private WebElement patientAccessLabel;

    @FindBy(xpath = "//div[normalize-space(text())='Patient Access Rules']/../following-sibling::div//mat-icon[normalize-space(text())='keyboard_arrow_down']")
    private WebElement patientAccessExpandButton;

    @FindBy(xpath = "//div[normalize-space(text())='Patient Access Rules']/../following-sibling::div//mat-icon[normalize-space(text())='keyboard_arrow_up']")
    private WebElement patientAccessCollapseButton;

    @FindBy(xpath = "//div[normalize-space(text())='LINKED ACCOUNTS']")
    private WebElement linkedAccountsLabel;

    @FindBy(xpath = "//div[normalize-space(text())='AUTO AGE-OUT']")
    private WebElement autoAgeOutLabel;

    @FindBy(xpath = "//div[normalize-space(text())='By default, patients will age out at']")
    private WebElement autoAgeOutStatement1;

    @FindBy(xpath = "//div[@formgroupname='ageout']//text()[2]")
    private WebElement autoAgeOutStatement2;

    @FindBy(xpath = "//div[@formgroupname='ageout']//div")
    private WebElement autoAgeOutStatement;

    @FindBy(xpath = "//div[@formgroupname='ageout']//span/strong")
    private WebElement autoAgeOutDefaultValue;

    @FindBy(xpath = "//div[normalize-space(text())='GUARDIAN ACCESS PERMISSIONS']")
    private WebElement guardianAccessLabel;

    @FindBy(xpath = "//div[normalize-space(text())='When a patient turns']")
    private WebElement guardianAccessStatement1;

    @FindBy(xpath = "//div[@formgroupname='guardianAccess']//text()[2]")
    private WebElement guardianAccessStatement2;

    @FindBy(xpath = "//div[@formgroupname='guardianAccess']//span/strong")
    private WebElement guardianAccessDefaultValue;

    @FindBy(xpath = "//div[@formarrayname='permissions']//span[normalize-space(text())='Messages']")
    private WebElement guardianAccessMessages;

    @FindBy(xpath = "//div[@formarrayname='permissions']//span[normalize-space(text())='Medications']")
    private WebElement guardianAccessMedications;

    @FindBy(xpath = "//div[@formarrayname='permissions']//span[normalize-space(text())='Forms']")
    private WebElement guardianAccessForms;

    @FindBy(xpath = "//div[@formarrayname='permissions']//span[normalize-space(text())='Appointments']")
    private WebElement guardianAccessAppointments;

    @FindBy(xpath = "//div[@formarrayname='permissions']//span[normalize-space(text())='Health Record']")
    private WebElement guardianAccessHealthRecord;

    @FindBy(xpath = "//div[@formarrayname='permissions']//div[normalize-space(text())='FULL ACCESS']")
    private List<WebElement> defaultPermissionList;

    @FindBy(xpath = "//div[normalize-space(text())='Patient Access Rules']/../following-sibling::div//span[normalize-space(text())='EDIT']")
    private WebElement patientAccessEdit;

    @FindBy(xpath = "//mat-slide-toggle[@formcontrolname='linkAccounts']//input[@aria-label='Linked Accounts']")
    private WebElement linkedAccountToggle;

    @FindBy(xpath = "//mat-slide-toggle[@formcontrolname='autoAgeout']//input[@aria-label='Auto Age out']")
    private WebElement autoAgeOutToggle;

    @FindBy(xpath = "//input[@formcontrolname='defaultAgeout']")
    private WebElement defaultAutoAgeOut;

    @FindBy(xpath = "//button/span[normalize-space(text())='STATE SPECIFIC RULE']")
    private WebElement stateSpecificRuleButton;

    @FindBy(xpath = "//span[normalize-space(text())='STATE SPECIFIC RULE']/parent::button")
    private WebElement stateSpecificRuleButtondisplay;

    @FindBy(xpath = "//input[@formcontrolname='age']")
    private List<WebElement> ageRules;

    @FindBy(xpath = "//ng-select[@formcontrolname='states']//div[@role='combobox']/input")
    private List<WebElement> stateRulesDropdown;

    @FindBy(xpath = "//*[@aria-label='Options list']//label[@id='directoryDirectName']")
    private List<WebElement> stateListOptions;

    @FindBy(xpath = "//button/span[text()='Save']")
    private WebElement stateSpecificRuleSaveButton;

    @FindBy(xpath = "//button/span[normalize-space(text())='CLEAR']")
    private WebElement stateSpecificRuleClearButton;

    @FindBy(xpath = "//mat-icon[normalize-space(text())='remove_circle']")
    private List<WebElement> listOfRemoveAgeSpecificRuleIcon;

    @FindBy(xpath = "//mat-icon[normalize-space(text())='remove_circle']")
    private WebElement removeAgeSpecificRuleIcon;

    @FindBy(xpath = "//input[@formcontrolname='accessTillAge']")
    private WebElement accessTillAgeText;

    @FindBy(xpath = "//span[normalize-space(text())='Appointments']/../following-sibling::div//input[@value='FULL_ACCESS']")
    private WebElement appointmentFullAccess;

    @FindBy(xpath = "//span[normalize-space(text())='Appointments']/../following-sibling::div//input[@value='VIEW_ONLY']")
    private WebElement appointmentViewOnly;

    @FindBy(xpath = "//span[normalize-space(text())='Appointments']/../following-sibling::div//input[@value='NO_ACCESS']")
    private WebElement appointmentNoAccess;

    @FindBy(xpath = "//span[normalize-space(text())='Messages']/../following-sibling::div//input[@value='FULL_ACCESS']")
    private WebElement messagesFullAccess;

    @FindBy(xpath = "//span[normalize-space(text())='Messages']/../following-sibling::div//input[@value='VIEW_ONLY']")
    private WebElement messagesViewOnly;

    @FindBy(xpath = "//span[normalize-space(text())='Messages']/../following-sibling::div//input[@value='NO_ACCESS']")
    private WebElement messagesNoAccess;

    @FindBy(xpath = "//span[normalize-space(text())='Medications']/../following-sibling::div//input[@value='FULL_ACCESS']")
    private WebElement medicationsFullAccess;

    @FindBy(xpath = "//span[normalize-space(text())='Medications']/../following-sibling::div//input[@value='VIEW_ONLY']")
    private WebElement medicationsViewOnly;

    @FindBy(xpath = "//span[normalize-space(text())='Medications']/../following-sibling::div//input[@value='NO_ACCESS']")
    private WebElement medicationsNoAccess;

    @FindBy(xpath = "//span[normalize-space(text())='Forms']/../following-sibling::div//input[@value='FULL_ACCESS']")
    private WebElement formsFullAccess;

    @FindBy(xpath = "//span[normalize-space(text())='Forms']/../following-sibling::div//input[@value='VIEW_ONLY']")
    private WebElement formsViewOnly;

    @FindBy(xpath = "//span[normalize-space(text())='Forms']/../following-sibling::div//input[@value='NO_ACCESS']")
    private WebElement formsNoAccess;

    @FindBy(xpath = "//span[normalize-space(text())='Health Record']/../following-sibling::div//input[@type='checkbox']")
    private WebElement healthRecordFullAccess;

    @FindBy(xpath = "//div[normalize-space(text())='Patient Access Rules']/../following-sibling::div//button[@id='saveDetailsBtn']")
    private WebElement patientAccessSaveButton;

    @FindBy(xpath = "//div[normalize-space(text())='Patient Access Rules']/../following-sibling::div//span[@id='cancelBtn']")
    private WebElement patientAccessCancelButton;

    public boolean verifyPatientAccessLabelIsDisplayed(WebElement patientAccessLabelElement, String labelName) throws InterruptedException, AWTException {
        try {
            CCUtils.scrollPageViaElement(driver, patientAccessLabelElement);
            CCUtils.verifyAndWaitForElement(patientAccessLabelElement, driver);
            log("verifying " + labelName + " label availability");
            CCUtils.assertEquals(patientAccessLabelElement.isDisplayed(), true);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public void clickOnPatientAccessButton(WebElement patientAccessButtonElement, String elementName) throws InterruptedException, AWTException {
        CCUtils.scrollPageViaElement(driver, patientAccessButtonElement);
        CCUtils.verifyAndWaitForElement(patientAccessButtonElement, driver);
        log("Clicked on Patient Access : " + elementName);
        javascriptClick(patientAccessButtonElement);
    }

    public boolean printListOfpermissions() throws InterruptedException, AWTException {
        for (int i = 0; i < defaultPermissionList.size(); i++)
            log(defaultPermissionList.get(i).getText());
        return true;
    }

    public void enterAutoAgeout(int i) throws InterruptedException, AWTException {
        ageRules.get(ageRules.size() - 1).clear();
        ageRules.get(ageRules.size() - 1).sendKeys(String.valueOf(i));
        log("Age-Out added Successfully");
    }

    public void clickStateRulesCombo() throws InterruptedException, AWTException {
        stateRulesDropdown.get(stateRulesDropdown.size() - 1).click();
    }

    public void selectStatesForAutoAgeout(int number) throws InterruptedException, AWTException {
        CCUtils.verifyElementToBeClickable(stateSpecificRuleSaveButton, driver);
        for (int i = 0; i <= number; i++) {
            log(stateListOptions.get(i).getText());
            stateListOptions.get(i).click();
        }
        log("Age-Out states added Successfully");
    }

    public void selectAllStatesForAutoAgeout() throws InterruptedException, AWTException {
        CCUtils.verifyElementToBeClickable(stateSpecificRuleSaveButton, driver);
        for (int i = 0; i < stateListOptions.size(); i++) {
            log(stateListOptions.get(i).getText());
            stateListOptions.get(i).click();
        }
        log("all Age-Out states added Successfully");
    }

    public void inputTextforDefaultAgeoutGaurdian(WebElement defaultAgeOut, int i) {
        defaultAgeOut.clear();
        defaultAgeOut.sendKeys(String.valueOf(i));
    }

    public void autoAgeOutToggleValidation() throws Exception {
        if (autoAgeOutToggle.isSelected())
            verifyPatientAccessLabelIsDisplayed(autoAgeOutToggle, "Auto AgeOut Section");
        else
            clickOnPatientAccessButton(autoAgeOutToggle, "Auto Age-Out Toggle Button");

    }

    public void stateSpecificRule(int age, int numberofStates) throws Exception {
        clickOnPatientAccessButton(stateSpecificRuleButton, "State Specific Rule Button");
        enterAutoAgeout(age);
        clickStateRulesCombo();
        selectStatesForAutoAgeout(numberofStates);
        clickOnPatientAccessButton(stateSpecificRuleSaveButton, "State Specific Rule Save Button");
    }


    public HashMap<String, Boolean> verifyPatientAccessDetailElements() throws Exception {
        HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        result.put("PXPP-24080_TC_01 verify Patient Access Title", verifyPatientAccessLabelIsDisplayed(patientAccessLabel, "Patient Access Details"));
        result.put("PXPP-24081_TC_02 verifying PatientAccessExpandArrow", verifyPatientAccessLabelIsDisplayed(patientAccessExpandButton, "Expand Button"));
        clickOnPatientAccessButton(patientAccessExpandButton, "Expand Button");
        logStep("PXPP-24084_TC_03_Verify the Status of Linked Accounts and Auto Age- Out under Patient Access Rules section in Command Center Application");
        result.put("verify LinkedAccounts Title", verifyPatientAccessLabelIsDisplayed(linkedAccountsLabel, "LinkedAccounts section"));
        result.put("verify Auto AgeOut Title", verifyPatientAccessLabelIsDisplayed(autoAgeOutLabel, "Auto AgeOut Section"));
        logStep("PXPP-24112_TC_011_Verify that by default LINKED ACCOUNTS & AUTO AGE-OUT are disabled under Patient Access Rules section");
        result.put("verify LinkedAccounts Toggle", CCUtils.isPaginationButtonDisabled(linkedAccountToggle, driver));
        result.put("verify Auto AgeOut Toggle", CCUtils.isPaginationButtonDisabled(autoAgeOutToggle, driver));
        logStep("PXPP-24092_TC_06_Verify that user can able to view the message with default age under GUARDIAN ACCESS PERMISSIONS in Patient Access Rules section");
        result.put("verify GuardianAccess Label", verifyPatientAccessLabelIsDisplayed(guardianAccessLabel, "GuardianAccess Section"));
        result.put("verify GuardianAccess Statement 1", verifyPatientAccessLabelIsDisplayed(guardianAccessStatement1, "GuardianAccess Statement 1"));
        result.put("verify GuardianAccess Statement 2", verifyPatientAccessLabelIsDisplayed(guardianAccessStatement2, "GuardianAccess Statement 2"));
        result.put("verify GuardianAccess Default Value", verifyPatientAccessLabelIsDisplayed(guardianAccessDefaultValue, "GuardianAccess Default Value"));
        logStep("PXPP-24094_TC_07_Verify that user can able to view the Guardian Access in GUARDIAN ACCESS PERMISSIONS section");
        result.put("verify GuardianAccess Messages", verifyPatientAccessLabelIsDisplayed(guardianAccessMessages, "GuardianAccess Messages"));
        result.put("verify GuardianAccess Medications", verifyPatientAccessLabelIsDisplayed(guardianAccessMedications, "GuardianAccess Medications"));
        result.put("verify GuardianAccess Forms", verifyPatientAccessLabelIsDisplayed(guardianAccessForms, "GuardianAccess Forms"));
        result.put("verify GuardianAccess Appointments", verifyPatientAccessLabelIsDisplayed(guardianAccessAppointments, "GuardianAccess Appointments"));
        result.put("verify GuardianAccess Health Record", verifyPatientAccessLabelIsDisplayed(guardianAccessHealthRecord, "GuardianAccess Health Record"));
        result.put("PXPP-24095_TC_08_verify GuardianAccess permission", printListOfpermissions());
        result.put("PXPP-24081_TC_02_verifying PatientAccessCollapseArrow", verifyPatientAccessLabelIsDisplayed(patientAccessCollapseButton, "Collapse Button"));
        clickOnPatientAccessButton(patientAccessCollapseButton, "Collapse Button");
        return result;
    }

    public void validateEditandCancelPatientAccess() throws Exception {
        clickOnPatientAccessButton(patientAccessExpandButton, "Expand Button");
        clickOnPatientAccessButton(patientAccessEdit, "Edit Button");
        clickOnPatientAccessButton(linkedAccountToggle, "Linked Account Toggle Button");
        clickOnPatientAccessButton(autoAgeOutToggle, "Auto Age-Out Toggle Button");
        log("PXPP-24090_TC_04_Verify that user can able to view the state specific auto age-out rules section");
        verifyPatientAccessLabelIsDisplayed(autoAgeOutStatement1, "autoAgeOut Statement 1");
        verifyPatientAccessLabelIsDisplayed(autoAgeOutStatement2, "autoAgeOut Statement 2");
        log("Validating Default Statment for Auto Age-out : " + autoAgeOutStatement1.getText());
        CCUtils.assertEquals(defaultAutoAgeOut.getAttribute("value"), "18");
        inputTextforDefaultAgeoutGaurdian(defaultAutoAgeOut, 21);
        log("PXPP-24086_TC_05_Verify that user can view states by using STATE SPECIFIC RULE button in AUTO AGE-OUT section");
        stateSpecificRule(18, 5);
        log("PXPP-24110_TC_09_Verify that user can able to select the radio buttons which is present the Guardian Access");
        log("Validating Default Statment for Gaurdian Access : " + guardianAccessStatement1.getText());
        CCUtils.assertEquals(accessTillAgeText.getAttribute("value"), "18");
        inputTextforDefaultAgeoutGaurdian(accessTillAgeText, 21);
        clickOnPatientAccessButton(appointmentViewOnly, "appointment ViewOnly");
        clickOnPatientAccessButton(messagesNoAccess, "messages NoAccess");
        clickOnPatientAccessButton(medicationsFullAccess, "medications FullAccess");
        clickOnPatientAccessButton(formsViewOnly, "forms ViewOnly");
        log("PXPP-24111_TC_010_Verify that Only Full Access checkbox is displayed for Health Record in the Guardian Access");
        if (healthRecordFullAccess.isSelected())
            verifyPatientAccessLabelIsDisplayed(healthRecordFullAccess, "healthRecord FullAccess");
        else
            clickOnPatientAccessButton(healthRecordFullAccess, "healthRecord FullAccess");
        clickOnPatientAccessButton(patientAccessCancelButton, "Patient Access Cancel Button");
    }

    public void validateEditandSavePatientAccessDetails() throws Exception {
        clickOnPatientAccessButton(patientAccessExpandButton, "Expand Button");
        log("PXPP-24089_TC_012_Verify that user can able to click on the EDIT button under Patient Access Rules section");
        clickOnPatientAccessButton(patientAccessEdit, "Edit Button");
        clickOnPatientAccessButton(linkedAccountToggle, "Linked Account Toggle Button");
        log("PXPP-24115_TC_016_Verify that user can able to select a single state & age-out with Guardian Access Permissions under Patient Access Rules section");
        autoAgeOutToggleValidation();
        inputTextforDefaultAgeoutGaurdian(defaultAutoAgeOut, 21);
        stateSpecificRule(18, 6);
        log("PXPP-25423_TC_019_ Verify whether user able to edit the default age text box from AUTO AGE-OUT and GUARDIAN ACCESS PERMISSIONS sub sections");
        inputTextforDefaultAgeoutGaurdian(accessTillAgeText, 21);
        clickOnPatientAccessButton(appointmentViewOnly, "appointment ViewOnly");
        clickOnPatientAccessButton(messagesNoAccess, "messages NoAccess");
        clickOnPatientAccessButton(medicationsFullAccess, "medications FullAccess");
        clickOnPatientAccessButton(formsViewOnly, "forms ViewOnly");
        clickOnPatientAccessButton(healthRecordFullAccess, "healthRecord FullAccess");
        clickOnPatientAccessButton(patientAccessSaveButton, "Patient Access Save Button");
    }

    public void addSingleStatetoSingleAgeAndMultipleAgeMultipleStates() throws Exception {
        clickOnPatientAccessButton(patientAccessExpandButton, "Expand Button");
        clickOnPatientAccessButton(patientAccessEdit, "Edit Button");
        autoAgeOutToggleValidation();
        log("PXPP-24098_TC_013_Verify that user can able to select a single state with age-out 20 under Patient Access Rules section");
        clickOnPatientAccessButton(stateSpecificRuleButton, "State Specific Rule Button");
        enterAutoAgeout(19);
        clickStateRulesCombo();
        selectStatesForAutoAgeout(0);
        String stateName1 = stateListOptions.get(0).getText();
        clickOnPatientAccessButton(stateSpecificRuleSaveButton, "State Specific Rule Save Button");
        log("PXPP-24113_TC_014_Verify that user can able to select a same state with age-out 21 under Patient Access Rules section");
        clickOnPatientAccessButton(stateSpecificRuleButton, "State Specific Rule Button");
        enterAutoAgeout(20);
        clickStateRulesCombo();
        for (int i = 0; i < stateListOptions.size(); i++) {
            if (stateName1.equalsIgnoreCase(stateListOptions.get(i).getText())) {
                log(stateName1 + "State is available for 2nd iteration");
                break;
            } else
                log(stateName1 + "State is not available for 2nd iteration");
        }
        clickOnPatientAccessButton(stateSpecificRuleClearButton, "State Specific Rule Clear Button");
        log("PXPP-24114_TC_015_Verify that user can able to select a multiple state & age-out under Patient Access Rules section");
        clickStateRulesCombo();
        selectStatesForAutoAgeout(6);
        clickOnPatientAccessButton(stateSpecificRuleSaveButton, "State Specific Rule Save Button");
        clickOnPatientAccessButton(patientAccessSaveButton, "Patient Access Save Button");
    }

    public void disableAndEnableAutoAgeOut() throws Exception {
        clickOnPatientAccessButton(patientAccessExpandButton, "Expand Button");
        clickOnPatientAccessButton(patientAccessEdit, "Edit Button");
        log("PXPP-24142_TC_017_Verify that user can able to view the latest changes after enabling the AUTO AGE - OUT section");
        autoAgeOutToggleValidation();
        clickOnPatientAccessButton(stateSpecificRuleButton, "State Specific Rule Button");
        enterAutoAgeout(17);
        clickStateRulesCombo();
        selectStatesForAutoAgeout(0);
        String stateName1 = stateListOptions.get(0).getText();
        clickOnPatientAccessButton(stateSpecificRuleSaveButton, "State Specific Rule Save Button");
        clickOnPatientAccessButton(patientAccessSaveButton, "Patient Access Save Button");
        CCUtils.verifyAndWaitForElement(toasterMessage, driver);
        clickOnPatientAccessButton(patientAccessEdit, "Edit Button");
        clickOnPatientAccessButton(autoAgeOutToggle, "Auto Age-Out Toggle Button");
        if (CCUtils.isPaginationButtonDisabled(autoAgeOutToggle, driver)) {
            log("auto Age-out Toggle Off");
            clickOnPatientAccessButton(patientAccessSaveButton, "Patient Access Save Button");
            CCUtils.verifyAndWaitForElement(toasterMessage, driver);
        } else
            log("auto Age-out Toggle On");
        clickOnPatientAccessButton(patientAccessEdit, "Edit Button");
        autoAgeOutToggleValidation();
        log("Validating Default Statment for Auto Age-out : " + autoAgeOutStatement1.getText());
        WebElement statepath = driver.findElement(By.xpath("//div/span[normalize-space(text())='" + stateName1 + "']"));
        log("Validating State Value : " + statepath.getText());
        stateSpecificRule(20, 6);
        log("PXPP-25425_TC_020_ Verify whether user able to remove the STATE SPECIFIC RULES from AUTO AGE-OUT");
        WebElement removeButton = driver.findElement(By.xpath("//span[normalize-space(text())='" + stateName1 + "']/../../../../../../following-sibling::div/button"));
        clickOnPatientAccessButton(removeButton, "Remove Button for " + stateName1);
        clickOnPatientAccessButton(patientAccessSaveButton, "Patient Access Save Button");
    }


    public void validateAllStatesMultipleAges() throws Exception {
        clickOnPatientAccessButton(patientAccessExpandButton, "Expand Button");
        clickOnPatientAccessButton(patientAccessEdit, "Edit Button");
        clickOnPatientAccessButton(linkedAccountToggle, "Linked Account Toggle Button");
        log("PXPP-24119_TC_018_Verify that user can able to select a multiple state & age-out with Guardian Access Permissions under Patient Access Rules section");
        autoAgeOutToggleValidation();
        inputTextforDefaultAgeoutGaurdian(defaultAutoAgeOut, 22);
        int count = 14;
        for (int i = 0; i < 5; i++) {
            stateSpecificRule(count, 6);
            count++;
        }
        clickOnPatientAccessButton(stateSpecificRuleButton, "State Specific Rule Button");
        enterAutoAgeout(21);
        clickStateRulesCombo();
        selectAllStatesForAutoAgeout();
        clickOnPatientAccessButton(stateSpecificRuleSaveButton, "State Specific Rule Save Button");
        inputTextforDefaultAgeoutGaurdian(accessTillAgeText, 22);
        clickOnPatientAccessButton(appointmentFullAccess, "appointment FullAccess");
        clickOnPatientAccessButton(messagesViewOnly, "messages ViewOnly");
        clickOnPatientAccessButton(medicationsFullAccess, "medications FullAccess");
        clickOnPatientAccessButton(formsViewOnly, "forms ViewOnly");
        clickOnPatientAccessButton(healthRecordFullAccess, "healthRecord FullAccess");
        clickOnPatientAccessButton(patientAccessSaveButton, "Patient Access Save Button");
        CCUtils.verifyAndWaitForElement(toasterMessage, driver);
        log("PXPP-25436_TC_021_ Verify STATE SPECIFIC RULES button going to disable from AUTO AGE-OUT when user selected all states");
        clickOnPatientAccessButton(patientAccessEdit, "Edit Button");
        if(stateSpecificRuleButtondisplay.isEnabled())
            log("State Specific Rules button is enabled : " + stateSpecificRuleButtondisplay.isEnabled());
        else
            log("State Specific Rules button is disabled : " + stateSpecificRuleButtondisplay.isEnabled());
        log("PXPP-25425_TC_020_ Verify whether user able to remove the STATE SPECIFIC RULES from AUTO AGE-OUT");
        for (int i = 1; i <= listOfRemoveAgeSpecificRuleIcon.size(); i++) {
            clickOnPatientAccessButton(listOfRemoveAgeSpecificRuleIcon.get(i), "Remove button validation for list: " + i);
        }
        clickOnPatientAccessButton(patientAccessSaveButton, "Patient Access Save Button");
    }


}