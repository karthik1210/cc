// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

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
import com.pxp.cc.objectmaps.PasswordResetPage;
import com.pxp.cc.objectmaps.SearchOptionsPage;
import com.pxp.cc.objectmaps.SendBulkEnrollmentPage;
import com.pxp.cc.objectmaps.SendEnrollmentConfirmationPage;
import com.pxp.cc.objectmaps.VarResellerUsersPage;
import com.pxp.cc.util.CCUtils;
import com.pxp.cc.util.CommonMethods;

public class VarResellerSendBulkEnrollmentTests extends CommandCenterGenericTest {
	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public VarResellerUsersPage varResellerUsersPage;
	public SendBulkEnrollmentPage sendBulkEnrollmentPage;
	public SendEnrollmentConfirmationPage sendEnrollmentConfirmationPage;
	public CommonMethods commonMethods;
	public PasswordResetPage resetPage;
	SecureRandom random = new SecureRandom();
	String autogeneratePassword = PasswordResetPage.genPassword(random);
	public SearchOptionsPage searchOptionsPage;

	@BeforeMethod(alwaysRun = true)
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Click on command center users icon and verify internal users tab is by default selected");
		commandCenterUsersPage = clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage.verifyInternalUsersTabByDefaultSelected();
		logStep("Click on VAR/Reseller tab on the command center users page");
		varResellerUsersPage = commandCenterUsersPage.clickOnVarResellerUsersTab();
		logStep("Verify VAR/Reseller users page title");
		varResellerUsersPage.verifyVarResellerUsersPageTitle();
		logStep("Click on send enrollment button on the VAR/Reseller users page");
		sendBulkEnrollmentPage = varResellerUsersPage.clickOnSendEnrollmentButton();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify send enrollment button functionality")
	public void testFunctionalityOfSendEnrollmentButton() throws Exception {
		logStep("Verify send enrollment button is disabled when user is not selected");
		sendBulkEnrollmentPage.verifyAllCheckboxesAreNotSelected();
		sendBulkEnrollmentPage.verifySendEnrollmentButtonIsDisabled();
		logStep("Select a user and verify send enrollment button got enabled");
		sendBulkEnrollmentPage.selectUsers(1);
		sendBulkEnrollmentPage.verifySendEnrollmentButtonIsEnabled();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify send bulk enrollment functionality")
	public void testSendBulkEnrollment() throws Exception {
		sendBulkEnrollmentPage.verifySendBulkEnrollmentPageTitle();
		logStep("Select users from enrollment user list and click on send enrollment button");
		sendBulkEnrollmentPage.selectUsers(4);
		sendEnrollmentConfirmationPage = sendBulkEnrollmentPage.clickOnSendEnrollmentButton();
		logStep("Verify confirmation text and click on yes,send button");
		sendEnrollmentConfirmationPage.verifyConfirmationPageTitle();
		sendEnrollmentConfirmationPage.verifyConfirmationTextForBulkEnrollment(4);
		sendEnrollmentConfirmationPage.clickOnYesSendButton();
		logStep("Verify success toaster");
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Enrollment sent to users successfully");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify only active users are displayed in send bulk enrollment list")
	public void testActiveUsersAreDisplayedInEnrollmentList() throws Exception {
		logStep("Verify send bulk enrollment page title");
		sendBulkEnrollmentPage.verifySendBulkEnrollmentPageTitle();
		logStep("Verify only active users are displayed in send bulk enrollment list");
		sendBulkEnrollmentPage.VerifyOnlyActiveUsersDisplayedInSendBulkEnrollmentList();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user select all checkboxes using indeterminate checkbox and deselect all checkboxes again clicking on indeterminate checkbox")
	public void testFunctionalityOfInderminateCheckbox() throws Exception {
		logStep("Verify user select all checkboxes using indeterminate checkbox");
		sendBulkEnrollmentPage.clickOnIndeterminateCheckbox();
		sendBulkEnrollmentPage.verifyAllCheckboxesAreSelected();
		logStep("Verify user deselect all checkboxes using indeterminate checkbox");
		sendBulkEnrollmentPage.clickOnIndeterminateCheckbox();
		sendBulkEnrollmentPage.verifyAllCheckboxesAreNotSelected();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user deselect all checkboxes using cancel button on send bulk enrollment page")
	public void testUserDeselectAllCheckboxesUsingCancelButton() throws Exception {
		logStep("Select all checkboxes using indeterminate checkbox");
		sendBulkEnrollmentPage.clickOnIndeterminateCheckbox();
		sendBulkEnrollmentPage.verifyAllCheckboxesAreSelected();
		logStep("Verify user deselect all selected checkboxes using cancel button");
		sendBulkEnrollmentPage.clickOnCancelButton();
		sendBulkEnrollmentPage.verifyAllCheckboxesAreNotSelected();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user redirected to var/reseller users page after clicking on back arrow on send bulk enrollment page")
	public void testBackArrowFunctionalityOnSendBulkEnrollmentPage() throws Exception {
		logStep("Click on back arrow on send bulk enrollment page");
		varResellerUsersPage = sendBulkEnrollmentPage.clickOnBackArrow();
		logStep("Verify after clicking on back arrow on the send bulk enrollment page, user redirected to var/reseller users page");
		varResellerUsersPage.verifyVarResellerUsersPageTitle();

	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify user able to set new password using password reset link found in the email")
	public void testResetPasswordFunctionality() throws Exception {
		sendBulkEnrollmentPage.verifySendBulkEnrollmentPageTitle();
		logStep("Select users from enrollment user list and click on send enrollment button");
		sendBulkEnrollmentPage.selectUsers(1);
		sendEnrollmentConfirmationPage = sendBulkEnrollmentPage.clickOnSendEnrollmentButton();
		logStep("Verify confirmation text and click on yes,send button");
		sendEnrollmentConfirmationPage.verifyConfirmationPageTitle();
		sendEnrollmentConfirmationPage.verifyConfirmationTextForBulkEnrollment(1);
		sendEnrollmentConfirmationPage.clickOnYesSendButton();
		logStep("Verify success toaster");
		commonMethods = new CommonMethods(driver);
		commonMethods.verifyToasterMessage("Enrollment sent to users successfully");
		logStep("Verify user able to set new password using password reset link found in the email");
		resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		resetPage.urlInEmail(ccuser.getResetEmail(), autogeneratePassword);
		CCUtils.assertEquals(resetPage.getNewPaswwordCreatedMsg(), "Success! Your password has been reset.");
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cancel button on send enrollment confirmation popup")
	public void testCancelButtonFunctionalityOnConfirmationPopup() throws Exception {
		sendBulkEnrollmentPage.verifySendBulkEnrollmentPageTitle();
		logStep("Select users from enrollment user list and click on send enrollment button");
		sendBulkEnrollmentPage.selectUsers(1);
		sendEnrollmentConfirmationPage = sendBulkEnrollmentPage.clickOnSendEnrollmentButton();
		logStep("Verify send enrollment confirmation page title and confirmation text");
		sendEnrollmentConfirmationPage.verifyConfirmationPageTitle();
		sendEnrollmentConfirmationPage.verifyConfirmationTextForBulkEnrollment(1);
		logStep("Verify after clicking on cancel button, user redirected to send bulk enrollment page");
		sendEnrollmentConfirmationPage.clickOnCancelButton();
		sendBulkEnrollmentPage.verifySendBulkEnrollmentPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of cross icon on send enrollment confirmation popup")
	public void testCrossIconFunctionalityOnConfirmationPopup() throws Exception {
		sendBulkEnrollmentPage.verifySendBulkEnrollmentPageTitle();
		logStep("Select users from enrollment user list and click on send enrollment button");
		sendBulkEnrollmentPage.selectUsers(1);
		sendEnrollmentConfirmationPage = sendBulkEnrollmentPage.clickOnSendEnrollmentButton();
		logStep("Verify send enrollment confirmation page title and confirmation text");
		sendEnrollmentConfirmationPage.verifyConfirmationPageTitle();
		sendEnrollmentConfirmationPage.verifyConfirmationTextForBulkEnrollment(1);
		logStep("Verify after clicking on cross icon, user redirected to send bulk enrollment page");
		sendEnrollmentConfirmationPage.clickOnCloseWindow();
		sendBulkEnrollmentPage.verifySendBulkEnrollmentPageTitle();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify enrollment search options popup")
	public void testSearchOptionPopup() throws Exception {
		logStep("Click on search options button on the send bulk enrollment page");
		searchOptionsPage = sendBulkEnrollmentPage.clickOnSearchOptionsButton();
		logStep("Verify enrollment search options pop up");
		searchOptionsPage.verifyEnrollmentSearchOptionsPopupForVarResellerUser();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify that an error message is displayed on the screen if the user is searching using the default value")
	public void testErrorMessageForEmptyFields() throws Exception {
		logStep("Click on search options button on the send bulk enrollment page");
		searchOptionsPage = sendBulkEnrollmentPage.clickOnSearchOptionsButton();
		logStep("Verify error message if user click on search button without entering data in first name, last name, var/reseller, user name, email and mobile number field");
		searchOptionsPage.verifyErrorMessageOnSearchOptionsPage();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Enter more than 100 characters in firstname, lastname,email,username and mobile number field and verify error message")
	public void testMaximumCharacterErrorMessage() throws Exception {
		searchOptionsPage = sendBulkEnrollmentPage.clickOnSearchOptionsButton();
		logStep("Verify maximum character error message for first name, last name, email,username and mobile number search fields");
		searchOptionsPage.verifyMaximumCharactersErrorMessage(CCUtils.generateAlphaNumericString(101),
				CCUtils.generateAlphaNumericString(101), CCUtils.generateAlphaNumericStringIncludingSpecialChar(101),
				CCUtils.generateNewEmail(101));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify that all selected search options are displayed on the screen, and that all search options are cleared after clicking the Clear All button")
	public void testSelectedSearchOptionsAreDisplayedOnScreenOrNot() throws Exception {
		logStep("Click on search options on the send bulk enrollment page");
		searchOptionsPage = sendBulkEnrollmentPage.clickOnSearchOptionsButton();
		logStep("Search user using first name, last name, user name, email, mobile number and enrollment status");
		List<String> enrollmentStatusList = new ArrayList<String>();
		enrollmentStatusList.add(testData.getProperty("enrollment.status.pending"));
		enrollmentStatusList.add(testData.getProperty("enrollment.status.notsent"));
		searchOptionsPage.searchVarResellerUser(testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.last.name"), testData.getProperty("valid.user.var.reseller.name"),
				testData.getProperty("valid.user.email"), testData.getProperty("valid.username"),
				testData.getProperty("valid.user.mobile.number"), enrollmentStatusList);
		logStep("Click on search button on the search options popup");
		searchOptionsPage.clickOnSearchButton();
		logStep("Verify clear all button is displayed");
		searchOptionsPage.verifyClearAllButtonIsDisplayed();
		logStep("Verify all searched options are displayed on screen or not");
		sendBulkEnrollmentPage.verifySelectedSearchOptions(enrollmentStatusList,
				testData.getProperty("valid.user.first.name"), testData.getProperty("valid.user.last.name"),
				testData.getProperty("valid.user.email"), testData.getProperty("valid.username"),
				testData.getProperty("valid.user.mobile.number"));
		sendBulkEnrollmentPage.verifySelectedVarResellerName(testData.getProperty("valid.user.var.reseller.name"));
		logStep("Click on clear all button");
		sendBulkEnrollmentPage.clickOnClearAllButton();
		logStep("Verify all search options are cleared after clicking the Clear All button");
		sendBulkEnrollmentPage.verifyAllOptionsGotCleared();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify functionality of clear button on search option page")
	public void testClearButtonFunctionalityOnSearchPage() throws Exception {
		logStep("Click on search options on the send bulk enrollment page");
		searchOptionsPage = sendBulkEnrollmentPage.clickOnSearchOptionsButton();
		logStep("Search user using first name, last name, user name, email, mobile number and enrollment status");
		List<String> enrollmentStatusList = new ArrayList<String>();
		enrollmentStatusList.add(testData.getProperty("enrollment.status.pending"));
		enrollmentStatusList.add(testData.getProperty("enrollment.status.notsent"));
		searchOptionsPage.searchVarResellerUser(testData.getProperty("valid.user.first.name"),
				testData.getProperty("valid.user.last.name"), testData.getProperty("valid.user.var.reseller.name"),
				testData.getProperty("valid.user.email"), testData.getProperty("valid.username"),
				testData.getProperty("valid.user.mobile.number"), enrollmentStatusList);
		logStep("Verify clear button is displayed on search options popup");
		searchOptionsPage.verifyClearButtonIsDisplayed();
		logStep("Click on clear button");
		searchOptionsPage.clickOnClearButton();
		logStep("Verify after clicking on clear button, all text-box and checkbox got empty");
		searchOptionsPage.verifyTextboxIsEmptyWhenClickOnClearButton();
		searchOptionsPage.verifyVarResellerTextboxIsEmptyWhenClickOnClearButton();
		searchOptionsPage.verifyStatusCheckboxesAreNotByDefaultSelectedOnEnrollmentSearchOptionsPage();
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

	@Test(enabled = true, dataProvider = "invalidTestData", retryAnalyzer = RetryAnalyzer.class, description = "Enter invalid data in firstname, lastname,email,username and mobile number field and verify error message")
	public void testErrorMessageForInvalidData(String[] getTestData) throws Exception {
		logStep("Click on search options on the send bulk enrollment page");
		searchOptionsPage = sendBulkEnrollmentPage.clickOnSearchOptionsButton();
		logStep("Enter invalid data in firstname, lastname,email,username and mobile number field and verify error message");
		searchOptionsPage.verifyFieldErrorMessageWithInvalidData(getTestData[0], getTestData[1], getTestData[2],
				getTestData[3], getTestData[4]);
	}

}
