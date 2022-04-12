// Copyright 2022 NXGN Management, LLC. All Rights Reserved. 

package com.pxp.cc.testcases.ui;

import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.common.utils.PropertyFileLoader;
import com.pxp.cc.model.CCUser;
import com.pxp.cc.objectmaps.ClientDirectoryPage;
import com.pxp.cc.objectmaps.LoginPage;

public class CommandCenterGenericTest extends BaseTestNGWebDriver {

	protected PropertyFileLoader testData;
	protected CCUser ccuser;

	@BeforeClass(alwaysRun = true)
	public void setUpTestData() throws Exception {
		testData = new PropertyFileLoader();
		ccuser = new CCUser(testData);
		ccuser.init();
		BasicConfigurator.configure();
	}

	public ClientDirectoryPage login(String url, String username, String password) throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver, url);
		return PageFactory.initElements(driver, ClientDirectoryPage.class);
	}
}
