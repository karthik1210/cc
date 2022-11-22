// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import com.google.sitebricks.client.Web;
import com.pxp.cc.util.CCUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class PrivacyPolicySettingsPage extends CCMainPage {

    public PrivacyPolicySettingsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[normalize-space(text())='Privacy Policies']")
    private WebElement privacyPoliciesLabel;

    @FindBy(xpath = "//div[normalize-space(text())='Privacy Policies']/../following-sibling::div/span/button/mat-icon[normalize-space(text())='keyboard_arrow_down']")
    private WebElement privacyPolicyExpandButton;

    @FindBy(xpath = "//div[normalize-space(text())='Privacy Policies']/../following-sibling::div/button[@aria-label='Add']")
    private WebElement privacyPolicyAddButton;

    @FindBy(xpath = "//span[normalize-space(text())='No Privacy Policies added.']")
    private WebElement noPrivacyPoliciesText;

    @FindBy(xpath = "//span[normalize-space(text())='Available for Purchase']")
    private WebElement availableForPurchaseLabel;

    @FindBy(xpath = "//span[normalize-space(text())='Available for Purchase']/../following-sibling::div/span/div/div/span/following-sibling::span[normalize-space(text())='Virtual Visits']/../following-sibling::div/span[text()='ACTIVATE']")
    private WebElement virtualVisitsActivate;

    @FindBy(xpath = "//span[normalize-space(text())='Available for Purchase']/../following-sibling::div/span/div/div/span/following-sibling::span[normalize-space(text())='Pay']/../following-sibling::div/span[text()='ACTIVATE']")
    private WebElement payActivate;

    @FindBy(xpath = "//span[normalize-space(text())='Available for Purchase']/../following-sibling::div/span/div/div/span/following-sibling::span[normalize-space(text())='Portal']/../following-sibling::div/span[text()='ACTIVATE']")
    private WebElement portalActivate;

    @FindBy(xpath = "//span[normalize-space(text())='Available for Purchase']/../following-sibling::div/span/div/div/span/following-sibling::span[normalize-space(text())='PSS']/../following-sibling::div/span[text()='ACTIVATE']")
    private WebElement pssActivate;

    @FindBy(xpath = "//span[normalize-space(text())='Available for Purchase']/../following-sibling::div/span/div/div/span/following-sibling::span[normalize-space(text())='Chat']/../following-sibling::div/span[text()='ACTIVATE']")
    private WebElement chatActivate;

    @FindBy(xpath = "//span[normalize-space(text())='Available for Purchase']/../following-sibling::div/span/div/div/span/following-sibling::span[normalize-space(text())='Engage']/../following-sibling::div/span[text()='ACTIVATE']")
    private WebElement engageActivate;

    @FindBy(xpath = "//button[normalize-space(text())='Yes, Activate']")
    private WebElement yesActivateButton;

    @FindBy(xpath = "//div[normalize-space(text())='Privacy Policies']/../following-sibling::div/span/button/mat-icon[normalize-space(text())='keyboard_arrow_up']")
    private WebElement privacyPolicyCollapseButton;

    @FindBy(xpath = "//label[normalize-space(text())='Policy Name *']")
    private WebElement policyNameLabel;

    @FindBy(xpath = "//input[@id='policyName']")
    private WebElement policyNameTextBox;

    @FindBy(id = "createPrivacyPolicyButton")
    private WebElement createPrivacyPolicySaveButton;

    @FindBy(id = "backNavButton")
    private WebElement privacyPolicyBackOrCancelButton;

    @FindBy(xpath = "//label[normalize-space(text())='Policy Language']")
    private WebElement privacyPolicyLanguageLabel;

    @FindBy(xpath = "//div[normalize-space(text())='English']")
    private WebElement privacyPolicyEnglishLanguageTab;

    @FindBy(xpath = "//div[normalize-space(text())='SPANISH']")
    private WebElement privacyPolicySpanishLanguageTab;

    @FindBy(xpath = "//div[normalize-space(text())='Policy name is required']")
    private WebElement policyNameEmptyErrorMessage;

    @FindBy(xpath = "//p[normalize-space(text())='Status']")
    private WebElement statusLabel;

    @FindBy(xpath = "//input[@value='Draft']")
    private WebElement draftRadioButton;

    @FindBy(xpath = "//input[@value='Active']")
    private WebElement activeRadioButton;

    @FindBy(xpath = "//input[@value='Inactive']")
    private WebElement inactiveRadioButton;

    @FindBy(xpath = "//div[normalize-space(text())='Draft']")
    private WebElement draftLabel;

    @FindBy(xpath = "//div[normalize-space(text())='Active']")
    private WebElement activeLabel;

    @FindBy(xpath = "//p[normalize-space(text())='Solutions']")
    private WebElement solutionsLabel;

    @FindBy(xpath = "//span[normalize-space(text())='Virtual Visits']")
    private WebElement virtualVisitsLabel;

    @FindBy(xpath = "//span[normalize-space(text())='Pay']")
    private WebElement payLabel;

    @FindBy(xpath = "//span[normalize-space(text())='Portal']")
    private WebElement portalLabel;

    @FindBy(xpath = "//span[normalize-space(text())='PSS']")
    private WebElement pssLabel;

    @FindBy(xpath = "//span[normalize-space(text())='Chat']")
    private WebElement chatLabel;

    @FindBy(xpath = "//span[normalize-space(text())='Engage']")
    private WebElement engageLabel;

    @FindBy(xpath = "//mat-icon[normalize-space(text())='videocam']")
    private WebElement virtualVisitsIcon;

    @FindBy(xpath = "//mat-icon[normalize-space(text())='attach_money']")
    private WebElement payIcon;

    @FindBy(xpath = "//mat-icon[normalize-space(text())='person']")
    private WebElement portalIcon;

    @FindBy(xpath = "//mat-icon[normalize-space(text())='event']")
    private WebElement pssIcon;

    @FindBy(xpath = "//mat-icon[normalize-space(text())='chat')]")
    private WebElement chatIcon;

    @FindBy(xpath = "//mat-icon[normalize-space(text())='campaign']")
    private WebElement engageIcon;

    @FindBy(xpath = "//input[@aria-label='Virtual Visits']/following-sibling::div")
    private WebElement virtualVisitsToggleButton;

    @FindBy(xpath = "//input[@aria-label='Pay']/following-sibling::div")
    private WebElement payToggleButton;

    @FindBy(xpath = "//input[@aria-label='Portal']/following-sibling::div")
    private WebElement portalToggleButton;

    @FindBy(xpath = "//input[@aria-label='PSS']/following-sibling::div")
    private WebElement pssToggleButton;

    @FindBy(xpath = "//input[@aria-label='Chat']/following-sibling::div")
    private WebElement chatToggleButton;

    @FindBy(xpath = "//input[@aria-label='Engage']/following-sibling::div")
    private WebElement engageToggleButton;

    @FindBy(xpath = "//div[normalize-space(text())='Maximum characters reached']")
    private WebElement maximumCharacterError;

    @FindBy(xpath = "//span[normalize-space(text())='Disregard Changes']")
    private WebElement disregardChanges;

    @FindBy(xpath = "//button[normalize-space(text())='Return to Page']")
    private WebElement returnToPage;

    @FindBy(xpath = "//div[contains(text(), 'STATEMENT OF PRIVACY POLICY')]")
    private WebElement englishContent;

    @FindBy(xpath = "//div[contains(text(), 'Declaración de pólitica')]")
    private WebElement spanishContent;

    @FindBy(xpath = "//span[@class='content']")
    private List<WebElement> policyList;

    @FindBy(xpath = "//span[normalize-space(text())='Virtual Visits']")
    private WebElement previewVirtualVisitsLabel;

    @FindBy(xpath = "//span[normalize-space(text())='Pay']")
    private WebElement previewPayLabel;

    @FindBy(xpath = "//span[normalize-space(text())='Portal']")
    private WebElement previewPortalLabel;

    @FindBy(xpath = "//span[normalize-space(text())='PSS']")
    private WebElement previewPSSLabel;

    @FindBy(xpath = "//span[normalize-space(text())='Chat']")
    private WebElement previewChatLabel;

    @FindBy(xpath = "//span[normalize-space(text())='Engage']")
    private WebElement previewEngageLabel;

    @FindBy(xpath = "//button[normalize-space(text())='Preview']")
    private WebElement kebabPreview;

    @FindBy(xpath = "//button[normalize-space(text())='Edit']")
    private WebElement kebabEdit;

    @FindBy(xpath = "//button[normalize-space(text())='Delete']")
    private WebElement kebabDelete;

    @FindBy(xpath = "//button[@id='edit']")
    private WebElement previewEdit;

    @FindBy(id = "editPrivacyPolicyButton")
    private WebElement editPrivacyPolicySaveButton;

    @FindBy(xpath = "//i[normalize-space(text())='DRAFT']")
    private WebElement draftStatusInPrivacyPoliciesSection;

    @FindBy(xpath = "//span[normalize-space(text())='Are you sure you want to delete this item?']")
    private WebElement deleteConfirmationMessage;

    @FindBy(xpath = "//button[normalize-space(text())='Yes, Delete']")
    private WebElement yesDelete;

    @FindBy(xpath = "//div[normalize-space(text())='Purchased Solutions']/following-sibling::div/button/mat-icon[normalize-space(text())='keyboard_arrow_down']")
    private WebElement expandButtonForPurchaseSolutions;

    @FindBy(xpath = "//button[normalize-space(text())='Yes, Continue']")
    private WebElement yesContinueButton;

    @FindBy(xpath = "//span[text()='By making this Privacy Policy inactive, it will no longer display in any of the associated products.  Do you want to proceed?']")
    private WebElement noActivePopUpMessage;

    @FindBy(xpath = "//span[normalize-space(text())='Cancel']")
    private WebElement cancelPopUp;

    @FindBy(xpath = "//span[normalize-space(text())='clear']")
    private WebElement clearButton;

    @FindBy(xpath = "//span[normalize-space(text())='The following solutions will no longer display Privacy Policy.']")
    private WebElement solutionMissingError;

    @FindBy(xpath = "//p[normalize-space(text())='Hide Inactive']")
    private WebElement hideInactive;

    @FindBy(xpath = "//p[normalize-space(text())='Show Inactive']")
    private WebElement showInactive;

    @FindBy(xpath = "//span[@mattooltip='Privacy Policy is active']")
    private WebElement activeToolTip;

    @FindBy(xpath = "//span[@mattooltip='Privacy Policy not active']")
    private WebElement inactiveToolTip;

    @FindBy(xpath = "//button[@mattooltip='Edit Policy to Activate']")
    private WebElement draftToolTip;

    @FindBy(xpath = "//div[normalize-space(text())='Must add Privacy Policy language']")
    private WebElement mustAddLanguageError;

    @FindBy(xpath = "//div[@class='angular-editor-textarea']")
    private WebElement languageContentTextArea;

    public HashMap<String,Boolean> verifyPrivacyPocilyBasicElements(){
        HashMap<String,Boolean> result = new HashMap<String, Boolean>();
        logStep("PXPP-20371 TC_001_Verify that user can able to see privacy policy details section under practices in Command Center Application");
        result.put("verifyPrivacyPoliciesLabel",verifyPrivacyPoliciesLabel());
        result.put("verifyPrivacyPolicyAddButton",verifyPrivacyPolicyAddButton());
        logStep("PXPP-23295 TC_003_Verify Expand functionality for privacy policy details section under practices in Command Center Application");
        result.put("verifyPrivacyPolicyExpandButton",verifyPrivacyPolicyExpandButton());
        clickPrivacyPolicyExpandButton();
        logStep("PXPP-24829 TC_002_Verify the Privacy Policies section, when Practice don't have any policies");
        result.put("verifyNoPrivacyPoliciesText",verifyNoPrivacyPoliciesText());
        result.put("verifyPrivacyPolicyCollapseButton",verifyPrivacyPolicyCollapseButton());
        clickPrivacyPolicyCollapseButton();
        result.put("verifyPrivacyPolicyExpandButton",verifyPrivacyPolicyExpandButton());
        logStep("PXPP-20709 TC_004_Verify that user can able to click on the add button in privacy policies section in command center application");
        clickPrivacyPolicyAddButton();
        result.put("verifyURLOfPolicyCreationPage",verifyUrlForPrivacyPolicyPage());
        logStep("PXPP-22262 TC_005_Verify the Field attributes on the right side on the new page when user clicks on \"+ADD \" under privacy policies section");
        result.put("verifyingAllElementsInPrivacyCreation",verifyAllElementsInPrivacyCreationPage());
        result.put("verifyAllSolutions", verifyAllSolutionLabel());
        clickOnSaveButton();
        logStep("PXPP-22263 TC_006_ Verify that user can notify with error “Policy name is required” when user save the Policy without entering Policy Name value");
        result.put("verifyPolicyNameError", verifyPolicyNameError());
        return result;
    }

    public boolean verifyPrivacyPoliciesLabel() {
        try {
            CCUtils.verifyAndWaitForElement(privacyPoliciesLabel, driver);
            CCUtils.scrollPageViaElement(driver, privacyPoliciesLabel);
            log("Verifying whether Privacy Policies Label present or not");
            new Actions(driver).moveToElement(privacyPoliciesLabel).build().perform();
            CCUtils.assertEquals(privacyPoliciesLabel.isDisplayed(), true);
            log("Privacy Policies Label successfully displayed");
            return true;
        }catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyPrivacyPolicyExpandButton() {
        try {
            CCUtils.scrollPageViaElement(driver, privacyPolicyExpandButton);
            CCUtils.verifyAndWaitForElement(privacyPolicyExpandButton, driver);
            log("Verifying whether Privacy Policy Expand Button present or not");
            new Actions(driver).moveToElement(privacyPolicyExpandButton).build().perform();
            CCUtils.assertEquals(privacyPolicyExpandButton.isDisplayed(), true);
            log("Privacy Policy Expand Button successfully displayed");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public void clickPrivacyPolicyExpandButton() {
        CCUtils.scrollPageViaElement(driver, privacyPolicyExpandButton);
        CCUtils.verifyAndWaitForElement(privacyPolicyExpandButton, driver);
        log("Clicking on Privacy Policy Expand Button");
        new Actions(driver).moveToElement(privacyPolicyExpandButton).pause(3000).click().build().perform();
        log("Successfully clicked on privacy policy Expand button");
    }

    public boolean verifyPrivacyPolicyCollapseButton() {
        try {
            CCUtils.scrollPageViaElement(driver, privacyPolicyCollapseButton);
            CCUtils.verifyAndWaitForElement(privacyPolicyCollapseButton, driver);
            log("Verifying whether Privacy Policy Collapse Button present or not");
            new Actions(driver).moveToElement(privacyPolicyCollapseButton).build().perform();
            CCUtils.assertEquals(privacyPolicyCollapseButton.isDisplayed(), true);
            log("Privacy Policy Collapse Button successfully displayed");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public void clickPrivacyPolicyCollapseButton() {
        CCUtils.scrollPageViaElement(driver, privacyPolicyCollapseButton);
        CCUtils.verifyAndWaitForElement(privacyPolicyCollapseButton, driver);
        log("Clicking on Privacy Policy Collapse Button");
        new Actions(driver).moveToElement(privacyPolicyCollapseButton).pause(3000).click().build().perform();
        log("Successfully clicked on privacy policy Collapse button");
    }

    public boolean verifyPrivacyPolicyAddButton() {
        try {
            CCUtils.scrollPageViaElement(driver, privacyPolicyAddButton);
            CCUtils.verifyAndWaitForElement(privacyPolicyAddButton, driver);
            log("Verifying whether Privacy Policy Add Button present or not");
            new Actions(driver).moveToElement(privacyPolicyAddButton).build().perform();
            CCUtils.assertEquals(privacyPolicyAddButton.isDisplayed(), true);
            log("Privacy Policy Add Button successfully displayed");
            return true;
        } catch(Exception exception) {
            return false;
        }
    }

    public void clickPrivacyPolicyAddButton() {
        CCUtils.scrollPageViaElement(driver, privacyPolicyAddButton);
        CCUtils.verifyAndWaitForElement(privacyPolicyAddButton, driver);
        log("Clicking on Privacy Policy Add Button");
        new Actions(driver).moveToElement(privacyPolicyAddButton).pause(2000).click().build().perform();
        log("Successfully clicked on privacy policy Add button");
    }

    public boolean verifyNoPrivacyPoliciesText() {
        try {
            CCUtils.scrollPageViaElement(driver, noPrivacyPoliciesText);
            CCUtils.verifyAndWaitForElement(noPrivacyPoliciesText, driver);
            log("Verifying whether No Privacy Policies Text present or not");
            new Actions(driver).moveToElement(noPrivacyPoliciesText).build().perform();
            CCUtils.assertEquals(noPrivacyPoliciesText.getText().equalsIgnoreCase("No Privacy Policies added."), true);
            log("No Privacy Policies Text successfully displayed");
            return true;
        } catch(Exception exception) {
            return false;
        }
    }

    public boolean verifyAvailableForPurchaseLabel() {
        try {
            CCUtils.scrollPageViaElement(driver, availableForPurchaseLabel);
            CCUtils.verifyAndWaitForElement(availableForPurchaseLabel, driver);
            log("Verifying whether Available for Purchase Label present or not");
            CCUtils.assertEquals(availableForPurchaseLabel.isDisplayed(), true);
            log("Available for Purchase Label successfully displayed");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public void clickOnActivateSolutionButton(String solution) {
        if(solution.equalsIgnoreCase("Virtual Visits")) {
            CCUtils.verifyAndWaitForElement(virtualVisitsActivate, driver);
            new Actions(driver).moveToElement(virtualVisitsActivate).click().build().perform();
        }
        else  if(solution.equalsIgnoreCase("Pay")) {
            CCUtils.verifyAndWaitForElement(payActivate, driver);
            new Actions(driver).moveToElement(payActivate).click().build().perform();
        }
        else  if(solution.equalsIgnoreCase("Portal")) {
            CCUtils.verifyAndWaitForElement(portalActivate, driver);
            new Actions(driver).moveToElement(portalActivate).click().build().perform();
        }
        else  if(solution.equalsIgnoreCase("PSS")) {
            CCUtils.verifyAndWaitForElement(pssActivate, driver);
            new Actions(driver).moveToElement(pssActivate).click().build().perform();
        }
        else  if(solution.equalsIgnoreCase("Chat")) {
            CCUtils.verifyAndWaitForElement(chatActivate, driver);
            new Actions(driver).moveToElement(chatActivate).click().build().perform();
        }
        else  if(solution.equalsIgnoreCase("Engage")) {
            CCUtils.verifyAndWaitForElement(engageActivate, driver);
            new Actions(driver).moveToElement(engageActivate).click().build().perform();
        }
    }

    public void activateAllSolutions() {
        CCUtils.verifyAndWaitForElement(virtualVisitsActivate, driver);
        new Actions(driver).moveToElement(virtualVisitsActivate).click().build().perform();
        CCUtils.verifyAndWaitForElement(yesActivateButton, driver);
        new Actions(driver).moveToElement(yesActivateButton).click().build().perform();
        CCUtils.verifyAndWaitForElement(payActivate, driver);
        new Actions(driver).moveToElement(payActivate).click().build().perform();
        CCUtils.verifyAndWaitForElement(yesActivateButton, driver);
        new Actions(driver).moveToElement(yesActivateButton).click().build().perform();
        /*CCUtils.verifyAndWaitForElement(portalActivate, driver);
        new Actions(driver).moveToElement(portalActivate).click().build().perform();
        CCUtils.verifyAndWaitForElement(yesActivateButton, driver);
        new Actions(driver).moveToElement(yesActivateButton).click().build().perform();*/
        CCUtils.verifyAndWaitForElement(pssActivate, driver);
        new Actions(driver).moveToElement(pssActivate).click().build().perform();
        CCUtils.verifyAndWaitForElement(yesActivateButton, driver);
        new Actions(driver).moveToElement(yesActivateButton).click().build().perform();
        /*CCUtils.verifyAndWaitForElement(chatActivate, driver);
        new Actions(driver).moveToElement(chatActivate).click().build().perform();
        CCUtils.verifyAndWaitForElement(yesActivateButton, driver);
        new Actions(driver).moveToElement(yesActivateButton).click().build().perform();*/
        CCUtils.verifyAndWaitForElement(engageActivate, driver);
        new Actions(driver).moveToElement(engageActivate).click().build().perform();
        CCUtils.verifyAndWaitForElement(yesActivateButton, driver);
        new Actions(driver).moveToElement(yesActivateButton).click().build().perform();
    }

    public void clickOnYesActivateButton() {
        CCUtils.scrollPageViaElement(driver, yesActivateButton);
        CCUtils.verifyAndWaitForElement(yesActivateButton, driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()=' Yes, Activate ']")));
        log("Clicking on yes activate Button");
        javascriptClick(yesActivateButton);
        log("Successfully clicked on yes activate button");
    }

    public boolean verifyUrlForPrivacyPolicyPage() {
        try {
            CCUtils.assertEquals(driver.getCurrentUrl().contains("privacypolicy"), true);
            log("We have successfully navigated to Privacy policy creation page");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyAllElementsInPrivacyCreationPage() {
        try {
            CCUtils.verifyAndWaitForElement(policyNameLabel, driver);
            log("Verifying whether Policy Name Label present or not");
            new Actions(driver).moveToElement(policyNameLabel).build().perform();
            CCUtils.assertEquals(policyNameLabel.isDisplayed(), true);
            log("Policy Name Label successfully displayed");
            CCUtils.verifyAndWaitForElement(policyNameTextBox, driver);
            log("Verifying whether Policy Name Text box present or not");
            new Actions(driver).moveToElement(policyNameTextBox).build().perform();
            CCUtils.assertEquals(policyNameTextBox.isDisplayed(), true);
            log("Policy Name Text box successfully displayed");
            CCUtils.verifyAndWaitForElement(statusLabel, driver);
            log("Verifying whether Status Label present or not");
            new Actions(driver).moveToElement(statusLabel).build().perform();
            CCUtils.assertEquals(statusLabel.isDisplayed(), true);
            log("Status Label successfully displayed");
            CCUtils.verifyAndWaitForElement(activeLabel, driver);
            log("Verifying whether Active Label present or not");
            new Actions(driver).moveToElement(activeLabel).build().perform();
            CCUtils.assertEquals(activeLabel.isDisplayed(), true);
            log("Active Label successfully displayed");
            CCUtils.verifyAndWaitForElement(draftLabel, driver);
            log("Verifying whether Draft Label present or not");
            new Actions(driver).moveToElement(draftLabel).build().perform();
            CCUtils.assertEquals(draftLabel.isDisplayed(), true);
            log("Draft Label successfully displayed");
            CCUtils.verifyAndWaitForElement(solutionsLabel, driver);
            log("Verifying whether Solutions Label present or not");
            new Actions(driver).moveToElement(solutionsLabel).build().perform();
            CCUtils.assertEquals(solutionsLabel.isDisplayed(), true);
            log("Solutions Label successfully displayed");
            CCUtils.verifyAndWaitForElement(privacyPolicyLanguageLabel, driver);
            log("Verifying whether Policy Language Label present or not");
            new Actions(driver).moveToElement(privacyPolicyLanguageLabel).build().perform();
            CCUtils.assertEquals(privacyPolicyLanguageLabel.isDisplayed(), true);
            log("Policy Language Label successfully displayed");
            CCUtils.verifyAndWaitForElement(privacyPolicyEnglishLanguageTab, driver);
            log("Verifying whether English Language Tab present or not");
            new Actions(driver).moveToElement(privacyPolicyEnglishLanguageTab).build().perform();
            CCUtils.assertEquals(privacyPolicyEnglishLanguageTab.isDisplayed(), true);
            log("English Language Tab successfully displayed");
            CCUtils.verifyAndWaitForElement(privacyPolicySpanishLanguageTab, driver);
            log("Verifying whether Spanish Language Tab present or not");
            new Actions(driver).moveToElement(privacyPolicySpanishLanguageTab).build().perform();
            CCUtils.assertEquals(privacyPolicySpanishLanguageTab.isDisplayed(), true);
            log("Spanish Language Tab successfully displayed");
            CCUtils.verifyAndWaitForElement(createPrivacyPolicySaveButton, driver);
            log("Verifying whether Save Button present or not");
            new Actions(driver).moveToElement(createPrivacyPolicySaveButton).build().perform();
            CCUtils.assertEquals(createPrivacyPolicySaveButton.isDisplayed(), true);
            log("Save Button successfully displayed");
            CCUtils.verifyAndWaitForElement(privacyPolicyBackOrCancelButton, driver);
            log("Verifying whether Back Arrow present or not");
            new Actions(driver).moveToElement(privacyPolicyBackOrCancelButton).build().perform();
            CCUtils.assertEquals(privacyPolicyBackOrCancelButton.isDisplayed(), true);
            log("Back Arrow successfully displayed");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public void verifyingPolicyNameTextBox(String policyName) {
        CCUtils.scrollPageViaElement(driver, policyNameTextBox);
        CCUtils.verifyAndWaitForElement(policyNameTextBox, driver);
        log("Verifying whether Policy Name Text box present or not and displaying "+policyName +" as Policy Name");
        CCUtils.assertEquals(policyNameTextBox.getAttribute("value").contains(policyName), true);
        log(policyName+" as Policy Name successfully displayed in text box");
    }

    public boolean verifyAllSolutionLabel() {
        try {
            CCUtils.verifyAndWaitForElement(virtualVisitsLabel, driver);
            CCUtils.assertEquals(virtualVisitsLabel.isDisplayed(), true);
            CCUtils.verifyAndWaitForElement(payLabel, driver);
            CCUtils.assertEquals(payLabel.isDisplayed(), true);
        /*CCUtils.verifyAndWaitForElement(portalLabel, driver);
        CCUtils.assertEquals(portalLabel.isDisplayed(), true);*/
            CCUtils.verifyAndWaitForElement(pssLabel, driver);
            CCUtils.assertEquals(pssLabel.isDisplayed(), true);
        /*CCUtils.verifyAndWaitForElement(chatLabel, driver);
        CCUtils.assertEquals(chatLabel.isDisplayed(), true);*/
            CCUtils.verifyAndWaitForElement(engageLabel, driver);
            CCUtils.assertEquals(engageLabel.isDisplayed(), true);
            return true;
        }catch (Exception exception) {
            return false;
        }
    }
    public void clickOnSaveButton() {
        CCUtils.scrollPageViaElement(driver, createPrivacyPolicySaveButton);
        CCUtils.verifyAndWaitForElement(createPrivacyPolicySaveButton, driver);
        log("Clicking on Save Button");
        new Actions(driver).moveToElement(createPrivacyPolicySaveButton).pause(2000).click().build().perform();
        log("successfully clicked on save button");
    }

    public boolean verifyPolicyNameError() {
        try {
            CCUtils.scrollPageViaElement(driver, policyNameEmptyErrorMessage);
            CCUtils.verifyAndWaitForElement(policyNameEmptyErrorMessage, driver);
            log("Verifying whether Error Message present when policy name is empty");
            new Actions(driver).moveToElement(policyNameEmptyErrorMessage).build().perform();
            CCUtils.assertEquals(policyNameEmptyErrorMessage.isDisplayed(), true);
            log("Error message successfully displayed");
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public void verifyMaximumCharacterError() {
        CCUtils.scrollPageViaElement(driver, maximumCharacterError);
        CCUtils.verifyAndWaitForElement(maximumCharacterError, driver);
        log("Verifying whether Maximum character Error Message present when policy name is more than 50");
        new Actions(driver).moveToElement(maximumCharacterError).build().perform();
        CCUtils.assertEquals(maximumCharacterError.isDisplayed(), true);
        log("Error message successfully displayed");
    }

    public void enterPolicyName(String value) {
        CCUtils.scrollPageViaElement(driver, policyNameTextBox);
        CCUtils.verifyAndWaitForElement(policyNameTextBox, driver);
        log("Entering value for Policy Name");
        policyNameTextBox.clear();
        policyNameTextBox.sendKeys(value);
        log("Entered the value "+value+" on Policy Name");
    }

    public void updatePolicyName() {
        CCUtils.scrollPageViaElement(driver, policyNameTextBox);
        CCUtils.verifyAndWaitForElement(policyNameTextBox, driver);
        log("Updating value for Policy Name");
        policyNameTextBox.sendKeys("_Update");
        log("Updating the value on Policy Name");
    }

    public void verifyRadioButtonEnabled(String status) {
        if (status.equalsIgnoreCase("Draft")) {
            CCUtils.verifyAndWaitForElement(draftRadioButton, driver);
            new Actions(driver).moveToElement(draftRadioButton).build().perform();
            CCUtils.assertEquals(draftRadioButton.isSelected(), true);
            log(status + " Radio button is enabled");
        }
        else if(status.equalsIgnoreCase("Active")) {
            CCUtils.verifyAndWaitForElement(activeRadioButton, driver);
            new Actions(driver).moveToElement(activeRadioButton).build().perform();
            CCUtils.assertEquals(activeRadioButton.isSelected(), true);
            log(status + " Radio button is enabled");
        }
    }

    public void clickOnBackButton() {
        CCUtils.scrollPageViaElement(driver, privacyPolicyBackOrCancelButton);
        CCUtils.verifyAndWaitForElement(privacyPolicyBackOrCancelButton, driver);
        log("Clicking on Back Button");
        new Actions(driver).moveToElement(privacyPolicyBackOrCancelButton).pause(2000).click().build().perform();
        log("successfully clicked on Back button");
    }

    public void clickOnRadioButtonEnabled(String status) {
        if (status.equalsIgnoreCase("Draft") && !draftRadioButton.isSelected()) {
            CCUtils.verifyAndWaitForElement(draftRadioButton, driver);
            new Actions(driver).moveToElement(draftRadioButton).pause(2000).click().build().perform();
            log(status + " Radio button is selected");
        }
        else if(status.equalsIgnoreCase("Active") && !activeRadioButton.isSelected()) {
            CCUtils.verifyAndWaitForElement(activeRadioButton, driver);
            new Actions(driver).moveToElement(activeRadioButton).pause(2000).click().build().perform();
            log(status + " Radio button is selected");
        }
        else if(status.equalsIgnoreCase("Inactive") && !inactiveRadioButton.isSelected()) {
            CCUtils.verifyAndWaitForElement(inactiveRadioButton, driver);
            new Actions(driver).moveToElement(inactiveRadioButton).pause(2000).click().build().perform();
            log(status + " Radio button is selected");
        }
    }

    public void clickReturnToPage() {
        CCUtils.scrollPageViaElement(driver, returnToPage);
        CCUtils.verifyAndWaitForElement(returnToPage, driver);
        log("Clicking on Return to page");
        new Actions(driver).moveToElement(returnToPage).pause(3000).click().build().perform();
        log("Successfully clicked on Return to Page");
    }

    public void clickDisregardChanges() {
        CCUtils.scrollPageViaElement(driver, disregardChanges);
        CCUtils.verifyAndWaitForElement(disregardChanges, driver);
        log("Clicking on Disregard Changes");
        new Actions(driver).moveToElement(disregardChanges).pause(3000).click().build().perform();
        log("Successfully clicked on Disregard Changes");
    }

    public void verifyToggleOff() {
        log("Verifying Toggle button off");
        CCUtils.verifyAndWaitForElement(virtualVisitsToggleButton, driver);
        CCUtils.assertEquals(virtualVisitsToggleButton.isSelected(),false);
        log("Virtual Visits toggle off");
        CCUtils.verifyAndWaitForElement(payToggleButton, driver);
        CCUtils.assertEquals(payToggleButton.isSelected(),false);
        log("Pay toggle off");
        /*CCUtils.verifyAndWaitForElement(portalToggleButton, driver);
        CCUtils.assertEquals(portalToggleButton.isSelected(),false);
        log("Portal toggle off");*/
        CCUtils.verifyAndWaitForElement(pssToggleButton, driver);
        CCUtils.assertEquals(pssToggleButton.isSelected(),false);
        log("PSS toggle off");
        /*CCUtils.verifyAndWaitForElement(chatToggleButton, driver);
        CCUtils.assertEquals(chatToggleButton.isSelected(),false);
        log("Chat toggle off");*/
        CCUtils.verifyAndWaitForElement(engageToggleButton, driver);
        CCUtils.assertEquals(engageToggleButton.isSelected(),false);
        log("Engage toggle off");
    }

    public void clickOnLanguageTab(String language) {
        log("clicking on "+language+" Language tab");
        if(language.equalsIgnoreCase("English")) {
            CCUtils.verifyAndWaitForElement(privacyPolicyEnglishLanguageTab, driver);
            new Actions(driver).moveToElement(privacyPolicyEnglishLanguageTab).pause(2000).click().build().perform();
        }
        else if(language.equalsIgnoreCase("Spanish")) {
            CCUtils.verifyAndWaitForElement(privacyPolicySpanishLanguageTab, driver);
            new Actions(driver).moveToElement(privacyPolicySpanishLanguageTab).pause(2000).click().build().perform();
        }
        log("Successfully clicked on "+language+" Language tab");
    }

    public void verifyEnglishContent(String value) {
        log("Verifying English content of Privacy policy");
        CCUtils.verifyAndWaitForElement(englishContent, driver);
        CCUtils.assertEquals(englishContent.getText().trim().equalsIgnoreCase(value), true);
        log("Successfully verified English content of privacy policy");
    }

    public void verifySpanishContent(String value) {
        log("Verifying Spanish content of Privacy policy");
        CCUtils.verifyAndWaitForElement(spanishContent, driver);
        System.out.println(value);
        CCUtils.assertEquals(spanishContent.getText().trim().contains(value), true);
        log("Successfully verified Spanish content of privacy policy");
    }

    public void toggleSwitchClick(String solutionName) throws InterruptedException, AWTException {
        if (solutionName.equalsIgnoreCase("Virtual Visits")) {
            CCUtils.verifyAndWaitForElement(virtualVisitsToggleButton, driver);
            virtualVisitsToggleButton.click();
            log(solutionName + " toggle on");
        } else if (solutionName.equalsIgnoreCase("Pay")) {
            CCUtils.verifyAndWaitForElement(payToggleButton, driver);
            payToggleButton.click();
            log(solutionName + " toggle on");
        } else if (solutionName.equalsIgnoreCase("Portal")) {
            CCUtils.verifyAndWaitForElement(portalToggleButton, driver);
            portalToggleButton.click();
            log(solutionName + " toggle on");
        } else if (solutionName.equalsIgnoreCase("PSS")) {
            CCUtils.verifyAndWaitForElement(pssToggleButton, driver);
            pssToggleButton.click();
            log(solutionName + " toggle on");
        } else if (solutionName.equalsIgnoreCase("Chat")) {
            CCUtils.verifyAndWaitForElement(chatToggleButton, driver);
            chatToggleButton.click();
            log(solutionName + " toggle on");
        } else if (solutionName.equalsIgnoreCase("Engage")) {
            CCUtils.verifyAndWaitForElement(engageToggleButton, driver);
            engageToggleButton.click();
            log(solutionName + " toggle on");
        }
    }

    public void toggleOnAllSolutions() {
        CCUtils.verifyAndWaitForElementPresence(virtualVisitsToggleButton, driver);
        virtualVisitsToggleButton.click();
        payToggleButton.click();
        /*portalToggleButton.click();*/
        pssToggleButton.click();
        /*chatToggleButton.click();*/
        engageToggleButton.click();
    }

    public void verifyPolicyName(String policyName) {
        int flag = 0;
        for (WebElement webElement : policyList) {
            if (webElement.getText().contains(policyName)) {
                flag = 1;
                log(policyName + " is available in privacy policies section");
                break;
            }
        }
        if(flag == 0)
            log(policyName + " is not available in privacy policies section");
    }

    public void verifyPolicyKebabMenu(String policyName) {
        int flag = 0;
        for(WebElement eachElement : policyList) {
            if(eachElement.getText().contains(policyName)) {
                flag = 1;
                WebElement kebabMenu = driver.findElement(By.xpath("//span[contains(text(),' "+policyName+" ')]/ancestor::div[1]/following-sibling::div/following-sibling::div/following-sibling::div/button[@id='IconContainer']"));
                new Actions(driver).moveToElement(kebabMenu).build().perform();
                CCUtils.assertEquals(kebabMenu.isDisplayed(), true);
                log(policyName + " kebab Menu is available in privacy policies section");
                break;
            }
        }
        if(flag == 0)
            log(policyName + " Kebab Menu is not available in privacy policies section");
    }

    public void clickPolicyKebabMenu(String policyName) {
        int flag = 0;
        for(WebElement eachElement : policyList) {
            if(eachElement.getText().contains(policyName)) {
                flag = 1;
                WebElement kebabMenu = driver.findElement(By.xpath("//span[contains(text(),' "+policyName+" ')]/ancestor::div[1]/following-sibling::div/following-sibling::div/following-sibling::div/button[@id='IconContainer']"));
                new Actions(driver).moveToElement(kebabMenu).pause(2000).click().build().perform();
                log(policyName + " kebab Menu is Clicked in privacy policies section");
                break;
            }
        }
        if(flag == 0)
            log(policyName + " Kebab Menu is not available in privacy policies section");
    }

    public void verifySolutionLabel(String labelName) {
        if(labelName.equalsIgnoreCase("Virtual Visits")) {
            CCUtils.verifyAndWaitForElement(previewVirtualVisitsLabel, driver);
            CCUtils.assertEquals(previewVirtualVisitsLabel.isDisplayed(), true);
        }
        else if(labelName.equalsIgnoreCase("Pay")) {
            CCUtils.verifyAndWaitForElement(previewPayLabel, driver);
            CCUtils.assertEquals(previewPayLabel.isDisplayed(), true);
        }
        else if(labelName.equalsIgnoreCase("Portal")) {
            CCUtils.verifyAndWaitForElement(previewPortalLabel, driver);
            CCUtils.assertEquals(previewPortalLabel.isDisplayed(), true);
        }
        else if(labelName.equalsIgnoreCase("PSS")) {
            CCUtils.verifyAndWaitForElement(previewPSSLabel, driver);
            CCUtils.assertEquals(previewPSSLabel.isDisplayed(), true);
        }
        else if(labelName.equalsIgnoreCase("Chat")) {
            CCUtils.verifyAndWaitForElement(previewChatLabel, driver);
            CCUtils.assertEquals(previewChatLabel.isDisplayed(), true);
        }
        else if(labelName.equalsIgnoreCase("Engage")) {
            CCUtils.verifyAndWaitForElement(previewEngageLabel, driver);
            CCUtils.assertEquals(previewEngageLabel.isDisplayed(), true);
        }
    }

    public void verifyKebabMenuOptions(String option) {
        if(option.equalsIgnoreCase("Preview")) {
            CCUtils.verifyAndWaitForElementPresence(kebabPreview, driver);
            CCUtils.assertEquals(kebabPreview.isDisplayed(), true);
        }
        else if(option.equalsIgnoreCase("Edit")) {
            CCUtils.verifyAndWaitForElement(kebabEdit, driver);
            CCUtils.assertEquals(kebabEdit.isDisplayed(), true);
        }
        else if(option.equalsIgnoreCase("Delete")) {
            CCUtils.verifyAndWaitForElement(kebabDelete, driver);
            CCUtils.assertEquals(kebabDelete.isDisplayed(), true);
        }
    }

    public void clickKebabMenuOptions(String option) {
        if(option.equalsIgnoreCase("Preview")) {
            CCUtils.verifyAndWaitForElement(kebabPreview, driver);
            new Actions(driver).moveToElement(kebabPreview).pause(2000).click().build().perform();
        }
        else if(option.equalsIgnoreCase("Edit")) {
            CCUtils.verifyAndWaitForElement(kebabEdit, driver);
            new Actions(driver).moveToElement(kebabEdit).pause(2000).click().build().perform();
        }
        else if(option.equalsIgnoreCase("Delete")) {
            CCUtils.verifyAndWaitForElement(kebabDelete, driver);
            new Actions(driver).moveToElement(kebabDelete).pause(2000).click().build().perform();
        }
    }

    public void verifyPreviewEdit() {
        CCUtils.verifyAndWaitForElementPresence(previewEdit, driver);
        CCUtils.scrollPageViaElement(driver, previewEdit);
        log("Verifying Edit button in preview");
        CCUtils.assertEquals(previewEdit.isDisplayed(), true);
        log("Successfully verified Edit button in preview");
    }

    public void clickPreviewEdit() {
        CCUtils.verifyAndWaitForElementPresence(previewEdit, driver);
        CCUtils.scrollPageViaElement(driver, previewEdit);
        log("Clicking Edit button in preview");
        new Actions(driver).moveToElement(previewEdit).pause(2000).click().build().perform();
        log("Successfully clicked on Edit button in preview");
    }

    public void updateLanguageContent(String language) {
        if(language.equalsIgnoreCase("English"))
            englishContent.sendKeys(" updated");
        else if(language.equalsIgnoreCase("Spanish"))
            spanishContent.sendKeys(" updated");
    }

    public void verifyEditSaveButton() {
        CCUtils.scrollPageViaElement(driver, editPrivacyPolicySaveButton);
        CCUtils.verifyAndWaitForElement(editPrivacyPolicySaveButton, driver);
        log("Verifying whether Save Button present or not");
        new Actions(driver).moveToElement(editPrivacyPolicySaveButton).build().perform();
        CCUtils.assertEquals(editPrivacyPolicySaveButton.isDisplayed(), true);
        log("Save Button successfully displayed");
    }

    public void verifyingEditSaveButton() {
        try {
            if (previewEdit.isDisplayed())
                log("Edit button in preview displayed successfully");
        } catch (Exception e) {
            log("Edit button is not available in preview");
        }
    }

    public void clickOnEditSaveButton() {
        CCUtils.scrollPageViaElement(driver, editPrivacyPolicySaveButton);
        CCUtils.verifyAndWaitForElement(editPrivacyPolicySaveButton, driver);
        log("Clicking on Save Button");
        new Actions(driver).moveToElement(editPrivacyPolicySaveButton).pause(2000).click().build().perform();
        log("successfully clicked on save button");
    }

    public void verifyStatusInPrivacyPoliciesSection(String policyName, String status) {
        int flag = 0;
        for (WebElement eachElement : policyList) {
            if (eachElement.getText().contains(policyName) && status.equalsIgnoreCase("DRAFT")) {
                flag = 1;
                WebElement draftStatus = driver.findElement(By.xpath("//span[contains(text(),' " + policyName + " ')]/ancestor::div[1]/following-sibling::div/following-sibling::div/button/span/i[text()='DRAFT']"));
                new Actions(driver).moveToElement(draftStatus).build().perform();
                CCUtils.assertEquals(draftStatus.isDisplayed(), true);
                log("Draft Status is available for " + policyName + " in privacy policies section");
                break;
            } else if (eachElement.getText().contains(policyName) && status.equalsIgnoreCase("INACTIVE")) {
                flag = 1;
                WebElement inactiveStatus = driver.findElement(By.xpath("//span[contains(text(),' " + policyName + " ')]/ancestor::div[1]/following-sibling::div/following-sibling::div/span[normalize-space(text())='INACTIVE']"));
                new Actions(driver).moveToElement(inactiveStatus).build().perform();
                CCUtils.assertEquals(inactiveStatus.isDisplayed(), true);
                log("Inactive Status is available for " + policyName + " in privacy policies section");
                break;
            }
            else if (eachElement.getText().contains(policyName) && status.equalsIgnoreCase("ACTIVE")) {
                flag = 1;
                WebElement activeStatus = driver.findElement(By.xpath("//span[contains(text(),' "+policyName+" ')]/ancestor::div[1]/following-sibling::div/following-sibling::div/span[normalize-space(text())='ACTIVE']"));
                new Actions(driver).moveToElement(activeStatus).build().perform();
                CCUtils.assertEquals(activeStatus.isDisplayed(), true);
                log("Active Status is available for " + policyName + " in privacy policies section");
                break;
            }
        }
        if(flag == 0)
            log(status + " status is not available for "+policyName +" in privacy policies section");
    }

    public void verifydeleteConfirmationMessage() {
        CCUtils.scrollPageViaElement(driver, deleteConfirmationMessage);
        CCUtils.verifyAndWaitForElement(deleteConfirmationMessage, driver);
        log("Verifying whether Delete Confirmation message is present or not");
        new Actions(driver).moveToElement(deleteConfirmationMessage).build().perform();
        CCUtils.assertEquals(deleteConfirmationMessage.isDisplayed(), true);
        log("Delete confirmation message successfully displayed");
    }

    public void clickOnYesDeleteButton() {
        CCUtils.scrollPageViaElement(driver, yesDelete);
        CCUtils.verifyAndWaitForElement(yesDelete, driver);
        log("Clicking on Yes, Delete Button");
        new Actions(driver).moveToElement(yesDelete).pause(2000).click().build().perform();
        log("successfully clicked on Yes, Delete button");
    }

    public void clickPurchasedSolutionsExpandButton() {
        CCUtils.scrollPageViaElement(driver, expandButtonForPurchaseSolutions);
        CCUtils.verifyAndWaitForElement(expandButtonForPurchaseSolutions, driver);
        log("Clicking on Purchased solutions Expand Button");
        new Actions(driver).moveToElement(expandButtonForPurchaseSolutions).pause(3000).click().build().perform();
        log("Successfully clicked on Purchased Solutions Expand button");
    }

    public void clickOnYesContinueButton() {
        CCUtils.scrollPageViaElement(driver, yesContinueButton);
        CCUtils.verifyAndWaitForElement(yesContinueButton, driver);
        log("Clicking on Yes, Continue Button");
        new Actions(driver).moveToElement(yesContinueButton).pause(2000).click().build().perform();
        log("successfully clicked on Yes, Continue button");
    }

    public void verifyNoActivePopUpMessage() {
        CCUtils.scrollPageViaElement(driver, noActivePopUpMessage);
        CCUtils.verifyAndWaitForElement(noActivePopUpMessage, driver);
        log("Verifying whether No Active Pop up Message present when policy name is more than 50");
        new Actions(driver).moveToElement(noActivePopUpMessage).build().perform();
        CCUtils.assertEquals(noActivePopUpMessage.isDisplayed(), true);
        log("Pop Up message successfully displayed");
    }

    public void clickOnCancelButton() {
        CCUtils.scrollPageViaElement(driver, cancelPopUp);
        CCUtils.verifyAndWaitForElement(cancelPopUp, driver);
        log("Clicking on Cancel Button");
        new Actions(driver).moveToElement(cancelPopUp).pause(2000).click().build().perform();
        log("successfully clicked on Cancel button");
    }

    public void clickOnClearButton() {
        CCUtils.scrollPageViaElement(driver, clearButton);
        CCUtils.verifyAndWaitForElement(clearButton, driver);
        log("Clicking on Clear Button");
        new Actions(driver).moveToElement(clearButton).pause(2000).click().build().perform();
        log("successfully clicked on Clear button");
    }

    public void verifySolutionMissingErrorMessage() {
        CCUtils.scrollPageViaElement(driver, solutionMissingError);
        CCUtils.verifyAndWaitForElement(solutionMissingError, driver);
        log("Verifying whether solution missing error Message present or not");
        new Actions(driver).moveToElement(solutionMissingError).build().perform();
        CCUtils.assertEquals(solutionMissingError.isDisplayed(), true);
        log("Solution Missing error message successfully displayed");
    }

    public void clickOnHideInactive() {
        CCUtils.scrollPageViaElement(driver, hideInactive);
        CCUtils.verifyAndWaitForElement(hideInactive, driver);
        log("Clicking on hide inactive");
        new Actions(driver).moveToElement(hideInactive).pause(2000).click().build().perform();
        log("successfully clicked on Hide Inactive");
    }

    public void clickOnShowInactiveButton() {
        CCUtils.scrollPageViaElement(driver, showInactive);
        CCUtils.verifyAndWaitForElement(showInactive, driver);
        log("Clicking on Show Inactive");
        new Actions(driver).moveToElement(showInactive).pause(2000).click().build().perform();
        log("successfully clicked on Show Inactive");
    }

    public void verifyDisplayingInactiveAndDraftPolicyList(int activeAvailable) {
        if(activeAvailable == 1)
            if(policyList.size() > 1)
                log("Drafts and Inactive are displaying in the Privacy Policies section at practice level and It is in show Inactive");
        else
            log("No Drafts and Inactive are displayed and It may be in hide inactive or not added respective privacy policies");
        else
        if(policyList.size() > 0)
            log("Drafts and Inactive are displaying in the Privacy Policies section at practice level and It is in show Inactive");
        else
            log("No Drafts and Inactive are displayed and It may be in hide inactive or not added respective privacy policies");
    }

    public void verifyToolTip(String status) {
        if(status.equalsIgnoreCase("Active")) {
            CCUtils.verifyAndWaitForElement(activeToolTip, driver);
            CCUtils.assertEquals(activeToolTip.isDisplayed(), true);
            log("Successfully verified the tool tip of " + status);
        }
        else if(status.equalsIgnoreCase("Inactive")) {
            CCUtils.verifyAndWaitForElement(inactiveToolTip, driver);
            CCUtils.assertEquals(inactiveToolTip.isDisplayed(), true);
            log("Successfully verified the tool tip of " + status);
        }
        else if(status.equalsIgnoreCase("Draft")) {
            CCUtils.verifyAndWaitForElement(draftToolTip, driver);
            CCUtils.assertEquals(draftToolTip.isDisplayed(), true);
            log("Successfully verified the tool tip of " + status);
        }
    }

    public void emptyLanguageContent(String language) {
        if(language.equalsIgnoreCase("English")) {
            CCUtils.verifyAndWaitForElement(englishContent, driver);
            englishContent.clear();
            CCUtils.verifyAndWaitForElement(languageContentTextArea, driver);
            new Actions(driver).moveToElement(languageContentTextArea).pause(2000).click().build().perform();
            languageContentTextArea.sendKeys(Keys.SPACE);
            languageContentTextArea.sendKeys(Keys.BACK_SPACE);
        }
        else if(language.equalsIgnoreCase("Spanish")) {
            CCUtils.verifyAndWaitForElement(spanishContent, driver);
            spanishContent.clear();
            CCUtils.verifyAndWaitForElement(languageContentTextArea, driver);
            new Actions(driver).moveToElement(languageContentTextArea).pause(2000).click().build().perform();
            languageContentTextArea.sendKeys(Keys.SPACE);
            languageContentTextArea.sendKeys(Keys.BACK_SPACE);
        }
    }

    public void verifyMustAddLanguageContentErrorMessage() {
        CCUtils.scrollPageViaElement(driver, mustAddLanguageError);
        CCUtils.verifyAndWaitForElement(mustAddLanguageError, driver);
        log("Verifying whether Must add Language content error Message present or not");
        new Actions(driver).moveToElement(mustAddLanguageError).build().perform();
        CCUtils.assertEquals(mustAddLanguageError.isDisplayed(), true);
        log("Must add Language content error message successfully displayed");
    }

    public void clickOnSystemArrow(String systemName) {
        WebElement systemArrow = driver.findElement(By.xpath("//button[@aria-label='toggle "+systemName+"']/span/mat-icon[normalize-space(text())='keyboard_arrow_right']"));
        CCUtils.verifyAndWaitForElement(systemArrow, driver);
        log("clicking on system level");
        new Actions(driver).moveToElement(systemArrow).click().build().perform();
        log("Successfully system has been viewed");
    }

    public void clickOnEnterpriseArrow(String enterpriseName) {
        WebElement enterpriseArrow = driver.findElement(By.xpath("//button[@aria-label='toggle "+enterpriseName+"']/span/mat-icon[normalize-space(text())='keyboard_arrow_right']"));
        CCUtils.verifyAndWaitForElement(enterpriseArrow, driver);
        log("clicking on Enterprise level");
        new Actions(driver).moveToElement(enterpriseArrow).click().build().perform();
        log("Successfully Enterprise has been viewed");
    }

    public void clickOnPracticeLevel(String practiceName) {
        WebElement practiceLevel = driver.findElement(By.xpath("//span[normalize-space(text())='"+practiceName+"']"));
        CCUtils.verifyAndWaitForElement(practiceLevel, driver);
        log("clicking on Practice level");
        new Actions(driver).moveToElement(practiceLevel).click().build().perform();
        log("Successfully Practice has been viewed");
    }
}
