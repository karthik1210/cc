// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.ui.testcases;

import java.security.SecureRandom;

import org.apache.log4j.BasicConfigurator;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.object.maps.passwordresetpage.PasswordResetPage;
import com.pxp.cc.object.maps.pojo.CCUser;

public class PasswordResetTests extends CommandCenterGenericTest {
	SecureRandom random = new SecureRandom();
	String autogeneratePassword = PasswordResetPage.genPassword(random);

	public CCUser ccuser;

	@Test(priority = 0, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void verifySuccessfullyPasswordResetFunctionality() throws Exception {
		BasicConfigurator.configure();
		log("Test to verify new password created successfully" + autogeneratePassword);
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.clickOnforgotPasswordLink(super.driver, ccuser, autogeneratePassword);
		log("Entering the auto generated password on password reset page");
	}

	@Test(priority = 1, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void verifySetNewPasswordFunctionalityWithAlreadyUsedPassword() throws Exception {
		BasicConfigurator.configure();
		log("Test to verify email validation with already used ten old password with auto generated password"
				+ autogeneratePassword);
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.resetPasswordAlreadyUsedPasswordValidation(super.driver, ccuser, autogeneratePassword);
		log("Entering the auto generated password on password reset page");
	}

	@Test(priority = 2, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void verifyPasswordResetFunctionalityForAlreadyUsedToken() throws Exception {
		log("Test to verify set new password with alredy used link of password reset");
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.resetPasswordAlreadyUsedTokenValidation(super.driver, ccuser);
		log("Displaying Reset your password page");
	}

	@Test(priority = 3, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void verifyPasswordResetFunctionalityWithoutEnteringUsername() throws Exception {
		BasicConfigurator.configure();
		log("Test to verify reset password with blank email id");
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.emailWithoutData(super.driver, ccuser);
		log("Displaying Reset your password page");
	}

	@Test(priority = 4, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void verifyPasswordResetFunctionalityForUnregisteredUser() throws Exception {
		log("Test to verify reset password with unregistered user");
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.emailAddressValidation(super.driver, ccuser);
		log("Displaying Reset your password page");
	}

	@Test(priority = 5, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void verifyPasswordResetFunctionalityForExternalUser() throws Exception {
		BasicConfigurator.configure();
		log("Test to verify reset password with external user");
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.externalUserLogin(super.driver, ccuser);
		log("Displaying Oops! page");
	}

	@Test(priority = 6, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void verifySetNewPasswordFunctionalityWithRepetitiveSequenceInPasswordField() throws Exception {
		log("Test to verify password reset with repetitive sequence entered in password fields");
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.resetPasswordRepetitiveSequenceValidation(super.driver, ccuser);
		log("Displaying Reset your password page");
	}

	@Test(priority = 7, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void verifySetNewPasswordFunctionalityWithSpaceInPasswordField() throws Exception {
		log("Test to verify set password validation with space entered in password fields");
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.resetPasswordSpaceValidations(super.driver, ccuser);
		log("Displaying Reset your password page");
	}

	@Test(priority = 8, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void verifySetNewPasswordFunctionalityWithCommonlyUsedPassword() throws Exception {
		log("Test to verify set new password with commonly used password");
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.resetPasswordCommonlyUsedValidation(super.driver, ccuser);
		log("Displaying Reset your password page");
	}

	@Test(priority = 9, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void verifySetNewPasswordFunctionalityWithMaximumCharacters() throws Exception {
		log("Test to verify set new password with max 101 characters");
		CCUser ccuser = new CCUser(property);
		log("Set the Test Data for CC");
		ccuser.setCCuser();
		log("Navigating to Command Center portal");
		PasswordResetPage resetPage = new PasswordResetPage(driver, ccuser.getUrl());
		log("Click on forgot password on Command Center portal");
		resetPage.resetPasswordMaximumCharactersValidation(super.driver, ccuser);
		log("Displaying Reset your password page");
	}

}
