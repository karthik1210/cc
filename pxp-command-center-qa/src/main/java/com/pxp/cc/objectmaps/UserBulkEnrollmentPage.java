// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.objectmaps;

import com.google.sitebricks.client.Web;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter

public class UserBulkEnrollmentPage extends CCMainPage {

    CommonMethods commonMethods;
    AddNewInternalUsersPage addNewInternalUsersPage;
    AddNewVarResellerUserPage addNewVarResellerUserPage;
    AddNewProviderPage addNewProviderPage;
    ClientDirectoryPage clientDirectoryPage;
    AddNewClientUserPage addNewClientUserPage;
    CommandCenterUsersPage commandCenterUsersPage;

    public UserBulkEnrollmentPage(WebDriver driver) {
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

    @FindBy(xpath = "//span[normalize-space(text())='inactive']")
    private WebElement inactiveFilter;

    @FindBy(id = "IconContainer")
    private WebElement moreOptionsKebabForCreatingUser;

    @FindBy(xpath = "//span[text()='CLEAR']")
    private WebElement clearButtonOnSearchFilterOnUserPage;

    @FindBy(xpath = "//span[text()='SEARCH']")
    private WebElement searchButtonOnSearchFilterOnUserPage;

    @FindBy(id = "addClientUser")
    private WebElement buttonNew;

    @FindBy(xpath = "(//input[@id='searchFilterInput'])[1]")
    private WebElement textboxSearchProviderName;

    @FindBy(id = "selectStatus")
    private WebElement textboxSearchClientName;

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

    @FindBy(xpath = "//button[normalize-space(text())='Yes, Send']/../..//span/span[text()='Cancel']")
    private WebElement cancelButtonOnSendEnrollmentConfirmation;

    @FindBy(xpath = "//span[normalize-space(text())='clear']")
    private WebElement clearButtonOnSendEnrollmentConfirmation;

    @FindBy(xpath = "//mat-select[@role='combobox']/div/div/div")
    private WebElement changeItemsDisplayPerPage;

    @FindBy(xpath = "//span[normalize-space(text())='Select users to send enrollment']")
    private WebElement selectUsersToSendEnrollmentLabel;

    public void changeItemsDisplayPerPage(int count) {
        log("Change display items per page to "+count);
        WebElement element = driver.findElement(By.xpath("//span[normalize-space(text())='"+count+"']"));
        element.click();
    }

    public boolean verifyTextBoxIsEmpty(String value) {
        if(value.equalsIgnoreCase("firstName")) {
            boolean result = firstNameTextBoxOnSearchFilter.getText().isEmpty();
            log("Textbox is empty");
            return result;
        }
        else {
            log("Textbox is not empty, it has some value");
            return false;
        }
    }

    public boolean isChecked(boolean value) {
        if(value) {
            CCUtils.assertEquals(driver.findElement(By.xpath("//input[@aria-checked='true']")).isDisplayed(), true);
            return true;
        }
        else {
            CCUtils.assertEquals(driver.findElement(By.xpath("//input[@aria-checked='false']")).isDisplayed(), true);
            return false;
        }

    }

    public void selectEnrollmentStatus(String status) {
        if(status.equalsIgnoreCase("Confirmed"))
            confirmedStatusCheckBox.click();
        else if(status.equalsIgnoreCase("Pending"))
            pendingStatusCheckBox.click();
        else if(status.equalsIgnoreCase("Expired"))
            expiredStatusCheckBox.click();
        else if(status.equalsIgnoreCase("Not Sent"))
            notSentStatusCheckBox.click();
    }

    public void countUsersOnUserSearchTable() throws InterruptedException {
        Thread.sleep(2000);
        List<WebElement> listOfStatus;
        List<String> enrollmentStatus = new ArrayList<String>();
        enrollmentStatus.add("Not Sent");
        enrollmentStatus.add("Pending");
        enrollmentStatus.add("Expired");
        listOfStatus = driver.findElements(By.xpath("//td/span[normalize-space(text())='"+enrollmentStatus.get(0)+"'] | //td[normalize-space(text())='"+enrollmentStatus.get(1)+"'] | //td[normalize-space(text())='"+enrollmentStatus.get(2)+"']"));
        if(listOfUsers.size() == listOfStatus.size()) {
            log("status = " + listOfStatus.size() + " and list of users = " + listOfUsers.size());
        }
        else {
            log("Error!! Need to be addressed");
            log("status = " + listOfStatus.size() + " and list of users = " + listOfUsers.size());
        }
    }

    public void checkEnrollmentStatus(String enrollmentStatus) throws InterruptedException {
        Thread.sleep(2000);
        List<WebElement> listOfStatus;
        if(enrollmentStatus.equalsIgnoreCase("Not Sent"))
            listOfStatus = driver.findElements(By.xpath("//td/span[normalize-space(text())='"+enrollmentStatus+"']"));
        else
            listOfStatus = driver.findElements(By.xpath("//td[normalize-space(text())='"+enrollmentStatus+"']"));
        if(listOfUsers.size() == listOfStatus.size()) {
            log("Status " + enrollmentStatus + " only fetched on User table");
            log("status = " + listOfStatus.size() + " and list of users = " + listOfUsers.size());
        }
        else {
            log("Error!! Need to be addressed");
            log("status = " + listOfStatus.size() + " and list of users = " + listOfUsers.size());
        }
    }

    //

    public boolean checkFirstNameOfUserOnUserSearchTable(String name) {
        return driver.findElement(By.xpath("//td[normalize-space(text())='"+name+"']")).isDisplayed();
    }

    public void clickOnElement(WebElement element, String elementName) {
        CCUtils.verifyAndWaitForElement(element, driver);
        log("clicking on "+elementName);
        CCUtils.focusOnElementAndClick(element, driver);
        log("Successfully clicked on "+elementName);
    }

    public void verifyElementPresence(WebElement element, String elementName) {
        CCUtils.scrollPageViaElement(driver, element);
        CCUtils.verifyAndWaitForElement(element, driver);
        log("verifying "+elementName+" is displayed");
        CCUtils.assertEquals(element.isDisplayed(), true);
        log("Successfully verified");
    }

    public void clickCommandCenterUserIcon() {
        CCUtils.verifyAndWaitForElement(commandCenterUsersIcon, driver);
        log("clicking on Command Center Users Icon");
        CCUtils.focusOnElementAndClick(commandCenterUsersIcon, driver);
        log("Successfully clicked on Command Center Users Icon");
    }

    public void clickSearchUserOnUserTable(String firstName) {
        driver.findElement(By.xpath("//td[normalize-space(text())='"+firstName+"']/following::span/following::td/span/mat-icon/following-sibling::mat-icon")).click();
    }

    public void clickInternalUsersTab() {
        CCUtils.verifyAndWaitForElement(internalUserTab, driver);
        log("clicking on Internal Users Tab");
        CCUtils.focusOnElementAndClick(internalUserTab, driver);
        log("Successfully clicked on Internal Users Tab");
    }

    public void clickClientUsersTab() {
        CCUtils.verifyAndWaitForElement(clientUserTab, driver);
        log("clicking on Client Users Tab");
        CCUtils.focusOnElementAndClick(clientUserTab, driver);
        log("Successfully clicked on Client Users Tab");
    }

    public void clickVarResellerUsersTab() {
        CCUtils.verifyAndWaitForElement(varResellerUserTab, driver);
        log("clicking on VarReseller Users Tab");
        CCUtils.focusOnElementAndClick(varResellerUserTab, driver);
        log("Successfully clicked on VarReseller Users Tab");
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
        List<WebElement> userTableSearchData = driver.findElements(By.xpath("//td/preceding-sibling::td//div"));
        if (userTableSearchData.isEmpty())
            log("No results found");
        else {
            userTableSearchData.get(0).click();
            log("Successfully Selected");
        }
    }

    public void doubleClickOnFirstUserOnUserTable() {
        log("Selecting First User On User Table");
        List<WebElement> userTableSearchData = driver.findElements(By.xpath("//td/preceding-sibling::td//div"));
        if (userTableSearchData.isEmpty())
            log("No results found");
        else {
            new Actions(driver).moveToElement(userTableSearchData.get(0)).doubleClick().build().perform();
            log("Successfully double clicked");
        }
    }



    public boolean verifyFirstNameLabel() {
        try {
            CCUtils.verifyAndWaitForElement(firstNameLabelOnSearchFilter, driver);
            log("verifying First name Label is displayed");
            CCUtils.assertEquals(firstNameLabelOnSearchFilter.isDisplayed(), true);
            log("Successfully verified");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyLastNameLabel() {
        try {
            CCUtils.verifyAndWaitForElement(lastNameLabelOnSearchFilter, driver);
            log("verifying Last name Label is displayed");
            CCUtils.assertEquals(lastNameLabelOnSearchFilter.isDisplayed(), true);
            log("Successfully verified");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyEmailLabel() {
        try {
            CCUtils.verifyAndWaitForElement(emailLabelOnSearchFilter, driver);
            log("verifying Email Label is displayed");
            CCUtils.assertEquals(emailLabelOnSearchFilter.isDisplayed(), true);
            log("Successfully verified");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyUserNameLabel() {
        try {
            CCUtils.verifyAndWaitForElement(usernameLabelOnSearchFilter, driver);
            log("verifying UserName Label is displayed");
            CCUtils.assertEquals(usernameLabelOnSearchFilter.isDisplayed(), true);
            log("Successfully verified");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyMobileNumberLabel() {
        try {
            CCUtils.verifyAndWaitForElement(mobileNumberLabelOnSearchFilter, driver);
            log("verifying Mobile Number Label is displayed");
            CCUtils.assertEquals(mobileNumberLabelOnSearchFilter.isDisplayed(), true);
            log("Successfully verified");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyFirstNameTextBox() {
        try {
            CCUtils.verifyAndWaitForElement(firstNameTextBoxOnSearchFilter, driver);
            log("verifying First name Text box is displayed");
            CCUtils.assertEquals(firstNameTextBoxOnSearchFilter.isDisplayed(), true);
            log("Successfully verified");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyLastNameTextBox() {
        try {
            CCUtils.verifyAndWaitForElement(lastNameTextBoxOnSearchFilter, driver);
            log("verifying Last name Text box is displayed");
            CCUtils.assertEquals(lastNameTextBoxOnSearchFilter.isDisplayed(), true);
            log("Successfully verified");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyEmailTextBox() {
        try {
            CCUtils.verifyAndWaitForElement(emailTextBoxOnSearchFilter, driver);
            log("verifying Email Text box is displayed");
            CCUtils.assertEquals(emailTextBoxOnSearchFilter.isDisplayed(), true);
            log("Successfully verified");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyUserNameTextBox() {
        try {
            CCUtils.verifyAndWaitForElement(usernameTextBoxOnSearchFilter, driver);
            log("verifying user name Text box is displayed");
            CCUtils.assertEquals(usernameTextBoxOnSearchFilter.isDisplayed(), true);
            log("Successfully verified");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyMobileNumberTextBox() {
        try {
            CCUtils.verifyAndWaitForElement(mobileNumberTextBoxOnSearchFilter, driver);
            log("verifying Mobile number Text box is displayed");
            CCUtils.assertEquals(mobileNumberTextBoxOnSearchFilter.isDisplayed(), true);
            log("Successfully verified");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifyEnrollmentStatusTitle() {
        try {
            CCUtils.verifyAndWaitForElement(enrollmentStatusTitleOnSearchFilter, driver);
            log("verifying Enrollment Status Title is displayed");
            CCUtils.assertEquals(enrollmentStatusTitleOnSearchFilter.isDisplayed(), true);
            log("Successfully verified");
            return true;
        } catch (Exception exception) {
            return false;
        }
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

    public boolean verifyClearButton() {
        try {
            CCUtils.verifyAndWaitForElement(clearButtonOnSearchFilterOnUserPage, driver);
            log("verifying Clear Button is displayed");
            CCUtils.assertEquals(clearButtonOnSearchFilterOnUserPage.isDisplayed(), true);
            log("Successfully verified");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean verifySearchButton() {
        try {
            CCUtils.verifyAndWaitForElement(searchButtonOnSearchFilterOnUserPage, driver);
            log("verifying Search Button is displayed");
            CCUtils.assertEquals(searchButtonOnSearchFilterOnUserPage.isDisplayed(), true);
            log("Successfully verified");
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public void clickOnNewButton() {
        CCUtils.verifyAndWaitForElement(buttonNew, driver);
        buttonNew.click();
        log("Clicked on new button in command center users page");
    }

    public void enterValue(WebElement element, String value) {
        CCUtils.verifyAndWaitForElement(element, driver);
        CCUtils.enterValueOnTextField(element, value);
    }

    public void clickOnSuggestion(String clientName) {
        WebElement element = driver.findElement(By.xpath("//b[text()='"+clientName+"']"));
        new Actions(driver).moveToElement(element).pause(1000).click().build().perform();
    }

    public void enterProviderName(String providerName) {
        CCUtils.verifyAndWaitForElement(textboxSearchProviderName, driver);
        textboxSearchProviderName.click();
        textboxSearchProviderName.sendKeys(providerName);
        log("Entered input for search provider field : " + providerName);
    }

    public void enterClientName(String clientName) throws InterruptedException {
        CCUtils.verifyAndWaitForElement(textboxSearchClientName, driver);
        textboxSearchClientName.sendKeys(clientName);
        log("Entered input for client field : " + clientName);
        textboxSearchClientName.clear();
        textboxSearchClientName.sendKeys(clientName);
        Thread.sleep(6000);
    }


    public void searchProvider(String providerName, String clientName) throws NullPointerException, InterruptedException {
        enterProviderName(providerName);
        clickOnSuggestion(clientName);
        //CCUtils.pressEnterKey();
    }

    public void searchClient(String clientName) throws InterruptedException {
        enterClientName(clientName);
        clickOnSuggestion(clientName);
        CCUtils.pressEnterKey();
    }

    public HashMap<String, Boolean> verifyElementsInSearchOptions() {
        HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        result.put("verifyFirstNameLabel", verifyFirstNameLabel());
        result.put("verifyLastNameLabel", verifyLastNameLabel());
        result.put("verifyEmailLabel", verifyEmailLabel());
        result.put("verifyUserNameLabel", verifyUserNameLabel());
        result.put("verifyMobileNumberLabel", verifyMobileNumberLabel());
        result.put("verifyFirstNameTextBox", verifyFirstNameTextBox());
        result.put("verifyLastNameTextBox", verifyLastNameTextBox());
        result.put("verifyEmailTextBox", verifyEmailTextBox());
        result.put("verifyUserNameTextBox", verifyUserNameTextBox());
        result.put("verifyMobileNumberTextBox", verifyMobileNumberTextBox());
        result.put("verifyEnrollmentStatusTitle", verifyEnrollmentStatusTitle());
        result.put("verifyAllEnrollmentStatusesAndCheckboxes", verifyEnrollmentStatusesAndCheckBox());
        result.put("verifyClearButton", verifyClearButton());
        result.put("verifySearchButton", verifySearchButton());
        return result;
    }

    public String testCreateActiveInternalUserWithRoles() throws Exception {
        logStep("Create a new active internal user with user roles and verify success toaster");
        commonMethods = new CommonMethods(driver);
        clientDirectoryPage = new ClientDirectoryPage(driver);
        clientDirectoryPage.clickOnCommandCenterUsersIcon();
        addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
        clickInternalUsersTab();
        javascriptClick(moreOptionsKebabForCreatingUser);
        javascriptClick(buttonNew);
        String firstName = CCUtils.generateAlphaNumericString(12).concat("'s");
        commonMethods.enterFirstName(firstName);
        commonMethods.enterLastName(CCUtils.generateAlphaNumericString(12));
        commonMethods.enterEmail(CCUtils.generateNewEmail(12));
        commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(12));
        addNewInternalUsersPage.addUserRoles();
        addNewInternalUsersPage.clickOnSaveAndAddButton();
        commonMethods.verifyToasterMessage("Success! New User has been successfully added");
        return firstName;
    }

    public String testCreateInactiveInternalUserWithRoles() throws Exception {
        logStep("Create a new inactive internal user with user roles and verify success toaster");
        commonMethods = new CommonMethods(driver);
        clientDirectoryPage = new ClientDirectoryPage(driver);
        clientDirectoryPage.clickOnCommandCenterUsersIcon();
        addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
        clickInternalUsersTab();
        javascriptClick(moreOptionsKebabForCreatingUser);
        javascriptClick(buttonNew);
        String firstName = "Inactive" + CCUtils.generateAlphaNumericString(12).concat("'s");
        commonMethods.enterFirstName(firstName);
        commonMethods.enterLastName(CCUtils.generateAlphaNumericString(3).concat("'s"));
        commonMethods.enterEmail(CCUtils.generateNewEmail(10));
        commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(6));
        commonMethods.selectInactiveRadiobutton();
        addNewInternalUsersPage.addUserRoles();
        addNewInternalUsersPage.clickOnSaveAndAddButton();
        commonMethods.verifyToasterMessage("Success! New User has been successfully added");
        return firstName;
    }

    public String testCreateActiveInternalUserWithoutRole() {
        logStep("Create a new active internal user with user roles and verify success toaster");
        commonMethods = new CommonMethods(driver);
        clientDirectoryPage = new ClientDirectoryPage(driver);
        clientDirectoryPage.clickOnCommandCenterUsersIcon();
        clickInternalUsersTab();
        javascriptClick(moreOptionsKebabForCreatingUser);
        javascriptClick(buttonNew);
        String firstName = "NoRoles"+CCUtils.generateAlphaNumericString(12).concat("'s");
        commonMethods.enterFirstName(firstName);
        commonMethods.enterLastName(CCUtils.generateAlphaNumericString(12));
        commonMethods.enterEmail(CCUtils.generateNewEmail(12));
        commonMethods.enterUserName(CCUtils.generateAlphaNumericString(20));
        addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
        addNewInternalUsersPage.clickOnSaveAndAddButton();
        addNewInternalUsersPage.clickOnSaveAndCreateButton();
        commonMethods.verifyToasterMessage("Success! New User has been successfully added");
        return firstName;
    }

    public String testCreateActiveVarResellerUserWithRoles(String varResellerUserForSearch, String userRole) {
        logStep("Enter data in mandatory fields");
        clientDirectoryPage = new ClientDirectoryPage(driver);
        clientDirectoryPage.clickOnCommandCenterUsersIcon();
        addNewVarResellerUserPage = new AddNewVarResellerUserPage(driver);
        clickVarResellerUsersTab();
        javascriptClick(moreOptionsKebabForCreatingUser);
        javascriptClick(buttonNew);
        addNewVarResellerUserPage.searchAndSelectVarReseller(varResellerUserForSearch);
        String firstName = CCUtils.generateAlphaNumericString(18);
        commonMethods.enterFirstName(firstName);
        commonMethods.enterLastName(CCUtils.generateAlphaNumericString(18).concat("'s"));
        commonMethods.enterEmail(CCUtils.generateNewEmail(11));
        commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
        commonMethods.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
        logStep("Verify active radio button is by default selected on add VAR/Reseller user page");
        commonMethods.verifyActiveRadioButtonByDefaultSelected();
        logStep("Select user role");
        addNewVarResellerUserPage.selectUserRole(userRole);
        logStep("Click on save and add button");
        addNewVarResellerUserPage.clickOnSaveAndAdd();
        logStep("Verify success toaster message");
        commonMethods.verifyToasterMessage("Success! New user has been successfully added");
        return firstName;
    }

    public String testCreateInactiveVarResellerUserWithRoles(String varResellerUserForSearch, String userRole) {
        logStep("Enter data in mandatory fields");
        clientDirectoryPage = new ClientDirectoryPage(driver);
        clientDirectoryPage.clickOnCommandCenterUsersIcon();
        addNewVarResellerUserPage = new AddNewVarResellerUserPage(driver);
        clickVarResellerUsersTab();
        javascriptClick(moreOptionsKebabForCreatingUser);
        javascriptClick(buttonNew);
        addNewVarResellerUserPage.searchAndSelectVarReseller(varResellerUserForSearch);
        String firstName = "Inactive" + CCUtils.generateAlphaNumericString(18);
        commonMethods.enterFirstName(firstName);
        commonMethods.enterLastName(CCUtils.generateAlphaNumericString(18).concat("'s"));
        commonMethods.enterEmail(CCUtils.generateNewEmail(11));
        commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
        commonMethods.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
        logStep("Select inactive status");
        commonMethods.selectInactiveRadiobutton();
        logStep("Select user role");
        addNewVarResellerUserPage.selectUserRole(userRole);
        logStep("Click on save and add button");
        addNewVarResellerUserPage.clickOnSaveAndAdd();
        logStep("Verify success toaster message");
        commonMethods.verifyToasterMessage("Success! New user has been successfully added");
        return firstName;
    }

    public String testCreateVarResellerUserWithoutUserRole(String varResellerUserForSearch) {
        logStep("Enter data in mandatory fields");
        clientDirectoryPage = new ClientDirectoryPage(driver);
        clientDirectoryPage.clickOnCommandCenterUsersIcon();
        addNewVarResellerUserPage = new AddNewVarResellerUserPage(driver);
        clickVarResellerUsersTab();
        javascriptClick(moreOptionsKebabForCreatingUser);
        javascriptClick(buttonNew);
        addNewVarResellerUserPage.searchAndSelectVarReseller(varResellerUserForSearch);
        commonMethods = new CommonMethods(driver);
        String firstName = "NoRoles"+CCUtils.generateAlphaNumericString(18).concat("'s");
        commonMethods.enterFirstName(firstName);
        commonMethods.enterLastName(CCUtils.generateAlphaNumericString(18));
        commonMethods.enterEmail(CCUtils.generateNewEmail(11));
        commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
        commonMethods.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
        logStep("Verify active radio button is by default selected on add VAR/Reseller user page");
        commonMethods.verifyActiveRadioButtonByDefaultSelected();
        logStep("Click on save and add button");
        addNewVarResellerUserPage.clickOnSaveAndAdd();
        logStep("Verify success toaster message");
        commonMethods = new CommonMethods(driver);
        commonMethods.verifyToasterMessage("Success! New user has been successfully added");
        return firstName;
    }

    public String createProvider() throws NullPointerException, InterruptedException {
        logStep("Verify Providers And Resources tab is displayed");
        GeneralSettingsPage generalSettingsPage = new GeneralSettingsPage(driver);
        addNewProviderPage = new AddNewProviderPage(driver);
        generalSettingsPage.verifyProvidersAndResourcesTab();
        generalSettingsPage.clickOnProvidersAndResourcesTab();
        addNewProviderPage.verifyProvidersAndResourcesTitle();
        logStep("Create Provider");
        String firstName = CCUtils.generateAlphaNumericString(5);
        addNewProviderPage.addProvider(firstName);
        commonMethods = new CommonMethods(driver);
        commonMethods.verifyToasterMessage("Success! New Provider has been successfully created");
        addNewProviderPage.clickSearchIcon();
        addNewProviderPage.enterSearchText(firstName);
        CCUtils.pressEnterKey();
        addNewProviderPage.selectProvider();
        addNewProviderPage.editProvider();
        addNewProviderPage.verifyProvidersAndResourcesTitle();
        return firstName;
    }

    public String createActiveClientUser(String clientName, String firstName) throws NullPointerException, InterruptedException {
        logStep("Select command center users icon");
        clientDirectoryPage = new ClientDirectoryPage(driver);
        clientDirectoryPage.clickOnCommandCenterUsersIcon();
        clickClientUsersTab();
        javascriptClick(moreOptionsKebabForCreatingUser);
        javascriptClick(buttonNew);
        addNewClientUserPage = new AddNewClientUserPage(driver);
        logStep("Enter data in mandatory fields and Click on next button");
        searchClient(clientName);
        addNewProviderPage.searchProvider(firstName);
        addNewClientUserPage.clickOnNextButton();
        logStep("Select entites");
        addNewClientUserPage.clickOnIndeterminateCheckbox();
        logStep("Click on save and add button");
        addNewClientUserPage.clickOnSaveAndAdd();
        logStep("Verify success toaster message");
        commonMethods = new CommonMethods(driver);
        commonMethods.verifyToasterMessage("Success! New user has been successfully created");
        return firstName;
    }

    public String createInactiveClientUser(String clientName, String firstName) throws NullPointerException, InterruptedException {
        logStep("Select command center users icon");
        clientDirectoryPage = new ClientDirectoryPage(driver);
        clientDirectoryPage.clickOnCommandCenterUsersIcon();
        clickClientUsersTab();
        javascriptClick(moreOptionsKebabForCreatingUser);
        javascriptClick(buttonNew);
        logStep("Enter data in mandatory fields and Click on next button");
        addNewClientUserPage = new AddNewClientUserPage(driver);
        searchClient(clientName);
        addNewProviderPage.searchProvider(firstName);
        addNewClientUserPage.clickOnInactiveStatus();
        addNewClientUserPage.clickOnNextButton();
        logStep("Select entites");
        addNewClientUserPage.clickOnIndeterminateCheckbox();
        logStep("Click on save and add button");
        addNewClientUserPage.clickOnSaveAndAdd();
        logStep("Verify success toaster message");
        commonMethods = new CommonMethods(driver);
        commonMethods.verifyToasterMessage("Success! New user has been successfully created");
        return firstName;
    }
}