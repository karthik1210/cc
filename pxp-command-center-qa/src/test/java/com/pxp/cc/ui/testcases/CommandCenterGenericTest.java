package com.pxp.cc.ui.testcases;

import org.testng.annotations.BeforeClass;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.common.utils.PropertyFileLoader;
import com.pxp.cc.model.CCUser;
import com.pxp.cc.object.maps.HomePage;
import com.pxp.cc.object.maps.LoginPage;

public class CommandCenterGenericTest extends BaseTestNGWebDriver {

	private PropertyFileLoader testData;
	protected CCUser ccuser;

	@BeforeClass(alwaysRun = true)
	public void setUpTestData() throws Exception {
		testData = new PropertyFileLoader();
		log("Set the Test Data for CC ");
		ccuser = new CCUser(testData);
		ccuser.init();
	}

	public HomePage login(String url, String username, String password) throws InterruptedException {
		log("Login to CC portal. url=" + url + " and email=" + username + " and password=" + password);
		LoginPage commandcenterloginpage = new LoginPage(driver, url);
		HomePage commandcenterhomepage = commandcenterloginpage.login(username, password);
		return commandcenterhomepage;
	}
}
