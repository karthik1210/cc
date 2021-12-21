// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.testcases.ui;

import java.security.SecureRandom;

import org.apache.log4j.BasicConfigurator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.PasswordResetPage;
import com.pxp.cc.util.CCUtils;

public class PasswordResetTests extends CommandCenterGenericTest {
	SecureRandom random = new SecureRandom();
	String autogeneratePassword = PasswordResetPage.genPassword(random);
	public PasswordResetPage resetPage;

	@BeforeClass
	public static void setup() {
		BasicConfigurator.configure();
	}

	@Test(priority = 0, groups = { "UISmokeTest" }, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testSuccessfullyPasswordResetFunctionality() throws Exception {
		log("Test to verify new password created successfully :" + autogeneratePassword);
		log("Set the Test Data for CC");
		logStep("Navigating to Command Center portal");
		resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		logStep("Click forgot password and send email");
		resetPage.clickForgotPasswordAndEnterEmailAddress(ccuser.getResetEmail(), autogeneratePassword);
		logStep("Validating email received");
		CCUtils.assertEquals(resetPage.getUserNotificationMsg(),
				"We just sent a verification message to the following address:");
		CCUtils.assertEquals(resetPage.getEmailSentNotificationMsg(), "r*****@y*****.com");
		CCUtils.assertEquals(resetPage.getReceivedEmailAddressMsg(),
				"If the email doesn’t arrive soon, check your spam folder. We sent it from confirm@nextgen.com.");
		resetPage.urlInEmail(ccuser.getResetEmail(), autogeneratePassword);
		logStep("Navigating to reset password page and verifying success message");
		CCUtils.assertEquals(resetPage.getNewPaswwordCreatedMsg(), "Success! Your password has been reset.");
		resetPage.callPasswordUpdated(ccuser.getResetEmail(), autogeneratePassword);
		resetPage.deleteEmail(ccuser.getResetEmail());
		driver.navigate().refresh();
		driver.close();
	}

	@Test(priority = 1, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testSetNewPasswordFunctionalityWithAlreadyUsedPassword() throws Exception {
		log("Test to verify email validation with already used ten old password with auto generated password"
				+ autogeneratePassword);
		log("Set the Test Data for CC");
		logStep("Navigating to Command Center portal");
		resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		logStep("Click forgot password and send email");
		resetPage.clickForgotPasswordAndEnterEmailAddress(ccuser.getMailinatorOldPassword(), autogeneratePassword);
		logStep("Validating email received");
		CCUtils.assertEquals("We just sent a verification message to the following address:",
				resetPage.getUserNotificationMsg());
		CCUtils.assertEquals(resetPage.getEmailSentNotificationMsg(), "r*****@y*****.com");
		CCUtils.assertEquals(resetPage.getReceivedEmailAddressMsg(),
				"If the email doesn’t arrive soon, check your spam folder. We sent it from confirm@nextgen.com.");
		resetPage.oldPasswordLinkInEmail(ccuser.getMailinatorOldPassword(), autogeneratePassword);
		logStep("Navigating to reset password page and verifying error");
		CCUtils.assertEquals(resetPage.getAlreadyUsedPasswordMsg(),
				"New password must not match with last 10 old passwords");
		logStep("Deleting email for user");
		resetPage.deleteEmail(ccuser.getMailinatorOldPassword());
		driver.navigate().refresh();
		driver.close();
	}

	@Test(priority = 2, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testPasswordResetFunctionalityForAlreadyUsedToken() throws Exception {
		log("Test to verify set new password with alredy used link of password reset");
		log("Set the Test Data for CC");
		logStep("Navigating to Command Center portal");
		resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		logStep("Click forgot password and send email");
		resetPage.clickForgotPasswordAndEnterEmailAddress(ccuser.getResetEmail(),
				ccuser.getInvalidTokenUrl());
		logStep("Validating email received");
		CCUtils.assertEquals(resetPage.getUserNotificationMsg(),
				"We just sent a verification message to the following address:");
		CCUtils.assertEquals(resetPage.getEmailSentNotificationMsg(), "r*****@y*****.com");
		CCUtils.assertEquals(resetPage.getReceivedEmailAddressMsg(),
				"If the email doesn’t arrive soon, check your spam folder. We sent it from confirm@nextgen.com.");
		resetPage.invalidTokenUrlInEmail(ccuser.getResetEmail(), ccuser.getInvalidTokenUrl());
		logStep("Navigating to reset password page and verifying error");
	}

	@Test(priority = 3, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testPasswordResetFunctionalityWithoutEnteringUsername() throws Exception {
		log("Test to verify reset password with blank email id");
		log("Set the Test Data for CC");
		logStep("Navigating to Command Center portal");
		resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		logStep("Click forgot password and send email");
		resetPage.verifyPasswordResetFunctionalityWithBlankUsername();
		logStep("Validating blank email");
		CCUtils.assertEquals(resetPage.getBlankEmailMsg(), "Username is required");
	}

	@Test(priority = 4, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testPasswordResetFunctionalityForUnregisteredUser() throws Exception {
		log("Test to verify reset password with unregistered user");
		log("Set the Test Data for CC");
		logStep("Navigating to Command Center portal");
		resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		logStep("Click forgot password and send email");
		resetPage.clickForgotPasswordAndEnterUnregisteredUserEmailAddress(ccuser.getUnregisteredEmail());
		logStep("Validating email received");
	}

	@Test(priority = 5, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testPasswordResetFunctionalityForExternalUser() throws Exception {
		log("Test to verify reset password with external user");
		log("Set the Test Data for CC");
		logStep("Navigating to Command Center portal");
		resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		logStep("Click forgot password and send email");
		resetPage.clickForgotPasswordAndEnterExternalUserEmailAddress(ccuser.getExternalUser());
		logStep("Validating email received");
		CCUtils.assertEquals(resetPage.getErrorMsgExternalUser(),
				"Your credentials are managed through an external system and the Command Center cannot change your password on your behalf.");
		CCUtils.assertEquals(resetPage.getChangePasswordMsgContactAdminMsg(),
				"If you would like to change your password, please contact _______________.");
		resetPage.callReturnToLogin();
	}

	@Test(priority = 6, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testSetNewPasswordFunctionalityWithRepetitiveSequenceInPasswordField() throws Exception {
		log("Test to verify password reset with repetitive sequence entered in password fields");
		log("Set the Test Data for CC");
		logStep("Navigating to Command Center portal");
		resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		logStep("Click forgot password and send email");
		resetPage.clickForgotPasswordAndEnterEmailAddress(ccuser.getResetEmail());
		logStep("Validating email received");
		CCUtils.assertEquals(resetPage.getUserNotificationMsg(),
				"We just sent a verification message to the following address:");
		CCUtils.assertEquals(resetPage.getEmailSentNotificationMsg(), "r*****@y*****.com");
		CCUtils.assertEquals(resetPage.getReceivedEmailAddressMsg(),
				"If the email doesn’t arrive soon, check your spam folder. We sent it from confirm@nextgen.com.");
		resetPage.linkUnderEmail(ccuser.getResetEmail());
	}

	@Test(priority = 7, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testSetNewPasswordFunctionalityWithSpaceInPasswordField() throws Exception {
		log("Test to verify set password validation with space entered in password fields");
		log("Set the Test Data for CC");
		logStep("Navigating to Command Center portal");
		resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		logStep("Click forgot password and send email");
		resetPage.clickForgotPasswordAndEnterEmailAddress(ccuser.getResetEmail());
		logStep("Validating email received");
		CCUtils.assertEquals("We just sent a verification message to the following address:",
				resetPage.getUserNotificationMsg());
		CCUtils.assertEquals(resetPage.getEmailSentNotificationMsg(), "r*****@y*****.com");
		CCUtils.assertEquals(resetPage.getReceivedEmailAddressMsg(),
				"If the email doesn’t arrive soon, check your spam folder. We sent it from confirm@nextgen.com.");
		resetPage.linkInEmail(ccuser.getResetEmail());
	}

	@Test(priority = 8, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testSetNewPasswordFunctionalityWithCommonlyUsedPassword() throws Exception {
		log("Test to verify set new password with commonly used password");
		log("Set the Test Data for CC");
		logStep("Navigating to Command Center portal");
		resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		logStep("Click forgot password and send email");
		resetPage.clickForgotPasswordAndEnterEmailAddress(ccuser.getResetEmail());
		logStep("Validating email received");
		CCUtils.assertEquals(resetPage.getUserNotificationMsg(),
				"We just sent a verification message to the following address:");
		CCUtils.assertEquals(resetPage.getEmailSentNotificationMsg(), "r*****@y*****.com");
		CCUtils.assertEquals(resetPage.getReceivedEmailAddressMsg(),
				"If the email doesn’t arrive soon, check your spam folder. We sent it from confirm@nextgen.com.");
		resetPage.commonPasswordUrlInEmail(ccuser.getResetEmail());
	}

	@Test(priority = 9, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testSetNewPasswordFunctionalityWithMaximumCharacters() throws Exception {
		log("Test to verify set new password with max 101 characters");
		log("Set the Test Data for CC");
		logStep("Navigating to Command Center portal");
		resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		logStep("Click forgot password and send email");
		resetPage.clickForgotPasswordAndEnterEmailAddress(ccuser.getResetEmail());
		logStep("Validating email received");
		CCUtils.assertEquals(resetPage.getUserNotificationMsg(),
				"We just sent a verification message to the following address:");
		CCUtils.assertEquals(resetPage.getEmailSentNotificationMsg(), "r*****@y*****.com");
		CCUtils.assertEquals(resetPage.getReceivedEmailAddressMsg(),
				"If the email doesn’t arrive soon, check your spam folder. We sent it from confirm@nextgen.com.");
		resetPage.maximumCharacterValidationlinkInEmail(ccuser.getResetEmail());
		logStep("Navigating to reset password page and verifying error");
	}
}
