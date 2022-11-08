// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddNewInternalUsersPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.InternalUserDetailsPage;
import com.pxp.cc.objectmaps.InternalUsersPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class EditInternalUserTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public InternalUsersPage internalUsersPage;
	public InternalUserDetailsPage internalUserDetailsPage;
	public AddNewInternalUsersPage addNewInternalUsersPage;
	public CommonMethods commonMethods;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Verify user redirected to command center users page");
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Clicked on user to edit");
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickEditInInternalUser();
		commonMethods = new CommonMethods(driver);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button is displayed & fields available for editing")
	public void testEditMode() throws Exception {
		logStep("Clicked on user to edit");
		internalUserDetailsPage.verifyPasswordUneditable();
		logStep("Verifying fields are editable");
		commonMethods.enterFirstName(CCUtils.generateAlphaNumericString(18));
		commonMethods.enterLastName(CCUtils.generateAlphaNumericString(18));
		commonMethods.enterEmail(CCUtils.generateNewEmail(11));
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(12));
		commonMethods.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
		logStep("Click on cancel button");
		internalUserDetailsPage.clickCancel();
	}

	@Test(enabled = true, groups = {"smoke"}, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button is displayed & fe")
	public void testEditInternalUser() throws Exception {
		logStep("Modifying editable fields detail");
		String firstNameValueInEditMode = CCUtils.generateAlphaNumericString(18);
		commonMethods.enterFirstName(firstNameValueInEditMode);
		String lastNameValueInEditMode = CCUtils.generateAlphaNumericString(18);
		commonMethods.enterLastName(lastNameValueInEditMode);
		String emailValueInEditMode = CCUtils.generateNewEmail(11);
		commonMethods.enterEmail(emailValueInEditMode);
		String userNameValueInEditMode = CCUtils.generateAlphaNumericStringIncludingSpecialChar(12);
		commonMethods.enterUserName(userNameValueInEditMode);
		String mobileNumberValueInEditMode = String.valueOf(CCUtils.generateMobileNumber());
		commonMethods.enterMobileNumber(mobileNumberValueInEditMode);
		logStep("Click on save button");
		internalUserDetailsPage.clickSave();
		logStep("Verify new updated value are displayed in first name, last name, username, email and mobile number fields");
		CCUtils.assertEquals(commonMethods.getFirstName(), firstNameValueInEditMode.trim());
		CCUtils.assertEquals(commonMethods.getLastName(), lastNameValueInEditMode.trim());
		CCUtils.assertEquals(commonMethods.getUserName(), userNameValueInEditMode.trim());
		CCUtils.assertEquals(commonMethods.getEmailAddress(), emailValueInEditMode.trim());
		CCUtils.assertEquals(commonMethods.getMobileNumber().replaceAll("-", "").trim(),
				mobileNumberValueInEditMode.trim());
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify required error message for mandatory fields in edit mode")
	public void testRequiredErrorMessageInEditMode() throws Exception {
		logStep("Modifying editable fields detail");
		commonMethods.clearFirstNameField();
		CCUtils.assertEquals(commonMethods.getRequiredErrorForFirstNameField(), "First name is required");
		commonMethods.clearLastNameField();
		CCUtils.assertEquals(commonMethods.getRequiredErrorForLastNameField(), "Last name is required");
		commonMethods.clearEmailField();
		CCUtils.assertEquals(commonMethods.getRequiredErrorForEmailField(), "Email is required");
		commonMethods.clearUsernameField();
		CCUtils.assertEquals(commonMethods.getRequiredErrorForUsernameField(), "Username is required");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify maximum characters error message for editable fields")
	public void testMaximumCharacterErrorInEditMode() throws Exception {
		logStep("Enter more than 100 characters in first name field and verify maximum characters error message");
		commonMethods.enterFirstName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(commonMethods.getMaximumCharacterErrorForFirstNameField(), "Maximum characters reached");
		logStep("Enter more than 100 characters in last name field and verify maximum characters error message");
		commonMethods.enterLastName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(commonMethods.getMaximumCharacterErrorForLastNameField(), "Maximum characters reached");
		logStep("Enter more than 100 characters in username field and verify maximum characters error message");
		commonMethods.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(101));
		CCUtils.assertEquals(commonMethods.getMaximumCharacterErrorForUsernameField(), "Maximum characters reached");
		logStep("Enter more than 100 characters in email field and verify maximum characters error message");
		commonMethods.enterEmail(CCUtils.generateAlphaNumericString(101).concat("@gmail.com"));
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
						testData.getProperty("invalid.user.mobile.number.three") } };
		return obj;
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidTestData", description = "Enter invalid data in editable fields and verify valid error message for editable fields")
	public void testEnterValidDetailErrorInEditMode(String[] getTestData) throws Exception {
		logStep("Enter invalid data in first name, last name, email,username and mobile number fields and verify error message");
		commonMethods.enterFirstName(getTestData[0]);
		CCUtils.assertEquals(commonMethods.getValidErrorForFirstNameField(), "Please enter valid first name");
		commonMethods.enterLastName(getTestData[1]);
		CCUtils.assertEquals(commonMethods.getValidErrorForLastNameField(), "Please enter valid last name");
		commonMethods.enterEmail(getTestData[2]);
		CCUtils.assertEquals(commonMethods.getValidErrorForEmailField(), "Please enter valid email address");
		commonMethods.enterMobileNumber(getTestData[4]);
		commonMethods.focusOnUserNameField();
		CCUtils.assertEquals(commonMethods.getValidErrorForMobileNumberField(), "Please enter valid mobile number");
		commonMethods.enterUserName(getTestData[3]);
		CCUtils.assertEquals(commonMethods.getValidErrorForUsernameField(), "Please enter valid username");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter already existing data in username, mobile number and email fields and verify error message")
	public void testUniqueErrorMssageInEditMode() throws Exception {
		logStep("Enter existing value in username, email and mobile number fields and verify error message");
		commonMethods.enterMobileNumber(testData.getProperty("internal.user.existing.mobile.number"));
		commonMethods.focusOnUserNameField();
		CCUtils.assertEquals(commonMethods.getDuplicateErrorForMobileNumberField(), "Mobile number already in use");
		commonMethods.enterUserName(testData.getProperty("internal.user.existing.username"));
		commonMethods.focusOnEmailField();
		CCUtils.assertEquals(commonMethods.getDuplicateErrorForUsernameField(), "Username already in use");
		commonMethods.enterEmail(testData.getProperty("internal.user.existing.email"));
		CCUtils.assertEquals(commonMethods.getDuplicateErrorForEmailField(), "Email already in use");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid data in username field and verify error and tooltip text for username field")
	public void testTooltipForUsernameField() throws Exception {
		logStep("Enter invalid username in username field");
		commonMethods.enterUserName(testData.getProperty("invalid.username.one"));
		CCUtils.assertEquals(commonMethods.getValidErrorForUsernameField(), "Please enter valid username");
		logStep("Hover over the error icon for the username field and verify the tooltip text for the username field");
		CCUtils.assertEquals(commonMethods.getTooltipTextForUsernameField(),
				"Only alphanumeric and special characters .,!@#$%&*_- allowed");
	}

}
