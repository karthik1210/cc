// Copyright 2021 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.apache.log4j.BasicConfigurator;
import org.testng.annotations.BeforeClass;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.common.utils.PropertyFileLoader;
import com.pxp.cc.model.CCUser;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.LoginPage;

public class CommandCenterGenericTest extends BaseTestNGWebDriver {

	private PropertyFileLoader testData;
	protected CCUser ccuser;

	@BeforeClass(alwaysRun = true)
	public void setUpTestData() throws Exception {
		testData = new PropertyFileLoader();
		ccuser = new CCUser(testData);
		ccuser.init();
		BasicConfigurator.configure();
	}

	public ClientDirectoryPage login(String url, String username, String password) throws InterruptedException {
		LoginPage commandcenterloginpage = new LoginPage(driver, url);
		ClientDirectoryPage commandcenterhomepage = commandcenterloginpage.login(username, password);
		return commandcenterhomepage;
	}
}
