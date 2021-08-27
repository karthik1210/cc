package com.pxp.cc.testcases.ui;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.PropertyFileLoader;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.EditOrgNodePage;
import com.pxp.cc.objectmaps.GeneralSettingsPage;
import com.pxp.cc.testcases.ui.CommandCenterGenericTest;

public class EditOrgNodeTest extends CommandCenterGenericTest {

	public ClientDirectoryPage clientDirectoryPage;
	public GeneralSettingsPage generalSettingsPage;
	public EditOrgNodePage editOrgNodePage;

	@BeforeMethod()
	public void loginToCommandCenterPortal() throws Exception {
		logStep("Login to CC portal. url= " + ccuser.getUrl() + "and password=" + ccuser.getPassword());
		clientDirectoryPage = login(ccuser.getUrl(), ccuser.getUsername(), ccuser.getPassword());
	}

	@Test(priority = 0, enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testOrgSystemSaveEditFunctionality() throws Exception {
		PropertyFileLoader prop= new PropertyFileLoader();
		logStep("Enter client name in search text box and click on client name in client table");
		clientDirectoryPage.searchAndSelectClientNameInClientTable(prop.getProperty("new.active.client.name"));
		generalSettingsPage = new GeneralSettingsPage(driver);
		editOrgNodePage = new EditOrgNodePage(driver);
		editOrgNodePage.clickOnSystemNode();
		editOrgNodePage.clickEditButtonOfOrg();
		editOrgNodePage.editOrgOnGeneralSettingPage(prop.getProperty("new.active.sapid"), prop.getProperty("new.solutiondisplayname"),
				prop.getProperty("new.timezone"));
		editOrgNodePage.clickInactiveRadioButtonOfOrgNode();
		editOrgNodePage.clickSaveButtonOfOrg();
//		CCUtils.assertEquals(generalSettingsPage.errorMessageOnEditOrgForInactiveClient(), "System cannot be Activated as Client is Inactive");
	}

}
