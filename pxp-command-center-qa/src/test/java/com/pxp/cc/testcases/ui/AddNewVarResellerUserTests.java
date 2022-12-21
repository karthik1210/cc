// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddNewVarResellerUserPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.VarResellerUsersPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;


public class AddNewVarResellerUserTests extends CommandCenterGenericTest {
    public CCUI ccui;
	String firstName = CCUtils.generateAlphaNumericString(12).concat("'s");
	String lastName=CCUtils.generateAlphaNumericString(12);
	String email=CCUtils.generateNewEmail(12);
	String userName=CCUtils.generateAlphaNumericStringIncludingSpecialChar(12);
	long mobileNumber=CCUtils.generateMobileNumber();

    @BeforeMethod(alwaysRun = true)
    public void loginToCommandCenterPortal() throws Exception {
        ccui = new CCUI(testData, driver);
        logStep("Login to command center portal");
        ccui.getClientDirectoryPage();
        navigatetoVARResellerUser();
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
    public void navigatetoVARResellerUser() throws Exception {
        logStep("Select command center users icon in left navigation panel");
        ccui.getClientDirectoryPage().clickOnCommandCenterUsersIcon();
        logStep("Select VAR/Reseller users tab in command center users page");
        ccui.getCommandCenterUsersPage().clickOnVarResellerUsersTab();
        ccui.getVarResellerUsersPage().verifyVarResellerUsersPageTitle();
        logStep("Click on new button");
        ccui.getVarResellerUsersPage().clickOnNewButton();
    }

    @Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 1, description = "Verify add VAR/Reseller user popup")
    public void testAddVarResellerUserPopup() throws Exception {
        logStep("Verify add VAR/Reseller user popup");
        ccui.getAddNewVarResellerUserPage().verifyAddNewVarResellerUserPageTitle();
        ccui.getAddNewVarResellerUserPage().VerifyHeaderText();
        ccui.getAddNewVarResellerUserPage().verifyVarResellerLabelTextAndSearchTextboxDefaultValue();
        ccui.getCommonMethods().verifyFirstNameLabelTextAndTextboxDefaultValue();
        ccui.getCommonMethods().verifyLastNameLabelTextAndTextboxDefaultValue();
        ccui.getCommonMethods().verifyUsernameLabelTextAndTextboxDefaultValue();
        ccui.getCommonMethods().verifyEmailLabelTextAndTextboxDefaultValue();
        ccui.getCommonMethods().verifyMobileNumberLabelTextAndTextboxDefaultValue();
        ccui.getAddNewVarResellerUserPage().verifyStatusLabelTextAndRadioButtons();
        ccui.getAddNewVarResellerUserPage().verifyActiveRadioButtonByDefaultSelected();
        ccui.getAddNewVarResellerUserPage().verifyUserRolesLabelTextAndRadioButtons();
        ccui.getAddNewVarResellerUserPage().verifyCloseWindowButtonIsDisplayed();
        ccui.getAddNewVarResellerUserPage().verifyCancelButtonIsDisplayed();
        ccui.getAddNewVarResellerUserPage().verifySaveAndAddButtonIsDisplayed();
    }

	@Test(enabled = true, groups = {"buildvalidation"}, retryAnalyzer = RetryAnalyzer.class, priority = 2, description = "Verify add new active VAR/Reseller user functionality")
	public void testActiveVarResellerUser() throws Exception {
		logStep("Enter data in mandatory fields");
		ccui.getAddNewVarResellerUserPage().clickVarResellerCombo(2);
		ccui.getCommonMethods().enterFirstName(firstName);
		ccui.getCommonMethods().enterLastName(lastName);
		ccui.getCommonMethods().enterEmail(email);
		ccui.getCommonMethods().enterUserName(userName);
		ccui.getCommonMethods().enterMobileNumber(String.valueOf(mobileNumber));
		logStep("Verify active radio button is by default selected on add VAR/Reseller user page");
		ccui.getCommonMethods().verifyActiveRadioButtonByDefaultSelected();
		logStep("Select user role");
		ccui.getAddNewVarResellerUserPage().selectUserRole("Super");
		logStep("Click on save and add button");
		ccui.getAddNewVarResellerUserPage().clickOnSaveAndAdd();
		logStep("Verify success toaster message");
		ccui.getCommonMethods().verifyToasterMessage("Success! New user has been successfully added");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 3, description = "Verify add new inactive VAR/Reseller user functionality")
	public void testInactiveVarResellerUser() throws Exception {
		logStep("Enter data in mandatory fields");
		ccui.getAddNewVarResellerUserPage().clickVarResellerCombo(1);
		ccui.getCommonMethods().enterFirstName(CCUtils.generateAlphaNumericString(18));
		ccui.getCommonMethods().enterLastName(
				CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(5)));
		ccui.getCommonMethods().enterEmail(CCUtils.generateNewEmail(11));
		ccui.getCommonMethods().enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		ccui.getCommonMethods().enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
		logStep("Select inactive status");
		ccui.getCommonMethods().selectInactiveRadiobutton();
		logStep("Select user role");
		ccui.getAddNewVarResellerUserPage().selectUserRole("Solution");
		logStep("Click on save and add button");
		ccui.getAddNewVarResellerUserPage().clickOnSaveAndAdd();
		logStep("Verify success toaster message");
		ccui.getCommonMethods().verifyToasterMessage("Success! New user has been successfully added");
	}

    @Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 4, description = "Verify required error message for mandatory fields in add VAR/Reseller user page")
    public void testRequiredErrorMessage() throws Exception {
        logStep("Enter valid data in VAR/Reseller, first name, last name, username, email and mobile number fiels");
        ccui.getAddNewVarResellerUserPage().clickOnSaveAndAdd();
        CCUtils.assertEquals(ccui.getAddNewVarResellerUserPage().getRequiredErrorForVarResellerField(),
                "VAR/Reseller User is required");
        CCUtils.assertEquals(ccui.getCommonMethods().getRequiredErrorForFirstNameField(), "First name is required");
        CCUtils.assertEquals(ccui.getCommonMethods().getRequiredErrorForLastNameField(), "Last name is required");
        CCUtils.assertEquals(ccui.getCommonMethods().getRequiredErrorForEmailField(), "Email is required");
        CCUtils.assertEquals(ccui.getCommonMethods().getRequiredErrorForUsernameField(), "Username is required");
    }

		@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class,  priority = 5, description = "Verify the functionality of cancel button and close window button on add VAR/Reseller user page")
	public void testFunctionalityOfCancelAndCrossButton() throws Exception {
		logStep("Click on cancel button and verify user redirected to VAR/reseller users page");
		ccui.getAddNewVarResellerUserPage().clickOnCancelButton();
		ccui.getVarResellerUsersPage().verifyVarResellerUsersPageTitle();
		logStep("Click on new button");
			ccui.getVarResellerUsersPage().clickOnNewButton();
		logStep("Click on close window button and verify user redirected to VAR/reseller users page");
		ccui.getAddNewVarResellerUserPage().clickOnCloseWindowButton();
			ccui.getVarResellerUsersPage().verifyVarResellerUsersPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class,  priority = 6, description = "Verify maximum characters error message for first name, last name, username and email field")
	public void testMaximumCharacterError() throws Exception {
		logStep("Enter more than 100 characters in first name, last name, username, email field and verify maximum characters error message");
		ccui.getCommonMethods().enterFirstName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(ccui.getCommonMethods().getMaximumCharacterErrorForFirstNameField(), "Maximum characters reached");
		ccui.getCommonMethods().enterLastName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(ccui.getCommonMethods().getMaximumCharacterErrorForLastNameField(), "Maximum characters reached");
		ccui.getCommonMethods().enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(101));
		CCUtils.assertEquals(ccui.getCommonMethods().getMaximumCharacterErrorForUsernameField(), "Maximum characters reached");
		ccui.getCommonMethods().enterEmail(CCUtils.generateAlphaNumericString(101).concat("@gmail.com"));
		CCUtils.assertEquals(ccui.getCommonMethods().getMaximumCharacterErrorForEmailField(), "Maximum characters reached");
	}

   	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 7, dataProvider = "invalidTestData", description = "Enter invalid data in first name, last name, email,username and mobile number fields and verify error message")
	public void testEnterValidDetailError(String[] getTestData) throws Exception {
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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, priority = 8, dependsOnMethods ={"testActiveVarResellerUser"} , description = "Enter already existing username, email and mobile number on add new internal user page and verify error message.")
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

}
