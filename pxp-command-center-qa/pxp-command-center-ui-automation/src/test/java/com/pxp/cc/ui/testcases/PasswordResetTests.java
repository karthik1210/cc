// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.ui.testcases;

import java.security.SecureRandom;

import org.apache.log4j.BasicConfigurator;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.object.maps.passwordresetpage.PasswordResetPage;
import com.pxp.cc.object.maps.pojo.CCUser;
import com.pxp.cc.object.maps.utilities.CCUtils;

public class PasswordResetTests extends CommandCenterGenericTest {
	SecureRandom random = new SecureRandom();
	String autogeneratePassword = PasswordResetPage.genPassword(random);

	public CCUser ccuser;

	@Test(priority = 0, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testSuccessfullyPasswordResetFunctionality() throws Exception {
		BasicConfigurator.configure();
		log("Test to verify new password created successfully :" + autogeneratePassword);
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.verifyUpdatedPasswordSuccessfullyLoginFunctionality(ccuser.getResetEmail(), autogeneratePassword);
		log("Entering the auto generated password on password reset page");
		CCUtils.assertEquals(resetPage.getUserNotificationMsg(),
				"We just sent a verification message to the following address:");
		CCUtils.assertEquals(resetPage.getEmailSentNotificationMsg(), "r*****@m*****.com");
		CCUtils.assertEquals(resetPage.getReceivedEmailAddressMsg(), "confirm@nextgen.com.");
		resetPage.urlInEmail(ccuser.getResetEmail(), autogeneratePassword);
		CCUtils.assertEquals(resetPage.getNewPaswwordCreatedMsg(), "Success! Your password has been reset.");
		resetPage.callPasswordUpdated(ccuser.getResetEmail(), autogeneratePassword);
	}

	@Test(priority = 1, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testSetNewPasswordFunctionalityWithAlreadyUsedPassword() throws Exception {
		BasicConfigurator.configure();
		log("Test to verify email validation with already used ten old password with auto generated password"
				+ autogeneratePassword);
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.verifySetNewPasswordFunctionalityWithLastTenOldPassword(ccuser.getMailinatorOldPassword(),
				autogeneratePassword);
		log("Entering the auto generated password on password reset page");
		CCUtils.assertEquals("We just sent a verification message to the following address:",
				resetPage.getUserNotificationMsg());
		CCUtils.assertEquals(resetPage.getEmailSentNotificationMsg(), "r*****@m*****.com");
		CCUtils.assertEquals(resetPage.getReceivedEmailAddressMsg(), "confirm@nextgen.com.");
		resetPage.oldPasswordLinkInEmail(ccuser.getMailinatorOldPassword(), autogeneratePassword);
		CCUtils.assertEquals(resetPage.getAlreadyUsedPasswordMsg(),
				"New password must not match with last 10 old passwords");
		resetPage.deleteEmail(ccuser.getMailinatorOldPassword());
		log("It will delete the already used link of email");
		driver.navigate().refresh();
		driver.close();
		log("It will close the current browser window");
	}

	@Test(priority = 2, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testPasswordResetFunctionalityForAlreadyUsedToken() throws Exception {
		log("Test to verify set new password with alredy used link of password reset");
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.verifyPasswordResetForInvalidToken(ccuser.getMailinatorInvalidToken(), ccuser.getInvalidTokenUrl());
		log("Displaying Reset your password page");
		CCUtils.assertEquals(resetPage.getUserNotificationMsg(),
				"We just sent a verification message to the following address:");
		CCUtils.assertEquals(resetPage.getEmailSentNotificationMsg(), "i*****@m*****.com");
		CCUtils.assertEquals(resetPage.getReceivedEmailAddressMsg(), "confirm@nextgen.com.");
		resetPage.invalidTokenUrlInEmail(ccuser.getMailinatorInvalidToken(), ccuser.getInvalidTokenUrl());
		CCUtils.assertEquals(resetPage.getInvalidTokenMsg(), "This password reset token is invalid");
	}

	@Test(priority = 3, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testPasswordResetFunctionalityWithoutEnteringUsername() throws Exception {
		BasicConfigurator.configure();
		log("Test to verify reset password with blank email id");
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.verifyPasswordResetFunctionalityWithBlankUsername();
		log("Displaying Reset your password page");
		CCUtils.assertEquals(resetPage.getBlankEmailMsg(), "Username is required");
	}

	@Test(priority = 4, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testPasswordResetFunctionalityForUnregisteredUser() throws Exception {
		log("Test to verify reset password with unregistered user");
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.verifyFunctionalityForUnregisteredUser(ccuser.getUnregisteredEmail());
		log("Displaying Reset your password page");
		CCUtils.assertEquals(resetPage.getErrorMsgUnregisteredUserMsg(), "You do not have access to this system.");
	}

	@Test(priority = 5, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testPasswordResetFunctionalityForExternalUser() throws Exception {
		BasicConfigurator.configure();
		log("Test to verify reset password with external user");
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.verifyPasswordResetValidationForExternalUser(ccuser.getExternalUser());
		log("Displaying Oops! page");
		CCUtils.assertEquals(resetPage.getErrorMsgExternalUser(),
				"Your credentials are managed through an external system and the Command Center cannot change your password on your behalf.");
		CCUtils.assertEquals(resetPage.getChangePasswordMsgContactAdminMsg(),
				"If you would like to change your password, please contact _______________.");
		resetPage.callReturnToLogin();
	}

	@Test(priority = 6, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testSetNewPasswordFunctionalityWithRepetitiveSequenceInPasswordField() throws Exception {
		log("Test to verify password reset with repetitive sequence entered in password fields");
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.verifySetNewPasswordFunctionalityWithRepetitiveSequenceEnteredInPasswordField(
				ccuser.getMailinatorRepetitiveSequence());
		log("Displaying Reset your password page");
		CCUtils.assertEquals(resetPage.getUserNotificationMsg(),
				"We just sent a verification message to the following address:");
		CCUtils.assertEquals(resetPage.getEmailSentNotificationMsg(), "r*****@m*****.com");
		CCUtils.assertEquals(resetPage.getReceivedEmailAddressMsg(), "confirm@nextgen.com.");
		resetPage.linkInEmail(ccuser.getMailinatorRepetitiveSequence());
	}

	@Test(priority = 7, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testSetNewPasswordFunctionalityWithSpaceInPasswordField() throws Exception {
		log("Test to verify set password validation with space entered in password fields");
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.verifySetNewPasswordFunctionalityWithSpaceEnteredInPasswordField(
				ccuser.getMailinatorSpaceInPassword());
		log("Displaying Reset your password page");
		CCUtils.assertEquals("We just sent a verification message to the following address:",
				resetPage.getUserNotificationMsg());
		CCUtils.assertEquals(resetPage.getEmailSentNotificationMsg(), "p*****@m*****.com");
		CCUtils.assertEquals(resetPage.getReceivedEmailAddressMsg(), "confirm@nextgen.com.");
		resetPage.linkInEmail(ccuser.getMailinatorSpaceInPassword());
		CCUtils.assertEquals(resetPage.getSpaceValidationMsg(), "Space not allowed in password");
	}

	@Test(priority = 8, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testSetNewPasswordFunctionalityWithCommonlyUsedPassword() throws Exception {
		log("Test to verify set new password with commonly used password");
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.verifySetNewPasswordFunctionalityWithCommonPassword(ccuser.getMailinatorCommonPassword());
		log("Displaying Reset your password page");
		CCUtils.assertEquals(resetPage.getUserNotificationMsg(),
				"We just sent a verification message to the following address:");
		CCUtils.assertEquals(resetPage.getEmailSentNotificationMsg(), "c*****@m*****.com");
		CCUtils.assertEquals(resetPage.getReceivedEmailAddressMsg(), "confirm@nextgen.com.");
		resetPage.commonPasswordUrlInEmail(ccuser.getMailinatorCommonPassword());
	}

	@Test(priority = 9, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testSetNewPasswordFunctionalityWithMaximumCharacters() throws Exception {
		log("Test to verify set new password with max 101 characters");
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.verifySetNewPasswordFunctionalityWithMaximumOfhunderedCharacters(
				ccuser.getMailinatorMaximumCharacters());
		log("Displaying Reset your password page");
		CCUtils.assertEquals(resetPage.getUserNotificationMsg(),
				"We just sent a verification message to the following address:");
		CCUtils.assertEquals(resetPage.getEmailSentNotificationMsg(), "M*****@m*****.com");
		CCUtils.assertEquals(resetPage.getReceivedEmailAddressMsg(), "confirm@nextgen.com.");
		resetPage.maximumCharacterValidationlinkInEmail(ccuser.getMailinatorMaximumCharacters());
		CCUtils.assertEquals(resetPage.getMaximumCharacterMsg(), "Password should have less than 100 characters");
	}

}
