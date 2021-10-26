// Copyright 2018-2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;

public class TopNavigationTests extends CommandCenterGenericTest {

	public ClientDirectoryPage cchomepage;

	@Test(enabled = true, groups = { "UISmokeTest" }, retryAnalyzer = RetryAnalyzer.class)
	public void verifyTopPanel() throws Exception {
		log("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		cchomepage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
		log("Verifying Top Panel Elements");
		cchomepage.verifyTopPanelElements();
		log("Logout of CC Portal");
		cchomepage.clickUserIcon();
		cchomepage.clickLogout();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void verifySSOLinksforInternalUsers() throws Exception {
		log("Login to CC portal. url=" + ccuser.getUrl() + " and email=" + ccuser.getUsernameInternal()
				+ " and password=" + ccuser.getPasswordInternal());
		cchomepage = login(ccuser.getUrl(), ccuser.getUsernameInternal(), ccuser.getPasswordInternal());
		log("Verifying SSO links for Internal Users");
		cchomepage.clickSSO();
		cchomepage.getSsoLinks();
		cchomepage.verifySSOLinksForInternalUsers();
		log("Logout of CC Portal");
		cchomepage.clickUserIcon();
		cchomepage.clickLogout();
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void verifySSOLinksforExternalUsers() throws Exception {
		log("Login to CC portal. url=" + ccuser.getUrl() + " and email=" + ccuser.getUsernameExternal()
				+ " and password=" + ccuser.getPasswordExternal());
		cchomepage = login(ccuser.getUrl(), ccuser.getUsernameExternal(), ccuser.getPasswordExternal());
		Thread.sleep(2000);
		log("Verifying SSO links for Internal Users");
		cchomepage.clickSSO();
		cchomepage.getSsoLinks();
		cchomepage.verifySSOLinksForExternalUsers();
		log("Logout of CC Portal");
		cchomepage.clickUserIcon();
		cchomepage.clickLogout();
	}
}
