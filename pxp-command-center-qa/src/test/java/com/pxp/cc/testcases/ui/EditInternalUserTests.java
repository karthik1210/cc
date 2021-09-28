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
		internalUsersPage.verifyEditPassword();
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
		internalUsersPage.verifyEditPassword();
		logStep("modifying editable fields detail");
		internalUsersPage.enterFirstName(CCUtils.generateAlphaNumericString(18));
		internalUsersPage.enterLastName(CCUtils.generateAlphaNumericString(18));
		internalUsersPage.enterEmail(CCUtils.generateNewEmail(11));
		internalUsersPage.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(12));
		internalUsersPage.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
		internalUsersPage.clickSave();
		internalUsersPage.verifySuccessToaster();
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
		internalUsersPage.verifyEditPassword();
		logStep("modifying editable fields detail");
		internalUsersPage.enterFirstName(CCUtils.generateAlphaNumericString(101));
		internalUsersPage.getFirstNameError();
		internalUsersPage.enterLastName(CCUtils.generateAlphaNumericString(101));
		internalUsersPage.getLastNameError();
		internalUsersPage.enterEmail(CCUtils.generateNewEmail(101));
		internalUsersPage.getEmailError();
		internalUsersPage.enterUserName(CCUtils.generateAlphaNumericStringIncludingSpecialChar(120));
		internalUsersPage.getUserNameError();
		internalUsersPage.enterMobileNumber(String.valueOf(CCUtils.generateMobileNumber()));
		internalUsersPage.getMobileNumberError();

	}
}
