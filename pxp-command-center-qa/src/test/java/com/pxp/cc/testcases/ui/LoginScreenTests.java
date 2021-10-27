// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.LoginPage;
import com.pxp.cc.util.CCUtils;

public class LoginScreenTests extends CommandCenterGenericTest {

	public LoginPage commandcenterloginpage;
	public ClientDirectoryPage commandcenterhomepage;

	@Test(priority = 0, enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to verify login to Command Center Portal")
	public void verifyLoginToCCPortal() throws Exception {
		log("Test to verify login to Command Center Portal");
		log("Login to Command Center portal via url = " + ccuser.getUrl() + " and email =" + ccuser.getUsername()
				+ " and password=" + ccuser.getPassword());
		commandcenterhomepage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		log("Landed to Command Center Home Page.");
		commandcenterhomepage.clickUserIcon();
		commandcenterhomepage.clickLogout();
	}

	@Test(priority = 2, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void verifyLoginWithInvalidPwd() throws Exception {
		log("Test to verify user will get 'Invalid Username' or 'Invalid Password' error msg after login with wrong password.");
		log("Login to CC portal. url=" + ccuser.getUrl() + " and email=" + ccuser.getUsername()
				+ " and invalid password=" + ccuser.getInvalidPassword());
		log("Launching of Command Center URL and logging in.");
		commandcenterloginpage = new LoginPage(driver, ccuser.getUrl());
		log("Entering Username in Username Input field.");
		commandcenterloginpage.enterUsername(ccuser.getUsername());
		log("Entering wrong password in Password Input field.");
		commandcenterloginpage.enterPassword(ccuser.getInvalidPassword());
		commandcenterloginpage.clickShowButton();
		commandcenterloginpage.clickHideButton();
		log("clicking on Login button.");
		commandcenterloginpage.clickLogin();
		commandcenterloginpage.verifyInvalidUsernameOrPasswordError();
	}

	@Test(priority = 3, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void verifyLoginWithInvalidUserName() throws Exception {
		log("Test to verify user will get 'Invalid Username' and 'valid Password' error msg after login with wrong username.");
		log("Login to CC portal. url=" + ccuser.getUrl() + " and Invalid email=" + ccuser.getInvalidUsername()
				+ " and password=" + ccuser.getPassword());
		log("Launching of Command Center URL.");
		LoginPage commandcenterloginpage = new LoginPage(driver, ccuser.getUrl());
		log("Entering wrong Username in Username Input field.");
		commandcenterloginpage.enterUsername(ccuser.getInvalidUsername());
		log("Entering Password in Password Input field.");
		commandcenterloginpage.enterPassword(ccuser.getPassword());
		commandcenterloginpage.clickShowButton();
		commandcenterloginpage.clickHideButton();
		log("Clicking to Login button.");
		commandcenterloginpage.clickLogin();
		commandcenterloginpage.verifyInvalidUsernameOrPasswordError();
	}

	@Test(priority = 1, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void veifyPasswordShowHideButton() throws Exception {
		log("Login to CC portal. url= " + ccuser.getUrl() + " and password=" + ccuser.getPassword());
		log("Launching of Command Center URL");
		LoginPage commandcenterloginpage = new LoginPage(driver, ccuser.getUrl());
		log("Entering Password in Password Input Field.");
		commandcenterloginpage.enterPassword(ccuser.getPassword());
		log("Click on SHOW button in Password Input Field");
		commandcenterloginpage.clickShowButton();
		log("Verfying the 'TYPE' attribute of Password field");
		if ((commandcenterloginpage.verifyPwdTypeAttribute())) {
			log("Password is displayed");
		} else
			log("Password is NOT displayed");
		log("Click on HIDE button in Password Input Field");
		commandcenterloginpage.clickHideButton();
		log("Verfying the 'TYPE' attribute of Password field");
		if ((commandcenterloginpage.verifyPwdTypeAttribute())) {
			log("Password is displayed");
		} else
			log("Password is NOT displayed");
	}

	@Test(priority = 4, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void verifyLockoutUser() throws Exception {
		log("Test to verify user will be Locked out after 6 login attempts with wrong password.");
		log("Login to CC portal. url=" + ccuser.getUrl() + " and username=" + ccuser.getLockoutUsername()
				+ " and password=" + ccuser.getInvalidPassword());
		log("Launching of Command Center URL");
		LoginPage commandcenterloginpage = new LoginPage(driver, ccuser.getUrl());
		log("Attempting 6 times login to Command Center Portal");
		commandcenterloginpage.sixAttemptsLoginViaInvalidPwd(ccuser.getLockoutUsername(), ccuser.getInvalidPassword(),
				ccuser.getLoginAttempts());
		CCUtils.assertEquals(commandcenterloginpage.getLockOutErrMsg().trim(),
				commandcenterloginpage.lockoutErrorMsg.trim());
	}
}
