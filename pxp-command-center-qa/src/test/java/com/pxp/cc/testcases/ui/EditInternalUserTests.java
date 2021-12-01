// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.AddNewInternalUsersPage;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.CommandCenterUsersPage;
import com.pxp.cc.objectmaps.InternalUsersPage;
import com.pxp.cc.util.CCUtils;

public class EditInternalUserTests extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public CommandCenterUsersPage commandCenterUsersPage;
	public InternalUsersPage internalUsersPage;
	public AddNewInternalUsersPage addNewInternalUsersPage;
	
	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button is displayed & fields available for editing")
	public void testEditMode() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Verify user redirected to command center users page");
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Clicked on user to edit");
		internalUsersPage.selectUser();
		internalUsersPage.clickEditInInternalUser();
		logStep("Clicked on user to edit");
		internalUsersPage.verifyPasswordUneditable();
		logStep("Verifying fields are editable");
		internalUsersPage.enterFirstName(CCUtils.generateAlphaNumericString(18));
		internalUsersPage.enterLastName(CCUtils.generateAlphaNumericString(18));
		internalUsersPage.enterEmail(CCUtils.generateNewEmail(11));
		internalUsersPage.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(12));
		internalUsersPage.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
		internalUsersPage.clickCancel();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify edit button is displayed & fe")
	public void testEditInternalUser() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Verify user redirected to command center users page");
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Clicked on user to edit");
		internalUsersPage.selectUser();
		internalUsersPage.clickEditInInternalUser();
		logStep("Clicked on user to edit");
		internalUsersPage.verifyPasswordUneditable();
		logStep("modifying editable fields detail");
		internalUsersPage.enterFirstName(CCUtils.generateAlphaNumericString(18));
		internalUsersPage.enterLastName(CCUtils.generateAlphaNumericString(18));
		internalUsersPage.enterEmail(CCUtils.generateNewEmail(11));
		internalUsersPage.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(12));
		internalUsersPage.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
		internalUsersPage.clickSave();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Verify error messages if incorrect data is entered")
	public void testErrorInEditInternalUser() throws Exception {
		logStep("Click on command center users icon in left side navigation panel");
		clientDirectoryPage.clickOnCommandCenterUsersIcon();
		commandCenterUsersPage = new CommandCenterUsersPage(driver);
		logStep("Verify user redirected to command center users page");
		internalUsersPage = new InternalUsersPage(driver);
		logStep("Clicked on user to edit");
		internalUsersPage.selectUser();
		internalUsersPage.clickEditInInternalUser();
		logStep("Clicked on user to edit");
		internalUsersPage.verifyPasswordUneditable();
		logStep("modifying editable fields detail");
		internalUsersPage.enterFirstName(CCUtils.generateAlphaNumericString(101));
		String actualFirstNameError = internalUsersPage.getFirstNameError();
		if (actualFirstNameError.equalsIgnoreCase("Maximum Characters reached")) {
			log("First name error is : " + actualFirstNameError);
		} else {
			log("First name error is : " + actualFirstNameError);
			CCUtils.assertTrue(false, "Test Failed");
		}
		internalUsersPage.enterLastName(CCUtils.generateAlphaNumericString(101));
		String actualLastNameError = internalUsersPage.getLastNameError();
		if (actualLastNameError.equalsIgnoreCase("Maximum Characters reached")) {
			log("Last name error is : " + actualLastNameError);
		} else {
			log("Last name error is : " + actualLastNameError);
			CCUtils.assertTrue(false, "Test Failed");
		}
		internalUsersPage.enterEmail(CCUtils.generateNewEmail(101));
		String actualEmailError = internalUsersPage.getEmailError();
		if (actualEmailError.equalsIgnoreCase("Maximum Characters reached")) {
			log("Email name error is : " + actualEmailError);
		} else {
			log("Email error is : " + actualEmailError);
			CCUtils.assertTrue(false, "Test Failed");
		}
		internalUsersPage.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(120));
		String actualUserNameError = internalUsersPage.getUserNameError();
		if (actualUserNameError.equalsIgnoreCase("Maximum Characters reached")) {
			log("Username error is : " + actualUserNameError);
		} else {
			log("Username error is : " + actualUserNameError);
			CCUtils.assertTrue(false, "Test Failed");
		}
		internalUsersPage.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()) + "23");
		String actualMobileError = internalUsersPage.getMobileNumberError();
		if (actualMobileError.equalsIgnoreCase("Maximum Characters reached")) {
			log("Mobile number error is : " + actualMobileError);
		} else {
			log("Mobile number error is : " + actualMobileError);
			CCUtils.assertTrue(false, "Test Failed");
		}
	}
}
