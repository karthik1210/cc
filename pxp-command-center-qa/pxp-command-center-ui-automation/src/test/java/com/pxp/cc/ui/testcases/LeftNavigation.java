// Copyright 2018-2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.ui.testcases;

import org.apache.log4j.BasicConfigurator;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.object.maps.homepage.CommandCenterHomePage;
import com.pxp.cc.object.maps.pojo.CCUser;

public class LeftNavigation extends CommandCenterGenericTest {

	public CCUser ccuser;
	public CommandCenterHomePage cchomepage;

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void leftPanelMenuValidation() throws Exception {
		BasicConfigurator.configure();
		log("Test to verify Left panel menu displayed after clicking on Hamburger Menu");
		log("Step 1: Set the Test Data for CC ");
		ccuser = new CCUser(property);
		ccuser.setCCuser();
		log("Step 2: Login to Command Center Portal");
		cchomepage = loginToCCPortal(super.driver, ccuser.getUrl(), ccuser.getNavigationEmail(), ccuser.getPassword());
		log("Step 3: Click to Hamburger Menu.");
		cchomepage.clickHamburger();
		log("Step 4: Clicking on all the PXP CC menu Items");
		cchomepage.clickCCMenuItems();
		cchomepage.clickUserIcon();
		cchomepage.clickLogout();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void adminLoginValidation() throws Exception {
		log("Test to verify user login via Admin Credentials and validate the Hamburger menu items");
		log("Step 1: Set the Test Data for CC -------");
		ccuser = new CCUser(property);
		ccuser.setCCuser();
		log("Step 2: Login to Command Center Portal with Admin Credentials");
		cchomepage = loginToCCPortal(super.driver, ccuser.getUrl(), ccuser.getAdminEmail(), ccuser.getPasswordAdmin());
		log("Step 3: Clicking on hamburger menu with Admin login");
		cchomepage.clickHamburger();
		log("Step 4: Clicking on all PXP CC menu Items with Admin login");
		cchomepage.clickCCMenuItems();
		cchomepage.clickUserIcon();
		cchomepage.clickLogout();
	}
}
