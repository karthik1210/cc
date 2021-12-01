// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.ClientUserDetailsPage;
import com.pxp.cc.objectmaps.ClientUsersPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.util.CCUtils;

public class EditClientUserTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public ClientUsersPage clientUsersPage;
	public ClientUserDetailsPage clientUserDetailsPage;

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

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Select command center users icon in left navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Select client users tab in command center users page");
		clientUsersPage = commandCenterUsersPage.clickOnClientUsersTab();
		clientUsersPage.verifyClientUsersPageTitle();
		clientUsersPage.selectUser();
		clientUserDetailsPage = new ClientUserDetailsPage(driver);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button is displayed & after clicking on edit button fields available for editing")
	public void testEditMode() throws Exception {
		logStep("Click on edit button");
		clientUserDetailsPage.clickEditInClientUser();
		logStep("Verify after clicking on edit button save and cancel button is displayed on client user details page");
		clientUserDetailsPage.verifyCancelButtonIsDisplayed();
		clientUserDetailsPage.verifySaveButtonIsDisplayed();
		logStep("Verify password is not editable field");
		clientUserDetailsPage.verifyPasswordUneditable();
		logStep("Verify first name, last name, email, username and mobile number fields are editable");
		clientUserDetailsPage.enterFirstName(CCUtils.generateAlphaNumericString(18));
		clientUserDetailsPage.enterLastName(CCUtils.generateAlphaNumericString(18));
		clientUserDetailsPage.enterEmail(CCUtils.generateNewEmail(11));
		clientUserDetailsPage.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(12));
		clientUserDetailsPage.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
		clientUserDetailsPage.clickCancel();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to modify first name, last name, username, email and mobile number fields")
	public void testEditClientUser() throws Exception {
		logStep("Click on edit button");
		clientUserDetailsPage.clickEditInClientUser();
		logStep("Modifying editable fields detail");
		clientUserDetailsPage.enterFirstName(CCUtils.generateAlphaNumericString(18));
		clientUserDetailsPage.enterLastName(CCUtils.generateAlphaNumericString(18));
		clientUserDetailsPage.enterEmail(CCUtils.generateNewEmail(11));
		clientUserDetailsPage.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(12));
		clientUserDetailsPage.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
		clientUserDetailsPage.clickSave();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify required error message for mandatory fields in edit mode")
	public void testRequiredErrorMessageInEditMode() throws Exception {
		logStep("Click on edit button");
		clientUserDetailsPage.clickEditInClientUser();
		logStep("Modifying editable fields detail");
		clientUserDetailsPage.clearFirstNameField();
		CCUtils.assertEquals(clientUserDetailsPage.getRequiredErrorForFirstNameField(), "First name is required");
		clientUserDetailsPage.clearLastNameField();
		CCUtils.assertEquals(clientUserDetailsPage.getRequiredErrorForLastNameField(), "Last name is required");
		clientUserDetailsPage.clearEmailField();
		CCUtils.assertEquals(clientUserDetailsPage.getRequiredErrorForEmailField(), "Email is required");
		clientUserDetailsPage.clearUsernameField();
		CCUtils.assertEquals(clientUserDetailsPage.getRequiredErrorForUsernameField(), "Username is required");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify maximum xharacter error message for editable fields")
	public void testMaximumCharacterErrorInEditMode() throws Exception {
		logStep("Click on edit button");
		clientUserDetailsPage.clickEditInClientUser();
		logStep("Enter more than 100 characters in first name, last name, username, email and mobile number field and verify maximum character error message");
		clientUserDetailsPage.enterFirstName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(clientUserDetailsPage.getMaximumCharacterErrorForFirstNameField(),
				"Maximum characters reached");
		clientUserDetailsPage.enterLastName(CCUtils.generateAlphaNumericString(101));
		CCUtils.assertEquals(clientUserDetailsPage.getMaximumCharacterErrorForLastNameField(),
				"Maximum characters reached");
		clientUserDetailsPage.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(101));
		CCUtils.assertEquals(clientUserDetailsPage.getMaximumCharacterErrorForUsernameField(),
				"Maximum characters reached");
		clientUserDetailsPage.enterEmail(CCUtils.generateNewEmail(101));
		CCUtils.assertEquals(clientUserDetailsPage.getMaximumCharacterErrorForEmailField(),
				"Maximum characters reached");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidTestData", description = "Enter invalid data in editable fields and verify valid error message for editable fields")
	public void testEnterValidDetailErrorInEditMode(String[] getTestData) throws Exception {
		logStep("Click on edit button");
		clientUserDetailsPage.clickEditInClientUser();
		logStep("Enter invalid data in first name, last name, email,username and mobile number fields and verify error message");
		clientUserDetailsPage.enterFirstName(getTestData[0]);
		CCUtils.assertEquals(clientUserDetailsPage.getValidErrorForFirstNameField(), "Please enter valid first name");
		clientUserDetailsPage.enterLastName(getTestData[1]);
		CCUtils.assertEquals(clientUserDetailsPage.getValidErrorForLastNameField(), "Please enter valid last name");
		clientUserDetailsPage.enterEmail(getTestData[2]);
		CCUtils.assertEquals(clientUserDetailsPage.getValidErrorForEmailField(), "Please enter valid email address");
		clientUserDetailsPage.enterUserName(getTestData[3]);
		CCUtils.assertEquals(clientUserDetailsPage.getValidErrorForUsernameField(), "Please enter valid username");
		clientUserDetailsPage.enterMobileNumber(getTestData[4]);
		CCUtils.assertEquals(clientUserDetailsPage.getValidErrorForMobileNumberField(),
				"Please enter valid mobile number");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter already existing data in username, mobile number and email fields and verify error message")
	public void testUniqueErrorMssageInEditMode() throws Exception {
		logStep("Click on edit button");
		clientUserDetailsPage.clickEditInClientUser();
		logStep("Enter existing value in username, email and mobile number fields and verify error message");
		clientUserDetailsPage.enterMobileNumber(testData.getProperty("client.user.existing.mobile.number"));
		CCUtils.assertEquals(clientUserDetailsPage.getDuplicateErrorForMobileNumberField(),
				"Mobile number already in use");
		clientUserDetailsPage.enterEmail(testData.getProperty("client.user.existing.email"));
		CCUtils.assertEquals(clientUserDetailsPage.getDuplicateErrorForEmailField(), "Email already in use");
		clientUserDetailsPage.enterUserName(testData.getProperty("client.user.existing.username"));
		CCUtils.assertEquals(clientUserDetailsPage.getDuplicateErrorForUsernameField(), "Username already in use");
	}
}
