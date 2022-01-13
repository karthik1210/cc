// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import java.security.SecureRandom;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.PasswordResetPage;
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

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal");
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		logStep("Click on command center users icon and verify internal users tab is by default selected");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
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
		logStep("Verify user deselect all selected checkboxes using cancel button");
		sendBulkEnrollmentPage.clickOnCancelButton();
		sendBulkEnrollmentPage.verifyAllCheckboxesAreNotSelected();
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


}
