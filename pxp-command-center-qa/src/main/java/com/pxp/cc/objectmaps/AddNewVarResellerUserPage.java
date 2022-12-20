// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.objectmaps;

import java.awt.*;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.pxp.cc.util.CCUtils;

public class AddNewVarResellerUserPage extends CCMainPage {
    @FindBy(id = "addNewTitle")
    private WebElement labelPageTitle;

    @FindBy(xpath = "//p[text()='Complete the following details.']")
    private WebElement labelUserDetailsText;

    @FindBy(xpath = "//span[text()='VAR/Reseller *']")
    private WebElement labelVarReseller;

    @FindBy(xpath = "//ng-select[@id='selectUser']//div[text()='Search/Select']")
    private WebElement textboxSearchVarResellerName;

    @FindBy(xpath = "//span[@class='ng-arrow-wrapper']")
    private WebElement comboSearchVarResellerName;

    @FindBy(xpath = "//div[@role='combobox']/input")
    private WebElement inputTextboxSearchVarResellerName;

    @FindBy(xpath = "//span[@title='Clear all']")
    private WebElement clearTextboxSearchVarResellerName;

    @FindBy(xpath = "//ng-option[@id='directoryDirectName']")
    private List<WebElement> listOfVARResellerType;

    @FindBy(xpath = "//label[text()='Status *']")
    private WebElement labelStatus;

    @FindBy(xpath = "//div[normalize-space(text())='Active']")
    private WebElement labelActive;

    @FindBy(xpath = "//div[normalize-space(text())='Inactive']")
    private WebElement labelInactive;

    @FindBy(xpath = "//input[@id='createUserActiveRadio-input']")
    private WebElement radioButtonActive;

    @FindBy(xpath = "//input[@id='createUserInactiveRadio-input']")
    private WebElement radioButtonInactive;

    @FindBy(xpath = "//label[text()='User Roles']")
    private WebElement labelUserRoles;

    @FindBy(xpath = "//label[text()='User Roles']//following::input[@type='radio']")
    private List<WebElement> listUserRoleRadioButtons;

    @FindBy(id = "addNewCancel")
    private WebElement buttonCancel;

    @FindBy(id = "addNewResellerUserBtn")
    private WebElement buttonSaveAndAdd;

    @FindBy(id = "roleName2012")
    private WebElement labelWorkflowUserRole;

    @FindBy(id = "roleName2013")
    private WebElement labelViewReportingUserRole;

    @FindBy(id = "roleName2010")
    private WebElement labelSuperUserRole;

    @FindBy(id = "roleName2011")
    private WebElement labelSolutionUserRole;

    @FindBy(xpath = "//span[text()='View Only']")
    private WebElement labelViewOnlyUserRole;

    @FindBy(id = "noRoles")
    private WebElement labelNoRoleAssigned;

    @FindBy(id = "cancel-text")
    private WebElement buttonCloseWindow;

    @FindBy(id = "resellerIdRequiredErr")
    private WebElement labelVarResellerNameReqError;

    @FindBy(xpath = "//span[@class='mat-option-text']")
    private WebElement selectFirstInAutoSuggestion;

    public AddNewVarResellerUserPage(WebDriver driver) {
        super(driver);
    }

    public void verifyAddNewVarResellerUserPageTitle() {
        log("Verifying add VAR/Reseller user page title");
        CCUtils.verifyAndWaitForElement(labelPageTitle, driver);
        CCUtils.assertTrue(labelPageTitle.isDisplayed(), "Add VAR/Reseller user page title is not displayed");
        CCUtils.assertEquals(labelPageTitle.getText().trim(), "Add VAR/Reseller User");
        log("Add VAR/Reseller user page title is " + labelPageTitle.getText().trim());
    }

    public void VerifyHeaderText() {
        log("Verifying header text on add new VAR/Reseller user");
        CCUtils.verifyAndWaitForElement(labelUserDetailsText, driver);
        CCUtils.assertTrue(labelUserDetailsText.isDisplayed(),
                "header text is not displayed on the add VAR/Reseller user page");
        CCUtils.assertEquals(labelUserDetailsText.getText().trim(), "Complete the following details.");
    }

    public void verifyVarResellerLabelTextAndSearchTextboxDefaultValue() {
        CCUtils.verifyAndWaitForElement(labelVarReseller, driver);
        log("Verifying VAR/Reseller label text");
        CCUtils.assertEquals(labelVarReseller.getText().trim(), "VAR/Reseller *");
        log("VAR/Reseller label text is " + labelVarReseller.getText().trim());
        CCUtils.verifyAndWaitForElement(textboxSearchVarResellerName, driver);
        log("Verifying search/select text placeholder is displayed for VAR/Reseller search textbox field");
        CCUtils.assertEquals(textboxSearchVarResellerName.getText().trim(), "Search/Select");
        log("Box text for VAR/Reseller search textbox field is " + textboxSearchVarResellerName.getText().trim());
    }

    public void verifyCloseWindowButtonIsDisplayed() {
        CCUtils.verifyAndWaitForElement(buttonCloseWindow, driver);
        log("Verifying close window button is displayed on add VAR/Reseller user page");
        CCUtils.assertTrue(buttonCloseWindow.isDisplayed(),
                "Close window button is not displayed on add VAR/Reseller user page");
        CCUtils.assertEquals(buttonCloseWindow.getText().trim(), "clear");
    }

    public void verifyCancelButtonIsDisplayed() {
        CCUtils.verifyAndWaitForElement(buttonCancel, driver);
        log("Verifying cancel button is displayed on add VAR/Reseller user page");
        CCUtils.assertTrue(buttonCancel.isDisplayed(), "Cancel button is not displayed on add VAR/Reseller user page");
        log("Verifying cancel button is enabled");
        CCUtils.assertTrue(buttonCancel.isEnabled(), "Cancel button is disabled");
        log("Verifying cancel button text");
        CCUtils.assertEquals(buttonCancel.getText().trim(), "Cancel");
    }

    public void verifySaveAndAddButtonIsDisplayed() {
        CCUtils.verifyAndWaitForElement(buttonSaveAndAdd, driver);
        log("Verifying save & add button is displayed on add VAR/Reseller user page");
        CCUtils.assertTrue(buttonSaveAndAdd.isDisplayed(),
                "Save & Add button is not displayed on add VAR/Reseller user page");
        log("Verifying save & add button is enabled");
        CCUtils.assertTrue(buttonSaveAndAdd.isEnabled(), "Save & Add button is disabled");
        log("Verifying save & add button text");
        CCUtils.assertEquals(buttonSaveAndAdd.getText().trim(), "Save & Add");
    }

    public VarResellerUsersPage clickOnCancelButton() {
        CCUtils.verifyAndWaitForElement(buttonCancel, driver);
        buttonCancel.click();
        log("Clicked on cancel button");
        return PageFactory.initElements(driver, VarResellerUsersPage.class);
    }

    public VarResellerUsersPage clickOnCloseWindowButton() {
        CCUtils.verifyAndWaitForElement(buttonCloseWindow, driver);
        buttonCloseWindow.click();
        log("Clicked on close window button");
        return PageFactory.initElements(driver, VarResellerUsersPage.class);
    }

    public VarResellerUsersPage clickOnSaveAndAdd() {
        CCUtils.verifyAndWaitForElement(buttonSaveAndAdd, driver);
        buttonSaveAndAdd.click();
        log("Clicked on save and add button");
        return PageFactory.initElements(driver, VarResellerUsersPage.class);
    }

    public void verifyUserRoleRadioButtonsAreNotByDefaultSelected() throws InterruptedException {
        for (int i = 0; i < listUserRoleRadioButtons.size(); i++) {
            CCUtils.assertTrue(!listUserRoleRadioButtons.get(i).isSelected(),
                    listUserRoleRadioButtons.get(i).getText().trim()
                            + " : User role radio buttons are already selected");
        }
    }

    public void verifyUserRoleValues() {
        CCUtils.verifyAndWaitForElement(labelWorkflowUserRole, driver);
        CCUtils.assertEquals(labelWorkflowUserRole.getText().trim(), "Workflow");
        CCUtils.verifyAndWaitForElement(labelViewReportingUserRole, driver);
        CCUtils.assertEquals(labelViewReportingUserRole.getText().trim(), "View/Reporting");
        CCUtils.verifyAndWaitForElement(labelSuperUserRole, driver);
        CCUtils.assertEquals(labelSuperUserRole.getText().trim(), "Super");
        CCUtils.verifyAndWaitForElement(labelSolutionUserRole, driver);
        CCUtils.assertEquals(labelSolutionUserRole.getText().trim(), "Solution");
        CCUtils.verifyAndWaitForElement(labelViewOnlyUserRole, driver);
        CCUtils.assertEquals(labelViewOnlyUserRole.getText().trim(), "View Only");
    }

    public void verifyUserRolesLabelTextAndRadioButtons() throws Exception {
        CCUtils.verifyAndWaitForElement(labelUserRoles, driver);
        log("Verifying user roles label is displayed on add VAR/Reseller user page");
        CCUtils.assertTrue(labelUserRoles.isDisplayed(),
                "User roles label is not displayed on add VAR/Reseller user page");
        CCUtils.assertEquals(labelUserRoles.getText().trim(), "User Roles");
        log("User roles label text is " + labelUserRoles.getText().trim());
        log("Verifying user role label values on add VAR/Reseller user page");
        verifyUserRoleValues();
        log("Verifying user role radio buttons are not selected");
        //verifyUserRoleRadioButtonsAreNotByDefaultSelected();
    }

    public void clickVarResellerCombo(int i) throws InterruptedException, AWTException {
        CCUtils.verifyElementToBeClickable(comboSearchVarResellerName, driver);
        comboSearchVarResellerName.click();
        log(listOfVARResellerType.get(i).getText());
        listOfVARResellerType.get(i).click();
        log("VAR/Reseller added Successfully");
    }

    public void enterVarResellerName(String input) {
        CCUtils.verifyAndWaitForElement(inputTextboxSearchVarResellerName, driver);
        inputTextboxSearchVarResellerName.clear();
        inputTextboxSearchVarResellerName.sendKeys(input);
        //CCUtils.pressEnterKey();
        log("Entered input for VAR/Reseller Name : " + input);
    }

    public void clearVarResellerField() {
        CCUtils.verifyAndWaitForElement(clearTextboxSearchVarResellerName, driver);
        clearTextboxSearchVarResellerName.click();
        log("deleted input in VAR/Reseller name field");
    }

    public String getRequiredErrorForVarResellerField() {
        CCUtils.verifyAndWaitForElement(labelVarResellerNameReqError, driver);
        return labelVarResellerNameReqError.getText().trim();
    }

    public void clickFirstInAutoSuggestion() {
        CCUtils.verifyAndWaitForElement(selectFirstInAutoSuggestion, driver);
        if (selectFirstInAutoSuggestion.isDisplayed() == true) {
            log("Clicked on " + selectFirstInAutoSuggestion.getText().trim() + " in Auto Suggestion ");
            javascriptClick(selectFirstInAutoSuggestion);

        } else {
            log(selectFirstInAutoSuggestion.getText() + " is not present in Auto Suggestion");
            CCUtils.assertTrue(false, "Searched text not present in client");
        }
    }

    public void searchAndSelectVarReseller(String varResellerName) {
        enterVarResellerName(varResellerName);
    }

    public void selectUserRole(String userRole) {
        if (listUserRoleRadioButtons.size() > 0) {
            if (userRole.equals("Workflow")) {
                javascriptClick(listUserRoleRadioButtons.get(0));
                log("Clicked on Workflow user role radio button");
            } else if (userRole.equals("View/Reporting")) {
                javascriptClick(listUserRoleRadioButtons.get(1));
                log("Clicked on View/Reporting user role radio button");
            } else if (userRole.equals("Super")) {
                javascriptClick(listUserRoleRadioButtons.get(2));
                log("Clicked on Super user role radio button");
            } else if (userRole.equals("Solution")) {
                javascriptClick(listUserRoleRadioButtons.get(3));
                log("Clicked on Solution user role radio button");
            } else if (userRole.equals("View Only")) {
                javascriptClick(listUserRoleRadioButtons.get(4));
                log("Clicked on view only user role radio button");
            }
        } else {
            log("User role radio buttons are not displayed on the add VAR/Reseller page");
        }
    }

    public void verifyStatusLabelTextAndRadioButtons() {
        CCUtils.verifyAndWaitForElement(labelStatus, driver);
        log("Verifying status label text");
        CCUtils.assertEquals(labelStatus.getText().trim(), "Status *");
        log("Status label text is " + labelStatus.getText().trim());
        log("Verifying active label and radio button are displayed on add new user page");
        CCUtils.assertEquals(labelActive.getText().trim(), "Active");
        CCUtils.assertTrue(radioButtonActive.isDisplayed(), "Active radiobutton is not present on add new user page");
        log("Verifying inactive label and radio button are displayed on add new user page");
        CCUtils.assertEquals(labelInactive.getText().trim(), "Inactive");
        CCUtils.assertTrue(radioButtonInactive.isDisplayed(),
                "Inactive radiobutton is not present on add new user page");
    }

    public void verifyActiveRadioButtonByDefaultSelected() {
        CCUtils.verifyAndWaitForElement(radioButtonActive, driver);
        log("Verifying active button is by default selected");
        CCUtils.assertTrue(radioButtonActive.isSelected(),
                "Active button is not displayed on add new user page and selected");
    }
}
