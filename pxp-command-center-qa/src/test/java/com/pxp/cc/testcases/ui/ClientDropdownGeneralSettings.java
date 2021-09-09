package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;

public class ClientDropdownGeneralSettings extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public GeneralSettingsPage generalSettingsPage;
	

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(testData.getProperty("base.url"), testData.getProperty("user.name"),
				testData.getProperty("user.password"));
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class, description = "Test to verify Search Client name visible in Client Dropdown Header and Recently Visited Section")
	public void testSearchedClientNameShouldPresentInClientDropdownHeader() {
		logStep("Test to verify that the searched client is visible general setting's Client Dropdown Header and Recently Visited Section.");
		logStep("Searching for client : "+ccuser.searchClientInClientDirectory);
		clientDirectoryPage.searchNode(ccuser.searchClientInClientDirectory);
		clientDirectoryPage.clickSearchButton();
		generalSettingsPage = clientDirectoryPage.selectSearchedClientNameInClientTable();
	}
}
