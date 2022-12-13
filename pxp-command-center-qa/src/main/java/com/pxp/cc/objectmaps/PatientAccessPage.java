// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import com.pxp.cc.util.CCUtils;
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

    @FindBy(xpath = "//div[normalize-space(text())='Patient Access Rules']/../following-sibling::div//span[normalize-space(text())='Edit']")
    private WebElement patientAccessEdit;

    @FindBy(xpath = "//mat-slide-toggle[@formcontrolname='linkAccounts']//input[@aria-label='Linked Accounts']")
    private WebElement linkedAccountToggle;

    @FindBy(xpath = "//mat-slide-toggle[@formcontrolname='autoAgeout']//input[@aria-label='Auto Age out']")
    private WebElement autoAgeOutToggle;

    @FindBy(xpath = "//input[@formcontrolname='defaultAgeout']")
    private WebElement defaultAutoAgeOut;

    @FindBy(xpath = " //button/span[normalize-space(text())='STATE SPECIFIC RULE']")
    private WebElement stateSpecificRuleButton;

    @FindBy(xpath = "//input[@formcontrolname='age']")
    private List<WebElement> ageRules;

    @FindBy(xpath = "//ng-select[@formcontrolname='states']//div[@role='combobox']/input")
    private List<WebElement> stateRulesDropdown;

    @FindBy(xpath = "//*[@aria-label='Options list']//label[@id='directoryDirectName']")
    private List<WebElement> stateListOptions;

    @FindBy(xpath = "//button/span[text()='Save']")
    private WebElement stateSpecificRuleSaveButton;

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


    public HashMap<String, Boolean> verifyPatientAccessDetailElements() throws Exception {
        HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        result.put("PXPP-24080_TC_01 verify Patient Access Title", verifyPatientAccessLabelIsDisplayed(patientAccessLabel, "Patient Access Details"));
        result.put("PXPP-24081_TC_02 verifying PatientAccessExpandArrow", verifyPatientAccessLabelIsDisplayed(patientAccessExpandButton, "Expand Button"));
        clickOnPatientAccessButton(patientAccessExpandButton, "Expand Button");
        logStep("PXPP-24084_TC_03_Verify the Status of Linked Accounts and Auto Age- Out under Patient Access Rules section in Command Center Application");
        result.put("verify LinkedAccounts Title", verifyPatientAccessLabelIsDisplayed(linkedAccountsLabel, "LinkedAccounts section"));
        result.put("verify LinkedAccounts Toggle", CCUtils.isPaginationButtonDisabled(linkedAccountToggle, driver));
        result.put("verify Auto AgeOut Title", verifyPatientAccessLabelIsDisplayed(autoAgeOutLabel, "Auto AgeOut Section"));
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

    public void validateEditandSavePatientAccessDetails() throws Exception {
        clickOnPatientAccessButton(patientAccessExpandButton, "Expand Button");
        clickOnPatientAccessButton(patientAccessEdit, "Edit Button");
        clickOnPatientAccessButton(linkedAccountToggle, "Linked Account Toggle Button");
        clickOnPatientAccessButton(autoAgeOutToggle, "Auto Age-Out Toggle Button");
        inputTextforDefaultAgeoutGaurdian(defaultAutoAgeOut, 21);
        clickOnPatientAccessButton(stateSpecificRuleButton, "State Specific Rule Button");
        enterAutoAgeout(18);
        clickStateRulesCombo();
        selectStatesForAutoAgeout();
        clickOnPatientAccessButton(stateSpecificRuleSaveButton, "State Specific Rule Save Button");
        inputTextforDefaultAgeoutGaurdian(accessTillAgeText, 21);
        clickOnPatientAccessButton(appointmentViewOnly, "appointment ViewOnly");
        clickOnPatientAccessButton(messagesNoAccess, "messages NoAccess");
        clickOnPatientAccessButton(medicationsFullAccess, "medications FullAccess");
        clickOnPatientAccessButton(formsViewOnly, "forms ViewOnly");
        clickOnPatientAccessButton(healthRecordFullAccess, "healthRecord FullAccess");
        clickOnPatientAccessButton(patientAccessSaveButton, "Patient Access Save Button");
    }

    public void validateEditandCancelPatientAccess() throws Exception {
        clickOnPatientAccessButton(patientAccessExpandButton, "Expand Button");
        clickOnPatientAccessButton(patientAccessEdit, "Edit Button");
        clickOnPatientAccessButton(linkedAccountToggle, "Linked Account Toggle Button");
        clickOnPatientAccessButton(autoAgeOutToggle, "Auto Age-Out Toggle Button");
        log("PXPP-24090_TC_04_Verify that user can able to view the state specific auto age-out rules section");
        verifyPatientAccessLabelIsDisplayed(autoAgeOutStatement1, "autoAgeOut Statement 1");
        verifyPatientAccessLabelIsDisplayed(autoAgeOutStatement2, "autoAgeOut Statement 2");
        log(autoAgeOutStatement1.getText());
        log(autoAgeOutStatement2.getTagName());
        log(defaultAutoAgeOut.getAttribute("value"));
        inputTextforDefaultAgeoutGaurdian(defaultAutoAgeOut, 21);
        log("PXPP-24086_TC_05_Verify that user can view states by using STATE SPECIFIC RULE button in AUTO AGE-OUT section");
        clickOnPatientAccessButton(stateSpecificRuleButton, "State Specific Rule Button");
        enterAutoAgeout(18);
        clickStateRulesCombo();
        selectStatesForAutoAgeout();
        clickOnPatientAccessButton(stateSpecificRuleSaveButton, "State Specific Rule Save Button");
        log("PXPP-24110_TC_09_Verify that user can able to select the radio buttons which is present the Guardian Access");
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
        log("Clicked on Patient Access" + elementName);
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

    public void selectStatesForAutoAgeout() throws InterruptedException, AWTException {
        CCUtils.verifyElementToBeClickable(stateSpecificRuleSaveButton, driver);
        for (int i = 0; i <= 10; i++) {
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
}