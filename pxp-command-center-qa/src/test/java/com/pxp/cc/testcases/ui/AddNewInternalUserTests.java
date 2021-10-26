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

public class AddNewInternalUserTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public InternalUsersPage internalUsersPage;
	public AddNewInternalUsersPage addNewInternalUsersPage;

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
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
	}

	@Test(enabled = true, groups = {
			"UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new internal user popup")
	public void testAddNewInternalUserPopup() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on new button");
		internalUsersPage.clickOnNewButton();
		addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
		logStep("Verify add new internal user popup");
		addNewInternalUsersPage.verifyAddNewInternalUserPopup();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify required error message is displayed for mandatory fields(First Name, Last Name, Username and Email)")
	public void testErrorMessageForMandatoryFields() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on new button");
		internalUsersPage.clickOnNewButton();
		addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
		logStep("Click on save & add button");
		addNewInternalUsersPage.clickOnSaveAndAddButton();
		logStep("Verify required error message is displayed for mandatory fields");
		addNewInternalUsersPage.verifyRequiredErrorMessageForMandatoryFields();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in firstname, last name,username and email field and verify error message")
	public void testMaximumCharactersErrorMessageForInputFields() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on new button");
		internalUsersPage.clickOnNewButton();
		addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
		logStep("Verify maximum character reached error message for firstname, last name,username and email field");
		addNewInternalUsersPage.verifyMaximumCharactersErrorMessage(CCUtils.generateAlphaNumericString(101),
				CCUtils.generateAlphaNumericString(101), CCUtils.generateAlphaNumericStringIncludingSpecialChar(101),
				CCUtils.generateNewEmail(101));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter already existing username, email and mobile number on add new internal user page and verify error message.")
	public void testUniqueErrorMessage() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on new button");
		internalUsersPage.clickOnNewButton();
		addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
		logStep("Enter existing data in username, email, mobile number field and verify error message");
		addNewInternalUsersPage.verifyUniqueMessageForUsernameEmailAndMobileNumberField(
				testData.getProperty("internal.user.existing.username"),
				testData.getProperty("internal.user.existing.email"),
				testData.getProperty("internal.user.existing.mobile.number"));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, dataProvider = "invalidTestData", description = "Enter invalid data in first name, last name, email, username and mobile number field and verify error message")
	public void testEnterValidDataErrorMessage(String[] getTestData) throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on new button");
		internalUsersPage.clickOnNewButton();
		addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
		logStep("Enter invalid data in first name, last name, email, username and mobile number field and verify error message");
		addNewInternalUsersPage.verifyValidErrorMessage(getTestData[0], getTestData[1], getTestData[2], getTestData[3],
				getTestData[4]);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify username is generated using beginning of email")
	public void testUsernameGeneratedFromEmail() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Verify user redirected to command center users page");
		commandCenterUsersPage.verifyUserRedirectedToCommandCenterPage();
		logStep("Verify internal users tab is by default selected");
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on new button");
		internalUsersPage.clickOnNewButton();
		addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
		logStep("Verify username is generated using beginning of email");
		addNewInternalUsersPage.verifyUsernameGeneratedUsingBeginningOfEmail(CCUtils.generateNewEmail(20));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel and cross icon")
	public void testCancelAndCrossIconFunctionality() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on new button");
		internalUsersPage.clickOnNewButton();
		addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
		logStep("Click on cancel button");
		addNewInternalUsersPage.clickOnCancelButton();
		logStep("Verify after clicking on cancel button user redirected to internal users page");
		internalUsersPage.verifyInternalUsersPageTitle();
		logStep("Click on new button");
		internalUsersPage.clickOnNewButton();
		logStep("Click on cross button");
		addNewInternalUsersPage.clickOnCrossIcon();
		logStep("Verify after clicking on cross button user redirected to internal users page");
		internalUsersPage.verifyInternalUsersPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new active internal user(without user role) functionality")
	public void testCreateActiveInternalUserWithoutRole() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on new button");
		internalUsersPage.clickOnNewButton();
		addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
		String firstName = CCUtils.generateAlphaNumericString(3).concat(" ")
				.concat(CCUtils.generateAlphaNumericString(10));
		String lastName = CCUtils.generateAlphaNumericString(3).concat(" ")
				.concat(CCUtils.generateAlphaNumericString(10));
		String email = CCUtils.generateNewEmail(25);
		String userName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		String mobileNumber = String.valueOf(CCUtils.generateMobileNumber());
		logStep("Create a new internal user without adding user role and verify success toaster");
		addNewInternalUsersPage.createInternalUser(firstName, lastName, userName, email, mobileNumber);
		addNewInternalUsersPage.clickOnSaveAndAddButton();
		internalUsersPage.verifySuccessToaster();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new inactive internal user(without user role) functionality")
	public void testCreateInactiveInternalUserWithoutRole() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on new button");
		internalUsersPage.clickOnNewButton();
		addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
		String firstName = CCUtils.generateAlphaNumericString(100);
		String lastName = CCUtils.generateAlphaNumericString(100);
		String email = CCUtils.generateNewEmail(100);
		String userName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(100);
		String mobileNumber = String.valueOf(CCUtils.generateMobileNumber());
		logStep("Create a new inactive internal user without adding user role and verify success toaster");
		addNewInternalUsersPage.createInternalUser(firstName, lastName, userName, email, mobileNumber);
		addNewInternalUsersPage.selectInactiveRadiobutton();
		addNewInternalUsersPage.clickOnSaveAndAddButton();
		internalUsersPage.verifySuccessToaster();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new active internal user(with user roles) functionality")
	public void testCreateActiveInternalUserWithRoles() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on new button");
		internalUsersPage.clickOnNewButton();
		addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
		String firstName = CCUtils.generateAlphaNumericString(3).concat(" ")
				.concat(CCUtils.generateAlphaNumericString(3)).concat("'s");
		String lastName = CCUtils.generateAlphaNumericString(99);
		String email = CCUtils.generateNewEmail(99);
		String userName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(99);
		String mobileNumber = String.valueOf(CCUtils.generateMobileNumber());
		logStep("Create a new active internal user with user roles and verify success toaster");
		addNewInternalUsersPage.createInternalUser(firstName, lastName, userName, email, mobileNumber);
		addNewInternalUsersPage.addUserRoles();
		addNewInternalUsersPage.clickOnSaveAndAddButton();
		internalUsersPage.verifySuccessToaster();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify add new inactive internal user(with user roles) functionality")
	public void testCreateInactiveInternalUserWithRoles() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Click on new button");
		internalUsersPage.clickOnNewButton();
		addNewInternalUsersPage = new AddNewInternalUsersPage(driver);
		String firstName = CCUtils.generateAlphaNumericString(99);
		String lastName = CCUtils.generateAlphaNumericString(3).concat(" ")
				.concat(CCUtils.generateAlphaNumericString(3)).concat("'s");
		String email = CCUtils.generateNewEmail(50);
		String userName = CCUtils.generateAlphaNumericStringIncludingSpecialChar(6);
		String mobileNumber = String.valueOf(CCUtils.generateMobileNumber());
		logStep("Create a new inactive internal user with user roles and verify success toaster");
		addNewInternalUsersPage.createInternalUser(firstName, lastName, userName, email, mobileNumber);
		addNewInternalUsersPage.selectInactiveRadiobutton();
		addNewInternalUsersPage.addUserRoles();
		addNewInternalUsersPage.clickOnSaveAndAddButton();
		internalUsersPage.verifySuccessToaster();
	}
}
