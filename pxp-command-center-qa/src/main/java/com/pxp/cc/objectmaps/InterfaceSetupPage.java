package com.pxp.cc.objectmaps;

import com.pxp.cc.util.CCUtils;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
@Setter

public class InterfaceSetupPage extends CCMainPage {

    public InterfaceSetupPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[text()='Interface Setup']")
    private WebElement interfaceSetupLabel;

    @FindBy(xpath = "//span[normalize-space(text())='+ New']")
    private WebElement newButtonForInterfaceSetup;

    @FindBy(xpath = "//div[text()='Interface Setup']/../following-sibling::div/div/span/following-sibling::button/mat-icon[normalize-space(text())='keyboard_arrow_down']")
    private WebElement interfaceSetupExpandButton;

    @FindBy(xpath = "//div[text()='Interface Setup']/../following-sibling::div/div/span/following-sibling::button/mat-icon[normalize-space(text())='keyboard_arrow_up']")
    private WebElement interfaceSetupCollapseButton;

    @FindBy(xpath = "//span[text()='No interfaces setup.']")
    private WebElement noInterfaceSetup;

    @FindBy(xpath = "//div[text()='Interface Setup']/../following-sibling::div/div/span/following-sibling::button/mat-icon[@mattooltip='Expand']")
    private WebElement interfaceSetupExpandTooltip;

    @FindBy(xpath = "//div[text()='Interface Setup']/../following-sibling::div/div/span/following-sibling::button/mat-icon[@mattooltip='Collapse']")
    private WebElement interfaceSetupCollapseTooltip;

    @FindBy(xpath = "//h1[text()='New Interface']")
    private WebElement newInterfaceLabel;

    @FindBy(xpath = "//label[text()='External System *']")
    private WebElement externalSystemLabel;

    @FindBy(xpath = "//ng-select[@formcontrolname='externalSystem']")
    private WebElement externalSystemComboBox;

    @FindBy(xpath = "//span[@class='ng-arrow']")
    private WebElement externalSystemComboBoxArrow;

    @FindBy(xpath = "//label[normalize-space(text())='Status']")
    private WebElement statusLabel;

    @FindBy(xpath = "//label[normalize-space(text())='Active']")
    private WebElement activeLabel;

    @FindBy(xpath = "//label[normalize-space(text())='Inactive']")
    private WebElement inactiveLabel;

    @FindBy(xpath = "//span[normalize-space(text())='Cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//button[normalize-space(text())='Save']")
    private WebElement saveButton;

    @FindBy(xpath = "//span[normalize-space(text())='CANCEL']")
    private WebElement cancelButtonOnEdit;

    @FindBy(xpath = "//button[normalize-space(text())='SAVE']")
    private WebElement saveButtonOnEdit;

    @FindBy(xpath = "//label[text()='Active']/preceding::input[@type='radio']")
    private WebElement activeRadioButton;

    @FindBy(xpath = "//label[text()='Inactive']/../preceding::input[@type='radio']/following::input[@type='radio']")
    private WebElement inactiveRadioButton;

    @FindBy(xpath = "//span[normalize-space(text())='MedfusionREST - NextGen']")
    private WebElement medfusionRest_Nextgen;

    @FindBy(xpath = "//span[contains(text(),'NextGen Enterprise ID')]")
    private WebElement nextgenEnterpriseId;

    @FindBy(xpath = "//span[contains(text(),'API Site ID')]")
    private WebElement apiSiteId;

    @FindBy(xpath = "//span[contains(text(),'NextGen Practice ID')]")
    private WebElement nextgenPracticeId;

    @FindBy(xpath = "//input[@aria-label='NextGen Enterprise ID']")
    private WebElement nextgenEnterpriseIdTextBox;

    @FindBy(xpath = "//input[@aria-label='API Site ID']")
    private WebElement apiSiteIdTextBox;

    @FindBy(xpath = "//input[@aria-label='NextGen Practice ID']")
    private WebElement nextgenPracticeIdTextBox;

    @FindBy(xpath = "//div[normalize-space(text())='External System']")
    private WebElement externalSystemLabelOnInterfaceSetupSection;

    @FindBy(xpath = "//div[normalize-space(text())='Status']")
    private WebElement statusLabelOnInterfaceSetupSection;

    @FindBy(xpath = "//span[normalize-space(text())='MedfusionREST - NextGen']")
    private WebElement medfusionRest_NextgenLabel;

    @FindBy(xpath = "//span[normalize-space(text())='MedfusionREST - NextGen']/../following-sibling::div/../following-sibling::td/div/div/span/mat-icon[text()='more_vert']")
    private WebElement medfusionRest_NextgenKebabMenu;

    @FindBy(xpath = "//tr/td/div/button/following-sibling::span")
    private List<WebElement> externalSystemList;

    @FindBy(xpath = "//button[normalize-space(text())='Edit']")
    private WebElement editButtonOnKebabMenu;

    @FindBy(xpath = "//button[normalize-space(text())='Delete']")
    private WebElement deleteButtonOnKebabMenu;

    @FindBy(xpath = "//div[normalize-space(text())='Inactive']/../preceding::input[@type='radio']/following::input[@type='radio']/following-sibling::div")
    private WebElement inactiveRadioButtonOnEdit;

    @FindBy(xpath = "//div[normalize-space(text())='Active']/preceding::input[@type='radio']")
    private WebElement activeRadioButtonOnEdit;

    public void clickOnExpandButtonOfExternalSystem(String externalSystemName) {
        int flag = 0;
        for (int i = 0; i < externalSystemList.size(); i++) {
            if (externalSystemList.get(i).getText().contains(externalSystemName)) {
                flag = 1;
                int j = i + 1;
                CCUtils.verifyAndWaitForElementPresence(driver.findElement(By.xpath("(//tbody[@role='rowgroup']/tr)["+j+"]/td/div/button")), driver);
                //WebElement expandButtonOfExternalSystem = driver.findElement(By.xpath("(//span[normalize-space(text())='" + externalSystemName + "']/preceding::mat-icon[normalize-space(text())='keyboard_arrow_right'])[" + j + "]"));
                WebElement expandButtonOfExternalSystem = driver.findElement(By.xpath("(//tbody[@role='rowgroup']/tr)["+j+"]/td/div/button"));
                CCUtils.verifyAndWaitForElement(expandButtonOfExternalSystem, driver);
                new Actions(driver).moveToElement(expandButtonOfExternalSystem).build().perform();
                javascriptClick(expandButtonOfExternalSystem);
                log(externalSystemName + " Expand Button clicked successfully");
                break;
            }
        }
        if (flag == 0)
            log(externalSystemName + " Expand Button is not available in Interface section");
    }

    public void verifyElementsInInterfaceSetUpSection() {
        CCUtils.verifyAndWaitForElement(interfaceSetupLabel, driver);
        CCUtils.scrollPageViaElement(driver, interfaceSetupLabel);
        log("Verifying Interface setup Label present or not");
        CCUtils.assertEquals(interfaceSetupLabel.isDisplayed(), true);
        log("Interface Setup Label displayed successfully");
        CCUtils.verifyAndWaitForElement(newButtonForInterfaceSetup, driver);
        CCUtils.scrollPageViaElement(driver, newButtonForInterfaceSetup);
        new Actions(driver).moveToElement(newButtonForInterfaceSetup).build().perform();
        log("Verifying New + button for Interface setup present or not");
        CCUtils.assertEquals(newButtonForInterfaceSetup.isDisplayed(), true);
        log("New + Button for Interface Setup displayed successfully");
        CCUtils.verifyAndWaitForElement(interfaceSetupExpandButton, driver);
        CCUtils.scrollPageViaElement(driver, interfaceSetupExpandButton);
        log("Verifying Interface Setup Expand button present or not");
        CCUtils.assertEquals(interfaceSetupExpandButton.isDisplayed(), true);
        log("Interface setup Expand button displayed successfully");
        new Actions(driver).moveToElement(interfaceSetupExpandButton).build().perform();
        CCUtils.verifyAndWaitForElement(interfaceSetupExpandTooltip, driver);
        log("Verifying Interface Setup Expand button tooltip present or not");
        CCUtils.assertEquals(interfaceSetupExpandTooltip.isDisplayed(), true);
        log("Interface setup Expand button tooltip displayed successfully");
        new Actions(driver).moveToElement(interfaceSetupExpandButton).click().build().perform();
        CCUtils.verifyAndWaitForElement(interfaceSetupCollapseButton, driver);
        log("Verifying Interface Setup Collapse button present or not");
        CCUtils.assertEquals(interfaceSetupCollapseButton.isDisplayed(), true);
        log("Interface setup Collapse button displayed successfully");
        new Actions(driver).moveToElement(interfaceSetupCollapseButton).build().perform();
        CCUtils.verifyAndWaitForElement(interfaceSetupCollapseTooltip, driver);
        log("Verifying Interface Setup Collapse button tooltip present or not");
        CCUtils.assertEquals(interfaceSetupCollapseTooltip.isDisplayed(), true);
        CCUtils.verifyAndWaitForElement(noInterfaceSetup, driver);
        CCUtils.scrollPageViaElement(driver, noInterfaceSetup);
        log("Verifying No Interface setup Label present or not");
        CCUtils.assertEquals(noInterfaceSetup.isDisplayed(), true);
        log("NO Interface Setup Label displayed successfully");
    }

    public void verifyElementsOnNewInterface() {
        CCUtils.verifyAndWaitForElement(newButtonForInterfaceSetup, driver);
        new Actions(driver).moveToElement(newButtonForInterfaceSetup).click().build().perform();
        log("successfully clicked on New + button");
        CCUtils.verifyAndWaitForElement(newInterfaceLabel, driver);
        log("Verifying New Interface Label present or not");
        CCUtils.assertEquals(newInterfaceLabel.isDisplayed(), true);
        log("New Interface label displayed successfully");
        CCUtils.verifyAndWaitForElement(externalSystemLabel, driver);
        log("Verifying External System Label present or not");
        CCUtils.assertEquals(externalSystemLabel.isDisplayed(), true);
        log("External System label displayed successfully");
        CCUtils.verifyAndWaitForElement(externalSystemComboBox, driver);
        log("Verifying External system combo box present or not");
        CCUtils.assertEquals(externalSystemComboBox.isDisplayed(), true);
        log("External system combo box displayed successfully");
        CCUtils.verifyAndWaitForElement(statusLabel, driver);
        log("Verifying Status Label present or not");
        CCUtils.assertEquals(statusLabel.isDisplayed(), true);
        log("Status label displayed successfully");
        CCUtils.verifyAndWaitForElement(activeLabel, driver);
        log("Verifying Active Label present or not");
        CCUtils.assertEquals(activeLabel.isDisplayed(), true);
        log("Active Label displayed successfully");
        CCUtils.verifyAndWaitForElement(inactiveLabel, driver);
        log("Verifying Inactive Label present or not");
        CCUtils.assertEquals(inactiveLabel.isDisplayed(), true);
        log("Inactive label displayed successfully");
        CCUtils.verifyAndWaitForElement(cancelButton, driver);
        log("Verifying Cancel button present or not");
        CCUtils.assertEquals(cancelButton.isDisplayed(), true);
        log("Cancel button displayed successfully");
        CCUtils.assertEquals(cancelButton.isEnabled(), true);
        log("Cancel button is enabled for the first time in New Interface");
        CCUtils.verifyAndWaitForElement(saveButton, driver);
        log("Verifying Save button present or not");
        CCUtils.assertEquals(saveButton.isDisplayed(), true);
        log("Save button displayed successfully");
        if(driver.findElement(By.xpath("//div[text()='Select']")).getText().equalsIgnoreCase("Select")) {
            CCUtils.assertEquals(saveButton.isEnabled(), false);
            log("Save button is disabled for the first time in New Interface");
        }
        CCUtils.verifyAndWaitForElement(activeRadioButton, driver);
        log("Verifying active radio button present or not");
        CCUtils.assertEquals(activeRadioButton.isDisplayed(), true);
        log("Active Radio button displayed successfully");
        CCUtils.verifyAndWaitForElement(inactiveRadioButton, driver);
        log("Verifying Inactive radio button present or not");
        CCUtils.assertEquals(inactiveRadioButton.isDisplayed(), true);
        log("Inactive Radio button displayed successfully");
    }

    public void verifyRadioButtonEnabled(String status) {
        if(status.equalsIgnoreCase("Active")) {
            if (activeRadioButton.isSelected())
                log("Active Radio button is selected");
            else
                driver.findElement(By.xpath("//input[@value='false']")).click();
        }
        else if(status.equalsIgnoreCase("Inactive")) {
            if (inactiveRadioButton.isSelected())
                log("Inactive Radio button is selected");
            else
                driver.findElement(By.xpath("//input[@value='false']")).click();
        }
        log(status +" Radio button is displayed and selected");
    }

    public void verifyRadioButtonEnabledOnEdit(String status) {
        if(status.equalsIgnoreCase("Active")) {
            if (activeRadioButtonOnEdit.isSelected())
                log("Active Radio button is selected");
            else {
                CCUtils.verifyElementToBeClickable(driver.findElement(By.xpath("//input[@type='radio' and @value='false']")), driver);
                driver.findElement(By.xpath("//input[@type='radio' and @value='false']")).click();
            }

        }
        else if(status.equalsIgnoreCase("Inactive")) {
            if (inactiveRadioButtonOnEdit.isSelected())
                log("Inactive Radio button is selected");
            else {
                CCUtils.verifyElementToBeClickable(driver.findElement(By.xpath("//input[@type='radio' and @value='false']")), driver);
                driver.findElement(By.xpath("//input[@type='radio' and @value='false']")).click();
            }
        }
        log(status +" Radio button is displayed and selected");
    }


    public void clickOnNewButton() {
        CCUtils.verifyAndWaitForElement(newButtonForInterfaceSetup, driver);
        CCUtils.scrollPageViaElement(driver, newButtonForInterfaceSetup);
        new Actions(driver).moveToElement(newButtonForInterfaceSetup).click().build().perform();
        log("Successfully clicked on New + Button");
    }

    public void clickOnExternalSystemComboBox() {
        CCUtils.verifyAndWaitForElement(externalSystemComboBoxArrow, driver);
        CCUtils.scrollPageViaElement(driver, externalSystemComboBoxArrow);
        new Actions(driver).moveToElement(externalSystemComboBoxArrow).click().build().perform();
        log("Successfully clicked on External System Combo Box");
    }

    public void clickOnCancelButton() {
        CCUtils.verifyAndWaitForElement(cancelButton, driver);
        CCUtils.scrollPageViaElement(driver, cancelButton);
        new Actions(driver).moveToElement(cancelButton).click().build().perform();
        log("Successfully clicked on Cancel Button");
    }

    public void clickOnSaveButton() {
        CCUtils.verifyAndWaitForElement(saveButton, driver);
        CCUtils.scrollPageViaElement(driver, saveButton);
        new Actions(driver).moveToElement(saveButton).click().build().perform();
        log("Successfully clicked on Save Button");
    }

    public void clickOnCancelButtonOnEdit() {
        CCUtils.verifyAndWaitForElement(cancelButtonOnEdit, driver);
        CCUtils.scrollPageViaElement(driver, cancelButtonOnEdit);
        new Actions(driver).moveToElement(cancelButtonOnEdit).click().build().perform();
        log("Successfully clicked on Cancel Button");
    }

    public void clickOnSaveButtonOnEdit() {
        CCUtils.verifyAndWaitForElement(saveButtonOnEdit, driver);
        CCUtils.scrollPageViaElement(driver, saveButtonOnEdit);
        new Actions(driver).moveToElement(saveButtonOnEdit).click().build().perform();
        log("Successfully clicked on Save Button");
    }

    public void enterValue(WebElement element, String value) {
        CCUtils.verifyAndWaitForElement(element, driver);
        element.sendKeys(value);
    }

    public void updateValue(WebElement element, String value) {
        CCUtils.verifyAndWaitForElement(element, driver);
        element.clear();
        element.sendKeys(value);
    }

    public void verifyElementsForExternalSystem(String externalSystemName) {
        if(externalSystemName.equalsIgnoreCase("MedfusionREST -NextGen")) {
            CCUtils.verifyAndWaitForElement(nextgenEnterpriseId, driver);
            CCUtils.assertEquals(nextgenEnterpriseId.isDisplayed(), true);
            log("NextGen Enterprise Id label displayed successfully");
            CCUtils.verifyAndWaitForElement(nextgenEnterpriseIdTextBox, driver);
            CCUtils.assertEquals(nextgenEnterpriseIdTextBox.isDisplayed(), true);
            log("NextGen Enterprise Id Text box displayed successfully");
            CCUtils.verifyAndWaitForElement(apiSiteId, driver);
            CCUtils.assertEquals(apiSiteId.isDisplayed(), true);
            log("API Site Id label displayed successfully");
            CCUtils.verifyAndWaitForElement(apiSiteIdTextBox, driver);
            CCUtils.assertEquals(apiSiteIdTextBox.isDisplayed(), true);
            log("Api Site Id Text box displayed successfully");
            CCUtils.verifyAndWaitForElement(nextgenPracticeId, driver);
            CCUtils.assertEquals(nextgenPracticeId.isDisplayed(), true);
            log("NextGen Practice Id label displayed successfully");
            CCUtils.verifyAndWaitForElement(nextgenPracticeIdTextBox, driver);
            CCUtils.assertEquals(nextgenPracticeIdTextBox.isDisplayed(), true);
            log("NextGen Practice Id Text box displayed successfully");
        }
    }

    public void verifyKebabEdit() {
        CCUtils.verifyAndWaitForElement(editButtonOnKebabMenu, driver);
        CCUtils.scrollPageViaElement(driver, editButtonOnKebabMenu);
        log("verifying Edit button in kebab menu present or not");
        CCUtils.assertEquals(editButtonOnKebabMenu.isDisplayed(), true);
        log("Edit button in kebab menu displayed successfully");
    }

    public void verifyKebabDelete() {
        CCUtils.verifyAndWaitForElement(deleteButtonOnKebabMenu, driver);
        CCUtils.scrollPageViaElement(driver, deleteButtonOnKebabMenu);
        log("verifying Delete button in kebab menu present or not");
        CCUtils.assertEquals(deleteButtonOnKebabMenu.isDisplayed(), true);
        log("Delete button in kebab menu displayed successfully");
    }

    public void clickOnKebabEdit() {
        CCUtils.verifyAndWaitForElement(editButtonOnKebabMenu, driver);
        CCUtils.scrollPageViaElement(driver, editButtonOnKebabMenu);
        log("Clicking on Edit button in kebab menu");
        new Actions(driver).moveToElement(editButtonOnKebabMenu).click().build().perform();
        log("Edit button in kebab menu Clicked successfully");
    }


    public void selectExternalSystemInDropDown(String externalSystem) {
        WebElement externalSystemValue = driver.findElement(By.xpath("//span[contains(text(),'"+externalSystem+"')]"));
        CCUtils.verifyAndWaitForElement(externalSystemValue, driver);
        externalSystemValue.click();
        log("Clicked on : " + externalSystem);
    }

    public void verifyInterfaceSetupKebabMenu(String externalSystemName) {
        int flag = 0;
        for(WebElement eachElement : externalSystemList) {
            if(eachElement.getText().contains(externalSystemName)) {
                flag = 1;
                WebElement kebabMenu = driver.findElement(By.xpath("//span[normalize-space(text())='"+externalSystemName+"']/../following-sibling::div/../following-sibling::td/div/div/span/mat-icon[text()='more_vert']"));
                new Actions(driver).moveToElement(kebabMenu).build().perform();
                CCUtils.assertEquals(kebabMenu.isDisplayed(), true);
                log(externalSystemName + " kebab Menu is available in Interface section");
                break;
            }
        }
        if(flag == 0)
            log(externalSystemName + " Kebab Menu is not available in Interface section");
    }

    public void verifyAutoGeneratedFields(String externalSystemName, List<String> fieldName) {
        int flag = 0;
        for(WebElement eachElement : externalSystemList) {
            if(eachElement.getText().contains(externalSystemName)) {
                flag = 1;
                for(String each : fieldName) {
                    CCUtils.verifyAndWaitForElement(driver.findElement(By.xpath("//span[normalize-space(text())='" + externalSystemName + "']/../following-sibling::div/div/div/div/div[normalize-space(text())='" + each + "']/mat-icon[@mattooltip='Automatically Generated']")), driver);
                    WebElement eachField = driver.findElement(By.xpath("//span[normalize-space(text())='" + externalSystemName + "']/../following-sibling::div/div/div/div/div[normalize-space(text())='" + each + "']/mat-icon[@mattooltip='Automatically Generated']"));
                    new Actions(driver).moveToElement(eachField).build().perform();
                    CCUtils.assertEquals(eachField.isDisplayed(), true);
                    log(each + " is auto generated");
                }
                log(externalSystemName + " kebab Menu is available in Interface section");
                break;
            }
        }
        if(flag == 0)
            log(externalSystemName + " Kebab Menu is not available in Interface section");
    }

    public void verifyEditableContent(List<String> fieldName, List<String> value) {
        for(int i=0; i<fieldName.size();i++) {
            int flag = 0;
            WebElement each = driver.findElement(By.xpath("//span[text()='MedfusionREST - NextGen']/following::div/../following::div/../following::div/../following::div/../following::div/../following::div/../following::div/../following::div/../div[normalize-space(text())='"+fieldName.get(i)+"']/following-sibling::div"));
            for(int j=0;j<value.size();j++) {
                if (each.getText().equalsIgnoreCase(value.get(j))) {
                    if(i == j)
                        log("No changes!!!");
                    else
                        log("Changes verified successfully");
                    CCUtils.assertEquals(each.getText().contains(value.get(j)), true);
                    flag = 1;
                    break;
                }
            }
        }
        log("successfully verified editable content");
    }

    public void clickOnInterfaceSetupKebabMenu(String externalSystemName) {
        int flag = 0;
        for(WebElement eachElement : externalSystemList) {
            if(eachElement.getText().contains(externalSystemName)) {
                flag = 1;
                WebElement kebabMenu = driver.findElement(By.xpath("//span[normalize-space(text())='"+externalSystemName+"']/../following-sibling::div/../following-sibling::td/div/div/span/mat-icon[text()='more_vert']"));
                new Actions(driver).moveToElement(kebabMenu).click().build().perform();
                CCUtils.assertEquals(kebabMenu.isDisplayed(), true);
                log(externalSystemName + " kebab Menu clicked successfully");
                break;
            }
        }
        if(flag == 0)
            log(externalSystemName + " Kebab Menu is not available in Interface section");
    }

    public void verifyInterfaceSetupStatus(String externalSystemName, String status) {
        int flag = 0;
        for(WebElement eachElement : externalSystemList) {
            if(eachElement.getText().contains(externalSystemName)) {
                flag = 1;
                WebElement kebabMenu = driver.findElement(By.xpath("//span[normalize-space(text())='"+externalSystemName+"']/../following-sibling::div/../following-sibling::td/div/div/div/span[normalize-space(text())='"+status.toUpperCase()+"']"));
                new Actions(driver).moveToElement(kebabMenu).build().perform();
                CCUtils.assertEquals(kebabMenu.isDisplayed(), true);
                log(externalSystemName + " with "+status+" status available in Interface Setup section");
                break;
            }
        }
        if(flag == 0)
            log(externalSystemName + " status is not available in Interface Setup section");
    }



}
