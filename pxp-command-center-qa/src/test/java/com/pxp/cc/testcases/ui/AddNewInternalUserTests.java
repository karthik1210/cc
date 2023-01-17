// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddNewInternalUsersPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.InternalUsersPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

import static com.pxp.cc.testcases.ui.CCUI.getClientSystementErpriseandPractice;

public class AddNewInternalUserTests extends CommandCenterGenericTest {
    public CCUI ccui;
    String firstName = CCUtils.generateAlphaNumericString(12).concat("'s");
    String lastName = CCUtils.generateAlphaNumericString(12);
    String email = CCUtils.generateNewEmail(12);
    String userName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(12);
    long mobileNumber = CCUtils.generateMobileNumber();

    @BeforeMethod(alwaysRun = true)
    public void loginToCommandCenterPortal() throws Exception {
        ccui = new CCUI(testData, driver);
        logStep("Login to command center portal");
        ccui.getClientDirectoryPage();
        navigatetoInternalUser();
    }

    @DataProvider(name = "invalidTestData")
    public Object[] getInvalidTestData() {
        Object[][] obj = new Object[][]{{testData.getProperty("invalid.user.first.name.one"),
                testData.getProperty("invalid.user.last.name.one"), testData.getProperty("invalid.user.email.one"),
                testData.getProperty("invalid.username.one"), testData.getProperty("invalid.user.mobile.number.one")},
                {testData.getProperty("invalid.user.first.name.two"),
                        testData.getProperty("invalid.user.last.name.two"),
                        testData.getProperty("invalid.user.email.two"), testData.getProperty("invalid.username.two"),
                        testData.getProperty("invalid.user.mobile.number.two")}};
        return obj;
    }


    @Test(enabled = false)
    public void navigatetoInternalUser() throws Exception {
        logStep("Click on command center users icon in left side navigation panel");
        ccui.getClientDirectoryPage().clickOnCommandCenterUsersIcon();
        logStep("Verify user redirected to command center users page");
        ccui.getCommandCenterUsersPage().verifyUserRedirectedToCommandCenterPage();
        logStep("Verify internal users tab is by default selected");
        ccui.getCommandCenterUsersPage().verifyInternalUsersTabByDefaultSelected();
        logStep("Click on more options button");
        ccui.getInternalUsersPage().clickToMoreOptions();
        logStep("Click on new button");
        ccui.getInternalUsersPage().clickOnNewButton();
    }

    @Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 1, description = "Verify add new internal user popup")
    public void testAddNewInternalUserPopup() throws Exception {
        logStep("Verify add new internal user popup");
        ccui.getAddNewInternalUsersPage().verifyAddNewInternalUserPageTitle();
        ccui.getCommonMethods().verifyFirstNameLabelTextAndTextboxDefaultValue();
        ccui.getCommonMethods().verifyLastNameLabelTextAndTextboxDefaultValue();
        ccui.getCommonMethods().verifyUsernameLabelTextAndTextboxDefaultValue();
        ccui.getCommonMethods().verifyEmailLabelTextAndTextboxDefaultValue();
        ccui.getCommonMethods().verifyMobileNumberLabelTextAndTextboxDefaultValue();
        ccui.getCommonMethods().verifyStatusLabelTextAndRadioButtons();
        ccui.getCommonMethods().verifyActiveRadioButtonByDefaultSelected();
        ccui.getAddNewInternalUsersPage().verifyUserRoles();
        ccui.getAddNewInternalUsersPage().verifyCrossIconIsDisplayed();
        ccui.getAddNewInternalUsersPage().verifyCancelButtonIsDisplayed();
        ccui.getAddNewInternalUsersPage().verifySaveAndAddButtonIsDisplayed();
    }

    @Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 2, description = "Verify add new active internal user(without user role) functionality")
    public void testCreateActiveInternalUserWithoutRole() throws Exception {
        logStep("Create a new active internal user with user roles and verify success toaster");
        String firstName = CCUtils.generateAlphaNumericString(12).concat("'s");
        ccui.getCommonMethods().enterFirstName(firstName);
        ccui.getCommonMethods().enterLastName(CCUtils.generateAlphaNumericString(12));
        ccui.getCommonMethods().enterEmail(CCUtils.generateNewEmail(12));
        ccui.getCommonMethods().enterUserName(CCUtils.generateAlphaNumericString(20));
        ccui.getAddNewInternalUsersPage().clickOnSaveAndAddButton();
        ccui.getAddNewInternalUsersPage().clickOnCautionYes();
        ccui.getCommonMethods().verifyToasterMessage("Success! New User has been successfully added");
    }

    @Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 3, description = "Verify add new inactive internal user(without user role) functionality")
    public void testCreateInactiveInternalUserWithoutRole() throws Exception {
        logStep("Create a new inactive internal user without adding user role and verify success toaster");
        String firstName = CCUtils.generateAlphaNumericString(12).concat("'s");
        ccui.getCommonMethods().enterFirstName(firstName);
        ccui.getCommonMethods().enterLastName(lastName);
        ccui.getCommonMethods().enterEmail(CCUtils.generateNewEmail(12));
        ccui.getCommonMethods().enterUserName(CCUtils.generateAlphaNumericString(20));
        ccui.getCommonMethods().selectInactiveRadiobutton();
        ccui.getAddNewInternalUsersPage().clickOnSaveAndAddButton();
        ccui.getAddNewInternalUsersPage().clickOnCautionYes();
        ccui.getCommonMethods().verifyToasterMessage("Success! New User has been successfully added");
    }

    @Test(enabled = true, groups = {"buildvalidation"}, priority = 4, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new active internal user(with user roles) functionality")
    public void testCreateActiveInternalUserWithRoles() throws Exception {
        logStep("Create a new active internal user with user roles and verify success toaster");
        ccui.getCommonMethods().enterFirstName(firstName);
        ccui.getCommonMethods().enterLastName(lastName);
        ccui.getCommonMethods().enterEmail(testData.getProperty("valid.user.first.name").concat(CCUtils.generateNewEmail(12)));
        ccui.getCommonMethods().enterUserName(testData.getProperty("new.client.name2").concat(CCUtils.generateAlphaNumericString(20)));
        ccui.getCommonMethods().enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
        ccui.getAddNewInternalUsersPage().addUserRoles();
        ccui.getAddNewInternalUsersPage().clickOnSaveAndAddButton();
        ccui.getCommonMethods().verifyToasterMessage("Success! New User has been successfully added");
    }

    @Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 5, description = "Verify add new inactive internal user(with user roles) functionality")
    public void testCreateInactiveInternalUserWithRoles() throws Exception {
        logStep("Create a new inactive internal user with user roles and verify success toaster");
        ccui.getCommonMethods().enterFirstName(CCUtils.generateAlphaNumericString(9));
        ccui.getCommonMethods().enterLastName(CCUtils.generateAlphaNumericString(3).concat("'s"));
        ccui.getCommonMethods().enterEmail(CCUtils.generateNewEmail(10));
        ccui.getCommonMethods().enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(6));
        ccui.getCommonMethods().selectInactiveRadiobutton();
        ccui.getAddNewInternalUsersPage().addUserRoles();
        ccui.getAddNewInternalUsersPage().clickOnSaveAndAddButton();
        ccui.getCommonMethods().verifyToasterMessage("Success! New User has been successfully added");
    }

    @Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 6, description = "Verify required error message is displayed for mandatory fields(First Name, Last Name, Username and Email)")
    public void testErrorMessageForMandatoryFields() throws Exception {
        logStep("Click on save & add button");
        ccui.getAddNewInternalUsersPage().clickOnSaveAndAddButton();
        logStep("Verify required error message is displayed for mandatory fields");
        CCUtils.assertEquals(ccui.getCommonMethods().getRequiredErrorForFirstNameField(), "First name is required");
        CCUtils.assertEquals(ccui.getCommonMethods().getRequiredErrorForLastNameField(), "Last name is required");
        CCUtils.assertEquals(ccui.getCommonMethods().getRequiredErrorForEmailField(), "Email is required");
        CCUtils.assertEquals(ccui.getCommonMethods().getRequiredErrorForUsernameField(), "Username is required");
    }

    @Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 7, description = "Enter more than 100 characters in firstname, last name,username and email field and verify error message")
    public void testMaximumCharactersErrorMessageForInputFields() throws Exception {
        logStep("Enter more than 100 characters in first name, last name, username, email and mobile number field and verify maximum characters error message");
        ccui.getCommonMethods().enterFirstName(CCUtils.generateAlphaNumericString(101));
        CCUtils.assertEquals(ccui.getCommonMethods().getMaximumCharacterErrorForFirstNameField(), "Maximum characters reached");
        ccui.getCommonMethods().enterLastName(CCUtils.generateAlphaNumericString(101));
        CCUtils.assertEquals(ccui.getCommonMethods().getMaximumCharacterErrorForLastNameField(), "Maximum characters reached");
        ccui.getCommonMethods().enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(101));
        CCUtils.assertEquals(ccui.getCommonMethods().getMaximumCharacterErrorForUsernameField(), "Maximum characters reached");
        ccui.getCommonMethods().enterEmail(CCUtils.generateAlphaNumericString(101).concat("@gmail.com"));
        CCUtils.assertEquals(ccui.getCommonMethods().getMaximumCharacterErrorForEmailField(), "Maximum characters reached");
    }

    @Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 8, dependsOnMethods = {"testCreateActiveInternalUserWithRoles"}, description = "Enter already existing username, email and mobile number on add new internal user page and verify error message.")
    public void testUniqueErrorMessage() throws Exception {
        logStep("Enter existing value in username, email and mobile number fields and verify error message");
        ccui.getCommonMethods().enterFirstName(firstName);
        ccui.getCommonMethods().enterLastName(lastName);
        ccui.getCommonMethods().enterEmail(email);
        ccui.getCommonMethods().enterUserName(userName);
        ccui.getCommonMethods().enterMobileNumber(String.valueOf(mobileNumber));
        ccui.getCommonMethods().focusOnInactiveRadioButton();
        CCUtils.assertEquals(ccui.getCommonMethods().getDuplicateErrorForMobileNumberField(), "Mobile number already in use");
        CCUtils.assertEquals(ccui.getCommonMethods().getDuplicateErrorForEmailField(), "Email already in use");
        CCUtils.assertEquals(ccui.getCommonMethods().getDuplicateErrorForUsernameField(), "Username already in use");
    }

    @Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 9, dataProvider = "invalidTestData", description = "Enter invalid data in first name, last name, email, username and mobile number field and verify error message")
    public void testEnterValidDataErrorMessage(String[] getTestData) throws Exception {
        logStep("Enter invalid data in first name, last name, email,username and mobile number fields and verify error message");
        ccui.getCommonMethods().enterFirstName(getTestData[0]);
        CCUtils.assertEquals(ccui.getCommonMethods().getValidErrorForFirstNameField(), "Please enter valid first name");
        ccui.getCommonMethods().enterLastName(getTestData[1]);
        CCUtils.assertEquals(ccui.getCommonMethods().getValidErrorForLastNameField(), "Please enter valid last name");
        ccui.getCommonMethods().enterEmail(getTestData[2]);
        CCUtils.assertEquals(ccui.getCommonMethods().getValidErrorForEmailField(), "Please enter valid email address");
        ccui.getCommonMethods().enterUserName(getTestData[3]);
        CCUtils.assertEquals(ccui.getCommonMethods().getValidErrorForUsernameField(), "Please enter valid username");
        ccui.getCommonMethods().enterMobileNumber(getTestData[4]);
        ccui.getCommonMethods().focusOnInactiveRadioButton();
        CCUtils.assertEquals(ccui.getCommonMethods().getValidErrorForMobileNumberField(), "Please enter valid mobile number");
    }

    @Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 10, description = "Verify username is generated using beginning of email")
    public void testUsernameGeneratedFromEmail() throws Exception {
        logStep("Verify username is generated using beginning of email");
        ccui.getCommonMethods().verifyUsernameGeneratedUsingBeginningOfEmail(CCUtils.generateNewEmail(20));
    }

    @Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 11, description = "Verify functionality of cancel and cross icon")
    public void testCancelAndCrossIconFunctionality() throws Exception {
        logStep("Click on cancel button");
        ccui.getAddNewInternalUsersPage().clickOnCancelButton();
        logStep("Verify after clicking on cancel button user redirected to internal users page");
        ccui.getInternalUsersPage().verifyInternalUsersPageTitle();
        logStep("Click on more options button");
        ccui.getInternalUsersPage().clickToMoreOptions();
        logStep("Click on new button");
        ccui.getInternalUsersPage().clickOnNewButton();
        logStep("Click on cross button");
        ccui.getAddNewInternalUsersPage().clickOnCrossIcon();
        logStep("Verify after clicking on cross button user redirected to internal users page");
        ccui.getInternalUsersPage().verifyInternalUsersPageTitle();
    }

}
