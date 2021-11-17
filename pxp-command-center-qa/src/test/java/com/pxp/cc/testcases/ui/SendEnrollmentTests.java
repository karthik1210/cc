// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.InternalUserDetailsPage;
import com.pxp.cc.objectmaps.InternalUsersPage;
import com.pxp.cc.objectmaps.PasswordResetPage;
import com.pxp.cc.objectmaps.SearchOptionsPage;
import com.pxp.cc.objectmaps.SendBulkEnrollmentPage;
import com.pxp.cc.objectmaps.SendEnrollmentConfirmationPage;
import com.pxp.cc.util.CCUtils;

public class SendEnrollmentTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public InternalUsersPage internalUsersPage;
	public SendBulkEnrollmentPage sendBulkEnrollmentPage;
	public SendEnrollmentConfirmationPage sendEnrollmentConfirmationPage;
	public InternalUserDetailsPage internalUserDetailsPage;
	public SearchOptionsPage searchOptionsPage;
	public List<String> userStatusList = null;
	public List<String> enrollmentStatusList = null;
	public PasswordResetPage resetPage;
	SecureRandom random = new SecureRandom();
	String autogeneratePassword = PasswordResetPage.genPassword(random);

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		internalUsersPage = new InternalUsersPage(driver);
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

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify send bulk enrollment functionality")
	public void testSendBulkEnrollment() throws Exception {
		sendBulkEnrollmentPage = internalUsersPage.clickOnSenEnrollmentButton();
		sendBulkEnrollmentPage.verifySendBulkEnrollmentPageTitle();
		logStep("Select users from enrollment user list and click on send enrollment button");
		sendBulkEnrollmentPage.selectUsers(4);
		sendEnrollmentConfirmationPage = sendBulkEnrollmentPage.clickOnSendEnrollmentButton();
		logStep("Verify confirmation text and click on yes,send button");
		sendEnrollmentConfirmationPage.verifyConfirmationPageTitle();
		sendEnrollmentConfirmationPage.verifyConfirmationTextForBulkEnrollment(4);
		sendEnrollmentConfirmationPage.clickOnYesSendButton();
		logStep("Verify success toaster");
		sendBulkEnrollmentPage.verifySuccessToaster();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify send enrollment button functionality")
	public void testFunctionalityOfSendEnrollmentButton() throws Exception {
		sendBulkEnrollmentPage = internalUsersPage.clickOnSenEnrollmentButton();
		logStep("Verify send enrollment button is disabled when user is not selected");
		sendBulkEnrollmentPage.verifySendEnrollmentButtonIsDisabled();
		logStep("Select a user and verify send enrollment button got enabled");
		sendBulkEnrollmentPage.selectUsers(1);
		sendBulkEnrollmentPage.verifySendEnrollmentButtonIsEnabled();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify only active users are displayed in send bulk enrollment list")
	public void testActiveUsersAreDisplayedInEnrollmentList() throws Exception {
		sendBulkEnrollmentPage = internalUsersPage.clickOnSenEnrollmentButton();
		sendBulkEnrollmentPage.VerifyOnlyActiveUsersDisplayedInSendBulkEnrollmentList();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user select all checkboxes using indeterminate checkbox and deselect all checkboxes again clicking on indeterminate checkbox")
	public void testFunctionalityOfInderminateCheckbox() throws Exception {
		sendBulkEnrollmentPage = internalUsersPage.clickOnSenEnrollmentButton();
		sendBulkEnrollmentPage.verifySendBulkEnrollmentPageTitle();
		logStep("Verify user select all checkboxes using indeterminate checkbox");
		sendBulkEnrollmentPage.clickOnIndeterminateCheckbox();
		sendBulkEnrollmentPage.verifyAllCheckboxesAreSelected();
		logStep("Verify user deselect all checkboxes using indeterminate checkbox");
		sendBulkEnrollmentPage.clickOnIndeterminateCheckbox();
		sendBulkEnrollmentPage.verifyAllCheckboxesAreNotSelected();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user deselect all checkboxes using cancel button on send bulk enrollment page")
	public void testUserDeselectAllCheckboxesUsingCancelButton() throws Exception {
		sendBulkEnrollmentPage = internalUsersPage.clickOnSenEnrollmentButton();
		sendBulkEnrollmentPage.verifySendBulkEnrollmentPageTitle();
		logStep("Select all checkboxes and Verify user deselect all selected checkboxes using cancel button");
		sendBulkEnrollmentPage.clickOnIndeterminateCheckbox();
		sendBulkEnrollmentPage.clickOnCancelButton();
		sendBulkEnrollmentPage.verifyAllCheckboxesAreNotSelected();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify enrollment search options popup")
	public void testSearchOptionPopup() throws Exception {
		sendBulkEnrollmentPage = internalUsersPage.clickOnSenEnrollmentButton();
		logStep("Click on search options button and verify search options pop up");
		searchOptionsPage = sendBulkEnrollmentPage.clickOnSearchOptionsButton();
		searchOptionsPage.verifyEnrollmentSearchOptionsPopup();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify send enrollment button is not displayed in kebab menu for inactive user")
	public void testSendEnrollmentButtonIsNotPresentForInactiveUser() throws Exception {
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		logStep("Search and select inactive user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.inactive"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickOnKebabMenuButton();
		internalUserDetailsPage.verifySendEnrollmentButtonIsNotDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify send enrollment button is not displayed in kebab menu for no roles assigned user")
	public void testSendEnrollmentButtonIsNotPresentForNoRolesAssignedUser() throws Exception {
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		logStep("Search and select no roles assigned user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.norolesassigned"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickOnKebabMenuButton();
		internalUserDetailsPage.verifySendEnrollmentButtonIsNotDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify send enrollment button is not displayed in kebab menu for locked out user")
	public void testSendEnrollmentButtonIsNotPresentForLockedUser() throws Exception {
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		logStep("Search and select locked user");
		userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.lockedout"));
		searchOptionsPage.selectUserStatus(userStatusList);
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		internalUserDetailsPage.clickOnKebabMenuButton();
		internalUserDetailsPage.verifySendEnrollmentButtonIsNotDisplayed();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to send enrollment email using kebab menu option")
	public void testSendEnrollmentUsingKebabMenuOption() throws Exception {
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		logStep("search and select active user");
		List<String> userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		List<String> enrollStatusList = new ArrayList<String>();
		enrollStatusList.add(testData.getProperty("enrollment.status.notsent"));
		searchOptionsPage.selectEnrollmentStatusValues(enrollStatusList);
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		String userName = internalUserDetailsPage.getUserName();
		logStep("Select send enrollment email button from kebab menu");
		internalUserDetailsPage.clickOnKebabMenuButton();
		sendEnrollmentConfirmationPage = internalUserDetailsPage.clickOnSendEnrollmentEmailButton();
		sendEnrollmentConfirmationPage.verifyConfirmationTextForSingleUser(userName);
		logStep("Click on yes send button and verify success toaster message");
		sendEnrollmentConfirmationPage.clickOnYesSendButton();
		internalUserDetailsPage.verifySuccessToaster();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify that an error message is displayed on the screen if the user is searching using the default value")
	public void testErrorMessageForEmptyFields() throws Exception {
		sendBulkEnrollmentPage = internalUsersPage.clickOnSenEnrollmentButton();
		searchOptionsPage = sendBulkEnrollmentPage.clickOnSearchOptionsButton();
		logStep("Verify error message if user click on search button without entering data in first name, last name,user name, email and mobile number field");
		searchOptionsPage.verifyErrorMessageOnSearchOptionsPage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in firstname, lastname,email,username and mobile number field and verify error message")
	public void testMaximumCharacterErrorMessage() throws Exception {
		sendBulkEnrollmentPage = internalUsersPage.clickOnSenEnrollmentButton();
		searchOptionsPage = sendBulkEnrollmentPage.clickOnSearchOptionsButton();
		logStep("Verify maximum character error message for first name, last name, email,username and mobile number search fields");
		searchOptionsPage.verifyMaximumCharactersErrorMessage(CCUtils.generateAlphaNumericString(101),
				CCUtils.generateAlphaNumericString(101), CCUtils.generateAlphaNumericStringIncludingSpecialChar(101),
				CCUtils.generateNewEmail(101), Long.parseLong(testData.getProperty("invalid.mobile.number")));
	}

	@Test(enabled = true, dataProvider = "invalidTestData", retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid data in firstname, lastname,email,username and mobile number field and verify error message")
	public void testErrorMessageForInvalidData(String[] getTestData) throws Exception {
		sendBulkEnrollmentPage = internalUsersPage.clickOnSenEnrollmentButton();
		searchOptionsPage = sendBulkEnrollmentPage.clickOnSearchOptionsButton();
		logStep("Enter invalid data in firstname, lastname,email,username and mobile number field and verify error message");
		searchOptionsPage.verifyFieldErrorMessageWithInvalidData(getTestData[0], getTestData[1], getTestData[2],
				getTestData[3], getTestData[4]);
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify that all selected search options are displayed on the screen, and that all search options are cleared after clicking the Clear All button")
	public void testSelectedSearchOptionsAreDisplayedOnScreenOrNot() throws Exception {
		sendBulkEnrollmentPage = internalUsersPage.clickOnSenEnrollmentButton();
		searchOptionsPage = sendBulkEnrollmentPage.clickOnSearchOptionsButton();
		logStep("Search user using first name, last name, user name, email, mobile number and enrollment status");
		List<String> enrollmentStatusList = new ArrayList<String>();
		enrollmentStatusList.add(testData.getProperty("enrollment.status.pending"));
		enrollmentStatusList.add(testData.getProperty("enrollment.status.notsent"));
		searchOptionsPage.searchUser(testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.last.name"), testData.getProperty("valid.user.email"),
				testData.getProperty("valid.username"), testData.getProperty("valid.user.mobile.number"),
				enrollmentStatusList);
		searchOptionsPage.clickOnSearchButton();
		logStep("Verify all searched options are displayed on screen or not");
		sendBulkEnrollmentPage.verifySelectedSearchOptions(enrollmentStatusList,
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"),
				testData.getProperty("valid.user.email"), testData.getProperty("valid.username"),
				testData.getProperty("valid.user.mobile.number"));
		logStep("Verify all search options are cleared after clicking the Clear All button");
		sendBulkEnrollmentPage.clickOnClearAllButton();
		sendBulkEnrollmentPage.verifyAllOptionsGotCleared();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to set new password using password reset link found in the email")
	public void testResetPasswordFunctionality() throws Exception {
		searchOptionsPage = internalUsersPage.clickOnSearchOptionPage();
		logStep("search and select user");
		List<String> userStatusList = new ArrayList<String>();
		userStatusList.add(testData.getProperty("user.status.active"));
		searchOptionsPage.selectUserStatus(userStatusList);
		List<String> enrollStatusList = new ArrayList<String>();
		enrollStatusList.add(testData.getProperty("enrollment.status.pending"));
		searchOptionsPage.selectEnrollmentStatusValues(enrollStatusList);
		searchOptionsPage.clickOnSearchButton();
		internalUsersPage.selectUser();
		internalUserDetailsPage = new InternalUserDetailsPage(driver);
		String userName = internalUserDetailsPage.getUserName();
		logStep("Select send enrollment email button from kebab menu");
		internalUserDetailsPage.clickOnKebabMenuButton();
		sendEnrollmentConfirmationPage = internalUserDetailsPage.clickOnSendEnrollmentEmailButton();
		sendEnrollmentConfirmationPage.verifyConfirmationTextForSingleUser(userName);
		logStep("Click on yes send button and verify success toaster message");
		sendEnrollmentConfirmationPage.clickOnYesSendButton();
		internalUserDetailsPage.verifySuccessToaster();
		logStep("Verify user able to set new password using password reset link found in the email");
		resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		resetPage.urlInEmail(ccuser.getResetEmail(), autogeneratePassword);
		CCUtils.assertEquals(resetPage.getNewPaswwordCreatedMsg(), "Success! Your password has been reset.");
	}
}
