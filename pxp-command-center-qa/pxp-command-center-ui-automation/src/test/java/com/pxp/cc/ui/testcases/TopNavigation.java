// Copyright 2018-2021 NXGN Management, LLC. All Rights Reserved.
package com.pxp.cc.ui.testcases;

import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.object.maps.homepage.CommandCenterHomePage;
import com.pxp.cc.object.maps.pojo.CCUser;

public class TopNavigation extends CommandCenterGenericTest {

	public CCUser ccuser;
	public CommandCenterHomePage cchomepage;

	@Test(enabled = true, groups = {"AcceptanceTests", "SmokeTest"}, retryAnalyzer = RetryAnalyzer.class)
	public void verifyTopPanel() throws Exception {
		ccuser = new CCUser(property);
		ccuser.setCCuser();
		log("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		cchomepage = loginToCCPortal(this.driver, ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		log("Verifying Top Panel Elements");
		cchomepage.verifyTopPanelElements();
		log("Logout of CC Portal");
		cchomepage.clickUserIcon();
		Thread.sleep(2000);
		cchomepage.clickLogout();
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void verifySSOLinksforInternalUsers() throws Exception {
		ccuser = new CCUser(property);
		ccuser.setCCInternalUser();
		log("Login to CC portal. url=" + ccuser.getUrl() + " and email=" + ccuser.getUsernameInternal() + " and password="
				+ ccuser.getPasswordInternal());
		cchomepage = loginToCCPortal(this.driver, ccuser.getUrl(), ccuser.getUsernameInternal(), ccuser.getPasswordInternal());
		Thread.sleep(2000);
		log("Verifying SSO links for Internal Users");
		cchomepage.clickSSO();
		Thread.sleep(2000);
		cchomepage.getSsoLinks();
		cchomepage.verifySSOLinksForInternalUsers();
		log("Logout of CC Portal");
		cchomepage.clickUserIcon();
		Thread.sleep(2000);
		cchomepage.clickLogout();
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void verifySSOLinksforExternalUsers() throws Exception {
		ccuser = new CCUser(property);
		ccuser.setCCExternalUser();
		log("Login to CC portal. url=" + ccuser.getUrl() + " and email=" + ccuser.getUsernameExternal() + " and password=" + ccuser.getPasswordExternal());
		cchomepage = loginToCCPortal(this.driver, ccuser.getUrl(), ccuser.getUsernameExternal(), ccuser.getPasswordExternal());
		Thread.sleep(2000);
		log("Verifying SSO links for Internal Users");
		cchomepage.clickSSO();
		cchomepage.getSsoLinks();
		Thread.sleep(1000);
		cchomepage.verifySSOLinksForExternalUsers();
		log("Logout of CC Portal");
		cchomepage.clickUserIcon();
		Thread.sleep(2000);
		cchomepage.clickLogout();
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void verifyAlertsSection() throws Exception {
		ccuser = new CCUser(property);
		ccuser.setCCuser();
		log("Login to CC portal. url=" + ccuser.getUrl() + " and email=" + ccuser.getUsername() + " and password=" + ccuser.getPassword());
		cchomepage = loginToCCPortal(this.driver, ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		Thread.sleep(2000);
		log("Verifying Alerts content for Internal Users");
		cchomepage.clickAlerts();
		Thread.sleep(1000);
		cchomepage.verifyAlertsContent();
		log("Logout of CC Portal");
		Thread.sleep(1000);
		cchomepage.clickUserIcon();
		Thread.sleep(2000);
		cchomepage.clickLogout();
	}
}
