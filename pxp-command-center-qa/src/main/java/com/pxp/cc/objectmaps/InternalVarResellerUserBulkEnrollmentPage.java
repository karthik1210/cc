// Copyright 2023 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter

public class InternalVarResellerUserBulkEnrollmentPage extends CCMainPage {

    CommonMethods commonMethods;
    AddNewInternalUsersPage addNewInternalUsersPage;
    AddNewVarResellerUserPage addNewVarResellerUserPage;
    ClientDirectoryPage clientDirectoryPage;

    public InternalVarResellerUserBulkEnrollmentPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "cmdCircleIcon")
    private WebElement commandCenterUsersIcon;

    @FindBy(xpath = "//div[text()='INTERNAL USERS']")
    private WebElement internalUserTab;

    @FindBy(xpath = "//span[normalize-space(text())='Internal Users']")
    private WebElement internalUsersLabel;

    @FindBy(xpath = "//div[text()='CLIENT USERS']")
    private WebElement clientUserTab;

    @FindBy(xpath = "//span[normalize-space(text())='Client Users']")
    private WebElement clientUsersLabel;

    @FindBy(xpath = "//div[text()='VAR/RESELLER USERS']")
    private WebElement varResellerUserTab;

    @FindBy(xpath = "//span[normalize-space(text())='VAR/Reseller Users']")
    private WebElement varResellerUsersLabel;

    @FindBy(xpath = "//button[@id = 'enrollment' and @mattooltip='Bulk Enrollment']")
    private WebElement sendEnrollmentButton;

    @FindBy(xpath = "//span[normalize-space(text())='Select users to send enrollment']")
    private WebElement bulkEnrollmentTitle;

    @FindBy(id = "applyCancel")
    private WebElement bulkEnrollmentCancelButton;

    @FindBy(id = "enrollBtn")
    private WebElement enrollButton;

    @FindBy(xpath = "//span[text()='SEARCH OPTIONS']/../../../button")
    private WebElement searchOptionsButton;

    @FindBy(xpath = "//table[@role='grid']")
    private WebElement userTable;

    @FindBy(xpath = "//table[@role='grid']/thead/following-sibling::tbody/tr")
    private List<WebElement> userTableData;

    @FindBy(xpath = "//td[normalize-space(text())='No results found']")
    private WebElement noResultsFoundMessage;

    @FindBy(xpath = "//span[contains(text(),'Are you sure you want to send an enrollment email to the ')]")
    private WebElement confirmationMessageForSendEnrollment;

    @FindBy(xpath = "//span[text()='First Name']")
    private WebElement firstNameLabelOnSearchFilter;

    @FindBy(xpath = "//span[text()='Last Name']")
    private WebElement lastNameLabelOnSearchFilter;

    @FindBy(xpath = "//span[text()='Email']")
    private WebElement emailLabelOnSearchFilter;

    @FindBy(xpath = "//span[text()='Username']")
    private WebElement usernameLabelOnSearchFilter;

    @FindBy(xpath = "//div[normalize-space(text())='Mobile Number']")
    private WebElement mobileNumberLabelOnSearchFilter;

    @FindBy(id = "searchFirstNameInput")
    private WebElement firstNameTextBoxOnSearchFilter;

    @FindBy(id = "searchLastNameInput")
    private WebElement lastNameTextBoxOnSearchFilter;

    @FindBy(id = "searchEmailInput")
    private WebElement emailTextBoxOnSearchFilter;

    @FindBy(id = "searchUserNameInput")
    private WebElement usernameTextBoxOnSearchFilter;

    @FindBy(id = "searchMobileInput")
    private WebElement mobileNumberTextBoxOnSearchFilter;

    @FindBy(xpath = "//span[normalize-space(text())='Confirmed']/preceding-sibling::div/input/../../../label[@class='mat-checkbox-layout']")
    private WebElement confirmedStatusCheckBox;

    @FindBy(xpath = "//span[normalize-space(text())='Pending']/preceding-sibling::div/input/../../../label[@class='mat-checkbox-layout']")
    private WebElement pendingStatusCheckBox;

    @FindBy(xpath = "//span[normalize-space(text())='Expired']/preceding-sibling::div/input/../../../label[@class='mat-checkbox-layout']")
    private WebElement expiredStatusCheckBox;

    @FindBy(xpath = "//span[@class='mat-checkbox-label']/span[normalize-space(text())='Not Sent']/../preceding-sibling::div/input/../../../label[@class='mat-checkbox-layout']")
    private WebElement notSentStatusCheckBox;

    @FindBy(xpath = "//button[@id='filterSearchBtn']/preceding::hr/following-sibling::div/div[normalize-space(text())='Enrollment Email Status']")
    private WebElement enrollmentStatusTitleOnSearchFilter;

    @FindBy(xpath = "//span[normalize-space(text())='Confirmed']")
    private WebElement confirmedStatusLabel;

    @FindBy(xpath = "//span[normalize-space(text())='Pending']")
    private WebElement pendingStatusLabel;

    @FindBy(xpath = "//span[normalize-space(text())='Expired']")
    private WebElement expiredStatusLabel;

    @FindBy(xpath = "//span[@class='mat-checkbox-label']/span[normalize-space(text())='Not Sent']")
    private WebElement notSentStatusLabel;

    @FindBy(id = "IconContainer")
    private WebElement moreOptionsKebabForCreatingUser;

    @FindBy(xpath = "//span[text()='CLEAR']")
    private WebElement clearButtonOnSearchFilterOnUserPage;

    @FindBy(xpath = "//span[text()='SEARCH']")
    private WebElement searchButtonOnSearchFilterOnUserPage;

    @FindBy(id = "addClientUser")
    private WebElement buttonNewVarReseller;

    @FindBy(id = "addInternalUser")
    private WebElement buttonNewInternal;

    @FindBy(xpath = "//mat-icon[@mattooltip='More Options']")
    private WebElement kebabMenuOnUserDetails;

    @FindBy(xpath = "//button[normalize-space(text())='Lock User']")
    private WebElement lockUserButton;

    @FindBy(xpath = "//mat-icon[normalize-space(text())='arrow_back']")
    private WebElement backArrowOnUserDetail;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> listOfUsers;

    @FindBy(xpath = "//span[normalize-space(text())='CLEAR ALL']")
    private WebElement clearAll;

    @FindBy(xpath = "(//th[@mat-sort-header='firstName']/..//div)[1]")
    private WebElement selectAllUserOnPage;

    @FindBy(xpath = "//button[normalize-space(text())='Yes, Send']")
    private WebElement yesSendButton;

    @FindBy(xpath = "//button[normalize-space(text())='Yes, Send']/../..//span/span[text()='Cancel'] | //button[text()='Cancel']")
    private WebElement cancelButtonOnSendEnrollmentConfirmation;

    @FindBy(id = "createBtn")
    private WebElement SaveAndCreate;

    @FindBy(xpath = "//span[normalize-space(text())='clear']")
    private WebElement clearButtonOnSendEnrollmentConfirmation;

    @FindBy(xpath = "//mat-select[@role='combobox']/div/div/div")
    private WebElement changeItemsDisplayPerPage;

    @FindBy(xpath = "//span[normalize-space(text())='Select users to send enrollment']")
    private WebElement selectUsersToSendEnrollmentLabel;

    @FindBy(xpath = "//tbody/tr/td/following-sibling::td")
    private WebElement firstUserSearchValue;

    @FindBy(xpath = "//button[normalize-space(text())='Send Enrollment Email']")
    private WebElement sendEnrollmentEmail;

    @FindBy(xpath = "//span[text()='Active']/../div")
    private WebElement activeCheckbox;

    public void changeItemsDisplayPerPage(int count) {
        log("Change display items per page to " + count);
        WebElement element = driver.findElement(By.xpath("//span[normalize-space(text())='" + count + "']"));
        element.click();
    }

    public boolean verifyTextBoxIsEmpty(String value) {
        if (value.equalsIgnoreCase("firstName")) {
            boolean result = firstNameTextBoxOnSearchFilter.getText().isEmpty();
            log("Textbox is empty");
            return result;
        } else {
            log("Textbox is not empty, it has some value");
            return false;
        }
    }

    public boolean isChecked(boolean value) {
        if (value) {
            CCUtils.assertEquals(driver.findElement(By.xpath("//input[@aria-checked='true']")).isDisplayed(), true);
            return true;
        } else {
            CCUtils.assertEquals(driver.findElement(By.xpath("//input[@aria-checked='false']")).isDisplayed(), true);
            return false;
        }

    }

    public void selectEnrollmentStatus(String status) {
        if (status.equalsIgnoreCase("Confirmed"))
            confirmedStatusCheckBox.click();
        else if (status.equalsIgnoreCase("Pending"))
            pendingStatusCheckBox.click();
        else if (status.equalsIgnoreCase("Expired"))
            expiredStatusCheckBox.click();
        else if (status.equalsIgnoreCase("Not Sent"))
            notSentStatusCheckBox.click();
    }

    public int countUsersOnUserSearchTable() throws InterruptedException {
        Thread.sleep(2000);
        List<WebElement> listOfStatus;
        List<String> enrollmentStatus = new ArrayList<String>();
        enrollmentStatus.add("Not Sent");
        enrollmentStatus.add("Pending");
        enrollmentStatus.add("Expired");
        enrollmentStatus.add("Confirmed");
        listOfStatus = driver.findElements(By.xpath("//td/span[normalize-space(text())='" + enrollmentStatus.get(0) + "'] | //td[normalize-space(text())='" + enrollmentStatus.get(1) + "'] | //td[normalize-space(text())='" + enrollmentStatus.get(2) + "'] | //td[normalize-space(text())='" + enrollmentStatus.get(3) + "']"));
        if (listOfUsers.size() == listOfStatus.size()) {
            log("status = " + listOfStatus.size() + " and list of users = " + listOfUsers.size());
        } else {
            log("Error!! Need to be addressed");
            log("status = " + listOfStatus.size() + " and list of users = " + listOfUsers.size());
        }
        return listOfUsers.size();
    }

    public void checkEnrollmentStatus(String enrollmentStatus) throws InterruptedException {
        Thread.sleep(2000);
        List<WebElement> listOfStatus;
        if (enrollmentStatus.equalsIgnoreCase("Not Sent"))
            listOfStatus = driver.findElements(By.xpath("//td/span[normalize-space(text())='" + enrollmentStatus + "']"));
        else
            listOfStatus = driver.findElements(By.xpath("//td[normalize-space(text())='" + enrollmentStatus + "']"));
        if (listOfUsers.size() == listOfStatus.size()) {
            log("Status " + enrollmentStatus + " only fetched on User table");
            log("status = " + listOfStatus.size() + " and list of users = " + listOfUsers.size());
        } else {
            log("Error!! Need to be addressed");
            log("status = " + listOfStatus.size() + " and list of users = " + listOfUsers.size());
        }
    }

    public boolean checkFirstNameOfUserOnUserSearchTable(String name) {
        return driver.findElement(By.xpath("//td[normalize-space(text())='" + name + "']")).isDisplayed();
    }

    public void clickOnElement(WebElement element, String elementName) {
        CCUtils.verifyAndWaitForElement(element, driver);
        log("clicking on " + elementName);
        CCUtils.focusOnElementAndClick(element, driver);
        log("Successfully clicked on " + elementName);
    }

    public boolean verifyElementPresence(WebElement element, String elementName) {
        try {
            CCUtils.scrollPageViaElement(driver, element);
            CCUtils.verifyAndWaitForElement(element, driver);
            log("verifying " + elementName + " is displayed");
            CCUtils.assertEquals(element.isDisplayed(), true);
            log("Successfully verified");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public void clickSearchUserOnUserTable(String firstName) {
        driver.findElement(By.xpath("//td[normalize-space(text())='" + firstName + "']/following::span/following::td/span/mat-icon/following-sibling::mat-icon")).click();
    }

    public boolean verifyElementIsEnabled(WebElement element) {
        CCUtils.verifyAndWaitForElement(element, driver);
        log("verifying " + element.getText() + " is enabled or not");
        return element.isEnabled();
    }

    public void selectUser(String firstName) {
        WebElement element = driver.findElement(By.xpath("//td[normalize-space(text())='" + firstName + "']/preceding-sibling::td//input"));
        CCUtils.verifyAndWaitForElement(element, driver);
        log("selecting " + firstName + " User");
        CCUtils.focusOnElementAndClick(element, driver);
        log("successfully selected");
    }

    public void selectFirstUserOnUserTable() {
        log("Selecting First User On User Table");
        List<WebElement> userTableSearchData = driver.findElements(By.xpath("//tr/td/mat-checkbox/label/div"));
        if (userTableSearchData.isEmpty())
            log("No results found");
        else {
                userTableSearchData.get(2).click();
                log("Successfully Selected");
            }
    }

    public void doubleClickOnFirstUserOnUserTable() {
        log("Selecting First User On User Table");
        List<WebElement> userTableSearchData = driver.findElements(By.xpath("//tr/td/mat-checkbox/label/div"));
        if (userTableSearchData.isEmpty())
            log("No results found");
        else {
            new Actions(driver).moveToElement(userTableSearchData.get(2)).doubleClick().build().perform();
            log("Successfully double clicked");
        }
    }

    public void enterValue(WebElement element, String value) {
        CCUtils.verifyAndWaitForElement(element, driver);
        CCUtils.enterValueOnTextField(element, value);
    }

    public boolean verifyEnrollmentStatusesAndCheckBox() {
        try {
            log("verifying Enrollment statuses and check boxes are displayed");
            CCUtils.assertEquals(confirmedStatusCheckBox.isDisplayed(), true);
            CCUtils.assertEquals(confirmedStatusLabel.isDisplayed(), true);
            CCUtils.assertEquals(pendingStatusCheckBox.isDisplayed(), true);
            CCUtils.assertEquals(pendingStatusLabel.isDisplayed(), true);
            CCUtils.assertEquals(expiredStatusCheckBox.isDisplayed(), true);
            CCUtils.assertEquals(expiredStatusLabel.isDisplayed(), true);
            CCUtils.assertEquals(notSentStatusCheckBox.isDisplayed(), true);
            CCUtils.assertEquals(notSentStatusLabel.isDisplayed(), true);
            log("Successfully verified");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public HashMap<String, Boolean> verifyUserBulkEnrollmentElements(String userType) {
        HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        logStep("Verify BULK ENROLLMENT button is displayed for "+ userType +" Users Tab In Command Center Users Screen");
        if (userType.equalsIgnoreCase("Internal")) {
            clickOnElement(internalUserTab, "Internal Users Tab");
            result.put("verifying Internal users label", verifyElementPresence(internalUsersLabel, "Internal Users Label"));
        } else {
            clickOnElement(varResellerUserTab, "VarReseller Users Tab");
            result.put("verifying VarReseller users label", verifyElementPresence(varResellerUsersLabel, "VarReseller Users Label"));
        }
        result.put("verifying Bulk Enrollment button", verifyElementPresence(sendEnrollmentButton, "Bulk Enrollment button"));
        logStep("Verify the SEARCH OPTIONS filter for "+ userType +"  Users in Bulk Enrollment Screen");
        clickOnElement(searchOptionsButton, "Search filter Options");
        result.put("verifyFirstNameLabel", verifyElementPresence(firstNameLabelOnSearchFilter, "First Name Label"));
        result.put("verifyLastNameLabel", verifyElementPresence(lastNameLabelOnSearchFilter, "Last Name Label"));
        result.put("verifyEmailLabel", verifyElementPresence(emailLabelOnSearchFilter, "Email Label"));
        result.put("verifyUserNameLabel", verifyElementPresence(usernameLabelOnSearchFilter, "Username Label"));
        result.put("verifyMobileNumberLabel", verifyElementPresence(mobileNumberLabelOnSearchFilter, "Mobile number Label"));
        result.put("verifyFirstNameTextBox", verifyElementPresence(firstNameTextBoxOnSearchFilter, "First Name Text box"));
        result.put("verifyLastNameTextBox", verifyElementPresence(lastNameTextBoxOnSearchFilter, "Last Name Text box"));
        result.put("verifyEmailTextBox", verifyElementPresence(emailTextBoxOnSearchFilter, "Email Text box"));
        result.put("verifyUserNameTextBox", verifyElementPresence(usernameTextBoxOnSearchFilter, "Username Text box"));
        result.put("verifyMobileNumberTextBox", verifyElementPresence(mobileNumberTextBoxOnSearchFilter, "Mobile number Text box"));
        result.put("verifyEnrollmentStatusTitle", verifyElementPresence(enrollmentStatusTitleOnSearchFilter, "Enrollment status Title"));
        result.put("verifyAllEnrollmentStatusesAndCheckboxes", verifyEnrollmentStatusesAndCheckBox());
        result.put("verifyClearButton", verifyElementPresence(clearButtonOnSearchFilterOnUserPage, "clear button"));
        result.put("verifySearchButton", verifyElementPresence(searchButtonOnSearchFilterOnUserPage, "search button"));
        logStep("Verify that Bulk Enrollment page got triggered when user click on BULK ENROLLMENT button for "+ userType +" User");
        clickOnElement(sendEnrollmentButton, "Bulk Enrollment button");
        result.put("verify Bulk Enrollment Title", verifyElementPresence(bulkEnrollmentTitle, "Bulk Enrollment Title"));
        logStep("Verify that User can enable the Send Enrollment button in Bulk Enrollment Screen for "+ userType +" Users");
        result.put("verify Enrollment button enabled", verifyElementIsEnabled(enrollButton));
        clickOnElement(selectAllUserOnPage, "Select All Users");
        result.put("verify Enrollment button enabled", verifyElementIsEnabled(enrollButton));
        clickOnElement(enrollButton, "Send Enrollment Button");
        logStep("PXPP-26704 TC 045_Verify Send Enrollment Confirmation popup in Bulk Enrollment Screen");
        verifyElementPresence(confirmationMessageForSendEnrollment, "Confirmation Message For Send Enrollment");
        verifyElementPresence(yesSendButton, "yes,Send button");
        verifyElementPresence(cancelButtonOnSendEnrollmentConfirmation, "cancel button");
        logStep("Verify that User back to "+ userType +" Users Tab when user click on  Cancel button in Bulk Enrollment Page for "+ userType +" User");
        clickOnElement(cancelButtonOnSendEnrollmentConfirmation, "cancel button");
        clickOnElement(bulkEnrollmentCancelButton, "Cancel button on Bulk Enrollment");
        if (userType.equalsIgnoreCase("Internal"))
            result.put("verifying Internal users label", verifyElementPresence(internalUsersLabel, "Internal Users Label"));
        else
            result.put("verifying VarReseller users label", verifyElementPresence(varResellerUsersLabel, "VarReseller Users Label"));
        return result;
    }

    public void typesOfUserSearchOnBulkEnrollment(String type, String userType) throws InterruptedException, AWTException {
        int count, num;
        commonMethods = new CommonMethods(driver);
        clientDirectoryPage = new ClientDirectoryPage(driver);
        clientDirectoryPage.clickOnCommandCenterUsersIcon();
        addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
        addNewVarResellerUserPage = new AddNewVarResellerUserPage(driver);
        if (userType.equalsIgnoreCase("Internal")) {
            logStep("Verify that "+ type +" Internal Users are not fetched to User search table in Bulk Enrollment Screen");
            clickOnElement(internalUserTab, "Internal Users Tab");
            clickOnElement(searchOptionsButton, "search filter option");
            enterValue(firstNameTextBoxOnSearchFilter, type);
            clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button on User Search filter");
            count = countUsersOnUserSearchTable();
            if (count == 0) {
                logStep("Create a new " + type + " internal user and verify success toaster");
                javascriptClick(moreOptionsKebabForCreatingUser);
                javascriptClick(buttonNewInternal);
                String firstName = type + CCUtils.generateAlphaNumericString(12).concat("'s");
                commonMethods.enterFirstName(firstName);
                commonMethods.enterLastName(CCUtils.generateAlphaNumericString(3).concat("'s"));
                commonMethods.enterEmail(CCUtils.generateNewEmail(10));
                commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(6));
                if (type.equalsIgnoreCase("inactive"))
                    commonMethods.selectInactiveRadiobutton();
                if (!type.equalsIgnoreCase("no roles"))
                    addNewInternalUsersPage.addUserRoles();
                addNewInternalUsersPage.clickOnSaveAndAddButton();
                if (type.equalsIgnoreCase("no roles"))
                    clickOnElement(SaveAndCreate, "Save And Create");
                commonMethods.verifyToasterMessage("Success! New User has been successfully added");
                if (type.equalsIgnoreCase("locked")) {
                    clickOnElement(searchOptionsButton, "search filter option");
                    enterValue(firstNameTextBoxOnSearchFilter, firstName);
                    clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button on User Search filter");
                    clickSearchUserOnUserTable(firstName);
                    clickOnElement(kebabMenuOnUserDetails, "kebab menu on user details");
                    clickOnElement(lockUserButton, "lock user button");
                    commonMethods.verifyToasterMessage("Success! User status has been successfully locked");
                    clickOnElement(backArrowOnUserDetail, "back button on user details");
                    clickOnElement(sendEnrollmentButton, "Bulk Enrollment Button");
                    verifyElementPresence(noResultsFoundMessage, "No Results Found Message");
                    clickOnElement(bulkEnrollmentCancelButton, "Cancel button On Bulk Enrollment");
                    clickOnElement(clearAll, "clearAll button");
                }
            }
                clickOnElement(searchOptionsButton, "search filter option");
                firstNameTextBoxOnSearchFilter.clear();
                enterValue(firstNameTextBoxOnSearchFilter, type);
                clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button on User Search filter");
                clickOnElement(sendEnrollmentButton, "Bulk Enrollment Button");
                verifyElementPresence(noResultsFoundMessage, "No Results Found Message");
                clickOnElement(bulkEnrollmentCancelButton, "Cancel button On Bulk Enrollment");
                clickOnElement(clearAll, "clearAll button");
        } else {
            logStep("Verify that "+ type +" VAR/Reseller Users are not fetched to User search table in Bulk Enrollment Screen");
            clickOnElement(varResellerUserTab, "VarReseller Users Tab");
            clickOnElement(searchOptionsButton, "search filter option");
            enterValue(firstNameTextBoxOnSearchFilter, type);
            clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button on User Search filter");
            count = countUsersOnUserSearchTable();
            if (count == 0) {
                javascriptClick(moreOptionsKebabForCreatingUser);
                javascriptClick(buttonNewVarReseller);
                addNewVarResellerUserPage.clickVarResellerCombo(2);
                String firstName = type + CCUtils.generateAlphaNumericString(18);
                commonMethods.enterFirstName(firstName);
                commonMethods.enterLastName(CCUtils.generateAlphaNumericString(18).concat("'s"));
                commonMethods.enterEmail(CCUtils.generateNewEmail(11));
                commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
                commonMethods.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
                if (type.equalsIgnoreCase("inactive")) {
                    logStep("Select inactive status");
                    commonMethods.selectInactiveRadiobutton();
                }
                if (!type.equalsIgnoreCase("no roles")) {
                    logStep("Select user role");
                    addNewVarResellerUserPage.selectUserRole("super");
                }
                logStep("Click on save and add button");
                addNewVarResellerUserPage.clickOnSaveAndAdd();
                if (type.equalsIgnoreCase("no roles"))
                    clickOnElement(SaveAndCreate, "Save And Create");
                logStep("Verify success toaster message");
                commonMethods.verifyToasterMessage("Success! New user has been successfully added");
                if (type.equalsIgnoreCase("locked")) {
                    clickOnElement(searchOptionsButton, "search filter option");
                    enterValue(firstNameTextBoxOnSearchFilter, firstName);
                    clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button on User Search filter");
                    clickSearchUserOnUserTable(firstName);
                    clickOnElement(kebabMenuOnUserDetails, "kebab menu on user details");
                    clickOnElement(lockUserButton, "lock user button");
                    commonMethods.verifyToasterMessage("Success! User status has been successfully locked");
                    clickOnElement(backArrowOnUserDetail, "back button on user details");
                    clickOnElement(sendEnrollmentButton, "Bulk Enrollment Button");
                    verifyElementPresence(noResultsFoundMessage, "No Results Found Message");
                    clickOnElement(bulkEnrollmentCancelButton, "Cancel button On Bulk Enrollment");
                    clickOnElement(clearAll, "clearAll button");
                }
            }
                clickOnElement(searchOptionsButton, "search filter option");
                firstNameTextBoxOnSearchFilter.clear();
                enterValue(firstNameTextBoxOnSearchFilter, type);
                clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button on User Search filter");
                clickOnElement(sendEnrollmentButton, "Bulk Enrollment Button");
                verifyElementPresence(noResultsFoundMessage, "No Results Found Message");
                clickOnElement(bulkEnrollmentCancelButton, "Cancel button On Bulk Enrollment");
                clickOnElement(clearAll, "clearAll button");
        }
    }

    public void userSearchWithAllAtributes(String type) throws InterruptedException, AWTException {
        logStep("Verify the Search Filter Functionality with all attributes in Bulk Enrollment Screen for "+ type +" Users");
        logStep("Create a new active " + type + " user with user roles and verify success toaster");
        commonMethods = new CommonMethods(driver);
        clientDirectoryPage = new ClientDirectoryPage(driver);
        clientDirectoryPage.clickOnCommandCenterUsersIcon();
        addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
        addNewVarResellerUserPage = new AddNewVarResellerUserPage(driver);
        String varResellerFirstName = null;
        String varResellerLastName = null;
        String varResellerEmail = null;
        String varResellerUserName = null;
        String varResellerMobileNumber = null;
        String internalFirstName = null;
        String internalLastName = null;
        String internalEmail = null;
        String internalUserName = null;
        if (type.equalsIgnoreCase("Internal")) {
            logStep("Verify the Display message after Filter Search in Bulk Enrollment Screen For "+ type +" Users");
            clickOnElement(internalUserTab, "Internal Users Tab");
            clickOnElement(moreOptionsKebabForCreatingUser, "kebab menu");
            clickOnElement(buttonNewInternal, "New Internal user creation button");
            internalFirstName = CCUtils.generateRandomStringWithoutSymbolsOrNumbers(12).concat("s");
            internalLastName = CCUtils.generateRandomStringWithoutSymbolsOrNumbers(12);
            internalEmail = CCUtils.generateNewEmail(12);
            internalUserName = CCUtils.generateRandomStringWithoutSymbolsOrNumbers(12);
            commonMethods.enterFirstName(internalFirstName);
            commonMethods.enterLastName(internalLastName);
            commonMethods.enterEmail(internalEmail);
            commonMethods.enterUserName(internalUserName);
            addNewInternalUsersPage.addUserRoles();
            addNewInternalUsersPage.clickOnSaveAndAddButton();
            commonMethods.verifyToasterMessage("Success! New User has been successfully added");
        }
        if (type.equalsIgnoreCase("VarReseller")) {
            logStep("Verify the Display message after Filter Search in Bulk Enrollment Screen For "+ type +" Users");
            clickOnElement(varResellerUserTab, "VarReseller Users Tab");
            clickOnElement(moreOptionsKebabForCreatingUser, "kebab menu");
            clickOnElement(buttonNewVarReseller, "New VarReseller user creation button");
            varResellerFirstName = CCUtils.generateRandomStringWithoutSymbolsOrNumbers(12).concat("s");
            varResellerLastName = CCUtils.generateRandomStringWithoutSymbolsOrNumbers(12);
            varResellerEmail = CCUtils.generateNewEmail(12);
            varResellerUserName = CCUtils.generateRandomStringWithoutSymbolsOrNumbers(12);
            varResellerMobileNumber = String.valueOf(CCUtils.generateMobileNumber());
            addNewVarResellerUserPage.clickVarResellerCombo(2);
            commonMethods.enterFirstName(varResellerFirstName);
            commonMethods.enterLastName(varResellerLastName);
            commonMethods.enterEmail(varResellerEmail);
            commonMethods.enterUserName(varResellerUserName);
            commonMethods.enterMobileNumber(varResellerMobileNumber);
            logStep("Verify active radio button is by default selected on add VAR/Reseller user page");
            commonMethods.verifyActiveRadioButtonByDefaultSelected();
            logStep("Select user role");
            addNewVarResellerUserPage.selectUserRole("Super");
            logStep("Click on save and add button");
            addNewVarResellerUserPage.clickOnSaveAndAdd();
            logStep("Verify success toaster message");
            commonMethods.verifyToasterMessage("Success! New user has been successfully added");
        }
        logStep("Verify the Clear and without Search button Functionality for Search Filter in Bulk Enrollment Screen");
        clickOnElement(sendEnrollmentButton, "Bulk enrollment button");
        clickOnElement(searchOptionsButton, "search filter option");
        if (type.equalsIgnoreCase("Internal")) {
            enterValue(firstNameTextBoxOnSearchFilter, internalFirstName);
            enterValue(lastNameTextBoxOnSearchFilter, internalLastName);
            enterValue(emailTextBoxOnSearchFilter, internalEmail);
            enterValue(usernameTextBoxOnSearchFilter, internalUserName);
            clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button On search filter");
            checkEnrollmentStatus("Not Sent");
            clickOnElement(clearAll, "Clear all button");
            clickOnElement(searchOptionsButton, "Search filter options");
            enterValue(firstNameTextBoxOnSearchFilter, internalFirstName);
            enterValue(lastNameTextBoxOnSearchFilter, internalLastName);
            enterValue(emailTextBoxOnSearchFilter, internalEmail);
            enterValue(usernameTextBoxOnSearchFilter, internalUserName);
            clickOnElement(clearButtonOnSearchFilterOnUserPage, "Clear Button on search filter");
            enterValue(firstNameTextBoxOnSearchFilter, internalFirstName);
            enterValue(lastNameTextBoxOnSearchFilter, internalLastName);
            enterValue(emailTextBoxOnSearchFilter, internalEmail);
            enterValue(usernameTextBoxOnSearchFilter, internalUserName);
            clickOnElement(selectUsersToSendEnrollmentLabel, "Send Enrollment label");
            clickOnElement(searchOptionsButton, "search filter option");
            verifyTextBoxIsEmpty("firstName");
            driver.navigate().refresh();
            clickOnElement(searchOptionsButton, "search filter option");
            verifyTextBoxIsEmpty("firstName");
            enterValue(firstNameTextBoxOnSearchFilter, internalFirstName);
        } else {
            enterValue(firstNameTextBoxOnSearchFilter, varResellerFirstName);
            enterValue(lastNameTextBoxOnSearchFilter, varResellerLastName);
            enterValue(emailTextBoxOnSearchFilter, varResellerEmail);
            enterValue(usernameTextBoxOnSearchFilter, varResellerUserName);
            clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button On search filter");
            checkEnrollmentStatus("Not Sent");
            clickOnElement(clearAll, "Clear all button");
            clickOnElement(searchOptionsButton, "Search filter options");
            enterValue(firstNameTextBoxOnSearchFilter, varResellerFirstName);
            enterValue(lastNameTextBoxOnSearchFilter, varResellerLastName);
            enterValue(emailTextBoxOnSearchFilter, varResellerEmail);
            enterValue(usernameTextBoxOnSearchFilter, varResellerUserName);
            clickOnElement(clearButtonOnSearchFilterOnUserPage, "Clear Button on search filter");
            enterValue(firstNameTextBoxOnSearchFilter, varResellerFirstName);
            enterValue(lastNameTextBoxOnSearchFilter, varResellerLastName);
            enterValue(emailTextBoxOnSearchFilter, varResellerEmail);
            enterValue(usernameTextBoxOnSearchFilter, varResellerUserName);
            clickOnElement(selectUsersToSendEnrollmentLabel, "Send Enrollment label");
            clickOnElement(searchOptionsButton, "search filter option");
            verifyTextBoxIsEmpty("firstName");
            driver.navigate().refresh();
            clickOnElement(searchOptionsButton, "search filter option");
            verifyTextBoxIsEmpty("firstName");
            enterValue(firstNameTextBoxOnSearchFilter, varResellerFirstName);
        }
        clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button on User Search filter");
        checkEnrollmentStatus("Not Sent");
        if (type.equalsIgnoreCase("Internal"))
            checkFirstNameOfUserOnUserSearchTable(internalFirstName);
        else
            checkFirstNameOfUserOnUserSearchTable(varResellerFirstName);
        clickOnElement(clearAll, "clear all");
        clickOnElement(searchOptionsButton, "search filter option");
        if (type.equalsIgnoreCase("Internal"))
            enterValue(lastNameTextBoxOnSearchFilter, internalLastName);
        else
            enterValue(lastNameTextBoxOnSearchFilter, varResellerLastName);
        clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button on User Search filter");
        checkEnrollmentStatus("Not Sent");
        if (type.equalsIgnoreCase("Internal"))
            checkFirstNameOfUserOnUserSearchTable(internalFirstName);
        else
            checkFirstNameOfUserOnUserSearchTable(varResellerFirstName);
        clickOnElement(clearAll, "clear all");
        clickOnElement(searchOptionsButton, "search filter option");
        if (type.equalsIgnoreCase("Internal"))
            enterValue(emailTextBoxOnSearchFilter, internalEmail);
        else
            enterValue(emailTextBoxOnSearchFilter, varResellerEmail);
        clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button on User Search filter");
        checkEnrollmentStatus("Not Sent");
        if (type.equalsIgnoreCase("Internal"))
            checkFirstNameOfUserOnUserSearchTable(internalFirstName);
        else
            checkFirstNameOfUserOnUserSearchTable(varResellerFirstName);
        clickOnElement(clearAll, "clear all");
        clickOnElement(searchOptionsButton, "search filter option");
        if (type.equalsIgnoreCase("Internal"))
            enterValue(usernameTextBoxOnSearchFilter, internalUserName);
        else
            enterValue(usernameTextBoxOnSearchFilter, varResellerUserName);
        clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button on User Search filter");
        checkEnrollmentStatus("Not Sent");
        if (type.equalsIgnoreCase("Internal"))
            checkFirstNameOfUserOnUserSearchTable(internalFirstName);
        else
            checkFirstNameOfUserOnUserSearchTable(varResellerFirstName);
        clickOnElement(clearAll, "clear all");
        clickOnElement(bulkEnrollmentCancelButton, "Cancel button On Bulk Enrollment");
    }

    public void selectAndDeselectInBulkEnrollment(String type) throws Exception {
        if (type.equalsIgnoreCase("Internal")) {
            logStep("PXPP-26701 TC 042_Verify the select/de-select the list of users for User Search Table in Bulk Enrollment Screen of Internal Users");
            clickOnElement(internalUserTab, "Internal Users Tab");
        }
        else {
            logStep("PXPP-26703 TC 044_Verify the select/de-select the list of users for User Search Table in Bulk Enrollment Screen of VAR/Reseller Users");
            clickOnElement(varResellerUserTab, "VarReseller Users Tab");
        }
        clickOnElement(sendEnrollmentButton, "Bulk Enrollment button");
        Thread.sleep(5000);
        doubleClickOnFirstUserOnUserTable();
        log(String.valueOf(isChecked(false)));
        selectFirstUserOnUserTable();
        log(String.valueOf(isChecked(true)));
        clickOnElement(selectAllUserOnPage, "Select All users");
        log(String.valueOf(isChecked(true)));
        clickOnElement(selectAllUserOnPage, "Select All users");
        log(String.valueOf(isChecked(false)));
        countUsersOnUserSearchTable();
        clickOnElement(changeItemsDisplayPerPage, "change items to be displayed icon");
        changeItemsDisplayPerPage(50);
        countUsersOnUserSearchTable();
        clickOnElement(bulkEnrollmentCancelButton, "cancel button on bulk enrollment page");
    }

    public void confirmedUserSearchOnBulkEnrollment(String userType) throws InterruptedException {
        int count;
        commonMethods = new CommonMethods(driver);
        clientDirectoryPage = new ClientDirectoryPage(driver);
        clientDirectoryPage.clickOnCommandCenterUsersIcon();
        addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
        addNewVarResellerUserPage = new AddNewVarResellerUserPage(driver);
        if (userType.equalsIgnoreCase("Internal")) {
            logStep("PXPP-26661 TC 025_Verify that Internal Users are not fetched to User search table in Bulk Enrollment Screen when Internal user Enrollment Status is Confirmed");
            clickOnElement(internalUserTab, "Internal Users Tab");
            clickOnElement(searchOptionsButton, "search filter option");
            clickOnElement(confirmedStatusCheckBox, "Confirmed check box");
            clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button on User Search filter");
            count = countUsersOnUserSearchTable();
            if (count == 0)
                log("Confirmed status User currently not available to proceed!!");
            else {
                clickOnElement(searchOptionsButton, "search filter option");
                if (!confirmedStatusCheckBox.isEnabled())
                    clickOnElement(confirmedStatusCheckBox, "Confirmed check box");
                clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button on User Search filter");
                clickOnElement(sendEnrollmentButton, "Bulk Enrollment Button");
                verifyElementPresence(noResultsFoundMessage, "No Results Found Message");
                clickOnElement(bulkEnrollmentCancelButton, "Cancel button On Bulk Enrollment");
                clickOnElement(clearAll, "clearAll button");
            }
        } else {
            logStep("PXPP-26663 TC 027_Verify that VAR/Reseller Users are not fetched to User search table in Bulk Enrollment Screen when VAR/Reseller user Enrollment Status is Confirmed");
            clickOnElement(varResellerUserTab, "VarReseller Users Tab");
            clickOnElement(searchOptionsButton, "search filter option");
            clickOnElement(confirmedStatusCheckBox, "Confirmed check box");
            clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button on User Search filter");
            count = countUsersOnUserSearchTable();
            if (count == 0)
                log("Confirmed status User currently not available to proceed!!");
            else {
                clickOnElement(searchOptionsButton, "search filter option");
                if (!confirmedStatusCheckBox.isEnabled())
                    clickOnElement(confirmedStatusCheckBox, "Confirmed check box");
                clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button on User Search filter");
                clickOnElement(sendEnrollmentButton, "Bulk Enrollment Button");
                verifyElementPresence(noResultsFoundMessage, "No Results Found Message");
                clickOnElement(bulkEnrollmentCancelButton, "Cancel button On Bulk Enrollment");
                clickOnElement(clearAll, "clearAll button");
            }
        }
    }

    public void typesOfStatusUserSearchOnBulkEnrollment(String status, String userType) throws InterruptedException, AWTException {
        commonMethods = new CommonMethods(driver);
        addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
        addNewVarResellerUserPage = new AddNewVarResellerUserPage(driver);
        int count;
        String firstName = null;
        String lastName = null;
        String email = null;
        String userName = null;
        String mobileNumber=null;
        if (userType.equalsIgnoreCase("Internal"))
            clickOnElement(internalUserTab, "Internal Users Tab");
        if(userType.equalsIgnoreCase("VarReseller"))
            clickOnElement(varResellerUserTab, "VarReseller Users Tab");
        clickOnElement(searchOptionsButton, "search filter option");
        if (status.equalsIgnoreCase("Not Sent"))
            clickOnElement(notSentStatusCheckBox, "Not Sent check box");
        else if (status.equalsIgnoreCase("Pending"))
            clickOnElement(pendingStatusCheckBox, "Pending check box");
        else if (status.equalsIgnoreCase("Expired"))
            clickOnElement(expiredStatusCheckBox, "Expired check box");
        clickOnElement(activeCheckbox, "Active check box");
        clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button on User Search filter");
        count = countUsersOnUserSearchTable();
        if (count == 0) {
            if(userType.equalsIgnoreCase("Internal")) {
                clickOnElement(moreOptionsKebabForCreatingUser, "kebab menu");
                clickOnElement(buttonNewInternal, "New Internal user creation button");
                firstName = CCUtils.generateRandomStringWithoutSymbolsOrNumbers(12).concat("s");
                lastName = CCUtils.generateRandomStringWithoutSymbolsOrNumbers(12);
                email = CCUtils.generateNewEmail(12);
                userName = CCUtils.generateRandomStringWithoutSymbolsOrNumbers(12);
                commonMethods.enterFirstName(firstName);
                commonMethods.enterLastName(lastName);
                commonMethods.enterEmail(email);
                commonMethods.enterUserName(userName);
                addNewInternalUsersPage.addUserRoles();
                addNewInternalUsersPage.clickOnSaveAndAddButton();
                commonMethods.verifyToasterMessage("Success! New User has been successfully added");
            }
            if (userType.equalsIgnoreCase("VarReseller")) {
                clickOnElement(varResellerUserTab, "VarReseller Users Tab");
                clickOnElement(moreOptionsKebabForCreatingUser, "kebab menu");
                clickOnElement(buttonNewVarReseller, "New VarReseller user creation button");
                firstName = CCUtils.generateRandomStringWithoutSymbolsOrNumbers(12).concat("s");
                lastName = CCUtils.generateRandomStringWithoutSymbolsOrNumbers(12);
                email = CCUtils.generateNewEmail(12);
                userName = CCUtils.generateRandomStringWithoutSymbolsOrNumbers(12);
                mobileNumber = String.valueOf(CCUtils.generateMobileNumber());
                addNewVarResellerUserPage.clickVarResellerCombo(2);
                commonMethods.enterFirstName(firstName);
                commonMethods.enterLastName(lastName);
                commonMethods.enterEmail(email);
                commonMethods.enterUserName(userName);
                commonMethods.enterMobileNumber(mobileNumber);
                logStep("Verify active radio button is by default selected on add VAR/Reseller user page");
                commonMethods.verifyActiveRadioButtonByDefaultSelected();
                logStep("Select user role");
                addNewVarResellerUserPage.selectUserRole("Super");
                logStep("Click on save and add button");
                addNewVarResellerUserPage.clickOnSaveAndAdd();
                logStep("Verify success toaster message");
                commonMethods.verifyToasterMessage("Success! New user has been successfully added");
            }
            if (status.equalsIgnoreCase("pending")) {
                clickOnElement(searchOptionsButton, "search filter option");
                enterValue(firstNameTextBoxOnSearchFilter, firstName);
                clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button");
                clickOnElement(kebabMenuOnUserDetails, "kebab menu on user details");
                clickOnElement(sendEnrollmentEmail, "Send Enrollment Email button");
                clickOnElement(yesSendButton, "yes, send button");
                commonMethods.verifyToasterMessage("Success! Enrollment email has been sent");
                clickOnElement(backArrowOnUserDetail, "back button on user details");
            }
            if (status.equalsIgnoreCase("Expired"))
                log("Expired status user is not available to proceed!!");
        }
        logStep("Verify that " + userType +" Users are fetched to User search table in Bulk Enrollment Screen when Internal User Enrollment Status as Not-Sent, Pending and Expired");
        if (!status.equalsIgnoreCase("Expired") || count != 0) {
            if (count != 0)
                firstName = firstUserSearchValue.getText().trim();
            clickOnElement(clearAll, "Clear All button");
            clickOnElement(sendEnrollmentButton, "Bulk Enrollment button");
            clickOnElement(searchOptionsButton, "search filter option");
            if (status.equalsIgnoreCase("Not Sent")) {
                clickOnElement(notSentStatusCheckBox, "Not Sent check box");
            }
            else if (status.equalsIgnoreCase("Pending"))
                clickOnElement(pendingStatusCheckBox, "Pending check box");
            else if (status.equalsIgnoreCase("Expired"))
                clickOnElement(expiredStatusCheckBox, "Expired check box");
            clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button");
            checkEnrollmentStatus(status);
            clickOnElement(clearAll, "Clear All button");
            logStep("Verify the Cancel or (X) Send Enrollment Confirmation POP-UP Functionality in Bulk Enrollment Screen");
            clickOnElement(searchOptionsButton, "search filter option");
            enterValue(firstNameTextBoxOnSearchFilter, firstName);
            clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button");
            clickOnElement(selectAllUserOnPage, "select All users on page");
            clickOnElement(enrollButton, "Enrollment button");
            clickOnElement(cancelButtonOnSendEnrollmentConfirmation, "cancel button");
            verifyElementIsEnabled(enrollButton);
            logStep("Verify the Send Enrollment Functionality in Bulk Enrollment Screen, When " + userType +" User Enrollment Status as Not-Sent, Pending and Expired");
            clickOnElement(selectAllUserOnPage, "select All users on page");
            clickOnElement(enrollButton, "Enrollment button");
            clickOnElement(clearButtonOnSendEnrollmentConfirmation, "clear button");
            verifyElementIsEnabled(enrollButton);
            clickOnElement(selectAllUserOnPage, "select All users on page");
            clickOnElement(enrollButton, "Enrollment button");
            clickOnElement(yesSendButton, "yes, send button");
            commonMethods.verifyToasterMessage("Success! Enrollment email has been sent");
            clickOnElement(clearAll, "Clear All button");
            clickOnElement(searchOptionsButton, "search filter option");
            enterValue(firstNameTextBoxOnSearchFilter, firstName);
            if (status.equalsIgnoreCase("Not Sent"))
                clickOnElement(notSentStatusCheckBox, "Not Sent check box");
            else if (status.equalsIgnoreCase("Pending"))
                clickOnElement(pendingStatusCheckBox, "Pending check box");
            else if (status.equalsIgnoreCase("Expired"))
                clickOnElement(expiredStatusCheckBox, "Expired check box");
            clickOnElement(searchButtonOnSearchFilterOnUserPage, "Search button");
            if (!status.equalsIgnoreCase("Pending"))
                verifyElementPresence(noResultsFoundMessage, "No Results Found Message");
            if (status.equalsIgnoreCase("Pending"))
                checkEnrollmentStatus(status);
            clickOnElement(clearAll, "Clear All button");
            clickOnElement(bulkEnrollmentCancelButton, "Cancel button");
        }
    }
}
