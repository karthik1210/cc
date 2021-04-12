// Copyright 2018-2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.ui.testcases;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.object.maps.homepage.CommandCenterHomePage;
import com.pxp.cc.object.maps.loginpage.CommandCenterLoginPage;
import com.pxp.cc.object.maps.pojo.CCUser;
import com.pxp.cc.object.maps.utilities.CCUtils;

public class LoginScreen extends CommandCenterGenericTest {

	public CCUser ccuser;
	public CommandCenterLoginPage commandcenterloginpage;
	public CommandCenterHomePage commandcenterhomepage;

	@Test(priority = 0, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void verifyLoginToCCPortal() throws Exception {
		log("Test to verify login to Command Center Portal");
		ccuser = new CCUser(property);
		ccuser.setCCuser();
		log("Login to Command Center portal via url = " + ccuser.getUrl() + " and email =" + ccuser.getUsername()
				+ " and password=" + ccuser.getPassword());
		commandcenterhomepage = loginToCCPortal(this.driver, ccuser.getUrl(), ccuser.getUsername(),
				ccuser.getPassword());
		Thread.sleep(3000);
		log("Landed to Command Center Home Page.");
		Thread.sleep(1000);
		commandcenterhomepage.clickUserIcon();
		Thread.sleep(2000);
		commandcenterhomepage.clickLogout();
	}

	@Test(priority = 2, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void verifyLoginWithInvalidPwd() throws Exception {
		log("Test to verify user will get 'Invalid Username' or 'Invalid Password' error msg after login with wrong password.");
		ccuser = new CCUser(property);
		ccuser.setAdminCCWithWrongPassword();
		log("Login to CC portal. url=" + ccuser.getUrl() + " and email=" + ccuser.getUsername()
				+ " and invalid password=" + ccuser.getInvalidPassword());
		log("Launching of Command Center URL and logging in.");
		commandcenterloginpage = new CommandCenterLoginPage(driver, ccuser.getUrl());
		log("Entering Username in Username Input field.");
		commandcenterloginpage.enterUsername(ccuser.getUsername());
		Thread.sleep(1000);
		log("Entering wrong password in Password Input field.");
		commandcenterloginpage.enterPassword(ccuser.getInvalidPassword());
		commandcenterloginpage.clickShowButton();
		Thread.sleep(2000);
		commandcenterloginpage.clickHideButton();
		log("clicking on Login button.");
		commandcenterloginpage.clickLogin();
		Thread.sleep(4000);
		commandcenterloginpage.verifyInvalidUsernameOrPasswordError();
		Thread.sleep(1000);
	}

	@Test(priority = 3, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void verifyLoginWithInvalidUserName() throws Exception {
		log("Test to verify user will get 'Invalid Username' and 'valid Password' error msg after login with wrong username.");
		CCUser ccuser = new CCUser(property);
		ccuser.setAdminCCWithWrongUsername();
		log("Login to CC portal. url=" + ccuser.getUrl() + " and Invalid email=" + ccuser.getInvalidUsername()
				+ " and password=" + ccuser.getPassword());
		log("Launching of Command Center URL.");
		CommandCenterLoginPage commandcenterloginpage = new CommandCenterLoginPage(driver, ccuser.getUrl());
		log("Entering wrong Username in Username Input field.");
		commandcenterloginpage.enterUsername(ccuser.getInvalidUsername());
		Thread.sleep(1000);
		log("Entering Password in Password Input field.");
		commandcenterloginpage.enterPassword(ccuser.getPassword());
		commandcenterloginpage.clickShowButton();
		Thread.sleep(2000);
		commandcenterloginpage.clickHideButton();
		Thread.sleep(1000);
		log("Clicking to Login button.");
		commandcenterloginpage.clickLogin();
		Thread.sleep(4000);
		commandcenterloginpage.verifyInvalidUsernameOrPasswordError();
		Thread.sleep(1000);
	}

	@Test(priority = 1, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void veifyPasswordShowHideButton() throws Exception {
		CCUser ccuser = new CCUser(property);
		ccuser.setCCuser();
		log("Login to CC portal. url= " + ccuser.getUrl() + " and password=" + ccuser.getPassword());
		log("Launching of Command Center URL");
		CommandCenterLoginPage commandcenterloginpage = new CommandCenterLoginPage(driver, ccuser.getUrl());
		log("Entering Password in Password Input Field.");
		commandcenterloginpage.enterPassword(ccuser.getPassword());
		Thread.sleep(2000);
		log("Click on SHOW button in Password Input Field");
		commandcenterloginpage.clickShowButton();
		Thread.sleep(4000);
		log("Verfying the 'TYPE' attribute of Password field");
		if ((commandcenterloginpage.verifyPwdTypeAttribute())) {
			log("Password is displayed");
		} else
			log("Password is NOT displayed");
		log("Click on HIDE button in Password Input Field");
		commandcenterloginpage.clickHideButton();
		Thread.sleep(4000);
		log("Verfying the 'TYPE' attribute of Password field");
		if ((commandcenterloginpage.verifyPwdTypeAttribute())) {
			log("Password is displayed");
		} else
			log("Password is NOT displayed");
		Thread.sleep(1000);
	}

	@Test(priority = 4, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void verifyLockoutUser() throws Exception {
		log("Test to verify user will be Locked out after 6 login attempts with wrong password.");
		CCUser ccuser = new CCUser(property);
		ccuser.setAdminCCWithLockoutUsername();
		log("Login to CC portal. url=" + ccuser.getUrl() + " and username=" + ccuser.getLockoutUsername()
				+ " and password=" + ccuser.getInvalidPassword());
		log("Launching of Command Center URL");
		CommandCenterLoginPage commandcenterloginpage = new CommandCenterLoginPage(driver, ccuser.getUrl());
		log("Attempting 6 times login to Command Center Portal");
		commandcenterloginpage.sixAttemptsLoginViaInvalidPwd(ccuser.getLockoutUsername(), ccuser.getInvalidPassword(),
				ccuser.getLoginAttempts());
		Thread.sleep(2000);
		CCUtils.assertEquals(commandcenterloginpage.getLockOutErrMsg().trim(),
				commandcenterloginpage.lockoutErrorMsg.trim());
		Thread.sleep(1000);
	}
}
