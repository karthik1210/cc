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

public class AddNewInternalUserTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public InternalUsersPage internalUsersPage;
	public AddNewInternalUsersPage addNewInternalUsersPage;
	public CommonMethods commonMethods;

	@DataProvider(name = "invalidTestData")
	public Object[] getInvalidTestData() {
		Object[][] obj = new Object[][] { { testData.getProperty("invalid.user.first.name.one"),
				testData.getProperty("invalid.user.last.name.one"), testData.getProperty("invalid.user.email.one"),
				testData.getProperty("invalid.username.one"), testData.getProperty("invalid.user.mobile.number.one") },
				{ testData.getProperty("invalid.user.first.name.two"),
						testData.getProperty("invalid.user.last.name.two"),
						testData.getProperty("invalid.user.email.two"), testData.getProperty("invalid.username.two"),
						testData.getProperty("invalid.user.mobile.number.two") } };
		return obj;
	}

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on more options button");
		internalUsersPage.clickToMoreOptions();
		logStep("Click on new button");
		internalUsersPage.clickOnNewButton();
		addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new internal user popup")
	public void testAddNewInternalUserPopup() throws Exception {
		logStep("Verify add new internal user popup");
		addNewInternalUsersPage.verifyAddNewInternalUserPageTitle();
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyFirstNameLabelTextAndTextboxDefaultValue();
		commonMethods.verifyLastNameLabelTextAndTextboxDefaultValue();
		commonMethods.verifyUsernameLabelTextAndTextboxDefaultValue();
		commonMethods.verifyEmailLabelTextAndTextboxDefaultValue();
		commonMethods.verifyMobileNumberLabelTextAndTextboxDefaultValue();
		commonMethods.verifyStatusLabelTextAndRadioButtons();
		commonMethods.verifyActiveRadioButtonByDefaultSelected();
		addNewInternalUsersPage.verifyUserRoles();
		addNewInternalUsersPage.verifyCrossIconIsDisplayed();
		addNewInternalUsersPage.verifyCancelButtonIsDisplayed();
		addNewInternalUsersPage.verifySaveAndAddButtonIsDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify required error message is displayed for mandatory fields(First Name, Last Name, Username and Email)")
	public void testErrorMessageForMandatoryFields() throws Exception {
		logStep("Click on save & add button");
		addNewInternalUsersPage.clickOnSaveAndAddButton();
		commonMethods = new CommonMethods(driver);
		logStep("Verify required error message is displayed for mandatory fields");
		CCUtils.assertEquals(commonMethods.getRequiredErrorForFirstNameField(), "First name is required");
		CCUtils.assertEquals(commonMethods.getRequiredErrorForLastNameField(), "Last name is required");
		CCUtils.assertEquals(commonMethods.getRequiredErrorForEmailField(), "Email is required");
		CCUtils.assertEquals(commonMethods.getRequiredErrorForUsernameField(), "Username is required");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in firstname, last name,username and email field and verify error message")
	public void testMaximumCharactersErrorMessageForInputFields() throws Exception {
		logStep("Verify maximum character reached error message for firstname, last name,username and email field");
		commonMethods = new CommonMethods(driver);
		logStep("Enter more than 100 characters in first name, last name, username, email and mobile number field and verify maximum characters error message");
		commonMethods.enterFirstName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(commonMethods.getMaximumCharacterErrorForFirstNameField(), "Maximum characters reached");
		commonMethods.enterLastName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(commonMethods.getMaximumCharacterErrorForLastNameField(), "Maximum characters reached");
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(101));
		CCUtils.assertEquals(commonMethods.getMaximumCharacterErrorForUsernameField(), "Maximum characters reached");
		commonMethods.enterEmail(CCUtils.generateAlphaNumericString(101).concat("@gmail.com"));
		CCUtils.assertEquals(commonMethods.getMaximumCharacterErrorForEmailField(), "Maximum characters reached");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter already existing username, email and mobile number on add new internal user page and verify error message.")
	public void testUniqueErrorMessage() throws Exception {
		logStep("Enter existing data in username, email, mobile number field and verify error message");
		commonMethods = new CommonMethods(driver);
		logStep("Enter existing value in username, email and mobile number fields and verify error message");
		commonMethods.enterMobileNumber(testData.getProperty("internal.user.existing.mobile.number"));
		commonMethods.focusOnInactiveRadioButton();
		CCUtils.assertEquals(commonMethods.getDuplicateErrorForMobileNumberField(), "Mobile number already in use");
		commonMethods.enterEmail(testData.getProperty("internal.user.existing.email"));
		CCUtils.assertEquals(commonMethods.getDuplicateErrorForEmailField(), "Email already in use");
		commonMethods.enterUserName(testData.getProperty("internal.user.existing.username"));
		CCUtils.assertEquals(commonMethods.getDuplicateErrorForUsernameField(), "Username already in use");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidTestData", description = "Enter invalid data in first name, last name, email, username and mobile number field and verify error message")
	public void testEnterValidDataErrorMessage(String[] getTestData) throws Exception {
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
		commonMethods.focusOnInactiveRadioButton();
		CCUtils.assertEquals(commonMethods.getValidErrorForMobileNumberField(), "Please enter valid mobile number");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify username is generated using beginning of email")
	public void testUsernameGeneratedFromEmail() throws Exception {
		logStep("Verify username is generated using beginning of email");
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyUsernameGeneratedUsingBeginningOfEmail(CCUtils.generateNewEmail(20));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel and cross icon")
	public void testCancelAndCrossIconFunctionality() throws Exception {
		logStep("Click on cancel button");
		addNewInternalUsersPage.clickOnCancelButton();
		logStep("Verify after clicking on cancel button user redirected to internal users page");
		internalUsersPage.verifyInternalUsersPageTitle();
		logStep("Click on more options button");
		internalUsersPage.clickToMoreOptions();
		logStep("Click on new button");
		internalUsersPage.clickOnNewButton();
		logStep("Click on cross button");
		addNewInternalUsersPage.clickOnCrossIcon();
		logStep("Verify after clicking on cross button user redirected to internal users page");
		internalUsersPage.verifyInternalUsersPageTitle();
	}

	@Test(enabled = true, groups = {
			"smoke" }, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new active internal user(without user role) functionality")
	public void testCreateActiveInternalUserWithoutRole() throws Exception {
		logStep("Create a new internal user without adding user role and verify success toaster");
		commonMethods = new CommonMethods(driver);
		commonMethods.enterFirstName(
				CCUtils.generateAlphaNumericString(3).concat("-").concat(CCUtils.generateAlphaNumericString(10)));
		commonMethods.enterLastName(CCUtils.generateAlphaNumericString(5).concat("'s"));
		commonMethods.enterEmail(CCUtils.generateNewEmail(64));
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(6));
		commonMethods.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
		addNewInternalUsersPage.clickOnSaveAndAddButton();
		commonMethods.verifyToasterMessage("Success! New User has been successfully added");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new inactive internal user(without user role) functionality")
	public void testCreateInactiveInternalUserWithoutRole() throws Exception {
		logStep("Create a new inactive internal user without adding user role and verify success toaster");
		commonMethods = new CommonMethods(driver);
		commonMethods.enterFirstName(CCUtils.generateAlphaNumericString(100));
		commonMethods.enterLastName(CCUtils.generateAlphaNumericString(100));
		commonMethods.enterEmail(CCUtils.generateAlphaNumericString(64).concat("@nextgenHealthCareServiceCenter1.com"));
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(100));
		commonMethods.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
		commonMethods.selectInactiveRadiobutton();
		addNewInternalUsersPage.clickOnSaveAndAddButton();
		commonMethods.verifyToasterMessage("Success! New User has been successfully added");
	}

	@Test(enabled = true, groups = {
			"smoke" }, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new active internal user(with user roles) functionality")
	public void testCreateActiveInternalUserWithRoles() throws Exception {
		logStep("Create a new active internal user with user roles and verify success toaster");
		commonMethods = new CommonMethods(driver);
		commonMethods.enterFirstName(CCUtils.generateAlphaNumericString(97).concat("'s"));
		commonMethods.enterLastName(CCUtils.generateAlphaNumericString(99));
		commonMethods.enterEmail(CCUtils.generateNewEmail(64));
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(99));
		addNewInternalUsersPage.addUserRoles();
		addNewInternalUsersPage.clickOnSaveAndAddButton();
		commonMethods.verifyToasterMessage("Success! New User has been successfully added");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new inactive internal user(with user roles) functionality")
	public void testCreateInactiveInternalUserWithRoles() throws Exception {
		logStep("Create a new inactive internal user with user roles and verify success toaster");
		commonMethods = new CommonMethods(driver);
		commonMethods.enterFirstName(CCUtils.generateAlphaNumericString(9));
		commonMethods.enterLastName(CCUtils.generateAlphaNumericString(3).concat("'s"));
		commonMethods.enterEmail(CCUtils.generateNewEmail(10));
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(6));
		commonMethods.selectInactiveRadiobutton();
		addNewInternalUsersPage.addUserRoles();
		addNewInternalUsersPage.clickOnSaveAndAddButton();
		commonMethods.verifyToasterMessage("Success! New User has been successfully added");
	}
}
