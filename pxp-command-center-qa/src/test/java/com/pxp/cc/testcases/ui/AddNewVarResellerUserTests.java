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
	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public VarResellerUsersPage varResellerUsersPage;
	public AddNewVarResellerUserPage addNewVarResellerUserPage;
	public CommonMethods commonMethods;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Select command center users icon in left navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Select VAR/Reseller users tab in command center users page");
		varResellerUsersPage = commandCenterUsersPage.clickOnVarResellerUsersTab();
		varResellerUsersPage.verifyVarResellerUsersPageTitle();
		logStep("Click on new button");
		addNewVarResellerUserPage = varResellerUsersPage.clickOnNewButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add VAR/Reseller user popup")
	public void testAddVarResellerUserPopup() throws Exception {
		logStep("Verify add VAR/Reseller user popup");
		addNewVarResellerUserPage.verifyAddNewVarResellerUserPageTitle();
		addNewVarResellerUserPage.VerifyHeaderText();
		addNewVarResellerUserPage.verifyVarResellerLabelTextAndSearchTextboxDefaultValue();
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyFirstNameLabelTextAndTextboxDefaultValue();
		commonMethods.verifyLastNameLabelTextAndTextboxDefaultValue();
		commonMethods.verifyUsernameLabelTextAndTextboxDefaultValue();
		commonMethods.verifyEmailLabelTextAndTextboxDefaultValue();
		commonMethods.verifyMobileNumberLabelTextAndTextboxDefaultValue();
		commonMethods.verifyStatusLabelTextAndRadioButtons();
		commonMethods.verifyActiveRadioButtonByDefaultSelected();
		addNewVarResellerUserPage.verifyUserRolesLabelTextAndRadioButtons();
		addNewVarResellerUserPage.verifyCloseWindowButtonIsDisplayed();
		addNewVarResellerUserPage.verifyCancelButtonIsDisplayed();
		addNewVarResellerUserPage.verifySaveAndAddButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify required error message for mandatory fields in add VAR/Reseller user page")
	public void testRequiredErrorMessage() throws Exception {
		commonMethods = new CommonMethods(driver);
		logStep("Enter valid data in VAR/Reseller, first name, last name, username, email and mobile number fiels");
		addNewVarResellerUserPage.enterVarResellerName(testData.getProperty("var.reseller.name"));
		commonMethods.enterFirstName(CCUtils.generateAlphaNumericString(18));
		commonMethods.enterLastName(CCUtils.generateAlphaNumericString(18));
		commonMethods.enterEmail(CCUtils.generateNewEmail(11));
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		commonMethods.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
		logStep("Remove all characters from mandatory fields and verify required error message");
		addNewVarResellerUserPage.clearVarResellerField();
		CCUtils.assertEquals(addNewVarResellerUserPage.getRequiredErrorForVarResellerField(),
				"VAR/Reseller is required");
		commonMethods.clearFirstNameField();
		CCUtils.assertEquals(commonMethods.getRequiredErrorForFirstNameField(), "First name is required");
		commonMethods.clearLastNameField();
		CCUtils.assertEquals(commonMethods.getRequiredErrorForLastNameField(), "Last name is required");
		commonMethods.clearEmailField();
		CCUtils.assertEquals(commonMethods.getRequiredErrorForEmailField(), "Email is required");
		commonMethods.clearUsernameField();
		CCUtils.assertEquals(commonMethods.getRequiredErrorForUsernameField(), "Username is required");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify the functionality of cancel button and close window button on add VAR/Reseller user page")
	public void testFunctionalityOfCancelAndCrossButton() throws Exception {
		logStep("Click on cancel button and verify user redirected to VAR/reseller users page");
		varResellerUsersPage = addNewVarResellerUserPage.clickOnCancelButton();
		varResellerUsersPage.verifyVarResellerUsersPageTitle();
		logStep("Click on new button");
		varResellerUsersPage.clickOnNewButton();
		logStep("Click on close window button and verify user redirected to VAR/reseller users page");
		varResellerUsersPage = addNewVarResellerUserPage.clickOnCloseWindowButton();
		varResellerUsersPage.verifyVarResellerUsersPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify maximum characters error message for first name, last name, username and email field")
	public void testMaximumCharacterError() throws Exception {
		commonMethods = new CommonMethods(driver);
		logStep("Enter more than 100 characters in first name, last name, username, email field and verify maximum characters error message");
		commonMethods.enterFirstName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(commonMethods.getMaximumCharacterErrorForFirstNameField(), "Maximum characters reached");
		commonMethods.enterLastName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(commonMethods.getMaximumCharacterErrorForLastNameField(), "Maximum characters reached");
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(101));
		CCUtils.assertEquals(commonMethods.getMaximumCharacterErrorForUsernameField(), "Maximum characters reached");
		commonMethods.enterEmail(CCUtils.generateNewEmail(101));
		CCUtils.assertEquals(commonMethods.getMaximumCharacterErrorForEmailField(), "Maximum characters reached");
	}

	@DataProvider(name = "invalidTestData")
	public Object[] getInvalidTestData() {
		Object[][] obj = new Object[][] { { testData.getProperty("invalid.user.first.name.one"),
				testData.getProperty("invalid.user.last.name.one"), testData.getProperty("invalid.user.email.one"),
				testData.getProperty("invalid.username.one"), testData.getProperty("invalid.user.mobile.number.one") },
				{ testData.getProperty("invalid.user.first.name.two"),
						testData.getProperty("invalid.user.last.name.two"),
						testData.getProperty("invalid.user.email.two"), testData.getProperty("invalid.username.two"),
						testData.getProperty("invalid.user.mobile.number.two") },
				{ testData.getProperty("invalid.user.first.name.three"),
						testData.getProperty("invalid.user.last.name.three"),
						testData.getProperty("invalid.user.email.three"),
						testData.getProperty("invalid.username.three"),
						testData.getProperty("invalid.user.mobile.number.three") },
				{ testData.getProperty("invalid.user.first.name.four"),
						testData.getProperty("invalid.user.last.name.four"),
						testData.getProperty("invalid.user.email.four"), testData.getProperty("invalid.username.four"),
						testData.getProperty("invalid.user.mobile.number.four") } };
		return obj;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidTestData", description = "Enter invalid data in first name, last name, email,username and mobile number fields and verify error message")
	public void testEnterValidDetailError(String[] getTestData) throws Exception {
		commonMethods = new CommonMethods(driver);
		logStep("Enter invalid data in first name, last name, email,username and mobile number fields and verify error message");
		commonMethods.enterFirstName(getTestData[0]);
		CCUtils.assertEquals(commonMethods.getValidErrorForFirstNameField(), "Please enter valid first name");
		commonMethods.enterLastName(getTestData[1]);
		CCUtils.assertEquals(commonMethods.getValidErrorForLastNameField(), "Please enter valid last name");
		commonMethods.enterEmail(getTestData[2]);
		CCUtils.assertEquals(commonMethods.getValidErrorForEmailField(), "Please enter valid email address");
		commonMethods.enterUserName(getTestData[3]);
		CCUtils.assertEquals(commonMethods.getValidErrorForUsernameField(), "Please enter valid username");
		commonMethods.enterMobileNumber(getTestData[4]);
		CCUtils.assertEquals(commonMethods.getValidErrorForMobileNumberField(), "Please enter valid mobile number");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter already existing data in username, mobile number and email fields and verify error message")
	public void testUniqueErrorMssage() throws Exception {
		commonMethods = new CommonMethods(driver);
		logStep("Enter existing value in username, email and mobile number fields and verify error message");
		commonMethods.enterMobileNumber(testData.getProperty("var.reseller.user.existing.mobile.number"));
		CCUtils.assertEquals(commonMethods.getDuplicateErrorForMobileNumberField(), "Mobile number already in use");
		commonMethods.enterEmail(testData.getProperty("var.reseller.user.existing.email"));
		CCUtils.assertEquals(commonMethods.getDuplicateErrorForEmailField(), "Email already in use");
		commonMethods.enterUserName(testData.getProperty("var.reseller.user.existing.username"));
		CCUtils.assertEquals(commonMethods.getDuplicateErrorForUsernameField(), "Username already in use");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid data in username field and verify error and tooltip text for username field")
	public void testTooltipForUsernameField() throws Exception {
		commonMethods = new CommonMethods(driver);
		logStep("Enter invalid username in username field");
		commonMethods.enterUserName(testData.getProperty("invalid.username.one"));
		CCUtils.assertEquals(commonMethods.getValidErrorForUsernameField(), "Please enter valid username");
		logStep("Hover over the error icon for the username field and verify the tooltip text for the username field");
		CCUtils.assertEquals(commonMethods.getTooltipTextForUsernameField(),
				"Only alphanumeric and special characters .,!@#$%&*_- allowed");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new active VAR/Reseller user functionality")
	public void testActiveVarResellerUser() throws Exception {
		logStep("Enter data in mandatory fields");
		addNewVarResellerUserPage.searchAndSelectVarReseller(testData.getProperty("var.reseller.name"));
		commonMethods = new CommonMethods(driver);
		commonMethods.enterFirstName(CCUtils.generateAlphaNumericString(18));
		commonMethods.enterLastName(CCUtils.generateAlphaNumericString(18).concat("'s"));
		commonMethods.enterEmail(CCUtils.generateNewEmail(11));
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		commonMethods.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
		logStep("Verify active radio button is by default selected on add VAR/Reseller user page");
		commonMethods.verifyActiveRadioButtonByDefaultSelected();
		logStep("Select user role");
		addNewVarResellerUserPage.selectUserRole(testData.getProperty("var.reseller.user.role.admin"));
		logStep("Click on save and add button");
		addNewVarResellerUserPage.clickOnSaveAndAdd();
		logStep("Verify success toaster message");
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Success! New user has been successfully created");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new inactive VAR/Reseller user functionality")
	public void testInactiveVarResellerUser() throws Exception {
		logStep("Enter data in mandatory fields");
		addNewVarResellerUserPage.searchAndSelectVarReseller(testData.getProperty("var.reseller.name"));
		commonMethods = new CommonMethods(driver);
		commonMethods.enterFirstName(CCUtils.generateAlphaNumericString(18));
		commonMethods.enterLastName(
				CCUtils.generateAlphaNumericString(5).concat("-").concat(CCUtils.generateAlphaNumericString(5)));
		commonMethods.enterEmail(CCUtils.generateNewEmail(11));
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(5));
		commonMethods.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
		logStep("Select inactive status");
		commonMethods.selectInactiveRadiobutton();
		logStep("Select user role");
		addNewVarResellerUserPage.selectUserRole(testData.getProperty("var.reseller.user.role.staff"));
		logStep("Click on save and add button");
		addNewVarResellerUserPage.clickOnSaveAndAdd();
		logStep("Verify success toaster message");
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Success! New user has been successfully created");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new VAR/Reseller user(without user role) functionality")
	public void testCreateVarResellerUserWithoutUserRole() throws Exception {
		logStep("Enter data in mandatory fields");
		addNewVarResellerUserPage.searchAndSelectVarReseller(testData.getProperty("var.reseller.name"));
		commonMethods = new CommonMethods(driver);
		commonMethods.enterFirstName(CCUtils.generateAlphaNumericString(18).concat("'s"));
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
		commonMethods.verifyToasterMessage("Success! New user has been successfully created");
	}

}
